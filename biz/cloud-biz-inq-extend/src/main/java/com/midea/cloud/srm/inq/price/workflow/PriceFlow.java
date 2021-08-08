package com.midea.cloud.srm.inq.price.workflow;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.base.BigCategoryTypeEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.WorkflowClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.inq.inquiry.service.IFileService;
import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.vo.MaterialMaxCategoryVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.inq.price.dto.InsertPriceApprovalDTO;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalFile;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequisitionDetail;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.workflow.dto.AttachFileDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowCreateResponseDto;
import com.midea.cloud.srm.model.workflow.dto.WorkflowFormDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 *  寻源结果审批审批流
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/26
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/priceFLow")
public class PriceFlow {

    @Resource
    private WorkflowClient workflowClient;

    @Resource
    private BaseClient baseClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Resource
    private RbacClient rbacClient;


    @Value("${template.priceTempId}")
    private String tempId;

    @Value("${template.priceSrmLink}")
    private String srmLink;

//    private String tempId = "17562998d126b289aff61a048608f018";
//    private String srmLink = "http://10.0.10.48/#/formDataPreview?funName=pricedingProject&formId=$formId$";



    /**
     * <pre>
     *
     * </pre>
     *
     * @author chenwt24@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: 2020-10-26
     *  修改内容:
     * </pre>
     */
    @PostMapping(value = "/submitPriceFlow")
    public String submitPriceConfFlow(@RequestBody InsertPriceApprovalDTO insertPriceApprovalDTO) throws Exception {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        ApprovalHeader approvalHeader = insertPriceApprovalDTO.getApprovalHeader();
        //计算汇率的值
        BigDecimal rateByFromTypeAndToType = baseClient.getRateByFromTypeAndToType(approvalHeader.getStandardCurrency(), null);
        Assert.notNull(rateByFromTypeAndToType,"找不到币种对应的汇率，请维护相应币种信息");
        approvalHeader.setBidAmount(approvalHeader.getBidAmount().multiply(rateByFromTypeAndToType));

        List<ApprovalFile> approvalFiles = insertPriceApprovalDTO.getApprovalFiles();
        Assert.notNull(loginAppUser.getCeeaEmpNo(), "申请人工号不能为空");
        WorkflowFormDto workflowForm = new WorkflowFormDto();
        String urlFormId = String.valueOf(approvalHeader.getApprovalHeaderId());

        List<ApprovalBiddingItemVO> approvalBiddingItemList = insertPriceApprovalDTO.getApprovalBiddingItemList();
        //处理物料对应的小类code信息
        approvalBiddingItemList = getcategoryCode(approvalBiddingItemList);
        insertPriceApprovalDTO.setApprovalBiddingItemList(approvalBiddingItemList);

        String formId = "";
        String docSubject = "寻源结果审批_"+approvalHeader.getApprovalNo();
        String fdTemplateId	=tempId;
        String docContent = "";


        JSONObject formValues = new JSONObject();



        String fd_approveLevel = "";
        if(isN1(insertPriceApprovalDTO)){
            fd_approveLevel = "N1";
        }else if(isN2(insertPriceApprovalDTO)){
            fd_approveLevel = "N2";
        }else if(isN3(insertPriceApprovalDTO)){
            fd_approveLevel = "N3";
        }


        //1.	审批层级：传递N1或者N2或者N3，逻辑上文已说明

        formValues.put("fd_approveLevel",fd_approveLevel);

        //2.	知会节点：传递N21或者空，逻辑上文已说明
        formValues.put("fd_notify",isN21(insertPriceApprovalDTO)?"N21":"");

        //10.	OU清单：如果是联采，则将中标行的OU清单传递给BPM
        //a)	汇总中标行的OU清单，如果该行是OU组合，则找到OU组合对应的OU清单，汇总到OU清单里
        //b)	传递给BPM的OU格式：23_OU_银川隆基，即数据库表中的OU CODE
        //c)	如果OU清单里有多个，则用英文分号“;”隔开
        formValues.put("fd_OU",isLianCai(insertPriceApprovalDTO)?getOU(insertPriceApprovalDTO):"N");



        formValues.put("fd_user_name",loginAppUser.getCeeaEmpNo());
        formValues.put("fd_document_num",approvalHeader.getApprovalNo());
        formValues.put("fd_srm_link",srmLink.replace("$formId$",urlFormId));
        formValues.put("fd_38e445b27b1286","");
        formValues.put("fd_38e061455fb0b4","");
        formValues.put("fd_38e06165ad1a8c","");
        formValues.put("fd_mobile_link",srmLink.replace("$formId$",urlFormId));

        String docStatus ="20";
        JSONObject docCreator = new JSONObject();
        docCreator.put("PersonNo",loginAppUser.getCeeaEmpNo());
        String fdKeyword ="[\"寻源结果\", \"审批\"]";
        String docProperty ="";
        workflowForm.setFormInstanceId(String.valueOf(approvalHeader.getApprovalHeaderId()));
        workflowForm.setDocSubject(docSubject);
        workflowForm.setFdTemplateId(fdTemplateId);
        workflowForm.setDocContent(docContent);
        workflowForm.setDocCreator(docCreator);
        workflowForm.setFormValues(formValues);
        workflowForm.setDocStatus(docStatus);


//        //附件
        ArrayList<AttachFileDto> fileDtos = new ArrayList<AttachFileDto>();
        if (CollectionUtils.isNotEmpty(approvalFiles)){
            approvalFiles.stream().forEach(
                    x->{
                        AttachFileDto attachFileDto = new AttachFileDto();
                        attachFileDto.setFdKey("fd_att");
                        attachFileDto.setFileId(x.getApprovalFileId());
                        fileDtos.add(attachFileDto);
                    });
        }

        workflowForm.setFileDtos(fileDtos);
        //起草人意见
        // 流程参数
//        JSONObject flowParam = new JSONObject();
//        flowParam.put("auditNote",requirementHead.getCeeaDrafterOpinion());
////      "{auditNote:\"请审核\", futureNodeId:\"N7\", changeNodeHandlers:[\"N7:1183b0b84ee4f581bba001c47a78b2d9;131d019fbac792eab0f0a684c8a8d0ec\"]}";
//        workflowForm.setFlowParam(flowParam);



        WorkflowCreateResponseDto workflowCreateResponse;
        if ("N".equals(insertPriceApprovalDTO.getProcessType())){
            //起草人意见
            // 流程参数
            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", "废弃");
            flowParam.put("operationType", "drafter_abandon");
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.updateFlow(workflowForm);
        }else {

            JSONObject flowParam = new JSONObject();
            flowParam.put("auditNote", StringUtils.isNotEmpty(approvalHeader.getCeeaDrafterOpinion())?approvalHeader.getCeeaDrafterOpinion():"起草人意见");
            workflowForm.setFlowParam(flowParam);
            workflowCreateResponse = workflowClient.submitFlow(workflowForm);
        }

        formId = workflowCreateResponse.getProcessId();
        return formId;
    }

    //6.	N1的判断规则
    //1)	中标行中有任一一行的小类为玻璃，玻璃的小类编码如下：
    public boolean isGlass(List<ApprovalBiddingItemVO> approvalBiddingItemList){
        List<String> strings = Arrays.asList("100901", "100902");
        return approvalBiddingItemList.stream().anyMatch(x->strings.contains(x.getCategoryCode()));
    }

    //2)	或者，中标行中有任一一行的小类为硅料，硅料的小类为100201
    public boolean isGuiliao(List<ApprovalBiddingItemVO> approvalBiddingItemList){
        return approvalBiddingItemList.stream().anyMatch(x->x.getCategoryCode().equals("100201"));
    }

    //3)	或者，中标行有任一一行的大类为物流，物流的大类编码为“60”并且中标金额<50万
    public boolean isWuliu(List<ApprovalBiddingItemVO> approvalBiddingItemList){
        List<MaterialMaxCategoryVO> materialItemVoList = baseClient.queryCategoryMaxCodeByMaterialIds(approvalBiddingItemList.stream().map(item -> item.getItemId()).collect(Collectors.toList()));
        return materialItemVoList.stream().anyMatch(x -> x.getCategoryCode().equals("60"));
    }

    //4)	或者，中标行有任一一行的小类为包材，并且中标金额<50万，
    public boolean isBaoCai(List<ApprovalBiddingItemVO> approvalBiddingItemList){
        List<String> strings = Arrays.asList("102201", "102202","102203","102204","102205","102301","102302","102401","102402","102403","102404","102501","102502","102503","102504","102505","102506","102507","102601","102602","102701","102801","102901");
        return approvalBiddingItemList.stream().anyMatch(x->strings.contains(x.getCategoryCode()));
    }

    //5)	或者，中标行有任一一行的小类为化学品，并且中标金额<50万
    public boolean isHuaXuePin(List<ApprovalBiddingItemVO> approvalBiddingItemList){
        List<String> strings = Arrays.asList("101801", "102802","102803","102804","102805","102806","102807","102808","102901","102403","102404","102501","102502","102503","102504","102505","102506","102507","102601","102602","102701","102801","102901");
        return approvalBiddingItemList.stream().anyMatch(x->strings.contains(x.getCategoryCode()));
    }

    //6)	或者，中标行为计划性物资，且任取一行的小类不为玻璃、硅料、包材、化学品或大类不为物流，且中标金额<200万
    public boolean isJhNotBl(InsertPriceApprovalDTO insertPriceApprovalDTO){
        ApprovalHeader approvalHeader = insertPriceApprovalDTO.getApprovalHeader();
        BigDecimal bidAmount = approvalHeader.getBidAmount();
        List<ApprovalBiddingItemVO> items = insertPriceApprovalDTO.getApprovalBiddingItemList();
        return isPlanMaterials(insertPriceApprovalDTO) && !isGlass(items) && !isGuiliao(items) && !isBaoCai(items) && !isHuaXuePin(items) && !isWuliu(items);
    }

    //7)	或者，中标行有任一一行的大类为20（设备）、40（服务类）、50（废旧物资处置）、70（综合类物资）且为总部且中标金额<50万
    public boolean is20andZongbu(InsertPriceApprovalDTO insertPriceApprovalDTO){
        return is20and40and50and70(insertPriceApprovalDTO) && isHeadQuarters(insertPriceApprovalDTO);
    }

    //8)	或者，中标行有任一一行大类为20（设备）、40（服务类）、50（废旧物资处置）、70（综合类物资）且为基地且中标金额<20万
    public boolean is20andJidi(InsertPriceApprovalDTO insertPriceApprovalDTO){
        return is20and40and50and70(insertPriceApprovalDTO) && isBase(insertPriceApprovalDTO);
    }

    //9)	或者，中标行有任一一行的大类为30（备品备件）且为联采且中标金额<50万
    public boolean is30andLianCai(InsertPriceApprovalDTO insertPriceApprovalDTO){
        return is30(insertPriceApprovalDTO) && isLianCai(insertPriceApprovalDTO);
    }

    //10)	或者，中标行有任一一行大类为30（备品备件）且为基地且中标金额<20万
    public boolean is30andJidi(InsertPriceApprovalDTO insertPriceApprovalDTO){
        return is30(insertPriceApprovalDTO) && isBase(insertPriceApprovalDTO);
    }

    public boolean isN1(InsertPriceApprovalDTO insertPriceApprovalDTO){
        ApprovalHeader approvalHeader = insertPriceApprovalDTO.getApprovalHeader();
        List<ApprovalBiddingItemVO> items = insertPriceApprovalDTO.getApprovalBiddingItemList();
        BigDecimal bidAmount = approvalHeader.getBidAmount();
        BigDecimal w20 = new BigDecimal(200000);
        BigDecimal w50 = new BigDecimal(500000);
        BigDecimal w200 = new BigDecimal(2000000);

        return  isGlass(items)
                || isGuiliao(items)
                || (isWuliu(items) && bidAmount.compareTo(w50)<0)
                || (isBaoCai(items) && bidAmount.compareTo(w50) < 0)
                || (isHuaXuePin(items) && bidAmount.compareTo(w50) < 0)
                || (isJhNotBl(insertPriceApprovalDTO) && bidAmount.compareTo(w200) < 0)
                || (is20andZongbu(insertPriceApprovalDTO) && bidAmount.compareTo(w50) < 0)
                || (is20andJidi(insertPriceApprovalDTO) && bidAmount.compareTo(w20) < 0)
                || (is30andLianCai(insertPriceApprovalDTO) && bidAmount.compareTo(w50) < 0)
                || (is30andJidi(insertPriceApprovalDTO) && bidAmount.compareTo(w20) < 0);
    }

    //7.	N2的判断规则
    //1)	中标行中有任一一行的小类为包材、化学品或者大类为物流，且中标总金额>=50万

    //2)	或者，中标行为计划性物资，且任取一行的小类不为包材、化学品或大类不为物流，且中标金额>=200万

    //3)	或者，中标行有任一一行的大类为20（设备）、40（服务类）、50（废旧物资处置）、70（综合类物资）且为总部且中标金额>=50万

    //4)	或者，中标行有任一一行大类为20（设备）、40（服务类）、50（废旧物资处置）、70（综合类物资）且为基地且中标金额>=20万

    //5)	或者，中标行有任一一行的大类为30（备品备件）且为联采且中标金额>=50万

    //6)	或者，中标行有任一一行大类为30（备品备件）且为基地且中标金额>=20万
    public boolean isN2(InsertPriceApprovalDTO insertPriceApprovalDTO){
        ApprovalHeader approvalHeader = insertPriceApprovalDTO.getApprovalHeader();
        List<ApprovalBiddingItemVO> items = insertPriceApprovalDTO.getApprovalBiddingItemList();
        BigDecimal bidAmount = approvalHeader.getBidAmount();
        BigDecimal w20 = new BigDecimal(200000);
        BigDecimal w50 = new BigDecimal(500000);
        BigDecimal w200 = new BigDecimal(2000000);
        BigDecimal w1000 = new BigDecimal(10000000);

        return  ((isBaoCai(items) || isHuaXuePin(items) || isWuliu(items) && bidAmount.compareTo(w50)>=0)
                || (isPlanMaterials(insertPriceApprovalDTO) && (!(isBaoCai(items) && isHuaXuePin(items)) || !isWuliu(items)) && bidAmount.compareTo(w200)>=0)
                || (is20andZongbu(insertPriceApprovalDTO) && bidAmount.compareTo(w50) >= 0)
                || (is20andJidi(insertPriceApprovalDTO) && bidAmount.compareTo(w20) >= 0)
                || (is30andLianCai(insertPriceApprovalDTO) && bidAmount.compareTo(w50) >= 0)
                || (is30andJidi(insertPriceApprovalDTO) && bidAmount.compareTo(w20) >= 0))
                && bidAmount.compareTo(w1000)<0;
    }



    //8.	N21的判断规则

    //1)	中标行中有任一一行的小类为玻璃

    //2)	或者，中标行中有任一一行的小类为硅料

    //3)	或者，中标行有任一一行的大类为物流，物流的大类编码为“60”并且中标金额<50万

    //4)	或者，中标行有任一一行的小类为包材，并且中标金额<50万

    //5)	或者，中标行有任一一行的小类为化学品，并且中标金额<50万

    //6)	或者，中标行为计划性物资，且任取一行的小类不为玻璃、硅料、包材、化学品或大类不为物流，且中标金额<200万
    public boolean isN21(InsertPriceApprovalDTO insertPriceApprovalDTO){
        ApprovalHeader approvalHeader = insertPriceApprovalDTO.getApprovalHeader();
        List<ApprovalBiddingItemVO> items = insertPriceApprovalDTO.getApprovalBiddingItemList();
        BigDecimal bidAmount = approvalHeader.getBidAmount();
        BigDecimal w50 = new BigDecimal(500000);
        BigDecimal w200 = new BigDecimal(2000000);

        return  isGlass(items)
                || isGuiliao(items)
                || (isWuliu(items) && bidAmount.compareTo(w50)<0)
                || (isBaoCai(items) && bidAmount.compareTo(w50) < 0)
                || (isHuaXuePin(items) && bidAmount.compareTo(w50) < 0)
                || (isPlanMaterials(insertPriceApprovalDTO) && (!(isGlass(items) && isGuiliao(items) && isBaoCai(items) && isHuaXuePin(items)) || !isWuliu(items)) && bidAmount.compareTo(w200) >= 0);
    }


    //9.	N3的判断规则
    //1)	该单为计划性物资且中标金额>=1000万    补充逻辑：且不是玻璃，不是硅料
    public boolean isN3(InsertPriceApprovalDTO insertPriceApprovalDTO){
        ApprovalHeader approvalHeader = insertPriceApprovalDTO.getApprovalHeader();
        BigDecimal bidAmount = approvalHeader.getBidAmount();
        BigDecimal w1000 = new BigDecimal(10000000);
        return  isPlanMaterials(insertPriceApprovalDTO) && bidAmount.compareTo(w1000) >=0
                && !isGlass(insertPriceApprovalDTO.getApprovalBiddingItemList())
                && !isGuiliao(insertPriceApprovalDTO.getApprovalBiddingItemList());
    }




    //10.	OU清单：如果是联采，则将中标行的OU清单传递给BPM
    //a)	汇总中标行的OU清单，如果该行是OU组合，则找到OU组合对应的OU清单，汇总到OU清单里
    //b)	传递给BPM的OU格式：23_OU_银川隆基，即数据库表中的OU CODE
    //c)	如果OU清单里有多个，则用英文分号“;”隔开
    public String getOU(InsertPriceApprovalDTO insertPriceApprovalDTO){
        List<ApprovalBiddingItemVO> approvalBiddingItemList = insertPriceApprovalDTO.getApprovalBiddingItemList();
        //a)	汇总中标行的OU清单，如果该行是OU组合，则找到OU组合对应的OU清单，汇总到OU清单里
        //b)	传递给BPM的OU格式：23_OU_银川隆基，即数据库表中的OU CODE
        //c)	如果OU清单里有多个，则用英文分号“;”隔开
        ArrayList<String> strings = new ArrayList<>();
        for (ApprovalBiddingItemVO item:approvalBiddingItemList){
            if(ObjectUtils.isEmpty(item.getOuId())){
                strings.add(item.getOrgName());
            }else{
                List<String> collect = baseClient.queryOuDetailById(item.getOuId()).getDetails().stream().map(x -> x.getOuName()).collect(Collectors.toList());
                strings.addAll(collect);
            }
        }
        return StringUtils.join(strings,";");

    }

    //TODO：dept需要补齐部门编码
    //1)	总部：如果单据创建人对应的上上级部门为供应链管理中心(100019)，且创建人的部门不是西安组件厂和BIPV采购组的，则该单为总部，通过部门编码判断
    public boolean isHeadQuarters(InsertPriceApprovalDTO insertPriceApprovalDTO){
        ApprovalHeader approvalHeader = insertPriceApprovalDTO.getApprovalHeader();
        Long createdId = approvalHeader.getCreatedId();

        //获取创建人的基本信息
        User user = rbacClient.getUser(new User().setUserId(createdId));
//        Dept dept = baseClient.get(user.getCeeaDeptId());

        Dept grandParentDept = baseClient.queryGrandParentDept(user.getCeeaDeptId());
        //总部：如果单据创建人对应的上上级部门为供应链管理中心(100019)，且创建人的部门不是西安组件厂和BIPV采购组的，则该单为总部，通过部门编码判断
        return !ObjectUtils.isEmpty(grandParentDept) && StringUtils.equals("100019",grandParentDept.getDeptid()) && !StringUtils.equals("西安组件厂",user.getDepartment()) && !StringUtils.equals("BIPV采购组",user.getDepartment());
    }

    //2)	如果为其他情况，则该单为基地
    public boolean isBase(InsertPriceApprovalDTO insertPriceApprovalDTO){
        return !isHeadQuarters(insertPriceApprovalDTO);
    }

    //4.	联采的判定规则：如果这一单不是总部，且中标行存在OU组或者中标行的OU不一样，则为联采
    public boolean isLianCai(InsertPriceApprovalDTO insertPriceApprovalDTO){

        List<ApprovalBiddingItemVO> approvalBiddingItemList = insertPriceApprovalDTO.getApprovalBiddingItemList();

        boolean haveOUZ = approvalBiddingItemList.stream().anyMatch(x -> !ObjectUtils.isEmpty(x.getOuId()));

        boolean diffOU = approvalBiddingItemList.stream().filter(x -> x.getOrgId()!=null).collect(Collectors.groupingBy(x -> x.getOrgId())).keySet().size() > 1;

        return !isHeadQuarters(insertPriceApprovalDTO) && (haveOUZ || diffOU);

    }

    //5.	计划性物资的判定规则：随便取一个中标行，对应的大类不是20（设备）、40（服务类）、50（废旧物资处置）、70（综合类物资）、30（备品备件），则该单为计划性物资
    public boolean isPlanMaterials(InsertPriceApprovalDTO insertPriceApprovalDTO){
        List<String> arr = Arrays.asList("20", "30", "40", "50", "70");

        List<ApprovalBiddingItemVO> approvalBiddingItemList = insertPriceApprovalDTO.getApprovalBiddingItemList();

        List<MaterialMaxCategoryVO> materialItemVoList = baseClient.queryCategoryMaxCodeByMaterialIds(approvalBiddingItemList.stream().map(item -> item.getItemId()).collect(Collectors.toList()));

        return materialItemVoList.stream().noneMatch(x -> arr.contains(x));
    }

    //中标行有任一一行的大类为20（设备）、40（服务类）、50（废旧物资处置）、70（综合类物资）
    public boolean is20and40and50and70(InsertPriceApprovalDTO insertPriceApprovalDTO){
        List<String> arr = Arrays.asList("20", "40", "50", "70");

        List<ApprovalBiddingItemVO> approvalBiddingItemList = insertPriceApprovalDTO.getApprovalBiddingItemList();

        List<MaterialMaxCategoryVO> materialItemVoList = baseClient.queryCategoryMaxCodeByMaterialIds(approvalBiddingItemList.stream().map(item -> item.getItemId()).collect(Collectors.toList()));

        return materialItemVoList.stream().anyMatch(x -> arr.contains(x));
    }

    //中标行有任一一行的大类为30（备品备件）
    public boolean is30(InsertPriceApprovalDTO insertPriceApprovalDTO){

        List<ApprovalBiddingItemVO> approvalBiddingItemList = insertPriceApprovalDTO.getApprovalBiddingItemList();

        List<MaterialMaxCategoryVO> materialItemVoList = baseClient.queryCategoryMaxCodeByMaterialIds(approvalBiddingItemList.stream().map(item -> item.getItemId()).collect(Collectors.toList()));

        return materialItemVoList.stream().anyMatch(x->StringUtils.equals("30",x.getCategoryCode()));
    }

    /**
     *
     * @param approvalBiddingItemVOList
     * @return
     */
    public List<ApprovalBiddingItemVO> getcategoryCode(List<ApprovalBiddingItemVO> approvalBiddingItemVOList){
        if (CollectionUtils.isNotEmpty(approvalBiddingItemVOList)){
            List<String> itemCodes = approvalBiddingItemVOList.stream().filter(x -> StringUtil.notEmpty(x.getItemCode())).map(
                    ApprovalBiddingItemVO::getItemCode).collect(Collectors.toList());
            Map<String, MaterialItem> materialItemMap = baseClient.ListMaterialItemByCategoryCode(itemCodes);
            for (ApprovalBiddingItemVO approvalBiddingItemVO:approvalBiddingItemVOList){
                MaterialItem materialItem = materialItemMap.get(approvalBiddingItemVO.getItemCode());
                Assert.isTrue(null!=materialItem&&StringUtils.isNotEmpty(materialItem.getCategoryCode()),"物料："+approvalBiddingItemVO.getItemCode()+"("+approvalBiddingItemVO.getItemName()+"),找不到对应的品类信息，请维护后重试！");
                approvalBiddingItemVO.setCategoryCode(materialItem.getCategoryCode());
            }
        }
        return approvalBiddingItemVOList;
    }

}

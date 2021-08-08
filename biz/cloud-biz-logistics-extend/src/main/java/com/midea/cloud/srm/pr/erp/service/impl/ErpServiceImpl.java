package com.midea.cloud.srm.pr.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.pm.pr.requirement.DUTY;
import com.midea.cloud.common.enums.pm.pr.requirement.PurchaseType;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.component.handler.InjectDefMetaObjectHelper;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequisitionDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.*;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.pr.division.mapper.DivisionCategoryMapper;
import com.midea.cloud.srm.pr.erp.service.IErpService;
import com.midea.cloud.srm.pr.erp.service.IRequirementLineNonAutoService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.pr.requirement.service.IRequisitionDetailService;
import com.midea.cloud.srm.pr.requirement.service.IRequisitionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  Erp总线服务实现类 pmp模块
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/28 14:53
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class ErpServiceImpl implements IErpService {

    /**
     * erp采购申请接口表Service
     */
    @Resource
    private IRequisitionService iRequisitionService;

    @Resource
    private DivisionCategoryMapper divisionCategoryMapper;

    /**
     * erp采购申请明细接口表Service
     */
    @Resource
    private IRequisitionDetailService iRequisitionDetailService;

    /**
     * srm采购需求头表接口Service
     */
    @Resource
    private IRequirementHeadService iRequirementHeadService;

    /**
     * srm采购需求行表接口Service
     */
    @Resource
    private IRequirementLineService iRequirementLineService;

    /**
     * feign跨模块调用baseClient的接口Service
     * 用途：根据库存组织编码查询库存组织Id和名称
     */
    @Resource
    private BaseClient baseClient;

    @Resource
    private RbacClient rbacClient;

    @Resource
    private IRequirementLineNonAutoService iRequirementLineNonAutoService;

    //private Map<String, MaterialItem> materialItemMap;
    //private Map<String, PurchaseCategory> purchaseCategorieMAXMap;
    //private Map<Long, PurchaseCategory> purchaseCategorieMINMap;
    private Map<String, PurchaseCurrency> purchaseCurrencieMap;
    //private Map<String, InvOrganization> invOrganizationMap;


    /**
     * 新增/修改 erp采购申请(暂时关闭)
     *
     * @param requisitionsList
     * @param instId           请求头id（消息头i）
     * @param requestTime      请求时的时间
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse saveOrUpdateRequisitions(List<RequisitionDTO> requisitionsList, String instId, String requestTime) {

        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();

        //设置默认返回参数
        String returnStatus = "S";//返回状态 :S成功 E 失败
        String returnMsg = "";//返回信息  成功空，失败返回对应错误信息
        String returnCode = "200";//相应代码

        try {
            Assert.isTrue(StringUtils.isNotEmpty(instId), "请求头为空，请检查后重新提交。");
            Assert.isTrue(StringUtils.isNotEmpty(requestTime), "请求时间为空，请检查后重新提交。");
            Assert.isTrue(CollectionUtils.isNotEmpty(requisitionsList), "数据采购申请为空，请检查后重试。");
            //填充返回信息
            esbInfo.setInstId(instId);//请求头
            esbInfo.setRequestTime(requestTime);//请求时时间

            // ----------------------------------------------------------------TODO 把查询全部的数据逻辑改为查询部分开始---------------------------------------------------------------------

            // ---------------------------------------------------------TODO 新的查询开始---------------------------------------------------------------
            //物料小类
            Map<Long, PurchaseCategory> purchaseCategorieMINMap = new HashMap<>();
            //物料大类
            Map<String, PurchaseCategory> purchaseCategorieMAXMap = new HashMap<>();
            //业务实体
            //Map<String, InvOrganization> invOrganizationMap=new HashMap<>();
            //业务实体
            Map<String, Organization> orgMap = new HashMap<>();
            //库存组织
            Map<String, Organization> organizationMap = new HashMap<>();
            //申请人信息
            Map<String, User> usersMap = new HashMap<>();
            //申请人信息
            List<String> users = new ArrayList<>();
            // 物料编码
            List<String> itemCodes = new ArrayList<>();
            // 采购申请头ID
            List<Long> requestHeaderIds = new ArrayList<>();
            // 采购申请
            List<Long> requisitionLineIds = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(requisitionsList)) {
                requisitionsList.forEach(requisitionDTO -> {
                    // 收集头id
                    Optional.ofNullable(requisitionDTO.getRequestHeaderId()).ifPresent(id -> requestHeaderIds.add(id));
                    List<RequisitionDetail> requisitionDetailList = requisitionDTO.getRequisitionDetailList();
                    if (CollectionUtils.isNotEmpty(requisitionDetailList)) {
                        // 收集申请行id
                        List<Long> lineIds = requisitionDetailList.stream().filter(requisitionDetail -> StringUtil.notEmpty(requisitionDetail.getRequisitionLineId())).
                                map(RequisitionDetail::getRequisitionLineId).collect(Collectors.toList());
                        Optional.ofNullable(lineIds).ifPresent(lines -> requisitionLineIds.addAll(lines));

                        // 收集物料编码
                        List<String> itemNumbers = requisitionDetailList.stream().filter(requisitionDetail -> StringUtil.notEmpty(requisitionDetail.getItemNumber())).map(
                                RequisitionDetail::getItemNumber).collect(Collectors.toList());
                        Optional.ofNullable(itemNumbers).ifPresent(coeds -> itemCodes.addAll(coeds));
                        //收集申请人信息
                        List<String> useridList = requisitionDetailList.stream().filter(requisitionDetail -> StringUtil.notEmpty(requisitionDetail.getRequestorNumber())).map(
                                RequisitionDetail::getRequestorNumber).collect(Collectors.toList());
                        Optional.ofNullable(useridList).ifPresent(userid -> users.addAll(userid));
                    }
                });
            }

            // 获取采购申请头
            Map<Long, Requisition> queryRequisitionMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(requestHeaderIds)) {
                List<Requisition> requisitions = iRequisitionService.list(Wrappers.lambdaQuery(Requisition.class)
                        .in(Requisition::getRequestHeaderId, requestHeaderIds));
                if (CollectionUtils.isNotEmpty(requisitions)) {
                    queryRequisitionMap = requisitions.stream().collect(Collectors.toMap(Requisition::getRequestHeaderId, Function.identity()));
                }
            }

            // 获取采购申请行
            Map<Long, RequisitionDetail> requisitionDetailMap = new HashMap<>();
            List<RequisitionDetail> queryRequisitionDetailList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(requisitionLineIds)) {
                queryRequisitionDetailList = iRequisitionDetailService.list(Wrappers.lambdaQuery(RequisitionDetail.class)
                        .in(RequisitionDetail::getRequisitionLineId, requisitionLineIds));
                if (CollectionUtils.isNotEmpty(queryRequisitionDetailList)) {
                    requisitionDetailMap = queryRequisitionDetailList.stream().collect(Collectors.toMap(RequisitionDetail::getRequisitionLineId, Function.identity()));
                }
            }


            /* 获取传入的采购申请报文的所有行物料编码 */
            Map<String, MaterialItem> materialItemMap=new HashMap<>();
            //  ------------------------------------------------------------------TODO 新的查询结束-------------------------------------------------------------------


            //erp源数据头信息
            //srm数据头信息 TODO 取消查询所有申请头方法
//            List<Requisition> queryRequisitionList = iRequisitionService.list();
//            Map<Long, Requisition> queryRequisitionMap = queryRequisitionList.stream().collect(Collectors.toMap(Requisition::getRequestHeaderId, Function.identity()));

//            // TODO
//            List<RequirementHead> srmQueryRequirementHeadList = iRequirementHeadService.list();
//            Map<Long, RequirementHead> srmQueryRequirementHeadMap = srmQueryRequirementHeadList.stream().collect(Collectors.toMap(RequirementHead::getRequirementHeadId, Function.identity()));

            //erp全量采购申请明细数据
            //srm全量采购需求行数据
//            // TODO 取消查询所有查询所有申请行方法
//            List<RequisitionDetail> queryRequisitionDetailList = iRequisitionDetailService.list();
//            Map<Long, RequisitionDetail> requisitionDetailMap = queryRequisitionDetailList.stream().collect(Collectors.toMap(RequisitionDetail::getRequisitionLineId, Function.identity()));

            // TODO
//            List<RequirementLine> srmQueryRequirementLineList = iRequirementLineService.list();
//            this.requirementLineMap = srmQueryRequirementLineList.stream().collect(Collectors.toMap(RequirementLine::getRequirementLineId, Function.identity()));

//            //查询物料信息 TODO 取消查询所有物料方法
//            List<MaterialItem> materialItemList = baseClient.MaterialItemListAlls();
//            //List<MaterialItem> materialItemList = new ArrayList<>();
//            this.materialItemMap = materialItemList.stream().collect(Collectors.toMap(MaterialItem::getMaterialCode, Function.identity()));


            // --------------------------------------------------------------------TODO 把查询全部的数据逻辑改为查询部分结束----------------------------------------------------------------
            try {
                /* 获取传入的采购申请报文的所有行物料编码 */
                materialItemMap = baseClient.listMaterialItemsByCodes(itemCodes);
                //物料小类和大类
                List<PurchaseCategory> purchaseCategorieList = baseClient.listParamCategoryMin();
                //大类
                purchaseCategorieMAXMap = purchaseCategorieList.stream().filter(purchaseCategory -> 1 == purchaseCategory.getLevel()).collect(Collectors.toMap(PurchaseCategory::getCategoryCode, Function.identity()));
                //小类
                purchaseCategorieMINMap = purchaseCategorieList.stream().filter(purchaseCategory -> 3 == purchaseCategory.getLevel()).collect(Collectors.toMap(PurchaseCategory::getCategoryId, Function.identity()));

                //币种
                List<PurchaseCurrency> purchaseCurrencieList = baseClient.PurchaseCurrencyListAll();
                this.purchaseCurrencieMap = purchaseCurrencieList.stream().collect(Collectors.toMap(PurchaseCurrency::getCurrencyCode, Function.identity()));
                //库存组织
                List<Organization> organizations = baseClient.iOrganizationServiceListAll();
                organizationMap = organizations.stream().filter(x -> StringUtils.isNotEmpty(x.getOrganizationCode()) && "INV".equals(x.getOrganizationTypeCode())).collect(Collectors.toMap(Organization::getOrganizationCode, Function.identity(), (O1, O2) -> O1));
                //业务实体
                orgMap = organizations.stream().filter(x -> StringUtils.isNotEmpty(x.getErpOrgId()) && "OU".equals(x.getOrganizationTypeCode())).collect(Collectors.toMap(Organization::getErpOrgId, Function.identity(), (O1, O2) -> O1));

                //申请人信息
                List<User> usersList = rbacClient.listUsersByUsersParamCode(users);
                usersMap = usersList.stream().collect(Collectors.toMap(User::getCeeaEmpNo, Function.identity(), (O1, O2) -> O1));
            } catch (Exception e) {
                Assert.isTrue(false, e.getMessage() + "获取校验数据失败，请重试。");
            }

            /** 复制RequisitionWsService接口传的requisitionList */
            //List<Requisition> copyRequisitionList = requisitionsList;

            //头表操作
            List<Requisition> saveRequisitionList = new ArrayList<>();
            List<Requisition> updateRequisitionList = new ArrayList<>();
            //行表操作
            List<RequisitionDetail> saveRequisitionDetailList = new ArrayList<>();
            List<RequisitionDetail> updateRequisitionDetailList = new ArrayList<>();

            //srm头操作
            List<RequirementHead> saveSrmRequirementHeadList = new ArrayList<>();
            List<RequirementHead> updateSrmRequirementHeadList = new ArrayList<>();
            //srm行操作
            List<RequirementLine> saveSrmRequirementLineList = new ArrayList<>();
            List<RequirementLine> updateSrmRequirementLineList = new ArrayList<>();


            //行表操作(错误的行信息)
            Map<Long, RequisitionDetail> EsaveRequisitionDetailMap = new HashMap<>();
            Map<Long, RequisitionDetail> EupdateRequisitionDetailMap = new HashMap<>();


            outter:
            for (RequisitionDTO requisitionDTO : requisitionsList) {
                Assert.notNull(requisitionDTO.getRequestHeaderId(), "数据存在采购订单头id为空，请检查后重新提交。");
                //设置头业务实体
                Organization organization = orgMap.get("" + requisitionDTO.getOperationUnitId());
                if (organization != null) {
                    requisitionDTO.setOrgId(organization.getOrganizationId());
                    requisitionDTO.setOrgCode(organization.getOrganizationCode());
                    requisitionDTO.setOrgName(organization.getOrganizationName());
                }
//情况一：传来的数据只有头信息，没有明细信息,则处理完头信息，跳过本次循环-------------------------------------------------------------------------------------------------------
                if (CollectionUtils.isEmpty(requisitionDTO.getRequisitionDetailList())) {
                    if (queryRequisitionMap.get(requisitionDTO.getRequestHeaderId()) != null) {
                        updateRequisitionList.add(requisitionDTO);
                        //获取srm表对应的数据，进行更新
                        List<RequirementHead> requirementHeads = saveAndUpdateRequisition(queryRequisitionDetailList, requisitionDTO, true, purchaseCategorieMAXMap, organizationMap);
                        updateSrmRequirementHeadList.addAll(requirementHeads);
                    } else {
                        //如果不存在则新增中间表及srm表的数据，然后跳过本次循环
                        requisitionDTO.setRequirementHeadId(IdGenrator.generate());
                        requisitionDTO.setRequirementHeadNum(baseClient.seqGen(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM));
                        saveRequisitionList.add(requisitionDTO);
                        //调用srm新增接口进行更新
                        List<RequirementHead> requirementHeads = saveAndUpdateRequisition(null, requisitionDTO, false, purchaseCategorieMAXMap, organizationMap);
                        saveSrmRequirementHeadList.addAll(requirementHeads);
                    }
                    continue outter;
                }
//情况二：传来的数据只有头信息和明细信息，则先处理明细信息，在根据明细的信息进行拆分头信息--------------------------------------------------------------------------------------
                //获取明细内容
                List<RequisitionDetail> requisitionDetailLists = requisitionDTO.getRequisitionDetailList();
                ArrayList<String> stringList = new ArrayList<>();

                List<RequisitionDetail> requisitionDetailList = new ArrayList<>();
                //品类分工
                Map<String, DivisionCategory> OrgByPurchaseCategoryMap = new HashMap<>();


                //==================================================================行明细填充物料小类及大类信息======================================================================
                //品类分工
                HashMap<String, Long> stringLongHashMap = new HashMap<>();
                //填充物料小类及物料大类信息
                for (RequisitionDetail requisitionDetail : requisitionDetailLists) {
                    try {
                        stringList.add(baseClient.seqGenForAnon(SequenceCodeConstant.SEQ_PMP_PR_APPLY_NUM));
                    } catch (Exception e) {
                        Assert.isTrue(false, "获取系统基础信息失败，请重试");
                    }
                    //================================报错的明细直接记录往记录表新增或更新，不走处理业务逻辑=====================================
                    MaterialItem materialItem = null;
                    PurchaseCategory purchaseCategory = null;
                    PurchaseCategory purchaseCategoryMix = null;
                    try {
                        Assert.isTrue(StringUtils.isNotEmpty(requisitionDetail.getNeedByDate()), "缺少行需求日期。");
                        materialItem = materialItemMap.get(requisitionDetail.getItemNumber());
                        Assert.notNull(materialItem, "找不到对应的物料.");
                        Assert.notNull(materialItem.getCategoryId(), "物料编码：\"+requisitionDetail.getItemNumber()+\"，找不到对应的物料小类。");
                        purchaseCategory = purchaseCategorieMINMap.get(materialItem.getCategoryId());
                        Assert.notNull(purchaseCategory, "找不到物料小类。");
                        String categoryMixCode = purchaseCategory.getCategoryCode().substring(0, 2);
                        purchaseCategoryMix = purchaseCategorieMAXMap.get(categoryMixCode);
                        Assert.notNull(purchaseCategoryMix, "找不到物料大类");
                        Assert.isTrue(StringUtils.isNotEmpty(requisitionDetail.getUnitOfMeasure()), "单位不能为空。");
                    } catch (Exception e) {
                        log.info(e.getMessage());
                        requisitionDetail.setRequestHeaderId(requisitionDTO.getRequestHeaderId());
                        requisitionDetail.setLineImportStatus(1);
                        RequisitionDetail byId = iRequisitionDetailService.getById(requisitionDetail.getRequisitionLineId());
                        if (byId == null) {
                            EsaveRequisitionDetailMap.put(requisitionDetail.getRequisitionLineId(), requisitionDetail);
                        } else {
                            EupdateRequisitionDetailMap.put(requisitionDetail.getRequisitionLineId(), requisitionDetail);
                        }
                        continue;
                    }
                    //=============================================物料信息正确的明细走下一个流程=================================
                    //添加物料小类信息
                    requisitionDetail.setCategoryId(purchaseCategory.getCategoryId())
                            .setCategoryCode(purchaseCategory.getCategoryCode())
                            .setCategoryName(purchaseCategory.getCategoryName());
                    //添加物料大类
                    requisitionDetail.setCategoryMixId(purchaseCategoryMix.getCategoryId())
                            .setCategoryMixCode(purchaseCategoryMix.getCategoryCode())
                            .setCategoryMixName(purchaseCategoryMix.getCategoryName());
                    if (StringUtils.isNotEmpty(requisitionDetail.getRequireOrgCode()) && materialItem.getCategoryId() != null) {
                        stringLongHashMap.put(requisitionDetail.getRequireOrgCode(), materialItem.getCategoryId());
                    }
                    requisitionDetailList.add(requisitionDetail);
                }
                if (!stringLongHashMap.isEmpty()) {
                    List<DivisionCategory> listOrgByPurchaseCategory = divisionCategoryMapper.getListOrgByPurchaseCategory(stringLongHashMap);
                    OrgByPurchaseCategoryMap = listOrgByPurchaseCategory.stream().collect(Collectors.toMap(x -> x.getOrganizationCode() + x.getCategoryId() + x.getIfMainPerson() + x.getDuty(), part -> part, (O1, O2) -> O1));

                }

                //============================================如果没有一条正确的数据则直接跳过此次循环==============================================================
                if (CollectionUtils.isEmpty(requisitionDetailList)) {
                    Requisition byId = iRequisitionService.getById(requisitionDTO.getRequestHeaderId());
                    requisitionDTO.setHeadImportStatus(1);
                    if (byId == null) {
                        saveRequisitionList.add(requisitionDTO);
                    } else {
                        updateRequisitionList.add(requisitionDTO);
                    }
                    continue;
                }
                //根据库存升序排序
                Collections.sort(requisitionDetailList, new Comparator<RequisitionDetail>() {
                    @Override
                    public int compare(RequisitionDetail o1, RequisitionDetail o2) {
                        Assert.isTrue(StringUtils.isNotEmpty(o1.getRequireOrgCode()), "库存组织不能为空");
                        if (o1.getRequireOrgCode().compareTo(o2.getRequireOrgCode()) == 0) {
                            return o1.getCategoryCode().compareTo(o2.getCategoryCode());
                        }
                        return o1.getRequireOrgCode().compareTo(o2.getRequireOrgCode());
                    }
                });
                //处理明细
                int requisitionDetailSize = requisitionDetailList.size();
                boolean falg = true;
                outter2:
                for (int i = 0; i < requisitionDetailSize; i++) {
                    RequisitionDetail requisitionDetail = requisitionDetailList.get(i);
                    //存在则更新
                    RequisitionDetail requisitionDetails = requisitionDetailMap.get(requisitionDetail.getRequisitionLineId());
                    if (null != requisitionDetails) {
                        //更新erp明细
                        updateRequisitionDetailList.add(requisitionDetail
                                .setRequirementHeadId(requisitionDetails.getRequirementHeadId())
                                .setRequirementLineId(requisitionDetails.getRequirementLineId())
                                .setRequirementHeadNum(requisitionDetails.getRequirementHeadNum()));
                        //更新srm明细
                        requisitionDetail
                                .setOperationUnitId(requisitionDTO.getOperationUnitId())
                                .setOperationName(requisitionDTO.getOperationName());
                        updateSrmRequirementLineList.add(saveAndUpdateRequisitionDetail(requisitionDetail, true, materialItemMap, organizationMap, usersMap, OrgByPurchaseCategoryMap));
                        continue outter2;
                    }
                    falg = false;
                    //不存在则新增
                    //判断库存组织是否不唯一，不唯一则新增srm头表
                    if (i > 0) {
                        //不等于库存组织的情况下：新增erp明细，erp头信息，srm明细，srm头信息
                        if (!requisitionDetail.getRequireOrgCode().equals(requisitionDetailList.get(i - 1).getRequireOrgCode()) ||
                                !requisitionDetail.getCategoryId().equals(requisitionDetailList.get(i - 1).getCategoryId())) {
                            //设置erp明细的头信息id
                            requisitionDetail.setRequestHeaderId(requisitionDTO.getRequestHeaderId());
                            //设置是srm表头大类
                            requisitionDTO.setCategoryMixCode(requisitionDetail.getCategoryMixCode());
                            //设置srm头表id
                            long headId = IdGenrator.generate();
                            requisitionDetail.setRequirementHeadId(headId);
                            requisitionDTO.setRequirementHeadId(headId);
                            //设置srm明细id
                            requisitionDetail.setRequirementLineId(IdGenrator.generate());
                            //设置srm明细业务实体
                            requisitionDetail.setOperationUnitId(requisitionDTO.getOrgId());
                            requisitionDetail.setOperationUnitCode(requisitionDTO.getOrgCode());
                            requisitionDetail.setOperationName(requisitionDTO.getOrgName());


                            //设置头申请人部门
                            User user = usersMap.get(requisitionDetail.getRequestorNumber());
                            if (user != null) {

                                if (StringUtils.isNotEmpty(user.getCeeaDeptId())){
                                    QueryWrapper<Dept> wrapper = new QueryWrapper<>();
                                    wrapper.select("ID,DEPTID,DESCR");
                                    wrapper.eq("DEPTID",user.getCeeaDeptId());
                                    List<Dept> deptList = baseClient.getDeptListbyWrapper(wrapper);
                                    if (CollectionUtils.isNotEmpty(deptList)){
                                        requisitionDTO.setCeeaDepartmentId(deptList.get(0).getDeptid());
                                    }
                                }
                                requisitionDTO.setCeeaDepartmentCode(user.getCeeaDeptId());
                                requisitionDTO.setCeeaDepartmentName(user.getDepartment());
                            }

                            // 设置srm采购单号
                            String seqPmpPrApplyNum = stringList.get(i);
                            requisitionDetail.setRequirementHeadNum(seqPmpPrApplyNum);
                            requisitionDTO.setRequirementHeadNum(seqPmpPrApplyNum);
                            //设置库存组织编号
                            requisitionDTO.setOrganizationCode(requisitionDetail.getRequireOrgCode());
                            //新增erp明细
                            saveRequisitionDetailList.add(requisitionDetail);
                            //新增srm明细
                            saveSrmRequirementLineList.add(saveAndUpdateRequisitionDetail(requisitionDetail, false, materialItemMap, organizationMap, usersMap, OrgByPurchaseCategoryMap));
                            //新增srm头表
                            List<RequirementHead> requirementHeads = saveAndUpdateRequisition(null, requisitionDTO, false, purchaseCategorieMAXMap, organizationMap);
                            saveSrmRequirementHeadList.addAll(requirementHeads);
                            //等于的情况下：新增erp明细，srm明细
                        } else {
                            RequisitionDetail requisitionDetailCopy = requisitionDetailList.get(i - 1);
                            //设置是srm表头大类
                            requisitionDTO.setCategoryMixCode(requisitionDetailCopy.getCategoryMixCode());
                            //设置erp明细的头信息id
                            requisitionDetail.setRequestHeaderId(requisitionDTO.getRequestHeaderId());
                            //设置库存组织编号
                            requisitionDTO.setOrganizationCode(requisitionDetail.getRequireOrgCode());
                            //设置srm头表id
                            long headId = requisitionDetailCopy.getRequirementHeadId();
                            requisitionDetail.setRequirementHeadId(headId);
                            //设置srm明细id
                            requisitionDetail.setRequirementLineId(IdGenrator.generate());
                            //设置头申请人部门
                            User user = usersMap.get(requisitionDetail.getRequestorNumber());
                            if (user != null) {
                                if (StringUtils.isNotEmpty(user.getCeeaDeptId())){
                                    QueryWrapper<Dept> wrapper = new QueryWrapper<>();
                                    wrapper.select("ID,DEPTID,DESCR");
                                    wrapper.eq("DEPTID",user.getCeeaDeptId());
                                    List<Dept> deptList = baseClient.getDeptListbyWrapper(wrapper);
                                    if (CollectionUtils.isNotEmpty(deptList)){
                                        requisitionDTO.setCeeaDepartmentId(deptList.get(0).getDeptid());
                                    }
                                }
                                requisitionDTO.setCeeaDepartmentCode(user.getCeeaDeptId());
                                requisitionDTO.setCeeaDepartmentName(user.getDepartment());
                            }
                            //设置srm明细业务实体
                            requisitionDetail.setOperationUnitId(requisitionDTO.getOrgId());
                            requisitionDetail.setOperationUnitCode(requisitionDTO.getOrgCode());
                            requisitionDetail.setOperationName(requisitionDTO.getOrgName());
                            //设置srm采购单号
                            String seqPmpPrApplyNum = requisitionDetailCopy.getRequirementHeadNum();
                            requisitionDetail.setRequirementHeadNum(seqPmpPrApplyNum);
                            //新增erp明细
                            saveRequisitionDetailList.add(requisitionDetail);
                            //新增srm明细
                            saveSrmRequirementLineList.add(saveAndUpdateRequisitionDetail(requisitionDetail, false, materialItemMap, organizationMap, usersMap, OrgByPurchaseCategoryMap));
                        }
                        continue outter2;
                    } else {
                        //设置erp明细的头信息id
                        requisitionDetail.setRequestHeaderId(requisitionDTO.getRequestHeaderId());
                        //设置是srm表头大类
                        requisitionDTO.setCategoryMixCode(requisitionDetail.getCategoryMixCode());
                        //设置srm头表id
                        long headId = IdGenrator.generate();
                        requisitionDetail.setRequirementHeadId(headId);
                        requisitionDTO.setRequirementHeadId(headId);
                        //设置srm明细id
                        requisitionDetail.setRequirementLineId(IdGenrator.generate());
                        //设置srm采购单号
                        String seqPmpPrApplyNum = stringList.get(i);
                        requisitionDetail.setRequirementHeadNum(seqPmpPrApplyNum);
                        requisitionDTO.setRequirementHeadNum(seqPmpPrApplyNum);
                        //设置头申请人部门
                        User user = usersMap.get(requisitionDetail.getRequestorNumber());
                        if (user != null) {
                            if (StringUtils.isNotEmpty(user.getCeeaDeptId())){
                                QueryWrapper<Dept> wrapper = new QueryWrapper<>();
                                wrapper.select("ID,DEPTID,DESCR");
                                wrapper.eq("DEPTID",user.getCeeaDeptId());
                                List<Dept> deptList = baseClient.getDeptListbyWrapper(wrapper);
                                if (CollectionUtils.isNotEmpty(deptList)){
                                    requisitionDTO.setCeeaDepartmentId(deptList.get(0).getDeptid());
                                }
                            }
                            requisitionDTO.setCeeaDepartmentCode(user.getCeeaDeptId());
                            requisitionDTO.setCeeaDepartmentName(user.getDepartment());
                        }
                        //设置srm明细业务实体
                        requisitionDetail.setOperationUnitId(requisitionDTO.getOrgId());
                        requisitionDetail.setOperationUnitCode(requisitionDTO.getOrgCode());
                        requisitionDetail.setOperationName(requisitionDTO.getOrgName());
                        //设置库存组织编号
                        requisitionDTO.setOrganizationCode(requisitionDetail.getRequireOrgCode());
                        //新增erp明细
                        saveRequisitionDetailList.add(requisitionDetail);
                        //新增srm明细
                        saveSrmRequirementLineList.add(saveAndUpdateRequisitionDetail(requisitionDetail, false, materialItemMap, organizationMap, usersMap, OrgByPurchaseCategoryMap));
                        //新增erp头信息
                        saveRequisitionList.add(requisitionDTO);
                        //新增srm头表
                        List<RequirementHead> requirementHeads = saveAndUpdateRequisition(null, requisitionDTO, false, purchaseCategorieMAXMap, organizationMap);
                        saveSrmRequirementHeadList.addAll(requirementHeads);
                        continue outter2;
                    }
                }
                //如果全部为更新的明细，则更新头信息
                if (falg) {
                    //更新rep头信息
                    //updateRequisitionList.add(requisitionDTO);
                    //获取srm表对应的数据，进行更新
                    List<RequirementHead> requirementHeads = saveAndUpdateRequisition(queryRequisitionDetailList, requisitionDTO, true, purchaseCategorieMAXMap, organizationMap);
                    updateSrmRequirementHeadList.addAll(requirementHeads);
                }
            }

            if (!EsaveRequisitionDetailMap.isEmpty()) {
                List<RequisitionDetail> collect = EsaveRequisitionDetailMap.entrySet().stream().map(et -> et.getValue()).collect(Collectors.toList());
                saveRequisitionDetailList.addAll(collect);
            }
            if (!EupdateRequisitionDetailMap.isEmpty()) {
                List<RequisitionDetail> collect = EsaveRequisitionDetailMap.entrySet().stream().map(et -> et.getValue()).collect(Collectors.toList());
                updateRequisitionDetailList.addAll(collect);
            }


            iRequisitionService.saveBatch(saveRequisitionList);
            iRequisitionService.updateBatchById(updateRequisitionList);
            iRequisitionDetailService.saveBatch(saveRequisitionDetailList);
            iRequisitionDetailService.updateBatchById(updateRequisitionDetailList);
            iRequirementHeadService.saveBatch(saveSrmRequirementHeadList);
            iRequirementHeadService.updateBatchById(updateSrmRequirementHeadList);
            //转换存储对象
            if (CollectionUtils.isNotEmpty(saveSrmRequirementLineList)) {
                ArrayList<RequirementLineNonAuto> RequirementLineNonAutoList = new ArrayList<>();
                for (RequirementLine requirementLine : saveSrmRequirementLineList) {
                    RequirementLineNonAuto requirementLineNonAuto = new RequirementLineNonAuto();
                    //取消强制覆盖
                    InjectDefMetaObjectHelper.setDefCreateAndUpdateFieldValue(requirementLineNonAuto);
                    if (requirementLine.getCreatedId() == null) {
                        requirementLine.setCreatedId(-1L);
                        requirementLine.setCreatedBy("系统管理员");
                        requirementLine.setCreatedFullName("系统管理员");
                    }
                    requirementLine.setCreatedByIp("10.0.3.217");
                    requirementLine.setLastUpdatedId(-1L);
                    requirementLine.setLastUpdatedBy("系统管理员");
                    requirementLine.setLastUpdatedByIp("10.0.3.217");

                    BeanCopyUtil.copyProperties(requirementLineNonAuto,requirementLine);
                    RequirementLineNonAutoList.add(requirementLineNonAuto);
                }
                iRequirementLineNonAutoService.saveBatch(RequirementLineNonAutoList);
            }
            //iRequirementLineService.saveBatch(saveSrmRequirementLineList);
            iRequirementLineService.updateBatchById(updateSrmRequirementLineList);

        } catch (Exception e) {
            log.error("操作失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            returnStatus = "E";//返回状态 :S成功 E 失败
            returnMsg = StringUtils.isEmpty(e.getMessage()) ? "" : e.getMessage();//返回信息  成功空，失败返回对应错误信息
            returnCode = "500";//相应代码
        }

        esbInfo.setReturnStatus(returnStatus);//返回状态
        esbInfo.setReturnCode(returnCode);//响应代码
        esbInfo.setReturnMsg(returnMsg);//返回信息
        esbInfo.setResponseTime(DateUtil.format(new Date()));//处理完时间

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);

        return soapResponse;
    }


    /**
     * 新增或修改srm采购头信息
     *
     * @param requisitionDetailList
     * @param requisitionDTO
     * @param falg
     * @return
     */
    public List<RequirementHead> saveAndUpdateRequisition(List<RequisitionDetail> requisitionDetailList, RequisitionDTO requisitionDTO, Boolean falg, Map<String, PurchaseCategory> purchaseCategorieMAXMap, Map<String, Organization> organizationMap) throws Exception {
        //srm头list
        List<RequirementHead> SrmRequirementHeadList = new ArrayList<>();
        //默认true为更新
        if (falg) {
            //如果erp明细表为空
            if (CollectionUtils.isEmpty(requisitionDetailList)) {
                return SrmRequirementHeadList;
            }
            //如果不为空
            for (RequisitionDetail requisitionDetail : requisitionDetailList) {
                //获取对应的明细的的srm明细id
                if (requisitionDTO.getRequestHeaderId().equals(requisitionDetail.getRequestHeaderId())) {
                    //查询表数据,如果存在则更新数据
                    RequirementHead byId = iRequirementHeadService.getById(requisitionDTO.getRequirementHeadId());
                    //获取对应的明细的srm的信息头
                    if (null != byId) {
                        //数据处理
                        SrmRequirementHeadList.add(RequisitionTurnRequirementHead(requisitionDTO, byId, purchaseCategorieMAXMap, organizationMap));
                    }
                }

            }
            return SrmRequirementHeadList;
        }
        //false直接新增
        SrmRequirementHeadList.add(RequisitionTurnRequirementHead(requisitionDTO, null, purchaseCategorieMAXMap, organizationMap));
        return SrmRequirementHeadList;
    }

    /**
     * 头数据处理
     *
     * @param requisitionDTO
     * @param requirementHead
     * @return
     */
    public RequirementHead RequisitionTurnRequirementHead(RequisitionDTO requisitionDTO, RequirementHead requirementHead, Map<String, PurchaseCategory> purchaseCategorieMAXMap, Map<String, Organization> organizationMap) throws Exception {
        if (requirementHead == null) {
            requirementHead = new RequirementHead();
            //申请时间
            requirementHead.setApplyDate(DateUtil.dateToLocalDate(new Date()));
            //采购申请类型
            requirementHead.setCeeaPurchaseType("PURCHASE".equals(requisitionDTO.getDocumentType()) ? PurchaseType.NORMAL.getValue() : requisitionDTO.getDocumentType());
            //erp申请编号
            requirementHead.setEsRequirementHeadNum(requisitionDTO.getRequestNumber());
            //srm采购申请表id
            requirementHead.setRequirementHeadId(requisitionDTO.getRequirementHeadId());
            //采购需求编号(申请编号)
            requirementHead.setRequirementHeadNum(requisitionDTO.getRequirementHeadNum());
            //项目名称
            requirementHead.setCeeaProjectName(requisitionDTO.getProjectName());
            //项目编号
            requirementHead.setCeeaProjectNum(requisitionDTO.getProjectNumber());
            //CANCELLED+APPROVED状态
            requirementHead.setAuditStatus(RequirementApproveStatus.APPROVED.getValue().equals(requisitionDTO.getAuthStatus()) ? requisitionDTO.getAuthStatus() : RequirementApproveStatus.ABANDONED.getValue());

            //设置业务实体编码、Id和名称 （非空)
            requirementHead.setOrgId(requisitionDTO.getOrgId())
                    .setOrgCode(requisitionDTO.getOrgCode())
                    .setOrgName(requisitionDTO.getOrgName());
            //库存组织
            Organization organization = organizationMap.get(requisitionDTO.getOrganizationCode());
            if (organization != null) {
                requirementHead.setOrganizationId(Long.valueOf(organization.getOrganizationId()))
                        .setOrganizationCode(organization.getOrganizationCode())
                        .setOrganizationName(organization.getOrganizationName());
            }
            PurchaseCategory purchaseCategory = purchaseCategorieMAXMap.get(requisitionDTO.getCategoryMixCode());
            //设置物料大类
            requirementHead.setCategoryId(purchaseCategory.getCategoryId())
                    .setCategoryCode(purchaseCategory.getCategoryCode())
                    .setCategoryName(purchaseCategory.getCategoryName());
        }
        //审核状态
        requirementHead.setAuditStatus(requisitionDTO.getAuthStatus());
        //申请类型
        requirementHead.setCeeaPrType(requisitionDTO.getDocumentType());
        //备注-->说明
        requirementHead.setComments(requisitionDTO.getDescription());
        //设置来源系统
        requirementHead.setSourceSystem(DataSourceEnum.ERP_SYS.getValue());

        return requirementHead;
    }

    /**
     * 新增或者修改srm采购明细
     *
     * @param
     * @return
     */
    public RequirementLine saveAndUpdateRequisitionDetail(RequisitionDetail requisitionDetail, Boolean falg, Map<String, MaterialItem> materialItemMap, Map<String, Organization> organizationMap, Map<String, User> usersMap, Map<String, DivisionCategory> OrgByPurchaseCategoryMap) throws Exception {
        //默认为true更新
        if (falg) {
            //如果找到srm对应的明细则更新，否则新增
            if (null == requisitionDetail.getRequisitionLineId()) {
                throw new Exception(LocaleHandler.getLocaleMsg("传入的采购申请行id存在为空的数据;"));
            }
            RequirementLine requirementLine = iRequirementLineService.getById(requisitionDetail.getRequisitionLineId());
            if (requirementLine != null) {
                //更新数据转换
                return this.requirementLineTurnRequirementLine(requisitionDetail, requirementLine, materialItemMap, organizationMap, usersMap, OrgByPurchaseCategoryMap);
            }
        }
        //新增数据转换
        return this.requirementLineTurnRequirementLine(requisitionDetail, null, materialItemMap, organizationMap, usersMap, OrgByPurchaseCategoryMap);
    }

    /**
     * srm行数据处理
     *
     * @param requisitionDetail
     * @param requirementLine
     * @return
     */
    public RequirementLine requirementLineTurnRequirementLine(RequisitionDetail requisitionDetail, RequirementLine requirementLine, Map<String, MaterialItem> materialItemMap, Map<String, Organization> organizationMap, Map<String, User> usersMap, Map<String, DivisionCategory> OrgByPurchaseCategoryMap) throws Exception {
        if (requirementLine == null) {
            requirementLine = new RequirementLine();

            //行号---行号不存在默认为零
            requirementLine.setRowNum(requisitionDetail.getLineNumber() != null ? requisitionDetail.getLineNumber().intValue() : 0);
            //申请人信息，部门
            User user = usersMap.get(requisitionDetail.getRequestorNumber());
            if (user != null) {
                requirementLine.setCreatedId(user.getUserId());
                requirementLine.setCreatedBy(user.getUsername());
                requirementLine.setCreatedFullName(user.getNickname());
                requirementLine.setRequirementDepartment(user.getDepartment());
            }
            //采购策略
            DivisionCategory purchaseStrategy = OrgByPurchaseCategoryMap.get(requisitionDetail.getRequireOrgCode() + requisitionDetail.getCategoryId() + "Y" + DUTY.Purchase_Strategy.toString());
            //采购履行
            DivisionCategory carryOut = OrgByPurchaseCategoryMap.get(requisitionDetail.getRequireOrgCode() + requisitionDetail.getCategoryId() + "Y" + DUTY.Carry_Out.toString());
            if (purchaseStrategy != null) {
                requirementLine.setCeeaStrategyUserId(purchaseStrategy.getPersonInChargeUserId());
                requirementLine.setCeeaStrategyUserNickname(purchaseStrategy.getPersonInChargeNickname());
                requirementLine.setCeeaStrategyUserName(purchaseStrategy.getPersonInChargeUsername());
            }
            if (carryOut != null) {
                requirementLine.setCeeaPerformUserId(carryOut.getPersonInChargeUserId());
                requirementLine.setCeeaPerformUserNickname(carryOut.getPersonInChargeNickname());
                requirementLine.setCeeaPerformUserName(carryOut.getPersonInChargeUsername());

            }
            //申请状态
            if (purchaseStrategy != null && carryOut != null) {
                requirementLine.setApplyStatus("ASSIGNED");
            } else {
                requirementLine.setApplyStatus("UNASSIGNED");
            }

            //srm采购申请表id
            requirementLine.setRequirementHeadId(requisitionDetail.getRequirementHeadId());
            //采购需求编号(申请编号)
            requirementLine.setRequirementHeadNum(requisitionDetail.getRequirementHeadNum());
            //设置srm明细id
            requirementLine.setRequirementLineId(requisitionDetail.getRequirementLineId());
            //已执行数量
            requirementLine.setCeeaExecutedQuantity(new BigDecimal(0));
            //需求数量
            requirementLine.setRequirementQuantity(new BigDecimal(0));
            //可下单数量
            requirementLine.setOrderQuantity(new BigDecimal(0));
            //业务实体
            requirementLine.setOrgId(requisitionDetail.getOperationUnitId())
                    .setOrgCode(requisitionDetail.getOperationUnitCode())
                    .setOrgName(requisitionDetail.getOperationName());
            //库存组织
            Organization organization = organizationMap.get(requisitionDetail.getRequireOrgCode());
            Assert.isTrue(organization != null, "采购需求的采购组织在srm上找不到对应的库存组织，请检查后重新提交。");
            requirementLine.setOrganizationId(organization.getOrganizationId())
                    .setOrganizationCode(organization.getOrganizationCode())
                    .setOrganizationName(organization.getOrganizationName());
            //物料
            MaterialItem materialItem = materialItemMap.get(requisitionDetail.getItemNumber());
            Assert.isTrue(!StringUtil.isEmpty(materialItem), "找不到对应的物料。");
            requirementLine.setMaterialId(materialItem.getMaterialId())
                    .setMaterialCode(materialItem.getMaterialCode())
                    .setMaterialName(materialItem.getMaterialName());
            //物料小类
            requirementLine.setCategoryId(requisitionDetail.getCategoryId())
                    .setCategoryCode(requisitionDetail.getCategoryCode())
                    .setCategoryName(requisitionDetail.getCategoryName());
            //币种
            PurchaseCurrency purchaseCurrency = this.purchaseCurrencieMap.get(requisitionDetail.getCurrencyCode());
            requirementLine.setCurrencyId(purchaseCurrency.getCurrencyId())
                    .setCurrency(purchaseCurrency.getCurrencyCode())
                    .setCurrencyName(purchaseCurrency.getCurrencyName());
            //总金额（预算单价）
            requirementLine.setNotaxPrice(requisitionDetail.getPrice().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : requisitionDetail.getPrice());
            //需求数量（申请数量）
            requirementLine.setRequirementQuantity(requisitionDetail.getQuantity().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : requisitionDetail.getQuantity());
            //剩余可下单数量
            requirementLine.setOrderQuantity(requisitionDetail.getQuantity().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : requisitionDetail.getQuantity());
            //单位
            requirementLine.setUnitCode(requisitionDetail.getUnitOfMeasure())
                    .setUnit(requisitionDetail.getUnitOfMeasure());
            //来源
            requirementLine.setRequirementSource(requisitionDetail.getSource());
            //部门
            requirementLine.setRequirementDepartment(requisitionDetail.getDepartName());
            //需求日期
            requirementLine.setRequirementDate(DateUtil.parseLocalDate(requisitionDetail.getNeedByDate()));
        }

        //驳回原因
        requirementLine.setRejectReason(requisitionDetail.getCancelReason());
        if (!StringUtil.isEmpty(requisitionDetail.getCancelFlag())) {
            requirementLine.setRejectReason(requisitionDetail.getCancelFlag());
        }
        //设置来源系统
        requirementLine.setSourceSystem(DataSourceEnum.ERP_SYS.getValue());

        return requirementLine;
    }

    public List<String> getMaterialItemCodesFromRequisition(List<RequisitionDTO> requisitionsList) {
        List<String> itemCodes = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(requisitionsList)) {
            requisitionsList.forEach(requisitionDTO -> {
                List<RequisitionDetail> requisitionDetailList = requisitionDTO.getRequisitionDetailList();
                if (CollectionUtils.isNotEmpty(requisitionDetailList)) {
                    List<String> itemNumbers = requisitionDetailList.stream().filter(requisitionDetail -> StringUtil.notEmpty(requisitionDetail.getItemNumber())).map(
                            RequisitionDetail::getItemNumber).collect(Collectors.toList());
                    Optional.ofNullable(itemNumbers).ifPresent(coeds -> itemCodes.addAll(coeds));
                }
            });
        }
        return itemCodes;
    }

}

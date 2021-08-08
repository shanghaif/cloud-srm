package com.midea.cloud.srm.cm.anon.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Function;
import com.midea.cloud.common.enums.contract.ContractStatus;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.srm.cm.accept.service.IAcceptOrderService;
import com.midea.cloud.srm.cm.contract.mapper.ContractHeadMapper;
import com.midea.cloud.srm.cm.contract.mapper.ContractMaterialMapper;
import com.midea.cloud.srm.cm.contract.mapper.ContractPartnerMapper;
import com.midea.cloud.srm.cm.contract.mapper.PayPlanMapper;
import com.midea.cloud.srm.cm.contract.service.IContractHeadService;
import com.midea.cloud.srm.cm.model.mapper.ModelHeadMapper;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractHeadDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractQueryDTO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.cm.model.entity.ModelHead;
import net.sf.cglib.core.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务内部调用接口(无需登录就可以对微服务模块间暴露)
 * </pre>
 *
 * @author ex_lizp6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-10 18:01
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/cm-anon")
public class ContractAnonController {
    @Autowired
    private IContractHeadService iContractHeadService;
    @Autowired
    private IAcceptOrderService iAcceptOrderService;
    @Autowired
    private ContractHeadMapper contractHeadMapper;
    @Autowired
    private ContractPartnerMapper contractPartnerMapper;
    @Autowired
    private PayPlanMapper payPlanMapper;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private ModelHeadMapper modelHeadMapper;
    @Autowired
    private ContractMaterialMapper contractMaterialMapper;

    /**
     * 采购商审批
     *
     * @param contractHeadId
     */
    @GetMapping("/contract/contractHead/buyerApprove")
    public void buyerApprove(@RequestParam Long contractHeadId) {
        iContractHeadService.buyerUpdateContractStatus(new ContractHeadDTO().setContractHeadId(contractHeadId), ContractStatus.ARCHIVED.name());
    }

    /**
     * 采购商审批拒绝
     *
     * @param contractHeadDTO
     */
    @PostMapping("/contract/contractHead/buyerRefused")
    public void buyerRefused(@RequestBody ContractHeadDTO contractHeadDTO) {
        iContractHeadService.buyerUpdateContractStatus(contractHeadDTO, ContractStatus.REFUSED.name());
    }

    /**
     * 采购商审批撤回
     *
     * @param contractHeadDTO
     */
    @PostMapping("/contract/contractHead/buyerWithdraw")
    public void buyerWithdraw(@RequestBody ContractHeadDTO contractHeadDTO) {
        iContractHeadService.buyerUpdateContractStatus(contractHeadDTO, ContractStatus.WITHDRAW.name());
    }

    /**
     * 获取ContractDTO2.0
     *
     * @param contractHeadId
     */
    @GetMapping("/contract/contractHead/getContractDTOSecond")
    public ContractDTO getContractDTOSecond(Long contractHeadId, String sourceId) {
        return iContractHeadService.getContractDTOSecond(contractHeadId, sourceId);
    }

    /**
     * 验收单通过
     *
     * @param acceptOrderDTO
     */
    @PostMapping("/accept/acceptOrder/vendorPass")
    public void vendorPass(@RequestBody AcceptOrderDTO acceptOrderDTO) {
        iAcceptOrderService.vendorPass(acceptOrderDTO);
    }

    /**
     * 验收单驳回
     *
     * @param acceptOrderDTO
     */
    @PostMapping("/accept/acceptOrder/buyerReject")
    public void buyerReject(@RequestBody AcceptOrderDTO acceptOrderDTO) {
        iAcceptOrderService.vendorReject(acceptOrderDTO);
    }

    /**
     * 验收单撤回
     *
     * @param acceptOrderId
     */
    @GetMapping("/accept/acceptOrder/acceptWithdraw")
    public void acceptWithdraw(@RequestParam Long acceptOrderId) {
        iAcceptOrderService.buyerWithdraw(acceptOrderId);
    }

    //取主协议框架合同用的
    @PostMapping("/listEffectiveContractByOrgCodesWithPartner")
    public List<ContractVo> listEffectiveContractByOrgIdsWithPartner(@RequestBody Collection<String> orgCodes) {
        if (CollectionUtils.isEmpty(orgCodes)) {
            return Collections.EMPTY_LIST;
        }
        List<DictItem> requireEntity = baseClient.listDictItemByDictCode("REQUIR_ENTITY");
        Map<String, Set<String>> buCodeMap = new HashMap<>();
        requireEntity.stream().map(DictItem::getDictItemCode).map(e -> e.split("-"))
                .forEach(l -> {
                    if (buCodeMap.containsKey(l[0])) {
                        buCodeMap.get(l[0]).add(l[1]);
                    } else {
                        HashSet<String> set = new HashSet<>();
                        set.add(l[1]);
                        buCodeMap.put(l[0], set);
                    }
                });
        Set<String> ouQuerySet = new HashSet<>();
        for (String code : orgCodes) {
            for (DictItem dictItem : requireEntity) {
                if (code.equals(dictItem.getDictItemName())) {
                    String[] split = dictItem.getDictItemCode().split("-");
                    Set<String> ouSet = buCodeMap.get(split[0]);
                    ouQuerySet.addAll(ouSet);
                    break;
                }
            }
        }
        //添加共享查询
        orgCodes.addAll(ouQuerySet);
        List<ContractVo> list = contractHeadMapper.listEffectiveContractByOrgId(orgCodes, LocalDate.now());
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        Set<Long> headerIds = list.stream().map(ContractVo::getContractHeadId).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(headerIds)) {
            Map<Long, List<ContractPartner>> contractMap = contractPartnerMapper.selectList(Wrappers.lambdaQuery(ContractPartner.class)
                    .in(ContractPartner::getContractHeadId, headerIds)
            ).stream().collect(Collectors.groupingBy(ContractPartner::getContractHeadId));
            Map<Long, List<PayPlan>> payPlanMap = payPlanMapper.selectList(Wrappers.lambdaQuery(PayPlan.class)
                    .in(PayPlan::getContractHeadId, headerIds)
            ).stream().collect(Collectors.groupingBy(PayPlan::getContractHeadId));
            //设置伙伴和付款计划
            for (ContractVo contractVo : list) {
                List<ContractPartner> contractPartners = contractMap.get(contractVo.getContractHeadId());
                contractVo.setContractPartnerList(contractPartners);
                List<PayPlan> payPlans = payPlanMap.get(contractVo.getContractHeadId());
                contractVo.setPayPlanList(payPlans);
            }
        }
        return list;
    }

    //取非协议框架-需求池用的
    @PostMapping("/listEffectiveContractByInvCodesAndMaterialCodes")
    public List<ContractVo> listEffectiveContractByInvIdAndMaterial(@RequestBody Map<String, Object> invAndMaterialParamMap) {
        Collection<ContractQueryDTO> queryList = JSON.parseArray(invAndMaterialParamMap.get("queryList").toString(), ContractQueryDTO.class);
        String frame = (String) invAndMaterialParamMap.get("frame");
        if (CollectionUtils.isEmpty(queryList)) {
            return Collections.emptyList();
        }
        Set<String> invCodes = new HashSet<>();
        Set<String> orgCodes = new HashSet<>();
        Set<String> materialCodes = new HashSet<>();

        getCodeSetFromParam(queryList, invCodes, orgCodes, materialCodes);

        List<ContractVo> list = contractHeadMapper.listEffectiveContractByInvCodeAndMaterialCode(invCodes, materialCodes, LocalDate.now(),LocalDate.now(), frame, orgCodes);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        } else {
            List<ContractVo> collect = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collect)) {
                return Collections.emptyList();
            }
        }
        return list;
    }

    //取非协议框架-创建订单用的
    @PostMapping("/listEffectiveContractByInvCodeAndMaterialCodeWithPartner")
    public List<ContractVo> listEffectiveContractByInvCodeAndMaterialCodeWithPartner(@RequestBody Map<String, Object> invAndMaterialParamMap) {
        Collection<ContractQueryDTO> queryList = JSON.parseArray(invAndMaterialParamMap.get("queryList").toString(), ContractQueryDTO.class);
        String frame = (String) invAndMaterialParamMap.get("frame");
        LocalDate from =LocalDate.parse( invAndMaterialParamMap.get("from").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate to =LocalDate.parse( invAndMaterialParamMap.get("to").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (CollectionUtils.isEmpty(queryList)) {
            return Collections.emptyList();
        }
        Set<String> invCodes = new HashSet<>();
        Set<String> orgCodes = new HashSet<>();
        Set<String> materialCodes = new HashSet<>();

        getCodeSetFromParam(queryList, invCodes, orgCodes, materialCodes);

        List<ContractVo> list = contractHeadMapper.listEffectiveContractByInvCodeAndMaterialCode(invCodes, materialCodes, from,to, frame, orgCodes);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        Set<Long> headerIds = list.stream().map(ContractVo::getContractHeadId).collect(Collectors.toSet());
        Map<Long, List<ContractPartner>> contractMap = contractPartnerMapper.selectList(Wrappers.lambdaQuery(ContractPartner.class)
                .in(ContractPartner::getContractHeadId, headerIds)
        ).stream().collect(Collectors.groupingBy(ContractPartner::getContractHeadId));
        Map<Long, List<PayPlan>> payPlanMap = payPlanMapper.selectList(Wrappers.lambdaQuery(PayPlan.class)
                .in(PayPlan::getContractHeadId, headerIds)
        ).stream().collect(Collectors.groupingBy(PayPlan::getContractHeadId));
        //设置伙伴和付款计划
        for (ContractVo contractVo : list) {
            contractVo.setContractCode(contractVo.getContractNo() + "|——|" + contractVo.getContractCode() + "|——|" + contractVo.getMaterialName());
            List<ContractPartner> contractPartners = contractMap.get(contractVo.getContractHeadId());
            if(Objects.nonNull(contractPartners)){
                contractVo.setContractPartnerList(contractPartners.stream().map(e-> BeanCopyUtil.copyProperties(e,ContractPartner::new)).collect(Collectors.toList()));
            }
            List<PayPlan> payPlans = payPlanMap.get(contractVo.getContractHeadId());
            if(Objects.nonNull(payPlans)){
                contractVo.setPayPlanList(payPlans.stream().map(e->BeanCopyUtil.copyProperties(e,PayPlan::new)).collect(Collectors.toList()));
            }
        }
        return list;
    }

    private void getCodeSetFromParam(Collection<ContractQueryDTO> queryList, Set<String> invCodes, Set<String> orgCodes, Set<String> materialCodes) {
        List<DictItem> requireEntity = baseClient.listDictItemByDictCode("REQUIR_ENTITY");
        Map<String, Set<String>> buCodeMap = new HashMap<>();
        requireEntity.stream().map(DictItem::getDictItemCode).map(e -> e.split("-"))
                .forEach(l -> {
                    if (buCodeMap.containsKey(l[0])) {
                        buCodeMap.get(l[0]).add(l[1]);
                    } else {
                        HashSet<String> set = new HashSet<>();
                        set.add(l[1]);
                        buCodeMap.put(l[0], set);
                    }
                });
        Set<String> ouQuerySet = new HashSet<>();
        for (ContractQueryDTO contractQueryDTO : queryList) {
            orgCodes.add(contractQueryDTO.getOrgCode());
            boolean shouldRemove = false;
            for (DictItem dictItem : requireEntity) {
                if (Objects.equals(contractQueryDTO.getOrgCode(),dictItem.getDictItemName())) {
                    String[] split = dictItem.getDictItemCode().split("-");
                    Set<String> ouSet = buCodeMap.get(split[0]);
                    ouQuerySet.addAll(ouSet);
                    shouldRemove = true;
                    break;
                }
            }
            if (!shouldRemove) {
                invCodes.add(contractQueryDTO.getInvCode());
            }
            materialCodes.add(contractQueryDTO.getMaterialCode());
        }
        orgCodes.addAll(ouQuerySet);
    }

    /**
     * 根据合同id获取合同模板的管控方式
     *
     * @param contractIds
     * @return
     */
    @PostMapping("/getContractMethodByContractId")
    public Map<Long, String> contractMethod(@RequestBody Collection<Long> contractIds) {
        if (CollectionUtils.isEmpty(contractIds)) {
            return Collections.emptyMap();
        }
        List<ContractHead> contractHeads = contractHeadMapper.selectList(Wrappers.lambdaQuery(ContractHead.class)
                .select(ContractHead::getContractHeadId, ContractHead::getModelHeadId)
                .in(ContractHead::getContractHeadId, contractIds)
        );
        Set<Long> modelHeadIds = contractHeads.stream().map(ContractHead::getModelHeadId).collect(Collectors.toSet());
        Map<Long, String> modelMap = modelHeadMapper.selectList(Wrappers.lambdaQuery(ModelHead.class)
                .select(ModelHead::getCeeaControlMethod, ModelHead::getModelHeadId)
                .in(ModelHead::getModelHeadId, modelHeadIds)
        ).stream().collect(Collectors.toMap(ModelHead::getModelHeadId, ModelHead::getCeeaControlMethod));
        Map<Long, String> result = contractHeads.stream().collect(Collectors.toMap(ContractHead::getContractHeadId, contractHead -> modelMap.get(contractHead.getModelHeadId())));
        return result;
    }

    /**
     * 批量获取合同物料信息
     * @param contractMaterials
     * @return
     */
    @PostMapping("/listContractMaterialMore")
    public List<ContractMaterial> listContractMaterialMore(@RequestBody List<ContractMaterial> contractMaterials){
        if(contractMaterials.isEmpty()){
            return new ArrayList<>();
        }

        Set<Long>  contractMaterialIds= contractMaterials.stream().map(c -> c.getContractMaterialId()).collect(Collectors.toSet());
        if(contractMaterialIds.isEmpty()){
            return new ArrayList<>();
        }

        List<ContractMaterial> contractMaterialList = contractMaterialMapper.selectList(Wrappers.lambdaQuery(ContractMaterial.class)
                .in(ContractMaterial::getContractMaterialId, contractMaterialIds));
        return contractMaterialList;
    }

    /**
     * 批量获取合同信息
     * @param contractHeads
     * @return
     */
    @PostMapping("/listContractHeadMore")
    public List<ContractHead> listContractHeadMore(@RequestBody List<ContractHead> contractHeads){
        if(contractHeads.isEmpty()){
            return new ArrayList<>();
        }

        Set<Long> contractHeadIds = contractHeads.stream().map(h -> h.getContractHeadId()).collect(Collectors.toSet());
        if(contractHeadIds.isEmpty()){
            return new ArrayList<>();
        }

        List<ContractHead> contractHeadList = contractHeadMapper.selectList(Wrappers.lambdaQuery(ContractHead.class)
            .in(ContractHead::getContractHeadId , contractHeadIds));
        return contractHeadList;
    }

    /**
     * 智汇签回调, 更新合同状态 ,保存盖章的文件ID
     */
    @PostMapping("/signingCallback")
    public void signingCallback(@RequestBody ContractHead contractHead){
        iContractHeadService.signingCallback(contractHead);
    }

}

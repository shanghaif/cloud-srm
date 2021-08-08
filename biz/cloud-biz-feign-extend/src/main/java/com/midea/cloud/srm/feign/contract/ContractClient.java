package com.midea.cloud.srm.feign.contract;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptDTO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.contract.dto.*;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalToContractDetail;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  合同管理模块 内部调用Feign接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-13 10:12
 *  修改内容:
 * </pre>
 */
@FeignClient("cloud-biz-contract")
public interface ContractClient {

    /**
     * 智汇签回调
     */
    @PostMapping("/cm-anon/signingCallback")
    void signingCallback(@RequestBody ContractHead contractHead);

    // 合同物料 [contractMaterial] - >>>>>

    @PostMapping("/contract/contractMaterial/listPageMaterialDTOByParm")
    PageInfo<ContractMaterialDTO> listPageMaterialDTOByParm(@RequestBody ContractMaterialDTO contractMaterialDTO);


    /**
     * 更新合同物料
     *
     * @param contractMaterialDTO
     */
    @PostMapping("/contract/contractMaterial/updateContractMaterial")
    void updateContractMaterial(@RequestBody ContractMaterialDTO contractMaterialDTO);


    /**
     * 批量更新合同物料
     *
     * @param contractMaterialDTOS
     */
    @PostMapping("/contract/contractMaterial/updateContractMaterials")
    void updateContractMaterials(@RequestBody List<ContractMaterialDTO> contractMaterialDTOS);


    // 合同物料 [contractMaterial] - <<<<<


    // 合同付款计划 [contractPayPlan] - >>>>>


    /**
     * 分页条件查询合同付款计划
     *
     * @param contractPayPlanDTO
     * @return
     */
    @PostMapping("/contract/payPlan/listPageContractPayPlanDTO")
    PageInfo<ContractPayPlanDTO> listPageContractPayPlanDTO(@RequestBody ContractPayPlanDTO contractPayPlanDTO);

    /**
     * 批量修改
     *
     * @param payPlanList
     */
    @PostMapping("/contract/payPlan/updateBatch")
    void ceeaUpdateBatch(@RequestBody List<PayPlan> payPlanList);

    /**
     * 根据id获取合同付款计划
     *
     * @param payPlanId
     * @return
     */
    @GetMapping("/getById")
    PayPlan ceeaGetById(@RequestParam Long payPlanId);

    // 合同付款计划 [contractPayPlan] - <<<<<

    /**
     * 根据合同id查询合同详细信息
     *
     * @param contractHeadId
     * @return
     */
    @GetMapping("/contract/contractHead/getContractDTO")
    ContractDTO getContractDetail(@RequestParam("contractHeadId") Long contractHeadId);

    /**
     * 保存合同(采购商)2.0
     *
     * @param contractDTO
     */
    @PostMapping("/contract/contractHead/buyerSaveOrUpdateContractDTOSecond")
    ContractHead buyerSaveOrUpdateContractDTOSecond(@RequestBody ContractDTO contractDTO) throws ParseException;

    /**
     * 价格审批回写合同价格
     *
     * @param approvalBiddingItemList
     */
    @PostMapping("/contract/contractHead/priceApprovalWriteBackContract")
    void priceApprovalWriteBackContract(@RequestBody List<ApprovalBiddingItem> approvalBiddingItemList);

    /**
     * 查询合同物料
     *
     * @param contractItemDto
     * @return
     */
    @PostMapping("/contract/contractMaterial/queryContractItem")
    List<ContractMaterialDto2> queryContractItem(@RequestBody ContractItemDto contractItemDto);

    /**
     * 根据条件获取合同头 add by chensl26
     *
     * @param contractHeadDTO
     * @return
     */
    @PostMapping("/contract/contractHead/getContractHeadByParam")
    ContractHead getContractHeadByParam(@RequestBody ContractHeadDTO contractHeadDTO);

    /**
     * 获取所有 有效的合同物料
     */
    @PostMapping("/contract/contractMaterial/listAllEffectiveCM")
    List<ContractVo> listAllEffectiveCM(@RequestBody ContractItemDto contractItemDto);

    @PostMapping("/contract/contractMaterial/listAllEffectiveCP")
    List<ContractPartner> listAllEffectiveCP(@RequestBody List<Long> contractHeadIds);

    /**
     * 根据id查询付款计划
     */
    @PostMapping("/contract/payPlan/list")
    List<PayPlan> list(@RequestBody PayPlan payPlan);

    /**
     * 根据条件查询有效合同
     */
    @PostMapping("/contract/contractMaterial/listEffectiveContractByParam")
    List<ContractVo> listEffectiveContractByParam(@RequestBody ContractMaterial contractMaterial);

    /**
     * 生成合同并返回合同id
     *
     * @param detail
     * @return
     */
    @PostMapping("/contract/contractHead/genContractFromApproval")
    Long genContractFromApproval(@RequestBody ApprovalToContractDetail detail);

    /**
     * 采购商审批
     *
     * @param contractHeadId
     */
    @GetMapping("/cm-anon/contract/contractHead/buyerApprove")
    void buyerApprove(@RequestParam("contractHeadId") Long contractHeadId);

    /**
     * 采购商审批拒绝
     *
     * @param contractHeadDTO
     */
    @PostMapping("/cm-anon/contract/contractHead/buyerRefused")
    void buyerRefused(@RequestBody ContractHeadDTO contractHeadDTO);

    /**
     * 采购商审批撤回
     *
     * @param contractHeadDTO
     */
    @PostMapping("/cm-anon/contract/contractHead/buyerWithdraw")
    void buyerWithdraw(@RequestBody ContractHeadDTO contractHeadDTO);

    /**
     * 获取ContractDTO2.0
     *
     * @param contractHeadId
     */
    @GetMapping("/cm-anon/contract/contractHead/getContractDTOSecond")
    ContractDTO getContractDTOSecond(@RequestParam("contractHeadId") Long contractHeadId, @RequestParam("sourceId") String sourceId);

    /**
     * 获取验收单详情（编辑时获取验收单和验收详情的接口）
     *
     * @param acceptOrderId
     */
    @GetMapping("/accept/acceptOrder/getAcceptDTO")
    AcceptDTO getAcceptDTO(@RequestParam("acceptOrderId") Long acceptOrderId);

    /**
     * 验收单通过
     *
     * @param acceptOrderDTO
     */
    @PostMapping("/cm-anon/accept/acceptOrder/vendorPass")
    void vendorPass(@RequestBody AcceptOrderDTO acceptOrderDTO);

    /**
     * 验收单驳回
     *
     * @param acceptOrderDTO
     */
    @PostMapping("/cm-anon/accept/acceptOrder/buyerReject")
    void buyerReject(@RequestBody AcceptOrderDTO acceptOrderDTO);

    /**
     * 验收单撤回
     *
     * @param acceptOrderId
     */
    @GetMapping("/cm-anon/accept/acceptOrder/acceptWithdraw")
    void acceptWithdraw(@RequestParam("acceptOrderId") Long acceptOrderId);

    /**
     * 根据协议框架、invIds、materialIds获取
     *
     * @param invAndMaterialParamMap
     * @return
     */
    @PostMapping("/cm-anon/listEffectiveContractByInvCodesAndMaterialCodes")
    List<ContractVo> listEffectiveContractByInvCodesAndMaterialCodes(@RequestBody Map<String, Object> invAndMaterialParamMap);

    @PostMapping("/cm-anon/listEffectiveContractByInvCodeAndMaterialCodeWithPartner")
    List<ContractVo> listEffectiveContractByInvCodeAndMaterialCodeWithPartner(@RequestBody Map<String, Object> invAndMaterialParamMap);

    @PostMapping("/cm-anon/listEffectiveContractByOrgCodesWithPartner")
    List<ContractVo> listEffectiveContractByOrgCodesWithPartner(@RequestBody Collection<String> orgCodes);

    @PostMapping("/cm-anon/getContractMethodByContractId")
    Map<Long, String> contractMethod(@RequestBody Collection<Long> contractIds);

    /**
     * 批量查询合同物料表
     */
    @PostMapping("/cm-anon/listContractMaterialMore")
    List<ContractMaterial> listContractMaterialMore(@RequestBody List<ContractMaterial> contractMaterials);

    /**
     * 批量获取合同头信息
     */
    @PostMapping("/cm-anon/listContractHeadMore")
    List<ContractHead> listContractHeadMore(@RequestBody List<ContractHead> contractHeads);

    @PostMapping("/cm-anon/contract/listContractForOrderCheck")
    Map<Long, ContractVo> listContractForOrderCheck(List<Long> contractMaterialIdsParam);

    /**
     * 根据合同id查询合同
     *
     * @param contractHeadIds
     * @return
     */
    @PostMapping("/contract/contractHead/listContractHeadByContractHeadIds")
    List<ContractHead> listContractHeadByContractHeadIds(@RequestBody List<Long> contractHeadIds);

    @PostMapping("/cm-anon/getContractMaterialBelongTimeByIds")
    List<ContractMaterial> getContractMaterialBelongTimeByIds(@RequestBody Collection<Long> sourcingIds);


    /**
     * 根据合同编号查询合同
     *
     * @param contractNo
     * @return
     */
    @PostMapping("/contract/contractHead/listContractHeadByContractNo")
    List<ContractHead> listContractHeadByContractNo(@RequestBody List<String> contractNos);
}


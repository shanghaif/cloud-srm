package com.midea.cloud.srm.cm.contract.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.contract.ContractSourceType;
import com.midea.cloud.common.enums.contract.ContractStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.contract.mapper.ContractHeadMapper;
import com.midea.cloud.srm.cm.contract.service.ICloseAnnexService;
import com.midea.cloud.srm.cm.contract.service.IContractHeadService;
import com.midea.cloud.srm.cm.contract.service.IContractLineService;
import com.midea.cloud.srm.cm.contract.service.IPayPlanService;
import com.midea.cloud.srm.cm.model.service.IModelHeadService;
import com.midea.cloud.srm.cm.template.service.ITemplHeadService;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.cm.contract.dto.BulkMaintenanceFrameworkParamDto;
import com.midea.cloud.srm.model.cm.contract.dto.ContractDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractHeadDTO;
import com.midea.cloud.srm.model.cm.contract.dto.PushContractParam;
import com.midea.cloud.srm.model.cm.contract.entity.CloseAnnex;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import com.midea.cloud.srm.model.cm.model.entity.ModelHead;
import com.midea.cloud.srm.model.cm.template.entity.TemplHead;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalToContractDetail;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryContractRequestDTO;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryContractResDTO;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.pm.ps.advance.vo.AdvanceApplyHeadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

/**
 * <pre>
 *  合同头表 前端控制器
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:10:46
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/contract/contractHead")
public class ContractHeadController extends BaseController {

    @Autowired
    private IContractHeadService iContractHeadService;

    @Autowired
    private ITemplHeadService iTemplHeadService;

    @Autowired
    private IModelHeadService iModelHeadService;

    @Autowired
    private IContractLineService iContractLineService;

    @Resource
    private ICloseAnnexService iCloseAnnexService;

    @Autowired
    private InqClient inqClient;

    @Resource
    private ContractHeadMapper contractHeadMapper;

    @Resource
    private SupcooperateClient supcooperateClient;
    @Resource
    private IPayPlanService iPayPlanService;

    /**
     * 获取ContractDTO
     *
     * @param contractHeadId
     */
    @GetMapping("/getContractDTO")
    public ContractDTO get(Long contractHeadId) {
        return iContractHeadService.getContractDTO(contractHeadId);
    }

    /**
     * 获取ContractDTO2.0
     *
     * @param contractHeadId
     */
    @GetMapping("/getContractDTOSecond")
    public ContractDTO getContractDTOSecond(Long contractHeadId, String sourceId) {
        return iContractHeadService.getContractDTOSecond(contractHeadId, sourceId);
    }

    /**
     * 采购商删除
     *
     * @param contractHeadId
     */
    @GetMapping("/buyerDelete")
    public void buyerDelete(Long contractHeadId) {
        checkAuth(contractHeadId);
        iContractHeadService.buyerDelete(contractHeadId);
    }

    /**
     * 废弃订单
     * @param contractHeadId
     */
    @GetMapping("/abandon")
    public void abandon(Long contractHeadId) {
        Assert.notNull(contractHeadId,"请选择需要废弃的单据。");
        iContractHeadService.abandon(contractHeadId);
    }

    /**
     * 采购商删除2.0
     *
     * @param contractHeadId
     */
    @GetMapping("/buyerDeleteSecond")
    public void buyerDeleteSecond(Long contractHeadId) {
        checkAuth(contractHeadId);
        iContractHeadService.buyerDeleteSecond(contractHeadId);
    }

    public void checkAuth(Long contractHeadId){
        if (StringUtil.notEmpty(contractHeadId)) {
            ContractHead contractHead = iContractHeadService.getById(contractHeadId);
            String createdBy = contractHead.getCreatedBy();
            String contractStatus = contractHead.getContractStatus();
            String userName = AppUserUtil.getUserName();
            if(ContractStatus.DRAFT.name().equals(contractStatus) && !userName.equals(createdBy)){
                throw new BaseException("你不是单据创建人, 无权操作");
            }
        }
    }

    /**
     * 分页查询
     *
     * @param contractHeadDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<ContractHead> listPageByParam(@RequestBody ContractHeadDTO contractHeadDTO) {
        return iContractHeadService.listPageByParam(contractHeadDTO);
    }

    /**
     * 查询导出的条数
     * @param contractHeadDTO
     * @return
     */
    @PostMapping("/queryExportCount")
    public Long queryExportCount(@RequestBody ContractHeadDTO contractHeadDTO){
        return iContractHeadService.queryCountByList(contractHeadDTO);
    }

    /**
     * 查询列表导出
     * @param contractHeadDTO
     * @param response
     * @return
     */
    @PostMapping("/export")
    public BaseResult export(@RequestBody ContractHeadDTO contractHeadDTO,HttpServletResponse response){
        try {
            iContractHeadService.export(contractHeadDTO, response);
            return BaseResult.buildSuccess();
        } catch (Exception e) {
            return BaseResult.build(ResultCode.UNKNOWN_ERROR,e.getMessage());
        }
    }

    /**
     * 导出合同详情
     * @param contractHeadDTO
     * @param response
     * @return
     */
    @PostMapping("/exportLine")
    public BaseResult exportLine(@RequestBody ContractHeadDTO contractHeadDTO,HttpServletResponse response){
        try {
            iContractHeadService.exportLine(contractHeadDTO, response);
            return BaseResult.buildSuccess();
        } catch (Exception e) {
            return BaseResult.build(ResultCode.UNKNOWN_ERROR,e.getMessage());
        }
    }

    /**
     * 保存合同(采购商)
     *
     * @param contractDTO
     */
    @PostMapping("/buyerSaveOrUpdateContractDTO")
    public void buyerSaveOrUpdateContractDTO(@RequestBody ContractDTO contractDTO) {
        checkAuth(contractDTO.getContractHead().getContractHeadId());
        iContractHeadService.buyerSaveOrUpdateContractDTO(contractDTO, ContractStatus.DRAFT.name());
    }

    /**
     * 保存合同(采购商)2.0
     *
     * @param contractDTO
     */
    @PostMapping("/buyerSaveOrUpdateContractDTOSecond")
    public ContractHead buyerSaveOrUpdateContractDTOSecond(@RequestBody ContractDTO contractDTO) throws ParseException {
        checkAuth(contractDTO.getContractHead().getContractHeadId());
        return iContractHeadService.buyerSaveOrUpdateContractDTOSecond(contractDTO, ContractStatus.DRAFT.name());
    }

    /**
     * 保存合同(供应商)
     *
     * @param contractHeadDTO
     */
    @PostMapping("/vendorUpdateContractHeadDTO")
    public void vendorUpdateContractHeadDTO(@RequestBody ContractHeadDTO contractHeadDTO) {
        checkAuth(contractHeadDTO.getContractHeadId());
        iContractHeadService.vendorUpdateContractHeadDTO(contractHeadDTO.setContractStatus(ContractStatus.SUPPLIER_CONFIRMING.name()));
    }

    /**
     * 查询全部生效的合同模板2.0
     *
     * @return
     */
    @PostMapping("/listEffectiveModelHead")
    public List<ModelHead> listEffectiveModelHead() {
        return iModelHeadService.modelList();
    }

    /**
     * 查询全部生效的合同模板
     *
     * @return
     */
    @PostMapping("/listEffectiveTempl")
    public List<TemplHead> listEffectiveTempl() {
        return iTemplHeadService.listEffectiveTempl();
    }

    /**
     * 采购商提交审批
     *
     * @param contractDTO
     */
    @PostMapping("/buyerSubmitApproval")
    public void buyerSubmitApproval(@RequestBody ContractDTO contractDTO) {
        checkAuth(contractDTO.getContractHead().getContractHeadId());
        iContractHeadService.buyerSaveOrUpdateContractDTO(contractDTO, ContractStatus.UNDER_REVIEW.name());
    }

    /**
     * 采购商提交审批2.0
     *
     * @param contractDTO
     */
    @PostMapping("/buyerSubmitApprovalSecond")
    public void buyerSubmitApprovalSecond(@RequestBody ContractDTO contractDTO) throws ParseException {
        checkAuth(contractDTO.getContractHead().getContractHeadId());
        iContractHeadService.buyerSubmitApprovalSecond(contractDTO);
    }

    /**
     * 采购商审批
     *
     * @param contractHeadId
     */
    @GetMapping("/buyerApprove")
    public void buyerApprove(@RequestParam Long contractHeadId) {
        iContractHeadService.buyerUpdateContractStatus(new ContractHeadDTO().setContractHeadId(contractHeadId), ContractStatus.UNPUBLISHED.name());
    }

    /**
     * 采购商发布
     *
     * @param contractHeadId
     */
    @GetMapping("/buyerPublish")
    public void buyerPublish(@RequestParam Long contractHeadId) {
        iContractHeadService.buyerUpdateContractStatus(new ContractHeadDTO().setContractHeadId(contractHeadId), ContractStatus.SUPPLIER_CONFIRMING.name());
    }

    /**
     * 采购商审批拒绝
     *
     * @param contractHeadDTO
     */
    @PostMapping("/buyerRefused")
    public void buyerRefused(@RequestBody ContractHeadDTO contractHeadDTO) {
        iContractHeadService.buyerUpdateContractStatus(contractHeadDTO, ContractStatus.REFUSED.name());
    }

    /**
     * 采购商归档
     *
     * @param contractHeadId
     */
    @GetMapping("/buyerArchive")
    public void buyerArchive(@RequestParam Long contractHeadId) {
        iContractHeadService.buyerUpdateContractStatus(new ContractHeadDTO().setContractHeadId(contractHeadId), ContractStatus.ARCHIVED.name());
    }

    /**
     * 供应商驳回
     *
     * @param contractHeadDTO
     */
    @PostMapping("/vendorReject")
    public void vendorReject(@RequestBody ContractHeadDTO contractHeadDTO) {
        iContractHeadService.vendorUpdateContractHeadDTO(contractHeadDTO.setContractStatus(ContractStatus.REJECTED.name()));
    }

    /**
     * 供应商确认
     *
     * @param contractHeadDTO
     */
    @PostMapping("/vendorConfirm")
    public void vendorConfirm(@RequestBody ContractHeadDTO contractHeadDTO) {
        iContractHeadService.vendorUpdateContractHeadDTO(contractHeadDTO.setContractStatus(ContractStatus.SUPPLIER_CONFIRMED.name()));
    }

    /**
     * 根据来源单号查找合同物料
     *
     * @param sourceNumber
     * @return
     */
    @GetMapping("/getMaterialsBySourceNumber")
    public List<ContractMaterial> getMaterialsBySourceNumber(String sourceNumber, Long orgId, Long vendorId) {
        return iContractHeadService.getMaterialsBySourceNumber(sourceNumber, orgId, vendorId);
    }

    /**
     * 根据来源单号查找合同物料
     *
     * @param sourceNumber
     * @return
     */
    @GetMapping("/getMaterialsBySourceNumberAndorgIdAndvendorId")
    public List<ContractMaterial> getMaterialsBySourceNumberAndorgIdAndvendorId(String sourceNumber, Long orgId, Long vendorId) {
        return iContractHeadService.getMaterialsBySourceNumberAndorgIdAndvendorId(sourceNumber, orgId, vendorId);
    }

    /**
     * 获取寻缘单合同物料
     *
     * @param contractMaterial
     * @return
     */
    @PostMapping("/getMaterialsBySource")
    public List<ContractMaterial> getMaterialsBySource(@RequestBody ContractMaterial contractMaterial) {
        return iContractLineService.getMaterialsByContractMaterial(contractMaterial);
    }

    /**
     * 合同关闭提示 信息系
     * @param contractHeadId
     * @return
     */
    @GetMapping("/closePrompt")
    public Map<String,Object> closePrompt(@RequestParam("contractHeadId") Long contractHeadId){
        /**
         * 合同关闭：点击关闭时，校验数量不为0的采购订单行上，有没有该合同的合同编号、合同序号：
         * 1、如果没有，关闭时；
         * ①关闭时，如果合同是关联价格审批单创建的
         * 【点关闭时，需弹框提示“该合同为价格审批单转成的合同，关闭后将释放价格审批单，并更新合同状态为已废弃"】
         * ②关闭时，如果合同是手动新增的，状态更新为“已关闭”；
         * 【点关闭时，需弹框提示“请确认是否关闭”】
         * 2、如果有，关闭时，提示“已创建订单，且订单数量未取消为0，不可关闭合同”
         */
        Map<String, Object> result = new HashMap<>();
        ContractHead contractHead = iContractHeadService.getById(contractHeadId);
        Assert.notNull(contractHead,"找不到该合同单据");
        // 校验数量不为0的采购订单行上，有没有该合同的合同编号、合同序号：
        boolean flag = supcooperateClient.checkOrderDetailIfQuoteContract(contractHead);
        if(flag){
            result.put("status", YesOrNo.NO.getValue());
            result.put("message","已创建订单，且订单数量未取消为0，不可关闭合同");
        }else {
            result.put("status", YesOrNo.YES.getValue());
            if(ContractSourceType.PRICE_APPROVAL.name().equals(contractHead.getSourceType())){
                result.put("message","该合同为价格审批单转成的合同，关闭后将释放价格审批单，并更新合同状态为已废弃");
            }else {
                result.put("message","请确认是否关闭");
            }
        }
        return result;
    }

    /**
     * 合同关闭
     *
     * @param contractHeadId
     * @return
     */
//    @GetMapping("/close")
    public Long close(@RequestParam("contractHeadId") Long contractHeadId) {
        /**
         * 合同关闭：点击关闭时，校验数量不为0的采购订单行上，有没有该合同的合同编号、合同序号：
         * 1、如果没有，关闭时；
         * ①关闭时，如果合同是关联价格审批单创建的，需释放价格审批单，并更新状态为“已废弃”；
         * 【点关闭时，需弹框提示“该合同为价格审批单转成的合同，关闭后将释放价格审批单，并更新合同状态为已废弃”；点击弹框中确定后，释放价格审批单，更新合同状态；点击取消，视为放弃本次操作】
         * ②关闭时，如果合同是手动新增的，状态更新为“已关闭”；
         * 【点关闭时，需弹框提示“请确认是否关闭”，点击弹框中确定后，更新合同状态；点击取消，视为放弃本次操作】
         *
         * 2、如果有，关闭时，提示“已创建订单，且订单数量未取消为0，不可关闭合同”
         */
        ContractHead contractHead = iContractHeadService.getById(contractHeadId);
        Assert.notNull(contractHead,"找不到该合同单据");
        // 校验数量不为0的采购订单行上，有没有该合同的合同编号、合同序号：
        boolean flag = supcooperateClient.checkOrderDetailIfQuoteContract(contractHead);
        contractHead.setEndDate(LocalDate.now());
        if(!flag){
            if(ContractSourceType.PRICE_APPROVAL.name().equals(contractHead.getSourceType())){
                /**
                 * 合同是关联价格审批单创建的，需释放价格审批单，并更新状态为“已废弃”；
                 */
                contractHead.setContractStatus(ContractStatus.ABANDONED.name());
                // 释放价格审批单
                inqClient.resetItemsBelongNumber(Collections.singleton(contractHeadId));
            }else {
                /**
                 * 状态更新为“已关闭”
                 */
                contractHead.setContractStatus(ContractStatus.CLOSE.name());
            }
            iContractHeadService.updateById(contractHead);
        }else {
            throw new BaseException("已创建订单，且订单数量未取消为0，不可关闭合同");
        }
        return contractHeadId;
    }

    /**
     * 合同关闭上传合同附件
     *
     * @param closeAnnex
     * @return
     */
    @PostMapping("/uploadCloseAnnex")
    public Long uploadCloseAnnex(@RequestBody CloseAnnex closeAnnex) {
        Assert.notNull(closeAnnex.getContractHeadId(), "缺少必传参数:contractHeadId");
        Assert.notNull(closeAnnex.getFileuploadId(), "缺少必传参数:fileuploadId");
        // 关闭合同
        close(closeAnnex.getContractHeadId());
        closeAnnex.setCloseAnnexId(IdGenrator.generate());
        iCloseAnnexService.save(closeAnnex);
        return closeAnnex.getContractHeadId();
    }

    /**
     * 预付款根据合同校验代付逻辑
     *
     * @param contractHeadDTO add by chensl26
     * @return
     */
    @PostMapping("/advanceCheckContract")
    public AdvanceApplyHeadVo advanceCheckContract(@RequestBody ContractHeadDTO contractHeadDTO) {
        return iContractHeadService.advanceCheckContract(contractHeadDTO);
    }

    /**
     * 合同物料价格变更发起寻源
     *
     * @param contractMaterials
     */
    @PostMapping("/cratePriceChangeSource")
    public void cratePriceChangeSource(@RequestBody List<ContractMaterial> contractMaterials) {
        iContractHeadService.cratePriceChangeSource(contractMaterials);
    }

    /**
     * 价格审批回写合同价格
     *
     * @param approvalBiddingItemList
     */
    @PostMapping("/priceApprovalWriteBackContract")
    public void priceApprovalWriteBackContract(@RequestBody List<ApprovalBiddingItem> approvalBiddingItemList) {
        iContractHeadService.priceApprovalWriteBackContract(approvalBiddingItemList);
    }

    /**
     * 根据条件获取合同头 add by chensl26
     *
     * @param contractHeadDTO
     * @return
     */
    @PostMapping("/getContractHeadByParam")
    public ContractHead getContractHeadByParam(@RequestBody ContractHeadDTO contractHeadDTO) {
        return iContractHeadService.getContractHeadByParam(contractHeadDTO);
    }

    /**
     * @Description 获取上架关联合同列表
     * @Param: []
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/10/4 11:09
     */
    @PostMapping("/getOnShelvesContractList")
    public List<PriceLibraryContractResDTO> getOnShelvesContractList(@RequestBody PriceLibraryContractRequestDTO priceLibraryContractRequestDTO) {
        return iContractHeadService.getOnShelvesContractList(priceLibraryContractRequestDTO);
    }

    /**
     * 新的转合同接口
     *
     * @param detail
     * @return
     */
    @PostMapping("/genContractFromApproval")
    public Long genContractFromApproval(@RequestBody ApprovalToContractDetail detail) {
        return iContractHeadService.genContractFromApproval(detail);
    }

    /**
     * 分页查询有效合同(结算用)
     *
     * @param contractHeadDTO add by chensl26
     * @return
     */
    @PostMapping("/listPageEffectiveByParam")
    public PageInfo<ContractHead> listPageEffectiveByParam(@RequestBody ContractHeadDTO contractHeadDTO) {
        return iContractHeadService.listPageEffectiveByParam(contractHeadDTO);
    }

    /**
     * Description 下载导入模板
     *
     * @Param [response]
     * @Author fansb3@meicloud.com
     * @Date 2020/10/13
     **/
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws Exception {
        iContractHeadService.importModelDownload(response);
    }

    /**
     * Description 导入
     *
     * @Param [file]
     * @Author fansb3@meicloud.com
     * @Date 2020/10/13
     **/
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iContractHeadService.importExcel(file, fileupload);
    }

    /**
     * Description 推送合同信息
     *
     * @Param []
     * @Author fansb3@meicloud.com
     * @Date 2020/10/19
     **/
    @GetMapping("/pushContractInfo")
    public void pushContractInfo(Long contractHeadId) {
        iContractHeadService.pushContractInfo(contractHeadId);
    }


    @PostMapping("/listContractHeadByIsMainAndVendorId")
    public PageInfo<ContractHead> listContractHeadByIsMainAndVendorId(@RequestBody Map<String, Object> param) {
        String isFrameworkAgreement = param.get("isFrameworkAgreement").toString();
        Object str = param.get("vendorId");
        Long vendorId = Objects.isNull(str)?null:Long.valueOf(str.toString());
        PageUtil.startPage(Integer.valueOf(param.get("pageNum").toString()), Integer.valueOf(param.get("pageSize").toString()));
        List<ContractHead> result = contractHeadMapper.listContractHeadByVendorIdAndIsFrameworkAgreement(vendorId, isFrameworkAgreement);
        return new PageInfo<>(result);
    }

    /**
     * 批量维护框架协议
     */
    @PostMapping("/bulkMaintenanceFramework")
    public void bulkMaintenanceFramework(@RequestBody BulkMaintenanceFrameworkParamDto bulkMaintenanceFrameworkParamDto){
        iContractHeadService.bulkMaintenanceFramework(bulkMaintenanceFrameworkParamDto);
    }

    /**
     * 导出合同明细
     **/
    @RequestMapping("/importContractMaterialDownload")
    public void importContractMaterialDownload(@RequestBody List<Long>  contractHeadIds, HttpServletResponse response) throws Exception {
        iContractHeadService.importContractMaterialDownload(contractHeadIds,response);
    }

    /**
     * 根据合同id查询合同
     * @param contractHeadIds
     * @return
     */
    @PostMapping("/listContractHeadByContractHeadIds")
    public List<ContractHead> listContractHeadByContractHeadIds(@RequestBody List<Long> contractHeadIds){
        if(CollectionUtils.isEmpty(contractHeadIds)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<ContractHead> wrapper = new QueryWrapper<>();
        wrapper.in("CONTRACT_HEAD_ID",contractHeadIds);
        return iContractHeadService.list(wrapper);
    }

    /**
     * 根据供应商ID查找合同
     * @param contractHead
     * @return
     */
    @PostMapping("/queryContractByVendorId")
    public PageInfo<ContractHead> queryContractByVendorId(@RequestBody ContractHead contractHead){
        return iContractHeadService.queryContractByVendorId(contractHead);
    }

    /**
     * 根据合同ID查找付款计划
     * @param contractHeadId
     * @return
     */
    @GetMapping("/queryPayPlanByContractHeadId")
    public List<PayPlan> queryPayPlanByContractHeadId(Long contractHeadId){
        Assert.notNull(contractHeadId,"缺少参数: [contractHeadId]");
        return iPayPlanService.list(new QueryWrapper<>(new PayPlan().setContractHeadId(contractHeadId)));
    }

    /**
     * 点击发布
     * @param pushContractParam
     */
    @PostMapping ("/release")
    public Map<String, Object> release(@RequestBody PushContractParam pushContractParam) throws Exception {
        return iContractHeadService.release(pushContractParam);
    }
}

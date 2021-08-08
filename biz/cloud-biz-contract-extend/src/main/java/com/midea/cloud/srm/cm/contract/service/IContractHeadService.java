package com.midea.cloud.srm.cm.contract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.contract.dto.BulkMaintenanceFrameworkParamDto;
import com.midea.cloud.srm.model.cm.contract.dto.ContractDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractHeadDTO;
import com.midea.cloud.srm.model.cm.contract.dto.PushContractParam;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.soap.Response;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalToContractDetail;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryContractRequestDTO;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryContractResDTO;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.pm.ps.advance.vo.AdvanceApplyHeadVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  合同头表 服务类
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
public interface IContractHeadService extends IService<ContractHead> {

    /**
     * 根据供应商ID查找合同
     * @param contractHead
     * @return
     */
    PageInfo<ContractHead> queryContractByVendorId(ContractHead contractHead);

    /**
     * 导出合同详情
     * @param contractHeadDTO
     * @param response
     */
    void exportLine(ContractHeadDTO contractHeadDTO,HttpServletResponse response) throws IOException;

    /**
     * 导出合同列表
     * @param contractHeadDTO
     * @param response
     * @return
     */
    void export(@RequestBody ContractHeadDTO contractHeadDTO, HttpServletResponse response) throws IOException;

    /**
     * 查询导出的条数
     * @param contractHeadDTO
     * @return
     */
    Long queryCountByList(ContractHeadDTO contractHeadDTO);

    /**
     * 新增或编辑合同(采购商)
     *
     * @param contractDTO
     */
    void buyerSaveOrUpdateContractDTO(ContractDTO contractDTO, String contractStatus);

    /**
     * 分页查询
     *
     * @param contractHeadDTO
     * @return
     */
    PageInfo<ContractHead> listPageByParam(ContractHeadDTO contractHeadDTO);

    /**
     * 根据条件查询所有合同头信息
     *
     * @param contractHeadDTO
     * @return
     */
    List<ContractHead> listContractHead(ContractHeadDTO contractHeadDTO);

    /**
     * 保存合同(供应商)
     *
     * @param contractHeadDTO
     */
    void vendorUpdateContractHeadDTO(ContractHeadDTO contractHeadDTO);

    /**
     * 获取ContractDTO
     *
     * @param contractHeadId
     * @return
     */
    ContractDTO getContractDTO(Long contractHeadId);

    /**
     * 采购商更新合同状态
     *
     * @param contractHeadDTO
     * @param contractStatus
     */
    void buyerUpdateContractStatus(ContractHeadDTO contractHeadDTO, String contractStatus);

    /**
     * 供应商更新合同状态
     *
     * @param contractHeadId
     * @param contractStatus
     */
    void vendorUpdateContractStatus(Long contractHeadId, String contractStatus);

    /**
     * 采购商删除
     *
     * @param contractHeadId
     */
    void buyerDelete(Long contractHeadId);

    /**
     * 废弃订单
     * @param contractHeadId
     */
    public void abandon(Long contractHeadId);

    /**
     * 根据来源单号查找合同物料
     *
     * @param sourceNumber
     * @return
     */
    List<ContractMaterial> getMaterialsBySourceNumber(String sourceNumber, Long orgId, Long vendorId);

    /**
     * 根据来源单号,组织,供应商 查找合同物料
     *
     * @param sourceNumber
     * @param orgId
     * @param vendorId
     * @return
     */
    List<ContractMaterial> getMaterialsBySourceNumberAndorgIdAndvendorId(String sourceNumber, Long orgId, Long vendorId);

    /**
     * 新增或编辑合同(采购商)
     *
     * @param contractDTO
     * @param contractStatus
     */
    ContractHead buyerSaveOrUpdateContractDTOSecond(ContractDTO contractDTO, String contractStatus) throws ParseException;

    /**
     * 获取ContractDTO
     *
     * @param contractHeadId
     * @return
     */
    ContractDTO getContractDTOSecond(Long contractHeadId, String sourceId);

    /**
     * 采购商删除 2.0
     *
     * @param contractHeadId
     */
    void buyerDeleteSecond(Long contractHeadId);

    /**
     * 提交审批2.0
     *
     * @param contractDTO
     */
    void buyerSubmitApprovalSecond(ContractDTO contractDTO) throws ParseException;

    /**
     * 寻源物料价格变更发起寻源
     *
     * @param contractMaterials
     */
    void cratePriceChangeSource(List<ContractMaterial> contractMaterials);

    /**
     * 价格审批回写合同
     *
     * @param approvalBiddingItemList
     */
    void priceApprovalWriteBackContract(@RequestBody List<ApprovalBiddingItem> approvalBiddingItemList);

    ContractHead getContractHeadByParam(ContractHeadDTO contractHeadDTO);

    /**
     * @Description 获取上架关联合同列表
     * @Param: [contractHeadDTO]
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/10/4 11:31
     */
    List<PriceLibraryContractResDTO> getOnShelvesContractList(@RequestBody PriceLibraryContractRequestDTO priceLibraryContractRequestDTO);

    /**
     * 预付款根据合同校验代付逻辑
     *
     * @param contractHeadDTO add by chensl26
     * @return
     */
    AdvanceApplyHeadVo advanceCheckContract(ContractHeadDTO contractHeadDTO);


    /**
     * 价格审批转合同,返回合同id
     * @param detail
     * @return
     */
    Long genContractFromApproval(ApprovalToContractDetail detail);

    /**
     *
     * @param contractHeadDTO
     * @return
     */
    PageInfo<ContractHead> listPageEffectiveByParam(ContractHeadDTO contractHeadDTO);

    /**
     * 下载导入模板
     * @param response
     * @throws Exception
     */
    void importModelDownload(HttpServletResponse response) throws Exception;
    void importContractMaterialDownload(List<Long>  contractHeadIds,HttpServletResponse response) throws Exception;

    /**
     * 导入
     * @param file
     */
   Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 推送合同信息
     */
    Response pushContractInfo(Long contractHeadId);

    /**
     * 批量维护框架协议
     * @param bulkMaintenanceFrameworkParamDto
     */
    void bulkMaintenanceFramework(BulkMaintenanceFrameworkParamDto bulkMaintenanceFrameworkParamDto);

    /**
     * 智汇签回调
     */
    void signingCallback(ContractHead contractHead);

    /**
     * 点击发布推送供应商
     * @param pushContractParam
     * @throws Exception
     */
    Map<String, Object> release(PushContractParam pushContractParam) throws Exception;
}

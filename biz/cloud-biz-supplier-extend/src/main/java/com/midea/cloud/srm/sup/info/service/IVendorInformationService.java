package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.info.dto.*;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;

import java.util.List;

/**
 * <pre>
 *  供应商档案Service
 * </pre>
 *
 * @update xiexh12@meicloud.com
 * <p>
 * 修改日期: 2020/9/9 20:23
 * </pre>
 */
public interface IVendorInformationService extends IService<VendorInformation> {

    /**
     * 分页查询供应商档案
     * @modified xiexh12@meicloud.com 2020/09/09
     * @param companyRequestDTO
     * @return
     */
    PageInfo<CompanyInfo> listByDTO(CompanyRequestDTO companyRequestDTO);

    List<CategoryDTO> getCategoryListByCompanyId(Long companyId, Long categoryId);

    /**
     * 保存或更新供应商信息
     * @param infoDTO
     * @modified xiexh12@meicloud.com 2020/09/11
     */
    void saveOrUpdateInformation(InfoDTO infoDTO);

    /**
     * 删除供应商信息
     * @param companyId
     * @modified xiexh12@meicloud.com 2020/09/11
     */
    void deleteVendorInformation(Long companyId);

    /**
     * 供应商审批
     * @param companyId
     * @modified xiexh12@meicloud.com
     */
    void vendorInformationApprove(Long companyId);

    /**
     * 分页查询供应商证件信息（证件到期提醒分页查询）
     */
    PageInfo<ManagementAttach> listManagementAttachPageByDTO(ManagementAttachRequestDTO managementAttachRequestDTO);
    /**
     * 分页查询供应商证件信息（不分页）
     */
    List<ManagementAttach> listAllManagementAttachByDTO(ManagementAttach managementAttach);

    /**
     * 供应商清单驳回
     */
    void rejectVendorInformation(Long companyId);

}

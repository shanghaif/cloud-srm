package com.midea.cloud.srm.sup.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.info.dto.CategoryDTO;
import com.midea.cloud.srm.model.supplier.info.dto.ManagementAttachRequestDTO;
import com.midea.cloud.srm.model.supplier.info.dto.VendorInformation;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @update xiexh12@meicloud.com
 * <p>
 * 修改日期: 2020/9/9 20:25
 * </pre>
 */
public interface VendorInformationMapper extends BaseMapper<VendorInformation> {

    List<CategoryDTO> getCategoryListByCompanyId(Long companyId, Long categoryId);

    List<ManagementAttach> listAttachByDTO(ManagementAttachRequestDTO managementAttachRequestDTO);

    List<ManagementAttach> listAttachMix(ManagementAttachRequestDTO managementAttachRequestDTO);
}

package com.midea.cloud.srm.sup.vendorimport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportCategoryDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.OrgDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImport;

import java.util.List;

/**
 * <p>
 * 供应商引入表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-09-13
 */
public interface VendorImportMapper extends BaseMapper<VendorImport> {

    List<OrgDTO> getOrgListByVendorId(Long vendorId);

    List<VendorImportCategoryDTO> getCategoryListByParams(Long vendorId, Long orgId);
}

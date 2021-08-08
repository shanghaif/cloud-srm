package com.midea.cloud.srm.sup.vendororgcategory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VendorOrgCateRelMapper
 *
 * @author zixuan.yan@meicloud.com
 */
public interface VendorOrgCateRelMapper extends BaseMapper<OrgCategory> {

    /**
     * 查询 满足特定组织品类权限要求的供应商集
     *
     * @param parameter 查询参数
     * @return 供应商集
     */
    List<CompanyInfo> findValidVendorWithOrgCateRelPermission(@Param("parameter") FindVendorOrgCateRelParameter parameter);

    /**
     * 查询 合格的供应商组织品类关系集
     *
     * @param parameter 查询参数
     * @return 供应商品类关系集
     */
    List<VendorOrgCateRelVO> findValidVendorOrgCateRelations(@Param("parameter") FindVendorOrgCateRelParameter parameter);

    /**
     * 查询 供应商组织品类关系集
     *
     * @param parameter 查询参数
     * @return 供应商品类关系集
     */
    List<VendorOrgCateRelVO> findVendorOrgCateRelations(@Param("parameter") FindVendorOrgCateRelParameter parameter);
}

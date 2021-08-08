package com.midea.cloud.srm.sup.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.model.supplier.info.dto.VendorDto;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织与品类 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-02
 */
public interface OrgCategoryMapper extends BaseMapper<OrgCategory> {
    /**
     * 查找供应商独家组织+品类
     * @param vendorId
     * @return
     */
    List<OrgCategory> querySingleSourceList(@Param("vendorId") Long vendorId);

    List<OrgCategory> listPageOrgCategoryByParam(@Param("query") OrgCategoryQueryDTO orgCategoryQueryDTO);

    /**
     *
     * @param orgCategoryQueryDTO
     * @return
     */
    List<OrgCategory> listForCheck(OrgCategoryQueryDTO orgCategoryQueryDTO);

    /**
     * 查询 合格的供应商组织品类关系集
     *
     * @param categoryIds 查询参数
     * @return 供应商品类关系集
     */
    List<VendorDto> findValidVendorOrgCate(@Param("categoryIds") List<Long> categoryIds);
}

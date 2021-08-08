package com.midea.cloud.srm.sup.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.VendorDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.dto.CompanyRequestDTO;
import com.midea.cloud.srm.model.supplier.info.dto.VendorDto;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基本信息 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-02
 */
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {

    /**
     * 根据合作组织ID和关键字分页查询供应商基本信息
     * @param keyWord
     * @param orgId
     * @return
     */
    List<CompanyInfo> listPageByOrgCodeAndKeyWord(CompanyRequestDTO companyRequestDTO);

    CompanyInfo queryVendorByNameAndOrgId(@Param("vendorName") String vendorName, @Param("orgId") Long orgId);

    List<User> getVendorAccountsByCompanyId(@Param("companyId") Long companyId);

    List<CompanyInfo> listAllForImport();

    /**
     * 查找用户品类分工没有对应的供应商ID
     */
    List<Long> queryVendorIdByUserId(@Param("userId") Long userId);

    List<VendorDTO> listCompanyInfosByStringList(@Param("list") List<String> list);
}

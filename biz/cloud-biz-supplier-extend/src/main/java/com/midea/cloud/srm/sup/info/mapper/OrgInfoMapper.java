package com.midea.cloud.srm.sup.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 合作组织信息 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-02
 */
public interface OrgInfoMapper extends BaseMapper<OrgInfo> {
    List<OrgInfo> queryOrgInfoByVendorId(@Param("companyId") Long companyId);
}

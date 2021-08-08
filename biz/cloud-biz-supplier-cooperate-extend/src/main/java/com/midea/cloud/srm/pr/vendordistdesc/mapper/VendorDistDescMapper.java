package com.midea.cloud.srm.pr.vendordistdesc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.VendorDistDesc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*  <pre>
 *  供应商分配明细表 Mapper 接口
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:01:39
 *  修改内容:
 * </pre>
*/
public interface VendorDistDescMapper extends BaseMapper<VendorDistDesc> {
    /**
     * 查找用户有权限的
     * 需将该用户组织权限内所有【是否已分配供应商】为“是”，且【剩余可下单数量】=【需求数量】的采购申请行数据
     * @param orgIds
     * @return
     */
    List<Long> queryRequirementLinebyOrgs(@Param("orgIds") List<Long> orgIds);

    /**
     * 查找
     * 【是否已分配供应商】字段，必须为“否”或“分配失败”
     * 【剩余可下单数量】=【需求数量】
     *  过滤用户有权限的组织
     *  @param orgIds
     * @return
     */
    List<RequirementLine> queryRequirementLine(@Param("orgIds") List<Long> orgIds);
}

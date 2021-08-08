package com.midea.cloud.srm.base.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.base.organization.mapper.LocationMapper;
import com.midea.cloud.srm.base.organization.mapper.OrganizationMapper;
import com.midea.cloud.srm.base.organization.service.ILocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.model.base.organization.entity.Location;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.netflix.discovery.converters.Auto;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  地址接口表 服务实现类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 19:44:45
 *  修改内容:
 * </pre>
*/
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements ILocationService {

    @Autowired
    private IOrganizationService iOrganizationService;

    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * 根据组织查询地点
     * @param organization 库存组织或业务实体
     * @modifiedBy xiexh12@meicloud.com
     */
    @Override
    public List<Location> getLocationsByOrganization(Organization organization){
        Assert.isTrue(!StringUtils.isEmpty(organization.getOrganizationCode()), "所选的组织编码为空！");
        String organizationCode = organization.getOrganizationCode();
        //先查询库存组织 根据编码
        QueryWrapper<Organization> organizationQueryWrapper = new QueryWrapper<>();
        organizationQueryWrapper.eq("ORGANIZATION_TYPE_CODE","INV")
                .eq("ORGANIZATION_CODE", organizationCode);
        List<Organization> list = iOrganizationService.list(organizationQueryWrapper);

        //如查询不到库存组织，再查业务实体
        if (CollectionUtils.isEmpty(list) && organization.getOrganizationId() != null){
            list = organizationMapper.getInvOrganizationsByOrgId(organization.getOrganizationId());
        }
        //两次查询到的都为空
        Assert.isTrue(CollectionUtils.isNotEmpty(list),"找不到对应的业务实体或者库存组织！");
        Map<Long, Location> map = new HashMap<>();
        for (Organization o : list) {
            List<Location> locationsByInvOrganization = getLocationsByInvOrganization(o);
            if (CollectionUtils.isNotEmpty(locationsByInvOrganization)){
                for (Location location : locationsByInvOrganization) {
                    if (location.getLocationId() != null && !map.containsKey(location.getId())){
                       map.put(location.getId(), location);
                    }
                }
            }
        }
        //将map转为地点List
        List<Location> resultLocations = new ArrayList<>();
        if (map != null){
            for (Map.Entry<Long, Location> locationEntry : map.entrySet()){
                if (locationEntry != null){
                    resultLocations.add(locationEntry.getValue());
                }
            }
        }
        return resultLocations;
    }

    /**
     * 根据库存组织查询地点
     * @modifiedBy xiexh12@meicloud.com 10-10 11:30
     * @Param organization 库存组织
     */
    public List<Location> getLocationsByInvOrganization(Organization organization){
        Assert.notNull(organization, "传入的库存组织为空！");
        //2020-12-29 隆基产品回迁 因为 Location.organizationCode 是通过与接收erp的信息赋值，所以现在用ceeaReceivingLocationId去匹配
//        Assert.isTrue(!StringUtils.isEmpty(organization.getOrganizationCode()), "传入的库存组织编码为空！");
//        List<Location> locations = this.list(
//                new QueryWrapper<>(new Location().setOrganizationCode(organization.getOrganizationCode()))
//        );
        if(StringUtils.isEmpty(organization.getCeeaReceivingLocationId())){
            throw new BaseException(organization.getOrganizationName() + "的ceeaReceivingLocationId为空");
        }
        List<Location> locations = this.list(new QueryWrapper<>(new Location().setLocationId(Long.parseLong(organization.getCeeaReceivingLocationId()))));
        return locations;
    }
}

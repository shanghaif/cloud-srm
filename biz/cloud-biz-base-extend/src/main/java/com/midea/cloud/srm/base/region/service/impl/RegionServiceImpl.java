package com.midea.cloud.srm.base.region.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.base.region.mapper.RegionMapper;
import com.midea.cloud.srm.base.region.service.IRegionService;
import com.midea.cloud.srm.model.base.region.dto.AreaDTO;
import com.midea.cloud.srm.model.base.region.dto.AreaDataDTO;
import com.midea.cloud.srm.model.base.region.dto.CityParamDto;
import com.midea.cloud.srm.model.base.region.entity.Region;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  国家地区表 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 10:15:09
 *  修改内容:
 * </pre>
*/
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {
    private static final String RANGE = "range";//大区域
    private static final String PROVINCE = "province";//省
    private static final String CITY = "city";//市
    private static final String AREA = "area";//县
    private static final String TOWN = "town";//乡镇、街道

    private static final long PROVINCE_LEVEL_NO = 1; //省 级别
    private static final long CITY_LEVEL_NO = 2;    //市级别
    private static final long COUNTY_LEVEL_NO = 3;  //县级别


    @Autowired
    private RegionMapper regionMapper;


    @Override
    public List<AreaDTO> getRegionsByType(String queryType, Long parentId) {
        switch (queryType) {
            case RANGE:
                return this.getRanges();
            case PROVINCE:
                return this.getProvinces(parentId);
            case CITY:
                return this.getCitys(parentId);
            case AREA:
                return this.getAreas(parentId);
            case TOWN:
                return this.getTowns(parentId);
            default:
                break;
        }
        return  null;
    }

    @Override
    public List<AreaDTO> getAllRegionsByType(String queryType, Long parentId) {
        switch (queryType) {
            case RANGE:
                return this.getRanges();
            case PROVINCE:
                return this.getProvinces(parentId);
            case CITY:
                return this.getAllCitys(parentId);
            case AREA:
                return this.getAreas(parentId);
            case TOWN:
                return this.getTowns(parentId);
            default:
                break;
        }
        return  null;
    }

    @Override
    public List<AreaDTO> getRanges() {
        return regionMapper.findRange();
    }

    @Override
    public List<AreaDTO> getProvinces(Long rangeId){
        return regionMapper.findProvince(rangeId);
    }

    @Override
    public List<AreaDTO> getCitys(Long provinceId){
        return regionMapper.findCity(provinceId);
    }

    @Override
    public List<AreaDTO> getAllCitys(Long provinceId){
        return regionMapper.findAllCity(provinceId);
    }

    @Override
    public List<AreaDTO> getAreas(Long cityId) {
        return regionMapper.findArea(cityId);
    }

    @Override
    public List<AreaDTO> getTowns(Long areaId) {
        return regionMapper.findTown(areaId);
    }

    @Override
    public AreaDataDTO getRegionById(Long id) {
        return regionMapper.findRegionById(id);
    }

    @Override
    public List<AreaDTO> checkCity(CityParamDto cityParamDto) {
        return regionMapper.checkCity(cityParamDto);
    }

    @Override
    public Map<String, Region> queryRegionByName(List<String> areaNames) {
        Map<String, Region> regionMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(areaNames)) {
            List<Region> regions = this.list(Wrappers.lambdaQuery(Region.class).in(Region::getAreaName, areaNames));
            if(CollectionUtils.isNotEmpty(regions)){
                regionMap = regions.stream().collect(Collectors.toMap(region -> String.valueOf(region.getAreaName()) + region.getParentId(), Function.identity(), (k1, k2) -> k1));
            }
        }
        return regionMap;
    }

    /**
     * 获取省数据
     * @param provinceNameList
     * @return
     */
    @Override
    public List<Region> listProvinceByNameBatch(List<String> provinceNameList) {
        if(CollectionUtils.isEmpty(provinceNameList)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<Region> regionQueryWrapper = new QueryWrapper<>();
        regionQueryWrapper.in("AREA_NAME",provinceNameList);
        regionQueryWrapper.eq("LEVEL_NO",PROVINCE_LEVEL_NO);
        return this.list(regionQueryWrapper);
    }

    /**
     * 获取市数据
     * @param cityNameList
     * @return
     */
    @Override
    public List<Region> listCityByNameBatch(List<String> cityNameList) {
        if(CollectionUtils.isEmpty(cityNameList)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<Region> regionQueryWrapper = new QueryWrapper<>();
        regionQueryWrapper.in("AREA_NAME",cityNameList);
        regionQueryWrapper.eq("LEVEL_NO",CITY_LEVEL_NO);
        return this.list(regionQueryWrapper);
    }

    /**
     * 获取县数据
     * @param countyNameList
     * @return
     */
    @Override
    public List<Region> listCountyByNameBatch(List<String> countyNameList) {
        if(CollectionUtils.isEmpty(countyNameList)){
            return Collections.EMPTY_LIST;
        }
        return regionMapper.listCountyByNameBatch(countyNameList);
    }
}

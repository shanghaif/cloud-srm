package com.midea.cloud.srm.base.region.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.region.dto.AreaDTO;
import com.midea.cloud.srm.model.base.region.dto.AreaDataDTO;
import com.midea.cloud.srm.model.base.region.dto.CityParamDto;
import com.midea.cloud.srm.model.base.region.entity.Region;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  国家地区表 服务类
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
public interface IRegionService extends IService<Region> {

    /**
     * 根据名字查找地名信息
     * @return areaName+parentId  : Region
     */
    Map<String,Region> queryRegionByName(List<String> areaNames);

    /**
     *
     * @param queryType 查询类型（province、city、area）
     * @param parentId 父级编号
     * @return
     */
    public List<AreaDTO> getRegionsByType(String queryType, Long parentId) ;

    /**
     *
     * @param queryType 查询类型（province、city、area）
     * @param parentId 父级编号
     * @return
     */
    public List<AreaDTO> getAllRegionsByType(String queryType, Long parentId) ;

    /**
     * 查询中国所有片区（如：华东、华北、东北...）
     * @return
     */
    public List<AreaDTO> getRanges();


    /**
     * 查询全国所有省（如：江西省、浙江省、广东省...）
     * @rangId 片区ID（可选参数，不传（即为null时）查询的是全国所有省份/直辖市）
     * @return
     */
    public List<AreaDTO> getProvinces(Long rangeId);


    /**
     * 按省编号查询所有城市（广州市、佛山市....）
     * @param provinceId
     * @return
     */
    public List<AreaDTO> getCitys(Long provinceId);

    /**
     * 按省编号查询所有城市（广州市、佛山市....）
     * @param provinceId
     * @return
     */
    public List<AreaDTO> getAllCitys(Long provinceId);


    /**
     * 按城市编号查询所有县级\区(白云区、荔湾区....)
     * @param cityId
     * @return
     */
    public List<AreaDTO> getAreas(Long cityId);


    /**
     * 按县编号查询所有乡镇(北滘镇....)
     * @param areaId
     * @return
     */
    public List<AreaDTO> getTowns(Long areaId);

    /**
     * 按id查询地区信息 （id：239987）
     * @param id
     * @return
     */
    public AreaDataDTO getRegionById(Long id);

    List<AreaDTO> checkCity(CityParamDto cityParamDto);

    /**
     * 获取省数据
     * @param provinceNameList
     * @return
     */
    List<Region> listProvinceByNameBatch(List<String> provinceNameList);

    /**
     * 获取市数据
     * @param cityNameList
     * @return
     */
    List<Region> listCityByNameBatch(List<String> cityNameList);

    /**
     * 获取县数据
     * @param countyNameList
     * @return
     */
    List<Region> listCountyByNameBatch(List<String> countyNameList);
}

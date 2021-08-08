package com.midea.cloud.srm.base.region.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.region.dto.AreaDTO;
import com.midea.cloud.srm.model.base.region.dto.AreaDataDTO;
import com.midea.cloud.srm.model.base.region.dto.CityParamDto;
import com.midea.cloud.srm.model.base.region.entity.Region;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 国家地区表 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-10
 */
public interface RegionMapper extends BaseMapper<Region> {

    /**
     * 查询所有片区（如：华东、华北、华南、东北、西北...）
     * @return
     * @throws Exception
     */
    public List<AreaDTO> findRange();

    /**
     * 查询所有省(按片区ID，华东、华北、东北....)
     * @return
     * @throws Exception
     */
    public List<AreaDTO> findProvince(Long rangeId);

    /**
     * 按省编号查询所有城市
     * @param m
     * @return
     * @throws Exception
     */
    public List<AreaDTO> findCity(Long provinceId);

    /**
     * 查询所有城市
     * @param m
     * @return
     * @throws Exception
     */
    public List<AreaDTO> findAllCity(Long provinceId);

    /**
     * 按城市编号查询所有县区域
     * @param m
     * @return
     * @throws Exception
     */
    public List<AreaDTO> findArea(Long cityId);

    /**
     * 按县、区编号查询所有乡镇、街道
     * @param m
     * @return
     * @throws Exception
     */
    public List<AreaDTO> findTown(Long areaId);

    /**
     * 按id查询地区信息 （id：239987）
     * @param id
     * @return
     */
    public AreaDataDTO findRegionById(Long id);

    List<AreaDTO> checkCity(CityParamDto cityParamDto);

    /**
     * 获取县数据
     * @param countyNameList
     * @return
     */
    List<Region> listCountyByNameBatch(@Param("countyNameList") List<String> countyNameList);
}

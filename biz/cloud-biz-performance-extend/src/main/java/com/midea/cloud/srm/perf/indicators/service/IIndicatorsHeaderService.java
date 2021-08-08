package com.midea.cloud.srm.perf.indicators.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsDTO;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsHeaderDTO;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsHeader;

import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  指标库头表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>o
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-26 11:35:36
 *  修改内容:
 * </pre>
 */
public interface IIndicatorsHeaderService extends IService<IndicatorsHeader> {

    /**
     * Description 根据条件获取指标头和行信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     * @throws
     **/
    IndicatorsHeaderDTO findIndicationHeadAndLineList(IndicatorsHeaderDTO indicatorsHeader);

    /**
     * Description 根据指标头ID删除指标头和行信息
     * @Param indicatorHeadId 指标头Id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     * @throws BaseException
     **/
    BaseResult<String> delIndicationHeaderAndLine(Long indicatorHeadId) throws BaseException;

    /**
     * Description 禁用/启动指标头和行信息
     * @Param indicatorHeadId 指标头Id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.26
     **/
    BaseResult<String> updateIndicatorHeadAndLineEnable(Long indicatorHeadId, String enableFlag) throws BaseException;

    /**
     * Description 新建指标头和行信息
     * @Param indicatorsDTO 指标头和行对象
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.27
     * @throws
     **/
    BaseResult<String> saveOrUpdateIndicatorHeadAndLine(IndicatorsDTO indicatorsDTO) throws BaseException;

    /**
     * Description 获取指标类型-指标维度(启动、不删除和不重复的指标维度和指标类型)
     * @Param
     * @return Map的Key为 indicatorType和indicatorDimension
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.30
     * @throws BaseException
     **/
    List<Map<String, Object>> findIndicatorsHeaderDimensionList() throws BaseException;

    /**
     * Description 根据指标类型和指标维度获取指标、指标行信息
     * @Param indicatorType 指标类型
     * @Param indicatorDimension 指标维度
     * @Param deleteFlag 是否删除
     * @Param enableFlag 是否有效
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.30
     * @throws BaseException
     **/
    public List<IndicatorsHeaderDTO> findIndicatorsHeaderByDimension(String indicatorType, String indicatorDimension,
                                                                     String deleteFlag, String enableFlag) throws BaseException;
}

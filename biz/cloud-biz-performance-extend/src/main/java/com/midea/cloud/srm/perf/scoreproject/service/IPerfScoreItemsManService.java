package com.midea.cloud.srm.perf.scoreproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;

import java.util.List;

/**
 *  <pre>
 *  绩效评分项目评分人表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-06 15:10:36
 *  修改内容:
 * </pre>
 */
public interface IPerfScoreItemsManService extends IService<PerfScoreItemsMan> {

    /**
     * Description 根据ID删除绩效评分项目评分人和子表信息
     * @Param scoreItemsManId 绩效评分项目评分人Id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     * @throws
     **/
    String deletePerfScoreItemsManById(Long scoreItemsManId) throws BaseException;

    /**
     * Description 根据绩效评分项目供应商Id、指标维度ID获取有效对应的评分人信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.11
     **/
    List<PerfScoreItemsMan> findScoreItemsManByItemsSupIdAndDimWeightId(PerfScoreItemsMan scoreItemsMan) throws BaseException;

}

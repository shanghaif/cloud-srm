package com.midea.cloud.srm.perf.scoreproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;

import java.util.List;

/**
 *  <pre>
 *  绩效评分项目供应商表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-06 15:10:37
 *  修改内容:
 * </pre>
 */
public interface IPerfScoreItemsSupService extends IService<PerfScoreItemsSup> {

    /**
     * Description 根据条件获取绩效评分项目-供应商信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     * @throws BaseException
     **/
    List<PerfScoreItemsSup> findScoreItemsSupList(PerfScoreItemsSup perfScoreItemsSup) throws BaseException;

    /**
     * Description 根据ID删除绩效评分项目供应商信息
     * @Param scoreItemsSupId 绩效评分项目供应商ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     * @throws
     **/
    String deletePerfScoreItemsSupById(Long scoreItemsSupId);
}

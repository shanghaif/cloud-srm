package com.midea.cloud.srm.perf.scoreproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;

import java.util.List;

/**
 *  <pre>
 *  绩效评分项目评分人-供应商指标表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-02 15:18:22
 *  修改内容:
 * </pre>
 */
public interface IPerfScoreItemManSupIndService extends IService<PerfScoreItemManSupInd> {

    /**
     * Description 批量保存/修改绩效评分项目供应商表-供应商指标信息
     * @Param scoreItemsManSupList 绩效评分项目供应商表-供应商指标集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     * @throws BaseException
     **/
    String saveOrUpdateScoreItemManSupInd(List<PerfScoreItemManSupInd> scoreItemManSupIndList) throws BaseException;



}

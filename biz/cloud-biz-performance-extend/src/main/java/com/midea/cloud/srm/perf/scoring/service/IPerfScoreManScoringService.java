package com.midea.cloud.srm.perf.scoring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoring.PerfIndicatorDimScore;
import com.midea.cloud.srm.model.perf.scoring.PerfOverallScore;
import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;

import java.math.BigDecimal;
import java.util.List;

/**
 *  <pre>
 *  评分人绩效评分表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-11 14:29:16
 *  修改内容:
 * </pre>
 */
public interface IPerfScoreManScoringService extends IService<PerfScoreManScoring> {

    /**
     * Description 根据绩效评分项目表ID保存评分人绩效评分信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.11
     * @throws BaseException
     **/
    String saveScoreManScoringByScoreItemsId(Long scoreItemsId) throws BaseException;

    /**
     * Description 分页查询评分人绩效评分信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     * @throws BaseException
     **/
    List<PerfScoreManScoring> listScoreManScoringPage(PerfScoreManScoring scoreManScoring) throws BaseException;

    /**
     * Description 根据绩效模型指标行表ID获取对应的分数(折算方式为INTERVAL_CONVERSION-按区间折算))
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     * @throws
     **/
    BigDecimal getScoreByTemplateLineId(Long templateLineId) throws BaseException;

    /**
     * Description 保存绩效模型指标集合
     * @Param scoreManScoringsList 绩效模型指标集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.13
     * @throws List<PerfScoreManScoring>
     **/
    String saveScoreManScoring(List<PerfScoreManScoring> scoreManScoringsList);

    /**
     * Description 分组获取指标维度绩效得分保存信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.17
     **/
    List<PerfIndicatorDimScore> getSaveIndicatorDimScoreList(PerfScoreManScoring scoreManScoring);

    /**
     * Description 分组获取指标维度绩效得分主表保存信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.17
     **/
    List<PerfOverallScore> getSaveOverallScoreList(PerfScoreManScoring scoreManScoring);

    /**
     * Description 根据绩效评分项目ID修改评分人绩效评分的状态
     * @Param scoreItemsId 绩效评分项目ID
     * @Param status 评分人绩效评分状态
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.23
     * @throws BaseException
     **/
    String updateScoreManScoringStatus(Long scoreItemsId, String status) throws BaseException;

    /**
     * 绩效评分项目 任务分配
     * @param perfScoreManScorings
     */
    void allocate(List<PerfScoreManScoring> perfScoreManScorings, PerfScoreItemsMan perfScoreItemsMan, Long scoreItemsId, Long templateHeadId);

    /**
     * 附件上传
     * @param perfScoreManScoring
     */
    void uploadFile(PerfScoreManScoring perfScoreManScoring);

    /**
     * 附件删除
     * @param perfScoreManScoring
     */
    void deleteFile(PerfScoreManScoring perfScoreManScoring);

}

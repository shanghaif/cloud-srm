package com.midea.cloud.srm.perf.scoreproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.perf.scoreproject.dto.PerfScoreItemSupIndDTO;
import com.midea.cloud.srm.model.perf.scoreproject.dto.PerfScoreItemsDTO;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;
import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;
import com.midea.cloud.srm.model.perf.scoring.ScoreManScoringV1;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;

import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  绩效评分项目主信息表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-05 17:33:40
 *  修改内容:
 * </pre>
 */
public interface IPerfScoreItemsService extends IService<PerfScoreItems> {

    /**
     * Description 根据绩效模型头ID获取绩效指标信息集合
     * @Param templateHeadId 绩效模型头ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     * @throws BaseException
     **/
    List<PerfTemplateLine> getPerfTemplateLineByHeaderId(Long templateHeadId) throws BaseException;

    /**
     * Description 根据条件获取绩效评分项目和子表集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     * @throws BaseException
     **/
    PerfScoreItemsDTO findScoreItemsAndSonList(Long scoreItemsId) throws BaseException;

    /**
     * Description 新增/修改绩效评分项目和子表信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     * @throws BaseException
     **/
    String saveOrUpdateScoreItemsAndSon(PerfScoreItemsDTO scoreItemsDTO) throws BaseException;

    /**
     * Description 新增或更新绩效评分项目和子表信息
     * @Author xiexh12@meicloud.com
     * @Date 2021.01.21
     * @throws BaseException
     **/
    Long saveOrUpdatePerfScoreItems(PerfScoreItemsDTO scoreItemsDTO) throws BaseException;

    /**
     * Description 绩效评项目-通知评分
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     * @throws BaseException
     **/
    String notifyScorers(PerfScoreItems scoreItems);

    /**
     * Description 修改绩效评分项目
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     * @throws Exception
     **/
    String updateScoreItems(PerfScoreItems scoreItems);

    /**
     * Description 获取已经计算的之后的评分绩效项目集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     * @throws BaseException
     **/
    List<PerfScoreItems> findCalculatedScoreItemsList() throws BaseException;

    /**
     * Description 根据ID删除绩效项目和子表信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.22
     * @throws BaseException
     **/
    String delScoreItemsAndSon(Long socreItemsId) throws BaseException;

    /**
     * Description 根据评分人绩效评分集合修改绩效评分项目ScorePeople
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.23
     * @throws
     **/
    String updateScoreItemsScorePeople(List<PerfScoreManScoring> scoreManScoringList) throws BaseException;

    /**
     * Description 根据评分人绩效评分集合修改绩效评分项目ScorePeople
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.23
     * @throws
     **/
    String updateScoreItemsAndScorePeople(List<ScoreManScoringV1> scoreManScoringV1List) throws BaseException;

    /**
     * Description 判断能否保存/修改绩效评分项目,能返回空，反之Assert提示错误信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.28
     * @throws
     **/
    String checkIsSaveOrUpdateScoreItems(Long scoreItemsId, PerfScoreItemsDTO scoreItemsDTO);

    /**
     * Description 根据绩效评分供应商获取评分绩效评分人-供应商指标表集合
     * @Param scoreItemsSupList 绩效评分项目-供应商集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.02
     * @throws
     **/
    List<PerfScoreItemSupIndDTO> getScoreItemsSupIndicatorList(List<PerfScoreItemsSup> scoreItemsSupList);

    Map<String,Object> updateScoreItemsWithFlow(PerfScoreItems scoreItems);

    /**
     * 绩效项目复制
     * @param scoreItemsId
     */
    void copyScoreItems(Long scoreItemsId);

    /**
     * 项目计算评分前查询哪些评分人没有评分
     * @param scoreItems
     * @return
     */
    BaseResult<String> confirmBeforeCalculate(PerfScoreItems scoreItems);

    /**
     * 项目计算评分前查询哪些评分人没有评分
     * @param scoreItemId
     * @return
     */
    void abandonAlready(Long scoreItemId);

}

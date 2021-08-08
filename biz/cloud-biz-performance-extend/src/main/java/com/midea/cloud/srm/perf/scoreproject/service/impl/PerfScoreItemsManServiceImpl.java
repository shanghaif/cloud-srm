package com.midea.cloud.srm.perf.scoreproject.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.perf.common.ScoreItemsConst;
import com.midea.cloud.srm.perf.scoreproject.mapper.PerfScoreItemsManMapper;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemManSupIndService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsManService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  <pre>
 *  绩效评分项目评分人表 服务实现类
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
@Service
public class PerfScoreItemsManServiceImpl extends ServiceImpl<PerfScoreItemsManMapper, PerfScoreItemsMan>
        implements IPerfScoreItemsManService {

    /**绩效评分项目评分人-供应商指标Service*/
    @Resource
    private IPerfScoreItemManSupIndService iPerfScoreItemManSupIndService;

    /**绩效评分项目评分人-供应商Service*/
//    @Resource
//    private IPerfScoreItemsManSupService iPerfScoreItemsManSupService;
    /**绩效评分项目评分人-绩效指标Service*/
//    @Resource
//    private IPerfScoreItemsManIndicatorService iPerfScoreItemsManIndicatorService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deletePerfScoreItemsManById(Long scoreItemsManId) throws BaseException {
        Assert.notNull(scoreItemsManId, "id不能为空");
        String result = ResultCode.OPERATION_FAILED.getMessage();
        int deleteScoreManCount = 0;
        try{
            deleteScoreManCount = getBaseMapper().deleteById(scoreItemsManId);
            result = ResultCode.SUCCESS.getMessage();
        }catch (Exception e){
            log.error("根据ID:"+scoreItemsManId+"删除绩效评分项目评分人信息时报错：",e);
            return result;
        }

        /**如果删除绩效评分项目评分人信息，则子表信息也删除*/
        if(0 < deleteScoreManCount){

            /**删除绩效评分项目评分人-供应商指标信息*/
            PerfScoreItemManSupInd scoreItemsManSup = new PerfScoreItemManSupInd();
            scoreItemsManSup.setScoreItemsManId(scoreItemsManId);
            List<PerfScoreItemManSupInd> scoreItemsManSupList = iPerfScoreItemManSupIndService.list(new QueryWrapper<>(scoreItemsManSup));
            if(CollectionUtils.isNotEmpty(scoreItemsManSupList)){
                List<Long> scoreItemsManSupIds = new ArrayList<>();
                for(PerfScoreItemManSupInd itemsManSup : scoreItemsManSupList){
                    if(null != itemsManSup){
                        scoreItemsManSupIds.add(itemsManSup.getScoreItemManSupIndId());
                    }
                }
                if(CollectionUtils.isNotEmpty(scoreItemsManSupIds)) {
                    try {
                        iPerfScoreItemManSupIndService.removeByIds(scoreItemsManSupIds);
                    }catch (Exception e){
                        log.error("根据多条绩效评分项目评分人-供应商ID删除信息时报错：",e);
                        throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                    }
                }
            }

            /**删除绩效评分项目评分人-绩效指标信息*/
/*            PerfScoreItemsManIndicator queryItemsManIndicator = new PerfScoreItemsManIndicator();
            queryItemsManIndicator.setScoreItemsManId(scoreItemsManId);
            List<PerfScoreItemsManIndicator> itemsManIndicatorList = iPerfScoreItemsManIndicatorService.list(new QueryWrapper<>(queryItemsManIndicator));
            if(CollectionUtils.isNotEmpty(itemsManIndicatorList)){
                List<Long> scoreItemsManIndicatorIds = new ArrayList<>();
                for(PerfScoreItemsManIndicator itemsManIndicator : itemsManIndicatorList){
                    if(null != itemsManIndicator){
                        scoreItemsManIndicatorIds.add(itemsManIndicator.getScoreItemsManIndicatorId());
                    }
                }
                if(CollectionUtils.isNotEmpty(scoreItemsManIndicatorIds)){
                    try{
                        iPerfScoreItemsManIndicatorService.removeByIds(scoreItemsManIndicatorIds);
                    }catch (Exception e){
                        log.error("根据多条绩效评分项目评分人-绩效指标ID删除信息时报错：",e);
                        throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                    }
                }
            }*/
        }
        return result;
    }

    @Override
    public List<PerfScoreItemsMan> findScoreItemsManByItemsSupIdAndDimWeightId(PerfScoreItemsMan scoreItemsMan) throws BaseException {
        Assert.notNull(scoreItemsMan, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        Assert.notNull(scoreItemsMan.getScoreItemsSupId(), ScoreItemsConst.PER_SCORE_ITEMS_SUP_ID_NOT_NULL);
        Assert.notNull(scoreItemsMan.getDimWightId(), ScoreItemsConst.PER_SCORE_ITEMS_MAN_DIM_WEIGHT_ID_NOT_NULL);
        return getBaseMapper().listScoreItemsManByItemsSupIdAndDimWeightId(scoreItemsMan);
    }
}

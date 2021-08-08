package com.midea.cloud.srm.perf.scoreproject.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;
import com.midea.cloud.srm.perf.scoreproject.mapper.PerfScoreItemsSupMapper;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemManSupIndService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsSupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  <pre>
 *  绩效评分项目供应商表 服务实现类
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
@Service
public class PerfScoreItemsSupServiceImpl extends ServiceImpl<PerfScoreItemsSupMapper, PerfScoreItemsSup>
        implements IPerfScoreItemsSupService {

    /**绩效评分项目评分人-供应商指标Service*/
    @Resource
    private IPerfScoreItemManSupIndService iPerfScoreItemManSupIndService;

    /**
     * 根据绩效评分项目ID获取绩效评分供应商集合
     * @param perfScoreItemsSup
     * @return
     * @throws BaseException
     */
    @Override
    public List<PerfScoreItemsSup> findScoreItemsSupList(PerfScoreItemsSup perfScoreItemsSup) throws BaseException {
        List<PerfScoreItemsSup> perfScoreItemsSupList =  new ArrayList<>();
        try{

            perfScoreItemsSupList = getBaseMapper().findScoreItemsSupList(perfScoreItemsSup);
        }catch (Exception e){
            log.error("根据条件获取绩效评分项目-供应商信息时报错："+e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return perfScoreItemsSupList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deletePerfScoreItemsSupById(Long scoreItemsSupId) {
        Assert.notNull(scoreItemsSupId, "id不能为空");
        String result = ResultCode.OPERATION_FAILED.getMessage();
        try{
            int delCount = getBaseMapper().deleteById(scoreItemsSupId);
            if(0 < delCount){
                /**根据供应商ID获取评分人-供应商指标的数据，并删除*/
                PerfScoreItemManSupInd scoreItemsManSup = new PerfScoreItemManSupInd();
                scoreItemsManSup.setScoreItemsSupId(scoreItemsSupId);
                List<PerfScoreItemManSupInd> scoreItemsManSupList = iPerfScoreItemManSupIndService.list(
                        new QueryWrapper<PerfScoreItemManSupInd>(scoreItemsManSup));
                if(CollectionUtils.isNotEmpty(scoreItemsManSupList)){
                    List<Long> scoreItemsManSupIds = new ArrayList<>();
                    for(PerfScoreItemManSupInd itemsManSup : scoreItemsManSupList){
                        if(null != itemsManSup.getScoreItemManSupIndId()) {
                            scoreItemsManSupIds.add(itemsManSup.getScoreItemManSupIndId());
                        }
                    }

                    if(CollectionUtils.isNotEmpty(scoreItemsManSupIds)){
                        try{
                            iPerfScoreItemManSupIndService.removeByIds(scoreItemsManSupIds);
                        }catch (Exception e){
                            log.error("根据ID:"+scoreItemsSupId+"删除绩效评分项目评分人-供应商指标信息时报错：",e);
                            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                        }
                    }
                }
            }
            result = ResultCode.SUCCESS.getMessage();
        }catch (Exception e){
            log.error("根据ID:"+scoreItemsSupId+"删除绩效评分项目供应商指标信息时报错：",e);
        }
        return result;
    }
}

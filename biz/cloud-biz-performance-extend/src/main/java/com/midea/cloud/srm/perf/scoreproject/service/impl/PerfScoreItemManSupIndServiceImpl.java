package com.midea.cloud.srm.perf.scoreproject.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;
import com.midea.cloud.srm.perf.scoreproject.mapper.PerfScoreItemManSupIndMapper;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemManSupIndService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 *  <pre>
 *  绩效评分项目评分人-供应商指标表 服务实现类
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
@Service
public class PerfScoreItemManSupIndServiceImpl extends ServiceImpl<PerfScoreItemManSupIndMapper, PerfScoreItemManSupInd>
        implements IPerfScoreItemManSupIndService {

    @Override
    public String saveOrUpdateScoreItemManSupInd(List<PerfScoreItemManSupInd> scoreItemsManSupList) throws BaseException {
        if(CollectionUtils.isNotEmpty(scoreItemsManSupList)){
            Assert.notNull(false, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        }
        String result = ResultCode.OPERATION_FAILED.getMessage();
        if(CollectionUtils.isNotEmpty(scoreItemsManSupList)){
            for(PerfScoreItemManSupInd scoreItemsManSup : scoreItemsManSupList){
                if(null != scoreItemsManSup){
                    Long scoreItemManSupIndId = scoreItemsManSup.getScoreItemManSupIndId();
                    if(null == scoreItemManSupIndId){
                        scoreItemManSupIndId = IdGenrator.generate();
                        //如果新增时启用状态为空，则设置为不启用
                        if(StringUtils.isEmpty(scoreItemsManSup.getEnableFlag())){
                            scoreItemsManSup.setEnableFlag(Enable.N.toString());
                        }
                    }
                    scoreItemsManSup.setScoreItemManSupIndId(scoreItemManSupIndId);
                }
            }

            try{
                boolean isEditItemsManSup = super.saveOrUpdateBatch(scoreItemsManSupList);
                if(!isEditItemsManSup){
                    return result;
                }
            }catch (Exception e){
                log.error("新增/保存绩效评分项目评分人-供应商指标表时报错：",e);
                throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
            }
        }
        result = ResultCode.SUCCESS.getMessage();
        return result;
    }
}

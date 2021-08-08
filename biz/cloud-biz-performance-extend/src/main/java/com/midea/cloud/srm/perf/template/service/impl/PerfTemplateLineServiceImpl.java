package com.midea.cloud.srm.perf.template.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsHeader;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsLine;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateLineDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsHeaderService;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsLineService;
import com.midea.cloud.srm.perf.template.mapper.PerfTemplateLineMapper;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateIndsLineService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  <pre>
 *  绩效模型指标信息表 服务实现类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-30 10:17:50
 *  修改内容:
 * </pre>
 */
@Service
public class PerfTemplateLineServiceImpl extends ServiceImpl<PerfTemplateLineMapper, PerfTemplateLine>
        implements IPerfTemplateLineService {

    /**绩效模型指标行表Service*/
    @Autowired
    private IPerfTemplateIndsLineService iPerfTemplateIndsLineService;

    /**指标库头Service接口*/
    @Autowired
    private IIndicatorsHeaderService iIndicatorsHeaderService;

    /**指标库行Service接口*/
    @Autowired
    private IIndicatorsLineService iIndicatorsLineService;

    @Override
    public PerfTemplateLineDTO findPerfTemplateLineAndIndsLine(Long indicatorHeaderId, Long perfTemplateLineId) throws BaseException {
        PerfTemplateLineDTO perfTemplateLineDTO = new PerfTemplateLineDTO();
        if(null != indicatorHeaderId && null != perfTemplateLineId){
            throw new BaseException(ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        }

        try {
            /**如果绩效模型指标ID不为空，则根据绩效模型指标ID获取绩效指标详情信息。否则根据指标库行ID获取绩效指标详情信息*/
            if (null != perfTemplateLineId) {
                /**根据绩效模型指标ID获取绩效指标和行信息,并设置到PerfTemplateLineDTO中*/
                PerfTemplateLine perfTemplateLine = getBaseMapper().selectById(perfTemplateLineId);
                perfTemplateLineDTO.setPerfTemplateLine(perfTemplateLine);
                if (null != perfTemplateLine && null != perfTemplateLine.getTemplateLineId()) {
                    PerfTemplateIndsLine perfTemplateIndsLine = new PerfTemplateIndsLine();
                    perfTemplateIndsLine.setTemplateLineId(perfTemplateLine.getTemplateLineId());
                    List<PerfTemplateIndsLine> perfTemplateIndsLineList = iPerfTemplateIndsLineService.list(new QueryWrapper<>(perfTemplateIndsLine));
                    perfTemplateLineDTO.setPerfTemplateIndsLineList(perfTemplateIndsLineList);
                }
            } else if (null != indicatorHeaderId) {

                /**根据指标库行ID获取指标库头和行信息,并设置到PerfTemplateLineDTO中*/
                IndicatorsHeader indicatorsHeader = iIndicatorsHeaderService.getById(indicatorHeaderId);
                if (null != indicatorsHeader) {
                    PerfTemplateLine perfTemplateLine = new PerfTemplateLine();
                    perfTemplateLine.setIndicatorType(indicatorsHeader.getIndicatorType());
                    perfTemplateLine.setQuoteMode(indicatorsHeader.getQuoteMode());
                    perfTemplateLine.setMarkLimit(indicatorsHeader.getMarkLimit());
                    perfTemplateLine.setIndicatorName(indicatorsHeader.getIndicatorName());
                    perfTemplateLine.setIndicatorLogic(indicatorsHeader.getIndicatorLogic());
                    perfTemplateLine.setEvaluation(indicatorsHeader.getEvaluation());
                    perfTemplateLine.setIndicatorDimension(indicatorsHeader.getIndicatorDimension());
                    perfTemplateLine.setIndicatorLineType(indicatorsHeader.getIndicatorLineType());
                    perfTemplateLine.setDeleteFlag(Enable.N.toString());
                    perfTemplateLine.setTemplateLineId(indicatorsHeader.getIndicatorHeadId());
                    perfTemplateLineDTO.setPerfTemplateLine(perfTemplateLine);

                    if (null != indicatorsHeader.getIndicatorHeadId()) {
                        IndicatorsLine indicatorsLine = new IndicatorsLine();
                        indicatorsLine.setIndicatorHeadId(indicatorsHeader.getIndicatorHeadId());
                        List<IndicatorsLine> indicatorsLineList = iIndicatorsLineService.list(new QueryWrapper(indicatorsLine));
                        List<PerfTemplateIndsLine> perfTemplateIndsLineList = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(indicatorsLineList)) {
                            for(IndicatorsLine line : indicatorsLineList){
                                if(null != line){
                                    PerfTemplateIndsLine perfTemplateIndsLine = new PerfTemplateIndsLine();
                                    perfTemplateIndsLine.setPefScore(line.getPefScore());
                                    perfTemplateIndsLine.setIndicatorLineDes(line.getIndicatorLineDes());
                                    perfTemplateIndsLine.setAssessmentPenalty(line.getAssessmentPenalty());
                                    perfTemplateIndsLine.setDeleteFlag(Enable.N.toString());
                                    perfTemplateIndsLine.setScoreEnd(line.getScoreEnd());
                                    perfTemplateIndsLine.setScoreStart(line.getScoreStart());
                                    perfTemplateIndsLine.setTemplateIndsLineId(line.getIndicatorLineId());
                                    perfTemplateIndsLine.setTemplateLineId(line.getIndicatorHeadId());
                                    perfTemplateIndsLineList.add(perfTemplateIndsLine);
                                }
                            }
                            perfTemplateLineDTO.setPerfTemplateIndsLineList(perfTemplateIndsLineList);
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("根据绩效模型指标ID或指标库行ID获取绩效指标详情信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return perfTemplateLineDTO;
    }

    @Override
    public List<PerfTemplateLine> findTempateLineByParam(PerfTemplateLine templateLine) {
        try{
            List<PerfTemplateLine> perfTemplateLineList = getBaseMapper().findTempateLineByParam(templateLine);
            return perfTemplateLineList;
        }catch (Exception e){
            log.error("根据条件获取绩效模型指标表集合时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
    }


}

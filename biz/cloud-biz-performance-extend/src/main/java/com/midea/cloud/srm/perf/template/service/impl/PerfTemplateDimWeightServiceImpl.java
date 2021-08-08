package com.midea.cloud.srm.perf.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDimWeightDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateLineDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateDimWeight;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;
import com.midea.cloud.srm.perf.template.mapper.PerfTemplateDimWeightMapper;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateDimWeightService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateIndsLineService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateLineService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 *  <pre>
 *  绩效模型指标维度表 服务实现类
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
public class PerfTemplateDimWeightServiceImpl extends ServiceImpl<PerfTemplateDimWeightMapper, PerfTemplateDimWeight>
        implements IPerfTemplateDimWeightService {

    /**绩效模型指标Service*/
    @Autowired
    private IPerfTemplateLineService iPerfTemplateLineService;

    /**绩效模型指标行Service*/
    @Autowired
    private IPerfTemplateIndsLineService iPerfTemplateLineIndsService;

    @Override
    public List<PerfTemplateDimWeightDTO> findPerTemplateDimWeightAndLine(PerfTemplateDimWeight perfTemplateDimWeight) throws BaseException {
        Assert.notNull(perfTemplateDimWeight, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        List<PerfTemplateDimWeightDTO> perfTemplateDimWeightDTOList = new ArrayList<>();
        try{
            //这种方式有bug，即perfTemplateLine信息如果为2条，且dimWeightId相同,则perfTemplateDimWeight也为两条
//            perfTemplateDimWeightDTOList = getBaseMapper().findPerTemplateDimWeightAndLine(perfTemplateDimWeight);
            String deleteFlag = perfTemplateDimWeight.getDeleteFlag();
            List<PerfTemplateDimWeight> perfTemplateDimWeightList = getBaseMapper().selectList(new QueryWrapper<>(perfTemplateDimWeight));
            if(CollectionUtils.isNotEmpty(perfTemplateDimWeightList)){
                for(PerfTemplateDimWeight dimWeight : perfTemplateDimWeightList){
                    if(null != dimWeight && null != dimWeight.getDimWeightId()){
                        PerfTemplateDimWeightDTO perfTemplateDimWeightDTO = new PerfTemplateDimWeightDTO();
                        perfTemplateDimWeightDTO.setPerfTemplateDimWeight(dimWeight);
                        PerfTemplateLine templateLineQuery = new PerfTemplateLine();
                        templateLineQuery.setTemplateDimWeightId(dimWeight.getDimWeightId());
                        templateLineQuery.setDeleteFlag(deleteFlag);
                        List<PerfTemplateLine> templateLineList = iPerfTemplateLineService.list(new QueryWrapper<>(templateLineQuery));
                        List<PerfTemplateLineDTO> perfTemplateLineDTOList = new ArrayList<>();
                        if(CollectionUtils.isNotEmpty(templateLineList)){
                            for(PerfTemplateLine line : templateLineList) {
                                if(null != line) {
                                    PerfTemplateLineDTO perfTemplateLineDTO = new PerfTemplateLineDTO();
                                    perfTemplateLineDTO.setPerfTemplateLine(line);
                                    perfTemplateLineDTOList.add(perfTemplateLineDTO);
                                    if(null != line.getTemplateLineId()){
                                        PerfTemplateIndsLine templateIndsLine = new PerfTemplateIndsLine();
                                        templateIndsLine.setTemplateLineId(line.getTemplateLineId());
                                        List<PerfTemplateIndsLine> templateIndsLineList = iPerfTemplateLineIndsService.list(new QueryWrapper<>(templateIndsLine));
                                        perfTemplateLineDTO.setPerfTemplateIndsLineList(templateIndsLineList);
                                    }
                                }
                            }
                        }
                        perfTemplateDimWeightDTO.setPerfTemplateLineList(perfTemplateLineDTOList);
                        perfTemplateDimWeightDTOList.add(perfTemplateDimWeightDTO);
                    }
                }
            }
        }catch (Exception e){
            log.error("根据条件获取绩效指标维度和指标信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return perfTemplateDimWeightDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delPefTemplateDimWeight(Long dimWeightId) throws BaseException {
        Assert.notNull(dimWeightId, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        PerfTemplateLine perfTemplateLine = new PerfTemplateLine();
        perfTemplateLine.setTemplateDimWeightId(dimWeightId);
        List<PerfTemplateLine> perfTemplateLineList = iPerfTemplateLineService.list(new QueryWrapper<>(perfTemplateLine));
        List<Long> deleteTemplateLineIds = new ArrayList<>();       //需要删除的绩效指标Id集合
        List<Long> deleteTemplateIndsLineIds = new ArrayList<>();   //需要删除的绩效指标行Id集合
        if(CollectionUtils.isNotEmpty(perfTemplateLineList)){
            for(PerfTemplateLine line : perfTemplateLineList){
                if(null != line && null != line.getTemplateLineId()){
                    deleteTemplateLineIds.add(line.getTemplateLineId());

                    PerfTemplateIndsLine perfTemplateIndsLine = new PerfTemplateIndsLine();
                    perfTemplateIndsLine.setTemplateLineId(line.getTemplateLineId());
                    List<PerfTemplateIndsLine> perfTemplateIndsLineList = iPerfTemplateLineIndsService.list(new QueryWrapper<>(perfTemplateIndsLine));
                    if(CollectionUtils.isNotEmpty(perfTemplateIndsLineList)){
                        for(PerfTemplateIndsLine indsLine : perfTemplateIndsLineList){
                            if(null != indsLine && null != indsLine.getTemplateIndsLineId()){
                                deleteTemplateIndsLineIds.add(indsLine.getTemplateIndsLineId());
                            }
                        }
                    }
                }
            }

            //删除除绩效指标库行记录
            boolean isDeleteIndLine = false;    //初始化是否删除绩效指标库行记录为false
            if(CollectionUtils.isNotEmpty(deleteTemplateIndsLineIds)){
                try {
                    isDeleteIndLine = iPerfTemplateLineIndsService.removeByIds(deleteTemplateIndsLineIds);
                }catch (Exception e){
                    log.error("根据绩效维度Id: "+dimWeightId+"删除绩效指标库行记录时报错: ",e);
                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                }
            }else{
                isDeleteIndLine = true;
            }

            //删除除绩效指标库记录
            boolean isDeleteLine = false;   //初始化是否删除绩效指标库记录为false
            if(isDeleteIndLine && CollectionUtils.isNotEmpty(deleteTemplateLineIds)){
                try{
                    isDeleteIndLine = iPerfTemplateLineService.removeByIds(deleteTemplateLineIds);
                }catch (Exception e){
                    log.error("根据绩效维度Id: "+dimWeightId+"删除绩效指标库记录时报错: ",e);
                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                }
            }else{
                isDeleteLine = true;
            }

            //删除绩效模型指标维度记录
            try {
                int deleteCount = getBaseMapper().deleteById(dimWeightId);
                if (0 > deleteCount) {
                    log.error("根据绩效维度Id: " + dimWeightId + "删除绩效指标维度时没删除到数据");
                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                }
            }catch (Exception e){
                log.error("根据绩效维度Id: "+dimWeightId+"删除绩效指标维度记录时报错: ",e);
                throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
            }
        }
        return ResultCode.SUCCESS.getMessage();
    }

    @Override
    public List<PerfTemplateDimWeight> findPerTemplateDimWeightByParam(PerfTemplateDimWeight templateDimWeight) {
        List<PerfTemplateDimWeight> templateDimWeightList = new ArrayList<>();
        try{
            templateDimWeightList = getBaseMapper().findPerTemplateDimWeightByParam(templateDimWeight);
        }catch (Exception e){
            log.error("根据条件获取绩效指标维度(如果有绩效指标和绩效指标行信息就查询)时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return templateDimWeightList;
    }
}

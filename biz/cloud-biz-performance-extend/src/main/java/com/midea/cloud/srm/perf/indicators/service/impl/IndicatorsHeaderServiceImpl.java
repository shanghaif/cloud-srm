package com.midea.cloud.srm.perf.indicators.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.perf.indicators.IndicatorsDimensionEnum;
import com.midea.cloud.common.enums.perf.indicators.IndicatorsTypeEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.ObjectUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsDTO;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsHeaderDTO;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsLineDTO;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsHeader;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsLine;
import com.midea.cloud.srm.perf.common.IndicatorsConst;
import com.midea.cloud.srm.perf.indicators.mapper.IndicatorsHeaderMapper;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsHeaderService;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsLineService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  指标库头表 服务实现类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-26 11:35:36
 *  修改内容:
 * </pre>
 */
@Service
public class IndicatorsHeaderServiceImpl extends ServiceImpl<IndicatorsHeaderMapper, IndicatorsHeader> implements IIndicatorsHeaderService {

    /**指标行Service接口*/
    @Autowired
    private IIndicatorsLineService iIndicatorsLineService;

    @Override
    public IndicatorsHeaderDTO findIndicationHeadAndLineList(IndicatorsHeaderDTO indicatorsHeader) {
        IndicatorsHeaderDTO updateIndicatorsHeader = new IndicatorsHeaderDTO();
        try{
            updateIndicatorsHeader = getBaseMapper().findIndicationHeadAndLineList(indicatorsHeader);

        }catch (Exception e){
            log.error("根据条件获取指标头和行信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getCode());
        }
        return updateIndicatorsHeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<String> delIndicationHeaderAndLine(Long indicatorHeadId) {
        Assert.notNull(indicatorHeadId, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        try{
            int updateCount = getBaseMapper().deleteById(indicatorHeadId);
            if(updateCount > 0){
                IndicatorsLine indicatorsLine = new IndicatorsLine();
                indicatorsLine.setIndicatorHeadId(indicatorHeadId);
                List<IndicatorsLine> indicatorsLineList = iIndicatorsLineService.list(new QueryWrapper<>(indicatorsLine));
                List<Long> indicatorsLineIds = new ArrayList<>();
                if(CollectionUtils.isNotEmpty(indicatorsLineList)){
                    for(IndicatorsLine line : indicatorsLineList){
                        if(null != line){
                            indicatorsLineIds.add(line.getIndicatorLineId());
                        }
                    }
                    if(CollectionUtils.isNotEmpty(indicatorsLineIds)) {
                        iIndicatorsLineService.updateBatchById(indicatorsLineList);
                    }
                }
            }
        }catch (Exception e){
            log.error("根据指标头ID: "+indicatorHeadId+"删除指标头和行信息",e);
            result.setCode(ResultCode.OPERATION_FAILED.getCode());
            result.setMessage(ResultCode.OPERATION_FAILED.getMessage());
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult<String> updateIndicatorHeadAndLineEnable(Long indicatorHeadId, String enableFlag) {
        Assert.notNull(indicatorHeadId, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        Assert.notNull(enableFlag, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());

        BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        try{
            IndicatorsHeader indicatorsHeader = new IndicatorsHeader();
            indicatorsHeader.setIndicatorHeadId(indicatorHeadId);
            indicatorsHeader.setEnableFlag(enableFlag);
            int updateCount = getBaseMapper().updateById(indicatorsHeader);
            if(0 > updateCount){
                result.setCode(ResultCode.OPERATION_FAILED.getCode());
                result.setMessage(ResultCode.OPERATION_FAILED.getMessage());
            }
        }catch (Exception e){
            log.error("根据指标头ID: "+indicatorHeadId+"禁用/启动指标头和行信息",e);
            result.setCode(ResultCode.OPERATION_FAILED.getCode());
            result.setMessage(ResultCode.OPERATION_FAILED.getMessage());
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<String> saveOrUpdateIndicatorHeadAndLine(IndicatorsDTO indicatorsDTO) throws BaseException {
        Assert.notNull(indicatorsDTO, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        IndicatorsHeader indicatorsHeader = indicatorsDTO.getIndicatorsHeader();
        Assert.notNull(indicatorsHeader, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        if(null != indicatorsHeader){
            boolean isSave = true;  //初始化是否是新增操作，默认为是
            Long indicatorsHeadId = indicatorsHeader.getIndicatorHeadId();
            if(null != indicatorsHeadId){
                isSave = false;
            }else {
                indicatorsHeadId = IdGenrator.generate();
            }
            result.setData(String.valueOf(indicatorsHeadId)); //渲染指标头ID
            boolean saveCount = false;
            try {
                indicatorsHeader.setIndicatorHeadId(indicatorsHeadId);
                indicatorsHeader.setEnableFlag(Enable.Y.toString());
                this.checkIndicationHeader(indicatorsHeader);   //检测指标头数据
                saveCount = super.saveOrUpdate(indicatorsHeader);
            }catch (Exception e){
                log.error("保存/修改绩效头时报错：",e);
                throw new BaseException(e.getMessage());
            }

            try {
                List<IndicatorsLine> indicatorsLineList = indicatorsDTO.getIndicatorsLineList();
                if (saveCount && CollectionUtils.isNotEmpty(indicatorsLineList)) {
                    //如果是修改，则先把旧数据删除，再保存新数据
                    if(!isSave) {
                        IndicatorsLine queryIndicatorLine = new IndicatorsLine();
                        queryIndicatorLine.setIndicatorHeadId(indicatorsHeadId);
                        List<IndicatorsLine> oldIndicatorsLineList = iIndicatorsLineService.list(new QueryWrapper<IndicatorsLine>(queryIndicatorLine));
                        List<Long> indicatorsLineIdList = new ArrayList<>();
                        if(CollectionUtils.isNotEmpty(oldIndicatorsLineList)){
                            for(IndicatorsLine indicatorsLine : oldIndicatorsLineList){
                                if(null != indicatorsLine && null != indicatorsLine.getIndicatorLineId()){
                                    indicatorsLineIdList.add(indicatorsLine.getIndicatorLineId());
                                }
                            }
                            if(CollectionUtils.isNotEmpty(indicatorsLineIdList)) {
                                iIndicatorsLineService.removeByIds(indicatorsLineIdList);
                            }
                        }
                    }

                    /**保存指标行信息*/
                    Long indicatorHeadId = indicatorsHeader.getIndicatorHeadId();
                    for (IndicatorsLine indicatorsLine : indicatorsLineList) {
                        if (null != indicatorsLine) {
                            indicatorsLine.setIndicatorLineId(IdGenrator.generate());
                            indicatorsLine.setIndicatorHeadId(indicatorHeadId);
                        }
                    }
                    this.checkIndicationLine(indicatorsLineList);   //检测指标行数据
                    iIndicatorsLineService.saveBatch(indicatorsLineList);
                }
            }catch (Exception e){
                log.error("保存/修改绩效行时报错：",e);
                throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> findIndicatorsHeaderDimensionList() throws BaseException {
        List<Map<String, Object>> indicatorsHeaderList = new ArrayList<>();
        try{
            indicatorsHeaderList = getBaseMapper().findIndicatorsHeaderDimensionList();
            if(CollectionUtils.isNotEmpty(indicatorsHeaderList)){
                for(Map<String, Object> indicatorsHeaderMap : indicatorsHeaderList){
                    if(null != indicatorsHeaderMap){
                        String indicatorType = String.valueOf(indicatorsHeaderMap.get("indicatorType"));
                        String indicatorDimension = String.valueOf(indicatorsHeaderMap.get("indicatorDimension"));
                        indicatorsHeaderMap.put("indicatorTypeName", IndicatorsTypeEnum.getName(indicatorType));
                        indicatorsHeaderMap.put("indicatorDimensionName", IndicatorsDimensionEnum.getName(indicatorDimension));
                        indicatorsHeaderMap.put("indicatorTypeDimension", String.format("%s-%s",indicatorType,indicatorDimension));
                    }
                }
            }
        }catch (Exception e){
            log.error("获取指标类型-指标维度(启动、不删除和不重复的指标维度和指标类型)时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return indicatorsHeaderList;
    }

    @Override
    public List<IndicatorsHeaderDTO> findIndicatorsHeaderByDimension(String indicatorsType, String indicatorsDimension,
                                                                     String deleteFlag, String enableFlag){
        List<IndicatorsHeaderDTO> indicatorsHeaderDTOList = new ArrayList();
        try{
            IndicatorsHeader indicatorsHeader = new IndicatorsHeader();
            indicatorsHeader.setIndicatorType(indicatorsType);
            indicatorsHeader.setIndicatorDimension(indicatorsDimension);
            indicatorsHeader.setEnableFlag(enableFlag);
            //findIndicatorsHeaderByDimension该方法返回值比较多，可以不用
//            indicatorsHeaderList = getBaseMapper().findIndicatorsHeaderByDimension(indicatorsHeader);
            List<IndicatorsHeader> indicatorsHeaderList = getBaseMapper().selectList(new QueryWrapper<>(indicatorsHeader));
            if(CollectionUtils.isNotEmpty(indicatorsHeaderList)){
                for(IndicatorsHeader header : indicatorsHeaderList){
                    IndicatorsHeaderDTO indicatorsHeaderDTO = new IndicatorsHeaderDTO();
                    BeanUtils.copyProperties(header, indicatorsHeaderDTO);
                    IndicatorsLine indicatorsLine = new IndicatorsLine();
                    indicatorsLine.setIndicatorHeadId(header.getIndicatorHeadId());
                    List<IndicatorsLine> indicatorsLineList = iIndicatorsLineService.list(new QueryWrapper<>(indicatorsLine));
                    if(CollectionUtils.isNotEmpty(indicatorsLineList)) {
                        List<IndicatorsLineDTO> indicatorsLineDTOList = (List<IndicatorsLineDTO>) ObjectUtil.deepCopy(indicatorsLineList);
                        indicatorsHeaderDTO.setIndicatorsLineList(indicatorsLineDTOList);
                    }
                    indicatorsHeaderDTOList.add(indicatorsHeaderDTO);
                }
            }

        }catch (Exception e){
            log.error("根据指标类型和指标维度获取有效的指标头信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return indicatorsHeaderDTOList;
    }

    /**
     * Description 检验新增/修改指标头数据
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     **/
    private void checkIndicationHeader(IndicatorsHeader indicatorsHeader){
        Assert.notNull(indicatorsHeader, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        Assert.notNull(indicatorsHeader.getIndicatorHeadId(), "id不能为空");
        Assert.notNull(indicatorsHeader.getIndicatorName(), IndicatorsConst.INDICATOR_NAME_NOT_NULL);
        Assert.notNull(indicatorsHeader.getIndicatorDimension() , IndicatorsConst.INDICATOR_DIMENSION_NOT_NULL);
        Assert.notNull(indicatorsHeader.getIndicatorType() , IndicatorsConst.INDICATOR_TYPE_NOT_NULL);
        Assert.notNull(indicatorsHeader.getEnableFlag() , IndicatorsConst.INDICATOR_ENABLE_FLAG_NOT_NULL);
        Assert.notNull(indicatorsHeader.getQuoteMode() , IndicatorsConst.QUOTE_MODE_NOT_NULL);
        Assert.notNull(indicatorsHeader.getEvaluation() , IndicatorsConst.EVALUATION_NOT_NULL);
        // 校验指标名称不能重复 --文宣说的
        String indicatorName = indicatorsHeader.getIndicatorName();
        QueryWrapper<IndicatorsHeader> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("INDICATOR_NAME", indicatorName);
        if (this.count(queryWrapper)>0) {
            throw new BaseException(LocaleHandler.getLocaleMsg("指标名称不能重复，请修改后重试。"));
        }
    }

    /**
     * Description 检验新增/修改指标行数据
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     **/
    private void checkIndicationLine(List<IndicatorsLine> indicatorsLineList ){
        if(CollectionUtils.isNotEmpty(indicatorsLineList)){
            for(IndicatorsLine indicatorsLine : indicatorsLineList){
                if(null != indicatorsLine){
                    Assert.notNull(indicatorsLine.getIndicatorLineId(), "id不能为空");
                    Assert.notNull(indicatorsLine.getIndicatorHeadId(), IndicatorsConst.INDICATOR_HEAD_ID_NOT_NULL);
                    Assert.notNull(indicatorsLine.getIndicatorLineDes() , IndicatorsConst.INDICATOR_LINE_DES_NOT_NULL);
                }
            }
        }
    }

}

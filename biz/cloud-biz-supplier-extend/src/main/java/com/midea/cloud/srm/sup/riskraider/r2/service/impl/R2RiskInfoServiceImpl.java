package com.midea.cloud.srm.sup.riskraider.r2.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.sup.RiskInfoEnum;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.monitor.HaveMonitor;
import com.midea.cloud.srm.model.supplier.riskraider.r2.dto.R2RiskInfoDto;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RelationDiagram;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RiskInfo;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RiskLabel;
import com.midea.cloud.srm.model.supplier.riskraider.r7.dto.R7FinancialDto;
import com.midea.cloud.srm.sup.info.service.impl.CompanyInfoServiceImpl;
import com.midea.cloud.srm.sup.riskraider.handle.HandleRaiderInfo;
import com.midea.cloud.srm.sup.riskraider.monitor.service.impl.HaveMonitorServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r2.mapper.R2RiskInfoMapper;
import com.midea.cloud.srm.sup.riskraider.r2.service.IR2RiskInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
*  <pre>
 *  风险扫描结果表 服务实现类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:19:50
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class R2RiskInfoServiceImpl extends ServiceImpl<R2RiskInfoMapper, R2RiskInfo> implements IR2RiskInfoService {
    @Autowired
    private HandleRaiderInfo handleRaiderInfo;
    @Autowired
    private R2RiskLabelServiceImpl r2RiskLabelService;
    @Autowired
    private R2RelationDiagramServiceImpl r2RelationDiagramService;
    @Autowired
    private CompanyInfoServiceImpl companyInfoService;
    @Autowired
    private HaveMonitorServiceImpl haveMonitorService;

    @Override
    public void saveOrUpdateRiskFromRaider(CompanyInfo companyInfo) {
        log.info("saveOrUpdateRiskFromRaider,参数：{}",companyInfo.toString());
        Objects.nonNull(companyInfo.getCompanyId());
        companyInfo = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        try {
            //1、调用接口，获取数据
            JSONObject riskInfo = handleRaiderInfo.getRiskInfo(companyInfo, RiskInfoEnum.R2.getCode(), true);

            JSONObject r11RiskInfo = null;
            if(checkHavingMonitor(companyInfo.getCompanyId())){
                r11RiskInfo = handleRaiderInfo.getRiskInfo(companyInfo, RiskInfoEnum.R11.getCode(), false);
            }

//            R2RiskInfoDto riskInfo = handleRaiderInfo.getRiskInfo(companyInfo, RiskInfoEnum.R2.getCode(), R2RiskInfoDto.class, true);
            if(Objects.nonNull(riskInfo)){
                R2RiskInfoDto r2RiskInfoDto = buildR2RiskInfoDto(riskInfo);
                if(Objects.nonNull(r11RiskInfo)){
                    buildR11ToR2(r2RiskInfoDto,r11RiskInfo);
                }

                //2、根据供应商id，查询，是保存，还是更新
                R2RiskInfo r2RiskInfo = this.getOne(Wrappers.lambdaQuery(R2RiskInfo.class).eq( R2RiskInfo::getVendorId,companyInfo.getCompanyId()));
                if(Objects.nonNull(r2RiskInfo) && Objects.nonNull(r2RiskInfo.getRiskInfoId())){
                    R2RiskInfoDto r2RiskInfoDtoOld = getR2RiskInfoDtoById(r2RiskInfo.getRiskInfoId());
                    updateRiskInfo(r2RiskInfoDto,r2RiskInfoDtoOld);
                }else {
                    r2RiskInfoDto.setVendorId(companyInfo.getCompanyId())
                            .setVendorName(companyInfo.getCompanyName())
                            .setVendorCode(companyInfo.getCompanyCode());
                    saveRiskInfo(r2RiskInfoDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRiskInfo(R2RiskInfoDto riskInfoDto){

        //1、保存风险扫描，主数据
        R2RiskInfo r2RiskInfo = BeanCopyUtil.copyProperties(riskInfoDto, R2RiskInfo::new);
        long riskInfoId = IdGenrator.generate();
        r2RiskInfo.setRiskInfoId(riskInfoId);
        this.save(r2RiskInfo);

        //2、保存风险扫描，风险标签数据说明
        R2RiskLabel riskLabel = BeanCopyUtil.copyProperties(riskInfoDto.getRiskLabel(), R2RiskLabel::new);
        riskLabel.setRiskInfoId(riskInfoId).setRiskLabelId(IdGenrator.generate());
        r2RiskLabelService.save(riskLabel);

        //3、保存风险扫描，关系挖掘数据
        R2RelationDiagram relationDiagram = BeanCopyUtil.copyProperties(riskInfoDto.getRelationDiagram(), R2RelationDiagram::new);
        relationDiagram.setRiskInfoId(riskInfoId).setRelationDiagramId(IdGenrator.generate());
        r2RelationDiagramService.save(relationDiagram);

    }
    public void updateRiskInfo(R2RiskInfoDto riskInfoDto,R2RiskInfoDto riskInfoDtoOld){
        //1、更新风险扫描，主数据
        Long riskInfoId = riskInfoDtoOld.getRiskInfoId();
        R2RiskInfo r2RiskInfo = BeanCopyUtil.copyProperties(riskInfoDto, R2RiskInfo::new);
        r2RiskInfo.setRiskInfoId(riskInfoId);
        this.updateById(r2RiskInfo);

        //2、更新风险扫描，风险标签数据说明
        R2RiskLabel riskLabel = BeanCopyUtil.copyProperties(riskInfoDto.getRiskLabel(), R2RiskLabel::new);
        riskLabel.setRiskInfoId(riskInfoId).setRiskLabelId(riskInfoDtoOld.getRiskLabel().getRiskLabelId());
        r2RiskLabelService.updateById(riskLabel);

        //3、更新风险扫描，关系挖掘数据
        R2RelationDiagram relationDiagram = BeanCopyUtil.copyProperties(riskInfoDto.getRelationDiagram(), R2RelationDiagram::new);
        relationDiagram.setRiskInfoId(riskInfoId).setRelationDiagramId(riskInfoDtoOld.getRelationDiagram().getRelationDiagramId());
        r2RelationDiagramService.updateById(relationDiagram);
    }

    public R2RiskInfoDto getR2RiskInfoDtoById(Long riskInfoId){
        Objects.requireNonNull(riskInfoId);
        R2RiskInfo riskInfo = this.getById(riskInfoId);
        R2RiskInfoDto r2RiskInfoDto = null;
        if(Objects.nonNull(riskInfo)){
            r2RiskInfoDto = BeanCopyUtil.copyProperties(riskInfo, R2RiskInfoDto::new);
            R2RiskLabel riskLabel = r2RiskLabelService.getOne(Wrappers.lambdaQuery(R2RiskLabel.class).eq(R2RiskLabel::getRiskInfoId, riskInfoId));
            r2RiskInfoDto.setRiskLabel(riskLabel);
            R2RelationDiagram relationDiagram = r2RelationDiagramService.getOne(Wrappers.lambdaQuery(R2RelationDiagram.class).eq(R2RelationDiagram::getRiskInfoId, riskInfoId));
            r2RiskInfoDto.setRelationDiagram(relationDiagram);
        }
        return r2RiskInfoDto;
    }

    @Override
    public R2RiskInfoDto getRiskInfoDtoByCompanyId(Long companyId){
        Objects.requireNonNull(companyId);
        List<R2RiskInfo> riskInfoList = this.list(Wrappers.lambdaQuery(R2RiskInfo.class).eq(R2RiskInfo::getVendorId, companyId));
        R2RiskInfo riskInfo = null;
        if(CollectionUtils.isNotEmpty(riskInfoList)){
            riskInfo = riskInfoList.get(0);
        }
//        else if(checkHavingMonitor(companyId)) {
//            return queryRiskInfoDtoAndSave(companyId);
//        }
        R2RiskInfoDto r2RiskInfoDto = null;
        if(Objects.nonNull(riskInfo)){
            r2RiskInfoDto = BeanCopyUtil.copyProperties(riskInfo, R2RiskInfoDto::new);
            R2RiskLabel riskLabel = r2RiskLabelService.getOne(Wrappers.lambdaQuery(R2RiskLabel.class).eq(R2RiskLabel::getRiskInfoId, riskInfo.getRiskInfoId()));
            r2RiskInfoDto.setRiskLabel(riskLabel);
            R2RelationDiagram relationDiagram = r2RelationDiagramService.getOne(Wrappers.lambdaQuery(R2RelationDiagram.class).eq(R2RelationDiagram::getRiskInfoId, riskInfo.getRiskInfoId()));
            r2RiskInfoDto.setRelationDiagram(relationDiagram);
        }
        return r2RiskInfoDto;
    }

    public R2RiskInfoDto buildR2RiskInfoDto(JSONObject jsonObject){
        JSONObject riskLabel = jsonObject.getJSONObject("riskLabel");
        JSONObject relationDiagram = jsonObject.getJSONObject("relationDiagram");
        R2RiskInfoDto r2RiskInfoDto = new R2RiskInfoDto();
        R2RiskLabel r2RiskLabel = new R2RiskLabel()
                .setBusinessRiskAnalysis(parseJsonArrayToString(riskLabel.getJSONArray("businessRiskAnalysisLabelList")))
                .setJudicialRiskAnalysis(parseJsonArrayToString(riskLabel.getJSONArray("judicialRiskAnalysisLabelList")))
                .setNewsRiskAnalysis(parseJsonArrayToString(riskLabel.getJSONArray("newsLabelList")))
                .setFinanceRiskAnalysis(parseJsonArrayToString(riskLabel.getJSONArray("financeRiskAnalysisLabelList")))
                .setBlacklistRiskAnalysis(parseJsonArrayToString(riskLabel.getJSONArray("blacklistRiskAnalysisLabelList")));

        R2RelationDiagram r2RelationDiagram = new R2RelationDiagram()
                .setRelationDiagramDesc(relationDiagram.getString("relationDiagramDesc"))
                .setRelationshipNum(relationDiagram.getInteger("relationshipNum"));

        r2RiskInfoDto.setRiskLabel(r2RiskLabel).setRelationDiagram(r2RelationDiagram);

        return r2RiskInfoDto;
    }

    public String parseJsonArrayToString(JSONArray jsonArray){
        return StringUtil.join(jsonArray.stream().toArray(),",");
    }

    public R2RiskInfoDto queryRiskInfoDtoAndSave(Long companyId){
        CompanyInfo companyInfo = new CompanyInfo().setCompanyId(companyId);
        this.saveOrUpdateRiskFromRaider(companyInfo);
        return getRiskInfoDtoByCompanyIdBySecond(companyId);
    }

    public R2RiskInfoDto getRiskInfoDtoByCompanyIdBySecond(Long companyId){
        Objects.requireNonNull(companyId);
        List<R2RiskInfo> riskInfoList = this.list(Wrappers.lambdaQuery(R2RiskInfo.class).eq(R2RiskInfo::getVendorId, companyId));
        R2RiskInfo riskInfo = null;
        if(CollectionUtils.isNotEmpty(riskInfoList)){
            riskInfo = riskInfoList.get(0);
        }
        R2RiskInfoDto r2RiskInfoDto = null;
        if(Objects.nonNull(riskInfo)){
            r2RiskInfoDto = BeanCopyUtil.copyProperties(riskInfo, R2RiskInfoDto::new);
            R2RiskLabel riskLabel = r2RiskLabelService.getOne(Wrappers.lambdaQuery(R2RiskLabel.class).eq(R2RiskLabel::getRiskInfoId, riskInfo.getRiskInfoId()));
            r2RiskInfoDto.setRiskLabel(riskLabel);
            R2RelationDiagram relationDiagram = r2RelationDiagramService.getOne(Wrappers.lambdaQuery(R2RelationDiagram.class).eq(R2RelationDiagram::getRiskInfoId, riskInfo.getRiskInfoId()));
            r2RiskInfoDto.setRelationDiagram(relationDiagram);
        }
        return r2RiskInfoDto;
    }

    public boolean checkHavingMonitor(Long companyId){
        //todo，判断该供应商是否监控供应商,先暂时做成实时获取，调用风险雷达接口，可能比较慢
        return Objects.isNull(haveMonitorService
                .getOne(Wrappers.lambdaQuery(HaveMonitor.class)
                        .eq(HaveMonitor::getCompanyId,companyId))) ? false : true;
    }

    public void saveOrUpdateRiskR2(JSONObject riskInfo,Long companyId){
        log.info("saveOrUpdateRiskR2,参数：{}",riskInfo);
        Objects.nonNull(companyId);
        CompanyInfo companyInfo = companyInfoService.getByCompanyId(companyId);

        JSONObject r11RiskInfo = null;
        if(checkHavingMonitor(companyInfo.getCompanyId())){
            try {
                r11RiskInfo = handleRaiderInfo.getRiskInfo(companyInfo, RiskInfoEnum.R11.getCode(), false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(Objects.nonNull(riskInfo)){
            R2RiskInfoDto r2RiskInfoDto = buildR2RiskInfoDto(riskInfo);
            if(Objects.nonNull(r11RiskInfo)){
                buildR11ToR2(r2RiskInfoDto,r11RiskInfo);
            }

            //2、根据供应商id，查询，是保存，还是更新
            R2RiskInfo r2RiskInfo = this.getOne(Wrappers.lambdaQuery(R2RiskInfo.class).eq( R2RiskInfo::getVendorId,companyInfo.getCompanyId()));
            if(Objects.nonNull(r2RiskInfo) && Objects.nonNull(r2RiskInfo.getRiskInfoId())){
                R2RiskInfoDto r2RiskInfoDtoOld = getR2RiskInfoDtoById(r2RiskInfo.getRiskInfoId());
                updateRiskInfo(r2RiskInfoDto,r2RiskInfoDtoOld);
            }else {



                r2RiskInfoDto.setVendorId(companyInfo.getCompanyId())
                        .setVendorName(companyInfo.getCompanyName())
                        .setVendorCode(companyInfo.getCompanyCode());
                saveRiskInfo(r2RiskInfoDto);
            }
        }
    }

    public R2RiskInfoDto buildR11ToR2(R2RiskInfoDto r2,JSONObject r11){
        if(Objects.isNull(r11)) return r2;
        r2.setRiskSituation(r11.getString("riskSituation"));
        List<String> labes = new ArrayList<String>();
        JSONArray riskLabel = r11.getJSONArray("riskLabel");
        for (int i = 0; i < riskLabel.size(); i++) {
            JSONObject jsonObject = riskLabel.getJSONObject(i);
            labes.add(jsonObject.getString("label"));
        }
        r2.setLabels(StringUtils.join(labes,","));
        return r2;
    }


}

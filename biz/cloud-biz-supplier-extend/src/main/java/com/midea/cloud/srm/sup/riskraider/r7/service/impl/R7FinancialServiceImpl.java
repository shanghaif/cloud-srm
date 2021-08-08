package com.midea.cloud.srm.sup.riskraider.r7.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.sup.RiskInfoEnum;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.monitor.HaveMonitor;
import com.midea.cloud.srm.model.supplier.riskraider.r2.dto.R2RiskInfoDto;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RiskInfo;
import com.midea.cloud.srm.model.supplier.riskraider.r7.dto.R7FinancialDto;
import com.midea.cloud.srm.model.supplier.riskraider.r7.entity.R7Financial;
import com.midea.cloud.srm.model.supplier.riskraider.r7.entity.R7FinancialDetail;
import com.midea.cloud.srm.sup.info.service.impl.CompanyInfoServiceImpl;
import com.midea.cloud.srm.sup.riskraider.handle.HandleRaiderInfo;
import com.midea.cloud.srm.sup.riskraider.monitor.service.impl.HaveMonitorServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r2.service.impl.R2RelationDiagramServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r7.mapper.R7FinancialMapper;
import com.midea.cloud.srm.sup.riskraider.r7.service.IR7FinancialService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
*  <pre>
 *  企业财务信息表 服务实现类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:42:20
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class R7FinancialServiceImpl extends ServiceImpl<R7FinancialMapper, R7Financial> implements IR7FinancialService {
    @Autowired
    private HandleRaiderInfo handleRaiderInfo;
    @Autowired
    private R7FinancialDetailServiceImpl financialDetailService;
    @Autowired
    private R2RelationDiagramServiceImpl r2RelationDiagramService;
    @Autowired
    private CompanyInfoServiceImpl companyInfoService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private HaveMonitorServiceImpl haveMonitorService;

    @Override
    public void saveOrUpdateR7FromRaider(CompanyInfo companyInfo) {
        log.info("saveOrUpdateR7FromRaider,参数：{}",companyInfo.toString());
        Objects.nonNull(companyInfo.getCompanyId());
        companyInfo = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        try {
            //1、调用接口，获取数据
            R7FinancialDto financialDto = handleRaiderInfo.getRiskInfo(companyInfo, RiskInfoEnum.R7.getCode(), R7FinancialDto.class, true);
            if(Objects.nonNull(financialDto)){

                //2、根据供应商id，查询，是保存，还是更新
                R7Financial financialOld = this.getOne(Wrappers.lambdaQuery(R7Financial.class).eq( R7Financial::getVendorId,companyInfo.getCompanyId()));

                if(Objects.nonNull(financialOld) && Objects.nonNull(financialOld.getFinancialId())){
                    updateFinancialInfo(financialDto,financialOld.getFinancialId());
                }else {
                    financialDto.setVendorId(companyInfo.getCompanyId())
                            .setVendorName(companyInfo.getCompanyName())
                            .setVendorCode(companyInfo.getCompanyCode());
                    saveFinancialInfo(financialDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFinancialInfo(R7FinancialDto financialDto){

        //1、保存企业经营分析，主数据
        R7Financial financial = BeanCopyUtil.copyProperties(financialDto, R7Financial::new);
        long financialId = IdGenrator.generate();
        financial.setFinancialId(financialId);
        this.save(financial);

        //2、保存企业经营分析，财务明细。
        List<R7FinancialDetail> companyFinanceDetailList = financialDto.getCompanyFinanceDetailList();
        companyFinanceDetailList.stream().forEach(x->{
            x.setFinancialId(financialId);
            x.setFinancialDetailId(IdGenrator.generate());
        });
        financialDetailService.saveBatch(companyFinanceDetailList);


    }
    public void updateFinancialInfo(R7FinancialDto financialDto,Long financialId){
        //1、更新企业经营分析，主数据
        R7Financial financial = BeanCopyUtil.copyProperties(financialDto, R7Financial::new);
        financial.setFinancialId(financialId);
        this.updateById(financial);

        //2、更新企业经营分析，财务明细。先删除，后更新
        List<R7FinancialDetail> companyFinanceDetailList = financialDto.getCompanyFinanceDetailList();
        Set<Long> collect = financialDetailService.list(Wrappers.lambdaQuery(R7FinancialDetail.class)
                .select(R7FinancialDetail::getFinancialDetailId)
                .eq(R7FinancialDetail::getFinancialId, financialId))
                .stream().map(x -> x.getFinancialDetailId()).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(companyFinanceDetailList)){
            financialDetailService.removeByIds(collect);
        }
        companyFinanceDetailList.stream().forEach(x->{
            x.setFinancialId(financialId);
            x.setFinancialDetailId(IdGenrator.generate());
        });
        financialDetailService.saveBatch(companyFinanceDetailList);
    }

    public R7FinancialDto getR7FinancialDtoById(Long financialId){
        Objects.requireNonNull(financialId);
        R7Financial financial = this.getById(financialId);
        R7FinancialDto financialDto = null;
        if(Objects.nonNull(financial)){
            financialDto = BeanCopyUtil.copyProperties(financial, R7FinancialDto::new);
            List<R7FinancialDetail> financialDetailList = financialDetailService.list(Wrappers.lambdaQuery(R7FinancialDetail.class).eq(R7FinancialDetail::getFinancialId, financialId));
            financialDto.setCompanyFinanceDetailList(financialDetailList);
        }
        return financialDto;
    }

    @Override
    public R7FinancialDto getR7FinancialDtoByCompanyId(Long companyId){
        Objects.requireNonNull(companyId);
        List<R7Financial> financialList = this.list(Wrappers.lambdaQuery(R7Financial.class).eq(R7Financial::getVendorId, companyId));

        R7Financial financial = null;
        if(CollectionUtils.isNotEmpty(financialList)){
            financial = financialList.get(0);
        }
//        else if(checkHavingMonitor(companyId)) {
//            return queryFinancialDtoAndSave(companyId);
//        }
        R7FinancialDto financialDto = null;
        if(Objects.nonNull(financial)){
            financialDto = BeanCopyUtil.copyProperties(financial, R7FinancialDto::new);
            List<R7FinancialDetail> financialDetailList = financialDetailService.list(Wrappers.lambdaQuery(R7FinancialDetail.class).eq(R7FinancialDetail::getFinancialId, financial.getFinancialId()));
            financialDto.setCompanyFinanceDetailList(financialDetailList);
        }
        return financialDto;
    }

    @Override
    public void saveR7ToEs(CompanyInfo companyInfo) throws Exception {
        log.info("saveOrUpdateR7FromRaider,参数：{}",companyInfo.toString());
        Objects.nonNull(companyInfo.getCompanyId());
        companyInfo = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        R7FinancialDto financialDto = handleRaiderInfo.getRiskInfo(companyInfo, RiskInfoEnum.R7.getCode(), R7FinancialDto.class, true);
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(companyInfo.getCompanyId());
        indexRequest.index("raider_r7").type("_doc").id(id).source(JSON.toJSONString(financialDto), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul
    }

    public R7FinancialDto queryFinancialDtoAndSave(Long companyId){
        CompanyInfo companyInfo = new CompanyInfo().setCompanyId(companyId);
        this.saveOrUpdateR7FromRaider(companyInfo);
        return getR7FinancialDtoByCompanyIdBySecond(companyId);
    }

    public R7FinancialDto getR7FinancialDtoByCompanyIdBySecond(Long companyId){
        Objects.requireNonNull(companyId);
        List<R7Financial> financialList = this.list(Wrappers.lambdaQuery(R7Financial.class).eq(R7Financial::getVendorId, companyId));

        R7Financial financial = null;
        if(CollectionUtils.isNotEmpty(financialList)){
            financial = financialList.get(0);
        }
        R7FinancialDto financialDto = null;
        if(Objects.nonNull(financial)){
            financialDto = BeanCopyUtil.copyProperties(financial, R7FinancialDto::new);
            List<R7FinancialDetail> financialDetailList = financialDetailService.list(Wrappers.lambdaQuery(R7FinancialDetail.class).eq(R7FinancialDetail::getFinancialId, financial.getFinancialId()));
            financialDto.setCompanyFinanceDetailList(financialDetailList);
        }
        return financialDto;
    }

    public boolean checkHavingMonitor(Long companyId){
        //todo，判断该供应商是否监控供应商，判断该供应商是否监控供应商,先暂时做成实时获取，调用风险雷达接口，可能比较慢
        return Objects.isNull(haveMonitorService
                .getOne(Wrappers.lambdaQuery(HaveMonitor.class)
                        .eq(HaveMonitor::getCompanyId,companyId))) ? false : true;
    }

    public void saveAllR7(){
        Set<Long> collect = haveMonitorService.list().parallelStream().map(HaveMonitor::getCompanyId).collect(Collectors.toSet());
        collect.stream().forEach(x->{
            saveOrUpdateR7FromRaider(new CompanyInfo().setCompanyId(x));
        });
    }

    public void saveOrUpdateRiskR7(JSONObject riskInfo, Long companyId){
        log.info("saveOrUpdateRiskR7,参数：{}",riskInfo);
        Objects.nonNull(companyId);
        CompanyInfo companyInfo = companyInfoService.getByCompanyId(companyId);

        R7FinancialDto financialDto = JSON.toJavaObject(riskInfo, R7FinancialDto.class);

        if(Objects.nonNull(financialDto)){

            //2、根据供应商id，查询，是保存，还是更新
            R7Financial financialOld = this.getOne(Wrappers.lambdaQuery(R7Financial.class).eq( R7Financial::getVendorId,companyInfo.getCompanyId()));

            if(Objects.nonNull(financialOld) && Objects.nonNull(financialOld.getFinancialId())){
                updateFinancialInfo(financialDto,financialOld.getFinancialId());
            }else {
                financialDto.setVendorId(companyInfo.getCompanyId())
                        .setVendorName(companyInfo.getCompanyName())
                        .setVendorCode(companyInfo.getCompanyCode());
                saveFinancialInfo(financialDto);
            }
        }
    }

}

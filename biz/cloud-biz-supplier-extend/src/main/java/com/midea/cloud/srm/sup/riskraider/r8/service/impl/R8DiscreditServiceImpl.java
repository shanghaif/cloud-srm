package com.midea.cloud.srm.sup.riskraider.r8.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.sup.RiskInfoEnum;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.monitor.HaveMonitor;
import com.midea.cloud.srm.model.supplier.riskraider.r7.dto.R7FinancialDto;
import com.midea.cloud.srm.model.supplier.riskraider.r7.entity.R7Financial;
import com.midea.cloud.srm.model.supplier.riskraider.r8.dto.R8DiscreditDto;
import com.midea.cloud.srm.model.supplier.riskraider.r8.dto.R8DiscreditGuaranteeDto;
import com.midea.cloud.srm.model.supplier.riskraider.r8.dto.R8DiscreditMainDto;
import com.midea.cloud.srm.model.supplier.riskraider.r8.dto.R8DiscreditRelationDto;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.*;
import com.midea.cloud.srm.sup.info.service.impl.CompanyInfoServiceImpl;
import com.midea.cloud.srm.sup.riskraider.handle.HandleRaiderInfo;
import com.midea.cloud.srm.sup.riskraider.monitor.service.impl.HaveMonitorServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r8.mapper.R8DiscreditMapper;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

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
 *  修改日期: 2021-01-18 10:46:05
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class R8DiscreditServiceImpl extends ServiceImpl<R8DiscreditMapper, R8Discredit> implements IR8DiscreditService {

    @Autowired
    private HandleRaiderInfo handleRaiderInfo;
    @Autowired
    private CompanyInfoServiceImpl companyInfoService;
    @Autowired
    private R8DiscreditMainServiceImpl discreditMainService;
    @Autowired
    private R8DiscreditMainLoanServiceImpl loanService;
    @Autowired
    private R8DiscreditMainCreditServiceImpl creditService;
    @Autowired
    private R8DiscreditMainExecutorServiceImpl executorService;
    @Autowired
    private R8DiscreditMainDisexecutorServiceImpl disexecutorService;

    @Autowired
    private R8DiscreditGuaranteeServiceImpl guaranteeService;
    @Autowired
    private R8DiscreditGuaranteeDetailServiceImpl guaranteeDetailService;

    @Autowired
    private R8DiscreditRelationServiceImpl relationService;
    @Autowired
    private R8DiscreditRelationDetailServiceImpl relationDetailService;
    @Autowired
    private R8DiscreditCustomServiceImpl customService;
    @Autowired
    private HaveMonitorServiceImpl haveMonitorService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateR8FromRaider(CompanyInfo companyInfo) {
        log.info("saveOrUpdateR8FromRaider,参数：{}",companyInfo.toString());
        Objects.nonNull(companyInfo.getCompanyId());
        companyInfo = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        try {
            //1、调用接口，获取数据
            R8DiscreditDto discreditDto = handleRaiderInfo.getRiskInfo(companyInfo, RiskInfoEnum.R8.getCode(), R8DiscreditDto.class, false);
            if(Objects.nonNull(discreditDto)){

                //2、根据供应商id，查询，是保存，还是更新
                R8Discredit discredit = this.getOne(Wrappers.lambdaQuery(R8Discredit.class).eq( R8Discredit::getVendorId,companyInfo.getCompanyId()));

                if(Objects.nonNull(discredit) && Objects.nonNull(discredit.getDiscreditId())){
                    updateDiscreditInfo(discreditDto,discredit.getDiscreditId());
                }else {
                    discreditDto.setVendorId(companyInfo.getCompanyId())
                            .setVendorName(companyInfo.getCompanyName())
                            .setVendorCode(companyInfo.getCompanyCode());
                    saveDiscreditInfo(discreditDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDiscreditInfo(R8DiscreditDto discreditDto){

        //1、保存企业失信信息，主数据
        R8Discredit r2RiskInfo = BeanCopyUtil.copyProperties(discreditDto, R8Discredit::new);
        long discreditId = IdGenrator.generate();
        r2RiskInfo.setDiscreditId(discreditId);
        this.save(r2RiskInfo);

        discreditDto.setDiscreditId(discreditId);

        //2、保存企业失信信息，主体企业失信信息。
        saveDiscreditMain(discreditDto);

        //3、保存企业失信信息，担保企业失信信息。
        saveGuaranteeDiscredit(discreditDto);

        //4、保存企业失信信息，关联企业失信信息。
        saveRelationDiscredit(discreditDto);

        //5、保存企业失信信息，自定义失信。
        saveCustomDiscredit(discreditDto);

    }
    public void updateDiscreditInfo(R8DiscreditDto discreditDto,Long discreditId){
        //1、更新企业经营分析，主数据
        R8Discredit discredit = BeanCopyUtil.copyProperties(discreditDto, R8Discredit::new);
        discredit.setDiscreditId(discreditId);
        this.updateById(discredit);
        discreditDto.setDiscreditId(discreditId);

        //2、更新企业失信信息，主体企业失信信息。
        updateDiscreditMain(discreditDto);

        //3、更新企业失信信息，担保企业失信信息。
        updateGuaranteeDiscredit(discreditDto);

        //4、更新企业失信信息，关联企业失信信息。
        updateRelationDiscredit(discreditDto);

        //5、更新企业失信信息，自定义失信。
        updateCustomDiscredit(discreditDto);
    }

    public R8DiscreditDto getR8DiscreditDtoById(Long discreditId){
        Objects.requireNonNull(discreditId);
        R8Discredit discredit = this.getById(discreditId);
        R8DiscreditDto discreditDto = null;
        if(Objects.nonNull(discredit)){
            discreditDto = BeanCopyUtil.copyProperties(discredit, R8DiscreditDto::new);
//            R8DiscreditMain discreditMains = discreditMainService.list(Wrappers.lambdaQuery(R8DiscreditMain.class).eq(R8DiscreditMain::getDiscreditId, discreditId)).stream().findFirst().orElse(new R8DiscreditMain());
//            discreditDto.setR8DiscreditMainDto(discreditMains);
        }
        return discreditDto;
    }


    @Override
    public R8DiscreditDto getR8DiscreditDtoByCompanyId(Long companyId){
        Objects.requireNonNull(companyId);
        List<R8Discredit> discreditList = this.list(Wrappers.lambdaQuery(R8Discredit.class).eq(R8Discredit::getVendorId,companyId));

        R8Discredit discredit = null;
        if(CollectionUtils.isNotEmpty(discreditList)){
            discredit = discreditList.get(0);
        }
//        else if(checkHavingMonitor(companyId)) {
//            return queryFinancialDtoAndSave(companyId);
//        }

        R8DiscreditDto discreditDto = null;
        if(Objects.nonNull(discredit)){
            discreditDto = BeanCopyUtil.copyProperties(discredit, R8DiscreditDto::new);
            Long discreditId = discredit.getDiscreditId();
            discreditDto.setMainDiscredit(buildDiscreditMain(discreditId));
            discreditDto.setGuaranteeDiscredit(buildGuaranteeDiscredit(discreditId));
            discreditDto.setRelationDiscredit(buildRelationDiscredit(discreditId));
            discreditDto.setCustomDiscredit(buildCustomDiscredit(discreditId));
        }
        return discreditDto;
    }

    public boolean checkHavingMonitor(Long companyId){
        //todo，判断该供应商是否监控供应商，判断该供应商是否监控供应商,先暂时做成实时获取，调用风险雷达接口，可能比较慢
        return Objects.isNull(haveMonitorService
                .getOne(Wrappers.lambdaQuery(HaveMonitor.class)
                        .eq(HaveMonitor::getCompanyId,companyId))) ? false : true;
    }

    public R8DiscreditDto queryFinancialDtoAndSave(Long companyId){
        CompanyInfo companyInfo = new CompanyInfo().setCompanyId(companyId);
        this.saveOrUpdateR8FromRaider(companyInfo);
        return getR8DiscreditDtoByCompanyIdSecond(companyId);
    }
    public R8DiscreditDto getR8DiscreditDtoByCompanyIdSecond(Long companyId){
        Objects.requireNonNull(companyId);
        List<R8Discredit> discreditList = this.list(Wrappers.lambdaQuery(R8Discredit.class).eq(R8Discredit::getVendorId,companyId));

        R8Discredit discredit = null;
        if(CollectionUtils.isNotEmpty(discreditList)){
            discredit = discreditList.get(0);
        }
        R8DiscreditDto discreditDto = null;
        if(Objects.nonNull(discredit)){
            discreditDto = BeanCopyUtil.copyProperties(discredit, R8DiscreditDto::new);
            Long discreditId = discredit.getDiscreditId();
            discreditDto.setMainDiscredit(buildDiscreditMain(discreditId));
            discreditDto.setGuaranteeDiscredit(buildGuaranteeDiscredit(discreditId));
            discreditDto.setRelationDiscredit(buildRelationDiscredit(discreditId));
            discreditDto.setCustomDiscredit(buildCustomDiscredit(discreditId));
        }
        return discreditDto;
    }

    public void saveDiscreditMain(R8DiscreditDto discreditDto){
        R8DiscreditMainDto mainDto = discreditDto.getMainDiscredit();
        long mainId = IdGenrator.generate();
        R8DiscreditMain discreditMain = new R8DiscreditMain()
                .setDiscreditId(discreditDto.getDiscreditId())
                .setDiscreditMainId(mainId)
                .setMainDiscreditFlag(mainDto.getMainDiscreditFlag())
                .setMainDiscreditPrompt(mainDto.getMainDiscreditPrompt());

        discreditMainService.save(discreditMain);

        //1、保存拖欠借款信息
        List<R8DiscreditMainLoan> defaultLoanList = mainDto.getDefaultLoanList();
        saveDefaultLoanList(defaultLoanList,mainId);

        //2、保存拖欠贷款信息
        List<R8DiscreditMainCredit> defaultCreditList = mainDto.getDefaultCreditList();
        saveDefaultCreditList(defaultCreditList,mainId);

        //3、保存被执行人信息
        List<R8DiscreditMainExecutor> executorList = mainDto.getExecutorList();
        saveExecutorList(executorList,mainId);

        //4、保存失信被执行人信息
        List<R8DiscreditMainDisexecutor> discreditExecutorList = mainDto.getDiscreditExecutorList();
        saveDiscreditExecutorList(discreditExecutorList,mainId);
    }
    public void saveDefaultLoanList(List<R8DiscreditMainLoan> defaultLoanList,Long mainId){
        defaultLoanList.stream().forEach(x->{
            x.setDiscreditMainId(mainId);
            x.setDiscreditMainLoanId(IdGenrator.generate());
        });
        loanService.saveBatch(defaultLoanList);
    }
    public void saveDefaultCreditList(List<R8DiscreditMainCredit> defaultCreditList,Long mainId){
        defaultCreditList.stream().forEach(x->{
            x.setDiscreditMainId(mainId);
            x.setDiscreditMainCreditId(IdGenrator.generate());
        });
        creditService.saveBatch(defaultCreditList);
    }
    public void saveExecutorList(List<R8DiscreditMainExecutor> executorList,Long mainId){
        executorList.stream().forEach(x->{
            x.setDiscreditMainId(mainId);
            x.setDiscreditMainExecutorId(IdGenrator.generate());
        });
        executorService.saveBatch(executorList);
    }
    public void saveDiscreditExecutorList(List<R8DiscreditMainDisexecutor> discreditExecutorList,Long mainId){
        discreditExecutorList.stream().forEach(x->{
            x.setDiscreditMainId(mainId);
            x.setDiscreditMainDisexecutorId(IdGenrator.generate());
        });
        disexecutorService.saveBatch(discreditExecutorList);
    }

    public void saveGuaranteeDiscredit(R8DiscreditDto discreditDto){
        R8DiscreditGuaranteeDto guaranteeDiscreditDto = discreditDto.getGuaranteeDiscredit();
        long guaranteeId = IdGenrator.generate();
        R8DiscreditGuarantee guarantee = new R8DiscreditGuarantee()
                .setDiscreditId(discreditDto.getDiscreditId())
                .setDiscreditGuaranteeId(guaranteeId)
                .setGuaranteeDiscreditFlag(guaranteeDiscreditDto.getGuaranteeDiscreditFlag())
                .setGuaranteeDiscreditPrompt(guaranteeDiscreditDto.getGuaranteeDiscreditPrompt());

        guaranteeService.save(guarantee);

        //1、对外担保
        List<R8DiscreditGuaranteeDetail> guaranteeDiscreditList = guaranteeDiscreditDto.getGuaranteeDiscreditList();
        saveGuaranteeDiscreditList(guaranteeDiscreditList,guaranteeId);

    }
    public void saveGuaranteeDiscreditList(List<R8DiscreditGuaranteeDetail> guaranteeDiscreditList,Long guaranteeId){
        guaranteeDiscreditList.stream().forEach(x->{
            x.setDiscreditGuaranteeId(guaranteeId);
            x.setDiscreditGuaranteeDetailId(IdGenrator.generate());
        });
        guaranteeDetailService.saveBatch(guaranteeDiscreditList);
    }



    public void saveRelationDiscredit(R8DiscreditDto discreditDto){
        long relationId = IdGenrator.generate();
        R8DiscreditRelationDto relationDiscreditDto = discreditDto.getRelationDiscredit();
        R8DiscreditRelation relation = new R8DiscreditRelation()
                .setDiscreditId(discreditDto.getDiscreditId())
                .setDiscreditRelationId(relationId)
                .setRelationDiscreditFlag(relationDiscreditDto.getRelationDiscreditFlag())
                .setRelationDiscreditPrompt(relationDiscreditDto.getRelationDiscreditPrompt());

        relationService.save(relation);

        //1、关联企业债务列表
        List<R8DiscreditRelationDetail> relationDiscreditList = relationDiscreditDto.getRelationDiscreditList();
        saveRelationDiscreditList(relationDiscreditList,relationId);
    }

    public void saveRelationDiscreditList(List<R8DiscreditRelationDetail> relationDiscreditList,Long relationId){
        relationDiscreditList.stream().forEach(x->{
            x.setDiscreditRelationId(relationId);
            x.setDiscreditRelationDetailId(IdGenrator.generate());
        });
        relationDetailService.saveBatch(relationDiscreditList);
    }

    public void saveCustomDiscredit(R8DiscreditDto discreditDto){
        List<R8DiscreditCustom> customDiscredit = discreditDto.getCustomDiscredit();
        if(CollectionUtils.isEmpty(customDiscredit)) return;
        customDiscredit.stream().forEach(x->{
            x.setDiscreditId(discreditDto.getDiscreditId());
            x.setDiscreditCustomId(IdGenrator.generate());
        });
        customService.saveBatch(customDiscredit);
    }

    public <T> void saveTList(List<T> ts,Long uid){
        ts.stream().forEach(x->{
            Class<?> aClass = x.getClass();
            String simpleName = aClass.getSimpleName();
            String replace = simpleName.replace("R8D", "d");
            Method[] declaredMethods = aClass.getDeclaredMethods();
            Field[] fields = aClass.getFields();
            for (Field field : fields) {
                String name = field.getName();
                if(Objects.equals(replace,name)){
                    try {
                        field.setLong(name,IdGenrator.generate());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
//            for (Method declaredMethod : declaredMethods) {
//                String name = declaredMethod.getName();
//                if(Objects.equals(replace,name)){
//                    try {
//                        declaredMethod.invoke(name,IdGenrator.generate());
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        });
    }

    public void updateDiscreditMain(R8DiscreditDto discreditDto){
        R8DiscreditMainDto mainDiscredit = discreditDto.getMainDiscredit();
        Long discreditMainId = mainDiscredit.getDiscreditMainId();
        //先删除
        creditService.remove(Wrappers.lambdaQuery(R8DiscreditMainCredit.class).eq(R8DiscreditMainCredit::getDiscreditMainId,discreditMainId));
        executorService.remove(Wrappers.lambdaQuery(R8DiscreditMainExecutor.class).eq(R8DiscreditMainExecutor::getDiscreditMainId,discreditMainId));
        loanService.remove(Wrappers.lambdaQuery(R8DiscreditMainLoan.class).eq(R8DiscreditMainLoan::getDiscreditMainId,discreditMainId));
        disexecutorService.remove(Wrappers.lambdaQuery(R8DiscreditMainDisexecutor.class).eq(R8DiscreditMainDisexecutor::getDiscreditMainId,discreditMainId));
        discreditMainService.removeById(discreditMainId);
        //后新增
        saveDiscreditMain(discreditDto);
    }

    public void updateGuaranteeDiscredit(R8DiscreditDto discreditDto){
        R8DiscreditGuaranteeDto guaranteeDiscredit = discreditDto.getGuaranteeDiscredit();
        Long guaranteeId = guaranteeDiscredit.getDiscreditGuaranteeId();
        //先删除
        guaranteeDetailService.remove(Wrappers.lambdaQuery(R8DiscreditGuaranteeDetail.class).eq(R8DiscreditGuaranteeDetail::getDiscreditGuaranteeId,guaranteeId));

        guaranteeService.removeById(guaranteeId);
        //后新增
        saveGuaranteeDiscredit(discreditDto);
    }

    public void updateRelationDiscredit(R8DiscreditDto discreditDto){
        R8DiscreditRelationDto relationDiscredit = discreditDto.getRelationDiscredit();
        Long relationId = relationDiscredit.getDiscreditRelationId();
        //先删除
        relationDetailService.remove(Wrappers.lambdaQuery(R8DiscreditRelationDetail.class).eq(R8DiscreditRelationDetail::getDiscreditRelationId,relationId));

        relationService.removeById(relationId);
        //后新增
        saveRelationDiscredit(discreditDto);
    }

    public void updateCustomDiscredit(R8DiscreditDto discreditDto){
        List<R8DiscreditCustom> customDiscredit = discreditDto.getCustomDiscredit();
        Long discreditId = discreditDto.getDiscreditId();
        //先删除
        customService.remove(Wrappers.lambdaQuery(R8DiscreditCustom.class).eq(R8DiscreditCustom::getDiscreditId, discreditId));
        //后新增
        saveCustomDiscredit(discreditDto);
    }

    public R8DiscreditMainDto buildDiscreditMain(Long discreditId){
        R8DiscreditMain r8DiscreditMain = discreditMainService.list(Wrappers.lambdaQuery(R8DiscreditMain.class).eq(R8DiscreditMain::getDiscreditId, discreditId)).stream().findFirst().orElse(new R8DiscreditMain());
        Long discreditMainId = r8DiscreditMain.getDiscreditMainId();
        List<R8DiscreditMainLoan> discreditMainLoanList = loanService.list(Wrappers.lambdaQuery(R8DiscreditMainLoan.class).eq(R8DiscreditMainLoan::getDiscreditMainId, discreditMainId));
        List<R8DiscreditMainCredit> creditList = creditService.list(Wrappers.lambdaQuery(R8DiscreditMainCredit.class).eq(R8DiscreditMainCredit::getDiscreditMainId, discreditMainId));
        List<R8DiscreditMainExecutor> executorList = executorService.list(Wrappers.lambdaQuery(R8DiscreditMainExecutor.class).eq(R8DiscreditMainExecutor::getDiscreditMainId, discreditMainId));
        List<R8DiscreditMainDisexecutor> disexecutorList = disexecutorService.list(Wrappers.lambdaQuery(R8DiscreditMainDisexecutor.class).eq(R8DiscreditMainDisexecutor::getDiscreditMainId, discreditMainId));
        R8DiscreditMainDto discreditMainDto = BeanCopyUtil.copyProperties(r8DiscreditMain, R8DiscreditMainDto::new);
        return discreditMainDto.setDefaultLoanList(discreditMainLoanList)
                .setDefaultCreditList(creditList)
                .setExecutorList(executorList)
                .setDiscreditExecutorList(disexecutorList);
    }
    public R8DiscreditGuaranteeDto buildGuaranteeDiscredit(Long discreditId){
        R8DiscreditGuarantee guarantee = guaranteeService.list(Wrappers.lambdaQuery(R8DiscreditGuarantee.class).eq(R8DiscreditGuarantee::getDiscreditId, discreditId)).stream().findFirst().orElse(new R8DiscreditGuarantee());
        Long guaranteeId = guarantee.getDiscreditGuaranteeId();
        List<R8DiscreditGuaranteeDetail> guaranteeDetailList = guaranteeDetailService.list(Wrappers.lambdaQuery(R8DiscreditGuaranteeDetail.class).eq(R8DiscreditGuaranteeDetail::getDiscreditGuaranteeId, guaranteeId));
        R8DiscreditGuaranteeDto r8DiscreditGuaranteeDto = BeanCopyUtil.copyProperties(guarantee, R8DiscreditGuaranteeDto::new);
        return r8DiscreditGuaranteeDto.setGuaranteeDiscreditList(guaranteeDetailList);
    }
    public R8DiscreditRelationDto buildRelationDiscredit(Long discreditId){
        R8DiscreditRelation relation = relationService.list(Wrappers.lambdaQuery(R8DiscreditRelation.class).eq(R8DiscreditRelation::getDiscreditId, discreditId)).stream().findFirst().orElse(new R8DiscreditRelation());
        Long relationId = relation.getDiscreditRelationId();
        List<R8DiscreditRelationDetail> relationDetailList = relationDetailService.list(Wrappers.lambdaQuery(R8DiscreditRelationDetail.class).eq(R8DiscreditRelationDetail::getDiscreditRelationId, relationId));
        R8DiscreditRelationDto relationDto = BeanCopyUtil.copyProperties(relation, R8DiscreditRelationDto::new);
        return relationDto.setRelationDiscreditList(relationDetailList);
    }

    public List<R8DiscreditCustom> buildCustomDiscredit(Long discreditId){
        return customService.list(Wrappers.lambdaQuery(R8DiscreditCustom.class).eq(R8DiscreditCustom::getDiscreditId,discreditId));
    }


    public void saveOrUpdateRiskR8(JSONObject riskInfo, Long companyId){
        log.info("saveOrUpdateRiskR8,参数：{}",riskInfo);
        Objects.nonNull(companyId);
        CompanyInfo companyInfo = companyInfoService.getByCompanyId(companyId);

        R8DiscreditDto discreditDto = JSON.toJavaObject(riskInfo, R8DiscreditDto.class);

        if(Objects.nonNull(discreditDto)){

            //2、根据供应商id，查询，是保存，还是更新
            R8Discredit discredit = this.getOne(Wrappers.lambdaQuery(R8Discredit.class).eq( R8Discredit::getVendorId,companyInfo.getCompanyId()));

            if(Objects.nonNull(discredit) && Objects.nonNull(discredit.getDiscreditId())){
                updateDiscreditInfo(discreditDto,discredit.getDiscreditId());
            }else {
                discreditDto.setVendorId(companyInfo.getCompanyId())
                        .setVendorName(companyInfo.getCompanyName())
                        .setVendorCode(companyInfo.getCompanyCode());
                saveDiscreditInfo(discreditDto);
            }
        }
    }

}

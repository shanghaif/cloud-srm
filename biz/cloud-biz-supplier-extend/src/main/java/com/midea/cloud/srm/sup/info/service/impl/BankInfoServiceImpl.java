package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.BankInfoMapper;
import com.midea.cloud.srm.sup.info.service.IBankInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
*  <pre>
 *  联系人信息 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 15:47:12
 *  修改内容:
 * </pre>
*/
@Service
public class BankInfoServiceImpl extends ServiceImpl<BankInfoMapper, BankInfo> implements IBankInfoService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateBank(BankInfo bankInfo, Long companyId) {
        bankInfo.setCompanyId(companyId);
        if(bankInfo.getBankInfoId() != null){
            bankInfo.setLastUpdateDate(new Date());
        }else{
            bankInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            bankInfo.setBankInfoId(id);
        }
        this.saveOrUpdate(bankInfo);
        if(null != bankInfo.getDimFieldContexts() && !bankInfo.getDimFieldContexts().isEmpty()){
            dimFieldContextService.saveOrUpdateList(bankInfo.getDimFieldContexts(),bankInfo.getBankInfoId(),companyId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateBank(BankInfo bankInfo) {
        if (bankInfo.getBankInfoId() != null){
            bankInfo.setLastUpdateDate(new Date());
            this.updateById(bankInfo);
        }else{
            bankInfo.setCreationDate(new Date())
                    .setLastUpdateDate(new Date());
            Long id = IdGenrator.generate();
            bankInfo.setBankInfoId(id);
            this.save(bankInfo);
        }
    }

    @Override
    public List<BankInfo> getByCompanyId(Long companyId) {
        QueryWrapper<BankInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        List<BankInfo> bankInfos = companyId != null?this.list(wrapper):null;
//        ceea,隆基不需要
//        if(!CollectionUtils.isEmpty(bankInfos)) {
//            for(BankInfo bankInfo:bankInfos){
//                Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(bankInfo.getBankInfoId());
//                bankInfo.setDimFieldContexts(dimFieldContexts);
//            }
//        }
        return bankInfos;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<BankInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID",companyId);
        this.remove(wrapper);
    }

    @Override
    public BankInfo getBankInfoByParm(BankInfo bankInfo) {
        QueryWrapper<BankInfo> queryWrapper = new QueryWrapper<>(bankInfo);
        List<BankInfo> bankInfos = this.list(queryWrapper);
        BankInfo bankInfoEntity = null;
        if (!CollectionUtils.isEmpty(bankInfos)) {
            bankInfoEntity = bankInfos.get(0);
        }
        return bankInfoEntity;
    }

    @Override
    public List<BankInfo> getBankInfosByParam(BankInfo bankInfo) {
        QueryWrapper<BankInfo> queryWrapper = new QueryWrapper<>(bankInfo);
        List<BankInfo> bankInfos = this.list(queryWrapper);
        return CollectionUtils.isNotEmpty(bankInfos)?bankInfos:null;
    }

    @Override
    public PageInfo<BankInfo> listPageByParam(BankInfo bankInfo) {
        int pageSize = 80;  //前端没有传分页，后端赋值，省去联调时间2020年11月22日
        PageUtil.startPage(bankInfo.getPageNum(), pageSize);
        QueryWrapper<BankInfo> queryWrapper = new QueryWrapper<>(new BankInfo()
                .setCompanyId(bankInfo.getCompanyId()));
        queryWrapper.like(StringUtils.isNotBlank(bankInfo.getUnionCode()), "UNION_CODE", bankInfo.getUnionCode());
        queryWrapper.like(StringUtils.isNotBlank(bankInfo.getBankName()), "BANK_NAME", bankInfo.getBankName());
        queryWrapper.like(StringUtils.isNotBlank(bankInfo.getBankAccountName()), "BANK_ACCOUNT_NAME", bankInfo.getBankAccountName());
        queryWrapper.like(StringUtils.isNotBlank(bankInfo.getBankAccount()), "BANK_ACCOUNT", bankInfo.getBankAccount());
        queryWrapper.like( "CEEA_ENABLED", "Y");    //只显示启用的
        List<BankInfo> bankInfos = this.list(queryWrapper);

        //银行号有重复的问题，去重，只取第一个
        Set<String> bankAccountSet = new HashSet<>();
        List<BankInfo> afterData = bankInfos.stream().filter(v -> bankAccountSet.add(v.getBankAccount()))
                .collect(Collectors.toList());
        return new PageInfo<>(afterData);
    }
}

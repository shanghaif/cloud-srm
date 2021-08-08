package com.midea.cloud.srm.base.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.ListUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.organization.mapper.ErpBranchBankMapper;
import com.midea.cloud.srm.base.organization.service.IErpBranchBankService;
import com.midea.cloud.srm.model.base.organization.entity.ErpBranchBank;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
*  <pre>
 *  银行分行信息（隆基银行分行数据同步） 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-14 12:29:12
 *  修改内容:
 * </pre>
*/
@Service
public class ErpBranchBankServiceImpl extends ServiceImpl<ErpBranchBankMapper, ErpBranchBank> implements IErpBranchBankService {
    @Override
    public List<ErpBranchBank> listAll(List<ErpBranchBank> erpBranchBanks) {
        List<ErpBranchBank> erpBranchBankList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(erpBranchBanks)){
            erpBranchBanks = ListUtil.listDeduplication(erpBranchBanks,erpBranchBank -> erpBranchBank.getBankNum()+erpBranchBank.getBankName()+erpBranchBank.getBranchBankName());
            erpBranchBanks.forEach(erpBranchBank -> {
                if(StringUtil.notEmpty(erpBranchBank.getBankNum()) &&
                        StringUtil.notEmpty(erpBranchBank.getBankName()) &&
                        StringUtil.notEmpty(erpBranchBank.getBranchBankName())){
                    QueryWrapper<ErpBranchBank> queryWrapper = new QueryWrapper<>();
                    queryWrapper.select("BRANCH_BANK_ID","BANK_NUM","BANK_NAME","BRANCH_BANK_NAME");
                    queryWrapper.eq("BANK_NUM",erpBranchBank.getBankNum());
                    queryWrapper.eq("BANK_NAME",erpBranchBank.getBankName());
                    queryWrapper.eq("BRANCH_BANK_NAME",erpBranchBank.getBranchBankName());
                    List<ErpBranchBank> branchBanks = this.list(queryWrapper);
                    if(!CollectionUtils.isEmpty(branchBanks)){
                        ErpBranchBank branchBank = branchBanks.get(0);
                        erpBranchBankList.add(branchBank);
                    }
                }
            });
        }
        return erpBranchBankList;
    }

    @Override
    public Map<String, String> getUnionCodeByOpeningBanks(List<String> openingBanks) {
        List<ErpBranchBank> erpBranchBanks = this.list(Wrappers.lambdaQuery(ErpBranchBank.class)
                .select(ErpBranchBank::getBranchBankName, ErpBranchBank::getBranchBankNum)
                .in(ErpBranchBank::getBranchBankName, openingBanks));
        Map<String, String> map = erpBranchBanks.stream().collect(Collectors.toMap(ErpBranchBank::getBranchBankName, ErpBranchBank::getBranchBankNum, (key1, key2) -> key2));
        return map;
    }
}

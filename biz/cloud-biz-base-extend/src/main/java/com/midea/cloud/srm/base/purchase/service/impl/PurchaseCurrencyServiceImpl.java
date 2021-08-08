package com.midea.cloud.srm.base.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.configguide.service.IConfigGuideService;
import com.midea.cloud.srm.base.purchase.mapper.PurchaseCurrencyMapper;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCurrencyService;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
*  <pre>
 *  币种设置 服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-26 14:08:07
 *  修改内容:
 * </pre>
*/
@Service
public class PurchaseCurrencyServiceImpl extends ServiceImpl<PurchaseCurrencyMapper, PurchaseCurrency> implements IPurchaseCurrencyService {

    @Autowired
    PurchaseCurrencyMapper purchaseCurrencyMapper;

    @Autowired
    private IConfigGuideService iConfigGuideService;

    @Override
    public PageInfo<PurchaseCurrency> listPage(PurchaseCurrency purchaseCurrency) {
        PageUtil.startPage(purchaseCurrency.getPageNum(), purchaseCurrency.getPageSize());
        QueryWrapper<PurchaseCurrency> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtil.isEmpty(purchaseCurrency.getCurrencyName()), "CURRENCY_NAME", purchaseCurrency.getCurrencyName());
        queryWrapper.like(!StringUtil.isEmpty(purchaseCurrency.getCurrencyCode()), "CURRENCY_CODE", purchaseCurrency.getCurrencyCode());
        queryWrapper.like(!StringUtil.isEmpty(purchaseCurrency.getEnabled()), "ENABLED", purchaseCurrency.getEnabled());
        return new PageInfo<>(purchaseCurrencyMapper.selectList(queryWrapper));
    }

    @Override
    @Transactional
    public void saveOrUpdateCurrency(PurchaseCurrency purchaseCurrency) {
        if (purchaseCurrency != null) {
            checkBeforeSaveOrUpdate(purchaseCurrency);
            if (purchaseCurrency.getCurrencyId() == null) {
                Long id = IdGenrator.generate();
                purchaseCurrency.setCurrencyId(id);
            }
            try {
                this.saveOrUpdate(purchaseCurrency);
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    String errMsg = ((SQLIntegrityConstraintViolationException) cause).getMessage();
                    if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("INDEX_CURRENCY_LANGUAGE") != -1) {
                        throw new BaseException("币种编码重复");
                    }
                }
            }

        }
    }

    @Override
    public List<PurchaseCurrency> listEnableAndLanguage() {
        PurchaseCurrency purchaseCurrency = new PurchaseCurrency();
        purchaseCurrency.setLanguage(LocaleHandler.getLocaleKey());
        purchaseCurrency.setEnabled(YesOrNo.YES.getValue());
        QueryWrapper<PurchaseCurrency> queryWrapper = new QueryWrapper<>(purchaseCurrency);
        queryWrapper.orderByAsc("CURRENCY_SORT is null,CURRENCY_SORT");
        return this.list(queryWrapper);
    }

    @Override
    public List<PurchaseCurrency> listAllCurrencyForImport() {
        return purchaseCurrencyMapper.listAllCurrencyForImport();
    }


    private void checkBeforeSaveOrUpdate(PurchaseCurrency purchaseCurrency) {
        if (StringUtils.isBlank(purchaseCurrency.getCurrencyName())) {
            throw new BaseException("币种名称为空");
        }
        if (StringUtils.isBlank(purchaseCurrency.getCurrencyCode())) {
            throw new BaseException("币种编码为空");
        }
        if (purchaseCurrency.getDecimalPoint() == null) {
            throw new BaseException("小数点位数为空");
        }
        if (StringUtils.isBlank(purchaseCurrency.getLanguage())) {
            throw new BaseException("语言为空");
        }
        Integer currencySort = purchaseCurrency.getCurrencySort();
        String language = purchaseCurrency.getLanguage();
        if (currencySort != null) {
            QueryWrapper<PurchaseCurrency> queryWrapper = new QueryWrapper<>(new PurchaseCurrency().setLanguage(language));
            List<PurchaseCurrency> purchaseCurrencys = this.list(queryWrapper);
            if (!CollectionUtils.isEmpty(purchaseCurrencys)) {
                for (PurchaseCurrency currency : purchaseCurrencys) {
                    if (currency == null) continue;
                    if (purchaseCurrency.getCurrencyId()==null&&currencySort.equals(currency.getCurrencySort())) {
                        throw new BaseException("序号重复");
                    }else if(purchaseCurrency.getCurrencyId()!=null&&currencySort.equals(currency.getCurrencySort())
                            &&!purchaseCurrency.getCurrencyId().equals(currency.getCurrencyId())){
                        throw new BaseException("序号重复");
                    }
                }
            }
        }
    }
}

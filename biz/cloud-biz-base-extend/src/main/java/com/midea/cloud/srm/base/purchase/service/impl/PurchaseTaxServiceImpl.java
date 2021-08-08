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
import com.midea.cloud.srm.base.purchase.mapper.PurchaseTaxMapper;
import com.midea.cloud.srm.base.purchase.service.IPurchaseTaxService;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
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
 *  税率设置 服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-27 15:06:10
 *  修改内容:
 * </pre>
*/
@Service
public class PurchaseTaxServiceImpl extends ServiceImpl<PurchaseTaxMapper, PurchaseTax> implements IPurchaseTaxService {

    @Autowired
    PurchaseTaxMapper purchaseTaxMapper;

    @Override
    public PageInfo<PurchaseTax> listPage(PurchaseTax purchaseTax) {
        PageUtil.startPage(purchaseTax.getPageNum(), purchaseTax.getPageSize());
        QueryWrapper<PurchaseTax> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtil.isEmpty(purchaseTax.getTaxName()), "TAX_NAME", purchaseTax.getTaxName());
        queryWrapper.like(!StringUtil.isEmpty(purchaseTax.getTaxKey()), "TAX_KEY", purchaseTax.getTaxKey());
        queryWrapper.like(!StringUtil.isEmpty(purchaseTax.getEnabled()), "ENABLED", purchaseTax.getEnabled());
        queryWrapper.orderByAsc("TAX_SORT");
        return new PageInfo<>(purchaseTaxMapper.selectList(queryWrapper));
    }

    @Override
    public List<PurchaseTax> listEnableAndLanguage() {
        PurchaseTax purchaseTax = new PurchaseTax();
        purchaseTax.setLanguage(LocaleHandler.getLocaleKey());
        purchaseTax.setEnabled(YesOrNo.YES.getValue());
        QueryWrapper<PurchaseTax> queryWrapper = new QueryWrapper<>(purchaseTax);
        queryWrapper.orderByAsc("TAX_SORT");
        return this.list(queryWrapper);
    }

    @Override
    @Transactional
    public void saveOrUpdateTax(PurchaseTax purchaseTax) {
        if (purchaseTax != null) {
            checkBeforeSaveOrUpdate(purchaseTax);
            if (purchaseTax.getTaxId() == null) {
                Long id = IdGenrator.generate();
                purchaseTax.setTaxId(id);
            }
            try {
                this.saveOrUpdate(purchaseTax);
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    String errMsg = ((SQLIntegrityConstraintViolationException) cause).getMessage();
                    if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("INDEX_TAX_LANGUAGE") != -1) {
                        throw new BaseException("税率编码重复");
                    }
                }
            }
        }
    }

    private void checkBeforeSaveOrUpdate(PurchaseTax purchaseTax) {
        if (StringUtils.isBlank(purchaseTax.getTaxName())) {
            throw new BaseException("税率名称为空");
        }
        if (purchaseTax.getTaxCode() == null) {
            throw new BaseException("税率值为空");
        }
        if (StringUtils.isBlank(purchaseTax.getTaxKey())) {
            throw new BaseException("税率编码");
        }
        if (StringUtils.isBlank(purchaseTax.getLanguage())) {
            throw new BaseException("语言为空");
        }
        String taxKey = purchaseTax.getTaxKey();
        String language = purchaseTax.getLanguage();
        if (taxKey != null) {
            QueryWrapper<PurchaseTax> queryWrapper = new QueryWrapper<>(new PurchaseTax().setLanguage(language));
            List<PurchaseTax> purchaseTaxs = this.list(queryWrapper);
            if (!CollectionUtils.isEmpty(purchaseTaxs)) {
                for (PurchaseTax tax : purchaseTaxs) {
                    if (tax == null) continue;
                    if (purchaseTax.getTaxId()==null&&taxKey.equals(tax.getTaxKey())) {
                        throw new BaseException("税率编码重复");
                    }else if(purchaseTax.getTaxId()!=null&&taxKey.equals(tax.getTaxKey())
                            &&!purchaseTax.getTaxId().equals(tax.getTaxId())){
                        throw new BaseException("税率编码重复");
                    }
                }
            }
        }
    }
}

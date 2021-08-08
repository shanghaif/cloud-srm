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
import com.midea.cloud.srm.base.purchase.mapper.PurchaseUnitMapper;
import com.midea.cloud.srm.base.purchase.service.IPurchaseUnitService;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
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
 *  单位设置 服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-28 11:40:17
 *  修改内容:
 * </pre>
*/
@Service
public class PurchaseUnitServiceImpl extends ServiceImpl<PurchaseUnitMapper, PurchaseUnit> implements IPurchaseUnitService {

    @Autowired
    PurchaseUnitMapper purchaseUnitMapper;

    @Override
    public PageInfo<PurchaseUnit> listPage(PurchaseUnit purchaseUnit) {
        PageUtil.startPage(purchaseUnit.getPageNum(), purchaseUnit.getPageSize());
        QueryWrapper<PurchaseUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtil.isEmpty(purchaseUnit.getUnitName()), "UNIT_NAME", purchaseUnit.getUnitName());
        queryWrapper.like(!StringUtil.isEmpty(purchaseUnit.getUnitCode()), "UNIT_CODE", purchaseUnit.getUnitCode());
        queryWrapper.like(!StringUtil.isEmpty(purchaseUnit.getEnabled()), "ENABLED", purchaseUnit.getEnabled());
        return new PageInfo<>(purchaseUnitMapper.selectList(queryWrapper));
    }

    @Override
    public List<PurchaseUnit> listEnableAndLanguage() {
        PurchaseUnit purchaseUnit = new PurchaseUnit();
        purchaseUnit.setLanguage(LocaleHandler.getLocaleKey());
        purchaseUnit.setEnabled(YesOrNo.YES.getValue());
        QueryWrapper<PurchaseUnit> queryWrapper = new QueryWrapper<>(purchaseUnit);
        return this.list(queryWrapper);
    }

    @Override
    public List<PurchaseUnit> listPurchaseUnitByCodeList(List<String> purchaseUnitList) {
        QueryWrapper<PurchaseUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("UNIT_CODE", purchaseUnitList);
        return this.list(queryWrapper);
    }

    @Override
    @Transactional
    public void saveOrUpdateUnit(PurchaseUnit purchaseUnit) {
        if (purchaseUnit != null) {
            checkBeforeSaveOrUpdate(purchaseUnit);
            if (purchaseUnit.getUnitId() == null) {
                Long id = IdGenrator.generate();
                purchaseUnit.setUnitId(id);
            }
            try {
                this.saveOrUpdate(purchaseUnit);
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause instanceof SQLIntegrityConstraintViolationException) {
                    String errMsg = ((SQLIntegrityConstraintViolationException) cause).getMessage();
                    if (StringUtils.isNotBlank(errMsg) && errMsg.indexOf("INDEX_UNIT_LANGUAGE") != -1) {
                        throw new BaseException("单位编码重复");
                    }
                }
            }
        }
    }

    private void checkBeforeSaveOrUpdate(PurchaseUnit purchaseUnit) {
        if (StringUtils.isBlank(purchaseUnit.getUnitName())) {
            throw new BaseException("单位名称为空");
        }
        if (StringUtils.isBlank(purchaseUnit.getUnitCode())) {
            throw new BaseException("单位编码为空");
        }
        if (StringUtils.isBlank(purchaseUnit.getLanguage())) {
            throw new BaseException("语言为空");
        }
        Integer unitSort = purchaseUnit.getUnitSort();
        String language = purchaseUnit.getLanguage();
        if (unitSort != null) {
            QueryWrapper<PurchaseUnit> queryWrapper = new QueryWrapper<>(new PurchaseUnit().setLanguage(language));
            List<PurchaseUnit> purchaseUnits = this.list(queryWrapper);
            if (!CollectionUtils.isEmpty(purchaseUnits)) {
                for (PurchaseUnit unit : purchaseUnits) {
                    if (unit == null) continue;
                    if (purchaseUnit.getUnitId()==null&&unitSort.equals(unit.getUnitSort())) {
                        throw new BaseException("序号重复");
                    }else if(purchaseUnit.getUnitId()!=null&&unitSort.equals(unit.getUnitSort())
                            &&!purchaseUnit.getUnitId().equals(unit.getUnitId())){
                        throw new BaseException("序号重复");
                    }
                }
            }
        }
    }
}

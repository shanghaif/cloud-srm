package com.midea.cloud.srm.supauth.review.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteConfigVendor;
import com.midea.cloud.srm.supauth.review.mapper.SiteConfigVendorMapper;
import com.midea.cloud.srm.supauth.review.service.ISiteConfigVendorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;

/**
*  <pre>
 *  现场评审周期设置(按供应商) 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-29 16:40:00
 *  修改内容:
 * </pre>
*/
@Service
public class SiteConfigVendorServiceImpl extends ServiceImpl<SiteConfigVendorMapper, SiteConfigVendor> implements ISiteConfigVendorService {

    @Override
    public void saveOrUpdateSiteConfigVendor(SiteConfigVendor siteConfigVendor) {
        checkBeforeSaveOrUpdate(siteConfigVendor);
        if (siteConfigVendor.getConfigVendorId() == null) {
            long id = IdGenrator.generate();
            siteConfigVendor.setConfigVendorId(id);
            this.save(siteConfigVendor);
        } else {
            this.updateById(siteConfigVendor);
        }
    }

    @Override
    public PageInfo<SiteConfigVendor> listPageByParm(SiteConfigVendor siteConfigVendor) {
        PageUtil.startPage(siteConfigVendor.getPageNum(), siteConfigVendor.getPageSize());
        SiteConfigVendor siteConfigVendorEntity = new SiteConfigVendor();
        if (StringUtils.isNotBlank(siteConfigVendor.getSiteCycle())) {
            siteConfigVendorEntity.setSiteCycle(siteConfigVendor.getSiteCycle());
        }
        QueryWrapper<SiteConfigVendor> queryWrapper = new QueryWrapper<>(siteConfigVendorEntity);
        if (StringUtils.isNotBlank(siteConfigVendor.getEnabled()) &&
                YesOrNo.YES.getValue().equals(siteConfigVendor.getEnabled())) {
            queryWrapper.le("START_DATE", LocalDate.now());
            queryWrapper.and(wrapper -> wrapper.gt("END_DATE", LocalDate.now()).or().isNull("END_DATE"));
        }
        if (StringUtils.isNotBlank(siteConfigVendor.getEnabled())
                && YesOrNo.NO.getValue().equals(siteConfigVendor.getEnabled())) {
            queryWrapper.le("END_DATE", LocalDate.now());
        }
        queryWrapper.like(StringUtils.isNotBlank(siteConfigVendor.getVendorName()), "VENDOR_NAME", siteConfigVendor.getVendorName());
        queryWrapper.like(StringUtils.isNotBlank(siteConfigVendor.getSqePerson()), "SQE_PERSON", siteConfigVendor.getSqePerson());
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<>(this.list(queryWrapper));
    }

    private void checkBeforeSaveOrUpdate(SiteConfigVendor siteConfigVendor) {
        Assert.hasText(siteConfigVendor.getVendorName(), "供应商名称不能为空");
        Assert.hasText(siteConfigVendor.getSiteCycle(), "现场评审周期不能为空");
        Assert.hasText(siteConfigVendor.getSqePerson(), "SQE/负责人不能为空");
    }
}

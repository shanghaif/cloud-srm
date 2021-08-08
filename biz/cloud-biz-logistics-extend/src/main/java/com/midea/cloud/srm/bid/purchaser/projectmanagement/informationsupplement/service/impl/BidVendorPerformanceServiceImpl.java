package com.midea.cloud.srm.bid.purchaser.projectmanagement.informationsupplement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.mapper.VendorPerformanceMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.informationsupplement.service.IBidVendorPerformanceService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.VendorPerformance;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *  供应商绩效 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 20:55:27
 *  修改内容:
 * </pre>
 */
@Service
public class BidVendorPerformanceServiceImpl extends ServiceImpl<VendorPerformanceMapper, VendorPerformance> implements IBidVendorPerformanceService {

    @Autowired
    private IBidVendorService iBidVendorService;

    @Override
    public PageInfo<VendorPerformance> listPage(VendorPerformance vendorPerformance) {
        PageUtil.startPage(vendorPerformance.getPageNum(), vendorPerformance.getPageSize());
        QueryWrapper<VendorPerformance> wrapper = new QueryWrapper<VendorPerformance>();

        wrapper.like(StringUtils.isNotBlank(vendorPerformance.getOrgCode()), "ORG_CODE",
                vendorPerformance.getOrgCode());
        wrapper.like(StringUtils.isNotBlank(vendorPerformance.getOrgName()), "ORG_NAME",
                vendorPerformance.getOrgName());
        wrapper.like(StringUtils.isNotBlank(vendorPerformance.getBidVendorCode()), "BID_VENDOR_CODE",
                vendorPerformance.getBidVendorCode());
        wrapper.like(StringUtils.isNotBlank(vendorPerformance.getBidVendorName()), "BID_VENDOR_NAME",
                vendorPerformance.getBidVendorName());
        wrapper.eq(vendorPerformance.getBidingId() != null, "BIDING_ID", vendorPerformance.getBidingId());

        return new PageInfo<VendorPerformance>(this.list(wrapper));

    }

    @Override
    @Transactional
    public void addBatch(List<VendorPerformance> vendorPerformanceList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(vendorPerformanceList), "供应商绩效列表不能为空");

        VendorPerformance performance = new VendorPerformance();
        performance.setBidingId(vendorPerformanceList.get(0).getBidingId());
        QueryWrapper<VendorPerformance> queryWrapper = new QueryWrapper<VendorPerformance>(performance);
        List<VendorPerformance> oldLineList = this.list(queryWrapper);

        List<String> checkUniqueList = new ArrayList<>();
        boolean oldLineListIsNotEmpty = CollectionUtils.isNotEmpty(oldLineList);
        if(oldLineListIsNotEmpty) {
            for (VendorPerformance oldLine : oldLineList) {
                StringBuilder sb = new StringBuilder();
                sb.append(oldLine.getOrgId()).append(oldLine.getCategoryId()).append(oldLine.getBidVendorId());
                checkUniqueList.add(sb.toString());
            }
        }

        for (VendorPerformance vendorPerformance : vendorPerformanceList) {

            BidVendor bidVendor = iBidVendorService.getOne(new QueryWrapper<>(new BidVendor().
                    setVendorId(vendorPerformance.getBidVendorId()).setBidingId(vendorPerformance.getBidingId()).setJoinFlag("Y")));
            if (bidVendor == null) {
                throw new BaseException(LocaleHandler.getLocaleMsg("未找到该供应商信息,编号", vendorPerformance.getBidVendorCode()));
            }
            Long id = IdGenrator.generate();
            //此处前端传入的bidVendorId实际为vendorId,修改为bidVendorId
            vendorPerformance.setPerformanceId(id).setBidVendorId(bidVendor.getBidVendorId());

            //采购组织+品类+供应商识别唯一标识
            StringBuilder sb = new StringBuilder();
            sb.append(vendorPerformance.getOrgId()).append(vendorPerformance.getCategoryId()).append(bidVendor.getBidVendorId());
            if (checkUniqueList.contains(sb.toString())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("供应商绩效信息重复,请检查"));
            }
        }
        this.saveBatch(vendorPerformanceList);
    }

    @Override
    @Transactional
    public void updateBatch(List<VendorPerformance> vendorPerformanceList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(vendorPerformanceList), "供应商绩效列表不能为空");

        VendorPerformance vendorPerformance = new VendorPerformance();
        vendorPerformance.setBidingId(vendorPerformanceList.get(0).getBidingId());
        QueryWrapper<VendorPerformance> queryWrapper = new QueryWrapper<VendorPerformance>(vendorPerformance);
        List<VendorPerformance> oldLineList = this.list(queryWrapper);
        List<Long> oldLineIdList = oldLineList.stream().map(VendorPerformance::getPerformanceId).collect(Collectors.toList());
        List<Long> newLineIdList = new ArrayList<>();
        List<StringBuilder> checkUniqueList = new ArrayList<>();
        boolean oldLineListIsNotEmpty = CollectionUtils.isNotEmpty(oldLineList);

        if(oldLineListIsNotEmpty) {
            for (VendorPerformance oldLine : oldLineList) {
                StringBuilder sb = new StringBuilder();
                sb.append(oldLine.getOrgId()).append(oldLine.getCategoryId()).append(oldLine.getBidVendorId());
                checkUniqueList.add(sb);
            }
        }

        for (VendorPerformance performance : vendorPerformanceList) {
            BidVendor bidVendor = iBidVendorService.getOne(new QueryWrapper<>(new BidVendor().
                    setVendorId(performance.getBidVendorId()).setBidingId(performance.getBidingId()).setJoinFlag("Y")));
            if (bidVendor == null) {
                throw new BaseException(LocaleHandler.getLocaleMsg("未找到该供应商信息,编号", performance.getBidVendorCode()));
            }
            Long performanceId = performance.getPerformanceId();
            //新增
            if (performanceId == null) {
                Long id = IdGenrator.generate();
                //此处前端传入的bidVendorId实际为vendorId,修改为bidVendorId
                performance.setPerformanceId(id).setBidVendorId(bidVendor.getBidVendorId());
                //采购组织+品类+供应商识别唯一标识
                StringBuilder sb = new StringBuilder();
                sb.append(performance.getOrgId()).append(performance.getCategoryId()).append(bidVendor.getBidVendorId());
                if (checkUniqueList.contains(sb)) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("供应商绩效信息重复,请检查"));
                }
                this.save(performance);
            } else {
                newLineIdList.add(performanceId);
                //采购组织+品类+供应商识别唯一标识
                StringBuilder sb = new StringBuilder();
                sb.append(performance.getOrgId()).append(performance.getCategoryId()).append(bidVendor.getBidVendorId());
                if (checkUniqueList.contains(sb)) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("供应商绩效信息重复,请检查"));
                }
                //此处前端传入的bidVendorId实际为vendorId,修改为bidVendorId
                performance.setBidVendorId(bidVendor.getBidVendorId());
                //更新
                this.updateById(performance);
            }
        }
        //删除
        for (Long oldId : oldLineIdList) {
            if (!newLineIdList.contains(oldId)) {
                this.removeById(oldId);
            }
        }
    }

}

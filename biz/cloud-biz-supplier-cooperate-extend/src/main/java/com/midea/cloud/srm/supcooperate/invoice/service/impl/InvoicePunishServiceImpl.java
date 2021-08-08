package com.midea.cloud.srm.supcooperate.invoice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.VendorAssesFormStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.sup.VendorImportStatus;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.perf.PerformanceClient;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.perf.vendorasses.dto.VendorAssesFormOV;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoicePunishQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoicePunish;
import com.midea.cloud.srm.supcooperate.invoice.mapper.InvoicePunishMapper;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoicePunishService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*  <pre>
 *  扣罚&派利明细 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-22 17:39:56
 *  修改内容:
 * </pre>
*/
@Service
public class InvoicePunishServiceImpl extends ServiceImpl<InvoicePunishMapper, InvoicePunish> implements IInvoicePunishService {

    @Autowired
    private PerformanceClient performanceClient;

    @Override
    public PageInfo listPageByParam(InvoicePunishQueryDTO invoicePunishQueryDTO) {
        //非服务类
        if (YesOrNo.NO.getValue().equals(invoicePunishQueryDTO.getIsService())) {
            PageUtil.startPage(invoicePunishQueryDTO.getPageNum(), invoicePunishQueryDTO.getPageSize());
            QueryWrapper<InvoicePunish> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(CollectionUtils.isNotEmpty(invoicePunishQueryDTO.getInvoiceNoticeIds()), "INVOICE_NOTICE_ID", invoicePunishQueryDTO.getInvoiceNoticeIds());
            queryWrapper.nested(q -> q.eq( "IF_QUOTE", YesOrNo.NO.getValue()).or().eq("IF_QUOTE", null));//过滤掉引用(Y)的
            List<InvoicePunish> invoicePunishes = this.list(queryWrapper);
            return new PageInfo<InvoicePunish>(invoicePunishes);
        }
        //服务类
        if (YesOrNo.YES.getValue().equals(invoicePunishQueryDTO.getIsService())) {
            VendorAssesForm vendorAssesForm = new VendorAssesForm();
            BeanUtils.copyProperties(invoicePunishQueryDTO, vendorAssesForm);
            PageInfo<VendorAssesFormOV> assesFormOVPageInfo = performanceClient.listPageForInvoice(vendorAssesForm);
            return assesFormOVPageInfo;
        }
        return new PageInfo();
    }

    @Override
    @Transactional
    public void bachDeleteInvoicePunishes(List<Long> invoicePunishIds) {
        this.removeByIds(invoicePunishIds);
        //修改供应商考核状态
        updateVendorAssesFormStatus(invoicePunishIds);
    }

    private void updateVendorAssesFormStatus(List<Long> invoicePunishIds) {
        if (CollectionUtils.isNotEmpty(invoicePunishIds)) {
            for (Long invoicePunishId : invoicePunishIds) {
                if (invoicePunishId == null) continue;
                InvoicePunish invoicePunish = this.getById(invoicePunishId);
                if (invoicePunish == null) continue;
                performanceClient.modify(new VendorAssesForm()
                        .setVendorAssesId(invoicePunish.getVendorAssesId())
                        .setIfQuote(YesOrNo.NO.getValue())
                        .setStatus(VendorAssesFormStatus.ASSESSED.getKey()));
            }
        }
    }
}

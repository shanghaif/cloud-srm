package com.midea.cloud.srm.perf.anon.controller;

import com.midea.cloud.common.enums.VendorAssesFormStatus;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.perf.vendorasses.service.IVendorAssesFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *  合同状态
 * </pre>
 *
 * @author ex_lizp6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-27 13:39
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/perf-anon")
public class PerfAnonController {
    @Autowired
    private IVendorAssesFormService iVendorAssesFormService;
    /**
     * 供应商考核单查询
     *
     * @param vendorAssesId
     * @return
     */
    @GetMapping("/vendorAsses/queryById")
    public VendorAssesForm queryById(@RequestParam Long vendorAssesId) {
        return iVendorAssesFormService.getById(vendorAssesId);
    }

    /**
     * 废弃订单
     * @param vendorAssesId
     */
    @GetMapping("/abandon")
    public void abandon(Long vendorAssesId) {
        Assert.notNull(vendorAssesId,"废弃订单id不能为空");
        iVendorAssesFormService.abandon(vendorAssesId);
    }


    /**
     * 绩效考核审批通过
     * @param vendorAssesForm
     */
    @PostMapping("/vendorAsses/VendorAssesFormPass")
    public void VendorAssesFormPass(@RequestBody VendorAssesForm vendorAssesForm) {
        Assert.isTrue(!ObjectUtils.isEmpty(vendorAssesForm),"处理对象为空。");
        vendorAssesForm.setStatus(VendorAssesFormStatus.REVIEWED.getKey());
        iVendorAssesFormService.updateById(vendorAssesForm);

    }
    /**
     * 绩效考核驳回
     * @param vendorAssesForm
     */
    @PostMapping("/vendorAsses/VendorAssesFormRejected")
    public void VendorAssesFormRejected(@RequestBody VendorAssesForm vendorAssesForm) {
        vendorAssesForm.setStatus(VendorAssesFormStatus.REJECTED.getKey());
        iVendorAssesFormService.updateById(vendorAssesForm);
    }
    /**
     * 绩效考核撤回
     * @param vendorAssesForm
     */
    @PostMapping("/vendorAsses/VendorAssesFormWithdraw")
    public void VendorAssesFormWithdraw(@RequestBody VendorAssesForm vendorAssesForm) {
        vendorAssesForm.setStatus(VendorAssesFormStatus.WITHDRAW.getKey());
        iVendorAssesFormService.updateById(vendorAssesForm);
    }

}

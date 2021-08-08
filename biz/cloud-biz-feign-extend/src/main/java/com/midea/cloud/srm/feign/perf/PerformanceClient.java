package com.midea.cloud.srm.feign.perf;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.feign.flow.WorkFlowFeignClient;
import com.midea.cloud.srm.feign.workflow.FlowBusinessCallbackClient;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.perf.vendorasses.dto.VendorAssesFormOV;
import com.midea.cloud.srm.model.supplier.info.dto.AssesFormDto;
import com.midea.cloud.srm.model.supplier.info.dto.ImproveFormDto;
import com.midea.cloud.srm.model.supplier.info.dto.PerfOverallScoreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <pre>
 *  绩效模块 内部调用Feign接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-23 17:30
 *  修改内容:
 * </pre>
 */
@FeignClient("cloud-biz-performance")
public interface PerformanceClient extends FlowBusinessCallbackClient {



    // xx功能 [功能简称] - >>>>>

    // xx功能 [功能简称] - <<<<<

    /**
     * 查询指定供应商的绩效评分信息
     * @param vendorId
     * @return
     */
    @PostMapping("/scoring/perfOverallScore/getPerfOverallScoreVendorId")
    List<PerfOverallScoreDto> getPerfOverallScoreVendorId(@RequestParam("vendorId") Long vendorId);

    /**
     * 查询供应商考核信息
     * @param vendorId
     * @return
     */
    @PostMapping("/vendorAsses/getAssesFormDtoByVendorId")
    List<AssesFormDto> getAssesFormDtoByVendorId(@RequestParam("vendorId") Long vendorId);

    /**
     * 查询自定供应商的改善信息
     * @param vendorId
     * @return
     */
    @PostMapping("/vendorImprove/getImproveFormDtoByVendorId")
    List<ImproveFormDto> getImproveFormDtoByVendorId(@RequestParam("vendorId") Long vendorId);

    /**
     * 修改(采购商/供应商)
     *
     * @param vendorAssesForm
     */
    @PostMapping("/vendorAsses/modify")
    void modify(@RequestBody VendorAssesForm vendorAssesForm);

    /**
     * 分页查询供应商考核单(结算用)
     * @param vendorAssesForm
     * @return
     */
    @PostMapping("/vendorAsses/listPageForInvoice")
    PageInfo<VendorAssesFormOV> listPageForInvoice(@RequestBody VendorAssesForm vendorAssesForm);

    /**
     * 供应商考核单查询
     *
     * @param vendorAssesId
     * @return
     */
    @GetMapping("/perf-anon/vendorAsses/queryById")
     VendorAssesForm queryById(@RequestParam("vendorAssesId") Long vendorAssesId);
    /**
     * 绩效考核审批通过
     * @param vendorAssesForm
     */
    @PostMapping("/perf-anon/vendorAsses/VendorAssesFormPass")
     void VendorAssesFormPass(@RequestBody VendorAssesForm vendorAssesForm);
    /**
     * 绩效考核驳回
     * @param vendorAssesForm
     */
    @PostMapping("/perf-anon/vendorAsses/VendorAssesFormRejected")
     void VendorAssesFormRejected(@RequestBody VendorAssesForm vendorAssesForm);
    /**
     * 绩效考核撤回
     * @param vendorAssesForm
     */
    @PostMapping("/perf-anon/vendorAsses/VendorAssesFormWithdraw")
     void VendorAssesFormWithdraw(@RequestBody VendorAssesForm vendorAssesForm);


    /**
     * 工作流回调入口，需要各个模块实现本模块下的各个功能的动态调用service
     */
    @PostMapping("/perf-anon/callbackFlow")
    @Override
    String callbackFlow(@RequestParam("serviceBean") String serviceBean, @RequestParam("callbackMethod") String callbackMethod, @RequestParam("businessId") Long businessId , @RequestParam("param") String param);

}
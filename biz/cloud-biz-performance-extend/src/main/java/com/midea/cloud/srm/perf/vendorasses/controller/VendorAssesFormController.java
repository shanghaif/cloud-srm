package com.midea.cloud.srm.perf.vendorasses.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.VendorAssesFormStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.perf.PerformanceClient;
import com.midea.cloud.srm.feign.workflow.FlowBusinessCallbackClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.perf.vendorasses.dto.VendorAssesFormOV;
import com.midea.cloud.srm.model.supplier.info.dto.AssesFormDto;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
import com.midea.cloud.srm.perf.vendorasses.service.IVendorAssesFormService;
import com.midea.cloud.srm.perf.vendorasses.utils.ExportUtils;
import com.midea.cloud.srm.perf.vendorasses.workflow.VendorAssesFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  供应商考核单表 前端控制器
 *
 *  采购发起审核,供应商确认--->已考核(ASSESSED)---->OA审批流
 *  采购发起审核,供应商反馈(填写反馈原因和信息)---->OA审批流
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:55:47
 *  修改内容:
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/vendorAsses")
public class VendorAssesFormController extends BaseController{

    @Autowired
    private IVendorAssesFormService iVendorAssesFormService;
    @Autowired
    private BaseClient baseClient;

    @Autowired
    private VendorAssesFlow vendorAssesFlow;

    /**
     * 供应商考核单分页查询
     *
     * @param vendorAssesForm
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<VendorAssesFormOV> listPage(@RequestBody VendorAssesForm vendorAssesForm) {
        return iVendorAssesFormService.listPage(vendorAssesForm);
    }

    /**
     * 查询供应商考核信息
     *
     * @param vendorId
     * @return
     */
    @PostMapping("/getAssesFormDtoByVendorId")
    public List<AssesFormDto> getAssesFormDtoByVendorId(@RequestParam("vendorId") Long vendorId) {
        return iVendorAssesFormService.getAssesFormDtoByVendorId(vendorId);
    }

    /**
     * 供应商考核单查询
     *
     * @param vendorAssesId
     * @return
     */
    @GetMapping("/queryById")
    public VendorAssesForm queryById(@RequestParam Long vendorAssesId) {
        return iVendorAssesFormService.getById(vendorAssesId);
    }

    /**
     * 新增考核单(采购商)
     *
     * @param vendorAssesForm
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody VendorAssesForm vendorAssesForm) {
        return iVendorAssesFormService.add(vendorAssesForm);
    }

    /**
     * 修改(采购商/供应商)
     *
     * @param vendorAssesForm
     */
    @PostMapping("/modify")
    public void modify(@RequestBody VendorAssesForm vendorAssesForm) {
        Assert.notNull(vendorAssesForm.getVendorAssesId(), "参数不能为空:vendorAssesId");
        iVendorAssesFormService.updateById(vendorAssesForm);
    }




    /**
     * 提交(采购商/供应商)
     *
     * @param vendorAssesForm
     */
    @PostMapping("/submitBatch")
    public void submitBatch(@RequestBody VendorAssesForm vendorAssesForm) {
        //单号不存在直接新增提交状态的订单
        if (vendorAssesForm.getVendorAssesId()==null) {
            vendorAssesForm.setVendorAssesId(IdGenrator.generate());
            //设置单号
            vendorAssesForm.setAssessmentNo(baseClient.seqGen(SequenceCodeConstant.SEQ_SCC_PERF_VENDOR_ASSES_FORM));
            vendorAssesForm.setStatus(VendorAssesFormStatus.SUBMITTED.getKey());
            iVendorAssesFormService.save(vendorAssesForm);
        }else {
            String status = vendorAssesForm.getStatus();
            if (VendorAssesFormStatus.DRAFT.getKey().equals(status) ||
                    VendorAssesFormStatus.REJECTED.getKey().equals(status) ||
                    VendorAssesFormStatus.WITHDRAW.getKey().equals(status)) {
                vendorAssesForm.setStatus(VendorAssesFormStatus.SUBMITTED.getKey());
                iVendorAssesFormService.updateById(vendorAssesForm);
            }else {
                Assert.isTrue(false,"拟定，驳回，审核撤回状态才可以提交。");
            }
        }

        String formId = null;
        try {
            formId = vendorAssesFlow.submitVendorAssesConfFlow(vendorAssesForm);
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
        if(StringUtils.isEmpty(formId)){
            throw new BaseException(LocaleHandler.getLocaleMsg("提交OA审批失败"));
        }
        //return vendorAssesForm.getVendorAssesId();
    }

    /**
     * 删除考核单(采购商)
     *
     * @param vendorAssesId 考核单id
     */

    @GetMapping("/delete")
    public void delete(Long vendorAssesId) {
        iVendorAssesFormService.delete(vendorAssesId);
    }

    /**
     * 通知供应商(采购商)
     *
     * @param vendorAssesForms
     */
    @PostMapping("/notifySupplier")
    public void notifySupplier(@RequestBody List<VendorAssesForm> vendorAssesForms) {
        iVendorAssesFormService.notifySupplier(vendorAssesForms);
    }

    /**
     * 供应商提交反馈信息
     *
     * @param vendorAssesForm
     */
    @PostMapping("/vendorFeedback")
    public void vendorBuyers(@RequestBody VendorAssesForm vendorAssesForm) {
        iVendorAssesFormService.vendorFeedback(vendorAssesForm);
    }

    /**
     * 供应商确认考核
     *
     * @param vendorAssesForm
     */
    @PostMapping("/vendorAffirm")
    public void vendorAffirm(@RequestBody VendorAssesForm vendorAssesForm) {
        iVendorAssesFormService.vendorAffirm(vendorAssesForm);

    }

    /**
     * 采购商处理
     *
     * @param vendorAssesForm
     */
    @PostMapping("/buyersProcess")
    public void buyersProcess(@RequestBody VendorAssesForm vendorAssesForm) {
        iVendorAssesFormService.buyersProcess(vendorAssesForm);
    }

    /**
     * 获取导出标题
     *
     * @return
     */
    @RequestMapping("/exportExcelTitle")
    public Map<String, String> exportExcelTitle() {
        return ExportUtils.getVendorAssesFormTitles();
    }

    /**
     * 导出文件
     *
     * @param vendorAssesFormDto
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody ExportExcelParam<VendorAssesForm> vendorAssesFormDto, HttpServletResponse response) throws Exception {
        iVendorAssesFormService.exportStart(vendorAssesFormDto, response);
    }

    /**
     * 导入文件模板下载
     *
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iVendorAssesFormService.importModelDownload(response);
    }

    /**
     * 导入文件
     *
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iVendorAssesFormService.importExcel(file, fileupload);
    }

    /**
     * 扣罚款明细查询
     *
     * @param vendorAssesForm
     * @return
     */
    @PostMapping("/queryAmerceInfo")
    public List<VendorAssesForm> queryAmerceInfo(@RequestBody VendorAssesForm vendorAssesForm) {
        return iVendorAssesFormService.queryAmerceInfo(vendorAssesForm);
    }

    @PostMapping("/submitWithFlow")
    public Map<String, Object> submit(@RequestBody VendorAssesForm assesForm) {
        //只有提交通过后才可以通知供应商，
        return iVendorAssesFormService.submitWithFlow(assesForm);
    }

    /**
     * 根据考核时间+指标维度+指标名称+评价结果-->建议考核金额
     * 获取对的建议考核金额
     *
     * @param assesForm
     * @return
     */
    @PostMapping("/getAssessmentPenalty")
    public BigDecimal getAssessmentPenalty(@RequestBody VendorAssesForm assesForm) {
        Assert.notNull(assesForm, "判断对象不能为空");
        Assert.notNull(assesForm.getAssessmentDate(), "审核时间不能为空");
        Assert.isTrue(!StringUtils.isEmpty(assesForm.getIndicatorDimension()), "指标维度不能为空");
        Assert.isTrue(!StringUtils.isEmpty(assesForm.getCeeaIndicatorLineDes()), "评选结果不能为空");
        return iVendorAssesFormService.getAssessmentPenalty(assesForm);
    }

    /**
     * 分页查询供应商考核单(结算用)
     *
     * @param vendorAssesForm
     * @return
     */
    @PostMapping("/listPageForInvoice")
    public PageInfo<VendorAssesFormOV> listPageForInvoice(@RequestBody VendorAssesForm vendorAssesForm) {
        return iVendorAssesFormService.listPageForInvoice(vendorAssesForm);
    }

}

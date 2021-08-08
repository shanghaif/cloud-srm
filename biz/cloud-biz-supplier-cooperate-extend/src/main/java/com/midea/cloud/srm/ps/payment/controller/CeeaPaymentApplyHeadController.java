package com.midea.cloud.srm.ps.payment.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.pm.ps.FSSCResponseCode;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplyDTO;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplyHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplySaveDTO;
import com.midea.cloud.srm.model.pm.ps.payment.entity.CeeaPaymentApplyHead;
import com.midea.cloud.srm.model.pm.ps.payment.vo.CeeaPaymentApplyVo;
import com.midea.cloud.srm.ps.payment.service.ICeeaPaymentApplyHeadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
*  <pre>
 *  付款申请-头表（隆基新增） 前端控制器
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 21:04:34
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/payment/paymentApplyHead")
@Slf4j
public class CeeaPaymentApplyHeadController extends BaseController {

    @Autowired
    private ICeeaPaymentApplyHeadService iCeeaPaymentApplyHeadService;

    @GetMapping("/getPaymentApply")
    public CeeaPaymentApplyDTO getPaymentApplyByPayPlanId(Long payPlanId){
        return iCeeaPaymentApplyHeadService.getPaymentApplyByPayPlanId(payPlanId);
    }

    @PostMapping("/listPage")
    public PageInfo<CeeaPaymentApplyHead> listPage(@RequestBody CeeaPaymentApplyHeadQueryDTO queryDTO){
        return iCeeaPaymentApplyHeadService.listPage(queryDTO);
    }

    /**
     * 保存付款申请
     * @param ceeaPaymentApplySaveDTO
     * @return
     */
    @PostMapping("/save")
    public Long savePaymentApply(@RequestBody CeeaPaymentApplySaveDTO ceeaPaymentApplySaveDTO){
        return iCeeaPaymentApplyHeadService.savePaymentApply(ceeaPaymentApplySaveDTO);
    }

    /**x`
     * 提交付款申请
     * @param ceeaPaymentApplySaveDTO
     */
    @PostMapping("/submit")
    @Transactional(rollbackFor = Exception.class)
    public BaseResult submitPaymentApply(@RequestBody CeeaPaymentApplySaveDTO ceeaPaymentApplySaveDTO){
    	Long id = iCeeaPaymentApplyHeadService.savePaymentApply(ceeaPaymentApplySaveDTO);
    	ceeaPaymentApplySaveDTO.getPaymentApplyHead().setPaymentApplyHeadId(id);
        FSSCResult fsscResult = iCeeaPaymentApplyHeadService.submitPaymentApply(ceeaPaymentApplySaveDTO);
        BaseResult baseResult = BaseResult.buildSuccess();
        if (FSSCResponseCode.WARN.getCode().equals(fsscResult.getCode())) {
            baseResult.setCode(fsscResult.getCode());
            baseResult.setMessage(fsscResult.getMsg());
            baseResult.setData(fsscResult.getType());
        }
        return baseResult;
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{paymentApplyHeadId}")
    public void delete(@PathVariable Long paymentApplyHeadId){
        iCeeaPaymentApplyHeadService.delete(paymentApplyHeadId);
    }

    /**
     * 查看详情
     */
    @GetMapping("/detail/{paymentApplyHeadId}")
    public CeeaPaymentApplyVo detail(@PathVariable Long paymentApplyHeadId){
        return iCeeaPaymentApplyHeadService.detail(paymentApplyHeadId);
    }

    /**
     * 作废
     * @param paymentApplyHeadId
     * @return
     */
    @GetMapping("/abandon")
    public BaseResult abandon(@RequestParam Long paymentApplyHeadId) {
        FSSCResult fsscResult = iCeeaPaymentApplyHeadService.abandon(paymentApplyHeadId);
        BaseResult baseResult = BaseResult.buildSuccess();
        if (StringUtils.isNotBlank(fsscResult.getCode()) && StringUtils.isNotBlank(fsscResult.getMsg())) {
            baseResult.setCode(fsscResult.getCode());
            baseResult.setMessage(fsscResult.getMsg());
        }
        return baseResult;
    }

    /**
     * <pre>
     *  付款申请导出
     * </pre>
     *
     * @author chenwt24@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: 2020-11-21
     *  修改内容:
     * </pre>
     */
    @PostMapping("/export")
    public void export(@RequestBody CeeaPaymentApplyHeadQueryDTO queryDTO,HttpServletResponse response) throws InterruptedException, ExecutionException, IOException {
        iCeeaPaymentApplyHeadService.export(queryDTO,response);
    }

}

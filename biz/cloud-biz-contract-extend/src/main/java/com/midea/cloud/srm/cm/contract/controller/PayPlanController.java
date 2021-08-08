package com.midea.cloud.srm.cm.contract.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.cm.contract.dto.ContractPayPlanDTO;
import com.midea.cloud.srm.model.cm.contract.dto.PayPlanRequestDTO;
import com.midea.cloud.srm.model.cm.contract.vo.PayPlanVo;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.cm.contract.service.IPayPlanService;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  合同付款计划 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:18:17
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/contract/payPlan")
public class PayPlanController extends BaseController {

    @Autowired
    private IPayPlanService iPayPlanService;


    /**
    * 批量删除
    * @param payPlanIds
    */
    @PostMapping ("/batchDelete")
    public void batchDelete(@RequestBody List<Long> payPlanIds) {
        if (!CollectionUtils.isEmpty(payPlanIds)) {
            iPayPlanService.removeByIds(payPlanIds);
        }
    }

    /**
     * 批量删除2.0
     * @param payPlanIds
     */
    @PostMapping ("/batchDeleteSecond")
    public void batchDeleteSecond(@RequestBody List<Long> payPlanIds) {
        iPayPlanService.batchDeleteSecond(payPlanIds);
    }

    /**
     * 分页条件查询合同付款计划(内部接口)
     * @param contractPayPlanDTO
     * @return
     */
    @PostMapping ("/listPageContractPayPlanDTO")
    public PageInfo<ContractPayPlanDTO> listPageContractPayPlanDTO(@RequestBody ContractPayPlanDTO contractPayPlanDTO) {
        return iPayPlanService.listPageContractPayPlanDTO(contractPayPlanDTO);
    }

    /**PayPlanRequestDTO
     * 分页查询付款计划(付款申请使用)
     */
    @PostMapping("/listPageForPaymentApply")
    public PageInfo<PayPlanVo> ceeaListPageForPaymentApply(@RequestBody PayPlanRequestDTO requestDTO){
        return iPayPlanService.ceeaListPageForPaymentApply(requestDTO);
    }

    /**
     * 发起付款申请
     * @param payPlanId
     */
    @GetMapping("/startPayApplication")
    public void startPayApplication(@RequestParam Long payPlanId) {
        iPayPlanService.startPayApplication(payPlanId);
    }

    /**
     * 批量修改
     * @param payPlanList
     */
    @PostMapping("/updateBatch")
    public void ceeaUpdateBatch(@RequestBody List<PayPlan> payPlanList){
        iPayPlanService.updateBatchById(payPlanList);
    }

    /**
     * 根据id获取合同付款计划
     * @return
     */
    @GetMapping("/getById")
    public PayPlan ceeaGetById(@RequestParam Long payPlanId){
        return iPayPlanService.getById(payPlanId);
    }

    /**
     * 根据条件获取付款计划
     */
    @PostMapping("/list")
    public List<PayPlan> list(@RequestBody PayPlan payPlan){
        return iPayPlanService.list(new QueryWrapper<>(payPlan));
    }
}

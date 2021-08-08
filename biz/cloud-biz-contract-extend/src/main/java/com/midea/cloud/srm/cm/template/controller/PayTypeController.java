package com.midea.cloud.srm.cm.template.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.cm.template.service.IPayTypeService;
import com.midea.cloud.srm.model.cm.template.dto.PayTypeDTO;
import com.midea.cloud.srm.model.cm.template.entity.PayType;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
*  <pre>
 *  合同付款类型 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-13 16:49:04
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/template/payType")
public class PayTypeController extends BaseController {

    @Autowired
    private IPayTypeService iPayTypeService;

    /**
    * 获取
    * @param payTypeId
    */
    @GetMapping("/get")
    public PayType get(Long payTypeId) {
        Assert.notNull(payTypeId, "payTypeId不能为空");
        return iPayTypeService.getById(payTypeId);
    }

    /**
    * 新增
    * @param payTypeDTO
    */
    @PostMapping("/add")
    public void add(@RequestBody PayTypeDTO payTypeDTO) {
        iPayTypeService.savePayTypeDTO(payTypeDTO);
    }

    /**
     * 修改
     * @param payTypeDTO
     */
    @PostMapping("/modify")
    public void modify(@RequestBody @Valid PayTypeDTO payTypeDTO) {
        iPayTypeService.updatePayTypeDTO(payTypeDTO);
    }

    /**
    * 删除
    * @param payTypeId
    */
    @GetMapping("/delete")
    public void delete(Long payTypeId) {
        Assert.notNull(payTypeId, "payTypeId不能为空");
        iPayTypeService.removeById(payTypeId);
    }

    /**
     * 分页条件查询
     * @param payType
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<PayTypeDTO> listPageByParm(@RequestBody PayType payType) {
        return iPayTypeService.listPageByParm(payType);
    }

    /**
     * 生效
     * @param payTypeId
     */
    @GetMapping("/effective")
    public void effective(Long payTypeId) {
        iPayTypeService.effective(payTypeId);
    }

    /**
     * 失效
     * @param payTypeId
     */
    @GetMapping("/invalid")
    public void invalid(Long payTypeId) {
        iPayTypeService.invalid(payTypeId);
    }

    /**
     * 付款条件维护新增
     */
    @PostMapping("/paymentTermsAdd")
    public Long paymentTermsAdd(@RequestBody PayType payType) {
        return iPayTypeService.paymentTermsAdd(payType);
    }

    /**
     * 付款条件维护新增
     */
    @PostMapping("/paymentTermsUpdate")
    public Long paymentTermsUpdate(@RequestBody PayType payType) {
        return iPayTypeService.paymentTermsUpdate(payType);
    }
    
    /**
     * 付款条件维护批量保存
     */
    @Transactional
    @PostMapping("/paymentTermsBatchSaveOrUpdate")
    public void paymentTermsBatchSaveOrUpdate(@RequestBody List<PayType> payTypes) {
    	if (null != payTypes && payTypes.size() > 0) {
    		for (PayType payType : payTypes) {
    			if (null != payType.getPayTypeId()) {
    				iPayTypeService.paymentTermsUpdate(payType);
    			} else {
    				iPayTypeService.paymentTermsAdd(payType);
    			}
    		}
    	}
        return ;
    }

    /**
     * 付款条件维护新增
     */
    @PostMapping("/paymentTermsPage")
    public PageInfo<PayType> paymentTermsPage(@RequestBody PayType payType) {
        return iPayTypeService.paymentTermsPage(payType);
    }

    /**
     * 付款条件维护新增
     */
    @GetMapping ("/getActivationPaymentTerms")
    public List<PayType> getActivationPaymentTerms() {
        return iPayTypeService.getActivationPaymentTerms();
    }

}

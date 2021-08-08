package com.midea.cloud.srm.feign.pm;

import com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.model.pm.pr.documents.param.RemoveParam;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.DeliveryPlanReportRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementHeadQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.param.FollowNameParam;
import com.midea.cloud.srm.model.pm.pr.requirement.param.OrderQuantityParam;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementApplyRejectVO;
import com.midea.cloud.srm.model.pm.pr.shopcart.entity.ShopCart;
import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.http.PaymentApplyDto;
import com.midea.cloud.srm.model.pm.ps.payment.dto.CeeaPaymentApplyDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentApplyHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentApplySaveDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * <pre>
 *  采购管理模块 内部调用Feign接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-11 18:59
 *  修改内容:
 *          </pre>
 */
@FeignClient(name = "cloud-biz-supplier-cooperate", contextId = "cloud-biz-pm-merge")
public interface PmClient {

    /**
     * 根据用户ID查找品类分工规则
     * @param userId
     * @return
     */
    @GetMapping("/division/divisionCategory/queryCategoryIdByUserId")
    List<Long> queryCategoryIdByUserId(@RequestParam("userId") Long userId);

    /**
     * 根据采购申请物料行id(requirementLineId)查询采购申请物料(requirementLine)
     */
    @GetMapping("/pr/requirementLine/get")
    RequirementLine getRequirementLineById(@RequestParam("id") Long id);

    /**
     * 根据requirementHeadId查询 PurchaseRequirementDTO
     *
     * @return
     */
    @GetMapping("/pr/requirementHead/getByHeadId")
    PurchaseRequirementDTO getPurchaseRequirementDTOByHeadId(@RequestParam("requirementHeadId") Long requirementHeadId);

    /**
     * 根据采购申请物料行id(requirementLineId)查询采购申请物料(requirementLine)
     */
    @PostMapping("/pr/requirementLine/modify")
    void modify(@RequestBody RequirementLine requirementLine);

    /**
     * 发布招标单修改采购需求行外部单据名称
     *
     * @param followNameParam
     */
    @PostMapping("/pr/requirementLine/updateIfExistRequirementLine")
    void updateIfExistRequirementLine(@RequestBody FollowNameParam followNameParam);

    /**
     * 批量调整可下单数量
     *
     * @param orderQuantityParamList
     * @return
     */
    @PostMapping("/pr/requirementLine/updateOrderQuantityBatch")
    void updateOrderQuantityBatch(@RequestBody List<OrderQuantityParam> orderQuantityParamList);

    /**
     * 根据参数获取付款申请头行
     *
     * @param paymentApplyHeadQueryDTO
     * @return
     */
    @PostMapping("ps/paymentApply/getPaymentApplyByParm")
    PaymentApplySaveDTO getPaymentApplyByParm(@RequestBody PaymentApplyHeadQueryDTO paymentApplyHeadQueryDTO);

    /**
     * 保存付款申请
     *
     * @param paymentApplySaveDTO
     */
    @PostMapping("ps/paymentApply/savePaymentApply")
    void savePaymentApply(@RequestBody PaymentApplySaveDTO paymentApplySaveDTO);

    /**
     * 根据合同付款计划ID 获取付款申请DTO
     *
     * @param payPlanId
     * @return
     * @update xiexh12@meicloud.com
     */
    @GetMapping("/payment/paymentApplyHead/getPaymentApply")
    CeeaPaymentApplyDTO getPaymentApplyByPayPlanId(@RequestParam("payPlanId") Long payPlanId);

    /**
     * 提交网上开票(对接费控)
     *
     * @param paymentApplyDto
     * @return
     */
    @PostMapping("/fsscReq/submitOnlineInvoice")
    FSSCResult submitOnlineInvoice(@RequestBody PaymentApplyDto paymentApplyDto);

    /**
     * 作废(对接费控)
     *
     * @param fsscStatus
     * @return
     */
    @PostMapping("/fsscReq/abandon")
    FSSCResult fsscAbandon(@RequestBody FsscStatus fsscStatus);

//	/**
//	 * 设置网上开票是否引用标记
//	 * @param advanceApplyHeadId
//	 * @param ifQuote
//	 */
//	@PostMapping("/ps/advanceApplyHead/setQuote")
//	void setQuote(@RequestParam("advanceApplyHeadId") Long advanceApplyHeadId, @RequestParam("ifQuote") String ifQuote);

    /**
     * 供应商拒绝采购订单回写
     *
     * @param detailList
     */
    @PostMapping("/pr/requirementLine/orderReturn")
    void orderReturn(@RequestBody List<OrderDetail> detailList);

    /**
     * @return
     * @Description 查询购物车
     * @Param [shopCart]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 18:12
     **/
    @PostMapping("/pr/shopCart/ceeaListByShopCart")
    List<ShopCart> ceeaListByShopCart(@RequestBody ShopCart shopCart);

    /**
     * @return
     * @Description 加入购物车
     * @Param [material]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 18:12
     **/
    @PostMapping("/pr/shopCart/ceeaAddToShopCart")
    void ceeaAddToShopCart(@RequestBody MaterialQueryDTO material);

    /**
     * 获取采购申请头
     *
     * @param requirementHeadQueryDTO
     * @return
     */
    @GetMapping("/pr/requirementHead/getRequirementHeadByParam")
    RequirementHead getRequirementHeadByParam(@RequestBody RequirementHeadQueryDTO requirementHeadQueryDTO);

    /**
     * 内部调用-获取采购申请明细行
     *
     * @param requirementLineId
     * @return
     */
    @GetMapping("/pr-anon/requirementLine/getById")
    RequirementLine getRequirementLineByIdForAnon(@RequestParam("requirementLineId") Long requirementLineId);

    /**
     * 内部调用-获取采购申请
     *
     * @param requirementHeadId
     * @return
     */
    @GetMapping({"/pr-anon/requirementHead/getByHeadId"})
    PurchaseRequirementDTO getPurchaseRequirementDTOByHeadIdForAnon(@RequestParam("requirementHeadId") Long requirementHeadId);

    /**
     * 根据采购申请筛选采购申请
     *
     * @param requirementHead
     * @return
     */
    @PostMapping("/pr/requirementHead/getRequirementHeadByParam")
    RequirementHead queryByRequirementHead(@RequestBody RequirementHead requirementHead);

    /**
     * 删除与单据行相关的id
     *
     * @param followFormId
     */
    @GetMapping("/documents/subsequentDocuments/deleteByFollowFormId")
    Boolean deleteByRequirementLineId(@RequestParam("followFormId") Long followFormId);

    @PostMapping("/pr-anon/deleletByRowNumAndFolloFormId")
    Boolean deleletByRowNumAndFolloFormId(@RequestBody RemoveParam param);


    @PostMapping("/pr/requirementHead/getRequirementHeadByNumber")
    List<RequirementHead> getRequirementHeadByNumber(@RequestBody Collection<String> numbers);


    /**
     * 内部调用-获取采购申请
     *
     * @param requirementHeadId
     * @return
     */
    @GetMapping({"/pr-anon/requirementHead/approval"})
    void approval(@RequestParam("requirementHeadId") Long requirementHeadId);


    /**
     * 内部调用-获取采购申请
     *
     * @param requirementApplyRejectVO
     * @return
     */
    @PostMapping({"/pr-anon/requirementHead/reject"})
    void reject(@RequestBody RequirementApplyRejectVO requirementApplyRejectVO);


    /**
     * 内部调用-获取采购申请
     *
     * @param requirementApplyRejectVO
     * @return
     */
    @PostMapping({"/pr-anon/requirementHead/withdraw"})
    void withdraw(@RequestBody RequirementApplyRejectVO requirementApplyRejectVO);


    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("/po-anon/queryOrderById")
    OrderSaveRequestDTO queryOrderById(@RequestParam("orderId") Long orderId);


    /**
     * 创建订单-审批
     */
    @PostMapping("/po-anon/approvalInEditStatus")
    void approvalInEditStatus(@RequestBody OrderSaveRequestDTO param);

    /**
     * 创建订单-驳回
     */
    @PostMapping("/po-anon/rejectInEditStatus")
    void rejectInEditStatus(@RequestBody OrderSaveRequestDTO param);


    /**
     * 编辑订单-撤回
     */
    @PostMapping("/po-anon/withdrawInEditStatus")
    void withdrawInEditStatus(@RequestBody OrderSaveRequestDTO param) throws Exception;


    /**
     * 根据条件获取品类分工信息
     *
     * @param divisionCategory
     * @return
     */
    @PostMapping("/pr-anon/loadDivisionCategoryByParam")
    List<DivisionCategory> loadDivisionCategoryByParam(@RequestBody DivisionCategory divisionCategory);

    @PostMapping("/pr-anon/getRequirementLineByIdsForAnon")
    List<RequirementLine> getRequirementLineByIdsForAnon(@RequestBody List<Long> requirementLineIds);

    @PostMapping("/pr-anon/getRequirementHeadByIdsForAnon")
    List<RequirementHead> getRequirementHeadByIdsForAnon(@RequestBody List<Long> requirementHeadIds);

    /**
     * Description 到货计划报表导出订单信息查询
     * @Param 
     * @return 
     * @Author wuwl18@meicloud.com
     * @Date 2020.12.14
     * @throws 
     **/
    @PostMapping("/pr-anon/findDeliveryPlanReportRequirement")
    List<DeliveryPlanReportRequirementDTO> findDeliveryPlanReportRequirement(@RequestBody List<Long> requirementLineIds);
}

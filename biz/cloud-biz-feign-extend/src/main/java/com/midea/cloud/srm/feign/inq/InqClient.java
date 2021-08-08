package com.midea.cloud.srm.feign.inq;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.feign.flow.WorkFlowFeignClient;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaParamDto;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.inq.inquiry.dto.InquiryHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
import com.midea.cloud.srm.model.inq.price.dto.*;
import com.midea.cloud.srm.model.inq.price.entity.*;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalAllVo;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateParam;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateParameter;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateResult;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.GenerateSourcingResultReportParameter;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.SourcingResultReport;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.supplier.info.dto.BidFrequency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 询价模块 内部调用Feign接口
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-14 9:25
 *  修改内容:
 *          </pre>
 */
@FeignClient("cloud-biz-inquiry")
public interface InqClient extends WorkFlowFeignClient {

    /**
     * 生成价格审批单
     *
     * @param insertPriceApprovalDTO
     */
    @PostMapping("/price/approval/insertPriceApproval")
    void insertPriceApproval(@RequestBody InsertPriceApprovalDTO insertPriceApprovalDTO);

    /**
     * 待报价数量
     */
    @GetMapping("/inquiry/vendor/waitQuote")
    WorkCount waitQuoteCount();

    /**
     * 查询净价
     *
     * @param netPriceQueryDTO
     * @return
     */
    @PostMapping("/price/approval/getApprovalNetPrice")
    BigDecimal getApprovalNetPrice(NetPriceQueryDTO netPriceQueryDTO);

    /**
     * 根据条件 查询价格目录
     */
    @PostMapping("/price/priceLibrary/getPriceLibraryByParam")
    PriceLibrary getPriceLibraryByParam(@RequestBody NetPriceQueryDTO netPriceQueryDTO);

    @PostMapping("/price/priceLibrary/getOnePriceLibrary")
    PriceLibrary getOnePriceLibrary(@RequestBody PriceLibrary priceLibrary);

    /**
     * 根据条件 批量查询价格目录列表
     */
    @PostMapping("/price/priceLibrary/listPriceLibraryByParam")
    List<PriceLibrary> listPriceLibraryByParam(@RequestBody List<NetPriceQueryDTO> netPriceQueryDTOList);

    /**
     * 根据条件 查询价格目录列表
     */
    @PostMapping("/price/priceLibrary/listPriceLibrary")
    List<PriceLibrary> listPriceLibrary(@RequestBody NetPriceQueryDTO netPriceQueryDTO);

    /**
     * 分页查询
     *
     * @param priceLibrary
     * @return
     */
    @PostMapping("/price/priceLibrary/listPage")
    PageInfo<PriceLibrary> listPagePriceLibrary(@RequestBody PriceLibrary priceLibrary);

    /**
     * 采购需求生成询比价单
     */
    @PostMapping("/inquiry/header/requirementGenInquiry")
    String requirementGenInquiry(@RequestBody List<RequirementLine> requirementLine);

    /**
     * 分页查询
     *
     * @param header
     * @return
     */
    @PostMapping("/inquiry/header/listPage")
    PageInfo<Header> listPage(@RequestBody Header header);

    /**
     * 获取头信息
     */
    @GetMapping("/inquiry/header/getHeadById")
    InquiryHeaderDto getHeadById(@RequestParam("inquiryId") Long inquiryId);

    /**
     * 获取近三年供应商中标次数
     *
     * @param vendorId
     * @return
     */
    @PostMapping("/price/priceLibrary/getThreeYearsBidFrequency")
    List<BidFrequency> getThreeYearsBidFrequency(@RequestParam("vendorId") Long vendorId) throws ParseException;

    /**
     * 合同查找价格目录
     *
     * @param priceLibraryParam
     * @return
     */
    @PostMapping("/price/priceLibrary/queryByContract")
    List<PriceLibrary> queryByContract(@RequestBody PriceLibraryParam priceLibraryParam);

    /**
     * 查询某个业务实体下，某个库存组织下，某个物料 是否存在有效价格
     */
    @PostMapping("/price/priceLibrary/ifHasPrice")
    String ifHasPrice(@RequestBody PriceLibrary priceLibrary);

    /**
     * 获取价格目录信息;条件为空时返回空集合
     *
     * @param priceLibraries
     * @return
     */
    @PostMapping("/inq-anon/price/priceLibrary/listPriceLibrary")
    List<PriceLibrary> listPriceLibrary(@RequestBody List<PriceLibrary> priceLibraries);

    @PostMapping("/inq-anon/price/priceLibrary/listPriceLibraryWithPaymentTerm")
    List<PriceLibrary> listPriceLibraryWithPaymentTerm(@RequestBody Map<String, Object> paramMap);

    /**
     * 查询某个业务实体下，某个库存组织下，某个物料 的有效价格
     */
    @PostMapping("/price/priceLibrary/listEffectivePrice")
    List<PriceLibrary> listEffectivePrice(@RequestBody PriceLibrary priceLibrary);

    /**
     * 合同查找价格审批物料
     *
     * @return
     */
    @PostMapping("/price/approval/ceeaQueryByCm")
    List<ApprovalBiddingItem> ceeaQueryByCm(@RequestBody ApprovalBiddingItemDto approvalBiddingItemDto);

    /**
     * 智能决标
     *
     * @param quotaCalculateParameter
     * @return
     */
    @PostMapping("/inquiry/quota/getCalculate")
    List<QuotaCalculateResult> getCalculate(@RequestBody QuotaCalculateParameter quotaCalculateParameter);

    @PostMapping("/inquiry/quotaBu/isSameQuotaInBuIds")
    Boolean isSameQuotaInBuIds(@RequestBody Collection<String> buIds);

    /**
     * 查询所有有效价格
     * 3+
     *
     * @return
     */
    @PostMapping("/price/priceLibrary/listAllEffective")
    List<PriceLibrary> listAllEffective(@RequestBody PriceLibrary priceLibrary);

    /**
     * 根据条件查询所有付款条款
     */
    @PostMapping("/inq/price-library-payment-term/list")
    List<PriceLibraryPaymentTerm> listPriceLibraryPaymentTerm(@RequestBody PriceLibraryPaymentTerm priceLibraryPaymentTerm);

    /**
     * 回写单据号接口
     *
     * @param itemVOS
     */
    @PostMapping("/price/approval/updateItemsBelongNumber")
    void updateApprovalBidingInfo(@RequestBody Collection<ApprovalBiddingItemVO> itemVOS);

    /**
     * 作废合同回写接口
     *
     * @param contractIds
     */
    @PostMapping("/price/approval/resetItemsBelongNumber")
    void resetItemsBelongNumber(@RequestBody Collection<Long> contractIds);

    /**
     * 根据业务实体id,物料id,查询最新的价格
     *
     * @param priceLibrary
     * @return
     */
    @PostMapping("/price/priceLibrary/getLatest")
    PriceLibrary getLatest(@RequestBody PriceLibrary priceLibrary);

    /**
     * 新的访问接口
     *
     * @param parameterList
     * @return
     */
    @PostMapping({"/inquiry/quota/getNewCalculate"})
    List<QuotaCalculateParam> calculate(@RequestBody List<QuotaCalculateParam> parameterList);

    /**
     * 获取寻源结果报表
     *
     * @param parameter
     * @return
     */
    @PostMapping("/inq-anon/price/sourcingResultReport/generate")
    SourcingResultReport generateForAnon(@RequestBody GenerateSourcingResultReportParameter parameter);

    @GetMapping("/inq-anon/price/sourcingResultReport/judegeIsFinishReport")
    InquiryHeaderReport judegeIsFinishReport(@RequestParam Long bidingId);

    @PostMapping("/inq-anon/price/priceLibrary/getLatestForAnon")
    PriceLibrary getLatestForAnon(@RequestBody PriceLibrary priceLibrary);

    @PostMapping("/inq-anon/price/priceLibrary/getLatestForAnonBatch")
    List<PriceLibrary> getLatestForAnonBatch(@RequestBody Collection<PriceLibrary> priceLibrary);

    /**
     * 审批通过
     */
    @PostMapping("/inq-anon/price/approval/auditPass")
    void auditPass(@RequestParam("approvalHeaderId") Long approvalHeaderId);

    /**
     * 审批驳回
     */
    @RequestMapping("/inq-anon/price/approval/reject")
    void reject(@RequestParam("approvalHeaderId") Long approvalHeaderId, @RequestParam("ceeaDrafterOpinion") String ceeaDrafterOpinion);

    /**
     * 审批撤回
     */
    @RequestMapping("/inq-anon/price/approval/withdraw")
    void withdraw(@RequestParam("approvalHeaderId") Long approvalHeaderId);

    /**
     * 获取价格审批详情
     */
    @GetMapping("/inq-anon/price/approval/approvalDetail")
    ApprovalAllVo ceeaGetApprovalDetail(@RequestParam("approvalHeaderId") Long approvalHeaderId);

    @PostMapping("/inq-anon/price/sourcingResultReport/callWhenFail")
    void callWhenFail(@RequestBody GenerateSourcingResultReportParameter parameter);

    /**
     * 根据物料编码,物料描述,供应商编码,已上架,有效,查询是否存在
     *
     * @param priceLibraryCheckDto
     * @return
     */
    @PostMapping("/price/priceLibrary/queryPriceLibraryByItemCodeNameVendorCode")
    List<String> queryPriceLibraryByItemCodeNameVendorCode(@RequestBody PriceLibraryCheckDto priceLibraryCheckDto);

    /**
     * 根据聚业务实体,库存组织查询
     *
     * @param
     * @return
     */
    @GetMapping("/price/priceLibrary/queryItemIdByOuAndInv")
    List<Long> queryItemIdByOuAndInv(@RequestParam("ouId") Long ouId, @RequestParam("invId") Long invId);


    @PostMapping("/inq-anon/price/priceLibrary/listPriceLibraryForTransferOrders")
    List<PriceLibrary> listPriceLibraryForTransferOrders(@RequestBody List<PriceLibrary> priceLibraryParams);

    /**
     * 根据寻源单号获取价格审批单（采购申请报表使用）
     *
     * @param subsequentDocumentsNo
     * @return
     */
    @PostMapping("/price/approval/listApprovalHeaderBysourceNo")
    List<ApprovalHeader> listApprovalHeaderBysourceNo(@RequestBody List<String> subsequentDocumentsNo);

    /**
     * 根据价格审批单id获取中标行(采购申请报表使用)
     *
     * @param approvalHeadIds
     * @return
     */
    @PostMapping("/price/approval-bidding-item/listApprovalBiddingItemByApprovalHeadIds")
    List<ApprovalBiddingItem> listApprovalBiddingItemByApprovalHeadIds(@RequestBody List<Long> approvalHeadIds);

    @GetMapping("/inq-anon/price/approval/status")
    String getPriceStatus(@RequestParam("sourcingNo") String sourcingNo);

    @GetMapping("/inq-anon/price/getPriceLibraryBelongTimeByIds")
    List<PriceLibrary> getPriceLibraryBelongTimeByIds(@RequestBody Collection<Long> sourcingIds);

    /**
     * 根据供应商_物料 检查是否存在有效价格
     * @param priceLibraryParamDto
     * @return
     */
    @PostMapping("/price/priceLibrary/queryValidByVendorItem")
    boolean queryValidByVendorItem(@RequestBody PriceLibraryParamDto priceLibraryParamDto);

    /**
     * 根据供应商_物料_日期 检查是否存在有效价格
     * @param priceLibraryParamDto
     * @return
     */
    @PostMapping("/price/priceLibrary/queryValidByVendorItemDate")
    boolean queryValidByVendorItemDate(@RequestBody PriceLibraryParamDto priceLibraryParamDto);

    /**
     * 根据批量供应商_物料_组织 检查是否存在有效价格
     *
     * @param quotaParamDto
     * @return
     */
    @PostMapping("/price/priceLibrary/queryValidByBatchVendorItem")
    boolean queryValidByBatchVendorItem(@RequestBody QuotaParamDto quotaParamDto);
}

package com.midea.cloud.srm.bid.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.bid.service.ILgtBidingService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.bid.dto.*;
import com.midea.cloud.srm.model.logistics.bid.entity.*;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidcontrol.vo.StartOrExtendBidingVO;
import com.midea.cloud.srm.model.logistics.bid.vo.CommercialBidVO;
import com.midea.cloud.srm.model.logistics.bid.vo.LgtBidInfoVO;
import com.midea.cloud.srm.model.logistics.bid.vo.TechBidVO;
import com.midea.cloud.srm.model.supplier.info.dto.VendorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
*  <pre>
 *  物流寻源基础信息表 前端控制器
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:54:37
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/biding")
public class LgtBidingController extends BaseController {

    @Autowired
    private ILgtBidingService iLgtBidingService;

    /**
     * 招投标项目管理-分页查询
     *
     * @param lgtBiding
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<LgtBiding> listPage(@RequestBody LgtBiding lgtBiding) {
        return iLgtBidingService.listPage(lgtBiding);
    }


    /**
     * 新增招标基础信息
     * @param lgtBidDto
     * @return
     */
    @PostMapping("/add")
    public Long add (@RequestBody LgtBidDto lgtBidDto){
        return iLgtBidingService.add(lgtBidDto);
    }

    /**
     * 更新招标基础信息
     * @param lgtBidDto
     * @return
     */
    @PostMapping("/modify")
    public Long modify (@RequestBody LgtBidDto lgtBidDto){
        return iLgtBidingService.modify(lgtBidDto);
    }

    /**
     * 获取物流招标详情
     * @param bidingId
     * @return
     */
    @GetMapping("/getLgtBidInfoVo")
    public LgtBidInfoDto getLgtBidInfoVo(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.getLgtBidInfoVo(bidingId);
    }

    /**
     * 获取物流招标需求行
     * @param bidingId
     * @return
     */
    @GetMapping("/getLgtBidRequirementLineByBidingId")
    public List<LgtBidRequirementLine> getLgtBidRequirementLineByBidingId(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.getLgtBidRequirementLineByBidingId(bidingId);
    }

    /**
     * 更新项目明细信息
     * @param lgtBidRequirementLines
     * @return
     */
    @PostMapping("/updateLgtBidRequirementLine")
    public List<LgtBidRequirementLine> updateLgtBidRequirementLine(@RequestBody List<LgtBidRequirementLine> lgtBidRequirementLines){
        return iLgtBidingService.updateLgtBidRequirementLine(lgtBidRequirementLines);
    }

    /**
     * 智能查找供应商
     * @param bidingId
     * @return
     */
    @GetMapping("/intelligentFindVendor")
    public List<VendorDto> intelligentFindVendor(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.intelligentFindVendor(bidingId);
    }

    /**
     * 获取邀请供应商信息
     * @param bidingId
     * @return
     */
    @GetMapping("/getLgtBidVendorByBidingId")
    public List<LgtBidVendor> getLgtBidVendorByBidingId(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.getLgtBidVendorByBidingId(bidingId);
    }

    /**
     *
     * 获取【立项审批状态为已审批】的招标单的邀请供应商信息
     * @param bidingId
     * @return
     */
    @GetMapping("/getLgtBidVendorByBidingIdAndStatus")
    public List<LgtBidVendor> getLgtBidVendorByBidingIdAndStatus(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.getLgtBidVendorByBidingIdAndStatus(bidingId);
    }

    /**
     * 根据邀请供应商获取报价权限
     * @param bidVendorId
     * @return
     */
    @GetMapping("/getLgtQuoteAuthorizeByBidVendorId")
    public List<LgtQuoteAuthorize> getLgtQuoteAuthorizeByBidVendorId(@RequestParam("bidingId") Long bidingId,@RequestParam("bidVendorId") Long bidVendorId){
        return iLgtBidingService.getLgtQuoteAuthorizeByBidVendorId(bidingId, bidVendorId);
    }

    /**
     * 保存邀请供应商信息
     * @param lgtBidVendors
     * @return
     */
    @PostMapping("/saveLgtBidVendor")
    public void saveLgtBidVendor(@RequestBody List<LgtBidVendor> lgtBidVendors){
        iLgtBidingService.saveLgtBidVendor(lgtBidVendors);
    }

    /**
     * 获取招标流程节点信息
     * @param bidingId
     */
    @GetMapping("/getNodeStatus")
    public List<LgtProcessNode> getNodeStatus(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.getNodeStatus(bidingId);
    }

    /**
     * 预览供应商
     * @param bidingId
     * @return
     */
    @GetMapping("/previewVendor")
    public List<LgtVendorQuotedDto> previewVendor(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.previewVendor(bidingId);
    }

    /**
     * 发布
     * @param bidingId
     */
    @GetMapping("/release")
    public void release(@RequestParam("bidingId") Long bidingId){
        iLgtBidingService.release(bidingId);
    }

    /**
     * 撤回
     * @param bidingId
     */
    @GetMapping("/withdraw")
    public void withdraw(@RequestParam("bidingId") Long bidingId,@RequestParam("reason") String reason){
        iLgtBidingService.withdraw(bidingId, reason);
    }

    /**
     * 删除
     * @param bidingIds
     */
    @PostMapping("/delete")
    public void delete(@RequestBody List<Long> bidingIds){
        iLgtBidingService.delete(bidingIds);
    }

    /**
     * 新一轮截止时间
     *
     * @param startOrExtendBidingVO
     */
    @PostMapping("/startBiding")
    public void startBiding(@RequestBody StartOrExtendBidingVO startOrExtendBidingVO) {
        iLgtBidingService.startBiding(startOrExtendBidingVO);
    }

    /**
     * 调整截止时间
     *
     * @param startOrExtendBidingVO
     */
    @PostMapping("/extendBiding")
    public void extendBiding(@RequestBody StartOrExtendBidingVO startOrExtendBidingVO){
        iLgtBidingService.extendBiding(startOrExtendBidingVO);
    }

    /**
     * 立即截止
     * @param bidingId
     */
    @GetMapping("/dueImmediately")
    public void dueImmediately(@RequestParam("bidingId") Long bidingId){
        iLgtBidingService.dueImmediately(bidingId);
    }


    /**
     * 技术标管理查看详情
     * @param bidingId
     * @return
     */
    @GetMapping("/detailTechBiding")
    public TechBidVO detailTechBiding(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.detailTechBiding(bidingId);

    }

    @GetMapping("/techOpenBiding")
    public void techOpenBiding(@RequestParam("bidingId")Long bidingId){
        iLgtBidingService.techOpenBiding(bidingId);
    }
    @GetMapping("/techOpenBusiness")
    public void techOpenBusiness(@RequestParam("bidingId")Long bidingId){
        iLgtBidingService.techOpenBusiness(bidingId);
    }

    /**
     * 商务标管理查看详情
     * @param bidingId
     * @return
     */
    @GetMapping("/detailCommercialBiding")
    public CommercialBidVO detailCommercialBiding(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.detailCommercialBiding(bidingId);
    }

    /**
     * 需求池转招标单
     * @param id
     * @return
     */
    @GetMapping("/requirementToBiding")
    public Long requirementToBiding(@RequestParam("id") Long id){
        return iLgtBidingService.requirementToBiding(id);
    }

    /**
     * 批量需求池转招标单
     * @param ids
     * @return
     */
    @PostMapping("/requirementToBidings")
    public List<Long> requirementToBidings(@RequestBody List<Long> ids){
        return iLgtBidingService.requirementToBidings(ids);
    }

    /**
     * 获取整个招标单详情,包括：
     * 1.招标项目基础信息
     * 2.招标项目附件信息
     * 3.供方必须上传附件信息
     * 4.需求明细表
     * 5.供应商报价信息
     *     (1)供应商报价头信息
     *     (2)供应商船期信息
     *     (3)供应商报名附件信息
     *     (4)供应商报价明细信息
     * 6.投标结果
     * 7.邀请供应商信息
     *     (1)邀请的供应商
     *     (2)报价权限
     *     (3)付款信息
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public LgtBidInfoVO detail(@RequestParam("id") Long id){
        return iLgtBidingService.detail(id);
    }

    /**
     * 投标控制获取数据
     * @param bidingId
     * @return
     */
    @GetMapping("/getTopInfo")
    public LgtVendorQuotedHeadVo getTopInfo(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.getTopInfo(bidingId);
    }

    /**
     * 评选界面获取信息
     * @param bidingId
     * @return
     */
    @GetMapping("/queryLgtVendorQuotedSumDto")
    public LgtVendorQuotedSumDto queryLgtVendorQuotedSumDto(
            @RequestParam("bidingId") Long bidingId,
            @RequestParam("round") Integer round,
            @RequestParam(name = "vendorId",required = false) Long vendorId,
            @RequestParam(name = "rank",required = false) Integer rank,
            @RequestParam(name = "bidResult",required = false) String bidResult
    ){
        return iLgtBidingService.queryLgtVendorQuotedSumDto(bidingId, round, vendorId,rank,bidResult);
    }

    /**
     * 评选报价明细行保存
     */
    @PostMapping("/selectionQuotedLineSave")
    public void selectionQuotedLineSave(@RequestBody LgtVendorQuotedLineDto lgtVendorQuotedLines){
        iLgtBidingService.selectionQuotedLineSaveNew(lgtVendorQuotedLines);
    }

    /**
     * 入围或淘汰
     * @param lgtVendorQuotedSumResult
     */
    @PostMapping("/shortlistedOrEliminated")
    public void shortlistedOrEliminated(@RequestBody LgtVendorQuotedSumResult lgtVendorQuotedSumResult){
        lgtVendorQuotedSumResult.setOperateType(YesOrNo.YES.getValue());
        iLgtBidingService.shortlistedOrEliminated(lgtVendorQuotedSumResult);
    }

    /**
     * 决标
     * @param lgtVendorQuotedSumResult
     */
    @PostMapping("/award")
    public void award(@RequestBody LgtVendorQuotedSumResult lgtVendorQuotedSumResult){
        lgtVendorQuotedSumResult.setOperateType(YesOrNo.NO.getValue());
        iLgtBidingService.shortlistedOrEliminated(lgtVendorQuotedSumResult);
    }

    /**
     * 公示结果
     * @param bidingId
     * @param round
     */
    @GetMapping("/publicResult")
    public void publicResult(@RequestParam("bidingId") Long bidingId,@RequestParam("round") Integer round){
        iLgtBidingService.publicResult(bidingId, round);
    }

    /**
     * 点击代理报价查找还没报价的供应商
     * @return
     */
    @GetMapping("/agencyQuotationQueryVendor")
    public List<LgtVendorQuotedHead> agencyQuotationQueryVendor(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.agencyQuotationQueryVendor(bidingId);
    }

    /**
     * 采购商代理获取-报价详情
     * @param bidingId
     * @param vendorId
     * @return
     */
    @GetMapping("/getQuotedInfo")
    public LgtVendorQuotedHeadDto getQuotedInfo(@RequestParam("bidingId") Long bidingId, @RequestParam("vendorId")Long vendorId){
        return iLgtBidingService.getQuotedInfo(bidingId, vendorId);
    }

    /**
     * 采购商代理报价-提交报价
     * @param lgtVendorQuotedHeadDto
     */
    @PostMapping("/submitQuotedPrice")
    public void submitQuotedPrice(@RequestBody LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto){
        iLgtBidingService.submitQuotedPrice(lgtVendorQuotedHeadDto);
    }

    /**
     * 根据供应商报价头ID获取供应商报价
     * @param quotedHeadId
     * @return
     */
    @GetMapping("/getQuotedInfoByQuotedHeadId")
    public LgtVendorQuotedHeadDto getQuotedInfoByQuotedHeadId(@RequestParam("quotedHeadId") Long quotedHeadId){
        return iLgtBidingService.getQuotedInfoByQuotedHeadId(quotedHeadId);
    }

    /**
     * 作废投标
     * @param quotedHeadId
     */
    @GetMapping("/invalidBid")
    public void invalidBid(@RequestParam("quotedHeadId") Long quotedHeadId,String invalidReason){
        iLgtBidingService.invalidBid(quotedHeadId, invalidReason);
    }

    /**
     * 商务标管理查看附件
     * @param quotedHeadId
     * @return
     */
    @GetMapping("/queryLgtFileConfig")
    public List<LgtFileConfig> queryLgtFileConfig(@RequestParam("bidingId") Long bidingId,@RequestParam("quotedHeadId") Long quotedHeadId){
        return iLgtBidingService.queryLgtFileConfig(bidingId,quotedHeadId);
    }

    /**
     * 生成结果审批单
     * @param bidingId
     */
    @GetMapping("/generateResultApprove")
    public void generateResultApprove(@RequestParam("bidingId") Long bidingId,String summaryDescription){
        iLgtBidingService.generateResultApprove(bidingId,summaryDescription);
    }

    /**
     * 审批流程界面查询数据
     */
    @GetMapping("/queryResultApproveInfo")
    public LgtApproveInfoDto queryResultApproveInfo(@RequestParam("bidingId") Long bidingId,
                                                    @RequestParam(name = "round",required = false) Long round
    ){
        return iLgtBidingService.queryResultApproveInfo(bidingId,round);
    }

    /**
     * 保存技术评选评论
     * @param bidingId
     * @param technoSelection
     */
    @GetMapping("/saveTechnoSelection")
    public void saveTechnoSelection(@RequestParam("bidingId") Long bidingId,@RequestParam(name = "technoSelection",required = false) String technoSelection){
        iLgtBidingService.saveTechnoSelection(bidingId,technoSelection);
    }

    /**
     * 评选结果导出
      * @param bidingId
     * @param round
     * @param vendorId
     */
    @GetMapping("/quotedSumExport")
    public BaseResult quotedSumExport(
            @RequestParam("bidingId") Long bidingId,
            @RequestParam("round") Integer round,
            @RequestParam(name = "vendorId",required = false) Long vendorId,
            HttpServletResponse response) throws IOException {
        BaseResult baseResult = BaseResult.build(ResultCode.SUCCESS);
        try {
            iLgtBidingService.quotedSumExport(bidingId,round,vendorId,response);
        } catch (Exception e) {
            baseResult = BaseResult.build(ResultCode.EXPORT_EXCEPTIONS,e.getMessage());
        }finally {
            return baseResult;
        }
    }

    /**
     * 获取需求行模板
     * @return
     */
    @GetMapping("/getLgtBidTemplate")
    public List<LgtBidTemplate> getLgtBidTemplate(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingService.getLgtBidTemplate(bidingId);
    }

}

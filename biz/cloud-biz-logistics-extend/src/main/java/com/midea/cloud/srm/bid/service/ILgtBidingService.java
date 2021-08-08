package com.midea.cloud.srm.bid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;
import com.midea.cloud.srm.model.logistics.bid.dto.*;
import com.midea.cloud.srm.model.logistics.bid.entity.*;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidcontrol.vo.StartOrExtendBidingVO;
import com.midea.cloud.srm.model.logistics.bid.vo.CommercialBidVO;
import com.midea.cloud.srm.model.logistics.bid.vo.LgtBidInfoVO;
import com.midea.cloud.srm.model.logistics.bid.vo.TechBidVO;
import com.midea.cloud.srm.model.supplier.info.dto.VendorDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  物流寻源基础信息表 服务类
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
public interface ILgtBidingService extends IService<LgtBiding> {
    /**
     * 分页查询
     * @param lgtBiding
     * @return
     */
    PageInfo<LgtBiding> listPage(LgtBiding lgtBiding);

    /**
     * 更新或保存招标基础信息
     * @param lgtBidDto
     * @return
     */
    Long updateOrAdd (LgtBidDto lgtBidDto);

    /**
     * 新增
     * @param lgtBidDto
     * @return
     */
    Long add (LgtBidDto lgtBidDto);

    /**
     * 更新
     * @param lgtBidDto
     * @return
     */
    Long modify (LgtBidDto lgtBidDto);

    /**
     * 获取招标详情
     * @param bidingId
     * @return
     */
    LgtBidInfoDto getLgtBidInfoVo(@RequestParam("bidingId") Long bidingId);

    /**
     * 获取项目需求明细
     * @param bidingId
     * @return
     */
    List<LgtBidRequirementLine> getLgtBidRequirementLineByBidingId(Long bidingId);

    /**
     * 更新项目明细信息
     * @param lgtBidRequirementLines
     * @return
     */
    List<LgtBidRequirementLine> updateLgtBidRequirementLine(List<LgtBidRequirementLine> lgtBidRequirementLines);

    /**
     * 智能推荐供应商
     * @param bidingId
     * @return
     */
    List<VendorDto> intelligentFindVendor(@RequestParam("bidingId") Long bidingId);

    /**
     * 获取供应商
     * @param bidingId
     * @return
     */
    List<LgtBidVendor> getLgtBidVendorByBidingId(Long bidingId);

    /**
     * 保存邀请供应商信息
     * @param lgtBidVendors
     * @return
     */
    void saveLgtBidVendor(@RequestBody List<LgtBidVendor> lgtBidVendors);

    /**
     * 根据邀请供应商获取报价权限
     * @param bidVendorId
     * @return
     */
    List<LgtQuoteAuthorize> getLgtQuoteAuthorizeByBidVendorId(Long bidingId,Long bidVendorId);

    /**
     * 更新流程节点状态
     * @param processNode
     * @return
     */
    void updateNodeStatus(LgtProcessNode processNode);


    /**
     * 获取招标流程节点信息
     * @param bidingId
     */
    List<LgtProcessNode> getNodeStatus(Long bidingId);

    /**
     * 保存供应商报价权限
     * @param lgtQuoteAuthorizes
     */
    void saveLgtQuoteAuthorize(List<LgtQuoteAuthorize> lgtQuoteAuthorizes);

    /**
     * 预览供应商
     * @param bidingId
     * @return
     */
    List<LgtVendorQuotedDto> previewVendor(Long bidingId);

    /**
     * 发布
     * @param bidingId
     */
    void release(Long bidingId);

    /**
     * 撤回
     * @param bidingId
     */
    void withdraw(Long bidingId,String reason);

    /**
     * 删除
     * @param bidingIds
     */
    void delete(List<Long> bidingIds);

    /**
     * 新一轮截止时间
     *
     * @param startOrExtendBidingVO
     */
    void startBiding(StartOrExtendBidingVO startOrExtendBidingVO);

    /**
     * 调整截止时间
     *
     * @param startOrExtendBidingVO
     */
    void extendBiding(StartOrExtendBidingVO startOrExtendBidingVO);

    /**
     * 立即截止
     * @param bidingId
     */
    void dueImmediately(Long bidingId);

    /**
     * 开标计算结果
     * @param lgtBiding
     */
    void bidOpeningCalculationResult(LgtBiding lgtBiding);

    /**
     * 供应商查看招标详情
     * @param bidingId
     * @return
     */
    LgtBidInfoVO supplierDetails(Long bidingId);

    /**
     * 技术标管理查看详情
     * @param bidingId
     * @return
     */
    TechBidVO detailTechBiding(Long bidingId);

    /**
     * 商务标管理查看详情
     * @param bidingId
     * @return
     */
    CommercialBidVO detailCommercialBiding(Long bidingId);

    /**
     * 需求池转招标单
     * @param id
     * @return
     */
    Long requirementToBiding(Long id);

    /**
     * 查询招标单详情
     * @param id
     * @return
     */
    LgtBidInfoVO detail(Long id);

    /**
     * 根据头表截止时间更新状态
     * @param lgtBiding
     */
    void updateEnrollEndDatetime(LgtBiding lgtBiding);

    /**
     * 投标控制获取数据
     * @param bidingId
     * @return
     */
    LgtVendorQuotedHeadVo getTopInfo(Long bidingId);

    /**
     * 评选界面获取信息
     * @param bidingId
     * @return
     */
    LgtVendorQuotedSumDto queryLgtVendorQuotedSumDto(Long bidingId,Integer round,Long vendorId,Integer rank,String bidResult);

    /**
     * 评选报价明细行保存
     */
    void selectionQuotedLineSave(List<LgtVendorQuotedLine> lgtVendorQuotedLines);

    /**
     * 入围或淘汰
     * @param lgtVendorQuotedSumResult
     */
    void shortlistedOrEliminated(LgtVendorQuotedSumResult lgtVendorQuotedSumResult);

    /**
     * 公示结果
     * @param bidingId
     * @param round
     */
    void publicResult(Long bidingId,Integer round);

    /**
     * 点击代理报价查找还没报价的供应商
     * @return
     */
    List<LgtVendorQuotedHead> agencyQuotationQueryVendor(Long bidingId);


    /**
     * 采购商获取报价详情
     * @param bidingId
     * @param vendorId
     * @return
     */
    LgtVendorQuotedHeadDto getQuotedInfo(Long bidingId,Long vendorId);

    /**
     * 采购商代理报价-提交报价
     * @param lgtVendorQuotedHeadDto
     */
    void submitQuotedPrice(LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto);


    /**
     * 根据供应商包间下头ID获取供应商报价
     * @param quotedHeadId
     * @return
     */
    LgtVendorQuotedHeadDto getQuotedInfoByQuotedHeadId(Long quotedHeadId);

    /**
     * 作废投标
     * @param quotedHeadId
     */
    void invalidBid(Long quotedHeadId,String invalidReason);

    /**
     * 商务标管理查看附件
     * @param quotedHeadId
     * @return
     */
    List<LgtFileConfig> queryLgtFileConfig(Long bidingId,Long quotedHeadId);

    /**
     * 生成结果审批单
     * @param bidingId
     */
    void generateResultApprove(Long bidingId,String summaryDescription);

    /**
     * 审批流程界面查询数据
     */
    LgtApproveInfoDto queryResultApproveInfo(Long bidingId,Long round);

    /**
     * 保存技术评选评论
     * @param bidingId
     * @param technoSelection
     */
    void saveTechnoSelection(Long bidingId,String technoSelection);

    /**
     * 评选结果导出
     * @param bidingId
     * @param round
     * @param vendorId
     */
    void quotedSumExport(Long bidingId, Integer round, Long vendorId, HttpServletResponse response) throws IOException;

    /**
     * 计算供应商报价行数据,并更新
     * @param lgtBiding
     * @param lgtVendorQuotedLines
     */
    void insideCalculationVendorQuotedLines(LgtBiding lgtBiding, List<LgtVendorQuotedLine> lgtVendorQuotedLines, Map<String, LatestGidailyRate> latestGidailyRateMap);

    /**
     * 计算供应商报价行数据,并更新
     * @param lgtBiding
     * @param lgtVendorQuotedLines
     */
    void noInsideCalculationVendorQuotedLines(LgtBiding lgtBiding, List<LgtVendorQuotedLine> lgtVendorQuotedLines,Map<String, LatestGidailyRate> latestGidailyRateMap);

    /**
     * 获取最新的人民币汇率
     */
    Map<String, LatestGidailyRate> getLatestGidailyRateMap(LgtBiding lgtBiding);

    /**
     * 获取需求行模板
     * @return
     */
    List<LgtBidTemplate> getLgtBidTemplate(Long bidingId);

    /**
     * 批量需求池转招标单
     * @param ids
     * @return
     */
    List<Long> requirementToBidings(List<Long> ids);

    /**
     *
     * 获取【立项审批状态为已审批】的招标单的邀请供应商信息
     * @param bidingId
     * @return
     */
    List<LgtBidVendor> getLgtBidVendorByBidingIdAndStatus(Long bidingId);

    /**
     * 评选报价明细行保存
     */
    void selectionQuotedLineSaveNew(LgtVendorQuotedLineDto lgtVendorQuotedLines);

    /**
     * 开技术标
     * @param bidingId
     */
    void techOpenBiding(Long bidingId);

    /**
     * 商务标
     */
    void techOpenBusiness(Long bidingId);
}

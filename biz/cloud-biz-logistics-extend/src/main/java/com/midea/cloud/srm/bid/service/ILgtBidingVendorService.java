package com.midea.cloud.srm.bid.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.logistics.bid.dto.*;
import com.midea.cloud.srm.model.logistics.bid.vo.LgtBidInfoVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * 物流招标-供应商协同
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
public interface ILgtBidingVendorService {

    /**
     * 分页查询
     * @param lgtBidingDto
     * @return
     */
    PageInfo<LgtBidingDto> listPage(LgtBidingDto lgtBidingDto);

    /**
     * 供应商获取报价详情
     * @param quotedHeadId
     * @return
     */
    LgtVendorQuotedHeadDto getLgtVendorQuotedHeadByQuotedHeadId(Long quotedHeadId);

    /**
     * 供应商获取报价详情
     * @param bidingId
     * @param vendorId
     * @return
     */
    LgtVendorQuotedHeadDto getLgtVendorQuotedHead(Long bidingId, Long vendorId);

    /**
     * 供应商保存报价
     * @param lgtVendorQuotedHead
     */
    void quotedPriceSave(LgtVendorQuotedHeadDto lgtVendorQuotedHead);

    /**
     * 供应商提交报价
     */
    void submitQuotedPrice(LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto);

    /**
     * 供应商撤回报价
     */
    void withdrawQuotedPrice(Long bidingId,Long vendorId,String withdrawReason);

    LgtBidInfoVO supplierDetails(Long bidingId);

    /**
     * 供应商查看详情 - 项目信息
     * @param bidingId
     * @return
     */
    LgtBidVendorDto getLgtBidVendor(Long bidingId);

    /**
     * 供应商查看详情 - 需求明细
     * @param bidingId
     * @return
     */
    LgtBidRequirementLineVendorDto getLgtBidRequirementLineVendorDto(Long bidingId);

    /**
     * 供应商查看详情 - 投标明细
     * @param bidingId
     * @return
     */
    LgtVendorQuotedHeadVendorDto getLgtVendorQuotedHeadVendorDto(Long bidingId);

    /**
     * 供应商查看详情 - 投标结果
     * @param bidingId
     * @return
     */
    LgtVendorQuotedSumVendorDto getLgtVendorQuotedSumVendorDto(Long bidingId);

    /**
     * 获取excel标题行
     * @param file
     * @return
     */
    LgtQuotedLineImportTitle getExcelTitle(MultipartFile file,Long bidingId) throws IOException;

    /**
     * 报价行导入
     * @param file
     * @return
     */
    Object quotedLineImport(MultipartFile file, String param, Long bidingId, Long vendorId,HttpServletResponse response) throws Exception;

    /**
     * 供应商船期导出
     * @param
     * @return
     */
    void lgtBidShipPeriodExport(Long bidingId,Long vendorId,HttpServletResponse response) throws IOException;

    /**
     * 供应商报价船期到入模板下载
     */
    void lgtBidShipPeriodImportModelDownload(Long bidingId,HttpServletResponse response) throws IOException;

    /**
     * 供应商报价船期导入
     */
    Object lgtBidShipPeriodImport(MultipartFile file, Long bidingId, Long vendorId, HttpServletResponse response) throws IOException;

    /**
     * 校验供应商必填
     * @param lgtVendorQuotedHead
     */
    void checkLgtVendorQuotedLine(LgtVendorQuotedHeadDto lgtVendorQuotedHead);

    /**
     * 导出供应商报价行信息
     * @param bidingId
     * @param vendorId
     */
    void exportLgtVendorQuotedLine(Long bidingId, Long vendorId,HttpServletResponse response) throws IOException;
}

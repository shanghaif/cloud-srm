package com.midea.cloud.srm.bid.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.bid.service.ILgtBidingVendorService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.bid.dto.*;
import com.midea.cloud.srm.model.logistics.bid.vo.LgtBidInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
*  <pre>
 *  投标结果汇总表 前端控制器
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/biding/vendor")
public class LgtBidingVendorController extends BaseController {

    @Autowired
    private ILgtBidingVendorService iLgtBidingVendorService;

    /**
     * 招投标项目管理-分页查询
     *
     * @param lgtBidingDto
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<LgtBidingDto> listPage(@RequestBody LgtBidingDto lgtBidingDto) {
        return iLgtBidingVendorService.listPage(lgtBidingDto);
    }

    /**
     * 供应商报价
     */
    @PostMapping("/quotedPriceSave")
    public void quotedPriceSave(@RequestBody LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto){
        iLgtBidingVendorService.quotedPriceSave(lgtVendorQuotedHeadDto);
    }

    /**
     * 供应商获取报价详情
     * @param bidingId
     * @param vendorId
     * @return
     */
    @GetMapping("/getLgtVendorQuotedHead")
    public LgtVendorQuotedHeadDto getLgtVendorQuotedHead(@RequestParam("bidingId") Long bidingId, @RequestParam("vendorId")Long vendorId){
        return iLgtBidingVendorService.getLgtVendorQuotedHead(bidingId, vendorId);
    }

    /**
     * 供应商提交报价
     * @param lgtVendorQuotedHeadDto
     */
    @PostMapping("/submitQuotedPrice")
    public void submitQuotedPrice(@RequestBody LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto){
        iLgtBidingVendorService.submitQuotedPrice(lgtVendorQuotedHeadDto);
    }

    /**
     * 供应商撤回报价
     */
    @PostMapping("/withdrawQuotedPrice")
    public void withdrawQuotedPrice(@RequestParam("bidingId") Long bidingId,
                                    @RequestParam("vendorId") Long vendorId,
                                    @RequestParam("withdrawReason") String withdrawReason){
        iLgtBidingVendorService.withdrawQuotedPrice(bidingId, vendorId,withdrawReason);
    }

    /**
     * 供应商端查看招标详情
     * @param bidingId
     * @return
     */
    @GetMapping("/supplierDetails")
    public LgtBidInfoVO supplierDetails(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingVendorService.supplierDetails(bidingId);
    }

    /**
     * 供应商查看详情 - 项目信息
     * @param bidingId
     * @return
     */
    @GetMapping("/getLgtBidVendor")
    public LgtBidVendorDto getLgtBidVendor(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingVendorService.getLgtBidVendor(bidingId);
    }

    /**
     * 供应商查看详情 - 需求明细
     * @param bidingId
     * @return
     */
    @GetMapping("/getLgtBidRequirementLineVendorDto")
    public LgtBidRequirementLineVendorDto getLgtBidRequirementLineVendorDto(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingVendorService.getLgtBidRequirementLineVendorDto(bidingId);
    }

    /**
     * 供应商查看详情 - 投标明细
     * @param bidingId
     * @return
     */
    @GetMapping("/getLgtVendorQuotedHeadVendorDto")
    public LgtVendorQuotedHeadVendorDto getLgtVendorQuotedHeadVendorDto(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingVendorService.getLgtVendorQuotedHeadVendorDto(bidingId);
    }

    /**
     * 供应商查看详情 - 投标结果
     * @param bidingId
     * @return
     */
    @GetMapping("/getLgtVendorQuotedSumVendorDto")
    public LgtVendorQuotedSumVendorDto getLgtVendorQuotedSumVendorDto(@RequestParam("bidingId") Long bidingId){
        return iLgtBidingVendorService.getLgtVendorQuotedSumVendorDto(bidingId);
    }

    /**
     * 获取excel标题行
     * @param file
     * @return
     */
    @PostMapping("/getExcelTitle")
    public LgtQuotedLineImportTitle getExcelTitle(@RequestParam("file") MultipartFile file, Long bidingId) throws IOException {
        return iLgtBidingVendorService.getExcelTitle(file,bidingId);
    }

    /**
     * 报价行导入
     * @param file
     * @return
     */
    @PostMapping("/quotedLineImport")
    public Object quotedLineImport(@RequestParam("file") MultipartFile file,
                                               String param,
                                               Long bidingId,
                                               Long vendorId,
                                               HttpServletResponse response) throws Exception {
        return iLgtBidingVendorService.quotedLineImport(file,param,bidingId,vendorId,response);
    }

    /**
     * 供应商船期导出
     * @param
     * @return
     */
    @GetMapping("/lgtBidShipPeriodExport")
    public void lgtBidShipPeriodExport(Long bidingId,Long vendorId,HttpServletResponse response) throws IOException {
        iLgtBidingVendorService.lgtBidShipPeriodExport(bidingId,vendorId,response);
    }

    /**
     * 供应商报价船期导入模板下载
     */
    @GetMapping("/lgtBidShipPeriodImportModelDownload")
    public void lgtBidShipPeriodImportModelDownload(Long bidingId, HttpServletResponse response) throws IOException {
        iLgtBidingVendorService.lgtBidShipPeriodImportModelDownload(bidingId,response);
    }

    /**
     * 供应商报价船期导入
     */
    @PostMapping("/lgtBidShipPeriodImport")
    public Object lgtBidShipPeriodImport(@RequestParam("file") MultipartFile file,
                                                     Long bidingId,
                                                     Long vendorId,
                                                     HttpServletResponse response) throws IOException {
        return iLgtBidingVendorService.lgtBidShipPeriodImport(file,bidingId,vendorId,response);
    }

    /**
     * 导出供应商报价行信息
     * @param bidingId
     * @param vendorId
     */
    @GetMapping("/exportLgtVendorQuotedLine")
    public BaseResult exportLgtVendorQuotedLine(@RequestParam("bidingId") Long bidingId,
                                                @RequestParam("vendorId") Long vendorId,
                                                HttpServletResponse response
    ) throws IOException {
        BaseResult baseResult = BaseResult.buildSuccess();
        try {
            iLgtBidingVendorService.exportLgtVendorQuotedLine(bidingId, vendorId, response);
        } catch (Exception e) {
            baseResult = BaseResult.build(ResultCode.IMPORT_EXCEPTIONS,e.getMessage());
        }finally {
            return baseResult;
        }
    }

}

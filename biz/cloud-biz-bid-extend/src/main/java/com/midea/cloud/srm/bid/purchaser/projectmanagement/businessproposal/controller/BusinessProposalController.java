package com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.controller;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderHeadMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderLineMapper;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHeadFile;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderHeadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.service.IBusinessProposalService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.vo.BusinessItemVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.vo.CancelBidParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.vo.OrderDetailVO;
import com.midea.cloud.srm.model.common.BaseController;

/**
 * <pre>
 * 商务标管理
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月25日 上午10:02:23
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/businessProposal")
public class BusinessProposalController extends BaseController {

    @Autowired
    private IBusinessProposalService iBusinessProposalService;
    @Autowired
    private OrderHeadMapper orderHeadMapper;
    @Autowired
    private OrderLineMapper orderLineMapper;

    /**
     * 开标校验
     *
     * @param bidingId
     */
    @GetMapping("/openBidCheck")
    public String openBidCheck(@RequestParam(required = true) Long bidingId) {
        return iBusinessProposalService.openBidCheck(bidingId);
    }

    /**
     * 商务开标
     *
     * @param bidingId
     */
    @GetMapping("/openBid")
    public void openBid(@RequestParam(required = true) Long bidingId) {
        iBusinessProposalService.openBid(bidingId);
    }

    /**
     * 商务评标列表
     *
     * @param bidingId
     * @return
     */
    @GetMapping("/queryBusinessItemList")
    public List<BusinessItemVO> queryBusinessItemList(@RequestParam(required = true) Long bidingId) {
        return iBusinessProposalService.queryBusinessItemList(bidingId);
    }

    @GetMapping("/removeOrderInfo")
    public void removeOrderInfo(@RequestParam("bidVendorId") Long bidVendorId, @RequestParam("round") Integer round) {
        orderLineMapper.delete(Wrappers.lambdaQuery(OrderLine.class)
                .eq(OrderLine::getBidVendorId, bidVendorId)
                .eq(OrderLine::getRound, round)
        );
        orderHeadMapper.delete(Wrappers.lambdaQuery(OrderHead.class)
                .eq(OrderHead::getBidVendorId, bidVendorId)
                .eq(OrderHead::getRound, round)
		);
    }

    /**
     * 作废投标
     *
     * @param cancelBidParam
     */
    @PostMapping("/cancelBid")
    public void cancelBid(@RequestBody CancelBidParam cancelBidParam) {
        iBusinessProposalService.cancelBid(cancelBidParam);
    }

    /**
     * 投标详情
     *
     * @param orderHeadId
     * @return
     */
    @GetMapping("/queryOrderDetailList")
    public BidOrderHeadVO queryOrderDetailList(@RequestParam(required = true) Long orderHeadId) {
        return iBusinessProposalService.queryNewOrderDetailList(orderHeadId);
    }

    @GetMapping("/getOrderHeadFileByOrderHeadId")
    public List<OrderHeadFile> getOrderHeadFileByOrderHeadId(@RequestParam Long orderHeadId) {
        return iBusinessProposalService.getOrderHeadFileByOrderHeadId(orderHeadId);
    }

    @GetMapping("/getOrderHeadFileByVendorIdAndBidingId")
    public List<OrderHeadFile> getOrderHeadFileByVendorIdAndBidingId(@RequestParam Long vendorId, @RequestParam Long bidingId) {
        return iBusinessProposalService.getOrderHeadFileByVendorIdAndBidingId(bidingId, vendorId);
    }

    /**
     * 投标监控报表
     *
     * @param bidingId
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/generateBidReport")
    public void generateBidReport(@RequestParam(required = true) Long bidingId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = iBusinessProposalService.generateBidReport(bidingId);
        String fileName = URLEncoder.encode(result.get("fileName").toString(), "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 解决axios下载后取不到文件名的问题
        response.setHeader("FileName", fileName); // 解决axios下载后取不到文件名的问题
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.getOutputStream().write((byte[]) result.get("buffer"));
        response.getOutputStream().close();
    }
}

package com.midea.cloud.srm.bid.suppliercooperate.projectlist.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.IBidSignUpService;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.ISupplierBidingService;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.SupplierBidingVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderHeadVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderLineVO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  供应商投标头表 前端控制器
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020-09-11 13:54:22
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/supplierCooperate/orderHead")
@Slf4j
public class OrderHeadController extends BaseController {

    @Autowired
    private IOrderHeadService iOrderHeadService;
    @Autowired
    private IOrderLineService iOrderLineService;
    @Autowired
    private IBidingService iBidingService;
    @Resource
    private ISupplierBidingService iSupplierBidingService;
    @Resource
    private IBidSignUpService iBidSignUpService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public OrderHead get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderHeadService.getById(id);
    }

    /**
     * 根据bidOrderHeadVO保存投标的头表和行表以及附件数据。
     * 投标--保存
     *
     * @param bidOrderHeadVO
     */
    @PostMapping("/saveOrderInfo")
    public BidOrderHeadVO saveOrderInfo(@RequestBody BidOrderHeadVO bidOrderHeadVO) {

        //判断是否满足投标条件，如果不满足，会抛出异常
        iOrderHeadService.judgeBidingConditions(bidOrderHeadVO);
        //保存的时候，判断是否满足条件
        iOrderHeadService.judgeSaveOrderInfoPerssion(bidOrderHeadVO);
        String orderStatus = bidOrderHeadVO.getOrderStatus();
        Assert.isTrue(!OrderStatusEnum.SUBMISSION.getValue().equals(orderStatus), "投标已经提交，不能修改。");
        bidOrderHeadVO.setOrderStatus(OrderStatusEnum.DRAFT.getValue());
        String key=new StringBuilder("bid-saveOrderInfo").append("-")
                .append(bidOrderHeadVO.getBidingId()).append("-").append(bidOrderHeadVO.getBidVendorId())
                .append("-").append(bidOrderHeadVO.getRoundId()).toString();
        try {
        	 Boolean lock = redisUtil.tryLock(key);
             if(lock){
                 iOrderHeadService.saveOrderInfo(bidOrderHeadVO, false);
                 return "Y".equals(bidOrderHeadVO.getIsProxyBidding())
                         ? proxyGetOrderHeadInfo(bidOrderHeadVO)
                         : getOrderHeadInfo(bidOrderHeadVO);
             }else{
                 throw new BaseException("系统报价保存中，无需重复操作");
             }
		} catch (Exception e) {
			throw e;
		} finally {
			 redisUtil.unLock(key);
		}
       

    }

    /**
     * 投标--提交
     *
     * @param bidOrderHeadVO
     */
    @PostMapping("/submitOrder")
    public BidOrderHeadVO submitOrderInfo(@RequestBody BidOrderHeadVO bidOrderHeadVO) throws IOException {
        iOrderHeadService.submitOrder(bidOrderHeadVO);
       /* return "Y".equals(bidOrderHeadVO.getIsProxyBidding())
                ? proxyGetOrderHeadInfo(bidOrderHeadVO)
                : getOrderHeadInfo(bidOrderHeadVO);*/
        return null;
    }

    /**
     * 投标--撤回
     *
     * @param bidOrderHeadVO
     */
    @PostMapping("/withdrawOrder")
    public Object withdrawOrder(@RequestBody BidOrderHeadVO bidOrderHeadVO) {
        OrderHead orderHead = iOrderHeadService.getById(bidOrderHeadVO.getOrderHeadId());
        Assert.isTrue(!OrderStatusEnum.WITHDRAW.getValue().equals(orderHead.getOrderStatus()), "投标已经撤回，不能重复撤回。");
        Assert.isTrue(OrderStatusEnum.SUBMISSION.getValue().equals(orderHead.getOrderStatus()), "投标状态不是已投标，无法撤回。");
        Biding biding = iBidingService.getById(bidOrderHeadVO.getBidingId());
        Assert.isTrue(BiddingProjectStatus.ACCEPT_BID.getValue().equals(biding.getBidingStatus()), "招标项目状态不是接受投标中，无法撤回。");
        //判断是否允许撤回投标
        Assert.isTrue(biding.getWithdrawBiding().equals(YesOrNo.YES.getValue()), "不允许供应商撤回投标");
        bidOrderHeadVO.setOrderStatus(OrderStatusEnum.WITHDRAW.getValue());
        iOrderHeadService.withdrawOrder(bidOrderHeadVO);
        return "投标撤回成功。";
    }

    /**
     * 投标--跟标
     *
     * @param bidOrderHeadVO
     */
    @PostMapping("/withStandardOrder")
    public Object withStandardOrder(@RequestBody BidOrderHeadVO bidOrderHeadVO) {
        List<BidOrderLineVO> orderLineVOS = bidOrderHeadVO.getOrderLines();
        List<OrderLine> orderLines = new ArrayList<>();
        for (BidOrderLineVO lineVO : orderLineVOS) {
            OrderLine temp = iOrderLineService.getById(lineVO.getOrderLineId());
            temp.setWithStandard("Y");
            orderLines.add(temp);
        }
        iOrderLineService.updateBatchById(orderLines);
        return "跟标操作成功。";
    }

    /**
     * 投标--取消跟标
     *
     * @param bidOrderHeadVO
     */
    @PostMapping("/cancleWithStandardOrder")
    public Object cancleWithStandardOrder(@RequestBody BidOrderHeadVO bidOrderHeadVO) {
        List<BidOrderLineVO> orderLineVOS = bidOrderHeadVO.getOrderLines();
        List<OrderLine> orderLines = new ArrayList<>();
        for (BidOrderLineVO lineVO : orderLineVOS) {
            OrderLine temp = iOrderLineService.getById(lineVO.getOrderLineId());
            temp.setWithStandard("N");
            orderLines.add(temp);
        }
        iOrderLineService.updateBatchById(orderLines);
        return "取消跟标操作成功。";
    }

    /**
     * 投标--跟标查询
     * 供应商列表点击“跟标”时候查询的数据
     *
     * @param bidOrderHeadVO
     * @return
     */
    @PostMapping("/withStandardOrderInfo")
    public BidOrderHeadVO withStandardOrderInfo(@RequestBody BidOrderHeadVO bidOrderHeadVO) {

        Long orderHeadId = bidOrderHeadVO.getOrderHeadId();
        Assert.notNull(orderHeadId, "投标单id不能为空");
        BidOrderHeadVO orderHeadVO = new BidOrderHeadVO();
        BeanCopyUtil.copyProperties(orderHeadVO, bidOrderHeadVO);
        //1 获取头表数据
        OrderHead orderHead = iOrderHeadService.getById(orderHeadId);
        BeanCopyUtil.copyProperties(orderHeadVO, orderHead);

        //2 获取行表数据
        Long bidingId = bidOrderHeadVO.getBidingId();
        List<BidOrderLineVO> bidOrderLineVOs = iOrderLineService.getWithStandardOrderInfoByOrderHeadId(orderHeadId);
        orderHeadVO.setOrderLines(bidOrderLineVOs);

        return orderHeadVO;
    }


    /**
     * 投标--供应商端
     * 供应商列表点击投标时候查询的数据
     *
     * @param bidOrderHeadVO
     * @return
     */
    @PostMapping("/orderDetail")
    public BidOrderHeadVO getOrderHeadInfo(@RequestBody BidOrderHeadVO bidOrderHeadVO) {
        Assert.notNull(bidOrderHeadVO.getBidVendorId(), "bidVendorId不允许为空");
        bidOrderHeadVO.setVendorId(AppUserUtil.getLoginAppUser().getCompanyId());
        bidOrderHeadVO.setIsProxyBidding("N");
        //判断是否满足投标条件，如果不满足，会抛出异常
        iOrderHeadService.judgeBidingConditions(bidOrderHeadVO);
        return iOrderHeadService.getOrCreateOrderHeadInfo(bidOrderHeadVO);
    }


    /**
     * 报价行导入模板下载
     *
     * @param bidOrderHeadVO
     * @return
     */
    @PostMapping("/importModelDownload")
    public void importModelDownload(@RequestBody BidOrderHeadVO bidOrderHeadVO, HttpServletResponse response) throws Exception {
        iBidSignUpService.importModelDownload(bidOrderHeadVO, response);
    }

    /**
     * 导入评选结果
     *
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Long bidingId, Fileupload fileupload) throws Exception {
        return iBidSignUpService.importExcel(file, fileupload, bidingId);
    }

    /**
     * 新增
     *
     * @param bidOrderHead
     */
    @PostMapping("/add")
    public void add(@RequestBody OrderHead bidOrderHead) {
        Long id = IdGenrator.generate();
        bidOrderHead.setOrderHeadId(id);
        iOrderHeadService.save(bidOrderHead);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderHeadService.removeById(id);
    }

    /**
     * 修改
     *
     * @param bidOrderHead
     */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderHead bidOrderHead) {
        iOrderHeadService.updateById(bidOrderHead);
    }

    /**
     * 分页查询
     *
     * @param bidOrderHead
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<OrderHead> listPage(@RequestBody OrderHead bidOrderHead) {
        PageUtil.startPage(bidOrderHead.getPageNum(), bidOrderHead.getPageSize());
        QueryWrapper<OrderHead> wrapper = new QueryWrapper<OrderHead>(bidOrderHead);
        return new PageInfo<OrderHead>(iOrderHeadService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<OrderHead> listAll() {
        return iOrderHeadService.list();
    }

    @GetMapping("/excelExport")
    public void excelEport(Long bidingId, Long bidVendorId, @RequestParam(required = false) Long orderHeadId, HttpServletResponse response) throws Exception {
        //获取数据，转为字节数组
        List dataList = iOrderHeadService.getOrderLineVOS(bidingId, orderHeadId, bidVendorId);
        Assert.isTrue(dataList.size() > 0, "未找到数据，无法导出。");
        byte[] buffer = EasyExcelUtil.writeExcelWithModel(dataList, BidOrderLineVO.class);

        String fileName = "投标商务信息.xls";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.getOutputStream().write(buffer);
        response.getOutputStream().close();
    }


    @PostMapping("/excelImport")
    public List<BidOrderLineVO> excelImport(@RequestParam("file") MultipartFile file, Long bidingId, Long bidVendorId, @RequestParam(required = false) Long orderHeadId) throws IOException {
        Assert.notNull(file, "文件上传失败");
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Assert.isTrue((StringUtils.equals("xls", suffix.toLowerCase()) || StringUtils.equals("xlsx", suffix.toLowerCase())),
                "请上传excel文件");
        List<Object> list = EasyExcelUtil.readExcelWithModel(file.getInputStream(), BidOrderLineVO.class);
        List<BidOrderLineVO> orderLineVOS = iOrderHeadService.matchOrderLine(bidingId, orderHeadId, bidVendorId, list);
        return orderLineVOS;
    }

    @PostMapping("/proxyOrderDetail")
    public BidOrderHeadVO proxyGetOrderHeadInfo(@RequestBody BidOrderHeadVO proxyVO) {
        Assert.notNull(proxyVO.getVendorId(), "vendorId不允许为空");
        Assert.notNull(proxyVO.getBidingId(), "bidingId不允许为空");
        List<SupplierBidingVO> supplierBiding = iSupplierBidingService.getSupplierBiding(proxyVO);
        if (CollectionUtils.isEmpty(supplierBiding)) {
            throw new BaseException("招标信息获取失败。 | bidingId: [" + proxyVO.getBidingId()
                    + "], vendorId: [" + proxyVO.getVendorId() + "]");
        }
        SupplierBidingVO supplierBidingVO = supplierBiding.get(0);
        BidOrderHeadVO bidOrderHeadVO = new BidOrderHeadVO();
        BeanUtils.copyProperties(supplierBidingVO, bidOrderHeadVO);
        bidOrderHeadVO.setIsProxyBidding("Y");
        //判断是否满足投标条件，如果不满足，会抛出异常
        iOrderHeadService.judgeBidingConditions(bidOrderHeadVO);
        return iOrderHeadService.getOrCreateOrderHeadInfo(bidOrderHeadVO);
    }

    @GetMapping("/findNotPricingVendors")
    public List<BidVendor> findNotPricingVendors(Long biddingId) {
        return iOrderHeadService.findNotPricingVendors(biddingId);
    }

}

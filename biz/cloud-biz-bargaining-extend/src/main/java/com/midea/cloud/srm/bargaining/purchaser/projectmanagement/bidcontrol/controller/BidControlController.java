package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidcontrol.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidcontrol.service.IBidControlService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.param.BidControlParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo.BidControlListVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo.BidControlTopInfoVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo.StartOrExtendBidingVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *  投标控制 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-09 9:50:34
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidControl")
public class BidControlController extends BaseController {

    @Autowired
    private IBidControlService iBidControlService;

    /**
     * 发起投标
     *
     * @param startOrExtendBidingVO
     */
    @PostMapping("/startBiding")
    public void startBiding(@RequestBody StartOrExtendBidingVO startOrExtendBidingVO) {
        Assert.notNull(startOrExtendBidingVO.getBidingId(), "招标id不能为空");
        Assert.notNull(startOrExtendBidingVO.getEndTime(), "投标结束时间不能为空");
        iBidControlService.startBiding(startOrExtendBidingVO.getBidingId(), startOrExtendBidingVO.getEndTime());
    }

    /**
     * 延长投标
     *
     * @param startOrExtendBidingVO
     */
    @PostMapping("/extendBiding")
    public void extendBiding(@RequestBody StartOrExtendBidingVO startOrExtendBidingVO) {
        Assert.notNull(startOrExtendBidingVO.getBidingId(), "招标id不能为空");
        Assert.notNull(startOrExtendBidingVO.getEndTime(), "延长时间不能为空");
        iBidControlService.extendBiding(startOrExtendBidingVO.getBidingId(), startOrExtendBidingVO.getEndTime(),
                startOrExtendBidingVO.getExtendReason());
    }

    /**
     * 获取头部信息
     *
     * @param bidingId
     */
    @GetMapping("/getTopInfo")
    public BidControlTopInfoVO getTopInfo(Long bidingId) {
        Assert.notNull(bidingId, "招标id不能为空");
        return iBidControlService.getTopInfo(bidingId);
    }

    /**
     * 分页查询
     *
     * @param bidControlParam
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<BidControlListVO> listPage(@RequestBody BidControlParam bidControlParam) {
        Assert.notNull(bidControlParam.getBidingId(), "招标id不能为空");
        return iBidControlService.listPageBidControl(bidControlParam.getBidingId(), bidControlParam.getPageNum(),
                bidControlParam.getPageSize());
    }

}

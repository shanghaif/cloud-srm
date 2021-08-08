package com.midea.cloud.srm.feign.bargaining;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.feign.flow.WorkFlowFeignClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  招投标模块 内部调用Feign接口
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/15
 *  修改内容:
 * </pre>
 */
@FeignClient(value = "cloud-biz-bargaining", contextId = "cloud-biz-bargaining")
public interface BidClient extends WorkFlowFeignClient {

    // 招标项目[supplierBiding] - >>>>>

    /**
     * 待投标统计
     */
    @GetMapping("/supplierCooperate/supplierBiding/countCreate")
    WorkCount supplierBidingCountCreate();

    // 招标项目[supplierBiding] - <<<<<

    /**
     * 采购需求生成招标单
     */
    @PostMapping("/bidInitiating/biding/requirementGenBiding")
    String requirementGenBiding(@RequestBody List<RequirementLine> requirementLine);


    /**
     * 分页查询
     *
     * @param biding
     * @return
     */
    @PostMapping("/bidInitiating/biding/listPage")
    PageInfo<Biding> listPage(@RequestBody Biding biding);


    /**
     * 根据投标单ID查找项目需求明细
     *
     * @param bidingId
     * @return
     */
    @GetMapping("/bidInitiating/bidRequirementLine/getRequirementLinesByBidingId")
    List<BidRequirementLine> getRequirementLinesByBidingId(@RequestParam("bidingId") Long bidingId);

    /**
     * 招标工作流回调接口
     *
     * @param
     * @return
     */
    @PostMapping("/bar-anon/bidInitiating/biding/callBackForWorkFlow")
    void callBackForWorkFlow(@RequestBody Biding biding);

    /**
     * 获取招标
     *
     * @param bidingId
     */
    @GetMapping("/bar-anon/bidInitiating/bargaining/getBargaining")
    Biding getBargaining(@RequestParam("bidingId")Long bidingId);

    @GetMapping("/bar-anon/biding/getBidVendorInfo")
    BidVendor getBidVendorInfo(@RequestParam("vendorId")Long vendorId, @RequestParam("bidingId")Long bidingId);

    @PostMapping("/bid-anon/biding/saveBrgJSON")
    void saveBrgJSON(@RequestBody Map<String,Object> map);

    @GetMapping("/bar-anon/biding/changeBidingApprovalStatus")
    void changeBidingApprovalStatus(@RequestParam("reset") String reset, @RequestParam("bidingNum") String bidingNum);
}

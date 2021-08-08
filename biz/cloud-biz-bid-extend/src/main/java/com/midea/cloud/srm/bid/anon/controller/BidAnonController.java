package com.midea.cloud.srm.bid.anon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service.IBidProcessConfigService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidBidingCurrencyMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidFileMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.model.bid.purchaser.bidprocessconfig.entity.BidProcessConfig;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidBidingCurrency;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidFile;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务内部调用接口(无需登录就可以对微服务模块间暴露)
 * </pre>
 *
 * @author ex_lizp6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-10 18:01
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bid-anon")
public class BidAnonController {

    @Autowired
    private IBidingService iBidingService;
    @Autowired
    private BidBidingCurrencyMapper currencyMapper;
    @Autowired
    private BidFileMapper fileMapper;
    @Autowired
    private BidVendorMapper bidVendorMapper;
    @Autowired
    private IBidRequirementLineService requirementLineService;
    @Autowired
    private IBidProcessConfigService bidProcessConfigService;

    /**
     * 工作流回调接口
     *
     * @param
     * @return
     * @see com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus
     */
    @PostMapping("/bidInitiating/biding/callBackForWorkFlow")
    public void callBackForWorkFlow(@RequestBody Biding biding) {
        iBidingService.callBackForWorkFlow(biding);
    }

    /**
     * 获取招标
     *
     * @param bidingId
     */
    @GetMapping("/bidInitiating/biding/getBiding")
    public Biding getBiding(Long bidingId) {
        Assert.notNull(bidingId, "id不能为空");
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<BidBidingCurrency> currencyByBidId = getCurrencyByBidId(bidingId);
        Biding byId = iBidingService.getById(bidingId);
        if (byId == null) {
            return null;
        }
        if (loginAppUser != null) {
            boolean isSupplier = UserType.VENDOR.name().equals(loginAppUser.getUserType());
            if (isSupplier && YesOrNo.YES.getValue().equals(byId.getShowRateType())) {
                currencyByBidId.forEach(e -> e.setPriceTax(null));
            }
            byId.setCurrencies(currencyByBidId);
        }
        List<BidFile> files = fileMapper.selectList(Wrappers.lambdaQuery(BidFile.class)
                .eq(BidFile::getBidingId, bidingId)
                .eq(Objects.equals(user == null ? null : user.getUserType(), UserType.VENDOR.name()), BidFile::getFileType, "Supplier")
        );
        byId.setFileList(files);
        return byId;
    }

    /**
     * 根据bidId返回
     */
    @GetMapping("/bidInitiating/biding/getCurrencyByBidingId")
    public List<BidBidingCurrency> getCurrencyByBidId(@RequestParam("bidingId") Long bidingId) {
        List<BidBidingCurrency> currencies = currencyMapper.selectList(Wrappers.lambdaQuery(BidBidingCurrency.class).eq(BidBidingCurrency::getBidingId, bidingId));
        return currencies;
    }

    @GetMapping("/biding/getBidVendorInfo")
    BidVendor getBidVendorInfo(@RequestParam("vendorId") Long vendorId, @RequestParam("bidingId") Long bidingId) {
        BidRequirementLine one = requirementLineService.getOne(Wrappers.lambdaQuery(BidRequirementLine.class)
                .select(BidRequirementLine::getRequirementLineId, BidRequirementLine::getPriceJson,BidRequirementLine::getIsSeaFoodFormula)
                .eq(BidRequirementLine::getBidingId, bidingId)
                .last("limit 1")
        );
        if (Objects.equals(one.getIsSeaFoodFormula(), YesOrNo.YES.getValue())) {
            BidVendor bidVendor = new BidVendor();
            bidVendor.setIsSeaFoodPrice(YesOrNo.YES.getValue());
            bidVendor.setPriceJSON(one.getPriceJson());
            return bidVendor;
        }
        BidVendor bidVendor = bidVendorMapper.selectOne(Wrappers.lambdaQuery(BidVendor.class)
                .select(BidVendor::getBidingId,BidVendor::getChooseBaseMaterialPrice)
                .eq(BidVendor::getBidingId, bidingId)
                .eq(BidVendor::getVendorId, vendorId)
        );
        return bidVendor;
    }

    @PostMapping("/bid-anon/biding/saveBidJSON")
    public void saveBidJSON(@RequestBody Map<String, Object> map) {
        Object bidVendorId = map.get("bidVendorId");
        Object data = map.get("data");
        BidVendor vendor = bidVendorMapper.selectOne(Wrappers.lambdaQuery(BidVendor.class)
                .select(BidVendor::getBidVendorId, BidVendor::getChooseBaseMaterialPrice)
                .eq(Objects.nonNull(bidVendorId), BidVendor::getBidVendorId, Long.valueOf(bidVendorId.toString()))
        );
        if (Objects.nonNull(data)) {
            vendor.setChooseBaseMaterialPrice(data.toString());
            bidVendorMapper.updateById(vendor);
        }
    }

    @GetMapping("/biding/changeBidingApprovalStatus")
    public  void changeBidingApprovalStatus(@RequestParam("reset") String reset,@RequestParam("bidingNum") String bidingNum){
        iBidingService.update(Wrappers.lambdaUpdate(Biding.class)
                .set(Biding::getEndEvaluation,reset)
                .set(Biding::getGeneratePriceApproval,reset)
                .eq(Biding::getBidingNum,bidingNum)
        );
    }

    @PostMapping("/bidProcessConfig/list")
    public List<BidProcessConfig> listProcessConfig(@RequestBody BidProcessConfig bidProcessConfig){
        return bidProcessConfigService.list(new QueryWrapper<BidProcessConfig>(bidProcessConfig));
    }
}

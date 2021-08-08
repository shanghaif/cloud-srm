package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bargaining.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidBidingCurrencyMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidFileMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.*;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.BidingVo;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.ProjectInfoVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SubmitApprovalVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.enums.RequirementPricingType;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingInfoQueryDto;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifySourcingType;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingInfoVO;
import com.midea.cloud.srm.model.base.purchase.dto.PurchaseRateCheck;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  招标基础信息表 前端控制器
 * </pre>
 *
 * @author fengdc3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020-09-09 10:53:11
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidInitiating/biding")
public class BidingController extends BaseController {

    @Autowired
    private IBidingService iBidingService;
    @Autowired
    private BidBidingCurrencyMapper currencyMapper;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private BidFileMapper bidFileMapper;
    @Autowired
    private RbacClient rbacClient;
    @Autowired
    private IBidRequirementLineService lineService;
    @Autowired
    private IBidRequirementService requirementService;


    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public BidingVo get(Long id) {

        Assert.notNull(id, "id不能为空");
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Biding byId = iBidingService.getById(id);
        List<BidBidingCurrency> currencyByBidId = getCurrencyByBidId(id);
        if (loginAppUser != null) {
            boolean isSupplier = UserType.VENDOR.name().equals(loginAppUser.getUserType());
            if (isSupplier && YesOrNo.YES.getValue().equals(byId.getShowRateType())) {
                currencyByBidId.forEach(e -> e.setPriceTax(null));
            }

        }
        byId.setCurrencies(currencyByBidId);
        List<BidFile> files = bidFileMapper.selectList(Wrappers.lambdaQuery(BidFile.class)
                .eq(BidFile::getBidingId, id)
                .eq(Objects.equals(user == null ? null : user.getUserType(), UserType.VENDOR.name()), BidFile::getFileType, "Supplier")
        );
        byId.setFileList(files);

        //封装返回信息
        BidingVo bidingVo = new BidingVo();
        BeanUtils.copyProperties(byId, bidingVo);
        User userInfo = rbacClient.getUser(new User().setUserId(byId.getCreatedId()));
        bidingVo.setCreatedByName(userInfo.getNickname());
        bidingVo.setCreatedByDept(userInfo.getDepartment());
        return bidingVo;
    }

    /**
     * 新增
     *
     * @param biding
     */
    @PostMapping("/add")
    public void add(@RequestBody Biding biding) {
        Long id = IdGenrator.generate();
        biding.setBidingId(id);
        iBidingService.save(biding);
    }

    /**
     * 招标立项-项目信息页保存
     *
     * @param projectInfoVO
     */
    @PostMapping("/saveProjectInfo")
    public Long saveProjectInfo(@RequestBody ProjectInfoVO projectInfoVO) {
        return iBidingService.saveProjectInfo(projectInfoVO);
    }

    /**
     * 招标立项-项目信息页更新
     *
     * @param projectInfoVO
     */
    @PostMapping("/updateProjectInfo")
    public void updateProjectInfo(@RequestBody ProjectInfoVO projectInfoVO) {
        Long bidingId = projectInfoVO.getBiding().getBidingId();
        Biding biding = iBidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getAuditStatus, Biding::getBidingId)
                .eq(Biding::getBidingId, bidingId));
        //审批后不操作
        if (Objects.equals(biding.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())) {
            return;
        }
        iBidingService.updateProjectInfo(projectInfoVO);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        iBidingService.removeByBidingId(id);
    }


    /**
     * 废弃订单
     *
     * @param bidingId
     */
    @GetMapping("/abandon")
    public void abandon(Long bidingId) {
        Assert.notNull(bidingId, "废弃订单id不能为空");
        iBidingService.abandon(bidingId);
    }


    /**
     * 修改
     *
     * @param biding
     */
    @PostMapping("/modify")
    public void modify(@RequestBody Biding biding) {
        Biding one = iBidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getAuditStatus, Biding::getBidingId)
                .eq(Biding::getBidingId, biding.getBidingId()));
        //审批后不操作
        if (Objects.equals(one.getAuditStatus(), BiddingApprovalStatus.APPROVED.getValue())) {
            return;
        }
        iBidingService.updatePermission(biding);
    }

    /**
     * 分页查询
     *
     * @param biding
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<Biding> listPage(@RequestBody Biding biding) {
        return iBidingService.listPage(biding);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<Biding> listAll() {
        return iBidingService.list();
    }

    /**
     * 获取立项审批工作流审批参数
     *
     * @return
     */
    @PostMapping("/getInitProjectParam")
    public Map<String, Object> getInitProjectParam(@RequestBody SubmitApprovalVO submitApprovalVO) {
        Long bidingId = submitApprovalVO.getBidingId();
        if (currencyMapper.selectCount(Wrappers.lambdaQuery(BidBidingCurrency.class)
                .eq(BidBidingCurrency::getBidingId, bidingId)
        ) <= 0) {
            throw new BaseException("请先维护报价币种");
        }
        /**
         * 校验是否公式报价，若为公式报价，校验第一行是否有公式，无公式则报错。然后校验第一行是否为海鲜价公式，若为海鲜价公式，检查每一行是否有基价，如果有一行没有基价，则报错
         */
        BidRequirement one = requirementService.getOne(Wrappers.lambdaQuery(BidRequirement.class).select(BidRequirement::getPricingType)
                .eq(BidRequirement::getBidingId, bidingId)
        );
        String pricingType = one.getPricingType();
        if (Objects.equals(RequirementPricingType.FORMULA_PURCHASER.getCode(), pricingType)) {
            List<BidRequirementLine> lines = lineService.list(Wrappers.lambdaQuery(BidRequirementLine.class)
                    .eq(BidRequirementLine::getBidingId, bidingId)

            );
            for (BidRequirementLine line : lines) {
                if (Objects.isNull(line.getFormulaId())) {
                    throw new BaseException("公式报价需要绑定公式id");
                }
                if (Objects.equals(line.getIsSeaFoodFormula(), "Y")) {
                    if (StringUtils.isEmpty(line.getPriceJson())) {
                        throw new BaseException("海鲜价公式需要保存基价");
                    }
                }
            }
        }


        return iBidingService.initProjectWorkFlow(bidingId, submitApprovalVO.getMenuId(),
                CbpmFormTemplateIdEnum.INIT_PROJECT_APPROVAL.getKey(), "立项审批_");
    }

    /**
     * 工作流回调接口
     *
     * @param
     * @return
     * @see BiddingApprovalStatus
     */
    @PostMapping("/callBackForWorkFlow")
    public void callBackForWorkFlow(@RequestBody Biding biding) {
        iBidingService.callBackForWorkFlow(biding);
    }


    /**
     * 获取结项审批工作流审批参数
     *
     * @return
     */
    @PostMapping("/getEndProjectParam")
    public Map<String, Object> getEndProjectParam(@RequestBody SubmitApprovalVO submitApprovalVO) {
        return iBidingService.initProjectWorkFlow(submitApprovalVO.getBidingId(), submitApprovalVO.getMenuId(),
                CbpmFormTemplateIdEnum.END_PROJECT_APPROVAL.getKey(), "结项审批_");
    }

    /**
     * 立项审批(备用)
     *
     * @param bidingId
     */
    @GetMapping("/initProjectApproval")
    public void initProjectApproval(Long bidingId) {
        iBidingService.initProjectApproval(bidingId);
    }

    /**
     * 结项审批(备用)
     *
     * @param bidingId
     */
    @GetMapping("/endProjectApproval")
    public void endProjectApproval(Long bidingId) {
        iBidingService.endProjectApproval(bidingId);
    }

    /**
     * 采购需求生成招标单
     *
     * @param requirementLine
     */
    @PostMapping("/requirementGenBiding")
    public String requirementGenBiding(@RequestBody List<RequirementLine> requirementLine) {
        return iBidingService.requirementGenBiding(requirementLine);
    }

    @GetMapping("/getBidingAuditStatus")
    public boolean getBidingAuditStatus(@RequestParam("bidingId") Long bidingId) {
        Biding one = iBidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getBidingId, Biding::getAuditStatus)
                .eq(Biding::getBidingId, bidingId)
        );
        String auditStatus = Optional.ofNullable(one).orElseThrow(() -> new BaseException(String.format("获取审批状态时候找不到询价单,id:%s", bidingId))).getAuditStatus();
        return Objects.equals(auditStatus, BiddingApprovalStatus.SUBMITTED.getValue())
                || Objects.equals(auditStatus, BiddingApprovalStatus.APPROVED.getValue());
    }

    /**
     * 批量保存招标币种
     *
     * @param currencys
     */
    @PostMapping("/saveBatchCurrency")
    public void createCurrency(@RequestBody List<BidBidingCurrency> currencys) {
        //先判断是否能转成本位币
        Long bidingId = currencys.stream().findAny().orElseThrow(() -> new BaseException("无法找到招标id")).getBidingId();
        Biding one = iBidingService.getOne(Wrappers.lambdaQuery(Biding.class)
                .select(Biding::getStandardCurrency, Biding::getBidingId)
                .eq(Biding::getBidingId, bidingId)
        );
        String standardCurrency = one.getStandardCurrency();
        Set<String> collect = currencys.stream().map(BidBidingCurrency::getCurrencyCode).collect(Collectors.toSet());
        PurchaseRateCheck check = new PurchaseRateCheck();
        check.setFromCode(standardCurrency);
        check.setToCodes(collect);
        List<String> result = baseClient.checkRateFromCodeToCode(check);
        if (!CollectionUtils.isEmpty(result)) {
            throw new BaseException(String.format("请维护本位币%s和[%s]的汇率", standardCurrency, collect.toString()));
        }
        Set<String> codeSet = new HashSet<>();
        List<BidBidingCurrency> currencies = currencyMapper.selectList(Wrappers.lambdaQuery(BidBidingCurrency.class)
                .eq(BidBidingCurrency::getBidingId, bidingId));
        List<Long> deleteIds = new LinkedList<>();
        //先删除不存在的币种
        for (BidBidingCurrency currency : currencies) {
            boolean find = false;
            for (BidBidingCurrency bidBidingCurrency : currencys) {
                if (Objects.equals(bidBidingCurrency.getCurrencyId(), currency.getCurrencyId())) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                deleteIds.add(currency.getCurrencyId());
            }
        }
        for (BidBidingCurrency currency : currencys) {
            if (!codeSet.contains(currency.getCurrencyCode())) {
                if (Objects.isNull(currency.getCurrencyId())) {
                    currency.setPricePrecision(currency.getPricePrecision() == null ? 8 : currency.getPricePrecision());
                    currency.setCurrencyId(IdGenrator.generate());
                    currencyMapper.insert(currency);
                } else {
                    currencyMapper.updateById(currency);
                }
                codeSet.add(currency.getCurrencyCode());
            }

        }
        if (!CollectionUtils.isEmpty(deleteIds)) {
            currencyMapper.delete(Wrappers.lambdaQuery(BidBidingCurrency.class)
                    .in(BidBidingCurrency::getCurrencyId, deleteIds));
        }
    }

    /**
     * 根据bidId返回
     */
    @GetMapping("/getCurrencyByBidingId")
    public List<BidBidingCurrency> getCurrencyByBidId(@RequestParam("bidingId") Long bidingId) {
        List<BidBidingCurrency> currencies = currencyMapper.selectList(Wrappers.lambdaQuery(BidBidingCurrency.class).eq(BidBidingCurrency::getBidingId, bidingId));
        return currencies;
    }

    /**
     * 根据币种code获取汇率
     *
     * @return
     */
    @GetMapping("/getRateByCode")
    public BigDecimal getCurrencyById(@RequestParam String fromCode, @RequestParam String toCode) {
        if (fromCode.equals(toCode)) {
            return BigDecimal.ONE;
        }
        BigDecimal rate = baseClient.getRateByFromTypeAndToType(fromCode, toCode);
        return rate;
    }


    /**
     * 根据id删除币种
     */
    @GetMapping("/deleteCurrencyById")
    public void deleteCurrencyById(@RequestParam Long currencyId) {
        currencyMapper.deleteById(currencyId);
    }

    @PostMapping("/querySourcingInfo")
    public PageInfo<SourcingInfoVO> querySourcingInfo(@RequestBody SourcingInfoQueryDto queryDto) {
        String sourcingType = queryDto.getSourcingType();
        if (StringUtils.isEmpty(sourcingType)) {
            throw new BaseException("寻源类型不能为空");
        }
        if (Objects.equals(sourcingType, ClarifySourcingType.RFQ.getItemValue())) {
            PageUtil.startPage(queryDto.getPageNum(), queryDto.getPageSize());
            List<SourcingInfoVO> result = iBidingService.list(Wrappers.lambdaQuery(Biding.class)
                    .select(Biding::getBidingId, Biding::getBidingNum, Biding::getBidingName)
                    .like(Objects.nonNull(queryDto.getSourcingProjectName()), Biding::getBidingName, queryDto.getSourcingProjectName())
                    .like(Objects.nonNull(queryDto.getSourcingNumber()), Biding::getBidingNum, queryDto.getSourcingNumber())
            ).stream().map(e -> {
                SourcingInfoVO vo = new SourcingInfoVO();
                vo.setSourcingProjectName(e.getBidingName());
                vo.setSourcingId(e.getBidingId());
                vo.setSourcingNumber(e.getBidingNum());
                vo.setSourcingType(sourcingType);
                return vo;
            }).collect(Collectors.toList());
            return new PageInfo<>(result);
        }
        return new PageInfo<>();
    }

    /**
     * 根据招标编码查询单据
     * @param bidingNums
     * @return
     */
    @PostMapping("/listByNumbers")
    public List<Biding> listByNumbers(@RequestBody List<String> bidingNums){
        if(CollectionUtils.isEmpty(bidingNums)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<Biding> wrapper = new QueryWrapper<>();
        wrapper.in("BIDING_NUM",bidingNums);
        return iBidingService.list(wrapper);
    }


}

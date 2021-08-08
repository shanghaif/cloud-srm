package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidingScope;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.SignUpStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.check.PreCheck;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.mapper.QuoteAuthorizeMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.service.IQuoteAuthorizeService;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.param.IntelligentRecommendParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.IntelligentRecommendVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.quoteauthorize.entity.QuoteAuthorize;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.quoteauthorize.vo.QuoteAuthorizeVO;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidSignUpVO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ContactInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  招标-供应商表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-27 17:36:33
 *  修改内容:
 *          </pre>
 */
@Service
public class BidVendorServiceImpl extends ServiceImpl<BidVendorMapper, BidVendor> implements IBidVendorService {

    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private IQuoteAuthorizeService iQuoteAuthorizeService;
    @Autowired
    private IBidingService iBidingService;
    @Autowired
    private IBidRequirementLineService iBidRequirementLineService;

    private final EntityManager<BidVendor> bidVendorDao
            = EntityManager.use(BidVendorMapper.class);
    private final EntityManager<QuoteAuthorize> quoteAuthorizeDao
            = EntityManager.use(QuoteAuthorizeMapper.class);


    /**
     * 当招标范围为公开招标，在供应商端报名的时候，保存一条记录到邀请供应商表
     *
     * @param bidSignUpVO
     * @return
     */
    @Override
    @Transactional
    public Long saveBidVendorWhenSignUp(BidSignUpVO bidSignUpVO) {
        Long bidingId = bidSignUpVO.getBidingId();
        Long vendorId = bidSignUpVO.getVendorId();
        BidVendor bidVendor = getBidVendorByBidingAndVerdonId(bidingId, vendorId);
        boolean addBidVendor = null == bidVendor;
        String join = YesOrNo.NO.getValue();
        if (SignUpStatus.SIGNUPED.getValue().equals(bidSignUpVO.getSignUpStatus())) {
            join = YesOrNo.YES.getValue();
        }
        if (addBidVendor) {
            bidVendor = new BidVendor();
            Long id = IdGenrator.generate();
            BeanCopyUtil.copyProperties(bidVendor, bidSignUpVO);
            bidVendor.setBidVendorId(id);
            bidVendor.setJoinFlag(join);
            bidVendor.setBidingScope(BidingScope.OPEN_TENDER.getValue());
            // 设置供应商编码和名称
            LoginAppUser user = AppUserUtil.getLoginAppUser();
            CompanyInfo companyInfo = supplierClient.getCompanyInfo(vendorId);
            bidVendor.setVendorCode(companyInfo.getCompanyCode());
            bidVendor.setVendorName(companyInfo.getCompanyName());
            // 设置联系人信息
            ContactInfo info = new ContactInfo();
            info.setCompanyId(vendorId);
            ContactInfo contactInfo = supplierClient.getContactInfoByParam(info);
            if (null != contactInfo) {
                bidVendor.setEmail(contactInfo.getEmail());
                bidVendor.setPhone(contactInfo.getPhoneNumber());
                bidVendor.setLinkManName(contactInfo.getContactName());
            }
            this.save(bidVendor);
        } else {
            bidVendor.setJoinFlag(join);
            this.updateById(bidVendor);
        }
        return bidVendor.getBidVendorId();
    }

    /**
     * 根据投标ID和供应商ID查找邀请供应商表是否存在记录
     *
     * @param bidingId
     * @param vendorId
     * @return
     */
    public BidVendor getBidVendorByBidingAndVerdonId(Long bidingId, Long vendorId) {
        return this.getOne(new QueryWrapper<>(new BidVendor().setBidingId(bidingId).setVendorId(vendorId)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreCheck(checkMethod = "preCheck")
    public void saveBidVendorList(List<BidVendor> bidVendorList) {
        //        Assert.isTrue(CollectionUtils.isNotEmpty(bidVendorList), "邀请供应商列表不能为空");
        if (CollectionUtils.isEmpty(bidVendorList))
            return;
        for (BidVendor vendor : bidVendorList) {
            if (StringUtils.isEmpty(vendor.getLinkManName())) {
                throw new BaseException(String.format("供应商名称为%s的联系人%s不能为空", vendor.getVendorName(), vendor.getLinkManName()));
            }
            Long id = IdGenrator.generate();
            /*CompanyInfo companyInfo = supplierClient.getCompanyInfoByParam(new CompanyInfo().setCompanyCode(vendor.getVendorCode()));
            if (companyInfo == null) {
                throw new BaseException(LocaleHandler.getLocaleMsg("未找到该供应商信息,编号", vendor.getVendorCode()));
            }*/
            vendor.setBidVendorId(id).setBidingScope(BidingScope.INVITE_TENDER.getValue()).setJoinFlag("N");
//            .setVendorId(companyInfo.getCompanyId())
        }
        this.saveBatch(bidVendorList);
        iQuoteAuthorizeService.saveQuoteAuthorize(bidVendorList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreCheck(checkMethod = "preCheck")
    public void updateBatch(List<BidVendor> bidVendors) {
        /*   if (CollectionUtils.isEmpty(bidVendors))
            throw new BaseException("邀请供应商不能为空。");*/
        if (CollectionUtils.isEmpty(bidVendors))
            return;
        for (BidVendor vendor : bidVendors) {
            if (StringUtils.isEmpty(vendor.getLinkManName())) {
                throw new BaseException(String.format("供应商名称为%s的联系人%s不能为空", vendor.getVendorName(), vendor.getLinkManName()));
            }
        }
        // 获取 招标单ID
        Long biddingId = bidVendors.stream()
                .filter(bidVendor -> bidVendor.getBidingId() != null)
                .map(BidVendor::getBidingId)
                .findAny()
                .orElseThrow(() -> new BaseException("招标单ID缺失"));

        // 设置 招标单ID
        bidVendors.forEach(bidVendor -> bidVendor.setBidingId(biddingId));


        // 删除数据库中 不存在[bidVendors]里的投标供应商信息 & 对应报价权限
        bidVendorDao.findAll(Wrappers.lambdaQuery(BidVendor.class).eq(BidVendor::getBidingId, biddingId)).stream()
                .filter(existed -> bidVendors.stream().noneMatch(bidVendor -> existed.getBidVendorId().equals(bidVendor.getBidVendorId())))
                .forEach(existed -> {
                    bidVendorDao.delete(existed.getBidVendorId());
                    quoteAuthorizeDao.delete(Wrappers.lambdaQuery(QuoteAuthorize.class).eq(QuoteAuthorize::getBidVendorId, existed.getBidVendorId()));
                });

        // 存储 投标供应商信息 & 报价权限
        bidVendorDao.useInterceptor()
                .beforeCreate(parameter -> {
                    BidVendor bidVendor = parameter.getPrepareCreateEntity();
                   /* CompanyInfo companyInfo = Optional
                            .ofNullable(supplierClient.getCompanyInfoByParam(new CompanyInfo().setCompanyCode(bidVendor.getVendorCode())))
                            .orElseThrow(() -> new BaseException("获取供应商信息失败。 | vendorCode: [" + bidVendor.getVendorCode() + "]"));*/
                    bidVendor.setBidVendorId(IdGenrator.generate())
                            .setBidingScope(BidingScope.INVITE_TENDER.getValue())
//                            .setVendorId(companyInfo.getCompanyId())
                            .setJoinFlag("N");
                })
                .save(bidVendors);
        iQuoteAuthorizeService.saveQuoteAuthorize(bidVendors);
    }

    //检查并设置vendorId
    private void preCheck(List<BidVendor> bidVendors) {
        Long biddingId = bidVendors.stream()
                .filter(bidVendor -> bidVendor.getBidingId() != null)
                .map(BidVendor::getBidingId)
                .findAny()
                .orElseThrow(() -> new BaseException("招标单ID缺失"));
        for (BidVendor bidVendor : bidVendors) {
            bidVendor.setBidingId(biddingId);
            List<QuoteAuthorizeVO> quoteAuthorizes = iQuoteAuthorizeService.findQuoteAuthorizes(biddingId, bidVendor.getVendorId());
            if (CollectionUtils.isEmpty(quoteAuthorizes)) {
                throw new BaseException(String.format("供应商%s没有对应的报价权限", bidVendor.getVendorName()));
            }
        }
        Assert.isTrue(CollectionUtils.isNotEmpty(bidVendors), "邀请供应商列表不能为空");
        for (BidVendor bidVendor : bidVendors) {
            CompanyInfo companyInfo = Optional
                    .ofNullable(supplierClient.getCompanyInfoByParam(new CompanyInfo().setCompanyCode(bidVendor.getVendorCode())))
                    .orElseThrow(() -> new BaseException("获取供应商信息失败。 | vendorCode: [" + bidVendor.getVendorCode() + "]"));
            bidVendor.setVendorId(companyInfo.getCompanyId());
        }

    }

    @Override
    public List<IntelligentRecommendVO> listIntelligentRecommendInfo(Long bidingId) {
        Assert.notNull(bidingId, "招标单id不能为空");
        Biding biding = iBidingService.getById(bidingId);
        List<BidRequirementLine> bidRequirementLineList = iBidRequirementLineService.list(new QueryWrapper<>(
                new BidRequirementLine().setBidingId(bidingId)));
        if (CollectionUtils.isEmpty(bidRequirementLineList)) {
            return null;
        } else {
            List<Long> categoryIdList = bidRequirementLineList.stream().map(BidRequirementLine::getCategoryId).collect(Collectors.toList());
            IntelligentRecommendParam param = new IntelligentRecommendParam();
            param
//                    .setOrgId(biding.getOrgId())
                    .setCategoryIdList(categoryIdList);
            return supplierClient.listIntelligentRecommendInfo(param);
        }
    }

    @Override
    public List<BidVendor> listVendorContactInfo(List<Long> vendorIdList) {

        List<BidVendor> bidVendors = new ArrayList<>();

        // 获取 供应商联系人 & 按[供应商ID]分组
        Map<Long, List<ContactInfo>> vendorContractInfoGroups = supplierClient.listContactInfoByParam(vendorIdList).stream()
                .collect(Collectors.groupingBy(ContactInfo::getCompanyId));

        // 获取 供应商信息 & 按[供应商ID]获取
        Map<Long, CompanyInfo> vendorInfos = supplierClient.getComponyByIds(vendorIdList).stream()
                .collect(Collectors.toMap(CompanyInfo::getCompanyId, x -> x));

        vendorInfos.keySet().forEach(vendorId->{
            // 获取 供应商信息
            CompanyInfo companyInfo = vendorInfos.get(vendorId);

            // 获取 供应商联系人[集]
            List<ContactInfo> contactInfos = vendorContractInfoGroups.getOrDefault(vendorId, Collections.emptyList());

            if(CollectionUtils.isNotEmpty(contactInfos)){
                contactInfos.forEach(contactInfo -> {
                    BidVendor bidVendor = new BidVendor()
                            .setEmail(contactInfo.getEmail())
                            .setPhone(contactInfo.getCeeaContactMethod())
                            .setLinkManName(contactInfo.getContactName())
                            .setVendorId(companyInfo.getCompanyId())
                            .setVendorCode(companyInfo.getCompanyCode())
                            .setVendorName(companyInfo.getCompanyName());
                    bidVendors.add(bidVendor);
                });
            }
        });
        return bidVendors;
    }

}

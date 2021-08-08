package com.midea.cloud.srm.base.datatransfer.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.PriceApprovalStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.srm.base.datatransfer.entity.*;
import com.midea.cloud.srm.base.datatransfer.mapper.DataTransferBidOrderLine1Mapper;
import com.midea.cloud.srm.base.datatransfer.mapper.DataTransferBrgOrderLine1Mapper;
import com.midea.cloud.srm.base.datatransfer.mapper.DataTransferCompanyInfoMapper;
import com.midea.cloud.srm.base.datatransfer.mapper.DataTransferUserMapper;
import com.midea.cloud.srm.base.material.mapper.MaterialItemMapper;
import com.midea.cloud.srm.base.organization.mapper.OrganizationMapper;
import com.midea.cloud.srm.base.ou.mapper.BaseOuDetailMapper;
import com.midea.cloud.srm.base.ou.mapper.BaseOuGroupMapper;
import com.midea.cloud.srm.base.purchase.mapper.PurchaseCategoryMapper;
import com.midea.cloud.srm.base.purchase.mapper.PurchaseCurrencyMapper;
import com.midea.cloud.srm.base.seq.service.ISeqDefinitionService;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuDetail;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuGroup;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuDetailVO;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItemPaymentTerm;
import com.midea.cloud.srm.model.inq.price.enums.SourcingType;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tanjl11
 * @date 2020/10/31 15:14
 */
@Component
@Slf4j
public class DataTransferApprovalPriceTransfer {

    @Autowired
    private DataTransferApprovalBiddingItemService approvalBiddingItemService;
    @Autowired
    private DataTransferApprovalHeaderService approvalHeaderService;
    @Autowired
    private DataTransferBidRequirement1Service bidRequirement1Service;
    @Autowired
    private DataTransferBrgRequirementLine1Service brgRequirementLine1Service;
    @Autowired
    private DataTransferBidOrderLine1Mapper bidOrderLine1Mapper;
    @Autowired
    private DataTransferBrgOrderLine1Mapper brgOrderLine1Mapper;
    @Autowired
    private DataTransferUserMapper userMapper;
    @Autowired
    private ISeqDefinitionService iSeqDefinitionService;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private BaseOuGroupMapper ouGroupMapper;
    @Autowired
    private BaseOuDetailMapper ouDetailMapper;
    @Autowired
    private PurchaseCategoryMapper purchaseCategoryMapper;
    @Autowired
    private MaterialItemMapper materialItemMapper;
    @Autowired
    private PurchaseCurrencyMapper purchaseCurrencyMapper;
    @Autowired
    private DataTransferOrderlinePaymentTermBiService paymentTermBiService;
    @Autowired
    private DataTransferOrderlinePaymentTermBrService paymentTermBrService;
    @Autowired
    private DataTransferApprovalBidingItemPaymentService paymentService;
    @Autowired
    private DataTransferCompanyInfoMapper companyInfoMapper;

    private static final String STANDARD = "STANDARD";

    private static final String ZERO = "-";

    public void transferByNo(String sourcingNo, List<String> orgName) {
        if (sourcingNo.startsWith("BID")) {
            CheckModuleHolder.checkout(Module.BID);
            List<DataTransferBidRequirementLine1> bidRequirementLine1s = bidRequirement1Service.list(Wrappers.lambdaQuery(DataTransferBidRequirementLine1.class)
                    .eq(DataTransferBidRequirementLine1::getRequirementNumber, sourcingNo)
                    .in(!org.springframework.util.CollectionUtils.isEmpty(orgName)
                            , DataTransferBidRequirementLine1::getOrgName, orgName
                    )
            );
            List<DataTransferOrderlinePaymentTermBi> paymentTermBis = new LinkedList<>();
            List<DataTransferBidOrderLine1> bidOrderLine1s = new LinkedList<>();
            if (!org.springframework.util.CollectionUtils.isEmpty(bidRequirementLine1s)) {
                Set<Long> requirementIds = bidRequirementLine1s.stream().map(DataTransferBidRequirementLine1::getRequirementLineId).collect(Collectors.toSet());
                bidOrderLine1s = bidOrderLine1Mapper.selectList(Wrappers.lambdaQuery(DataTransferBidOrderLine1.class)
                        .in(DataTransferBidOrderLine1::getRequirementLineId, requirementIds)
                ).stream().filter(e -> (Objects.equals(e.getSuggestedFlag(), "Y") || Objects.equals(e.getSuggestedFlag(), "P"))
                        && Objects.equals(e.getCreateContractFlag(), "N")
                ).collect(Collectors.toList());
                if (!org.springframework.util.CollectionUtils.isEmpty(bidOrderLine1s)) {
                    List<Long> orderLineIds = bidOrderLine1s.stream()
                            .map(DataTransferBidOrderLine1::getOrderLineId).collect(Collectors.toList());
                    paymentTermBis = paymentTermBiService.list(Wrappers.lambdaQuery(DataTransferOrderlinePaymentTermBi.class)
                            .in(DataTransferOrderlinePaymentTermBi::getOrderlineId, orderLineIds)
                    );
                }else{
                    CheckModuleHolder.release();
                    throw new BaseException("无可用行生成价格审批单");
                }
            }
            CheckModuleHolder.release();
            try {
                generateApprovalsByBidRO(bidRequirementLine1s, bidOrderLine1s, paymentTermBis);
            } finally {
                CheckModuleHolder.release();
            }
        }
        if (sourcingNo.startsWith("RFQ") || sourcingNo.startsWith("RFA")) {
            CheckModuleHolder.checkout(Module.BARGAIN);
            List<DataTransferBrgRequirementLine1> brgRequirementLine1s = brgRequirementLine1Service.list(Wrappers.lambdaQuery(DataTransferBrgRequirementLine1.class)
                    .eq(DataTransferBrgRequirementLine1::getRequirementNumber, sourcingNo)
                    .in(!org.springframework.util.CollectionUtils.isEmpty(orgName)
                            , DataTransferBrgRequirementLine1::getOrgName, orgName
                    )
            );
            List<DataTransferBrgOrderLine1> brgOrderLine1s = new LinkedList<>();
            List<DataTransferOrderlinePaymentTermBr> paymentTermBrs = new LinkedList<>();
            if (!org.springframework.util.CollectionUtils.isEmpty(brgRequirementLine1s)) {
                Set<Long> requirementIds = brgRequirementLine1s.stream().map(DataTransferBrgRequirementLine1::getRequirementLineId).collect(Collectors.toSet());
                brgOrderLine1s = brgOrderLine1Mapper.selectList(Wrappers.lambdaQuery(DataTransferBrgOrderLine1.class)
                        .in(DataTransferBrgOrderLine1::getRequirementLineId, requirementIds)
                ).stream().filter(e -> (Objects.equals(e.getSuggestedFlag(), "Y") || Objects.equals(e.getSuggestedFlag(), "P"))
                        && Objects.equals(e.getCreateContractFlag(), "N")
                ).collect(Collectors.toList());
                if (!org.springframework.util.CollectionUtils.isEmpty(brgOrderLine1s)) {
                    List<Long> orderLineIds = brgOrderLine1s.stream()
                           .map(DataTransferBrgOrderLine1::getOrderLineId).collect(Collectors.toList());
                    paymentTermBrs = paymentTermBrService.list(Wrappers.lambdaQuery(DataTransferOrderlinePaymentTermBr.class)
                            .in(DataTransferOrderlinePaymentTermBr::getOrderlineId, orderLineIds)
                    );
                }else{
                    CheckModuleHolder.release();
                    throw new BaseException("无可用行生成价格审批单");
                }
            }
            CheckModuleHolder.release();
            try {
                generateApprovalsByBrgRO(brgRequirementLine1s, brgOrderLine1s, paymentTermBrs);
            } finally {
                CheckModuleHolder.release();
            }
        }
    }


    //转价格审批单，先把报价行、需求行信息查出来
    public void transferBrgToApprovalHeader() {

        //1.选择2020之后未创建合同的数据，中标标志为Y
        CheckModuleHolder.checkout(Module.BARGAIN);
        List<DataTransferBrgOrderLine1> bidOrderLine1s = brgOrderLine1Mapper.selectList(Wrappers.lambdaQuery(DataTransferBrgOrderLine1.class)
                .eq(DataTransferBrgOrderLine1::getCreateContractFlag, "N")
                .eq(DataTransferBrgOrderLine1::getSuggestedFlag, "Y")
                .ge(DataTransferBrgOrderLine1::getCreationDate, "2020")
        );
        List<Long> orderLineIds = bidOrderLine1s.stream().map(DataTransferBrgOrderLine1::getOrderLineId).collect(Collectors.toList());
        Set<Long> requirementIds = bidOrderLine1s.stream().map(DataTransferBrgOrderLine1::getRequirementLineId).collect(Collectors.toSet());
        List<DataTransferBrgRequirementLine1> brgRequirementLine1s = brgRequirementLine1Service.listByIds(requirementIds);
        List<DataTransferOrderlinePaymentTermBr> paymentTermBrs = paymentTermBrService.list(Wrappers.lambdaQuery(DataTransferOrderlinePaymentTermBr.class)
                .in(DataTransferOrderlinePaymentTermBr::getOrderlineId, orderLineIds)
        );
        CheckModuleHolder.release();
        generateApprovalsByBrgRO(brgRequirementLine1s, bidOrderLine1s, paymentTermBrs);
    }


    public void transferBidToApprovalHeader() {
        CheckModuleHolder.checkout(Module.BID);
        //1.选择2020之后未创建合同的数据，中标标志为Y
        List<DataTransferBidOrderLine1> bidOrderLine1s = bidOrderLine1Mapper.selectList(Wrappers.lambdaQuery(DataTransferBidOrderLine1.class)
                .eq(DataTransferBidOrderLine1::getCreateContractFlag, "N")
                .eq(DataTransferBidOrderLine1::getSuggestedFlag, "Y")
                .ge(DataTransferBidOrderLine1::getCreationDate, "2020")
        );
        Set<Long> requirementIds = bidOrderLine1s.stream().map(DataTransferBidOrderLine1::getRequirementLineId).collect(Collectors.toSet());
        List<DataTransferBidRequirementLine1> bidRequirementLine1s = bidRequirement1Service.listByIds(requirementIds);
        List<Long> orderLineIds = bidOrderLine1s.stream().map(DataTransferBidOrderLine1::getOrderLineId).collect(Collectors.toList());
        List<DataTransferOrderlinePaymentTermBi> paymentTermBis = paymentTermBiService.list(Wrappers.lambdaQuery(DataTransferOrderlinePaymentTermBi.class)
                .in(DataTransferOrderlinePaymentTermBi::getOrderlineId, orderLineIds)
        );
        CheckModuleHolder.release();
        generateApprovalsByBidRO(bidRequirementLine1s, bidOrderLine1s, paymentTermBis);
    }


    private void generateApprovalsByBidRO(List<DataTransferBidRequirementLine1> bidRequirementLine1s, List<DataTransferBidOrderLine1> bidOrderLine1s, List<DataTransferOrderlinePaymentTermBi> paymentTermBis) {
        Map<Long, List<DataTransferBidOrderLine1>> orderRequireMap = bidOrderLine1s.stream().collect(Collectors.groupingBy(DataTransferBidOrderLine1::getRequirementLineId));
        Map<String, List<DataTransferBidRequirementLine1>> numberMap = bidRequirementLine1s.stream().collect(Collectors.groupingBy(DataTransferBidRequirementLine1::getRequirementNumber));
        Map<Long, List<DataTransferOrderlinePaymentTermBi>> paymentTermsMap = paymentTermBis.stream().collect(Collectors.groupingBy(DataTransferOrderlinePaymentTermBi::getOrderlineId));
        Set<String> createBySet = bidRequirementLine1s.stream().map(DataTransferBidRequirementLine1::getCreatedByCode).collect(Collectors.toSet());
        Set<String> vendorCode = bidOrderLine1s.stream().map(DataTransferBidOrderLine1::getVendorCode).collect(Collectors.toSet());
        Map<String, CompanyInfo> companyInfoMap = new HashMap<>();
        CheckModuleHolder.checkout(Module.SUP);
        List<CompanyInfo> companyInfos = companyInfoMapper.selectList(Wrappers.lambdaQuery(CompanyInfo.class)
                .in(CompanyInfo::getCompanyCode, vendorCode)
        );
        if (CollectionUtils.isNotEmpty(companyInfos)) {
            companyInfoMap = companyInfos.stream().collect(Collectors.toMap(CompanyInfo::getCompanyCode, Function.identity()));
        }
        CheckModuleHolder.release();
        CheckModuleHolder.checkout(Module.BASE);
        //获取组织信息
        Set<String> erpOrgNames = new HashSet<>();
        Set<String> erpInvNames = new HashSet<>();
        for (DataTransferBidRequirementLine1 bidRequirementLine1 : bidRequirementLine1s) {
            if (Objects.nonNull(bidRequirementLine1.getCeeaInvName()) && !StringUtil.isEmpty(bidRequirementLine1.getCeeaInvName().trim())) {
                erpInvNames.add(bidRequirementLine1.getCeeaInvName().trim());
            }
            if (Objects.nonNull(bidRequirementLine1.getOrgName()) && !StringUtil.isEmpty(bidRequirementLine1.getOrgName().trim())) {
                erpOrgNames.add(bidRequirementLine1.getOrgName().trim());
            }
        }
        Map<String, Organization> nameOrgMap = null;
        if (CollectionUtils.isNotEmpty(erpOrgNames)) {
            List<Organization> organizations = organizationMapper.selectList(Wrappers.lambdaQuery(Organization.class)
                    .in(Organization::getOrganizationName, erpOrgNames)
            );
            if (CollectionUtils.isNotEmpty(organizations)) {
                nameOrgMap = organizations.stream().peek(e -> e.setOrganizationName(e.getOrganizationName().trim())).collect(Collectors.toMap(Organization::getOrganizationName, Function.identity()));
            }
            if (Objects.nonNull(nameOrgMap)) {
                Set<String> orgSet = nameOrgMap.keySet();
                for (String erpOrgName : erpOrgNames) {
                    if (!orgSet.contains(erpOrgName)) {
                        log.warn(String.format("历史寻源单转价格审批单找不到%s的业务组织信息", erpOrgName));
                    }
                }
            }
        }
        //根据inv分组
        Map<String, Organization> nameInvMap = null;
        if (CollectionUtils.isNotEmpty(erpInvNames)) {
            List<Organization> organizations = organizationMapper.selectList(Wrappers.lambdaQuery(Organization.class)
                    .in(Organization::getOrganizationName, erpInvNames)
            );
            if (CollectionUtils.isNotEmpty(organizations)) {
                nameInvMap = organizations.stream().peek(e -> e.setOrganizationName(e.getOrganizationName().trim())).collect(Collectors.toMap(Organization::getOrganizationName, Function.identity()));
            }
            if (Objects.nonNull(nameInvMap)) {
                Set<String> invSet = nameInvMap.keySet();
                for (String erpInvName : erpInvNames) {
                    if (!invSet.contains(erpInvName)) {
                        log.warn(String.format("历史寻源单转价格审批单找不到%s的库存组织信息", erpInvName));
                    }
                }
            }

        }

        //获取ou组信息
        Set<String> ouNumbers = bidRequirementLine1s.stream().filter(e -> Objects.equals(e.getCeeaIsBaseOu(), "N")).map(DataTransferBidRequirementLine1::getRequirementNumber)
                .collect(Collectors.toSet());
        Map<String, BaseOuGroup> ouInfoMap = null;
        Map<Long, List<BaseOuDetail>> ouDetailMap = null;
        if (CollectionUtils.isNotEmpty(ouNumbers)) {
            List<BaseOuGroup> groups = ouGroupMapper.selectList(Wrappers.lambdaQuery(BaseOuGroup.class)
                    .select(BaseOuGroup::getOuGroupCode, BaseOuGroup::getOuGroupName, BaseOuGroup::getOuGroupId)
                    .in(BaseOuGroup::getOuGroupCode, ouNumbers)
            );
            List<BaseOuDetail> details = ouDetailMapper.selectList(Wrappers.lambdaQuery(BaseOuDetail.class)
                    .in(BaseOuDetail::getOuGroupId, groups.stream().map(BaseOuGroup::getOuGroupId).collect(Collectors.toList()))
            );
            if (Objects.nonNull(groups) && !groups.isEmpty()) {
                ouInfoMap = groups.stream().collect(Collectors.toMap(BaseOuGroup::getOuGroupCode, Function.identity()));
            }
            if (CollectionUtils.isNotEmpty(details)) {
                ouDetailMap = details.stream().collect(Collectors.groupingBy(BaseOuDetail::getOuGroupId));
            }
        }

        //获取分类信息
        Set<String> categoryCodeSet = bidRequirementLine1s.stream().map(DataTransferBidRequirementLine1::getCategoryCode).collect(Collectors.toSet());
        Map<String, List<PurchaseCategory>> purchaseCategoryMap = purchaseCategoryMapper.selectList(Wrappers.lambdaQuery(PurchaseCategory.class)
                .select(PurchaseCategory::getCategoryName, PurchaseCategory::getCategoryCode, PurchaseCategory::getCategoryId, PurchaseCategory::getLevel)
                .in(PurchaseCategory::getCategoryName, categoryCodeSet)
        ).stream().collect(Collectors.groupingBy(PurchaseCategory::getCategoryCode));
        //获取物料信息
        Set<String> materialNumSets = bidRequirementLine1s.stream().map(DataTransferBidRequirementLine1::getTargetNum).collect(Collectors.toSet());
        Map<String, MaterialItem> materialItemMap = materialItemMapper.selectList(Wrappers.lambdaQuery(MaterialItem.class)
                .select(MaterialItem::getMaterialId, MaterialItem::getMaterialCode, MaterialItem::getMaterialName)
                .in(MaterialItem::getMaterialCode, materialNumSets)
        ).stream().collect(Collectors.toMap(MaterialItem::getMaterialCode, Function.identity()));
        //获取币种信息
        Set<String> currencyCodeSets = bidOrderLine1s.stream().map(DataTransferBidOrderLine1::getCeeaCurrencyType).collect(Collectors.toSet());
        Map<String, PurchaseCurrency> currencyMap = purchaseCurrencyMapper.selectList(Wrappers.lambdaQuery(PurchaseCurrency.class)
                .select(PurchaseCurrency::getCurrencyCode, PurchaseCurrency::getCurrencyId,
                        PurchaseCurrency::getCurrencyName)
                .in(PurchaseCurrency::getCurrencyCode, currencyCodeSets)
        ).stream().collect(Collectors.toMap(PurchaseCurrency::getCurrencyCode, Function.identity()));
        CheckModuleHolder.release();
        //获取部门人信息
        CheckModuleHolder.checkout(Module.RBAC);
        Map<String, User> userMap = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                .select(User::getCeeaEmpNo, User::getUsername, User::getUserId)
                .in(User::getCeeaEmpNo, createBySet)
        ).stream().collect(Collectors.toMap(User::getCeeaEmpNo, Function.identity()));
        User zuoshaopeng = userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                .select(User::getUserId, User::getUsername)
                .eq(User::getUsername, "zuoshaopeng"));
        CheckModuleHolder.release();
        List<DataTransferApprovalBiddingItem> items = new LinkedList<>();
        List<DataTransferApprovalHeader> approvalHeaders = new LinkedList<>();
        List<ApprovalBiddingItemPaymentTerm> paymentTerms = new LinkedList<>();
        for (Map.Entry<String, List<DataTransferBidRequirementLine1>> entry : numberMap.entrySet()) {
            DataTransferApprovalHeader approvalHeader = new DataTransferApprovalHeader();
            String title = null;
            String userName = null;
            String num = entry.getKey();
            String currencyCode = null;
            Long userId = null;
            Long headerId = IdGenrator.generate();
            String approvalNo = iSeqDefinitionService.genSequencesNumBase(SequenceCodeConstant.SEQ_INQ_APPROVAL_NO);
            for (DataTransferBidRequirementLine1 bidRequirementLine1 : entry.getValue()) {
                if (Objects.isNull(title)) {
                    title = bidRequirementLine1.getRequirementTitle();
                }
                if (Objects.isNull(userName)) {
                    User user = userMap.get(bidRequirementLine1.getCreatedByCode());
                    if (Objects.nonNull(user)) {
                        userName = user.getUsername();
                    }
                }
                if (Objects.isNull(currencyCode)) {
                    currencyCode = bidRequirementLine1.getCurrencyCode();
                }
                if (Objects.isNull(userId)) {
                    User user = userMap.get(bidRequirementLine1.getCreatedByCode());
                    if (Objects.nonNull(user)) {
                        userId = user.getUserId();
                    }
                }
                List<DataTransferBidOrderLine1> orderLine1s = orderRequireMap.get(bidRequirementLine1.getRequirementLineId());
                for (DataTransferBidOrderLine1 orderLine1 : orderLine1s) {
                    DataTransferApprovalBiddingItem approvalBiddingItem = new DataTransferApprovalBiddingItem();
                    approvalBiddingItem.setPriceType(STANDARD)
                            .setApprovalHeaderId(headerId);
                    //设置OU组||组织信息
                    if (bidRequirementLine1.getCeeaIsBaseOu().equals("Y")) {
                        String orgName = bidRequirementLine1.getOrgName();
                        String invName = bidRequirementLine1.getCeeaInvName();
                        Organization inv = nameInvMap.get(invName);
                        Organization org = nameOrgMap.get(orgName);
                        if (Objects.nonNull(inv)) {
                            approvalBiddingItem.setOrganizationId(inv.getOrganizationId())
                                    .setOrganizationName(inv.getOrganizationName())
                                    .setOrganizationCode(inv.getOrganizationCode());
                        } else {
                            approvalBiddingItem.setOrganizationName(invName);
                        }
                        if (Objects.nonNull(org)) {
                            approvalBiddingItem.setOrgId(org.getOrganizationId())
                                    .setOrgName(org.getOrganizationName())
                                    .setOrgCode(org.getOrganizationCode());
                        } else {
                            approvalBiddingItem.setOrgName(org.getOrganizationName());
                        }
                    } else {
                        BaseOuGroup group = ouInfoMap.get(bidRequirementLine1.getRequirementNumber());
                        if (Objects.nonNull(group)) {
                            approvalBiddingItem.setOuId(group.getOuGroupId())
                                    .setOuNumber(group.getOuGroupCode())
                                    .setOuName(group.getOuGroupName());
                        } else {
                            approvalBiddingItem.setOuNumber(bidRequirementLine1.getRequirementNumber());
                        }

                    }
                    //设置类别信息
                    List<PurchaseCategory> purchaseCategories = purchaseCategoryMap.get(bidRequirementLine1.getCategoryCode());
                    if (CollectionUtils.isNotEmpty(purchaseCategories)) {
                        PurchaseCategory purchaseCategory = purchaseCategories.stream().sorted(new Comparator<PurchaseCategory>() {
                            @Override
                            public int compare(PurchaseCategory o1, PurchaseCategory o2) {
                                return o2.getLevel().compareTo(o1.getLevel());
                            }
                        }).findFirst().get();
                        approvalBiddingItem.setCategoryId(purchaseCategory.getCategoryId())
                                .setCategoryCode(purchaseCategory.getCategoryCode())
                                .setCategoryName(purchaseCategory.getCategoryName());
                    } else {
                        approvalBiddingItem.setCategoryName(bidRequirementLine1.getCategoryName())
                                .setCategoryCode(bidRequirementLine1.getCategoryCode());
                    }
                    //设置用户信息
                    if (userName == null || userId == null) {
                        userName = zuoshaopeng.getUsername();
                        userId = zuoshaopeng.getUserId();
                    }
                    //用户信息
                    approvalBiddingItem.setCreatedBy(userName)
                            .setCreatedId(userId)
                            .setCreatedByIp("127.0.0.1")
                            .setCreationDate(new Date());
                    //头信息
                    approvalBiddingItem
                            .setApprovalHeaderId(headerId)
                            .setApprovalNo(num)
                            .setApprovalBiddingItemId(IdGenrator.generate())
                            //需求行信息
                            .setArrivalPlace(bidRequirementLine1.getCeeaDeliveryPlace())
                            .setNeedNum(BigDecimal.valueOf(bidRequirementLine1.getQuantity()))
                            .setStandardCurrency(currencyCode)
                            .setUnit(bidRequirementLine1.getUomCode());
                    //物料信息
                    MaterialItem materialItem = materialItemMap.get(bidRequirementLine1.getTargetNum());
                    if (Objects.nonNull(materialItem)) {
                        approvalBiddingItem.setItemName(materialItem.getMaterialName())
                                .setItemCode(materialItem.getMaterialCode())
                                .setItemId(materialItem.getMaterialId());
                    } else {
                        approvalBiddingItem.setItemName(bidRequirementLine1.getTargetDesc());
                        approvalBiddingItem.setItemCode(bidRequirementLine1.getTargetNum());
                    }
                    PurchaseCurrency purchaseCurrency = currencyMap.get(orderLine1.getCeeaCurrencyType());
                    if (Objects.nonNull(purchaseCurrency)) {
                        approvalBiddingItem.setCurrencyId(purchaseCurrency.getCurrencyId())
                                .setCurrencyName(purchaseCurrency.getCurrencyName());
                    } else {
                        approvalBiddingItem.setCurrencyName(orderLine1.getCeeaCurrencyType());
                    }
                    //报价行信息
                    approvalBiddingItem.setTaxKey(orderLine1.getTaxKey())
                            .setTaxRate(orderLine1.getTaxRate())
                            .setTaxPrice(orderLine1.getPrice())
                            .setPurchaseRequestNum(bidRequirementLine1.getCeeaPurchaseRequestNum())
                            .setPurchaseRequestRowNum(bidRequirementLine1.getCeeaPurchaseRequestRowNum())
                            .setCurrencyCode(orderLine1.getCeeaCurrencyType())
                            .setLAndT(orderLine1.getCeeaLeadTime())
                            .setQuotaQuantity(orderLine1.getCeeaQuotaQuantity())
                            .setQuotaProportion(new BigDecimal(StringUtil.isEmpty(orderLine1.getCeeaQuotaRatio()) || Objects.equals(ZERO, orderLine1.getCeeaQuotaRatio()) ? "0" : orderLine1.getCeeaQuotaRatio()))
                            .setStartTime(orderLine1.getExpiryDateFrom())
                            .setEndTime(orderLine1.getExpiryDateTo());
                    //供应商信息
                    CompanyInfo companyInfo = companyInfoMap.get(orderLine1.getVendorCode());
                    if (Objects.nonNull(companyInfo)) {
                        approvalBiddingItem.setVendorCode(companyInfo.getCompanyCode())
                                .setVendorId(companyInfo.getCompanyId())
                                .setVendorName(companyInfo.getCompanyName());
                    } else {
                        approvalBiddingItem.setVendorName(orderLine1.getVendorName())
                                .setVendorCode(orderLine1.getVendorCode());
                    }
                    List<DataTransferOrderlinePaymentTermBi> terms = paymentTermsMap.get(orderLine1.getOrderLineId());
                    if (CollectionUtils.isNotEmpty(terms)) {
                        terms.forEach(e ->
                                paymentTerms.add(ApprovalBiddingItemPaymentTerm.builder()
                                        .paymentDayCode(e.getPaymentDayCode())
                                        .paymentTerm(e.getPaymentTerm())
                                        .paymentDay(e.getPaymentDay())
                                        .paymentWay(e.getPaymentWay())
                                        .approvalBiddingItemPaymentTermId(IdGenrator.generate())
                                        .approvalBiddingItemId(approvalBiddingItem.getApprovalBiddingItemId())
                                        .build())
                        );
                    }
                    //ou组json
                    if (Objects.nonNull(approvalBiddingItem.getOuId())) {
                        List<BaseOuDetail> details = ouDetailMap.get(approvalBiddingItem.getOuId());
                        List<ApprovalBiddingItem> tempItems = new LinkedList<>();
                        for (BaseOuDetail baseOuGroupDetailVO : details) {
                            ApprovalBiddingItemVO sub = BeanCopyUtil.copyProperties(approvalBiddingItem, ApprovalBiddingItemVO::new);
                            sub.setOrgId(baseOuGroupDetailVO.getOuId())
                                    .setOrgCode(baseOuGroupDetailVO.getOuCode())
                                    .setOrgName(baseOuGroupDetailVO.getOuName())
                                    .setOrganizationId(baseOuGroupDetailVO.getInvId())
                                    .setOrganizationCode(baseOuGroupDetailVO.getInvCode())
                                    .setOrganizationName(baseOuGroupDetailVO.getInvName());
                            sub.setOuId(null);
                            sub.setOuName(null);
                            sub.setOuNumber(null);
                            List<ApprovalBiddingItemPaymentTerm> tempTerms = terms.stream().map(e -> BeanCopyUtil.copyProperties(e, ApprovalBiddingItemPaymentTerm::new)).collect(Collectors.toList());
                            sub.setApprovalBiddingItemPaymentTermList(tempTerms);
                            tempItems.add(sub);
                        }
                        String subJson = JSON.toJSONString(tempItems);
                        approvalBiddingItem.setOuGroupJson(subJson);
                    }
                    items.add(approvalBiddingItem);
                }
            }
            if (userName == null || userId == null) {
                userName = zuoshaopeng.getUsername();
                userId = zuoshaopeng.getUserId();
            }
            approvalHeader.setApprovalNo(approvalNo)
                    .setApprovalHeaderId(headerId)
                    .setBusinessTitle(title)
                    .setCeeaTitle(title)
                    .setCeeaSourceNo(num)
                    .setSourceType(SourcingType.TENDER.getItemValue())
                    .setStandardCurrency(currencyCode)
                    .setCeeaIfUpdatePriceLibrary("N")
                    .setCreatedBy(userName)
                    .setCreatedByIp("127.0.0.1")
                    .setCreationDate(new Date())
                    .setCreatedId(userId)
                    .setStatus(PriceApprovalStatus.RESULT_PASSED.getValue());
            approvalHeaders.add(approvalHeader);
        }
        CheckModuleHolder.checkout(Module.INQ);
        Set<String> collect = approvalHeaders.stream().map(DataTransferApprovalHeader::getCeeaSourceNo).collect(Collectors.toSet());
        List<Long> ids = approvalHeaderService.list(Wrappers.lambdaQuery(DataTransferApprovalHeader.class)
                .select(DataTransferApprovalHeader::getApprovalHeaderId)
                .in(DataTransferApprovalHeader::getCeeaSourceNo, collect)).stream().map(DataTransferApprovalHeader::getApprovalHeaderId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Long> itemIds = approvalBiddingItemService.list(Wrappers.lambdaQuery(DataTransferApprovalBiddingItem.class)
                    .select(DataTransferApprovalBiddingItem::getApprovalBiddingItemId)
                    .in(DataTransferApprovalBiddingItem::getApprovalHeaderId, ids)
            ).stream().map(DataTransferApprovalBiddingItem::getApprovalBiddingItemId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(itemIds)) {
                paymentService.remove(Wrappers.lambdaQuery(ApprovalBiddingItemPaymentTerm.class)
                        .in(ApprovalBiddingItemPaymentTerm::getApprovalBiddingItemId, itemIds)
                );
                approvalBiddingItemService.removeByIds(itemIds);
            }
            approvalHeaderService.removeByIds(ids);
        }
        if (CollectionUtils.isNotEmpty(approvalHeaders)) {
            approvalHeaderService.saveBatch(approvalHeaders);
        }
        if (CollectionUtils.isNotEmpty(items)) {
            approvalBiddingItemService.saveBatch(items);
        }
        if (CollectionUtils.isNotEmpty(paymentTerms)) {
            paymentService.saveBatch(paymentTerms);
        }
        CheckModuleHolder.release();
    }


    private void generateApprovalsByBrgRO(List<DataTransferBrgRequirementLine1> brgRequirementLine1s, List<DataTransferBrgOrderLine1> brgOrderLine1s, List<DataTransferOrderlinePaymentTermBr> paymentTermBrs) {
        Map<Long, List<DataTransferBrgOrderLine1>> orderRequireMap = brgOrderLine1s.stream().collect(Collectors.groupingBy(DataTransferBrgOrderLine1::getRequirementLineId));
        Map<String, List<DataTransferBrgRequirementLine1>> numberMap = brgRequirementLine1s.stream().collect(Collectors.groupingBy(DataTransferBrgRequirementLine1::getRequirementNumber));
        Map<Long, List<DataTransferOrderlinePaymentTermBr>> brsMap = paymentTermBrs.stream().collect(Collectors.groupingBy(DataTransferOrderlinePaymentTermBr::getOrderlineId));
        Set<String> createBySet = brgRequirementLine1s.stream().map(DataTransferBrgRequirementLine1::getCreatedByCode).collect(Collectors.toSet());
        Set<String> vendorCode = brgOrderLine1s.stream().map(DataTransferBrgOrderLine1::getVendorCode).collect(Collectors.toSet());
        Map<String, CompanyInfo> companyInfoMap = new HashMap<>();
        CheckModuleHolder.checkout(Module.SUP);
        List<CompanyInfo> companyInfos = companyInfoMapper.selectList(Wrappers.lambdaQuery(CompanyInfo.class)
                .in(CompanyInfo::getCompanyCode, vendorCode)
        );
        if (CollectionUtils.isNotEmpty(companyInfos)) {
            companyInfoMap = companyInfos.stream().collect(Collectors.toMap(CompanyInfo::getCompanyCode, Function.identity()));
        }
        CheckModuleHolder.release();
        CheckModuleHolder.checkout(Module.BASE);
        //获取组织信息
        Set<String> erpOrgNames = new HashSet<>();
        Set<String> erpInvNames = new HashSet<>();
        for (DataTransferBrgRequirementLine1 brgRequirementLine1 : brgRequirementLine1s) {
            if (Objects.nonNull(brgRequirementLine1.getCeeaInvName()) && !StringUtil.isEmpty(brgRequirementLine1.getCeeaInvName().trim())) {
                erpInvNames.add(brgRequirementLine1.getCeeaInvName().trim());
            }
            if (Objects.nonNull(brgRequirementLine1.getOrgName()) && !StringUtil.isEmpty(brgRequirementLine1.getOrgName().trim())) {
                erpOrgNames.add(brgRequirementLine1.getOrgName().trim());
            }
        }
        Map<String, Organization> nameOrgMap = null;
        if (CollectionUtils.isNotEmpty(erpOrgNames)) {
            List<Organization> organizations = organizationMapper.selectList(Wrappers.lambdaQuery(Organization.class)
                    .in(Organization::getOrganizationName, erpOrgNames)
            );
            if (CollectionUtils.isNotEmpty(organizations)) {
                nameOrgMap = organizations.stream().peek(e -> e.setOrganizationName(e.getOrganizationName().trim())).collect(Collectors.toMap(Organization::getOrganizationName, Function.identity()));
            }
            if (Objects.nonNull(nameOrgMap)) {
                Set<String> orgSet = nameOrgMap.keySet();
                for (String erpOrgName : erpOrgNames) {
                    if (!orgSet.contains(erpOrgName)) {
                        log.warn(String.format("历史寻源单转价格审批单找不到%s的业务组织信息", erpOrgName));
                    }
                }
            }

        }
        //根据inv分组
        Map<String, Organization> nameInvMap = null;
        if (CollectionUtils.isNotEmpty(erpInvNames)) {
            List<Organization> organizations = organizationMapper.selectList(Wrappers.lambdaQuery(Organization.class)
                    .in(Organization::getOrganizationName, erpInvNames)
            );
            if (CollectionUtils.isNotEmpty(organizations)) {
                nameInvMap = organizations.stream().peek(e -> e.setOrganizationName(e.getOrganizationName().trim())).collect(Collectors.toMap(Organization::getOrganizationName, Function.identity()));
            }
            if (Objects.nonNull(nameInvMap)) {
                Set<String> invSet = nameInvMap.keySet();
                for (String erpInvName : erpInvNames) {
                    if (!invSet.contains(erpInvName)) {
                        log.warn(String.format("历史寻源单转价格审批单找不到%s的库存组织信息", erpInvName));
                    }
                }
            }

        }

        //获取ou组信息
        Set<String> ouNumbers = brgRequirementLine1s.stream().filter(e -> Objects.equals(e.getCeeaIsBaseOu(), "N")).map(DataTransferBrgRequirementLine1::getRequirementNumber)
                .collect(Collectors.toSet());
        Map<String, BaseOuGroup> ouInfoMap = null;
        Map<Long, List<BaseOuDetail>> ouDetailMap = null;
        if (CollectionUtils.isNotEmpty(ouNumbers)) {
            List<BaseOuGroup> groups = ouGroupMapper.selectList(Wrappers.lambdaQuery(BaseOuGroup.class)
                    .select(BaseOuGroup::getOuGroupCode, BaseOuGroup::getOuGroupName, BaseOuGroup::getOuGroupId)
                    .in(BaseOuGroup::getOuGroupCode, ouNumbers)
            );
            List<BaseOuDetail> details = ouDetailMapper.selectList(Wrappers.lambdaQuery(BaseOuDetail.class)
                    .in(BaseOuDetail::getOuGroupId, groups.stream().map(BaseOuGroup::getOuGroupId).collect(Collectors.toList()))
            );
            if (Objects.nonNull(groups) && !groups.isEmpty()) {
                ouInfoMap = groups.stream().collect(Collectors.toMap(BaseOuGroup::getOuGroupCode, Function.identity()));
            }
            if (CollectionUtils.isNotEmpty(details)) {
                ouDetailMap = details.stream().collect(Collectors.groupingBy(BaseOuDetail::getOuGroupId));
            }
        }

        //获取分类信息
        Set<String> categoryCodeSet = brgRequirementLine1s.stream().map(DataTransferBrgRequirementLine1::getCategoryCode).collect(Collectors.toSet());
        Map<String, List<PurchaseCategory>> purchaseCategoryMap = purchaseCategoryMapper.selectList(Wrappers.lambdaQuery(PurchaseCategory.class)
                .select(PurchaseCategory::getCategoryName, PurchaseCategory::getCategoryCode, PurchaseCategory::getCategoryId, PurchaseCategory::getLevel)
                .in(PurchaseCategory::getCategoryName, categoryCodeSet)
        ).stream().collect(Collectors.groupingBy(PurchaseCategory::getCategoryCode));
        //获取物料信息
        Set<String> materialNumSets = brgRequirementLine1s.stream().map(DataTransferBrgRequirementLine1::getTargetNum).collect(Collectors.toSet());
        Map<String, MaterialItem> materialItemMap = materialItemMapper.selectList(Wrappers.lambdaQuery(MaterialItem.class)
                .select(MaterialItem::getMaterialId, MaterialItem::getMaterialCode, MaterialItem::getMaterialName)
                .in(MaterialItem::getMaterialCode, materialNumSets)
        ).stream().collect(Collectors.toMap(MaterialItem::getMaterialCode, Function.identity()));
        //获取币种信息
        Set<String> currencyCodeSets = brgOrderLine1s.stream().map(DataTransferBrgOrderLine1::getCeeaCurrencyType).collect(Collectors.toSet());
        Map<String, PurchaseCurrency> currencyMap = purchaseCurrencyMapper.selectList(Wrappers.lambdaQuery(PurchaseCurrency.class)
                .select(PurchaseCurrency::getCurrencyCode, PurchaseCurrency::getCurrencyId,
                        PurchaseCurrency::getCurrencyName)
                .in(PurchaseCurrency::getCurrencyCode, currencyCodeSets)
        ).stream().collect(Collectors.toMap(PurchaseCurrency::getCurrencyCode, Function.identity()));
        CheckModuleHolder.release();
        //获取部门人信息
        CheckModuleHolder.checkout(Module.RBAC);
        Map<String, User> userMap = userMapper.selectList(Wrappers.lambdaQuery(User.class)
                .select(User::getCeeaEmpNo, User::getUsername, User::getUserId)
                .in(User::getCeeaEmpNo, createBySet)
        ).stream().collect(Collectors.toMap(User::getCeeaEmpNo, Function.identity()));
        User zuoshaopeng = userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                .select(User::getUserId, User::getUsername)
                .eq(User::getUsername, "zuoshaopeng"));
        CheckModuleHolder.release();
        List<DataTransferApprovalBiddingItem> items = new LinkedList<>();
        List<DataTransferApprovalHeader> approvalHeaders = new LinkedList<>();
        List<ApprovalBiddingItemPaymentTerm> paymentTerms = new LinkedList<>();
        for (Map.Entry<String, List<DataTransferBrgRequirementLine1>> entry : numberMap.entrySet()) {
            DataTransferApprovalHeader approvalHeader = new DataTransferApprovalHeader();
            String title = null;
            String userName = null;
            String num = entry.getKey();
            String currencyCode = null;
            Long userId = null;
            Long headerId = IdGenrator.generate();
            String approvalNo = iSeqDefinitionService.genSequencesNumBase(SequenceCodeConstant.SEQ_INQ_APPROVAL_NO);
            for (DataTransferBrgRequirementLine1 bidRequirementLine1 : entry.getValue()) {
                if (Objects.isNull(title)) {
                    title = bidRequirementLine1.getRequirementTitle();
                }
                if (Objects.isNull(userName)) {
                    User user = userMap.get(bidRequirementLine1.getCreatedByCode());
                    if (Objects.nonNull(user)) {
                        userName = user.getUsername();
                    }
                }
                if (Objects.isNull(currencyCode)) {
                    currencyCode = bidRequirementLine1.getCurrencyCode();
                }
                if (Objects.isNull(userId)) {
                    User user = userMap.get(bidRequirementLine1.getCreatedByCode());
                    if (Objects.nonNull(user)) {
                        userId = user.getUserId();
                    }
                }
                List<DataTransferBrgOrderLine1> orderLine1s = orderRequireMap.get(bidRequirementLine1.getRequirementLineId());
                for (DataTransferBrgOrderLine1 orderLine1 : orderLine1s) {
                    DataTransferApprovalBiddingItem approvalBiddingItem = new DataTransferApprovalBiddingItem();
                    approvalBiddingItem.setPriceType(STANDARD)
                            .setApprovalHeaderId(headerId);
                    //设置OU组||组织信息
                    if (bidRequirementLine1.getCeeaIsBaseOu().equals("Y")) {
                        String orgName = bidRequirementLine1.getOrgName();
                        String invName = bidRequirementLine1.getCeeaInvName();
                        Organization inv = nameInvMap.get(invName);
                        Organization org = nameOrgMap.get(orgName);
                        if (Objects.nonNull(inv)) {
                            approvalBiddingItem.setOrganizationId(inv.getOrganizationId())
                                    .setOrganizationName(inv.getOrganizationName())
                                    .setOrganizationCode(inv.getOrganizationCode());
                        } else {
                            approvalBiddingItem.setOrganizationName(invName);
                        }
                        if (Objects.nonNull(org)) {
                            approvalBiddingItem.setOrgId(org.getOrganizationId())
                                    .setOrgName(org.getOrganizationName())
                                    .setOrgCode(org.getOrganizationCode());
                        } else {
                            approvalBiddingItem.setOrgName(orgName);
                        }
                    } else {
                        BaseOuGroup group = ouInfoMap.get(bidRequirementLine1.getRequirementNumber());
                        if (Objects.nonNull(group)) {
                            approvalBiddingItem.setOuId(group.getOuGroupId())
                                    .setOuNumber(group.getOuGroupCode())
                                    .setOuName(group.getOuGroupName());
                        } else {
                            approvalBiddingItem.setOuNumber(bidRequirementLine1.getRequirementNumber());
                        }

                    }
                    //设置类别信息
                    List<PurchaseCategory> purchaseCategories = purchaseCategoryMap.get(bidRequirementLine1.getCategoryCode());
                    if (CollectionUtils.isNotEmpty(purchaseCategories)) {
                        PurchaseCategory purchaseCategory = purchaseCategories.stream().sorted(new Comparator<PurchaseCategory>() {
                            @Override
                            public int compare(PurchaseCategory o1, PurchaseCategory o2) {
                                return o2.getLevel().compareTo(o1.getLevel());
                            }
                        }).findFirst().get();
                        approvalBiddingItem.setCategoryId(purchaseCategory.getCategoryId())
                                .setCategoryCode(purchaseCategory.getCategoryCode())
                                .setCategoryName(purchaseCategory.getCategoryName());
                    } else {
                        approvalBiddingItem.setCategoryName(bidRequirementLine1.getCategoryName())
                                .setCategoryCode(bidRequirementLine1.getCategoryCode());
                    }
                    //设置用户信息
                    if (userName == null || userId == null) {
                        userName = zuoshaopeng.getUsername();
                        userId = zuoshaopeng.getUserId();
                    }
                    //用户信息
                    approvalBiddingItem.setCreatedBy(userName)
                            .setCreatedId(userId)
                            .setCreatedByIp("127.0.0.1")
                            .setCreationDate(new Date());
                    //头信息
                    approvalBiddingItem
                            .setApprovalHeaderId(headerId)
                            .setApprovalNo(num)
                            .setApprovalBiddingItemId(IdGenrator.generate())
                            //需求行信息
                            .setArrivalPlace(bidRequirementLine1.getCeeaDeliveryPlace())
                            .setNeedNum(BigDecimal.valueOf(bidRequirementLine1.getQuantity()))
                            .setStandardCurrency(currencyCode)
                            .setUnit(bidRequirementLine1.getUomCode());
                    //物料信息
                    MaterialItem materialItem = materialItemMap.get(bidRequirementLine1.getTargetNum());
                    if (Objects.nonNull(materialItem)) {
                        approvalBiddingItem.setItemName(materialItem.getMaterialName())
                                .setItemCode(materialItem.getMaterialCode())
                                .setItemId(materialItem.getMaterialId());
                    } else {
                        approvalBiddingItem.setItemName(bidRequirementLine1.getTargetDesc())
                                .setItemCode(bidRequirementLine1.getTargetNum());
                    }

                    PurchaseCurrency purchaseCurrency = currencyMap.get(orderLine1.getCeeaCurrencyType());
                    if (Objects.nonNull(purchaseCurrency)) {
                        approvalBiddingItem.setCurrencyId(purchaseCurrency.getCurrencyId())
                                .setCurrencyName(purchaseCurrency.getCurrencyName());
                    } else {
                        approvalBiddingItem.setCurrencyName(orderLine1.getCeeaCurrencyType());
                    }
                    //报价行信息
                    approvalBiddingItem.setTaxKey(orderLine1.getTaxKey())
                            .setTaxRate(orderLine1.getTaxRate())
                            .setPurchaseRequestNum(bidRequirementLine1.getCeeaPurchaseRequestNum())
                            .setPurchaseRequestRowNum(bidRequirementLine1.getCeeaPurchaseRequestRowNum())
                            .setTaxPrice(orderLine1.getPrice())
                            .setCurrencyCode(orderLine1.getCeeaCurrencyType())
                            .setQuotaQuantity(orderLine1.getCeeaQuotaQuantity())
                            .setLAndT(orderLine1.getCeeaLeadTime())
                            .setQuotaProportion(new BigDecimal(StringUtil.isEmpty(orderLine1.getCeeaQuotaRatio()) || Objects.equals(ZERO, orderLine1.getCeeaQuotaRatio()) ? "0" : orderLine1.getCeeaQuotaRatio()))
                            .setStartTime(orderLine1.getExpiryDateFrom())
                            .setEndTime(orderLine1.getExpiryDateTo());
                    //供应商信息
                    CompanyInfo companyInfo = companyInfoMap.get(orderLine1.getVendorCode());
                    if (Objects.nonNull(companyInfo)) {
                        approvalBiddingItem.setVendorCode(companyInfo.getCompanyCode())
                                .setVendorId(companyInfo.getCompanyId())
                                .setVendorName(companyInfo.getCompanyName());
                    } else {
                        approvalBiddingItem.setVendorName(orderLine1.getVendorName())
                                .setVendorCode(orderLine1.getVendorCode());
                    }
                    List<DataTransferOrderlinePaymentTermBr> orderlinePaymentTermBrs = brsMap.get(orderLine1.getOrderLineId());
                    if (CollectionUtils.isNotEmpty(orderlinePaymentTermBrs)) {
                        orderlinePaymentTermBrs.forEach(e -> {
                            paymentTerms.add(ApprovalBiddingItemPaymentTerm.builder()
                                    .paymentDayCode(e.getPaymentDayCode())
                                    .paymentTerm(e.getPaymentTerm())
                                    .paymentDay(e.getPaymentDay())
                                    .paymentWay(e.getPaymentWay())
                                    .approvalBiddingItemPaymentTermId(IdGenrator.generate())
                                    .approvalBiddingItemId(approvalBiddingItem.getApprovalBiddingItemId())
                                    .build());
                        });
                    }
                    //ou组json
                    if (Objects.nonNull(approvalBiddingItem.getOuId())) {
                        List<BaseOuDetail> details = ouDetailMap.get(approvalBiddingItem.getOuId());
                        List<ApprovalBiddingItem> tempItems = new LinkedList<>();
                        for (BaseOuDetail baseOuGroupDetailVO : details) {
                            ApprovalBiddingItemVO sub = BeanCopyUtil.copyProperties(approvalBiddingItem, ApprovalBiddingItemVO::new);
                            sub.setOrgId(baseOuGroupDetailVO.getOuId())
                                    .setOrgCode(baseOuGroupDetailVO.getOuCode())
                                    .setOrgName(baseOuGroupDetailVO.getOuName())
                                    .setOrganizationId(baseOuGroupDetailVO.getInvId())
                                    .setOrganizationCode(baseOuGroupDetailVO.getInvCode())
                                    .setOrganizationName(baseOuGroupDetailVO.getInvName());
                            sub.setOuId(null);
                            sub.setOuName(null);
                            sub.setOuNumber(null);
                            List<ApprovalBiddingItemPaymentTerm> tempTerms = orderlinePaymentTermBrs.stream().map(e -> BeanCopyUtil.copyProperties(e, ApprovalBiddingItemPaymentTerm::new)).collect(Collectors.toList());
                            sub.setApprovalBiddingItemPaymentTermList(tempTerms);
                            tempItems.add(sub);
                        }
                        String subJson = JSON.toJSONString(tempItems);
                        approvalBiddingItem.setOuGroupJson(subJson);
                    }
                    items.add(approvalBiddingItem);
                }
            }
            if (userName == null || userId == null) {
                userName = zuoshaopeng.getUsername();
                userId = zuoshaopeng.getUserId();
            }
            approvalHeader.setApprovalNo(approvalNo)
                    .setApprovalHeaderId(headerId)
                    .setCeeaTitle(title)
                    .setBusinessTitle(title)
                    .setCeeaSourceNo(num)
                    .setSourceType(SourcingType.TENDER.getItemValue())
                    .setStandardCurrency(currencyCode)
                    .setCeeaIfUpdatePriceLibrary("N")
                    .setCreatedBy(userName)
                    .setCreatedId(userId)
                    .setCreatedByIp("127.0.0.1")
                    .setCreationDate(new Date())
                    .setStatus(PriceApprovalStatus.RESULT_PASSED.getValue());
            approvalHeaders.add(approvalHeader);
        }
        CheckModuleHolder.checkout(Module.INQ);
        Set<String> collect = approvalHeaders.stream().map(DataTransferApprovalHeader::getCeeaSourceNo).collect(Collectors.toSet());
        List<Long> ids = approvalHeaderService.list(Wrappers.lambdaQuery(DataTransferApprovalHeader.class)
                .select(DataTransferApprovalHeader::getApprovalHeaderId)
                .in(DataTransferApprovalHeader::getCeeaSourceNo, collect)).stream().map(DataTransferApprovalHeader::getApprovalHeaderId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Long> itemIds = approvalBiddingItemService.list(Wrappers.lambdaQuery(DataTransferApprovalBiddingItem.class)
                    .select(DataTransferApprovalBiddingItem::getApprovalBiddingItemId)
                    .in(DataTransferApprovalBiddingItem::getApprovalHeaderId, ids)
            ).stream().map(DataTransferApprovalBiddingItem::getApprovalBiddingItemId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(itemIds)) {
                paymentService.remove(Wrappers.lambdaQuery(ApprovalBiddingItemPaymentTerm.class)
                        .in(ApprovalBiddingItemPaymentTerm::getApprovalBiddingItemId, itemIds)
                );
                approvalBiddingItemService.removeByIds(itemIds);
            }
            approvalHeaderService.removeByIds(ids);
        }
        if (CollectionUtils.isNotEmpty(approvalHeaders)) {
            approvalHeaderService.saveBatch(approvalHeaders);
        }
        if (CollectionUtils.isNotEmpty(items)) {
            approvalBiddingItemService.saveBatch(items);
        }
        if (CollectionUtils.isNotEmpty(paymentTerms)) {
            paymentService.saveBatch(paymentTerms);
        }
        CheckModuleHolder.release();
    }
}

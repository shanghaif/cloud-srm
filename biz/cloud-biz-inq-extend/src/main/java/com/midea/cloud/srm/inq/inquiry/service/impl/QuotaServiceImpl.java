package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.inq.QuotaStatus;
import com.midea.cloud.common.enums.inq.ReasonForLimit;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.inq.inquiry.mapper.QuotaMapper;
import com.midea.cloud.srm.inq.inquiry.service.*;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaBuDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.*;
import com.midea.cloud.srm.model.inq.inquiry.vo.QuotaParamVO;
import com.midea.cloud.srm.model.inq.quota.vo.*;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  配额表 服务实现类
 * </pre>
 *
 * @author yourname@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
 */
@Service
public class QuotaServiceImpl extends ServiceImpl<QuotaMapper, Quota> implements IQuotaService {
    @Autowired
    private IQuotaBuService iQuotaBuService;
    @Autowired
    private IQuotaPreinstallService iQuotaPreinstallService;
    @Autowired
    private IQuotaRestrictionsService iQuotaRestrictionsService;
    @Autowired
    private IAgreementRatioService iAgreementRatioService;
    @Autowired
    private IPriceStandardService iPriceStandardService;
    @Resource
    private IQuotaRebateService iQuotaRebateService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private SupcooperateClient supcooperateClient;
    //没有收到上限的供应商
    //private HashMap<Integer, CompanyInfo> companyInfoMap;
    //原始排名的供应商
    //private HashMap<Integer, CompanyInfo> companyInfoListMap;
    //协议比例
    //private List<AgreementRatio> agreementRatioList;
    /**
     * 配额-预设比例集合
     */
    //private List<QuotaPreinstall> quotaPreinstallList;
    /**
     * 配额-配额上下限集合
     */
    //private List<QuotaRestrictions> quotaRestrictionsList;
    /**
     * 配额-差价标准集合
     */
    //private List<PriceStandard> priceStandardList;
    /**
     * 配额-预估返利集合
     */
    //private List<QuotaRebate> quotaRebateList;
    //下限
    //private double sum;
    //配额比例字符串
    //private String quotaPreinstallNumber;
    //供应商配额原因类型
    //private  List<String> companyInfoServiceStatusList;
    //最低供应的的预估返利
    //private Integer minProportion;
    //最低供应商的折息价格
    //private BigDecimal minDiscountPrice;


    @Override
    public List<Quota> quotaList(QuotaDTO quotaDTO) {
        QueryWrapper<Quota> wrapper = new QueryWrapper<>();
        //单据标题模糊查询
        wrapper.like(StringUtils.isNotEmpty(quotaDTO.getQuotaName()), "QUOTA_NAME", quotaDTO.getQuotaName());
        //单据编号模糊查询
        wrapper.like(StringUtils.isNotEmpty(quotaDTO.getQuotaCode()), "QUOTA_CODE", quotaDTO.getQuotaCode());
        //事业部条件查询
        if (quotaDTO.getBuId() != null) {
            String value = String.valueOf(quotaDTO.getBuId());
            wrapper.ge("(SELECT SUM(ID) FROM ceea_inquiry_quota_bu WHERE BU_ID=" + value + ")", 1);
        }
        //创建人模糊查询
        wrapper.like(StringUtils.isNotEmpty(quotaDTO.getCreatedBy()), "CREATED_BY", quotaDTO.getCreatedBy());
        //状态条件查询
        wrapper.eq(StringUtils.isNotEmpty(quotaDTO.getQuotaStatus()), "QUOTA_STATUS", quotaDTO.getQuotaStatus());
        //创建日期开始
        wrapper.ge(quotaDTO.getCreationDate() != null, "CREATION_DATE", quotaDTO.getCreationDate());

        return this.list(wrapper);

    }

    /**
     * 保存和新增
     *
     * @param quotaDTO
     */
    @Transactional
    @Override
    public void updateAndAdd(QuotaDTO quotaDTO) {
        Quota quota = quotaDTO.getQuota();
        List<QuotaBu> quotaBuList = quotaDTO.getQuotaBuList();
        //新增
        if (quota.getQuotaId() == null) {
            Long id = IdGenrator.generate();
            quota.setQuotaId(id);
            //添加生成的编号
            quota.setQuotaCode(baseClient.seqGen(SequenceCodeConstant.SEQ_INQ_QUOTA_CODE));
            //状态默认新建
            quota.setQuotaStatus(QuotaStatus.get("DRAFT"));
            this.save(quota);
        } else {
            this.updateById(quota);
        }
        Long quotaId = quota.getQuotaId();
        //添加事业编码
        if (CollectionUtils.isNotEmpty(quotaBuList)) {
            for (QuotaBu quotaBu : quotaBuList) {
                quotaBu.setQuotaId(quotaId);
                try {
                    if (quotaBu.getId() == null) {
                        iQuotaBuService.save(quotaBu);
                    } else {
                        iQuotaBuService.updateById(quotaBu);
                    }
                } catch (Exception e) {
                    Assert.isTrue(false, "保存的事业部已经被某配额配置使用，请确认后重试。");
                }

            }
        }
        //添加配额差价标准
        if (CollectionUtils.isNotEmpty(quotaDTO.getPriceStandardList())) {
            for (PriceStandard priceStandard : quotaDTO.getPriceStandardList()) {
                priceStandard.setQuotaId(quotaId);
            }
            iPriceStandardService.priceStandardAdd(quotaDTO.getPriceStandardList());
        }
        // 配额-协议比例
        if (CollectionUtils.isNotEmpty(quotaDTO.getAgreementRatioList())) {
            for (AgreementRatio priceStandard : quotaDTO.getAgreementRatioList()) {
                priceStandard.setQuotaId(quotaId);
            }
            iAgreementRatioService.agreementRatioAdd(quotaDTO.getAgreementRatioList());
        }
        // 配额-预设比例
        if (CollectionUtils.isNotEmpty(quotaDTO.getQuotaPreinstallList())) {
            for (QuotaPreinstall priceStandard : quotaDTO.getQuotaPreinstallList()) {
                priceStandard.setQuotaId(quotaId);
            }
            iQuotaPreinstallService.quotaPreinstallAdd(quotaDTO.getQuotaPreinstallList());
        }
        //配额-预估返利
        if (CollectionUtils.isNotEmpty(quotaDTO.getQuotaRebateList())) {
            for (QuotaRebate priceStandard : quotaDTO.getQuotaRebateList()) {
                priceStandard.setQuotaId(quotaId);
            }
            iQuotaRebateService.quotaRebateAdd(quotaDTO.getQuotaRebateList());
        }
        //配额-配额上下限
        if (CollectionUtils.isNotEmpty(quotaDTO.getQuotaRestrictionsList())) {
            for (QuotaRestrictions priceStandard : quotaDTO.getQuotaRestrictionsList()) {
                priceStandard.setQuotaId(quotaId);
            }
            iQuotaRestrictionsService.quotaRestrictionsAdd(quotaDTO.getQuotaRestrictionsList());
        }

    }

    /**
     * 获取单据的所有信息
     *
     * @param id
     * @return
     */
    @Override
    public QuotaDTO getQuota(Long id) {
        QuotaDTO quotaDTO = new QuotaDTO();
        Quota quota = this.getById(id);
        Assert.notNull(quota, "需要编辑的单据不存在");
        //获取单据基本信息
        quotaDTO.setQuota(quota);
        //获取单据关联的事业部信息
        QuotaBuDTO quotaBu = new QuotaBuDTO();
        quotaBu.setQuotaId(id);
        List<QuotaBu> quotaBuDTOList = iQuotaBuService.quotaBuList(quotaBu);
        quotaDTO.setQuotaBuDTOList(quotaBuDTOList);
        //获取配额-预设比例集合
        List<QuotaPreinstall> quotaPreinstallList = iQuotaPreinstallService.quotaPreinstallList(new QuotaPreinstall().setQuotaId(id));
        quotaDTO.setQuotaPreinstallList(quotaPreinstallList);
        //获取配额-配额上下限集合
        List<QuotaRestrictions> quotaRestrictions = iQuotaRestrictionsService.quotaRestrictionsList(new QuotaRestrictions().setQuotaId(id));
        quotaDTO.setQuotaRestrictionsList(quotaRestrictions);
        //获取配额-协议比例集合
        List<AgreementRatio> agreementRatioList = iAgreementRatioService.agreementRatioList(new AgreementRatio().setQuotaId(id));
        quotaDTO.setAgreementRatioList(agreementRatioList);
        //获取配额-差价标准集合
        List<PriceStandard> priceStandardList = iPriceStandardService.priceStandardList(new PriceStandard().setQuotaId(id));
        quotaDTO.setPriceStandardList(priceStandardList);
        //配额-预估返利集合
        List<QuotaRebate> quotaRebateList = iQuotaRebateService.quotaRebateList(new QuotaRebate().setQuotaId(id));
        quotaDTO.setQuotaRebateList(quotaRebateList);
        return quotaDTO;
    }

    /**
     * 删除相关的所有数据
     *
     * @param id
     */
    @Transactional
    @Override
    public void removeAlls(Long id) {
        this.removeById(id);
        iQuotaBuService.remove(new QueryWrapper<QuotaBu>().eq("QUOTA_ID", id));
        iQuotaPreinstallService.remove(new QueryWrapper<QuotaPreinstall>().eq("QUOTA_ID", id));
        iQuotaRestrictionsService.remove(new QueryWrapper<QuotaRestrictions>().eq("QUOTA_ID", id));
        iAgreementRatioService.remove(new QueryWrapper<AgreementRatio>().eq("QUOTA_ID", id));
        iPriceStandardService.remove(new QueryWrapper<PriceStandard>().eq("QUOTA_ID", id));
        iQuotaRebateService.remove(new QueryWrapper<QuotaRebate>().eq("QUOTA_ID", id));
    }

    @Override
    public Map<Integer, QuotaCalculateResult> getCalculate(List<QuotaCalculateParameter> quotaCalculateParameterList) {
        Map<Integer, QuotaCalculateResult> quotaCalculateParameterMap = new HashMap<>();
        //获取所有的获取所有组织与品类的品类服务状态
        List<OrgCategory> byCategoryAllList = supplierClient.getByCategoryAll();
        Map<Long, List<OrgCategory>> byCategoryAllMap = byCategoryAllList.stream().collect(Collectors.toMap(k -> k.getCategoryId() + k.getCompanyId(), e -> new ArrayList<>(Arrays.asList(e)),
                (List<OrgCategory> oldList, List<OrgCategory> newList) -> {
                    oldList.addAll(newList);
                    return oldList;
                }));
        //获取供应商信息
        List<CompanyInfo> companyInfoList = supplierClient.CompanyInfoListAll();
        Assert.isTrue(CollectionUtils.isNotEmpty(companyInfoList), "获取供应商信息失败。");
        Map<Long, CompanyInfo> companyInfosMap = companyInfoList.stream().collect(Collectors.toMap(CompanyInfo::getCompanyId, Function.identity()));
        Date date = new Date();
        // System.out.println("开始："+ DateUtil.format(new Date()));
        for (QuotaCalculateParameter quotaCalculateParameter : quotaCalculateParameterList) {

            // 品类ID
            Long categoryId = quotaCalculateParameter.getCategoryId();
            Assert.notNull(categoryId, "缺少物料品类（物料小类）信息，请重试。");
            //供应商价格信息
            List<VendorPriceInfo> vendorPriceInfoList = quotaCalculateParameter.getVendorPriceInfo();
            Assert.isTrue(CollectionUtils.isNotEmpty(vendorPriceInfoList), "缺少供应商信息，请重试。");
            //排序
            Collections.sort(vendorPriceInfoList, new Comparator<VendorPriceInfo>() {
                @Override
                public int compare(VendorPriceInfo o1, VendorPriceInfo o2) {
                    Assert.notNull(o1.getRank(), "供应商排名不能为空");
                    if (o1.getRank().equals(o2.getRank())) {
                        o2.setRank(o2.getRank() + 1);
                    }
                    return o1.getRank().compareTo(o2.getRank());
                }
            });
            Collections.sort(vendorPriceInfoList, new Comparator<VendorPriceInfo>() {
                @Override
                public int compare(VendorPriceInfo o1, VendorPriceInfo o2) {
                    if (!o2.getRank().equals(o1.getRank() + 1)) {
                        Assert.isTrue(!o1.getRank().equals(o2.getRank()), "供应商排名不能有断层");
                    }
                    return o1.getRank().compareTo(o2.getRank());
                }
            });
            //获取事业部id集合
            List<String> collect = vendorPriceInfoList.stream().map(VendorPriceInfo::getBuCode).collect(Collectors.toList());
            //获取配额id
            List<QuotaBu> quotaBuDTOList = iQuotaBuService.quotaBuList(new QuotaBuDTO().setBuIdList(collect));
            Assert.isTrue(CollectionUtils.isNotEmpty(quotaBuDTOList) || quotaBuDTOList.size() > 1, "找不到符合的配额");
            //获取配额
            QuotaDTO quotaDTO = this.getQuota(quotaBuDTOList.get(0).getQuotaId());
            Assert.isTrue(quotaDTO.getQuota().getQuotaStatus().equals("EFFECT"), "找不到符合的生效的配额配置");
            //获取预设比例
            Assert.isTrue(quotaDTO.getQuotaPreinstallList().size() >= vendorPriceInfoList.size(), "配额预设比例的数量不满足智能决标的供应商数量。");
            //必要信息校验及初始化
            //quotaPreinstallNumber = "";
            Integer minProportion = 0;
            BigDecimal minDiscountPrice=new BigDecimal(0);
            //companyInfoListMap = new HashMap<>();
            //最低折息价的供应商信息集
            Collection<VendorInfo> minDiscountPriceVendorInfo = quotaCalculateParameter.getMinDiscountPriceVendorInfo();
            //获取预估返利
            List<QuotaRebate> quotaRebateList = quotaDTO.getQuotaRebateList();
            //获取最低的预估返利值(取最大)
            for (VendorInfo vendorInfo : minDiscountPriceVendorInfo) {
                for (QuotaRebate quotaRebate : quotaRebateList) {
                    //如果是预估返利的供应商就进行计算
                    if (vendorInfo.getVendorId() == quotaRebate.getVendorId()) {
                        //且是预估返利的供应商的品类的
                        if (categoryId.equals(quotaRebate.getCategoryId())) {
                            minProportion = minProportion.equals(0) ? quotaRebate.getProportion()
                                    : (minProportion < quotaRebate.getProportion() ? quotaRebate.getProportion() : minProportion);
                        }
                    }
                }
            }
            Map<BigDecimal, Collection<VendorInfo>> minDiscountPriceMap = new HashMap<>();
            if (quotaCalculateParameter.getMinDiscountPrice()!=null){
                minDiscountPrice = quotaCalculateParameter.getMinDiscountPrice();
            }
            Collection<VendorInfo> minDiscountPriceVendorInfoList = quotaCalculateParameter.getMinDiscountPriceVendorInfo();
            minDiscountPriceMap.put(minDiscountPrice, minDiscountPriceVendorInfoList);
            //最低供应商的折息价格

            List<Integer> quotaRatioList = this.capacityAwardOfBid(vendorPriceInfoList, categoryId, minDiscountPriceMap, byCategoryAllMap, companyInfosMap, quotaDTO,minProportion,minDiscountPrice);

            Collection<VendorQuotaInfo> vendorQuotaInfoList = new ArrayList<>();
            int i = 0;
            for (VendorPriceInfo vendorPriceInfo : vendorPriceInfoList) {
                vendorQuotaInfoList.add(VendorQuotaInfo.builder()
                        .vendorInfo(vendorPriceInfo.getVendorInfo())
                        .buCode(collect.get(i))
                        .quotaRatio(quotaRatioList.get(i)).build());
                i = i + 1;
            }
            //quotaCalculateParameterMap.put(quotaCalculateParameter.getGroupId(), QuotaCalculateResult.builder().categoryId(categoryId).vendorQuotaInfo(vendorQuotaInfoList).build());

        }
        System.out.println("结束：" + (new Date().getTime() - date.getTime()) / 1000);
        return quotaCalculateParameterMap;
    }

    /**
     * * 智能决标
     *
     * @param vendorPriceInfoList 中标供应商配额时必需的供应商信息
     * @param categoryId          品类id
     * @param minDiscountPriceMap 最低折息价格和对应供应商
     * @return
     */
    public List<Integer> capacityAwardOfBid(List<VendorPriceInfo> vendorPriceInfoList, Long categoryId, Map<BigDecimal, Collection<VendorInfo>> minDiscountPriceMap, Map<Long, List<OrgCategory>> byCategoryAllMap, Map<Long, CompanyInfo> companyInfosMap, QuotaDTO quotaDTO,Integer minProportion,BigDecimal minDiscountPrice) {
        //h获取协议比例的供应商
        List<AgreementRatio> agreementRatioList = quotaDTO.getAgreementRatioList();
        Map<Long, AgreementRatio> agreementRatioMap = agreementRatioList.stream().collect(Collectors.toMap(AgreementRatio::getVendorId, Function.identity()));
        //是否是配额上下限的供应商
        List<QuotaRestrictions> quotaRestrictionsList = quotaDTO.getQuotaRestrictionsList();
        Map<String, QuotaRestrictions> quotaRestrictionsMap = quotaRestrictionsList.stream().collect(Collectors.toMap(QuotaRestrictions::getRestrictionsType, Function.identity()));
        //获取预设比例
        List<QuotaPreinstall> quotaPreinstallList = quotaDTO.getQuotaPreinstallList();
        //获取预估返利
        List<QuotaRebate> quotaRebateList = quotaDTO.getQuotaRebateList();
        Map<Long, QuotaRebate> quotaRebateMap = quotaRebateList.stream().collect(Collectors.toMap(k -> k.getVendorId() + k.getCategoryId(), Function.identity()));
        //获取差价标准
        List<PriceStandard> priceStandardList = quotaDTO.getPriceStandardList();
        Map<Long, PriceStandard> priceStandardMap = priceStandardList.stream().collect(Collectors.toMap(PriceStandard::getCategoryId, Function.identity()));
        //原始排名的供应商
        HashMap<Integer, CompanyInfo> companyInfoListMap=new HashMap<>();

        //必要信息校验及初始化
        //供应商配额原因类型
        Map<Integer, List<String>> companyInfoServiceStatusMap = new HashMap<>();
        List<VendorInfo> VendorInfoList = vendorPriceInfoList.stream().map(VendorPriceInfo::getVendorInfo).collect(Collectors.toList());
        //将获取到的按排名排序
        List<CompanyInfo> componyByIdList = new ArrayList<>();
        for (VendorInfo vendorInfo : VendorInfoList) {
            CompanyInfo companyInfo = companyInfosMap.get(vendorInfo.getVendorId());
            Assert.notNull(companyInfo, "获取对应供应商信息失败，没有对应公司基本信息");
            componyByIdList.add(companyInfo);
        }
        //初始化百分比总量
        double sum = 100;
        //初始化对应排名的百分比
        List<Double> priceList = new ArrayList<>();
        //获取供应商数量
        int size = vendorPriceInfoList.size();
        //供应商排序进map容器
        //==================================================第一次初始化下限赋值===================================================
        for (int i = 0; i < size; i++) {
            List<String> companyInfoServiceStatusList = new ArrayList<>();
            //获取供应商信息
            VendorInfo vendorInfo = vendorPriceInfoList.get(i).getVendorInfo();
            Long vendorId = vendorInfo.getVendorId();
            //根据品类信息和供应商id，获取供应商黑牌，红牌表信息
            List<OrgCategory> orgCategorieList = byCategoryAllMap.get(categoryId + vendorId);
            if (CollectionUtils.isNotEmpty(orgCategorieList)) {
                for (OrgCategory orgCategory : orgCategorieList) {
                    companyInfoServiceStatusList.add(orgCategory.getServiceStatus());
                }
            }
            //获取战略供应商
            companyInfoServiceStatusList.add(componyByIdList.get(i).getVendorClassification());
            //获取是否是新战略供应商
            Order order = new Order().setVendorId(vendorInfo.getVendorId());
            List<Order> orders = supcooperateClient.listParamOrder(order);
            if (CollectionUtils.isEmpty(orders)) {
                companyInfoServiceStatusList.add(ReasonForLimit.NEW_SUPPLIER.name());
            }
            //初始化供应商配额原因类型
            companyInfoServiceStatusMap.put(i, companyInfoServiceStatusList);

            companyInfoListMap.put(i, componyByIdList.get(i));

            QuotaParamVO oneQuotaParamVO = this.getOne(componyByIdList.get(i), agreementRatioMap, quotaRestrictionsMap, companyInfoServiceStatusList, sum);
            priceList.add(oneQuotaParamVO.getPriceDouble());
            sum=oneQuotaParamVO.getSum();
        }

        //=======================================================第二次=====================================================
        //获取初始的配额比例
        priceList = this.getTwoAndFour(priceList,quotaPreinstallList.get(size - 1).getQuotaPreinstallNumber(),companyInfoListMap,sum);
        //======================================================第三次赋值=======================================================
        sum = 100;
        //判断此次品类是不是价差标准的品类
        PriceStandard priceStandard = priceStandardMap.get(categoryId);
        //没有收到上限的供应商
        Map<Integer, CompanyInfo> companyInfoMap=new HashMap<>();
        //获取中标的折息价格列表
        List<BigDecimal> discountPriceList = vendorPriceInfoList.stream().map(VendorPriceInfo::getDiscountPrice).collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            QuotaParamVO threeQuotaParamVO = this.getThree(priceList.get(i), componyByIdList.get(i), i, priceStandard, discountPriceList.get(i), agreementRatioMap, quotaRestrictionsMap, companyInfoServiceStatusMap.get(i), quotaRebateMap, sum, minProportion, minDiscountPrice,companyInfoMap);
            priceList.set(i,threeQuotaParamVO.getPriceDouble());
            sum=threeQuotaParamVO.getSum();
            companyInfoMap=threeQuotaParamVO.getCompanyInfoMap();
        }
        //判断是否全部存在上限
        if (companyInfoMap.isEmpty() && sum > 0) {
            Assert.isTrue(false, "第三次分配系统异常");
        }
        //==============================================================第四次===============================================================
        //获取对应数量的比例
        priceList = this.getTwoAndFour(priceList,quotaPreinstallList.get(companyInfoMap.size() - 1).getQuotaPreinstallNumber(),companyInfoMap,sum);
        //=========================================================第五次=========================================================
        return this.getFive(priceList);
    }

    /**
     * 第一次赋值
     */
    public QuotaParamVO getOne(CompanyInfo companyInfo, Map<Long, AgreementRatio> agreementRatioMap, Map<String, QuotaRestrictions> quotaRestrictionsMap, List<String> companyInfoServiceStatusList, double sum) {
        double num = 0;
        //是否是协议比例的供应商
        AgreementRatio agreementRatio = agreementRatioMap.get(companyInfo.getCompanyId());
        if (agreementRatio != null) {
            String symbolType = agreementRatio.getSymbolType();
            //如果是上线限值就赋值
            if ("GT".equals(symbolType) || "GE".equals(symbolType)) {
                num = agreementRatio.getProportion();
            }
        }
        //是否是配额上下限的供应商
        if (CollectionUtils.isNotEmpty(companyInfoServiceStatusList)) {
            for (String RestrictionsType : companyInfoServiceStatusList) {
                QuotaRestrictions quotaRestrictions = quotaRestrictionsMap.get(RestrictionsType);
                if (quotaRestrictions != null) {
                    String symbolType = quotaRestrictions.getSymbolType();
                    //如果是上限值就赋值
                    if ("GT".equals(symbolType) || "GE".equals(symbolType)) {
                        Integer num2 = quotaRestrictions.getProportion();
                        //选择最大的下限
                        if (num2 > num) {
                            num = num2;
                        }
                    }
                }
            }
        }
        sum = sum - num;
        if (sum < 0) {
            Assert.isTrue(false, "第一次分配系统异常");
        }
        QuotaParamVO quotaParamVO = new QuotaParamVO();
        quotaParamVO.setPriceDouble(num);
        quotaParamVO.setSum(sum);
        return quotaParamVO;
    }

    /**
     * 第二次赋值/第四次赋值
     * 原比例+剩余比例*对应的份额
     *
     * @param priceList
     * @return
     */
    public List<Double> getTwoAndFour(List<Double> priceList,String quotaPreinstallNumber,Map<Integer, CompanyInfo> companyInfoListMap,double sum) {
        //获取第几轮的预设比例--->有运行时的值决定
        int[] arrayInts = StringUtil.strArrayInt(quotaPreinstallNumber);
        //companyInfoListMap来源决定了第几次赋值
        int f = 0;
        for (int i : companyInfoListMap.keySet()) {
            priceList.set(i, priceList.get(i) + (sum * arrayInts[f] / 100));
            f = f + 1;
        }
        return priceList;
    }


    /**
     * 第三次赋值
     *
     * @param price
     * @return
     */
    public QuotaParamVO getThree(Double price, CompanyInfo companyInfo, Integer i, PriceStandard priceStandard, BigDecimal discountPrice, Map<Long, AgreementRatio> agreementRatioMap, Map<String, QuotaRestrictions> quotaRestrictionsMap, List<String> companyInfoServiceStatusList,Map<Long, QuotaRebate> quotaRebateMap,double sum,Integer minProportion,BigDecimal minDiscountPrice,Map<Integer, CompanyInfo> companyInfoMap) {
        boolean falg = true;
        double num = price;
        //是否是协议比例的供应商
        AgreementRatio agreementRatio = agreementRatioMap.get(companyInfo.getCompanyId());
        if (agreementRatio != null) {
            String symbolType = agreementRatio.getSymbolType();
            //如果是上限值就赋值
            if ("LT".equals(symbolType) || "LE".equals(symbolType)) {
                falg = false;
                num = agreementRatio.getProportion();
            }
        }
        //是否是配额上下限的供应商
        if (CollectionUtils.isNotEmpty(companyInfoServiceStatusList)) {
            for (String RestrictionsType : companyInfoServiceStatusList) {
                QuotaRestrictions quotaRestrictions = quotaRestrictionsMap.get(RestrictionsType);
                if (quotaRestrictions != null) {
                    String symbolType = quotaRestrictions.getSymbolType();
                    //如果是上限值就赋值
                    if ("LT".equals(symbolType) || "LE".equals(symbolType)) {
                        falg = false;
                        Integer num2 = quotaRestrictions.getProportion();
                        //选择最小的上限
                        if (num == 0 || num > num2) {
                            num = num2;
                        }
                    }
                }
            }
        }
        //考虑预估返利，是否大于预估返利的价差
        if (priceStandard != null) {
            falg = false;
            int ProportionNum = 0;
            QuotaRebate quotaRebate = quotaRebateMap.get(companyInfo.getCompanyId() + priceStandard.getCategoryId());
            if (quotaRebate!=null){
                ProportionNum = quotaRebate.getProportion();
            }
            //当前供应商的折息价格
            double v = discountPrice.doubleValue() * (100 - ProportionNum);
            //最低供应商的折息价格
            double minv = minDiscountPrice.doubleValue() * (100 - minProportion);
            //当前供应商和最低供应商的折息价格的差价
            double differenceV = Math.abs(v - minv);
            if (differenceV > priceStandard.getSpread()) {
                Integer num2 = priceStandard.getProportion();
                //选择最小的上限
                if (num == 0 || num > num2) {
                    num = num2;
                }
            }
        }
        if (falg) {
            //记录下未考虑上限的供应商
            companyInfoMap.put(i, companyInfo);
        }
        //计算出剩下需要分配的百分比
        sum = sum - num;
        QuotaParamVO quotaParamVO = new QuotaParamVO();
        quotaParamVO.setPriceDouble(num);
        quotaParamVO.setSum(sum);
        quotaParamVO.setCompanyInfoMap(companyInfoMap);
        return quotaParamVO;
    }

    /**
     * 第五次取值
     *
     * @param priceList
     * @return
     */
    public List<Integer> getFive(List<Double> priceList) {
        List<Double> priceListCopy = new ArrayList<>();
        priceListCopy.addAll(priceList);

        int size = priceList.size();
        ArrayList<Integer> priceIntList = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < size; i++) {
            double price = priceList.get(i);
            if (price > 10) {
                int theUnit = (int) (price / 10);
                //获取个位和小数点为
                double difference = price - theUnit * 10;
                priceIntList.add((theUnit * 10) + getCalculate(difference));
            } else {
                priceIntList.add(getCalculate(price));
            }
            num = num + priceIntList.get(i);
        }
        if (num != 100) {
            int num1 = 100 - num;
            double num2 = 0;
            int assign = 0;
            for (int f = 0; f < priceListCopy.size(); f++) {
                if (Math.abs(priceListCopy.get(f) - priceIntList.get(f)) > num2) {
                    num2 = Math.abs(priceListCopy.get(f) - priceIntList.get(f));
                    assign = f;
                }
            }
            priceIntList.set(assign, priceIntList.get(assign) + num1);
        }
        return priceIntList;
    }

    public  int getCalculate(double i) {
        if (i >= 3 && i <= 7) {
            return 5;
        } else if (i <= 2) {
            return 0;
        } else if (i >= 8) {
            return 10;
        } else {
            return getCalculate(Math.round(i));
        }
    }

}


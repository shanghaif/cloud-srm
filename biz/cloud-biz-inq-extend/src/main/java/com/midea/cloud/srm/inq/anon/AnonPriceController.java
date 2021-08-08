package com.midea.cloud.srm.inq.anon;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.inq.price.mapper.ApprovalHeaderMapper;
import com.midea.cloud.srm.inq.price.mapper.InquiryHeaderReportMapper;
import com.midea.cloud.srm.inq.price.mapper.PriceLibraryMapper;
import com.midea.cloud.srm.inq.price.mapper.PriceLibraryPaymentTermMapper;
import com.midea.cloud.srm.inq.price.service.IPriceLibraryService;
import com.midea.cloud.srm.inq.sourcingresult.service.ISourcingResultReportService;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.entity.InquiryHeaderReport;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibraryPaymentTerm;
import com.midea.cloud.srm.model.inq.price.enums.TransferStatus;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.GenerateSourcingResultReportParameter;
import com.midea.cloud.srm.model.inq.sourcingresult.vo.SourcingResultReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tanjl11
 * @date 2020/10/27 17:32
 */
@RestController
@RequestMapping("/inq-anon/price")
public class AnonPriceController {
    @Autowired
    private ISourcingResultReportService sourcingResultReportService;

    @Autowired
    private InquiryHeaderReportMapper reportMapper;

    @Autowired
    private ApprovalHeaderMapper approvalHeaderMapper;

    @Autowired
    private IPriceLibraryService iPriceLibraryService;

    @Autowired
    private PriceLibraryMapper priceLibraryMapper;

    @Autowired
    private PriceLibraryPaymentTermMapper paymentTermMapper;

    @Autowired
    private BaseClient baseClient;

    //此时已经生成了价格审批单，有相关的id
    @PostMapping("/sourcingResultReport/generate")
    public SourcingResultReport generate(@RequestBody GenerateSourcingResultReportParameter parameter) {
        InquiryHeaderReport inquiryHeaderReport = reportMapper.selectOne(Wrappers.lambdaQuery(InquiryHeaderReport.class)
                .eq(InquiryHeaderReport::getBiddingId, parameter.getSourcingFormId()));

        //如果不为空且为完成
        if (Objects.nonNull(inquiryHeaderReport) && Objects.equals(inquiryHeaderReport.getChangeStatus(), TransferStatus.FINISH.getCode())) {
            String reportJson = inquiryHeaderReport.getReportJson();
            SourcingResultReport sourcingResultReport = JSON.parseObject(reportJson, SourcingResultReport.class);
            return sourcingResultReport;
        }
        //如果一开始是空的，直接生成
        if (Objects.isNull(inquiryHeaderReport) || Objects.isNull(inquiryHeaderReport.getChangeStatus())) {
            inquiryHeaderReport = new InquiryHeaderReport();
            inquiryHeaderReport.setBiddingId(parameter.getSourcingFormId())
                    .setReportId(IdGenrator.generate())
                    .setBiddingNo(parameter.getSourcingFormNo())
                    .setInquiryId(parameter.getInquiryId())
                    .setChangeStatus(TransferStatus.CHANGE.getCode());
            SourcingResultReport generate = sourcingResultReportService.generate(parameter);
            String s = JSON.toJSONString(generate);
            inquiryHeaderReport.setReportJson(s).setChangeStatus(TransferStatus.FINISH.getCode());
            reportMapper.insert(inquiryHeaderReport);
            //转换状态
            approvalHeaderMapper.update(null, Wrappers.lambdaUpdate(ApprovalHeader.class)
                    .set(ApprovalHeader::getReportTransferStatus, TransferStatus.FINISH.getCode())
                    .eq(ApprovalHeader::getCeeaSourceNo, parameter.getSourcingFormNo())
            );
            return generate;
        }
        //否则返回空的
        return SourcingResultReport.builder().build();
    }

    @GetMapping("/sourcingResultReport/judegeIsFinishReport")
    public InquiryHeaderReport judegeIsFinishReport(@RequestParam Long bidingId) {
        return reportMapper.selectOne(Wrappers.lambdaQuery(InquiryHeaderReport.class)
                .eq(InquiryHeaderReport::getBiddingId, bidingId)
        );
    }

    @PostMapping("/sourcingResultReport/callWhenFail")
    public void callWhenFail(@RequestBody GenerateSourcingResultReportParameter parameter) {
        InquiryHeaderReport inquiryHeaderReport = new InquiryHeaderReport();
        inquiryHeaderReport.setBiddingId(parameter.getSourcingFormId())
                .setReportId(IdGenrator.generate())
                .setBiddingNo(parameter.getSourcingFormNo())
                .setInquiryId(parameter.getInquiryId())
                .setReportId(IdGenrator.generate())
                .setReportJson(parameter.getFailMsg())
                .setChangeStatus(TransferStatus.FAIL.getCode());
        reportMapper.insert(inquiryHeaderReport);
    }

    @PostMapping("/priceLibrary/getLatestForAnon")
    PriceLibrary getLatestForAnon(@RequestBody PriceLibrary priceLibrary) {
        return iPriceLibraryService.getLatest(priceLibrary);
    }

    @PostMapping("/priceLibrary/getLatestForAnonBatch")
    List<PriceLibrary> getLatestForAnon(@RequestBody Collection<PriceLibrary> priceLibrary) {
        Set<Long> itemIds = new HashSet<>();
        Set<Long> orgIds = new HashSet<>();
        for (PriceLibrary library : priceLibrary) {
            if (Objects.nonNull(library.getItemId())) {
                itemIds.add(library.getItemId());
            }
            if (Objects.nonNull(library.getCeeaOrgId())) {
                orgIds.add(library.getCeeaOrgId());
            }
        }
        if (!CollectionUtils.isEmpty(itemIds) && !CollectionUtils.isEmpty(orgIds)) {
            return priceLibraryMapper.getLastestBatch(orgIds, itemIds);
        }
        return Collections.emptyList();
    }


    /**
     * 获取价格目录信息
     *
     * @param priceLibraries
     * @return
     */
    @PostMapping("/priceLibrary/listPriceLibrary")
    public List<PriceLibrary> listPriceLibrary(@RequestBody List<PriceLibrary> priceLibraries) {
        if (CollectionUtils.isEmpty(priceLibraries)) {
            return new ArrayList<>();
        }
        Set<String> orgCodes = new HashSet<>();
        Set<String> organizationCodes = new HashSet<>();
        Set<String> itemCodes = new HashSet<>();
        Set<String> itemDesc = new HashSet<>();

        //这里添加额外的取价格库价格逻辑
        Set<String> ouQuerySet = buildParam(priceLibraries, orgCodes, organizationCodes, itemCodes, itemDesc);
        orgCodes.addAll(ouQuerySet);
        if (orgCodes.isEmpty() &&
                itemCodes.isEmpty()
        ) {
            return new ArrayList<>();
        }
        List<PriceLibrary> emptyName = new LinkedList<>();
        List<PriceLibrary> notEmptyName = new LinkedList<>();
        priceLibraries.forEach(e -> {
            if (!StringUtils.isEmpty(e.getItemDesc())) {
                notEmptyName.add(e);
            } else {
                emptyName.add(e);
            }
        });
        LambdaQueryWrapper<PriceLibrary> wrapper = Wrappers.lambdaQuery(PriceLibrary.class)
                .eq(PriceLibrary::getPriceType, "STANDARD")
                .le(PriceLibrary::getEffectiveDate, LocalDate.now())
                .ge(PriceLibrary::getExpirationDate, LocalDate.now())
                .in(!orgCodes.isEmpty(), PriceLibrary::getCeeaOrgCode, orgCodes)
                .in(!organizationCodes.isEmpty(), PriceLibrary::getCeeaOrganizationCode, organizationCodes);
        wrapper.nested(e -> {
            e.in(!emptyName.isEmpty(), PriceLibrary::getItemCode, emptyName.stream().map(PriceLibrary::getItemCode).collect(Collectors.toList()));
            if (!notEmptyName.isEmpty()) {
                e.or().nested(y -> {
                    for (PriceLibrary priceLibrary : notEmptyName) {
                        y.nested(q -> q.eq(PriceLibrary::getItemCode, priceLibrary.getItemCode())
                                .eq(!StringUtils.isEmpty(priceLibrary.getItemDesc()), PriceLibrary::getItemDesc, priceLibrary.getItemDesc()))
                                .or()
                        ;
                    }
                });

            }
        });
        List<PriceLibrary> standard = priceLibraryMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(standard)) {
            return Collections.emptyList();
        }
        return standard;
    }


    @PostMapping("/priceLibrary/listPriceLibraryWithPaymentTerm")
    public List<PriceLibrary> listPriceLibraryWithPaymentTerm(@RequestBody Map<String, Object> paramMap) {
        List<PriceLibrary> priceLibraries = JSON.parseArray(paramMap.get("priceList").toString(), PriceLibrary.class);
        LocalDate from = LocalDate.parse(paramMap.get("from").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate to = LocalDate.parse(paramMap.get("to").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (CollectionUtils.isEmpty(priceLibraries)) {
            return new ArrayList<>();
        }
        Set<String> orgCodes = new HashSet<>();
        Set<String> organizationCodes = new HashSet<>();
        Set<String> itemCodes = new HashSet<>();
        Set<String> itemDesc = new HashSet<>();

        //这里添加额外的取价格库价格逻辑
        Set<String> ouQuerySet = buildParam(priceLibraries, orgCodes, organizationCodes, itemCodes, itemDesc);
        orgCodes.addAll(ouQuerySet);
        if (orgCodes.isEmpty() &&
                itemCodes.isEmpty()
        ) {
            return new ArrayList<>();
        }
        List<PriceLibrary> emptyName = new LinkedList<>();
        List<PriceLibrary> notEmptyName = new LinkedList<>();
        priceLibraries.forEach(e -> {
            if (!StringUtils.isEmpty(e.getItemDesc())) {
                notEmptyName.add(e);
            } else {
                emptyName.add(e);
            }
        });
        LambdaQueryWrapper<PriceLibrary> wrapper = Wrappers.lambdaQuery(PriceLibrary.class)
                .eq(PriceLibrary::getPriceType, "STANDARD")
                .le(PriceLibrary::getEffectiveDate, to)
                .ge(PriceLibrary::getExpirationDate, from)
                .in(!orgCodes.isEmpty(), PriceLibrary::getCeeaOrgCode, orgCodes)
                .in(!organizationCodes.isEmpty(), PriceLibrary::getCeeaOrganizationCode, organizationCodes);
        wrapper.nested(e -> {
            e.in(!emptyName.isEmpty(), PriceLibrary::getItemCode, emptyName.stream().map(PriceLibrary::getItemCode).collect(Collectors.toList()));
            if (!notEmptyName.isEmpty()) {
                e.or().nested(y -> {
                    for (PriceLibrary priceLibrary : notEmptyName) {
                        y.nested(q -> q.eq(PriceLibrary::getItemCode, priceLibrary.getItemCode())
                                .eq(!StringUtils.isEmpty(priceLibrary.getItemDesc()), PriceLibrary::getItemDesc, priceLibrary.getItemDesc()))
                                .or()
                        ;
                    }
                });
            }
        });

        List<PriceLibrary> standard = priceLibraryMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(standard)) {
            return Collections.emptyList();
        }
        Set<Long> libraryIds = standard.stream().map(PriceLibrary::getPriceLibraryId).collect(Collectors.toSet());
        Map<Long, List<PriceLibraryPaymentTerm>> map = paymentTermMapper.selectList(Wrappers.lambdaQuery(PriceLibraryPaymentTerm.class)
                .select(PriceLibraryPaymentTerm::getPaymentDay, PriceLibraryPaymentTerm::getPaymentTerm
                        , PriceLibraryPaymentTerm::getPriceLibraryId
                        , PriceLibraryPaymentTerm::getPaymentWay)
                .in(PriceLibraryPaymentTerm::getPriceLibraryId, libraryIds)
        ).stream().collect(Collectors.groupingBy(PriceLibraryPaymentTerm::getPriceLibraryId));
        for (PriceLibrary library : standard) {
            List<PriceLibraryPaymentTerm> priceLibraryPaymentTerms = map.get(library.getPriceLibraryId());
            if (Objects.nonNull(priceLibraryPaymentTerms)) {
                library.setPaymentTerms(priceLibraryPaymentTerms.stream().map(e -> BeanCopyUtil.copyProperties(e, PriceLibraryPaymentTerm::new)).collect(Collectors.toList()));
            }
        }
        return standard;
    }

    private Set<String> buildParam(List<PriceLibrary> priceLibraries, Set<String> orgCodes, Set<String> organizationCodes, Set<String> itemCodes, Set<String> itemDesc) {
        List<DictItem> requireEntity = baseClient.listDictItemByDictCode("REQUIR_ENTITY");
        Map<String, Set<String>> buCodeMap = new HashMap<>();
        requireEntity.stream().map(DictItem::getDictItemCode).map(e -> e.split("-"))
                .forEach(l -> {
                    if (buCodeMap.containsKey(l[0])) {
                        buCodeMap.get(l[0]).add(l[1]);
                    } else {
                        HashSet<String> set = new HashSet<>();
                        set.add(l[1]);
                        buCodeMap.put(l[0], set);
                    }
                });
        Set<String> ouQuerySet = new HashSet<>();

        for (PriceLibrary priceLibrary : priceLibraries) {
            orgCodes.add(priceLibrary.getCeeaOrgCode());
            boolean shouldRemove = false;
            for (DictItem dictItem : requireEntity) {
                if (Objects.equals(priceLibrary.getCeeaOrgCode(), dictItem.getDictItemName())) {
                    String[] split = dictItem.getDictItemCode().split("-");
                    Set<String> ouSet = buCodeMap.get(split[0]);
                    ouQuerySet.addAll(ouSet);
                    shouldRemove = true;
                    break;
                }
            }
            if (!shouldRemove) {
                organizationCodes.add(priceLibrary.getCeeaOrganizationCode());
            }
            if (Objects.nonNull(priceLibrary.getItemDesc())) {
                itemDesc.add(priceLibrary.getItemDesc());
            }
            itemCodes.add(priceLibrary.getItemCode());
        }
        return ouQuerySet;
    }

    /**
     * 根据物料id + 物料名称 + 库存组织id + 供应商id  已上架 + 有效期内 + 价格类型为标准的价格
     *
     * @param priceLibraryParams
     * @return
     */
    @PostMapping("/priceLibrary/listPriceLibraryForTransferOrders")
    public List<PriceLibrary> listPriceLibraryForTransferOrders(@RequestBody List<PriceLibrary> priceLibraryParams) {
        if (CollectionUtils.isEmpty(priceLibraryParams)) {
            return new ArrayList<>();
        }
        Set<Long> materialIds = new HashSet<>();
        Set<String> materialNames = new HashSet<>();
        Set<Long> organizationIds = new HashSet<>();
        Set<Long> vendorIds = new HashSet<>();
        for (PriceLibrary priceLibrary : priceLibraryParams) {
            materialIds.add(priceLibrary.getItemId());
            materialNames.add(priceLibrary.getItemDesc());
            organizationIds.add(priceLibrary.getCeeaOrganizationId());
            vendorIds.add(priceLibrary.getVendorId());
        }
        if (materialIds.isEmpty() &&
                materialNames.isEmpty() &&
                organizationIds.isEmpty() &&
                vendorIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<PriceLibrary> standard = priceLibraryMapper.listPriceLibraryForTransferOrders(materialIds, materialNames, organizationIds, vendorIds);
        if (CollectionUtils.isEmpty(standard)) {
            return Collections.emptyList();
        }
        Set<Long> libraryIds = standard.stream().map(PriceLibrary::getPriceLibraryId).collect(Collectors.toSet());
        Map<Long, List<PriceLibraryPaymentTerm>> map = paymentTermMapper.selectList(Wrappers.lambdaQuery(PriceLibraryPaymentTerm.class)
                .select(PriceLibraryPaymentTerm::getPaymentDay, PriceLibraryPaymentTerm::getPaymentTerm
                        , PriceLibraryPaymentTerm::getPriceLibraryId
                        , PriceLibraryPaymentTerm::getPaymentWay)
                .in(PriceLibraryPaymentTerm::getPriceLibraryId, libraryIds)
        ).stream().collect(Collectors.groupingBy(PriceLibraryPaymentTerm::getPriceLibraryId));
        for (PriceLibrary library : standard) {
            List<PriceLibraryPaymentTerm> priceLibraryPaymentTerms = map.get(library.getPriceLibraryId());
            library.setPaymentTerms(priceLibraryPaymentTerms);
        }
        return standard;
    }
}

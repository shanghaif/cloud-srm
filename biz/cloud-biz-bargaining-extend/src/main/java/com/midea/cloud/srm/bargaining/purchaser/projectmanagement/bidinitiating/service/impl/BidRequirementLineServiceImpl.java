package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.exception.BidException;
import com.midea.cloud.common.handler.SpinnerSheetWriteHandler;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.check.PreCheck;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.*;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.quoteauthorize.service.IQuoteAuthorizeService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.PricingFormulaCalculateClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.dto.BidRequirementLineDto;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.BidRequirementLineImportVO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.formula.dto.calculate.BaseMaterialPriceDTO;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuGroup;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
import com.midea.cloud.srm.model.base.region.dto.AreaDTO;
import com.midea.cloud.srm.model.base.region.dto.AreaPramDTO;
import com.midea.cloud.srm.model.base.region.dto.CityParamDto;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.OuRelatePrice;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.quoteauthorize.entity.QuoteAuthorize;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.midea.cloud.common.enums.bargaining.projectmanagement.evaluation.BidingAwardWayEnum.COMBINED_DECISION;

/**
 * <pre>
 *  招标需求明细表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:  tanjl11@meicloud.com
 *  修改日期: 2020-09-03 17:04:28
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class BidRequirementLineServiceImpl extends ServiceImpl<BidRequirementLineMapper, BidRequirementLine> implements IBidRequirementLineService {

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private IBidingService bidingService;
    @Autowired
    private IBidVendorService iBidVendorService;
    @Autowired
    private IQuoteAuthorizeService iQuoteAuthorizeService;
    @Autowired
    private IOuRelatePriceService relatePriceService;
    @Resource
    private PricingFormulaCalculateClient pricingFormulaCalculateClient;
    @Resource
    private FileCenterClient fileCenterClient;
    @Resource
    private IBidRequirementService iBidRequirementService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreCheck(checkMethod = "checkParam")
    public void saveBidRequirementLineList(List<BidRequirementLine> bidRequirementLineList, BidRequirement bidRequirement) {
        Map<Long, String> materialMap = new HashMap<>();
        HashSet<Long> ouIds = new HashSet<>();
        Map<Long, String> invNameMap = new HashMap<>();
        for (BidRequirementLine line : bidRequirementLineList) {
            line.setQuoteStatus(YesOrNo.YES.getValue());
            Long id = IdGenrator.generate();
            line.setRequirementLineId(id).setRequirementId(bidRequirement.getRequirementId()).
                    setBidingId(bidRequirement.getBidingId());
            if (StringUtils.isBlank(line.getPurchaseRequestNum())) {
                materialMap.put(line.getTargetId(), line.getTargetDesc());
                if (Objects.nonNull(line.getOuId())) {
                    ouIds.add(line.getOuId());
                } else {
                    invNameMap.put(line.getInvId(), line.getInvName());
                }
            }
            //添加基价公式值
            if (!org.springframework.util.CollectionUtils.isEmpty(line.getMaterialPrices())) {
                String nowJSON = JSON.toJSONString(line.getMaterialPrices());
                line.setPriceJson(nowJSON);
            }
        }
        checkMaterialWhetherBelongInv(materialMap, ouIds, invNameMap);
        this.saveBatch(bidRequirementLineList);
    }

    private void checkParam(List<BidRequirementLine> bidRequirementLineList, BidRequirement bidRequirement) {
        Assert.isTrue(CollectionUtils.isNotEmpty(bidRequirementLineList), "需求明细不能为空");
        boolean needGroupFlag = false;
        Biding biding = bidingService.getById(bidRequirement.getBidingId());
        if (COMBINED_DECISION.getValue().equals(biding.getBidingAwardWay())) {
            needGroupFlag = true;
        }
        Set<String> codeSet = new HashSet<>();
        for (BidRequirementLine line : bidRequirementLineList) {
            if (Objects.isNull(line.getQuantity())) {
                throw new BidException(String.format("物料[%s]的[需求数量]不能为空", line.getTargetDesc()));
            }
            if (Objects.nonNull(line.getOuId()) && Objects.nonNull(line.getOrgId())) {
                throw new BaseException(String.format("物料[%s]的业务实体或Ou组只能选一个!", line.getTargetDesc()));
            }
            boolean orgOrInvNull = Objects.isNull(line.getOrgId()) || Objects.isNull(line.getInvId());
            if (orgOrInvNull && Objects.isNull(line.getOuId())) {
                throw new BaseException(String.format("物料[%s]的业务实体、库存组织或Ou组必填!", line.getTargetDesc()));
            }
            //决标方式为“组合决标”时，项目需求中字段“组合”需必填
            if (needGroupFlag) {
                if (StringUtils.isBlank(line.getItemGroup())) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("决标方式为[组合决标]时，项目需求中[组合]需必填"));
                }
            }
            Assert.notNull(line.getItemGroup(), "物料组合不能为空");
            Assert.notNull(line.getTargetNum(), "标的编码不能为空");
            Assert.notNull(line.getTargetDesc(), "标的描述不能为空");
            Assert.notNull(line.getQuantity(), "预计采购数量不能为空");
            long count = biding.getDefaultPriceValidFrom().getTime() - line.getPriceStartTime().getTime();
            if (count / (1000 * 3600 * 24) != 0) {
                throw new BaseException(String.format("物料[%s]的价格起始日期和项目信息不一致", line.getTargetDesc()));
            }
            count=biding.getDefaultPriceValidTo().getTime()-line.getPriceEndTime().getTime();
            if (count / (1000 * 3600 * 24) != 0) {
                throw new BaseException(String.format("物料[%s]的价格结束日期和项目信息不一致", line.getTargetDesc()));
            }
            long dayCount = (line.getPriceEndTime().getTime() - line.getPriceStartTime().getTime()) / (1000 * 3600 * 24);
            if (dayCount > 365) {
                throw new BaseException(String.format("物料[%s]的价格有效期差距不能超过365天", line.getTargetDesc()));
            }
            codeSet.add(line.getCategoryCode());
        }
        List<DictItem> priceBlackList = baseClient.listDictItemByDictCode("PRICE_BLACK_LIST");
        if (!org.springframework.util.CollectionUtils.isEmpty(priceBlackList)) {
            Set<String> blackCode = priceBlackList.stream().map(DictItem::getDictItemCode).collect(Collectors.toSet());
            for (String nowCode : codeSet) {
                if (blackCode.contains(nowCode)&&Objects.equals(biding.getIsSyncToPriceLibrary(),"Y")) {
                    throw new BaseException(String.format("所选需求行中的物料小类编码%s，不满足进入价格库的条件，请您返回上一步，在“项目信息”界面，将“是否进入价格库”，勾选“否”。", nowCode));
                }
            }
        }
    }

    private void checkParam(BidRequirementLine line) {
        Assert.notNull(line, "需求明细不能为空");
        Assert.notNull(line.getItemGroup(), "物料组合不能为空");
        Assert.notNull(line.getTargetNum(), "标的编码不能为空");
        Assert.notNull(line.getTargetDesc(), "标的描述不能为空");
        Assert.notNull(line.getQuantity(), "需求数量不能为空");
        Assert.notNull(line.getQuantity(), "预计采购数量不能为空");
        Assert.notNull(line.getPriceType(), "价格类型不能为空");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreCheck(checkMethod = "checkParam")
    public void updateBatch(List<BidRequirementLine> bidRequirementLineList, BidRequirement bidRequirement) {
        Assert.isTrue(CollectionUtils.isNotEmpty(bidRequirementLineList), "需求明细不能为空");
        Map<Long, String> materialMap = new HashMap<>();
        HashSet<Long> ouIds = new HashSet<>();
        Map<Long, String> invNameMap = new HashMap<>();
        List<BidRequirementLine> addList = new LinkedList<>();
        List<BidRequirementLine> updateList = new LinkedList<>();
        for (BidRequirementLine line : bidRequirementLineList) {
            Long requirementLineId = line.getRequirementLineId();
            //新增
            if (requirementLineId == null) {
                Long id = IdGenrator.generate();
                line.setRequirementLineId(id).setRequirementId(bidRequirement.getRequirementId()).
                        setBidingId(bidRequirement.getBidingId());
                addList.add(line);
            } else {
                //更新
                updateList.add(line);
            }
            if (StringUtils.isBlank(line.getPurchaseRequestNum())) {
                materialMap.put(line.getTargetId(), line.getTargetDesc());
                if (Objects.nonNull(line.getOuId())) {
                    ouIds.add(line.getOuId());
                } else {
                    invNameMap.put(line.getInvId(), line.getInvName());
                }
            }

        }
        checkMaterialWhetherBelongInv(materialMap, ouIds, invNameMap);

        List<BidRequirementLine> shouldDeleteList = list(Wrappers.lambdaQuery(BidRequirementLine.class)
                .select(BidRequirementLine::getRequirementLineId,
                        BidRequirementLine::getTargetId,
                        BidRequirementLine::getQuantity,
                        BidRequirementLine::getAmount,
                        BidRequirementLine::getPriceStartTime,
                        BidRequirementLine::getPriceEndTime,
                        BidRequirementLine::getOuNumber,
                        BidRequirementLine::getDeliveryPlace,
                        BidRequirementLine::getPriceType,
                        BidRequirementLine::getPurchaseType,
                        BidRequirementLine::getTradeTerm,
                        BidRequirementLine::getTransportType,
                        BidRequirementLine::getShowRequireNum,
                        BidRequirementLine::getWarrantyPeriod,
                        BidRequirementLine::getCategoryCode
                )
                .eq(BidRequirementLine::getRequirementId, bidRequirement.getRequirementId())
        );
        List<BidRequirementLine> waitToJudgeChangeList = new LinkedList<>();
        for (int i = shouldDeleteList.size() - 1; i >= 0; i--) {
            boolean find = false;
            BidRequirementLine shouldDeleteEntity = shouldDeleteList.get(i);
            for (BidRequirementLine bidRequirementLine : updateList) {
                if (Objects.equals(bidRequirementLine.getRequirementLineId(), shouldDeleteEntity.getRequirementLineId())) {
                    find = true;
                    break;
                }
            }
            //如果找到把他加入待判断列表，剩下的要删除
            if (find) {
                waitToJudgeChangeList.add(shouldDeleteList.remove(i));
            }
        }
        if (CollectionUtils.isNotEmpty(shouldDeleteList)) {
            List<Long> shouldDeleteIds = shouldDeleteList.stream().map(BidRequirementLine::getRequirementLineId).collect(Collectors.toList());
            removeByIds(shouldDeleteIds);
            relatePriceService.remove(Wrappers.lambdaQuery(OuRelatePrice.class)
                    .in(OuRelatePrice::getRequirementLineId, shouldDeleteIds));
            iQuoteAuthorizeService.remove(Wrappers.lambdaQuery(QuoteAuthorize.class)
                    .in(QuoteAuthorize::getRequirementLineId, shouldDeleteIds)
            );
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            saveBatch(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            Map<Long, BidRequirementLine> judgeMap = waitToJudgeChangeList.stream().collect(Collectors.toMap(e -> e.getRequirementLineId(), Function.identity()));
            for (int i = updateList.size() - 1; i >= 0; i--) {
                BidRequirementLine current = updateList.get(i);
                BidRequirementLine temp = judgeMap.get(current.getRequirementLineId());
                boolean isFieldAllEquals = ObjectUtil.isFieldAllEquals(temp, current);
                boolean jsonEquals = true;
                //添加基价公式值
                if (!org.springframework.util.CollectionUtils.isEmpty(current.getMaterialPrices())) {
                    Collections.sort(current.getMaterialPrices(), Comparator.comparing(BaseMaterialPriceDTO::getBaseMaterialId));
                    String priceJson = temp.getPriceJson();
                    String nowJSON = JSON.toJSONString(current.getMaterialPrices());
                    jsonEquals = Objects.equals(priceJson, nowJSON);
                    current.setPriceJson(nowJSON);
                }
                if (isFieldAllEquals && jsonEquals) {
                    updateList.remove(i);
                }
            }
            judgeMap = null;
            waitToJudgeChangeList = null;
            if (CollectionUtils.isNotEmpty(updateList)) {
                updateBatchById(updateList);
            }
        }
    }

    @Override
    @Transactional
    public void updateTargetPriceBatch(List<BidRequirementLine> bidRequirementLineList) {
//        Assert.isTrue(CollectionUtils.isNotEmpty(bidRequirementLineList), "拦标价列表不能为空");
        //校验数据一致
        Long bidingId = bidRequirementLineList.get(0).getBidingId();
        Integer checkCount = this.count(new QueryWrapper<>(new BidRequirementLine().setBidingId(bidingId)));
        List<String> targetNumList = bidRequirementLineList.stream().map(BidRequirementLine::getTargetNum).collect(Collectors.toList());
        long inputCount = targetNumList.stream().distinct().count();
        if (checkCount.longValue() != inputCount) {
            throw new BaseException(LocaleHandler.getLocaleMsg("数据错误,请检查导入数据"));
        }

        for (BidRequirementLine line : bidRequirementLineList) {
//            Assert.notNull(line.getTargetPrice(), "拦标价不能为空");
            if (StringUtils.isBlank(line.getTargetNum()) || line.getBidingId() == null) {
                throw new BaseException(LocaleHandler.getLocaleMsg("标的编码或招标id不能为空"));
            }
            BidRequirementLine oldLine = this.getOne(new QueryWrapper<>(
                    new BidRequirementLine().setTargetNum(line.getTargetNum()).setBidingId(line.getBidingId())));
            if (oldLine == null) {
                throw new BaseException(LocaleHandler.getLocaleMsg("标的编码不存在,请检查", line.getTargetNum()));
            }
            if (!line.getBidingId().equals(oldLine.getBidingId())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("标的编码不正确,请检查", line.getTargetNum()));
            }

            BidRequirementLine setLine = new BidRequirementLine();
            setLine.setTargetPrice(line.getTargetPrice());
            this.update(setLine, new QueryWrapper<>(oldLine));
        }
    }

    @Override
    public PageInfo<BidRequirementLine> listPage(BidRequirementLine bidRequirementLine) {
        PageUtil.startPage(bidRequirementLine.getPageNum(), bidRequirementLine.getPageSize());
        QueryWrapper<BidRequirementLine> wrapper = new QueryWrapper<BidRequirementLine>(bidRequirementLine);
        List<BidRequirementLine> bidRequirementLineList = this.list(wrapper);
        for (BidRequirementLine requirementLine : bidRequirementLineList) {
            if (StringUtils.isNotBlank(requirementLine.getPriceJson())) {
                requirementLine.setMaterialPrices(JSON.parseArray(requirementLine.getPriceJson(), BaseMaterialPriceDTO.class));
            }
        }
        return new PageInfo<BidRequirementLine>(bidRequirementLineList);
    }

    @Override
    public List<BidRequirementLine> importExcelInfo(List<Object> list) {
        List<BidRequirementLine> bidRequirementLineList = new ArrayList<>();
        List<String> orgNameList = new ArrayList<>();
        List<String> categoryNameList = new ArrayList<>();
        List<String> targetNumList = new ArrayList<>();
        List<String> uomCodeList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);

            BidRequirementLineImportVO vo = (BidRequirementLineImportVO) obj;
            BidRequirementLine line = new BidRequirementLine();
            //获取税率值

            PurchaseTax purchaseTax = baseClient.getByTaxKeyAndLanguage(vo.getTaxKey(), LocaleHandler.getLocaleKey());
            Assert.notNull(purchaseTax, "第" + (i + 1) + "税率编码不存在");
            checkParam(line);
            BeanCopyUtil.copyProperties(line, vo);
            line.setTaxRate(purchaseTax.getTaxCode());

            orgNameList.add(line.getOrgName());
            categoryNameList.add(line.getCategoryName());
            targetNumList.add(line.getTargetNum());
            uomCodeList.add(line.getUomCode());

            bidRequirementLineList.add(line);
        }
        log.debug("需求行列表：" + JSON.toJSONString(bidRequirementLineList));

        List<Organization> organizationList = baseClient.getOrganizationByNameList(orgNameList);
        Map<String, Long> organizatioMap = organizationList.stream().collect(
                Collectors.toMap(Organization::getOrganizationName, Organization::getOrganizationId));
        log.debug("查询事业部信息：" + JSON.toJSONString(organizatioMap));

        List<PurchaseCategory> purchaseCategoryList = baseClient.listPurchaseCategoryByNameBatch(categoryNameList);
        Map<String, Long> purchaseCategoryMap = purchaseCategoryList.stream().collect(
                Collectors.toMap(PurchaseCategory::getCategoryName, PurchaseCategory::getCategoryId));
        log.debug("查询采购分类信息：" + JSON.toJSONString(purchaseCategoryMap));

        List<MaterialItem> materialItemList = baseClient.listMaterialByCodeBatch(targetNumList);
        Map<String, MaterialItem> materialItemMap = materialItemList.stream().collect(
                Collectors.toMap(MaterialItem::getMaterialCode, materialItem -> materialItem));
        log.debug("查询物料信息：" + JSON.toJSONString(materialItemMap));

        List<PurchaseUnit> purchaseUnitList = baseClient.listPurchaseUnitByCodeList(uomCodeList);
        Map<String, String> purchaseUnitMap = purchaseUnitList.stream().collect(
                Collectors.toMap(PurchaseUnit::getUnitCode, PurchaseUnit::getUnitName));
        log.debug("查询单位信息：" + JSON.toJSONString(purchaseUnitMap));

        for (int i = 0; i < bidRequirementLineList.size(); i++) {
            BidRequirementLine line = bidRequirementLineList.get(i);
            log.debug("第" + i + "行数据:" + line);
            //查询采购组织ID
            Long orgId = organizatioMap.get(line.getOrgName());
            Assert.notNull(orgId, "第" + (i + 1) + "行获取事业部信息为空,请检查事业部名称");
            line.setOrgId(orgId);

            //查询采购分类ID
            Long categoryId = purchaseCategoryMap.get(line.getCategoryName());
            Assert.notNull(categoryId, "第" + (i + 1) + "行获取采购分类信息为空,请检查采购分类名称");
            line.setCategoryId(categoryId);

            //查询标的ID和编码
            MaterialItem materialItem = materialItemMap.get(line.getTargetNum());
            Assert.notNull(materialItem, "第" + (i + 1) + "行获取标的信息为空,请检查标的编码");
            line.setTargetId(materialItem.getMaterialId()).setTargetNum(materialItem.getMaterialCode());

            //校验单位编码
            String uomDesc = purchaseUnitMap.get(line.getUomCode());
            Assert.isTrue(StringUtils.isNotBlank(uomDesc), "第" + (i + 1) + "行获取单位信息为空,请检查单位名称");
            if (!uomDesc.equals(line.getUomDesc())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("单位编码与单位名称不匹配,错误数据所在行:", String.valueOf(i)));
            }

        }

        return bidRequirementLineList;
    }

    @Override
    public void excelResponse(HttpServletResponse response, Long bidingId) {
        //获取数据，转为字节数组
        List<BidRequirementLine> dataList =
                this.list(new QueryWrapper<>(new BidRequirementLine().setBidingId(bidingId)));
        log.debug("dataList:" + JSON.toJSONString(dataList));
        List<BidRequirementLineImportVO> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dataList)) {
            voList = BeanCopyUtil.copyListProperties(dataList, BidRequirementLineImportVO.class);
        }
        log.debug("voList:" + JSON.toJSONString(voList));
        Biding biding = bidingService.getById(bidingId);
        String bidingName = (biding == null ? "" : biding.getBidingName());
        //构造单位编码/名称对照下拉框
        List<PurchaseUnit> purchaseUnitList = baseClient.listAllEnablePurchaseUnit();
        log.debug("查询单位信息:" + JSON.toJSONString(purchaseUnitList));
        String[] unitSpinner = purchaseUnitList.stream().map(unit -> new StringBuilder(
                unit.getUnitCode()).append("/").append(unit.getUnitName()).toString())
                .collect(Collectors.toList()).toArray(new String[purchaseUnitList.size()]);
        log.debug("单位下拉框：" + JSON.toJSONString(unitSpinner));
        String fileName = bidingName + "-项目需求信息.xls";
        Map<Integer, String[]> mapDropDown = new HashMap<Integer, String[]>();
        mapDropDown.put(17, unitSpinner);
        File file = new File(fileName);
        EasyExcel.write(file, BidRequirementLineImportVO.class).
                registerWriteHandler(new SpinnerSheetWriteHandler(mapDropDown)).
                sheet().doWrite(voList);

        try {
            byte[] buffer = FileUtils.readFileToByteArray(file);
            file.delete();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.getOutputStream().write(buffer);
            response.getOutputStream().close();
        } catch (IOException e) {
            log.error("输出流报错:" + e.getMessage());
            throw new BaseException(LocaleHandler.getLocaleMsg("系统异常,请联系系统管理员"));
        }
    }

    @Override
    public void importModelDownload(HttpServletResponse response) throws Exception {
        String fileName = "项目需求导入模板";
        ArrayList<BidRequirementLineDto> bidRequirementLineDtos = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream, fileName, bidRequirementLineDtos, BidRequirementLineDto.class);
    }

    @Override
    public Map<String, Object> importExcel(BidRequirement bidRequirement, MultipartFile file, Fileupload fileupload) throws Exception {
        // 校验必传参数
        Assert.notNull(bidRequirement.getBidingId(), "招标id不能为空");
        // 文件校验
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<BidRequirementLineDto> bidRequirementLineDtos = readData(file);
        // 是否有报错标识
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 获取数据
        List<BidRequirementLine> bidRequirementLines = checkData(bidRequirementLineDtos, errorFlag);
        if (errorFlag.get()) {
            // 报错
            fileupload.setFileSourceName("项目需求导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    bidRequirementLineDtos, BidRequirementLineDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(), fileupload1.getFileSourceName());
        } else {
            // 保存或更新招标需求表
            Long requirementId = bidRequirement.getRequirementId();
            if (StringUtil.notEmpty(requirementId)) {
                iBidRequirementService.updateById(bidRequirement);
            } else {
                bidRequirement.setRequirementId(IdGenrator.generate());
                iBidRequirementService.save(bidRequirement);
            }
            // 保存物料行
            this.remove(new QueryWrapper<>(new BidRequirementLine().
                    setBidingId(bidRequirement.getBidingId()).setRequirementId(bidRequirement.getRequirementId())));
            if (CollectionUtils.isNotEmpty(bidRequirementLines)) {
                bidRequirementLines.forEach(bidRequirementLine -> {
                    bidRequirementLine.setBidingId(bidRequirement.getBidingId());
                    bidRequirementLine.setRequirementId(bidRequirement.getRequirementId());
                    bidRequirementLine.setRequirementLineId(IdGenrator.generate());
                });
                this.saveBatch(bidRequirementLines);
            }
            return ImportStatus.importSuccess();
        }
    }

    /**
     * 校验导入数据
     *
     * @param bidRequirementLineDtos
     * @param errorFlag
     */
    private List<BidRequirementLine> checkData(List<BidRequirementLineDto> bidRequirementLineDtos, AtomicBoolean errorFlag) {
        List<BidRequirementLine> bidRequirementLines = new ArrayList<>();
        /**
         * 预先查找要校验的数据
         * 1. OU编码查询OU组
         * 2. 业务实体名字+库存组织名字查找业务实体
         * 3. 查找省下所有的市集合
         * 4. 根据供应商名字查找左右供应商
         * 5. 根据物料名称查找所有物料
         * 6. 根据物料查找对应的公式值
         */
        Map<String, String> cityProvinceSuccessMap = new HashMap<>();
        Map<String, String> cityProvinceErrorMap = new HashMap<>();


        Map<String, List<BaseOuGroup>> ouCodeMap = new HashMap<>();
        Map<String, List<Organization>> orgMap = new HashMap<>();
        Map<String, List<CompanyInfo>> companyInfoMap = new HashMap<>();
        Map<String, List<MaterialItem>> materialMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bidRequirementLineDtos)) {
            // ou组编码
            List<String> ouCodeList = new ArrayList<>();
            // 组织名字
            List<String> orgList = new ArrayList<>();
            // 供应商名称
            List<String> vendorNameList = new ArrayList<>();
            // 物料编码
            List<String> targetNumList = new ArrayList<>();
            bidRequirementLineDtos.forEach(bidRequirementLineDto -> {
                String ouCode = bidRequirementLineDto.getOuCode();
                String orgName = bidRequirementLineDto.getOrgName();
                String invName = bidRequirementLineDto.getInvName();
                String supplierName = bidRequirementLineDto.getAwardedSupplierName();
                String targetNum = bidRequirementLineDto.getTargetNum();
                Optional.ofNullable(ouCode).ifPresent(s -> ouCodeList.add(s.trim()));
                Optional.ofNullable(orgName).ifPresent(s -> orgList.add(s.trim()));
                Optional.ofNullable(invName).ifPresent(s -> orgList.add(s.trim()));
                Optional.ofNullable(supplierName).ifPresent(s -> vendorNameList.add(s.trim()));
                Optional.ofNullable(targetNum).ifPresent(s -> targetNumList.add(s.trim()));
            });

            List<BaseOuGroup> baseOuGroups = baseClient.queryOuByOuCodeList(ouCodeList.stream().distinct().collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(baseOuGroups)) {
                ouCodeMap = baseOuGroups.stream().collect(Collectors.groupingBy(BaseOuGroup::getOuGroupCode));
            }
            List<Organization> organizationList = baseClient.getOrganizationByNameList(orgList.stream().distinct().collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(organizationList)) {
                orgMap = organizationList.stream().collect(Collectors.groupingBy(Organization::getOrganizationName));
            }
            List<CompanyInfo> companyInfos = supplierClient.getComponyByNameList(vendorNameList.stream().distinct().collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(companyInfos)) {
                companyInfoMap = companyInfos.stream().collect(Collectors.groupingBy(CompanyInfo::getCompanyName));
            }
            List<MaterialItem> materialItems = baseClient.queryMaterialItemByCodes(targetNumList.stream().distinct().collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(materialItems)) {
                materialMap = materialItems.stream().collect(Collectors.groupingBy(MaterialItem::getMaterialCode));
            }
        }

        if (CollectionUtils.isNotEmpty(bidRequirementLineDtos)) {
            Map<String, String> dicMap = getImportDic();
            // 校验数据
            for (BidRequirementLineDto bidRequirementLineDto : bidRequirementLineDtos) {
                BidRequirementLine bidRequirementLine = new BidRequirementLine();
                StringBuffer errorMsg = new StringBuffer();


                // 检查OU组编号
                String ouCode = bidRequirementLineDto.getOuCode();
                if (StringUtil.notEmpty(ouCode)) {
                    ouCode = ouCode.trim();
                    if (null != ouCodeMap.get(ouCode)) {
                        BaseOuGroup baseOuGroup = ouCodeMap.get(ouCode).get(0);
                        bidRequirementLine.setOuId(baseOuGroup.getOuGroupId());
                        bidRequirementLine.setOuName(baseOuGroup.getOuGroupName());
                        bidRequirementLine.setOuNumber(baseOuGroup.getOuGroupCode());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("OU组不存在或没权限; ");
                    }

                }
//                String ouCode = bidRequirementLineDto.getOuCode();
//                if(StringUtil.notEmpty(ouCode)){
//                    ouCode = ouCode.trim();
//                    BaseOuGroupQueryDTO baseOuGroupQueryDTO = new BaseOuGroupQueryDTO();
//                    baseOuGroupQueryDTO.setOuGroupCode(ouCode);
//                    List<BaseOuGroupDetailVO> baseOuGroupDetailVOS = baseClient.queryOuDetailByDto(baseOuGroupQueryDTO);
//                    if(CollectionUtils.isNotEmpty(baseOuGroupDetailVOS)){
//                        BaseOuGroupDetailVO baseOuGroupDetailVO = baseOuGroupDetailVOS.get(0);
//                        bidRequirementLine.setOuId(baseOuGroupDetailVO.getOuGroupId());
//                        bidRequirementLine.setOuName(baseOuGroupDetailVO.getOuGroupName());
//                        bidRequirementLine.setOuNumber(baseOuGroupDetailVO.getOuGroupCode());
//                    }else {
//                        errorFlag.set(true);
//                        errorMsg.append("OU组不存在或没权限; ");
//                    }
//
//                }

                // 是否为基准ou
                String baseOu = bidRequirementLineDto.getBaseOu();
                if (StringUtil.notEmpty(baseOu)) {
                    baseOu = baseOu.trim();
                    if (YesOrNo.YES.getValue().equals(baseOu) || YesOrNo.NO.getValue().equals(baseOu)) {
                        bidRequirementLine.setBaseOu(baseOu);
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("是否为基准ou只能填\"Y\"或\"N\"; ");
                    }
                }

                // 业务实体
                String orgName = bidRequirementLineDto.getOrgName();
                if (StringUtil.notEmpty(orgName)) {
                    orgName = orgName.trim();
                    if (null != orgMap.get(orgName)) {
                        Organization organization = orgMap.get(orgName).get(0);
                        bidRequirementLine.setOrgId(organization.getOrganizationId());
                        bidRequirementLine.setOrgCode(organization.getOrganizationCode());
                        bidRequirementLine.setOrgName(organization.getOrganizationName());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("业务实体不存在; ");
                    }
                }
//                if(StringUtil.notEmpty(orgName)){
//                    orgName = orgName.trim();
//                    Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(orgName));
//                    if(null != organization && StringUtil.notEmpty(organization.getOrganizationId())){
//                        bidRequirementLine.setOrgId(organization.getOrganizationId());
//                        bidRequirementLine.setOrgCode(organization.getOrganizationCode());
//                        bidRequirementLine.setOrgName(organization.getOrganizationName());
//                    }else {
//                        errorFlag.set(true);
//                        errorMsg.append("业务实体不存在; ");
//                    }
//                }

                // 库存组织名称
                String invName = bidRequirementLineDto.getInvName();
                if (StringUtil.notEmpty(invName)) {
                    invName = invName.trim();
                    if (null != orgMap.get(invName)) {
                        Organization organization = orgMap.get(invName).get(0);
                        if (StringUtil.notEmpty(bidRequirementLine.getOrgId()) && organization.getParentOrganizationIds().contains(String.valueOf(bidRequirementLine.getOrgId()))) {
                            bidRequirementLine.setInvId(organization.getOrganizationId());
                            bidRequirementLine.setInvCode(organization.getOrganizationCode());
                            bidRequirementLine.setInvName(organization.getOrganizationName());
                        } else {
                            errorFlag.set(true);
                            errorMsg.append("库存组织与业务实体不存在父子关系; ");
                        }
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("库存组织不存在; ");
                    }
                }
//                if(StringUtil.notEmpty(invName)){
//                    invName = invName.trim();
//                    Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(invName));
//                    if(null != organization && StringUtil.notEmpty(organization.getOrganizationId())){
//                        if(StringUtil.notEmpty(bidRequirementLine.getOrgId()) && organization.getParentOrganizationIds().contains(String.valueOf(bidRequirementLine.getOrgId()))){
//                            bidRequirementLine.setInvId(organization.getOrganizationId());
//                            bidRequirementLine.setInvCode(organization.getOrganizationCode());
//                            bidRequirementLine.setInvName(organization.getOrganizationName());
//                        }else {
//                            errorFlag.set(true);
//                            errorMsg.append("库存组织与业务实体不存在父子关系; ");
//                        }
//                    }else {
//                        errorFlag.set(true);
//                        errorMsg.append("库存组织不存在; ");
//                    }
//                }

                if (StringUtil.notEmpty(bidRequirementLine.getOuId()) &&
                        (StringUtil.notEmpty(bidRequirementLine.getOrgId()) || StringUtil.notEmpty(bidRequirementLine.getInvId()))) {
                    errorFlag.set(true);
                    errorMsg.append("ou组和(业务实体或库存组织)只能二选一; ");
                }

                // 交货地点
                String deliveryPlace = bidRequirementLineDto.getDeliveryPlace();
                if (StringUtil.notEmpty(deliveryPlace)) {
                    // 省份  (格式例如: 广东省/广州市)
                    deliveryPlace = deliveryPlace.trim();


                    if (!cityProvinceErrorMap.containsKey(deliveryPlace)) {
                        if (!cityProvinceSuccessMap.containsKey(deliveryPlace)) {
                            if (deliveryPlace.contains("/")) {
                                List<String> strings = Arrays.asList(deliveryPlace.split("/"));
                                if (CollectionUtils.isNotEmpty(strings) && strings.size() == 2) {
                                    String province = strings.get(0).trim();
                                    String city = strings.get(1).trim();
                                    if (StringUtil.isEmpty(dicMap.get(province))) {
                                        errorMsg.append("省份不存在; ");
                                        cityProvinceErrorMap.put(deliveryPlace, "省份不存在; ");
                                        errorFlag.set(true);
                                    } else {
                                        // 省份id
                                        String parentId = dicMap.get(province);
                                        CityParamDto cityParamDto = new CityParamDto();
                                        cityParamDto.setParentId(Long.parseLong(parentId));
                                        cityParamDto.setAreaName(city);
                                        List<AreaDTO> areaDTOS = baseClient.checkCity(cityParamDto);
                                        if (CollectionUtils.isEmpty(areaDTOS)) {
                                            cityProvinceErrorMap.put(deliveryPlace, "该城市不属于当前省份; ");
                                            errorMsg.append("该城市不属于当前省份; ");
                                            errorFlag.set(true);
                                        } else {
                                            String cityId = areaDTOS.get(0).getCityId().toString();
                                            String deliveryPlaceNew = JSON.toJSONString(Arrays.asList(parentId, cityId));
                                            cityProvinceSuccessMap.put(deliveryPlace, deliveryPlaceNew);
                                            bidRequirementLine.setDeliveryPlace(deliveryPlaceNew);
                                        }
                                    }
                                } else {
                                    errorFlag.set(true);
                                    cityProvinceErrorMap.put(deliveryPlace, "交货必须包括省和市; ");
                                    errorMsg.append("交货必须包括省和市; ");
                                }
                            } else {
                                errorFlag.set(true);
                                cityProvinceErrorMap.put(deliveryPlace, "交货地点必须有斜杆\"/\"分隔省和市; ");
                                errorMsg.append("交货地点必须有斜杆\"/\"分隔省和市; ");
                            }
                        } else {
                            bidRequirementLine.setDeliveryPlace(cityProvinceSuccessMap.get(deliveryPlace));
                        }
                    } else {
                        errorFlag.set(true);
                        errorMsg.append(cityProvinceErrorMap.get(deliveryPlace));
                    }
                }

                // 价格类型
                String priceType = bidRequirementLineDto.getPriceType();
                if (StringUtil.notEmpty(priceType)) {
                    priceType = priceType.trim();
                    if (StringUtil.notEmpty(dicMap.get(priceType))) {
                        bidRequirementLine.setPriceType(dicMap.get(priceType));
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("价格类型字典值不存在; ");
                    }
                } else {
                    errorFlag.set(true);
                    errorMsg.append("价格类型不能为空; ");
                }

                // 采购类型
                String purchaseType = bidRequirementLineDto.getPurchaseType();
                if (StringUtil.notEmpty(purchaseType)) {
                    purchaseType = purchaseType.trim();
                    if (StringUtil.notEmpty(dicMap.get(purchaseType))) {
                        bidRequirementLine.setPurchaseType(dicMap.get(purchaseType));
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("采购类型字典值不存在; ");
                    }
                }

                // 贸易条款
                String tradeTerm = bidRequirementLineDto.getTradeTerm();
                if (StringUtil.notEmpty(tradeTerm)) {
                    tradeTerm = tradeTerm.trim();
                    if (StringUtil.notEmpty(dicMap.get(tradeTerm))) {
                        bidRequirementLine.setTradeTerm(dicMap.get(tradeTerm));
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("贸易条款字典值不存在; ");
                    }
                }

                // 运输方式
                String transportType = bidRequirementLineDto.getTransportType();
                if (StringUtil.notEmpty(transportType)) {
                    transportType = transportType.trim();
                    if (StringUtil.notEmpty(dicMap.get(transportType))) {
                        bidRequirementLine.setTransportType(dicMap.get(transportType));
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("运输方式字典值不存在; ");
                    }
                }

                // 是否显示需求量
                String showRequireNum = bidRequirementLineDto.getShowRequireNum();
                if (StringUtil.notEmpty(showRequireNum)) {
                    showRequireNum = showRequireNum.trim();
                    if (YesOrNo.YES.getValue().equals(showRequireNum) || YesOrNo.NO.getValue().equals(showRequireNum)) {
                        bidRequirementLine.setShowRequireNum(showRequireNum);
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("是否显示需求量只能填\"Y\"或\"N\"; ");
                    }
                }

                // 保修期(月)
                String warrantyPeriod = bidRequirementLineDto.getWarrantyPeriod();
                if (StringUtil.notEmpty(warrantyPeriod)) {
                    warrantyPeriod = warrantyPeriod.trim();
                    try {
                        int i = Integer.parseInt(warrantyPeriod);
                        bidRequirementLine.setWarrantyPeriod(i);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("保修期(月)非合法数字; ");
                    }
                }

                // 指定供应商名
                String awardedSupplierName = bidRequirementLineDto.getAwardedSupplierName();
                if (StringUtil.notEmpty(awardedSupplierName)) {
                    awardedSupplierName = awardedSupplierName.trim();
                    if (null != companyInfoMap.get(awardedSupplierName)) {
                        CompanyInfo companyInfo = companyInfoMap.get(awardedSupplierName).get(0);
                        bidRequirementLine.setAwardedSupplierId(companyInfo.getCompanyId());
                        bidRequirementLine.setAwardedSupplierName(companyInfo.getCompanyName());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("指定供应商名不存在; ");
                    }
                }
//                if(StringUtil.notEmpty(awardedSupplierName)){
//                    awardedSupplierName = awardedSupplierName.trim();
//                    CompanyInfo info = supplierClient.getCompanyInfoByParam(new CompanyInfo().setCompanyName(awardedSupplierName));
//                    if(null != info && StringUtil.notEmpty(info.getCompanyId())){
//                        bidRequirementLine.setAwardedSupplierId(info.getCompanyId());
//                        bidRequirementLine.setAwardedSupplierName(info.getCompanyName());
//                    }else {
//                        errorFlag.set(true);
//                        errorMsg.append("指定供应商名不存在; ");
//                    }
//                }

                // 物料编码
                String targetNum = bidRequirementLineDto.getTargetNum();
                if (StringUtil.notEmpty(targetNum)) {
                    targetNum = targetNum.trim();
                    if (null != materialMap.get(targetNum)) {
                        MaterialItem materialItem = materialMap.get(targetNum).get(0);
                        bidRequirementLine.setTargetId(materialItem.getMaterialId());
                        bidRequirementLine.setTargetNum(materialItem.getMaterialCode());
                        bidRequirementLine.setTargetDesc(org.springframework.util.StringUtils.isEmpty(bidRequirementLineDto.getTargetDesc())?materialItem.getMaterialName():bidRequirementLineDto.getTargetDesc());
                        bidRequirementLine.setCategoryId(materialItem.getCategoryId());
                        bidRequirementLine.setCategoryCode(materialItem.getCategoryCode());
                        bidRequirementLine.setCategoryName(materialItem.getCategoryName());
                        bidRequirementLine.setUomCode(materialItem.getUnit());
                        bidRequirementLine.setUomDesc(materialItem.getUnitName());
                        bidRequirementLine.setFormulaValue(materialItem.getPricingFormulaValue());
                        bidRequirementLine.setFormulaId(materialItem.getPricingFormulaHeaderId());
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("物料名称不存在; ");
                    }
                } else if (StringUtil.notEmpty(bidRequirementLineDto.getFormulaValue())) {
                    errorFlag.set(true);
                    errorMsg.append("公式值必须依赖物料存在; ");
                }
//                if(StringUtil.notEmpty(targetDesc)){
//                    targetDesc = targetDesc.trim();
//                    List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialName(targetDesc));
//                    if(CollectionUtils.isNotEmpty(materialItems)){
//                        MaterialItem materialItem = materialItems.get(0);
//                        bidRequirementLine.setTargetId(materialItem.getMaterialId());
//                        bidRequirementLine.setTargetNum(materialItem.getMaterialCode());
//                        bidRequirementLine.setTargetDesc(materialItem.getMaterialName());
//                        bidRequirementLine.setCategoryId(materialItem.getCategoryId());
//                        bidRequirementLine.setCategoryCode(materialItem.getCategoryCode());
//                        bidRequirementLine.setCategoryName(materialItem.getCategoryName());
//                        bidRequirementLine.setUomCode(materialItem.getUnit());
//                        bidRequirementLine.setUomDesc(materialItem.getUnitName());
//
//                        // 根据物料查公式值
//                        List<PricingFormulaHeaderVO> pricingFormulaHeaderVOS = pricingFormulaCalculateClient.getPricingFormulaHeaderByMaterialId(materialItem.getMaterialId());
//                        if(CollectionUtils.isNotEmpty(pricingFormulaHeaderVOS)){
//                            PricingFormulaHeaderVO pricingFormulaHeaderVO = pricingFormulaHeaderVOS.get(0);
//                            bidRequirementLine.setFormulaValue(pricingFormulaHeaderVO.getPricingFormulaValue());
//                            bidRequirementLine.setFormulaId(pricingFormulaHeaderVO.getPricingFormulaHeaderId());
//                        }
//                    }else {
//                        errorFlag.set(true);
//                        errorMsg.append("物料名称不存在; ");
//                    }
//                }else if(StringUtil.notEmpty(bidRequirementLineDto.getFormulaValue())){
//                    errorFlag.set(true);
//                    errorMsg.append("公式值必须依赖物料存在; ");
//                }

                // 定价开始时间
                String priceStartTime = bidRequirementLineDto.getPriceStartTime();
                if (StringUtil.notEmpty(priceStartTime)) {
                    priceStartTime = priceStartTime.trim();
                    try {
                        Date date = DateUtil.parseDate(priceStartTime);
                        bidRequirementLine.setPriceStartTime(date);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("定价开始时间格式非法; ");
                    }
                }

                // 定价开始时间
                String priceEndTime = bidRequirementLineDto.getPriceEndTime();
                if (StringUtil.notEmpty(priceEndTime)) {
                    priceEndTime = priceEndTime.trim();
                    try {
                        Date date = DateUtil.parseDate(priceEndTime);
                        bidRequirementLine.setPriceEndTime(date);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("定价开始时间格式非法; ");
                    }
                }

                // 采购数量
                String quantity = bidRequirementLineDto.getQuantity();
                if (StringUtil.notEmpty(quantity)) {
                    quantity = quantity.trim();
                    try {
                        int i = Integer.parseInt(quantity);
                        bidRequirementLine.setQuantity((double) i);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("采购数量格式非法; ");
                    }
                }

                // 预计采购金额(万元)
                String amount = bidRequirementLineDto.getAmount();
                if (StringUtil.notEmpty(amount)) {
                    amount = amount.trim();
                    if (StringUtil.isDigit(amount)) {
                        bidRequirementLine.setAmount(new BigDecimal(amount));
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("预计采购金额格式非法; ");
                    }
                }

                // 备注
                String comments = bidRequirementLineDto.getComments();
                if (StringUtil.notEmpty(comments)) {
                    comments = comments.trim();
                    bidRequirementLine.setComments(comments);
                }

                // 需求日期
                String ceeaDemandDate = bidRequirementLineDto.getCeeaDemandDate();
                if (StringUtil.notEmpty(ceeaDemandDate)) {
                    ceeaDemandDate = ceeaDemandDate.trim();
                    try {
                        Date date = DateUtil.parseDate(ceeaDemandDate);
                        LocalDate localDate = DateUtil.dateToLocalDate(date);
                        bidRequirementLine.setCeeaDemandDate(localDate);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("需求日期格式非法; ");
                    }
                }

                if (errorMsg.length() > 0) {
                    bidRequirementLineDto.setErrorMsg(errorMsg.toString());
                } else {
                    bidRequirementLineDto.setErrorMsg(null);
                }
                bidRequirementLines.add(bidRequirementLine);
            }
        }
        return bidRequirementLines;
    }

    /**
     * 获取项目需求所需字典
     *
     * @return
     */
    public Map<String, String> getImportDic() {
        HashMap<String, String> dicMap = new HashMap<>();
        // 所有的省份
        AreaPramDTO areaPramDTO = new AreaPramDTO();
        areaPramDTO.setQueryType("province");
        List<AreaDTO> regions = baseClient.queryRegionById(areaPramDTO);
        if (CollectionUtils.isNotEmpty(regions)) {
            regions.forEach(region -> {
                dicMap.put(region.getProvince(), region.getProvinceId().toString());
            });
        }

        // 所有单位 purchase/purchaseUnit/listAll
        List<PurchaseUnit> purchaseUnits = baseClient.listAllEnablePurchaseUnit();
        if (CollectionUtils.isNotEmpty(purchaseUnits)) {
            purchaseUnits.forEach(purchaseUnit -> {
                dicMap.put(purchaseUnit.getUnitName(), purchaseUnit.getUnitCode());
            });
        }
        // 字典编码
        ArrayList<String> dicCodeList = new ArrayList<>();
        // 价格类型
        dicCodeList.add("PRICE_TYPE");
        dicCodeList.add("PURCHASE_TYPE");
        dicCodeList.add("trade_clause");
        dicCodeList.add("TRANSF_TYPE");
        dicCodeList.add("TRANSF_TYPE");
        // 查询字典
        List<DictItemDTO> dictItemDTOS = baseClient.listByDictCode(dicCodeList);
        // 把字典塞到Map里
        this.setMapKeyValue(dicMap, dictItemDTOS);
        return dicMap;
    }

    /**
     * 把字典塞到Map里
     *
     * @param dicMap
     * @param dictItemDTOS1
     */
    public void setMapKeyValue(HashMap<String, String> dicMap, List<DictItemDTO> dictItemDTOS1) {
        if (CollectionUtils.isNotEmpty(dictItemDTOS1)) {
            dictItemDTOS1.forEach(dictItemDTOS -> {
                dicMap.put(dictItemDTOS.getDictItemName(), dictItemDTOS.getDictItemCode());
            });
        }
    }

    private List<BidRequirementLineDto> readData(MultipartFile file) {
        List<BidRequirementLineDto> bidRequirementLineDtos = null;
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<BidRequirementLineDto> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream, listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(BidRequirementLineDto.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            bidRequirementLineDtos = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return bidRequirementLineDtos;
    }


    private void checkMaterialWhetherBelongInv(Map<Long, String> materialMap, HashSet<Long> ouIds, Map<Long, String> invNameMap) {
        //获取到对应物料和业务实体的关系
        if (org.springframework.util.CollectionUtils.isEmpty(materialMap)
                || org.springframework.util.CollectionUtils.isEmpty(ouIds)
                || org.springframework.util.CollectionUtils.isEmpty(invNameMap)
        ) {
            return;
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(ouIds)) {
            List<BaseOuGroupDetailVO> baseOuGroupDetailVOS = baseClient.queryOuInfoDetailByIds(ouIds);
            baseOuGroupDetailVOS.stream()
                    .flatMap(e -> e.getDetails().stream()).forEach(inv -> invNameMap.putIfAbsent(inv.getInvId(), inv.getInvName()));
        }
        Map<String, Collection<Long>> paramMap = new HashMap<>();
        paramMap.put("materialIds", materialMap.keySet());
        paramMap.put("invIds", invNameMap.keySet());
        List<MaterialOrg> materialOrgList = baseClient.listMaterialOrgByMaterialIdsAndInvIds(paramMap);
        //按业务实体进行分组
        Map<Long, List<MaterialOrg>> materialOrgMap = materialOrgList.stream().collect(Collectors.groupingBy(MaterialOrg::getOrganizationId));
        for (Map.Entry<Long, List<MaterialOrg>> materialOrg : materialOrgMap.entrySet()) {
            List<MaterialOrg> value = materialOrg.getValue();
            value.stream().filter(e -> (Objects.equals(e.getUserPurchase(), "N") || Objects.equals(e.getItemStatus(), "N"))
                    && invNameMap.containsKey(e.getOrganizationId())
            )
                    .findAny().ifPresent(one -> {
                throw new BaseException(String.format("库存组织[%s]不允许物料[%s]寻源", one.getOrganizationName(), materialMap.get(one.getMaterialId())));
            });
        }
    }
}

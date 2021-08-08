package com.midea.cloud.srm.bid.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidFileType;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.SelectionStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.enums.logistics.BiddingOrderStates;
import com.midea.cloud.common.enums.logistics.CompanyType;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.enums.logistics.TransportModeEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.bid.mapper.LgtBidingMapper;
import com.midea.cloud.srm.bid.service.*;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.logistics.baseprice.service.BasePriceService;
import com.midea.cloud.srm.logistics.expense.service.IExpenseItemService;
import com.midea.cloud.srm.logistics.expense.service.IPortService;
import com.midea.cloud.srm.logistics.expense.service.IRegionService;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;
import com.midea.cloud.srm.model.logistics.baseprice.entity.BasePrice;
import com.midea.cloud.srm.model.logistics.bid.dto.*;
import com.midea.cloud.srm.model.logistics.bid.entity.*;
import com.midea.cloud.srm.model.logistics.bid.vo.LgtBidInfoVO;
import com.midea.cloud.srm.model.logistics.expense.entity.Port;
import com.midea.cloud.srm.model.logistics.expense.entity.Region;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class LgtBidingVendorServiceImpl implements ILgtBidingVendorService {
    @Resource
    private ILgtFileService iLgtFileService;
    @Resource
    private ILgtBidRequirementLineService iLgtBidRequirementLineService;
    @Resource
    private ILgtBidShipPeriodService iLgtBidShipPeriodService;
    @Resource
    private ILgtBidTemplateService iLgtBidTemplateService;
    @Resource
    private ILgtFileConfigService iLgtFileConfigService;
    @Resource
    private ILgtVendorFileService iLgtVendorFileService;
    @Resource
    private BaseClient baseClient;
    @Resource
    private ILgtVendorQuotedHeadService iLgtVendorQuotedHeadService;
    @Resource
    private ILgtVendorQuotedLineService iLgtVendorQuotedLineService;
    @Resource
    private ILgtRoundService iLgtRoundService;
    @Resource
    private ILgtVendorQuotedSumService iLgtVendorQuotedSumService;
    @Resource
    private ILgtBidingService iLgtBidingService;
    @Resource
    private LgtBidingMapper lgtBidingMapper;
    @Resource
    private FileCenterClient fileCenterClient;
    @Resource
    private IPortService iPortService;
    @Resource
    private IRegionService iRegionService;
    @Resource
    private BasePriceService basePriceService;
    @Resource
    private IExpenseItemService iExpenseItemService;

    /**
     * 导出供应商报价行信息
     * @param bidingId
     * @param vendorId
     */
    @Override
    public void exportLgtVendorQuotedLine(Long bidingId, Long vendorId, HttpServletResponse response) throws IOException {
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        // 查找供应商报价行
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                eq(LgtVendorQuotedLine::getVendorId,vendorId).
                eq(LgtVendorQuotedLine::getBidingId, lgtBiding.getBidingId()).
                eq(LgtVendorQuotedLine::getRound, lgtBiding.getCurrentRound()));
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtVendorQuotedLines),"没有可导出的数据!");
        // 查找供应商能查看的字段
        List<LgtBidTemplate> lgtBidTemplates = iLgtBidTemplateService.list(new QueryWrapper<>(new LgtBidTemplate().setBidingId(lgtBiding.getBidingId()).setVendorVisibleFlag(YesOrNo.YES.getValue())));
        Assert.isTrue(CollectionUtils.isNotEmpty(lgtBidTemplates),"供方没有可查看权限的字段!");
        // 需导出的字段
        List<String> fieldCodes = lgtBidTemplates.stream().map(lgtBidTemplate -> StringUtil.toCamelCase(lgtBidTemplate.getFieldCode())).collect(Collectors.toList());
        List<String> fieldNames = lgtBidTemplates.stream().map(LgtBidTemplate::getFieldName).collect(Collectors.toList());
        // 创建工作蒲
        Workbook workbook = ExcelUtil.createWorkbookModel(fieldNames);
        Sheet sheet = workbook.getSheetAt(0);
        AtomicInteger rowNum = new AtomicInteger(1);
        /**
         * 字段转换:
         * 是否往返: Y/N
         * 币制:
         * 费项: CHARGE_NAME
         * 计费方式: CHARGE_LEVEL
         * 计费单位: SUB_LEVEL
         * 贸易术语: TRADE_TERM
         * 整柜/拼柜: FCL/LCL
         * LEG: LEG
         * 进出口方式: EXP/IMP
         */
        // 是否往返
        Map<String, String> ifBackMap = EasyExcelUtil.getDicCodeName("YES_OR_NO", baseClient);
        // 费项字典
        Map<String, String> chargeNameMap = EasyExcelUtil.getDicCodeName("CHARGE_NAME", baseClient);
        // 计费方式
        Map<String, String> chargeLevelMap = EasyExcelUtil.getDicCodeName("CHARGE_LEVEL", baseClient);
        // 计费单位
        Map<String, String> subLevelMap = EasyExcelUtil.getDicCodeName("SUB_LEVEL", baseClient);
        // 贸易术语
        Map<String, String> tradeTermMap = EasyExcelUtil.getDicCodeName("TRADE_TERM", baseClient);
        // 整柜/拼柜
        Map<String, String> fclLclMap = EasyExcelUtil.getDicCodeName("FCL /LCL", baseClient);
        // LEG
        Map<String, String> legMap = EasyExcelUtil.getDicCodeName("LEG", baseClient);
        // 进出口方式
        Map<String, String> importExportMethodMap = EasyExcelUtil.getDicCodeName("EXP/IMP", baseClient);
        // 币种
        Map<String, String> currencyCodeName = EasyExcelUtil.getCurrencyCodeName(baseClient);
        lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
            // 创建行
            Row row = sheet.createRow(rowNum.getAndAdd(1));
            AtomicInteger cellNum = new AtomicInteger(0);
            Class<LgtVendorQuotedLine> lgtVendorQuotedLineClass = LgtVendorQuotedLine.class;
            fieldCodes.forEach(fieldCode -> {
                if("logisticsCategoryCode".equals(fieldCode)){
                    fieldCode = "logisticsCategoryName";
                }
                // 创建单元格
                Cell cell = row.createCell(cellNum.getAndAdd(1));
                try {
                    Object value = getFieldValue(lgtVendorQuotedLine, lgtVendorQuotedLineClass, fieldCode);
                    if(!ObjectUtils.isEmpty(value)){
                        String str = ExcelUtil.subZeroAndDot(value.toString());

                        // 判断是否需转字典
                        switch (fieldCode){
                            case "ifBack":
                                str = ifBackMap.get(str);
                                break;
                            case "expenseItem":
                                str = chargeNameMap.get(str);
                                break;
                            case "chargeMethod":
                                str = chargeLevelMap.get(str);
                                break;
                            case "chargeUnit":
                                str = subLevelMap.get(str);
                                break;
                            case "tradeTerm":
                                str = tradeTermMap.get(str);
                                break;
                            case "wholeArk":
                                str = fclLclMap.get(str);
                                break;
                            case "leg":
                                str = legMap.get(str);
                                break;
                            case "importExportMethod":
                                str = importExportMethodMap.get(str);
                                break;
                            case "currency":
                                str = currencyCodeName.get(str);
                                break;
                            default:
                                break;
                        }
                        cell.setCellValue(str);
                    }
                } catch (Exception e) {

                }
            });
        });
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "投标行明细");
        workbook.write(outputStream);
        workbook.close();
    }

    @Override
    public Object lgtBidShipPeriodImport(MultipartFile file, Long bidingId, Long vendorId, HttpServletResponse response) throws IOException {
        // 检查文件
        EasyExcelUtil.checkExcelIsXlsx(file);
        LgtBiding biding = iLgtBidingService.getById(bidingId);
        // 业务数据
        List<LgtBidShipPeriod> lgtBidShipPeriods = new ArrayList<>();
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        List<LgtBidShipPeriodImport> lgtBidShipPeriodImports ;
        List<LgtBidShipPeriodImportNew> lgtBidShipPeriodImportNews ;
        // 检查是否铁运/陆运
        if(TransportModeEnum.RAILWAY_TRANSPORT.name().equals(biding.getTransportModeCode()) ||
                TransportModeEnum.LAND_TRANSPORT.name().equals(biding.getTransportModeCode())){
            // 读取excel数据
            lgtBidShipPeriodImportNews = EasyExcelUtil.readExcelWithModel(file, LgtBidShipPeriodImportNew.class);
            // 检查参数
            checkImportDataNew(lgtBidShipPeriodImportNews,errorFlag,lgtBidShipPeriods,bidingId,vendorId);
            if(errorFlag.get()){
                return EasyExcelUtil.uploadErrorFile(fileCenterClient, lgtBidShipPeriodImportNews, LgtBidShipPeriodImportNew.class, "船期明细导入报错", file);
            }else {
                // 保存船期
                saveImportShipPeriod(bidingId, vendorId, lgtBidShipPeriods);
            }
        }else {
            // 读取excel数据
            lgtBidShipPeriodImports = EasyExcelUtil.readExcelWithModel(file, LgtBidShipPeriodImport.class);
            // 检查参数
            checkImportData(lgtBidShipPeriodImports,errorFlag,lgtBidShipPeriods,bidingId,vendorId);
            if(errorFlag.get()){
                return EasyExcelUtil.uploadErrorFile(fileCenterClient, lgtBidShipPeriodImports, LgtBidShipPeriodImport.class, "船期明细导入报错", file);
            }else {
                // 保存船期
                saveImportShipPeriod(bidingId, vendorId, lgtBidShipPeriods);
            }
        }
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        return iLgtBidShipPeriodService.list(Wrappers.lambdaQuery(LgtBidShipPeriod.class).
                eq(LgtBidShipPeriod::getBidingId, bidingId).
                eq(LgtBidShipPeriod::getVendorId, vendorId).
                eq(LgtBidShipPeriod::getRound, lgtBiding.getCurrentRound()));
    }

    @Transactional
    public void saveImportShipPeriod(Long bidingId, Long vendorId, List<LgtBidShipPeriod> lgtBidShipPeriods) {
        if(CollectionUtils.isNotEmpty(lgtBidShipPeriods)){
            LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
            // 获取供应商报价头
            Assert.isTrue(lgtBiding.getEnrollEndDatetime().compareTo(new Date()) > 0,"投标时间已截止,不能导入报价!");
            LgtVendorQuotedHead vendorQuotedHead = iLgtVendorQuotedHeadService.getOne(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                    eq(LgtVendorQuotedHead::getBidingId, bidingId).
                    eq(LgtVendorQuotedHead::getVendorId, vendorId).
                    eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).
                    last("LIMIT 1"));
            lgtBidShipPeriods.forEach(lgtBidShipPeriod -> {
//                LgtBidShipPeriod periodServiceOne = iLgtBidShipPeriodService.getOne(Wrappers.lambdaQuery(LgtBidShipPeriod.class).
//                        eq(LgtBidShipPeriod::getQuotedHeadId, vendorQuotedHead.getQuotedHeadId()).
//                        eq(LgtBidShipPeriod::getBidRequirementLineId, lgtBidShipPeriod.getBidRequirementLineId()).last("LIMIT 1"));
                lgtBidShipPeriod
                        .setShipPeriodId(IdGenrator.generate())
                        .setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())
                        .setBidingId(bidingId)
                        .setVendorId(vendorQuotedHead.getVendorId())
                        .setVendorCode(vendorQuotedHead.getVendorCode())
                        .setVendorName(vendorQuotedHead.getVendorName())
                        .setRound(lgtBiding.getCurrentRound());
            });
            iLgtBidShipPeriodService.saveBatch(lgtBidShipPeriods);
        }
    }
    public void checkImportDataNew(List<LgtBidShipPeriodImportNew> lgtBidShipPeriodImportNews,AtomicBoolean errorFlag,List<LgtBidShipPeriod> lgtBidShipPeriods,Long bidingId, Long vendorId) {
        if(CollectionUtils.isNotEmpty(lgtBidShipPeriodImportNews)){
            // 字典
            Map<String, String> wholeArkNameCode = EasyExcelUtil.getDicNameCode("FCL /LCL", baseClient);
            // 行政区域
            Map<String, Region> regionParentMap = new HashMap<>();
            Map<String, Region> regionMap = new HashMap<>();
            // 港口(编码-名称)
            Map<String, Port> portCodeMap = new HashMap<>();
            // 其他区域名称
            List<String> regionNames = new ArrayList<>();
            List<String> portCodes = new ArrayList<>();
            lgtBidShipPeriodImportNews.forEach(lgtBidShipPeriodImport -> {
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromPort())){
                    portCodes.add(lgtBidShipPeriodImport.getFromPort().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToPort())){
                    portCodes.add(lgtBidShipPeriodImport.getToPort().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromCountry())){
                    regionNames.add(lgtBidShipPeriodImport.getFromCountry().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromProvince())){
                    regionNames.add(lgtBidShipPeriodImport.getFromProvince().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromCity())){
                    regionNames.add(lgtBidShipPeriodImport.getFromCity().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromCounty())){
                    regionNames.add(lgtBidShipPeriodImport.getFromCounty().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToCountry())){
                    regionNames.add(lgtBidShipPeriodImport.getToCountry().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToProvince())){
                    regionNames.add(lgtBidShipPeriodImport.getToProvince().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToCity())){
                    regionNames.add(lgtBidShipPeriodImport.getToCity().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToCounty())){
                    regionNames.add(lgtBidShipPeriodImport.getToCounty().trim());
                }
            });

            if (CollectionUtils.isNotEmpty(regionNames)) {
                List<Region> regions = iRegionService.list(Wrappers.lambdaQuery(Region.class).in(Region::getRegionName,regionNames));
                if(CollectionUtils.isNotEmpty(regions)){
                    regionParentMap = regions.stream().collect(Collectors.toMap(region -> region.getRegionName() +region.getParentRegionCode(),Function.identity(),(k1, k2)->k1));
                    regionMap = regions.stream().collect(Collectors.toMap(region -> String.valueOf(region.getRegionName()),Function.identity(),(k1,k2)->k1));
                }
            }

            if (CollectionUtils.isNotEmpty(portCodes)) {
                List<Port> portList = iPortService.list(Wrappers.lambdaQuery(Port.class).in(Port::getPortCode,portCodes));
                if(CollectionUtils.isNotEmpty(portList)){
                    portCodeMap = portList.stream().collect(Collectors.toMap(Port::getPortCode, Function.identity(), (k1, k2) -> k1));
                }
            }

            List<LgtBidRequirementLine> bidRequirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).eq(LgtBidRequirementLine::getBidingId, bidingId));
            Map<String, LgtBidRequirementLine> requirementLineMap = bidRequirementLines.stream().collect(Collectors.toMap(lgtBidRequirementLine -> lgtBidRequirementLine.getStartAddress() + lgtBidRequirementLine.getEndAddress(), Function.identity(),(k1, k2)->k1));
            HashSet<String> hashSet = new HashSet<>();
            for(LgtBidShipPeriodImportNew lgtBidShipPeriodImport:lgtBidShipPeriodImportNews){
                StringBuffer errorMsg = new StringBuffer();
                LgtBidShipPeriod lgtBidShipPeriod = new LgtBidShipPeriod();
                // 起运国
                String fromCountry = lgtBidShipPeriodImport.getFromCountry();
                if(StringUtil.notEmpty(fromCountry)){
                    fromCountry = fromCountry.trim();
                    Region region = regionMap.get(fromCountry);
                    if(null != region){
                        lgtBidShipPeriod.setFromCountry(fromCountry.trim());
                        lgtBidShipPeriod.setFromCountryCode(region.getRegionCode());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[起运国]填写错误; ");
                    }
                }
                // 始发省
                String fromProvince = lgtBidShipPeriodImport.getFromProvince();
                if(StringUtil.notEmpty(fromProvince)){
                    fromProvince = fromProvince.trim();
                    Region region = null;
                    if (ObjectUtils.isEmpty(lgtBidShipPeriod.getFromCountryCode())) {
                        region = regionMap.get(fromProvince);
                    }else {
                        region = regionParentMap.get(fromProvince+lgtBidShipPeriod.getFromCountryCode());
                    }
                    if(null != region){
                        lgtBidShipPeriod.setFromProvince(region.getRegionName());
                        lgtBidShipPeriod.setFromProvinceCode(String.valueOf(region.getRegionCode()));
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[始发省]填写错误; ");
                    }
                }
                // 始发市
                String fromCity = lgtBidShipPeriodImport.getFromCity();
                if(StringUtil.notEmpty(fromCity)){
                    fromCity = fromCity.trim();
                    if (StringUtil.notEmpty(lgtBidShipPeriod.getFromProvinceCode())) {
                        Region region = regionParentMap.get(fromCity+lgtBidShipPeriod.getFromProvinceCode());
                        if(null != region){
                            lgtBidShipPeriod.setFromCity(region.getRegionName());
                            lgtBidShipPeriod.setFromCityCode(String.valueOf(region.getRegionCode()));
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("[始发市]与[始发省]不存在层级关系; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[始发市]与[始发省]不存在层级关系; ");
                    }
                }
                // 始发区县
                String fromCounty = lgtBidShipPeriodImport.getFromCounty();
                if(StringUtil.notEmpty(fromCounty)){
                    fromCounty = fromCounty.trim();
                    if (StringUtil.notEmpty(lgtBidShipPeriod.getFromCityCode())) {
                        Region region = regionParentMap.get(fromCounty+lgtBidShipPeriod.getFromCityCode());
                        if(null != region){
                            lgtBidShipPeriod.setFromCounty(region.getRegionName());
                            lgtBidShipPeriod.setFromCountyCode(String.valueOf(region.getRegionCode()));
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("[始发区县]与[始发市]不存在层级关系; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[始发区县]与[始发市]不存在层级关系; ");
                    }
                }
                // 起运地
                String fromPlace = lgtBidShipPeriodImport.getFromPlace();
                if(StringUtil.notEmpty(fromPlace)){
                    fromPlace = fromPlace.trim();
                    lgtBidShipPeriod.setFromPlace(fromPlace);
                }
                // 目的国
                String toCountry = lgtBidShipPeriodImport.getToCountry();
                if(StringUtil.notEmpty(toCountry)){
                    toCountry = toCountry.trim();
                    Region region = regionMap.get(toCountry);
                    if (null != region) {
                        lgtBidShipPeriod.setToCountry(region.getRegionName());
                        lgtBidShipPeriod.setToCountryCode(region.getRegionCode());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[目的国]填写错误; ");
                    }
                }
                // 目的省
                String toProvince = lgtBidShipPeriodImport.getToProvince();
                if(StringUtil.notEmpty(toProvince)){
                    toProvince = toProvince.trim();
                    Region region = null;
                    if (ObjectUtils.isEmpty(lgtBidShipPeriod.getToCountryCode())) {
                        region = regionMap.get(toProvince);
                    }else {
                        region = regionParentMap.get(toProvince+lgtBidShipPeriod.getToCountryCode());
                    }
                    if(null != region){
                        lgtBidShipPeriod.setToProvince(region.getRegionName());
                        lgtBidShipPeriod.setToProvinceCode(String.valueOf(region.getRegionCode()));
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[目的省]填写错误; ");
                    }
                }
                // 目的市
                String toCity = lgtBidShipPeriodImport.getToCity();
                if(StringUtil.notEmpty(toCity)){
                    toCity = toCity.trim();
                    if (StringUtil.notEmpty(lgtBidShipPeriod.getToProvinceCode())) {
                        Region region = regionParentMap.get(toCity+lgtBidShipPeriod.getToProvinceCode());
                        if(null != region){
                            lgtBidShipPeriod.setToCity(region.getRegionName());
                            lgtBidShipPeriod.setToCityCode(String.valueOf(region.getRegionCode()));
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("[目的市]与[目的省]不存在层级; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[目的市]与[目的省]不存在层级; ");
                    }
                }
                // 目的区县
                String toCounty = lgtBidShipPeriodImport.getToCounty();
                if(StringUtil.notEmpty(toCounty)){
                    toCounty = toCounty.trim();
                    if (StringUtil.notEmpty(lgtBidShipPeriod.getToCityCode())) {
                        Region region = regionParentMap.get(toCounty+lgtBidShipPeriod.getToCityCode());
                        if(null != region){
                            lgtBidShipPeriod.setToCounty(region.getRegionName());
                            lgtBidShipPeriod.setToCountyCode(String.valueOf(region.getRegionCode()));
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("[目的区县]与[目的市]不存在层级; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[目的区县]与[目的市]不存在层级; ");
                    }
                }
                // 目的地
                String toPlace = lgtBidShipPeriodImport.getToPlace();
                if(StringUtil.notEmpty(toPlace)){
                    toPlace = toPlace.trim();
                    lgtBidShipPeriod.setToPlace(toPlace);
                }
                // 起运港编码
                String fromPort = lgtBidShipPeriodImport.getFromPort();
                if(StringUtil.notEmpty(fromPort)){
                    fromPort = fromPort.trim();
                    Port port = portCodeMap.get(fromPort);
                    if (null != port) {
                        lgtBidShipPeriod.setFromPort(port.getPortNameZhs());
                        lgtBidShipPeriod.setFromPortCode(port.getPortCode());
                        lgtBidShipPeriod.setFromPortId(port.getPortId());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[起运港编码]无法匹配; ");
                    }
                }
                // 目的港编码
                String toPort = lgtBidShipPeriodImport.getToPort();
                if(StringUtil.notEmpty(toPort)){
                    toPort = toPort.trim();
                    Port port = portCodeMap.get(toPort);
                    if (null != port) {
                        lgtBidShipPeriod.setToPort(port.getPortNameZhs());
                        lgtBidShipPeriod.setToPortCode(port.getPortCode());
                        lgtBidShipPeriod.setToPortId(port.getPortId());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[起运港编码]无法匹配; ");
                    }
                }

                // 车数
                String charNum = lgtBidShipPeriodImport.getCharNum();
                if(StringUtil.notEmpty(charNum)){
                    charNum = charNum.trim();
                    try {
                        BigDecimal bigDecimal = new BigDecimal(charNum);
                        lgtBidShipPeriod.setCharNum(bigDecimal);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[车数]格式非法; ");
                    }
                }
                // 兆瓦数
                String megawatt = lgtBidShipPeriodImport.getMegawatt();
                if(StringUtil.notEmpty(megawatt)){
                    megawatt = megawatt.trim();
                    try {
                        BigDecimal bigDecimal = new BigDecimal(megawatt);
                        lgtBidShipPeriod.setMegawatt(bigDecimal);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[兆瓦数]格式非法; ");
                    }
                }
                // 是否满足(Y/N)
                String ifSatisfied = lgtBidShipPeriodImport.getIfSatisfied();
                if(StringUtil.notEmpty(ifSatisfied)){
                    ifSatisfied = ifSatisfied.trim();
                    if("Y".equals(ifSatisfied) || "N".equals(ifSatisfied)){
                        lgtBidShipPeriod.setIfSatisfied(ifSatisfied);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[是否满足]只能填[Y]或[N]; ");
                    }
                }
                // 可满足情况（是否满足为否时必填）
                String satisfiableSituation = lgtBidShipPeriodImport.getSatisfiableSituation();
                if(StringUtil.notEmpty(satisfiableSituation)){
                    satisfiableSituation = satisfiableSituation.trim();
                    lgtBidShipPeriod.setSatisfiableSituation(satisfiableSituation);
                }else {
                    if("N".equals(lgtBidShipPeriod.getIfSatisfied())){
                        errorFlag.set(true);
                        errorMsg.append("[可满足情况]不能为空; ");
                    }
                }
                // 特殊说明
                String specialInstructions = lgtBidShipPeriodImport.getSpecialInstructions();
                if(StringUtil.notEmpty(specialInstructions)){
                    specialInstructions = specialInstructions.trim();
                    lgtBidShipPeriod.setSpecialInstructions(specialInstructions);
                }
                // 备注
                String remarks = lgtBidShipPeriodImport.getRemarks();
                if(StringUtil.notEmpty(remarks)){
                    remarks = remarks.trim();
                    lgtBidShipPeriod.setRemarks(remarks);
                }

                if (errorMsg.length() <= 0) {
                    LgtBidingServiceImpl.setStartEndAddress(lgtBidShipPeriod);
                    String key = lgtBidShipPeriod.getStartAddress() + lgtBidShipPeriod.getEndAddress();
                    LgtBidRequirementLine requirementLine = requirementLineMap.get(key);
                    if(null == requirementLine){
                        errorFlag.set(true);
                        errorMsg.append("船期路线,在需求明细不存在; ");
                    }else {
                        lgtBidShipPeriod.setRowNum(requirementLine.getRowNum());
                        lgtBidShipPeriod.setBidRequirementLineId(requirementLine.getBidRequirementLineId());
                    }
                    if (!hashSet.add(key)) {
                        errorFlag.set(true);
                        errorMsg.append("船期路线,存在重复; ");
                    }
                }

                if(errorMsg.length() > 0){
                    lgtBidShipPeriodImport.setErrorMsg(errorMsg.toString());
                }else {
                    lgtBidShipPeriodImport.setErrorMsg(null);
                }

                lgtBidShipPeriods.add(lgtBidShipPeriod);

            }
        }
    }


    public void checkImportData(List<LgtBidShipPeriodImport> lgtBidShipPeriodImports,AtomicBoolean errorFlag,List<LgtBidShipPeriod> lgtBidShipPeriods,Long bidingId, Long vendorId) {
        if(CollectionUtils.isNotEmpty(lgtBidShipPeriodImports)){
            // 字典
            Map<String, String> wholeArkNameCode = EasyExcelUtil.getDicNameCode("FCL /LCL", baseClient);
            // 行政区域
            Map<String, Region> regionParentMap = new HashMap<>();
            Map<String, Region> regionMap = new HashMap<>();
            // 港口(编码-名称)
            Map<String, Port> portCodeMap = new HashMap<>();
            // 其他区域名称
            List<String> regionNames = new ArrayList<>();
            List<String> portCodes = new ArrayList<>();
            lgtBidShipPeriodImports.forEach(lgtBidShipPeriodImport -> {
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromPort())){
                    portCodes.add(lgtBidShipPeriodImport.getFromPort().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToPort())){
                    portCodes.add(lgtBidShipPeriodImport.getToPort().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromCountry())){
                    regionNames.add(lgtBidShipPeriodImport.getFromCountry().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromProvince())){
                    regionNames.add(lgtBidShipPeriodImport.getFromProvince().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromCity())){
                    regionNames.add(lgtBidShipPeriodImport.getFromCity().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getFromCounty())){
                    regionNames.add(lgtBidShipPeriodImport.getFromCounty().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToCountry())){
                    regionNames.add(lgtBidShipPeriodImport.getToCountry().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToProvince())){
                    regionNames.add(lgtBidShipPeriodImport.getToProvince().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToCity())){
                    regionNames.add(lgtBidShipPeriodImport.getToCity().trim());
                }
                if(!ObjectUtils.isEmpty(lgtBidShipPeriodImport.getToCounty())){
                    regionNames.add(lgtBidShipPeriodImport.getToCounty().trim());
                }
            });

            if (CollectionUtils.isNotEmpty(regionNames)) {
                List<Region> regions = iRegionService.list(Wrappers.lambdaQuery(Region.class).in(Region::getRegionName,regionNames));
                if(CollectionUtils.isNotEmpty(regions)){
                    regionParentMap = regions.stream().collect(Collectors.toMap(region -> region.getRegionName() +region.getParentRegionCode(),Function.identity(),(k1, k2)->k1));
                    regionMap = regions.stream().collect(Collectors.toMap(region -> String.valueOf(region.getRegionName()),Function.identity(),(k1,k2)->k1));
                }
            }

            if (CollectionUtils.isNotEmpty(portCodes)) {
                List<Port> portList = iPortService.list(Wrappers.lambdaQuery(Port.class).in(Port::getPortCode,portCodes));
                if(CollectionUtils.isNotEmpty(portList)){
                    portCodeMap = portList.stream().collect(Collectors.toMap(Port::getPortCode, Function.identity(), (k1, k2) -> k1));
                }
            }

            List<LgtBidRequirementLine> bidRequirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).eq(LgtBidRequirementLine::getBidingId, bidingId));
            Map<String, LgtBidRequirementLine> requirementLineMap = bidRequirementLines.stream().collect(Collectors.toMap(lgtBidRequirementLine -> lgtBidRequirementLine.getStartAddress() + lgtBidRequirementLine.getEndAddress(), Function.identity(),(k1, k2)->k1));
            HashSet<String> hashSet = new HashSet<>();
            for(LgtBidShipPeriodImport lgtBidShipPeriodImport:lgtBidShipPeriodImports){
                StringBuffer errorMsg = new StringBuffer();
                LgtBidShipPeriod lgtBidShipPeriod = new LgtBidShipPeriod();
                // 起运国
                String fromCountry = lgtBidShipPeriodImport.getFromCountry();
                if(StringUtil.notEmpty(fromCountry)){
                    fromCountry = fromCountry.trim();
                    Region region = regionMap.get(fromCountry);
                    if(null != region){
                        lgtBidShipPeriod.setFromCountry(fromCountry.trim());
                        lgtBidShipPeriod.setFromCountryCode(region.getRegionCode());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[起运国]填写错误; ");
                    }
                }
                // 始发省
                String fromProvince = lgtBidShipPeriodImport.getFromProvince();
                if(StringUtil.notEmpty(fromProvince)){
                    fromProvince = fromProvince.trim();
                    Region region = null;
                    if (ObjectUtils.isEmpty(lgtBidShipPeriod.getFromCountryCode())) {
                        region = regionMap.get(fromProvince);
                    }else {
                        region = regionParentMap.get(fromProvince+lgtBidShipPeriod.getFromCountryCode());
                    }
                    if(null != region){
                        lgtBidShipPeriod.setFromProvince(region.getRegionName());
                        lgtBidShipPeriod.setFromProvinceCode(String.valueOf(region.getRegionCode()));
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[始发省]填写错误; ");
                    }
                }
                // 始发市
                String fromCity = lgtBidShipPeriodImport.getFromCity();
                if(StringUtil.notEmpty(fromCity)){
                    fromCity = fromCity.trim();
                    if (StringUtil.notEmpty(lgtBidShipPeriod.getFromProvinceCode())) {
                        Region region = regionParentMap.get(fromCity+lgtBidShipPeriod.getFromProvinceCode());
                        if(null != region){
                            lgtBidShipPeriod.setFromCity(region.getRegionName());
                            lgtBidShipPeriod.setFromCityCode(String.valueOf(region.getRegionCode()));
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("[始发市]与[始发省]不存在层级关系; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[始发市]与[始发省]不存在层级关系; ");
                    }
                }
                // 始发区县
                String fromCounty = lgtBidShipPeriodImport.getFromCounty();
                if(StringUtil.notEmpty(fromCounty)){
                    fromCounty = fromCounty.trim();
                    if (StringUtil.notEmpty(lgtBidShipPeriod.getFromCityCode())) {
                        Region region = regionParentMap.get(fromCounty+lgtBidShipPeriod.getFromCityCode());
                        if(null != region){
                            lgtBidShipPeriod.setFromCounty(region.getRegionName());
                            lgtBidShipPeriod.setFromCountyCode(String.valueOf(region.getRegionCode()));
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("[始发区县]与[始发市]不存在层级关系; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[始发区县]与[始发市]不存在层级关系; ");
                    }
                }
                // 起运地
                String fromPlace = lgtBidShipPeriodImport.getFromPlace();
                if(StringUtil.notEmpty(fromPlace)){
                    fromPlace = fromPlace.trim();
                    lgtBidShipPeriod.setFromPlace(fromPlace);
                }
                // 目的国
                String toCountry = lgtBidShipPeriodImport.getToCountry();
                if(StringUtil.notEmpty(toCountry)){
                    toCountry = toCountry.trim();
                    Region region = regionMap.get(toCountry);
                    if (null != region) {
                        lgtBidShipPeriod.setToCountry(region.getRegionName());
                        lgtBidShipPeriod.setToCountryCode(region.getRegionCode());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[目的国]填写错误; ");
                    }
                }
                // 目的省
                String toProvince = lgtBidShipPeriodImport.getToProvince();
                if(StringUtil.notEmpty(toProvince)){
                    toProvince = toProvince.trim();
                    Region region = null;
                    if (ObjectUtils.isEmpty(lgtBidShipPeriod.getToCountryCode())) {
                        region = regionMap.get(toProvince);
                    }else {
                        region = regionParentMap.get(toProvince+lgtBidShipPeriod.getToCountryCode());
                    }
                    if(null != region){
                        lgtBidShipPeriod.setToProvince(region.getRegionName());
                        lgtBidShipPeriod.setToProvinceCode(String.valueOf(region.getRegionCode()));
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[目的省]填写错误; ");
                    }
                }
                // 目的市
                String toCity = lgtBidShipPeriodImport.getToCity();
                if(StringUtil.notEmpty(toCity)){
                    toCity = toCity.trim();
                    if (StringUtil.notEmpty(lgtBidShipPeriod.getToProvinceCode())) {
                        Region region = regionParentMap.get(toCity+lgtBidShipPeriod.getToProvinceCode());
                        if(null != region){
                            lgtBidShipPeriod.setToCity(region.getRegionName());
                            lgtBidShipPeriod.setToCityCode(String.valueOf(region.getRegionCode()));
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("[目的市]与[目的省]不存在层级; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[目的市]与[目的省]不存在层级; ");
                    }
                }
                // 目的区县
                String toCounty = lgtBidShipPeriodImport.getToCounty();
                if(StringUtil.notEmpty(toCounty)){
                    toCounty = toCounty.trim();
                    if (StringUtil.notEmpty(lgtBidShipPeriod.getToCityCode())) {
                        Region region = regionParentMap.get(toCounty+lgtBidShipPeriod.getToCityCode());
                        if(null != region){
                            lgtBidShipPeriod.setToCounty(region.getRegionName());
                            lgtBidShipPeriod.setToCountyCode(String.valueOf(region.getRegionCode()));
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("[目的区县]与[目的市]不存在层级; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[目的区县]与[目的市]不存在层级; ");
                    }
                }
                // 目的地
                String toPlace = lgtBidShipPeriodImport.getToPlace();
                if(StringUtil.notEmpty(toPlace)){
                    toPlace = toPlace.trim();
                    lgtBidShipPeriod.setToPlace(toPlace);
                }
                // 起运港编码
                String fromPort = lgtBidShipPeriodImport.getFromPort();
                if(StringUtil.notEmpty(fromPort)){
                    fromPort = fromPort.trim();
                    Port port = portCodeMap.get(fromPort);
                    if (null != port) {
                        lgtBidShipPeriod.setFromPort(port.getPortNameZhs());
                        lgtBidShipPeriod.setFromPortCode(port.getPortCode());
                        lgtBidShipPeriod.setFromPortId(port.getPortId());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[起运港编码]无法匹配; ");
                    }
                }
                // 目的港编码
                String toPort = lgtBidShipPeriodImport.getToPort();
                if(StringUtil.notEmpty(toPort)){
                    toPort = toPort.trim();
                    Port port = portCodeMap.get(toPort);
                    if (null != port) {
                        lgtBidShipPeriod.setToPort(port.getPortNameZhs());
                        lgtBidShipPeriod.setToPortCode(port.getPortCode());
                        lgtBidShipPeriod.setToPortId(port.getPortId());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[起运港编码]无法匹配; ");
                    }
                }
                // FCL/LCL
                String wholeArk = lgtBidShipPeriodImport.getWholeArk();
                if(StringUtil.notEmpty(wholeArk)){
                    wholeArk = wholeArk.trim();
                    String code = wholeArkNameCode.get(wholeArk);
                    if(StringUtil.notEmpty(code)){
                        lgtBidShipPeriod.setWholeArk(code);
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("[FCL/LCL]字典值不存在; ");
                    }
                }
                // Mon
                String mon = lgtBidShipPeriodImport.getMon();
                if(StringUtil.notEmpty(mon)){
                    try {
                        BigDecimal monNum = new BigDecimal(mon);
                        lgtBidShipPeriod.setMon(monNum);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[Mon]非数字; ");
                    }
                }
                // Tue
                String tue = lgtBidShipPeriodImport.getTue();
                if(StringUtil.notEmpty(tue)){
                    try {
                        BigDecimal tueNum = new BigDecimal(tue);
                        lgtBidShipPeriod.setTue(tueNum);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[Tue]非数字; ");
                    }
                }
                // Wed
                String wed = lgtBidShipPeriodImport.getWed();
                if(StringUtil.notEmpty(wed)){
                    try {
                        BigDecimal wedNum = new BigDecimal(wed);
                        lgtBidShipPeriod.setWed(wedNum);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[Wed]非数字; ");
                    }
                }
                // Thu
                String thu = lgtBidShipPeriodImport.getThu();
                if(StringUtil.notEmpty(thu)){
                    try {
                        BigDecimal thuNum = new BigDecimal(thu);
                        lgtBidShipPeriod.setThu(thuNum);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[Thu]非数字; ");
                    }
                }
                // Fri
                String fri = lgtBidShipPeriodImport.getFri();
                if(StringUtil.notEmpty(fri)){
                    try {
                        BigDecimal friNum = new BigDecimal(fri);
                        lgtBidShipPeriod.setFri(friNum);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[Fri]非数字; ");
                    }
                }
                // Sat
                String sat = lgtBidShipPeriodImport.getSat();
                if(StringUtil.notEmpty(sat)){
                    try {
                        BigDecimal satNum = new BigDecimal(sat);
                        lgtBidShipPeriod.setSat(satNum);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[Sat]非数字; ");
                    }
                }
                // Sun
                String sun = lgtBidShipPeriodImport.getSun();
                if(StringUtil.notEmpty(sun)){
                    try {
                        BigDecimal sunNum = new BigDecimal(sun);
                        lgtBidShipPeriod.setSun(sunNum);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[Sun]非数字; ");
                    }
                }
                // Transit Time_PTP（Days）
                String transitTime = lgtBidShipPeriodImport.getTransitTime();
                if(StringUtil.notEmpty(transitTime)){
                    try {
                        BigDecimal transitTimeNum = new BigDecimal(transitTime);
                        lgtBidShipPeriod.setTransitTime(transitTimeNum);
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("[Transit Time_PTP（Days）]非数字; ");
                    }
                }
                // 船公司/航空公司
                String shipCompanyName = lgtBidShipPeriodImport.getShipCompanyName();
                if(StringUtil.notEmpty(shipCompanyName)){
                    shipCompanyName = shipCompanyName.trim();
                    lgtBidShipPeriod.setShipCompanyName(shipCompanyName);
                }
                // 中转港/中转站
                String transferPort = lgtBidShipPeriodImport.getTransferPort();
                if(StringUtil.notEmpty(transferPort)){
                    transferPort = transferPort.trim();
                    lgtBidShipPeriod.setTransferPort(transferPort);
                }

                if (errorMsg.length() <= 0) {
                    LgtBidingServiceImpl.setStartEndAddress(lgtBidShipPeriod);
                    String key = lgtBidShipPeriod.getStartAddress() + lgtBidShipPeriod.getEndAddress();
                    LgtBidRequirementLine requirementLine = requirementLineMap.get(key);
                    if(null == requirementLine){
                        errorFlag.set(true);
                        errorMsg.append("船期路线,在需求明细不存在; ");
                    }else {
                        lgtBidShipPeriod.setRowNum(requirementLine.getRowNum());
                        lgtBidShipPeriod.setBidRequirementLineId(requirementLine.getBidRequirementLineId());
                    }
                    if (!hashSet.add(key)) {
                        errorFlag.set(true);
                        errorMsg.append("船期路线,存在重复; ");
                    }
                }

                if(errorMsg.length() > 0){
                    lgtBidShipPeriodImport.setErrorMsg(errorMsg.toString());
                }else {
                    lgtBidShipPeriodImport.setErrorMsg(null);
                }

                lgtBidShipPeriods.add(lgtBidShipPeriod);

            }
        }
    }

    @Override
    public void lgtBidShipPeriodImportModelDownload(Long bidingId,HttpServletResponse response) throws IOException {
        Assert.notNull(bidingId,"缺少参数:bidingId");
        /**
         * 判断是否陆运/铁运
         */
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        if(TransportModeEnum.LAND_TRANSPORT.name().equals(lgtBiding.getTransportModeCode()) ||
                TransportModeEnum.RAILWAY_TRANSPORT.name().equals(lgtBiding.getTransportModeCode())){
            List<LgtBidShipPeriodImportNew> lgtBidShipPeriodImportNews = new ArrayList<>();
            EasyExcelUtil.writeExcelWithModel(response,"船期明细导入模板",lgtBidShipPeriodImportNews,LgtBidShipPeriodImportNew.class);
        }else{
            List<LgtBidShipPeriodImport> lgtBidShipPeriodImports = new ArrayList<>();
            EasyExcelUtil.writeExcelWithModel(response,"船期明细导入模板",lgtBidShipPeriodImports,LgtBidShipPeriodImport.class);
        }
    }

    @Override
    public void lgtBidShipPeriodExport(Long bidingId, Long vendorId, HttpServletResponse response) throws IOException {
        Assert.notNull(bidingId,"缺少参数: bidingId");
        Assert.notNull(vendorId,"缺少参数: vendorId");
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        List<LgtBidShipPeriodExport> periodExports = new ArrayList<>();
        List<LgtBidShipPeriodExportNew> lgtBidShipPeriodExportNews = new ArrayList<>();
        // 字典
        Map<String, String> dicCodeName = EasyExcelUtil.getDicCodeName("FCL /LCL", baseClient);
        // 获取船期
        List<LgtBidShipPeriod> shipPeriods = iLgtBidShipPeriodService.list(Wrappers.lambdaQuery(LgtBidShipPeriod.class).
                eq(LgtBidShipPeriod::getBidingId, bidingId).
                eq(LgtBidShipPeriod::getVendorId, vendorId).
                eq(LgtBidShipPeriod::getRound, lgtBiding.getCurrentRound()));
        if(CollectionUtils.isNotEmpty(shipPeriods)){
            shipPeriods.forEach(lgtBidShipPeriod -> {
                if(TransportModeEnum.LAND_TRANSPORT.name().equals(lgtBiding.getTransportModeCode()) ||
                        TransportModeEnum.RAILWAY_TRANSPORT.name().equals(lgtBiding.getTransportModeCode())){
                    // 陆运/铁运
                    LgtBidShipPeriodExportNew lgtBidShipPeriodExportNew = new LgtBidShipPeriodExportNew();
                    BeanCopyUtil.copyProperties(lgtBidShipPeriodExportNew,lgtBidShipPeriod);
                    lgtBidShipPeriodExportNews.add(lgtBidShipPeriodExportNew);
                }else {
                    LgtBidShipPeriodExport periodExport = new LgtBidShipPeriodExport();
                    BeanCopyUtil.copyProperties(periodExport,lgtBidShipPeriod);
                    periodExport.setWholeArk(!ObjectUtils.isEmpty(periodExport.getWholeArk())?dicCodeName.get(periodExport.getWholeArk()):null);
                    periodExports.add(periodExport);
                }
            });
        }
        // 导出
        if(TransportModeEnum.LAND_TRANSPORT.name().equals(lgtBiding.getTransportModeCode()) ||
                TransportModeEnum.RAILWAY_TRANSPORT.name().equals(lgtBiding.getTransportModeCode())){
            EasyExcelUtil.writeExcelWithModel(response,"船期明细",lgtBidShipPeriodExportNews,LgtBidShipPeriodExportNew.class);
        }else {
            EasyExcelUtil.writeExcelWithModel(response,"船期明细",periodExports,LgtBidShipPeriodExport.class);
        }
    }

    @Override
    public Object quotedLineImport(MultipartFile file, String param,Long bidingId,Long vendorId, HttpServletResponse response) throws Exception {
        Assert.notNull(param,"缺少字段对应参数!");
        Assert.notNull(bidingId,"缺少参数: bidingId");
        Assert.isTrue(param.contains("onlyAddress"),"[序号]对应字段不能为空!");
        Assert.notNull(vendorId,"缺少参数: vendorId");
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        // 获取供应商可操作性字段
        List<String> vendorOperatingField = getVendorOperatingField(bidingId);
        vendorOperatingField.add("onlyAddress");
        Assert.isTrue(CollectionUtils.isNotEmpty(vendorOperatingField),"当前供应商无可操作性字段权限,不可导入报价!");
        Assert.isTrue(lgtBiding.getEnrollEndDatetime().compareTo(new Date()) > 0,"投标时间已截止,不能导入报价!");
        // 检查上传文件是否.xlsx
        EasyExcelUtil.checkExcelIsXlsx(file);
        LgtVendorQuotedHead vendorQuotedHead = iLgtVendorQuotedHeadService.getOne(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, bidingId).
                eq(LgtVendorQuotedHead::getVendorId, vendorId).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).last("LIMIT 1"));
        Assert.isTrue(!BiddingOrderStates.SUBMISSION.getValue().equals(vendorQuotedHead.getStatus()),"当前供应商已提交投标,不能导入报价!");
        Map<String,String> fieldCodeName = JSON.parse(param, Map.class);
        Assert.notNull(fieldCodeName.get("onlyAddress"),"路线地址唯一Key对应字段关系不能为空!");

        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 错误信息集合
        List<String> errorMsgs = new ArrayList<>();
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = new ArrayList<>();
        // 读取数据
        Workbook workbook = readExcelData(file, fieldCodeName, errorFlag, bidingId, errorMsgs, lgtVendorQuotedLines,vendorOperatingField);
        if(errorFlag.get()){
            // 上传错误文件
            return ExcelUtil.getUploadErrorFile(file,errorMsgs,workbook,fileCenterClient);
        }else {
            /**
             * 唯一key : 明细ID+ LEG +费用 项+计费方式+计费单位
             */
            saveImportData(bidingId, lgtBiding, vendorOperatingField, vendorQuotedHead, lgtVendorQuotedLines);
        }

        List<LgtVendorQuotedLine> vendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                eq(LgtVendorQuotedLine::getQuotedHeadId, vendorQuotedHead.getQuotedHeadId()));
        return vendorQuotedLines;
    }

    /**
     * 保存导入的报价数据
     * @param bidingId
     * @param lgtBiding
     * @param vendorOperatingField
     * @param vendorQuotedHead
     * @param lgtVendorQuotedLines
     */
    @Transactional
    public void saveImportData(Long bidingId, LgtBiding lgtBiding, List<String> vendorOperatingField, LgtVendorQuotedHead vendorQuotedHead, List<LgtVendorQuotedLine> lgtVendorQuotedLines) {
        // 初始化供应商报价行
        initVendorQuotedLine(vendorQuotedHead,lgtBiding);
        List<String> list = Arrays.asList("chargeUnit", "chargeMethod", "expenseItem", "leg");
        List<String> tempList = new ArrayList<>();
        vendorOperatingField.forEach(s -> {
            if(list.contains(s)){
                tempList.add(s+"Name");
            }
            if(allFieldList.contains(s)){
                tempList.add(s+"Code");
            }
        });

        vendorOperatingField.addAll(tempList);

        if (CollectionUtils.isNotEmpty(lgtVendorQuotedLines)) {
            List<LgtVendorQuotedLine> vendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                    eq(LgtVendorQuotedLine::getQuotedHeadId, vendorQuotedHead.getQuotedHeadId()).
                    eq(LgtVendorQuotedLine::getRound, lgtBiding.getCurrentRound()));
            // 国内
            Map<String, LgtVendorQuotedLine> quotedLineMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(vendorQuotedLines)){
                quotedLineMap = vendorQuotedLines.stream().collect(Collectors.
                        toMap(lgtVendorQuotedLine ->
                                        lgtVendorQuotedLine.getBidRequirementLineId() +
                                                lgtVendorQuotedLine.getChargeMethod()+
                                                lgtVendorQuotedLine.getChargeUnit()+
                                                lgtVendorQuotedLine.getLeg()+
                                                lgtVendorQuotedLine.getExpenseItem(),
                                Function.identity(),(k1,k2)->k1));
            }
            List<LgtVendorQuotedLine> lgtVendorQuotedLinesSum = new ArrayList<>();
            for(LgtVendorQuotedLine lgtVendorQuotedLine : lgtVendorQuotedLines){
                LgtVendorQuotedLine vendorQuotedLine;
                String key = lgtVendorQuotedLine.getBidRequirementLineId() +
                        lgtVendorQuotedLine.getChargeMethod()+
                        lgtVendorQuotedLine.getChargeUnit()+
                        lgtVendorQuotedLine.getLeg()+
                        lgtVendorQuotedLine.getExpenseItem();
                vendorQuotedLine = quotedLineMap.get(key);

                if(null == vendorQuotedLine){
                    vendorQuotedLine = iLgtVendorQuotedLineService.getOne(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                            eq(LgtVendorQuotedLine::getQuotedHeadId, vendorQuotedHead.getQuotedHeadId()).
                            eq(LgtVendorQuotedLine::getRound, lgtBiding.getCurrentRound()).
                            eq(LgtVendorQuotedLine::getBidRequirementLineId,lgtVendorQuotedLine.getBidRequirementLineId()).
                            and(wrapper-> wrapper.eq(LgtVendorQuotedLine::getChargeMethod,"").or().isNull(LgtVendorQuotedLine::getChargeMethod)).
                            and(wrapper-> wrapper.eq(LgtVendorQuotedLine::getChargeUnit,"").or().isNull(LgtVendorQuotedLine::getChargeUnit)).
                            and(wrapper-> wrapper.eq(LgtVendorQuotedLine::getLeg,"").or().isNull(LgtVendorQuotedLine::getLeg)).
                            and(wrapper-> wrapper.eq(LgtVendorQuotedLine::getExpenseItem,"").or().isNull(LgtVendorQuotedLine::getExpenseItem)).
                            last(" LIMIT 1"));
                }

                Class<? extends LgtVendorQuotedLine> aClass = LgtVendorQuotedLine.class;
                if(null != vendorQuotedLine){
                    // 更新
                    LgtVendorQuotedLine finalVendorQuotedLine = vendorQuotedLine;
                    vendorOperatingField.forEach(field->{
                        try {
                            Field declaredField = aClass.getDeclaredField(field);
                            declaredField.setAccessible(true);
                            Object value = declaredField.get(lgtVendorQuotedLine);
                            declaredField.set(finalVendorQuotedLine,value);
                        } catch (Exception e) {
                            log.error("物流招标报价导入更新数据字段赋值报错:"+e);
                            throw new BaseException("物流招标报价导入更新数据字段赋值报错:"+e);
                        }
                    });
                    iLgtVendorQuotedLineService.updateById(finalVendorQuotedLine);
                    lgtVendorQuotedLinesSum.add(finalVendorQuotedLine);
                }else {
                    // 新增
                    LgtBidRequirementLine lgtBidRequirementLine = iLgtBidRequirementLineService.getById(lgtVendorQuotedLine.getBidRequirementLineId());
                    LgtVendorQuotedLine quotedLine = new LgtVendorQuotedLine();
                    BeanCopyUtil.copyProperties(quotedLine,lgtBidRequirementLine);
                    quotedLine.setQuotedLineId(IdGenrator.generate());
                    quotedLine.setQuotedHeadId(vendorQuotedHead.getQuotedHeadId());
                    quotedLine.setBidingId(bidingId);
                    quotedLine.setRound(lgtBiding.getCurrentRound());
                    quotedLine.setPurchaseRemark(lgtBidRequirementLine.getComments());
                    quotedLine.setVendorId(vendorQuotedHead.getVendorId());
                    quotedLine.setVendorCode(vendorQuotedHead.getVendorCode());
                    quotedLine.setVendorName(vendorQuotedHead.getVendorName());
                    quotedLine.setIfCopy(YesOrNo.YES.getValue());
                    vendorOperatingField.forEach(field->{
                        // 排除路线字段
                        try {
                            Field declaredField = aClass.getDeclaredField(field);
                            declaredField.setAccessible(true);
                            declaredField.set(quotedLine,null);
                            Object value = declaredField.get(lgtVendorQuotedLine);
                            declaredField.set(quotedLine,value);
                        } catch (Exception e) {
                            log.error("物流招标报价导入新增数据字段赋值报错:"+e);
                            throw new BaseException("物流招标报价导入更新数据字段赋值报错:"+e);
                        }
                    });
                    LgtBidingServiceImpl.setStartEndAddress(quotedLine);
                    iLgtVendorQuotedLineService.save(quotedLine);
                    lgtVendorQuotedLinesSum.add(quotedLine);
                }
            }
            if(CollectionUtils.isNotEmpty(lgtVendorQuotedLinesSum)){
                // 计算供应商报价行信息
                calculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLinesSum);
            }
        }
    }

    // 初始化供应商报价行
    public void initVendorQuotedLine(LgtVendorQuotedHead lgtVendorQuotedHead,LgtBiding lgtBiding){
        // 查找供应商报价明细行
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(new QueryWrapper<>(new LgtVendorQuotedLine().
                setQuotedHeadId(lgtVendorQuotedHead.getQuotedHeadId())));
        if(CollectionUtils.isEmpty(lgtVendorQuotedLines)){
            // 从明细表里复制一份
            List<LgtBidRequirementLine> lgtBidRequirementLines = iLgtBidRequirementLineService.list(new QueryWrapper<>(new LgtBidRequirementLine().
                    setBidingId(lgtBiding.getBidingId())));
            if(CollectionUtils.isNotEmpty(lgtBidRequirementLines)){
                List<LgtVendorQuotedLine> vendorQuotedLines = new ArrayList<>();
                lgtBidRequirementLines.forEach(lgtBidRequirementLine -> {
                    LgtVendorQuotedLine lgtVendorQuotedLine = new LgtVendorQuotedLine();
                    BeanCopyUtil.copyProperties(lgtVendorQuotedLine,lgtBidRequirementLine);
                    lgtVendorQuotedLine.setQuotedLineId(IdGenrator.generate());
                    lgtVendorQuotedLine.setQuotedHeadId(lgtVendorQuotedHead.getQuotedHeadId());
                    lgtVendorQuotedLine.setBidingId(lgtBiding.getBidingId());
                    lgtVendorQuotedLine.setRound(lgtBiding.getCurrentRound());
                    lgtVendorQuotedLine.setPurchaseRemark(lgtBidRequirementLine.getComments());
                    lgtVendorQuotedLine.setVendorId(lgtVendorQuotedHead.getVendorId());
                    lgtVendorQuotedLine.setVendorCode(lgtVendorQuotedHead.getVendorCode());
                    lgtVendorQuotedLine.setVendorName(lgtVendorQuotedHead.getVendorName());
                    vendorQuotedLines.add(lgtVendorQuotedLine);
                });
                iLgtVendorQuotedLineService.saveBatch(vendorQuotedLines);
            }
        }
    }

    // 获取供应商可操作性字段
    public List<String> getVendorOperatingField(Long bidingId) {
        List<String> operatingFields = new ArrayList<>();
        // 获取供应商可操作列
        List<LgtBidTemplate> lgtBidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).
                eq(LgtBidTemplate::getBidingId, bidingId).
                eq(LgtBidTemplate::getVendorOperateFlag, YesOrNo.YES.getValue()));
        if(CollectionUtils.isNotEmpty(lgtBidTemplates)){
            operatingFields = lgtBidTemplates.stream().map(lgtBidTemplate -> StringUtil.toCamelCase(lgtBidTemplate.getFieldCode())).collect(Collectors.toList());
        }
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        /**
         * 如果是过内业务增加: 计费方式和计费单位
         */
        if(CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
            if(!operatingFields.contains("chargeUnit")){
                operatingFields.add("chargeUnit");
            }
            if(!operatingFields.contains("chargeMethod")){
                operatingFields.add("chargeMethod");
            }
        }
        return operatingFields;
    }
    // 起全地点字段
    private final static List<String> startAddList = Arrays.asList("fromCountry", "fromProvince", "fromCity", "fromCounty");
    // 终全地点字段
    private final static List<String> endAddList = Arrays.asList("toCountry", "toProvince", "toCity", "toCounty");
    // 地点字段
    private final static List<String> placeFieldList = Arrays.asList("fromCountry", "fromProvince", "fromCity", "fromCounty","toCountry", "toProvince", "toCity", "toCounty");
    // 港口字段
    private final static List<String> portFieldList = Arrays.asList("fromPort","toPort");
    // 全部地点字段
    private final static List<String> allFieldList = Arrays.asList("fromCountry", "fromProvince", "fromCity", "fromCounty","toCountry", "toProvince", "toCity", "toCounty","fromPort","toPort");


    public Workbook readExcelData(MultipartFile file, Map<String,String> fieldCodeName,AtomicBoolean errorFlag,
                                  Long bidingId,List<String> errorMsgs,List<LgtVendorQuotedLine> lgtVendorQuotedLines,List<String> vendorOperatingField) throws Exception {
        log.info("--------------------------------------物流招标导入读取数据开始---------------------------------");
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 读取excel标题行
        List<String> head = getExcelHead(sheet);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();

        List<String> legNames = new ArrayList<>();
        List<String> chargeMethodNames = new ArrayList<>();
        List<String> regionNames = new ArrayList<>(); // 行政区域名称  placeFieldList
        List<String> portNames = new ArrayList<>(); // 港口名称
        for (int i =1;i <= totalRows;i++){
            // 获取行内容
            Row row = sheet.getRow(i);
            String value1 = getCellValue(head, row, fieldCodeName.get("leg"));
            if(StringUtil.notEmpty(value1)){
                legNames.add(value1.trim());
            }
            String value2 = getCellValue(head, row, fieldCodeName.get("chargeMethod"));
            if(StringUtil.notEmpty(value2)){
                chargeMethodNames.add(value2.trim());
            }

            placeFieldList.forEach(key->{
                // 获取对应字段关系
                String str = fieldCodeName.get(key);
                if (!ObjectUtils.isEmpty(str)) {
                    String value = getCellValue(head, row, str);
                    if(StringUtil.notEmpty(value)){
                        regionNames.add(value.trim());
                    }
                }
            });

            portFieldList.forEach(key->{
                // 获取对应字段关系
                String str = fieldCodeName.get(key);
                if (!ObjectUtils.isEmpty(str)) {
                    String value = getCellValue(head, row, str);
                    if(StringUtil.notEmpty(value)){
                        portNames.add(value.trim());
                    }
                }
            });
        }

        // 港口 名字-编码
        Map<String,String> portNameMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(portNames)) {
            /**
             * 如果空运, 就是空港
             * 海运,就是海港
             */
            String portType = null;
            if(TransportModeEnum.AIR_TRANSPORT.name().equals(lgtBiding.getTransportModeCode())){
                // 空运
                portType = "AIR";
            }else if(TransportModeEnum.SEA_TRANSPORT.name().equals(lgtBiding.getTransportModeCode())){
                // 海运
                portType = "OCN";
            }else if(TransportModeEnum.LAND_TRANSPORT.name().equals(lgtBiding.getTransportModeCode())){
                //
                portType = "LAND";
            }
            List<Port> portList = iPortService.list(Wrappers.lambdaQuery(Port.class).
                    in(Port::getPortNameZhs,portNames).
                    eq(!ObjectUtils.isEmpty(portType),Port::getPortType,portType));
            if(CollectionUtils.isNotEmpty(portList)){
                portNameMap = portList.stream().collect(Collectors.toMap(Port::getPortNameEn, Port::getPortCode, (k1, k2) -> k1));
            }
        }

        // 行政区域 (名字_父编码)-实体
        Map<String, Region> regionParentMap = new HashMap<>();
        // 行政区域 (名字)-实体
        Map<String, Region> regionMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(regionNames)) {
            List<Region> regions = iRegionService.list(Wrappers.lambdaQuery(Region.class).in(Region::getRegionName,regionNames));
            if(CollectionUtils.isNotEmpty(regions)){
                regionParentMap = regions.stream().collect(Collectors.toMap(region -> region.getRegionName() +region.getParentRegionCode(),Function.identity(),(k1, k2)->k1));
                regionMap = regions.stream().collect(Collectors.toMap(region -> String.valueOf(region.getRegionName()),Function.identity(),(k1,k2)->k1));
            }
        }

        // 查询leg和费项的关系
        Map<String, Map<String, String>> legChargeMap = iExpenseItemService.queryLegChargeMap(legNames,bidingId);
        // 查询计费方式和计费单位的关系
        Map<String, Map<String, String>> billingCombinationMap = baseClient.queryBillingCombinationMap(chargeMethodNames);
        // 费项字典
        Map<String, String> chargeNameMap = EasyExcelUtil.getDicNameCode("CHARGE_NAME", baseClient);
        // 计费方式
        Map<String, String> chargeLevelMap = EasyExcelUtil.getDicNameCode("CHARGE_LEVEL", baseClient);
        // 计费单位
        Map<String, String> subLevelMap = EasyExcelUtil.getDicNameCode("SUB_LEVEL", baseClient);
        // 整柜/拼柜
        Map<String, String> fclLclMap = EasyExcelUtil.getDicNameCode("FCL/LCL", baseClient);
        // LEG
        Map<String, String> legMap = EasyExcelUtil.getDicNameCode("LEG", baseClient);
        // 币种
        Map<String, String> currencyNameCode = EasyExcelUtil.getCurrencyNameCode(baseClient);
        // 获取需求行信息 行序号-行id
        Map<Integer, Long> onlyAddMap = getOnlyAdds(bidingId);
        // 获取供应商必填项
        List<String> requiredField = getRequiredField(bidingId);
        // 供应商必填项标题
        Map<String, String> requiredFieldMap = getRequiredFieldMap(bidingId);
        // 供应商可操作字段标题
        Map<String, String> operabilityFieldMap = getOperabilityFieldMap(bidingId);
        Set<String> hashSet = new HashSet<>();
        Set<Integer> hashSetRowNum = new HashSet<>();

        for (int i =1;i <= totalRows;i++){
            LgtVendorQuotedLine lgtVendorQuotedLine = new LgtVendorQuotedLine();
            StringBuffer errorMsg = new StringBuffer();
            // 获取行内容
            Row row = sheet.getRow(i);
            vendorOperatingField.forEach(key -> {
                // 列标题
                String title = fieldCodeName.get(key);
                // 单元格内容
                String value = getCellValue(head, row, title);

                // 获取对应字段名
                String fieldName = key;
                if (StringUtil.notEmpty(value)) {
                    value = value.trim();
                    // 检查数值格式
                    checkForm(errorMsg, title, value, fieldName,errorFlag,lgtVendorQuotedLine);
                    // 赋值
                    setFieldValue(errorFlag, chargeNameMap, chargeLevelMap, subLevelMap, fclLclMap, legMap, currencyNameCode, lgtVendorQuotedLine, errorMsg, title, value, fieldName);
                }else {
                    // 校验是否必填
                    if(requiredField.contains(fieldName)){
                        errorMsg.append(String.format("[%s]不能为空!; ",requiredFieldMap.get(fieldName)));
                        errorFlag.set(true);
                    }
                }
            });

            // 检查有没有序号
            Integer onlyAddress = lgtVendorQuotedLine.getOnlyAddress();
            if(ObjectUtils.isEmpty(onlyAddress)){
                errorFlag.set(true);
                errorMsg.append("序号不能为空; ");
            }else {
                /**
                 * 序号对应需求行的行号, 非国内不能重复, 非国内可以重复
                 */
                // 需求行ID
                Long bidRequirementLineId = onlyAddMap.get(onlyAddress);
                if(null != bidRequirementLineId){
                    lgtVendorQuotedLine.setBidRequirementLineId(bidRequirementLineId);
                    lgtVendorQuotedLine.setRowNum(onlyAddress);
                }else {
                    errorFlag.set(true);
                    errorMsg.append("[序号]在物流需求中不存在; ");
                }
            }

            if(errorMsg.length() <= 0){
                if(CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
                    Integer rowNum = lgtVendorQuotedLine.getRowNum();
                    // 国内供应商行号不能重复
                    if(!hashSetRowNum.add(rowNum)){
                        errorFlag.set(true);
                        errorMsg.append("国内业务[序号]存在重复!; ");
                    }
                }
                // 校验唯一 LEG +费项+计费方式+计费单位
                StringBuffer onlyKey = new StringBuffer();
                onlyKey.append(lgtVendorQuotedLine.getRowNum()).
                        append(lgtVendorQuotedLine.getLeg()).
                        append(lgtVendorQuotedLine.getExpenseItem()).
                        append(lgtVendorQuotedLine.getChargeMethod()).
                        append(lgtVendorQuotedLine.getChargeUnit());
                if(!hashSet.add(onlyKey.toString())){
                    errorFlag.set(true);
                    errorMsg.append("导入报价数据存在重复!; ");
                }
            }

            /**
             * 检查地点信息
             */
            if(errorMsg.length() <= 0) {
                Class<LgtVendorQuotedLine> quotedLineClass = LgtVendorQuotedLine.class;
                // 字段名-code值
                Map<String, String> fieldCodeMap = new HashMap<>();
                // 处理起点
                processingStartingPoint(startAddList,errorFlag, regionParentMap, regionMap, operabilityFieldMap, lgtVendorQuotedLine, errorMsg, quotedLineClass, fieldCodeMap);
                // 处理终点
                processingStartingPoint(endAddList,errorFlag, regionParentMap, regionMap, operabilityFieldMap, lgtVendorQuotedLine, errorMsg, quotedLineClass, fieldCodeMap);
            }

            /**
             * 检查港口
             */
            if(errorMsg.length() <= 0){
                // portCodeMap
                Class<LgtVendorQuotedLine> quotedLineClass = LgtVendorQuotedLine.class;
                for(String key : portFieldList){
                    if(errorMsg.length() > 0) break;
                    try {
                        Object value = getFieldValue(lgtVendorQuotedLine, quotedLineClass, key);
                        if(!ObjectUtils.isEmpty(value)){
                            // 港口名字
                            String portName = value.toString();
                            // 港口编码
                            String portCode = portNameMap.get(portName);
                            if(null != portCode){
                                key = key+"Code";
                                setFieldValue(lgtVendorQuotedLine, quotedLineClass, key,portCode);
                            }else {
                                errorFlag.set(true);
                                errorMsg.append(String.format("[%s]填写错误; ",operabilityFieldMap.get(key)));
                            }
                        }
                    } catch (Exception e) {
                        errorFlag.set(true);
                        errorMsg.append("校验港口程序报错:"+e.getMessage());
                        break;
                    }
                }
            }

            if(errorMsg.length() <= 0) {
                /**
                 * 1. 检查计费方式和计费单位的层级关系
                 * 2. 检查leg和费项的层级关系
                 */
                if (vendorOperatingField.contains("chargeMethod") && vendorOperatingField.contains("chargeUnit")) {
                    String chargeUnit = lgtVendorQuotedLine.getChargeUnit();
                    String chargeMethod = lgtVendorQuotedLine.getChargeMethod();
                    if(!ObjectUtils.isEmpty(chargeUnit) && !ObjectUtils.isEmpty(chargeMethod)){
                        Map<String, String> map = billingCombinationMap.get(chargeMethod);
                        if(null == map){
                            errorMsg.append("计费方式和计费单位不是层级关系!; ");
                            errorFlag.set(true);
                        }else {
                            if(ObjectUtils.isEmpty(map.get(chargeUnit))){
                                errorMsg.append("计费方式和计费单位不是层级关系!; ");
                                errorFlag.set(true);
                            }
                        }
                    }
                }

                if (vendorOperatingField.contains("leg") && vendorOperatingField.contains("expenseItem")) {
                    String leg = lgtVendorQuotedLine.getLeg();
                    String expenseItem = lgtVendorQuotedLine.getExpenseItem();
                    if(!ObjectUtils.isEmpty(leg) && !ObjectUtils.isEmpty(expenseItem)){
                        Map<String, String> map = legChargeMap.get(leg);
                        if(null == map){
                            errorMsg.append("LEG和费项不是层级关系!; ");
                            errorFlag.set(true);
                        }else {
                            if(ObjectUtils.isEmpty(map.get(expenseItem))){
                                errorMsg.append("LEG和费项不是层级关系!; ");
                                errorFlag.set(true);
                            }
                        }
                    }
                }
            }
            errorMsgs.add(errorMsg.toString());
            lgtVendorQuotedLines.add(lgtVendorQuotedLine);
        }
        log.info("--------------------------------------物流招标导入读取数据结束---------------------------------");
        return workbook;
    }

    public void processingStartingPoint(List<String> addList,AtomicBoolean errorFlag, Map<String, Region> regionParentMap, Map<String, Region> regionMap, Map<String, String> operabilityFieldMap, LgtVendorQuotedLine lgtVendorQuotedLine, StringBuffer errorMsg, Class<LgtVendorQuotedLine> quotedLineClass, Map<String, String> fieldCodeMap) {
        for(int j = 0; j<addList.size();j++){
            if(errorMsg.length() > 0 ){
                break;
            }
            try {
                String key = addList.get(j);
                Object value = getFieldValue(lgtVendorQuotedLine, quotedLineClass, key);
                if(!ObjectUtils.isEmpty(value)){
                    // 地点名字
                    String addName = value.toString();
                    // 看有没父的值
                    if(j != 0){
                        String previousKey = addList.get(j - 1);
                        String regionCode = fieldCodeMap.get(previousKey);
                        if(ObjectUtils.isEmpty(regionCode)){
                            // 设置地点编码
                            setAddCode(addList,errorFlag, regionMap, operabilityFieldMap, lgtVendorQuotedLine, errorMsg, quotedLineClass, fieldCodeMap, j, key, addName);
                        }else {
                            Region region = regionParentMap.get(addName + regionCode);
                            if(null != region){
                                // 把  字段名-code值 储存
                                fieldCodeMap.put(addList.get(j),region.getRegionCode());
                                key = key+"Code";
                                setFieldValue(lgtVendorQuotedLine, quotedLineClass, key,region.getRegionCode());
                            }else {
                                errorFlag.set(true);
                                errorMsg.append(String.format("[%s]和[%s]不是级联关系",operabilityFieldMap.get(addList.get(j)),operabilityFieldMap.get(addList.get(j-1))));
                            }
                        }
                    }else {
                        // 设置地点编码
                        setAddCode(addList,errorFlag, regionMap, operabilityFieldMap, lgtVendorQuotedLine, errorMsg, quotedLineClass, fieldCodeMap, j, key, addName);
                    }
                }
            } catch (Exception e) {
                errorFlag.set(true);
                errorMsg.append("校验地点程序报错:"+e.getMessage());
                break;
            }
        }
    }

    public void setAddCode(List<String> addList, AtomicBoolean errorFlag, Map<String, Region> regionMap, Map<String, String> operabilityFieldMap, LgtVendorQuotedLine lgtVendorQuotedLine, StringBuffer errorMsg, Class<LgtVendorQuotedLine> quotedLineClass, Map<String, String> fieldCodeMap, int j, String key, String addName) throws NoSuchFieldException, IllegalAccessException {
        Region region = regionMap.get(addName);
        if(null != region){
            // 把  字段名-code值 储存
            fieldCodeMap.put(addList.get(j),region.getRegionCode());
            key = key+"Code";
            setFieldValue(lgtVendorQuotedLine, quotedLineClass, key,region.getRegionCode());
        }else {
            errorFlag.set(true);
            errorMsg.append(String.format("[%s]填写错误:",operabilityFieldMap.get(key)));
        }
    }

    public Object getFieldValue(LgtVendorQuotedLine lgtVendorQuotedLine, Class<LgtVendorQuotedLine> quotedLineClass, String key) throws NoSuchFieldException, IllegalAccessException {
        // 获取
        Field field = quotedLineClass.getDeclaredField(key);
        field.setAccessible(true);
        return field.get(lgtVendorQuotedLine);
    }

    public void setFieldValue(LgtVendorQuotedLine lgtVendorQuotedLine, Class<LgtVendorQuotedLine> quotedLineClass, String key,Object value) throws NoSuchFieldException, IllegalAccessException {
        // 获取
        Field field = quotedLineClass.getDeclaredField(key);
        field.setAccessible(true);
        field.set(lgtVendorQuotedLine,value);
    }

    public String getCellValue(List<String> head, Row row, String title) {
        String cellValue = null;
        if (StringUtil.notEmpty(title)) {
            int index = head.indexOf(title);
            if (index >= 0) {
                Cell cell = row.getCell(index);
                // 获取值
                cellValue =  ExcelUtil.getCellValue(cell);
            }
        }
        return cellValue;
    }

    public List<String> getRequiredField(Long bidingId) {
        List<String> requiredField = new ArrayList<>();
        List<LgtBidTemplate> lgtBidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).
                eq(LgtBidTemplate::getBidingId, bidingId).
                eq(LgtBidTemplate::getVendorNotEmptyFlag, YesOrNo.YES.getValue()));
        if(CollectionUtils.isNotEmpty(lgtBidTemplates)){
            requiredField = lgtBidTemplates.stream().map(lgtBidTemplate -> StringUtil.toCamelCase(lgtBidTemplate.getFieldCode())).collect(Collectors.toList());
        }
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        /**
         * 如果是过内业务增加: 计费方式和计费单位
         */
        if(CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
            if(!requiredField.contains("chargeUnit")){
                requiredField.add("chargeUnit");
            }
            if(!requiredField.contains("chargeMethod")){
                requiredField.add("chargeMethod");
            }
        }
        return requiredField;
    }

    public Map<String,String> getRequiredFieldMap(Long bidingId) {
        Map<String,String> requiredField = new HashMap<>();
        List<LgtBidTemplate> lgtBidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).
                eq(LgtBidTemplate::getBidingId, bidingId).
                eq(LgtBidTemplate::getVendorNotEmptyFlag, YesOrNo.YES.getValue()));
        if(CollectionUtils.isNotEmpty(lgtBidTemplates)){
            requiredField = lgtBidTemplates.stream().collect(Collectors.toMap(lgtBidTemplate -> StringUtil.toCamelCase(lgtBidTemplate.getFieldCode()), LgtBidTemplate::getFieldName, (k1, k2) -> k1));
        }
        return requiredField;
    }

    public Map<String,String> getOperabilityFieldMap(Long bidingId) {
        Map<String,String> requiredField = new HashMap<>();
        List<LgtBidTemplate> lgtBidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).
                eq(LgtBidTemplate::getBidingId, bidingId).
                eq(LgtBidTemplate::getVendorOperateFlag, YesOrNo.YES.getValue()));
        if(CollectionUtils.isNotEmpty(lgtBidTemplates)){
            requiredField = lgtBidTemplates.stream().collect(Collectors.toMap(lgtBidTemplate -> StringUtil.toCamelCase(lgtBidTemplate.getFieldCode()), LgtBidTemplate::getFieldName, (k1, k2) -> k1));
        }
        return requiredField;
    }

    public Map<Integer,Long> getOnlyAdds(Long bidingId) {
        Map<Integer,Long> onlyAddMap = new HashMap<>();
        List<LgtBidRequirementLine> lgtBidRequirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).
                eq(LgtBidRequirementLine::getBidingId, bidingId));
        if(CollectionUtils.isNotEmpty(lgtBidRequirementLines)){
            lgtBidRequirementLines.forEach(LgtBidingVendorServiceImpl::setStartEndAddress);
            onlyAddMap = lgtBidRequirementLines.stream().collect(Collectors.toMap(LgtBidRequirementLine::getRowNum, LgtBidRequirementLine::getBidRequirementLineId,(k1,k2)->k1));
        }
        return onlyAddMap;
    }

    public Map<String,Long> getOnlyAddsByI(Long bidingId) {
        Map<String,Long> onlyAddMap = new HashMap<>();
        List<LgtBidRequirementLine> lgtBidRequirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).
                eq(LgtBidRequirementLine::getBidingId, bidingId));
        if(CollectionUtils.isNotEmpty(lgtBidRequirementLines)){
            /**
             * 船期改为编码
             */
            lgtBidRequirementLines.forEach(LgtBidingVendorServiceImpl::setStartEndAddress);
            onlyAddMap = lgtBidRequirementLines.stream().collect(Collectors.toMap(lgtBidRequirementLine -> lgtBidRequirementLine.getStartAddress() + lgtBidRequirementLine.getEndAddress() + lgtBidRequirementLine.getChargeMethod() + lgtBidRequirementLine.getChargeUnit(), LgtBidRequirementLine::getBidRequirementLineId));
        }
        return onlyAddMap;
    }

    /**
     * 自动拼接 起运地/最终地
     * @param obj
     */
    public static <T> void setStartEndAddress(T obj){
        /**
         * 最终地 - toCountry-toProvince-toCity-toCounty-toPlace-toPortId
         * 起运地 - fromCountry-fromProvince-fromCity-fromCounty-fromPlace-fromPortCode
         */
        List<String> startList = Arrays.asList("fromCountry", "fromProvince", "fromCity", "fromCounty", "fromPlace","fromPortCode");
        List<String> endList = Arrays.asList("toCountry", "toProvince", "toCity", "toCounty", "toPlace","toPortCode");
        StringBuffer startAddress = new StringBuffer();
        StringBuffer endAddress = new StringBuffer();
        endList.forEach(key->{
            getField(obj, endAddress,key);
        });
        startList.forEach(key->{
            getField(obj, startAddress,key);
        });
        Class aClass = obj.getClass();
        try {
            Field startAddress1 = aClass.getDeclaredField("startAddress");
            startAddress1.setAccessible(true);
            Field endAddress1 = aClass.getDeclaredField("endAddress");
            endAddress1.setAccessible(true);
            startAddress1.set(obj,startAddress.toString());
            endAddress1.set(obj,endAddress.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static <T> void getField(T obj, StringBuffer endAddress,String key) {
        try {
            Field field = obj.getClass().getDeclaredField(key);
            if (null != field) {
                field.setAccessible(true);
                Object o = field.get(obj);
                if (!ObjectUtils.isEmpty(o)) {
                    endAddress.append(o);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void setFieldValue(AtomicBoolean errorFlag, Map<String, String> chargeNameMap, Map<String, String> chargeLevelMap, Map<String, String> subLevelMap, Map<String, String> fclLclMap, Map<String, String> legMap, Map<String, String> currencyNameCode, LgtVendorQuotedLine lgtVendorQuotedLine, StringBuffer errorMsg, String title, String value, String fieldName) {
        try {
            Class<LgtVendorQuotedLine> lgtVendorQuotedLineClass = LgtVendorQuotedLine.class;
            Field field = lgtVendorQuotedLineClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            switch (fieldName){
                case "expenseItem":
                    // 费项
                    setDicValue(errorFlag, chargeNameMap, lgtVendorQuotedLine, errorMsg, value, field,title);
                    Field field1 = lgtVendorQuotedLineClass.getDeclaredField("expenseItemName");
                    field1.setAccessible(true);
                    field1.set(lgtVendorQuotedLine,value);
                    break;
                case "chargeMethod":
                    // 计费方式
                    setDicValue(errorFlag, chargeLevelMap, lgtVendorQuotedLine, errorMsg, value, field,title);
                    Field field2 = lgtVendorQuotedLineClass.getDeclaredField("chargeMethodName");
                    field2.setAccessible(true);
                    field2.set(lgtVendorQuotedLine,value);
                    break;
                case "chargeUnit":
                    // 计费单位
                    setDicValue(errorFlag, subLevelMap, lgtVendorQuotedLine, errorMsg, value, field,title);
                    Field field3 = lgtVendorQuotedLineClass.getDeclaredField("chargeUnitName");
                    field3.setAccessible(true);
                    field3.set(lgtVendorQuotedLine,value);
                    break;
                case "wholeArk":
                    // 整柜/拼柜
                    setDicValue(errorFlag, fclLclMap, lgtVendorQuotedLine, errorMsg, value, field,title);
                    break;
                case "leg":
                    setDicValue(errorFlag, legMap, lgtVendorQuotedLine, errorMsg, value, field,title);
                    Field field4 = lgtVendorQuotedLineClass.getDeclaredField("legName");
                    field4.setAccessible(true);
                    field4.set(lgtVendorQuotedLine,value);
                    break;
                case "currency":
                    setDicValue(errorFlag, currencyNameCode, lgtVendorQuotedLine, errorMsg, value, field,title);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error(""+e);
        }
    }

    public void setDicValue(AtomicBoolean errorFlag, Map<String, String> dicMap, LgtVendorQuotedLine lgtVendorQuotedLine, StringBuffer errorMsg, String value, Field field,String title) throws IllegalAccessException {
        String dicCode = dicMap.get(value);
        if(StringUtil.notEmpty(dicCode)){
            field.set(lgtVendorQuotedLine,dicCode);
        }else {
            errorFlag.set(true);
            errorMsg.append(String.format("%s字典值不存在; ",title));
        }
    }

    public void checkForm(StringBuffer errorMsg, String title, String value, String fieldName,AtomicBoolean errorFlag,LgtVendorQuotedLine lgtVendorQuotedLine) {
        try {
            Class<LgtVendorQuotedLine> lgtVendorQuotedLineClass = LgtVendorQuotedLine.class;
            Field field = lgtVendorQuotedLineClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            String typeName = field.getType().getSimpleName();
            switch (typeName){
                case "Long":
                    long aLong = Long.parseLong(value);
                    field.set(lgtVendorQuotedLine,aLong);
                    break;
                case "Integer":
                    int i1 = Integer.parseInt(value);
                    field.set(lgtVendorQuotedLine,i1);
                    break;
                case "BigDecimal":
                    BigDecimal decimal = new BigDecimal(value);
                    field.set(lgtVendorQuotedLine,decimal);
                    break;
                default:
                    field.set(lgtVendorQuotedLine,value);
            }
        } catch (Exception e) {
            errorMsg.append(String.format("[%s]格式非法!; ",title));
            errorFlag.set(true);
        }
    }

    public List<String> getExcelHead(Sheet sheet) {
        // 获取标题行数据
        List<String> head = new ArrayList<>();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        // 获取首行
        Row headRow = sheet.getRow(0);
        // 遍历每个单元格的值
        for (int i = 0; i < totalCells; i++) {
            Cell cell = headRow.getCell(i);
            head.add(ExcelUtil.getCellValue(cell));
        }
        return head;
    }

    @Override
    public LgtQuotedLineImportTitle getExcelTitle(MultipartFile file,Long bidingId) throws IOException {
        // 检查上传文件是否.xlsx
        EasyExcelUtil.checkExcelIsXlsx(file);
        // 获取导入excel标题
        List<String> titles = getTitles(file);
        Map<String, String> fieldCodeName = new HashMap<>();
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        // 获取供应商可操作列
        List<LgtBidTemplate> lgtBidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).
                eq(LgtBidTemplate::getBidingId, bidingId).
                eq(LgtBidTemplate::getVendorOperateFlag, YesOrNo.YES.getValue()));
        if(CollectionUtils.isNotEmpty(lgtBidTemplates)){
            fieldCodeName.put("onlyAddress","序号");
            /**
             * 国内业务,增加计费方式和计费单位
             */
            if(CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
                fieldCodeName.put("chargeMethod","计费方式");
                fieldCodeName.put("chargeUnit","计费单位");
            }
            lgtBidTemplates.forEach(lgtBidTemplate -> {
                fieldCodeName.put(StringUtil.toCamelCase(lgtBidTemplate.getFieldCode()),lgtBidTemplate.getFieldName());
            });
        }
        return LgtQuotedLineImportTitle.builder().titles(titles).fieldCodeName(fieldCodeName).build();
    }

    public List<String> getTitles(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        // 获取标题行数据
        List<String> titles = new ArrayList<>();
        // 获取首行
        Row headRow = sheet.getRow(0);
        // 遍历每个单元格的值
        for (int i = 0; i < totalCells; i++) {
            Cell cell = headRow.getCell(i);
            titles.add(ExcelUtil.getCellValue(cell));
        }
        return titles;
    }

    @Override
    public LgtVendorQuotedSumVendorDto getLgtVendorQuotedSumVendorDto(Long bidingId) {
        LgtVendorQuotedSumVendorDto lgtVendorQuotedSumVendorDto = new LgtVendorQuotedSumVendorDto();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtils.isEmpty(loginAppUser.getCompanyId()) || !UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            return lgtVendorQuotedSumVendorDto;
        }
        // 招标头
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        //公示结果后供应商可看见中标供应商名称 VISIBLE_WIN_VENDOR
        if (!YesOrNo.YES.getValue().equals(lgtBiding.getVisibleWinVendor())) {
            return lgtVendorQuotedSumVendorDto;
        }


        // 查询已公示的轮次
        List<LgtRound> lgtRounds = iLgtRoundService.list(Wrappers.lambdaQuery(LgtRound.class).
                eq(LgtRound::getBidingId, bidingId).
                eq(LgtRound::getPublicResult, YesOrNo.YES.getValue()));

        if (CollectionUtils.isNotEmpty(lgtRounds)) {
            List<Integer> rounds = lgtRounds.stream().map(LgtRound::getRound).collect(Collectors.toList());
            List<LgtVendorQuotedSum> lgtVendorQuotedSums = iLgtVendorQuotedSumService.list(Wrappers.lambdaQuery(LgtVendorQuotedSum.class).
                    eq(LgtVendorQuotedSum::getBidingId,bidingId).
                    in(LgtVendorQuotedSum::getRound,rounds).
                    and(wrapper -> wrapper.eq(LgtVendorQuotedSum::getBidResult, SelectionStatusEnum.WIN.getValue()).or().
                            eq(LgtVendorQuotedSum::getBidResult,SelectionStatusEnum.FIRST_WIN.getValue()).or().
                            eq(LgtVendorQuotedSum::getBidResult,SelectionStatusEnum.SECOND_WIN.getValue())).
                    eq(LgtVendorQuotedSum::getVendorId,loginAppUser.getCompanyId()));
            if(CollectionUtils.isNotEmpty(lgtVendorQuotedSums)){
                if(!YesOrNo.YES.getValue().equals(lgtBiding.getVisibleFinalPrice())){
                    lgtVendorQuotedSums.forEach(lgtVendorQuotedSum -> {
                        lgtVendorQuotedSum.setSumPrice(null);
                    });
                }
            }
            lgtVendorQuotedSumVendorDto.setLgtVendorQuotedSums(lgtVendorQuotedSums);
        }
        return lgtVendorQuotedSumVendorDto;
    }

    @Override
    public LgtVendorQuotedHeadVendorDto getLgtVendorQuotedHeadVendorDto(Long bidingId) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtils.isEmpty(loginAppUser.getCompanyId()) || !UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            return null;
        }
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        // 报价行
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(Wrappers.lambdaQuery(LgtVendorQuotedLine.class).
                eq(LgtVendorQuotedLine::getBidingId, bidingId).
                eq(LgtVendorQuotedLine::getRound, lgtBiding.getCurrentRound()).
                eq(LgtVendorQuotedLine::getVendorId, loginAppUser.getCompanyId()));
        // 船期
        List<LgtBidShipPeriod> lgtBidShipPeriods = iLgtBidShipPeriodService.list(Wrappers.lambdaQuery(LgtBidShipPeriod.class).
                eq(LgtBidShipPeriod::getBidingId, bidingId).
                eq(LgtBidShipPeriod::getRound, lgtBiding.getCurrentRound()).
                eq(LgtBidShipPeriod::getVendorId, loginAppUser.getCompanyId()));
        // 附件
        List<LgtFileConfig> lgtFileConfigs = iLgtFileConfigService.list(Wrappers.lambdaQuery(LgtFileConfig.class).
                eq(LgtFileConfig::getBidingId, bidingId));
        if(CollectionUtils.isNotEmpty(lgtFileConfigs)){
            lgtFileConfigs.forEach(lgtFileConfig -> {
                Long requireId = lgtFileConfig.getRequireId();
                LgtVendorFile lgtVendorFile = iLgtVendorFileService.getOne(Wrappers.lambdaQuery(LgtVendorFile.class).
                        eq(LgtVendorFile::getRequireId, requireId).
                        eq(LgtVendorFile::getVendorId,loginAppUser.getCompanyId()).
                        eq(LgtVendorFile::getRound, lgtBiding.getCurrentRound()).
                        last(" LIMIT 1"));
                if (null != lgtVendorFile) {
                    lgtFileConfig.setVendorDocId(lgtVendorFile.getDocId());
                    lgtFileConfig.setVendorFileName(lgtVendorFile.getFileName());
                }
            });
        }
        return LgtVendorQuotedHeadVendorDto.builder().lgtVendorQuotedLines(lgtVendorQuotedLines).lgtBidShipPeriods(lgtBidShipPeriods).lgtFileConfigs(lgtFileConfigs).build();
    }

    @Override
    public LgtBidRequirementLineVendorDto getLgtBidRequirementLineVendorDto(Long bidingId) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtils.isEmpty(loginAppUser.getCompanyId()) || !UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            return null;
        }
        List<LgtBidRequirementLine> lgtBidRequirementLines = iLgtBidRequirementLineService.list(new QueryWrapper<>(new LgtBidRequirementLine().setBidingId(bidingId)));
        List<LgtBidTemplate> bidTemplates = iLgtBidTemplateService.list(new QueryWrapper<>(new LgtBidTemplate().setBidingId(bidingId)));
        return LgtBidRequirementLineVendorDto.builder().lgtBidRequirementLines(lgtBidRequirementLines).lgtBidTemplates(bidTemplates).build();
    }

    @Override
    public LgtBidVendorDto getLgtBidVendor(Long bidingId) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtils.isEmpty(loginAppUser.getCompanyId()) || !UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            return null;
        }
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        List<LgtBidFile> lgtBidFiles = iLgtFileService.list(Wrappers.lambdaQuery(LgtBidFile.class).
                eq(LgtBidFile::getBidingId, bidingId).
                eq(LgtBidFile::getFileType, BidFileType.Supplier.getValue()));
        return LgtBidVendorDto.builder().biding(lgtBiding).fileList(lgtBidFiles).build();
    }

    @Override
    public void withdrawQuotedPrice(Long bidingId, Long vendorId,String withdrawReason) {
        /**
         * 1. 校验是否允许撤回报价
         */
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        Assert.isTrue(YesOrNo.YES.getValue().equals(lgtBiding.getWithdrawBiding()),"该单据不允许撤回报价!");
        Assert.isTrue(lgtBiding.getEnrollEndDatetime().compareTo(new Date()) > 0,"投标截止时间结束,不能撤回报报价");
        // 更新状态
        LgtVendorQuotedHead lgtVendorQuotedHead = iLgtVendorQuotedHeadService.getOne(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, bidingId).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).
                eq(LgtVendorQuotedHead::getVendorId, vendorId).last(" LIMIT 1"));
        lgtVendorQuotedHead.setStatus(BiddingOrderStates.WITHDRAW.getValue());
        lgtVendorQuotedHead.setWithdrawReason(withdrawReason);
        iLgtVendorQuotedHeadService.updateById(lgtVendorQuotedHead);
        // 更新头表字段
        String biddingSuppliers = lgtBiding.getBiddingSuppliers();
        String[] split = biddingSuppliers.split("/");
        biddingSuppliers = (Integer.parseInt(split[0]) - 1) + "/" +split[1];
        lgtBiding.setBiddingSuppliers(biddingSuppliers);
        iLgtBidingService.updateById(lgtBiding);
    }

    @Override
    public LgtBidInfoVO supplierDetails(Long bidingId) {
        return iLgtBidingService.supplierDetails(bidingId);
    }

    // 检查家畜价格表, 维护数据
    @Transactional
    public void checkbasePrice(List<LgtVendorQuotedLine> lgtVendorQuotedLines,LgtBiding lgtBiding){
        /**
         * 提交时自动检测供应商提交的报价明细匹配基础价格定义。
         * 根据业务模式+运输方式+行政区域+港口+leg+费用项+计费方式+计费单位匹配供应商的报价，
         * 有费用项则更新供应商的费用报价费率。没有费用项则自动增加相应的供应商报价费用。
         */
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
            lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                List<BasePrice> basePrices = basePriceService.list(Wrappers.lambdaQuery(BasePrice.class).
                        eq(BasePrice::getBusinessModeCode, lgtBiding.getBusinessModeCode()).
                        eq(BasePrice::getTransportModeCode, lgtBiding.getTransportModeCode()).
                        and(wrapper -> {
                            wrapper.eq(BasePrice::getRegionCode, lgtVendorQuotedLine.getFromCountryCode()).or().
                                    eq(BasePrice::getRegionCode, lgtVendorQuotedLine.getFromProvinceCode()).or().
                                    eq(BasePrice::getRegionCode, lgtVendorQuotedLine.getFromCityCode()).or().
                                    eq(BasePrice::getRegionCode, lgtVendorQuotedLine.getFromCountyCode()).or().
                                    eq(BasePrice::getRegionCode, lgtVendorQuotedLine.getToCountryCode()).or().
                                    eq(BasePrice::getRegionCode, lgtVendorQuotedLine.getToProvinceCode()).or().
                                    eq(BasePrice::getRegionCode, lgtVendorQuotedLine.getToCityCode()).or().
                                    eq(BasePrice::getRegionCode, lgtVendorQuotedLine.getToCountyCode());
                        }).
                        and(wrapper -> {
                            wrapper.eq(BasePrice::getPortCode, lgtVendorQuotedLine.getFromPortCode()).or().
                                    eq(BasePrice::getPortCode, lgtVendorQuotedLine.getToPortCode());
                        }).
                        eq(BasePrice::getLeg, lgtVendorQuotedLine.getLeg()).
                        eq(BasePrice::getExpenseItem, lgtVendorQuotedLine.getExpenseItem()).
                        eq(BasePrice::getChargeMethod, lgtVendorQuotedLine.getChargeMethod()).
                        eq(BasePrice::getChargeUnit, lgtVendorQuotedLine.getChargeUnit()).
                        eq(BasePrice::getStatus, LogisticsStatus.EFFECTIVE.getValue())
                );
                if(CollectionUtils.isNotEmpty(basePrices)){

                }
            });
        }

    }

    @Override
    @Transactional
    public void submitQuotedPrice(LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto) {
        // 检查基础价格
        // 校验参数
        checkSubmitQuotedPriceParam(lgtVendorQuotedHeadDto);
        // 保存基础信息
        quotedPriceSave(lgtVendorQuotedHeadDto);
        // 更新报价头表状态
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        LgtVendorQuotedHead lgtVendorQuotedHead = lgtVendorQuotedHeadDto.getLgtVendorQuotedHead();
        lgtVendorQuotedHead.setStatus(BiddingOrderStates.SUBMISSION.getValue())
                .setIfProxy(YesOrNo.NO.getValue())
                .setSubmitDate(new Date())
                .setSubmitUserId(loginAppUser.getUserId())
                .setSubmitUsername(loginAppUser.getUsername())
                .setSubmitNikeName(loginAppUser.getNickname());
        iLgtVendorQuotedHeadService.updateById(lgtVendorQuotedHead);

        // 更新头表
        Long bidingId = lgtVendorQuotedHeadDto.getLgtBiding().getBidingId();

        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        int count = iLgtVendorQuotedHeadService.count(Wrappers.lambdaQuery(LgtVendorQuotedHead.class)
                .eq(LgtVendorQuotedHead::getBidingId, bidingId)
                .eq(LgtVendorQuotedHead::getStatus, BiddingOrderStates.SUBMISSION.getValue())
                .eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound())
        );
        String biddingSuppliers = lgtBiding.getBiddingSuppliers();
        String[] split = biddingSuppliers.split("/");
        biddingSuppliers = count + "/" +split[1];
        lgtBiding.setBiddingSuppliers(biddingSuppliers);
        iLgtBidingService.update(null,Wrappers.lambdaUpdate(LgtBiding.class)
                .eq(LgtBiding::getBidingId,bidingId)
                .set(LgtBiding::getBiddingSuppliers,biddingSuppliers)
        );
    }

    public void checkSubmitQuotedPriceParam(LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto) {
        // 校验上传附件不能为空
        List<LgtFileConfig> lgtFileConfigs = lgtVendorQuotedHeadDto.getLgtFileConfigs();
        if(CollectionUtils.isNotEmpty(lgtFileConfigs)){
            lgtFileConfigs.forEach(lgtFileConfig -> {
                Assert.isTrue(!ObjectUtils.isEmpty(lgtFileConfig.getVendorDocId()),"附件信息不能为空!");
            });
        }
        Assert.notNull(lgtVendorQuotedHeadDto.getLgtBiding(),"参数缺少招标头信息!");

        String ifVendorSubmitShipDate = lgtVendorQuotedHeadDto.getLgtBiding().getIfVendorSubmitShipDate();
        if(YesOrNo.YES.getValue().equals(ifVendorSubmitShipDate)){
            Assert.isTrue(CollectionUtils.isNotEmpty(lgtVendorQuotedHeadDto.getLgtBidShipPeriods()),"船期明细不能为空!");
        }
        // 检查供应商报价行
        checkLgtVendorQuotedLines(lgtVendorQuotedHeadDto);
    }

    public void checkLgtVendorQuotedLines(LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto) {
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = lgtVendorQuotedHeadDto.getLgtVendorQuotedLines();
        Assert.notNull(lgtVendorQuotedLines,"投标信息不能为空!");
        Set<String> hashSet = new HashSet<>();
        lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
            LgtBidingServiceImpl.setStartEndAddress(lgtVendorQuotedLine);
            // 校验唯一 LEG +费项+计费方式+计费单位
            StringBuffer onlyKey = new StringBuffer();
            onlyKey.append(lgtVendorQuotedLine.getRowNum()).
                    append(lgtVendorQuotedLine.getLeg()).
                    append(lgtVendorQuotedLine.getExpenseItem()).
                    append(lgtVendorQuotedLine.getChargeMethod()).
                    append(lgtVendorQuotedLine.getChargeUnit());
            if(!hashSet.add(onlyKey.toString())){
                throw new BaseException(String.format("投标信息行:[%s+LEG +费项+计费方式+计费单位]存在重复!",lgtVendorQuotedLine.getStartAddress()+lgtVendorQuotedLine.getEndAddress()));
            }
        });
    }

    /**
     * 校验供应商必填和船期
     * @param lgtVendorQuotedHead
     */
    @Override
    public void checkLgtVendorQuotedLine(LgtVendorQuotedHeadDto lgtVendorQuotedHead){
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = lgtVendorQuotedHead.getLgtVendorQuotedLines();
        LgtBiding lgtBiding = lgtVendorQuotedHead.getLgtBiding();
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
            List<LgtBidTemplate> lgtBidTemplates = iLgtBidTemplateService.list(Wrappers.lambdaQuery(LgtBidTemplate.class).
                    eq(LgtBidTemplate::getBidingId, lgtBiding.getBidingId()).
                    eq(LgtBidTemplate::getVendorNotEmptyFlag, YesOrNo.YES.getValue()));
            if(CollectionUtils.isNotEmpty(lgtBidTemplates)){
                Class<LgtVendorQuotedLine> quotedLineClass = LgtVendorQuotedLine.class;
                Map<String, String> fieldMap = lgtBidTemplates.stream().collect(Collectors.toMap(lgtBidTemplate -> StringUtil.toCamelCase(lgtBidTemplate.getFieldCode()), LgtBidTemplate::getFieldName, (k1, k2) -> k1));
                lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                    fieldMap.keySet().forEach(field -> {
                        Object o = null;
                        boolean flag = true;
                        try {
                            Field declaredField = quotedLineClass.getDeclaredField(field);
                            declaredField.setAccessible(true);
                            o = declaredField.get(lgtVendorQuotedLine);
                        } catch (Exception e) {
                            flag = false;
                        }
                        if(flag){
                            Assert.isTrue(!ObjectUtils.isEmpty(o),String.format("投标信息:[%s]不能为空",fieldMap.get(field)));
                        }
                    });
                });
            }
        }

        /**
         * 校验船期, 船期路线路线必须与需求行匹配
         */
       /* List<LgtBidShipPeriod> lgtBidShipPeriods = lgtVendorQuotedHead.getLgtBidShipPeriods();
        if(CollectionUtils.isNotEmpty(lgtBidShipPeriods)){
            lgtBidShipPeriods.forEach(LgtBidingServiceImpl::setStartEndAddress);
            List<LgtBidRequirementLine> requirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).
                    eq(LgtBidRequirementLine::getBidingId, lgtBiding.getBidingId()));
            List<String> onlyKeys = requirementLines.stream().map(lgtBidRequirementLine -> lgtBidRequirementLine.getStartAddress() + lgtBidRequirementLine.getEndAddress()).collect(Collectors.toList());
            lgtBidShipPeriods.forEach(lgtBidShipPeriod -> {
                StringBuffer key = new StringBuffer().append(lgtBidShipPeriod.getStartAddress()).append(lgtBidShipPeriod.getEndAddress());
                boolean flag = onlyKeys.contains(key.toString());
                Assert.isTrue(flag,String.format("船期路线[%s]在需求行里不存在!",key));
            });
        }*/
    }

    @Override
    @Transactional
    public void quotedPriceSave(LgtVendorQuotedHeadDto lgtVendorQuotedHead) {
        checkLgtVendorQuotedLine(lgtVendorQuotedHead);
        // 判断状态,投标状态为“未投标”，项目状态为“接受报价中”的行，可进行该操作；
        Long bidingId = lgtVendorQuotedHead.getLgtBiding().getBidingId();
        Assert.notNull(bidingId,"缺少参数: bidingId");
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        LgtVendorQuotedHead vendorQuotedHead = lgtVendorQuotedHead.getLgtVendorQuotedHead();
        Assert.notNull(vendorQuotedHead,"报价头信息不能为空!");
        Assert.isTrue(BiddingProjectStatus.ACCEPT_BID.getValue().equals(lgtBiding.getBidingStatus()) &&
                        (BiddingOrderStates.DRAFT.getValue().equals(vendorQuotedHead.getStatus()) ||
                                BiddingOrderStates.WITHDRAW.getValue().equals(vendorQuotedHead.getStatus())),
                "只有项目状态为\"接受报价中\",投标状态为\"未投标\"的单据才能报价");

        // 保存报价行表
        saveLgtVendorQuotedLines(lgtVendorQuotedHead, bidingId, lgtBiding, vendorQuotedHead);

        // 保存船期
        saveLgtBidShipPeriods(lgtVendorQuotedHead, bidingId, lgtBiding, vendorQuotedHead);

        // 保存附件
        saveVendorFile(lgtVendorQuotedHead, bidingId, lgtBiding, vendorQuotedHead);
    }

    public void saveVendorFile(LgtVendorQuotedHeadDto lgtVendorQuotedHead, Long bidingId, LgtBiding lgtBiding, LgtVendorQuotedHead vendorQuotedHead) {
        List<LgtFileConfig> lgtFileConfigs = lgtVendorQuotedHead.getLgtFileConfigs();
        iLgtVendorFileService.remove(new QueryWrapper<>(new LgtVendorFile().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        if(CollectionUtils.isNotEmpty(lgtFileConfigs)){
            List<LgtVendorFile> lgtVendorFiles = new ArrayList<>();
            lgtFileConfigs.forEach(lgtFileConfig -> {
                if(!ObjectUtils.isEmpty(lgtFileConfig.getVendorDocId())){
                    LgtVendorFile lgtVendorFile = new LgtVendorFile()
                            .setVendorFileId(IdGenrator.generate())
                            .setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())
                            .setVendorId(vendorQuotedHead.getVendorId())
                            .setVendorCode(vendorQuotedHead.getVendorCode())
                            .setVendorName(vendorQuotedHead.getVendorName())
                            .setRound(lgtBiding.getCurrentRound())
                            .setRequireId(lgtFileConfig.getRequireId())
                            .setDocId(lgtFileConfig.getVendorDocId())
                            .setFileName(lgtFileConfig.getVendorFileName())
                            .setFileType(lgtFileConfig.getReferenceFileType())
                            .setBidingId(bidingId);
                    lgtVendorFiles.add(lgtVendorFile);
                }
            });
            iLgtVendorFileService.saveBatch(lgtVendorFiles);
        }
    }

    @Transactional
    public void saveLgtBidShipPeriods(LgtVendorQuotedHeadDto lgtVendorQuotedHead, Long bidingId, LgtBiding lgtBiding, LgtVendorQuotedHead vendorQuotedHead) {
        List<LgtBidRequirementLine> bidRequirementLines = iLgtBidRequirementLineService.list(Wrappers.lambdaQuery(LgtBidRequirementLine.class).eq(LgtBidRequirementLine::getBidingId, lgtBiding.getBidingId()));
        Map<String, LgtBidRequirementLine> requirementLineMap = bidRequirementLines.stream().collect(Collectors.toMap(lgtBidRequirementLine -> lgtBidRequirementLine.getStartAddress() + lgtBidRequirementLine.getEndAddress(), Function.identity(),(k1, k2)->k1));
        List<LgtBidShipPeriod> lgtBidShipPeriods = lgtVendorQuotedHead.getLgtBidShipPeriods();
        iLgtBidShipPeriodService.remove(new QueryWrapper<>(new LgtBidShipPeriod().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        if(CollectionUtils.isNotEmpty(lgtBidShipPeriods)){
            HashSet<String> hashSet = new HashSet<>();
            lgtBidShipPeriods.forEach(lgtBidShipPeriod -> {
                // 校验船期路线是否与需求行匹配
                LgtBidingServiceImpl.setStartEndAddress(lgtBidShipPeriod);
                // String key = String.valueOf(lgtBidShipPeriod.getStartAddress()) + lgtBidShipPeriod.getEndAddress();
                // Assert.isTrue(hashSet.add(key),String.format("船期路线: [%s],存在重复!",key));
                // LgtBidRequirementLine requirementLine = requirementLineMap.get(key);
                // Assert.notNull(requirementLine,String.format("船期路线: [%s],在需求明细不存在!",key));

                lgtBidShipPeriod
                        .setShipPeriodId(IdGenrator.generate())
                        .setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())
                        .setBidingId(bidingId)
                        .setVendorId(vendorQuotedHead.getVendorId())
                        .setVendorCode(vendorQuotedHead.getVendorCode())
                        .setVendorName(vendorQuotedHead.getVendorName())
                        //.setBidRequirementLineId(requirementLine.getBidRequirementLineId())
                        .setRound(lgtBiding.getCurrentRound());
            });
            iLgtBidShipPeriodService.saveBatch(lgtBidShipPeriods);
        }
    }

    public void saveLgtVendorQuotedLines(LgtVendorQuotedHeadDto lgtVendorQuotedHead, Long bidingId, LgtBiding lgtBiding, LgtVendorQuotedHead vendorQuotedHead) {
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = lgtVendorQuotedHead.getLgtVendorQuotedLines();
        iLgtVendorQuotedLineService.remove(new QueryWrapper<>(new LgtVendorQuotedLine().setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())));
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
            lgtVendorQuotedLines.forEach(lgtVendorQuotedLine -> {
                LgtBidingServiceImpl.setStartEndAddress(lgtVendorQuotedLine);
                lgtVendorQuotedLine.setQuotedLineId(IdGenrator.generate()).
                        setQuotedHeadId(vendorQuotedHead.getQuotedHeadId())
                        .setBidingId(bidingId)
                        .setRound(lgtBiding.getCurrentRound());
            });
            iLgtVendorQuotedLineService.saveBatch(lgtVendorQuotedLines);
            // 计算供应商报价行信息
            calculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines);

        }
    }

    public void calculationVendorQuotedLines(LgtBiding lgtBiding, List<LgtVendorQuotedLine> lgtVendorQuotedLines) {
        // 获取本位币种最新汇率信息  (源币种编码-汇率)
        Map<String, LatestGidailyRate> latestGidailyRateMap = iLgtBidingService.getLatestGidailyRateMap(lgtBiding);

        if (CompanyType.INSIDE.getValue().equals(lgtBiding.getBusinessModeCode())){
            // 国内业务 计算供应商报价行数据,并更新
            iLgtBidingService.insideCalculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines,latestGidailyRateMap);
        }else {
            // 非国内业务 计算供应商报价行数据,并更新
            iLgtBidingService.noInsideCalculationVendorQuotedLines(lgtBiding, lgtVendorQuotedLines,latestGidailyRateMap);
        }
    }

    @Override
    public PageInfo<LgtBidingDto> listPage(LgtBidingDto lgtBidingDto) {
        PageUtil.startPage(lgtBidingDto.getPageNum(), lgtBidingDto.getPageSize());

        //获取供应商ID
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser,"找不用用户基础信息");
        Assert.notNull(loginAppUser.getUserType(),"找不用用户类型信息");
        if (UserType.VENDOR.name().equals(loginAppUser.getUserType())) {
            if(Objects.isNull(loginAppUser.getCompanyId())){
                return new PageInfo<>(Collections.emptyList());
            }
            lgtBidingDto.setVendorId(loginAppUser.getCompanyId());
        }

        List<LgtBidingDto> lgtBidingDtos = lgtBidingMapper.queryLgtBidingDto(lgtBidingDto);
        if(CollectionUtils.isNotEmpty(lgtBidingDtos)){
            lgtBidingDtos.forEach(this::updateEnrollEndDatetime);
        }
        return new PageInfo<>(lgtBidingDtos);
    }

    /**
     * 根据头表截止时间更新状态
     * @param lgtBidingDtos
     */
    public void updateEnrollEndDatetime(LgtBidingDto lgtBidingDtos){
        if (BiddingProjectStatus.ACCEPT_BID.getValue().equals(lgtBidingDtos.getBidingStatus())) {
            Date enrollEndDatetime = lgtBidingDtos.getEnrollEndDatetime();
            if(!ObjectUtils.isEmpty(enrollEndDatetime) && enrollEndDatetime.compareTo(new Date()) < 0){
                /**
                 * 更新项目状态为投标已截止
                 */
                lgtBidingDtos.setBidingStatus(BiddingProjectStatus.TENDER_ENDING.getValue());
                LgtBiding lgtBiding = new LgtBiding().setBidingId(lgtBidingDtos.getBidingId()).setBidingStatus(BiddingProjectStatus.TENDER_ENDING.getValue());
                iLgtBidingService.updateById(lgtBiding);
            }
        }
    }

    @Override
    public LgtVendorQuotedHeadDto getLgtVendorQuotedHeadByQuotedHeadId(Long quotedHeadId) {
        LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto = new LgtVendorQuotedHeadDto();
        LgtVendorQuotedHead lgtVendorQuotedHead = iLgtVendorQuotedHeadService.getById(quotedHeadId);
        Long bidingId = lgtVendorQuotedHead.getBidingId();
        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        Assert.notNull(lgtBiding,"没有找到招标头信息!");
        if(!LgtBidingServiceImpl.canSeeResultSet.contains(lgtBiding.getBidingStatus())){
            throw new BaseException("需商务评标后才能看到报价信息");
        }
        // 设置报价行明细
        setQuotedLineInfo(bidingId, lgtVendorQuotedHeadDto, lgtBiding, lgtVendorQuotedHead);
        return lgtVendorQuotedHeadDto;
    }

    @Override
    public LgtVendorQuotedHeadDto getLgtVendorQuotedHead(Long bidingId, Long vendorId) {
        LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto = new LgtVendorQuotedHeadDto();

        LgtBiding lgtBiding = iLgtBidingService.getById(bidingId);
        Assert.notNull(lgtBiding,"没有找到招标头信息!");
        lgtVendorQuotedHeadDto.setLgtBiding(lgtBiding);
        // 报价头信息
        LgtVendorQuotedHead vendorQuotedHead = iLgtVendorQuotedHeadService.getOne(Wrappers.lambdaQuery(LgtVendorQuotedHead.class).
                eq(LgtVendorQuotedHead::getBidingId, bidingId).
                eq(LgtVendorQuotedHead::getVendorId,vendorId).
                eq(LgtVendorQuotedHead::getRound, lgtBiding.getCurrentRound()).last(" LIMIT 1"));
        lgtVendorQuotedHeadDto.setLgtVendorQuotedHead(vendorQuotedHead);

        // 设置报价行明细
        setQuotedLineInfo(bidingId, lgtVendorQuotedHeadDto, lgtBiding, vendorQuotedHead);

        return lgtVendorQuotedHeadDto;
    }

    public void setQuotedLineInfo(Long bidingId, LgtVendorQuotedHeadDto lgtVendorQuotedHeadDto, LgtBiding lgtBiding, LgtVendorQuotedHead lgtVendorQuotedHead ) {
        // 项目头信息
        lgtVendorQuotedHeadDto.setLgtBiding(lgtBiding);
        // 报价明细
        List<LgtVendorQuotedLine> lgtVendorQuotedLines = null;
        if (null != lgtVendorQuotedHead) {
            lgtVendorQuotedLines = iLgtVendorQuotedLineService.list(new QueryWrapper<>(new LgtVendorQuotedLine().
                    setQuotedHeadId(lgtVendorQuotedHead.getQuotedHeadId()).
                    setRound(lgtBiding.getCurrentRound())));
        }
        // 项目需求
        List<LgtBidRequirementLine> lgtBidRequirementLines = iLgtBidRequirementLineService.list(new QueryWrapper<>(new LgtBidRequirementLine().setBidingId(bidingId)));
        List<Integer> rowNums = lgtBidRequirementLines.stream().map(LgtBidRequirementLine::getRowNum).collect(Collectors.toList());
        rowNums.sort(Integer::compareTo);

        if(CollectionUtils.isEmpty(lgtVendorQuotedLines)){
            // 从明细表里复制一份
            if(CollectionUtils.isNotEmpty(lgtBidRequirementLines)){
                List<LgtVendorQuotedLine> finalLgtVendorQuotedLines = new ArrayList<>();
                lgtBidRequirementLines.forEach(lgtBidRequirementLine -> {
                    LgtVendorQuotedLine lgtVendorQuotedLine = new LgtVendorQuotedLine();
                    BeanCopyUtil.copyProperties(lgtVendorQuotedLine,lgtBidRequirementLine);
                    lgtVendorQuotedLine.setQuotedLineId(IdGenrator.generate());
                    lgtVendorQuotedLine.setBidingId(bidingId);
                    lgtVendorQuotedLine.setRound(lgtBiding.getCurrentRound());
                    lgtVendorQuotedLine.setPurchaseRemark(lgtBidRequirementLine.getComments());
                    if (null != lgtVendorQuotedHead) {
                        lgtVendorQuotedLine.setQuotedHeadId(lgtVendorQuotedHead.getQuotedHeadId());
                        lgtVendorQuotedLine.setVendorId(lgtVendorQuotedHead.getVendorId());
                        lgtVendorQuotedLine.setVendorCode(lgtVendorQuotedHead.getVendorCode());
                        lgtVendorQuotedLine.setVendorName(lgtVendorQuotedHead.getVendorName());
                    }
                    finalLgtVendorQuotedLines.add(lgtVendorQuotedLine);
                });
                lgtVendorQuotedLines = finalLgtVendorQuotedLines;

                // 保存报价行
                if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
                    lgtVendorQuotedLines.forEach(LgtBidingServiceImpl::setStartEndAddress);
                    iLgtVendorQuotedLineService.saveBatch(lgtVendorQuotedLines);
                }

            }
        }
        if(CollectionUtils.isNotEmpty(lgtVendorQuotedLines)){
            List<LgtVendorQuotedLine> quotedLines = new ArrayList<>();
            // 对需求明细排序
            Map<Integer, List<LgtVendorQuotedLine>> integerListMap = lgtVendorQuotedLines.stream().collect(Collectors.groupingBy(LgtVendorQuotedLine::getRowNum));
            rowNums.forEach(rowNum->{
                List<LgtVendorQuotedLine> lines = integerListMap.get(rowNum);
                if(CollectionUtils.isNotEmpty(lines)){
                    quotedLines.addAll(lines);
                }
            });
            lgtVendorQuotedHeadDto.setLgtVendorQuotedLines(quotedLines);
        }

        // 船期
        List<LgtBidShipPeriod> lgtBidShipPeriods = null;
        if (null != lgtVendorQuotedHead) {
            lgtBidShipPeriods = iLgtBidShipPeriodService.list(Wrappers.lambdaQuery(LgtBidShipPeriod.class).
                    eq(LgtBidShipPeriod::getQuotedHeadId, lgtVendorQuotedHead.getQuotedHeadId()).
                    eq(LgtBidShipPeriod::getRound,lgtBiding.getCurrentRound()));
            if(CollectionUtils.isEmpty(lgtBidShipPeriods)){
                // 从项目需求里面复制一份
                List<LgtBidShipPeriod> shipPeriods = new ArrayList<>();
                lgtBidRequirementLines.forEach(lgtBidRequirementLine -> {
                    LgtBidShipPeriod lgtBidShipPeriod = new LgtBidShipPeriod();
                    BeanCopyUtil.copyProperties(lgtBidShipPeriod,lgtBidRequirementLine);
                    lgtBidShipPeriod.setShipPeriodId(IdGenrator.generate());
                    lgtBidShipPeriod.setBidingId(bidingId);
                    lgtBidShipPeriod.setRound(lgtBiding.getCurrentRound());
                    if (null != lgtVendorQuotedHead) {
                        lgtBidShipPeriod.setQuotedHeadId(lgtVendorQuotedHead.getQuotedHeadId());
                        lgtBidShipPeriod.setVendorId(lgtVendorQuotedHead.getVendorId());
                        lgtBidShipPeriod.setVendorCode(lgtVendorQuotedHead.getVendorCode());
                        lgtBidShipPeriod.setVendorName(lgtVendorQuotedHead.getVendorName());
                    }
                    shipPeriods.add(lgtBidShipPeriod);
                });
                iLgtBidShipPeriodService.saveBatch(shipPeriods);
                lgtBidShipPeriods = shipPeriods;
            }
        }
        if(CollectionUtils.isNotEmpty(lgtBidShipPeriods)){
            // 船期排序
            List<LgtBidShipPeriod> quotedLines = new ArrayList<>();
            // 对需求明细排序
            Map<Integer, List<LgtBidShipPeriod>> integerListMap = lgtBidShipPeriods.stream().collect(Collectors.groupingBy(LgtBidShipPeriod::getRowNum));
            rowNums.forEach(rowNum->{
                List<LgtBidShipPeriod> lines = integerListMap.get(rowNum);
                if(CollectionUtils.isNotEmpty(lines)){
                    quotedLines.addAll(lines);
                }
            });
            lgtVendorQuotedHeadDto.setLgtBidShipPeriods(quotedLines);
        }

        // 附件明细
        List<LgtFileConfig> lgtFileConfigs = iLgtFileConfigService.list(Wrappers.lambdaQuery(LgtFileConfig.class).
                eq(LgtFileConfig::getBidingId, bidingId));
        if(CollectionUtils.isNotEmpty(lgtFileConfigs) && null != lgtVendorQuotedHead){
            lgtFileConfigs.forEach(lgtFileConfig -> {
                Long requireId = lgtFileConfig.getRequireId();
                LgtVendorFile lgtVendorFile = iLgtVendorFileService.getOne(Wrappers.lambdaQuery(LgtVendorFile.class).
                        eq(LgtVendorFile::getRequireId, requireId).
                        eq(LgtVendorFile::getQuotedHeadId,lgtVendorQuotedHead.getQuotedHeadId()).
                        eq(LgtVendorFile::getRound, lgtBiding.getCurrentRound()).
                        last(" LIMIT 1"));
                if (null != lgtVendorFile) {
                    lgtFileConfig.setVendorDocId(lgtVendorFile.getDocId());
                    lgtFileConfig.setVendorFileName(lgtVendorFile.getFileName());
                }
            });
        }
        lgtVendorQuotedHeadDto.setLgtFileConfigs(lgtFileConfigs);
    }
}

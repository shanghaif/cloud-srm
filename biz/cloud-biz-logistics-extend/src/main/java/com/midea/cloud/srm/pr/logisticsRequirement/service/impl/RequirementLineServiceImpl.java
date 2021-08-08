package com.midea.cloud.srm.pr.logisticsRequirement.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.logistics.expense.service.IPortService;
import com.midea.cloud.srm.logistics.expense.service.IRegionService;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateLineService;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.logistics.expense.entity.Port;
import com.midea.cloud.srm.model.logistics.expense.entity.Region;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsRequirementLineExcelDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementLine;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateLine;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementLineImport;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.pr.logisticsRequirement.mapper.LogisticsRequirementLineMapper;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  物流采购需求行表 服务实现类
 * </pre>
 *
 * @author chenwt24@meiCloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 10:59:47
 *  修改内容:
 * </pre>
 */
@Service(value = "LogisticsRequirementLineServiceImpl")
@Slf4j
public class RequirementLineServiceImpl extends ServiceImpl<LogisticsRequirementLineMapper, LogisticsRequirementLine> implements IRequirementLineService {


    @Autowired
    private ILogisticsTemplateLineService iLogisticsTemplateLineService;

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private IPortService portService;

    @Autowired
    private IRequirementLineService requirementLineService;

    @Autowired
    private IRegionService regionService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRequirementLineBatch(LogisticsRequirementHead requirementHead, List<LogisticsRequirementLine> requirementLineList) {
        log.info("批量新增物流采购申请行addRequirementLineBatch，参数：{},{}", requirementHead, requirementLineList);
        Objects.requireNonNull(requirementHead);
        if (CollectionUtils.isEmpty(requirementLineList)) return;

        int rowNum = 0;
        Iterator<LogisticsRequirementLine> iterator = requirementLineList.iterator();
        while (iterator.hasNext()) {
            long id = IdGenrator.generate();
            LogisticsRequirementLine line = iterator.next();
            line.setRequirementLineId(id)
                    .setRequirementHeadId(requirementHead.getRequirementHeadId())
                    .setRequirementHeadNum(requirementHead.getRequirementHeadNum())
                    .setRowNum(++rowNum);
        }

        this.saveBatch(requirementLineList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(LogisticsRequirementHead requirementHead, List<LogisticsRequirementLine> requirementLineList) {
        LogisticsRequirementLine requirementLine = new LogisticsRequirementLine();
        requirementLine.setRequirementHeadId(requirementHead.getRequirementHeadId());
        QueryWrapper<LogisticsRequirementLine> queryWrapper = new QueryWrapper<>(requirementLine);
        List<LogisticsRequirementLine> oldLineList = this.list(queryWrapper);
        List<Long> oldLineIdList = oldLineList.stream().map(LogisticsRequirementLine::getRequirementLineId).collect(Collectors.toList());
        List<Long> newLineIdList = new ArrayList<>();
        for (int i = 0; i < requirementLineList.size(); i++) {
            LogisticsRequirementLine line = requirementLineList.get(i);
            Long requirementLineId = line.getRequirementLineId();
            line.setRowNum(i + 1);
            //设置剩余可下单数量等于采购需求数量
//            line.setOrderQuantity(line.getRequirementQuantity());

            // 新增
            if (requirementLineId == null) {
                line.setRequirementLineId(IdGenrator.generate()).setRequirementHeadId(requirementHead.getRequirementHeadId()).setRequirementHeadNum(requirementHead.getRequirementHeadNum());

                this.save(line);
            } else {
                newLineIdList.add(requirementLineId);
                // 更新
                this.updateById(line);
            }
        }
        // 删除
        for (Long oldId : oldLineIdList) {
            if (!newLineIdList.contains(oldId)) {
                this.removeById(oldId);
            }
        }
    }

    @Override
    public void importRequirementLineModelDownload(HttpServletResponse response,Long templateHeadId) throws Exception {
        String fileName = "logistics_template_line";
        List<RequirementLineImport> requirementLineImports  = new ArrayList<>();

        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        List<Integer> column = Arrays.asList(0,1);
        HashMap<Integer,String> titleConfig = new HashMap();
//        titleConfig.put(0 , "必填，需与ERP物料编码保持一致");
//        titleConfig.put(1 , "必填，维护本物料行对应的申请数量");
//        titleConfig.put(2 , "非必填，服务类时必填");
//        titleConfig.put(3 , "必填，日期格式必须为YYYY-MM-DD或YYYY/MM/DD");
//        titleConfig.put(4 , "非必填，若为指定采购时可维护对应供应商编码");
//        titleConfig.put(5 , "非必填，若为指定采购时可维护对应供应商名称");
//        titleConfig.put(6 , "非必填");
//        titleConfig.put(7 , "非必填");
        //泛型有问题
        HashMap<Integer, String[]> dropDownMap = new HashMap<>();
        // 指定下拉框
//        String requestManCode ="DMAND_LINE_REQUEST";	//需求人字典编码

        // 测试多sheel导出
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
//		WriteSheet test1 = EasyExcel.writerSheet(0, fileName).head(RequirementLineImport.class).build();

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);


        List<LogisticsTemplateLine> lines = iLogisticsTemplateLineService.list(Wrappers.lambdaQuery(LogisticsTemplateLine.class)
                .eq(LogisticsTemplateLine::getHeadId, templateHeadId));

        List<LogisticsTemplateLine> notEmptyList = lines.parallelStream().filter(x -> Objects.equals(x.getApplyNotEmptyFlag(), "Y")).collect(Collectors.toList());


        List<List<String>> requestManData = new ArrayList<>();
        for(int i = 0 ; i< notEmptyList.size() ; i++){
            List<String> temp = new ArrayList<>();
            temp.add(notEmptyList.get(i).getFieldName());
            requestManData.add(temp);
        }
        WriteSheet test2 = EasyExcel.writerSheet(1, "logistics_template_line").head(requestManData).
                registerWriteHandler(horizontalCellStyleStrategy).build();


        excelWriter.write(requirementLineImports, test2);
        excelWriter.finish();

//		EasyExcelUtil.writeExcelWithModel(outputStream, requirementLineImports, RequirementLineImport.class, fileName ,titleHandler);
    }


    /**
     * 物流采购申请-路线-导入文件模板下载
     * @param response
     */
    @Override
    public void importModelDownload2(HttpServletResponse response) throws IOException {
        //构建表格
        Workbook workbook = createImportModelWorkbook(getTitles());
        //获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"路线导入模板");
        //导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private List<String> getTitles(){
        List<String> modelExcelTitles = new LinkedList<>();
        modelExcelTitles.add("起运地");
        modelExcelTitles.add("始发省");
        modelExcelTitles.add("始发市");
        modelExcelTitles.add("始发区县");
        modelExcelTitles.add("目的地");
        modelExcelTitles.add("目的省");
        modelExcelTitles.add("目的市");
        modelExcelTitles.add("目的区县");
        modelExcelTitles.add("是否往返");
        modelExcelTitles.add("最小收费");
        modelExcelTitles.add("最大收费");
        modelExcelTitles.add("币制");
        modelExcelTitles.add("运输距离");
        modelExcelTitles.add("备注");
        modelExcelTitles.add("时效");
        modelExcelTitles.add("起运港中文名称");
        modelExcelTitles.add("目的港中文名称");
        modelExcelTitles.add("提前预定时间");
        modelExcelTitles.add("全程时效");
        modelExcelTitles.add("船期");
        modelExcelTitles.add("免费存放期");
        modelExcelTitles.add("费项");
        modelExcelTitles.add("计费方式");
        modelExcelTitles.add("计费单位");
        modelExcelTitles.add("费用");
        modelExcelTitles.add("指定供应商");
        modelExcelTitles.add("贸易术语");
        modelExcelTitles.add("整柜/拼柜");
        modelExcelTitles.add("船期频率");
        modelExcelTitles.add("免堆期");
        modelExcelTitles.add("免箱期");
        modelExcelTitles.add("超期堆存费");
        modelExcelTitles.add("超期用箱费");
        modelExcelTitles.add("LEG");
        modelExcelTitles.add("进出口方式");
        modelExcelTitles.add("单拖成本");
        modelExcelTitles.add("单公里成本");
        modelExcelTitles.add("起运国");
        modelExcelTitles.add("目的国");
        modelExcelTitles.add("物流品类名称");
        modelExcelTitles.add("总金额");
        modelExcelTitles.add("备用字段1");
        modelExcelTitles.add("备用字段2");
        modelExcelTitles.add("备用字段3");
        modelExcelTitles.add("备用字段4");
        modelExcelTitles.add("备用字段5");
        modelExcelTitles.add("备用字段6");
        modelExcelTitles.add("备用字段7");
        modelExcelTitles.add("备用字段8");
        modelExcelTitles.add("备用字段9");
        modelExcelTitles.add("备用字段10");
        return modelExcelTitles;
    }

    /**
     * 物流采购申请-导出文件下载
     * @param response
     * @param id
     */
    @Override
    public void export(HttpServletResponse response, Long id) throws IOException {
        List<LogisticsRequirementLine> requirementLineList = requirementLineService.list(new QueryWrapper<LogisticsRequirementLine>(new LogisticsRequirementLine().setRequirementHeadId(id)));

        String fileName = "物流采购申请路线";
        List<LogisticsRequirementLineExcelDTO> logisticsRequirementLineExportDTOList = new LinkedList<>();
        for(LogisticsRequirementLine requirementLine : requirementLineList){
            LogisticsRequirementLineExcelDTO requirementLineExportDTO = new LogisticsRequirementLineExcelDTO();
            BeanUtils.copyProperties(requirementLine,requirementLineExportDTO);
            logisticsRequirementLineExportDTOList.add(requirementLineExportDTO);
        }
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,logisticsRequirementLineExportDTOList, LogisticsRequirementLineExcelDTO.class);
    }

    /**
     * 导入逻辑：
     * 获取物流品类，省，市，县，港口，LEG，费项，计费方式，计费单位，币制，供应商基础数据。
     * 根据名称匹配数据，如果匹配到一条数据，则设置编码，反之则置空。
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public List<LogisticsRequirementLine> importExcel(MultipartFile file) throws IOException {
        //校验文件格式
        String originalFilename = file.getOriginalFilename();
        if (!EasyExcelUtil.isExcel(originalFilename)) {
            throw new BaseException("请导入正确的Excel文件");
        }
        //将文件数据处理至集合
        InputStream inputStream = file.getInputStream();
        List<LogisticsRequirementLineExcelDTO> excelDTOList = EasyExcelUtil.readExcelWithModel(LogisticsRequirementLineExcelDTO.class, inputStream);
        //获取外部模块的数据
        //获取物流品类数据
        Map<String, List<PurchaseCategory>> logisticsCategoryMap = getLogisticsCategoryMap(excelDTOList);
        //获取国数据
        Map<String, Region> countryMap = getCountryMap(excelDTOList);
        //获取省数据
        Map<String, Region> provinceMap = getProvinceMap(excelDTOList);
        //获取市数据
        Map<String, Region> cityMap = getCityMap(excelDTOList);
        //获取县的数据
        Map<String, Region> countyMap = getCountyMap(excelDTOList);
        //获取港口数据(编码-实体)
        Map<String, Port> portMap = getPortMap(excelDTOList);
        //获取LEG数据
//        Map<String, List<DictItem>> legMap = getLegMap(excelDTOList);
        //获取费用项数据
//        Map<String, List<DictItem>> expenseItemMap = getExpenseItemMap(excelDTOList);
        //获取计费方式数据
//        Map<String, List<DictItem>> chargeLevelMap = getChargeLevelMap(excelDTOList);
        //获取计费单位数据
//        Map<String, List<DictItem>> subLevelMap = getSubLevelMap(excelDTOList);
        // 获取币种数据
//        Map<String, List<PurchaseCurrency>> currencyMap = getCurrencyMap(excelDTOList);
        //获取供应商数据
        Map<String, List<CompanyInfo>> companyInfoMap = getCompanyInfoMap(excelDTOList);
        // 币种
        Map<String, String> currencyNameCode = EasyExcelUtil.getCurrencyNameCode(baseClient);

        List<LogisticsRequirementLine> result = new LinkedList<>();
        for(LogisticsRequirementLineExcelDTO excelDTO : excelDTOList){
            LogisticsRequirementLine requirementLine = new LogisticsRequirementLine();
            BeanUtils.copyProperties(excelDTO,requirementLine);

            //设置品类编码
            List<PurchaseCategory> purchaseCategoryList = logisticsCategoryMap.get(requirementLine.getLogisticsCategoryName());
            if(CollectionUtils.isNotEmpty(purchaseCategoryList) && purchaseCategoryList.size() == 1){
                requirementLine.setLogisticsCategoryId(purchaseCategoryList.get(0).getCategoryId());
                requirementLine.setLogisticsCategoryCode(purchaseCategoryList.get(0).getCategoryCode());
            }
            //设置始发国
            Region fromCountry = countryMap.get(requirementLine.getFromCountry());
            if(Objects.nonNull(fromCountry)){
                requirementLine.setFromCountryCode(fromCountry.getRegionCode());
            }
            //设置目的国
            Region toCountry = countryMap.get(requirementLine.getToCountry());
            if(Objects.nonNull(toCountry)){
                requirementLine.setToCountyCode(toCountry.getRegionCode());
            }
            //设置始发省
            Region fromProvince = provinceMap.get(requirementLine.getFromProvince());
            if(Objects.nonNull(fromProvince)){
                requirementLine.setFromProvinceCode(fromProvince.getRegionCode());
            }
            //设置目的省
            Region toProvince = provinceMap.get(requirementLine.getToProvince());
            if(Objects.nonNull(toProvince)){
                requirementLine.setToProvinceCode(toProvince.getRegionCode());
            }
            //设置始发市
            Region fromCity = cityMap.get(requirementLine.getFromCity());
            if(Objects.nonNull(fromCity)){
                requirementLine.setFromCityCode(fromCity.getRegionCode());
            }
            //设置目的市
            Region toCity = cityMap.get(requirementLine.getToCity());
            if(Objects.nonNull(toCity)){
                requirementLine.setToCityCode(toCity.getRegionCode());
            }
            //设置始发县
            String fromCountyKey = new StringBuffer()
                    .append(requirementLine.getFromCity())
                    .append(requirementLine.getFromCountry())
                    .toString();
            Region fromCounty = countyMap.get(fromCountyKey);
            if(Objects.nonNull(fromCounty)){
                requirementLine.setFromCountryCode(fromCounty.getRegionCode());
            }
            //设置目的县
            String toCountyKey = new StringBuffer()
                    .append(requirementLine.getToCity())
                    .append(requirementLine.getToCountyCode())
                    .toString();
            Region toCounty = countyMap.get(toCountyKey);
            if(Objects.nonNull(toCounty)){
                requirementLine.setToCountyCode(toCounty.getRegionCode());
            }
            //设置起始港口
            Port port1 = portMap.get(requirementLine.getFromPort());
            if(null != port1){
                requirementLine.setFromPortCode(port1.getPortCode());
                requirementLine.setFromPort(port1.getPortNameZhs());
            }
            //设置目的港口
            Port port2 = portMap.get(requirementLine.getToPort());
            if(null != port2){
                requirementLine.setToPortCode(port2.getPortCode());
                requirementLine.setToPort(port2.getPortNameZhs());
            }
            //设置供应商
            List<CompanyInfo> companyInfoList = companyInfoMap.get(requirementLine.getSpecifiedVendor());
            if(CollectionUtils.isNotEmpty(companyInfoList) && companyInfoList.size() == 1){
                requirementLine.setSpecifiedVendorCode(companyInfoList.get(0).getCompanyCode());
            }

            // 设置币种
            String currency = excelDTO.getCurrency();
            if(!ObjectUtils.isEmpty(currency)){
                currency = currencyNameCode.get(currency.trim());
                requirementLine.setCurrency(currency);
            }

            result.add(requirementLine);
        }
        return result;
    }

    /**
     * 获取物流品类数据
     * @param excelDTOList
     * @return
     */
    public Map<String,List<PurchaseCategory>> getLogisticsCategoryMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //获取参数
        List<String> categoryNameList = excelDTOList.stream().map(item -> item.getLogisticsCategoryName()).collect(Collectors.toList());
        //查询数据
        List<PurchaseCategory> logisticsCategoryList = baseClient.listPurchaseCategoryByNameBatch(categoryNameList);
        //返回分组结果
        return logisticsCategoryList.stream().collect(Collectors.groupingBy(item -> item.getCategoryName()));
    }

    /**
     * 获取国家数据
     * @param excelDTOList
     * @return
     */
    public Map<String,Region> getCountryMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //获取参数
        List<String> countryNameList = new LinkedList<String>(){{
            addAll(excelDTOList.stream().map(item -> item.getFromCountry()).collect(Collectors.toList()));
            addAll(excelDTOList.stream().map(item -> item.getToCountry()).collect(Collectors.toList()));
        }};
        //查询数据
        if(CollectionUtils.isEmpty(countryNameList)){
            return Collections.EMPTY_MAP;
        }
        QueryWrapper<Region> regionWrapper = new QueryWrapper<>();
        regionWrapper.in("REGION_NAME",countryNameList);
        List<Region> countryList = regionService.list(regionWrapper);
        //返回结果
        return countryList.stream().collect(Collectors.toMap(item -> item.getRegionName(), item -> item));
    }

    /**
     * 获取省数据
     * @param excelDTOList
     * @return
     */
    public Map<String,Region> getProvinceMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //获取参数
        List<String> provinceNameList = new LinkedList<String>(){{
            addAll(excelDTOList.stream().map(item -> item.getFromProvince()).collect(Collectors.toList()));
            addAll(excelDTOList.stream().map(item -> item.getToProvince()).collect(Collectors.toList()));
        }};
        //查询数据
        if(CollectionUtils.isEmpty(provinceNameList)){
            return Collections.EMPTY_MAP;
        }
        QueryWrapper<Region> regionWrapper = new QueryWrapper<>();
        regionWrapper.in("REGION_NAME",provinceNameList);
        List<Region> provinceList = regionService.list(regionWrapper);
        //返回结果
        return provinceList.stream().collect(Collectors.toMap(item -> item.getRegionName(),item -> item));
    }

    /**
     * 获取市数据
     * @param excelDTOList
     * @return
     */
    public Map<String, Region> getCityMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //获取参数
        List<String> cityNameList = new LinkedList<String>(){{
            addAll(excelDTOList.stream().map(item -> item.getFromCity()).collect(Collectors.toList()));
            addAll(excelDTOList.stream().map(item -> item.getToCity()).collect(Collectors.toList()));
        }};
        //查询数据
        if(CollectionUtils.isEmpty(cityNameList)){
            return Collections.EMPTY_MAP;
        }

        QueryWrapper<Region> regionWrapper = new QueryWrapper<>();
        regionWrapper.in("REGION_NAME",cityNameList);
        List<Region> cityList = regionService.list(regionWrapper);

        //返回结果
        return cityList.stream().collect(Collectors.toMap(item -> item.getRegionName(),item -> item));
    }

    /**
     * 获取县数据
     * @param excelDTOList
     * @return
     */
    private Map<String, Region> getCountyMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //获取参数
        List<String> countyNameList = new LinkedList<String>(){{
            addAll(excelDTOList.stream().map(item -> item.getFromCounty()).collect(Collectors.toList()));
            addAll(excelDTOList.stream().map(item -> item.getToCounty()).collect(Collectors.toList()));
        }};
        //查询数据
        if(CollectionUtils.isEmpty(countyNameList)){
            return Collections.EMPTY_MAP;
        }
        QueryWrapper<Region> regionWrapper = new QueryWrapper<>();
        regionWrapper.in("REGION_NAME",countyNameList);
        List<Region> countyList = regionService.list(regionWrapper);

        //返回结果
        return countyList.stream().collect(Collectors.toMap(item -> new StringBuffer()
                        .append(item.getParentRegionName())
                        .append(item.getRegionName())
                        .toString(),item -> item));
    }

    /**
     * 获取港口数据
     * @param excelDTOList
     * @return
     */
    public Map<String, Port> getPortMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        Map<String, Port> map = new HashMap<>();
        //获取参数
        List<String> portCodeList = new LinkedList<String>(){{
            addAll(excelDTOList.stream().map(item -> item.getFromPort()).collect(Collectors.toList()));
            addAll(excelDTOList.stream().map(item -> item.getToPort()).collect(Collectors.toList()));
        }};
        //查询数据
        List<Port> portList = portService.list(Wrappers.lambdaQuery(Port.class).in(Port::getPortCode,portCodeList));
        //返回结果
        if (CollectionUtils.isNotEmpty(portList)) {
            map = portList.stream().collect(Collectors.toMap(Port::getPortCode, Function.identity()));
        }
        return map;
    }

    /**
     * 获取LEG数据
     * @param excelDTOList
     * @return
     */
    private Map<String, List<DictItem>> getLegMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //查询数据
        List<DictItem> legList = baseClient.listDictItemByDictCode("LEG");
        //返回结果
        return legList.stream().collect(Collectors.groupingBy(item -> item.getDictItemName()));
    }

    /**
     * 获取费用项数据
     * @param excelDTOList
     * @return
     */
    private Map<String, List<DictItem>> getExpenseItemMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //查询数据
        List<DictItem> dictItemList = baseClient.listDictItemByDictCode("CHARGE_NAME");
        //返回结果
        return dictItemList.stream().collect(Collectors.groupingBy(item -> item.getDictItemName()));
    }

    /**
     * 获取计费方式数据
     * @param excelDTOList
     * @return
     */
    private Map<String, List<DictItem>> getChargeLevelMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //查询数据
        List<DictItem> dictItemList = baseClient.listDictItemByDictCode("CHARGE_LEVEL");
        //返回结果
        return dictItemList.stream().collect(Collectors.groupingBy(item -> item.getDictItemName()));
    }

    /**
     * 获取计费单位数据
     * @param excelDTOList
     * @return
     */
    private Map<String, List<DictItem>> getSubLevelMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //查询数据
        List<DictItem> dictItemList = baseClient.listDictItemByDictCode("SUB_LEVEL");
        //返回结果
        return dictItemList.stream().collect(Collectors.groupingBy(item -> item.getDictItemName()));
    }

    /**
     * 获取币种数据
     * @param excelDTOList
     * @return
     */
    private Map<String, List<PurchaseCurrency>> getCurrencyMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //查询数据
        List<PurchaseCurrency> purchaseCurrencyList = baseClient.listAllPurchaseCurrency();
        //返回结果
        return purchaseCurrencyList.stream().collect(Collectors.groupingBy(item -> item.getCurrencyName()));
    }

    /**
     * 获取供应商数据
     * @param excelDTOList
     * @return
     */
    private Map<String,List<CompanyInfo>> getCompanyInfoMap(List<LogisticsRequirementLineExcelDTO> excelDTOList){
        //查询数据
        List<String> companyInfoNameList = excelDTOList.stream().map(item -> item.getSpecifiedVendor()).collect(Collectors.toList());
        //查询数据
        List<CompanyInfo> companyInfoList = supplierClient.listVendorByNameBatch(companyInfoNameList);
        //返回结果
        return companyInfoList.stream().collect(Collectors.groupingBy(item -> item.getCompanyName()));
    }

    /**
     * 构建导入模板
     * @param titleList
     * @return
     */
    private Workbook createImportModelWorkbook(List<String> titleList){
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表:自定义导入
        XSSFSheet sheet = workbook.createSheet("sheet");
        // 创建单元格样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        // 创建标题行
        XSSFRow row = sheet.createRow(0);

        // 当前单元格下标
        int cellIndex = 0;

        //设置标题
        for(String title : titleList){
            XSSFCell cell = row.createCell(cellIndex);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyle);
            cellIndex ++;
        }
        return workbook;
    }


}

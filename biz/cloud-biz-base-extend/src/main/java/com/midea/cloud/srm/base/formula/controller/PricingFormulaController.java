package com.midea.cloud.srm.base.formula.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.controller.BaseCheckController;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.base.FormulaPriceExportTypeEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.srm.base.formula.param.BasePriceParam;
import com.midea.cloud.srm.base.formula.service.CalculateInfoHolder;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorService;
import com.midea.cloud.srm.base.formula.service.IPricingFormulaCalculateService;
import com.midea.cloud.srm.base.formula.service.IPricingFormulaHeaderService;
import com.midea.cloud.srm.base.formula.service.impl.EssentialFactorServiceImpl;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.formula.dto.FormulaPriceDTO;
import com.midea.cloud.srm.model.base.formula.dto.calculate.BaseMaterialPriceDTO;
import com.midea.cloud.srm.model.base.formula.dto.calculate.CalculatePriceForOrderResult;
import com.midea.cloud.srm.model.base.formula.dto.calculate.SeaFoodFormulaCalculateParam;
import com.midea.cloud.srm.model.base.formula.dto.create.MaterialFormulaRelateCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.create.PricingFormulaCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.MaterialFormulaRelateQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.query.PricingFormulaQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.PricingFormulaUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.EssentialFactor;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaHeader;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaLine;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.*;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import jxl.write.Formula;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileUpload;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <pre>
 *  计价公式头表 前端控制器
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:27:13
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bid/pricing-formula")
@Slf4j
public class PricingFormulaController extends BaseCheckController {

    private final IPricingFormulaHeaderService iPricingFormulaHeaderService;

    private final IPricingFormulaCalculateService calculateService;

    @Autowired
    private IEssentialFactorService essentialFactorService;

    @Autowired
    private IOrganizationService organizationService;


    public PricingFormulaController(IPricingFormulaHeaderService iPricingFormulaHeaderService, IPricingFormulaCalculateService calculateService) {
        this.iPricingFormulaHeaderService = iPricingFormulaHeaderService;
        this.calculateService = calculateService;
    }

    @PostMapping("/createPricingFormula")
    public PricingFormulaDetailVO create(@Valid @RequestBody PricingFormulaCreateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return iPricingFormulaHeaderService.createPricingFormulate(dto);
    }

    @PostMapping("/savePricingFormulaTemporary")
    public PricingFormulaDetailVO saveTemporary(@Valid @RequestBody PricingFormulaUpdateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        dto.setPricingFormulaStatus(StuffStatus.DRAFT.getStatus());
        return iPricingFormulaHeaderService.updateFormulateDetailById(dto);
    }

    @PostMapping("/activePricingFormula")
    public PricingFormulaDetailVO active(@Valid @RequestBody PricingFormulaUpdateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        dto.setPricingFormulaStatus(StuffStatus.ACTIVE.getStatus());
        return iPricingFormulaHeaderService.updateFormulateDetailById(dto);
    }

    @GetMapping("/getPricingFormulaById")
    public PricingFormulaDetailVO getById(@RequestParam Long headerId) {
        return iPricingFormulaHeaderService.getPriceFormulaDetailById(headerId);
    }

    @GetMapping("/getPricingFormulaByIds")
    public List<PricingFormulaDetailVO> getByIds(@RequestParam Long[] ids) {
        List<PricingFormulaDetailVO> list = new ArrayList<>();
        for (Long id : ids) {
            list.add(iPricingFormulaHeaderService.getPriceFormulaDetailById(id));
        }
        return list;
    }


    @GetMapping("/deletePricingFormulaById")
    public Boolean deleteById(@RequestParam Long headerId) {
        return iPricingFormulaHeaderService.deleteByHeaderId(headerId);
    }


    @GetMapping("/inActivePricingFormula")
    public Boolean inActive(@RequestParam Long id) {
        return iPricingFormulaHeaderService.updateStatus(StuffStatus.INVAILD, id);
    }

    /**
     * 根据公式id返回要素
     *
     * @param formulaId
     * @return
     */
    @GetMapping("/getFactorByFormulaId")
    public List<EssentialFactorVO> getEssentialFactorByFormulaId(@RequestParam(required = false) Long formulaId) {
        return iPricingFormulaHeaderService.getEssentialFactorByFormulaId(formulaId);
    }

    /**
     * 物料和公式关联
     */
    @PostMapping("/combineFormulaAndMaterial")
    public Boolean combineFormulaAndMaterial(@Valid @RequestBody MaterialFormulaRelateCreateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return iPricingFormulaHeaderService.combineMaterialAndFormula(dto);
    }

    /**
     * 删除关联行
     */
    @PostMapping("/divideFormulaAndMaterial")
    public Boolean divideFormulaAndMaterial(@Valid @RequestBody MaterialFormulaRelateCreateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return iPricingFormulaHeaderService.divideMaterialAndFormula(dto);
    }

    /**
     * 根据物料id返回公式头
     *
     * @param materialId
     * @return
     */
    @GetMapping("/getPricingFormulaHeaderByMaterialId")
    public List<PricingFormulaHeaderVO> getPricingFormulaHeaderByMaterialId(@RequestParam Long materialId) {
        return iPricingFormulaHeaderService.getPricingFormulaHeaderByMaterialId(materialId);
    }

    @GetMapping("/getPricingFormulaHeaderByMaterialIdAndValue")
    public List<PricingFormulaHeaderVO> checkIfExistsMaterialAndFormula(@RequestParam Long materialId, @RequestParam String formulaValue) {
        return iPricingFormulaHeaderService.getPricingFormulaHeaderByMaterialIdAndValue(materialId, formulaValue);

    }

    @PostMapping("/listPricingFormulaHeaderByPage")
    public PageInfo<PricingFormulaHeader> listPricingFormulaHeaderByPage(@RequestBody PricingFormulaQueryDto dto) {
        return iPricingFormulaHeaderService.listPricingFormulaHeaderByPage(dto);
    }

    @GetMapping("/listAllFormulaHeader")
    public List<PricingFormulaHeader> listAllFormulaHeader() {
        return iPricingFormulaHeaderService.listAllFormulaHeader();
    }

    @PostMapping("/listMaterialFormulaRelateByPage")
    public PageInfo<MaterialFormulaRelateVO> listMaterialFormulaRelateByPage(@RequestBody MaterialFormulaRelateQueryDto dto) {
        return iPricingFormulaHeaderService.listMaterialFormulaRelateByPage(dto);
    }

    /**
     * 下载计价公式头表导入模板
     *
     * @return
     */
    @PostMapping("/importLineModelDownload")
    public void importLineModelDownload(@RequestBody List<MaterialItemAttribute> materialAttributeList, HttpServletResponse response) throws Exception {
        iPricingFormulaHeaderService.importLineModelDownload(materialAttributeList, response);
    }

    /**
     * 导入文件
     *
     * @param file
     */
    @PostMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iPricingFormulaHeaderService.importExcel(file, fileupload);
    }

    /**
     * 根据公式id查找基价
     */
    @PostMapping("/getBasePriceByFormulaInfo")
    public List<BaseMaterialPriceTable> getBasePriceByFormulaInfo(@RequestBody BasePriceParam priceParam
    ) {
        return iPricingFormulaHeaderService.getBasePriceByFormulaInfo(priceParam);
    }

    /**
     * 根据公式id返回可编辑基价
     */
    @GetMapping("/getBaseMaterialPriceByFormulaId")
    public List<BaseMaterialPriceDTO> getBaseMaterialPriceByFormulaId(@RequestParam Long formulaId) {
        if (Objects.isNull(formulaId)) {
            return Collections.emptyList();
        }
        return iPricingFormulaHeaderService.getBaseMaterialPriceByFormulaId(formulaId);
    }

    /**
     * 根据公式id返回基价列表（订单使用）
     *
     * @param formulaId
     * @return
     */
    @GetMapping("/getBaseMaterialPriceByFormulaIdForOrder")
    public List<BaseMaterialPriceTable> getBaseMaterialPriceByFormulaIdForOrder(@RequestParam Long formulaId) {
        if (Objects.isNull(formulaId)) {
            return Collections.emptyList();
        }
        return iPricingFormulaHeaderService.getPriceTablesByFormulaId(formulaId);
    }

    /**
     * 根据公式id、供应商报价、基价保存结果计算公式（订单使用）
     */
    @PostMapping("/calculatePriceForOrder")
    public CalculatePriceForOrderResult calculatePriceForOrder(@RequestBody SeaFoodFormulaCalculateParam param) {
        PricingFormulaDetailVO pricingFormulaDetail = iPricingFormulaHeaderService.getPriceFormulaDetailById(param.getFormulaId());
        PricingFormulaHeader pricingFormulaHeader = pricingFormulaDetail.toPricingFormulaHeader();
        List<PricingFormulaLine> pricingFormulaLines = pricingFormulaDetail.toPricingFormulaLines();
        MaterialItem item = new MaterialItem().setMaterialId(param.getMaterialId());
        PricingFormulaCalculateParameter calculateParameter = PricingFormulaCalculateParameter.from(
                null, item,
                null,
                pricingFormulaHeader, pricingFormulaLines, null, YesOrNo.YES.getValue(), param.getPriceJSON(), param.getEssentialFactorValues());
        CalculatePriceForOrderResult orderResult = new CalculatePriceForOrderResult();
        PricingFormulaCalculateResult calculate = calculateService.calculate(calculateParameter);
        orderResult.calcResult = calculate.getValue();
        orderResult.formulaAndParam = calculate.getFormulaParam();
        return orderResult;
    }

    @PostMapping("/calculatePriceForOrderBatch")
    public Map<String, CalculatePriceForOrderResult> calculatePriceForOrderBatch(@RequestBody List<SeaFoodFormulaCalculateParam> params) {
        return params.stream().collect(Collectors.toMap(SeaFoodFormulaCalculateParam::getCalculateId, this::calculatePriceForOrder));
    }


    /**
     * 招标/询比价 批量公式报价 导入模块下载
     * @param header
     * @param response
     */
    @PostMapping("/importTemplateDownload")
    public void importTemplateDownload(@RequestBody List<String> header,HttpServletResponse response) throws IOException {
        if(CollectionUtils.isEmpty(header)){
            throw new BaseException("请传递excel模板的表头参数");
        }
        // 构建表格
        Workbook workbook = createWorkbook(header);
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "公式报价导入模板");
        // 导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public Workbook createWorkbook(List<String> params){
        //解析header数据 -1业务实体  -2物料描述

        //获取外围系统数据 org
        List<Long> essentialFactorIds = new ArrayList<>();
        String headerString = params.get(0);
        JSONObject headerJSONObject = JSON.parseObject(headerString);
        for(String str : headerJSONObject.keySet()){
            if(str != "-1" && str != "-2"){
                essentialFactorIds.add(Long.parseLong(str));
            }
        }
        QueryWrapper<EssentialFactor> wrapper = new QueryWrapper();
        wrapper.in("ESSENTIAL_FACTOR_ID",essentialFactorIds);
        List<EssentialFactor> essentialFactorList = essentialFactorService.list(wrapper);
        Map<String,EssentialFactor> map = essentialFactorList.stream().collect(Collectors.toMap(item ->String.valueOf(item.getEssentialFactorId()),item -> item));


        List<String> header = new ArrayList<>();
        List<List<String>> content = new ArrayList<>();
        //获取头表信息

        header.add("业务实体");
        header.add("物料编码");
        for (String str : headerJSONObject.keySet()) {
            //获取表头
            if(str != "-1" && str != "-2"){
                EssentialFactor e = map.get(str);
                if(Objects.nonNull(e)){
                    header.add(e.getEssentialFactorName());
                }
            }
        }

        //获取内容
        for(int i=0;i<params.size();i++){
            String s = params.get(i);
            JSONObject jsonObject = JSONObject.parseObject(s);
            List<String> contentChildren = new ArrayList<>();
            contentChildren.add(jsonObject.getString("-1"));
            contentChildren.add(jsonObject.getString("-2"));
            content.add(contentChildren);
        }

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

        //设置标题列
        for(int i=0;i<header.size();i++){
            XSSFCell cell = row.createCell(cellIndex);
            cell.setCellValue(header.get(i));
            cell.setCellStyle(cellStyle);
            cellIndex ++;
        }
        //设置内容
        for(int i=0;i<content.size();i++){
            XSSFRow rowContent = sheet.createRow(i + 1);
            List<String> c = content.get(i);
            for(int j=0;j<c.size();j++){
                XSSFCell cell = rowContent.createCell(j);
                cell.setCellValue(c.get(j));
                cell.setCellStyle(cellStyle);
            }
        }
        return workbook;
    }

    /**
     * 招标/询比价 批量公式报价 导入
     * @param file
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importExcel2")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        //获取数据
        JSONObject jsonObject = getImportData(inputStream);

        Map<String,Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message", "success");
        result.put("fileName", file.getOriginalFilename());
        result.put("data",jsonObject);
        return result;

    }

    /**
     * 获取excel表数据,封装为json返回前端
     * @param inputStream
     * @return
     */
    private JSONObject getImportData(InputStream inputStream) throws IOException {
        JSONObject result = new JSONObject();

        Workbook workbook = new XSSFWorkbook(inputStream);
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum() + 1;
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        // 获取标题行数据
        List<String> head = new ArrayList<>();
        //获取第一行
        Row headRow = sheet.getRow(0);
        for(int i=0;i<totalCells;i++){
            Cell cell = headRow.getCell(i);
            head.add(ExcelUtil.getCellValue(cell));
        }

        //获取外围系统数据 公式要素
        List<String> params = new ArrayList<>();
        for(int i=2;i<totalCells;i++){
            Cell cell = headRow.getCell(i);
            params.add(ExcelUtil.getCellValue(cell));
        }
        List<EssentialFactor> essentialFactorList = essentialFactorService.listByEssentialFactorName(params);
        Map<String,EssentialFactor> map = essentialFactorList.stream().collect(Collectors.toMap(item ->item.getEssentialFactorName(),item -> item));

        //遍历每个单元格的值(除去表头)
        for(int i=1;i<totalRows;i++){
            Row currentRow = sheet.getRow(i);
            JSONArray array = new JSONArray();
            JSONObject jsonCell = new JSONObject();
            for(int j=0;j<totalCells;j++){
                Cell cell = currentRow.getCell(j);

                if(j == 0){
                    jsonCell.put("-1",ExcelUtil.getCellValue(cell));
                }else if(j == 1){
                    jsonCell.put("-2",ExcelUtil.getCellValue(cell));
                }else{
                    String value = ExcelUtil.getCellValue(cell);
                    String key = head.get(j);
                    EssentialFactor essentialFactor = map.get(key);
                    jsonCell.put(Objects.nonNull(essentialFactor.getEssentialFactorId()) ? String.valueOf(essentialFactor.getEssentialFactorId()) : "-1",value);
                }
            }
            array.add(jsonCell);
            result.put("row",array);
        }
        return result;
    }


    //招标/询比价 批量公式报价 导出
    @PostMapping("/export")
    public void export(@RequestBody List<FormulaPriceDTO> formulaPriceDTOList, HttpServletResponse response) throws IOException {
        if(CollectionUtils.isEmpty(formulaPriceDTOList)){
            throw new BaseException("请传递必要参数");
        }
        //构建表格
        Workbook workbook = createWorkbookModel(formulaPriceDTOList);
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划导出");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private Workbook createWorkbookModel(List<FormulaPriceDTO> formulaPriceDTOList){
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


        List<FormulaPriceDTO> type1 = new ArrayList<>();
        List<FormulaPriceDTO> type2 = new ArrayList<>();
        List<FormulaPriceDTO> type3 = new ArrayList<>();
        for(FormulaPriceDTO formulaPriceDTO : formulaPriceDTOList){
            if(FormulaPriceExportTypeEnum.TYPE1.getValue().equals(formulaPriceDTO.getType())){
                type1.add(formulaPriceDTO);
            }
            if(FormulaPriceExportTypeEnum.TYPE2.getValue().equals(formulaPriceDTO.getType())){
                type2.add(formulaPriceDTO);
            }
            if(FormulaPriceExportTypeEnum.TYPE3.getValue().equals(formulaPriceDTO.getType())){
                type3.add(formulaPriceDTO);
            }
        }

        //行index
        int rowIndex = 0;

        //开始设置头表数据
        for(int i=0;i<type1.size();i++){
            //当前单元格下标
            int cellIndex = 0;
            FormulaPriceDTO formulaPriceDTO = type1.get(i);
            XSSFRow row = sheet.createRow(rowIndex);

            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(formulaPriceDTO.getContent());

            rowIndex ++;
        }

        //开始设置基材数据
        for(int i=0;i<type3.size();i++){
            //当前单元格下标
            int cellIndex = 0;
            FormulaPriceDTO formulaPriceDTO = type3.get(i);
            XSSFRow row = sheet.createRow(rowIndex);

            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(formulaPriceDTO.getBaseMaterialName());
            cellIndex ++;

            XSSFCell cell2 = row.createCell(cellIndex);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(formulaPriceDTO.getBaseMaterialPrice());
            cellIndex ++;

            rowIndex ++;
        }

        //开始设置数据
        for(int i=0;i<type2.size();i++){
            //当前单元格下标
            int cellIndex = 0;
            FormulaPriceDTO formulaPriceDTO = formulaPriceDTOList.get(i);
            XSSFRow row = sheet.createRow(rowIndex);

            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(formulaPriceDTO.getOrgName());
            cellIndex ++;

            XSSFCell cell2 = row.createCell(cellIndex);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(formulaPriceDTO.getMaterialItemDesc());
            cellIndex ++;

            JSONArray jsonArray = JSON.parseArray(formulaPriceDTO.getContent());
            for(int j=0;j<jsonArray.size();j++){
                //获取值
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                String value = jsonObject.getString("value");
                XSSFCell cell3 = row.createCell(cellIndex);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue(value);
                cellIndex ++;
            }
            rowIndex ++;

        }
        return workbook;
    }


}

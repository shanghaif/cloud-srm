package com.midea.cloud.srm.base.dept.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.dept.mapper.CompaniesMapper;
import com.midea.cloud.srm.base.dept.service.ICompaniesService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dept.entity.Companies;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *   服务实现类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-07 13:59:21
 *  修改内容:
 * </pre>
*/
@Service
public class CompaniesServiceImpl extends ServiceImpl<CompaniesMapper, Companies> implements ICompaniesService {
    @Resource
    private FileCenterClient fileCenterClient;
    private static final List<String> fixedTitle;

    static {
        fixedTitle = new ArrayList<>();
        fixedTitle.addAll(Arrays.asList("*公司ID", "*公司代码", "*公司名称", "公司简称","地址","*有效期从","有效期至","*是否启用"));

    }
    @Transactional
    @Override
    public Map<String, Object> importExcelByBasics(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查上传文件
        EasyExcelUtil.checkParam(file, fileupload);
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        // 导入文件是否有错误信息标志
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 导入的数据
        List<Companies> companiesnList = new ArrayList<>();
        // 每行报错信息
        List<String> errorList = new ArrayList<>();
        // 返回信息
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message", "success");
        // 获取导入数据
        Workbook workbook = this.getImportData(inputStream, companiesnList, errorList, errorFlag);
        if (errorFlag.get()) {
            // 有报错,上传报错文件
            this.uploadErrorFile(file, fileupload, errorList, result, workbook);
        } else {
            if (CollectionUtils.isNotEmpty(companiesnList)){
                this.saveBatch(companiesnList);
            }
        }
        return result;
    }
    /**
     * 获取导入的数据, 并对数据进行校验
     *
     * @param inputStream
     * @param errorList
     * @throws IOException
     * @throws ParseException
     */
    private Workbook getImportData(InputStream inputStream, List<Companies> companiesnList, List<String> errorList, AtomicBoolean errorFlag) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        // 获取标题行数据
        List<String> head = new ArrayList<>();
        QueryWrapper<Companies> wrapper = new QueryWrapper<>();
        wrapper.select("BASE_COMPANIES_ID,COMPANY_ID,COMPANY_CODE,COMPANY_SHORT_NAME,COMPANY_FULL_NAME");
        wrapper.isNotNull("COMPANY_ID");
        List<Companies> list = this.list(wrapper);
        Map<BigDecimal, Companies> companyIdMap = list.stream().collect(Collectors.toMap(Companies::getCompanyId, Function.identity()));
        Map<String, Companies> companyCodeMap = list.stream().collect(Collectors.toMap(Companies::getCompanyCode, Function.identity()));
        Map<String, Companies> companyFullNameMap = list.stream().collect(Collectors.toMap(Companies::getCompanyFullName, Function.identity()));


        //获取首行
        Row headRow = sheet.getRow(0);
        // 遍历每个单元格的值
        for (int i = 0; i < totalCells; i++) {
            Cell cell = headRow.getCell(i);
            head.add(ExcelUtil.getCellValue(cell));
        }

        // 遍历内容行获取数据,从2行开始,也就是行的下标为1
        for (int r = 1; r <= totalRows; r++) {
            Row row = sheet.getRow(r);
            if (null == row) {
                // 过滤空行,空行一下内容全部上移一行
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows--;
                continue;
            }

            // 过滤空行, 当前行每个单元格的值都为空时, 当前行一下行全部往上移一行
            int count = 0;
            for (int i = 0; i < totalCells; i++) {
                // 获取当前单元格
                Cell cell = row.getCell(i);
                // 调用方法获取数值
                String cellValue = ExcelUtil.getCellValue(cell);
                if (null == cellValue || "".equals(cellValue)) {
                    count++;
                }
            }
            if (count == totalCells) {
                if (r + 1 > totalRows) {
                    break;
                }
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows--;
                continue;

            }
// <------------------------------------一下正式开始获取表格内容-------------------------------------------->
            // 收集每行错误信息
            StringBuffer errorMsg = new StringBuffer();
            // 开始遍历行的单元格值
            Companies companies = new Companies();
            StringBuffer uniKey = new StringBuffer();


            // 获取公司ID
            Cell cell0 = row.getCell(0 );
            String cell00 = ExcelUtil.getCellValue(cell0);
            if (StringUtils.isNotEmpty(cell00)) {
                cell00 = cell00.trim();
                uniKey.append(cell00);
                try {
                    BigDecimal companyId = new BigDecimal(Long.parseLong(cell00));
                    if (companyIdMap.get(companyId) == null) {
                        companies.setCompanyId(companyId);
                    } else {
                        errorFlag.set(true);
                        errorMsg.append("公司信息已存在；");
                    }
                } catch (Exception e) {
                    errorFlag.set(true);
                    errorMsg.append("公司ID应为纯数字; ");
                }
            }

            // 检查公司代码
            Cell cell1 = row.getCell(1);
            String companyCode = ExcelUtil.getCellValue(cell1);
            if (StringUtils.isNotEmpty(companyCode)) {
                companyCode = companyCode.trim();
                uniKey.append(companyCode);
                if (companyCodeMap.get(companyCode)==null){
                    companies.setCompanyCode(companyCode);
                }else {
                    errorFlag.set(true);
                    errorMsg.append("公司代码重复! ");
                }
            } else {
                errorFlag.set(true);
                errorMsg.append("公司代码不能为空; ");
            }

            // 公司名称
            Cell cell2 = row.getCell(2);
            String companyFullName = ExcelUtil.getCellValue(cell2);
            if (StringUtils.isNotEmpty(companyFullName)) {
                companyFullName = companyFullName.trim();
                uniKey.append(companyFullName);
                if (companyFullNameMap.get(companyFullName)==null){
                    companies.setCompanyFullName(companyFullName);
                }else {
                    errorFlag.set(true);
                    errorMsg.append("公司名称重复！");
                }
            } else {
                errorFlag.set(true);
                errorMsg.append("公司名称不能为空; ");
            }

            // 公司简称
            Cell cell3 = row.getCell(3);
            String companyShortName = ExcelUtil.getCellValue(cell3);
            if (StringUtils.isNotEmpty(companyShortName)) {
                companyShortName = companyShortName.trim();
                uniKey.append(companyShortName);
                companies.setCompanyShortName(companyShortName);
            }

            //地址
            Cell cell4 = row.getCell(4);
            String address = ExcelUtil.getCellValue(cell4);
            if (StringUtils.isNotEmpty(address)) {
                address = address.trim();
                uniKey.append(address);
                companies.setAddress(address);
            }

            //*有效期从----》生效日期
            Cell cell5 = row.getCell(5);
            String cell05 = ExcelUtil.getCellValue(cell5);
            LocalDate startDateActive=null;
            if (StringUtils.isNotEmpty(cell05)) {
                cell05 = cell05.trim();
                uniKey.append(cell05);
                try{
                    startDateActive = DateUtil.parseLocalDates(cell05);
                    companies.setStartDateActive(DateUtil.getLocalDateByDate(startDateActive));
                }catch (Exception e){
                    errorFlag.set(true);
                    errorMsg.append("有效期从日期格式应为YYYY-MM-DD ");
                }
            } else {
                errorFlag.set(true);
                errorMsg.append("有效期从不能为空; ");
            }

            //有效期至---->失效日期
            Cell cell6 = row.getCell(6);
            String cell06 = ExcelUtil.getCellValue(cell6);
            LocalDate endDateActive=null;
            if (StringUtils.isNotEmpty(cell06)) {
                cell06 = cell06.trim();
                uniKey.append(cell06);
                try{
                    endDateActive = DateUtil.parseLocalDates(cell06);
                    if (endDateActive.compareTo(startDateActive)>=0){
                        companies.setStartDateActive(DateUtil.getLocalDateByDate(endDateActive));
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("有效期结束日期不可小于开始日期！");
                    }

                }catch (Exception e){
                    errorFlag.set(true);
                    errorMsg.append("有效期从日期格式应为YYYY-MM-DD ");
                }
            }

            //是否启用---》
            Cell cell7 = row.getCell(7);
            String enabledFlag = ExcelUtil.getCellValue(cell7);
            if (StringUtils.isNotEmpty(enabledFlag)) {
                enabledFlag = enabledFlag.trim();
                uniKey.append(enabledFlag);
                if ("Y".equals(enabledFlag)||"N".equals(enabledFlag)){
                    companies.setEnabledFlag(enabledFlag);
                }else {
                    errorFlag.set(true);
                    errorMsg.append("启用标识格式:Y，N;(Y为启用，N为弃用)");
                }
            } else {
                errorFlag.set(true);
                errorMsg.append("启用标识不能为空; ");
            }
            companies.setBaseCompaniesId(IdGenrator.generate());
            companiesnList.add(companies);
            errorList.add(errorMsg.toString());
        }
        return workbook;
    }
    private void uploadErrorFile(MultipartFile file, Fileupload fileupload, List<String> errorList, Map<String, Object> result, Workbook workbook) {
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);

        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        sheet.setColumnWidth(totalCells, sheet.getColumnWidth(totalCells) * 17 / 5);

        // 设置"错误信息"标题
        this.setErrorTitle(workbook, sheet, totalCells);
        for (int i = 1; i <= totalRows; i++) {
            Cell cell = sheet.getRow(i).createCell(totalCells);
            cell.setCellValue(errorList.get(i - 1));
        }
        Fileupload fileupload1 = ExcelUtil.uploadErrorFile(fileCenterClient, fileupload, workbook, file);
        result.put("status", YesOrNo.NO.getValue());
        result.put("message", "error");
        result.put("fileuploadId", fileupload1.getFileuploadId());
        result.put("fileName", fileupload1.getFileSourceName());
    }
    private void setErrorTitle(Workbook workbook, Sheet sheet, int totalCells) {
        Row row0 = sheet.getRow(0);
        // 创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
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
        Cell cell1 = row0.createCell(totalCells);
        cell1.setCellValue("错误信息");
        cell1.setCellStyle(cellStyle);
    }
    /**
     * 下载公司基本信息数据导入模板
     * @return
     */
    @Override
    public void imporDownloadByBasics(HttpServletResponse response) throws Exception {
        // 构建表格
        Workbook workbook = crateWorkbookModel();
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "公司基本信息数据导入模板");
        // 导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
    /**
     * 构建导入模板
     */
    public Workbook crateWorkbookModel() throws ParseException {
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
        //第一行
        XSSFRow row = sheet.createRow(0);

        // 当前单元格下标
        int cellIndex = 0;

        // 设置固定标题列
        for (int i = 0; i < fixedTitle.size(); i++) {
            XSSFCell cell1 = row.createCell(cellIndex);
            if (i != fixedTitle.size() - 1) {
                cell1.setCellValue(fixedTitle.get(i));
                cell1.setCellStyle(cellStyle);
            } else {
                String msg = "有效期从,有效期至需进行日期格式校验，格式为YYYY-MM-DD";
                EasyExcelUtil.setCellStyle(workbook, cell1, sheet, msg, fixedTitle.get(i));
            }
            cellIndex++;
        }
        return workbook;
    }
}

package com.midea.cloud.srm.base.customimport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.base.customimport.mapper.ImportHeadMapper;
import com.midea.cloud.srm.base.customimport.service.IImportHeadService;
import com.midea.cloud.srm.base.customimport.service.IImportLineService;
import com.midea.cloud.srm.model.base.customimport.dto.ImportModelParamDto;
import com.midea.cloud.srm.model.base.customimport.entity.ImportHead;
import com.midea.cloud.srm.model.base.customimport.entity.ImportLine;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.collections4.CollectionUtils;
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
import java.time.LocalDate;
import java.util.*;

/**
*  <pre>
 *  自定义导入头表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 15:22:34
 *  修改内容:
 * </pre>
*/
@Service
public class ImportHeadServiceImpl extends ServiceImpl<ImportHeadMapper, ImportHead> implements IImportHeadService {
    @Resource
    private IImportLineService iImportLineService;

    private static final List<Object> fixedTitle;

    static {
        fixedTitle = new ArrayList<>();
        fixedTitle.addAll(Arrays.asList("固定标题1","固定标题2","固定标题3","固定标题4","固定标题5"));
    }

    @Override
    public void importModelDownload(ImportModelParamDto importModelParamDto, HttpServletResponse response) throws IOException {
        // 构建模板
        Workbook workbook = crateWorkbookModel(importModelParamDto);
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "自定义导入模板");
        // 导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 构建导入模板
     * @param importModelParamDto
     */
    public Workbook crateWorkbookModel(ImportModelParamDto importModelParamDto) {
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

        // 设置固定标题列
        for(int i= 0;i<5;i++){
            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellValue(fixedTitle.get(i).toString());
            cell1.setCellStyle(cellStyle);
            cellIndex ++;
        }

        // 设置字典动态列
        List<String> dicParams = importModelParamDto.getDicParams();
        if (CollectionUtils.isNotEmpty(dicParams)) {
            for (int i = 0; i < dicParams.size(); i++) {
                XSSFCell cell = row.createCell(cellIndex);
                // 设置内容
                cell.setCellValue(dicParams.get(i));
                // 设置格式
                cell.setCellStyle(cellStyle);
                cellIndex ++;
            }
        }

        // 设置时间动态列
        LocalDate startDate = importModelParamDto.getStartDate();
        LocalDate endDate = importModelParamDto.getEndDate();
        List<String> monthBetween = DateUtil.getMonthBetween(startDate, endDate,"yyyy-MM");
        if (CollectionUtils.isNotEmpty(monthBetween)) {
            for (int i = 0; i < monthBetween.size(); i++) {
                XSSFCell cell = row.createCell(cellIndex);
                // 设置内容
                cell.setCellValue(monthBetween.get(i));
                // 设置格式
                cell.setCellStyle(cellStyle);
                cellIndex ++;
            }
        }


        for (int i = 0; i < cellIndex; i++) {
            // 调整每一列宽度
            sheet.autoSizeColumn((short) i);
            // 解决自动设置列宽中文失效的问题
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }

        return workbook;
    }

    @Override
    @Transactional
    public Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws IOException {
        // 检查上传文件
        EasyExcelUtil.checkParam(file,fileupload);
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        // 获取导入数据
        List<ImportHead> importHeads = getImportData(inputStream);
        // 保存
        if(CollectionUtils.isNotEmpty(importHeads)){
            importHeads.forEach(importHead -> {
                Long headId = IdGenrator.generate();
                importHead.setImportHeadId(headId);
                List<ImportLine> importLines = importHead.getImportLines();
                if(CollectionUtils.isNotEmpty(importLines)){
                    importLines.forEach(importLine -> {
                        importLine.setImportLineId(IdGenrator.generate());
                        importLine.setImportHeadId(headId);
                    });
                    iImportLineService.saveBatch(importLines);
                }
            });
            this.saveBatch(importHeads);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message","success");
        return result;
    }

    public List<ImportHead> getImportData(InputStream inputStream) throws IOException {
        List<ImportHead> importHeads = new ArrayList<>();
        // 获取工作簿
        Workbook workbook = new XSSFWorkbook(inputStream);
        List<Map<String, Object>> dataLst = new ArrayList<>();
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        // 获取标题行数据
        List<String> head = new ArrayList<>();
        // 获取首行
        Row headRow = sheet.getRow(0);
        // 遍历每个单元格的值
        for (int i = 0; i < totalCells; i++) {
            Cell cell = headRow.getCell(i);
            head.add(PoiUtils.getCellValue(cell));
        }

        // 遍历内容行获取数据,从2行开始,也就是行的下标为1
        for (int r = 1; r <= totalRows; r++) {
            Row row = sheet.getRow(r);
            if (null == row) {
                // 过滤空行,空行一下内容全部上移一行
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows --;
                continue;
            }

            // 过滤空行, 当前行每个单元格的值都为空时, 当前行一下行全部往上移一行
            int count = 0;
            for (int i = 0; i < totalCells; i++) {
                // 获取当前单元格
                Cell cell = row.getCell(i);
                // 调用方法获取数值
                String cellValue = PoiUtils.getCellValue(cell);
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
                totalRows --;
                continue;

            }

            // 开始遍历行的单元格值
            ImportHead importHead = new ImportHead();
            List<ImportLine> importLines = new ArrayList<>();
            // 先获取5个固定值
            importHead.setFixedOne(PoiUtils.getCellValue(row.getCell(0)));
            importHead.setFixedTwo(PoiUtils.getCellValue(row.getCell(1)));
            importHead.setFixedThree(PoiUtils.getCellValue(row.getCell(2)));
            importHead.setFixedFour(PoiUtils.getCellValue(row.getCell(3)));
            importHead.setFixedFive(PoiUtils.getCellValue(row.getCell(4)));

            for (int c = 5; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = PoiUtils.getCellValue(cell);
                ImportLine importLine = new ImportLine();
                importLine.setTitle(head.get(c));
                importLine.setValue(cellValue);
                importLines.add(importLine);
            }
            importHead.setImportLines(importLines);
            importHeads.add(importHead);
        }
        return importHeads;
    }

    @Override
    public void exportExcel(ImportModelParamDto importModelParamDto, HttpServletResponse response) throws IOException {
        // 获取标题列
        List<String> titleList = getTitleList(importModelParamDto);
        // 构建数据, 列转行
        List<ImportHead> importHeads = this.list();
        // 动态列字段
        List<Map<String, Object>> dataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(importHeads)) {
            // 获取动态列字段
            getDynamicColumnData(titleList, importHeads, dataList);
            // 获取模板
            XSSFWorkbook workbook = (XSSFWorkbook)crateWorkbookModel(importModelParamDto);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= importHeads.size(); i++) {
                XSSFRow row = sheet.createRow(i);
                ImportHead importHead = importHeads.get(i - 1);

                // 先设置固定内容
                XSSFCell cell0 = row.createCell(0);
                cell0.setCellValue(importHead.getFixedOne());
                XSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(importHead.getFixedTwo());
                XSSFCell cell2 = row.createCell(2);
                cell2.setCellValue(importHead.getFixedThree());
                XSSFCell cell3 = row.createCell(3);
                cell3.setCellValue(importHead.getFixedFour());
                XSSFCell cell4 = row.createCell(4);
                cell4.setCellValue(importHead.getFixedFive());

                // 设置动态列数据
                if(CollectionUtils.isNotEmpty(titleList)){
                    int index = 5;
                    for (int j = 0; j < titleList.size(); j++) {
                        Map<String, Object> date = dataList.get(i - 1);
                        String key = titleList.get(j);
                        if(null != date.get(key)){
                            XSSFCell cell = row.createCell(index);
                            cell.setCellValue(date.get(key).toString());
                        }
                        index ++;
                    };
                }
            }
            // 获取输出流
            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "自定义导入模板");
            // 导出
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

        }

    }

    public void getDynamicColumnData(List<String> titleList, List<ImportHead> importHeads, List<Map<String, Object>> dataList) {
        if (CollectionUtils.isNotEmpty(titleList)) {
            // 获取动态变化列信息
            importHeads.forEach(importHead -> {
                // 获取变化字段
                Long importHeadId = importHead.getImportHeadId();
                Map<String, Object> data = new HashMap<>();
                List<ImportLine> importLines = iImportLineService.list(new QueryWrapper<>(new ImportLine().setImportHeadId(importHeadId)));
                if(CollectionUtils.isNotEmpty(importLines)){
                    importLines.forEach(importLine -> {
                        String title = importLine.getTitle();
                        if(titleList.contains(title)){
                            data.put(title,importLine.getValue());
                        }
                    });
                }
                dataList.add(data);
            });
        }
    }

    public List<String> getTitleList(ImportModelParamDto importModelParamDto) {
        // 日期列表
        List<String> monthBetween = DateUtil.getMonthBetween(importModelParamDto.getStartDate(), importModelParamDto.getEndDate(),"yyyy-MM");
        // 字典列表
        List<String> dicParams = importModelParamDto.getDicParams();
        List<String> titles = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(dicParams)){
            titles.addAll(dicParams);
        }
        if(CollectionUtils.isNotEmpty(monthBetween)){
            titles.addAll(monthBetween);
        }
        return titles;
    }

    @Override
    public ImportHead get(Long importHeadId) {
        Assert.notNull(importHeadId, "importHeadId不能为空");
        ImportHead importHead = this.getById(importHeadId);
        if(null != importHead){
            List<ImportLine> importLines = iImportLineService.list(new QueryWrapper<>(new ImportLine().setImportHeadId(importHead.getImportHeadId())));
            importHead.setImportLines(importLines);
        }
        return importHead;
    }

    @Override
    public PageInfo<List<Object>> listPage(ImportModelParamDto importModelParamDto) {
        PageUtil.startPage(importModelParamDto.getPageNum(),importModelParamDto.getPageSize());
        List<ImportHead> importHeads = this.list(new QueryWrapper<ImportHead>().orderByDesc("IMPORT_HEAD_ID"));
        List<List<Object>> results = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(importHeads)) {
            // 动态变化字段列
            List<String> titleList = getTitleList(importModelParamDto);
            List<Object> titles = new ArrayList<>();
            titles.addAll(fixedTitle);
            titles.addAll(titleList);
            // 设置首行标题内容
            results.add(titles);

            // 设置内容行
            importHeads.forEach(importHead -> {
                List<Object> lineInfo = new ArrayList<>();
                // 固定字段
                lineInfo.add(importHead.getFixedOne());
                lineInfo.add(importHead.getFixedTwo());
                lineInfo.add(importHead.getFixedThree());
                lineInfo.add(importHead.getFixedFour());
                lineInfo.add(importHead.getFixedFive());

                if (CollectionUtils.isNotEmpty(titleList)) {
                    // 动态字段类
                    List<ImportLine> importLines = iImportLineService.list(new QueryWrapper<>(new ImportLine().setImportHeadId(importHead.getImportHeadId())));
                    if(CollectionUtils.isNotEmpty(importLines)){
                        HashMap<String, Object> lineMap = new HashMap<>();
                        importLines.forEach(importLine -> {
                            lineMap.put(importLine.getTitle(),importLine.getValue());
                        });

                        titleList.forEach(title->{
                            Object value = lineMap.get(title);
                            if(StringUtil.isEmpty(value)){
                                lineInfo.add("");
                            }else {
                                lineInfo.add(value);
                            }
                        });
                    }
                }
                results.add(lineInfo);
            });

        }

        return new PageInfo<List<Object>>(results);
    }
}

package com.midea.cloud.common.utils;

import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.BaseErrorCell;
import jxl.Cell;
import jxl.DateCell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 *  Excel处理公共类
 * </pre>
 *
 * @author huangbf3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/16 10:32
 *  修改内容:
 * </pre>
 */
public class ExcelUtil {

    // 上传错误文件处理 >>>>>
    /**
     * 上传导入错误文件
     * @param headLineNum excel导入文件标题所占行数
     * @param fileCenterClient
     * @param rwb
     * @param errorCells
     * @param fileupload
     * @param fileName
     * @param originalFilename
     * @param contentType
     */
    public static void uploadErrorFile(Integer headLineNum,FileCenterClient fileCenterClient, Workbook rwb, List<ErrorCell> errorCells, Fileupload fileupload
            , String fileName, String originalFilename, String contentType) {
        ByteArrayInputStream inputStream = null;
        try {
            inputStream = outputErrorFile(headLineNum,rwb, errorCells);
            MultipartFile file = new MockMultipartFile(fileName, originalFilename, contentType, inputStream);
            fileupload = fileCenterClient.feignClientUpload(file,fileupload.getSourceType(),fileupload.getUploadType()
                    ,fileupload.getFileModular(),fileupload.getFileFunction(),fileupload.getFileType());
            throw new BaseException(JSONObject.toJSONString(fileupload));
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        //这里是将要输出的workbook
        WritableWorkbook writeBook = null;
        ByteArrayInputStream swapStream = null;
        try {
            Workbook workbook = Workbook.getWorkbook(new File("D:\\MyData\\huangbf3\\Downloads\\采购订单模板.xls"));
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setWriteAccess(null);
            workbookSettings.setPropertySets(false);
            workbookSettings.setIgnoreBlanks(true);
            workbookSettings.setEncoding("UTF-8");
            writeBook = Workbook.createWorkbook(new File("D:\\MyData\\huangbf3\\Downloads\\采购订单模板3.xls")
                    , workbook, workbookSettings);
            for (int sheetIndex = 0; sheetIndex < writeBook.getNumberOfSheets(); sheetIndex++) {
                WritableSheet writableSheet = writeBook.getSheet(sheetIndex);
                for (int r = 2; r < writableSheet.getRows(); r++) {
                    boolean isBlack = true;
                    for (int c = 0; c < writableSheet.getColumns(); c++) {
                        if (StringUtils.isNotBlank(writableSheet.getCell(c, r).getContents())) {
                            isBlack = false;
                            break;
                        }
                    }
                    if (isBlack) {
                        break;
                    }
                    //列数
                    for (int c = 0; c < writableSheet.getColumns(); c++) {
                        WritableCell writableCell = writableSheet.getWritableCell(c, r);
                        if( writableCell.getWritableCellFeatures()!=null){
                            WritableCellFormat wcf = new WritableCellFormat();
                            writableCell.setCellFormat(wcf);
                            writableCell.getWritableCellFeatures().removeComment();
                        }
                    }
                }
            }

            writeBook.write();
            writeBook.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 获取导入报错输出流
     * @param headLineNum excel导入文件标题所占行数
     * @param workbook
     * @param errorCells
     * @return
     * @throws IOException
     */
    public static ByteArrayInputStream outputErrorFile(Integer headLineNum,Workbook workbook, List<ErrorCell> errorCells) throws IOException {
        //这里是将要输出的workbook
        ByteArrayOutputStream os = null;
        WritableWorkbook writeBook = null;
        ByteArrayInputStream swapStream = null;
        try {
            os = new ByteArrayOutputStream();
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setWriteAccess(null);
            workbookSettings.setEncoding("UTF-8");
            writeBook = Workbook.createWorkbook(os, workbook, workbookSettings);
            for (int sheetIndex = 0; sheetIndex < writeBook.getNumberOfSheets(); sheetIndex++) {
                WritableSheet writableSheet = writeBook.getSheet(sheetIndex);
                for (int r = headLineNum; r < writableSheet.getRows(); r++) {
                    boolean isBlack = true;
                    for (int c = 0; c < writableSheet.getColumns(); c++) {
                        if (StringUtils.isNotBlank(writableSheet.getCell(c, r).getContents())) {
                            isBlack = false;
                            break;
                        }
                    }
                    if (isBlack) {
                        break;
                    }
                    //列数
                    for (int c = 0; c < writableSheet.getColumns(); c++) {
                        WritableCell writableCell = writableSheet.getWritableCell(c, r);
                        if( writableCell.getWritableCellFeatures()!=null){
                            WritableCellFormat wcf = new WritableCellFormat();
                            writableCell.setCellFormat(wcf);
                            writableCell.getWritableCellFeatures().removeComment();
                        }
                    }
                }
            }
            //读取将要修改的cell
            for (ErrorCell errorCell : errorCells) {
                if(StringUtils.isNotBlank(errorCell.getComment())){
                    WritableSheet sheet = writeBook.getSheet(errorCell.getSheetIndex());
                    WritableCell cell = sheet.getWritableCell(errorCell.getColumn(), errorCell.getRow());
                    WritableCellFormat wcf = new WritableCellFormat(NumberFormats.TEXT);
                    wcf.setBackground(Colour.RED);
                    Label lable = new Label(cell.getColumn(), cell.getRow(), cell.getContents());
                    //将修改后的单元格格式设置成和原来一样的
                    lable.setCellFormat(wcf);
                    WritableCellFeatures cellFeatures = new WritableCellFeatures();
                    if(cell.getWritableCellFeatures()!=null&&StringUtils.isNotBlank(cell.getWritableCellFeatures().getComment())){
                        errorCell.setComment(cell.getWritableCellFeatures().getComment()+";"+errorCell.getComment());
                    }
                    cellFeatures.setComment(errorCell.getComment());//增加批注

                    lable.setCellFeatures(cellFeatures);
                    //将修改后的cell放回sheet中
                    sheet.addCell(lable);
                }
                if(StringUtils.isNotBlank(errorCell.getLineErrorContents())){
                    WritableSheet sheet = writeBook.getSheet(errorCell.getSheetIndex());
                    WritableCell cell = sheet.getWritableCell(errorCell.getColumn(), errorCell.getRow());
                    WritableCellFormat wcf = new WritableCellFormat(NumberFormats.TEXT);
                    wcf.setBackground(Colour.RED);
                    Label lable = new Label(errorCell.getColumn(), cell.getRow(),errorCell.getLineErrorContents());
                    //将修改后的单元格格式设置成和原来一样的
                    lable.setCellFormat(wcf);
                    //将修改后的cell放回sheet中
                    sheet.addCell(lable);
                }
            }
            writeBook.write();
            writeBook.close();
            swapStream = new ByteArrayInputStream(os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
        }
        return swapStream;
    }

    // 上传错误文件处理 <<<<<

    /**
     * 添加错误单元格记录
     * @param isError
     * @param errorCells
     * @param errorCell
     */
    public static void addErrorCell(boolean isError, List<ErrorCell> errorCells, ErrorCell errorCell) {
        if (isError) {
            errorCells.add(errorCell);
        }
    }

    /**
     * 从对象中获取错误信息列表
     * @param object
     * @return
     */
    public static List<ErrorCell> getErrorCells(Object object){
        List<ErrorCell> errorCells = new ArrayList<>();
        if(object==null){
            return new ArrayList();
        }
        if(object instanceof BaseErrorCell){
            BaseErrorCell baseErrorCell = (BaseErrorCell) object;
            errorCells.addAll(baseErrorCell.getErrorCellMap().values().stream().collect(Collectors.toList()));
        }else if(object instanceof List){
            List list = (List) object;
            for(Object item:list){
                if(item instanceof BaseErrorCell){
                    BaseErrorCell baseErrorCell = (BaseErrorCell) item;
                    errorCells.addAll(baseErrorCell.getErrorCellMap().values().stream().collect(Collectors.toList()));
                }else {
                    errorCells.addAll(getErrorCells(item));
                }
            }
        }else{
            if(object.getClass().getName().contains("com.midea.cloud")){
                Class<?> forName = object.getClass();
                Field[] fields = forName.getDeclaredFields();
                for(Field field:fields){
                    field.setAccessible(true);
                    try {
                        Object fieldObject = field.get(object);
                        if(fieldObject!=null){
                            errorCells.addAll(getErrorCells(fieldObject));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        List<ErrorCell> result = new ArrayList();
        for(ErrorCell errorCell:errorCells){
            if(StringUtils.isNotBlank(errorCell.getComment())||StringUtils.isNotBlank(errorCell.getLineErrorContents())){
                result.add(errorCell);
            }
        }
        System.out.println(JSONObject.toJSONString(result));
        return result;
    }

    // 单元格数据格式校验 >>>>>
    /**
     * 检查文本是否是正整型字符串
     *
     * @param text
     * @return
     */
    public static boolean checkPositiveInt(String text) {
        try {
            Integer number = new Integer(text);
            return number > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查文本是否是正数
     *
     * @param text
     * @return
     */
    public static boolean checkPositiveNumber(String text) {
        try {
            Double number = new Double(text);
            return number > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 將excel中的日期转换为Date对象
     * @param cell
     * @return
     */
    public static Date getDateByStr(Cell cell){
        try{
            if(StringUtils.equals("Date",cell.getType().toString())){
                DateCell dateCell = (DateCell) cell;
                return dateCell.getDate();
            }else{
                return getDateByStr(cell.getContents());
            }
        }catch (Exception e){
            return null;
        }
    }



    /**
     * 將excel中的日期转换为Date对象
     * @param text
     * @return
     */
    public static Date getDateByStr(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        try{
            if(text.contains("/")){//可能是yyyy/MM/dd格式
                return DateUtils.parseDate(text,"yyyy/MM/dd");
            }else if(text.contains("-")){//可能是yyyy-MM-dd格式
                return DateUtils.parseDate(text,"yyyy-MM-dd");
            }else{//可能是yyyyMMdd格式
                return DateUtils.parseDate(text,"yyyyMMdd");
            }
        }catch (Exception e){
            return null;
        }
    }

    // 根据单元格的格式获取值
    public static String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型
            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    // 数值
                    cellValue = cell.getNumericCellValue() + "";
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 处理日期格式、时间格式
                        SimpleDateFormat sdf = null;
                        if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                            sdf = new SimpleDateFormat("HH:mm");
                        } else {// 日期
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        }
                        Date date = cell.getDateCellValue();
                        cellValue = sdf.format(date);
                    } else if (cell.getCellStyle().getDataFormat() == 58) {
                        // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                        cellValue = sdf.format(date);
                    }else {
                        // 数字,表格中返回的数字类型是科学计数法,因此需要转换
                        double value = cell.getNumericCellValue();
                        CellStyle style = cell.getCellStyle();
                        DecimalFormat format = new DecimalFormat();
                        String temp = style.getDataFormatString();
                        // 单元格设置成常规
                        if (temp.equals("General")) {
                            format.applyPattern("#.##########");
                            cellValue = format.format(value);
                        } else if (temp.equals("000000")) {
                            format.applyPattern("000000");
                            cellValue = format.format(value);
                        } else {
                            DataFormatter formatter = new DataFormatter();
                            cellValue = formatter.formatCellValue(cell);
                        }
                    }
                    break;

                case STRING: // 字符串
                    cellValue = cell.getStringCellValue();
                    break;

                case BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;

                case FORMULA: // 公式
//                    cellValue = cell.getCellFormula() + "";
                    try {
                        // 数字,表格中返回的数字类型是科学计数法,因此需要转换
                        double value = cell.getNumericCellValue();
                        CellStyle style = cell.getCellStyle();
                        DecimalFormat format = new DecimalFormat();
                        String temp = style.getDataFormatString();
                        // 单元格设置成常规
                        if (temp.equals("General")) {
                            format.applyPattern("#.##########");
                            cellValue = format.format(value);
                        } else if (temp.equals("000000")) {
                            format.applyPattern("000000");
                            cellValue = format.format(value);
                        } else {
                            DataFormatter formatter = new DataFormatter();
                            cellValue = formatter.formatCellValue(cell);
                        }
                    } catch (IllegalStateException e) {
                        cellValue = String.valueOf(cell.getRichStringCellValue());
                    }
                    break;

                case BLANK: // 空值
                    cellValue = "";
                    break;

                case ERROR: // 故障
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }
        }
        return cellValue;
    }

    // 单元格数据格式校验 <<<<<

    /**
     * 上传导入错误文件
     * @param fileCenterClient
     * @param fileupload
     * @param workbook
     * @param file
     * @return
     */
    public static Fileupload uploadErrorFile(FileCenterClient fileCenterClient, Fileupload fileupload
            , org.apache.poi.ss.usermodel.Workbook workbook, MultipartFile file) {
        ByteArrayInputStream inputStream = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            MultipartFile fileNew = new MockMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), inputStream);
            return fileCenterClient.feignClientUpload(fileNew,fileupload.getSourceType(),fileupload.getUploadType(),fileupload.getFileModular(),fileupload.getFileFunction(),fileupload.getFileType());
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传导入错误文件
     * @param fileCenterClient
     * @param fileupload
     * @param workbook
     * @param file
     * @return
     */
    public static Fileupload uploadErrorFile(FileCenterClient fileCenterClient, Fileupload fileupload
            , org.apache.poi.ss.usermodel.Workbook workbook, MultipartFile file,List<String> errorList) {
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);

        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        sheet.setColumnWidth(totalCells, sheet.getColumnWidth(totalCells) * 17 / 5);

        // 设置"错误信息"标题
        setErrorTitle(workbook, sheet, totalCells);
        for (int i = 1; i <= totalRows; i++) {
            org.apache.poi.ss.usermodel.Cell cell = sheet.getRow(i).createCell(totalCells);
            cell.setCellValue(errorList.get(i - 1));
        }

        ByteArrayInputStream inputStream = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            MultipartFile fileNew = new MockMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), inputStream);
            return fileCenterClient.feignClientUpload(fileNew,fileupload.getSourceType(),fileupload.getUploadType(),fileupload.getFileModular(),fileupload.getFileFunction(),fileupload.getFileType());
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setErrorTitle(org.apache.poi.ss.usermodel.Workbook workbook, Sheet sheet, int totalCells) {
        Row row0 = sheet.getRow(0);
        // 创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        org.apache.poi.ss.usermodel.Cell cell1 = row0.createCell(totalCells);
        cell1.setCellValue("错误信息");
        cell1.setCellStyle(cellStyle);
    }

    /**
     *
     * @param file              文件
     * @param fileupload        fileupload
     * @param errorList         错误内容
     * @param workbook
     * @param fileCenterClient
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getUploadErrorFile(MultipartFile file, Fileupload fileupload, List<String> errorList, org.apache.poi.ss.usermodel.Workbook workbook,FileCenterClient fileCenterClient) throws Exception {
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        sheet.setColumnWidth(totalCells, sheet.getColumnWidth(totalCells) * 17 / 5);

        // 设置"错误信息"标题
        ExcelUtil.setErrorTitle(workbook, sheet, totalCells);
        for (int i = 1; i <= totalRows; i++) {
            org.apache.poi.ss.usermodel.Cell cell = sheet.getRow(i).createCell(totalCells);
            cell.setCellValue(errorList.get(i - 1));
        }
        //上传失败文件
        Fileupload fileupload1 = ExcelUtil.uploadErrorFile(fileCenterClient, fileupload, workbook, file);
        //失败信息，需要文件的id和名称
        return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
    }

    /**
     * 上传错误文件
     * @param file              文件
     * @param errorList         错误内容
     * @param workbook
     * @param fileCenterClient
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getUploadErrorFile(MultipartFile file,List<String> errorList, org.apache.poi.ss.usermodel.Workbook workbook,FileCenterClient fileCenterClient) throws Exception {
        Fileupload fileupload = new Fileupload().
                setUploadType(FileUploadType.FASTDFS.name()).
                setSourceType("WEB_APP").
                setFileModular("logistics").
                setFileFunction("quotedLineImport").
                setFileType("images");

        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        sheet.setColumnWidth(totalCells, sheet.getColumnWidth(totalCells) * 17 / 5);

        // 设置"错误信息"标题
        ExcelUtil.setErrorTitle(workbook, sheet, totalCells);
        for (int i = 1; i <= totalRows; i++) {
            org.apache.poi.ss.usermodel.Cell cell = sheet.getRow(i).createCell(totalCells);
            cell.setCellValue(errorList.get(i - 1));
        }
        //上传失败文件
        Fileupload fileupload1 = ExcelUtil.uploadErrorFile(fileCenterClient, fileupload, workbook, file);
        //失败信息，需要文件的id和名称
        return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
    }

    /**
     * 创建一个新的Workbook, 设置标题列
     * @param headName
     * @return
     */
    public static org.apache.poi.ss.usermodel.Workbook createWorkbookModel(List<String> headNames){
        // 创建工作簿
        org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook();
        // 创建工作表:Sheet1
        Sheet sheet = workbook.createSheet("Sheet1");
        // 创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBold(true);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < headNames.size(); i++) {
            org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
            // 设置内容
            cell.setCellValue(headNames.get(i));
            // 设置格式
            cell.setCellStyle(cellStyle);
        }
        return workbook;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}

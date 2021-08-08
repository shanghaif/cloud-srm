package com.midea.cloud.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.*;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.annonations.ExcelParamCheck;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class EasyExcelUtil {
    /**
     * 字典结果组合: 名字-编码
     */
    public static final String NAME_CODE = "NAME_CODE";

    /**
     * 字典结果组合: 编码-名字
     */
    public static final String CODE_NAME = "CODE_NAME";

    public static BaseClient baseClient;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestVO {
        @ExcelProperty(value = "*姓名", index = 0)
        private String name;
        @ExcelProperty(value = "*年龄", index = 1)
        private int age;
        @ExcelProperty(value = "学校", index = 2)
        private String school;
    }

    @Data
    @ColumnWidth(20)
    public static class TestVO1 {
        @ExcelProperty(value = "姓名", index = 0)
        private String name;
        @ExcelProperty(value = "*年龄", index = 1)
        private int age;
        @ExcelProperty(value = "*学校", index = 2)
        private String school;
    }

    @Data
    @ColumnWidth(20)
    public static class MultiLanguage {
        @ExcelProperty(value = "字段1", index = 0)
        private String value1;
        @ExcelProperty(value = "字段2", index = 1)
        private String value2;
        @ExcelProperty(value = "字段3", index = 2)
        private String value3;
        @ExcelProperty(value = "字段4", index = 3)
        private String value4;
    }

    @Test
    public void testReadAllSheet() throws IOException {
        // 输入流
        FileInputStream inputStream = new FileInputStream("D:\\MyData\\wangpr\\Desktop\\多语言翻译导出.xlsx");
        List<MultiLanguage> listenerDatas = readExcelAllSheet(inputStream, MultiLanguage.class);
        if(CollectionUtils.isNotEmpty(listenerDatas)){
            listenerDatas.forEach(System.out::println);
        }
    }

    /**
     * 测试简单的导出
     */
    @Test
    public void simpleExport() throws Exception {
        // 输出流
        FileOutputStream outputStream = new FileOutputStream("D:/简单的导出.xlsx");
        // 数据
        List<TestVO> testVOS = Arrays.asList(new TestVO("小王",18,"美创"));
        // 导出
        EasyExcel.write(outputStream).head(TestVO.class).sheet(0).sheetName("sheetName").doWrite(testVOS);
    }

    /**
     * 测试简单导入
     * @throws Exception
     */
    @Test
    public void simpleImport() throws Exception{
        // 输入流
        FileInputStream inputStream = new FileInputStream("D:/简单的导出.xlsx");
        //读取数据
        List<TestVO> datas = readExcelWithModelNew(inputStream, TestVO.class);
        // 打印
        System.out.println(JSON.toJSONString(datas));
    }

    /**
     * 测试无模型导出,可自定义列宽
     */
    @Test
    public void testNotModelExport() throws FileNotFoundException {
        // 标题
        List<String> title = Arrays.asList("姓名", "年龄", "学校");
        // 内容
        List<List<Object>> dataList = new ArrayList<>();
        for(int i= 0; i< 10 ;i++){
            dataList.add(Arrays.asList("小王"+i,18+i,"美的"+i));
        }
        // 输出流
        FileOutputStream outputStream = new FileOutputStream("D:/无模板导出1.xlsx");
        writeExcelWithModel(outputStream,dataList,title,"sheet",new CustomizeExportHandler(20));
    }

    @Test
    public void testBigDropDown() throws Exception {
        Map<Integer, String[]> spinnerLongMap = new HashMap<>();
        String arrays[] = new String[400];
        for(int i = 0;i<400;i++){
            arrays[i] = "德玛西亚"+i;
        }
        spinnerLongMap.put(4,arrays);
        EasyExcel.write(
                new FileOutputStream("D:/2.xlsx"), TestVO.class)
                .registerWriteHandler(new SpinnerLongHandler())
                .sheet("管理").doWrite(new ArrayList<TestVO>());
    }

    /**
     * 测试分批查询导出
     */
    @Test
    public void testBatchExport() throws Exception {
        FileOutputStream outputStream = new FileOutputStream("D:/1.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        /**
         * 分批查询进行导出
         * 示例: 现在要导出30000条数据, 模拟每次查询出10000条数据进行导出(防止全部查出内存溢出), 循环3次
         */
        WriteSheet writeSheet = EasyExcel.writerSheet(0).head(TestVO.class).build();
        for(int i = 0 ; i < 3 ; i++){
            // 每次查询10000条数据
            List<TestVO> testVOS = new ArrayList<>();
            for(int j = 0; j < 10000;j++){
                TestVO testVO = new TestVO("名字"+j,j,"学校"+j);
                testVOS.add(testVO);
            }
            excelWriter.write(testVOS,writeSheet);
        }
        excelWriter.finish();
    }

    /**
     * 测试导出下拉款
     *
     * @throws IOException
     */
    @Test
    public void testDropDown() throws IOException {
        // 输出流
        OutputStream outputStream = new FileOutputStream(new File("D:\\1.xlsx"));
        // 导出的数据
        List<TestVO> dataList = new ArrayList<>();
        // 指定标红色的列
        List<Integer> columns = Arrays.asList(0, 1);
        // 指定批注
        HashMap<Integer, String> annotationsMap = new HashMap<>();
        annotationsMap.put(0, "第一列标题批注");
        annotationsMap.put(1, "第二列标题批注");
        HashMap<Integer, String[]> dropDownMap = new HashMap<>();
        // 指定下拉框
        String[] ags = {"13", "34", "64"};
        String[] school = {"一中", "二中", "三中"};
        dropDownMap.put(1, ags);
        dropDownMap.put(2, school);
        TitleHandler titleHandler = new TitleHandler(columns, IndexedColors.RED.index, annotationsMap, dropDownMap);
        writeExcelWithModel(outputStream, dataList, TestVO.class, "sheetName", titleHandler);
    }

    /**
     * 测试导出模板
     * 1. 标题指定某列标红色字段
     * 2. 标题指定某列加批注
     */
    @Test
    public void testExport1() throws FileNotFoundException {
        // 输出流
        OutputStream outputStream = new FileOutputStream(new File("D:\\1.xlsx"));
        // 导出的数据
        List<TestVO> dataList = new ArrayList<>();
        // 指定标红色的列
        List<Integer> columns = Arrays.asList(0, 1);
        // 指定批注
        HashMap<Integer, String> annotationsMap = new HashMap<>();
        annotationsMap.put(0, "第一列标题批注");
        annotationsMap.put(1, "第二列标题批注");
        TitleHandler titleHandler = new TitleHandler(columns, IndexedColors.RED.index, annotationsMap);
        writeExcelWithModel(outputStream, dataList, TestVO.class, "sheetName", titleHandler);
    }

    /**
     * 测试导入多个sheet导入
     *
     * @throws Exception
     */
    @Test
    public void read() throws Exception {
        String filePath = "D:/1.xlsx";
        InputStream inputStream = null;
        inputStream = new FileInputStream(new File(filePath));
        AnalysisEventListenerImpl<Object> listener = new AnalysisEventListenerImpl<>();
        ExcelReader excelReader = EasyExcel.read(inputStream, listener).build();
        // 第一个sheet读取类型
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(TestVO.class).headRowNumber(3).build();
        // 第二个sheet读取类型
        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(TestVO1.class).build();
        // 开始读取第一个sheet
        excelReader.read(readSheet1);
        List<Object> list = listener.getDatas();
        list.forEach((user) -> {
            TestVO user1 = (TestVO) user;
            System.out.println(user1.getName() + ", " + user1.getAge() + ", " + user1.getSchool());
        });
        // 清空之前的数据
        listener.getDatas().clear();
        // 开始读取第二个sheet
        excelReader.read(readSheet2);
        System.out.println("---------------------------------");
        List<Object> list2 = listener.getDatas();
        list2.forEach((user) -> {
            TestVO1 user2 = (TestVO1) user;
            System.out.println(user2.getName() + ", " + user2.getAge() + ", " + user2.getSchool());
        });
    }

    /**
     * 多个sheet导入测试
     *
     * @throws FileNotFoundException
     */
    @Test
    public void sheetImport() throws FileNotFoundException {
        // 输出流
        OutputStream outputStream = null;
        outputStream = new FileOutputStream(new File("D:/1.xlsx"));

        // 导出的数据
        List<TestVO> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestVO testVO = new TestVO();
            testVO.setAge(i + 20);
            testVO.setName("vo" + i);
            testVO.setSchool("school" + i);
            dataList.add(testVO);
        }

        // 标题
        List<String> headList = Arrays.asList("姓名", "年龄", "学校");

        String sheetName = "导出文件";

        List<Integer> columnIndexs = Arrays.asList(0, 1, 2);
        List<Integer> rowIndexs = Arrays.asList(0);
        TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rowIndexs, columnIndexs, IndexedColors.RED.index);

        List<Integer> columnIndexs1 = Arrays.asList(0, 1);
        List<Integer> rowIndexs1 = Arrays.asList(1, 2, 3, 4);
        CellColorSheetWriteHandler colorSheetWriteHandler = new CellColorSheetWriteHandler(rowIndexs1, columnIndexs1, IndexedColors.RED.index);

        // 测试多sheel导出
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        WriteSheet test1 = EasyExcel.writerSheet(0, "test1").head(TestVO.class).
                registerWriteHandler(horizontalCellStyleStrategy).
                registerWriteHandler(titleColorSheetWriteHandler).build();
        WriteSheet test2 = EasyExcel.writerSheet(1, "test2").head(TestVO.class).
                registerWriteHandler(horizontalCellStyleStrategy).
                registerWriteHandler(titleColorSheetWriteHandler).build();
        excelWriter.write(dataList, test1).write(dataList, test2);
        excelWriter.finish();
    }

    /**
     * 使用 模型 来读取Excel
     *
     * @param fileInputStream Excel的输入流
     * @param tClass         模型的类
     * @return 返回 模型 的列表(为object列表,需强转)
     */
    public static <T> List<T> readExcelAllSheet(InputStream fileInputStream, Class<T> tClass) throws IOException {
        AnalysisEventListenerImpl<T> listener = new AnalysisEventListenerImpl<T>();
        ExcelReader excelReader = EasyExcel.read(fileInputStream, tClass, listener).build();
        excelReader.readAll();
        excelReader.finish();
        return listener.getDatas();
    }

    /**
     * 给单元格设置内容/批注
     *
     * @param wb         工作表
     * @param cell       单元格
     * @param sheet      工作蒲
     * @param annotation 批注内容
     * @param content    单元格内容
     */
    public static void setCellStyle(Workbook wb, Cell cell, XSSFSheet sheet, String annotation, String content) {
        // 创建绘图对象
        XSSFDrawing p = sheet.createDrawingPatriarch();
        // 插入单元格内容
        cell.setCellValue(content);
        // 获取批注对象
        // (int dx1, int dy1, int dx2, int dy2, short col1, int row1, short
        // col2, int row2)
        // 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
        XSSFComment comment = p.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 5, 5, (short) 10, 10));
        // 输入批注信息
        comment.setString(new XSSFRichTextString(annotation));
        // 添加作者,选中B5单元格,看状态栏
//		comment.setAuthor("toad");
        // 将批注添加到单元格对象中
        cell.setCellComment(comment);
        CellStyle cellStyle = wb.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
//		// 设置背景色为红色
//		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
//		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置单元格样式
        cell.setCellStyle(cellStyle);
    }

    /**
     * 使用模型读取EXCEL数据
     * @param file 上传的文件
     * @param classA  模板类
     * @param <T> 泛型
     * @return
     */
    public static <T> List<T> readExcelWithModel(MultipartFile file, Class<T> classA) {
        List<T> list = new ArrayList<>();
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<T> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream, listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(classA).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            list = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return list;
    }

    /**
     * 使用 模型 来读取Excel
     *
     * @param fileInputStream Excel的输入流
     * @param tClass         模型的类
     * @return 返回 模型 的列表(为object列表,需强转)
     */
    public static <T> List<T> readExcelWithModelNew(InputStream fileInputStream, Class<T> tClass) throws IOException {
        AnalysisEventListenerImpl<T> listener = new AnalysisEventListenerImpl<T>();
        ExcelReader excelReader = EasyExcel.read(fileInputStream, tClass, listener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
        return listener.getDatas();
    }

    /**
     * 使用 模型 来读取Excel
     *
     * @param fileInputStream Excel的输入流
     * @param clazz           模型的类
     * @return 返回 模型 的列表(为object列表,需强转)
     */
    public static List<Object> readExcelWithModel(InputStream fileInputStream, Class<? extends Object> clazz) throws IOException {
        AnalysisEventListenerImpl<Object> listener = new AnalysisEventListenerImpl<Object>();
        ExcelReader excelReader = EasyExcel.read(fileInputStream, clazz, listener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
        return listener.getDatas();
    }


    public static<T> void readExcelWithModel(InputStream fileInputStream,Class<T> clazz, AnalysisEventListener listener , int sheetNo) throws IOException {
        ExcelReader reader = EasyExcel.read(fileInputStream,clazz, listener).build();
        ReadSheet readSheet = EasyExcel.readSheet(sheetNo).build();
        reader.read(readSheet);
        reader.finish();
    }


    /**
     * 导出数据到Excel
     *
     * @param dataList 保存的数据
     * @param clazz    数据模型
     * @throws IOException
     */
    public static byte[] writeExcelWithModel(List<? extends Object> dataList, Class<? extends Object> clazz) throws IOException {
        String fileName = System.currentTimeMillis() + ".xls";
        //指定文件输出位置
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        //创建writeSheet，设置基本信息
        WriteSheet writeSheet = new WriteSheet();
        //设置sheet表名
        writeSheet.setSheetName("sheet1");
        //设置Clazz会把配置信息ExcelProperty设置为表头
        writeSheet.setClazz(clazz);
        /**
         * 写数据到Write上下文中
         * 第一个参数：要写入的内容
         * 第二个参数：要写入的sheet目标
         */
        excelWriter.write(dataList, writeSheet);
        excelWriter.finish();
        File file = new File(fileName);
        byte[] buffer = FileUtils.readFileToByteArray(file);
        file.delete();
        return buffer;
    }

    /**
     * 使用 模型 来写入Excel
     *
     * @param outputStream Excel的输出流
     * @param dataList     要写入的以 模型 为单位的数据
     * @param clazz        模型的类
     */
    public static void writeExcelWithModel(OutputStream outputStream, String sheetName, List<? extends Object> dataList, Class<? extends Object> clazz) {
        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = EasyExcel.write(outputStream).build();
        //创建writeSheet，设置基本信息
        WriteSheet sheet = EasyExcel.writerSheet().sheetName(sheetName).head(clazz).build();
        writer.write(dataList, sheet);
        writer.finish();
    }

    /**
     * 导出数据到Excel
     *
     * @param dataList 保存的数据
     * @param clazz    数据模型
     * @throws IOException
     */
    public static <T> void writeExcelWithModel(HttpServletResponse response,String fileName,List<T> dataList, Class<T> clazz) throws IOException {
        OutputStream outputStream = getServletOutputStream(response, fileName);
        EasyExcel.write(outputStream).head(clazz).sheet(0,fileName).doWrite(dataList);
    }

    /**
     * 使用 模型 来写入Excel
     *
     * @param outputStream Excel的输出流
     * @param dataList     要写入的以 模型 为单位的数据
     * @param clazz        模型的类
     */
    public static void writeExcelWithModel(OutputStream outputStream,List<? extends Object> dataList, Class<? extends Object> clazz) {
        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = EasyExcel.write(outputStream).build();
        //创建writeSheet，设置基本信息
        WriteSheet sheet = EasyExcel.writerSheet().head(clazz).build();
        writer.write(dataList, sheet);
        writer.finish();
    }

    /**
     * 导入报错生产多个sheet表Excel
     * Description
     *
     * @Param [outputStream, sheetName, dataList, clazz]
     * @Author fansb3@meicloud.com
     * @Date 2020/10/15
     **/
    public static void writeExcelWithModel(OutputStream outputStream, String[] sheetName, List<List<? extends Object>> dataList, Class<? extends Object>[] clazz) {
        ExcelWriter writer = EasyExcel.write(outputStream).build();
        for (int i = 1; i < sheetName.length; i++) {
            WriteSheet sheet = EasyExcel.writerSheet(i - 1, sheetName[i]).head(clazz[i - 1]).build();
            writer.write(dataList.get(i - 1), sheet);
        }
        writer.finish();
    }

    /**
     * @param filePath  文件路径
     * @param sheetName 定义sheet名称
     * @param headList  sheet表头
     * @param lineList  sheet行数据
     */
    public static void writeExcelWithModel(String filePath, String sheetName, List<List<String>> headList, List lineList) {
        EasyExcel.write(filePath).head(headList).sheet(sheetName).doWrite(lineList);
    }

    /**
     * @param outputStream 输出流
     * @param sheetName    定义sheet名称
     * @param headList     sheet表头
     * @param lineList     sheet行数据
     */
    public static void writeExcelWithModel(OutputStream outputStream, String sheetName, List<String> headList, List<List<Object>> lineList) {
        List<List<String>> list = new ArrayList<>();
        if (headList != null) {
            headList.forEach(h -> list.add(Collections.singletonList(h)));
            EasyExcel.write(outputStream).head(list).sheet(sheetName).
                    registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(lineList);
        }
    }

    /**
     * 生成excle
     *
     * @param outputStream 输出流
     * @param data         数据源
     * @param head         表头
     */
    public static void writeSimpleBySheet(OutputStream outputStream, List<List<Object>> data, List<String> head) {
        // 导出文件
        writeExcelWithModel(outputStream,data,head,"sheet",new CustomizeExportHandler(15));
    }

    /**
     * 获取输出流
     *
     * @param response
     * @param fileName
     * @return
     * @throws IOException
     */
    public static ServletOutputStream getServletOutputStream(HttpServletResponse response, String fileName) throws IOException {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");
        return response.getOutputStream();
    }

    /**
     * 开始导出
     *
     * @param response
     * @param dataList
     * @param head
     * @param fileName
     * @throws IOException
     */
    public static void exportStart(HttpServletResponse response, List<List<Object>> dataList, List<String> head, String fileName) throws IOException {
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        // 导出文件
        EasyExcelUtil.writeExcelWithModel(outputStream, fileName, head, dataList);
    }

    /**
     * 判断文件是否 Excel
     *
     * @param fileName 文件名
     * @return
     */
    public static boolean isExcel(String fileName) {
        if (null != fileName) {
            fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if ("xls".equals(fileName) || "xlsx".equals(fileName)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 检查excel是否.xlsx
     * @param file
     * @return
     */
    public static void checkExcelIsXlsx(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (null != fileName) {
            fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!"xlsx".equals(fileName)) {
                throw new BaseException("请上传正确的excel文件,文件后缀需为: [.xlsx]");
            }
        } else {
            throw new BaseException("文件名为空!");
        }
    }

    /**
     * 上传导入错误文件
     *
     * @param fileCenterClient
     * @param fileupload
     * @param dataList
     * @param clazz
     */
    public static Fileupload uploadErrorFile(FileCenterClient fileCenterClient, Fileupload fileupload
            , List<? extends Object> dataList, Class<? extends Object> clazz, String fileName, String originalFilename, String contentType) {
        ByteArrayInputStream inputStream = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writeExcelWithModel(outputStream, fileupload.getFileSourceName(), dataList, clazz);
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            MultipartFile file = new MockMultipartFile(fileName, originalFilename, contentType, inputStream);
            return fileCenterClient.feignClientUpload(file, fileupload.getSourceType(), fileupload.getUploadType(), fileupload.getFileModular(), fileupload.getFileFunction(), fileupload.getFileType());
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

    public static Fileupload uploadErrorFile(FileCenterClient fileCenterClient, Fileupload fileupload
            , List<List<? extends Object>> dataList, Class<? extends Object>[] clazz, String[] fileName, String originalFilename, String contentType) {
        ByteArrayInputStream inputStream = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writeExcelWithModel(outputStream, fileName, dataList, clazz);
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            MultipartFile file = new MockMultipartFile(fileName[0], originalFilename, contentType, inputStream);
            return fileCenterClient.feignClientUpload(file, fileupload.getSourceType(), fileupload.getUploadType(), fileupload.getFileModular(), fileupload.getFileFunction(), fileupload.getFileType());
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
     * 上传错误文件
     * @param fileCenterClient
     * @param dataList
     * @param tClass
     * @param fileName
     * @param file
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> Map<String,Object> uploadErrorFile(FileCenterClient fileCenterClient
            , List<T> dataList, Class<T> tClass,String fileName,MultipartFile file) throws IOException {
        Fileupload fileupload = new Fileupload().
                setUploadType(FileUploadType.FASTDFS.name()).
                setSourceType("WEB_APP").
                setFileModular("logistics").
                setFileFunction("quotedLineImport").
                setFileType("images");
        ByteArrayInputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeExcelWithModel(outputStream, fileName, dataList, tClass);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        MultipartFile fileNew = new MockMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), inputStream);
        Fileupload errorFileupload = fileCenterClient.feignClientUpload(fileNew, fileupload.getSourceType(), fileupload.getUploadType(), fileupload.getFileModular(), fileupload.getFileFunction(), fileupload.getFileType());
        return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
    }

    /**
     * 使用该方法必须为list加上括号
     *
     * @param fileCenterClient
     * @param fileupload
     * @param dataList
     * @param file
     * @param <T>
     * @return
     */
    public static <T> Fileupload uploadErrorFile(FileCenterClient fileCenterClient
            , Fileupload fileupload, List<T> dataList, Class<T> clazz, MultipartFile file) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ) {
            //把信息写到outputStream
            writeExcelWithModel(outputStream, fileupload.getFileSourceName(), dataList, clazz);
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray())) {
                file = new MockMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), inputStream);
                return fileCenterClient.feignClientUpload(file, fileupload.getSourceType(), fileupload.getUploadType()
                        , fileupload.getFileModular(), fileupload.getFileFunction()
                        , fileupload.getFileType());
            }
        } catch (IOException e) {
            throw new BaseException("上传错误文件出错!");
        }
    }

    /**
     * 检查上传文件参数
     *
     * @param file
     * @param fileupload
     */
    public static void checkParam(MultipartFile file, Fileupload fileupload) {
        Assert.notNull(fileupload, "文件参数不能为空");
        Assert.notNull(fileupload.getSourceType(), "来源类型不能为空");
        Assert.notNull(fileupload.getUploadType(), "上传介质类型不能为空");
        Assert.notNull(fileupload.getFileModular(), "文件所属模块不能为空");
        Assert.notNull(fileupload.getFileFunction(), "文件所属功能不能为空");
        Assert.notNull(fileupload.getFileType(), "文件所属类型不能为空");
        Assert.notNull(file, "文件上传失败");
        //校验文件名是否excel
        String fileName = file.getOriginalFilename();
        if (!EasyExcelUtil.isExcel(fileName)) {
            throw new RuntimeException("请导入正确的Excel文件");
        }
    }

    /**
     * 导出表头必填字段标红色
     *
     * @param outputStream      输入流
     * @param dataList          导入数据
     * @param headList          表头列表
     * @param sheetName         sheetname
     * @param cellWriteHandlers
     */
    public static void writeExcelWithModel(OutputStream outputStream, List<List<Object>> dataList, List<String> headList, String sheetName, CellWriteHandler... cellWriteHandlers) {
        List<List<String>> list = new ArrayList<>();
        if (headList != null) {
            headList.forEach(h -> list.add(Collections.singletonList(h)));
        }

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(outputStream).head(list).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy);
        if (null != cellWriteHandlers && cellWriteHandlers.length > 0) {
            for (int i = 0; i < cellWriteHandlers.length; i++) {
                excelWriterSheetBuilder.registerWriteHandler(cellWriteHandlers[i]);
            }
        }
        // 开始导出
        excelWriterSheetBuilder.doWrite(dataList);
    }

    /**
     * 导出excel
     *
     * @param outputStream      输出流
     * @param dataList          导出的数据
     * @param classT            模板类
     * @param sheetName         sheetName
     * @param cellWriteHandlers 样式处理类
     */
    public static void writeExcelWithModel(OutputStream outputStream, List<? extends Object> dataList, Class<? extends Object> classT, String sheetName, CellWriteHandler... cellWriteHandlers) {

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(outputStream, classT).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy);
        if (null != cellWriteHandlers && cellWriteHandlers.length > 0) {
            for (int i = 0; i < cellWriteHandlers.length; i++) {
                excelWriterSheetBuilder.registerWriteHandler(cellWriteHandlers[i]);
            }
        }
        // 开始导出
        excelWriterSheetBuilder.doWrite(dataList);
    }

    /**
     * 获取字典数据
     *
     * @param code       字典编码
     * @param baseClient
     * @return 名字 - 编码
     */
    public static Map<String, String> getDicNameCode(String code, BaseClient baseClient) {
        Map<String, String> divisionMap = new HashMap<>(16);
        divisionMap = getDicMapByCode(code, baseClient, divisionMap,NAME_CODE);
        return divisionMap;
    }

    /**
     * 获取字典数据
     *
     * @param code       字典编码
     * @return 名字 - 编码
     */
    public static Map<String, String> getDicNameCode(String code) {
        Map<String, String> divisionMap = new HashMap<>(16);
        divisionMap = getDicMapByCode(code, baseClient, divisionMap,NAME_CODE);
        return divisionMap;
    }

    /**
     * 获取字典数据
     *
     * @param code       字典编码
     * @param baseClient
     * @return 编码 - 名字
     */
    public static Map<String, String> getDicCodeName(String code, BaseClient baseClient) {
        Map<String, String> divisionMap = new HashMap<>();
        divisionMap = getDicMapByCode(code, baseClient, divisionMap, CODE_NAME);
        return divisionMap;
    }

    /**
     * 获取字典数据
     *
     * @param code       字典编码
     * @return 编码 - 名字
     */
    public static Map<String, String> getDicCodeName(String code) {
        Map<String, String> divisionMap = new HashMap<>();
        divisionMap = getDicMapByCode(code, baseClient, divisionMap, CODE_NAME);
        return divisionMap;
    }

    /**
     * 根据字典编码查询
     * @param dicCodes 多个字典编码
     * @return 编码 - 名字
     */
    public static Map<String, Map<String,String>> getDicCodeNameByCodes(List<String> dicCodes){
        Map<String, Map<String,String>> dicCodeNameMap = new HashMap<>(16);
        getDicMapByCodes(dicCodes, dicCodeNameMap,CODE_NAME);
        return dicCodeNameMap;
    }

    /**
     * 根据字典编码查询
     * @param dicCodes 多个字典编码
     * @return 名字 - 编码
     */
    public static Map<String, Map<String,String>> getDicNameCodeByCodes(List<String> dicCodes){
        Map<String, Map<String,String>> dicCodeNameMap = new HashMap<>(16);
        getDicMapByCodes(dicCodes, dicCodeNameMap,NAME_CODE);
        return dicCodeNameMap;
    }

    /**
     * 获取所有生效的币种字典
     * @param baseClient 编码 - 名字
     * @return
     */
    public static Map<String,String> getCurrencyCodeName(BaseClient baseClient){
        Map<String, String> currencyCodeName = new HashMap<>();
        getCurrencyAll(baseClient, currencyCodeName,CODE_NAME);
        return currencyCodeName;
    }

    /**
     * 获取所有生效的币种字典
     * @param baseClient 编码 - 名字
     * @return
     */
    public static Map<String,String> getCurrencyCodeName(){
        Map<String, String> currencyCodeName = new HashMap<>();
        getCurrencyAll(baseClient, currencyCodeName,CODE_NAME);
        return currencyCodeName;
    }

    /**
     * 获取所有生效的币种字典
     * @param baseClient 名字 - 编码
     * @return
     */
    public static Map<String,String> getCurrencyNameCode(BaseClient baseClient){
        Map<String, String> currencyCodeName = new HashMap<>();
        getCurrencyAll(baseClient, currencyCodeName,NAME_CODE);
        return currencyCodeName;
    }

    /**
     * 获取所有生效的币种字典
     * @param baseClient 名字 - 编码
     * @return
     */
    public static Map<String,String> getCurrencyNameCode(){
        Map<String, String> currencyCodeName = new HashMap<>();
        getCurrencyAll(baseClient, currencyCodeName,NAME_CODE);
        return currencyCodeName;
    }

    public static Map<String, String> getDicMapByCode(String code, BaseClient baseClient, Map<String, String> divisionMap,String type) {
        if (StringUtil.notEmpty(code)) {
            if(null == baseClient){
                baseClient = getBaseClient();
            }
            List<DictItemDTO> division = baseClient.listAllByDictCode(code);
            if (CollectionUtils.isNotEmpty(division)) {
                Map<String, String> map;
                if (NAME_CODE.equals(type)) {
                    map = division.stream().collect(Collectors.toMap(DictItemDTO::getDictItemName, DictItemDTO::getDictItemCode, (k1, k2) -> k1));
                }else {
                    map = division.stream().collect(Collectors.toMap(DictItemDTO::getDictItemCode, DictItemDTO::getDictItemName, (k1, k2) -> k1));
                }
                divisionMap.putAll(map);
            }
        }
        return divisionMap;
    }

    public static void getDicMapByCodes(List<String> dicCodes, Map<String, Map<String, String>> dicCodeNameMap, String type) {
        if (CollectionUtils.isNotEmpty(dicCodes)) {
            BaseClient baseClient = getBaseClient();
            List<DictItemDTO> dictItemDTOS = baseClient.listByDictCode(dicCodes);
            if(CollectionUtils.isNotEmpty(dictItemDTOS)){
                Map<String, List<DictItemDTO>> listMap = dictItemDTOS.stream().collect(Collectors.groupingBy(DictItemDTO::getDictCode));
                listMap.forEach((decCode, dictItems) -> {
                    if(CollectionUtils.isNotEmpty(dictItems)){
                        Map<String, String> map = null;
                        if (NAME_CODE.equals(type)) {
                            map = dictItems.stream().collect(Collectors.toMap(DictItemDTO::getDictItemName, DictItemDTO::getDictItemCode, (k1, k2) -> k1));
                        }else {
                            map = dictItems.stream().collect(Collectors.toMap(DictItemDTO::getDictItemCode, DictItemDTO::getDictItemName, (k1, k2) -> k1));

                        }
                        dicCodeNameMap.put(decCode,map);
                    }
                });
            }
        }
    }

    public static void getCurrencyAll(BaseClient baseClient, Map<String, String> currencyCodeName,String type) {
        if(null == baseClient){
            baseClient = getBaseClient();
        }
        List<PurchaseCurrency> purchaseCurrencies = baseClient.listAllPurchaseCurrency();
        if(CollectionUtils.isNotEmpty(purchaseCurrencies)){
            Map<String,String> map ;
            if (NAME_CODE.equals(type)) {
                map = purchaseCurrencies.stream().collect(Collectors.toMap(PurchaseCurrency::getCurrencyName, PurchaseCurrency::getCurrencyCode, (k1, k2) -> k1));
            }else {
                map = purchaseCurrencies.stream().collect(Collectors.toMap(PurchaseCurrency::getCurrencyCode, PurchaseCurrency::getCurrencyName, (k1, k2) -> k1));
            }
            currencyCodeName.putAll(map);
        }
    }

    public static <T> List<T> readExcelWithModel(Class<T> clazz, InputStream fileInputStream) throws IOException {
        AnalysisEventListenerImpl<T> listener = new AnalysisEventListenerImpl();
        ExcelReader excelReader = EasyExcel.read(fileInputStream, clazz, listener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
        return listener.getDatas();
    }

    /**
     * 开始导出
     *
     * @param response
     * @param dataList 内容列表
     * @param head     标题
     * @param fileName 文件名
     * @Param width    列宽  (推荐15)
     * @throws IOException
     */
    public static void exportStart(HttpServletResponse response, List<List<Object>> dataList, List<String> head, String fileName,int width) throws IOException {
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        // 导出文件
        writeExcelWithModel(outputStream,dataList,head,fileName,new CustomizeExportHandler(width));
    }

    /**
     * 导出数据字典转换
     * @param list
     * @return <T>
     * @throws IOException
     */
    public static <T> void exportDicChange(List<T> list) {
        if(CollectionUtils.isNotEmpty(list)){
            // 先获取所有的字典
            Map<String, Map<String,String>> dicMap = new HashMap<>(16);
            Class<?> aClass = list.get(0).getClass();
            Field[] fields = aClass.getDeclaredFields();
            // 有ExcelParamCheck注解的属性
            List<Field> fieldsList = new ArrayList<>();

            if(!ObjectUtils.isEmpty(fields)){
                Arrays.stream(fields).forEach(field -> {
                    // 参数检查注解
                    ExcelParamCheck excelParamCheck = field.getAnnotation(ExcelParamCheck.class);
                    if(null != excelParamCheck){
                        // 字典编码
                        String dicCode = excelParamCheck.dicCode();
                        if (!ObjectUtils.isEmpty(dicCode)) {
                            Map<String, String> dicCodeName = getDicCodeName(dicCode);
                            dicMap.put(dicCode,dicCodeName);
                            fieldsList.add(field);
                        }
                    }
                });

                // 币种 (编码-名字)
                Map<String, String> currencyCodeName = getCurrencyCodeName();

                if (CollectionUtils.isNotEmpty(fieldsList)) {
                    list.forEach(obj -> {
                        try {
                            for(Field field : fieldsList){
                                field.setAccessible(true);
                                Object value = field.get(obj);
                                if(!ObjectUtils.isEmpty(value)){
                                    ExcelParamCheck annotation = field.getAnnotation(ExcelParamCheck.class);
                                    String dicCode = annotation.dicCode(); // 字典编码
                                    String valueStr = value.toString().trim(); // 字典条目编码
                                    // 检查字典值
                                    if(!ObjectUtils.isEmpty(dicCode)){
                                        Map<String, String> nameCodeMap = dicMap.get(dicCode);
                                        String dicName = nameCodeMap.get(valueStr);
                                        if(!ObjectUtils.isEmpty(dicName)){
                                            field.set(obj,dicName);
                                        }
                                    }
                                    // 检查币种
                                    boolean ifCurrency = annotation.isCurrency();
                                    if(ifCurrency){
                                        String currencyName = currencyCodeName.get(valueStr);
                                        if(!ObjectUtils.isEmpty(currencyName)){
                                            field.set(obj,currencyName);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            String errorMsg = ExceptionUtil.getErrorMsg(e);
                            log.error("检查导入数据非空和字典工具类报错"+errorMsg);
                            throw new BaseException("导出字典值转换工具类报错!");
                        }
                    });
                }
            }
        }
    }

    /**
     * 检查导入数据非空校验和字典
     * <<约定: 错误信息字段名为: errorMsg>></约定:>
     * @param list
     * @param errorFlag
     * @return <T>
     * @throws IOException
     */
    public static <T> void checkParamNoNullAndDic(List<T> list, AtomicBoolean errorFlag) {
        if(CollectionUtils.isNotEmpty(list)){
            // 先获取所有的字典
            Map<String, Map<String,String>> dicMap = new HashMap<>(16);
            Class<?> aClass = list.get(0).getClass();
            Field[] fields = aClass.getDeclaredFields();
            // 有ExcelParamCheck注解的属性
            List<Field> fieldsList = new ArrayList<>();

            if(!ObjectUtils.isEmpty(fields)){
                AtomicBoolean existsCurrency = new AtomicBoolean(false);
                AtomicBoolean isCurrencyName = new AtomicBoolean(false);
                Arrays.stream(fields).forEach(field -> {
                    // 参数检查注解
                    ExcelParamCheck excelParamCheck = field.getAnnotation(ExcelParamCheck.class);
                    if(null != excelParamCheck){
                        // 字典编码
                        String dicCode = excelParamCheck.dicCode();
                        if (!ObjectUtils.isEmpty(dicCode)) {
                            Map<String, String> dicNameCode = getDicNameCode(dicCode);
                            dicMap.put(dicCode,dicNameCode);
                        }
                        fieldsList.add(field);
                        // 是否为币种
                        if(excelParamCheck.isCurrency()) {
                            existsCurrency.set(true);
                            // 是否填写币种名称
                            if(excelParamCheck.isCurrencyName()){
                                isCurrencyName.set(true);
                            }
                        }
                    }
                });

                Map<String, String> currencyMap = new HashMap<String, String>(16);
                if(existsCurrency.get()){
                    if(isCurrencyName.get()){
                        currencyMap = getCurrencyNameCode();
                    }else {
                        currencyMap = getCurrencyCodeName();
                    }
                }

                if (CollectionUtils.isNotEmpty(fieldsList)) {
                    Map<String, String> finalCurrencyMap = currencyMap;
                    list.forEach(obj -> {
                        StringBuffer errorMsg = new StringBuffer();
                        try {
                            for(Field field : fieldsList){
                                field.setAccessible(true);
                                Object value = field.get(obj);
                                ExcelParamCheck annotation = field.getAnnotation(ExcelParamCheck.class);
                                String dicCode = annotation.dicCode(); // 字典编码
                                boolean ifRequired = annotation.ifRequired(); // 是否必填
                                boolean isCurrency = annotation.isCurrency(); // 是否为币种
                                if(ObjectUtils.isEmpty(value)){
                                    if(ifRequired){
                                        if(ObjectUtils.isEmpty(value)){
                                            String localeMsg = LocaleHandler.getLocaleMsg(annotation.errorMsg());
                                            errorMsg.append(localeMsg+" ;");
                                            errorFlag.set(true);
                                        }
                                    }
                                }else {
                                    String valueStr = value.toString().trim();
                                    // 检查字典值
                                    if(!ObjectUtils.isEmpty(dicCode)){
                                        Map<String, String> nameCodeMap = dicMap.get(dicCode);
                                        String code = nameCodeMap.get(valueStr);
                                        if(!ObjectUtils.isEmpty(code)){
                                            field.set(obj,code);
                                        }else {
                                            String localeMsg = LocaleHandler.getLocaleMsg(annotation.dicErrorMsg());
                                            errorMsg.append(localeMsg+" ;");
                                            errorFlag.set(true);
                                        }
                                    }
                                    // 检查币种
                                    if(isCurrency){
                                        String currency = finalCurrencyMap.get(valueStr);
                                        if(!ObjectUtils.isEmpty(currency)){
                                            field.set(obj,currency);
                                        }else {
                                            String localeMsg = LocaleHandler.getLocaleMsg(annotation.currencyErrorMsg());
                                            errorMsg.append(localeMsg+" ;");
                                            errorFlag.set(true);
                                        }
                                    }
                                }
                            }
                            Field field = aClass.getDeclaredField("errorMsg");
                            field.setAccessible(true);
                            if(errorMsg.length() > 0){
                                field.set(obj,errorMsg.toString());
                            }else {
                                field.set(obj,null);
                            }
                        } catch (Exception e) {
                            log.error("检查导入数据非空和字典工具类报错"+e.getMessage());
                            log.error("检查导入数据非空和字典工具类报错"+e);
                            throw new BaseException("检查导入数据非空和字典工具类报错!");
                        }
                    });
                }
            }
        }
    }

    /**
     * 检查字段名一样的字段, 检查数据类型并赋值
     * @param list
     * @param aClass
     * @param errorFlag
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T,V> List<V> dataTransform(List<T> list,Class<V> aClass,AtomicBoolean errorFlag){
        List<V> vList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            // 业务类字段
            Field[] businessFields = aClass.getDeclaredFields();
            // 导入实体类字段
            Class importClass = list.get(0).getClass();
            Field[] importFields = importClass.getDeclaredFields();
            if(!ObjectUtils.isEmpty(businessFields) && !ObjectUtils.isEmpty(importFields)){
                // 业务字段
                Map<String, Field> businessFieldMap = Arrays.stream(businessFields).collect(Collectors.toMap(Field::getName, Function.identity()));
                // 导入数据字段
                Map<String, Field> importFieldMap = Arrays.stream(importFields).collect(Collectors.toMap(Field::getName, Function.identity()));

                for(T obj:list){
                    // 手机错误信息
                    StringBuffer errorMsg = new StringBuffer();
                    V businessInstance = null;
                    try {
                        businessInstance = aClass.newInstance();
                    } catch (Exception e) {
                        log.error("创建实体类报错"+e.getMessage());
                        log.error("创建实体类报错"+e);
                    }
                    if (null != businessInstance) {
                        V finalBusinessInstance = businessInstance;
                        businessFieldMap.forEach((fieldName, field) -> {
                            field.setAccessible(true);
                            // 导入的字段
                            Field importField = importFieldMap.get(fieldName);
                            if(null != importField){
                                String typeName = field.getType().getSimpleName();
                                Object value = null;
                                try {
                                    // 获取导入的值
                                    importField.setAccessible(true);
                                    value = importField.get(obj);
                                    if (!ObjectUtils.isEmpty(value)) {
                                        String valueStr = value.toString();
                                        switch (typeName){
                                            case "Long":
                                                long aLong = Long.parseLong(valueStr);
                                                field.set(finalBusinessInstance,aLong);
                                                break;
                                            case "Integer":
                                                int i1 = Integer.parseInt(valueStr);
                                                field.set(finalBusinessInstance,i1);
                                                break;
                                            case "BigDecimal":
                                                BigDecimal decimal = new BigDecimal(valueStr);
                                                field.set(finalBusinessInstance,decimal);
                                                break;
                                            case "Date":
                                                Date date = DateUtil.parseDate(valueStr);
                                                field.set(finalBusinessInstance,date);
                                                break;
                                            case "LocalDate":
                                                Date date1 = DateUtil.parseDate(valueStr);
                                                LocalDate localDate = DateUtil.dateToLocalDate(date1);
                                                field.set(finalBusinessInstance,localDate);
                                                break;
                                            case "LocalDateTime":
                                                Date date2 = DateUtil.parseDate(valueStr);
                                                LocalDateTime localDateTime = DateUtil.dateToLocalDateTime(date2);
                                                field.set(finalBusinessInstance,localDateTime);
                                                break;
                                            case "Double":
                                                double aDouble = Double.parseDouble(valueStr);
                                                field.set(finalBusinessInstance,aDouble);
                                                break;
                                            default:
                                                field.set(finalBusinessInstance,valueStr);
                                        }
                                    }
                                } catch (Exception e) {
                                    ExcelParamCheck annotation = importField.getAnnotation(ExcelParamCheck.class);
                                    String formatErrorMsg = "字段格式错误";
                                    if(null != annotation){
//                                        formatErrorMsg = annotation.formatErrorMsg();
                                        formatErrorMsg = LocaleHandler.getLocaleMsg(annotation.formatErrorMsg());
                                    }
                                    errorMsg.append(formatErrorMsg+" ;");
                                    errorFlag.set(true);
                                }
                            }
                        });
                        vList.add((V) finalBusinessInstance);
                        try {
                            Field errorMsgField = importClass.getDeclaredField("errorMsg");
                            errorMsgField.setAccessible(true);
                            if(errorMsg.length() > 0){
                                errorMsgField.set(obj,errorMsg.toString());
                            }else {
                                errorMsgField.set(obj,null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return vList;
    }

    public static synchronized BaseClient getBaseClient(){
        if(null == baseClient){
            baseClient = SpringContextHolder.getBean(BaseClient.class);
        }
        return baseClient;
    }
}

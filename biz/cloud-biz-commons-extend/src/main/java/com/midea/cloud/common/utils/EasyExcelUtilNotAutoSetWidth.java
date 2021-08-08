package com.midea.cloud.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.CellColorSheetWriteHandler;
import com.midea.cloud.common.handler.SpinnerLongHandler;
import com.midea.cloud.common.handler.TitleColorSheetWriteHandler;
import com.midea.cloud.common.handler.TitleHandler;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class EasyExcelUtilNotAutoSetWidth {
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

    private static Sheet initSheet;

    static {
        initSheet = new Sheet(1, 0);
        initSheet.setSheetName("sheet");
        //设置自适应宽度
        initSheet.setAutoWidth(Boolean.TRUE);
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
        FileOutputStream outputStream = new FileOutputStream("D:/2.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        /**
         * 分批查询进行导出
         * 示例: 现在要导出30000条数据, 模拟每次查询出10000条数据进行导出(防止全部查出内存溢出), 循环3次
         */
        for(int i = 0 ; i < 3 ; i++){
            WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet" + i).head(TestVO.class).
                    registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
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
     * 测试导出到样式
     */
    @Test
    public void testExport() throws Exception {
        // 输出流
        OutputStream outputStream = new FileOutputStream(new File("D:/导出excel.xlsx"));

        // 导出的数据
        List<TestVO> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestVO testVO = new TestVO();
            testVO.setAge(i + 20);
            testVO.setName("vo" + i);
            testVO.setSchool("school" + i);
            dataList.add(testVO);
        }

        // 指定标颜色的行
        List<Integer> rows = Arrays.asList(0);
        // 指定标颜色的列
        List<Integer> columns = Arrays.asList(1, 2);
        TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rows, columns, IndexedColors.RED.index);
        writeExcelWithModel(outputStream, dataList, TestVO.class, "sheetName", titleColorSheetWriteHandler);
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
        ExcelWriter writer = EasyExcel.write(outputStream, clazz).build();
        //创建writeSheet，设置基本信息
        WriteSheet writeSheet = new WriteSheet();
        writeSheet.setSheetName(sheetName);
        writer.write(dataList, writeSheet);
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
            EasyExcel.write(outputStream).head(list).sheet(sheetName).doWrite(lineList);
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
        Sheet sheet = initSheet;

        if (head != null) {
            List<List<String>> list = new ArrayList<>();
            head.forEach(h -> list.add(Collections.singletonList(h)));
            sheet.setHead(list);
            sheet.setAutoWidth(Boolean.TRUE);
        }

        ExcelWriter writer = null;
        try {
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write1(data, sheet);
        } catch (Exception e) {
            log.error("文件导出异常" + e);
        } finally {
            try {
                if (writer != null) {
                    writer.finish();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

            } catch (IOException e) {
                log.error("excel文件导出失败, 失败原因：{}", e);
            }
        }

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
        ServletOutputStream outputStream = EasyExcelUtilNotAutoSetWidth.getServletOutputStream(response, fileName);
        // 导出文件
        EasyExcelUtilNotAutoSetWidth.writeExcelWithModel(outputStream, fileName, head, dataList);
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
        if (!EasyExcelUtilNotAutoSetWidth.isExcel(fileName)) {
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
    public static void writeExcelWithModel(OutputStream outputStream, List<? extends Object> dataList, List<String> headList, String sheetName, CellWriteHandler... cellWriteHandlers) {
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
        Map<String, String> divisionMap = new HashMap<>();
        if (StringUtil.notEmpty(code)) {
            List<DictItemDTO> division = baseClient.listAllByDictCode(code);
            if (CollectionUtils.isNotEmpty(division)) {
                divisionMap = division.stream().collect(Collectors.toMap(DictItemDTO::getDictItemName, DictItemDTO::getDictItemCode, (k1, k2) -> k1));
            }
        }
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
        if (StringUtil.notEmpty(code)) {
            List<DictItemDTO> division = baseClient.listAllByDictCode(code);
            if (CollectionUtils.isNotEmpty(division)) {
                divisionMap = division.stream().collect(Collectors.toMap(DictItemDTO::getDictItemCode, DictItemDTO::getDictItemName, (k1, k2) -> k1));
            }
        }
        return divisionMap;
    }

    public static <T> List<T> readExcelWithModel(Class<T> clazz, InputStream fileInputStream) throws IOException {
        AnalysisEventListenerImpl<T> listener = new AnalysisEventListenerImpl();
        ExcelReader excelReader = EasyExcel.read(fileInputStream, clazz, listener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
        return listener.getDatas();
    }

}

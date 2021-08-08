package com.midea.cloud.common.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * <pre>
 *  pdf工具类
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
public class PdfUtil {

    /**
     * 测试去空白页
     * @throws Exception
     */
    @Test
    public void testClearBlankPages() throws Exception {
        FileInputStream inputStream = new FileInputStream("D://a.pdf");
        FileOutputStream outputStream = new FileOutputStream("D://b.pdf");
        removeBlankPdfPages(inputStream, outputStream);
    }

    /**
     * 测试加图片水印
     * @throws Exception
     */
    @Test
    public void testAddImageWaterMark() throws Exception {
        FileInputStream inputStream = new FileInputStream("D://a.pdf");
        FileOutputStream outputStream = new FileOutputStream("D://b.pdf");
        FileInputStream imgInputStream = new FileInputStream("D://水印.png");
        imageWaterMark(inputStream,outputStream,getImage(imgInputStream));
    }

    /**
     * 测试加页码
     * @throws Exception
     */
    @Test
    public void testAddPage() throws Exception {
        FileInputStream inputStream = new FileInputStream("D://a.pdf");
        FileOutputStream outputStream = new FileOutputStream("D://b.pdf");
        addPageCount(inputStream,outputStream);
    }

    /**
     * 测试去空白页、加水印、加页码
     * @throws Exception
     */
    @Test
    public void testAll() throws Exception {
        FileInputStream inputStream = new FileInputStream("D://a.pdf");
        FileOutputStream outputStream = new FileOutputStream("D://b.pdf");
        FileInputStream imgInputStream = new FileInputStream("D://水印.png");
        pdfAddAll(inputStream,outputStream,getImage(imgInputStream));
    }

    /**
     * 给pdf加水印，加页码，去空白页
     * @param inputStream 输入流
     * @param outputStream 输出流
     * @param image 水印图片
     */
    public static void pdfAddAll(InputStream inputStream,OutputStream outputStream,Image image){
        // 创建内存输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 去除空白页
        removeBlankPdfPages(inputStream,byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        // 添加页码
        addPageCount(byteArrayInputStream,byteArrayOutputStream);
        byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        // 添加图片水印
        imageWaterMark(byteArrayInputStream,outputStream,image);
    }


    /**
     * pdf添加页码
     * @param inputStream
     * @param outputStream
     */
    public static void addPageCount(InputStream inputStream, OutputStream outputStream)
    {
        try
        {
            // step 1: create new reader
            PdfReader reader = new PdfReader(inputStream);
            Document document = new Document(reader.getPageSizeWithRotation(1));
            // step 2: 复制原pdf文件第一页创建一个新的pdf
            PdfCopy pdfNew = new PdfCopy(document, outputStream);

            // step 3: we open the document
            document.open();
            // step 4: we add content
            PdfImportedPage page = null;


            int pagesNum=0;//总页码
            int pages=0;//当前页码

            pagesNum+=reader.getNumberOfPages();
            PdfCopy.PageStamp stamp;//插入页码所需  不要页码可删除

            //loop through each page and if the bs is larger than 20 than we know it is not blank.
            //if it is less than 20 than we don't include that blank page.
            for (int i=1;i<=reader.getNumberOfPages();i++)
            {
                pages++;
                document.newPage();
                page = pdfNew.getImportedPage(reader, i);
                stamp=pdfNew.createPageStamp(page);//插入页码所需  不要页码可删除
                ColumnText.showTextAligned(stamp.getUnderContent(), Element.ALIGN_CENTER,new Phrase(addFont(String.format("第%d页/共%d页", pages, pagesNum),10f)),520f,10f,0f);//插入页码所需  不要页码可删除
                stamp.alterContents();//插入页码所需  不要页码可删除
                pdfNew.addPage(page);
            }

            //close everything
            document.close();
            pdfNew.close();
            reader.close();
        }
        catch(Exception e)
        {
            //do what you need here
        }
    }


    /**
     * pdf清除空白页
     * @param pdfSourceFile
     * @param outputStream
     */
    public static void removeBlankPdfPages(InputStream inputStream, OutputStream outputStream)
    {
        try
        {
            // step 1: create new reader
            PdfReader reader = new PdfReader(inputStream);
            Document document = new Document(reader.getPageSizeWithRotation(1));
            // step 2: 复制原pdf文件第一页创建一个新的pdf
            PdfCopy pdfNew = new PdfCopy(document, outputStream);

            // step 3: we open the document
            document.open();
            // step 4: we add content
            PdfImportedPage page = null;


            int pagesNum=0;//总页码
            int pages=0;//当前页码

            pagesNum+=reader.getNumberOfPages();
            PdfCopy.PageStamp stamp;//插入页码所需  不要页码可删除

            //loop through each page and if the bs is larger than 20 than we know it is not blank.
            //if it is less than 20 than we don't include that blank page.
            for (int i=1;i<=reader.getNumberOfPages();i++)
            {

                String textFromPage = PdfTextExtractor.getTextFromPage(reader, i);
                //get the page content
                //write the content to an output stream
                //add the page to the new pdf
                if (StringUtils.isNotBlank(textFromPage))
                {
                    page = pdfNew.getImportedPage(reader, i);
                    pdfNew.addPage(page);
                }
            }

            //close everything
            document.close();
            pdfNew.close();
            reader.close();
        }
        catch(Exception e)
        {
            //do what you need here
        }
    }

    public static Paragraph addFont(String content,float size) {
        BaseFont baseFont=null;
        try {
            try {
                baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        Font font=null;
        font=new Font(baseFont,size,Font.NORMAL);//设置字体
        return addText(content, font);
    }
    private static Paragraph addText(String content, Font font) {
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }


    /**
     * 给pdf文件加水印
     * @param inputStream 输入流
     * @param outputFile 输出文件路径
     * @param waterMarkName 水印内容
     * @return
     */
    public static void imageWaterMark(InputStream inputStream,OutputStream outputStream,Image image) {
        try {
            PdfReader reader = new PdfReader(inputStream);
            PdfStamper stamper = new PdfStamper(reader, outputStream);

            int total = reader.getNumberOfPages() + 1;
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.5f);
            PdfContentByte pdfContentByte;
            // 添加一个水印
            for (int i = 1; i < total; i++) {
                // 在内容上方加水印
                pdfContentByte = stamper.getOverContent(i);
                pdfContentByte.setGState(gs);
                pdfContentByte.addImage(image);
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(InputStream inputStream) throws Exception {
        byte[] bytes = toByteArray(inputStream);
        Image image = Image.getInstance(bytes);
        //设置图片水印的位置。
        image.setAbsolutePosition(10, 0);
        // 调整图片缩放
        image.scaleToFit(840,840);
        return image;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 给pdf文件加水印
     * @param inputFile 源文件路径
     * @param outputFile 输出文件路径
     * @param waterMarkName 水印内容
     * @return
     */
    public static void waterMark(String inputFile,String outputFile, String waterMarkName) {
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            Font font = new Font(Font.FontFamily.HELVETICA, 50, Font.BOLD, BaseColor.RED);
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);

            PdfGState gs = new PdfGState();
            //改透明度
            gs.setFillOpacity(0.5f);
            gs.setStrokeOpacity(0.4f);
            int total = reader.getNumberOfPages() + 1;

//            JLabel label = new JLabel();
//            label.setText(waterMarkName);

            PdfContentByte under;
            // 添加一个水印
            for (int i = 1; i < total; i++) {
                // 在内容上方加水印
//                under = stamper.getOverContent(i);
                //在内容下方加水印
                under = stamper.getUnderContent(i);
                gs.setFillOpacity(0.5f);
                under.setGState(gs);
                under.beginText();
                //改变颜色
                under.setColorFill(BaseColor.LIGHT_GRAY);
                //改水印文字大小
                under.setFontAndSize(base, 150);
                under.setTextMatrix(70, 200);
                //后3个参数，x坐标，y坐标，角度
                under.showTextAligned(Element.ALIGN_CENTER, waterMarkName, 300, 350, 55);

                under.endText();
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 标准字体
    public static Font NORMALFONT;
    // 加粗字体
    public static Font BOLDFONT;
    //固定高
    public static float fixedHeight = 27f;
    //间距
    public static int spacing = 5;

    static {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            NORMALFONT = new Font(bfChinese, 10, Font.NORMAL);
            BOLDFONT = new Font(bfChinese, 14, Font.BOLD);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建pdf
     * @return
     */
    public static Document createDocument() {
        //生成pdf
        Document document = new Document();
        // 页面大小
        Rectangle rectangle = new Rectangle(PageSize.A4);
        // 页面背景颜色
        rectangle.setBackgroundColor(BaseColor.WHITE);
        document.setPageSize(rectangle);
        // 页边距 左，右，上，下
        document.setMargins(20, 20, 20, 20);
        return document;
    }


    /**
     * @param text 段落内容
     * @return
     */
    public static Paragraph createParagraph(String text, Font font) {
        Paragraph elements = new Paragraph(text, font);
        elements.setSpacingBefore(5);
        elements.setSpacingAfter(5);
        elements.setSpacingAfter(spacing);
        return elements;
    }


    public static Font createFont(int fontNumber, int fontSize, BaseColor fontColor) {
        //中文字体 ----不然中文会乱码
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            return new Font(bf, fontNumber, fontSize, fontColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Font(bf, Font.DEFAULTSIZE, Font.NORMAL, BaseColor.BLACK);
    }

    /**
     * 隐藏表格边框线
     *
     * @param cell 单元格
     */
    public static void disableBorderSide(PdfPCell cell) {
        if (cell != null) {
            cell.disableBorderSide(1);
            cell.disableBorderSide(2);
            cell.disableBorderSide(4);
            cell.disableBorderSide(8);
        }
    }


    /**
     * 创建居中得单元格
     *
     * @return
     */
    public static PdfPCell createCenterPdfPCell() {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(fixedHeight);
        return cell;
    }

    /**
     * 创建指定文字得单元格
     *
     * @param text
     * @return
     */
    public static PdfPCell createCenterPdfPCell(String text, int rowSpan, int colSpan, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setFixedHeight(fixedHeight);
        cell.setRowspan(rowSpan);
        cell.setColspan(colSpan);
        return cell;
    }

    /**
     * @param len 表格列数
     * @return
     */
    public static PdfPTable createPdfPTable(int len) {
        PdfPTable pdfPTable = new PdfPTable(len);
        pdfPTable.setSpacingBefore(5);
        pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        return pdfPTable;
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
        response.setContentType("application/pdf");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");
        return response.getOutputStream();
    }

}

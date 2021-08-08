package com.midea.cloud.ureport.extension;

import com.alibaba.fastjson.JSONObject;
import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.definition.Alignment;
import com.bstek.ureport.definition.CellStyle;
import com.bstek.ureport.definition.Orientation;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.export.FullPageData;
import com.bstek.ureport.export.PageBuilder;
import com.bstek.ureport.export.pdf.CellBorderEvent;
import com.bstek.ureport.export.pdf.CellPhrase;
import com.bstek.ureport.export.pdf.PageHeaderFooterEvent;
import com.bstek.ureport.export.pdf.PdfProducer;
import com.bstek.ureport.model.Image;
import com.bstek.ureport.model.*;
import com.bstek.ureport.utils.ImageUtils;
import com.bstek.ureport.utils.UnitUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class ExtPdfProducer extends PdfProducer {


	/**
	 * 水印文本开启
	 */
	private static Boolean isBgTextEnable = false;

	/**
	 * 水印文本开启的模板名称
	 */
	@Value("${cloud.scc.ureport.bgtext.whitelist: }")
	private String bgTextWhitelist;

	/**
	 * 水印文本
	 */
	@Value("${cloud.scc.ureport.bgtext.value:Cloud-Srm}")
	private String bgText;

	/**
	 * 模板名称
	 */
	private String templateName;

	public String getTemplateName() {
		return templateName;
	}


	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	@Override
	public void produce(Report report,OutputStream outputStream) {
		report.getPaper().getBgImage();
		Paper paper=report.getPaper();
		int width=paper.getWidth();
		int height=paper.getHeight();
		Rectangle pageSize=new RectangleReadOnly(width,height);
		if(paper.getOrientation().equals(Orientation.landscape)){
			pageSize=pageSize.rotate();
		}
		int leftMargin=paper.getLeftMargin();
		int rightMargin=paper.getRightMargin();
		int topMargin=paper.getTopMargin();
		int bottomMargin=paper.getBottomMargin();
		Document document=new Document(pageSize,leftMargin,rightMargin,topMargin,bottomMargin);
		try{
			PdfWriter writer=PdfWriter.getInstance(document,outputStream);
			PageHeaderFooterEvent headerFooterEvent=new PageHeaderFooterEvent(report);
			writer.setPageEvent(headerFooterEvent);
			document.open();
			List<Column> columns=report.getColumns();
			List<Integer> columnsWidthList=new ArrayList<Integer>();
			int[] intArr=buildColumnSizeAndTotalWidth(columns,columnsWidthList);
			int colSize=intArr[0],totalWidth=intArr[1];
			int[] columnsWidth=new int[columnsWidthList.size()];
			for(int i=0;i<columnsWidthList.size();i++){
				columnsWidth[i]=columnsWidthList.get(i);
			}
			FullPageData pageData=PageBuilder.buildFullPageData(report);
			List<List<Page>> list=pageData.getPageList();
			if(list.size()>0){
				int columnCount=paper.getColumnCount();
				int w=columnCount*totalWidth+(columnCount-1)*paper.getColumnMargin();
				int size=columnCount+(columnCount-1);
				int[] widths=new int[size];
				for(int i=0;i<size;i++){
					int mode=(i+1)%2;
					if(mode==0){
						widths[i]=paper.getColumnMargin();
					}else{
						widths[i]=totalWidth;
					}
				}
				float tableHeight=pageSize.getHeight()-paper.getTopMargin()-paper.getBottomMargin();
				Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();

				int pageNum = 0;
				for(List<Page> pages:list){
					pageNum++;

					PdfPTable table=new PdfPTable(size);
					table.setLockedWidth(true);
					table.setTotalWidth(w);
					table.setWidths(widths);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					int ps=pages.size();
					for(int i=0;i<ps;i++){
						if(i>0){
							PdfPCell pdfMarginCell=new PdfPCell();
							pdfMarginCell.setBorder(Rectangle.NO_BORDER);
							table.addCell(pdfMarginCell);
						}
						Page page=pages.get(i);

						PdfPTable childTable=new PdfPTable(colSize);
						childTable.setLockedWidth(true);
						childTable.setTotalWidth(totalWidth);
						childTable.setWidths(columnsWidth);
						childTable.setHorizontalAlignment(Element.ALIGN_LEFT);
						List<Row> rows=page.getRows();
						for(Row row:rows){
							Map<Column,Cell> colMap=cellMap.get(row);
							if(colMap==null){
								continue;
							}
							for(Column col:columns){
								if(col.getWidth()<1){
									continue;
								}
								Cell cell=colMap.get(col);
								if(cell==null){
									continue;
								}
								int cellHeight=buildCellHeight(cell,rows);
								PdfPCell pdfcell=buildPdfPCell(cell,cellHeight);
								childTable.addCell(pdfcell);
							}
						}
						float childTableHeight=childTable.calculateHeights();
						if(tableHeight>childTableHeight){
							for(int j=0;j<columns.size();j++){
								PdfPCell lastCell=new PdfPCell();
								lastCell.setBorder(Rectangle.NO_BORDER);
								childTable.addCell(lastCell);
							}
						}
						PdfPCell pdfContainerCell=new PdfPCell(childTable);
						pdfContainerCell.setBorder(Rectangle.NO_BORDER);
						table.addCell(pdfContainerCell);
					}
					if(ps<columnCount){
						int left=columnCount-ps;
						for(int i=0;i<left;i++){
							PdfPCell pdfMarginCell=new PdfPCell();
							pdfMarginCell.setBorder(Rectangle.NO_BORDER);
							table.addCell(pdfMarginCell);
							pdfMarginCell=new PdfPCell();
							pdfMarginCell.setBorder(Rectangle.NO_BORDER);
							table.addCell(pdfMarginCell);
						}
					}

					//水印-文本
					if (isBgTextEnable) {
						writer.setPageEvent(new Watermark());
					}

					// 判断是否首页打印
					Map<String, Object> paramter = report.getContext().getParameters();
					Object isRepeat = paramter.get("isRepeat");
					if ("Y".equals(isRepeat)) {
						createBgNew(writer, report);
					} else {
						if (1 == pageNum) {
							createBgNew(writer, report);
						}
					}

					document.add(table);
					document.newPage();
				}
			}else{
				List<Page> pages=report.getPages();
				Map<Row, Map<Column, Cell>> cellMap=report.getRowColCellMap();

				int pageNum = 0;
				for(Page page : pages){
					pageNum++;

					PdfPTable table=new PdfPTable(colSize);
					table.setLockedWidth(true);
					table.setTotalWidth(totalWidth);
					table.setWidths(columnsWidth);
					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					List<Row> rows=page.getRows();
					for(Row row:rows){
						Map<Column,Cell> colMap=cellMap.get(row);
						if(colMap==null){
							continue;
						}
						for(Column col:columns){
							if(col.getWidth()<1){
								continue;
							}
							Cell cell=colMap.get(col);
							if(cell==null){
								continue;
							}
							int cellHeight=buildCellHeight(cell,rows);
							PdfPCell pdfcell=buildPdfPCell(cell,cellHeight);
							table.addCell(pdfcell);
						}
					}

					//水印-文本
					if (isBgTextEnable) {
						writer.setPageEvent(new Watermark());
					}

					// 判断是否首页打印
					Map<String, Object> paramter = report.getContext().getParameters();
					Object isRepeat = paramter.get("isRepeat");
					if ("Y".equals(isRepeat)) {
						createBgNew(writer, report);
					} else {
						if (1 == pageNum) {
							createBgNew(writer, report);
						}
					}

					document.add(table);
					document.newPage();
				}
			}
			document.close();
		}catch(Exception ex){
			throw new ReportComputeException(ex);
		}
	}


	/**
	 * 添加水印-文本
	 */
	private void createBgText(PdfWriter pdfWriter, Report report) {
		Map<String, Object> paramter = report.getContext().getParameters();
		try {
			//是否开启
			if( !this.isBgTextEnable ) {
				return;
			}
			//是否启用白名单
			boolean isPassWhitelist =false;
			if( this.bgTextWhitelist!=null && !"".equals(this.bgTextWhitelist.trim()) ) {
				//有设置白名单，则过滤
				String[] arrTemp =this.bgTextWhitelist.split(",");
				for( String s : arrTemp ) {
					if( this.templateName.indexOf(s)>-1 ) {
						isPassWhitelist =true;
					}
				}
			}else {
				//没有设置表示全部加水印
				isPassWhitelist =true;
			}
			if( !isPassWhitelist ) {
				return;
			}

			//优先取参数
			String bgText = (String)paramter.get("bgText");
			if (StringUtils.isBlank(bgText)) {
				//取配置文件的
				bgText =this.bgText;
			}


			bgText = URLDecoder.decode(bgText, "UTF-8");

			// 加入水印
			PdfContentByte pdfContentByte = pdfWriter.getDirectContentUnder();
			// 开始设置水印
			pdfContentByte.beginText();
			// 设置水印透明度
			PdfGState gs = new PdfGState();
			// 设置填充字体不透明度为0.4f
			gs.setFillOpacity(0.2f);
			try {
				// 设置水印字体参数及大小                                  (字体参数，字体编码格式，是否将字体信息嵌入到pdf中（一般不需要嵌入），字体大小)
				pdfContentByte.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 18);
				// 设置透明度
				pdfContentByte.setGState(gs);
				// 设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
				for(int i=0;i<10;i++) {
					for(int j=0;j<10;j++) {
						pdfContentByte.showTextAligned(Element.ALIGN_RIGHT, bgText , i*200+100, j*200+100, 45);
					}
				}
				// 设置水印颜色
				pdfContentByte.setColorFill(BaseColor.GRAY);
				//结束设置
				pdfContentByte.endText();
				pdfContentByte.stroke();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				pdfContentByte = null;
				gs = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 添加盖章
	 * @param pdfWriter
	 */
	private void createBgNew(PdfWriter pdfWriter, Report report) {

		Map<String, Object> paramter = report.getContext().getParameters();
		try {
			String bgUrl = "";
			int x;
			int y;
			float percent;
			bgUrl = (String)paramter.get("bgUrl");
			if (StringUtils.isBlank(bgUrl)) {
				return;
			}
			bgUrl = URLDecoder.decode(bgUrl, "UTF-8");
			x = paramter.get("x") == null ? 70 : Integer.parseInt(paramter.get("x").toString());
			y = paramter.get("y") == null ? 350 : Integer.parseInt(paramter.get("y").toString());
			percent = paramter.get("percent") == null ? 40 : Float.parseFloat(paramter.get("percent").toString());

			ResponseEntity<byte[]> imgBytes = new RestTemplate().getForEntity(bgUrl, byte[].class);
			PdfContentByte waterMarkPdfContent = pdfWriter.getDirectContentUnder();
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(imgBytes.getBody());
			img.setAbsolutePosition(x, y);
			img.scalePercent(percent);
			waterMarkPdfContent.addImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加水印
	 * @param pdfWriter
	 */
	protected void createBg(PdfWriter pdfWriter, Report repot) {
		try {
			String bgUrl = "";
			int x;
			int y;
			float percent;
			String watermark = (String)repot.getContext().getParameters().get("watermark");
			if (!StringUtils.isBlank(watermark)) {
				watermark = URLDecoder.decode(watermark, "UTF-8");
				JSONObject jsonObj = (JSONObject) JSONObject.parse(watermark);
				bgUrl = jsonObj.getString("bgUrl");
				if (StringUtils.isBlank(bgUrl)) {
					return;
				}
				x = jsonObj.get("x") == null ? 70 : jsonObj.getIntValue("x");
				y = jsonObj.get("x") == null ? 350 : jsonObj.getIntValue("x");
				percent = jsonObj.get("percent") == null ? 40 : jsonObj.getFloatValue("percent");
			} else {
				return;
			}

			ResponseEntity<byte[]> imgBytes = new RestTemplate().getForEntity(bgUrl, byte[].class);
			PdfContentByte waterMarkPdfContent = pdfWriter.getDirectContentUnder();
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(imgBytes.getBody());
			img.setAbsolutePosition(x, y);
			img.scalePercent(percent);
			waterMarkPdfContent.addImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int buildCellHeight(Cell cell,List<Row> rows){
		int height=cell.getRow().getRealHeight();
		int rowSpan=cell.getPageRowSpan();
		if(rowSpan>0){
			int pos=rows.indexOf(cell.getRow());
			int start=pos+1,end=start+rowSpan-1;
			for(int i=start;i<end;i++){
				height+=rows.get(i).getRealHeight();
			}
		}
		return height;
	}

	private PdfPCell buildPdfPCell(Cell cellInfo,int cellHeight) throws Exception{
		CellStyle style=cellInfo.getCellStyle();
		CellStyle customStyle=cellInfo.getCustomCellStyle();
		CellStyle rowStyle=cellInfo.getRow().getCustomCellStyle();
		CellStyle colStyle=cellInfo.getColumn().getCustomCellStyle();
		PdfPCell cell=newPdfCell(cellInfo,cellHeight);
		cell.setPadding(0);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setCellEvent(new CellBorderEvent(style,customStyle));
		int rowSpan=cellInfo.getPageRowSpan();
		if(rowSpan>0){
			cell.setRowspan(rowSpan);
		}
		int colSpan=cellInfo.getColSpan();
		if(colSpan>0){
			cell.setColspan(colSpan);
		}
		Alignment align=style.getAlign();
		if(customStyle!=null && customStyle.getAlign()!=null){
			align=customStyle.getAlign();
		}
		if(rowStyle!=null && rowStyle.getAlign()!=null){
			align=rowStyle.getAlign();
		}
		if(colStyle!=null && colStyle.getAlign()!=null){
			align=colStyle.getAlign();
		}
		if(align!=null){
			if(align.equals(Alignment.left)){
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			}else if(align.equals(Alignment.center)){
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			}else if(align.equals(Alignment.right)){
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			}
		}
		Alignment valign=style.getValign();
		if(customStyle!=null && customStyle.getValign()!=null){
			valign=customStyle.getValign();
		}
		if(rowStyle!=null && rowStyle.getValign()!=null){
			valign=rowStyle.getValign();
		}
		if(colStyle!=null && colStyle.getValign()!=null){
			valign=colStyle.getValign();
		}
		if(valign!=null){
			if(valign.equals(Alignment.top)){
				cell.setVerticalAlignment(Element.ALIGN_TOP);
			}else if(valign.equals(Alignment.middle)){
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}else if(valign.equals(Alignment.bottom)){
				cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			}
		}
		String bgcolor=style.getBgcolor();
		if(customStyle!=null && StringUtils.isNotBlank(customStyle.getBgcolor())){
			bgcolor=customStyle.getBgcolor();
		}
		if(rowStyle!=null && StringUtils.isNotBlank(rowStyle.getBgcolor())){
			bgcolor=rowStyle.getBgcolor();
		}
		if(colStyle!=null && StringUtils.isNotBlank(colStyle.getBgcolor())){
			bgcolor=colStyle.getBgcolor();
		}
		if(StringUtils.isNotEmpty(bgcolor)){
			String[] colors=bgcolor.split(",");
			cell.setBackgroundColor(new BaseColor(Integer.valueOf(colors[0]),Integer.valueOf(colors[1]),Integer.valueOf(colors[2])));
		}
		return cell;
	}

	private int[] buildColumnSizeAndTotalWidth(List<Column> columns,List<Integer> list){
		int count=0,totalWidth=0;
		for(int i=0;i<columns.size();i++){
			Column col=columns.get(i);
			int width=col.getWidth();
			if(width<1){
				continue;
			}
			count++;
			list.add(width);
			totalWidth+=width;
		}
		return new int[]{count,totalWidth};
	}

	private PdfPCell newPdfCell(Cell cellInfo,int cellHeight) throws Exception{
		PdfPCell cell=null;
		Object cellData=cellInfo.getFormatData();
		if(cellData instanceof Image){
			Image img=(Image)cellData;
			cell=new PdfPCell(buildPdfImage(img.getBase64Data(), 0, 0));
		}else if(cellData instanceof ChartData){
			ChartData chartData=(ChartData)cellData;
			String base64Data=chartData.retriveBase64Data();
			if(base64Data!=null){
				Image img=new Image(base64Data,chartData.getWidth(),chartData.getHeight());
				cell=new PdfPCell(buildPdfImage(img.getBase64Data(), 0, 0));
			}else{
				cell=new PdfPCell();
				CellPhrase pargraph=new CellPhrase(cellInfo,"");
				cell.setPhrase(pargraph);
				cell.setFixedHeight(cellHeight);
			}
		}else{
			cell=new PdfPCell();
			CellPhrase pargraph=new CellPhrase(cellInfo,cellData);
			cell.setPhrase(pargraph);
			cell.setFixedHeight(cellHeight);
		}
		CellStyle style=cellInfo.getCellStyle();
		if(style!=null && style.getLineHeight()>0){
			cell.setLeading(style.getLineHeight(), style.getLineHeight());
		}
		return cell;
	}
	private com.itextpdf.text.Image buildPdfImage(String base64Data, int width,int height) throws Exception{
		com.itextpdf.text.Image pdfImg=null;
		InputStream input=ImageUtils.base64DataToInputStream(base64Data);
		try{
			byte[] bytes=IOUtils.toByteArray(input);
			pdfImg=com.itextpdf.text.Image.getInstance(bytes);
			float imgWidth=pdfImg.getWidth();
			float imgHeight=pdfImg.getHeight();
			if(width==0){
				width=Float.valueOf(imgWidth).intValue();
			}
			if(height==0){
				height=Float.valueOf(imgHeight).intValue();
			}
			width=UnitUtils.pixelToPoint(width-2);
			height=UnitUtils.pixelToPoint(height-2);
			pdfImg.scaleToFit(width,height);
		}finally{
			IOUtils.closeQuietly(input);
		}
		return pdfImg;
	}

	class Watermark extends PdfPageEventHelper{
		Font FONT = new Font(Font.FontFamily.HELVETICA, 50, Font.BOLD, BaseColor.RED);
		private String waterCont;//水印内容
		public Watermark() {

		}
		public Watermark(Object waterCont) {
			if (null != waterCont) {
				this.waterCont = waterCont.toString();
			}
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte pdfContentByte = writer.getDirectContentUnder();

			try {
				// 获取图片
				InputStream inputStream = this.getClass().getResourceAsStream("/image/水印.png");
				byte[] bytes = toByteArray(inputStream);
				com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(bytes);// 插入水印
				//设置图片水印的位置。
				img.setAbsolutePosition(0, 0);
				img.scaleToFit(864,612);
				pdfContentByte.addImage(img);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
//			// 设置水印透明度
//			PdfGState gs = new PdfGState();
//			// 设置填充字体不透明度为0.2f
//			gs.setFillOpacity(0.2f);
//			pdfContentByte.setGState(gs);
//			for(int i=0 ; i<5; i++) {
//				for(int j=0; j<5; j++) {
//					ColumnText.showTextAligned(pdfContentByte,
//							Element.ALIGN_CENTER,
//							new Phrase(this.waterCont == null ? "LONGI" : this.waterCont, FONT),
//							(60.5f+i*250),
//							(40.0f+j*150),
//							writer.getPageNumber() % 2 == 1 ? 45 : -45);
//				}
//			}
		}

		public byte[] toByteArray(InputStream input) throws IOException {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*4];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return output.toByteArray();
		}
	}

}

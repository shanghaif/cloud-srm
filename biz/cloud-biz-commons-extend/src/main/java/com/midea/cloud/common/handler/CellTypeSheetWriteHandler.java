package com.midea.cloud.common.handler;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import lombok.Data;

/**
 * 
 * <pre>
 * 指定excel单元格格式
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: May 22, 20202:17:53 PM 
 *  修改内容:
 *          </pre>
 */
@Data
public class CellTypeSheetWriteHandler implements SheetWriteHandler {

	private Class<?> clazz;

	public CellTypeSheetWriteHandler() {

	}

	public CellTypeSheetWriteHandler(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

	}

	@Override
	public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
		if (clazz == null) {
			return;
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		Sheet sheet = writeSheetHolder.getSheet();
		for (Field f : declaredFields) {
			CellStyle textStyle = writeWorkbookHolder.getWorkbook().createCellStyle();
			DataFormat format = writeWorkbookHolder.getWorkbook().createDataFormat();
			ExcelProperty annotation = f.getAnnotation(ExcelProperty.class);
			if (annotation != null && annotation.index() > -1) {
				if (f.getType() == String.class) {
					textStyle.setDataFormat(format.getFormat("@"));
					sheet.setDefaultColumnStyle(annotation.index(), textStyle);
				} else if (f.getType() == Date.class) {
					String formatStr = "yyyy-MM-dd";
					DateTimeFormat dateTimeFormat = f.getAnnotation(DateTimeFormat.class);
					if (dateTimeFormat != null && dateTimeFormat.value() != null) {
						formatStr = dateTimeFormat.value();
					}
					textStyle.setDataFormat(format.getFormat(formatStr));
					sheet.setDefaultColumnStyle(annotation.index(), textStyle);
				} else if (f.getType() == int.class || f.getType() == Integer.class || f.getType() == long.class || f.getType() == Long.class) {
					textStyle.setDataFormat(format.getFormat("#,##0"));
					sheet.setDefaultColumnStyle(annotation.index(), textStyle);
				}
			}
		}
	}

}

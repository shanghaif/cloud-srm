package com.midea.cloud.common.handler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.List;
import java.util.Set;

/**
 * <pre>
 *  操作标题行单元格颜色
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-28 9:29
 *  修改内容:
 * </pre>
 */
@Slf4j
public class TitleColorSheetWriteHandler implements CellWriteHandler {

    //操作行
    private List<Integer> columnIndexs;
    //操作列
    private List<Integer> rowIndexs;
    //颜色
    private Short colorIndex;

    //构造
    public TitleColorSheetWriteHandler(List<Integer> rowIndexs, List<Integer> columnIndexs, Short colorIndex) {
        this.rowIndexs = rowIndexs;
        this.columnIndexs = columnIndexs;
        this.colorIndex = colorIndex;
    }

    public TitleColorSheetWriteHandler() {
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if( 0 == cell.getRowIndex()){
            // 设置列宽
            Sheet sheet = writeSheetHolder.getSheet();
            sheet.setColumnWidth(cell.getColumnIndex(), 14 * 256);
            writeSheetHolder.getSheet().getRow(0).setHeight((short)(1.8*256));

            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();

            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontName("宋体");
            headWriteFont.setFontHeightInPoints((short)14);
            headWriteFont.setBold(true);
            headWriteCellStyle.setWriteFont(headWriteFont);
            headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            if (CollectionUtils.isNotEmpty(columnIndexs) &&
                    CollectionUtils.isNotEmpty(rowIndexs) &&
                    colorIndex != null &&
                    cell.getRowIndex() == 0 &&
                    columnIndexs.contains(cell.getColumnIndex())) {
                // 设置字体颜色
                headWriteFont.setColor(colorIndex);
            }
            CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle);
            cell.setCellStyle(cellStyle);
        }
    }
}

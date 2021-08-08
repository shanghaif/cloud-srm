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

import java.util.List;
import java.util.Set;

/**
 * <pre>
 *  操作内容行单元格颜色
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
public class CellColorSheetWriteHandler implements CellWriteHandler {

    //操作行
    private List<Integer> columnIndexs;
    //操作列
    private List<Integer> rowIndexs;
    //颜色
    private Short colorIndex;

    //构造
    public CellColorSheetWriteHandler(List<Integer> rowIndexs, List<Integer> columnIndexs, Short colorIndex) {
        this.rowIndexs = rowIndexs;
        this.columnIndexs = columnIndexs;
        this.colorIndex = colorIndex;
    }

    public CellColorSheetWriteHandler() {
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if(0 != cell.getRowIndex()){
            Workbook workbook = cell.getSheet().getWorkbook();
            writeSheetHolder.getSheet().getRow(cell.getRowIndex()).setHeight((short)(1.4*256));
            // 单元格策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            // 设置背景颜色白色
            contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
            contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
            contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
            WriteFont cellWriteFont = new WriteFont();
            cellWriteFont.setFontHeightInPoints((short)12);
            if (CollectionUtils.isNotEmpty(columnIndexs) &&
                    CollectionUtils.isNotEmpty(rowIndexs) &&
                    colorIndex != null &&
                    rowIndexs.contains(cell.getRowIndex()) &&
                    columnIndexs.contains(cell.getColumnIndex())) {
                // 设置字体颜色
                cellWriteFont.setColor(colorIndex);
            }
            contentWriteCellStyle.setWriteFont(cellWriteFont);
            CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, contentWriteCellStyle);
            cell.setCellStyle(cellStyle);
        }
    }
}

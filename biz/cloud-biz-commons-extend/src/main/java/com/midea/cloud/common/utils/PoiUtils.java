package com.midea.cloud.common.utils;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/24
 *  修改内容:
 * </pre>
 */
public class PoiUtils {
    // 根据单元格的格式获取值
    public static String getCellValue(Cell cell) {
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
}

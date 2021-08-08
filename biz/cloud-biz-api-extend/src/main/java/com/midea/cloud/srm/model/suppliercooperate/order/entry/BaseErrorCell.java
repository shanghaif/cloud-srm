package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import jxl.Cell;
import jxl.DateCell;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <pre>
 *  记录导入execl单元格坐标
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/24 10:20
 *  修改内容:
 * </pre>
 */
@Data
public class BaseErrorCell extends BaseEntity {
    /**
     * 对象的唯一标识
     */
    @TableField(exist = false)
    private String objectKey;

    /**
     * 通过对象 objectKey+fieldName 反向找到文本对应的单元格位置
     */
    @TableField(exist = false)
    private Map<String,ErrorCell> errorCellMap;

    public BaseErrorCell(){
        this.objectKey = UUID.randomUUID().toString();  //生成对象的唯一标识
        this.errorCellMap = new HashMap<>();
    }

    /**
     * 给对象属性添加报错提醒信息对象
     * @param fieldName
     * @param errorCell
     */
    public void addErrorCell(String fieldName,ErrorCell errorCell){
        this.errorCellMap.put(this.objectKey+fieldName,errorCell);
    }

    /**
     * 给对象属性添加报错提醒信息对象
     * @param addFlag
     * @param fieldName
     * @param errorCell
     */
    public void addErrorCell(boolean addFlag,String fieldName,ErrorCell errorCell){
        if(addFlag){
            this.errorCellMap.put(this.objectKey+fieldName,errorCell);
        }
    }

    /**
     * 给对象属性添加报错提醒信息对象
     * @param fieldName 属性名
     * @param comment 属性名称
     * @param cell 单元格
     * @param sheetIndex sheet下标
     * @param notNull 是否不能为空
     */
    public void addErrorCell(String fieldName, String comment, Cell cell,Integer sheetIndex, Boolean notNull){
        this.addErrorCell(fieldName, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), null));
        if(notNull){
            if(StringUtils.isBlank(cell.getContents())){
                this.addErrorCell(fieldName, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), comment+"不能为空"));
            }
        }

        if(StringUtils.isNotBlank(cell.getContents())){
            Class<?> forName = this.getClass();
            try {
                Field field = forName.getDeclaredField(fieldName);
                field.setAccessible(true);
                String fieldType = field.getGenericType().toString();

                switch (fieldType){
                    case "class java.util.Date":
                        Date date = getDateByStr(cell);
                        if(date==null){
                            this.addErrorCell(fieldName, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), comment+"格式错误"));
                        }else{
                            field.set(this, date);
                        }
                        break;
                    case "class java.lang.Integer":
                        if(!checkPositiveInt(cell.getContents())){
                            this.addErrorCell(fieldName, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), comment+"格式错误"));
                        }else{
                            field.set(this, new Integer(cell.getContents()));
                        }
                        break;
                    case "class java.lang.Long":
                        if(!checkPositiveLong(cell.getContents())) {
                            this.addErrorCell(fieldName, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), comment + "格式错误"));
                        }else {
                            field.set(this, new Long(cell.getContents()));
                        }
                        break;
                    case "class java.lang.Double":
                        if(!checkPositiveNumber(cell.getContents())){
                            this.addErrorCell(fieldName, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), comment+"格式错误"));
                        }else{
                            field.set(this, new Double(cell.getContents()));
                        }
                        break;
                    case "class java.math.BigDecimal":
                        if(!checkPositiveNumber(cell.getContents())){
                            this.addErrorCell(fieldName, new ErrorCell(sheetIndex, cell.getColumn(), cell.getRow(), comment+"格式错误"));
                        }else{
                            field.set(this, new BigDecimal(cell.getContents()));
                        }
                        break;
                    default:
                        field.set(this, cell.getContents());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过属性名获取属性对应excel所在单元格的报错信息对象
     * @param fieldName
     * @return
     */
    public ErrorCell getErrorCell(String fieldName){
        if(errorCellMap!=null){
            return errorCellMap.get(this.objectKey+fieldName);
        }
        return null;
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
     * 检查文本是否是Long型字符串
     *
     * @param text
     * @return
     */
    public static boolean checkPositiveLong(String text) {
        try {
            Long number = new Long(text);
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

    // 单元格数据格式校验 <<<<<

}

package com.midea.cloud.srm.model.common.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <pre>
 *  读取excel的String类型字段(如物料编码)时:
 *  若该字段为全数字组成,数字后会加上".0",导致数据错乱
 *  在需要转换字段的注解属性里加上该转换器可解决此问题
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-22 9:44
 *  修改内容:
 * </pre>
 */
@Slf4j
public class ExcelNumToStringConverter implements Converter<String> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读的时候会调用
     *
     * @param cellData            NotNull
     * @param contentProperty     Nullable
     * @param globalConfiguration NotNull
     * @return
     */
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        String value = String.valueOf(cellData);
        if(value.contains(".")) {
            //pattern:'#'号意思是若此位数等于0则为空
            DecimalFormat format = new DecimalFormat("###################.################");
            return format.format(new BigDecimal(value));
        }else {
            return value;
        }
    }

    /**
     * 这里是写的时候会调用 不用管
     *
     * @param value               NotNull
     * @param contentProperty     Nullable
     * @param globalConfiguration NotNull
     * @return
     */
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        return new CellData(value);
    }

}

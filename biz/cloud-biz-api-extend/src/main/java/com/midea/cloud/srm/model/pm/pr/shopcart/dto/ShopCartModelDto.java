package com.midea.cloud.srm.model.pm.pr.shopcart.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *  购物车导入需求行excel模型对象
 * </pre>
 *
 * @author dengyl23@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/26 13:31
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(150)
@HeadRowHeight(60)
public class ShopCartModelDto implements Serializable {

    @ExcelProperty(value = "采购类型(可维护字段同购物车页面选择字段)", index = 0)
    private String purchaseType;

    @ExcelProperty(value = "需求时间(示例：2020/09/08，必填)", index = 1)
    private String requirementDate;

    @ExcelProperty(value = "数量(示例：1000，需大于等于最小起订量，必填)", index = 2)
    private String requirementNum;

    @ExcelProperty(value = "物料编码(必填)", index = 3)
    private String materialCode;

    @ExcelProperty(value = "物料描述(必填)", index = 4)
    private String materialName;

    @ExcelProperty(value = "品类(输入小类名称，必填)", index = 5)
    private String categoryName;

    @ExcelProperty(value = "业务实体(必填)", index = 6)
    private String orgName;

    @ExcelProperty(value = "库存组织(必填)", index = 7)
    private String organizationName;
    /**
     * 序号
     */
    @ExcelIgnore
    private Integer rowId;

}

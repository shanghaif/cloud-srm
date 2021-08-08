package com.midea.cloud.srm.model.pm.pr.shopcart.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

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
@ColumnWidth(20)
public class ShopCartImportModelDto implements Serializable {

    @ExcelProperty(value = "是否目录化(N/Y)", index = 0)
    private String isDirectory;

    @ExcelProperty(value = "*采购类型", index = 1)
    private String purchaseType;

    @ExcelProperty(value = "*需求时间", index = 2)
    private String requirementDate;

    @ExcelProperty(value = "*数量", index = 3)
    private String requirementNum;

    @ExcelProperty(value = "*物料编码", index = 4)
    private String materialCode;

    @ExcelProperty(value = "*物料描述", index = 5)
    private String materialName;

    @ExcelProperty(value = "*业务实体", index = 6)
    private String orgName;

    @ExcelProperty(value = "*库存组织", index = 7)
    private String organizationName;

    @ExcelProperty(value = "*供应商编码", index = 8)
    private String supplierCode;

    @ExcelProperty(value = "错误信息提示", index = 9)
    private String errorMsg;

}

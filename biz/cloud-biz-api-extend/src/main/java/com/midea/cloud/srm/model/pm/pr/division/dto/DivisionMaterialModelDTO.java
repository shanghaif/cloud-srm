package com.midea.cloud.srm.model.pm.pr.division.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  功能名称  品类分工规则EXCEL模型
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/24 8:30
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class DivisionMaterialModelDTO implements Serializable {

    private static final long serialVersionUID = 525681281551782880L;

    /**
     * 业务实体
     */
    @ExcelProperty(value = "业务实体", index = 0)
    private String orgName;


    /**
     * 库存组织名称
     */
    @ExcelProperty(value = "库存组织", index = 1)
    private String organizationName;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码", index = 2)
    private String materialCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称", index = 3)
    private String materialName;

    /**
     * 供应商管理采购员名称
     */
    @ExcelProperty(value = "供应商管理", index = 4)
    private String supUserNickname;

    /**
     * 策略负责采购员名称
     */
    @ExcelProperty(value = "策略负责", index = 5)
    private String strategyUserNickname;

    /**
     * 采购履行采购员名称
     */
    @ExcelProperty(value = "采购履行", index = 6)
    private String performUserNickname;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "生效日期", index = 7)
    private String startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "失效日期", index = 8)
    private String endDate;

    /**
     * 数据行号
     */
    @ExcelIgnore
    private int row;

}

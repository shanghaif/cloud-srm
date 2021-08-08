package com.midea.cloud.srm.model.pm.pr.division.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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
@HeadRowHeight(-10)
public class DivisionCategoryModelDTO implements Serializable {

    private static final long serialVersionUID = 525681281551782880L;

    /**
     * 业务实体
     */
    @ExcelProperty(value = "业务实体", index = 0)
    @NotEmpty(message = "业务实体名不能为空")
    private String orgName;


    /**
     * 库存组织名称
     */
    @ExcelProperty(value = "库存组织", index = 1)
    @NotEmpty(message = "库存组织名不能为空")
    private String organizationName;

    /**
     * 品类编码(物料小类编码)
     */
    @ExcelProperty(value = "物料小类编码", index = 2)
    @NotEmpty(message = "物料小类编码不能为空")
    private String categoryCode;

    /**
     * 品类名称(物料小类名称)
     */
    @ExcelProperty(value = "物料小类名称", index = 3)
    @NotEmpty(message = "物料小类名称不能为空")
    private String categoryName;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "生效日期", index = 8)
    @NotEmpty(message = "生效日期不能为空")
    private String startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "失效日期", index = 9)
    private String endDate;

    /**
     * 数据行号
     */
    @ExcelIgnore
    private int row;

    /**
     * 职责
     */
    @ExcelProperty(value = "职责",index = 5)
    @NotEmpty(message = "职责不能为空")
    private String duty;

    /**
     * 负责人名称
     */
    @ExcelProperty(value = "负责人名称",index =6)
    private String personInChargeNickname;

    /**
     * 负责人用户名
     */
    @ExcelProperty(value = "负责人工号",index =7)
    @NotEmpty(message = "负责人工号不能为空")
    private String erpNum;


    /**
     * 是否主负责人
     */
    @ExcelProperty(value = "是否主要负责人(Y/N)",index = 4)
    @NotEmpty(message = "是否为主要负责人不能为空")
    private String ifMainPerson;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示",index = 10)
    private String errorMsg;

    @ExcelProperty(value = "更新人",index = 11)
    private String lastUpdatedBy;
    @ExcelProperty(value = "最后更新时间",index = 12)
    private Date lastUpdateDate;
}

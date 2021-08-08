package com.midea.cloud.srm.model.supplier.dim.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BasePage;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  维度字段表DTO
 * </pre>
 *
 * @author  zhuwl7
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/2/19 14:26
 *  修改内容:
 * </pre>
 */
@Data
public class DimFieldDTO extends BasePage {

    /**
     * 属性ID
     */
    private Long fieldId;

    /**
     * 维度ID
     */
    private Long dimId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 属性
     */
    private String fieldName;

    /**
     * 类型编码
     */
    private String fieldTypeCode;

    /**
     * 类型名称
     */
    private String fieldTypeName;

    /**
     * 属性编码
     */
    private String fieldCode;

    /**
     * 是否拓展字段(是Y,否N)
     */
    private String isField;

    /**
     * 附件大小限制
     */
    private String fileSize;

    /**
     * 附件格式限制
     */
    private String fileType;

    /**
     * 长度限制
     */
    private Integer fieldLength;

    /**
     * 顺序
     */
    private Integer fieldOrderNum;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建日期
     */
    private Date creationDate;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 属性维度名称
     */
    private String dimName;

    /**
     * 属性维度编码
     */
    private String dimCode;


}

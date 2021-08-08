package com.midea.cloud.srm.model.supplier.dim.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-3 16:38
 *  修改内容:
 * </pre>
 */
@Data
public class DimFieldConfigDTO {
    /**
     * 属性ID
     */
    private Long fieldConfigId;

    /**
     * 维度ID
     */
    private Long dimConfigId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 字段ID
     */
    private Long fieldId;

    /**
     * 属性
     */
    private String fieldName;


    /**
     * code
     */
    private String fieldCode;

    /**
     * 类型编码
     */
    private String fieldTypeCode;

    /**
     * 类型编码
     */
    private String fieldTypeName;

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
     * 字段别名
     */
    private String fieldNickName;

    /**
     * 是否显示(是Y,否N)
     */
    private String isUse;

    /**
     * 是否校验(是Y,否N)
     */
    private String isCheck;

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
}

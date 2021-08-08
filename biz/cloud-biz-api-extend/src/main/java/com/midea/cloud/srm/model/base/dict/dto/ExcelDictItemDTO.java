package com.midea.cloud.srm.model.base.dict.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
@Data
@Accessors(chain = true)
public class ExcelDictItemDTO {
    /**
     * ID
     */
    private Long dictItemId;

    /**
     * 字典ID
     */
    private Long dictId;

    /**
     * 条目编码
     */
    private String dictItemCode;

    /**
     * 条目语言
     */
    private String itemLanguage;

    /**
     * 条目语言名称
     */
    private String itemLanguageName;

    /**
     * 条目名称
     */
    private String dictItemName;

    /**
     * 条目描述
     */
    private String itemDescription;

    /**
     * 条目顺序
     */
    private Integer dictItemNo;

    /**
     * 条目标识
     */
    private String dictItemMark;

    /**
     * 生效日期
     */
    private Date activeDate;

    /**
     * 失效日期
     */
    private Date inactiveDate;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    private String lastUpdatedBy;

    /**
     * 更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 字典语言
     */
    private String language;

    /**
     * 字典语言名称
     */
    private String languageName;

    /**
     * 是否默认
     */
    private Boolean isDefault = false;

}

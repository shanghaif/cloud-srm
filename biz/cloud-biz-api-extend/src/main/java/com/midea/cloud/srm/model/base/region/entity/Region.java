package com.midea.cloud.srm.model.base.region.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  国家地区表 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 10:15:09
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_region")
public class Region extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("REGION_ID")
    private Long regionId;

    /**
     * 区域名称
     */
    @TableField("AREA_NAME")
    private String areaName;

    /**
     * 父栏目
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 简称
     */
    @TableField("SHORT_NAME")
    private String shortName;

    /**
     * 区域编码
     */
    @TableField("AREA_CODE")
    private String areaCode;

    @TableField("ZIP_CODE")
    private String zipCode;

    @TableField("PING_YIN")
    private String pingYin;

    /**
     * 经度
     */
    @TableField("ING")
    private String ing;

    /**
     * 维度
     */
    @TableField("LAT")
    private String lat;

    @TableField("LEVEL_NO")
    private Integer levelNo;

    @TableField("POSITION")
    private String position;

    /**
     * 排序
     */
    @TableField("SORT")
    private BigDecimal sort;

    /**
     * SAP地区编码(CuxKK)
     */
    @TableField("SAP_AREA_CODE")
    private String sapAreaCode;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 父区域名称
     */
    @TableField(exist = false)
    private String parentAreaName;

}

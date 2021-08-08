package com.midea.cloud.srm.model.logistics.po.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
*  <pre>
 *  物流采购订单行船期表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-11 11:21:42
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_order_line_ship")
public class OrderLineShip extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ORDER_LINE_SHIP_ID")
    private Long orderLineShipId;

    /**
     * 采购订单头表ID
     */
    @TableField("ORDER_HEAD_ID")
    private Long orderHeadId;

    /**
     * 采购订单编号(订单编号)
     */
    @TableField("ORDER_HEAD_NUM")
    private String orderHeadNum;

    /**
     * 订单行号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * 船期ID
     */
    @TableField("SHIP_ID")
    private Long shipId;

    /**
     * 起运港ID
     */
    @TableField("FROM_PORT_ID")
    private Long fromPortId;

    /**
     * 起运港编码
     */
    @TableField("FROM_PORT_CODE")
    private String fromPortCode;

    /**
     * 起运港名称
     */
    @TableField("FROM_PORT")
    private String fromPort;

    /**
     * 目的港ID
     */
    @TableField("TO_PORT_ID")
    private Long toPortId;

    /**
     * 目的港编码
     */
    @TableField("TO_PORT_CODE")
    private String toPortCode;

    /**
     * 目的港名称
     */
    @TableField("TO_PORT")
    private String toPort;

    /**
     * FCL/LCL, 整柜/拼柜
     */
    @TableField("WHOLE_ARK")
    private String wholeArk;

    /**
     * Mon
     */
    @TableField("MON")
    private BigDecimal mon;

    /**
     * Tue
     */
    @TableField("TUE")
    private BigDecimal tue;

    /**
     * Wed
     */
    @TableField("WED")
    private BigDecimal wed;

    /**
     * Thu
     */
    @TableField("THU")
    private BigDecimal thu;

    /**
     * Fri
     */
    @TableField("FRI")
    private BigDecimal fri;

    /**
     * Sat
     */
    @TableField("SAT")
    private BigDecimal sat;

    /**
     * Sun
     */
    @TableField("SUN")
    private BigDecimal sun;

    /**
     * 中转时间
     */
    @TableField("TRANSIT_TIME")
    private BigDecimal transitTime;

    /**
     * 船公司
     */
    @TableField("SHIP_COMPANY_NAME")
    private String shipCompanyName;

    /**
     * 中转港
     */
    @TableField("TRANSFER_PORT")
    private String transferPort;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

    /**
     * 备用字段6
     */
    @TableField("ATTR6")
    private String attr6;

    /**
     * 备用字段7
     */
    @TableField("ATTR7")
    private String attr7;

    /**
     * 备用字段8
     */
    @TableField("ATTR8")
    private String attr8;

    /**
     * 备用字段9
     */
    @TableField("ATTR9")
    private String attr9;

    /**
     * 备用字段10
     */
    @TableField("ATTR10")
    private String attr10;

    /**
     * 备用字段11
     */
    @TableField("ATTR11")
    private String attr11;

    /**
     * 备用字段12
     */
    @TableField("ATTR12")
    private String attr12;

    /**
     * 备用字段13
     */
    @TableField("ATTR13")
    private String attr13;

    /**
     * 备用字段14
     */
    @TableField("ATTR14")
    private String attr14;

    /**
     * 备用字段15
     */
    @TableField("ATTR15")
    private String attr15;

    /**
     * 来源系统（如：erp系统）
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 起运国
     */
    @TableField("FROM_COUNTRY")
    private String fromCountry;

    /**
     * 起运国编码
     */
    @TableField("FROM_COUNTRY_CODE")
    private String fromCountryCode;

    /**
     * 始发省
     */
    @TableField("FROM_PROVINCE")
    private String fromProvince;

    /**
     * 始发省编码
     */
    @TableField("FROM_PROVINCE_CODE")
    private String fromProvinceCode;

    /**
     * 始发市
     */
    @TableField("FROM_CITY")
    private String fromCity;

    /**
     * 始发市编码
     */
    @TableField("FROM_CITY_CODE")
    private String fromCityCode;

    /**
     * 始发区县
     */
    @TableField("FROM_COUNTY")
    private String fromCounty;


    /**
     * 始发区县编码
     */
    @TableField("FROM_COUNTY_CODE")
    private String fromCountyCode;

    /**
     * 起运地
     */
    @TableField("FROM_PLACE")
    private String fromPlace;

    /**
     * 起运地编码
     */
    @TableField("FROM_PLACE_CODE")
    private String fromPlaceCode;

    /**
     * 目的国
     */
    @TableField("TO_COUNTRY")
    private String toCountry;

    /**
     * 目的国编码
     */
    @TableField("TO_COUNTRY_CODE")
    private String toCountryCode;

    /**
     * 目的省
     */
    @TableField("TO_PROVINCE")
    private String toProvince;

    /**
     * 目的省编码
     */
    @TableField("TO_PROVINCE_CODE")
    private String toProvinceCode;

    /**
     * 目的市
     */
    @TableField("TO_CITY")
    private String toCity;

    /**
     * 目的市编码
     */
    @TableField("TO_CITY_CODE")
    private String toCityCode;

    /**
     * 目的区县
     */
    @TableField("TO_COUNTY")
    private String toCounty;

    /**
     * 目的区县编码
     */
    @TableField("TO_COUNTY_CODE")
    private String toCountyCode;


    /**
     * 目的地
     */
    @TableField("TO_PLACE")
    private String toPlace;


    /**
     * 目的地编码
     */
    @TableField("TO_PLACE_CODE")
    private String toPlaceCode;

    /**
     * 增加字段, 陆运和铁运专用
     * 车数+兆瓦数+是否满足+可满足情况（否时必填）+特殊说明+备注+评标结论（采购商填）
     */
    /**
     * 车数
     */
    @TableId("CHAR_NUM")
    private BigDecimal charNum;
    /**
     * 兆瓦数
     */
    @TableId("MEGAWATT")
    private BigDecimal megawatt;
    /**
     * 是否满足
     */
    @TableId("IF_SATISFIED")
    private String ifSatisfied;
    /**
     * 可满足情况（是否满足为否时必填）
     */
    @TableId("SATISFIABLE_SITUATION")
    private String satisfiableSituation;
    /**
     * 特殊说明
     */
    @TableId("SPECIAL_INSTRUCTIONS")
    private String specialInstructions;
    /**
     * 备注
     */
    @TableId("REMARKS")
    private String remarks;
    /**
     * 评标结论（采购商填）
     */
    @TableId("BID_RESULT")
    private String bidResult;

}

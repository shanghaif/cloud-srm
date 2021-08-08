package com.midea.cloud.srm.model.logistics.po.order.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  物流采购订单行表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:51:00
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_order_line")
public class OrderLine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ORDER_LINE_ID")
    private Long orderLineId;

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
     * 单公里成本
     */
    @TableField("SINGLE_KM_COST")
    private BigDecimal singleKmCost;

    /**
     * 单拖成本
     */
    @TableField("SINGLE_DRAG_COST")
    private BigDecimal singleDragCost;

    /**
     * 进出口方式
     */
    @TableField("IMPORT_EXPORT_METHOD")
    private String importExportMethod;

    /**
     * LEG
     */
    @TableField("LEG")
    private String leg;

    /**
     * 超期用箱费
     */
    @TableField("BEYOND_BOX_COST")
    private BigDecimal beyondBoxCost;

    /**
     * 超期堆存费
     */
    @TableField("BEYOND_STORAGE_COST")
    private BigDecimal beyondStorageCost;

    /**
     * 免箱期
     */
    @TableField("FREE_BOX_PERIOD")
    private String freeBoxPeriod;

    /**
     * 免堆期
     */
    @TableField("FREE_STORAGE_PERIOD")
    private String freeStoragePeriod;

    /**
     * 船期频率
     */
    @TableField("SHIP_DATE_FREQUENCY")
    private String shipDateFrequency;

    /**
     * 整柜/拼柜
     */
    @TableField("WHOLE_ARK")
    private String wholeArk;

    /**
     * 贸易术语
     */
    @TableField("TRADE_TERM")
    private String tradeTerm;

    /**
     * 指定供应商
     */
    @TableField("SPECIFIED_VENDOR")
    private String specifiedVendor;

    /**
     * 费用
     */
    @TableField("EXPENSE")
    private BigDecimal expense;

    /**
     * 计费单位
     */
    @TableField("CHARGE_UNIT")
    private String chargeUnit;

    /**
     * 计费方式
     */
    @TableField("CHARGE_METHOD")
    private String chargeMethod;

    /**
     * 费项
     */
    @TableField("EXPENSE_ITEM")
    private String expenseItem;

    /**
     * 免费存放期
     */
    @TableField("FREE_STAY_PERIOD")
    private String freeStayPeriod;

    /**
     * 船期
     */
    @TableField("SHIP_DATE")
    private String shipDate;

    /**
     * 全程时效
     */
    @TableField("FULL_REAL_TIME")
    private String fullRealTime;

    /**
     * 提前预定时间
     */
    @TableField("RESERVE_TIME")
    private String reserveTime;

    /**
     * 目的港名称
     */
    @TableField("TO_PORT")
    private String toPort;

    /**
     * 目的港编码
     */
    @TableField("TO_PORT_CODE")
    private String toPortCode;

    /**
     * 起运港名称
     */
    @TableField("FROM_PORT")
    private String fromPort;

    /**
     * 起运港编码
     */
    @TableField("FROM_PORT_CODE")
    private String fromPortCode;

    /**
     * 时效
     */
    @TableField("REAL_TIME")
    private String realTime;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 运输距离
     */
    @TableField("TRANSPORT_DISTANCE")
    private String transportDistance;

    /**
     * 币制
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 最大收费
     */
    @TableField("MAX_COST")
    private String maxCost;

    /**
     * 最小收费
     */
    @TableField("MIN_COST")
    private String minCost;

    /**
     * 是否往返
     */
    @TableField("IF_BACK")
    private String ifBack;

    /**
     * 目的区县
     */
    @TableField("TO_COUNTY")
    private String toCounty;

    /**
     * 目的市
     */
    @TableField("TO_CITY")
    private String toCity;

    /**
     * 目的省
     */
    @TableField("TO_PROVINCE")
    private String toProvince;

    /**
     * 目的地
     */
    @TableField("TO_PLACE")
    private String toPlace;

    /**
     * 始发区县
     */
    @TableField("FROM_COUNTY")
    private String fromCounty;

    /**
     * 始发市
     */
    @TableField("FROM_CITY")
    private String fromCity;

    /**
     * 始发省
     */
    @TableField("FROM_PROVINCE")
    private String fromProvince;

    /**
     * 起运地
     */
    @TableField("FROM_PLACE")
    private String fromPlace;

    /**
     * 起运国
     */
    @TableField("FROM_COUNTRY")
    private String fromCountry;

    /**
     * 目的国
     */
    @TableField("TO_COUNTRY")
    private String toCountry;

    /**
     * 目的区县编码
     */
    @TableField("TO_COUNTY_CODE")
    private String toCountyCode;

    /**
     * 目的市编码
     */
    @TableField("TO_CITY_CODE")
    private String toCityCode;

    /**
     * 目的省编码
     */
    @TableField("TO_PROVINCE_CODE")
    private String toProvinceCode;

    /**
     * 目的地编码
     */
    @TableField("TO_PLACE_CODE")
    private String toPlaceCode;

    /**
     * 始发区县编码
     */
    @TableField("FROM_COUNTY_CODE")
    private String fromCountyCode;

    /**
     * 始发市编码
     */
    @TableField("FROM_CITY_CODE")
    private String fromCityCode;

    /**
     * 始发省编码
     */
    @TableField("FROM_PROVINCE_CODE")
    private String fromProvinceCode;

    /**
     * 起运地编码
     */
    @TableField("FROM_PLACE_CODE")
    private String fromPlaceCode;

    /**
     * 起运国编码
     */
    @TableField("FROM_COUNTRY_CODE")
    private String fromCountryCode;

    /**
     * 目的国编码
     */
    @TableField("TO_COUNTRY_CODE")
    private String toCountryCode;

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
     * 物流品类id
     */
    @TableField("LOGISTICS_CATEGORY_ID")
    private Long logisticsCategoryId;

    /**
     * 物流品类编码
     */
    @TableField("LOGISTICS_CATEGORY_CODE")
    private String logisticsCategoryCode;

    /**
     * 物流品类名称
     */
    @TableField("LOGISTICS_CATEGORY_NAME")
    private String logisticsCategoryName;

    /**
     * 总金额
     */
    @TableField("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /**
     * 数量
     */
    @TableField("NUMBER")
    private BigDecimal number;

    /**
     * 费用行(3级行)
     */
    @TableField(exist = false)
    private List<OrderLineFee> orderLineFeeList;

    /**
     * 计费单位名称
     */
    @TableField("CHARGE_UNIT_NAME")
    private String chargeUnitName;

    /**
     * 计费方式名称
     */
    @TableField("CHARGE_METHOD_NAME")
    private String chargeMethodName;

    /**
     * 费项名称
     */
    @TableField("EXPENSE_ITEM_NAME")
    private String expenseItemName;

    /**
     * LEG名称
     */
    @TableField("LEG_NAME")
    private String legName;


}

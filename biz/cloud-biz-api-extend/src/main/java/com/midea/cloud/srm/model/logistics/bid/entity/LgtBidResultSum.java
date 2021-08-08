package com.midea.cloud.srm.model.logistics.bid.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  投标结果汇总表 模型
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_bid_result_sum")
public class LgtBidResultSum extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 投标结果ID
     */
    @TableId("RESULT_SUM_ID")
    private Long resultSumId;

    /**
     * 物流招标头ID
     */
    @TableField("BIDING_ID")
    private Long bidingId;

    /**
     * 当前轮次
     */
    @TableField("ROUND")
    private Integer round;

    /**
     * 申请行号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * 服务项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 起运地
     */
    @TableField("FROM_PLACE")
    private String fromPlace;

    /**
     * 目的地
     */
    @TableField("TO_PLACE")
    private String toPlace;

    /**
     * 是否往返
     */
    @TableField("IF_BACK")
    private String ifBack;

    /**
     * 计费方式
     */
    @TableField("CHARGE_METHOD")
    private String chargeMethod;

    /**
     * 币制
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 运输距离
     */
    @TableField("TRANSPORT_DISTANCE")
    private String transportDistance;

    /**
     * 决标结果
     */
    @TableField("result")
    private String result;

    /**
     * 价格有效开始时间
     */
    @TableField("PRICE_TIME_START")
    private LocalDate priceTimeStart;

    /**
     * 价格有效开始时间
     */
    @TableField("PRICE_TIME_END")
    private LocalDate priceTimeEnd;

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


}

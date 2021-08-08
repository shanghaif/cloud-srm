package com.midea.cloud.srm.model.api.aps;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PiNotifyLine实体
 *
 * @author caolj2
 * @date 2020-10-29 13:22:08
 */
@Data
@TableName("ceea_mrp_deliver_plan_detail")
public class PiNotifyLine1 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 来源数据批次号
     */

    private String sourceBatchId;
    /**
     * 插入时间
     */
    private Date insertTime;
    /**
     * 接口数据处理时间
     */
    private Date dealTime;
    /**
     * 处理标志
     */
    private String dealFlag;
    /**
     * 是用来作为写正式表用的批次
     */
    private String groupId;
    /**
     * 操作类型（A 新增或修改 D删除）
     */
    private String action;
    /**
     * 是否处理成功
     */

    private String successFlag;
    /**
     * 失败原因
     */

    private String failMessage;
    /**
     * 工厂编码
     */

    private String plantCode;
    /**
     * 送货通知单号
     */

    private String notifyNumber;
    /**
     * 送货通知行号
     */

    private BigDecimal lineNum;
    /**
     * 采购订单号
     */

    private String poNumber;
    /**
     * 采购订单行号
     */

    private String poLineNum;
    /**
     * 数量
     */

    private BigDecimal quantity;
    /**
     * 接收数量
     */

    private BigDecimal deliveryQuantity;
    /**
     * SRM行号
     */

    private String srmLineNo;
    /**
     * SRM序列号
     */

    private String srmSerialNumber;
    /**
     * 供应商承诺数量
     */

    private BigDecimal vendorQty;
    /**
     * 行状态
     */

    private String status;
    /**
     * 传输方向：SEND-发送，RECEIVE-发送
     */

    private String transferFlag;
    /**
     * 送货通知类型：DP-送货计划，DN-送货通知
     */

    private String notifyType;
    /**
     * 需求日期
     */

    private Date needByDate;
    /**
     * 是否锁定（1是，2否）
     */
    private String lockFlag;
}

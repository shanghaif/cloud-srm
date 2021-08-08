package com.midea.cloud.srm.model.base.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.base.monitor.enums.BizStatus;
import com.midea.cloud.srm.model.base.monitor.enums.YesOrNo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_monitor_biz_log")
public class MonitorBizLog extends MonitorBaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("MONITOR_BIZ_LOG_ID")
    private Long monitorBizLogId;

    /**
     * ID
     */
    @TableField("MONITOR_BIZ_CONFIG_ID")
    private Long monitorBizConfigId;
    /**
     * 业务监控名称
     */
    @TableField("MONITOR_BIZ_NAME")
    private String monitorBizName;
    /**
     * 接收邮箱
     */
    @TableField("RECEIVE_MAILS")
    private String receiveMails;

    /**
     * 执行开始时间
     */
    @TableField("EXECUTE_START_TIME")
    private Date executeStartTime;

    /**
     * 执行结束时间
     */
    @TableField("EXECUTE_END_TIME")
    private Date executeEndTime;

    /**
     * 业务状态
     */
    @TableField("BIZ_STATUS")
    private BizStatus bizStatus;

    /**
     * 错误信息
     */
    @TableField("ERROR_MSG")
    private String errorMsg;

    /**
     * 处理状态
     */
    @TableField("DEAL_STATUS")
    private YesOrNo dealStatus;
}

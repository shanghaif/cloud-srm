package com.midea.cloud.srm.model.base.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.base.monitor.enums.BizStatus;
import com.midea.cloud.srm.model.base.monitor.enums.CycleType;
import com.midea.cloud.srm.model.base.monitor.enums.MonitorMode;
import com.midea.cloud.srm.model.base.monitor.enums.OperationSymbol;
import com.midea.cloud.srm.model.base.monitor.enums.YesOrNo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_monitor_biz_config")
public class MonitorBizConfig extends MonitorBaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("MONITOR_BIZ_CONFIG_ID")
    private Long monitorBizConfigId;

    /**
     * 业务监控名称
     */
    @TableField("MONITOR_BIZ_NAME")
    private String monitorBizName;

    /**
     * 业务监控类型
     */
    @TableField("MONITOR_BIZ_TYPE")
    private String monitorBizType;

    /**
     * 业务监控模式
     */
    @TableField("MONITOR_MODE")
    private MonitorMode monitorMode;

    /**
     * 查询模块 字典: MODULE_DIVISION
     */
    @TableField("QUERY_MODULE")
    private String queryModule;

    /**
     * sql表达式
     */
    @TableField("SQL_EXPRESSION")
    private String sqlExpression;

    /**
     * 字段名
     */
    @TableField("COLUMN_NAME")
    private String columnName;

    /**
     * 操作类型：>/>=/</<=/between
     */
    @TableField("OPERATION_SYMBOL")
    private OperationSymbol operationSymbol;

    /**
     * 操作值
     */
    @TableField("DATA_VALUE")
    private String dataValue;

    /**
     * 最小值
     */
    @TableField("MIN_VALUE")
    private String minValue;

    /**
     * 最大值
     */
    @TableField("MAX_VALUE")
    private String maxValue;

    /**
     * 周期类型:暂定为自定义
     */
    @TableField("CYCLE_TYPE")
    private CycleType cycleType;

    /**
     * 结果数据类型：当前只支持单个数据
     */
    @TableField("RESULT_DATA_TYPE")
    private String resultDataType;

    /**
     * 任务名称:前缀：MonitorBizConfig+configId+UUID
     */
    @TableField("TRIGGER_NAME")
    private String triggerName;

    /**
     * 周期表达式
     */
    @TableField("CRON_EXPRESSION")
    private String cronExpression;

    /**
     * 接收邮箱
     */
    @TableField("RECEIVE_MAILS")
    private String receiveMails;

    /**
     * 启用状态
     */
    @TableField("VALID_STATUS")
    private YesOrNo validStatus;

    /**
     * 最新业务状态
     */
    @TableField("LATEST_BIZ_STATUS")
    private BizStatus latestBizStatus;

}

package com.midea.cloud.srm.model.quartz.scheduler.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 * 保存触发器的基本信息表
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/7
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("qrtz_triggers")
public class QuartzTrigger extends BaseEntity {
    private static final long serialVersionUID = 7014947494485143967L;

    /** 触发器的名字,该名字用户自己可以随意定制,无强行要求 */
    @TableField("TRIGGER_NAME")
    private String triggerName;
    /** 触发器所属组的名字,该名字用户自己随意定制,无强行要求 */
    @TableField("TRIGGER_GROUP")
    private String triggerGroup;
    /** 上一次触发时间（毫秒） */
    @TableField("PREV_FIRE_TIME")
    private Long prevFireTime;
    /** 下一次触发时间，默认为-1，意味不会自动触发 */
    @TableField("NEXT_FIRE_TIME")
    private Long nextFireTime;
    /** 上次触发日期 */
    @TableField(exist = false)
    private Date prevFireDate;
    /** 下次触发日期 */
    @TableField(exist = false)
    private Date nextFireDate;
    /** 当前触发器状态,设置为ACQUIRED,如果设置为WAITING,则job不会触(WAITING:等待 PAUSED:暂停ACQUIRED:正常执行 BLOCKED：阻塞 ERROR：错误) */
    @TableField("TRIGGER_STATE")
    private String triggerState;
    /** 触发器的类型，使用cron表达式 */
    @TableField("TRIGGER_TYPE")
    private String triggerType;
    /** 开始时间 */
    @TableField("START_TIME")
    private Long startTime;
    /** 开始时间 */
    @TableField(exist = false)
    private Date startDate;
    /** cron表达式 */
    @TableField(exist = false)
    private String cronExpression;
    /** 重复总数 */
    @TableField(exist = false)
    private Long repeatCount;
    /** 内部重复 */
    @TableField(exist = false)
    private Long repeatInterval;

}

package com.midea.cloud.srm.model.quartz.scheduler.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 * 保存触发器的基本信息表DTO
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
public class QuartzTriggerDTO extends BaseDTO {

    private static final long serialVersionUID = -3708746583924392133L;

    /**触发器的名字,该名字用户自己可以随意定制,无强行要求*/
    private String triggerName;
    /**触发器所属组的名字,该名字用户自己随意定制,无强行要求*/
    private String triggerGroup;
    /**上一次触发时间（毫秒）*/
    private Long prevFireTime;
    /**下一次触发时间，默认为-1，意味不会自动触发*/
    private Long nextFireTime;
    /**上次触发日期*/
    private Date prevFireDate;
    /**下次触发日期*/
    private Date nextFireDate;
    /**当前触发器状态,设置为ACQUIRED,如果设置为WAITING,则job不会触(WAITING:等待 PAUSED:暂停ACQUIRED:正常执行 BLOCKED：阻塞 ERROR：错误)*/
    private String triggerState;
    /**触发器的类型，使用cron表达式*/
    private String triggerType;
    /**开始时间*/
    private Long startTime;
    /**开始日期*/
    private Date startDate;
    /**cron表达式*/
    private String cronExpression;
    /**重复总数*/
    private Long repeatCount;
    /**内部重复*/
    private Long repeatInterval;

}

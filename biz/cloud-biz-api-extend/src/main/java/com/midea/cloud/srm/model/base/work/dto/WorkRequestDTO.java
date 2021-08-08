package com.midea.cloud.srm.model.base.work.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.base.work.entry.Work;
import lombok.Data;

/**
 * <pre>
 *  任务表 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/9 14:09
 *  修改内容:
 * </pre>
 */
@Data
public class WorkRequestDTO extends Work {
    /**
     * 操作目标
     */
    private String opt;//待处理: ON_HAND;待审批:OT_APPROVED ; 已处理: HANDLED ; 我启动: MY_START 4; 抄送我 COPY_TO_ME

    /**
     * 公司ID
     */
    private Long companyId;


    /**
     * 开始创建时间
     */
    private String startCreationDate;

    /**
     * 截止创建时间
     */
    private String endCreationDate;
}

package com.midea.cloud.srm.model.base.seq.entity.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/4
 *  修改内容:
 * </pre>
 */
@Data
public class SeqDefinitionDto implements Serializable {

    private static final long serialVersionUID = -999555886465190199L;
    /**
     * 序列名称
     */
    @TableField("SEQUENCE_NAME")
    private String sequenceName;

    /**
     * 单据编码
     */
    @TableField("SEQUENCE_CODE")
    private String sequenceCode;

    /**
     * 长度
     */
    @TableField("LENGTH")
    private Long length;

    /**
     * 前缀
     */
    @TableField("PREFIX")
    private String prefix;

    @TableField("CURRENT_VALUE")
    private Long currentValue;

    /**
     * 区域定义
     */
    @TableField("SCOPE_DEFINITION")
    private String scopeDefinition;

    /**
     * 开始时间
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 结束时间
     */
    @TableField("START_DATE")
    private Date endDate;

}

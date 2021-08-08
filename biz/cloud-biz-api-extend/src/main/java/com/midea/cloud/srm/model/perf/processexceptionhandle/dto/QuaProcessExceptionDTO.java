package com.midea.cloud.srm.model.perf.processexceptionhandle.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *  8D报告 模型
 * </pre>
 *
 * @author chenjw90@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:47:57 PM
 *  修改内容:
 * </pre>
 */
@Data
public class QuaProcessExceptionDTO extends BaseDTO {
    private static final long serialVersionUID = 506203L;
    /**
     * 单据编码
     */
    private Long billCode;
    /**
     * 业务实体名字
     */
    private String orgName;
    /**
     * 工单
     */
    private Long orderCode;
    /**
     * 产品代码
     */
    private Long productionCode;
    /**
     * 产品描述
     */
    private String productionDesc;
    /**
     * 物料编码
     */
    private Long materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 批号
     */
    private Long batchCode;
    /**
     * 是否需要回复8D报告
     */
    private String report8D;
    /**
     * 问题状态
     */
    private String problemStatus;
    /**
     * 处理结果
     */
    private String handleResult;
    /**
     * 不良描述
     */
    private String unqualifiedDesc;
    /**
     * 负责人
     */
    private String processAgent;
    /**
     * 外协工厂
     */
    private String outsourcingFactory;
    /**
     * 供应商名称
     */
    private String vendorName;
    /**
     * 备注
     */
    private String processComments;
    /**
     * 投入数量
     */
    private Long investmentTotal;
    /**
     * 不良数量
     */
    private Long unqualifiedTotal;
    /**
     * 分属事业部
     */
    private String buName;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date creationDate;
}

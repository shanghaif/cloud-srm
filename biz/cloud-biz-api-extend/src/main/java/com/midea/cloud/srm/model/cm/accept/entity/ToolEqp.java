package com.midea.cloud.srm.model.cm.accept.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  合同附带工具设备 模型
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-11 10:56:08
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_contract_tool_eqp")
public class ToolEqp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 附件工具设备ID
     */
    @TableId(value = "TOOL_EQP_ID")
    private Long toolEqpId;

    /**
     * 合同验收单ID
     */
    @TableField("ACCEPT_ORDER_ID")
    private Long acceptOrderId;

    /**
     * 工具设备名称
     */
    @TableField("TOOL_EQP_NAME")
    private String toolEqpName;

    /**
     * 规格
     */
    @TableField("EQP_SPECIFICATION")
    private String eqpSpecification;

    /**
     * 数量
     */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;


}

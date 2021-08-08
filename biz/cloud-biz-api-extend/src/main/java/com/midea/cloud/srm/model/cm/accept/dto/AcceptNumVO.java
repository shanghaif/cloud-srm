package com.midea.cloud.srm.model.cm.accept.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class AcceptNumVO extends BaseEntity {
    /**
     * 主键ID
     */
    private Long orderDetailId;

    /**
     * 缺陷数量
     */

    private BigDecimal ceeaDamageQuantity;
    /**
     * 本次验收数量
     */
    private BigDecimal acceptQuantity;
}

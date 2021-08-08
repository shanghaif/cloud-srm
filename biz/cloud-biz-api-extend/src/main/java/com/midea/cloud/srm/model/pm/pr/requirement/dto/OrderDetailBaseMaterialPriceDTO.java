package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialPriceTable;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  海鲜价计算规则
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/24 下午 05:49
 *  修改内容:
 * </pre>
 */
@Data
public class OrderDetailBaseMaterialPriceDTO implements Serializable {
    /**
     * 订单明细行
     */
    private List<Long> orderDetailIds;

    /**
     * 基价信息
     */
    private List<BaseMaterialPriceTable> baseMaterialPrices;
}

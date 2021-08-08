package com.midea.cloud.srm.model.cm.order.dto;

import com.midea.cloud.srm.model.cm.model.entity.ModelLine;
import com.midea.cloud.srm.model.cm.order.entity.Order;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

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
 *  修改日期: 2020/7/6
 *  修改内容:
 * </pre>
 */
@Data
public class OrderDto extends BaseDTO {
    private Order order;
    private List<ModelLine> modelLines;
}

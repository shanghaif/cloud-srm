package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import lombok.Data;

import java.util.List;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;

/**
 * <pre>
 *  采购订单excel导入模板接收对象 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/24 15:03
 *  修改内容:
 * </pre>
 */
@Data
public class ExcelOrderRequestDTO extends Order {

    private static final long serialVersionUID = 1L;

    /**
     * 订单明细列表
     */
    private List<OrderDetailDTO> list;
}

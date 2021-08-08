package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;

import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/13 11:03
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryNoteDetailConfirmDTO extends DeliveryNoteDetail {
    /**
     * 采购订单ID
     */
    private Long orderId;

    /**
     * 采购订单号
     */
    private String orderNumver;

    /**
     * 订单数量
     */
    private BigDecimal orderNum;

    /**
     * 累计收货量
     */
    private BigDecimal receiveSum;

    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;
}

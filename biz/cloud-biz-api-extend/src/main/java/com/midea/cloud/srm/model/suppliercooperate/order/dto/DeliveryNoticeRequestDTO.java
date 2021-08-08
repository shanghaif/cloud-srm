package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  送货通知单 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14:45
 *  修改内容:
 *          </pre>
 */

@Data
public class DeliveryNoticeRequestDTO extends DeliveryNotice {

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 采购组织ID
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织名称
     */
    private String organizationName;


    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 收货工厂
     */
    private String receivedFactory;

    // 送货单 >>>>>
    /**
     * 送货单号
     */
    private String deliveryNumber;
    // 送货单 <<<<<

    // 送货单明细 >>>>>
    /**
     * 行号
     */
    private Integer deliveryLineNum;

    // 送货单明细 <<<<<

    /**
     * 业务实体id
     */
    private Long orgId;

    /**
     * 物料名称
     */
    private String materialName;

    private List<Long> ids;

}

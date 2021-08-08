package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  采购订单明细 数据返回传输对象
 * </pre>
 *
 * @author chenwj92
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/03 15:44
 *  修改内容:
 * </pre>
 */
@Data
public class OrderDetailDTO extends OrderDetail {

    private static final long serialVersionUID = 1L;

    //订单表属性 >>>>>
    /*private Date refuseTime;//拒绝时间
    private String refuseReason;//拒绝原因
    */
    /**
     * 剩余未送货数量
     */
    private String numberRemaining;
    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 币种
     */
    private String rfqSettlementCurrency;
    /**
     * 订单状态 字典编码：PURCHASE_ORDER
     */
    private String orderStatus;

    /*
    private String taxKey;//税率编码
    private BigDecimal taxRate;//税率
    private String rfqSettlementCurrency;//币种
    private String paymentMethod;//付款方式
    private String termOfPayment;//付款条件
    private String orderStatus;//订单状态
    private String organizationName;//采购组织名称
    private String organizationCode;//采购组织编码
    private String deliveryLevel;//交期等级
    private String orderAmount;//订单金额
    private String orderType;// 订单类型
    */

    /**
     * 采购申请可执行数量
     */
    private BigDecimal requirementLineNum;
    /**
     * 剩余收货数量
     */
    private BigDecimal unreceivedSum;
    /**
     * 订单日期
     */
    private Date ceeaPurchaseOrderDate;

    /**
     * 业务实体ID(longi)
     */
    private Long ceeaOrgId;

    /**
     * 业务实体编码(longi)
     */
    private String ceeaOrgCode;

    /**
     * 业务实体名称(longi)
     */
    private String ceeaOrgName;
    //订单表属性 <<<<<


    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 采购员id
     */
    private Long ceeaEmpUseId;

    /**
     * 采购员名称
     */
    private String ceeaEmpUsername;
    /**
     * 待验收数量
     */
    private BigDecimal waitAcceptQuantity;
    /**
     * 采购订单号
     */
    private String orderNumber;


    private BigDecimal warehouseReceiptQuantity; //入库数量

    private BigDecimal returnQuantity;//退货数量

    private String organizationName;//采购组织名称

    private String fullPathId;//路径ID

    /**
     * 业务实体
     */
    private Long orgId;

    /**
     * 收货地址
     */
    private String ceeaReceiveAddress;

    /**
     * 收单地址
     */
    private String ceeaReceiveOrderAddress;

    /**
     * 业务实体id
     */
    private List<Long> orgIds;

    /**
     * 订单类型
     */
    private String orderType;


    /**
     * 是否供应商确认
     */
    private String ceeaIfSupplierConfirm;

    /**
     * 是否寄售
     */
    private String ceeaIfConSignment;

    /**
     * 是否电站业务
     */
    private String ceeaIfPowerStationBusiness;

    /**
     * 送货通知数量
     */
    private BigDecimal noticeSum;

    /**
     * 送货时间
     */
    private Date deliveryTime;

    /**
     * 送货通知单号
     */
    private String deliveryNoticeNum;

    /**
     * 送货通知单ID
     */
    private Long deliveryNoticeId;

    /**
     * 本次可通知送货数量
     */
    private BigDecimal surplusDeliveryQuantity;

    /**
     * 订单来源
     * {{@link com.midea.cloud.common.enums.supcooperate.DeliveryNoteSource}}
     */
    private String orderSource;

    /**
     * 累计送货数量
     */
    private BigDecimal deliveryQuantitySum;

}

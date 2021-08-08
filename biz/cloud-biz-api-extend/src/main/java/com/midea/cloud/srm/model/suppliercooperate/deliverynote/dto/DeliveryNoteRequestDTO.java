package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  送货单 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/26 15:22
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryNoteRequestDTO extends DeliveryNote {

    private static final long serialVersionUID = 1L;

    //订单 >>>>>
    /**
     * 采购订单号
     */
    private String orderNumber;

    /**
     * 订单编号列表
     */
    private List<String> orderNumbers;

    /**
     * 业务实体Id列表 多选
     */
    private List<Long> orgIds;

    /**
     * 库存组织Id列表 多选
     */
    private List<Long> organizationIds;

    //订单 <<<<<

    //订单明细 >>>>>

    /**
     * 外部编号
     */
    private String externalNum;

    /**
     * 物料Id
     */
    private String materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    //订单明细 <<<<<

    /**
     * 送货预约号
     */
    private String deliveryAppointNumber;

    /**
     * 起始送货日期
     */
    private String startDeliveryDate;

    /**
     * 截止送货日期
     */
    private String endDeliveryDate;

    /**
     * 起始下单日期
     */
    private String startSubmittedTime;

    /**
     * 截止下单日期
     */
    private String endSubmittedTime;

    /**
     * 是否含旧送货单号
     */
    private Boolean containOld;

    /**
     * 当天送货日期
     */
    private String eqDeliveryDate;

    /**
     * 送货通知单号
     */
    private String deliveryNoticeNum;


    /**
     * 开始时间
     */
    private Date fromDate;

    /**
     * 结束时间
     */
    private Date toDate;
}

package com.midea.cloud.srm.model.suppliercooperate.invoice.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称描述:
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-28 9:52
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class InvoiceNoticeQueryDTO extends BaseDTO{

    private String invoiceNoticeNumber; //开票通知单号

    private Long invoiceNoticeId; //开票通知单ID

    private Long statementHeadId; //对账单头ID

    private Long orgId; //采购组织ID

    private String invoiceNoticeStatus; //单据状态

    private String creationDate; //创建日期

    private Date startCreationDate; //起始创建日期

    private Date endCreationDate; //终止创建日期

    private List<String> invoiceNoticeStatuses; //多个单据状态

    private String statementNumber; //对账单号

    private String statementStatus; //对账单状态

    private String fullPathId; //组织全路径ID

    private Long vendorId; //供应商ID

    private LocalDate startInvoiceDate;//开始开票日期

    private LocalDate endInvoiceDate;//结束开票日期

    private List<Long> orgIds;//业务实体ID集

    private String vendorParam;//供应商名称或编码

    private String vendorName;//供应商名称

    /**
     * 成本类型
     */
    private String ceeaCostType;

    /**
     * 成本类型编码(对应ERP供应商地点ID)
     */
    private String ceeaCostTypeCode;

    private String orderNumber;//采购订单号

    private String receiveOrderNo;//接收单号

    private String userType;

    private String onlineOrNotice;//网上开票或者开票通知

    private String contractNo;//合同序号

    private String contractCode;//合同编号

    private String materialParam;//物料编码或物料名称

    private String categoryName;//物料小类名称

    private String projectNum;//项目编号

    private String projectName;//项目名称

    private String orgParam;//业务主体名称或编码

    private Long organizationId;//库存组织ID

    private String organizationCode;//库存组织编码

    private String ifService = "N";//是否服务类,默认为非服务类

    private String orderStatus;//订单状态

    private String orderDetailId;//订单明细ID

    private List<String> businessSmallCodes;//业务小类编码集

    private String orderDetailStatus;//订单行状态

}

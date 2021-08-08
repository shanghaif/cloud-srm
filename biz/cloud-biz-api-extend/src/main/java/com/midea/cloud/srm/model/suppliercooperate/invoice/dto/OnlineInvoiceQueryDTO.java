package com.midea.cloud.srm.model.suppliercooperate.invoice.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/1 17:44
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class OnlineInvoiceQueryDTO extends BaseDTO {

    /**
     * 业务实体ID
     */
    private Long orgId;

    /**
     * 供应商编号/供应商名称
     */
    private String vendorKey;

    /**
     * 成本类型名称
     */
    private String costTypeName;

    /**
     * 成本类型编码
     */
    private String costTypeCode;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 网上发票号
     */
    private String onlineInvoiceNum;

    /**
     * 发票状态
     */
    private String invoiceStatus;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 导入状态
     */
    private String importStatus;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 业务实体集合
     */
    private List<Long> orgIds;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 税务发票号
     */
    private String taxInvoiceNum;

    /**
     * 费控发票号
     */
    private String fsscNo;

    /**
     * 付款方式
     */
    private String payMethod;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 网上开票类型
     */
    private String onlineInvoiceType;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商名称
     */
    private String vendorName;

    private List<OnlineInvoice> onlineInvoices;

    private Map<Long, List<OnlineInvoiceDetail>> mapOnlineInvoiceDetail;

    private Map<Long, List<OnlineInvoiceAdvance>> mapOnlineInvoiceAdvance;

    private Map<Long, List<Fileupload>> mapFileuploads;
}
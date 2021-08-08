package com.midea.cloud.srm.model.supplier.invite.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  邀请供应商 excel导出模型
 * </pre>
 *
 * @author dengbin1@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 30, 2021 9:52:55 AM
 *  修改内容:
 * </pre>
 */

@Data
@ColumnWidth(15) //列宽
public class ExcelInviteVendorDto {
    private static final long serialVersionUID = 729112L;
    /**
     * invite_vendor_id
     */
    @ExcelProperty(value = "invite_vendor_id", index = -1)
    private Long inviteVendorId;
    /**
     * 单据编码
     */
    @ExcelProperty(value = "单据编码", index = 0)
    private String inviteVendorNo;
    /**
     * 状态
     */
    @ExcelProperty(value = "状态", index = 1)
    private String inviteStatus;
    /**
     * 邀请原因，字典值
     */
    @ExcelProperty(value = "邀请原因，字典值", index = 2)
    private String inviteReason;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 3)
    private String remark;
    /**
     * 联系人
     */
    @ExcelProperty(value = "联系人", index = 4)
    private String contactPerson;
    /**
     * 联系邮箱
     */
    @ExcelProperty(value = "联系邮箱", index = 5)
    private String contactEmail;
    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码", index = 6)
    private String phoneNumber;
    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称", index = 7)
    private String vendorName;
    /**
     * 统一社会信用代码
     */
    @ExcelProperty(value = "统一社会信用代码", index = 8)
    private String socialCreditCode;
    /**
     * 注册后的供应商ID
     */
    @ExcelProperty(value = "注册后的供应商ID", index = 9)
    private Long vendorId;
    /**
     * 创建人ID
     */
    @ExcelProperty(value = "创建人ID", index = 10)
    private Long createdId;
    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人", index = 11)
    private String createdBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 12)
    private Date creationDate;
    /**
     * 创建人IP
     */
    @ExcelProperty(value = "创建人IP", index = 13)
    private String createdByIp;
    /**
     * 最后更新人ID
     */
    @ExcelProperty(value = "最后更新人ID", index = 14)
    private Long lastUpdatedId;
    /**
     * 最后更新人
     */
    @ExcelProperty(value = "最后更新人", index = 15)
    private String lastUpdatedBy;
    /**
     * 最后更新时间
     */
    @ExcelProperty(value = "最后更新时间", index = 16)
    private Date lastUpdateDate;
    /**
     * 最后更新人IP
     */
    @ExcelProperty(value = "最后更新人IP", index = 17)
    private String lastUpdatedByIp;
    /**
     * 租户ID
     */
    @ExcelProperty(value = "租户ID", index = 18)
    private Long tenantId;
    /**
     * 版本号
     */
    @ExcelProperty(value = "版本号", index = 19)
    private Long version;
    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 20)
    private String errorMsg;

}
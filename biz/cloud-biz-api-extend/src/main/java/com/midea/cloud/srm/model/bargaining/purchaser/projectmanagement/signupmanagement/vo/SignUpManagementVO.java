package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.signupmanagement.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * <pre>
 *  报名管理 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-7 14:14:03
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class SignUpManagementVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bidVendorId;//供应商表主键
    private Long vendorId;//供应商ID
    private Long signUpId;//供应商报名记录表主键
    private String vendorCode;//供应商编号
    private String vendorName;//供应商名称
    private String linkManName;//联系人
    private String phone;//电话
    private String email;//邮箱
    private String signUpStatus;//报名状态
    private String rejectReason;//驳回原因
    private Date replyDatetime;//回复时间
	private String ifDeposit;
}

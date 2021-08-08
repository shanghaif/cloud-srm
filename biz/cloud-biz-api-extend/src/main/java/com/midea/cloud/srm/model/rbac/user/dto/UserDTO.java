package com.midea.cloud.srm.model.rbac.user.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  用户信息 模型
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:27:10
 *  修改内容:
 * </pre>
 */
@Data
public class UserDTO  extends BaseDTO {


    /**
     * 角色权限关联ID
     */
    private Long userId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门
     */
    private String department;

    /**
     * 用户类型： 采购商： BUYER, 供应商： VENDOR
     */
    private String userType;

    /**
     * 账号类型： 主账号： Y, 子账号：N
     */
    private String mainType;

    /**
     * 生效日期
     */
    private LocalDate startDate;

    /**
     * 失效日期
     */
    private LocalDate endDate;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private String lastUpdatedId;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 验证码
     */
    private String verifyCode;


}

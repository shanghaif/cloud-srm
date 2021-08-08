package com.midea.cloud.srm.model.rbac.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  <pre>
 *  用户安全 模型
 * </pre>
 *
 * @author haibo1.huang@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-03 09:15:27
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_rbac_user_security")
public class UserSecurity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户安全ID
     */
    @TableId("USER_SECURITY_ID")
    private Long userSecurityId;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 账号
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 密码错误次数
     */
    @TableField("PSW_INCORRECT_TIMES")
    private Long pswIncorrectTimes;

    /**
     * 最后错误时间
     */
    @TableField("LAST_INCORRECT_TIME")
    private Date lastIncorrectTime;

    /**
     * 密码更新时间
     */
    @TableField("PWD_MODIFY_TIME")
    private Date pwdModifyTime;

    /**
     * 开启人脸识别
     */
    @TableField("IS_FACE_ENABLE")
    private String isFaceEnable;

    /**
     * 人脸识别正图片
     */
    @TableField("FACE_FILE_ID")
    private Long faceFileId;

    /**
     * 人脸识别上图片
     */
    @TableField("FACE_UP_FILE_ID")
    private Long faceUpFileId;
    
    /**
     * 人脸识别下图片
     */
    @TableField("FACE_DOWN_FILE_ID")
    private Long faceDownFileId;
    
    /**
     * 人脸识别左图片
     */
    @TableField("FACE_LEFT_FILE_ID")
    private Long faceLeftFileId;
    
    /**
     * 人脸识别右图片
     */
    @TableField("FACE_RIGHT_FILE_ID")
    private Long faceRightFileId;
    

    /**
     * 人脸识别闭眼图片
     */
    @TableField("FACE_EYE_FILE_ID")
    private Long faceEyeFileId;
    
    
    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}

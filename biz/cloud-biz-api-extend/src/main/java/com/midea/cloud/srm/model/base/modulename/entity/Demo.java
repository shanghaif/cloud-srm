package com.midea.cloud.srm.model.base.modulename.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.common.assist.handler.LongFormatAESEncryptHandler;
import com.midea.cloud.srm.model.common.assist.handler.StringFormatAESEncryptHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 *  演示 模型
 * </pre>
 *
 * @author huanghb14@example.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-12 13:25:26
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "demo", autoResultMap = true) // autoResultMap = true
public class Demo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    private String name;

    @TableField(value = "age", typeHandler = LongFormatAESEncryptHandler.class)
    // 通过LongFormatAESEncryptHandler对age字段进行入库加密，出库解密
    private Long age;

    @TableField(value = "secret", typeHandler = StringFormatAESEncryptHandler.class)
    // 通过StringFormatAESEncryptHandler对secret字段进行入库加密，出库解密
    private String secret;

    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;


}

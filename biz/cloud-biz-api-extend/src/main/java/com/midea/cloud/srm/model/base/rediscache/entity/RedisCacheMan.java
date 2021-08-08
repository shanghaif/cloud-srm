package com.midea.cloud.srm.model.base.rediscache.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.annonations.NotNull;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  redis缓存管理表 模型
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-25 10:13:00
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_redis_cache_man")
public class RedisCacheMan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * redis缓存ID
     */
    @TableId("REDIS_CACHE_ID")
    private Long redisCacheId;

    /**
     * 接口名称
     */
    @TableField("INTERFACE_NAME")
    @NotNull("接口名称不能为空")
    private String interfaceName;

    /**
     * 接口URL
     */
    @TableField("INTERFACE_URL")
    @NotNull("接口URL不能为空")
    private String interfaceUrl;

    /**
     * 缓存KEY
     */
    @TableField("CACHE_KEY")
    @NotNull("缓存KEY不能为空")
    private String cacheKey;

    /**
     * 是否开启(N/Y)
     */
    @TableField("IF_OPEN")
    @NotNull("是否开启不能为空")
    private String ifOpen;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 缓存时间
     */
    @TableField(exist = false)
    private String cacheTime;

    /**
     * 是否有缓存内容
     */
    @TableField(exist = false)
    private String ifCacheContent;
}

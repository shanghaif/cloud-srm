package com.midea.cloud.srm.model.logistics.soap.tms.entity;

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
 *  tms行政区域表(tms系统的数据, 接口表) 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-30 11:05:37
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_tms_region")
public class TmsRegion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 行政区域ID
     */
    @TableId("REGION_ID")
    private Long regionId;

    /**
     * 行政区域代码
     */
    @TableField("ADMIN_REGION_CODE")
    private String adminRegionCode;

    /**
     * 行政区域名称
     */
    @TableField("ADMIN_REGION_NAME")
    private String adminRegionName;

    /**
     * 行政区域英文名称
     */
    @TableField("ATTRIBUTE1")
    private String attribute1;

    /**
     * 行政区域层级
     */
    @TableField("REGION_LEVEL_CODE")
    private String regionLevelCode;

    /**
     * 上级区域代码
     */
    @TableField("ATTRIBUTE2")
    private String attribute2;

    /**
     * 有效性
     */
    @TableField("ENABLE_FLAG")
    private String enableFlag;

    /**
     * 所属国家代码
     */
    @TableField("BELONG_COUNTRY_CODE")
    private String belongCountryCode;

    /**
     * 所属省/直辖市代码
     */
    @TableField("BELONG_PROVINCE_CODE")
    private String belongProvinceCode;

    /**
     * 所属省/直辖市名称
     */
    @TableField("BELONG_PROVINCE_NAME")
    private String belongProvinceName;

    /**
     * 所属市代码
     */
    @TableField("BELONG_CITY_CODE")
    private String belongCityCode;

    /**
     * 所属市名称
     */
    @TableField("BELONG_CITY_NAME")
    private String belongCityName;

    /**
     * 所属区/县代码
     */
    @TableField("BELONG_COUNTY_CODE")
    private String belongCountyCode;

    /**
     * 所属区/县名称
     */
    @TableField("BELONG_COUNTY_NAME")
    private String belongCountyName;

    /**
     * 行政区域级别
     */
    @TableField("REGION_LEVEL")
    private String regionLevel;

    /**
     * 经度
     */
    @TableField("LONGITUDE")
    private String longitude;

    /**
     * 纬度
     */
    @TableField("DIMENSION")
    private String dimension;

    /**
     * 来源系统编码
     */
    @TableField("SOURCE_SYS_CODE")
    private String sourceSysCode;

    /**
     * 来源系统标识
     */
    @TableField("SOURCE_SYS_ID")
    private String sourceSysId;

    /**
     * 来源系统参考
     */
    @TableField("SOURCE_SYS_REF")
    private String sourceSysRef;

    /**
     * 备用字段3
     */
    @TableField("ATTRIBUTE3")
    private String attribute3;

    /**
     * 备用字段4
     */
    @TableField("ATTRIBUTE4")
    private String attribute4;

    /**
     * 备用字段5
     */
    @TableField("ATTRIBUTE5")
    private String attribute5;

    /**
     * 来源系统
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 同步业务表时间
     */
    @TableField("IMPORT_DATE")
    private Date importDate;

    /**
     * 导入业务表状态,0:未导入,1:已导入,-1:导入失败,
     */
    @TableField("IMPORT_STATUS")
    private Integer importStatus;

    /**
     * 报错信息
     */
    @TableField("ERROR_MSG")
    private String errorMsg;

}

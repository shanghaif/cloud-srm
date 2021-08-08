package com.midea.cloud.srm.model.logistics.soap.tms.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  tms行政区域请求实体类 用于接收行政区域数据的实体
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/30 11:01
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "adminRegionCode",
        "adminRegionName",
        "attribute1",
        "regionLevelCode",
        "attribute2",
        "enableFlag",
        "belongCountryCode",
        "belongProvinceCode",
        "belongProvinceName",
        "belongCityCode",
        "belongCityName",
        "belongCountyCode",
        "belongCountyName",
        "regionLevel",
        "longitude",
        "dimension",
        "sourceSysCode",
        "sourceSysId",
        "sourceSysRef",
        "attribute3",
        "attribute4",
        "attribute5"

})
public class TmsRegionEntity {

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

}

package com.midea.cloud.srm.model.base.region.dto;

import lombok.Data;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-10 10:43
 *  修改内容:
 * </pre>
 */
@Data
public class AreaDTO {
    private Long areaId;
    private String area;
    private Long cityId;
    private String city;
    private Long provinceId;
    private String province;
    private Long rangeId;
    private String rangeName;
    private String townId;
    private String town;
}

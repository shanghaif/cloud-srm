package com.midea.cloud.flow.model.dto;

/**
 *
 * <pre>
 * 组织管理POJO
 * </pre>
 *
 * @author liangtf2 liangtf2@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class OrgUnitDto {

    /**
     * 部门id
     */
    private Integer unitId;

    /**
     * 部门名称
     */
    private String unitName;
    
    /**
     * 部门编码
     */
    private String unitCode;

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

}

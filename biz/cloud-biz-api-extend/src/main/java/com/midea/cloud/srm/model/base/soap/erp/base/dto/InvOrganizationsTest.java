package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;

/**
*  <pre>
 *  库存组织接口表 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 15:39:50
 *  修改内容:
 * </pre>
*/
public class InvOrganizationsTest {

    protected String itfHeaderId;

    /**
     * 企业集团
     */
    private String businessGroup;

    /**
     * 企业系统code
     */
    private String externalSystemCode;

    public String getItfHeaderId() {
        return itfHeaderId;
    }

    public void setItfHeaderId(String itfHeaderId) {
        this.itfHeaderId = itfHeaderId;
    }

    public String getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(String businessGroup) {
        this.businessGroup = businessGroup;
    }

    public String getExternalSystemCode() {
        return externalSystemCode;
    }

    public void setExternalSystemCode(String externalSystemCode) {
        this.externalSystemCode = externalSystemCode;
    }
}

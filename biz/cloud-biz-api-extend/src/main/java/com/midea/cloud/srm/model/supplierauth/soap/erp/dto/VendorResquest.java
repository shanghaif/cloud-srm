package com.midea.cloud.srm.model.supplierauth.soap.erp.dto;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;

import javax.xml.bind.annotation.*;

/**
 * <pre>
 *  供应商基础信息请求实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2 11:18
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "vendorRequest")
@XmlType(name="", propOrder = {
        "esbInfo"
})
public class VendorResquest {

    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }
}

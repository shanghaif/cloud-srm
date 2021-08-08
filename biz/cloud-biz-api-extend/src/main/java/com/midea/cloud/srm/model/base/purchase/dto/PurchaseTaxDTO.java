package com.midea.cloud.srm.model.base.purchase.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/29 22:11
 *  修改内容:
 * </pre>
 */
@Data
public class PurchaseTaxDTO extends BaseDTO {

    //税率Id
    private String erpTaxId;

    //ERP税率编码
    private String erpTaxCode;

}

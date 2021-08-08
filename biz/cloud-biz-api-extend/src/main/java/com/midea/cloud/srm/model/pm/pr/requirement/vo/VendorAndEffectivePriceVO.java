package com.midea.cloud.srm.model.pm.pr.requirement.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <pre>
 *  货源供应商与有效价格  视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-22 14:32
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class VendorAndEffectivePriceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String haveSupplier;//货源供应商 Y:有 N:无
    private String haveEffectivePrice;//有效价格 Y:有,N:无

}

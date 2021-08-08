package com.midea.cloud.srm.model.inq.price.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/3
 *  修改内容:
 * </pre>
 */
@Data
public class PriceLibraryCheckDto implements Serializable {
    /**
     * 物料编码
     */
    private List<String> itemCodes;
    /**
     * 物料名称
     */
    private List<String> itemNames;
    /**
     * 供应商编码
     */
    private List<String> vendorCodes;
}

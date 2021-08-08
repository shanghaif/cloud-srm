package com.midea.cloud.srm.model.inq.price.dto;

import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibraryPaymentTerm;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/24 21:22
 *  修改内容:
 * </pre>
 */
@Data
public class PriceLibraryDTO extends PriceLibrary {
    private List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList;
}

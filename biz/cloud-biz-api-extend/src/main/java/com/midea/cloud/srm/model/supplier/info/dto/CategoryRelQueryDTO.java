package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.supplier.info.entity.CategoryRel;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/9 16:52
 *  修改内容:
 * </pre>
 */
@Data
public class CategoryRelQueryDTO extends CategoryRel{

    private String vendorCode;//供应商编码

    private String vendorName;//供应商名称

    private List<PurchaseCategory> purchaseCategories;//品类集

    private List<Long> orgIds;//业务实体ID集
}

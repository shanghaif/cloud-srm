package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称 合作关系查询DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/10 11:16
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class OrgCategoryQueryDTO extends OrgCategory{

    /*private List<PurchaseCategory> purchaseCategories;//品类集*/

    private List<Long> orgIds;//业务实体ID集
}

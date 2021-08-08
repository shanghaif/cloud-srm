package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplier.change.entity.OrgCategoryChange;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/10 12:55
 *  修改内容:
 * </pre>
 */
@Data
public class OrgCategorySaveDTO extends BaseDTO{

    private OrgCategory orgCategory;//合作品类关系

    private List<OrgCategoryChange> orgCategoryChanges;//合作品类关系变更状态集

    private ReviewForm reviewForm;//资质审查单
}

package com.midea.cloud.srm.model.base.purchase.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.base.purchase.entity.CategoryPurchaseType;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  物料小类采购类型DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/26 14:23
 *  修改内容:
 * </pre>
 */
@Data
public class CategoryPurchaseTypeDTO extends BaseDTO {

    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 采购类型
     */
    private List<CategoryPurchaseType> purchaseTypes;

}

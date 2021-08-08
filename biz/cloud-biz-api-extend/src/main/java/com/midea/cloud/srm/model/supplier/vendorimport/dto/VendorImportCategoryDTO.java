package com.midea.cloud.srm.model.supplier.vendorimport.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  根据供应商Id和orgId查询合作的品类列表
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/17 15:04
 *  修改内容:
 * </pre>
 */
@Data
public class VendorImportCategoryDTO extends BaseDTO {

    /**
     * 品类Id
     */
    private Long categoryId;

    /**
     * 品类编码
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 品类全路径Id
     */
    private String categoryFullId;

    /**
     * 品类全名称
     */
    private String categoryFullName;

}

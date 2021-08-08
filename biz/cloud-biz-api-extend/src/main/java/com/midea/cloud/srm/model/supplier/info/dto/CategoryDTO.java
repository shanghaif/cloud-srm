package com.midea.cloud.srm.model.supplier.info.dto;

import lombok.Data;

/**
 * <pre>
 *  供应商可供品类实体类DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/10 13:26
 *  修改内容:
 * </pre>
 */
@Data
public class CategoryDTO {

    /**
     * 供应商ID
     */
    private Long companyId;

    /**
     * 合作品类ID
     */
    private Long categoryId;

    /**
     * 合作品类code
     */
    private String categoryCode;

    /**
     * 合作品类
     */
    private String categoryName;

    /**
     * 合作品类全路径ID
     */
    private String categoryFullId;

    /**
     * 合作品类全称
     */
    private String categoryFullName;
}

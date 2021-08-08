package com.midea.cloud.srm.model.base.material.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanjl11
 * @date 2020/10/15 10:07
 * 所属物料大类
 */
@Data
@Accessors(chain = true)
public class MaterialMaxCategoryVO {

    private Long materialId;

    private String categoryCode;

    private Long categoryId;
}

package com.midea.cloud.srm.model.base.purchase.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <pre>
 * 采购分类大中小类信息实体
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
public class PurchaseCategoryAllInfo {
    /**
     * 大类ID
     */
    @TableId("CATEGORY_ID")
    private Long categoryId1;

    /**
     * 大类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode1;

    /**
     * 大类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName1;

    /**
     * 中类ID
     */
    @TableId("CATEGORY_ID")
    private Long categoryId2;

    /**
     * 中类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode2;

    /**
     * 中类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName2;

    /**
     * 小类ID
     */
    @TableId("CATEGORY_ID")
    private Long categoryId3;

    /**
     * 小类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode3;

    /**
     * 小类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName3;
}

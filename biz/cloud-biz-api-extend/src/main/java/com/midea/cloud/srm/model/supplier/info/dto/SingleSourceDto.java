package com.midea.cloud.srm.model.supplier.info.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 * 单一来源
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1
 *  修改内容:
 * </pre>
 */
@Data
public class SingleSourceDto extends BaseDTO {

    /**
     * 组织ID
     */
    private Long orgId;

    /**
     * 组织code
     */
    private String orgCode;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织类型
     */
    private String orgType;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 品类code
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 合作品类全称
     */
    private String categoryFullName;

    /**
     * 父ID
     */
    private Long fatherId;

}

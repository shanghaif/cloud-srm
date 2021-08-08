package com.midea.cloud.srm.model.base.formula.dto.create;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/28 16:54
 *  修改内容:
 * </pre>
 */
@Data
public class EssentialFactorCreateDto {
    /**
     * 要素名称
     */
    @NotEmpty(message = "要素名称不能为空")
    private String essentialFactorName;

    /**
     * 要素描述
     */
    @NotEmpty(message = "要素描述不能为空")
    private String essentialFactorDesc;

    /**
     * 来源
     */
    @NotEmpty(message = "要素取值来源不能为空")
    private String essentialFactorFrom;

    /**
     * 基材id
     */
    private Long baseMaterialId;

    /**
     * 基材编码
     */
    private String baseMaterialCode;

    /**
     * 基材编码
     */
    private String baseMaterialName;

    /**
     * 物料主属性id
     */
    private Long materialAttributeId;

    /**
     * 物料主属性名
     */
    private String materialAttributeName;

    /**
     * 价格类型
     */
    private String priceType;
}

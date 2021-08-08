package com.midea.cloud.srm.model.base.material.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
 *  修改日期: 2020/8/28 15:02
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialItemAttributeRelateUpdateDto {
    @NotNull(message = "关联行id不能为空")
    private Long relateId;

    /**
     * 属性值
     */
    private String attributeValue;

    /**
     * 是否为关键属性
     */
    private String keyFeature;
}

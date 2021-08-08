package com.midea.cloud.srm.model.supplier.quest.vo;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <pre>
 *  页签字段名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/19 17:20
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestTemplatePropVo extends BaseEntity {
    private static final long serialVersionUID = -8372137916383385630L;


    /**
     * 主键ID
     */
    private Long questTemplatePropId;

    /**
     * 排序号
     */
    private Integer questTemplatePropSort;
    /**
     * 字段编码
     */
    private String questTemplatePropField;
    /**
     * 字段描述
     */
    private String questTemplatePropFieldDesc;
    /**
     * 组件类型(1文本、2多行文本、3数值、4日期、5开关、6LOV值集、7下拉、8附件框)
     */
    private String questTemplatePropType;
    /**
     * 字典
     */
    private String questTemplatePropDict;
    /**
     * 组件属性
     */
    private String questTemplatePropComponent;
    /**
     * 是否启用(N否 Y是)
     */
    private String enabledFlag;
    /**
     * 是否必填(N否 Y是)
     */
    private String emptyFlag;

}

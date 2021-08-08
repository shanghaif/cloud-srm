package com.midea.cloud.srm.model.supplier.quest.vo;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <pre>
 *  页签组
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/19 17:08
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestTemplatePropGroupVo extends BaseEntity {


    private static final long serialVersionUID = 5298098926191245954L;

    /**
     * 主键ID
     */
    private Long questTemplatePropGroupId;
    /**
     * 页签组编码
     */
    private String questTemplatePropGroupCode;
    /**
     * 页签组名称
     */
    private String questTemplatePropGroupName;
    /**
     * 页签组类型: single单表页签 detail明细表页签
     */
    private String questTemplatePropGroupType;
    /**
     * 是否显示: N不显示  Y显示
     */
    private String showFlag;

    /**
     * 是否删除 0不删除 1删除
     */
    private Boolean deleteFlag;

    /**
     * 是否必填一行(类型为明细表类型的显示)
     */
    private String fillOneLineFlag;


    /**
     * 页签字段
     */
    private List<QuestTemplatePropVo> questTemplatePropArr;

}

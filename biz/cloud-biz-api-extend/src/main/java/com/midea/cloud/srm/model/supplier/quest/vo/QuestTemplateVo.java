package com.midea.cloud.srm.model.supplier.quest.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateOrg;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/19 16:30
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestTemplateVo  extends BaseEntity {


    private static final long serialVersionUID = -3716532753617202970L;

    /**
     * 调查模板编码
     */
    private Long questTemplateId;

    /**
     * 调查模板编码
     */
    private String questTemplateCode;
    /**
     * 调查模板名称
     */
    private String questTemplateName;
    /**
     * 调查模板类型
     */
    private String questTemplateType;

    /**
     * 备注
     */
    private String questTemplateRemark;

    /**
     * 页签组
     */
    private List<QuestTemplatePropGroupVo> questTemplateTabArr;

    private List<Long> organizationIds;

    private List<QuestTemplateOrg> questTemplateOrgArr;

    //创建人账号
    private String createdBy;
    //创建人姓名
    private String createdFullName;
    //创建时间
    private Date creationDate;
    //最后更新人账号
    private String lastUpdatedBy;
    //最后更新人姓名
    private String lastUpdatedFullName;
    //最后更新时间
    private Date lastUpdateDate;

}

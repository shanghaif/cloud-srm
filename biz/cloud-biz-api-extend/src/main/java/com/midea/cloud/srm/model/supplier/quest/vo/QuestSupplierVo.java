package com.midea.cloud.srm.model.supplier.quest.vo;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  问卷调查 模型
 * </pre>
 *
 * @author bing5.wang@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 16, 2021 5:33:46 PM
 *  修改内容:
 * </pre>
 */

@Data
public class QuestSupplierVo extends BaseEntity {
    private Long questSupId;
    //调查表编号
    private String questNo;
    //调查表名称
    private String questName;
    //调查表状态
    private String approvalStatus;
    //调查表状态
    private String approvalStatusName;
    //供应商编码
    private String companyCode;
    //供应商名称
    private String companyName;
    //社会信用代码
    private String lcCode;
    //联系人
    private String contactName;
    //联系方式
    private String ceeaContactMethod;
    //邮箱
    private String email;
    //调查模板类型
    private String questTemplateType;
    //调查模板类型名称
    private String questTemplateTypeName;
    //组织ID
    private String questTemplateOrgId;
    //组织编码
    private String questTemplateOrgCode;
    //组织名称
    private String questTemplateOrgName;
    //调查模板ID
    private Long questTemplateId;
    //调查模板编码
    private String questTemplateCode;
    //调查表模板名称
    private String questTemplateName;
    //问卷反馈备注
    private String questFeedBack;
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
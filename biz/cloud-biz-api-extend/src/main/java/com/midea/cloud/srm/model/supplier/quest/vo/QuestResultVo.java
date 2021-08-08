package com.midea.cloud.srm.model.supplier.quest.vo;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
 *  修改日期: Apr 16, 2021 5:34:12 PM
 *  修改内容:
 * </pre>
 */

@Data
public class QuestResultVo extends BaseDTO {
    //主键ID
    private Long questResultId;
    //问卷分配给供应商表主键ID
    private Long questSupId;
    //调查模板定义表主键ID
    private Long questTemplateId;
    //调查表编号
    private String questNo;
    //调查表名称
    private String questName;
    //调查模板类型
    private String questTemplateType;
    //调查模板类型名称
    private String questTemplateTypeName;
    //调查模板所属组织ID
    private String questTemplateOrgId;
    //调查模板所属组织编码
    private String questTemplateOrgCode;
    //调查模板所属组织名称
    private String questTemplateOrgName;
    //供应商名称
    private String companyName;
    //审批状态
    private String approvalStatus;
    //反馈备注
    private String questFeedback;
    //页签组信息
    private List<GroupInfo> groupInfoList = new ArrayList<>();

    @Data
    public static class GroupInfo {
        //问卷模板属性组表主键ID
        private Long questTemplatePropGroupId;
        //页签组编码
        private String questTemplatePropGroupCode;
        //页签组名称
        private String questTemplatePropGroupName;
        //页签组类型: single单表页签 detail明细表页签
        private String questTemplatePropGroupType;
        //字段与数据
        private List<FieldInfo> fieldInfoList = new ArrayList<>();

        @Data
        public static class FieldInfo {
            //问卷模板属性表主键ID
            private Long questTemplatePropId;
            //排序号
            private Integer questTemplatePropSort;
            //字段编码
            private String questTemplatePropField;
            //字段值
            private String questTemplatePropFieldData;
            //字段标签
            private String questTemplatePropFieldLable;
            //组件类型
            private String questTemplatePropType;
            //字典
            private String questTemplatePropDict;
        }
    }
}
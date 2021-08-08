package com.midea.cloud.srm.model.supplier.quest.dto;

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
public class QuestResultDto extends BaseDTO {
    //主键ID
    private Long questResultId;
    //问卷分配给供应商表主键ID
    private Long questSupId;
    //调查模板定义表主键ID
    private Long questTemplateId;
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
        //问卷模板属性组编码
        private String questTemplatePropGroupCode;

        private String questTemplatePropGroupType;
        //字段与数据
        private List<FieldInfo> fieldInfoList = new ArrayList<>();

        @Data
        public static class FieldInfo {
            //问卷模板属性表主键ID
            private Long questTemplatePropId;
            //字段编码
            private String questTemplatePropField;
            //字段值
            private String questTemplatePropFieldData;
            //字段标签
            private String questTemplatePropFieldLable;
        }
    }
}
package com.midea.cloud.srm.model.supplier.quest.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.midea.cloud.srm.model.common.BaseDTO;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  问卷调查表
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
public class QuestSupplierDto extends BaseDTO {

    private Long questSupIdForQuery;
    //调查模板定义主键ID
    private Long questTemplateId;
    //供应商表主键ID 查询用
    private Long companyIdForQuery;
    //调查表编号 查询用
    private String questNoForQuery;
    //调查表名称
    private String questName;
    //调查模板类型
    private String questTemplateType;
    //调查模板类型名称
    private String questTemplateTypeName;
    //调查模板所属组织ID
    private String questTemplateOrgId;
    private List<String> questTemplateOrgIdList=new ArrayList<String>();
    //调查模板所属组织编码
    private String questTemplateOrgCode;
    //调查模板所属组织名称
    private String questTemplateOrgName;
    //审批状态
    private String approvalStatus;
    private List<String> approvalStatusList=new ArrayList<String>();
    //问卷反馈备注
    private String questFeedback;
    //供应商信息
    private List<CompanyInfo> companyInfoList;
    //操作类型:暂存,提交
    String opType;
    //创建日期起止
    private Date creationDateBegin;
    private Date creationDateEnd;
    private String orgCondition="N";

    @Data
    public static class CompanyInfo {
        private Long questSupId;
        //调查表编号
        private String questNo;
        //供应商表主键ID
        private Long companyId;
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
    }

    public List<String> getApprovalStatusList() {
        if (StringUtils.isNotBlank(approvalStatus)) {
            approvalStatusList = Arrays.asList(approvalStatus.split(","));
        }
        return approvalStatusList;
    }
}
package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-12 11:57
 *  修改内容:
 * </pre>
 */
@Data
public class CompanyRequestDTO  extends BaseDTO {
    /**
     * 可供品类Id
     */
    private List<Long> companyIds;
    /**
     * 公司Id
     */
    private String companyId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 海外关系
     */
    private String overseasRelation;
    /**
     * 公司性质
     */
    private String companyType;
    /**
     * 统一信用代码
     */
    private String lcCode;
    /**
     * 数据来源
     */
    private String dataSources;
    /**
     * 单据审批状态
     */
    private String status;
    /**
     * 法定代表人
     */
    private String legalPerson;
    /**
     * 是否黑名单
     */
    private String isBacklist;
    /**
     * 准入日期开始
     */
    private Date startDate;
    /**
     * 准入日期结束
     */
    private Date endDate;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 企业编码
     */
    private String companyCode;

    /**
     * 合作组织
     */
    private Long orgId;

    /**
     * 合作组织
     */
    private List<Long> orgIds;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 可供品类Id
     */
    private Long categoryId;

    /**
     * 供应商负责人ID
     */
    private Long responsibilityId;

    /**
     * 是否显示废弃单据，默认不显示 N
     */
    private String ifDisplayAbandoned;

    private String createdBy;

    /**
     * 默认联系人
     */
    private String contactName;

    /**
     * 联系方式
     */
    private String ceeaContactMethod;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 详细地址
     */
    private String companyAddress;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 是否有特殊角色
     */
    private String isContainRole;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 绿色通道单号
     */
    private String greenWayNumber;

    private String serviceStatus;

    /**
     * 供应商问卷审批状态
     */
    List<String> approvalStatusList;
}

package com.midea.cloud.srm.model.supplier.info.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  公司基本信息 模型
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-11 17:00:17
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_company_info")
public class CompanyInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("COMPANY_ID")
    private Long companyId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 单据状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 状态名称
     */
    @TableField("STATUS_NAME")
    private String statusName;

    /**
     * 公司网站
     */
    @TableField("CEEA_COMPANY_WEBSITE")
    private String ceeaCompanyWebsite;

    /**
     * 代理品牌
     */
    @TableField("CEEA_AGENT_BRAND")
    private String ceeaAgentBrand;

    /**
     * 上市时间
     */
    @TableField("CEEA_LISTED_TIME")
    private LocalDate ceeaListedTime;

    /**
     * 是否上市
     */
    @TableField("CEEA_IF_LISTED")
    private String ceeaIfListed;

    /**
     * 是否有母公司
     */
    @TableField("CEEA_HAS_PARENT_COMPANY")
    private String ceeaHasParentCompany;

    /**
     * 母公司名称
     */
    @TableField("CEEA_PARENT_COMPANY_NAME")
    private String ceeaParentCompanyName;

    /**
     * 母公司统一信用代码
     */
    @TableField("CEEA_PARENT_COMPANY_LC_CODE")
    private String ceeaParentCompanyLcCode;

    /**
     * 厂房面积
     */
    @TableField("CEEA_PLANT_AREA")
    private String ceeaPlantArea;

    /**
     * 厂房性质
     */
    @TableField("CEEA_PLANT_TYPE")
    private String ceeaPlantType;

    /**
     * 公司简介
     */
    @TableField("CEEA_COMPANY_INTRO")
    private String ceeaCompanyIntro;

    /**
     * 行业类型
     */
    @TableField("CEEA_INDUSTRY_TYPE")
    private String ceeaIndustryType;

    /**
     * 供应商业务类型
     */
    @TableField("CEEA_SUP_BUSINESS_TYPE")
    private String ceeaSupBusinessType;

    /**
     * 商业模式
     */
    @TableField("CEEA_BUSINESS_MODEL")
    private String ceeaBusinessModel;

    /**
     * ERP供应商Id（隆基新增，NSrm推送Erp成功后，Erp回传的erpVendorId）
     */
    @TableField("ERP_VENDOR_ID")
    private Long erpVendorId;

    /**
     * ERP供应商Code（隆基新增，NSrm推送Erp成功后，Erp回传的erpVendorCode）
     */
    @TableField("ERP_VENDOR_CODE")
    private String erpVendorCode;

    /**
     * 企业CODE
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 企业名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * 企业名称（英文）
     */
    @TableField("COMPANY_EN_NAME")
    private String companyEnName;

    /**
     * 境内外关系
     */
    @TableField("OVERSEAS_RELATION")
    private String overseasRelation;

    /**
     * 境外关系名称
     */
    @TableField("OVERSEAS_RELATION_NAME")
    private String overseasRelationName;

    /**
     * 企业性质
     */
    @TableField("COMPANY_TYPE")
    private String companyType;

    /**
     * 企业性质名称
     */
    @TableField("COMPANY_TYPE_NAME")
    private String companyTypeName;

    /**
     * 企业简称
     */
    @TableField("COMPANY_SHORT_NAME")
    private String companyShortName;

    /**
     * 统一社会信用代码
     */
    @TableField("LC_CODE")
    private String lcCode;

    /**
     * 上传(三证合一)文件ID
     */
    @TableField("BUSINESS_LICENSE_FILE_ID")
    private String businessLicenseFileId;

    /**
     * 上传三证合一照片名称
     */
    @TableField("BUSINESS_LICENSE")
    private String businessLicense;

    /**
     * 身份证号码
     */
    @TableField("ID_NUMBER")
    private String idNumber;

    /**
     * DUNS编号
     */
    @TableField("DUNS_CODE")
    private String dunsCode;

    /**
     * 经营状态
     */
    @TableField("COMPANY_STATUS")
    private String companyStatus;

    /**
     * 经营状态名称
     */
    @TableField("COMPANY_STATUS_NAME")
    private String companyStatusName;


    /**
     * 法定代表人
     */
    @TableField("LEGAL_PERSON")
    private String legalPerson;

    /**
     * 注册资金(注册资本(万元))
     */
    @TableField("REGISTERED_CAPITAL")
    private String registeredCapital;

    /**
     * 企业成立日期
     */
    @TableField("COMPANY_CREATION_DATE")
    private LocalDate companyCreationDate;

    /**
     * 营业日期开始
     */
    @TableField("BUSINESS_START_DATE")
    private LocalDate businessStartDate;

    /**
     * 营业日期结束
     */
    @TableField("BUSINESS_END_DATE")
    private LocalDate businessEndDate;

    /**
     * 企业注册日期
     */
    @TableField("COMPANY_REGISTERED_DATE")
    private LocalDate companyRegisteredDate;

    /**
     * 营业地址（国家/地区）
     */
    @TableField("COMPANY_COUNTRY")
    private String companyCountry;

    /**
     * 营业地址（省份/州）
     */
    @TableField("COMPANY_PROVINCE")
    private String companyProvince;

    /**
     * 营业地址（城市）
     */
    @TableField("COMPANY_CITY")
    private String companyCity;

    /**
     * 详细地址
     */
    @TableField("COMPANY_ADDRESS")
    private String companyAddress;

    /**
     * 可供物料品类
     */
    @TableField("MATERIAL_CATEGORY")
    private String materialCategory;

    /**
     * 经营范围
     */
    @TableField("BUSINESS_SCOPE")
    private String businessScope;

    /**
     * 是否启用到期提醒
     */
    @TableField("IS_USE_REMINDER")
    private String isUseReminder;

    /**
     * 登记机关
     */
    @TableField("REGISTRATION_AUTHORITY")
    private String registrationAuthority;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 注册资金币种
     */
    @TableField("REGIST_CURRENCY")
    private String registCurrency;

    /**
     * 注册资金币种名称
     */
    @TableField("REGIST_CURRENCY_NAME")
    private String registCurrencyName;

    /**
     * 是否黑名单
     */
    @TableField("IS_BACKLIST")
    private String isBacklist;

    /**
     * 供应商分级
     */
    @TableField("VENDOR_CLASSIFICATION")
    private String vendorClassification;

    /**
     * 是否长期供应商（Y：是，N：否，默认否）
     */
    @TableField("IF_LONG_PERIOD")
    private String ifLongPeriod;

    /**
     * 黑名单更新人
     */
    @TableField("BACKLIST_UPDATED_BY")
    private String backlistUpdatedBy;

    /**
     * 黑名单更新时间
     */
    @TableField("BACKLIST_UPDATED_DATE")
    private Date backlistUpdatedDate;

    /**
     * 1、注册用户 2、绿色通道用户
     */
    @TableField("DATA_SOURCES")
    private String dataSources;

    /**
     * 黑名单生效日期
     */
    @TableField("BLACK_LIST_EFFECTIVE_DATE")
    private Date blackListEffectiveDate;

    /**
     * 核准日期
     */
    @TableField("APPROVING_DATE")
    private LocalDate approvingDate;

    /**
     * 审批信息
     */
    @TableField("APPROVAL_INFO")
    private String approvalInfo;

    /**
     * 注册申请号
     */
    @TableField("APPLICATION_NUMBER")
    private String applicationNumber;

    /**
     * 注册申请日期
     */
    @TableField("APPLICATION_DATE")
    private Date applicationDate;

    /**
     * 审批日期
     */
    @TableField("APPROVED_DATE")
    private LocalDate approvedDate;

    /**
     * 审批人
     */
    @TableField("APPROVED_BY")
    private String approvedBy;

    /**
     * 审批人账号
     */
    @TableField("APPROVER")
    private String approver;

    @TableField(exist = false)
    Map<String, Object> dimFieldContexts;

    /**
     * 可供品类
     */
    @TableField(exist = false)
    private List<CategoryRel> categoryRels;

//    /**
//     * 主营品类
//     */
//    @TableField(exist = false)
//    private List<OrgCategory> orgCategories;


    /**昵称*/
//    @TableField("NICKNAME")
//    private String nickname;
    /**邮箱*/
//    @TableField("EMAIL")
//    private String email;

    /**
     * 是否新供应商（Y：是，N：否，默认Y）
     */
    @TableField("IF_NEW_COMPANY")
    private String ifNewCompany;

    /**
     * 老SRM供应商ID(备用,暂不需要)
     */
    @TableField("OLD_COMPANY_ID")
    private String oldCompanyId;
    /**
     * 起草人意见
     */
    @TableField("CEEA_DRAFTSMAN_OPINION")
    private String ceeaDraftsmanOpinion;

    /**
     * 是否A级
     */
    @TableField("COMPANY_LEVEL")
    private String companyLevel;

    /**
     * 限期使用,Y:是,N:否
     */
    @TableField(value = "LIMIT_TIME_USE", exist = false)
    private String limitTimeUse;

    /**
     * 绿色通道引入供应商的原因详述
     */
    @TableField("GREEN_CHANNEL_REASON")
    private String greenChannelReason;

    /**
     * 创建人（展示用）
     */
    @TableField(exist = false)
    private String createdByName;

    @TableField(exist = false)
    private Long jobLogId;

    /**
     * 是否显示问卷调查
     */
    @TableField(exist = false)
    private Boolean showQuestSupplier;
}

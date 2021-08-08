package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.supplier.info.entity.CategoryRel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  功能名称 公司信息导入模型DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/21 16:07
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class CompanyInfoModelDTO implements Serializable {

    /*scc_sup_company_info*/
    @ExcelProperty(value = "新SRM供应商ID", index = 0)
    private String companyId;

    @ExcelProperty(value = "老SRM供应商编码（C开头的）", index = 1)
    private String companyCode;

    @ExcelProperty(value = "ERP供应商ID", index = 2)
    private String erpVendorId;

    @ExcelProperty(value = "供应商ERP编码", index = 3)
    private String erpVendorCode;

    @ExcelProperty(value = "企业名称", index = 4)
    @NotEmpty(message = "企业名称不能为空")
    private String companyName;

    @ExcelProperty(value = "供应商简称", index = 5)
    private String companyShortName;

    @ExcelProperty(value = "境内外关系", index = 6)
    private String overseasRelation;

    @ExcelProperty(value = "供应商业务类型", index = 7)
    private String ceeaSupBusinessType;

    @ExcelProperty(value = "企业性质", index = 8)//需要转换字典码
    private String companyTypeName;

    @ExcelProperty(value = "注册资本（万元）", index = 9)
    private String registeredCapital;

    @ExcelProperty(value = "成立日期", index = 10)
    private String companyCreationDate;

    @ExcelProperty(value = "统一社会信用代码", index = 11)
    private String lcCode;

    @ExcelProperty(value = "法定代表人", index = 12)
    private String legalPerson;

    @ExcelProperty(value = "营业日期从", index = 13)
    private String businessStartDate;

    @ExcelProperty(value = "营业日期至", index = 14)
    private String businessEndDate;

    @ExcelProperty(value = "注册地址（国家/地区）", index = 15)
    private String companyCountry;

    @ExcelProperty(value = "省份/州", index = 16)
    private String companyProvince;

    @ExcelProperty(value = "城市", index = 17)
    private String companyCity;

    @ExcelProperty(value = "详细地址", index = 18)
    private String companyAddress;

    @ExcelProperty(value = "商业模式", index = 19)
    private String ceeaBusinessModel;

    @ExcelProperty(value = "企业简介", index = 20)
    private String ceeaCompanyIntro;

    @ExcelProperty(value = "经营范围", index = 21)
    private String businessScope;

    @ExcelProperty(value = "厂房性质", index = 22)
    private String ceeaPlantType;

    @ExcelProperty(value = "厂房面积", index = 23)
    private String ceeaPlantArea;

    @ExcelProperty(value = "代理品牌", index = 24)
    private String ceeaAgentBrand;

    @ExcelProperty(value = "是否上市", index = 25)
    private String ceeaIfListed;

    @ExcelProperty(value = "上市时间", index = 26)
    private String ceeaListedTime;

    /*ceea_sup_company_info_detail*/
    @ExcelProperty(value = "员工数量", index = 27)
    private String staffQuantity;

    @ExcelProperty(value = "管理人员数量", index = 28)
    private String managerQuantity;

    @ExcelProperty(value = "技术人员数量", index = 29)
    private String technicistQuantity;

    @ExcelProperty(value = "生产人员数量", index = 30)
    private String productorQuantity;

    @ExcelProperty(value = "是否有研发部", index = 31)
    private String ifRad;

    @ExcelProperty(value = "研发人员数量", index = 32)
    private String radStaffQuantity;

    @ExcelProperty(value = "行业排名", index = 33)
    private String businessRank;

    @ExcelProperty(value = "市场份额", index = 34)
    private String marketShare;

    @ExcelProperty(value = "行业排名前三的企业名称", index = 35)
    private String internationalTopFive;

    /*scc_sup_operation_info*/
    @ExcelProperty(value = "年营业额", index = 36)
    private String ceeaYearTurnover;

    @ExcelProperty(value = "前三年销售额", index = 37)
    private String ceeaPreThreeYearsSale;

    @ExcelProperty(value = "前三年净利润", index = 38)
    private String ceeaPreThreeYearsProfit;

    @ExcelProperty(value = "前三年净资产负债率", index = 39)
    private String ceeaPreThreeYearsAal;

    @ExcelProperty(value = "经营范围和各项业务所占比重", index = 40)
    private String ceeaScopeBusinessRatio;

    @ExcelProperty(value = "是否具有供应太阳能行业相关物资的经验", index = 41)
    private String ceeaIfHasSolarPower;

    @ExcelProperty(value = "贵司的上下游产业布局", index = 42)
    private String ceeaUpDownLayout;

    @ExcelProperty(value = "3年内经营规模变化的预期", index = 43)
    private String ceeaThreeScaleChangeExp;

    @ExcelProperty(value = "降低客户采购成本的建议和要求", index = 44)
    private String ceeaReducePurCostAdvise;

    @ExcelProperty(value = "未来控制产品成本的计划和策略", index = 45)
    private String ceeaProCostPlanStrategy;

    @ExcelProperty(value = "年研发投入占销售额的比例", index = 46)
    private String ceeaRdSaleRate;

    @ExcelProperty(value = "请从技术角度解析贵司产品的优劣性", index = 47)
    private String ceeaProGoodBad;

    @ExcelProperty(value = "描述该产品后期研发方向(技术路线图)", index = 48)
    private String ceeaProTechRoute;

    @ExcelProperty(value = "描述贵公司研发团队构成情况及研发能力评估", index = 49)
    private String ceeaTeamShapeAbility;

    @ExcelProperty(value = "请提供我们所采购物资的价格构成要素,比例", index = 50)
    private String ceeaProPriceInscapeRate;

    @ExcelProperty(value = "哪些要素对于今后成本降低有较大推动作用", index = 51)
    private String ceeaReduceCostFactor;

    @ExcelProperty(value = "贵司将怎样迎合我司对于价格持续优化的需求", index = 52)
    private String ceeaHowUpgradePrice;

    @ExcelProperty(value = "售后能力", index = 53)
    private String ceeaAfterSalesAbility;

    @ExcelProperty(value = "状态", index = 54)
    private String status;

    @ExcelProperty( value = "错误提示信息",index = 55)
    private String errorMessage;

//    /**
//     * 公司网站
//     */
//    @TableField("CEEA_COMPANY_WEBSITE")
//    private String ceeaCompanyWebsite;
//
//    /**
//     * 是否有母公司
//     */
//    @TableField("CEEA_HAS_PARENT_COMPANY")
//    private String ceeaHasParentCompany;
//
//    /**
//     * 母公司名称
//     */
//    @TableField("CEEA_PARENT_COMPANY_NAME")
//    private String ceeaParentCompanyName;
//
//    /**
//     * 母公司统一信用代码
//     */
//    @TableField("CEEA_PARENT_COMPANY_LC_CODE")
//    private String ceeaParentCompanyLcCode;
//
//
//    /**
//     * 行业类型
//     */
//    @TableField("CEEA_INDUSTRY_TYPE")
//    private String ceeaIndustryType;
//
//    /**
//     * 企业名称（英文）
//     */
//    @TableField("COMPANY_EN_NAME")
//    private String companyEnName;
//
//    /**
//     * 境外关系名称
//     */
//    @TableField("OVERSEAS_RELATION_NAME")
//    private String overseasRelationName;
//
//
//    /**
//     * 上传(三证合一)文件ID
//     */
//    @TableField("BUSINESS_LICENSE_FILE_ID")
//    private String businessLicenseFileId;
//
//    /**
//     * 上传三证合一照片名称
//     */
//    @TableField("BUSINESS_LICENSE")
//    private String businessLicense;
//
//    /**
//     * 身份证号码
//     */
//    @TableField("ID_NUMBER")
//    private String idNumber;
//
//    /**
//     * DUNS编号
//     */
//    @TableField("DUNS_CODE")
//    private String dunsCode;
//
//    /**
//     * 经营状态
//     */
//    @TableField("COMPANY_STATUS")
//    private String companyStatus;
//
//    /**
//     * 经营状态名称
//     */
//    @TableField("COMPANY_STATUS_NAME")
//    private String companyStatusName;
//
//
//
//
//
//    /**
//     * 企业注册日期
//     */
//    @TableField("COMPANY_REGISTERED_DATE")
//    private LocalDate companyRegisteredDate;
//
//
//    /**
//     * 可供物料品类
//     */
//    @TableField("MATERIAL_CATEGORY")
//    private String materialCategory;
//
//
//    /**
//     * 登记机关
//     */
//    @TableField("REGISTRATION_AUTHORITY")
//    private String registrationAuthority;
//
//    /**
//     * 版本号
//     */
//    @TableField("VERSION")
//    private Long version;
//
//    /**
//     * 创建人ID
//     */
//    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
//    private Long createdId;
//
//    /**
//     * 创建人
//     */
//    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
//    private String createdBy;
//
//    /**
//     * 创建时间
//     */
//    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
//    private Date creationDate;
//
//    /**
//     * 创建人IP
//     */
//    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
//    private String createdByIp;
//
//    /**
//     * 最后更新人ID
//     */
//    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
//    private Long lastUpdatedId;
//
//    /**
//     * 最后更新人
//     */
//    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
//    private String lastUpdatedBy;
//
//    /**
//     * 最后更新时间
//     */
//    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
//    private Date lastUpdateDate;
//
//    /**
//     * 最后更新人IP
//     */
//    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
//    private String lastUpdatedByIp;
//
//    /**
//     * 注册资金币种
//     */
//    @TableField("REGIST_CURRENCY")
//    private String registCurrency;
//
//    /**
//     * 注册资金币种名称
//     */
//    @TableField("REGIST_CURRENCY_NAME")
//    private String registCurrencyName;
//
//    /**
//     * 是否黑名单
//     */
//    @TableField("IS_BACKLIST")
//    private String isBacklist;
//
//    /**
//     * 供应商分级
//     */
//    @TableField("VENDOR_CLASSIFICATION")
//    private String vendorClassification;
//
//    /**
//     * 是否长期供应商（Y：是，N：否，默认否）
//     */
//    @TableField("IF_LONG_PERIOD")
//    private String ifLongPeriod;
//
//    /**
//     * 黑名单更新人
//     */
//    @TableField("BACKLIST_UPDATED_BY")
//    private String backlistUpdatedBy;
//
//    /**
//     * 黑名单更新时间
//     */
//    @TableField("BACKLIST_UPDATED_DATE")
//    private Date backlistUpdatedDate;
//
//    /**
//     * 1、注册用户 2、绿色通道用户
//     */
//    @TableField("DATA_SOURCES")
//    private String dataSources;
//
//    /**
//     * 黑名单生效日期
//     */
//    @TableField("BLACK_LIST_EFFECTIVE_DATE")
//    private Date blackListEffectiveDate;
//
//    /**
//     * 核准日期
//     */
//    @TableField("APPROVING_DATE")
//    private LocalDate approvingDate;
//
//    /**
//     * 审批信息
//     */
//    @TableField("APPROVAL_INFO")
//    private String approvalInfo;
//
//    /**
//     * 注册申请号
//     */
//    @TableField("APPLICATION_NUMBER")
//    private String applicationNumber;
//
//    /**
//     * 注册申请日期
//     */
//    @TableField("APPLICATION_DATE")
//    private Date applicationDate;
//
//    /**
//     * 审批日期
//     */
//    @TableField("APPROVED_DATE")
//    private LocalDate approvedDate;
//
//    /**
//     * 审批人
//     */
//    @TableField("APPROVED_BY")
//    private String approvedBy;
//
//    /**
//     * 审批人账号
//     */
//    @TableField("APPROVER")
//    private String approver;
//
//    @TableField(exist = false)
//    Map<String, Object> dimFieldContexts;
//
//    /**
//     * 可供品类
//     */
//    @TableField(exist = false)
//    private List<CategoryRel> categoryRels;
//
////    /**
////     * 主营品类
////     */
////    @TableField(exist = false)
////    private List<OrgCategory> orgCategories;
//
//
//    /**昵称*/
////    @TableField("NICKNAME")
////    private String nickname;
//    /**邮箱*/
////    @TableField("EMAIL")
////    private String email;
//
//    /**
//     * 是否新供应商（Y：是，N：否，默认Y）
//     */
//    @TableField("IF_NEW_COMPANY")
//    private String ifNewCompany;

}

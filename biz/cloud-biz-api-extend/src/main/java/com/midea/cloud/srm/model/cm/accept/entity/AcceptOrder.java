package com.midea.cloud.srm.model.cm.accept.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  合同验收单 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-13 10:52:46
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_accept_order")
public class AcceptOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,验收单ID
     */
    @TableId("ACCEPT_ORDER_ID")
    private Long acceptOrderId;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 合同头ID
     */
    @TableField("CONTRACT_HEAD_ID")
    private Long contractHeadId;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 验收单号
     */
    @TableField("ACCEPT_NUMBER")
    private String acceptNumber;

    /**
     * 联系人名称
     */
    @TableField("CONTACT_NAME")
    private String contactName;

    /**
     * 联系电话
     */
    @TableField("MOBILE_NUMBER")
    private String mobileNumber;

    /**
     * 验收人账号
     */
    @TableField("ACCEPT_USER_NAME")
    private String acceptUserName;

    /**
     * 验收人ID
     */
    @TableField("ACCEPT_USER_ID")
    private Long acceptUserId;

    /**
     * 验收状态
     */
    @TableField("ACCEPT_STATUS")
    private String acceptStatus;

    /**
     * 验收日期
     */
    @TableField("ACCEPT_DATE")
    private LocalDate acceptDate;

    /**
     * 实际完成日期
     */
    @TableField("OVER_DATE")
    private LocalDate overDate;

    /**
     * 采购方负责人ID
     */
    @TableField("DUTY_USER_ID")
    private Long dutyUserId;

    /**
     * 采购方负责人名称
     */
    @TableField("DUTY_USER_NAME")
    private String dutyUserName;

    /**
     * 附件ID
     */
    @TableField("FILE_ID")
    private Long fileId;

    /**
     * 附件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 判断标准
     */
    @TableField("JUDGE_STANDARD")
    private String judgeStandard;

    /**
     * 质量标准
     */
    @TableField("QUALITY_STANDARD")
    private String qualityStandard;

    /**
     * 实际验收情况备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 驳回原因
     */
    @TableField("REJECT_REASON")
    private String rejectReason;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 业务实体ID
     */
    @TableField("CEEA_ORG_ID")
    private Long ceeaOrgId;

    /**
     * 业务实体编码
     */
    @TableField("CEEA_ORG_CODE")
    private String ceeaOrgCode;

    /**
     * 业务实体名称
     */
    @TableField("CEEA_ORG_NAME")
    private String ceeaOrgName;

    /**
     * 资产类别（关联字段的编号）
     */
    @TableField("CEEA_ASSET_TYPE")
    private String ceeaAssetType;

    /**
     * 资产名称
     */
    @TableField("CEEA_ASSET_NAME")
    private String ceeaAssetName;

    /**
     * 资产数量
     */
    @TableField("CEEA_ASSET_NUMBER")
    private BigDecimal ceeaAssetNumber;

    /**
     * 立项编号
     */
    @TableField("CEEA_PROJECT_NUMBER")
    private String ceeaProjectNumber;

    /**
     * 存放地点
     */
    @TableField("CEEA_STORE_CODE")
    private String ceeaStoreCode;

    /**
     * 使用部门
     */
    @TableField("CEEA_USER_DEPARTMENT")
    private String ceeaUserDepartment;

    /**
     * 使用人
     */
    @TableField("CEEA_USER_NAME")
    private String ceeaUserName;

    /**
     * 使用人员工工号
     */
    @TableField("CEEA_USER_EMP_NO")
    private String ceeaUserEmpNo;

    /**
     * 使用人ID
     */
    @TableField("CEEA_USER_ID")
    private Long ceeaUserId;

    /**
     * 币种
     */
    @TableField("CEEA_MONEY_TYPE")
    private String ceeaMoneyType;

    /**
     * 申请单号
     */
    @TableField("CEEA_APPLICTION_CODE")
    private String ceeaApplictionCode;

    /**
     * 验收类型
     */
    @TableField("CEEA_ACCEPT_TYPE")
    private String ceeaAcceptType;

    /**
     * 其他杂费
     */
    @TableField("CEEA_TOTAL_QUANTITY")
    private BigDecimal ceeaTotalQuantity;

    /**
     * 到货日期
     */
    @TableField("CEEA_ACCEPT_DATE")
    private Date ceeaAcceptDate;

    /**
     * 规格型号
     */
    @TableField("CEEA_SPECIFICATIONS_MODELS")
    private String ceeaSpecificationsModels;

    /**
     * 附带工具设备标识
     */
    @TableField("CEEA_TOOL_EQUIPMENT")
    private Integer ceeaToolEquipment;

    /**
     * 随附技术文件标识
     */
    @TableField("CEEA_TECHNICAL_DOCUMENTS")
    private Integer ceeaTechnicalDocuments;

    /**
     * 不动产大于500万标识
     */
    @TableField("CEEA_ASSET_QUALIFICATION")
    private Integer ceeaAssetQualification;

    /**
     * 起草人意见
     */
    @TableField("CEEA_DRAFTSMAN_OPINION")
    private String ceeaDraftsmanOpinion;
    /**
     * 验收申请单号
     */
    @TableField("CEEA_ACCEPT_APPLICATION_NUM")
    private String ceeaAcceptApplicationNum;
    /**
     * 申请时间
     */
    @TableField("CEEA_APPLICATION_DATE")
    private LocalDate  ceeaApplicationDate;
    /**
     * 员工工号
     */
    @TableField("CEEA_EMP_NO")
    private String ceeaEmpNo;
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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 服务区域
     */
    @TableField("CEEA_SERVICE_ZONE")
    private String ceeaServiceZone;
    /**
     * 服务日期从
     */
    @TableField("CEEA_SERVICE_DATE_START")
    private LocalDate ceeaServiceDateStart;
    /**
     * 服务日期至
     */
    @TableField("CEEA_SERVICE_DATE_END")
    private LocalDate ceeaServiceDateEnd;

}

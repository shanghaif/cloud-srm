package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderPaymentProvision;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *   需求池管理DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/29 13:43
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class RequirementManageDTO extends BaseDTO {

    /**
     * 主键ID
     */
    private Long requirementHeadId;

    /**
     * 采购需求编号(申请编号)
     */
    private String requirementHeadNum;

    /**
     * 处理状态
     */
    private String handleStatus;

    /**
     * 采购类型
     */
    private String ceeaPurchaseType;

    /**
     * 业务实体ID
     */
    private Long orgId;

    /**
     * 业务实体编码
     */
    private String orgCode;

    /**
     * 业务实体名称
     */
    private String orgName;

    /**
     * 部门ID
     */
    private String ceeaDepartmentId;

    /**
     * 部门编码
     */
    private String ceeaDepartmentCode;

    /**
     * 部门名称
     */
    private String ceeaDepartmentName;

    /**
     * 申请类型
     */
    private String ceeaPrType;

    /**
     * 项目名称
     */
    private String ceeaProjectName;

    /**
     * 项目编号
     */
    private String ceeaProjectNum;

    /**
     * 项目负责人用户ID
     */
    private Long ceeaProjectUserId;

    /**
     * 项目负责人用户名称
     */
    private String ceeaProjectUserNickname;

    /**
     * 立项流水号
     */
    private String ceeaProjectApprovalNum;

    /**
     * 是否募投
     */
    private String ceeaIfVote;

    /**
     * 募投项目名称
     */
    private String ceeaVoteProjectName;

    /**
     * 资产类别
     */
    private String ceeaAssetType;

    /**
     * 预算总金额
     */
    private BigDecimal ceeaTotalBudget;

    /**
     * 业务小类
     */
    private String ceeaBusinessSmall;

    /**
     * 业务小类编码
     */
    private String ceeaBusinessSmallCode;

    /**
     * 是否总部
     */
    private String ceeaIfHq;

    /**
     * 是否使用LOGO
     */
    private String ceeaIfUseLogo;

    /**
     * 紧急情况说明
     */
    private String ceeaUrgencyExplain;

    /**
     * 指定原因
     */
    private String ceeaAppointReason;

    /**
     * 起草人意见
     */
    private String ceeaDrafterOpinion;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /**
     * 备注
     */
    private String comments;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 采购需求创建方式 CREATE_NEW:手动新建  MERGE_NEW:合并需求行新建
     */
    private String createType;

    /**
     * 申请人姓名(创建人姓名)
     */
    private String createdFullName;

    /**
     * 申请人ID(申请人姓名)
     */
    private Long createdId;

    /**
     * 申请日期
     */
    private LocalDate applyDate;

    /**
     * 企业编码
     */
    private String companyCode;

    /**
     * 组织编码
     */
    private String organizationCode;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 主键ID  需求行ID
     */
    private Long requirementLineId;

    /**
     * 申请行号
     */
    private Integer rowNum;

    /**
     * 库存组织ID
     */
    private Long organizationId;

    /**
     * 库存组织名称
     */
    private String organizationName;

    /**
     * 组织全路径ID
     */
    private String fullPathId;

    /**
     * 物料小类ID(longi)
     */
    private Long categoryId;

    /**
     * 物料小类名称(longi)
     */
    private String categoryName;

    /**
     * 物料小类编码(longi)
     */
    private String categoryCode;

    /**
     * 是否目录化(longi)
     */
    private String ceeaIfDirectory;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 采购组织
     */
    private String purchaseOrganization;

    /**
     * 单位编码
     */
    private String unitCode;

    /**
     * 单位描述
     */
    private String unit;

    /**
     * 未税单价
     */
    private BigDecimal notaxPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxPrice;

    /**
     * 价格单位
     */
    private String priceUnit;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 币种
     */
    private String currency;

    /**
     * 当前库存量
     */
    private BigDecimal currentInventory;

    /**
     * 需求数量(申请数量)
     */
    private BigDecimal requirementQuantity;

    /**
     * 可下单数量
     */
    private BigDecimal orderQuantity;

    /**
     * 货源供应商 Y:有,N:无
     */
    private String haveSupplier;

    /**
     * 有效价格 Y:有,N:无
     */
    private String haveEffectivePrice;

    /**
     * 需求来源
     */
    private String requirementSource;

    /**
     * 收货工厂
     */
    private String receivedFactory;

    /**
     * 外部申请编号
     */
    private String externalApplyCode;

    /**
     * 外部申请行号
     */
    private Integer externalApplyRowNum;

    /**
     * 后续单据编号
     */
    private String followFormCode;

    /**
     * 后续单据名称
     */
    private String followFormName;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 需求日期
     */
    private LocalDate requirementDate;

    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 供应商管理采购员ID
     */
    private Long ceeaSupUserId;

    /**
     * 供应商管理采购员名称
     */
    private String ceeaSupUserNickname;

    /**
     * 供应商管理采购员账号
     */
    private String ceeaSupUserName;

    /**
     * 策略负责采购员ID
     */
    private Long ceeaStrategyUserId;

    /**
     * 策略负责采购员名称
     */
    private String ceeaStrategyUserNickname;

    /**
     * 策略负责采购员账号
     */
    private String ceeaStrategyUserName;

    /**
     * 采购履行采购员ID
     */
    private Long ceeaPerformUserId;

    /**
     * 采购履行采购员名称
     */
    private String ceeaPerformUserNickname;

    /**
     * 采购履行采购员账号
     */
    private String ceeaPerformUserName;

    /**
     * 已执行数量(已下单数量)
     */
    private BigDecimal ceeaExecutedQuantity;

    /**
     * 交货地点
     */
    private String ceeaDeliveryPlace;

    /**
     * 申请状态
     */
    private String applyStatus;

    /*业务主体ID集*/
    private List<Long> orgIds;

    /*库存组织ID集*/
    private List<Long> organizationIds;

    /*需求部门ID集*/
    private List<Long> ceeaDepartmentIds;

    /*申请人ID集(创建人ID集)*/
    private List<Long> createdIds;

    /*物料ID集*/
    private List<Long> materialIds;

    /*物料编码集*/
    private List<String> materialCodes;

    /*物料小类ID集*/
    private List<Long> categoryIds;

    /*需求行id集*/
    private List<Long> requirementLineIds;

    /*申请编号*/
    private List<String> requirementHeadNums;

    /*申请起始日期*/
    private LocalDate startApplyDate;

    /*申请截止日期*/
    private LocalDate endApplyDate;

    /*是否创建后续单据*/
    private String ifCreateFollowForm;

    /*是否创建寻源*/
    private String ifCreateBid;

    /*是否创建订单*/
    private String ifCreateOrder;

    /*合同编号*/
    private String contractNum;

    /*指定供应商ID*/
    private Long vendorId;

    /*指定供应商名称*/
    private String vendorName;

    /*指定供应商编码*/
    private String vendorCode;

    /**
     * 订单类型(采购申请转订单 使用)
     */
    private String orderType;

    /**
     * 是否供应商确认(采购申请转订单 使用)
     */
    private String ceeaIfSupplierConfirm;

    /**
     * 是否电站业务(采购申请转订单 使用)
     */
    private String ceeaIfPowerStationBusiness;

    /**
     * 是否寄售(采购申请转订单 使用)
     */
    private String ceeaIfConSignment;

    /**
     * 币种id(采购申请转订单 使用)
     */
    private Long currencyId;

    /**
     * 币种名称(采购申请转订单 使用)
     */
    private String currencyName;

    /**
     * 币种编码(采购申请转订单 使用)
     */
    private String currencyCode;

    /**
     * 最小起订量(采购申请转订单 使用)
     */
    private BigDecimal minOrderQuantity;

    /**
     * 采购订单类型条目标识（对接erp需要使用）(采购申请转订单 使用)
     */
    private String ceeaOrderTypeIdentification;

    /**
     * 需求人列
     */
    private String dmandLineRequest;

    /**
     * 付款条款(付款条件，付款方式，付款账期)
     */
    private List<OrderPaymentProvision> orderPaymentProvisionList;

    /**
     * 采购申请行关联合同
     */
    private List<ContractVo> contractVoList;
    /**
     * 能否创建寻源单据
     */
    private String canCreateSourcing;

    /**
     * 物料大类编码
     */
    private String bigCategoryCode;

    /**
     * 是否有剩余可下单数量
     */
    private String ifHaveOrderQuantity;

    /**
     * Erp申请编号
     */
    private String esRequirementHeadNum;

    //todo 是否价格库
    private String fromPrice;

    /**
     * 付款条款id
     */
    private String paymentTermId;
    /**
     * 查询的角色类型，非系统的角色类型，而是需求文档中的“特殊角色”
     */
    private String queryRoleCode;
    /**
     * 是否海鲜价 Y - N
     */
    private String isSeaFoodFormula;
    /**
     * 海鲜价时候的基价列表
     */
    private String priceJson;
    /**
     * 公式表达式
     */
    private String ceeaFormulaValue;

    /**
     * 公式表达式值
     */
    private String ceeaFormulaResult;
    /**
     * 公式id
     */
    private Long ceeaFormulaId;
    /**
     * 供应商要素报价明细
     */
    private String essentialFactorValues;
    /**
     * 存储公式结果id
     */
    private String calculateId;

    /**
     * 价格来源类型
     */
    private String ceeaPriceSourceType;

    /**
     * 价格来源单据id(contractMaterialId, priceLibraryId)
     */
    private Long ceeaPriceSourceId;
    /**
     * 是否暂挂
     */
    private String ifHold;
    /**
     * 合同序号
     */
    private String contractNo;

    /**
     * 退回操作人
     */
    private String returnOperator;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 项目编号
     */
    private String projectNumber;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 任务编号
     */
    private String taskNumber;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 数据来源
     */
    private String sourceSystem;
    /**
     * 贸易术语/贸易条款--来源价格库或合同物料
     */
    private String tradeTerm;
    /**
     * 价格库L/T
     */
    private String priceLt;

    /**
     * 本次下单数量(采购申请转订单 使用)
     */
    private BigDecimal thisOrderQuantity;

    /**
     * 配额比例(采购申请转订单 使用)
     */

    private BigDecimal quotaProportion;

    /**
     * 是否已分配供应商
     * @enum com.midea.cloud.common.enums.pm.pr.requirement.IfDistributionVendor
     */
    private String ifDistributionVendor;

    /**
     * 失败原因
     */
    private String errorMsg;

    private BigDecimal alreadyQuota; // 已分配配额比(计算该物料，该供应商已分配总数量的占比)
    private BigDecimal alreadyNum; // 已分配数量(取该物料，该供应商的已分配量)
    private BigDecimal orderQuota;// 本次分配数量(展示该供应商的计划分配数量，可修改)(订单数量)
    private BigDecimal totalDistribution; // 本次分配总量(汇总值,展示该供应商、该物料本次分配的总数量，即【本次分配数量】之和)
    private BigDecimal afterQuota; // 分配后配额 (展示经过本次分配后供应商的分配比例 分配后配额=（该供应商本次分配总量+该供应商已分配数量）/（该物料)
    private int allocationQuotaFlag; // 分配配额标识(0:未分配,1:已分配)
}

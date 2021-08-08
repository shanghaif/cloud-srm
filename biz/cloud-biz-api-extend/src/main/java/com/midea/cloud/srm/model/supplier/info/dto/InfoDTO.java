package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.change.entity.InfoChange;
import com.midea.cloud.srm.model.supplier.info.entity.*;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestSupplierVo;
import com.midea.cloud.srm.model.supplier.statuslog.entity.CompanyStatusLog;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
 *  修改日期: 2020-3-2 17:04
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class InfoDTO {
    // 公司基本信息
    private CompanyInfo companyInfo;
    // 公司基本信息从表
    private CompanyInfoDetail companyInfoDetail;
    // 管理体系信息
    private ManagementInfo managementInfo;
    // 管理体系信息认证附件
    private List<ManagementAttach> managementAttaches;
    // 银行 信息
    @NotNull(message = "供应商的银行信息不能为空")
    @Size(min = 1, message = "至少维护1条银行信息！")
    @Valid
    private List<BankInfo> bankInfos;
    // 地点 信息
    private List<SiteInfo> siteInfos;
    // 联系人信息
    @NotNull(message = "供应商的联系人信息不能为空")
    @Size(min = 1, message = "至少维护1条联系人信息！")
    @Valid
    private List<ContactInfo> contactInfos;
    // 财务信息
    private List<FinanceInfo> financeInfos;
    // 经营信息
    private OperationInfo operationInfo;
    // 经营信息质量控制
    private List<OperationQuality> operationQualities;
    //经营信息产品能力
    private List<OperationProduct> operationProducts;
    //经营信息设备信息
    private List<OperationEquipment> operationEquipments;
    // 组织与品类
    private List<OrgCategory> orgCategorys;
    // 合作组织信息
    private List<OrgInfo> orgInfos;
    // 业务信息(ceea客户情况)
    private List<BusinessInfo> businessInfos;
    // 用户信息
    private User userInfo;
    // 文件上传
    private List<Fileupload> fileUploads;
    //操作历史
    private List<CompanyStatusLog> companyStatusLogs;

    private String processType;//提交审批Y，废弃N

    /**
     * 用于接收通知业务人员字段 头信息
     */
    private InfoChange infoChange;

    private List<QuestSupplierVo> questSupplierList;

//    ceea,隆基不需要
//    private HolderInfo holderInfo;
//    private OtherInfo otherInfo;
//    private HonorInfo honorInfo;
}

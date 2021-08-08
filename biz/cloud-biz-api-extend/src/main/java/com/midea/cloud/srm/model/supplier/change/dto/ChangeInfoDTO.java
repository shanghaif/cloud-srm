package com.midea.cloud.srm.model.supplier.change.dto;

import com.midea.cloud.srm.model.supplier.change.entity.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  供应商信息变更DTO
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-11 11:44
 *  修改内容:
 * </pre>
 */
@Data
public class ChangeInfoDTO implements Serializable {

    private InfoChange infoChange;

    private CompanyInfoChange companyInfoChange;

    private List<ContactInfoChange>  contactInfoChanges;

    private List<BankInfoChange> bankInfoChanges;

    private List<SiteInfoChange> siteInfoChanges;

    private List<FinanceInfoChange> financeInfoChanges;

    private OtherInfoChange otherInfoChange;

    private HonorInfoChange honorInfoChange;

    private HolderInfoChange holderInfoChange;

    private BusinessInfoChange businessInfoChange;

    private OperationInfoChange operationInfoChange;

    private List<OrgCategoryChange> orgCategoryChanges;

    private List<OrgInfoChange> orgInfoChanges;

    private List<FileuploadChange> fileuploadChanges;

    private List<ManagementAttachChange> managementAttachChanges;

    private String processType;//提交审批Y，废弃N

}

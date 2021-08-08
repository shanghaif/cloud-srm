package com.midea.cloud.srm.model.suppliercooperate.deliver.dto;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReportOneDTO extends BaseEntity {

    private String requirementHeadNum;

    private String requirementDepartment;

    private String createdFullName;

    private String ceeaDepartmentName;

    private String ceeaPerformUserNickname;

    private String ceeaPrType;

    private String auditStatus;

    private String creationDateSq;

    private String creationDateHt;

    private String applyDate;

    private String totalAmount;

    private String rowNum;

    private String materialCode;

    private String materialName;

    private String categoryCode;

    private String unit;

    private String requirementQuantity;

    private String requirementDate;

    private String ceeaDeliveryPlace;

    private String comments;

    private String contractNo;

    private String contractCode;

    private String contractName;

    private String contractStatus;

    private String organizationName;

    private String orgName;

    private String orderNumber;

    private String orderStatus;

    private String orderType;

    private String vendorName;

    private String orderNum;

    private String ceeaAmountIncludingTax;

    private String receiveDate;

    private String receiveNum;

    private String xylx;
    private String xybh;
    private String xyzt;
    private String rkje;
}

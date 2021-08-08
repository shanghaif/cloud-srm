package com.midea.cloud.srm.model.supplier.riskraider.toEs.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.ScriptAssert;

import java.util.List;

/**
 * @Author vent
 * @Description
 **/
@Data
@Accessors(chain = true)
public class RaiderEsDto extends BaseDTO {
    private Long companyId;
    private String companyCode;
    private String companyName;
    private String keywords;
    private String interfaceCode;
    private String apiKey;
    private String enterpriseName;
    private String accountId;
    private String monitorId;
    private String accountType;
    private String eventSubType;
    private String eventScopeFlag;
    private String endDate;
    private String monitorCycle;
    private String riskSituation;
    private String eventLevel;
    private String associatedEnterprises;
    private String riskChange;
    private String tagList;
    private List<String>companyNames;
    private List<String>interfaceCodes;
    private List<Long>companyIds;
    private Long jobLogId;


}

/**
  * Copyright 2021 json.cn 
  */
package com.midea.cloud.srm.model.supplier.riskraider.r3;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2021-01-21 16:53:7
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class R3RootBean {

    private List<ShareholderInfo> shareholderInfo;
    private RegisterInfo registerInfo;
    private List<String> chattelMortgage;
    private IndustryInfo industryInfo;
    private List<LegalRepresentativeForeignInvestment> legalRepresentativeForeignInvestment;
    private List<String> abnormalOperation;
    private List<LegalRepresentativeForeignTenure> legalRepresentativeForeignTenure;
    private List<BranchInfo> branchInfo;
    private List<EquityFreeze> equityFreeze;
    private List<EquityPledge> equityPledge;
    private List<String> administrativePenalty;
    private List<EnterpriseForeignInvestment> enterpriseForeignInvestment;
    private List<KeyPersonnel> keyPersonnel;
    private List<ChangeRegistration> changeRegistration;

}
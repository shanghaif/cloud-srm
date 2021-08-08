package com.midea.cloud.common.enums.bpm;

import com.midea.cloud.common.enums.pm.po.StatusForBpmEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 *  模板id和功能模块枚举
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-20
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@Getter
public enum TempIdToModuleEnum {
    /** 模块*/
    //预付款申请
    ADVANCEPAYMENT("ADVANCEPAYMENT","ADVANCEPAYMENT"),
    //采购申请
    REQUIREMENT("174bee46872692fde82c0344fff9fa40","REQUIREMENT"),
    //采购订单
    ORDER("174d7f30422491c368401d64f758dc21","ORDER"),
    //合同审批
    CONTRACT("1751ac32ff05629370a37e44d9b88ba9","CONTRACT"),
    //供应商资质审查
    REVIEW("174dcdded4656e29589aa02433887a2f","BusinessType_QuaReview"),
    //供应商认证审批
    SUPPLIERAUTH("174dca621eb1350f09118034c7797601","SUPPLIERAUTH"),
    //供应商信息变更审批
    CHANGE("1751a78eab12cbfd18fc0f842369425d","CHANGE"),
    //供应商跨OU引入
    IMPORT("17511d4219977716e9fb15a415183103","IMPORT"),
    //固定资产&服务验收
    ACCEPT("1751aecac3c4d73be9421bf4e0fbf494","ACCEPT"),
    //供应商绿色通道审批
    COMPANYINFO("1754422fe66dd865bc59bdd47a5b6650","COMPANYINFO"),
    //供应商考核审批
    VENDORASSES("175447b2cbb4d69983dda524ded83e61","VENDORASSES"),
    //招议标文件审批
    FORCOMPARISON("17562b39900289ff05112854187a3097","FORCOMPARISON"),
    //询比价文件审批
    BIDING("175628784ff0eb21112d0ef4f4e9a36e","BIDING"),
    //寻源结果审批(价格审批单)
    APPROVAL("17562998d126b289aff61a048608f018","APPROVAL");

    private String code;
    private String value;

    public static String getValueByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        for (TempIdToModuleEnum bpmEnum:TempIdToModuleEnum.values()){
            if(code.equals(bpmEnum.getCode())){
                return bpmEnum.getValue();
            }
        }
        return null;
    }
}

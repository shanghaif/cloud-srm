package com.midea.cloud.srm.supcooperate.reconciliation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.OrgStatus;
import com.midea.cloud.common.enums.order.PenaltyInvoiceStatus;
import com.midea.cloud.common.enums.order.PenaltyReconciliatStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.ExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.Penalty;
import com.midea.cloud.srm.supcooperate.reconciliation.mapper.PenaltyMapper;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IPenaltyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 *  罚扣款单表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/7 16:02
 *  修改内容:
 * </pre>
 */
@Service
public class PenaltyServiceImpl extends ServiceImpl<PenaltyMapper, Penalty> implements IPenaltyService  {

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;

    @Override
    @Transactional
    public void saveBatchByExcel(List<Penalty> list) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        List<OrganizationUser> organizations = loginAppUser.getOrganizationUsers();
        List<DictItemDTO> penaltyTypeDictItems = baseClient.listAllByDictCode("PENALTY_TYPE");
        List<PurchaseCurrency> purchaseCurrencys = baseClient.listAllPurchaseCurrency();

        for(Penalty eord:list){
            CompanyInfo companyInfo1 = new CompanyInfo();
            companyInfo1.setCompanyName(eord.getVendorName());
            companyInfo1.setCompanyCode(eord.getVendorCode());
            companyInfo1.setStatus("APPROVED");
            CompanyInfo companyInfo = supplierClient.getCompanyInfoByParam(companyInfo1);
            if(companyInfo==null){
                eord.getErrorCell("vendorName").setComment("不存在名称为：'"+eord.getVendorName()+"'的已批准供应商");
            }else{
                eord.setVendorId(companyInfo.getCompanyId());
                eord.setVendorCode(companyInfo.getCompanyCode());
            }

            OrganizationUser organization = getOrganization(organizations,eord.getOrganizationName());
            if(organization==null){
                eord.getErrorCell("organizationName").setComment("不存在名称为：'"+eord.getOrganizationName()+"'的有效组织");
            }else{
                eord.setOrganizationId(organization.getOrganizationId());
            }

            //检验这个供应商有没有这个合作组织
            if(organization!=null&&companyInfo!=null){
                //检验这个供应商有没有这个合作组织
                OrgInfo orgInfo = supplierClient.getOrgInfoByOrgIdAndCompanyId(organization.getOrganizationId(),companyInfo.getCompanyId());
                if(!checkOrgEffective(orgInfo)){
                    eord.getErrorCell("organizationName").setComment("不存在名称为：'"+eord.getOrganizationName()+"'的有效组织");
                }
            }
            String penaltyType = getDictItemCode(penaltyTypeDictItems,eord.getPenaltyType());
            if(StringUtils.isBlank(penaltyType)){
                eord.getErrorCell("penaltyType").setComment("罚扣款类型不存在");
            }else{
                eord.setPenaltyType(penaltyType);
            }

            String rfq = getCurrencyCode(purchaseCurrencys,eord.getRfqSettlementCurrency());
            if(StringUtils.isBlank(rfq)){
                eord.getErrorCell("rfqSettlementCurrency").setComment("币种不存在");
            }else {
                eord.setRfqSettlementCurrency(rfq);
            }


            eord.setPenaltyId(IdGenrator.generate());
            eord.setPenaltyNumber(baseClient.seqGen("SEQ_SSC_PENALTY_NUM"));
            eord.setInvoiceStatus(PenaltyInvoiceStatus.UNMATCHED.name());
            eord.setReconciliatStatus(PenaltyReconciliatStatus.NOT_GENERATED.name());
        }
        if(ExcelUtil.getErrorCells(list).size()>0){
            return;
        }

        for(Penalty eord:list){
            try{
                this.save(eord);
            }catch(Exception e){
                e.printStackTrace();
                eord.getErrorCell("lineErrorContents").setLineErrorContents("该行上传失败，请重新处理后再导入");
            }
        }
    }

    /**
     * 检验合作组织的有效性
     * @return
     */
    private static boolean checkOrgEffective(OrgInfo orgInfo)  {
        if(orgInfo == null||orgInfo.getStartDate()==null||!StringUtils.equals(orgInfo.getServiceStatus(), OrgStatus.EFFECTIVE.name())){
            return false;
        }
        LocalDate now = LocalDate.now();
        if(orgInfo.getStartDate().isAfter(now)){
            return false;
        }
        if(orgInfo.getEndDate()!=null&&(now.isAfter(orgInfo.getEndDate())||now.isEqual(orgInfo.getEndDate()))){
            return false;
        }
        return true;
    }

    /**
     * 通过组织名称获取组织ID
     * @param organizations
     * @param organizationName
     * @return
     */
    private OrganizationUser getOrganization(List<OrganizationUser> organizations, String organizationName) {
        for(OrganizationUser organizationUser:organizations){
            if(StringUtils.equals(organizationName,organizationUser.getOrganizationName())){
                return organizationUser;
            }
        }
        return null;
    }

    /**
     * 通过币种名称查询币种的编码
     * @param purchaseCurrencys
     * @param currencyName
     * @return
     */
    private String getCurrencyCode(List<PurchaseCurrency> purchaseCurrencys, String currencyName) {
        for(PurchaseCurrency item:purchaseCurrencys){
            if(StringUtils.equals(currencyName,item.getCurrencyName())){
                return item.getCurrencyCode();
            }
        }
        return null;
    }
    /**
     * 通过字典类型名称查询字典类型编号
     * @param dictItems
     * @param dictItemName
     * @return
     */
    private String getDictItemCode(List<DictItemDTO> dictItems, String dictItemName) {
        for(DictItemDTO dictItemDTO:dictItems){
            if(StringUtils.equals(dictItemName,dictItemDTO.getDictItemName())){
                return dictItemDTO.getDictItemCode();
            }
        }
        return null;
    }
}

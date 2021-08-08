package com.midea.cloud.srm.cm.contract.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.cm.contract.mapper.ContractLineMapper;
import com.midea.cloud.srm.cm.contract.service.IContractLineService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
import com.midea.cloud.srm.model.cm.contract.entity.ContractLine;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalBiddingItemDto;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  合同行表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:12:07
 *  修改内容:
 * </pre>
*/
@Service
public class ContractLineServiceImpl extends ServiceImpl<ContractLineMapper, ContractLine> implements IContractLineService {
    @Autowired
    BaseClient baseClient;

    @Autowired
    InqClient inqClient;

    @Resource
    private PmClient pmClient;

    @Override
    public List<ContractMaterial> getMaterialsByContractMaterial(ContractMaterial material) {
        ApprovalBiddingItemDto approvalBiddingItemDto = new ApprovalBiddingItemDto();
        approvalBiddingItemDto.setOrgId(material.getOrganizationId());
        approvalBiddingItemDto.setVendorId(material.getVendorId());
        approvalBiddingItemDto.setCeeaSourceNo(material.getSourceNumber());
        List<ApprovalBiddingItem> approvalBiddingItems = inqClient.ceeaQueryByCm(approvalBiddingItemDto);
        List<ContractMaterial> contractMaterials = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(approvalBiddingItems)) {
            approvalBiddingItems.forEach(approvalBiddingItem -> {
                ContractMaterial contractMaterial = new ContractMaterial();
                contractMaterial.setCeeaSourceLineId(approvalBiddingItem.getApprovalBiddingItemId());
                contractMaterial.setSourceNumber(approvalBiddingItem.getCeeaSourceNo());
                contractMaterial.setSourceType(approvalBiddingItem.getSourceType());
                contractMaterial.setCeeaOuId(approvalBiddingItem.getOuId());
                contractMaterial.setCeeaOuName(approvalBiddingItem.getOuName());
                contractMaterial.setCeeaOuNumber(approvalBiddingItem.getOuNumber());
                contractMaterial.setBuId(approvalBiddingItem.getOrgId());
                contractMaterial.setBuName(approvalBiddingItem.getOrgName());
                contractMaterial.setBuCode(approvalBiddingItem.getOrgCode());
                contractMaterial.setInvId(approvalBiddingItem.getOrganizationId());
                contractMaterial.setInvCode(approvalBiddingItem.getOrganizationCode());
                contractMaterial.setInvName(approvalBiddingItem.getOrganizationName());
//                contractMaterial.setTradingLocations(approvalBiddingItem.getArrivalPlace()); // 交货地点
                contractMaterial.setMaterialId(approvalBiddingItem.getItemId());
                contractMaterial.setMaterialCode(approvalBiddingItem.getItemCode());
                contractMaterial.setMaterialName(approvalBiddingItem.getItemName());
                contractMaterial.setCategoryId(approvalBiddingItem.getCategoryId());
                contractMaterial.setCategoryCode(approvalBiddingItem.getCategoryCode());
                contractMaterial.setCategoryName(approvalBiddingItem.getCategoryName());
                contractMaterial.setUnitName(approvalBiddingItem.getUnit());
                contractMaterial.setContractQuantity(approvalBiddingItem.getNeedNum()); // 需求数量
                contractMaterial.setTaxedPrice(approvalBiddingItem.getTaxPrice()); // 含税单价
                contractMaterial.setVendorId(approvalBiddingItem.getVendorId());
                contractMaterial.setVendorCode(approvalBiddingItem.getVendorCode());
                contractMaterial.setVendorName(approvalBiddingItem.getVendorName());
                contractMaterial.setTaxKey(approvalBiddingItem.getTaxKey());
                contractMaterial.setTaxRate(approvalBiddingItem.getTaxRate());
                contractMaterial.setCurrency(approvalBiddingItem.getCurrencyName());
                contractMaterial.setApprovalBiddingItemId(approvalBiddingItem.getApprovalBiddingItemId());
                if(null != contractMaterial.getContractQuantity() && null != contractMaterial.getTaxedPrice()){
                    contractMaterial.setAmount(contractMaterial.getContractQuantity().multiply(contractMaterial.getTaxedPrice()));
                }
                contractMaterial.setTaxKey(approvalBiddingItem.getTaxKey());
                contractMaterial.setTaxRate(approvalBiddingItem.getTaxRate());
                if (null != contractMaterial.getAmount() &&
                        contractMaterial.getAmount().compareTo(BigDecimal.ZERO) != 0 &&
                        null != contractMaterial.getTaxRate() &&
                contractMaterial.getTaxRate().compareTo(BigDecimal.ZERO) != 0
                ) {
                    double taxRate = contractMaterial.getTaxRate().doubleValue();
                    double amount = contractMaterial.getAmount().doubleValue();
                    double unAmount = amount/(taxRate / 100 + 1);
                    contractMaterial.setUnAmount(new BigDecimal(unAmount));
                }
                contractMaterial.setStartDate(approvalBiddingItem.getStartTime());
                contractMaterial.setEndDate(approvalBiddingItem.getEndTime());
                // TODO 项目编号 , 项目名称
                RequirementHead requirementHead = pmClient.queryByRequirementHead(new RequirementHead().setRequirementHeadNum(approvalBiddingItem.getPurchaseRequestNum()));
                if(null != requirementHead){
                    contractMaterial.setItemNumber(requirementHead.getCeeaProjectNum());
                    contractMaterial.setItemName(requirementHead.getCeeaProjectName());
                }
                contractMaterials.add(contractMaterial);
            });
        }
        return contractMaterials;
    }

    //设置单位相关字段
    private void setUnit(ContractMaterial contractMaterial, String unitCode) {
        if (StringUtils.isNotBlank(unitCode)) {
            List<PurchaseUnit> purchaseUnits = baseClient.listPurchaseUnitByParam(new PurchaseUnit().setUnitCode(unitCode));
            if (!CollectionUtils.isEmpty(purchaseUnits)) {
                PurchaseUnit purchaseUnit = purchaseUnits.get(0);
                contractMaterial.setUnitName(purchaseUnit.getUnitName());
                contractMaterial.setUnitId(purchaseUnit.getUnitId());
                contractMaterial.setUnitCode(unitCode);
            }
        }
    }

    private void setCategory(ContractMaterial contractMaterial, Long categoryId) {
        if (categoryId != null) {
            PurchaseCategory purchaseCategory = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryId(categoryId));
            if (purchaseCategory != null) {
                contractMaterial.setCategoryId(categoryId)
                        .setCategoryCode(purchaseCategory.getCategoryCode())
                        .setCategoryName(purchaseCategory.getCategoryName());
            }
        }
    }
}

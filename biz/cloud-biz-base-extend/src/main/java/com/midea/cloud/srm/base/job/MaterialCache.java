package com.midea.cloud.srm.base.job;

import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/27 16:50
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialCache implements Serializable {

    List<MaterialItem> saveMaterialItemList = new ArrayList<>();    //要保存的物料集合
    List<MaterialItem> updateMaterialItemList = new ArrayList<>();  //更新的物料集合
    List<MaterialOrg> saveMaterialOrgList = new ArrayList<>();      //要保存的物料组织集合
    List<MaterialOrg> updateMaterialOrgList = new ArrayList<>();    //要更新的物料组织集合
    List<MaterialOrg> inDBMaterialOrgList = new ArrayList<>();     //数据库中存在的物料组织集合
    List<ErpMaterialItem> updateErpMaterialList = new ArrayList<>();    //要更新的erp推送数据集


    List<Organization> invs = new ArrayList<>();            //库存组织
    List<PurchaseUnit> purchaseUnits = new ArrayList<>();   //单位集合

    List<PurchaseCategory> categorys = new ArrayList<>();   //品类信息集合

    List<ErpMaterialItem> erpMaterialItemList = new ArrayList<>();//

}

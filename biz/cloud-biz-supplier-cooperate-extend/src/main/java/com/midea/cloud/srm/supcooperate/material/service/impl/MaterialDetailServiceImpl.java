package com.midea.cloud.srm.supcooperate.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialDetail;
import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialItem;
import com.midea.cloud.srm.supcooperate.material.mapper.MaterialDetailMapper;
import com.midea.cloud.srm.supcooperate.material.service.IMaterialDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.supcooperate.material.service.IMaterialItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
*  <pre>
 *  物料计划明细表 服务实现类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 23:38:17
 *  修改内容:
 * </pre>
*/
@Service
public class MaterialDetailServiceImpl extends ServiceImpl<MaterialDetailMapper, CeeaMaterialDetail> implements IMaterialDetailService {
    @Autowired
    IMaterialItemService iMaterialItemService;
    /**
     * 修改物料明细计划详情
     * @param materialDetail
     */
    @Transactional
    @Override
    public void updateBycount(CeeaMaterialDetail materialDetail) {
        this.updateById(materialDetail);
        //获取出此明细id外的相关明细数量总和
        QueryWrapper<CeeaMaterialDetail> wrapper = new QueryWrapper<>();
        wrapper.select("sum(REQUIREMENT_QUANTITY)AS REQUIREMENT_QUANTITY");
        wrapper.ne("ID",materialDetail.getId());
        wrapper.eq("MATERIAL_ITEM_ID",materialDetail.getMaterialItemId());
        List<CeeaMaterialDetail> list = this.list(wrapper);
        CeeaMaterialDetail byId = list.get(0);
        //设置对应的物料计划表参数
        CeeaMaterialItem materialItem = new CeeaMaterialItem();
        materialItem.setMaterialItemId(materialDetail.getMaterialItemId());
        materialItem.setSchTotalQuantity(byId.getRequirementQuantity().add(materialDetail.getRequirementQuantity()));
        //修改物料计划表的总数量
        iMaterialItemService.updateById(materialItem);
    }
}

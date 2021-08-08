package com.midea.cloud.srm.supcooperate.deliver.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderDeliveryDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 到货计划维护表 Mapper 接口
 * </p>
 *
 * @author zhi1772778785@163.com
 * @since 2020-08-27
 */
public interface DeliverPlanMapper extends BaseMapper<DeliverPlan> {
    void updateSchTotalQuantity(@Param("deliverPlanId") Long deliverPlanId);
    List<DeliverPlanDetail> getDeliverPlanList(DeliverPlanDTO wrapper);
}

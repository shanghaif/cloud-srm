package com.midea.cloud.srm.supcooperate.deliver.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderDeliveryDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderDeliveryDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 到货计划明细表 Mapper 接口
 * </p>
 *
 * @authorzhi1772778785@163.com
 * @since 2020-08-28
 */
public interface OrderDeliveryDetailMapper extends BaseMapper<OrderDeliveryDetail> {
    List<OrderDeliveryDetail> orderDeliveryDetailListPage(@Param(Constants.WRAPPER) QueryWrapper<OrderDeliveryDetailDTO>  wrapper);
    List<OrderDeliveryDetailDTO> orderDeliveryDetailListPageCopy(@Param(Constants.WRAPPER) QueryWrapper<OrderDeliveryDetailDTO>  wrapper);
}

package com.midea.cloud.srm.supcooperate.order.mapper;


import com.alibaba.dubbo.config.support.Parameter;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaAdjustDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  采购订单明细表 Mapper 接口
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:15
 *  修改内容:
 * </pre>
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    /**
     * 查询订单明细列表
     * @param orderRequestDTO
     * @return
     */
    List<OrderDetailDTO> findList(OrderRequestDTO orderRequestDTO);
    List<OrderDetailDTO> findListCopy(@Param(Constants.WRAPPER)QueryWrapper<OrderRequestDTO> wrapper);
    List<OrderDetailDTO> findListByCopy(@Param(Constants.WRAPPER)QueryWrapper<OrderRequestDTO> wrapper);

    /**
     * 查询订单明细
     * @param orderRequestDTO
     * @return
     */
    OrderDetail getJitOrderDetail(OrderRequestDTO orderRequestDTO);

    /**
     * 查询订单明细物料列表
     * @param
     * @return
     */
    List<OrderDetailDTO> findMaterialList(OrderRequestDTO filter);
    /**
     * 查询订单明细物料列表
     * @param
     * @return
     */
    List<OrderDetailDTO> findMaterialListCopy(@Param(Constants.WRAPPER) QueryWrapper<OrderRequestDTO> wrapper);
    /**
     * 查询订单明细物料列表2
     * @param orderRequestDTO
     * @return
     */
    List<OrderDetailDTO> listUnDeliveryPage(OrderRequestDTO orderRequestDTO);

    /**
     * 判断是否修改成功
     * @param orderDetailDTO
     * @return
     */
    List<OrderDetailDTO> falgList(OrderDetailDTO orderDetailDTO);

    BigDecimal aggregateAmount(Map<String,Object> param);


    OrderDetailDTO selectByIdAndCode(@Param("orderDetailId") Long orderDetailId);


    List<OrderDetail> getOrderDetailForCheck(@Param("requirementLineId") Long requirementLineId);

    List<OrderDetail> getOrderDetailByOrderStatus();

    List<OrderDetail> listValidOrderDetail(@Param("priceSourceIds") List<Long> priceSourceIds , @Param("orderDetail") OrderDetail orderDetail);

    /**
     * 送货通知单 - 选择订单明细
     * @param orderDetailDTO
     * @return
     */
    List<OrderDetailDTO> listInDeliveryNotice(OrderDetailDTO orderDetailDTO);

    /**
     * 订单明细列表查询
     * @param orderRequestDTO
     * @return
     */
    List<OrderDetailDTO> list(OrderRequestDTO orderRequestDTO);

    /**
     * 送货单 - 选择订单明细
     * @param orderRequestDTO
     * @return
     */
    List<OrderDetailDTO> listInDeliveryNote(OrderRequestDTO orderRequestDTO);

    List<OrderDetailDTO> findInDeliveryOrder(OrderRequestDTO orderRequestDTO);
}



package com.midea.cloud.srm.supcooperate.deliver.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderDeliveryDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderDeliveryDetail;
import com.midea.cloud.srm.supcooperate.deliver.service.IOrderDeliveryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  到货计划明细表 前端控制器
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-28 13:59:09
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/deliver/orderDeliveryDetail")
public class OrderDeliveryDetailController extends BaseController {

    @Autowired
    private IOrderDeliveryDetailService iOrderDeliveryDetailService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public OrderDeliveryDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderDeliveryDetailService.getById(id);
    }

    /**
     * 新增
     *
     * @param orderDeliveryDetail
     */
    @PostMapping("/add")
    public void add(@RequestBody OrderDeliveryDetail orderDeliveryDetail) {
        Long id = IdGenrator.generate();
        orderDeliveryDetail.setOrderDeliveryDetailId(id);
        iOrderDeliveryDetailService.save(orderDeliveryDetail);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderDeliveryDetailService.removeById(id);
    }

    /**
     * 修改
     *
     * @param orderDeliveryDetail
     */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderDeliveryDetail orderDeliveryDetail) {
        iOrderDeliveryDetailService.updateById(orderDeliveryDetail);
    }

    /**
     * 分页查询
     *
     * @param orderDeliveryDetailDTO
     * @return
     */
    @PostMapping("/orderDeliveryDetailListPage")
    public PageInfo<OrderDeliveryDetail> listPage(@RequestBody OrderDeliveryDetailDTO orderDeliveryDetailDTO) {
        Assert.notNull(orderDeliveryDetailDTO,"对应的明细不存在。");
        return iOrderDeliveryDetailService.orderDeliveryDetailListPage(orderDeliveryDetailDTO);
    }

    /**
     * 到货计划转送货单分页查询
     *
     * @param orderDeliveryDetailDTO
     * @return
     */
    @PostMapping("/orderDeliveryDetailListPageCopy")
    public PageInfo<OrderDeliveryDetailDTO> orderDeliveryDetailListPageCopy(@RequestBody OrderDeliveryDetailDTO orderDeliveryDetailDTO) {
        Assert.notNull(orderDeliveryDetailDTO,"对应的明细不存在。");
        PageUtil.startPage(orderDeliveryDetailDTO.getPageNum(), orderDeliveryDetailDTO.getPageSize());
        return new PageInfo<OrderDeliveryDetailDTO>(iOrderDeliveryDetailService.orderDeliveryDetailListPageCopy(orderDeliveryDetailDTO));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<OrderDeliveryDetail> listAll() {
        return iOrderDeliveryDetailService.list();
    }


    /**
     * 订单交货明细废弃
     */
    @GetMapping("/getAuditNote")
    public void getAuditNote(Long id){
        Assert.notNull(id,"请选择需要废弃的到货计划明细。");
        iOrderDeliveryDetailService.getAuditNote(id);
    }
}

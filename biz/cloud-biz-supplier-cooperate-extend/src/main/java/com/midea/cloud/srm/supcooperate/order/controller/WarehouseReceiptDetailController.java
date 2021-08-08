package com.midea.cloud.srm.supcooperate.order.controller;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import com.midea.cloud.srm.supcooperate.order.service.IWarehouseReceiptDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.io.IOException;


/**
* <pre>
 *  入库单行表 前端控制器
 * </pre>
*
* @author chenwj92@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 19, 2021 1:40:47 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/order/warehousereceiptdetail")
public class WarehouseReceiptDetailController extends BaseController {

    @Autowired
    private IWarehouseReceiptDetailService warehouseReceiptDetailService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public WarehouseReceiptDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return warehouseReceiptDetailService.getById(id);
    }

    /**
    * 新增
    * @param warehouseReceiptDetail
    */
    @PostMapping("/add")
    public void add(@RequestBody WarehouseReceiptDetail warehouseReceiptDetail) {
        Long id = IdGenrator.generate();
        warehouseReceiptDetail.setWarehouseReceiptDetailId(id);
        warehouseReceiptDetailService.save(warehouseReceiptDetail);
    }

    /**
     * 批量新增或者修改
     * @param warehouseReceiptDetailList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<WarehouseReceiptDetail> warehouseReceiptDetailList) throws IOException{
         warehouseReceiptDetailService.batchSaveOrUpdate(warehouseReceiptDetailList);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        warehouseReceiptDetailService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        warehouseReceiptDetailService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param warehouseReceiptDetail
    */
    @PostMapping("/modify")
    public void modify(@RequestBody WarehouseReceiptDetail warehouseReceiptDetail) {
        warehouseReceiptDetailService.updateById(warehouseReceiptDetail);
    }

    /**
    * 分页查询
    * @param warehouseReceiptDetail
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<WarehouseReceiptDetail> listPage(@RequestBody WarehouseReceiptDetail warehouseReceiptDetail) {
        return warehouseReceiptDetailService.listPage(warehouseReceiptDetail);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<WarehouseReceiptDetail> listAll() {
        return warehouseReceiptDetailService.list();
    }
}

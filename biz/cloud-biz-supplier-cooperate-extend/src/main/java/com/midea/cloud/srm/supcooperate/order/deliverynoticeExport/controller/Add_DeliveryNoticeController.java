package com.midea.cloud.srm.supcooperate.order.deliverynoticeExport.controller;
import com.midea.cloud.srm.model.suppliercooperate.order.deliverynoticeExport.entity.DeliveryNotice;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.supcooperate.order.deliverynoticeExport.service.Add_DeliveryNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
* <pre>
 *  导出 前端控制器
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 5, 2021 10:54:16 AM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/deliverynotice")
public class Add_DeliveryNoticeController extends BaseController {

    @Autowired
    private Add_DeliveryNoticeService deliveryNoticeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public DeliveryNotice get(Long id) {
        Assert.notNull(id, "id不能为空");
        return deliveryNoticeService.getById(id);
    }
    /**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody DeliveryNotice excelParam, HttpServletResponse response) throws Exception {
        deliveryNoticeService.exportExcel(excelParam,response);
    }
}

package com.midea.cloud.srm.supcooperate.orderreceive.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.OrderReceiveDTO;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.entity.OrderReceive;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;

import com.midea.cloud.srm.supcooperate.orderreceive.service.OrderReceiveService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
* <pre>
 *  订单管理-》收货明细
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 30, 2021 10:31:54 AM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/pm/receive")
public class OrderReceiveController extends BaseController {

    @Autowired
    private OrderReceiveService orderReceiveService;
    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrderReceive get(Long id) {
        Assert.notNull(id, "id不能为空");
        return orderReceiveService.getById(id);
    }
    /**
     * 批量新增或者修改
     * @param orderReceiveList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<OrderReceive> orderReceiveList) throws Exception{
        orderReceiveService.batchSaveOrUpdate(orderReceiveList);
    }
    /**
     * 批量删除
     * @param ids
     */
    @PostMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        orderReceiveService.batchDeleted(ids);
    }
    /**
    * 分页查询
    * @param orderReceiveDTO
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrderReceive> listPage(@RequestBody OrderReceiveDTO orderReceiveDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "不是采购商");
        }
        orderReceiveDTO.setCreatedId(loginAppUser.getUserId());
        return orderReceiveService.listPage(orderReceiveDTO);
    }
    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/exportExcelTemplate")
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        orderReceiveService.exportExcelTemplate(response);
    }
    /**
     * 导入文件内容
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return orderReceiveService.importExcel(file,fileupload);
    }
    /**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody OrderReceiveDTO excelParam, HttpServletResponse response) throws Exception {
        orderReceiveService.exportExcel(excelParam,response);
    }
}

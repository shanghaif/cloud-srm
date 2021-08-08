package com.midea.cloud.srm.logistics.baseprice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.logistics.baseprice.service.BasePriceService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.baseprice.entity.BasePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


/**
* <pre>
 *  物流招标基础价格 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 26, 2021 4:32:59 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base/baseprice")
public class BasePriceController extends BaseController {

    @Autowired
    private BasePriceService basePriceService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BasePrice get(Long id) {
        Assert.notNull(id, "id不能为空");
        return basePriceService.getById(id);
    }

    /**
    * 新增
    * @param basePrice
    */
    @PostMapping("/add")
    public Long add(@RequestBody BasePrice basePrice) {
        Long id = IdGenrator.generate();
        basePrice.setBasePriceId(id);
        String nickname = AppUserUtil.getLoginAppUser().getNickname();
        basePrice.setCreatedByName(nickname);
        basePrice.setLastUpdatedByName(nickname);
        basePriceService.save(basePrice);
        return id;
    }

    /**
     * 批量新增
     * @param basePriceList
     */
    @PostMapping("/batchAdd")
    public void batchAdd(@RequestBody List<BasePrice> basePriceList) throws IOException{
        basePriceService.batchAdd(basePriceList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        basePriceService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        basePriceService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param basePrice
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BasePrice basePrice) {
        String nickname = AppUserUtil.getLoginAppUser().getNickname();
        basePrice.setLastUpdatedByName(nickname);
        basePriceService.updateById(basePrice);
    }


    /**
     * 批量修改
     * @param basePriceList
     */
    @PostMapping("/batchModify")
    public void batchModify(@RequestBody List<BasePrice> basePriceList) throws IOException{
        basePriceService.batchUpdate(basePriceList);
    }
    /**
    * 分页查询
    * @param basePrice
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BasePrice> listPage(@RequestBody BasePrice basePrice) {
        return basePriceService.listPage(basePrice);
    }

//    /**
//    * 查询所有
//    * @return
//    */
//    @PostMapping("/listAll")
//    public List<BasePrice> listAll() {
//        return basePriceService.list();
//    }

//    /**
//     * 导入文件模板下载
//     * @return
//     */
//    @RequestMapping("/exportExcelTemplate")
//    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
//        basePriceService.exportExcelTemplate(response);
//    }
//
//    /**
//     * 导入文件内容
//     * @param file
//     */
//    @RequestMapping("/importExcel")
//    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
//        return basePriceService.importExcel(file,fileupload);
//    }
//
//    /**
//     * 导出文件
//     * @param excelParam
//     * @param response
//     * @throws Exception
//     */
//    @RequestMapping("/exportExcel")
//    public void exportExcel(@RequestBody BasePrice excelParam, HttpServletResponse response) throws Exception {
//        basePriceService.exportExcel(excelParam,response);
//    }

    /**
     * 批量生效
     * @param basePriceIds
     */
    @PostMapping("/effect")
    public void effect(@RequestBody List<Long> basePriceIds){
        basePriceService.effect(basePriceIds);
    }

    /**
     * 批量失效
     * @param basePriceIds
     */
    @PostMapping("/invalid")
    public void invalid(@RequestBody List<Long> basePriceIds){
        basePriceService.invalid(basePriceIds);
    }
}

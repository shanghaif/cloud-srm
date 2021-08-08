package com.midea.cloud.srm.price.baseprice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.price.baseprice.entity.BasePrice;
import com.midea.cloud.srm.price.baseprice.service.IBasePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
*  <pre>
 *  基价表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:16:13
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base-price")
public class BasePriceController extends BaseController {

    @Autowired
    private IBasePriceService iBasePriceService;

    /**
     * 分页查询
     * @param basePrice
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<BasePrice> listPage(@RequestBody BasePrice basePrice) {
        return iBasePriceService.listPage(basePrice);
    }


    /**
    * 修改
    * @param basePrice
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody BasePrice basePrice) {
        iBasePriceService.updateById(basePrice);
        return basePrice.getBasePriceId();
    }

    /**
     * 生效
     * @param basePriceId
     */
    @GetMapping("/takeEffect")
    public void takeEffect(Long basePriceId){
        iBasePriceService.takeEffect(basePriceId);
    }

    /**
     * 失效
     * @param basePriceId
     */
    @GetMapping("/failure")
    public void failure(Long basePriceId){
        iBasePriceService.failure(basePriceId);
    }

    /**
     * 删除
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBasePriceService.removeById(id);
    }

    /**
     * 新增
     * @param basePrice
     */
    @PostMapping("/add")
    public Long add(@RequestBody BasePrice basePrice) {
        return null;
    }

    /**
     * 根据成本要素编码和属性值查询
     */
    @PostMapping("/queryBy")
    public BasePrice queryBy(@RequestBody BasePrice basePrice) {
        return iBasePriceService.queryBy(basePrice);
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(@RequestBody BasePrice basePrice,HttpServletResponse response) throws IOException {
        iBasePriceService.importModelDownload(basePrice, response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iBasePriceService.importExcel(file, fileupload);
    }

}

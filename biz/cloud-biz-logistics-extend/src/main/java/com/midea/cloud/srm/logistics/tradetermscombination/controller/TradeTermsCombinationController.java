package com.midea.cloud.srm.logistics.tradetermscombination.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.srm.logistics.tradetermscombination.service.TradeTermsCombinationService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.tradetermscombination.dto.TradeTermsCombinationDto;
import com.midea.cloud.srm.model.logistics.tradetermscombination.entity.TradeTermsCombination;
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
 *  贸易术语组合 前端控制器
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Mar 3, 2021 11:52:27 AM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/tradetermscombination")
public class TradeTermsCombinationController extends BaseController {

    @Autowired
    private TradeTermsCombinationService tradeTermsCombinationService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public TradeTermsCombination get(Long id) {
        Assert.notNull(id, "id不能为空");
        return tradeTermsCombinationService.getById(id);
    }


    /**
     * 批量新增或者修改
     * @param tradeTermsCombinationList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<TradeTermsCombination> tradeTermsCombinationList) throws IOException{
         tradeTermsCombinationService.batchSaveOrUpdate(tradeTermsCombinationList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        tradeTermsCombinationService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @PostMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        tradeTermsCombinationService.batchDeleted(ids);
    }
//    /**
//    * 修改
//    * @param tradeTermsCombination
//    */
//    @PostMapping("/modify")
//    public void modify(@RequestBody TradeTermsCombination tradeTermsCombination) {
//        tradeTermsCombinationService.updateById(tradeTermsCombination);
//    }

    /**
    * 分页查询
    * @param tradeTermsCombination
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<TradeTermsCombination> listPage(@RequestBody TradeTermsCombination tradeTermsCombination) {
        return tradeTermsCombinationService.listPage(tradeTermsCombination);
    }

//    /**
//    * 查询所有
//    * @return
//    */
//    @PostMapping("/listAll")
//    public List<TradeTermsCombination> listAll() {
//        return tradeTermsCombinationService.list();
//    }
    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/exportExcelTemplate")
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        tradeTermsCombinationService.exportExcelTemplate(response);
    }
    /**
     * 导入文件内容
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return tradeTermsCombinationService.importExcel(file,fileupload);
    }

    /**
     * 定义生效(批量)
     *
     * @param tradeTermsCombinationIds
     */
    @PostMapping("/effectiveTradeTermsCombination")
    public void effectiveTradeTermsCombination(@RequestBody List<Long> tradeTermsCombinationIds) {
        tradeTermsCombinationService.updateStatus(tradeTermsCombinationIds, LogisticsStatus.EFFECTIVE.getValue());
    }

    /**
     * 定义失效(批量)
     *
     * @param tradeTermsCombinationIds
     */
    @PostMapping("/inEffectiveTradeTermsCombination")
    public void inEffectiveTradeTermsCombination(@RequestBody List<Long> tradeTermsCombinationIds) {
        tradeTermsCombinationService.updateStatus(tradeTermsCombinationIds, LogisticsStatus.INEFFECTIVE.getValue());
    }

    /**
     * 查找生效的贸易术语组合
     * @return
     */
    @GetMapping("/queryTradeTermsCombinationDto")
    public List<TradeTermsCombinationDto> queryTradeTermsCombinationDto(){
        return tradeTermsCombinationService.queryTradeTermsCombinationDto();
    }



}

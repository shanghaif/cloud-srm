package com.midea.cloud.srm.sup.riskraider.toEs.controller;

import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.toEs.dto.RaiderEsDto;
import com.midea.cloud.srm.sup.riskraider.toEs.service.RaiderInfoToEsService;
import com.midea.cloud.srm.sup.riskraider.toEs.service.SearchFromEsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

/**
*  <pre>
 *  企业财务信息表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:42:20
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/raiderInfo")
public class RaiderInfoToEsController extends BaseController {

    @Autowired
    private RaiderInfoToEsService raiderInfoToEsService;
    @Autowired
    private SearchFromEsService searchFromEsService;

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR1ToEs")
    public void saveR1ToEs(@RequestBody RaiderEsDto raiderEsDto) throws Exception {
        raiderInfoToEsService.saveR1ToEs(raiderEsDto);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR2ToEs")
    public void saveR2ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR2ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR3ToEs")
    public void saveR3ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR3ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR4ToEs")
    public void saveR4ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR4ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR5ToEs")
    public void saveR5ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR5ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR6ToEs")
    public void saveR6ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR6ToEs(companyInfo);
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR7ToEs")
    public void saveR7ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR7ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR8ToEs")
    public void saveR8ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR8ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR9ToEs")
    public void saveR9ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR9ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR10ToEs")
    public void saveR10ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR10ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR11ToEs")
    public void saveR11ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.saveR11ToEs(companyInfo);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR12ToEs")
    public void saveR12ToEs(@RequestBody RaiderEsDto raiderEsDto) throws Exception {
        raiderInfoToEsService.saveR12ToEs(raiderEsDto);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR13ToEs")
    public void saveR13ToEs(@RequestBody RaiderEsDto raiderEsDto) throws Exception {
        raiderInfoToEsService.saveR13ToEs(raiderEsDto);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR14ToEs")
    public void saveR14ToEs(@RequestBody RaiderEsDto raiderEsDto) throws Exception {
        raiderInfoToEsService.saveR14ToEs(raiderEsDto);
    }
    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveToEs")
    public void saveToEs(@RequestBody RaiderEsDto raiderEsDto) throws Exception {
        raiderInfoToEsService.saveToEs(raiderEsDto);
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/searchFromEs")
    public Collection<Object> search(@RequestBody RaiderEsDto raiderEsDto) throws Exception {
        return searchFromEsService.search(raiderEsDto);
    }
    /**
     * 企业添加监控规范
     * @return
     */
    @PostMapping("/addMonitorEnterprise")
    public void addMonitorEnterprise(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.addMonitorEnterprise(companyInfo);
    }

    /**
     * 企业添加监控规范
     * @return
     */
    @PostMapping("/cancelMonitorEnterprise")
    public void cancelMonitorEnterprise(@RequestBody CompanyInfo companyInfo) throws Exception {
        raiderInfoToEsService.cancelMonitorEnterprise(companyInfo);
    }

    /**
     * 企业添加监控规范
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    public void saveOrUpdateBatch(@RequestBody RaiderEsDto raiderEsDto) throws Exception {
        raiderInfoToEsService.saveOrUpdateBatch(raiderEsDto);
    }

    @GetMapping("/queryFromEs")
    public HashMap<String, JSONObject> queryFromEs(@RequestParam("companyId") Long companyId) throws InterruptedException {
        return raiderInfoToEsService.queryFromEs(companyId);
    }




}

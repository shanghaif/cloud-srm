package com.midea.cloud.srm.supauth.entry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.entry.dto.EntryCategoryConfigSaveResultDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfig;
import com.midea.cloud.srm.supauth.entry.service.IEntryCategoryConfigService;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigService;

/**
*  <pre>
 *  供应商准入流程行表（品类配置） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-15 11:28:48
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/entry/entry-category-config")
public class EntryCategoryConfigController extends BaseController {

    @Autowired
    private IEntryCategoryConfigService iEntryCategoryConfigService;

    @Autowired
    private IEntryConfigService iEntryConfigService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public EntryCategoryConfig get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iEntryCategoryConfigService.getById(id);
    }

    /**
    * 新增
    * @param entryCategoryConfig
    */
    @PostMapping("/add")
    public void add(@RequestBody EntryCategoryConfig entryCategoryConfig) {
        Long id = IdGenrator.generate();
        entryCategoryConfig.setEntryCategoryConfigId(id);
        iEntryCategoryConfigService.save(entryCategoryConfig);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iEntryCategoryConfigService.removeById(id);
    }

    /**
    * 修改
    * @param entryCategoryConfig
    */
    @PostMapping("/modify")
    public void modify(@RequestBody EntryCategoryConfig entryCategoryConfig) {
        iEntryCategoryConfigService.updateById(entryCategoryConfig);
    }

    /**
    * 分页查询
    * @param entryCategoryConfig
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<EntryCategoryConfig> listPage(@RequestBody EntryCategoryConfig entryCategoryConfig) {
        PageUtil.startPage(entryCategoryConfig.getPageNum(), entryCategoryConfig.getPageSize());
        QueryWrapper<EntryCategoryConfig> wrapper = new QueryWrapper<EntryCategoryConfig>(entryCategoryConfig);
        return new PageInfo<EntryCategoryConfig>(iEntryCategoryConfigService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<EntryCategoryConfig> listAll() { 
        return iEntryCategoryConfigService.list();
    }

    /**
     * 根据头表Id查询维护品类列表
     * @modifiedBy xiexh12@meicloud.com 2020-09-15
     */
    @GetMapping("/getCategoryListByEntryId")
    public PageInfo<EntryCategoryConfig> getCategoryListByEntryId(@RequestParam("entryConfigId") Long entryConfigId) {
        Assert.notNull(entryConfigId, "entryId为空！");
        return iEntryCategoryConfigService.getCategoryListByEntryId(entryConfigId);
    }

    /**
     * 根据选取的品类列表和头表Id对维护品类集进行保存
     * @modifiedBy xiexh12@meicloud.com 2020-09-15
     */
    @PostMapping("/saveEntryCategoryList")
    public EntryCategoryConfigSaveResultDTO saveEntryCategoryList(@RequestBody EntryConfig entryConfig) {
        Long entryId = entryConfig.getEntryConfigId();
        String quaReviewType = iEntryConfigService.getById(entryId).getQuaReviewType();
        List<EntryCategoryConfig> categoryConfigList = entryConfig.getEntryCategoryConfigList();
        return iEntryCategoryConfigService.saveEntryCategoryList(categoryConfigList, entryId, quaReviewType);
    }

    /**
     * 导数据用（请忽略）
     */
    @PostMapping("/importEntryCategoryConfig")
    public void importEntryCategoryConfig(){
        iEntryCategoryConfigService.importEntryCategoryConfig();
    }

}

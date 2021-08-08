package com.midea.cloud.srm.base.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.CacheClear;
import com.midea.cloud.common.annotation.CacheData;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemQueryDTO;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  字典条目 前端控制器
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-19 10:33:18
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/dict/base-dict-item")
public class DictItemController extends BaseController {

    @Autowired
    private IDictItemService iDictItemService;

    /**
     * 获取
     * @param id
     */
    @GetMapping("/get")
    public DictItem get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDictItemService.getById(id);
    }

    /**
     * 新增
     * @param dictItem
     */
    @PostMapping("/add")
    public void add(DictItem dictItem) {
        Long id = IdGenrator.generate();
        dictItem.setDictItemId(id);
        iDictItemService.save(dictItem);
    }

    /**
     * 删除
     * @param id
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDictItemService.removeById(id);
    }

    /**
     * 修改
     * @param dictItem
     */
    @PostMapping("/modify")
    public void modify(DictItem dictItem) {
        iDictItemService.updateById(dictItem);
    }

    /**
     * 分页查询
     * @param dictItem
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DictItem> listPage(DictItem dictItem) {
        PageUtil.startPage(dictItem.getPageNum(), dictItem.getPageSize());
        QueryWrapper<DictItem> wrapper = new QueryWrapper<DictItem>(dictItem);
        return new PageInfo<DictItem>(iDictItemService.list(wrapper));
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/listAll")
    public List<DictItem> listAll() {
        return iDictItemService.list();
    }

    /**
     * 保存与更新
     * @param dictItem
     * @return
     */
    @PostMapping("/saveOrUpdateDictItem")
    @CacheClear(keyName = RedisKey.DICT_CACHE_KEY)
    public void saveOrUpdateDictItem(@RequestBody DictItem dictItem){
        iDictItemService.saveOrUpdateDictItem(dictItem);
    };


    /**
     * 根据条件分页查询
     * @param dictItem
     * @return
     */
    @PostMapping("/queryPageByConditions")
    public PageInfo<DictItem> queryPageByConditions(@RequestBody DictItem dictItem){
        return iDictItemService.queryPageByConditions(dictItem, dictItem.getPageNum(), dictItem.getPageSize());
    }

    /**
     *根据请求返回带字典信息的条目
     */
    @GetMapping("/queryById")
    public DictItemDTO queryById(Long id){
        return  iDictItemService.queryById(id);
    }

    /**
     * 字典右边导入模板下载
     */
    @RequestMapping("/rightImportExcelTemplate")
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        iDictItemService.rightImportExcelTemplate(response);
    }

    /**
     * 字典右边导出
     */
    @PostMapping("/rightExportExcel")
    public void exportExcel(@RequestBody List<Long>ids) throws IOException {
        if( ids.size() == 0) {
            Assert.isTrue(false,"请勾选头字典");
        }
        iDictItemService.exportExcel(ids, HttpServletHolder.getResponse());
    }
    /**
     * 字典右边导入
     */
    @PostMapping("rightImportExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iDictItemService.rightImportExcel(file,fileupload);
    }

    /**
     * 字典code来查询字典条目
     * @return
     */
    @GetMapping("/listAllByDictCode")
    public List<DictItemDTO> listAllByDictCode(String dictCode) {
        return iDictItemService.listAllByDictCode(dictCode);
    }

    /**
     * 多个字典code来查询字典条目
     * @return
     */
    @PostMapping("/listByDictCode")
    public List<DictItemDTO> listByDictCode(@RequestBody List<String> dictCodes) {
        return iDictItemService.listByDictCode(dictCodes);
    }

    /**
     * 多条件来查询字典条目
     * @return
     */
    @PostMapping("/listAllByParam")
    @CacheData(keyName = RedisKey.DICT_CACHE_KEY,cacheTime = 3600*6,interfaceName = "字典查询", i18n = true)
    public List<DictItemDTO> listAllByParam(@RequestBody DictItemDTO requestDto) {
        return iDictItemService.listAllByParam(requestDto);
    }

    /**
     * 多条件来查询多条字典条目
     * @return
     */
    @PostMapping("/listAllByListParam")
    public List<Map<String,Object>> listAllByListParam(@RequestBody List<DictItemDTO> requestDtos) {
        return iDictItemService.listAllByListParam(requestDtos);
    }

    @PostMapping("/getDictItemsByDictCodeAndDictItemNames")
    public List<DictItemDTO> getDictItemsByDictCodeAndDictItemNames(@RequestBody DictItemQueryDTO dto){
        return iDictItemService.getDictItemsByDictCodeAndDictItemNames(dto);
    }

    @PostMapping("/listDictItemsByParam")
    public List<DictItem> listDictItemsByParam(@RequestBody DictItemDTO dictItemDTO) {
        return iDictItemService.listDictItemsByParam(dictItemDTO);
    }
    
    /**
     * 品类接口查询
     * @return
     */
    @GetMapping("/queryProductType")
    public List<DictItemDTO> queryProductType() {
        return iDictItemService.queryProductType();
    }
}

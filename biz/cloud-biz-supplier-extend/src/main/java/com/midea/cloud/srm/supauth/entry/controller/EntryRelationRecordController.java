package com.midea.cloud.srm.supauth.entry.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryRelationRecord;
import com.midea.cloud.srm.supauth.entry.service.IEntryRelationRecordService;


/**
* <pre>
 *  准入组织品类关系记录表 前端控制器
 * </pre>
*
* @author scc_sup_entry_relation_record
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 26, 2021 1:47:56 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/entryrelationrecord")
public class EntryRelationRecordController extends BaseController {

    @Autowired
    private IEntryRelationRecordService iEntryRelationRecordService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public EntryRelationRecord get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iEntryRelationRecordService.getById(id);
    }

    /**
    * 新增
    * @param entryRelationRecord
    */
    @PostMapping("/add")
    public void add(@RequestBody EntryRelationRecord entryRelationRecord) {
        Long id = IdGenrator.generate();
        entryRelationRecord.setRelationId(id);
        iEntryRelationRecordService.save(entryRelationRecord);
    }

    /**
     * 批量新增
     * @param entryRelationRecordList
     */
    @PostMapping("/batchAdd")
    public void batchAdd(@RequestBody List<EntryRelationRecord> entryRelationRecordList) throws IOException{
    	iEntryRelationRecordService.batchAdd(entryRelationRecordList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iEntryRelationRecordService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
    	iEntryRelationRecordService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param entryRelationRecord
    */
    @PostMapping("/modify")
    public void modify(@RequestBody EntryRelationRecord entryRelationRecord) {
    	iEntryRelationRecordService.updateById(entryRelationRecord);
    }


    /**
     * 批量修改
     * @param entryRelationRecordList
     */
    @PostMapping("/batchModify")
    public void batchModify(@RequestBody List<EntryRelationRecord> entryRelationRecordList) throws IOException{
    	iEntryRelationRecordService.batchUpdate(entryRelationRecordList);
    }
    /**
    * 分页查询
    * @param entryRelationRecord
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<EntryRelationRecord> listPage(@RequestBody EntryRelationRecord entryRelationRecord) {
        return iEntryRelationRecordService.listPage(entryRelationRecord);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<EntryRelationRecord> listAll() {
        return iEntryRelationRecordService.list();
    }
}

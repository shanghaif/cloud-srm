package com.midea.cloud.srm.supauth.entry.controller;
import java.io.IOException;
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
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfigRecord;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryFileConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.supauth.entry.service.IFileRecordService;


/**
* <pre>
 *  准入附件记录 前端控制器
 * </pre>
*
* @author kuangzm
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 25, 2021 11:15:34 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/filerecord")
public class FileRecordController extends BaseController {

    @Autowired
    private IFileRecordService iFileRecordService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public FileRecord get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iFileRecordService.getById(id);
    }

    /**
    * 新增
    * @param fileRecord
    */
    @PostMapping("/add")
    public void add(@RequestBody FileRecord fileRecord) {
        Long id = IdGenrator.generate();
        fileRecord.setFileRecordId(id);
        iFileRecordService.save(fileRecord);
    }

    /**
     * 批量新增
     * @param fileRecordList
     */
    @PostMapping("/batchAdd")
    public void batchAdd(@RequestBody List<FileRecord> fileRecordList) throws IOException{
    	iFileRecordService.batchAdd(fileRecordList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iFileRecordService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
    	iFileRecordService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param fileRecord
    */
    @PostMapping("/modify")
    public void modify(@RequestBody FileRecord fileRecord) {
    	iFileRecordService.updateById(fileRecord);
    }


    /**
     * 批量修改
     * @param fileRecordList
     */
    @PostMapping("/batchModify")
    public void batchModify(@RequestBody List<FileRecord> fileRecordList) throws IOException{
    	iFileRecordService.batchUpdate(fileRecordList);
    }
    /**
    * 分页查询
    * @param fileRecord
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<FileRecord> listPage(@RequestBody FileRecord fileRecord) {
        return iFileRecordService.listPage(fileRecord);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<FileRecord> listAll() {
        return iFileRecordService.list();
    }

    @GetMapping("/getFileRecord")
	public List<FileRecord> getFileRecord(@RequestParam("formId") Long formId,
			@RequestParam("formType") String formType) {
		Assert.notNull(formId, "formId为空！");
		Assert.notNull(formType, "formType为空！");
		QueryWrapper<FileRecord> wrapper = new QueryWrapper<FileRecord>();
		wrapper.eq("formId", formId);
		wrapper.eq("formType", formType);
		return iFileRecordService.list(wrapper);
	}
}

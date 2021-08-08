package com.midea.cloud.srm.inq.inquiry.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.inquiry.service.IFileService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  询价-附件表 前端控制器
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:54
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/file")
public class FileController extends BaseController {

    @Autowired
    private IFileService iFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public File get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iFileService.getById(id);
    }

    /**
    * 新增
    * @param file
    */
    @PostMapping("/add")
    public void add(@RequestBody File file) {
        Long id = IdGenrator.generate();
        file.setInquiryFileId(id);
        iFileService.save(file);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iFileService.removeById(id);
    }

    /**
    * 修改
    * @param file
    */
    @PostMapping("/modify")
    public void modify(@RequestBody File file) {
        iFileService.updateById(file);
    }

    /**
    * 分页查询
    * @param file
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<File> listPage(@RequestBody File file) {
        PageUtil.startPage(file.getPageNum(), file.getPageSize());
        QueryWrapper<File> wrapper = new QueryWrapper<File>(file);
        return new PageInfo<File>(iFileService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<File> listAll() { 
        return iFileService.list();
    }

    /**
     * 根据询价单id和文件类型获取文件
     *
     * @param inquiryId
     * @param type
     */
    @GetMapping("/getFiles")
    public List<File> getInnerFiles(Long inquiryId, String type) {
        Assert.notNull(inquiryId, "询价单id不能为空");
        return iFileService.getByHeadId(inquiryId, type);
    }
 
}

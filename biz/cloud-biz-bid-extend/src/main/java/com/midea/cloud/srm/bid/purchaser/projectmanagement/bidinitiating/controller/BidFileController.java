package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidFileService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidFile;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  招标附件表 前端控制器
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 14:08:23
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/bidInitiating/bidFile")
public class BidFileController extends BaseController {

    @Autowired
    private IBidFileService iBidFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BidFile get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidFileService.getById(id);
    }

    /**
    * 新增
    * @param bidFile
    */
    @PostMapping("/add")
    public void add(@RequestBody BidFile bidFile) {
        Long id = IdGenrator.generate();
        bidFile.setFileId(id);
        iBidFileService.save(bidFile);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBidFileService.removeById(id);
    }

    /**
    * 修改
    * @param bidFile
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BidFile bidFile) {
        iBidFileService.updateById(bidFile);
    }

    /**
    * 分页查询
    * @param bidFile
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BidFile> listPage(@RequestBody BidFile bidFile) {
        PageUtil.startPage(bidFile.getPageNum(), bidFile.getPageSize());
        QueryWrapper<BidFile> wrapper = new QueryWrapper<BidFile>(bidFile);
        return new PageInfo<BidFile>(iBidFileService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BidFile> listAll(@RequestBody BidFile bidFile) {
        QueryWrapper<BidFile> wrapper = new QueryWrapper<BidFile>(bidFile);
        return iBidFileService.list(wrapper);
    }
 
}

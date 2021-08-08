package com.midea.cloud.srm.cm.accept.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.cm.accept.service.IAcceptDetailService;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptDetail;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  合同验收明细 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-03 15:26:28
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/accept/acceptDetail")
public class AcceptDetailController extends BaseController {

    @Autowired
    private IAcceptDetailService iAcceptDetailService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public AcceptDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iAcceptDetailService.getById(id);
    }

    /**
    * 新增
    * @param acceptDetail
    */
    @PostMapping("/add")
    public void add(@RequestBody AcceptDetail acceptDetail) {
        Long id = IdGenrator.generate();
        acceptDetail.setAcceptDetailId(id);
        iAcceptDetailService.save(acceptDetail);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iAcceptDetailService.removeById(id);
    }

    /**
    * 修改
    * @param acceptDetail
    */
    @PostMapping("/modify")
    public void modify(@RequestBody AcceptDetail acceptDetail) {
        iAcceptDetailService.updateById(acceptDetail);
    }

    /**
    * 分页查询
    * @param acceptDetail
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<AcceptDetail> listPage(@RequestBody AcceptDetail acceptDetail) {
        PageUtil.startPage(acceptDetail.getPageNum(), acceptDetail.getPageSize());
        QueryWrapper<AcceptDetail> wrapper = new QueryWrapper<AcceptDetail>(acceptDetail);
        return new PageInfo<AcceptDetail>(iAcceptDetailService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<AcceptDetail> listAll() { 
        return iAcceptDetailService.list();
    }
 
}

package com.midea.cloud.srm.bid.purchaser.bidprocessconfig.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.bidprocessconfig.service.IProcessNodeService;
import com.midea.cloud.srm.model.bid.purchaser.bidprocessconfig.entity.ProcessNode;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  招标流程节点标记表 前端控制器
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-27 09:03:45
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/bidProcessConfig/processNode")
public class ProcessNodeController extends BaseController {

    @Autowired
    private IProcessNodeService iProcessNodeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ProcessNode get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iProcessNodeService.getById(id);
    }

    /**
    * 新增
    * @param processNode
    */
    @PostMapping("/add")
    public List<ProcessNode> add(@RequestBody ProcessNode processNode) {
        return iProcessNodeService.saveNodes(processNode.getBidingId(),processNode.getProcessConfigId());
    }

    /**
     * 更新状态
     * @param processNode
     */
    @PostMapping("/updateNodeStatus")
    public List<ProcessNode> update(@RequestBody ProcessNode processNode) {
        return iProcessNodeService.updateNodeStatus(processNode);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iProcessNodeService.removeById(id);
    }

    /**
    * 修改
    * @param processNode
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ProcessNode processNode) {
        iProcessNodeService.updateById(processNode);
    }

    /**
    * 分页查询
    * @param processNode
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ProcessNode> listPage(@RequestBody ProcessNode processNode) {
        PageUtil.startPage(processNode.getPageNum(), processNode.getPageSize());
        QueryWrapper<ProcessNode> wrapper = new QueryWrapper<ProcessNode>(processNode);
        return new PageInfo<ProcessNode>(iProcessNodeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ProcessNode> listAll() {
        return iProcessNodeService.list();
    }
    /**
     * 根据招标单ID查询数据
     * @return
     */
    @GetMapping("/listByBidingId")
    public List<ProcessNode> listByBiding(Long bidingId) {
        Assert.notNull(bidingId,"招标单ID不能为空");
        QueryWrapper<ProcessNode> wrapper = new QueryWrapper<ProcessNode>(new ProcessNode().setBidingId(bidingId));
        return iProcessNodeService.list(wrapper);
    }
}

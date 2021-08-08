package com.midea.cloud.srm.bid.purchaser.techdiscuss.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.bid.purchaser.techdiscuss.service.ITechDiscussProjInfoService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussProjInfo;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.vo.TechDiscussProjInfoVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  技术交流项目信息表 前端控制器
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/techDiscuss/techDiscussProjInfo")
public class TechDiscussProjInfoController extends BaseController {

    @Autowired
    private ITechDiscussProjInfoService iTechDiscussProjInfoService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public TechDiscussProjInfo get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTechDiscussProjInfoService.getById(id);
    }

    /**
     * 新增或更新
     *
     * @param techDiscussProjInfoVO
     */
    @PostMapping("/saveOrUpdateProjInfo")
    public void saveOrUpdateProjInfo(@RequestBody TechDiscussProjInfoVO techDiscussProjInfoVO) {
        iTechDiscussProjInfoService.saveOrUpdateProjInfo(techDiscussProjInfoVO);
    }

    /**
     * 发布
     *
     * @param projId
     */
    @PostMapping("/publish")
    public void publish(Long projId) {
        Assert.notNull(projId, "id不能为空");
        iTechDiscussProjInfoService.publish(projId);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTechDiscussProjInfoService.removeById(id);
    }

    /**
     * 修改
     *
     * @param techDiscussProjInfo
     */
    @PostMapping("/modify")
    public void modify(@RequestBody TechDiscussProjInfo techDiscussProjInfo) {
        iTechDiscussProjInfoService.updateById(techDiscussProjInfo);
    }

    /**
     * 分页查询
     *
     * @param techDiscussProjInfo
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<TechDiscussProjInfo> listPage(@RequestBody TechDiscussProjInfo techDiscussProjInfo) {
        return iTechDiscussProjInfoService.listPage(techDiscussProjInfo);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<TechDiscussProjInfo> listAll() {
        return iTechDiscussProjInfoService.list();
    }

}

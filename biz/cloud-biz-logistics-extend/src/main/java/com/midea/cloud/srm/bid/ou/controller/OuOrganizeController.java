package com.midea.cloud.srm.bid.ou.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.ou.service.IOuOrganizeService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.logistics.bid.ou.entity.OuOrganize;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  ou组基础信息表 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 13:53:09
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/ou/ouOrganize")
public class OuOrganizeController extends BaseController {

    @Autowired
    private IOuOrganizeService iOuOrganizeService;
    @Autowired
    private BaseClient baseClient;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/getOu")
    public OuOrganize get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOuOrganizeService.getById(id);
    }

    /**
    * 新增
    * @param ouOrganize
    */
    @PostMapping("/ouAddOne")
    public void add(@RequestBody OuOrganize ouOrganize) {
        Long id = IdGenrator.generate();
        ouOrganize.setOuId(id);
        //获取系统生成的编码
        String oucode = baseClient.seqGen(SequenceCodeConstant.SEQ_BID_OU_OU_CODE);
        ouOrganize.setOuNumber(oucode);
        iOuOrganizeService.save(ouOrganize);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/ouDelete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOuOrganizeService.removeById(id);
    }

    /**
    * 修改
    * @param ouOrganize
    */
    @PostMapping("/ouModify")
    public void modify(@RequestBody OuOrganize ouOrganize) {
        iOuOrganizeService.updateById(ouOrganize);
    }

    /**
    * 分页查询
    * @param ouOrganize
    * @return
    */
    @PostMapping("/ouListPage")
    public PageInfo<OuOrganize> listPage(@RequestBody OuOrganize ouOrganize) {
        return iOuOrganizeService.getOuListPage(ouOrganize);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/ouListAll")
    public List<OuOrganize> listAll() { 
        return iOuOrganizeService.list();
    }



}

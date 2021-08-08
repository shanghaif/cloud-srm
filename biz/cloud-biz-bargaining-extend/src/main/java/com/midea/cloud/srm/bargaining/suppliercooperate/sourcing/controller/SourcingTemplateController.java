//package com.midea.cloud.srm.bargaining.suppliercooperate.sourcing.controller;
//
//import com.midea.cloud.common.enums.bargaining.projectmanagement.sourcing.SourcingType;
//import com.midea.cloud.common.utils.IdGenrator;
//import com.midea.cloud.common.utils.PageUtil;
//import com.midea.cloud.srm.bargaining.suppliercooperate.sourcing.service.ISourcingTemplateService;
//import com.midea.cloud.srm.model.common.BaseController;
//import com.midea.cloud.srm.model.bargaining.suppliercooperate.entity.SourcingTemplates;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.github.pagehelper.PageInfo;
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.Assert;
//import org.springframework.web.bind.annotation.*;
//
//import org.springframework.web.bind.annotation.RestController;
//
///**
//*  <pre>
// *  寻源模板表 前端控制器
// * </pre>
//*
//* @author yourname@meiCloud.com
//* @version 1.00.00
//*
//*  <pre>
// *  修改记录
// *  修改后版本:
// *  修改人:
// *  修改日期: 2020-08-24 15:37:16
// *  修改内容:
// * </pre>
//*/
//@RestController
//@RequestMapping("/suppliercooperate/sourcingTemplate")
//public class SourcingTemplateController extends BaseController {
//
//    @Autowired
//    private ISourcingTemplateService iSourcingTemplateService;
//
//    /**
//    * 获取
//    * @param id
//    */
//    @GetMapping("/getSourcingTemplate")
//    public SourcingTemplates get(Long id) {
//        Assert.notNull(id, "id不能为空");
//        return iSourcingTemplateService.getById(id);
//    }
//
//    /**
//    * 新增
//    * @param sourcingTemplates
//    */
//    @PostMapping("/sourcingTemplateAdd")
//    public void add(@RequestBody SourcingTemplates sourcingTemplates) {
//        Long id = IdGenrator.generate();
//        sourcingTemplates.setSourcingId(id);
//        iSourcingTemplateService.save(sourcingTemplates);
//    }
//
//    /**
//    * 删除
//    * @param id
//    */
//    @GetMapping("/delete")
//    public void delete(Long id) {
//        Assert.notNull(id, "id不能为空");
//        SourcingTemplates byId = iSourcingTemplateService.getById(id);
//        if (StringUtils.isNotEmpty(byId.getSourcingType())&&byId.getSourcingType().equals(SourcingType.LOSE_EFFICACY.toString())){
//            iSourcingTemplateService.removeById(id);
//        }else {
//            Assert.notNull(null, "未失效的模板不能删除");
//        }
//    }
//
//    /**
//    * 修改
//    * @param sourcingTemplates
//    */
//    @PostMapping("/sourcingTemplateModify")
//    public void modify(@RequestBody SourcingTemplates sourcingTemplates) {
//        iSourcingTemplateService.updateById(sourcingTemplates);
//    }
//
//    /**
//    * 分页查询
//    * @param sourcingTemplates
//    * @return
//    */
//    @PostMapping("/sourcingTemplateListPage")
//    public PageInfo<SourcingTemplates> listPage(@RequestBody SourcingTemplates sourcingTemplates) {
//        PageUtil.startPage(sourcingTemplates.getPageNum(), sourcingTemplates.getPageSize());
//        QueryWrapper<SourcingTemplates> wrapper = new QueryWrapper<SourcingTemplates>(sourcingTemplates);
//        //模板状态条件查询
//        wrapper.eq(StringUtils.isNotEmpty(sourcingTemplates.getSourcingType()),"SOURCING_TYPE", sourcingTemplates.getSourcingType());
//        //模板名称模糊查询
//        wrapper.like(StringUtils.isNotEmpty(sourcingTemplates.getSourcingName()),"SOURCING_NAME", sourcingTemplates.getSourcingName());
//        return new PageInfo<SourcingTemplates>(iSourcingTemplateService.list(wrapper));
//    }
//
//    /**
//    * 查询所有
//    * @return
//    */
//    @PostMapping("/listAll")
//    public List<SourcingTemplates> listAll() {
//        return iSourcingTemplateService.list();
//    }
//
//    /**
//     * 失效寻源模板
//     */
//    @GetMapping("/getLoseEfficacy")
//    public  void getLoseEfficacy(Long id){
//        Assert.notNull(id, "寻源模板不存在");
//        SourcingTemplates byId = iSourcingTemplateService.getById(id);
//        if (StringUtils.isNotEmpty(byId.getSourcingType())||!byId.getSourcingType().equals(SourcingType.LOSE_EFFICACY.toString())){
//            byId.setSourcingType(SourcingType.LOSE_EFFICACY.toString());
//            iSourcingTemplateService.updateById(byId);
//        }
//    }
//
//}

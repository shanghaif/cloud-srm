package com.midea.cloud.srm.perf.template.controller;

import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateCategory;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 *  <pre>
 *  绩效模型头表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 16:41:07
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/template-category")
@Slf4j
public class PerfTemplateCategoryController extends BaseController {

    @Autowired
    private IPerfTemplateCategoryService iPerfTemplateCategoryService;

    /**
     * 删除模型品类行数据
     * @param templateCategoryId
     */
    @GetMapping("/deleteTemplateCategoryByLineId")
    public void listPefTemplateHeaderPage(@NotNull Long templateCategoryId) {
        PerfTemplateCategory perfTemplateCategory = iPerfTemplateCategoryService.getById(templateCategoryId);
        Assert.notNull(perfTemplateCategory, "找不到对应的采购分类行数据。");
        iPerfTemplateCategoryService.removeById(templateCategoryId);
    }

}

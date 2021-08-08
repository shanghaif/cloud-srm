package com.midea.cloud.srm.perf.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateCategory;

import java.util.List;

/**
 *  <pre>
 *  绩效模型采购分类表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 16:43:12
 *  修改内容:
 * </pre>
 */
public interface IPerfTemplateCategoryService extends IService<PerfTemplateCategory> {

    /**
     * 绩效模板品类行校验
     * @param perfTemplateCategoryList
     */
    void checkTemplateCategories(List<PerfTemplateCategory> perfTemplateCategoryList);

}

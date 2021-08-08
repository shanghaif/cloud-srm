package com.midea.cloud.srm.perf.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateCategory;
import com.midea.cloud.srm.perf.template.mapper.PerfTemplateCategoryMapper;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateCategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  <pre>
 *  绩效模型采购分类表 服务实现类
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
@Service
public class PerfTemplateCategoryServiceImpl extends ServiceImpl<PerfTemplateCategoryMapper, PerfTemplateCategory>
        implements IPerfTemplateCategoryService {

    /**
     * 绩效模板品类行校验
     * 校验品类不能重复
     * @param perfTemplateCategoryList
     */
    @Override
    public void checkTemplateCategories(List<PerfTemplateCategory> perfTemplateCategoryList) {
        if (CollectionUtils.isEmpty(perfTemplateCategoryList)) {
            return;
        }
        Map<Long, List<PerfTemplateCategory>> collect = perfTemplateCategoryList.stream().collect(Collectors.groupingBy(PerfTemplateCategory::getCategoryId));
        collect.forEach((k, v) -> {
            if (v.size() > 1) {
                String categoryName = v.get(0).getCategoryName();
                StringBuffer sb = new StringBuffer();
                sb.append("绩效模型品类行中的品类不能重复，品类：[").append(categoryName).append("]重复，请调整后重试。");
                throw new BaseException(LocaleHandler.getLocaleMsg(sb.toString()));
            }
        });
    }
}

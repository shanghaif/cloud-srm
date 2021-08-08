package com.midea.cloud.srm.sup.quest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 问卷调查 Mapper 接口
 * </p>
 *
 * @author bing5.wang@midea.com
 * @since Apr 16, 2021 5:17:12 PM
 */
public interface QuestTemplateMapper extends BaseMapper<QuestTemplate> {
    Integer checkUseBySupplier(@Param("questTemplateId") Long questTemplateId);
}
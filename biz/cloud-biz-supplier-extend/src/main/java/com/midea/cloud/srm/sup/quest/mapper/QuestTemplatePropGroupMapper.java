package com.midea.cloud.srm.sup.quest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplatePropGroup;

import java.util.List;

/**
 * <p>
 * 问卷调查 Mapper 接口
 * </p>
 *
 * @author bing5.wang@midea.com
 * @since Apr 16, 2021 6:29:05 PM
 */
public interface QuestTemplatePropGroupMapper extends BaseMapper<QuestTemplatePropGroup> {
    List<QuestTemplatePropGroup> queryByQuestSupId(Long questSupId);
}
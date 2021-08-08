package com.midea.cloud.srm.sup.quest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestResult;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateProp;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 问卷调查 Mapper 接口
 * </p>
 *
 * @author bing5.wang@midea.com
 * @since Apr 16, 2021 5:34:12 PM
 */
public interface QuestResultMapper extends BaseMapper<QuestResult> {
    List<QuestResultVo.GroupInfo.FieldInfo> queryByQuestSupId(@Param("questSupId") Long questSupId, @Param("questTemplatePropGroupId") Long questTemplatePropGroupId);


}
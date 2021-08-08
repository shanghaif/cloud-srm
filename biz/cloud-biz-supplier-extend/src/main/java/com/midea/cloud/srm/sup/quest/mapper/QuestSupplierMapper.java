package com.midea.cloud.srm.sup.quest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestSupplier;

import java.util.List;

/**
 * <p>
 * 问卷调查 Mapper 接口
 * </p>
 *
 * @author bing5.wang@midea.com
 * @since Apr 16, 2021 5:33:46 PM
 */
public interface QuestSupplierMapper extends BaseMapper<QuestSupplier> {

    List<Long> countQuestByCompanyId(List<String> approvalStatusList, List<Long> companyIdList,List<Long> orgIdList);

}
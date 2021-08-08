package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.clearquestion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.clearquestion.entity.BidingQuestion;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.clearquestion.vo.BidingQuestionVO;

/**
 * <p>
 * 招标质疑表 Mapper 接口
 * </p>
 *
 * @author zhuomb1@midea.com
 * @since 2020-04-20
 */
public interface BidingQuestionMapper extends BaseMapper<BidingQuestion> {
    BidingQuestionVO queryBidingQuestion(BidingQuestionVO bidingQuestionVO);
}

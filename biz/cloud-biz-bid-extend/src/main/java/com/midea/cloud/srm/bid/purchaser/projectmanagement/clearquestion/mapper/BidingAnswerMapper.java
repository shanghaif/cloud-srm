package com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.entity.BidingAnswer;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.vo.BidingAnswerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招标澄清表 Mapper 接口
 * </p>
 *
 * @author zhuomb1@midea.com
 * @since 2020-04-20
 */
public interface BidingAnswerMapper extends BaseMapper<BidingAnswer> {
    List<BidingAnswerVO> queryBidingAnswer(@Param("bidingAnswerVO") BidingAnswerVO bidingAnswerVO);
}

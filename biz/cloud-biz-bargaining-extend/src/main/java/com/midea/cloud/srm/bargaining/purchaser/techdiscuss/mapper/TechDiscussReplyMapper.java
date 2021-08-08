package com.midea.cloud.srm.bargaining.purchaser.techdiscuss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.bargaining.purchaser.techdiscuss.entity.TechDiscussReply;
import com.midea.cloud.srm.model.bargaining.purchaser.techdiscuss.vo.TechDiscussReplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 技术交流项目供应商回复表 Mapper 接口
 * </p>
 *
 * @author fengdc3@meicloud.com
 * @since 2020-03-12
 */
public interface TechDiscussReplyMapper extends BaseMapper<TechDiscussReply> {

    List<TechDiscussReplyVO> listTechDiscussReplyInfo(@Param("techDiscussReplyVO") TechDiscussReplyVO techDiscussReplyVO);

}



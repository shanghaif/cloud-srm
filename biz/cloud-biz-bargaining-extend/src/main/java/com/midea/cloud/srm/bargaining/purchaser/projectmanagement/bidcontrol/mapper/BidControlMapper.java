package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidcontrol.mapper;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo.BidControlListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 投标控制 Mapper 接口
 * </p>
 *
 * @author fengdc3@meiCloud.com
 * @since 2020-05-05
 */
public interface BidControlMapper {
    List<BidControlListVO> listBidControlFirstRound(@Param("bidingId") Long bidingId,
                                           @Param("signUpStatus") String signUpStatus);
    List<BidControlListVO> listBidControl(@Param("bidingId") Long bidingId,
                                           @Param("round") Integer round);

    Integer countVendor(@Param("bidingId") Long bidingId);
}

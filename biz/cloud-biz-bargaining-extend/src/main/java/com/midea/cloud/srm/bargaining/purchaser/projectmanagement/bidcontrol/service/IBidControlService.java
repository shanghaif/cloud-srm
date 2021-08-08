package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidcontrol.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo.BidControlListVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo.BidControlTopInfoVO;

import java.util.Date;

/**
 * <pre>
 * 投标控制 接口
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年4月9日 下午16:44:01
 *  修改内容:
 *          </pre>
 */
public interface IBidControlService {

    PageInfo<BidControlListVO> listPageBidControl(Long bidingId, Integer pageNum, Integer pageSize);

    BidControlTopInfoVO getTopInfo(Long bidingId);

    void startBiding(Long bidingId, Date endTime);

    void extendBiding(Long bidingId, Date endTime, String extendReason);
}

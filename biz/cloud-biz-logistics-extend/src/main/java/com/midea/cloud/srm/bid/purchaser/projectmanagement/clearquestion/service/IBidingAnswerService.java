package com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.clearquestion.entity.BidingAnswer;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.clearquestion.vo.BidingAnswerVO;

import java.util.List;

/**
*  <pre>
 *  招标澄清表 服务类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-20 15:22:47
 *  修改内容:
 * </pre>
*/
public interface IBidingAnswerService extends IService<BidingAnswer> {
    BidingAnswerVO saveBidingAnswer(BidingAnswerVO bidingAnswerVO);
    List<BidingAnswerVO> listBidingAnswer(BidingAnswerVO bidingAnswerVO);
}

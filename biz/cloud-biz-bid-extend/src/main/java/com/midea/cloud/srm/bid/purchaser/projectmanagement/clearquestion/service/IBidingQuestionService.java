package com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.entity.BidingQuestion;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.vo.BidingQuestionVO;

import java.util.List;

/**
*  <pre>
 *  招标质疑表 服务类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-20 15:22:06
 *  修改内容:
 * </pre>
*/
public interface IBidingQuestionService extends IService<BidingQuestion> {

    BidingQuestionVO saveBidingQuestion(BidingQuestionVO bidingQuestionVO);

    List<BidingQuestion> listBidingQuestion(BidingQuestionVO bidingQuestionVO);
}

package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidingresult.entity.BidResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.vo.EvaluationResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo.AbandonmentBidSupVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 招标结果 接口
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020年4月9日 下午16:44:01
 *  修改内容:
 *          </pre>
 */
public interface IBidingResultService extends IService<BidResult> {

    PageInfo<BidResult> listPageBidingResult(Long bidingId);

    void updateBidResultBatchById(List<BidResult> bidResultList);

    List<BidResult> getResultByBidingId(Long bidingId);

    PageInfo<EvaluationResult> getNewResultByBidingId(Map<String,Object> paramMap);

    List<AbandonmentBidSupVO> getAbandonBidSup(Long bidingId);
}

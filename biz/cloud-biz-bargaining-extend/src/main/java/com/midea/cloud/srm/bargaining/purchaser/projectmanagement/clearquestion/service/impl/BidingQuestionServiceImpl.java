package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.clearquestion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.bargaining.projectmanagement.clearquestion.BidingQuestionEnum;
import com.midea.cloud.common.enums.bargaining.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.clearquestion.mapper.BidingQuestionMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.clearquestion.service.IBidingQuestionService;
import com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.clearquestion.entity.BidingQuestion;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.clearquestion.vo.BidingQuestionVO;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.BidVendorFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
*  <pre>
 *  招标质疑表 服务实现类
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
@Service
public class BidingQuestionServiceImpl extends ServiceImpl<BidingQuestionMapper, BidingQuestion> implements IBidingQuestionService {

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private IBidVendorFileService iBidVendorFileService;

    @Override
    public BidingQuestionVO saveBidingQuestion(BidingQuestionVO bidingQuestionVO) {
        Long questionId = bidingQuestionVO.getQuestionId();
        if(null == questionId){
            BidingQuestion bidingQuestion = new BidingQuestion();
            BeanCopyUtil.copyProperties(bidingQuestion,bidingQuestionVO);
            questionId = IdGenrator.generate();
            bidingQuestion.setQuestionId(questionId);
            bidingQuestionVO.setQuestionId(questionId);
            String questionNum = baseClient.seqGen(SequenceCodeConstant.SEQ_BIDING_QUESTION_CODE);
            bidingQuestion.setQuestionNum(questionNum);
            bidingQuestionVO.setQuestionNum(questionNum);
            bidingQuestion.setQuestionStatus(BidingQuestionEnum.DRAFT.getValue());
            this.save(bidingQuestion);
        }else{
            BidingQuestion bidingQuestion = this.getById(questionId);
            Assert.isTrue(bidingQuestion.getQuestionStatus().equals(BidingQuestionEnum.DRAFT.getValue()),"质疑已经发布，不能重复操作");
            BeanCopyUtil.copyProperties(bidingQuestion,bidingQuestionVO);
            this.updateById(bidingQuestion);
        }

        //保存附件
        List<BidVendorFileVO> fileVOS = bidingQuestionVO.getFiles();
        Long finalQuestionId = questionId;
        fileVOS.forEach(filevo->{
            filevo.setBidingId(bidingQuestionVO.getBidingId());
            filevo.setVendorId(bidingQuestionVO.getVendorId());
            filevo.setBusinessId(finalQuestionId);
            filevo.setFileType(VendorFileType.BIDING_QUESTION.getValue());
        });
        iBidVendorFileService.saveBatchVendorFilesByBusinessIdAndFileType(fileVOS);

        return bidingQuestionVO;
    }

    @Override
    public List<BidingQuestion> listBidingQuestion(BidingQuestionVO bidingQuestionVO) {
        QueryWrapper<BidingQuestion> wrapper = new QueryWrapper<BidingQuestion>();
        //招标项目编号
        String bidingNum = bidingQuestionVO.getBidingNum();
        if(null != bidingNum && !"".equals(bidingNum)){
            wrapper.like("biding_num",bidingNum);
        }
        //招标项目名称
        String bidingName = bidingQuestionVO.getBidingName();
        if(null != bidingName && !"".equals(bidingName)){
            wrapper.like("biding_name",bidingName);
        }
        //质疑编号
        String questionNum = bidingQuestionVO.getQuestionNum();
        if(null != questionNum && !"".equals(questionNum)){
            wrapper.like("question_num",questionNum);
        }
        //质疑标题
        //质疑编号
        String questionTitle = bidingQuestionVO.getQuestionTitle();
        if(null != questionTitle && !"".equals(questionTitle)){
            wrapper.like("question_title",questionTitle);
        }
        //质疑状态
        String questionStatus = bidingQuestionVO.getQuestionStatus();
        if(null != questionStatus && !"".equals(questionStatus)){
            wrapper.setEntity(new BidingQuestion().setQuestionStatus(questionStatus));
        }
        wrapper.orderByDesc("submit_time");
        return this.list(wrapper);
    }
}

package com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.bid.projectmanagement.clearquestion.BidingAnswerEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.mapper.BidingAnswerMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.service.IBidingAnswerService;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.entity.BidingAnswer;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.vo.BidingAnswerVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidVendorFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  招标澄清表 服务实现类
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
@Service
public class BidingAnswerServiceImpl extends ServiceImpl<BidingAnswerMapper, BidingAnswer> implements IBidingAnswerService {

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private IBidVendorFileService iBidVendorFileService;

    @Override
    public BidingAnswerVO saveBidingAnswer(BidingAnswerVO bidingAnswerVO) {

        Long answerId = bidingAnswerVO.getAnswerId();
        if(null == answerId){
            BidingAnswer bidingAnswer = new BidingAnswer();
            BeanCopyUtil.copyProperties(bidingAnswer,bidingAnswerVO);
            answerId = IdGenrator.generate();
            bidingAnswer.setAnswerId(answerId);
            bidingAnswerVO.setAnswerId(answerId);
            bidingAnswer.setAnswerNum(baseClient.seqGen(SequenceCodeConstant.SEQ_BIDING_ANSWER_CODE));
            bidingAnswer.setAnswerStatus(BidingAnswerEnum.DRAFT.getValue());
            this.save(bidingAnswer);
        }else{
            BidingAnswer bidingAnswer = new BidingAnswer();
            BeanCopyUtil.copyProperties(bidingAnswer,bidingAnswerVO);
            this.updateById(bidingAnswer);
        }

        //保存附件
        List<BidVendorFileVO> fileVOS = bidingAnswerVO.getFiles();
        Long finalAnswerId = answerId;
        fileVOS.forEach(filevo->{
            filevo.setBidingId(bidingAnswerVO.getBidingId());
            filevo.setBusinessId(finalAnswerId);
            filevo.setFileType(VendorFileType.BIDING_ANSWER.getValue());
        });
        iBidVendorFileService.saveBatchVendorFilesByBusinessIdAndFileType(fileVOS);

        return bidingAnswerVO;
    }

    @Override
    public List<BidingAnswerVO> listBidingAnswer(BidingAnswerVO bidingAnswerVO) {
        return this.baseMapper.queryBidingAnswer(bidingAnswerVO);
    }
}

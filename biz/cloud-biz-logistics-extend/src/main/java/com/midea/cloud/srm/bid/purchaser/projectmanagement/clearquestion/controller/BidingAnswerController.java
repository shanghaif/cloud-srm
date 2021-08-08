package com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.bid.projectmanagement.clearquestion.BidingAnswerEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.service.IBidingAnswerService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.service.IBidingQuestionService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.clearquestion.entity.BidingAnswer;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.clearquestion.vo.BidingAnswerVO;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidVendorFileVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  招标澄清表 前端控制器
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
@RestController
@RequestMapping("/bidingAnswer")
public class BidingAnswerController extends BaseController {

    @Autowired
    private IBidingAnswerService iBidingAnswerService;
    @Autowired
    private IBidVendorFileService iBidVendorFileService;
    @Autowired
    private IBidingQuestionService iBidingQuestionService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BidingAnswer get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidingAnswerService.getById(id);
    }

    /**
    * 新增
    * @param bidingAnswer
    */
    @PostMapping("/add")
    public void add(@RequestBody BidingAnswer bidingAnswer) {
        Long id = IdGenrator.generate();
        bidingAnswer.setAnswerId(id);
        iBidingAnswerService.save(bidingAnswer);
    }
    /**
     * 澄清--保存
     * @param bidingAnswerVO
     */
    @PostMapping("/save")
    public BidingAnswerVO save(@RequestBody BidingAnswerVO bidingAnswerVO) {
        Assert.notNull(bidingAnswerVO.getBidingId(),"招标单ID不能为空");
        bidingAnswerVO = iBidingAnswerService.saveBidingAnswer(bidingAnswerVO);
        return queryBidingAnswerDetail(bidingAnswerVO.getAnswerId());
    }


    /**
     * 澄清--发布
     * @param bidingAnswerVO
     */
    @PostMapping("/submit")
    public BidingAnswerVO submit(@RequestBody BidingAnswerVO bidingAnswerVO) {
        Assert.notNull(bidingAnswerVO.getBidingId(),"招标单ID不能为空");
        //先保存
        bidingAnswerVO = save(bidingAnswerVO);
        //再更新
        BidingAnswer bidingAnswer = iBidingAnswerService.getById(bidingAnswerVO.getAnswerId());
        bidingAnswer.setSubmitTime(new Date());
        bidingAnswer.setAnswerStatus(BidingAnswerEnum.ISSUED.getValue());
        iBidingAnswerService.updateById(bidingAnswer);
        return queryBidingAnswerDetail(bidingAnswerVO.getAnswerId());
    }


    /**
     * 澄清--查询详情
     * @param answerId
     */
    @GetMapping("/getBidingAnswerById")
    public BidingAnswerVO queryBidingAnswerDetail(@RequestParam Long answerId) {

        BidingAnswerVO bidingAnswerVO = new BidingAnswerVO();
        bidingAnswerVO.setAnswerId(answerId);
        bidingAnswerVO = iBidingAnswerService.listBidingAnswer(bidingAnswerVO).get(0);
        //获取附件信息
        BidVendorFileVO bidVendorFileVO = new BidVendorFileVO();
        bidVendorFileVO.setBusinessId(answerId);
        bidVendorFileVO.setFileType(VendorFileType.BIDING_ANSWER.getValue());
        List<BidVendorFileVO> files = iBidVendorFileService.getVendorFileList(bidVendorFileVO);
        bidingAnswerVO.setFiles(files);
        return bidingAnswerVO;
    }


    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public String delete(Long id) {
        Assert.notNull(id, "id不能为空");
        BidingAnswer bidingAnswer = iBidingAnswerService.getById(id);
        Assert.notNull(bidingAnswer,"未找到澄清单");
        Assert.isTrue(BidingAnswerEnum.DRAFT.getValue().equals(bidingAnswer.getAnswerStatus()),"拟定状态的数据才可以删除");
        iBidingAnswerService.removeById(id);
        return "删除成功";
    }

    /**
     * 澄清--撤回
     * @param id
     */
    @GetMapping("/withDraw")
    public String withDraw(Long id) {
        Assert.notNull(id, "id不能为空");
        BidingAnswer bidingAnswer = iBidingAnswerService.getById(id);
        Assert.isTrue(BidingAnswerEnum.ISSUED.getValue().equals(bidingAnswer.getAnswerStatus()),"已发布状态的数据才可以撤回");
        bidingAnswer.setAnswerStatus(BidingAnswerEnum.DRAFT.getValue());
        iBidingAnswerService.updateById(bidingAnswer);
        return "撤回成功";
    }

    /**
    * 修改
    * @param bidingAnswer
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BidingAnswer bidingAnswer) {
        iBidingAnswerService.updateById(bidingAnswer);
    }

    /**
    * 分页查询
    * @param bidingAnswerVO
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BidingAnswerVO> listPage(@RequestBody BidingAnswerVO bidingAnswerVO) {
        PageUtil.startPage(bidingAnswerVO.getPageNum(), bidingAnswerVO.getPageSize());
        return new PageInfo<BidingAnswerVO>(iBidingAnswerService.listBidingAnswer(bidingAnswerVO));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BidingAnswer> listAll() { 
        return iBidingAnswerService.list();
    }
 
}

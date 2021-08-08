package com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.bid.projectmanagement.clearquestion.BidingQuestionEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.clearquestion.service.IBidingQuestionService;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.entity.BidingQuestion;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.clearquestion.vo.BidingQuestionVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidVendorFileVO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  招标质疑表 前端控制器
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
@RestController
@RequestMapping("/bidingQuestion")
public class BidingQuestionController extends BaseController {

    @Autowired
    private IBidingQuestionService iBidingQuestionService;
    @Autowired
    private IBidVendorFileService iBidVendorFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BidingQuestion get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidingQuestionService.getById(id);
    }

    /**
    * 新增
    * @param bidingQuestion
    */
    @PostMapping("/add")
    public void add(@RequestBody BidingQuestion bidingQuestion) {
        Assert.isNull(bidingQuestion.getBidingId(),"招标单ID不能为空");
        bidingQuestion.setQuestionStatus(BidingQuestionEnum.DRAFT.getValue());
        iBidingQuestionService.save(bidingQuestion);
    }

    /**
     * 质疑--保存
     * @param bidingQuestionVO
     */
    @PostMapping("/save")
    public BidingQuestionVO save(@RequestBody BidingQuestionVO bidingQuestionVO) {
        Assert.notNull(bidingQuestionVO.getBidingId(),"招标单ID不能为空");
        if(StringUtils.isEmpty(bidingQuestionVO.getVendorId())){
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            bidingQuestionVO.setVendorId(loginAppUser.getCompanyId());
        }
        bidingQuestionVO = iBidingQuestionService.saveBidingQuestion(bidingQuestionVO);
        return queryBidingQuestionDetail(bidingQuestionVO.getQuestionId());
    }

    /**
     * 质疑--发布
     * @param bidingQuestionVO
     */
    @PostMapping("/submit")
    public BidingQuestionVO submit(@RequestBody BidingQuestionVO bidingQuestionVO) {
        Assert.notNull(bidingQuestionVO.getBidingId(),"招标单ID不能为空");
        //先保存
        bidingQuestionVO = save(bidingQuestionVO);
        //再更新
        BidingQuestion bidingQuestion = iBidingQuestionService.getById(bidingQuestionVO.getQuestionId());
        bidingQuestion.setSubmitTime(new Date());
        bidingQuestion.setQuestionStatus(BidingQuestionEnum.SUBMITTED.getValue());
        iBidingQuestionService.updateById(bidingQuestion);

        return queryBidingQuestionDetail(bidingQuestionVO.getQuestionId());
    }

    /**
     * 质疑--撤回
     * @param bidingQuestionVO
     */
    @PostMapping("/reject")
    public String reject(@RequestBody BidingQuestionVO bidingQuestionVO) {
        Assert.notNull(bidingQuestionVO.getQuestionId(),"质疑单id不能为空。");
        BidingQuestion bidingQuestion = iBidingQuestionService.getById(bidingQuestionVO.getQuestionId());
        bidingQuestion.setRejectReason(bidingQuestionVO.getRejectReason());
        bidingQuestion.setQuestionStatus(BidingQuestionEnum.REJECTED.getValue());
        bidingQuestion.setRejectTime(new Date());
        iBidingQuestionService.updateById(bidingQuestion);

        return "质疑撤回成功";
    }
    /**
     * 质疑--澄清
     * @param bidingQuestionVO
     */
    @PostMapping("/clarified")
    public String clarified(@RequestBody BidingQuestionVO bidingQuestionVO) {
        Assert.notNull(bidingQuestionVO.getQuestionId(),"质疑单id不能为空。");
        BidingQuestion bidingQuestion = iBidingQuestionService.getById(bidingQuestionVO.getQuestionId());
        bidingQuestion.setQuestionStatus(BidingQuestionEnum.CLARIFIED.getValue());
        iBidingQuestionService.updateById(bidingQuestion);

        return "质疑已澄清";
    }

    /**
     * 质疑--查询详情
     * @param questionId
     */
    @GetMapping("/getBidingQuestionById")
    public BidingQuestionVO queryBidingQuestionDetail(@RequestParam Long questionId) {
        BidingQuestionVO bidingQuestionVO = new BidingQuestionVO();
        BidingQuestion bidingQuestion = iBidingQuestionService.getById(questionId);
        BeanCopyUtil.copyProperties(bidingQuestionVO,bidingQuestion);
        //获取附件信息
        BidVendorFileVO bidVendorFileVO = new BidVendorFileVO();
        bidVendorFileVO.setBusinessId(questionId);
        bidVendorFileVO.setFileType(VendorFileType.BIDING_QUESTION.getValue());
        List<BidVendorFileVO> files = iBidVendorFileService.getVendorFileList(bidVendorFileVO);
        bidingQuestionVO.setFiles(files);
        return bidingQuestionVO;
    }


    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public String delete(Long id) {
        Assert.notNull(id, "id不能为空");
        BidingQuestion bidingQuestion = iBidingQuestionService.getById(id);
        Assert.notNull(bidingQuestion,"未找到质疑单");
        Assert.isTrue(BidingQuestionEnum.DRAFT.getValue().equals(bidingQuestion.getQuestionStatus()),"拟定状态的数据才可以删除");
        iBidingQuestionService.removeById(id);
        return "删除成功";
    }

    /**
     * 质疑--撤回
     * @param id
     */
    @GetMapping("/withDraw")
    public String withDraw(Long id) {
        Assert.notNull(id, "id不能为空");
        BidingQuestion bidingQuestion = iBidingQuestionService.getById(id);
        Assert.isTrue(BidingQuestionEnum.SUBMITTED.getValue().equals(bidingQuestion.getQuestionStatus()),"已提交状态的数据才可以撤回");
        bidingQuestion.setQuestionStatus(BidingQuestionEnum.DRAFT.getValue());
        iBidingQuestionService.updateById(bidingQuestion);
        return "撤回成功";
    }

    /**
    * 修改
    * @param bidingQuestion
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BidingQuestion bidingQuestion) {
        iBidingQuestionService.updateById(bidingQuestion);
    }

    /**
    * 分页查询
    * @param bidingQuestionVO
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BidingQuestion> listPage(@RequestBody BidingQuestionVO bidingQuestionVO) {
        PageUtil.startPage(bidingQuestionVO.getPageNum(), bidingQuestionVO.getPageSize());
        List<BidingQuestion> list = iBidingQuestionService.listBidingQuestion(bidingQuestionVO);
        return new PageInfo<BidingQuestion>(list);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BidingQuestion> listAll() { 
        return iBidingQuestionService.list();
    }
 
}

package com.midea.cloud.srm.bid.purchaser.techdiscuss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.bid.purchaser.techdiscuss.service.ITechDiscussReplyService;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussReply;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.vo.TechDiscussReplyVO;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidVendorFileVO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  技术交流供应商回复表 前端控制器
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/techDiscuss/techDiscussReply")
public class TechDiscussReplyController extends BaseController {

    @Autowired
    private ITechDiscussReplyService iTechDiscussReplyService;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private IBidVendorFileService bidVendorFileService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public TechDiscussReply get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTechDiscussReplyService.getById(id);
    }

    /**
     * 技术交流--保存
     *
     * @param techDiscussReplyVO
     */
    @PostMapping("/saveTechDiscussReplyInfo")
    public TechDiscussReplyVO saveTechDiscussReplyInfo(@RequestBody TechDiscussReplyVO techDiscussReplyVO) {
        techDiscussReplyVO.setStatus(YesOrNo.NO.getValue());
        iTechDiscussReplyService.saveTechDiscussReplyInfo(techDiscussReplyVO);
        return techDiscussReplyInfo(techDiscussReplyVO);
    }

    /**
     * 技术交流--回复
     *
     * @param techDiscussReplyVO
     */
    @PostMapping("/reply")
    public TechDiscussReplyVO reply(@RequestBody TechDiscussReplyVO techDiscussReplyVO) {
        techDiscussReplyVO.setStatus(YesOrNo.YES.getValue());
        iTechDiscussReplyService.saveTechDiscussReplyInfo(techDiscussReplyVO);
        return techDiscussReplyInfo(techDiscussReplyVO);
    }
    /**
     * 技术交流--回复--查询
     *
     * @param techDiscussReplyVO
     */
    @PostMapping("/techDiscussReplyInfo")
    public TechDiscussReplyVO techDiscussReplyInfo(@RequestBody TechDiscussReplyVO techDiscussReplyVO){
        Assert.notNull(techDiscussReplyVO.getProjId(), "项目id不能为空");
        TechDiscussReplyVO techDiscussReplyVO1 = iTechDiscussReplyService.listTechDiscussReplyInfo(techDiscussReplyVO).get(0);
        BidVendorFileVO vendorFileVO = new BidVendorFileVO();
        vendorFileVO.setBusinessId(techDiscussReplyVO.getProjId());
        vendorFileVO.setFileType(VendorFileType.TECH_DISCUSS_REPLY.getValue());
        List<BidVendorFileVO> files = bidVendorFileService.getVendorFileList(vendorFileVO);
        techDiscussReplyVO1.setFiles(files);
        return techDiscussReplyVO1;
    }

    /**
     * 新增
     *
     * @param techDiscussReply
     */
    @PostMapping("/add")
    public void add(@RequestBody TechDiscussReply techDiscussReply) {
        Long id = IdGenrator.generate();
        techDiscussReply.setReplyId(id);
        iTechDiscussReplyService.save(techDiscussReply);
    }

    /**
     * 删除
     *
     * @param id
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTechDiscussReplyService.removeById(id);
    }

    /**
     * 修改
     *
     * @param techDiscussReply
     */
    @PostMapping("/modify")
    public void modify(TechDiscussReply techDiscussReply) {
        iTechDiscussReplyService.updateById(techDiscussReply);
    }

    /**
     * 分页查询
     *
     * @param techDiscussReplyVO
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<TechDiscussReplyVO> listPage(@RequestBody TechDiscussReplyVO techDiscussReplyVO) {
        //设置供应商编码和名称
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        CompanyInfo companyInfo = supplierClient.getCompanyInfo(user.getCompanyId());
        techDiscussReplyVO.setSupplierCode(companyInfo.getCompanyCode());
        return new PageInfo<TechDiscussReplyVO>(iTechDiscussReplyService.listTechDiscussReplyInfo(techDiscussReplyVO));
    }

    /**
     * 根据项目id查询回复
     *
     * @return
     */
    @GetMapping("/listByProjId")
    public List<TechDiscussReply> listByProjId(Long projId) {
        return iTechDiscussReplyService.list(new QueryWrapper<>(new TechDiscussReply().setProjId(projId)));
    }

}

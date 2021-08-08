package com.midea.cloud.srm.bid.purchaser.projectmanagement.signupmanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.bargaining.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.SignUpStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidFileConfigMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.service.IBusinessProposalService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.service.ISignUpService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.signupmanagement.service.ISignUpManagementService;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.BidSignUpVO;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.BidVendorFileVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidFileConfig;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.entity.SignUp;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.signupmanagement.vo.SignUpBaseInfoVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.signupmanagement.vo.SignUpFileVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.signupmanagement.vo.SignUpInfoVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.signupmanagement.vo.SignUpManagementVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHeadFile;
import com.midea.cloud.srm.model.bid.suppliercooperate.entity.BidVendorFile;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  报名管理 前端控制器
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020-09-11 13:54:22
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/signUpManagement/management")
public class SignUpManagementController extends BaseController {

    @Autowired
    private ISignUpService iSignUpService;
    @Autowired
    private IBidVendorService iBidVendorService;
    @Autowired
    private IBidVendorFileService iBidVendorFileService;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private ISignUpManagementService iSignUpManagementService;
    @Autowired
    private IBusinessProposalService proposalService;
    @Autowired
    private BidFileConfigMapper configMapper;

    /**
     * 驳回
     *
     * @param bidVendorId
     * @param signUpId
     */
    @GetMapping("/reject")
    public void reject(Long bidVendorId, Long signUpId) {
        Assert.notNull(bidVendorId, "供应商表主键id不能为空");
        Assert.notNull(signUpId, "供应商报名记录表主键id不能为空");
        iBidVendorService.update(new BidVendor().setJoinFlag("N"),
                new UpdateWrapper<BidVendor>().setEntity(new BidVendor().setBidVendorId(bidVendorId)));
        iSignUpService.update(new SignUp().setSignUpStatus(SignUpStatus.REJECTED.getValue()),
                new UpdateWrapper<SignUp>().setEntity(new SignUp().setSignUpId(signUpId)));
    }

    /**
     * 分页查询报名供应商信息
     *
     * @param signUp
     * @return
     */
    @PostMapping("/listPageInfo")
    public PageInfo<SignUpManagementVO> listPageInfo(@RequestBody SignUp signUp) {
        Long bidingId = signUp.getBidingId();
        Assert.notNull(bidingId, "招标id不能为空");
        return iSignUpManagementService.getSignUpManagementPageInfo(bidingId, signUp.getPageNum(), signUp.getPageSize());
    }

    /**
     * 获取报名资料
     *
     * @param bidingId
     * @param vendorId
     */
    @GetMapping("/getSignUpInfo")
    public SignUpInfoVO getSignUpInfo(Long bidingId, Long vendorId) {
        Assert.notNull(bidingId, "招标id不能为空");
        Assert.notNull(vendorId, "供应商id不能为空");

        SignUpInfoVO signUpInfoVO = new SignUpInfoVO();
        SignUpBaseInfoVO signUpBaseInfoVO = new SignUpBaseInfoVO();

        CompanyInfo companyInfo = supplierClient.getCompanyInfo(vendorId);
        BeanCopyUtil.copyProperties(signUpBaseInfoVO, companyInfo);
        signUpBaseInfoVO.setAddress(new StrBuilder(signUpBaseInfoVO.getCompanyProvince()).append(signUpBaseInfoVO.getCompanyCity()).toString());

        List<BidVendorFile> bidVendorFileList = iBidVendorFileService.list(new QueryWrapper<>(new BidVendorFile().
                setBidingId(bidingId).setVendorId(vendorId)));

        List<SignUpFileVO> signUpFileVOList = new ArrayList<>();
        for (BidVendorFile bidVendorFile : bidVendorFileList) {
            SignUpFileVO signUpFileVO = new SignUpFileVO();
            BeanCopyUtil.copyProperties(signUpFileVO, bidVendorFile);
            signUpFileVO.setDocId(Long.valueOf(bidVendorFile.getDocId()));
            signUpFileVOList.add(signUpFileVO);
        }

        signUpInfoVO.setSignUpBaseInfoVO(signUpBaseInfoVO).setSignUpFileVOList(signUpFileVOList);
        return signUpInfoVO;
    }

    @GetMapping("/getTechInfo")
    public SignUpInfoVO getTechInfo(@RequestParam("bidingId") Long bidingId, @RequestParam("vendorId") Long vendorId) {
        Assert.notNull(bidingId, "招标id不能为空");
        Assert.notNull(vendorId, "供应商id不能为空");

        SignUpInfoVO signUpInfoVO = new SignUpInfoVO();
        SignUpBaseInfoVO signUpBaseInfoVO = new SignUpBaseInfoVO();

        CompanyInfo companyInfo = supplierClient.getCompanyInfo(vendorId);
        BeanCopyUtil.copyProperties(signUpBaseInfoVO, companyInfo);
        signUpBaseInfoVO.setAddress(new StrBuilder(signUpBaseInfoVO.getCompanyProvince()).append(signUpBaseInfoVO.getCompanyCity()).toString());

        List<OrderHeadFile> files = proposalService.getOrderHeadFileByVendorIdAndBidingId(bidingId, vendorId);
        Set<Long> configFileIds = files.stream().map(OrderHeadFile::getConfigFileId).collect(Collectors.toSet());
        Map<Long, String> fileMap = configMapper.selectList(Wrappers.lambdaQuery(BidFileConfig.class)
                .select(BidFileConfig::getRequireId, BidFileConfig::getFileName)
                .in(BidFileConfig::getRequireId, configFileIds)
        ).stream().collect(Collectors.toMap(BidFileConfig::getRequireId, BidFileConfig::getFileName));
        List<SignUpFileVO> signUpFileVOList = files.stream().filter(e -> Objects.nonNull(e.getVendorReferenceFileId())).map(e -> {
            SignUpFileVO signUpFileVO = BeanCopyUtil.copyProperties(e, SignUpFileVO::new);
            signUpFileVO.setDocId(e.getVendorReferenceFileId());
            signUpFileVO.setFileName(e.getVendorReferenceFileName());
            signUpFileVO.setReqFileName(fileMap.get(e.getConfigFileId()));
            return signUpFileVO;
        }).collect(Collectors.toList());

        signUpInfoVO.setSignUpBaseInfoVO(signUpBaseInfoVO).setSignUpFileVOList(signUpFileVOList);
        return signUpInfoVO;
    }
}

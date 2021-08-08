package com.midea.cloud.srm.bid.purchaser.projectmanagement.signupmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidingScope;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidVendorService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.service.ISignUpService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.signupmanagement.mapper.SignUpManagementMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.signupmanagement.service.ISignUpManagementService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.entity.SignUp;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.signupmanagement.vo.SignUpManagementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * <pre>
 * 招标-报名管理页
 * </pre>
 * 
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020-09-11 13:54:22
 *  修改内容:
 *          </pre>
 */
@Service
public class SignUpManagementServiceImpl implements ISignUpManagementService {

	@Autowired
	private IBidingService iBidingService;
	@Autowired
	private IBidVendorService iBidVendorService;
	@Autowired
	private ISignUpService iSignUpService;
	@Autowired
	private SignUpManagementMapper signUpManagementMapper;

	@Override
	public PageInfo<SignUpManagementVO> getSignUpManagementPageInfo(Long bidingId, Integer pageNum, Integer pageSize) {
		List<SignUpManagementVO> voList = new ArrayList<>();
		PageUtil.startPage(pageNum, pageSize);

		//若为邀请招标,项目发布之后,将邀请供应商的信息显示在报名管理界面
		Biding biding = iBidingService.getById(bidingId);
		if (BidingScope.INVITE_TENDER.getValue().equals(biding.getBidingScope()) && "Y".equals(biding.getReleaseFlag())) {
			return new PageInfo<SignUpManagementVO>(signUpManagementMapper.getSignUpManagementPageInfo(bidingId));
		} else {
			//若为公开招标,只显示供应商报名表内容
			List<SignUp> signUpList = iSignUpService.list(new QueryWrapper<>(new SignUp().setBidingId(bidingId)));
			if (CollectionUtils.isEmpty(signUpList)) {
				return new PageInfo<SignUpManagementVO>(voList);
			}
			for (SignUp tempSignUp : signUpList) {
				SignUpManagementVO vo = new SignUpManagementVO();
				BidVendor bidVendor =
						iBidVendorService.getOne(new QueryWrapper<>(new BidVendor().setVendorId(tempSignUp.getVendorId()).setBidingId(bidingId)));
				BeanCopyUtil.copyProperties(vo, bidVendor);
				vo.setRejectReason(tempSignUp.getRejectReason()).setSignUpStatus(tempSignUp.getSignUpStatus()).
						setReplyDatetime(tempSignUp.getReplyDatetime()).setSignUpId(tempSignUp.getSignUpId());
				voList.add(vo);
			}
		}

		return new PageInfo<SignUpManagementVO>(voList);
	}
}

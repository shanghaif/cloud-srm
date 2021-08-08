package com.midea.cloud.srm.bid.purchaser.projectmanagement.signupmanagement.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.signupmanagement.vo.SignUpManagementVO;

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
public interface ISignUpManagementService {

	PageInfo<SignUpManagementVO> getSignUpManagementPageInfo(Long bidingId, Integer pageNum, Integer pageSize);
}

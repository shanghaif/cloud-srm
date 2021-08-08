package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.signupmanagement.mapper;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.signupmanagement.vo.SignUpManagementVO;
import org.apache.ibatis.annotations.Param;

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
public interface SignUpManagementMapper {

    List<SignUpManagementVO> getSignUpManagementPageInfo(@Param("bidingId") Long bidingId);
}

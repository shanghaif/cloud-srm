package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.signupmanagement.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 报名管理-报名资料视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-7 17:36:03
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class SignUpInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private SignUpBaseInfoVO signUpBaseInfoVO;
    private List<SignUpFileVO> signUpFileVOList;
}

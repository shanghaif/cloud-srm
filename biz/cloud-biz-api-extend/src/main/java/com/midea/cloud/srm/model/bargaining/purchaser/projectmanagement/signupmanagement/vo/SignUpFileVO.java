package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.signupmanagement.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 报名管理-报名附件 视图对象
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
public class SignUpFileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reqFileName;//资料要求
    private Long docId;//文档中心ID
    private String fileName;//附件名称
    private String comments;//备注
}

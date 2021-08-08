package com.midea.cloud.srm.model.signature;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/16
 *  修改内容:
 * </pre>
 */
@Data
public class SigningParam extends BaseDTO {

    /**
     * 单据ID
     */
    private String receiptId;

    /**
     * 审批流示例ID
     */
    private String flowId;

    /**
     * 合同法编号
     */
    private String contractCode;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 文件ID
     */
    private Long fileuploadId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    private Double y;

    private Double x1;

    private Double x2;

    private String posPage;
}

package com.midea.cloud.srm.model.cm.contract.dto;

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
 *  修改日期: 2020/11/18
 *  修改内容:
 * </pre>
 */
@Data
public class PushContractParam extends BaseDTO {
    /**
     * 合同头ID
     */
    private Long contractHeadId;

    /**
     * 合同ID
     */
    private Long fileuploadId;

    /**
     * 用户姓名(真实)
     */
    private String name;

    /**
     * 手机号码(真实)
     */
    private String phone;

    /**
     * 邮箱(真实)
     */
    private String email;
}

package com.midea.cloud.srm.model.log.trace.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
*  <pre>
 *  用户使用痕迹明细表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-10 09:01:19
 *  修改内容:
 * </pre>
*/
@Data
public class UserTraceInfoDto extends BaseDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录IP
     */
    private String logIp;

    /**
     * 用户类型
     */
    private String userType;


}

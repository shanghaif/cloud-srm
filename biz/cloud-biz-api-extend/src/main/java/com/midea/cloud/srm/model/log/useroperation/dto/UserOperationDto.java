package com.midea.cloud.srm.model.log.useroperation.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
*  <pre>
 *  用户操作日志表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-11 13:53:23
 *  修改内容:
 * </pre>
*/
@Data
public class UserOperationDto extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 操作名称
     */
    private String methodName;

    /**
     * 日期开始
     */
    private Date operationTimeStart;

    /**
     * 日期结束
     */
    private Date operationTimeEnd;

    /**
     * 操作结果
     */
    private String resultStatus;

    /**
     * 模块
     */
    private String model;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 请求URL
     */
    private String requestUrl;

}

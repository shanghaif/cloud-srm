package com.midea.cloud.srm.model.log.trace.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
*  <pre>
 *  用户使用痕迹表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08 10:55:34
 *  修改内容:
 * </pre>
*/
@Data
public class UserTraceDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long traceId;

    /**
     * 日期
     */
    private LocalDate nowDate;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private String loginDate;

    /**
     * 退出时间
     */
    private String logoutDate;

    /**
     * 登录次数
     */
    private Long loginNum;

    /**
     * 单次在线时间
     */
    private Double singleOnlineTime;

    /**
     * 累计在线时间
     */
    private Double cumulativeOnlineTime;

    /**
     * 类型
     */
    private String type;

    private Integer pageNum;

    private Integer pageSize;

}

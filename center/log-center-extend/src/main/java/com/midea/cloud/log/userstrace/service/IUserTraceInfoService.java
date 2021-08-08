package com.midea.cloud.log.userstrace.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.log.trace.entity.UserTraceInfo;

/**
*  <pre>
 *  用户使用痕迹明细表 服务类
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
public interface IUserTraceInfoService extends IService<UserTraceInfo> {
    /**
     * 增加
     * @param username
     * @param userIP
     */
    void add(String username , String userIP, String userType);

    /**
     * 更新用户痕迹
     * @param username
     * @param userIP
     */
    void update(String username , String userIP);

}

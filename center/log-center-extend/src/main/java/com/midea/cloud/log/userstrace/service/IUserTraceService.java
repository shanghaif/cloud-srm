package com.midea.cloud.log.userstrace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.log.trace.entity.UserTrace;

/**
*
* <pre>
 * 用户使用痕迹明细表
 * </pre>
*
* @author wangpr
* @version 2.00.00
* 创建时间:2020-6-8 13:54:18
*
* <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
*/

public interface IUserTraceService extends IService<UserTrace> {

    /**
     * 分页查询
     * @param
     * @return
     * @throws Exception
     */
    PageInfo<UserTrace> queryUserTracesDto(UserTrace userTrace);

}

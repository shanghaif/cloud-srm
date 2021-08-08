package com.midea.cloud.srm.sup.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.bda.entity.BdaStateControl;

/**
*  <pre>
 *  业务状态控制 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-05 10:17:03
 *  修改内容:
 * </pre>
*/
public interface IBdaStateControlService extends IService<BdaStateControl> {

    PageInfo<BdaStateControl> listPageByParam(BdaStateControl bdaStateControl);

    void saveOrUpdateBda(BdaStateControl bdaStateControl);
}

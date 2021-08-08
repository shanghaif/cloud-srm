package com.midea.cloud.srm.base.configguide.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.configguide.entity.ConfigGuide;

/**
*  <pre>
 *  配置导引表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-17 10:14:16
 *  修改内容:
 * </pre>
*/
public interface IConfigGuideService extends IService<ConfigGuide> {

    ConfigGuide getInfoByUser();

    void saveOrUpdateConfigGuide(ConfigGuide configGuide);
}

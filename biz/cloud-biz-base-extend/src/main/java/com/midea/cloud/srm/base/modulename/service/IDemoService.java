package com.midea.cloud.srm.base.modulename.service;

import com.midea.cloud.srm.model.base.modulename.entity.Demo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  演示 服务类
 * </pre>
*
* @author huanghb14@example.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-12 13:25:26
 *  修改内容:
 * </pre>
*/
public interface IDemoService extends IService<Demo> {

    int countRecord();

}

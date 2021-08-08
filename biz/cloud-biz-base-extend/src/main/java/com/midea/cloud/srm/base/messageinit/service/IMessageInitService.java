package com.midea.cloud.srm.base.messageinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.messageinit.entity.MessageInit;

import java.util.List;

/**
*  <pre>
 *  消息定义 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-21 14:28:11
 *  修改内容:
 * </pre>
*/
public interface IMessageInitService extends IService<MessageInit> {

    PageInfo<MessageInit> listPageByParam(MessageInit messageInit);

    void saveOrUpdateMessageInit(MessageInit messageInit);

    void bathSaveOrUpdateMessageInit(List<MessageInit> messageInits);
}

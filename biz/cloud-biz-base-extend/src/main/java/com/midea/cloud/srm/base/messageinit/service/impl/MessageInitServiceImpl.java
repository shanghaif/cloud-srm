package com.midea.cloud.srm.base.messageinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.messageinit.mapper.MessageInitMapper;
import com.midea.cloud.srm.base.messageinit.service.IMessageInitService;
import com.midea.cloud.srm.model.base.messageinit.entity.MessageInit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
*  <pre>
 *  消息定义 服务实现类
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
@Service
public class MessageInitServiceImpl extends ServiceImpl<MessageInitMapper, MessageInit> implements IMessageInitService {

    @Override
    public PageInfo<MessageInit> listPageByParam(MessageInit messageInit) {
        PageUtil.startPage(messageInit.getPageNum(), messageInit.getPageSize());
        MessageInit queryInit = new MessageInit();
        queryInit.setMessageLanguage(messageInit.getMessageLanguage());
        QueryWrapper<MessageInit> wrapper = new QueryWrapper<MessageInit>(queryInit);
        wrapper.like(StringUtils.isNotBlank(queryInit.getMessageCode()),"MESSAGE_CODE",queryInit.getMessageCode());
        wrapper.like(StringUtils.isNotBlank(queryInit.getMessageValue()),"MESSAGE_VALUE",queryInit.getMessageValue());
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<MessageInit>(this.list(wrapper));
    }

    @Override
    @Transactional
    public void saveOrUpdateMessageInit(MessageInit messageInit) {
        Assert.notNull(messageInit, LocaleHandler.getLocaleMsg("不能传空值"));
        if(StringUtils.isBlank(messageInit.getMessageCode()) ||
        StringUtils.isBlank(messageInit.getMessageValue()) ||
        StringUtils.isBlank(messageInit.getMessageLanguage())){
            throw new BaseException(LocaleHandler.getLocaleMsg("消息编码、消息语言、消息值不能为空"));
        }
        if(StringUtil.checkString(messageInit.getMessageCode()) ||
            StringUtil.isContaitNumber(messageInit.getMessageCode())
        ){
           throw new BaseException(LocaleHandler.getLocaleMsg("消息编码只能包含英文字符，不允许有空格或其他特殊字符"));
        }
        List<MessageInit> messageInits = this.findByCodeAndLanguage(messageInit.getMessageCode(),
                messageInit.getMessageLanguage(),
                messageInit.getMessageId());
        if(!CollectionUtils.isEmpty(messageInits)){
            throw new BaseException(LocaleHandler.getLocaleMsg("消息已配置"+messageInit.getMessageCode()));
        }
        if(messageInit.getMessageId() != null){
            messageInit.setMessageCode(null);
            this.updateById(messageInit);
        }else {
            Long id = IdGenrator.generate();
            messageInit.setMessageId(id);
            this.save(messageInit);
        }
    }

    @Override
    public void bathSaveOrUpdateMessageInit(List<MessageInit> messageInits) {
        for(MessageInit messageInit:messageInits){
            this.saveOrUpdateMessageInit(messageInit);
        }
    }

    private List<MessageInit> findByCodeAndLanguage(String messageCode, String messageLanguage, Long messageId){
        MessageInit queryMessageInit = new MessageInit();
        queryMessageInit.setMessageCode(messageCode);
        queryMessageInit.setMessageLanguage(messageLanguage);
        QueryWrapper<MessageInit> wrapper = new QueryWrapper<>(queryMessageInit);
        wrapper.ne(messageId != null,"MESSAGE_ID",messageId);
        return  this.list(wrapper);
    }


}

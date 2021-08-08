package com.midea.cloud.srm.base.messageinit.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.messageinit.service.IMessageInitService;
import com.midea.cloud.srm.model.base.messageinit.entity.MessageInit;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  消息定义 前端控制器
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
@RestController
@RequestMapping("/messageInit")
public class MessageInitController extends BaseController {

    @Autowired
    private IMessageInitService iMessageInitService;

    /**
    * 获取
    * @param messageId
    */
    @GetMapping("/get")
    public MessageInit get(Long messageId) {
        Assert.notNull(messageId, "id不能为空");
        return iMessageInitService.getById(messageId);
    }

    /**
    * 新增
    * @param messageInit
    */
    @PostMapping("/add")
    public void add(@RequestBody MessageInit messageInit) {
        Long id = IdGenrator.generate();
        messageInit.setMessageId(id);
        iMessageInitService.save(messageInit);
    }
    
    /**
    * 删除
    * @param messageId
    */
    @GetMapping("/delete")
    public void delete(Long messageId) {
        Assert.notNull(messageId, "id不能为空");
        iMessageInitService.removeById(messageId);
    }

    /**
    * 修改
    * @param messageInit
    */
    @PostMapping("/modify")
    public void modify(@RequestBody MessageInit messageInit) {
        iMessageInitService.updateById(messageInit);
    }

    /**
    * 分页查询
    * @param messageInit
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<MessageInit> listPageByParam(@RequestBody MessageInit messageInit) {
        return  iMessageInitService.listPageByParam(messageInit);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<MessageInit> listAll() { 
        return iMessageInitService.list();
    }


    /**
     * 保存或变更
     * @param messageInit
     * @return
     */
    @PostMapping("/saveOrUpdateMessageInit")
    public void saveOrUpdateMessageInit(@RequestBody MessageInit messageInit) {
          iMessageInitService.saveOrUpdateMessageInit(messageInit);
    }

    /**
     * 保存或变更
     * @param messageInits
     * @return
     */
    @PostMapping("/bathSaveOrUpdateMessageInit")
    public void bathSaveOrUpdateMessageInit(@RequestBody List<MessageInit> messageInits) {
        iMessageInitService.bathSaveOrUpdateMessageInit(messageInits);
    }
 
}

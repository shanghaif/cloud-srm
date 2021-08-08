package com.midea.cloud.srm.base.rediscache.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.rediscache.service.IRedisCacheManService;
import com.midea.cloud.srm.model.base.rediscache.entity.RedisCacheMan;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  redis缓存管理表 前端控制器
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-25 10:13:00
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/rediscache")
public class RedisCacheManController extends BaseController {

    @Autowired
    private IRedisCacheManService iRedisCacheManService;

    /**
    * 新增
    * @param redisCacheMans
    */
    @PostMapping("/add")
    public void add(@RequestBody List<RedisCacheMan> redisCacheMans) {
        iRedisCacheManService.add(redisCacheMans);
    }
    
    /**
    * 删除
    * @param redisCacheId
    */
    @GetMapping("/delete")
    public void delete(Long redisCacheId) {
        Assert.notNull(redisCacheId, "redisCacheId不能为空");
        iRedisCacheManService.removeById(redisCacheId);
    }

    /**
     * 批量删除
     * @param redisCacheId
     */
    @PostMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<Long> redisCacheIds) {
        if (CollectionUtils.isNotEmpty(redisCacheIds)) {
            iRedisCacheManService.removeByIds(redisCacheIds);
        }
    }

    /**
    * 修改
    * @param redisCacheMans
    */
    @PostMapping("/modify")
    public void modify(@RequestBody List<RedisCacheMan> redisCacheMans) {
        iRedisCacheManService.modify(redisCacheMans);
    }

    /**
    * 分页查询
    * @param redisCacheMan
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<RedisCacheMan> listPage(@RequestBody RedisCacheMan redisCacheMan) {
        return iRedisCacheManService.listPage(redisCacheMan);
    }

    /**
     * 根据redisKey查找内容
     * @param redisKey
     * @return
     */
    @PostMapping("/getRedisCacheContent")
    public String getRedisCacheContent(@RequestBody Map<String,String> param){
        String redisKey = param.get("redisKey");
        return iRedisCacheManService.getRedisCacheContent(redisKey);
    }

    /**
     * 根据redisKey前缀匹配删除
     * @param keyPrefix
     */
    @PostMapping("/delRedisCacheByPrefix")
    public void delRedisCacheByPrefix(@RequestBody Map<String,String> param) throws IOException {
        String keyPrefix = param.get("keyPrefix");
        if (StringUtil.notEmpty(keyPrefix)) {
            iRedisCacheManService.delRedisCacheByPrefix(keyPrefix);
        }
    }

    /**
     * 根据redisKey前缀匹配删除
     * @param redisKey
     */
    @PostMapping("/delRedisCacheByKey")
    public void delRedisCacheByKey(@RequestBody Map<String,String> param){
        iRedisCacheManService.delRedisCacheByKey(param.get("redisKey"));
    }

    /**
     * 根据redisKey检查缓存是否开启
     * @param redisKey
     */
    @GetMapping("/checkCacheIfOpen")
    public boolean checkCacheIfOpen(@RequestParam("redisKey") String redisKey){
        boolean flag = false;
        if (StringUtil.notEmpty(redisKey)) {
            List<RedisCacheMan> redisCacheMEN = iRedisCacheManService.list(Wrappers.lambdaQuery(RedisCacheMan.class).
                    select(RedisCacheMan::getIfOpen).
                    eq(RedisCacheMan::getCacheKey, redisKey));
            if(CollectionUtils.isNotEmpty(redisCacheMEN)){
                String ifOpen = redisCacheMEN.get(0).getIfOpen();
                if(YesOrNo.YES.getValue().equals(ifOpen)){
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 根据redisKey检查缓存是否开启
     * @param redisKey
     */
    @PostMapping("/checkCacheIfOpenNew")
    public boolean checkCacheIfOpenNew(@RequestBody RedisCacheMan redisCacheMan){
        return iRedisCacheManService.checkCacheIfOpenNew(redisCacheMan);
    }
 
}

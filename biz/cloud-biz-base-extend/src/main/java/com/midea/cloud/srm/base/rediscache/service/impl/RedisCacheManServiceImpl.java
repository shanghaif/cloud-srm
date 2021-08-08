package com.midea.cloud.srm.base.rediscache.service.impl;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.srm.base.rediscache.mapper.RedisCacheManMapper;
import com.midea.cloud.srm.base.rediscache.service.IRedisCacheManService;
import com.midea.cloud.srm.model.base.rediscache.entity.RedisCacheMan;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
*  <pre>
 *  redis缓存管理表 服务实现类
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
@Service
public class RedisCacheManServiceImpl extends ServiceImpl<RedisCacheManMapper, RedisCacheMan> implements IRedisCacheManService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    @Transactional
    public void add(List<RedisCacheMan> redisCacheMans) {
        if (CollectionUtils.isNotEmpty(redisCacheMans)) {
            redisCacheMans.forEach(redisCacheMan -> {
                String cacheKey = redisCacheMan.getCacheKey();
                List<RedisCacheMan> cacheMEN = this.list(Wrappers.lambdaQuery(RedisCacheMan.class).eq(RedisCacheMan::getCacheKey, cacheKey));
                if(CollectionUtils.isNotEmpty(cacheMEN)) throw new BaseException("缓存KEY存在重复");
                redisCacheMan.setRedisCacheId(IdGenrator.generate());
            });
            this.saveBatch(redisCacheMans);
        }
    }

    @Override
    @Transactional
    public void modify(List<RedisCacheMan> redisCacheMans) {
        if (CollectionUtils.isNotEmpty(redisCacheMans)) {
            redisCacheMans.forEach(redisCacheMan -> {
                String cacheKey = redisCacheMan.getCacheKey();
                List<RedisCacheMan> cacheMEN = this.list(Wrappers.lambdaQuery(RedisCacheMan.class).ne(RedisCacheMan::getRedisCacheId,redisCacheMan.getRedisCacheId()).eq(RedisCacheMan::getCacheKey, cacheKey));
                if(CollectionUtils.isNotEmpty(cacheMEN)) throw new BaseException("缓存KEY存在重复");
            });
            this.updateBatchById(redisCacheMans);
        }
    }

    @Override
    public PageInfo<RedisCacheMan> listPage(RedisCacheMan redisCacheMan) {
        PageUtil.startPage(redisCacheMan.getPageNum(),redisCacheMan.getPageSize());
        List<RedisCacheMan> cacheManList = this.list(Wrappers.lambdaQuery(RedisCacheMan.class).
                like(StringUtil.notEmpty(redisCacheMan.getInterfaceName()), RedisCacheMan::getInterfaceName, redisCacheMan.getInterfaceName()).
                like(StringUtil.notEmpty(redisCacheMan.getInterfaceUrl()), RedisCacheMan::getInterfaceUrl, redisCacheMan.getInterfaceUrl()).
                like(StringUtil.notEmpty(redisCacheMan.getCacheKey()), RedisCacheMan::getCacheKey, redisCacheMan.getCacheKey()).
                eq(StringUtil.notEmpty(redisCacheMan.getIfOpen()), RedisCacheMan::getIfOpen, redisCacheMan.getIfOpen()).
                orderByDesc(RedisCacheMan::getLastUpdateDate)
        );
        if(CollectionUtils.isNotEmpty(cacheManList)){
            cacheManList.forEach(redisCache -> {
                String cacheKey = redisCache.getCacheKey();
                /**
                 * 根据key 获取剩余过期时间
                 *
                 * @param key 键 不能为null
                 * @return 时间(秒)
                 * 返回-1代表为永久有效
                 * 返回-2代表key不存在
                 */
                long ttl = redisUtil.ttl(cacheKey);
                switch (String.valueOf(ttl)){
                    case "-1":
                        redisCache.setCacheTime("永久有效");
                        redisCache.setIfCacheContent(YesOrNo.YES.getValue());
                        break;
                    case "-2":
                        redisCache.setCacheTime("key不存在");
                        redisCache.setIfCacheContent(YesOrNo.NO.getValue());
                        break;
                    default:
                        redisCache.setCacheTime(String.valueOf(ttl));
                        redisCache.setIfCacheContent(YesOrNo.YES.getValue());
                }
            });
        }
        return new PageInfo<>(cacheManList);
    }

    @Override
    public String getRedisCacheContent(String redisKey) {
        Assert.notEmpty(redisKey,"redisKey不能为空");
        Object obj = redisUtil.get(redisKey);
        String content = null;
        if(null == obj) return null;
        try {
            content = JSON.toJSONString(obj);
        } catch (Exception e) {
            content = "该缓存数据无法做JSON解析";
        }
        return content;
    }

    @Override
    public void delRedisCacheByPrefix(String keyPrefix) throws IOException {
        Assert.notEmpty(keyPrefix,"keyPrefix不能为空");
        redisUtil.deleteByPattern(keyPrefix);
    }

    @Override
    public void delRedisCacheByKey(String redisKey) {
        Assert.notEmpty(redisKey,"redisKey不能为空");
        redisUtil.del(redisKey);
    }

    @Override
    public boolean checkCacheIfOpenNew(RedisCacheMan redisCacheMan) {
        boolean flag = false;
        RedisCacheMan redisCache = this.getOne(Wrappers.lambdaQuery(RedisCacheMan.class).
                eq(RedisCacheMan::getCacheKey, redisCacheMan.getCacheKey()).last("LIMIT 1"));
        if(null != redisCache){
            String ifOpen = redisCache.getIfOpen();
            if(YesOrNo.YES.getValue().equals(ifOpen)){
                flag = true;
            }
        }else {
            redisCacheMan.setIfOpen(YesOrNo.YES.getValue());
            redisCacheMan.setRedisCacheId(IdGenrator.generate());
            this.save(redisCacheMan);
            flag = true;
        }
        return flag;
    }
}

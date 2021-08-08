package com.midea.cloud.srm.base.rediscache.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.rediscache.entity.RedisCacheMan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
*  <pre>
 *  redis缓存管理表 服务类
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
public interface IRedisCacheManService extends IService<RedisCacheMan> {

    /**
     * 新增
     * @param redisCacheMans
     */
    void add(List<RedisCacheMan> redisCacheMans);

    /**
     * 修改
     * @param redisCacheMans
     */
    void modify(List<RedisCacheMan> redisCacheMans);

    /**
     * 分页查询
     * @param redisCacheMan
     * @return
     */
    PageInfo<RedisCacheMan> listPage(RedisCacheMan redisCacheMan);

    /**
     * 根据redisKey查找缓存内容
     * @param redisKey
     * @return
     */
    String getRedisCacheContent(String redisKey);

    /**
     * 根据redisKey前缀匹配删除
     * @param KeyPrefix
     */
    void delRedisCacheByPrefix(@RequestParam("KeyPrefix") String KeyPrefix) throws IOException;

    /**
     * 根据key删除缓存
     * @param redisKey
     */
    void delRedisCacheByKey(@RequestParam("redisKey") String redisKey);

    /**
     * 根据redisKey检查缓存是否开启
     * @param redisKey
     */
    boolean checkCacheIfOpenNew(@RequestBody RedisCacheMan redisCacheMan);

}

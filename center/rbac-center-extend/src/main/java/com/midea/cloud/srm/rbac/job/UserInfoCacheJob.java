package com.midea.cloud.srm.rbac.job;

import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.rbac.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * 用户信息缓存 [用户账号 -> 用户名]
 * </pre>`
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/04/1 15:42
 *  修改内容:
 * </pre>
 */
@Job("userInfoCacheJob")
public class UserInfoCacheJob implements ExecuteableJob {

    @Autowired
    IUserService userService;

    @Resource(name = "stringRedisTemplate")
    RedisTemplate redisTemplate;

    @Override
    public BaseResult executeJob(Map<String, String> params) {
        long startTimestamp = System.currentTimeMillis();
        try {
            int pageSize = 500;
            try {
                pageSize = Integer.valueOf(params.get("pageSize"));
            } catch (Exception ex) {
            }
            HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
            int count = userService.count();
            if (count > 0) {
                int totalPage = count / pageSize + (count % pageSize > 0 ? 1 : 0);
                for (int startPage = 0; startPage < totalPage; startPage++) {
                    PageUtil.startPage(startPage, pageSize);
                    List<User> userList = userService.list();
                    Map<String, String> userMap = userList.stream().collect(Collectors.toMap(User::getUsername, User::getNickname));
                    hashOperations.putAll(RedisKey.USER_CACHE, userMap);
                }
            }
        } catch (Exception ex) {
            return BaseResult.build(ResultCode.OPERATION_FAILED, "用户信息缓存写入失败", Arrays.toString(ex.getStackTrace()));
        }
        return BaseResult.buildSuccess("用户信息缓存写入成功：" + (System.currentTimeMillis() - startTimestamp) / 1000 + "s");
    }

}

package com.midea.cloud.srm.feign.oauth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <pre>
 *  认证中心模块 内部调用Feign接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 17:19
 *  修改内容:
 * </pre>
 */
@FeignClient("oauth-center")
public interface Oauth2Client {

    // token处理 [token] - >>>>>

    /**
     * 获取access_token
     *
     * @param parameters
     * @return
     */
    @PostMapping(path = "/oauth/token")
    Map<String, Object> postAccessToken(@RequestParam Map<String, String> parameters);

    /**
     * 删除access_token和refresh_token
     *
     * @param access_token
     */
    @DeleteMapping(path = "/remove_token")
    void removeToken(@RequestParam("access_token") String access_token);

    /**
     * 获取认证信息
     *
     * @param token
     * @return
     */
    @GetMapping(path = "/user-me")
    Map<String, Object> getUserInfoByToken(@RequestParam("access_token") String token);

    // token处理 [token] - <<<<<

}

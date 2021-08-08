package com.midea.cloud.srm.rbac.anon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.rbac.security.service.IUserSecurityService;
import com.midea.cloud.srm.rbac.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * 不需要认证，前端调用接口
 * </pre>
 *
 * @author chenhb30@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/19 15:53
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/rbac-anon")
public class CfmbRbacAnonController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IUserSecurityService iUserSecurityService;

    /**
     * 校验密码
     * @param userName
     * @return User
     */
    @PostMapping(value = "/internal/getUserByUserName")
    public User getUserByUserName(@RequestParam("userName")String userName){
        return iUserService.getOne(new QueryWrapper<>(new User().setUsername(userName)));
    }

    /**
     * 验证用户密码是否被锁住，锁住返回true，否则返回false
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/internal/verifyUserLock")
    public Boolean verifyUserLock (@RequestParam("userName")String userName, @RequestParam("password")String password) {
        return iUserSecurityService.verifyUserLock(userName, password);
    }

    /**
     * @author chenhb30@meicloud.com
     * @date 2020/9/19 15:59
     * @param userName
     * @param password
     * @return Boolean
     * @Desc 未登录下确认用户密码是否正确
     */
    @PostMapping("/internal/verifyPassword")
    public Boolean verifyPassword(@RequestParam("userName")String userName, @RequestParam("password")String password){
        return iUserSecurityService.verifyPassword(userName,password);
    }
}


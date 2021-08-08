package com.midea.cloud.srm.rbac.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.rbac.security.dto.UserSecurityDto;
import com.midea.cloud.srm.model.rbac.security.entity.UserSecurity;
import com.midea.cloud.srm.model.rbac.user.entity.User;

/**
 * <pre>
 *  用户安全 服务类
 * </pre>
 *
 * @author haibo1.huang@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-03 09:15:27
 *  修改内容:
 * </pre>
 */
public interface IUserSecurityService extends IService<UserSecurity> {

    /**
     * 累计用户密码错误次数
     *
     * @param user
     */
    void cumulatePasswordErrorTime(User user, UserSecurity userSecurity);

    /**
     * 用户密码错误信息清零
     *
     * @param userSecurity
     */
    void removeUserLock(UserSecurity userSecurity);

    /**
     * 验证用户密码是否被锁住，锁住返回true，否则返回false
     *
     * @param username
     * @return
     */
    Boolean verifyUserLock(String username, String logPassword);

    /**
     * @author chenhb30@meicloud.com
     * @date 2020/9/22 8:48
     * @param userName
     * @param password
     * @return java.lang.Boolean
     * @Desc 确认用户密码是否正确
     */
    Boolean verifyPassword(String userName, String password);
    
    /**
     * 保存上传人脸识别
     * @param userSecurity
     * @return
     */
    Boolean modifyFace(UserSecurityDto userSecurityDto);
    
    /**
     * 校验人脸识别
     * @param userSecurity
     * @return
     */
    Boolean verifyFace(UserSecurityDto userSecurityDto);
    
}

package com.midea.cloud.srm.rbac.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.idm.esb.mainusers.schemas.mainusers.v1.IdmUserResponse;
import com.midea.cloud.srm.model.base.soap.idm.updatepassword.schemas.updatepassword.v1.IdmUpdUserResponse;
import com.midea.cloud.srm.model.rbac.role.entity.RoleUser;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO;
import com.midea.cloud.srm.model.rbac.user.dto.UserPermissionDTO;
import com.midea.cloud.srm.model.rbac.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *  用户信息 服务类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:27:10
 *  修改内容:
 * </pre>
 */
public interface IUserService extends IService<User> {

    /**
     * 通过查询参数获取用户相关信息
     *
     * @param user
     * @return
     */
    UserPermissionDTO getUserByParam(User user);

    /**
     * 保存用户相关信息
     *
     * @param userPermissionDTO
     */
    void save(UserPermissionDTO userPermissionDTO);

    /**
     * 修改用户相关信息
     *
     * @param userPermissionDTO
     */
    void update(UserPermissionDTO userPermissionDTO);

    /**
     * 校验用户信息
     *
     * @param user
     * @param validPassword
     */
    void validUser(User user, boolean validPassword);

    /**
     * 校验角色用户信息
     *
     * @param roleUsers
     * @param userId
     */
    void validRoleUser(List<RoleUser> roleUsers, Long userId);

    /**
     * 校验组织与用户关系信息
     *
     * @param organizationUsers
     * @param userId
     */
    void validOrganizationUser(List<OrganizationUser> organizationUsers, Long userId);

    /**
     * 通过查询参数获取用户列表
     *
     * @param user
     * @return
     */
    List<User> listByParam(User user);

    User queryByCompanyId(Long companyId);

    void deleteByCompanyId(Long companyId);

    void binding(User user);

    /**
     * 重置用户密码(发送邮件)
     *
     * @param userId 用户ID
     * @param email 邮箱
     */
    void resetPasswordByEmail(Long userId, String email);

    /**
     * 重置用户密码(不发送邮件)
     *
     * @param userId 用户ID
     * @param password 密码
     * @param email 邮箱
     * @param isSendEmail 是否发送邮箱，是-true，否-false
     */
    void resetPasswordByNoEmail(Long userId, String password, String email, boolean isSendEmail);

    /**
     * 添加供应商主账号
     *
     * @param user
     * @param urlAddress
     */
    void addBuyerMain(User user, String urlAddress);

    /**
     * 校验旧密码
     *
     * @param user
     */
    void checkOldPassword(User user);

    /**
     * 修改密码
     *
     * @param user
     */
    void modifyPassword(User user);

    /**
     * 修改个人信息
     *
     * @param user
     */
    void modifyUser(User user);


    /**
     * 根据公司ID和账号类型获取用户
     *
     * @param user
     * @return
     */
    User getUserByParm(User user);

    /**
     * 通过邮件重置用户密码前进行校验
     *
     * @param userDTO
     */
    User checkBeforeResetUserPwByEmail(UserDTO userDTO);

    /**
     * 通过手机短信重置用户密码前进行校验
     *
     * @param userDTO
     * @return
     */
    User checkBeforeResetUserPwByPhone(UserDTO userDTO);

    /**
     * 通过手机短信重置密码
     *
     * @param userId
     * @param phone
     */
    void resetPasswordByPhone(Long userId, String phone);

    /**
     * Description 保存供应商相关信息
     *
     * @return
     * @throws
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.25
     **/
    Long saveVendor(User user);

    /**
     * Description 主账号同步到IDM
     *
     * @return
     * @throws
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.26
     **/
    IdmUserResponse createIdmUser(User user);

    /**
     * Description 修改密码同步到IDM
     *
     * @return
     * @throws
     * @Param user 用户表
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.27
     **/
    IdmUpdUserResponse changePasswordForIdmUser(User user);

    /**
     * Description IDM修改密码同步到Srm
     *
     * @return
     * @throws
     * @Param instId 插入表Id， requestTime请求时间，user 用户表
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.27
     **/
    SoapResponse changePasswordForSrm(String instId,String requestTime, String emplId, String newPassword, String oldPassword);

    /**
     * 获取验证码
     * @param response
     * @param request
     * @throws IOException
     */
    void getVerificationCode(HttpServletResponse response, HttpServletRequest request) throws IOException;

    /**
     * 校验验证
     * @param verifyCode
     * @param request
     */
    void checkVerifyCode(String verifyCode,HttpServletRequest request);
    
    /**
     * 更新用户信息
     * @param userPermissionDTO
     */
    void updateUser(UserPermissionDTO userPermissionDTO);
}

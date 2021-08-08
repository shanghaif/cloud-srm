package com.midea.cloud.srm.feign.rbac;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.UserDto;
import com.midea.cloud.srm.model.log.trace.dto.UserTraceInfoDto;
import com.midea.cloud.srm.model.rbac.function.entity.Function;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.po.entity.PoAgent;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.model.rbac.role.entity.RoleFuncSet;
import com.midea.cloud.srm.model.rbac.role.entity.RoleUser;
import com.midea.cloud.srm.model.rbac.security.dto.UserSecurityDto;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO1;
import com.midea.cloud.srm.model.rbac.user.dto.UserPermissionDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  用户角色控制中心模块 内部调用Feign接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: xiexh12@meicloud.com
 *  修改日期: 2020-8-19 09:19
 *  修改内容:
 * </pre>
 */
@FeignClient(value = "rbac-center")
public interface RbacClient {

    /**
     * 根据用户名获取用户
     * @param userName
     * @return User
     */
    @PostMapping(value = "/rbac-anon/internal/getUserByUserName")
    User getUserByUserName(@RequestParam("userName")String userName);

    /**
     * 验证用户密码是否被锁住，锁住返回true，否则返回false
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/rbac-anon/internal/verifyUserLock")
    Boolean verifyUserLock (@RequestParam("userName")String userName, @RequestParam("password")String password);

    /**
     * 校验密码
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value = "/rbac-anon/internal/verifyPassword")
    Boolean verifyPassword(@RequestParam("userName")String userName, @RequestParam("password")String password);

    /**
     * 当前登录用户 LoginAppUser
     *
     * @return
     */
    @GetMapping("/user/current")
    public LoginAppUser getUser();

    // 用户信息[user] - >>>>>

    /**
     * 统计用户数量
     *
     * @return
     */
    @GetMapping(value = "/rbac-anon/internal/user/getAccountNum")
    Integer getAccountNum();

    /**
     * 通过用户名获取用户信息
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/rbac-anon/internal/user/findByUsername")
    LoginAppUser findByUsername(@RequestParam("username") String username);

    /**
     * 供应商注册接口，-anon后缀表示：该接口不允许外部通过网关调用的接口
     *
     * @param user
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/register")
    Long registerVendor(@RequestBody User user);

    /**
     * 通过用户名查是否有重复名字
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/rbac-anon/internal/user/checkByUsername")
    List<User> checkByUsername(@RequestParam("username") String username);


    /**
     * 根据公司Id删除供应商
     *
     * @param companyId
     * @return
     */
    @GetMapping("/rbac-anon/internal/user/deleteByCompanyId")
    void deleteByCompanyId(@RequestParam("companyId") Long companyId);

    /**
     * 根据参数公司id以及用户id绑定对应的供应商
     *
     * @param user
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/binding")
    void binding(@RequestBody User user);

    /**
     * 根据入账单位参数查询供应商用户
     *
     * @param companyId
     * @return
     */
    @GetMapping("/rbac-anon/internal/user/queryByCompanyId")
    User queryByCompanyId(@RequestParam("companyId") Long companyId);

    /**
     * Description 查询所有类型用户(内部接口调用)
     *
     * @return
     * @throws
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.29
     **/
    @PostMapping("/rbac-anon/internal/user/listByBuyer")
    List<User> listByUser(@RequestBody User user);


    /**
     * 分页查询所有类型用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/listByBuyer")
    PageInfo<User> listByBuyer(@RequestBody User user);

    /**
     * 通过用户ID获取用户相关信息
     *
     * @param id
     * @return
     * @Author huangbf3@meicloud.com
     */
    @GetMapping(value = "/user/getByBuyer")
    UserPermissionDTO getByBuyer(@RequestParam("id") Long id);

    /**
     * 注册采购商主账号（内部调用接口，不暴露到外部）
     *
     * @param user
     * @param urlAddress
     */
    @PostMapping(value = "/rbac-anon/internal/user/registerBuyerMain")
    void registerBuyerMain(@RequestBody User user, @RequestParam("urlAddress") String urlAddress);


    /**
     * 查询所有类型用户
     *
     * @param user
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/listAll")
    PageInfo<User> listAllForAnon(@RequestBody User user);


    /**
     * 根据公司ID和账号类型获取用户
     *
     * @param user
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/getUserByParm")
    User getUserByParmForAnon(@RequestBody User user);


    // 用户信息[user] - <<<<<


    // 角色信息[role] - >>>>>

    /**
     * 根据条件获取角色
     *
     * @param role
     * @return
     */
    @PostMapping("/rbac-anon/internal/role/getRoleByParm")
    Role getRoleByParmForAnon(@RequestBody Role role);


    /**
     * 根据userId修改角色
     *
     * @param userId
     */
    @PostMapping("/rbac-anon/internal/roleUser/modifyRoleByUserId")
    void modifyRoleByUserIdForAnon(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId);

    /**
     * 查询所有角色
     *
     * @return
     */
    @PostMapping("/role/role/listAll")
    List<Role> queryRoleAll();

    /**
     * 根据角色编码获取角色id
     */
    @PostMapping("/rbac-anon/internal/role/getRoleByRoleCode")
    List<Role> getRoleByRoleCodeForAnon(@RequestBody List<String> roleCodes);

    /**
     * 根据用户角色id获取员工id
     */
    @PostMapping("/rbac-anon/internal/role/getUserByRoleId")
    List<RoleUser> getUserByRoleId(@RequestBody Map<String,List<Long>> map);

    /**
     * 根据员工ID获取角色ID
     */
    @PostMapping("/rbac-anon/internal/role/getRoleByUserId")
    List<RoleUser> getRoleByUserId(@RequestBody List<Long> userIds);

    /**
     * 根据角色ID获取角色编码
     */
    @PostMapping("/rbac-anon/internal/role/getRoleCodeByUserIdForAnon")
    List<Role> getRoleCodeByUserIdForAnon(@RequestBody List<Long> roleIdlist);


    // 角色信息[role] - <<<<<

    // 权限信息[permission] - >>>>>

    /**
     * Description 获取所有启动流程的菜单集合
     *
     * @return
     * @throws
     * @Param permission
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.23
     **/
    @PostMapping("/rbac-anon/internal/perm/permission/queryEnablePermission")
    List<Permission> queryEnablePermission(@RequestBody Permission permission);


    /**
     * 根据菜单功能id获取菜单功能
     *
     * @param menuId
     * @return
     */
    @GetMapping("/perm/permission/getMenu")
    Permission getMenu(@RequestParam("menuId") Long menuId);


    /**
     * 通过固定条件查询功能
     *
     * @return
     */
    @PostMapping("/perm/permission/listFunctionByParm")
    List<Function> listFunctionByParm(@RequestBody Permission permission);


    // 权限信息[permission] - <<<<<

    /**
     * 对账号进行授权
     *
     * @param userId
     * @param roleType
     */
    @PostMapping("/rbac-anon/internal/role/initUserRole")
    void initUserRole(@RequestParam("userId") Long userId, @RequestParam("roleType") String roleType);


    /**
     * 根据参数查询账号的数量
     *
     * @param user
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/getCountByParam")
    public int getCountByParam(@RequestBody User user);

    /**
     * 根据QueryWarpper查询用户列表
     *
     * @param queryUser
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/listUsers")
    public List<User> listUsers(@RequestBody User queryUser);

    /**
     * 根据QueryWarpper查询用户
     *
     * @param queryUser
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/getUser")
    public User getUser(@RequestBody User queryUser);

    /**
     * 查询全部用户的ceeaEmpNo
     */
    @PostMapping("/rbac-anon/internal/user/getgetUserIdAndCeeaEmpNos")
    public List<UserDTO1> getUserIdAndCeeaEmpNos();

    /**
     * save users batch
     *
     * @param usersList
     * @date 2020-08-30
     */
    @PostMapping("/rbac-anon/internal/user/saveBatchUsers")
    public boolean saveBatchUsers(@RequestBody List<User> usersList);

    /**
     * update users batch
     *
     * @param usersList
     * @date 2020-08-30
     */
    @PostMapping("/rbac-anon/internal/user/updateBatchUsers")
    public boolean updateBatchUsers(@RequestBody List<User> usersList);

    /**
     * Description 批量新增或修改用户信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.10.30
     * @throws
     **/
    @PostMapping("/rbac-anon/internal/user/saveOrUpdateBatchUsers")
    public boolean saveOrUpdateBatchUsers(@RequestBody List<User> usersList);

    /**
     * save scc_base_employees' data to rbac_user
     *
     * @param usersList
     * @update xiexh12@meicloud.com
     * @date 2020-08-30
     */
    @PostMapping("/rbac-anon/internal/user/saveUsersFromEmployee")
    public boolean saveUsersFromEmployee(@RequestBody List<User> usersList);

    /**
     * update scc_base_employees' data to rbac_user
     *
     * @param usersList
     * @update xiexh12@meicloud.com
     * @date 2020-08-30
     */
    @PostMapping("/rbac-anon/internal/user/updateUsersFromEmployee")
    public boolean updateUsersFromEmployee(@RequestBody List<User> usersList);

    /**
     * 根据id获取用户信息
     *
     * @param userIds
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/getByUserIds")
    List<User> getByUserIds(@RequestBody Collection<Long> userIds);

    /**
     * 根据用户名s获取用户信息
     */
    @PostMapping("/rbac-anon/internal/user/getUserByNos")
    Map<String,User> getUserByNos(@RequestBody Collection<String> userByNos);

    /**
     * 根据条件获取角色功能设置集合
     * @return
     */
    @PostMapping("/rbac-anon/internal/role/findRoleFuncSetByParam")
    List<RoleFuncSet> findRoleFuncSetByParam(@RequestBody RoleFuncSet roleFuncSet);

    /**
     * 根据条件获取角色权限信息
     * @param functionCode  菜单编码
     * @param roleCodes     角色编码集合
     * @return
     */
    @PostMapping("/rbac-anon/internal/role/findRoleFuncSetMoreRole")
    List<RoleFuncSet> findRoleFuncSetMoreRole(@RequestParam("functionCode")String functionCode,
                                              @RequestBody List<String> roleCodes);

    /**
     * 根据条件查询采购员
     * @param poAgent
     * @return
     */
    @PostMapping("/po/po-agent/listByParams")
    List<PoAgent> listPoAgentByParams(@RequestBody PoAgent poAgent);

    /**
     * 根据用户工号查询符合条件的用户
     * @param usersParamCodeList
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/listUsersByUsersParamCode")
    List<User> listUsersByUsersParamCode(@RequestBody List<String> usersParamCodeList);

    /**
     *
     * @param usersParamNickNameList
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/listUsersByUsersParamNickName")
    List<User> listUsersByUsersParamNickName(@RequestBody List<String> usersParamNickNameList);


    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    @GetMapping("/rbac-anon/user/getUserById")
    User getUserByIdAnon(@RequestParam("id") Long id);

    /**
     * 根据条件获取采购员
     * @param poAgent
     * @return
     */
    @PostMapping("/rbac-anon/poAgent/listPoAgentByParam")
    List<PoAgent> listPoAgentByParamAnon(@RequestBody PoAgent poAgent);
    
    /**
     * 根据QueryWarpper查询用户列表
     *
     * @param queryUser
     * @return
     */
    @PostMapping("/rbac-anon/internal/user/listUsersPage")
    public List<User> listUsersPage(@RequestBody User queryUser, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
    

    /**
     * 保存用户登录痕迹
     *
     * @param userTraceInfoDto
     */
    @PostMapping("/traceinfo/save")
    void saveUserTrace(@RequestBody UserTraceInfoDto userTraceInfoDto);

    /**
     * 更新用户登录痕迹
     *
     * @param userTraceInfoDto
     */
    @PostMapping("/traceinfo/update")
    void updateUserTrace(@RequestBody UserTraceInfoDto userTraceInfoDto);

    /**
     * 通过dto查询用户信息
     */
    @PostMapping("/rbac-anon/listUsersByUsreDto")
    public List<User> listUsersByUsreDto(@RequestBody UserDto userDto);

    /**
     * 校验人脸识别
     *
     */
    @PostMapping("/rbac-anon/verifyFace")
    Boolean verifyFace(@RequestBody UserSecurityDto userSecurityDto);


}

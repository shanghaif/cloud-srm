package com.midea.cloud.srm.rbac.anon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.MainType;
import com.midea.cloud.common.enums.RoleType;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.UserDto;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.po.entity.PoAgent;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.model.rbac.role.entity.RoleFuncSet;
import com.midea.cloud.srm.model.rbac.role.entity.RoleUser;
import com.midea.cloud.srm.model.rbac.security.dto.UserSecurityDto;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO1;
import com.midea.cloud.srm.model.rbac.user.dto.UserPermissionDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.rbac.permission.service.IPermissionService;
import com.midea.cloud.srm.rbac.role.mapper.RoleMapper;
import com.midea.cloud.srm.rbac.role.service.IRoleFuncSetService;
import com.midea.cloud.srm.rbac.role.service.IRoleService;
import com.midea.cloud.srm.rbac.role.service.IRoleUserService;
import com.midea.cloud.srm.rbac.security.service.IUserSecurityService;
import com.midea.cloud.srm.rbac.soap.po.poagent.service.IPoAgentService;
import com.midea.cloud.srm.rbac.user.controller.UserController;
import com.midea.cloud.srm.rbac.user.mapper.UserMapper;
import com.midea.cloud.srm.rbac.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务内部调用接口(无需登录就可以对微服务模块间暴露)
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-11 9:09
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/rbac-anon")
@Slf4j
public class RbacAnonController {

    @Autowired
    private IPermissionService iPermissionService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRoleFuncSetService iRoleFuncSetService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IPoAgentService poAgentService;
    
    @Autowired
    private UserController userController;
    
    @Autowired
    private IUserSecurityService iUserSecurityService;
    

    /**
     * 忘记密码验证获取
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/getVerifyCode")
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        iUserService.getVerificationCode(response,request);
    }

    /**
     * 校验验证码
     * @param verifyCode
     */
    @PostMapping("/checkVerifyCode")
    public void checkVerifyCode(String verifyCode)  {
        iUserService.checkVerifyCode(verifyCode, SessionUtil.getRequest());
    }

    /**
     * Description 获取所有启动流程的菜单集合
     *
     * @return
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.23
     **/
    @PostMapping("/internal/perm/permission/queryEnablePermission")
    public List<Permission> queryEnablePermission(@RequestBody Permission permission) {
        return iPermissionService.queryPermissionListByParam(permission);
    }

    /**
     * Description 获取角色信息并且判断
     *
     * @return
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.23
     **/
    @PostMapping("/internal/role/initUserRole")
    public void initUserRole(@RequestParam("userId") Long userId, @RequestParam("roleType") String roleType) {
        Assert.notNull(userId, "用户ID不能为空");
        Assert.hasLength(roleType, "角色类型不能为空");
        //先删除用户注册默认的角色权限再进行角色新增
        QueryWrapper<Role> deleteRoles = new QueryWrapper<Role>();
        deleteRoles.like("ROLE_TYPE", RoleType.REG_DEFAULT.name());
        List<Role> delRegDefaultRoles = iRoleService.list(deleteRoles);
        if (CollectionUtils.isNotEmpty(delRegDefaultRoles)) {
            for (Role role : delRegDefaultRoles) {
                RoleUser roleUser = new RoleUser();
                roleUser.setUserId(userId);
                roleUser.setRoleId(role.getRoleId());
                iRoleUserService.remove(new QueryWrapper<>(roleUser));
            }
        }
        QueryWrapper<Role> queryRoleWrapper = new QueryWrapper<Role>();
        queryRoleWrapper.eq("ROLE_TYPE", roleType);
        List<Role> regDefaultRoles = iRoleService.list(queryRoleWrapper);
        if (CollectionUtils.isNotEmpty(regDefaultRoles)) {
            // 授权供应商对应的角色
            List<RoleUser> roleUsers = new ArrayList<RoleUser>();
            regDefaultRoles.forEach(regDefaultRole -> {
                List<RoleUser> list = iRoleUserService.list(new QueryWrapper<>(new RoleUser().
                        setUserId(userId).setRoleId(regDefaultRole.getRoleId())));
                if (CollectionUtils.isEmpty(list)) {
                    roleUsers.add(new RoleUser().setRoleUserId(IdGenrator.generate())
                            .setUserId(userId).setRoleId(regDefaultRole.getRoleId()).setStartDate(LocalDate.now()));
                }
            });
            if (CollectionUtils.isNotEmpty(roleUsers)) {
                iRoleUserService.saveBatch(roleUsers);
            }
        }
    }

    /**
     * 内部调用接口，通过用户名获取用户信息
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/internal/user/findByUsername")
    public LoginAppUser findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        User user = iUserService.getOne(new QueryWrapper<User>(new User().setUsername(username)));
        if (user == null) {
            return null;
        }
        LoginAppUser loginAppUser = new LoginAppUser();
        BeanCopyUtil.copyProperties(loginAppUser, user);
        // 加载用户按钮权限(后台受保护资源)权限 TODO 考虑加入缓存
        Permission queryPermission = new Permission(); // 获取所有按钮权限
        List<Permission> buttonPermissions = iPermissionService.queryByAuthButton(queryPermission, user.getUserId());
        HashSet<String> buttonCodes = new HashSet<String>();
        buttonPermissions.forEach(per -> {
            buttonCodes.add(per.getPermission());
        });
        loginAppUser.setPermissions(buttonCodes);
        // 设置供应商公司名字
        Long companyId = loginAppUser.getCompanyId();
        if (companyId != null) {
            CompanyInfo companyInfo = supplierClient.getCompanyInfoForAnon(companyId);
            if (companyInfo != null) {
                loginAppUser.setCompanyCode(companyInfo.getCompanyCode());
                loginAppUser.setCompanyName(companyInfo.getCompanyName());
            }
        }
        // 加载供应商组织权限
        OrganizationUser organizationUser =new OrganizationUser().setUserId(loginAppUser.getUserId());
        List<OrganizationUser> organizationUsers = baseClient.annoListOrganUserByParam(organizationUser);
        loginAppUser.setOrganizationUsers(organizationUsers);
        loadRoles(loginAppUser);
        return loginAppUser;
    }

    //加载用户角色权限
    private void loadRoles(LoginAppUser loginAppUser) {
        Long userId = loginAppUser.getUserId();
        List<RoleUser> roleUsers = iRoleUserService.list(new QueryWrapper<>(new RoleUser().setUserId(userId)));
        List<Role> rolePermissions = new ArrayList<>();
        if (!org.springframework.util.CollectionUtils.isEmpty(roleUsers)) {
            for (RoleUser roleUser : roleUsers) {
                if (roleUser == null) continue;
                List<Role> roles = roleMapper.selectByParam(new Role().setRoleId(roleUser.getRoleId()), loginAppUser.getUserType(), LocaleHandler.getLocaleKey());
                rolePermissions.addAll(roles);
            }
        }
        loginAppUser.setRolePermissions(rolePermissions);
    }

    /**
     * 内部调用接口，通过用户名检查是否有名字重复
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/internal/user/checkByUsername")
    public List<User> checkByUsername(String username) {
        Assert.hasLength(username, "账号名不能为空");
        User user = new User();
        user.setUsername(username);
        QueryWrapper<User> wrapper = new QueryWrapper<User>(user);
        return iUserService.list(wrapper);
    }

    /**
     * 供应商注册
     *
     * @param user
     */
    @PostMapping("/internal/user/register")
    public Long register(@RequestBody User user) {
//        iUserService.validUser(user, true); // 校验用户信息
        return iUserService.saveVendor(user);
    }


    /**
     * 根据参数查询供应商用户
     *
     * @param companyId
     * @return
     */
    @GetMapping("/internal/user/queryByCompanyId")
    public User queryByCompanyId(Long companyId) {
        if (companyId == null) {
            throw new BaseException("当前公司ID不允许空");
        }
        return iUserService.queryByCompanyId(companyId);
    }

    /**
     * 根据参数查询账号的数量
     *
     * @param user
     * @return
     */
    @PostMapping("/internal/user/getCountByParam")
    public int getCountByParam(@RequestBody User user) {
        return iUserService.count(new QueryWrapper<User>(user));
    }

    /**
     * 根据参数公司id以及用户id绑定对应的供应商
     *
     * @param user
     * @return
     */
    @PostMapping("/internal/user/binding")
    public void binding(@RequestBody User user) {
        if (user.getCompanyId() == null
                || user.getUserId() == null) {
            throw new BaseException("当前公司ID与人员ID不允许空");
        }
        iUserService.binding(user);
    }

    /**
     * 注册供应商主账号，从门户订单同步账号信息
     *
     * @param user
     * @param urlAddress
     */
    @PostMapping("/internal/user/registerBuyerMain")
    public void registerBuyerMain(@RequestBody User user, String urlAddress) {
        Long userId = IdGenrator.generate();
        user.setUserId(userId); // 设置用户ID
        user.setCompanyId(null); // 公司ID置空，采购商没有公司信息
        user.setUserType(UserType.BUYER.name()); // 设置用户类型： 采购商
        user.setMainType(MainType.Y.name()); // 设置主账号
        String password = StringUtil.genPwdChar(8); // 生成8位随机密码
        user.setPassword(password); // 设置随机密码
        iUserService.validUser(user, false); // 校验用户信息
        iUserService.addBuyerMain(user, urlAddress);
    }

    /**
     * 根据公司Id删除供应商
     *
     * @param companyId
     * @return
     */
    @GetMapping("/internal/user/deleteByCompanyId")
    public void deleteByCompanyId(Long companyId) {
        iUserService.deleteByCompanyId(companyId);
    }

    /**
     * Description 查询所有类型用户
     *
     * @return
     * @throws
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.29
     **/
    @PostMapping("/internal/user/listByBuyer")
    public List<User> listByUser(@RequestBody User user) {
        PageUtil.startPage(user.getPageNum(), user.getPageSize());
        return iUserService.listByParam(user);
    }

    @GetMapping("/internal/user/getAccountNum")
    public Integer getAccountNum() {
        return iUserService.count();
    }


    /**
     * 查询所有类型用户
     *
     * @param user
     * @return
     */
    @PostMapping("/internal/user/listAll")
    public PageInfo<User> listAll(@RequestBody User user) {
        return new PageInfo<User>(iUserService.listByParam(user));
    }

    /**
     * 根据公司ID和账号类型获取用户
     *
     * @param user
     * @return
     */
    @PostMapping("/internal/user/getUserByParm")
    public User getUserByParm(@RequestBody User user) {
        return iUserService.getUserByParm(user);
    }

    /**
     * 根据条件获取角色
     *
     * @param role
     * @return
     */
    @PostMapping("/internal/role/getRoleByParm")
    public Role getRoleByParm(@RequestBody Role role) {
        return iRoleService.getRoleByParm(role);
    }

    /**
     * 根据userId修改角色
     *
     * @param userId
     */
    @PostMapping("/internal/roleUser/modifyRoleByUserId")
    public void modifyRoleByUserId(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        iRoleUserService.modifyRoleByUserId(userId, roleId);
    }

    /**
     * 通过邮箱重置密码
     * 邮件模板：
     * 重置密码
     * 发件人：srm@meicloud.com
     * 收件人：账号邮箱
     * 主题：【美云智数-SRM云】密码重置成功！
     * 正文：
     * 您的SRM云账号密码已初始重置，您可使用重置密码登录系统修改。
     * <p>
     * SRM云网址：单实例网址
     * 您的账号：用户账号
     * 重置密码：随机生成（【个人中心-账号安全】可修改密码.）
     *
     * @param userDTO
     */
    @PostMapping("/user/resetUserPwByEmail")
    public void resetUserPwByEmail(@RequestBody UserDTO userDTO) {
        User user = iUserService.checkBeforeResetUserPwByEmail(userDTO);
        iUserService.resetPasswordByEmail(user.getUserId(), user.getEmail());
    }

    /**
     * 通过手机短信重置密码
     *
     * @param userDTO
     */
    @PostMapping("/user/resetUserPwByPhone")
    public void resetUserPwByPhone(@RequestBody UserDTO userDTO) {
        User user = iUserService.checkBeforeResetUserPwByPhone(userDTO);
        iUserService.resetPasswordByPhone(user.getUserId(), user.getPhone());
    }

    /**
     * 根据QueryWarpper查询用户列表
     *
     * @param queryUser
     * @return
     * @update xiexh12@meicloud.com
     */
    @PostMapping("/internal/user/listUsers")
    public List<User> listUsers(@RequestBody User queryUser) {
        return iUserService.list(new QueryWrapper<>(queryUser));
    }

    /**
     * 根据QueryWarpper查询用户
     *
     * @param queryUser
     * @return
     * @update xiexh12@meicloud.com
     */
    @PostMapping("/internal/user/getUser")
    public User getUser(@RequestBody User queryUser) {
        return iUserService.getUserByParm(queryUser);
    }

    /**
     * 查询所有用户的CeeaEmpNo
     *
     * @update xiexh12@meicloud.com
     */
    @PostMapping("/internal/user/getgetUserIdAndCeeaEmpNos")
    public List<UserDTO1> getgetUserIdAndCeeaEmpNos() {
        return userMapper.getgetUserIdAndCeeaEmpNos();
    }

    /**
     * save users batch
     *
     * @param usersList
     * @return
     * @update xiexh12@meicloud.com
     */
    @PostMapping("/internal/user/saveBatchUsers")
    public boolean saveBatchUsers(@RequestBody List<User> usersList) {
        return iUserService.saveBatch(usersList);
    }

    /**
     * udpate users batch
     *
     * @param usersList
     * @return
     * @update xiexh12@meicloud.com
     */
    @PostMapping("/internal/user/updateBatchUsers")
    public boolean updateBatchUsers(@RequestBody List<User> usersList) {
        return iUserService.updateBatchById(usersList);
    }

    /**
     * Description 批量新增或修改用户信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.10.30
     * @throws
     **/
    @PostMapping("/internal/user/saveOrUpdateBatchUsers")
    public boolean saveOrUpdateBatchUsers(@RequestBody List<User> usersList) {
        return iUserService.saveOrUpdateBatch(usersList);
    }

    /**
     * save scc_base_employees' data to rbac_user
     *
     * @param usersList
     * @update xiexh12@meicloud.com
     * @date 2020-08-30
     */
    @PostMapping("/internal/user/saveUsersFromEmployee")
    public boolean saveUsersFromEmployee(@RequestBody List<User> usersList) {
        return iUserService.saveBatch(usersList);
    }

    /**
     * update scc_base_employees' data to rbac_user
     *
     * @param usersList
     * @update xiexh12@meicloud.com
     * @date 2020-08-30
     */
    @PostMapping("/internal/user/updateUsersFromEmployee")
    public boolean updateUsersFromEmployee(@RequestBody List<User> usersList) {
        return iUserService.updateBatchById(usersList);
    }

    @PostMapping("/internal/user/getByUserIds")
    public List<User> getByUserIds(@RequestBody Collection<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return iUserService.listByIds(userIds);
    }

    @PostMapping("/internal/user/getUserByNos")
    public Map<String, User> getUserByNames(@RequestBody Collection<String> nos) {
        return iUserService.list(Wrappers.lambdaQuery(User.class)
                .select(User::getUsername, User::getNickname, User::getUserId,User::getCeeaEmpNo)
                .in(User::getCeeaEmpNo, nos)
        ).stream().collect(Collectors.toMap(User::getCeeaEmpNo, Function.identity()));
    }

    /**
     * 根据条件获取角色功能设置集合
     *
     * @return
     */
    @PostMapping("/internal/role/findRoleFuncSetByParam")
    public List<RoleFuncSet> findRoleFuncSetByParam(@RequestBody RoleFuncSet roleFuncSet) {
        PageUtil.startPage(roleFuncSet.getPageNum(), roleFuncSet.getPageSize());
        return iRoleFuncSetService.list(new QueryWrapper<>(roleFuncSet));
    }

    /**
     * 根据条件获取角色权限信息
     * @param functionCode  菜单编码
     * @param roleCodes     角色编码集合
     * @return
     */
    @PostMapping("/internal/role/findRoleFuncSetMoreRole")
    List<RoleFuncSet> findRoleFuncSetMoreRole(@RequestParam("functionCode")String functionCode,
                                              @RequestBody List<String> roleCodes){
        return iRoleFuncSetService.list(Wrappers.lambdaQuery(RoleFuncSet.class)
                .eq(RoleFuncSet::getFunctionCode , functionCode)
                .eq(RoleFuncSet::getEnableFlag , YesOrNo.YES.getValue())
                .in(RoleFuncSet::getRoleCode , roleCodes));
    }


    /**
     * 保存角色权限
     *
     * @param roleFuncSet
     * @return
     */
    @PostMapping("/internal/role/saveRoleFuncSet")
    public boolean saveRoleFuncSet(@RequestBody RoleFuncSet roleFuncSet) {
        if (Objects.isNull(roleFuncSet.getRoleFuncSetId())) {
            roleFuncSet.setRoleFuncSetId(IdGenrator.generate());
        }
        return iRoleFuncSetService.saveOrUpdate(roleFuncSet);
    }

    /**
     * 删除角色菜单权限
     *
     * @param id
     */
    @PostMapping("/internal/role/deleteRoleFuncSet")
    public void deleteRoleFunSet(Long id) {
        Assert.notNull(id, "角色菜单权限id不能为空");
        iRoleFuncSetService.removeById(id);
    }

    /**
     * 根据用户列表参数查询用户列表（员工工号）
     */
    @PostMapping("/internal/user/listUsersByUsersParamCode")
    public List<User> listUsersByUsersParamCode(@RequestBody List<String> usersParamCodeList) {
        List<User> returnUserList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(usersParamCodeList)) {
            returnUserList = iUserService.list(Wrappers.lambdaQuery(User.class)
                    .in(User::getCeeaEmpNo, usersParamCodeList.stream().distinct().collect(Collectors.toList())));
        }
        return returnUserList;
    }

    /**
     * 根据用户列表参数查询用户列表（昵称）
     */
    @PostMapping("/internal/user/listUsersByUsersParamNickName")
    public List<User> listUsersByUsersParamNickName(@RequestBody List<String> usersParamNickNameList) {
        List<User> returnUserList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(usersParamNickNameList)) {
            returnUserList = iUserService.list(Wrappers.lambdaQuery(User.class)
                    .in(User::getNickname, usersParamNickNameList.stream().distinct().collect(Collectors.toList())));
        }
        return returnUserList;
    }
    @GetMapping("/getUserInfoByAccount")
    public Map<String, Object> getUserInfoByUserName(@RequestParam("account") String account) {
        List<User> list = iUserService.list(Wrappers.lambdaQuery(User.class)
                .select(User::getNickname, User::getUsername, User::getDepartment)
                .eq(User::getUsername, account)
        );
        if (list.size() > 1) {
            throw new BaseException(String.format("存在重复的账号%s", account));
        }
        Map<String, Object> map = new HashMap<>();
        User result = list.get(0);
        map.put("account", result.getUsername());
        map.put("nickName", result.getNickname());
        map.put("department", result.getDepartment());
        return map;
    }


    /**
     * 根据条件查询采购员
     * @param poAgent
     * @return
     */
    @PostMapping("/listByParams")
    public List<PoAgent> listByParams(@RequestBody PoAgent poAgent){
        return poAgentService.listByParams(poAgent);
    }

    /**
     * 通过用户id获取采购用户相关信息
     * @param id
     * @return
     */
    @GetMapping(value = "/getByBuyer")
    public UserPermissionDTO getByBuyer(Long id) {
        Assert.notNull(id, "id不能为空");
        UserPermissionDTO userPermissionDTO = iUserService.getUserByParam(new User().setUserId(id));
        return userPermissionDTO;
    }

    /**
     * 通过id获取用户
     * @param id
     * @return
     */
    @GetMapping("/user/getUserById")
    public User getUserById(@RequestParam("id") Long id){
        return iUserService.getById(id);
    }

    /**
     * 根据条件查询采购员
     * @param poAgent
     * @return
     */
    @PostMapping("/poAgent/listPoAgentByParam")
    public List<PoAgent> listPoAgentByParam(@RequestBody PoAgent poAgent){
        return poAgentService.list(new QueryWrapper<PoAgent>(poAgent));
    }
    
    @PostMapping("/external/user/addBuyer")
    public void addBuyer(@RequestBody UserPermissionDTO userPermissionDTO) {
    	if (null != userPermissionDTO && null != userPermissionDTO.getUser()) {
    		userPermissionDTO.getUser().setUserType("BUYER");//采购类型
    	}
    	//设置权限
    	if (null != userPermissionDTO && null != userPermissionDTO.getUser() && null != userPermissionDTO.getOrganizationUsers()) {
    		List<OrganizationRelation> trees = baseClient.allTree();
    		for (OrganizationUser ou : userPermissionDTO.getOrganizationUsers()) {
    			ou.setStartDate(userPermissionDTO.getUser().getStartDate());
    			String fullPathId = getFullPathId(ou, trees);
    			if (StringUtils.isBlank(fullPathId)) {
		            throw new BaseException("组织关系不存在系统中");
		        }
    			ou.setFullPathId(fullPathId);
    		}
    	}
    	//设置角色
    	if (null != userPermissionDTO && null != userPermissionDTO.getUser() && null != userPermissionDTO.getRoleUsers()) {
    		for (RoleUser ru : userPermissionDTO.getRoleUsers()) {
    			if (null == ru.getRoleId()) {
		            throw new BaseException("角色不存在系统中");
		        }
    			ru.setStartDate(userPermissionDTO.getUser().getStartDate());
    		}
    	}
    	userController.addBuyer(userPermissionDTO);
    }
    
    @PostMapping("/external/user/modifyBuyer")
    public void modifyBuyer(@RequestBody UserPermissionDTO userPermissionDTO) {
    	//设置权限
    	if (null != userPermissionDTO && null != userPermissionDTO.getUser() && null != userPermissionDTO.getOrganizationUsers()) {
    		List<OrganizationRelation> trees = baseClient.allTree();
    		for (OrganizationUser ou : userPermissionDTO.getOrganizationUsers()) {
    			ou.setStartDate(userPermissionDTO.getUser().getStartDate());
    			String fullPathId = getFullPathId(ou, trees);
    			if (StringUtils.isBlank(fullPathId)) {
		            throw new BaseException("组织关系不存在系统中");
		        }
    			ou.setFullPathId(fullPathId);
    		}
    	}
    	iUserService.updateUser(userPermissionDTO);
    }

    private String getFullPathId(OrganizationUser ou , List<OrganizationRelation> trees) {
    	String fullPathId = null;
    	if (null != trees && trees.size() > 0) {
    		for (OrganizationRelation relation : trees) {
    			if (ou.getOrganizationId().equals(relation.getOrganizationId()) && ou.getParentOrganizationId().equals(relation.getParentOrganizationId())) {
    				fullPathId = relation.getFullPathId();
    				break;
    			}
    			if (null != relation.getChildOrganRelation() && relation.getChildOrganRelation().size() > 0) {
    				fullPathId = this.getFullPathId(ou, relation.getChildOrganRelation());
    			}
    			if (!StringUtil.isEmpty(fullPathId)) {
					break;
				}
    		}
    	}
    	return fullPathId;
    }
    
    /**
     * 根据QueryWarpper查询用户列表，支持分页,pageNum从1开始
     *
     * @param queryUser
     * @return
     * @update lizl7@meicloud.com
     */
    @PostMapping("/internal/user/listUsersPage")
    public List<User> listUsersPage(@RequestBody User queryUser, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
    	PageUtil.startPage(pageNum, pageSize);
        return iUserService.list(new QueryWrapper<>(queryUser));
    }
    
    /**
    *校验人脸识别
    * @return
    */
    @PostMapping("/verifyFace")
    public Boolean verifyFace(@RequestBody UserSecurityDto userSecurityDto) { 
        return iUserSecurityService.verifyFace(userSecurityDto);
    }
    /**
     * 通过dto查询用户信息
     */
    @PostMapping("/listUsersByUsreDto")
    public List<User> listUsersByUsreDto(@RequestBody UserDto userDto){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.in(CollectionUtils.isNotEmpty(userDto.getUserIdList()),"USER_ID",userDto.getUserIdList());
        wrapper.eq(userDto.getUserType()!=null,"USER_TYPE",userDto.getUserType());
        wrapper.isNotNull("CEEA_JOBCODE_DESCR");
        return iUserService.list(wrapper);
    }

    /**
     * 根据角色编码获取角色id
     */
    @PostMapping("/internal/role/getRoleByRoleCode")
    public List<Role> getRoleByRoleCodeForAnon(@RequestBody List<String> roleCodes){
        QueryWrapper wrapper = new QueryWrapper<Role>();
        wrapper.in("ROLE_CODE",roleCodes);
        return iRoleService.list(wrapper);
    }

    /**
     * 根据用户角色id获取员工id
     */
    @PostMapping("/internal/role/getUserByRoleId")
    public List<RoleUser> getUserByRoleId(@RequestBody Map<String,List<Long>> map){
        QueryWrapper wrapper = new QueryWrapper<RoleUser>();
        wrapper.in("USER_ID",map.get("userList"));
        wrapper.in("ROLE_ID",map.get("roleIdList"));
        return iRoleUserService.list(wrapper);
    }

    /**
     * 根据员工ID获取角色ID
     */
    @PostMapping("/internal/role/getRoleByUserId")
    List<RoleUser> getRoleByUserId(@RequestBody List<Long> userIds){
        QueryWrapper<RoleUser> wrapper = new QueryWrapper();
        wrapper.in("USER_ID",userIds);
        wrapper.select("ROLE_ID");
        return iRoleUserService.list(wrapper);
    }

    /**
     * 根据角色ID获取角色编码
     */
    @PostMapping("/internal/role/getRoleCodeByUserIdForAnon")
    List<Role> getRoleCodeByUserIdForAnon(@RequestBody List<Long> roleIdlist){
        QueryWrapper<Role> wrapper = new QueryWrapper();
        wrapper.in("ROLE_ID",roleIdlist);
        return iRoleService.list(wrapper);
    }
}

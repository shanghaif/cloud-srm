package com.midea.cloud.srm.rbac.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.DESUtil;
import com.midea.cloud.common.constants.RbacConst;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.enums.MainType;
import com.midea.cloud.common.enums.RoleType;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.external.entity.ExternalOrder;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.idm.esb.mainusers.schemas.mainusers.v1.IdmUserResponse;
import com.midea.cloud.srm.model.base.soap.idm.esb.mainusers.schemas.mainusers.v1.IdmUserSoapRequest;
import com.midea.cloud.srm.model.base.soap.idm.updatepassword.schemas.updatepassword.v1.IdmUpdUserRequest;
import com.midea.cloud.srm.model.base.soap.idm.updatepassword.schemas.updatepassword.v1.IdmUpdUserResponse;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.model.rbac.role.entity.RoleUser;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO;
import com.midea.cloud.srm.model.rbac.user.dto.UserPermissionDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.rbac.LongiIdmSaopUrl;
import com.midea.cloud.srm.rbac.idm.service.IIdmUserWsService;
import com.midea.cloud.srm.rbac.role.service.IRoleService;
import com.midea.cloud.srm.rbac.role.service.IRoleUserService;
import com.midea.cloud.srm.rbac.soap.idm.esb.mainusers.wsdls.bpmsendmainuserssoapproxy.v1.ExecutePtt;
import com.midea.cloud.srm.rbac.soap.idm.updatepassword.v1.IdmAcceptUpdatePassWordSoapBizPtt;
import com.midea.cloud.srm.rbac.user.mapper.UserMapper;
import com.midea.cloud.srm.rbac.user.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  用户信息 服务实现类
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
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private IRoleService iRoleService;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    /**Idm用户相关(包括创建主账号和修改密码接口)*/
    @Resource
    private IIdmUserWsService iIdmUserService;

    /**IMD接口访问用户名(创建主账号和同步密码到IDM)*/
    @Value("${IDM_USER.idmUserName}")
    private String idmUserName;
    /**IMD接口访问密码(创建主账号和同步密码到IDM)*/
    @Value("${IDM_USER.idmPassword}")
    private String idmPassword;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        ServletOutputStream os =  response.getOutputStream();
        int width=120;
        int height=30;
        BufferedImage verifyImg=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        //生成对应宽高的初始图片
        String randomText = VerifyCode.drawRandomText(width,height,verifyImg);
        request.getSession().setAttribute("verifyCode",randomText);
        redisTemplate.opsForValue().set(RedisKey.FORGET_PASSWORD_VERIFY_CODE, randomText,60*5, TimeUnit.SECONDS);
        // 将图形写给浏览器
        response.setContentType("image/jpeg");
        // 发头控制浏览器不要缓存
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        // 将图片写给浏览器
        ImageIO.write(verifyImg, "jpg", os);
        os.flush();
        os.close();
    }

    @Override
    public void checkVerifyCode(String verifyCode, HttpServletRequest request) {
        Object sessionVerifyCode = redisTemplate.opsForValue().get(RedisKey.FORGET_PASSWORD_VERIFY_CODE);
        Assert.isTrue(!ObjectUtils.isEmpty(sessionVerifyCode),"验证码报错请重新获取");
        if(StringUtils.isBlank(verifyCode) ||
                !sessionVerifyCode.toString().toLowerCase().equals(verifyCode.toLowerCase())){
            throw new BaseException("验证码不正确");
        }
    }

    @Override
    public UserPermissionDTO getUserByParam(User user) {
        Long userId = user.getUserId();
        user = this.getOne(new QueryWrapper<User>(user));
        if (user == null) {
            throw new BaseException("用户不存在");
        }
        QueryWrapper<RoleUser> roleUserQueryWrapper = new QueryWrapper<RoleUser>(new RoleUser().setUserId(userId));
        List<RoleUser> roleUserList = iRoleUserService.list(roleUserQueryWrapper);
        List<OrganizationUser> organizationUsers = baseClient.listOrganUserByParam(new OrganizationUser().setUserId(userId));
        UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
        user.setPassword(null); // 密码置空 TODO
        userPermissionDTO.setUser(user);
        userPermissionDTO.setRoleUsers(roleUserList);
        userPermissionDTO.setOrganizationUsers(organizationUsers);
        return userPermissionDTO;
    }

    @Transactional
    @Override
    public void save(UserPermissionDTO userPermissionDTO) {
        User user = userPermissionDTO.getUser();
        if (this.getOne(new QueryWrapper<User>(new User().setUsername(user.getUsername()))) != null) {
            throw new BaseException("该用户名已存在");
        }
        Long userId = user.getUserId();
        String password = user.getPassword();
        validRoleUser(userPermissionDTO.getRoleUsers(), userId);
        validOrganizationUser(userPermissionDTO.getOrganizationUsers(), userId);
        try{
            user.setPassword(DESUtil.encrypt(DigestUtils.sha1Hex(password)));
        }catch (Exception e){
            log.error("保存用户信息获取密码时报错",e);
        }
        this.save(user); // 保存用户信息
        List<OrganizationUser> organizationUsers = userPermissionDTO.getOrganizationUsers();
        if (CollectionUtils.isNotEmpty(organizationUsers)) {
            baseClient.addOrganUserBatch(organizationUsers); // 批量保存组织与用户关系信息
        }
        List<RoleUser> roleUsers = userPermissionDTO.getRoleUsers();
        if (CollectionUtils.isNotEmpty(roleUsers)) {
            iRoleUserService.saveBatch(roleUsers); // 批量保存角色用户关系信息
        }
        try {
            baseClient.sendEmail(user.getEmail(), "【美云智数-SRM云】账号创建成功！", String.format("<p>您的SRM云账号已创建成功，请尽快登录系统修改初始密码。</p>" +
                    "<p>SRM云网址：%s</p>" +
                    "<p>您的账号：%s</p>" +
                    "<p>初始密码：%s</p><p>（【个人中心-账号安全】可修改密码.）</p>", getOrderUrl(), user.getUsername(), password)); // TODO SRM云网址从订单配置获取
        } catch (Exception ex) {
            log.error("邮件发送密码失败，请检查邮箱配置", ex);
            throw new BaseException("邮件发送密码失败，请检查邮箱配置");
        }
    }

    @Transactional
    @Override
    public void update(UserPermissionDTO userPermissionDTO) {
        User user = userPermissionDTO.getUser();
        Long userId = user.getUserId();
        validRoleUser(userPermissionDTO.getRoleUsers(), userId);
        validOrganizationUser(userPermissionDTO.getOrganizationUsers(), userId);
        this.updateById(user); // 修改用户信息
        List<OrganizationUser> organizationUsers = userPermissionDTO.getOrganizationUsers();
        // 先删除所有的用户与组织关系记录，再添加所有记录
        baseClient.deleteOrganUserByUserId(user.getUserId());  // 通过用户ID删除组织与用户关系记录
        if (CollectionUtils.isNotEmpty(organizationUsers)) {
            baseClient.addOrganUserBatch(organizationUsers); // 批量保存组织与用户关系信息
        }
        List<RoleUser> roleUsers = userPermissionDTO.getRoleUsers();
        // 先删除所有的角色用户权限关系记录，再添加所有记录
        QueryWrapper<RoleUser> queryRoleUserWrapper
                = new QueryWrapper<RoleUser>(new RoleUser().setUserId(user.getUserId()));
        iRoleUserService.remove(queryRoleUserWrapper); // 通过用户ID删除角色与用户关系记录
        if (CollectionUtils.isNotEmpty(roleUsers)) {
            iRoleUserService.saveBatch(roleUsers); // 批量保存角色用户关系信息
        }
    }

    public void validUser(User user, boolean validPassword) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new BaseException("账号不能为空");
        }
        if (user.getUsername().contains("|")) {
            throw new BaseException("账号不能包含|字符");
        }
        if (StringUtils.isBlank(user.getPassword()) && validPassword) {
            throw new BaseException("密码不能为空");
        }
        if (StringUtils.isBlank(user.getNickname())) {
            throw new BaseException("用户名不能为空");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            throw new BaseException("邮箱不能为空");
        }
        if (user.getStartDate() == null) {
            throw new BaseException("生效时间不能为空");
        }
    }

    public void validRoleUser(List<RoleUser> roleUsers, Long userId) {
        if (CollectionUtils.isNotEmpty(roleUsers)) {
            roleUsers.forEach(roleUser -> {
                Long roleUserId = IdGenrator.generate();
                roleUser.setRoleUserId(roleUserId);
                roleUser.setUserId(userId);
            });
        }
    }

    public void validOrganizationUser(List<OrganizationUser> organizationUsers, Long userId) {
        if (CollectionUtils.isNotEmpty(organizationUsers)) {
            organizationUsers.forEach(organizationUser -> {
                Long organizationUserId = IdGenrator.generate();
                organizationUser.setOrganizationUserRelId(organizationUserId);
                organizationUser.setUserId(userId);
            });
        }
    }

    @Override
    public List<User> listByParam(User user) {
        // 默认不分页，分页信息在控制层定义
        String username = user.getUsername();
        String nickname = user.getNickname();
        LocalDate startDate = user.getStartDate();
        String queryName = user.getQueryName();
        user.setUsername(null).setNickname(null).setStartDate(null);
        QueryWrapper<User> wrapper = new QueryWrapper<User>(user);
        if (username != null) {
            wrapper.like("USERNAME", username);
        }
        if (nickname != null) {
            wrapper.like("NICKNAME", nickname);
        }
        if (startDate != null) {
            wrapper.le("START_DATE", startDate);
        }

        /*查询username或nickname*/
        if (StringUtils.isNotBlank(queryName)) {
            wrapper.like("USERNAME", queryName).or().like("NICKNAME", queryName);
        }
        // 获取结果集
        List<User> users = this.list(wrapper);
        // 关联供应商名称
        List<Long> queryCompanyIds = new ArrayList<Long>();
        if (CollectionUtils.isNotEmpty(users)) {
            users.forEach(queryUser -> {
                if (queryUser.getCompanyId() != null) {
                    queryCompanyIds.add(queryUser.getCompanyId());
                }
            });
        }
        if (CollectionUtils.isNotEmpty(queryCompanyIds)) {
            List<CompanyInfo> companyInfos = supplierClient.getComponyByIds(queryCompanyIds);
            if (CollectionUtils.isNotEmpty(companyInfos)) {
                Map<Long, CompanyInfo> companyInfoMap = companyInfos.stream().collect(Collectors.toMap(CompanyInfo::getCompanyId, Function.identity()));
                users.forEach(updateUser -> {
                    Long companyId = updateUser.getCompanyId();
                    if (companyId != null) {
                        CompanyInfo companyInfo = companyInfoMap.get(companyId);
                        if (companyInfo != null) {
                            updateUser.setCompanyName(companyInfo.getCompanyName()); // 关联供应商公司名称
                        }
                    }
                });
            }
        }
        return users;
    }

    @Override
    public User queryByCompanyId(Long companyId) {
        User user = new User();
        User result = new User();
        user.setUserType(UserType.VENDOR.name());
        user.setCompanyId(companyId);
        QueryWrapper<User> wrapper = new QueryWrapper<User>(user);
        List<User> results = this.list(wrapper);
        if (CollectionUtils.isNotEmpty(results)) {
            results.get(0).setPassword("");
            result = results.get(0);
        }
        return result;
    }

    @Override
    public void deleteByCompanyId(Long companyId) {
        if (companyId != null) {
            Map<String, Object> deleteMap = new HashMap<>();
            deleteMap.put("COMPANY_ID", companyId);
            this.removeByMap(deleteMap);
        }
    }

    @Override
    public void binding(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>(user);
        if (this.getOne(wrapper) == null) {
            User existUser = this.getById(user.getUserId());
            Assert.notNull(existUser, "用户不存在");
            Assert.isNull(existUser.getCompanyId(), "用户已绑定对应供应商");
            existUser.setCompanyId(user.getCompanyId());
            existUser.setMainType(YesOrNo.YES.getValue());
            this.updateById(existUser);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetPasswordByEmail(Long userId, String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>(new User().setUserId(userId));
        User user = this.getOne(wrapper);
        if (user == null) {
            throw new BaseException("用户不存在");
        } else {
            String password = StringUtil.genPwdChar(8); // 新密码
//            String password = RbacConst.LONGI_PASSWORD; //新密码默认

            // 重置数据库密码
            try{
                user.setPassword(DESUtil.encrypt(DigestUtils.sha1Hex(password)));
            }catch (Exception e){
                log.error("重置用户信息获取密码时报错",e);
                throw new BaseException("重置用户信息获取密码时报错");
            }
            boolean isUpdate = this.updateById(user);
            if(isUpdate){
                try {
                    baseClient.sendEmail(email, "您的SRM云账号密码已初始重置，您可使用重置密码登录系统修改。", String.format("<p>SRM云网址：%s</p>" +
                            "<p>您的账号：%s</p>" +
                            "<p>重置密码：%s</p><p>（【个人中心-账号安全】可修改密码.）</p>", getOrderUrl(), user.getUsername(), password)); // TODO SRM云网址从订单配置获取
                } catch (Exception ex) {
                    log.error("邮件发送密码失败，请检查邮箱配置", ex);
                    throw new BaseException("邮件发送密码失败，请检查邮箱配置");
                }
//                IdmUpdUserResponse response = changePasswordForIdmUser(user);  //修改密码同步到IDM
//                if(null == response || null == response.getEsbInfo() || "E".equals(response.getEsbInfo().getReturnStatus())){
//                    throw new BaseException("重置用户信息获取密码,修改IDM密码时报错");
//                }
            } else {

            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetPasswordByNoEmail(Long userId, String password,String email, boolean isSendEmail) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>(new User().setUserId(userId));
        User user = this.getOne(wrapper);
        if (user == null) {
            throw new BaseException("用户不存在");
        } else {
//            String password = RbacConst.LONGI_PASSWORD; //新密码默认
            // 重置数据库密码
            try{
                user.setPassword(DESUtil.encrypt(DigestUtils.sha1Hex(password)));
            }catch (Exception e){
                log.error("重置用户信息获取密码时报错",e);
                throw new BaseException("重置用户信息获取密码时报错");
            }
            boolean isUpdate = this.updateById(user);
            if(isUpdate){
//                IdmUpdUserResponse response = changePasswordForIdmUser(user);  //修改密码同步到IDM
//                if(null == response || null == response.getEsbInfo() || "E".equals(response.getEsbInfo().getReturnStatus())){
//                    throw new BaseException("重置用户信息获取密码,修改IDM密码时报错");
//                }

                if(isSendEmail) {
                    try {
                        baseClient.sendEmail(email, "您的SRM云账号密码已初始重置，您可使用重置密码登录系统修改。", String.format("<p>SRM云网址：%s</p>" +
                                "<p>您的账号：%s</p>" +
                                "<p>重置密码：%s</p><p>（【个人中心-账号安全】可修改密码.）</p>", getOrderUrl(), user.getUsername(), password)); // TODO SRM云网址从订单配置获取
                    } catch (Exception ex) {
                        log.error("邮件发送密码失败，请检查邮箱配置", ex);
                        throw new BaseException("邮件发送密码失败，请检查邮箱配置");
                    }
                }
            } else {
                throw new BaseException("重置用户密码失败");
            }
        }
    }

    @Transactional
    @Override
    public void addBuyerMain(User user, String urlAddress) {
        if (this.getOne(new QueryWrapper<User>(new User().setUsername(user.getUsername()))) != null) {
            throw new BaseException("该用户名已存在");
        }
        Long userId = user.getUserId();
        String password = user.getPassword();
        // 采购商角色初始化在门户同步订单是进行创建用户及授权 BUYER_INIT
        QueryWrapper<Role> queryRoleWrapper = new QueryWrapper<Role>();
        queryRoleWrapper.like("ROLE_TYPE", RoleType.BUYER_INIT.name());
        List<Role> initDefaultRoles = iRoleService.list(queryRoleWrapper);
        if (CollectionUtils.isNotEmpty(initDefaultRoles)) {
            // 授权采购商初始化角色
            List<RoleUser> roleUsers = new ArrayList<RoleUser>();
            initDefaultRoles.forEach(initDefaultRole -> {
                roleUsers.add(new RoleUser().setRoleUserId(IdGenrator.generate())
                        .setUserId(userId).setRoleId(initDefaultRole.getRoleId()).setStartDate(LocalDate.now()));
            });
            iRoleUserService.saveBatch(roleUsers);
        }
        try{
            user.setPassword(DESUtil.encrypt(DigestUtils.sha1Hex(password)));
        }catch (Exception e){
            log.error("新增用户信息获取密码时报错",e);
        }
        this.save(user); // 保存用户信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                int tryNoticeNum = 0;
                while (tryNoticeNum < 10) {
                    try {
                        baseClient.sendEmail(user.getEmail(), "欢迎使用【美云智数-SRM云】！", String.format(
                                "<p>您的SRM云已成功部署，请登录专有URL：%s 使用服务.</p>" +
                                        "<p>您的账号：%s</p>" +
                                        "<p>初始密码：%s</p><p>（【个人中心-账号安全】可修改密码.）</p>", urlAddress, user.getUsername(), password)); // SRM云网址从订单配置获取
                        tryNoticeNum = 10;
                    } catch (Exception ex) {
                        log.error("邮件发送密码失败，请检查邮箱配置", ex);
                        try {
                            Thread.sleep(3000); // 3秒重试
                            tryNoticeNum++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        throw new BaseException("邮件发送密码失败，请检查邮箱配置");
                    }
                }
            }
        }).start();
    }

    @Override
    public void checkOldPassword(User user) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String excistPassword = loginAppUser.getPassword();
        if (!bCryptPasswordEncoder.matches(user.getPassword(), excistPassword)) {
            throw new BaseException("旧密码不正确");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyPassword(User user) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (null == loginAppUser && loginAppUser.getUserId().compareTo(user.getUserId()) != 0) {
            throw new BaseException("修改的用户非当前登录用户");
        }
        LocalDate endDate = loginAppUser.getEndDate();
        String newPassword = user.getPassword(); // sha1密码
        try{
            user.setPassword(DESUtil.encrypt(newPassword));
        }catch (Exception e){
            log.error("检测新密码和旧密码是否一致时获取报错",e);
        }
        boolean isUpdate = this.updateById(user.setEndDate(endDate));
        // 20210226 注释同步erp idm系统的逻辑，基线版本不需要此操作
//        if(isUpdate){
//            IdmUpdUserResponse response = changePasswordForIdmUser(user);  //修改密码同步到IDM
//            if(null == response || null == response.getEsbInfo() || "E".equals(response.getEsbInfo().getReturnStatus())){
//                String errorMes = (StringUtils.isNotBlank(response.getEsbInfo().getReturnMsg()) ? response.getEsbInfo().getReturnMsg()
//                        : "重置用户信息获取密码,修改IDM密码时报错");
//                throw new BaseException(errorMes);
//            }
//        }
    }

    @Override
    public void modifyUser(User user) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (loginAppUser.getUserId().compareTo(user.getUserId()) != 0) {
            throw new BaseException("修改的用户非当前登录用户");
        }
        Assert.hasText(user.getUsername(), "姓名不能为空");
        Assert.hasText(user.getEmail(), "邮箱不能为空");
        LocalDate endDate = loginAppUser.getEndDate();
        user.setEndDate(endDate);
        this.updateById(user);
    }

    @Override
    public User getUserByParm(User user) {
        PageUtil.startPage(1, 1);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        List<User> list = this.list(queryWrapper);
        User userEntity = null;
        if (CollectionUtils.isNotEmpty(list)) {
            userEntity = list.get(0);
        }
        return userEntity;
    }

    @Override
    public User checkBeforeResetUserPwByEmail(UserDTO userDTO) {
        User existUser = this.getOne(new QueryWrapper<>(new User().setUsername(userDTO.getUsername())));
        if (existUser == null) {
            throw new BaseException("用户不存在");
        }
        String email = existUser.getEmail();
        if (StringUtils.isBlank(email)) {
            throw new BaseException("用户尚未绑定邮箱");
        }
        Long userId = existUser.getUserId();
        checkVerifyCode(userDTO.getVerifyCode());
        return new User().setUserId(userId).setEmail(email);
    }

    @Override
    public User checkBeforeResetUserPwByPhone(UserDTO userDTO) {
        User existUser = this.getOne(new QueryWrapper<>(new User().setUsername(userDTO.getUsername())));
        if (existUser == null) {
            throw new BaseException("用户不存在");
        }
        String phone = existUser.getPhone();
        if (StringUtils.isBlank(phone)) {
            throw new BaseException("用户尚未绑定手机");
        }
        Long userId = existUser.getUserId();
        checkVerifyCode(userDTO.getVerifyCode());
        return new User().setUserId(userId).setPhone(phone);
    }

    @Override
    public void resetPasswordByPhone(Long userId, String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>(new User().setUserId(userId));
        User user = this.getOne(wrapper);
        if (user == null) {
            throw new BaseException("用户不存在");
        } else {
            String password = StringUtil.genPwdChar(8); // 新密码
            try {
                //baseClient.sendPhoneMsg(phone, password);
            } catch (Exception ex) {
                log.error("手机短信发送密码失败，请检查手机短信配置", ex);
                throw new BaseException("手机短信发送密码失败，请检查手机短信配置");
            }
            // 重置数据库密码
            try{
                user.setPassword(DESUtil.encrypt(DigestUtils.sha1Hex(password)));
            }catch (Exception e){
                log.error("重置数据库密码时获取密码报错",e);
            }
            this.updateById(user);
        }
    }

    private void checkVerifyCode(String verifyCode) {
        Object verifyCodeInSession = HttpServletHolder.getRequest().getSession().getAttribute("verifyCode");
        if (verifyCodeInSession == null || !verifyCodeInSession.toString().toLowerCase().equals(verifyCode.toLowerCase())) {
            throw new BaseException("验证码不正确");
        }
//        if (stringRedisTemplate.hasKey(RedisKey.VERIFY_CODE) ||
//                StringUtils.isNotBlank(stringRedisTemplate.opsForValue().get(RedisKey.VERIFY_CODE))) {
//            String verifyCodeInRedis = stringRedisTemplate.opsForValue().get(RedisKey.VERIFY_CODE);
//            if (StringUtils.isBlank(verifyCode) ||
//                    !verifyCodeInRedis.toLowerCase().equals(verifyCode.toLowerCase())) {
//                throw new BaseException("验证码不正确");
//            }
//        } else {
//            throw new BaseException("验证码报错请重新获取");
//        }
    }

    private String getOrderUrl() {
        List<ExternalOrder> externalOrders = baseClient.listExternalOrder();
        if (CollectionUtils.isNotEmpty(externalOrders)) {
            ExternalOrder externalOrder = externalOrders.get(0);
            return externalOrder.getUrlAddress();
        }
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long saveVendor(User user) {
        if (user.getUsername() == null) {
            throw new BaseException("账号不能为空");
        }
        Long id = IdGenrator.generate();
        user.setUserId(id);
        user.setUserType(UserType.VENDOR.name()); // 供应商
        user.setMainType(MainType.Y.name()); // 主账号
        try {
            user.setPassword(DESUtil.encrypt(user.getPassword())); // 密码加密
//            user.setPassword(DESUtil.encrypt(RbacConst.LONGI_PASSWORD)); // 密码加密(使用固定密码)
        }catch (Exception e){
            log.error("供应商注册获取密码时报错",e);
            throw new BaseException("账号或密码不能为空");
        }
        user.setStartDate(LocalDate.now());
        // 供应商角色初始化在注册供应商用户时进行 REG_DEFAULT
        QueryWrapper<Role> queryRoleWrapper = new QueryWrapper<Role>();
        queryRoleWrapper.like("ROLE_TYPE", RoleType.REG_DEFAULT.name());
        List<Role> regDefaultRoles = iRoleService.list(queryRoleWrapper);
        if (CollectionUtils.isNotEmpty(regDefaultRoles)) {
            // 授权供应商初始化角色
            List<RoleUser> roleUsers = new ArrayList<RoleUser>();
            regDefaultRoles.forEach(regDefaultRole -> {
                roleUsers.add(new RoleUser().setRoleUserId(IdGenrator.generate())
                        .setUserId(id).setRoleId(regDefaultRole.getRoleId()).setStartDate(LocalDate.now()));
            });
            iRoleUserService.saveBatch(roleUsers);
        }
        boolean isSave = super.save(user);
        //kuangzm 屏蔽IDM用户创建接口
//        if(isSave){
//            createIdmUser(user); //供应商用户信息同步到IDM
//        }
        return id;
    }

    @Override
    public IdmUserResponse createIdmUser(User user){
        IdmUserResponse response = new IdmUserResponse();
        try {
//            ExecuteBindQSService locator = new ExecuteBindQSService();
//            ExecutePtt service = locator.getExecuteBindQSPort();

//            String wsdl = "http://soatest.longi.com:8011/IDMSB/ESB/MainUser/ProxyServices/BpmSendmainUsersSoapProxy?wsdl";
//            String namespaceURI = "http://www.longi.com/IDMSB/ESB/mainUsers/WSDLs/BpmSendmainUsersSoapProxy/v1.0";
//            String localPart = "ExecuteBindQSService";
//            GeneralSoapClient soapClient = new GeneralSoapClient(wsdl, namespaceURI, localPart);
//            soapClient.setRequireAuth(true);
//            soapClient.setUsername("longi_xasrm01");
//            soapClient.setPassword("da771ee2653e41f3b2c90d6dc9445c2e");
//            ExecuteBindQSService locator = soapClient.create(ExecuteBindQSService.class);
//            ExecutePtt service = locator.getExecuteBindQSPort();

            // 创建主账号接口地址
            String address = LongiIdmSaopUrl.createIdmUserUrl;
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
            jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            jaxWsProxyFactoryBean.setUsername(this.idmUserName);
            jaxWsProxyFactoryBean.setPassword(this.idmPassword);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(ExecutePtt.class);
            // 创建一个代理接口实现
            ExecutePtt service = (ExecutePtt) jaxWsProxyFactoryBean.create();
            IdmUserSoapRequest request = new IdmUserSoapRequest();

            /**设置EsbInfo*/
            IdmUserSoapRequest.EsbInfo esbInfo = new IdmUserSoapRequest.EsbInfo();
            esbInfo.setInstId(String.valueOf(user.getUserId()));
            esbInfo.setRequestTime(String.valueOf(System.currentTimeMillis()));
            esbInfo.setAttr1("");
            esbInfo.setAttr2("");
            esbInfo.setAttr3("");
            request.setEsbInfo(esbInfo);

            /**设置RequestInfo*/
            IdmUserSoapRequest.RequestInfo requestInfo = new IdmUserSoapRequest.RequestInfo();
            IdmUserSoapRequest.RequestInfo.MainUsers mainUsers = new IdmUserSoapRequest.RequestInfo.MainUsers();
//            mainUsers.setSysCode(new JAXBElement<String>(new QName("SysCode"), String.class,RbacConst.SYS_CODE));
            mainUsers.setSysCode(RbacConst.SYS_CODE);
            IdmUserSoapRequest.RequestInfo.MainUsers.MainUser mainUser = new IdmUserSoapRequest.RequestInfo.MainUsers.MainUser();

            List<IdmUserSoapRequest.RequestInfo.MainUsers.MainUser> mainUsersList = new ArrayList<>();
            String nowDate = DateUtil.format(new Date(), DateUtil.DATE_FORMAT_19);
//            mainUser.setUSERID(new JAXBElement<String>(new QName("USERID"), String.class, user.getUsername()));
//            mainUser.setCOMPANYNAME(new JAXBElement<String>(new QName("COMPANY_NAME"), String.class, ""));
//            mainUser.setEDIBPMFLAG(new JAXBElement<String>(new QName("EDI_BPM_FLAG"), String.class, RbacConst.EDI_BPM_FLAG_E));
//            mainUser.setEDIBPMTIME(new JAXBElement<String>(new QName("EDI_BPM_TIME"), String.class, nowDate));
//            mainUser.setPASSWORD(new JAXBElement<String>(new QName("PASSWORD"), String.class, ""));
//            mainUser.setUSEREMAIL(new JAXBElement<String>(new QName("USER_EMAIL"), String.class, user.getEmail()));
//            mainUser.setUSERNAMEC(new JAXBElement<String>(new QName("USER_NAME_C"), String.class, user.getNickname()));
//            mainUser.setUSERTEL(new JAXBElement<String>(new QName("USER_TEL"), String.class, user.getPhone()));
//            mainUser.setUSERTYPE(new JAXBElement<String>(new QName("USER_TYPE"), String.class, "6"));
//            mainUser.setSTATUS(new JAXBElement<String>(new QName("STATUS"), String.class, "0"));
//            mainUser.setAttr1(new JAXBElement<String>(new QName("Attr1"), String.class, RbacConst.LONGI_PASSWORD));
            mainUser.setUSERID(user.getUsername());
            mainUser.setCOMPANYNAME("");
            mainUser.setEDIBPMFLAG(RbacConst.EDI_BPM_FLAG_E);
            mainUser.setEDIBPMTIME(nowDate);
            mainUser.setPASSWORD("");
            mainUser.setUSEREMAIL(user.getEmail());
            mainUser.setUSERNAMEC(user.getNickname());
            mainUser.setUSERTEL(user.getPhone());
            mainUser.setUSERTYPE( "6");
            mainUser.setSTATUS( "0");
            mainUser.setAttr1( RbacConst.LONGI_PASSWORD);
            mainUsersList.add(mainUser);
            mainUsers.setMainUser(mainUsersList);
            requestInfo.setMainUsers(mainUsers);
            request.setRequestInfo(requestInfo);
            log.debug("request: " + JsonUtil.entityToJsonStr(request));


            response = service.execute(request);


            log.debug("response: " + JsonUtil.entityToJsonStr(response));
        } catch (Exception ex) {
            log.error("调用IDM总线创建主账号接口时报错: ",ex);
//            throw new BaseException(ResultCode.RPC_ERROR.getMessage());
            throw new BaseException(ex.getMessage());
        }
        return response;
    }

    @Override
    public IdmUpdUserResponse changePasswordForIdmUser(User user){
        IdmUpdUserResponse response = new IdmUpdUserResponse();
        try {
            // 修改密码接口地址
            String address = LongiIdmSaopUrl.changePasswordForIdmUser;
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();

            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            jaxWsProxyFactoryBean.setUsername(this.idmUserName);
            jaxWsProxyFactoryBean.setPassword(this.idmPassword);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(IdmAcceptUpdatePassWordSoapBizPtt.class);
            // 创建一个代理接口实现
            IdmAcceptUpdatePassWordSoapBizPtt service = (IdmAcceptUpdatePassWordSoapBizPtt) jaxWsProxyFactoryBean.create();
//            Client proxy = ClientProxy.getClient(service);
//            // 通过本地客户端设置 网络策略配置
//            HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
//            // 用于配置客户端HTTP端口的属性
//            HTTPClientPolicy policy = new HTTPClientPolicy();
//            // 超时控制 单位 : 毫秒
//            policy.setConnectionTimeout(3* 60 * 1000);
//            policy.setReceiveTimeout(3* 60 * 1000);
//            conduit.setClient(policy);

            IdmUpdUserRequest request = new IdmUpdUserRequest();

            /**根据用户ID获取用户信息，拿用户账号*/
            User queryUser = this.getById(user.getUserId());
            String ceeaEmpNo = "";
            if(null != queryUser){
                ceeaEmpNo = queryUser.getCeeaEmpNo();
            }

            /**设置EsbInfo*/
            IdmUpdUserRequest.EsbInfo esbInfo = new IdmUpdUserRequest.EsbInfo();
            esbInfo.setInstId(ceeaEmpNo);
            esbInfo.setRequestTime(String.valueOf(System.currentTimeMillis()));
            esbInfo.setAttr1("");
            esbInfo.setAttr2("");
            esbInfo.setAttr3("");
            request.setEsbInfo(esbInfo);


            /**设置RequestInfo*/
            IdmUpdUserRequest.RequestInfo requestInfo = new IdmUpdUserRequest.RequestInfo();
            IdmUpdUserRequest.RequestInfo.Employees employees = new IdmUpdUserRequest.RequestInfo.Employees();
            List<IdmUpdUserRequest.RequestInfo.Employees.Employee> employeesList = new ArrayList<>();
            IdmUpdUserRequest.RequestInfo.Employees.Employee employee = new IdmUpdUserRequest.RequestInfo.Employees.Employee();
            employee.setNewPassword(user.getPassword());
            employee.setOldPassword("");
            employee.setEmplid(ceeaEmpNo);
            employee.setAttr1("");
            employee.setAttr2("");
            employee.setAttr3("");
            employee.setAttr4("");
            employee.setAttr5("");

            employeesList.add(employee);
            employees.getEmployee(employeesList);
            requestInfo.setEmployees(employees);
            request.setRequestInfo(requestInfo);
            log.debug("request: " + JsonUtil.entityToJsonStr(request));
            response = service.operation1(request);
            log.debug("response: " + JsonUtil.entityToJsonStr(response));
        } catch (Exception ex) {
            log.error("调用IDM总线修改密码同步到IDM接口时报错: ",ex);
//            throw new BaseException(ResultCode.RPC_ERROR.getMessage());
            throw new BaseException(ex.getMessage());
        }
        return response;
    }

    @Override
    public SoapResponse changePasswordForSrm(String instId,String requestTime, String emplId, String newPassword, String oldPassword) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (StringUtils.isBlank(emplId) || StringUtils.isBlank(newPassword)) {
            returnStatus = "E";
            resultMsg = "接口用户编码或新密码为空.";
        } else {
            User queryUser = new User();
            queryUser.setCeeaEmpNo(emplId);
            List<User> queryUserList = getBaseMapper().selectList(new QueryWrapper<>(queryUser));
            if(CollectionUtils.isEmpty(queryUserList) || null == queryUserList.get(0)){
                throw new BaseException("修改的用户NSrm不存在");
            }
            try {
                User saveUser = queryUserList.get(0);
                saveUser.setPassword(newPassword);
                logger.info("IDM修改密码同步到Nsrm: " + JsonUtil.entityToJsonStr(saveUser));
                boolean isUpdate = this.updateById(saveUser.setEndDate(LocalDate.now()));
                if (isUpdate) {
                    returnStatus = "S";
                    resultMsg = "成功同步IDM密码到Nsrm.";
                } else {
                    returnStatus = "E";
                    resultMsg = "IDM修改密码同步到Nsrm时报错";
                    log.error("IDM修改密码同步到Nsrm时报错");
                }

            } catch (Exception e) {
                returnStatus = "E";
                resultMsg = "IDM修改密码同步到Nsrm时报错";
                log.error("IDM修改密码同步到Nsrm时报错：", e);
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }
    
    @Transactional
    @Override
    public void updateUser(UserPermissionDTO userPermissionDTO) {
    	
    	//1.修改用户信息，存在值得情况才修改，不存在则不修改
        User user = userPermissionDTO.getUser();
        Long userId = user.getUserId();
        if (null == userId) {
            throw new BaseException("用户不存在");
        }
        User userDb = this.getById(userId);
        if (null == userDb) {
        	throw new BaseException("用户不存在");
        }
        if (null != user && StringUtils.isBlank(user.getNickname())) {
        	userDb.setNickname(user.getNickname());
        }
        if (null != user && StringUtils.isBlank(user.getPhone())) {
        	userDb.setPhone(user.getPhone());
        }
        if (null != user && StringUtils.isBlank(user.getEmail())) {
        	userDb.setEmail(user.getEmail());
        }
        if (null != user && StringUtils.isBlank(user.getDepartment())) {
        	userDb.setDepartment(user.getDepartment());
        }
        if (null != user && null != user.getEndDate()) {
        	userDb.setEndDate(user.getEndDate());
        }
        this.updateById(userDb); 
        
        //2。组织处理 如果不存在 则不处理 ，如果存在 则先删除 后更新
        List<OrganizationUser> organizationUsers = userPermissionDTO.getOrganizationUsers();
        if (null != organizationUsers && organizationUsers.size() > 0) {
        	for (OrganizationUser ou : organizationUsers) {
        		ou.setStartDate(userDb.getStartDate());
        		ou.setUserId(userDb.getUserId());
        	}
        	// 先删除所有的用户与组织关系记录，再添加所有记录
            baseClient.deleteOrganUserByUserId(user.getUserId());  // 通过用户ID删除组织与用户关系记录
            if (CollectionUtils.isNotEmpty(organizationUsers)) {
                baseClient.addOrganUserBatch(organizationUsers); // 批量保存组织与用户关系信息
            }
        }
    }
}

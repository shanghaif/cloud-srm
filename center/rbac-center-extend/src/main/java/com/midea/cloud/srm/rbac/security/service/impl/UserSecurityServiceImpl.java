package com.midea.cloud.srm.rbac.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.CredentialType;
import com.midea.cloud.common.constants.UserSecurityConstant;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.oauth.Oauth2Client;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.security.dto.UserSecurityDto;
import com.midea.cloud.srm.model.rbac.security.entity.UserSecurity;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.rbac.security.common.facepass.FaceUtil;
import com.midea.cloud.srm.rbac.security.mapper.UserSecurityMapper;
import com.midea.cloud.srm.rbac.security.service.IUserSecurityService;
import com.midea.cloud.srm.rbac.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  用户安全 服务实现类
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
@Service
@Slf4j
public class UserSecurityServiceImpl extends ServiceImpl<UserSecurityMapper, UserSecurity> implements IUserSecurityService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private Oauth2Client oauth2Client;

    @Autowired
    private FileCenterClient fileCenterClient;
    
    /**
     * 累计用户密码错误信息
     *
     * @param user
     * @param userSecurity
     */
    @Override
    public void cumulatePasswordErrorTime(User user, UserSecurity userSecurity) {
        if (userSecurity == null) {
            // 新增
            userSecurity = new UserSecurity();
            userSecurity.setUserSecurityId(IdGenrator.generate()).setUserId(user.getUserId()).setPassword(user.getPassword()).setPswIncorrectTimes(1L)
                    .setLastIncorrectTime(new Date()).setCreationDate(new Date()).setUsername(user.getUsername());
            this.save(userSecurity);
        } else {
            userSecurity.setPassword(user.getPassword()).setPswIncorrectTimes(userSecurity.getPswIncorrectTimes() == null ? 1L : userSecurity.getPswIncorrectTimes() + 1L)
                    .setLastIncorrectTime(new Date());
            this.updateById(userSecurity);
        }
    }

    /**
     * 用户密码错误信息清零
     *
     * @param userSecurity
     */
    @Override
    public void removeUserLock(UserSecurity userSecurity) {
        if (userSecurity != null) {
            this.updateById(userSecurity.setPswIncorrectTimes(0L));
        }
    }

    /**
     * 验证用户密码是否被锁住，锁住返回true，否则返回false
     * @param username
     * @param logPassword
     * @return
     */
    @Override
    public Boolean verifyUserLock(String username, String logPassword) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(logPassword)) {
            throw new BaseException("username || logPassword can not be empty");
        }
        User user = iUserService.getOne(new QueryWrapper<>(new User().setUsername(username)));
        if (user == null || user.getUserId() == null) {
            throw new BaseException("用户不存在，username = " + username);
        }
        // 是否被锁住
        boolean isLock = false;
        UserSecurity userSecurity = this.getOne(new QueryWrapper<>(new UserSecurity().setUserId(user.getUserId())).last("LIMIT 1"));
        if (userSecurity != null) {
            Long pswIncorrectTimes = userSecurity.getPswIncorrectTimes();
            if(userSecurity.getLastIncorrectTime() == null ){
                //没有最后错误时间，直接返回成功
                return false;
            }
            long incorrectTime = System.currentTimeMillis() - userSecurity.getLastIncorrectTime().getTime();
            if (UserSecurityConstant.PASSWORD_INCORRECT_MAX_NUMBER <= pswIncorrectTimes.intValue()
                    && incorrectTime < UserSecurityConstant.PASSWORD_INCORRECT_LOCK_TIME * 60 * 1000) {
                isLock = true;
            }
        }
        // 本次密码校验是否通过
        boolean passwordCorrectFlag = verifyPassword(username,logPassword);
        if (passwordCorrectFlag) {
            // 密码校验成功且用户未来被锁住，错误信息清零
            if (!isLock) {
                this.removeUserLock(userSecurity);
            }
        } else {
            // 密码校验失败，累计错误次数
            this.cumulatePasswordErrorTime(user, userSecurity);
        }
        return isLock;
    }

    @Override
    public Boolean verifyPassword(String username, String password) {
    	log.error("不应该在这里判断账号密码，统一返回false");
    	//不应该在这里判断账号密码---lizl7
//    	return false;
        boolean flag = true;

        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "cloud");
        parameters.put("client_secret", "cloud_20200213");
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", username + "|" + CredentialType.USERNAME.name());
        parameters.put("password", password);

        try {
            oauth2Client.postAccessToken(parameters);
        } catch (Exception e) {
            log.error("登入异常", e);
            flag = false;
        }
        return flag;
    }
    
    /**
     * 保存上传人脸识别，支持上传，在线
     * @param userSecurity
     * @return
     */
    @Override
    public Boolean modifyFace(UserSecurityDto userSecurityDto) {
    	
    	//如果是摄像头截取的，先上传
    	if( userSecurityDto.getFaceFileBase64()!=null && !"".equals(userSecurityDto.getFaceFileBase64()) ) {
    		//检测人脸
        	if( !FaceUtil.detectFace(userSecurityDto.getFaceFileBase64()) ) {
        		throw new BaseException("没有检测到人脸信息");
        	}
    		FaceUtil.detectFace(userSecurityDto.getFaceFileBase64());
    		
    		String name =AppUserUtil.getLoginAppUser().getUserId()+"_"+"face.img";
    		byte[] bytes = Base64.getDecoder().decode(userSecurityDto.getFaceFileBase64());
//    		MultipartFile multipartFile = new MockMultipartFile(name,name,ContentType.APPLICATION_OCTET_STREAM.toString(),bytes);
//    		Fileupload fileupload =new Fileupload();
//    		fileupload.setFileExtendType("img");
//    		fileupload =fileCenterClient.upload(multipartFile, fileupload);
    		
            MultipartFile multipartFile = new MockMultipartFile("file",name,"",bytes);
            String sourceType = "modifyFace";
            String uploadType = FileUploadType.FASTDFS.name();
            String fileModular = "userManage";
            String fileFunction = "profile";
            String fileType = "img";
            Fileupload fileupload = fileCenterClient.feignClientUpload(multipartFile,sourceType,uploadType,fileModular,fileFunction,fileType);
    		userSecurityDto.setFaceFileId(fileupload.getFileuploadId());
    	}

    	//获取当前用户
    	UserSecurity userSecurityDb = this.getOne(new QueryWrapper<>(new UserSecurity().setUserId(AppUserUtil.getLoginAppUser().getUserId())));
    	if( userSecurityDb==null ) {
    		userSecurityDb =new UserSecurity();
    		userSecurityDb.setUserId(AppUserUtil.getLoginAppUser().getUserId());
    		userSecurityDb.setUsername(AppUserUtil.getUserName());
    		userSecurityDb.setFaceFileId(userSecurityDto.getFaceFileId());
    		this.save(userSecurityDb);
    	}else {
    		userSecurityDb.setFaceFileId(userSecurityDto.getFaceFileId());
    		this.updateById(userSecurityDb);
    	}
    	return true;
    }
    
    /**
     * 校验人脸识别
     * @param userSecurity
     * @return
     */
    @Override
    public Boolean verifyFace(UserSecurityDto userSecurityDto) {
    	UserSecurity userSecurityDb = this.getOne(new QueryWrapper<>(new UserSecurity().setUsername(userSecurityDto.getUsername())));
    	if( userSecurityDb==null || userSecurityDb.getFaceFileId()==null ) {
    		throw new BaseException("请先在个人中心录入人脸信息");
    	}
    	
    	//调用人脸识别接口认证
    	Fileupload fileupload =new Fileupload();
    	fileupload.setFileuploadId(userSecurityDb.getFaceFileId());
    	PageInfo<Fileupload> pageInfo =fileCenterClient.listPageInternal(fileupload, Enable.Y.name());
    	fileupload =pageInfo.getList().get(0);
    	//获取的base64会有换行
    	if( !FaceUtil.verifyFace(userSecurityDto.getFaceFileBase64(), fileupload.getBase64().replaceAll("\r|\n", ""))) {
    		throw new BaseException("人脸信息不匹配");
    	}
    	
    	return true;
    }
    
}

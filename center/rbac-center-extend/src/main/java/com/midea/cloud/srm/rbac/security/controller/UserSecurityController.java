package com.midea.cloud.srm.rbac.security.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.security.dto.UserSecurityDto;
import com.midea.cloud.srm.model.rbac.security.entity.UserSecurity;
import com.midea.cloud.srm.rbac.security.service.IUserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  用户安全 前端控制器
 * </pre>
*
* @author haibo1.huang@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-03 09:15:27
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/user-security")
public class UserSecurityController extends BaseController {

    @Autowired
    private IUserSecurityService iUserSecurityService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public UserSecurity get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iUserSecurityService.getById(id);
    }

    /**
    * 新增
    * @param userSecurity
    */
    @PostMapping("/add")
    public void add(@RequestBody UserSecurity userSecurity) {
        Long id = IdGenrator.generate();
        userSecurity.setUserSecurityId(id);
        iUserSecurityService.save(userSecurity);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iUserSecurityService.removeById(id);
    }

    /**
    * 修改
    * @param userSecurity
    */
    @PostMapping("/modify")
    public void modify(@RequestBody UserSecurity userSecurity) {
        iUserSecurityService.updateById(userSecurity);
    }

    /**
    * 分页查询
    * @param userSecurity
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<UserSecurity> listPage(@RequestBody UserSecurity userSecurity) {
        PageUtil.startPage(userSecurity.getPageNum(), userSecurity.getPageSize());
        QueryWrapper<UserSecurity> wrapper = new QueryWrapper<UserSecurity>(userSecurity);
        return new PageInfo<UserSecurity>(iUserSecurityService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<UserSecurity> listAll() { 
        return iUserSecurityService.list();
    }
    
    /**
    * 上传人脸
    * @return
    */
    @PostMapping("/modifyFace")
    public Boolean modifyFace(@RequestBody UserSecurityDto userSecurityDto) { 
        return iUserSecurityService.modifyFace(userSecurityDto);
    }

}

package com.midea.cloud.srm.model.rbac.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;

/**
 * <pre>
 *  登录成功用户信息
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 17:21:10
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginAppUser extends User implements UserDetails {

    private static final long serialVersionUID = 1753977564987556640L;

    private Set<String> permissions; // 按钮权限(后台受保护资源)，在rbac接口/user/current初始化加载

    private List<Permission> menus; // 菜单，在rbac接口/user/current初始化加载，支持动态国际化的多语言菜单信息

    private List<OrganizationUser> organizationUsers; // 采购商对应组织权限

    private List<Role> rolePermissions;//角色权限

    private Map<String, Object> additionalInformation = Collections.emptyMap(); // 附件信息

    /**第一次登陆标记*/
    private String firstLogin;

    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<GrantedAuthority>();
        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.parallelStream().forEach(per -> {
                collection.add(new SimpleGrantedAuthority(per));
            });
        }
        return collection;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (super.getStartDate() == null && super.getEndDate() == null) {
            return true;
        } else if (super.getStartDate() != null && super.getEndDate() != null) {
            if ((super.getStartDate().isEqual(LocalDate.now()) || super.getStartDate().isBefore(LocalDate.now()))
                    && (super.getEndDate().isEqual(LocalDate.now()) || super.getEndDate().isAfter(LocalDate.now()))) {
                return true;
            }
        } else if (super.getStartDate() != null && (super.getStartDate().isEqual(LocalDate.now()) || super.getStartDate().isBefore(LocalDate.now()))) {
            return true;
        } else if (super.getEndDate() != null && (super.getEndDate().isEqual(LocalDate.now()) || super.getEndDate().isAfter(LocalDate.now()))) {
            return true;
        }
        return false;
    }

}

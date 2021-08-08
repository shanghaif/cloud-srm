package com.midea.cloud.srm.model.rbac.role.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.model.rbac.role.entity.RoleLanguage;
import com.midea.cloud.srm.model.rbac.role.entity.RolePermission;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  角色菜单信息 DTO
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-17 16:04
 *  修改内容:
 * </pre>
 */
@Data
public class RoleMenuDTO extends BaseDTO {

    private Role role;

    private List<RolePermission> rolePermissions;

    private List<RoleLanguage> roleLanguages;

}

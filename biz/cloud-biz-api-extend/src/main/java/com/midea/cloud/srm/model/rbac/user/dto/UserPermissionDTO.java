package com.midea.cloud.srm.model.rbac.user.dto;

import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.rbac.role.entity.RolePermission;
import com.midea.cloud.srm.model.rbac.role.entity.RoleUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-19 19:50
 *  修改内容:
 * </pre>
 */
@Data
public class UserPermissionDTO extends BaseDTO {

    private User user;

    private List<OrganizationUser> organizationUsers;

    private List<RoleUser> roleUsers;

}

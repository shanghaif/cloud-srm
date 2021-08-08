package com.midea.cloud.srm.model.rbac.permission.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.permission.entity.PermissionLanguage;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  菜单按钮 DTO
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-16 20:11
 *  修改内容:
 * </pre>
 */
@Data
public class MenuButtonDTO extends BaseDTO {

    private static final long serialVersionUID = 8294752827211126419L;

    /**
     * 菜单
     */
    private Permission menu;

    /**
     * 菜单多语言
     */
    private List<PermissionLanguage> permissionLanguages;

    /**
     * 按钮
     */
    private List<Permission> buttons;

}

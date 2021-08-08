package com.midea.cloud.srm.rbac.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SysConstant;
import com.midea.cloud.common.enums.PermissionType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.rbac.function.entity.Function;
import com.midea.cloud.srm.model.rbac.permission.dto.MenuButtonDTO;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.permission.entity.PermissionLanguage;
import com.midea.cloud.srm.rbac.function.service.IFunctionService;
import com.midea.cloud.srm.rbac.permission.mapper.PermissionMapper;
import com.midea.cloud.srm.rbac.permission.service.IPermissionLanguageService;
import com.midea.cloud.srm.rbac.permission.service.IPermissionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 *  权限维护 服务实现类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:30:00
 *  修改内容:
 * </pre>
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private IPermissionLanguageService iPermissionLanguageService;

    @Autowired
    private IFunctionService iFunctionService;

    @Transactional
    @Override
    public void savePermissionButton(Permission menu, List<PermissionLanguage> permissionLanguages, List<Permission> buttons) {
        Permission checkDuplicatPerm = this.getOne(new QueryWrapper<Permission>(
                new Permission().setPermissionType(PermissionType.MENU.name()).setPermissionCode(menu.getPermissionCode())));
        if (checkDuplicatPerm != null) {
            throw new BaseException("菜单编码不能重复");
        }
        if (CollectionUtils.isNotEmpty(buttons)) {
            this.saveBatch(buttons); // 保存按钮
        }
        this.save(menu); // 保存菜单
        iPermissionLanguageService.saveBatch(permissionLanguages); // 保存菜单多语言
    }

    @Transactional
    @Override
    public void updatePermissionButton(Permission menu, List<PermissionLanguage> permissionLanguages, List<Permission> buttons) {
        Permission checkDuplicatPerm = this.getOne(new QueryWrapper<Permission>(
                new Permission().setPermissionType(PermissionType.MENU.name()).setPermissionCode(menu.getPermissionCode())));
        if (checkDuplicatPerm != null && checkDuplicatPerm.getPermissionId().compareTo(menu.getPermissionId()) != 0) {
            throw new BaseException("菜单编码不能重复");
        }
        this.updateById(menu); // 修改菜单

        QueryWrapper<Permission> queryButtonWrapper = new QueryWrapper<Permission>(new Permission().setParentPermissionId(menu.getPermissionId()).setPermissionType(PermissionType.BUTTON.name())); // 先删除所有的按钮信息，后续再重新添加
        this.remove(queryButtonWrapper); // 删除按钮信息
        if (CollectionUtils.isNotEmpty(buttons)) {
            for (Permission item : buttons) {
                Assert.isTrue(StringUtils.isNotBlank(item.getPermission()), "按钮标识不能为空");
                Assert.isTrue(StringUtils.isNotBlank(item.getPermissionCode()), "按钮编码不能为空");
                Assert.isTrue(StringUtils.isNotBlank(item.getPermissionName()), "按钮名称不能为空");
            }
            this.saveBatch(buttons); // 保存按钮信息
        }

        QueryWrapper<PermissionLanguage> queryPermissionLanguageWrapper = new QueryWrapper<PermissionLanguage>(new PermissionLanguage().setPermissionId(menu.getPermissionId())); // 先删除所有的菜单多语言关联信息，后续重新关联
        iPermissionLanguageService.remove(queryPermissionLanguageWrapper); // 删除菜单多语言信息
        if (CollectionUtils.isNotEmpty(permissionLanguages)) {
            iPermissionLanguageService.saveBatch(permissionLanguages); // 批量添加菜单多语言信息
        }
    }

    @Override
    public PageInfo<Permission> listByParam(Permission permission) {
        List<Permission> permissions = this.getBaseMapper().selectByParam(permission, LocaleHandler.getLocaleKey());
        return new PageInfo<Permission>(permissions);
    }

    @Override
    public MenuButtonDTO getPermissionButton(Long id) {
        Permission menu = this.getById(id); // 菜单信息
        if (menu.getFunctionId() != null) {
            menu.setFunctionName(iFunctionService.getById(menu.getFunctionId()).getFunctionName());
        }
        if (menu.getParentPermissionId().compareTo(SysConstant.TREE_PARENT_ID) == 0) {
            menu.setParentPermissionName("");
        } else {
            List<Permission> parentMenus = this.getBaseMapper().selectByParam(new Permission().setPermissionId(menu.getParentPermissionId()), LocaleHandler.getLocaleKey()); // 父级菜单信息
            if (CollectionUtils.isNotEmpty(parentMenus)) {
                menu.setParentPermissionName(parentMenus.get(0).getPermissionName());
            }
        }
        QueryWrapper<Permission> queryPermissionWrapper = new QueryWrapper<Permission>(new Permission().setParentPermissionId(id).setPermissionType(PermissionType.BUTTON.name()));
        List<Permission> buttons = this.getBaseMapper().selectList(queryPermissionWrapper); // 按钮信息
        QueryWrapper<PermissionLanguage> queryPermissionLanguageWrapper = new QueryWrapper<PermissionLanguage>(new PermissionLanguage().setPermissionId(id));
        List<PermissionLanguage> permissionLanguages = iPermissionLanguageService.list(queryPermissionLanguageWrapper); // 菜单语言信息
        MenuButtonDTO menuButtonDTO = new MenuButtonDTO();
        menuButtonDTO.setMenu(menu);
        menuButtonDTO.setPermissionLanguages(permissionLanguages);
        menuButtonDTO.setButtons(buttons);
        return menuButtonDTO;
    }

    @Override
    public List<Permission> assembleTree(Permission permission) {
        Permission queryPermission = new Permission();
        queryPermission.setPermissionType(permission.getPermissionType());
        queryPermission.setEndDate(LocalDate.now());
        List<Permission> permissions = this.getBaseMapper().selectByParam(queryPermission, LocaleHandler.getLocaleKey());
        if (permission.getParentPermissionId() == null) {
            permission.setParentPermissionId(SysConstant.TREE_PARENT_ID);
        }
        return assemble(permissions, permission.getParentPermissionId());
    }

    /**
     * 组装所有的节点
     *
     * @param permissions
     * @param parentPermissionId
     * @return
     */
    public List<Permission> assemble(List<Permission> permissions, Long parentPermissionId) {
        List<Permission> endPointPermissionTree = new ArrayList<Permission>();
        // 保存当前节点的权限信息
        permissions.forEach(permission -> {
            if (permission.getParentPermissionId().compareTo(parentPermissionId) == 0) {
                Long parentId = permission.getPermissionId();
                // 添加子节点权限信息
                permission.setChildPermissions(assemble(permissions, parentId));
                endPointPermissionTree.add(permission);
            }
        });
        return endPointPermissionTree;
    }

    @Override
    public List<Permission> queryPermissionListByParam(Permission permission) {
        List<Permission> permissionsList = new ArrayList<Permission>();
        try {
            permissionsList = this.getBaseMapper().selectByParam(permission, LocaleHandler.getLocaleKey());
        } catch (Exception e) {
            LOGGER.error("通过查询参数获取菜单信息时报错：" + e.toString());
            throw new BaseException("通过查询参数获取菜单信息时报错.");
        }
        return permissionsList;
    }

    @Override
    public Permission getMenu(Long menuId) {
        MenuButtonDTO menuButtonDTO = getPermissionButton(menuId);

        return menuButtonDTO.getMenu();
    }

    public List<Permission> queryByAuthButton(Permission permission, Long userId) {
        permission.setPermissionType(PermissionType.BUTTON.name());
        return this.getBaseMapper().selectByAuthButton(LocalDate.now(), userId, permission);
    }

    @Override
    public List<Permission> queryByAuthMenu(Permission permission) {
        permission.setPermissionType(PermissionType.MENU.name());
        List<Permission> menus = this.getBaseMapper().selectByMenuButton(LocalDate.now(), AppUserUtil.getLoginAppUser().getUserId(), LocaleHandler.getLocaleKey(), permission);
        return assemble(menus, SysConstant.TREE_PARENT_ID);
    }


    @Override
    public List<Function> listFunctionByParm(Permission permission) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>(permission);
        List<Permission> permissions = this.list(queryWrapper);
        List<Function> functions = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(permissions)) {
            HashSet<Long> set = new HashSet<>();
            List<Long> functionIds = new ArrayList<>();
            for (Permission permissionEntity : permissions) {
                if (permissionEntity == null) continue;
                set.add(permissionEntity.getFunctionId());
            }
            Iterator<Long> iterator = set.iterator();
            while (iterator.hasNext()) {
                functionIds.add(iterator.next());
            }
            if (CollectionUtils.isNotEmpty(functionIds)) {
                functions = iFunctionService.listByIds(functionIds);
            }
        }
        return functions;
    }

}

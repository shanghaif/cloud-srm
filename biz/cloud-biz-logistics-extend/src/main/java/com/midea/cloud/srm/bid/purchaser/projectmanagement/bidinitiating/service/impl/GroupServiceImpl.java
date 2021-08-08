package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.GroupMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IGroupService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Group;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  招标工作小组表 服务实现类
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 13:59:12
 *  修改内容:
 * </pre>
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    @Autowired
    private RbacClient rbacClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatchGroup(List<Group> groupList, Long bidingId) {
        //校验成员用户是否存在
        for (Group group : groupList) {
//            checkAddParam(group);
            if (group.getUserId() == null) {
                LoginAppUser user = rbacClient.findByUsername(group.getUserName());
                if (user != null) {
                    group.setUserId(user.getUserId());
                }else {
                    throw new BaseException(LocaleHandler.getLocaleMsg("该用户不存在", group.getUserName()));
                }
            }
            Long id = IdGenrator.generate();
            group.setGroupId(id).setConfirmeDatetime(new Date()).setBidingId(bidingId);
        }
        this.saveBatch(groupList);
    }

    private void checkAddParam(Group group) {
        Assert.notNull(group.getUserName(), "成员账号不能为空");
        Assert.notNull(group.getFullName(), "成员姓名不能为空");
        Assert.notNull(group.getUserName(), "电话不能为空");
        Assert.notNull(group.getUserName(), "邮箱不能为空");
        Assert.notNull(group.getUserName(), "岗位不能为空");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchGroup(List<Group> groupList, Biding biding) {
        //先根据招标id删除
        Long bidingId = biding.getBidingId();
       if(Objects.nonNull(bidingId)){
           remove(Wrappers.lambdaQuery(Group.class).eq(Group::getBidingId,bidingId));
       }
        //批量新增
        this.saveBatchGroup(groupList, bidingId);
    }
}

package com.midea.cloud.srm.base.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.work.mapper.CeeaBaseWorkSortMapper;
import com.midea.cloud.srm.base.work.service.ICeeaBaseWorkSortService;
import com.midea.cloud.srm.model.base.work.entity.CeeaBaseWorkSort;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * 我的任务列表排序
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-24 17:02
 * 修改内容:
 * </pre>
 */
@Service
public class ICeeaBaseWorkSortServiceImpl extends ServiceImpl<CeeaBaseWorkSortMapper, CeeaBaseWorkSort> implements ICeeaBaseWorkSortService {


    /*** 保存我的任务列表排序信息 ***/
    @Override
    @Transactional
    public void saveWorkCountSort(List<CeeaBaseWorkSort> list) throws Exception {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        for(int i = 0; i < list.size(); ++i) {
            list.get(i).setWorkSortId(IdGenrator.generate());
            list.get(i).setUserId(loginAppUser.getUserId());
            list.get(i).setSort(i);
        }

        QueryWrapper<CeeaBaseWorkSort> queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", loginAppUser.getUserId());
        this.remove(queryWrapper);
        this.saveBatch(list);
    }
}

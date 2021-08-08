//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.midea.cloud.srm.base.work.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.work.service.ICeeaBaseWorkSortService;
import com.midea.cloud.srm.base.work.service.IWorkService;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.base.work.dto.WorkRequestDTO;
import com.midea.cloud.srm.model.base.work.entity.CeeaBaseWorkSort;
import com.midea.cloud.srm.model.base.work.entry.Work;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping({"/work"})
public class WorkController extends BaseController {
    @Autowired
    private IWorkService iWorkService;
    @Autowired
    private ICeeaBaseWorkSortService ceeaBaseWorkSortService;

    public WorkController() {
    }

    @GetMapping({"/workCount"})
    public List<WorkCount> workCount() {
        return this.iWorkService.workCount();
    }

    /** @deprecated */
    @Deprecated
    @GetMapping({"/finishWork"})
    public void finishWork(@RequestParam("fromId") Long fromId, @RequestParam("topic") String topic) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("FROM_ID", fromId);
        queryWrapper.eq("WORK_STATUS", "ON_HAND");
        queryWrapper.eq("TOPIC", topic);
        Work work = (Work)this.iWorkService.getOne(queryWrapper);
        Assert.notNull(work, "找不到该任务");
        work.setWorkStatus("HANDLED");
        this.iWorkService.updateById(work);
    }

    /** @deprecated */
    @Deprecated
    @GetMapping({"/cancalWork"})
    public void cancalWork(@RequestParam("fromId") Long fromId, @RequestParam("topic") String topic) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("FROM_ID", fromId);
        queryWrapper.eq("WORK_STATUS", "ON_HAND");
        queryWrapper.eq("TOPIC", topic);
        Work work = (Work)this.iWorkService.getOne(queryWrapper);
        Assert.notNull(work, "找不到该任务");
        work.setWorkStatus("CANCAL");
        this.iWorkService.updateById(work);
    }

    /** @deprecated */
    @Deprecated
    @PostMapping({"/save"})
    public void save(@RequestBody Work work) {
        this.checkSaveOrUpdate(work);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("FROM_ID", work.getFromId());
        queryWrapper.eq("WORK_STATUS", "ON_HAND");
        queryWrapper.eq("TOPIC", work.getTopic());
        Work checkWork = (Work)this.iWorkService.getOne(queryWrapper);
        if (checkWork != null) {
            work.setWorkId(checkWork.getWorkId());
        } else {
            work.setWorkId(IdGenrator.generate());
            work.setWorkStatus("ON_HAND");
        }

        work.setLinkUrl(work.getLinkUrl() + "?id=" + work.getFromId());
        this.iWorkService.saveOrUpdate(work);
    }

    private void checkSaveOrUpdate(Work work) {
        Assert.notNull(work, "数据错误");
        if (StringUtils.isNotBlank(work.getHandleBy()) || StringUtils.isNotBlank(work.getHandleNickname()) || work.getHandleId() != null) {
            Assert.notNull(work.getHandleBy(), "处理人账号不能为空");
            Assert.notNull(work.getHandleNickname(), "处理人姓名不能为空");
            Assert.notNull(work.getHandleId(), "处理人ID不能为空");
        }

        if (StringUtils.isNotBlank(work.getToVendorCode()) || StringUtils.isNotBlank(work.getToVendorName()) || work.getToVendorId() != null) {
            Assert.notNull(work.getToVendorCode(), "接收供应商编号不能为空");
            Assert.notNull(work.getToVendorName(), "接收供应商名称不能为空");
            Assert.notNull(work.getToVendorId(), "接收供应商ID不能为空");
        }

        Assert.notNull(work.getFromId(), "业务来源ID不能为空");
        Assert.notNull(work.getSendBy(), "发送人不能为空");
        Assert.notNull(work.getSenderId(), "发送人ID不能为空");
        Assert.notNull(work.getTopic(), "主题不能为空");
        Assert.notNull(work.getLinkUrl(), "链接不能为空");
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
            work.setFromVendorId(loginAppUser.getCompanyId());
            work.setFromVendorCode(loginAppUser.getCompanyCode());
            work.setFromVendorName(loginAppUser.getCompanyName());
        }

        work.setSendTime(new Date());
    }

    /** @deprecated */
    @Deprecated
    @PostMapping({"/listPage"})
    public PageInfo<Work> listPage(@RequestBody WorkRequestDTO workRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType()) && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            Assert.isTrue(false, "用户类型不存在");
        }

        if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
            workRequestDTO.setToVendorId(loginAppUser.getCompanyId());
        }

        if (StringUtils.isNotBlank(workRequestDTO.getOpt())) {
            String var3 = workRequestDTO.getOpt();
            byte var4 = -1;
            switch(var3.hashCode()) {
                case -578635057:
                    if (var3.equals("ON_HAND")) {
                        var4 = 0;
                    }
                    break;
                case -310562109:
                    if (var3.equals("NOT_APPROVED")) {
                        var4 = 1;
                    }
                    break;
                case 832629906:
                    if (var3.equals("COPY_TO_ME")) {
                        var4 = 4;
                    }
                    break;
                case 1234847727:
                    if (var3.equals("MY_START")) {
                        var4 = 3;
                    }
                    break;
                case 1410786076:
                    if (var3.equals("HANDLED")) {
                        var4 = 2;
                    }
            }

            switch(var4) {
                case 0:
                    workRequestDTO.setWorkStatus("ON_HAND");
                    break;
                case 1:
                    workRequestDTO.setWorkStatus("NOT_APPROVED");
                    workRequestDTO.setHandleId(loginAppUser.getUserId());
                    break;
                case 2:
                    workRequestDTO.setWorkStatus("HANDLED");
                    break;
                case 3:
                    workRequestDTO.setSenderId(loginAppUser.getUserId());
                case 4:
            }
        }

        PageUtil.startPage(workRequestDTO.getPageNum(), workRequestDTO.getPageSize());
        return new PageInfo(this.iWorkService.findList(workRequestDTO));
    }

    /*** 保存我的任务列表排序信息 ***/
    @PostMapping(value = "saveWorkCountSort")
    public void saveWorkCountSort(@RequestBody List<CeeaBaseWorkSort> list) throws Exception {
        this.ceeaBaseWorkSortService.saveWorkCountSort(list);
    }
}

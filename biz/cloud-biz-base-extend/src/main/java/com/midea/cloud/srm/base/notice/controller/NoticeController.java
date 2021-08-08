package com.midea.cloud.srm.base.notice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.notice.service.INoticeService;
import com.midea.cloud.srm.model.base.notice.dto.NoticeDetailDTO;
import com.midea.cloud.srm.model.base.notice.dto.NoticeRequestDTO;
import com.midea.cloud.srm.model.base.notice.dto.NoticeSaveRequestDTO;
import com.midea.cloud.srm.model.base.notice.entry.Notice;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <pre>
 *  公告表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/8 16:41
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/notice/notice")
public class NoticeController extends BaseController {
    @Autowired
    private INoticeService iNoticeService;

    /**
     * 分页查询公告列表
     * @param
     */
    @PostMapping("/listPage")
    public PageInfo<Notice> listPage(@RequestBody NoticeRequestDTO noticeRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            noticeRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(noticeRequestDTO.getPageNum(), noticeRequestDTO.getPageSize());
        return new PageInfo(iNoticeService.listPage(noticeRequestDTO));
    }

    /**
     * 获取公告信息
     * @param
     */
    @GetMapping("/get")
    public NoticeDetailDTO get(@RequestParam("noticeId") Long noticeId) {
        Assert.notNull(noticeId, "公告ID不能为空");
        return iNoticeService.getDetail(noticeId);
    }



    /**
     * 新增
     * @param noticeSaveRequestDTO
     */
    @PostMapping("/add")
    public void add(@RequestBody NoticeSaveRequestDTO noticeSaveRequestDTO) {
        Assert.notNull(noticeSaveRequestDTO, "数据错误");
        Assert.notNull(noticeSaveRequestDTO.getNotice(), "数据错误");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getNoticeType(), "公告分类不能为空");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getTitle(), "标题不能为空");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getDetail(), "正文不能为空");

        noticeSaveRequestDTO.getNotice().setNoticeStatus("UNPUBLISHED");
        iNoticeService.saveOrUpdate(noticeSaveRequestDTO);
    }

    /**
     * 删除
     * @param noticeId
     */
    @GetMapping("/delete")
    public void delete(@RequestParam("noticeId") Long noticeId) {
        Assert.notNull(noticeId, "noticeId不能为空");
        iNoticeService.delete(noticeId);
    }

    /**
     * 修改
     * @param noticeSaveRequestDTO
     */
    @PostMapping("/modify")
    public void modify(@RequestBody NoticeSaveRequestDTO noticeSaveRequestDTO) {
        Assert.notNull(noticeSaveRequestDTO, "数据错误");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getNoticeId(), "公告ID不能为空");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getNoticeType(), "公告分类不能为空");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getTitle(), "标题不能为空");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getDetail(), "正文不能为空");

        iNoticeService.saveOrUpdate(noticeSaveRequestDTO);
    }

    /**
     * 发布
     * @param noticeSaveRequestDTO
     */
    @PostMapping("/publish")
    public void publish(@RequestBody NoticeSaveRequestDTO noticeSaveRequestDTO) {
        Assert.notNull(noticeSaveRequestDTO, "数据错误");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getNoticeType(), "公告分类不能为空");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getTitle(), "标题不能为空");
        Assert.notNull(noticeSaveRequestDTO.getNotice().getDetail(), "正文不能为空");

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        noticeSaveRequestDTO.getNotice().setNoticeStatus("PUBLISHED");
        noticeSaveRequestDTO.getNotice().setPublishBy(loginAppUser.getUsername());
        noticeSaveRequestDTO.getNotice().setPublisherId(loginAppUser.getUserId());
        noticeSaveRequestDTO.getNotice().setPublishTime(new Date());

        iNoticeService.saveOrUpdate(noticeSaveRequestDTO);
    }
}

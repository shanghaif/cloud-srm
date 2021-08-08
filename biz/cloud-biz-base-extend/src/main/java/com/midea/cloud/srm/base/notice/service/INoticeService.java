package com.midea.cloud.srm.base.notice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.notice.dto.NoticeDetailDTO;
import com.midea.cloud.srm.model.base.notice.dto.NoticeRequestDTO;
import com.midea.cloud.srm.model.base.notice.dto.NoticeSaveRequestDTO;
import com.midea.cloud.srm.model.base.notice.entry.Notice;

import java.util.List;

/**
 * <pre>
 *  公告表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/8 16:43
 *  修改内容:
 * </pre>
 */
public interface INoticeService extends IService<Notice> {
    void saveOrUpdate(NoticeSaveRequestDTO noticeSaveRequestDTO);

    void delete(Long noticeId);

    List listPage(NoticeRequestDTO noticeRequestDTO);

    NoticeDetailDTO getDetail(Long noticeId);
}

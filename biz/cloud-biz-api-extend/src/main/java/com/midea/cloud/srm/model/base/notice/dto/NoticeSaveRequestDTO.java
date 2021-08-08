package com.midea.cloud.srm.model.base.notice.dto;

import com.midea.cloud.srm.model.base.notice.entry.Notice;
import com.midea.cloud.srm.model.base.notice.entry.NoticeVendor;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/8 19:03
 *  修改内容:
 * </pre>
 */
@Data
public class NoticeSaveRequestDTO {
    private Notice notice;

    private List<NoticeVendor> noticeVendors;

    private List<Long> deleteNoticeVendorIds;
}

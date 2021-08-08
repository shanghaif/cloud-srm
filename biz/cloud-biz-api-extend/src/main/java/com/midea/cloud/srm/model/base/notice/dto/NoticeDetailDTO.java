package com.midea.cloud.srm.model.base.notice.dto;

import com.midea.cloud.srm.model.base.notice.entry.Notice;
import com.midea.cloud.srm.model.base.notice.entry.NoticeVendor;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  公告表明细，包含邀请供应商列表 数据返回传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/14 10:09
 *  修改内容:
 * </pre>
 */
@Data
public class NoticeDetailDTO extends Notice {

    private List<NoticeVendor> noticeVendors;
}

package com.midea.cloud.srm.model.base.notice.dto;

import com.midea.cloud.srm.model.base.notice.entry.Notice;
import com.midea.cloud.srm.model.base.notice.entry.NoticeVendor;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  公告表查询 数据请求传输对象
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
public class NoticeRequestDTO extends Notice {
    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 开始发布日期
     */
    private String startPublisherTime;

    /**
     * 截止发布日期
     */
    private String endPublisherTime;

    /**
     * 开始创建日期
     */
    private String startCreationDate;


    /**
     * 截止创建日期
     */
    private String endCreationDate;
}

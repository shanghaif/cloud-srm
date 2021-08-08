package com.midea.cloud.srm.base.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.notice.dto.NoticeDetailDTO;
import com.midea.cloud.srm.model.base.notice.dto.NoticeRequestDTO;
import com.midea.cloud.srm.model.base.notice.entry.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author huangbf3
 * @since 2020-02-12
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    List findList(NoticeRequestDTO noticeRequestDTO);

    NoticeDetailDTO getDetail(@Param("noticeId")Long noticeId);
}
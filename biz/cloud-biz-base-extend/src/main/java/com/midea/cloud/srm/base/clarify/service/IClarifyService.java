package com.midea.cloud.srm.base.clarify.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingClarifyDto;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingClarifyQueryDto;
import com.midea.cloud.srm.model.base.clarify.dto.SourcingClarifyReplyDto;
import com.midea.cloud.srm.model.base.clarify.enums.ClarifyStatus;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyDetailVO;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyReplyDetailVO;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyReplyVO;
import com.midea.cloud.srm.model.base.clarify.vo.SourcingClarifyVO;

import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/07 13:52
 */
public interface IClarifyService {
    /**
     * 发布澄清公告 详情页用
     *
     * @param dto
     * @return
     */
    SourcingClarifyDetailVO publishSourcingClarify(SourcingClarifyDto dto);

    /**
     * 发布澄清公告 列表用
     *
     * @param clarifyId
     * @return
     */
    Boolean publishSourcingClarifyById(Long clarifyId);

    /**
     * 暂存澄清公告
     *
     * @param dto
     * @param status
     * @return
     */
    SourcingClarifyDetailVO saveTemporarySourcingClarify(SourcingClarifyDto dto, ClarifyStatus status);

    /**
     * 根据id删除澄清公告
     *
     * @param sourcingClarifyId
     * @return
     */
    Boolean deleteSourcingClarifyById(Long sourcingClarifyId);

    /**
     * 回复澄清公告
     *
     * @param dto
     * @return
     */
    SourcingClarifyReplyDetailVO replySourcingClarify(SourcingClarifyReplyDto dto);

    /**
     * 暂存澄清回复
     *
     * @param status
     * @param dto
     * @return
     */
    SourcingClarifyReplyDetailVO saveTemporarySourcingClarifyReply(SourcingClarifyReplyDto dto, ClarifyStatus status);


    /**
     * 根据条件返回澄清公告列表
     *
     * @param queryDto
     * @return
     */
    PageInfo<SourcingClarifyVO> listSourcingClarifyByPage(SourcingClarifyQueryDto queryDto);


    /**
     * 供应商查看澄清公告详情
     *
     * @param clarifyId
     * @return
     */
    SourcingClarifyReplyDetailVO queryReplyDetailById(Long clarifyId);

    /**
     * （采购商）根据回复id查询供应商回复
     *
     * @param replyId
     * @return
     */
    SourcingClarifyReplyDetailVO queryReplyDetailByReplyId(Long replyId);

    /**
     * 获取澄清公告详情
     *
     * @param clarifyId
     * @return
     */
    SourcingClarifyVO queryClarifyDetailById(Long clarifyId);
}

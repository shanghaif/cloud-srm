package com.midea.cloud.srm.model.base.clarify.vo;

import lombok.Data;

import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/07 20:56
 */
@Data
public class SourcingClarifyReplyDetailVO {
    private SourcingClarifyReplyVO reply;
    private List<SourcingClarifyFileVO> files;
}

package com.midea.cloud.srm.model.base.clarify.vo;

import lombok.Data;

import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/07 16:27
 */
@Data
public class SourcingClarifyDetailVO {
    /**
     * 澄清信息
     */
    private SourcingClarifyVO clarify;
    /**
     * 澄清文件
     */
    private List<SourcingClarifyFileVO> files;

}

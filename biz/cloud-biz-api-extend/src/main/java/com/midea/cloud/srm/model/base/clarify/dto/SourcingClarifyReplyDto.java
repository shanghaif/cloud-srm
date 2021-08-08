package com.midea.cloud.srm.model.base.clarify.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/07 14:57
 */
@Data
public class SourcingClarifyReplyDto {

    /**
     * 澄清公告回复主键
     */
    @NotNull(message = "澄清回复id不能为空")
    private Long clarifyReplyId;

    /**
     * 澄清公告主键
     */
    @NotNull(message = "澄清公告主键不能为空")
    private Long clarifyId;

    /**
     * 回复内容
     */
    @NotEmpty(message = "回复内容不能为空")
    private String replyContent;


    @Valid
    List<SourcingClarifyFileDto> files;
    //供应商
    private Long vendorId;
    private String vendorName;
    private String vendorCode;
    //公司
    private String companyCode;
    private Long companyId;
    private String companyName;
}

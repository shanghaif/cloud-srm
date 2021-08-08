package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/3 15:36
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PaymentApplyDto {
    private BoeHeader boeHeader;

    private List<BoeExpenseCavDto> boeExpenseCavDtos;

    private List<ZfsBoePayment> zfsBoePayments;

    private List<AttachmentDto> attachmentDtos;

    private List<ZfsBoeLine> zfsBoeLines;
}

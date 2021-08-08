package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/3 10:36
 *  修改内容:
 * </pre>
 */
@Data
public class BoeDto {

    BoeHeader boeHeader;
    List<ZfsBoeLine> zfsBoeLines;
    List<ZfsBoePayment> zfsBoePayments;
    List<AttachmentDto> attachmentDtos;
}

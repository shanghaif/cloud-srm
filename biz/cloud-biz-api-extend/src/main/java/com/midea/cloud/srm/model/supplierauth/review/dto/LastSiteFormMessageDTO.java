package com.midea.cloud.srm.model.supplierauth.review.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;

/**
 * <pre>
 *  功能名称描述:  上一次现场评审信息DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-18 16:01
 *  修改内容:
 * </pre>
 */
@Data
public class LastSiteFormMessageDTO extends BaseDTO{

    private LocalDate lastSiteDate;

    private String lastSiteMember;
}

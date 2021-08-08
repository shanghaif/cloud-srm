package com.midea.cloud.srm.model.quartz.scheduler.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 * 策略表DTO
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/7
 *  修改内容:
 * </pre>
 */
@Data
public class QuartzStrategyDTO extends BaseDTO {

    private static final long serialVersionUID = 597665258918257509L;

    private Long strategyId;

    private String strategyName;

    private String strategyUrl;


}

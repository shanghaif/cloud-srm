package com.midea.cloud.srm.model.base.categorydv.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-7 11:35
 *  修改内容:
 * </pre>
 */
@Data
public class DvRequestDTO extends BaseDTO {
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private String fullName;
    private Long userId;
    private String isActive;

}

package com.midea.cloud.srm.model.base.work.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 供应商待办查询DTO
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-26 14:49
 * 修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class WorkCountQueryDTO extends BaseDTO {
    private String status;// 查询单据状态
    private Long userId;// 当前用户id
    private Long vendorId;// 当前供应商id
    private String vendorCode;// 当前供应商编码
}

package com.midea.cloud.srm.model.supplier.demotion.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/1/7 14:17
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class DemotionOrgQueryDTO extends BaseDTO {

    /**
     * 供应商id
     */
    private Long companyId;

    /**
     * 品类ids
     */
    private List<Long> categoryIds;

    /**
     * 降级类型
     */
    private String demotionType;

}

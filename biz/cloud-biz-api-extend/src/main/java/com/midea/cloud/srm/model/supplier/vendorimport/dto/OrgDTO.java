package com.midea.cloud.srm.model.supplier.vendorimport.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  根据供应商Id查询合作的业务实体 实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/17 14:44
 *  修改内容:
 * </pre>
 */
@Data
public class OrgDTO extends BaseDTO {

    /**
     * 业务实体ID
     */
    private Long orgId;

    /**
     * 业务实体编码
     */
    private String orgCode;

    /**
     * 业务实体名称
     */
    private String orgName;
}

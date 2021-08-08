package com.midea.cloud.srm.model.supplier.risk.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/3
 *  修改内容:
 * </pre>
 */
@Data
public class ExternalRiskInfoDto extends BaseDTO {
    /**
     * 标题
     */
    private String title;

    /**
     * 项数
     */
    private int num;

    /**
     * 内容
     */
    private String content;
}

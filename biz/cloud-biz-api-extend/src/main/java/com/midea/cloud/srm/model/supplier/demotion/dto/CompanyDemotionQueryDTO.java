package com.midea.cloud.srm.model.supplier.demotion.dto;

import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import lombok.Data;
import lombok.experimental.Accessors;

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
 *  修改日期: 2021/1/6 22:10
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class CompanyDemotionQueryDTO extends CompanyDemotion {

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 品类编码
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 关联绩效单id
     */
    private String performanceId;

    /**
     * 关联绩效单号
     */
    private String performanceNumber;

}

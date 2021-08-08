package com.midea.cloud.srm.model.base.formula.dto.query;

import lombok.Data;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/26 22:32
 *  修改内容:
 * </pre>
 */
@Data
public class BaseMaterialPriceQueryDto {

    private Long baseMaterialPriceId;

    private String baseMaterialPriceType;

    private String baseMaterialCode;

    private String baseMaterialName;

    private String priceFrom;

    private String baseMaterialPriceStatus;

    private Integer pageSize;

    private Integer pageNum;

    private Date activeDateFrom;

    /**
     * 有效期结束日期
     */
    private Date activeDateTo;
    
    private String dataSource;

}

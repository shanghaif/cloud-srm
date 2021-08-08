package com.midea.cloud.srm.model.pm.pr.shopcart.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 *  购物车批量更新采购类型和需求对象
 * </pre>
 *
 * @author wuhx29@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/16 17:31
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ShopcartDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Long> ids;

    private String purchaseType;

    private LocalDate requirementDate;
}

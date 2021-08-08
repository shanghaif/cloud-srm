package com.midea.cloud.srm.model.logistics.tradetermscombination.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class legExpenseItemDto implements Serializable {

    private static final long serialVersionUID = -2776353894510663634L;

    /**
     * 费项编码
     */
    private String expenseItem;

    /**
     * LEG编码
     */
    private String leg;
}

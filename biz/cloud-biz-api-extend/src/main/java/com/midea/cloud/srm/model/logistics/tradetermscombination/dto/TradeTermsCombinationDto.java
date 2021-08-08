package com.midea.cloud.srm.model.logistics.tradetermscombination.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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
public class TradeTermsCombinationDto implements Serializable {

    private static final long serialVersionUID = -302813063130736652L;
    /**
     * 组合名称
     */
    private String combinationName;

    /**
     * 贸易术语编码
     */
    private String tradeTerm;

    /**
     * 进出口方式编码
     */
    private String importExportMethod;

    /**
     * leg和费项组合
     */
    private List<legExpenseItemDto> legExpenseItemDtos;
}

package com.midea.cloud.srm.model.perf.inditors.dto;

import com.midea.cloud.srm.model.common.BaseDTO;

import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsHeader;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsLine;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  指标头和指标行DTO
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/27 11:20
 *  修改内容:
 * </pre>
 */
@Data
public class IndicatorsDTO extends BaseDTO {

    private static final long serialVersionUID = 7871699673306643745L;
    /**指标头*/
    private IndicatorsHeader indicatorsHeader;
    /**指标行集合*/
    private List<IndicatorsLine> indicatorsLineList;
}

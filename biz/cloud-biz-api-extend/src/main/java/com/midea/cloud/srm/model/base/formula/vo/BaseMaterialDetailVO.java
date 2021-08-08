package com.midea.cloud.srm.model.base.formula.vo;

import lombok.Data;

import java.util.List;

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
 *  修改日期: 2020/8/26 22:57
 *  修改内容:
 * </pre>
 */
@Data
public class BaseMaterialDetailVO {
    private BaseMaterialVO baseMaterialVO;
    private List<BaseMaterialPriceVO> baseMaterialPriceVOList;
}

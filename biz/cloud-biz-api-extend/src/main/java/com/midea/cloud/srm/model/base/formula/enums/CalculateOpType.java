package com.midea.cloud.srm.model.base.formula.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
 *  修改日期: 2020/8/31 15:33
 *  修改内容:
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum CalculateOpType {
    UPDATE(1),DELETE(0),SPILT(2);
    private Integer opType;
}

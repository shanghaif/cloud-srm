package com.midea.cloud.srm.model.base.formula.dto.query;

import lombok.Data;

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
 *  修改日期: 2020/8/28 17:05
 *  修改内容:
 * </pre>
 */
@Data
public class EssentialFactorQueryDto {
    /**
     * 要素名称
     */
    private String essentialFactorName;
    /**
     * 要素描述
     */
    private String essentialFactorDesc;
    /**
     * 来源
     */
    private String essentialFactorFrom;
    /**
     * 要素状态
     */
    private String essentialFactorStatus;
    /**
     * 当前页大小
     */
    private Integer pageSize;
    /**
     * 当前页数
     */
    private Integer pageNum;
}

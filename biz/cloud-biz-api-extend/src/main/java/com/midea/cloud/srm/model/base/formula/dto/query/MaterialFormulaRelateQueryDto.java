package com.midea.cloud.srm.model.base.formula.dto.query;

import com.midea.cloud.srm.model.common.BasePage;
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
 *  修改日期: 2020/9/4 14:15
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialFormulaRelateQueryDto  {

    private String materialCode;

    private String materialName;

    private Long categoryId;

    private int pageSize;

    private int pageNum;
}

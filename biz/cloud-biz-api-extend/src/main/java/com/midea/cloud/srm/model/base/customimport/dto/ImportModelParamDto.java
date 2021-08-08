package com.midea.cloud.srm.model.base.customimport.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 * 自定义导入模板参数
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/21
 *  修改内容:
 * </pre>
 */
@Data
public class ImportModelParamDto extends BaseDTO {
    /**
     * 开始时间
     */
    private LocalDate startDate;

    /**
     * 结束时间
     */
    private LocalDate endDate;

    /**
     * 导入自定字典标题
     */
    private List<String> dicParams;

}

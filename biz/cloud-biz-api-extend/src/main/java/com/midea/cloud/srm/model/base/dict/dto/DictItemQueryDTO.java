package com.midea.cloud.srm.model.base.dict.dto;

import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  字典查询DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/2 10:00
 *  修改内容:
 * </pre>
 */
@Data
public class DictItemQueryDTO extends BaseDTO {

    /**
     * 字典码
     */
    private String dictCode;

    /**
     * 字典条目名称列表
     */
    private List<String> dictItemNames;

}

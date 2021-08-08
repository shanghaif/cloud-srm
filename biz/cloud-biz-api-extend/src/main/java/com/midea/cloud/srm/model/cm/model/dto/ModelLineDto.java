package com.midea.cloud.srm.model.cm.model.dto;

import com.midea.cloud.srm.model.cm.model.entity.ModelHead;
import com.midea.cloud.srm.model.cm.model.entity.ModelLine;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
*  <pre>
 *  合同行表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 09:24:06
 *  修改内容:
 * </pre>
*/
@Data
public class ModelLineDto extends BaseDTO {

    private static final long serialVersionUID = -7877749465606917132L;
    /**
     * 元素列表
     */
    private List<ModelLine> modelLine;
    /**
     * 合同模板
     */
    private ModelHead modelHead;
}

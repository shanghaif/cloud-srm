package com.midea.cloud.srm.cm.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.cm.model.entity.ModelElement;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  合同模板元素表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 17:30:16
 *  修改内容:
 * </pre>
*/
public interface IModelElementService extends IService<ModelElement> {
    /**
     * 新增自定义元素合同元素
     * @param modelElement
     * @return
     */
    Map<String,Object> add (ModelElement modelElement);

    /**
     * 批量根据元素符号获取变量元素
     * @param variableSigns
     * @return
     */
    List<ModelElement> getElementList(List<String> variableSigns);

    /**
     * 获取元素列表
     * @param modelElement
     * @return
     */
    List<ModelElement> getElement(ModelElement modelElement);

    /**
     * 同步至定元素
     * @param elementId
     */
    void syncFixed(Long elementId);

}

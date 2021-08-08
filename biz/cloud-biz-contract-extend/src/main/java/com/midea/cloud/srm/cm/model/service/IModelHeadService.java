package com.midea.cloud.srm.cm.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.model.entity.ModelHead;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  合同模板头表 服务类
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
public interface IModelHeadService extends IService<ModelHead> {
    /**
     * 新增
     * @param modelHead
     */
    Map<String,Object> add(ModelHead modelHead);

    /**
     * 更新
     * @param modelHead
     * @return
     */
    Map<String,Object> update(ModelHead modelHead);

    /**
     * 分页查询
     * @return
     */
    PageInfo<ModelHead> listPage (ModelHead modelHead);

    /**
     * 查找生效日期有效且状态为通过的列表
     * @return
     */
    List<ModelHead> modelList();

    List<ModelHead> modelListByType(String modelType);

    /**
     * 合同生效
     * @param modelHeadId
     */
    void takeEffect(Long modelHeadId);

    /**
     * 合同失效
     * @param modelHeadId
     */
    void failure(Long modelHeadId);

    /**
     * 合同冻结
     * @param modelHeadId
     */
    void freeze(Long modelHeadId);
}

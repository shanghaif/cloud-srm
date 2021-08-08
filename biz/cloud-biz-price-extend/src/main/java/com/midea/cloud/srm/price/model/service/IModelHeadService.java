package com.midea.cloud.srm.price.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.price.model.entity.ModelHead;

/**
*  <pre>
 *  价格模型头表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:27:17
 *  修改内容:
 * </pre>
*/
public interface IModelHeadService extends IService<ModelHead> {
    /**
     * 分页查询
     * @param modelHead
     * @return
     */
    PageInfo<ModelHead> listPage(ModelHead modelHead);

    /**
     * 新增
     * @param modelHead
     * @return
     */
    Long add(ModelHead modelHead);

    /**
     * 修改
     * @param modelHead
     */
    Long modify(ModelHead modelHead);

    /**
     * 获取详情
     * @param priceModelHeadId
     * @return
     */
    ModelHead get(Long priceModelHeadId);

    /**
     * 删除
     * @param priceModelHeadId
     */
    void delete(Long priceModelHeadId);

    /**
     * 生效
     * @param priceModelHeadId
     */
    void takeEffect(Long priceModelHeadId);

    /**
     * 失效
     * @param priceModelHeadId
     */
    void failure(Long priceModelHeadId);
}

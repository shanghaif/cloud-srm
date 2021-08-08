package com.midea.cloud.srm.price.costelement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.price.costelement.entity.CostElement;

/**
*  <pre>
 *  成本要素表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 13:58:19
 *  修改内容:
 * </pre>
*/
public interface ICostElementService extends IService<CostElement> {

    /**
     * 分页查询
     * @param costElement
     * @return
     */
    PageInfo<CostElement> listPage(CostElement costElement);

    /**
     * 新增成本要素
     * @param costElement
     */
    Long updateOrSave(CostElement costElement);

    /**
     * 根据id查找
     * @param costElementId
     * @return
     */
    CostElement get(Long costElementId);

    /**
     * 删除
     * @param costElementId
     */
    void delete(Long costElementId);

    /**
     * 生效
     * @param costElementId
     */
    void takeEffect(Long costElementId);

    /**
     * 失效
     * @param costElementId
     */
    void failure(Long costElementId);

    /**
     * 创建新版本
     * @param costElement
     * @return
     */
    Long createNewVersion(CostElement costElement);
}

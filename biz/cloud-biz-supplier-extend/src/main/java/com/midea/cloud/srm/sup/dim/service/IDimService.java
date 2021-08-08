package com.midea.cloud.srm.sup.dim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.dim.entity.Dim;

import java.util.List;

/**
*  <pre>
 *  维度表 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 15:17:35
 *  修改内容:
 * </pre>
*/
public interface IDimService extends IService<Dim> {

    /**
     * 对应维度配置数据只能更新一次
     * @param dimList
     */
    void definitionDim(List<Dim> dimList);

    /**
     * 批量更新维度基础信息
     * @param dimList
     */
    void updateBasic(List<Dim> dimList);

    Dim queryByParam(String dimCode);

}

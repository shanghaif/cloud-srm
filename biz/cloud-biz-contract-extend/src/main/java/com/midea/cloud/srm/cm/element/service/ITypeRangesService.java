package com.midea.cloud.srm.cm.element.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.element.entity.ElemMaintain;
import com.midea.cloud.srm.model.cm.element.entity.TypeRange;

import java.util.List;

/**
*  <pre>
 *  合同要素维护表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-12 16:29:03
 *  修改内容:
 * </pre>
*/
public interface ITypeRangesService extends IService<TypeRange> {
    /**
     * 分页查询
     * @param typeRange
     * @return
     */
    PageInfo<TypeRange> listPage(TypeRange typeRange);

    /**
     * 查询指定合同类型所有有效的要素
     * @param contractType
     * @return
     */
    List<ElemMaintain> queryByValid(String contractType);

}

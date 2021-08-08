package com.midea.cloud.srm.cm.element.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.element.entity.ElemMaintain;

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
 *  修改日期: 2020-08-12 16:29:02
 *  修改内容:
 * </pre>
*/
public interface IElemMaintainsService extends IService<ElemMaintain> {
    /**
     * 新增
     * @param elemMaintain
     * @return
     */
    Long add(ElemMaintain elemMaintain);

    /**
     * 更新
     * @param elemMaintain
     * @return
     */
    Long modify(ElemMaintain elemMaintain);

    /**
     * 获取详情
     * @param elemMaintainId
     * @return
     */
    ElemMaintain get(Long elemMaintainId);

    /**
     * 分页查询
     * @param elemMaintain
     * @return
     */
    PageInfo<ElemMaintain> listPage(ElemMaintain elemMaintain);

    /**
     * 查询左右有效的
     * @return
     */
    List<ElemMaintain> listAll();

}

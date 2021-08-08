package com.midea.cloud.srm.cm.condfactor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.condfactor.entity.CondFactor;

import java.util.List;

/**
*  <pre>
 *  条件因素表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-11 11:15:50
 *  修改内容:
 * </pre>
*/
public interface ICondFactorService extends IService<CondFactor> {

    /**
     * 条件因素分页查询
     * @param condFactor
     * @return
     */
    PageInfo<CondFactor> listPage(CondFactor condFactor);

    /**
     * 批量获取
     * @param idList (多个id用英文逗号隔开)
     * @return
     */
    List<CondFactor> getByIdList(String idList);

    /**
     * 新增
     * @param condFactor
     */
    Long add(CondFactor condFactor);

    /**
     * 查询所有有效的
     * @return
     */
    List<CondFactor> queryAll();
}

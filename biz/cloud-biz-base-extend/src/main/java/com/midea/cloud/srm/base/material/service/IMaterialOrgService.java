package com.midea.cloud.srm.base.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.material.MaterialOrg;

/**
*  <pre>
 *  物料与组织关系表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 11:14:25
 *  修改内容:
 * </pre>
*/
public interface IMaterialOrgService extends IService<MaterialOrg> {

    /**
     * Description 根据条件分页获取物料库存组织列表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.09.12
     * @throws
     **/
    PageInfo<MaterialOrg> findMateriaOrgPageList(MaterialOrg materialOrg);

}

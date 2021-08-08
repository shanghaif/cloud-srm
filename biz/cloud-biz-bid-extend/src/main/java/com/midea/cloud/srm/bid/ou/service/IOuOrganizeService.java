package com.midea.cloud.srm.bid.ou.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bid.ou.entity.OuOrganize;
import com.baomidou.mybatisplus.extension.service.IService;

/**
*  <pre>
 *  ou组基础信息表 服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 13:53:09
 *  修改内容:
 * </pre>
*/
public interface IOuOrganizeService extends IService<OuOrganize> {
    PageInfo<OuOrganize> getOuListPage(OuOrganize ouOrganize);

}

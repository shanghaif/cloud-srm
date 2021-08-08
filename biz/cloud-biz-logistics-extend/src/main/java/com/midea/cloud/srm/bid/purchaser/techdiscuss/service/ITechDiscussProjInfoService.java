package com.midea.cloud.srm.bid.purchaser.techdiscuss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussProjInfo;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.vo.TechDiscussProjInfoVO;

/**
*  <pre>
 *  技术交流项目信息表 服务类
 * </pre>
*
* @author fengdc3@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
*/
public interface ITechDiscussProjInfoService extends IService<TechDiscussProjInfo> {

    void saveOrUpdateProjInfo(TechDiscussProjInfoVO techDiscussProjInfoVO);

    PageInfo<TechDiscussProjInfo> listPage(TechDiscussProjInfo techDiscussProjInfo);

    void publish(Long projId);
}

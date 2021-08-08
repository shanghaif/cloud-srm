package com.midea.cloud.srm.inq.inquiry.service;

import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaBuDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaBu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  配额事业部中间表 服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
*/
public interface IQuotaBuService extends IService<QuotaBu> {
    List<QuotaBu> quotaBuList(QuotaBuDTO quotaBuDTO);
}

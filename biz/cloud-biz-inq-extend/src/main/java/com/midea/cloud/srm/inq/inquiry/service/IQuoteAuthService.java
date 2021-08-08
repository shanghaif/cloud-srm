package com.midea.cloud.srm.inq.inquiry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuoteAuth;

import java.util.List;

/**
*  <pre>
 *   服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-21 15:25:37
 *  修改内容:
 * </pre>
*/
public interface IQuoteAuthService extends IService<QuoteAuth> {
    List<QuoteAuth> getByHeadId(Long headId);
}

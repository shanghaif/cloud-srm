package com.midea.cloud.srm.sup.riskraider.toEs.service;

import com.midea.cloud.srm.model.supplier.riskraider.toEs.dto.RaiderEsDto;

import java.io.IOException;
import java.util.Collection;

/**
*  <pre>
 *  企业财务信息表 服务类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:42:20
 *  修改内容:
 * </pre>
*/
public interface SearchFromEsService {

    Collection<Object> search(RaiderEsDto raiderEsDto) throws IOException;


}

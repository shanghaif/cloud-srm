package com.midea.cloud.srm.sup.riskraider.toEs.service;

import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.toEs.dto.RaiderEsDto;


import java.util.HashMap;

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
public interface RaiderInfoToEsService{

    void saveR1ToEs(RaiderEsDto raiderEsDto) throws Exception;
    void saveR2ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR3ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR4ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR5ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR6ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR7ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR8ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR9ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR10ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR11ToEs(CompanyInfo companyInfo) throws Exception;
    void saveR12ToEs(RaiderEsDto raiderEsDto) throws Exception;
    void saveR13ToEs(RaiderEsDto raiderEsDto) throws Exception;
    void saveR14ToEs(RaiderEsDto raiderEsDto) throws Exception;

    void saveToEs(RaiderEsDto raiderEsDto) throws Exception;
    void addMonitorEnterprise(CompanyInfo companyInfo) throws Exception;
    void cancelMonitorEnterprise(CompanyInfo companyInfo) throws Exception;

    void saveOrUpdateBatch(RaiderEsDto raiderEsDto) throws Exception;

    HashMap<String, JSONObject> queryFromEs(Long companyId) throws InterruptedException;


}

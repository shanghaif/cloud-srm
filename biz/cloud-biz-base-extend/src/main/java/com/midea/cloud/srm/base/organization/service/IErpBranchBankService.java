package com.midea.cloud.srm.base.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.ErpBranchBank;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  银行分行信息（隆基银行分行数据同步） 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-14 12:29:12
 *  修改内容:
 * </pre>
*/
public interface IErpBranchBankService extends IService<ErpBranchBank> {
    List<ErpBranchBank> listAll(List<ErpBranchBank> erpBranchBanks);

    Map<String, String> getUnionCodeByOpeningBanks(List<String> openingBanks);
}

package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;

import java.util.List;

/**
*  <pre>
 *  联系人信息 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 15:47:12
 *  修改内容:
 * </pre>
*/
public interface IBankInfoService extends IService<BankInfo> {
    void saveOrUpdateBank(BankInfo bankInfo, Long companyId);

    void saveOrUpdateBank(BankInfo bankInfo);

    List<BankInfo> getByCompanyId(Long companyId);

    void removeByCompanyId(Long companyId);

    BankInfo getBankInfoByParm(BankInfo bankInfo);

    List<BankInfo> getBankInfosByParam(BankInfo bankInfo);

    PageInfo<BankInfo> listPageByParam(BankInfo bankInfo);
}

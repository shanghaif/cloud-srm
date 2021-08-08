package com.midea.cloud.srm.inq.inquiry.service;

import com.midea.cloud.srm.model.inq.inquiry.entity.AgreementRatio;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
*  <pre>
 *  配额-协议比例 服务类
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
public interface IAgreementRatioService extends IService<AgreementRatio> {
    List<AgreementRatio> agreementRatioList(AgreementRatio agreementRatio);
    void agreementRatioAdd(List<AgreementRatio> agreementRatioList);

}

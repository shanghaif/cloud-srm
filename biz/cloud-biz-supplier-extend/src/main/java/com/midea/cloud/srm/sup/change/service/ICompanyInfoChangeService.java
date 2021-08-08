package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeRequestDTO;
import com.midea.cloud.srm.model.supplier.change.entity.CompanyInfoChange;

/**
*  <pre>
 *  公司基本信息 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-11 11:01:27
 *  修改内容:
 * </pre>
*/
public interface ICompanyInfoChangeService extends IService<CompanyInfoChange> {





    CompanyInfoChange addChange(CompanyInfoChange companyInfoChange);

    CompanyInfoChange modifyChange(CompanyInfoChange companyInfoChange);



    CompanyInfoChange getByChangeId(Long changeId);


    CompanyInfoChange saveOrUpdateCompany(CompanyInfoChange companyInfoChange,Long companyId,Long changeId);

    void removeByChangeId(Long changeId);
}

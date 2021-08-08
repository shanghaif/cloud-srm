package com.midea.cloud.srm.base.dept.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.dept.dto.CompaniesDto;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dept.entity.Dept;

import java.util.List;

/**
*  <pre>
 *  部门表(隆基部门同步) 服务类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-01 14:07:03
 *  修改内容:
 * </pre>
*/
public interface IDeptService extends IService<Dept> {
    /**
     * 查询公司的结构树
     * @param srmCompanyId 公司ID
     * @return
     */
    List<Dept> deptTree(String srmCompanyId,Long orgId);

    /**
     * 查找
     * @param deptDto
     * @return
     */
    List<DeptDto> queryDeptByCompany(DeptDto deptDto);

    /**
     * 查找公司信息
     * @return
     */
    List<CompaniesDto> listAllByCompany();
}

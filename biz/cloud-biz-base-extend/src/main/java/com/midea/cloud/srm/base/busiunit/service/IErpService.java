package com.midea.cloud.srm.base.busiunit.service;

import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.base.organization.entity.*;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EmployeeEntity;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;

import java.util.List;

/**
 * <pre>
 *  Erp总线service接口
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/11 19:42
 *  修改内容:
 * </pre>
 */
public interface IErpService {

    //SRM物料类别
    static final String CATEGORY_SET_CODE       =   "1100000101";
    static final String CATEGORY_SET_NAME_ZHS   =   "SRM物料类别";

    static final String CATEGORY_SET_VALUE_DEFAULT  = "00";
    static final String CATEGORY_VALUE_DES_ZHS      =   "默认";

    //库存
    static final String INVENTORY_SET_CODE      =   "1";
    static final String INVENTORY_SET_NAME_ZHS  =   "库存";

    //未导入物料基础表
    static final Integer IMPORT_DEFAULT_STATUS  =   0;
    //导入物料基础表成功
    static final Integer IMPORT_SUCCESS_STATUS  =   1;
    //导入物料基础表失败
    static final Integer IMPORT_FAIL_STATUS  =   -1;

    /**
     * Description 新增或修改库存组织表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.11
     * @throws
     **/
    SoapResponse saveOrUpdateInvOrganiztions(InvOrganization invOrganization, String instId, String requestTime);

    /**
     * Description 新增或修改库存组织表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.11
     * @throws
     **/
    SoapResponse saveOrUpdateInvOrganiztions(List<InvOrganization> invOrganizationList, String instId, String requestTime,
                                             List<Organization> orgList, List<Organization> unitOrgList);

    /**
     * Description 新增/修改地址接口表
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.12
     * @throws
     **/
    SoapResponse saveOrUpdateLocations(List<Location> locationsList, String instId, String requestTime);

    /**
     * Description 新增/修改部门接口表
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.08.15
     * @throws
     **/
    SoapResponse saveOrUpdateDepts(List<Dept> deptList, String instId, String requestTime);

    /**
     * Description 新增/修改职位接口表
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.08.17
     * @throws
     **/
    SoapResponse saveOrUpdatePositions(List<Position> positionsList, String instId, String requestTime);

    /**
     * Description 新增/修改员工接口表
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.08.18
     * @throws
     **/
    SoapResponse saveOrUpdateEmployees(List<Employee> employeesList, String instId, String requestTime);

    /**
     * Description 新增/修改物料接口表
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.08.20
     * @throws
     **/
    SoapResponse saveOrUpdateMaterialItems(List<ErpMaterialItem> erpMaterialItemsList, String instId, String requestTime);
    /**
     * Description 新增/修改汇率接口表
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.08.25
     * @throws
     **/
    SoapResponse saveOrUpdateGidailyRates(List<GidailyRate> erpMaterialItemsList, String instId, String requestTime);

    /**
     * Description 新增/修改币种接口表
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.08.25
     * @throws
     **/
    SoapResponse saveOrUpdateCurrencys(List<ErpCurrency> erpMaterialItemsList, String instId, String requestTime);

    /**
     * Description 新增/修改税率接口表（从隆基Erp拉取税率数据）
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.09.13
     * @throws
     **/
    SoapResponse saveOrUpdatePurchaseTaxs(List<ErpPurchaseTax> erpPurchaseTaxsList, String instId, String requestTime);

    /**
     * Description 新增/修改银行分行接口表（从隆基Erp拉取银行分行数据）
     * @Param
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2020.09.13
     * @throws
     **/
    SoapResponse saveOrUpdateBranchBanks(List<ErpBranchBank> erpErpBranchBanksList, String instId, String requestTime);

    /**
     * Description 新增/修改员工接口表new
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.10.28
     * @throws
     **/
//    SoapResponse saveOrUpdateEmployeesNew(List<EmployeeEntity> employeesEntityList, String instId, String requestTime);
}


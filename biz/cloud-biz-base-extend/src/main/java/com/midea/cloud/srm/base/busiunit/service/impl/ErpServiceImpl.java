package com.midea.cloud.srm.base.busiunit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.RbacConst;
import com.midea.cloud.common.enums.soap.InterfaceResourceEnum;
import com.midea.cloud.common.enums.soap.InterfaceStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.dept.service.IDeptService;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.base.material.service.IMaterialOrgService;
import com.midea.cloud.srm.base.organization.service.*;
import com.midea.cloud.srm.base.purchase.service.*;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import com.midea.cloud.srm.model.base.material.dto.MaterialOrgOrganizationDTO;
import com.midea.cloud.srm.model.base.organization.entity.*;
import com.midea.cloud.srm.model.base.purchase.entity.*;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * ERP接口实现Service
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/21 19:25
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class ErpServiceImpl implements IErpService {

    /**
     * erp业务组织表Service
     */
    @Resource
    private IBusinessUnitsService iBusinessUnitsService;

    /**
     * erp库存组织表Service
     */
    @Resource
    private IInvOrganizationsService iInvOrganizationsService;

    /**
     * erp地址接口表Service
     */
    @Resource
    private ILocationService iLocationService;

    /**
     * erp部门接口表Service
     */
    @Resource
    private IDeptService iDeptService;

    /**
     * erp职位接口表Service
     */
    @Resource
    private IPositionService iPositionService;

    /**
     * erp员工接口表Service
     */
    @Resource
    private IEmployeeService iEmployeeService;

    /**
     * erp人员接口表Service
     */
    @Autowired
    private RbacClient rbacClient;

    /**
     * erp物料接口表Service
     */
    @Resource
    private IErpMaterialItemService iErpMaterialItemService;

    /**
     * erp物料类别接口Service
     */
    @Resource
    private ICategoryService iCategoryService;

    /**
     * srm物料接口Service
     */
    @Resource
    private IMaterialItemService iMaterialItemService;

    /**
     * erp汇率接口Service
     */
    @Resource
    private IGidailyRateService iGidailyRateService;

    /**
     * erp汇率接口Service
     */
    @Resource
    private IErpCurrencyService iErpCurrencyService;

    /**
     * srm品类接口Service
     */
    @Resource
    private IPurchaseCategoryService iPurchaseCategoryService;

    /**
     * srm最新汇率接口Service
     */
    @Resource
    private ILatestGidailyRateService iLatestGidailyRateService;

    /**
     * srm币种接口Service
     */
    @Resource
    private IPurchaseCurrencyService iPurchaseCurrencyService;

    /**
     * erp税率接口Service
     */
    @Resource
    private IErpPurchaseTaxService iErpPurchaseTaxService;

    /**
     * srm税率接口Service
     */
    @Resource
    private IPurchaseTaxService iPurchaseTaxService;


    /***/
    @Resource
    IOrganizationService iOrganizationService;

    /**
     * erp银行分行信息接口Service
     */
    @Resource
    private IErpBranchBankService iErpBranchBankService;

    /**
     * srm单位接口Service
     */
    @Resource
    private IPurchaseUnitService iPurchaseUnitService;

    /**
     * srm物料组织关系Service
     */
    @Resource
    private IMaterialOrgService iMaterialOrgService;


    /**
     * 保存或更新库存组织
     *
     * @param invOrganization
     * @param instId
     * @param requestTime
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse saveOrUpdateInvOrganiztions(InvOrganization invOrganization, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (null == invOrganization) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                boolean isSaveOrUpdate = false;
                log.info("新增或修改库存组织数据: " + JsonUtil.entityToJsonStr(invOrganization));
                if (null != invOrganization) {
                    isSaveOrUpdate = iInvOrganizationsService.saveOrUpdate(invOrganization);
                    if (!isSaveOrUpdate) {
                        returnStatus = "E";
                        resultMsg = "新增或修改库存组织表时报错";
                        log.error("新增或修改库存组织表时报错");
                    } else {
                        returnStatus = "S";
                        resultMsg = "成功插入表.";
                    }
                } else {
                    returnStatus = "S";
                    resultMsg = "插入表数据为空,无需插入.";
                }
            } catch (Exception e) {
                log.error("保存erp库存组织表时报错：", e);
                returnStatus = "E";
                resultMsg = "保存erp库存组织表时报错";
            }

        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse saveOrUpdateInvOrganiztions(List<InvOrganization> invOrganizationList, String instId, String requestTime,
                                                    List<Organization> orgList, List<Organization> unitOrgList) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (null == invOrganizationList) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                boolean isSaveOrUpdate = false;
                log.info("新增或修改库存组织数据: " + JsonUtil.entityToJsonStr(invOrganizationList));
                if (null != invOrganizationList) {
                    isSaveOrUpdate = iInvOrganizationsService.saveOrUpdateBatch(invOrganizationList);
                    if (!isSaveOrUpdate) {
                        returnStatus = "E";
                        resultMsg = "新增或修改库存组织表时报错";
                        log.error("新增或修改库存组织表时报错");
                    } else {
                        /**后续再考虑更新erp库存组时再更新srm的组织设置表*/
                        try {
                            iOrganizationService.saveOrUpdateOrganizationForErp(orgList); //保存库存组织基础数据
                        } catch (Exception e) {
                            returnStatus = "E";
                            resultMsg = "新增或修改库存组织表时保存库存组织基础数据报错";
                            log.error("新增或修改库存组织表时保存库存组织基础数据报错", e);
                        }
                        returnStatus = "S";
                        resultMsg = "成功插入表.";
                    }
                } else {
                    returnStatus = "S";
                    resultMsg = "插入表数据为空,无需插入.";
                }
            } catch (Exception e) {
                log.error("保存erp库存组织表时报错：", e);
                returnStatus = "E";
                resultMsg = "保存erp库存组织表时报错";
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    /**
     * 保存或更新地点信息Location
     *
     * @param locationsList
     * @param instId
     * @param requestTime
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse saveOrUpdateLocations(List<Location> locationsList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (CollectionUtils.isEmpty(locationsList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                for (Location location : locationsList) {
                    if (null != location) {
                        /**根据地址ID查询记录，如果有记录则修改，反之则新增*/
                        Long id = IdGenrator.generate();
                        Long locationId = location.getLocationId();
                        String status = InterfaceStatusEnum.NEW.getName();
                        if (null != locationId) {
                            Location queryLocation = new Location();
                            queryLocation.setLocationId(locationId);
                            List<Location> queryLocationList = iLocationService.list(new QueryWrapper<>(queryLocation));
                            if (CollectionUtils.isNotEmpty(queryLocationList) && null != queryLocationList.get(0)
                                    && null != queryLocationList.get(0).getLocationId()) {
                                id = queryLocationList.get(0).getId();
                                status = InterfaceStatusEnum.UPDATE.getName();
                            }
                        }
                        location.setId(id);
                        location.setItfStatus(status);
                        if (status.equals("NEW")) {
                            location.setCreationDate(nowDate);
                        }
                        location.setLastUpdateDate(nowDate);
                    }
                }

                log.info("新增或修改地址接口数据: " + JsonUtil.entityToJsonStr(locationsList));
                boolean isSaveOrUpdate = iLocationService.saveOrUpdateBatch(locationsList);
                if (isSaveOrUpdate) {
                    returnStatus = "S";
                    resultMsg = "成功插入地址接口表.";
                } else {
                    returnStatus = "E";
                    resultMsg = "新增或修改地址接口表时报错";
                    log.error("新增或修改地址接口表时报错");
                }

            } catch (Exception e) {
                returnStatus = "E";
                resultMsg = "新增或修改erp地址接口表时报错";
                log.error("新增或修改erp地址接口表时报错：", e);
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    /**
     * 保存或更新部门信息Dept
     *
     * @param deptsList
     * @param instId
     * @param requestTime
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse saveOrUpdateDepts(List<Dept> deptsList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        int numForNonDeptId = 0;
        int numForRepeat = 0;
        if (CollectionUtils.isEmpty(deptsList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                String err = "";
                //过滤掉DeptId为空的部门数据
                List<Dept> collect = deptsList.stream().filter(x -> !ObjectUtils.isEmpty(x.getDeptid())).collect(Collectors.toList());
                //deptId为空的部门数据数
                numForNonDeptId = deptsList.size() - collect.size();
                //对传入的部门集根据deptId去重
                List<Dept> deptList = collect.stream().distinct().collect(Collectors.toList());
                numForRepeat = collect.size() - deptList.size();

                //查询数据库所有数据并根据deptId去重
                List<Dept> depts = iDeptService.list().stream().distinct().collect(Collectors.toList());
                Map<String, Dept> map = depts.stream().collect(Collectors.toMap(Dept::getDeptid, Function.identity()));
                List<Dept> saveDeptList = new ArrayList<>();
                List<Dept> updateDeptList = new ArrayList<>();
                for (Dept dept : deptList) {
                    if (Objects.nonNull(dept)) {
                        /**根据部门Id查询记录，如果有记录则修改，反之则新增*/
                        Long id = null;
                        String deptId = dept.getDeptid();
                        String status = InterfaceStatusEnum.NEW.getName();
                        //数据库存在这个部门
                        boolean dbHasDept = (map.containsKey(deptId)) && (Objects.nonNull(map.get(deptId)));
                        //数据库存在这个部门 更新操作
                        if (dbHasDept) {
                            id = map.get(deptId).getId();
                            status = InterfaceStatusEnum.UPDATE.getName();
                            dept.setId(id).setItfStatus(status);
                            updateDeptList.add(dept);
                        } else {
                            id = IdGenrator.generate();
                            dept.setId(id).setItfStatus(status);
                            saveDeptList.add(dept);
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(saveDeptList)) {
                    try {
                        iDeptService.saveBatch(saveDeptList);
                    } catch (Exception e) {
                        log.error("操作失败",e);
                        e.printStackTrace();
                        err = "新增部门数据出错";
                    }
                }
                if (CollectionUtils.isNotEmpty(updateDeptList)) {
                    try {
                        iDeptService.updateBatchById(updateDeptList);
                    } catch (Exception e) {
                        log.error("操作失败",e);
                        e.printStackTrace();
                        err = "修改部门数据出错";
                    }
                }

                if (StringUtils.isEmpty(err)) {
                    returnStatus = "S";
                    resultMsg = "成功插入部门接口表.成功插入：" + saveDeptList.size() + "条，更新：" + updateDeptList.size() + "条。";
                    if (numForNonDeptId > 0) {
                        resultMsg += "已忽略deptId为空的数据：" + numForNonDeptId + "条";
                    }
                    if (numForRepeat > 0) {
                        resultMsg += "已忽略deptId重复的数据：" + numForRepeat + "条";
                    }
                } else {
                    returnStatus = "E";
                    resultMsg = err;
                    log.error(err);
                }

            } catch (Exception e) {
                returnStatus = "E";
                resultMsg = "新增或修改部门数据时报错";
                log.error("新增或修改erp部门接口表时报错：", e);
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    /**
     * 保存或更新职位信息Position
     *
     * @param positionsList
     * @param instId
     * @param requestTime
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse saveOrUpdatePositions(List<Position> positionsList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        int numForNonPositionNbr = 0;
        int numForRepeat = 0;
        if (CollectionUtils.isEmpty(positionsList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                String err = "";
                //过滤掉positionNbr为空的职位数据
                List<Position> collect = positionsList.stream().filter(x -> !ObjectUtils.isEmpty(x.getPositionNbr())).collect(Collectors.toList());
                //positionNbr为空的职位数据数
                numForNonPositionNbr = positionsList.size() - collect.size();
                //对传入的职位集根据positionNbr去重
                List<Position> positionList = collect.stream().distinct().collect(Collectors.toList());
                numForRepeat = collect.size() - positionList.size();

                //查询数据库所有的数据并根据positionNbr去重
                List<Position> positions = iPositionService.list().stream().distinct().collect(Collectors.toList());
                Map<String, Position> map = positions.stream().collect(Collectors.toMap(Position::getPositionNbr, Function.identity()));

                List<Position> savePositionList = new ArrayList<>();
                List<Position> updatePositionList = new ArrayList<>();

                for (Position position : positionList) {
                    if (Objects.nonNull(position)) {
                        /** 根据positionNbr查询记录，如果有记录则修改，反之则新增 **/
                        Long id = null;
                        String positionNbr = position.getPositionNbr();
                        String status = InterfaceStatusEnum.NEW.getName();
                        //数据库存在这个职位
                        boolean dbHasPosition = (map.containsKey(positionNbr)) && (Objects.nonNull(map.get(positionNbr)));
                        //数据库存在这个职位 更新操作
                        if (dbHasPosition) {
                            id = map.get(positionNbr).getId();
                            status = InterfaceStatusEnum.UPDATE.getName();
                            position.setId(id).setItfStatus(status);
                            updatePositionList.add(position);
                        }
                        //新增操作
                        else {
                            id = IdGenrator.generate();
                            position.setId(id).setItfStatus(status);
                            savePositionList.add(position);
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(savePositionList)) {
                    try {
                        iPositionService.saveBatch(savePositionList);
                    } catch (Exception e) {
                        log.error("操作失败",e);
                        e.printStackTrace();
                        err = "新增职位数据出错";
                    }
                }
                if (CollectionUtils.isNotEmpty(updatePositionList)) {
                    try {
                        iPositionService.updateBatchById(updatePositionList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        err = "修改职位数据出错";
                    }
                }

                if (StringUtils.isEmpty(err)) {
                    returnStatus = "S";
                    resultMsg = "职位数据接收成功。成功插入：" + savePositionList.size() + "条，更新：" + updatePositionList.size() + "条。";
                    if (numForNonPositionNbr > 0) {
                        resultMsg += "已忽略positionNbr为空的数据：" + numForNonPositionNbr + "条";
                    }
                    if (numForRepeat > 0) {
                        resultMsg += "已忽略positionNbr重复的数据：" + numForRepeat + "条";
                    }
                } else {
                    returnStatus = "E";
                    resultMsg = err;
                    log.error(err);
                }
            } catch (Exception e) {
                returnStatus = "E";
                resultMsg = "新增或修改职位数据时报错";
                log.error("新增或修改erp职位接口表时报错：", e);
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }




    /**
     * 保存或更新人员信息数据
     *
     * @param employeesList
     * @param instId
     * @param requestTime
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse saveOrUpdateEmployees(List<Employee> employeesList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (CollectionUtils.isEmpty(employeesList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                /** 复制一份传入的员工List **/
                List<Employee> usersList = employeesList;
                String errorMsg = "";

                //保存员工信息
                errorMsg = saveOrUpdateErpEmployees(employeesList, nowDate, errorMsg);

                //保存用户信息
                errorMsg = saveOrUpdateSrmUsers(nowDate, employeesList, errorMsg);

                if (StringUtils.isEmpty(errorMsg)) {
                    returnStatus = "S";
                    resultMsg = "成功插入srm用户表.";
                } else {
                    returnStatus = "E";
                    resultMsg = errorMsg;
                    log.error(errorMsg);
                }
            } catch (Exception e) {
                log.error("接收员工信息,新增/修改员工或用户表时失败: ",e);
                returnStatus = "E";
                resultMsg = "新增/修改srm员工表或用户时报错";
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    /**
     * 保存或更新员工数据到Srm的User表
     *
     * @param nowDate
     * @param rcdUsersList 主岗员工
     * @param err
     * @return
     */
    public String saveOrUpdateSrmUsers(Date nowDate, List<Employee> rcdUsersList, String err) {
        /** insert employee date into SRM rbac-user */
        List<User> queryUserList = rbacClient.listUsers(new User());    //查询所有的人员信息
        List<User> saveUserList = new ArrayList<>();
        List<User> updateUserList = new ArrayList<>();
        Map<String, User> userMap = new HashMap<>();
        rcdUsersList.forEach(employee ->{

            /**主岗且有账号和员工号的记录需要保存到人员表中，已账号为主，如果账号为空则使用员工号为账号*/
            if(null != employee && "0".equals(employee.getEmplRcd()) && (StringUtils.isNotBlank(employee.getOprid()) || null != employee.getEmplid() )){
                //如果没用账号则使用员工号
                String oprid = employee.getOprid() ;
                boolean isSave = true;
                Long userId = IdGenrator.generate();
                Date createDate = nowDate;
                /**判断是否是更新用户信息，是userId为旧记录，否则为新增记录*/
                if(StringUtils.isNotBlank(oprid)){ //账号不为空
                    if( CollectionUtils.isNotEmpty(queryUserList)) {
                        for (User user : queryUserList) {
                            if (null != user && oprid.equals(user.getUsername())) {
                                userId = user.getUserId();
                                createDate = user.getCreationDate();
                                isSave = false;
                                break;
                            }
                        }
                    }
                }if(StringUtils.isBlank(oprid)) { //账号为空则使用员工号为账号
                    if( CollectionUtils.isNotEmpty(queryUserList)) {
                        for (User user : queryUserList) {
                            if (null != user && null != employee.getEmplid() && String.valueOf(employee.getEmplid()).equals(user.getCeeaEmpNo())) {
                                userId = user.getUserId();
                                createDate = user.getCreationDate();
                                isSave = false;
                                break;
                            }
                        }
                    }
                    employee.setOprid(String.valueOf(employee.getEmplid()));
                }

                User updateUser = settingSRMUserInfo(nowDate , employee, isSave);
                updateUser.setUserId(userId);
                updateUser.setCreationDate(createDate);
                userMap.put(oprid, updateUser);
            }else{
                log.info("员工接收时,没有账号/员工号且不是主岗的用户信息: "+ JsonUtil.entityToJsonStr(employee));
            }
        });
        if(null != userMap){
            for(Map.Entry<String, User> userEntry : userMap.entrySet()){
                if(null != userEntry && null != userEntry.getValue()){
                    updateUserList.add(userEntry.getValue());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(updateUserList)) {
            log.info("新增/修改用户数据: " + updateUserList.size() + "条记录");
            try {
                rbacClient.saveOrUpdateBatchUsers(updateUserList);
            } catch (Exception e) {
                log.error("接收员工信息,新增/修改用户数据失败: ",e);
                err = "新增/修改用户数据失败";
            }
        }

        /** query all the user data */
//        User listUser = new User();
//        Set<String> userNameSet = new HashSet<>();
//        Map<String, User> userMap = rbacClient.listUsers(listUser).stream().collect(Collectors.toMap(
//                u -> u.getUsername(),
//                u -> u,
//                (o, o2) -> o2
//        ));
//        for (Employee employee : rcdUsersList) {
//            if(null == employee){
//                continue;
//            }
//            if(StringUtils.isBlank(employee.getOprid())){
//                continue;
//            }
//            if(!userNameSet.add(employee.getOprid())){
//                log.info("当前导入批次employee信息-{}-已经存在，不重复导入",employee.getOprid());
//                continue;
//            }
//            String oprid = employee.getOprid();     //userName账号
//            User dbRecordUser = userMap.get(oprid);
//            if (Objects.nonNull(dbRecordUser)) {
//                User updateUser = settingSRMUserInfo(nowDate , employee, false);
//                updateUser.setUserId(dbRecordUser.getUserId());
//                updateUserList.add(updateUser);
//            } else {
//                User saveUser = settingSRMUserInfo(nowDate , employee , true);
//                saveUser.setUserId(IdGenrator.generate());
//                saveUserList.add(saveUser);
//            }
//        }
//
//        if (CollectionUtils.isNotEmpty(saveUserList)) {
//            log.info("新增用户数据: " + saveUserList.size() + "条记录");
//            try {
//                rbacClient.saveBatchUsers(saveUserList);
//            } catch (Exception e) {
//                log.error("操作失败",e);
//                err = "新增用户数据失败";
//            }
//        }
//        if (CollectionUtils.isNotEmpty(updateUserList)) {
//            log.info("修改用户数据: " + updateUserList.size() + "条记录");
//            try {
//                rbacClient.updateBatchUsers(updateUserList);
//            } catch (Exception e) {
//                log.error("操作失败",e);
//                err = "修改用户数据失败";
//            }
//        }
        return err;
    }

    /**
     * isSave 是否新建，true为新建**/
    private User settingSRMUserInfo(Date nowDate, Employee employee, boolean isSave) {
        User updateUser = new User();
        updateUser.setCeeaEmpNo(String.valueOf(employee.getEmplid()));
        updateUser.setUsername(employee.getOprid());
        updateUser.setPassword(RbacConst.SAVE_USER_PASSWORD);
        updateUser.setNickname(employee.getName());//隆基 姓名 --> 昵称
        updateUser.setPhone(employee.getMobilePhone());//隆基 移动电话 --> 手机

        //企业邮箱不为空，则保存企业邮箱，否则保存个人邮箱
        if(StringUtils.isNotBlank(employee.getEmailAddr())) {
            updateUser.setEmail(employee.getEmailAddr());//隆基 企业邮箱 --> 邮箱
        }else{
            updateUser.setEmail(employee.getEmailAddr2());//隆基 个人邮箱
        }

        updateUser.setDepartment(employee.getDeptDescr());//隆基 部门 --> 部门
        updateUser.setUserType("BUYER");//用户类型 -- 设置为采购商
        updateUser.setMainType("N");//账号类型 -- 设置为子账号
        if(isSave) { //只有新增的时候插入，修改的时候不用修改为当前时间
            updateUser.setStartDate(LocalDate.now());//生效日期 设置为当前日期
        }
        updateUser.setIsConfirm("N");//是否阅读协议 设置为N
        updateUser.setCeeaCompany(employee.getCompany());//隆基 公司 --> 公司（隆基新增）
        updateUser.setCeeaCompanyDescr(employee.getCompanyDescr()); //隆基 公司描述 --> 公司描述（隆基新增）
        updateUser.setCeeaDeptId(employee.getDeptid());//隆基 部门编码 --> 部门编码
        updateUser.setCeeaJobcode(employee.getJobcode());//隆基 岗位代码
        updateUser.setCeeaJobcodeDescr(employee.getJobcodeDescr());
        updateUser.setLastUpdateDate(nowDate);
        return updateUser;
    }
    /**
     * 将员工数据保存到Erp基础数据表ceea_base_employee
     *
     * @param employeesList
     * @param nowDate
     * @param err
     * @return
     */
    public String saveOrUpdateErpEmployees(List<Employee> employeesList, Date nowDate, String err) {
        //查询全量人员数据（employee）
        List<Employee> queryEmployeeList = iEmployeeService.list();
/*        Map<Long, Employee> employeeMap = new HashMap<>();
        for (Employee employee : queryEmployeeList) {
            employeeMap.put(employee.getEmplid(), employee);
        }*/
//        List<Employee> saveEmployeeList = new ArrayList<>();
        Date createDate = nowDate;
        List<Employee> updateEmployeeList = new ArrayList<>();
        for (Employee employee : employeesList) {
            if (null != employee) {
                /**根据员工ID（empId）查询记录，如果有记录则修改，反之则新增*/
                Long id = IdGenrator.generate();
                /**获取员工的empId*/
//                Long employeeId = employee.getEmplid();
                String oprid = employee.getOprid(); //账号
                /**init status as NEW*/
                String status = InterfaceStatusEnum.NEW.getName();
                if (StringUtils.isNotBlank(oprid)) {
                    if (CollectionUtils.isNotEmpty(queryEmployeeList)) {
                        for (Employee emp : queryEmployeeList) {
                            if (null != emp && oprid.equals(emp.getOprid())) {   //账号相同的更新
                                status = InterfaceStatusEnum.UPDATE.getName();
                                createDate = emp.getCreationDate();
                                id = emp.getId();
                                break;
                            }
                        }
                    }
//                    employee.setId(id);
//                    employee.setItfStatus(status);
//                    employee.setCreationDate(nowDate);
//                    employee.setLastUpdateDate(nowDate);
//                    updateEmployeeList.add(employee);
                }

                employee.setId(id);
                employee.setItfStatus(status);
                employee.setCreationDate(createDate);
                employee.setLastUpdateDate(nowDate);
                updateEmployeeList.add(employee);
            }

        }
//        if (CollectionUtils.isNotEmpty(saveEmployeeList)) {
//            log.info("新增员工数据: " + saveEmployeeList.size() + "条记录");
//            /** if is saved or updated to Erp employee table */
//            try {
//                iEmployeeService.saveBatch(saveEmployeeList);
//            } catch (Exception e) {
//                log.error("操作失败",e);
//                err = "新增员工数据失败";
//            }
//
//        }
//        if (CollectionUtils.isNotEmpty(updateEmployeeList)) {
//            log.info("修改员工数据: " + updateEmployeeList.size() + "条记录");
//            try {
//                iEmployeeService.updateBatchById(updateEmployeeList);
//            } catch (Exception e) {
//                log.error("操作失败",e);
//                err = "修改员工数据失败";
//            }
//
//        }
        if (CollectionUtils.isNotEmpty(updateEmployeeList)) {
            log.info("新增/修改员工数据: " + updateEmployeeList.size() + "条记录");
            try {
                iEmployeeService.saveOrUpdateBatch(updateEmployeeList);
            } catch (Exception e) {
                log.error("接收员工信息,新增/修改员工时失败: ",e);
                err = "接收员工信息,新增/修改员工失败";
            }
        }
        return err;
    }

    /**
     * 保存或更新物料数据 materialItem
     * WebService入口
     *
     * @param erpMaterialList
     * @param instId
     * @param requestTime
     * @return
     */
    @Override
    public SoapResponse saveOrUpdateMaterialItems(List<ErpMaterialItem> erpMaterialList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (CollectionUtils.isEmpty(erpMaterialList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                /** 复制MaterialItemWsService接口传的materialList */
                List<ErpMaterialItem> copyMaterialList = erpMaterialList;
                /** 复制MaterialItemWsService接口传的materialList */
                List<ErpMaterialItem> copyMaterialOrgList = erpMaterialList;
                String err = "";
                //保存物料数据到erp全量物料数据表ceea_base_material_item "10-26号导入生产物料，先不接收物料";
                err = saveOrUpdateErpMaterialItem(erpMaterialList, nowDate); //先注释，然后为错误


                //根据物料编码去重
//               copyMaterialList = materialItemCodeDupilicateRemove(copyMaterialList);

                //保存物料数据到srm基础数据表scc_base_material_item
//                err = saveOrUpdateSrmMaterialItem(copyMaterialList, err);

                //维护到库存物料关系表
//                saveOrUpdateSrmMaterialOrg(copyMaterialOrgList, nowDate, err);

                if (StringUtils.isEmpty(err)) {
                    returnStatus = "S";
                    resultMsg = "接收物料成功";
                } else {
                    returnStatus = "E";
                    resultMsg = err;
                    log.error(err);
                }
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                returnStatus = "E";
                resultMsg = "新增或修改srm物料表时报错" + e.getMessage();
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    public List<ErpMaterialItem> materialItemCodeDupilicateRemove(List<ErpMaterialItem> materialItems) {
        List<ErpMaterialItem> erpMaterialItemList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(materialItems)) {
            Map<String, ErpMaterialItem> map = new HashMap<>();
            for (ErpMaterialItem materialItem : materialItems) {
                if (!map.containsKey(materialItem.getItemNumber())) {
                    map.put(materialItem.getItemNumber(), materialItem);
                }
            }
            if (null != map) {
                for (Map.Entry<String, ErpMaterialItem> mapEntiry : map.entrySet()) {
                    if (null != mapEntiry) {
                        erpMaterialItemList.add(mapEntiry.getValue());
                    }
                }
            }

        }
        return erpMaterialItemList;
    }

    /**
     * 保存或更新物料数据到Srm的基础物料表scc_base_material_item
     *
     * @param copyMaterialList
     * @param err
     * @return
     */
    public String saveOrUpdateSrmMaterialItem(List<ErpMaterialItem> copyMaterialList, String err) {
        /** 把物料接口表的物料信息插入到 Srm的material表 */
        /** 同时维护物料所属的品类和单位 到purchase_category表和purchase_unit表 **/
        List<MaterialItem> querySrmMaterialList = iMaterialItemService.list();
        List<MaterialItem> saveSrmMaterialList = new ArrayList<>();
        List<MaterialItem> updateSrmMaterialList = new ArrayList<>();
        Map<String, MaterialItem> materialItemMap = new HashMap<>();
        QueryWrapper<PurchaseCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("CATEGORY_CODE");

        //List<PurchaseCategory> PurchaseCategoryList = iPurchaseCategoryService.list(queryWrapper);

        //List<PurchaseCategory> list = iPurchaseCategoryService.list();

        //Map<String, PurchaseCategory> purchaseCategoryMap = PurchaseCategoryList.stream().filter(purchaseCategory -> StringUtils.isNotEmpty(purchaseCategory.getCategoryCode())).collect(Collectors.toMap(PurchaseCategory::getCategoryCode, Function.identity()));

        //构建Map
        for (MaterialItem materialItem : querySrmMaterialList) {
            materialItemMap.put(materialItem.getMaterialCode(), materialItem);
        }
        /** for copyMaterialList 循环开始 --------------------------------------------------------------------------------------------------------------------------------------------------------**/
        for (ErpMaterialItem erpMaterialItem : copyMaterialList) {
            MaterialItem saveMaterialItem = new MaterialItem();
            MaterialItem updateMaterialItem = new MaterialItem();

            Long materialId = IdGenrator.generate();
            String materialCode = erpMaterialItem.getItemNumber();
            //初始化物料来源状态 为 NEW
            String status = InterfaceStatusEnum.NEW.getName();

            List<Category> erpCategoryList = erpMaterialItem.getCategoryList();

            //使用HashMa的Key来判断是新增还是更新
            Assert.notNull(materialCode, "传入的物料编码为空！");
            //判断是新增还是更新，非首次推送数据
            if (CollectionUtils.isNotEmpty(querySrmMaterialList)) {
                //开始 更新处理
                if (materialItemMap.containsKey(materialCode)) {
                    materialId = materialItemMap.get(materialCode).getMaterialId();
                    updateMaterialItem.setMaterialId(materialId);
                    updateMaterialItem.setMaterialCode(materialCode);
                    updateMaterialItem.setMaterialName(erpMaterialItem.getItemDescZhs());
                    if (erpMaterialItem.getItemStatus().equals("Active")) {
                        updateMaterialItem.setStatus("Y");
                    } else {
                        updateMaterialItem.setStatus("N");
                    }
                    updateMaterialItem.setUnit(erpMaterialItem.getPrimaryUnitOfMeasure());
                    updateMaterialItem.setUnitName(erpMaterialItem.getPrimaryUnitOfMeasure());
                    //将物料接口里的单位维护到单位表里
                    saveOrUpdatePurchaseUnit(erpMaterialItem.getPrimaryUnitOfMeasure());

                    /**开始 如果传入的物料有物料分类，则保存物料分类（物料小类），否则置空 **/
                    boolean hasCategory = ifHasCategory(erpCategoryList);
                    if (hasCategory) {
                        updateMaterialItem = setMaterialPurchaseCategory(updateMaterialItem, erpCategoryList);
                    }
                    /**结束 如果传入的物料有物料分类，则保存物料分类（物料小类），否则置空 **/

                    /**开始 如果传入的物料有库存分类，解析保存库存分类，否则置空 **/
                    boolean hasInventory = ifHasInventory(erpCategoryList);
                    if (hasInventory) {
                        updateMaterialItem = setMaterialInventory(updateMaterialItem, erpCategoryList);
                    }
                    /**结束 如果传入的物料有库存分类，解析保存库存分类，否则置空 **/

                    materialItemMap.replace(materialCode, updateMaterialItem);
                    updateSrmMaterialList.add(updateMaterialItem);
                }
                //结束 更新处理
                //开始 新增处理
                else {
                    saveMaterialItem.setMaterialId(materialId);
                    saveMaterialItem.setMaterialCode(materialCode);
                    saveMaterialItem.setMaterialName(erpMaterialItem.getItemDescZhs());
                    if (erpMaterialItem.getItemStatus().equals("Active")) {
                        saveMaterialItem.setStatus("Y");
                    } else {
                        saveMaterialItem.setStatus("N");
                    }
                    saveMaterialItem.setUnit(erpMaterialItem.getPrimaryUnitOfMeasure());
                    saveMaterialItem.setUnitName(erpMaterialItem.getPrimaryUnitOfMeasure());
                    saveOrUpdatePurchaseUnit(erpMaterialItem.getPrimaryUnitOfMeasure());

                    /**开始 如果传入的物料有物料分类，则保存物料分类（物料小类），否则置空 **/
                    boolean hasCategory = ifHasCategory(erpCategoryList);
                    if (hasCategory) {
                        saveMaterialItem = setMaterialPurchaseCategory(saveMaterialItem, erpCategoryList);
                    }
                    /**结束 如果传入的物料有物料分类，则保存物料分类（物料小类），否则置空 **/

                    /**开始 如果传入的物料有库存分类，解析保存库存分类，否则置空 **/
                    boolean hasInventory = ifHasInventory(erpCategoryList);
                    if (hasInventory) {
                        saveMaterialItem = setMaterialInventory(saveMaterialItem, erpCategoryList);
                    }
                    /**结束 如果传入的物料有库存分类，解析保存库存分类，否则置空 **/

                    materialItemMap.put(materialCode, saveMaterialItem);
                    saveSrmMaterialList.add(saveMaterialItem);
                }
                //结束 新增处理
            }
            //首次拉数据 开始
            else {
                saveMaterialItem.setMaterialId(materialId);
                saveMaterialItem.setMaterialCode(materialCode);
                saveMaterialItem.setMaterialName(erpMaterialItem.getItemDescZhs());
                if (erpMaterialItem.getItemStatus().equals("Active")) {
                    saveMaterialItem.setStatus("Y");
                } else {
                    saveMaterialItem.setStatus("N");
                }
                saveMaterialItem.setUnit(erpMaterialItem.getPrimaryUnitOfMeasure());
                saveMaterialItem.setUnitName(erpMaterialItem.getPrimaryUnitOfMeasure());
                saveOrUpdatePurchaseUnit(erpMaterialItem.getPrimaryUnitOfMeasure());

                /**开始 如果传入的物料有物料分类，则保存物料分类（物料小类），否则置空 **/
                boolean hasCategory = ifHasCategory(erpCategoryList);
                if (hasCategory) {
                    saveMaterialItem = setMaterialPurchaseCategory(saveMaterialItem, erpCategoryList);
                }
                /**结束 如果传入的物料有物料分类，则保存物料分类（物料小类），否则置空 **/

                /**开始 如果传入的物料有库存分类，解析保存库存分类，否则置空 **/
                boolean hasInventory = ifHasInventory(erpCategoryList);
                if (hasInventory) {
                    saveMaterialItem = setMaterialInventory(saveMaterialItem, erpCategoryList);
                }
                /**结束 如果传入的物料有库存分类，解析保存库存分类，否则置空 **/

                materialItemMap.put(materialCode, saveMaterialItem);
                saveSrmMaterialList.add(saveMaterialItem);
            }
            //首次拉数据 结束
        }
        /** for copyMaterialList 循环结束 ------------------------------------------------------------------------------------------------------------------------------------------------------- **/

        /** 开始批处理保存或更新Erp物料数据到Srm 开始---------------------------------------------------------------------------------------------------------------------------------------------- **/
        if (CollectionUtils.isNotEmpty(saveSrmMaterialList)) {
            log.info("新增Srm物料数据: " + saveSrmMaterialList.size() + "条数据");
            try {
                iMaterialItemService.saveBatch(saveSrmMaterialList);
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                err = "新增Srm物料数据失败";
            }
        }
        if (CollectionUtils.isNotEmpty(updateSrmMaterialList)) {
            log.info("修改Srm物料数据: " + updateSrmMaterialList.size() + "条数据");
            try {
                iMaterialItemService.updateBatchById(updateSrmMaterialList);
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                err = "修改Srm物料数据失败";
            }
        }
        /** 开始批处理保存或更新Erp物料数据到Srm 结束---------------------------------------------------------------------------------------------------------------------------------------------- **/
        return err;
    }

    /**
     * 设置物料的类别属性
     * 物料小类Id，物料小类名称，品类struct，品类categoryFullName
     *
     * @param saveMaterialItem
     * @param erpCategoryList
     * @return
     */
    public MaterialItem setMaterialPurchaseCategory(MaterialItem saveMaterialItem, List<Category> erpCategoryList) {
        for (Category category : erpCategoryList) {
            String categorySetCode = category.getCategorySetCode(); //获取类别集代码 '1100000101'
            String categorySetName = category.getCategorySetNameUs(); //获取类别集名称 'SRM物料类别'

            //开始 遍历循环得到SRM物料类别
            if (StringUtils.isNotBlank(categorySetCode) && StringUtils.isNotBlank(categorySetName)
                    && categorySetCode.equals("1100000101") && categorySetName.equals("SRM物料类别")) {
                //saveMaterialItem.setCategoryId(Long.valueOf(category.getSetValue()));
                //String categoryName[] = category.getSetValueDescZhs().split("-");

                //获取物料的类别编码 如300615
                String purchaseCategoryCode = category.getSetValue();


                /** 根据物料对应的品类编码去品类表查询品类（品类表里那个编码上的唯一索引不能取消） **/
                List<PurchaseCategory> categorys = iPurchaseCategoryService.list(
                        new QueryWrapper<>(new PurchaseCategory().setCategoryCode(purchaseCategoryCode))
                );
                //校验传入的物料小类是否已经维护
                //Assert.notEmpty(categorys, "找不到对应的物料小类，请先维护物料小类！");
                if (CollectionUtils.isNotEmpty(categorys)) {
                    PurchaseCategory purchaseCategory = categorys.get(0);
                    saveMaterialItem.setCategoryId(purchaseCategory.getCategoryId());
                    saveMaterialItem.setCategoryName(purchaseCategory.getCategoryName());
                    //校验传入的品类在srm品类表中的3级分类结构是否为空
                    Assert.notNull(purchaseCategory.getStruct(), "物料对应的物料小类对应的3级分类结构为空！");
                    //开始 设置物料对应品类的3级分类结构struct以及品类全名称categoryFullName
                    saveMaterialItem.setStruct(purchaseCategory.getStruct());
                    PurchaseCategory cate = new PurchaseCategory().setStruct(purchaseCategory.getStruct());
                    iPurchaseCategoryService.setCategoryFullName(cate);
                    Assert.notNull(cate.getCategoryFullName(), "物料对应的物料小类对应的品类全路径名称为空！");
                    String saveCategoryFullName = cate.getCategoryFullName();
                    //结束 设置物料对应品类的3级分类结构struct以及品类全名称categoryFullName
                    saveMaterialItem.setCategoryFullName(saveCategoryFullName);
                    //iPurchaseCategoryService.SaveOrUpdateByCategoryFullCode(purchaseCategoryCode, saveCategoryFullName);
                    break;
                }
            }
            //结束 遍历循环得到SRM物料类别
        }
        return saveMaterialItem;
    }

    /**
     * 设置物料的库存类别属性 库存类别编码、库存类别名称
     *
     * @param saveMaterialItem
     * @param erpCategoryList
     * @return
     */
    public MaterialItem setMaterialInventory(MaterialItem saveMaterialItem, List<Category> erpCategoryList) {
        for (Category category : erpCategoryList) {
            String categorySetCode = category.getCategorySetCode(); //获取类别集代码 '1'
            String categorySetName = category.getCategorySetNameZhs(); //获取类别集名称 '库存'

            //开始 遍历循环得到库存类别
            if (StringUtils.isNotBlank(categorySetCode) && StringUtils.isNotBlank(categorySetName)
                    && categorySetCode.equals("1") && categorySetName.equals("库存")) {
                //设置库存分类编码和库存分类名称
                String inventoryCode = category.getSetValue();
                String inventoryName = category.getSetValueDescZhs();
                saveMaterialItem.setInventoryCode(inventoryCode);
                saveMaterialItem.setInventoryName(inventoryName);
            }
            //结束 遍历循环得到库存类别
        }
        return saveMaterialItem;
    }

    /**
     * 保存或更新物料数据到Erp的基础物料表ceea_base_material_item
     *
     * @param erpMaterialList
     * @param nowDate
     * @return
     */
    @Transactional
    public String saveOrUpdateErpMaterialItem(List<ErpMaterialItem> erpMaterialList, Date nowDate) throws Exception {
        AtomicReference<String> err = new AtomicReference<>();
        // 新增物料
        List<Category> categories = saveItem(erpMaterialList, nowDate, err);
        //新增或修改类别数据
        saveOrUpdateErpCategory(nowDate, err, categories);
        return err.get();
    }

    private List<Category> saveItem(List<ErpMaterialItem> erpMaterialList, Date nowDate, AtomicReference<String> err) {
        List<Category> materialItemCategories = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(erpMaterialList)) {
            for (ErpMaterialItem erpMaterialItem : erpMaterialList) {
                if (Objects.nonNull(erpMaterialItem)) {
                    Long itemid = erpMaterialItem.getItemId();
                    Assert.notNull(itemid, "传入的物料itemId为空！");
                    //初始化物料的接口来源状态 为 NEW
                    String status = InterfaceStatusEnum.NEW.getName();

                    /** 获取当前物料的类别集 */
                    List<Category> erpCategoryList = erpMaterialItem.getCategoryList();
                    if (CollectionUtils.isNotEmpty(erpCategoryList)) {
                        erpCategoryList.forEach(c -> {
                            c.setItemId(itemid);
                            materialItemCategories.add(c);
                        });
                    }
                    erpMaterialItem.setId(IdGenrator.generate());
                    erpMaterialItem.setItfStatus(status)
                            .setCreationDate(nowDate)
                            .setLastUpdateDate(nowDate)
                            .setImportStatus(IErpService.IMPORT_DEFAULT_STATUS);
                }
            }
            log.info("新增Erp物料数据: " + erpMaterialList.size() + "条数据");
            try {
                iErpMaterialItemService.saveBatch(erpMaterialList);
            } catch (Exception e) {
                log.error("操作失败",e);
                log.error("新增Erp物料数据失败" + e);
                err.set("新增Erp物料数据失败");
            }
        }
        return materialItemCategories;
    }

    /**
     * 保存或更新物料数据到Erp的基础物料表ceea_base_material_item
     *
     * @param erpMaterialList
     * @param nowDate
     * @param err
     * @return
     */
//    public String saveOrUpdateErpMaterialItem(List<ErpMaterialItem> erpMaterialList, Date nowDate, String err) throws Exception {
//        /**查询全量物料数据 **/
//        List<ErpMaterialItem> queryErpMaterialItemList = iErpMaterialItemService.list();
//
//        List<ErpMaterialItem> saveErpMaterialItemList = new ArrayList<>();
//        List<ErpMaterialItem> updateErpMaterialItemList = new ArrayList<>();
//
//        Map<String, ErpMaterialItem> queryErpMaterialMap = queryErpMaterialItemList.stream()
//                .collect(Collectors.toMap(k -> k.getItemId() + k.getOrgCode(), part -> part));
//
//        //物料传入数据去重（erpMaterialList大约200条数据）
//        erpMaterialList = erpMaterialList.stream().distinct().collect(Collectors.toList());
//
//        List<Category> materialItemCategories = new ArrayList<>();
//
//        for (ErpMaterialItem erpMaterialItem : erpMaterialList) {
//            if (Objects.nonNull(erpMaterialItem)) {
//                /**根据ItemId和orgCode组合唯一，如果有记录则修改 **/
//                Long id = IdGenrator.generate();
//                Long itemid = erpMaterialItem.getItemId();
//                String orgCode = erpMaterialItem.getOrgCode();
//                //初始化物料的接口来源状态 为 NEW
//                String status = InterfaceStatusEnum.NEW.getName();
//
//                ErpMaterialItem saveErpMaterialItem = new ErpMaterialItem();
//                ErpMaterialItem updateErpMaterialItem = new ErpMaterialItem();
//
//                /** 获取当前物料的类别集 */
//                List<Category> erpCategoryList = erpMaterialItem.getCategoryList();
//                if (CollectionUtils.isNotEmpty(erpCategoryList) && Objects.nonNull(itemid)){
//                    erpCategoryList.forEach(c -> {
//                        c.setItemId(itemid);
//                        materialItemCategories.add(c);
//                    });
//                }
//
//                Assert.notNull(itemid, "传入的物料itemId为空！");
//                //非首次拉数据
//                if (CollectionUtils.isNotEmpty(queryErpMaterialItemList)) {
//                    //如果可以查到记录，更新操作
//                    ErpMaterialItem existErpMaterialItem = queryErpMaterialMap.get(itemid + orgCode);
//                    if (Objects.nonNull(existErpMaterialItem)) {
//                        id = existErpMaterialItem.getId();
//
//                        updateErpMaterialItem = setErpMaterialItemFields(itemid, orgCode, erpMaterialItem, updateErpMaterialItem);
//
//                        updateErpMaterialItem.setId(id);
//                        status = InterfaceStatusEnum.UPDATE.getName();
//                        updateErpMaterialItem.setItfStatus(status)
//                                .setLastUpdateDate(nowDate)
//                                .setImportStatus(IErpService.IMPORT_DEFAULT_STATUS);
//                        updateErpMaterialItemList.add(updateErpMaterialItem);
//                    }
//                    //新增操作
//                    else {
//                        saveErpMaterialItem = setErpMaterialItemFields(itemid, orgCode, erpMaterialItem, saveErpMaterialItem);
//
//                        saveErpMaterialItem.setId(id);
//                        saveErpMaterialItem.setItfStatus(status)
//                                .setCreationDate(nowDate)
//                                .setLastUpdateDate(nowDate)
//                                .setImportStatus(IErpService.IMPORT_DEFAULT_STATUS);
//                        saveErpMaterialItemList.add(saveErpMaterialItem);
//                    }
//                }
//                //首次推数据 新增操作
//                else {
//                    saveErpMaterialItem = setErpMaterialItemFields(itemid, orgCode, erpMaterialItem, saveErpMaterialItem);
//
//                    saveErpMaterialItem.setId(id);
//                    saveErpMaterialItem.setItfStatus(status)
//                            .setCreationDate(nowDate)
//                            .setLastUpdateDate(nowDate)
//                            .setImportStatus(IErpService.IMPORT_DEFAULT_STATUS);
//                    saveErpMaterialItemList.add(saveErpMaterialItem);
//                }
//            }
//        }
//        /** 开始批处理进行新增或更新Erp物料数据 开始----------------------------------------------------------------------------------------------------------------------------**/
//        if (CollectionUtils.isNotEmpty(saveErpMaterialItemList)) {
//            log.info("新增Erp物料数据: " + saveErpMaterialItemList.size() + "条数据");
//            try {
//                iErpMaterialItemService.saveBatch(saveErpMaterialItemList);
//            } catch (Exception e) {
//                e.printStackTrace();
//                err = "新增Erp物料数据失败";
//            }
//        }
//        if (CollectionUtils.isNotEmpty(updateErpMaterialItemList)) {
//            log.info("修改Erp物料数据: " + updateErpMaterialItemList.size() + "条数据");
//            try {
//                iErpMaterialItemService.updateBatchById(updateErpMaterialItemList);
//            } catch (Exception e) {
//                e.printStackTrace();
//                err = "修改Erp物料数据失败";
//            }
//        }
//        /** 开始批处理进行新增或更新Erp物料数据 结束----------------------------------------------------------------------------------------------------------------------------**/
//
//        //新增或修改类别数据
//        err = saveOrUpdateErpCategory(nowDate, err, materialItemCategories);
//
//        return err;
//    }

    /**
     * 保存或更新物料与组织关系数据Material Org
     *
     * @param copyMaterialOrgList
     * @param nowDate
     * @param err
     * @return
     */
    public void saveOrUpdateSrmMaterialOrg(List<ErpMaterialItem> copyMaterialOrgList, Date nowDate, String err) throws Exception {
        /**查询全量物料组织数据 **/
        List<MaterialOrg> queryMaterialOrgList = iMaterialOrgService.list();
        /** 将全量物料组织数据转为HashMap **/
        Map<MaterialOrgOrganizationDTO, MaterialOrg> materialOrgMap = new HashMap<>();
        queryMaterialOrgList.forEach(materialOrg -> {
            MaterialOrgOrganizationDTO materialOrgOrganizationDTO = new MaterialOrgOrganizationDTO();
            materialOrgOrganizationDTO.setMaterialId(materialOrg.getMaterialId());
            materialOrgOrganizationDTO.setOrganizationId(materialOrg.getOrganizationId());
            materialOrgOrganizationDTO.setOrgId(materialOrg.getOrgId());
            materialOrgMap.put(materialOrgOrganizationDTO, materialOrg);
        });

        List<MaterialOrg> saveMaterialOrgList = new ArrayList<>(); //批处理保存List
        List<MaterialOrg> updateMaterialOrgList = new ArrayList<>(); //批处理更新List

        //开始 遍历传入的物料列表---------------------------------------------------------------------------------------------------------------------
        for (ErpMaterialItem material : copyMaterialOrgList) {
            /** 查询Srm物料表scc_base_material_item获取material_id **/
            List<MaterialItem> materialItems = iMaterialItemService.list(new QueryWrapper<>(
                            new MaterialItem().setMaterialCode(material.getItemNumber())
                    )
            );
            Assert.isTrue(CollectionUtils.isNotEmpty(materialItems), "维护物库存料组织关系时找不到对应的物料！");
            Long materialId = materialItems.get(0).getMaterialId();

            //查询库存组织
            String organizationCode = material.getOrgCode();
            List<Organization> organizations = iOrganizationService.list(new QueryWrapper<>(
                            new Organization().setOrganizationTypeCode("INV").setOrganizationCode(organizationCode)
                    )
            );
            Assert.isTrue(CollectionUtils.isNotEmpty(organizations), "维护物料库存组织关系时找不到对应的库存组织！");
            Assert.notNull(organizations.get(0).getOrganizationId(), "维护物料库存组织关系时根据库存组织编码查询的库存组织Id为空！");
            Assert.notNull(organizations.get(0).getParentOrganizationIds(), "维护物料库存组织关系时根据库存组织编码查询对应的业务实体Id为空！");
            Long organizationId = organizations.get(0).getOrganizationId();
            String organizationName = organizations.get(0).getOrganizationName();
            Long orgId = Long.valueOf(organizations.get(0).getParentOrganizationIds());

            //查询这条物料所属库存组织对应的业务实体
            List<Organization> orgs = iOrganizationService.list(new QueryWrapper<>(
                            new Organization().setOrganizationTypeCode("OU").setOrganizationId(orgId)
                    )
            );
            Assert.isTrue(CollectionUtils.isNotEmpty(orgs), "维护物料库存组织关系时找不到对应的业务实体！");
            Assert.notNull(orgs.get(0).getOrganizationId(), "维护物料库存组织关系时业务实体Id为空！");
            Assert.notNull(orgs.get(0).getOrganizationName(), "维护物料库存组织关系时业务实体名称为空！");
            String orgName = orgs.get(0).getOrganizationName();

            /** 根据HashMap判断是否已存在这条物料这个组织的关系 **/
            MaterialOrgOrganizationDTO dto = new MaterialOrgOrganizationDTO();
            dto.setMaterialId(materialId);
            dto.setOrganizationId(organizationId);
            dto.setOrgId(orgId);
            //不包含即为新增
            if (!materialOrgMap.containsKey(dto)) {
                MaterialOrg saveMaterialOrg = new MaterialOrg();
                Long materialOrgId = IdGenrator.generate();
                saveMaterialOrg.setMaterialOrgId(materialOrgId); //主键Id
                saveMaterialOrg.setMaterialId(materialId); //物料Id
                saveMaterialOrg.setOrganizationId(organizationId); //库存组织Id
                saveMaterialOrg.setOrganizationName(organizationName); //库存组织名称
                saveMaterialOrg.setOrgId(orgId); //业务实体Id
                saveMaterialOrg.setOrgName(orgName); //业务实体名称
                //物料状态 有效、无效
                if (material.getItemStatus().equals("Active")) {
                    saveMaterialOrg.setItemStatus("Y");
                } else {
                    saveMaterialOrg.setItemStatus("N");
                }
                saveMaterialOrg.setUserPurchase(material.getPurchasingEnableFlag()); //可采购
                saveMaterialOrg.setStockEnableFlag(material.getStockEnableFlag()); //可存储
                materialOrgMap.put(dto, saveMaterialOrg);
                saveMaterialOrgList.add(saveMaterialOrg);
            }
            //包含则更新
            else {
                MaterialOrg updateMaterialOrg = new MaterialOrg();
                MaterialOrg materialOrg = materialOrgMap.get(dto);
                BeanUtils.copyProperties(materialOrg, updateMaterialOrg);
                //物料状态 有效、无效
                if (material.getItemStatus().equals("Active")) {
                    updateMaterialOrg.setItemStatus("Y");
                } else {
                    updateMaterialOrg.setItemStatus("N");
                }
                updateMaterialOrg.setUserPurchase(material.getPurchasingEnableFlag()); //可采购
                updateMaterialOrg.setStockEnableFlag(material.getStockEnableFlag()); //可存储
                materialOrgMap.put(dto, updateMaterialOrg);
                updateMaterialOrgList.add(updateMaterialOrg);
            }
        }
        //结束 遍历传入的物料列表---------------------------------------------------------------------------------------------------------------------
        //开始 批处理保存物料组织关系List
        if (CollectionUtils.isNotEmpty(saveMaterialOrgList)) {
            iMaterialOrgService.saveBatch(saveMaterialOrgList);
        }
        if (CollectionUtils.isNotEmpty(updateMaterialOrgList)) {
            iMaterialOrgService.updateBatchById(updateMaterialOrgList);
        }
    }

    /**
     * 保存或更新Erp类别数据
     *
     * @param nowDate
     * @param err
     * @return
     */
    public void saveOrUpdateErpCategory(Date nowDate, AtomicReference<String> err, List<Category> materialItemCategories) {
        List<Category> saveErpCategoryList = new ArrayList<>();
        List<Category> updateErpCategoryList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(materialItemCategories)) {
            HashSet<Long> ids = new HashSet<>();
            materialItemCategories.forEach(category -> {
                if (StringUtil.notEmpty(category.getItemId())) {
                    ids.add(category.getItemId());
                }
            });
            /** 查询全量物料类别数据 **/
            List<Category> queryErpCategoryList = iCategoryService.list(new QueryWrapper<Category>().in("ITEM_ID", ids));
            Map<String, Category> queryErpCategoryMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(queryErpCategoryList)) {
                queryErpCategoryMap = queryErpCategoryList.stream()
                        .filter(category -> StringUtil.notEmpty(category.getItemId()) && StringUtil.notEmpty(category.getCategorySetCode()))
                        .collect(Collectors.toMap(k -> k.getItemId() + k.getCategorySetCode(), part -> part,(K1,K2)->K2));
            }

            //去重
            materialItemCategories = materialItemCategories.stream().distinct().collect(Collectors.toList());

            for (Category erpCategory : materialItemCategories) {
                Long categoryId = IdGenrator.generate();
                Long itemid = erpCategory.getItemId();
                String categorySetCode = erpCategory.getCategorySetCode();
                String categoryStatus = InterfaceStatusEnum.NEW.getName();

                Category saveCategory = new Category();
                Category updateCategory = new Category();

                if (StringUtils.isNotBlank(categorySetCode)) {
                    //非首次拉数据
                    if (CollectionUtils.isNotEmpty(queryErpCategoryList)) {
                        //如果可以查到记录 更新操作
                        Category category = queryErpCategoryMap.get(itemid + categorySetCode);
                        if (category != null) {
                            categoryId = category.getId();
                            updateCategory.setId(categoryId);

                            updateCategory = setCategoryFields(itemid, categorySetCode, erpCategory, updateCategory);
                            categoryStatus = InterfaceStatusEnum.UPDATE.getName();
                            updateCategory.setItfStatus(categoryStatus);
                            updateCategory.setLastUpdateDate(nowDate);
                            updateErpCategoryList.add(updateCategory);

                        }
                        //新增操作
                        else {
                            saveCategory.setId(categoryId);
                            saveCategory = setCategoryFields(itemid, categorySetCode, erpCategory, saveCategory);

                            saveCategory.setItfStatus(categoryStatus);
                            saveCategory.setCreationDate(nowDate);
                            saveCategory.setLastUpdateDate(nowDate);
                            saveErpCategoryList.add(saveCategory);
                        }

                    }
                    //首次拉数据 新增操作
                    else {
                        saveCategory.setId(categoryId);
                        saveCategory = setCategoryFields(itemid, categorySetCode, erpCategory, saveCategory);

                        saveCategory.setItfStatus(categoryStatus);
                        saveCategory.setCreationDate(nowDate);
                        saveCategory.setLastUpdateDate(nowDate);
                        saveErpCategoryList.add(saveCategory);
                    }
                }

            }
        }
        if (CollectionUtils.isNotEmpty(saveErpCategoryList)) {
            log.info("新增erp类别数据: " + saveErpCategoryList.size() + "条");
            try {
                iCategoryService.saveBatch(saveErpCategoryList);
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                err.set("新增erp类别数据失败");
            }
        }
        if (CollectionUtils.isNotEmpty(updateErpCategoryList)) {
            log.info("修改erp类别数据: " + updateErpCategoryList.size() + "条");
            try {
                iCategoryService.updateBatchById(updateErpCategoryList);
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                err.set("修改erp类别数据失败");
            }
        }
    }

    /**
     * 设置erp物料属性
     *
     * @param saveOrUpdateErpMaterialItem
     * @return
     */
    private ErpMaterialItem setErpMaterialItemFields(Long itemid, String orgCode, ErpMaterialItem erpMaterialItem, ErpMaterialItem saveOrUpdateErpMaterialItem) {
        BeanUtils.copyProperties(erpMaterialItem, saveOrUpdateErpMaterialItem);
        saveOrUpdateErpMaterialItem.setItemId(itemid)
                .setOrgCode(orgCode);
        return saveOrUpdateErpMaterialItem;
    }

    /**
     * 设置类别属性
     *
     * @param erpCategory
     * @return
     */
    private Category setCategoryFields(Long itemid, String categorySetCode, Category erpCategory, Category saveOrUpdateCategory) {
        saveOrUpdateCategory.setItemId(itemid);
        saveOrUpdateCategory.setCategorySetCode(categorySetCode);
        saveOrUpdateCategory.setCategorySetNameZhs(erpCategory.getCategorySetNameZhs());
        saveOrUpdateCategory.setCategorySetNameUs(erpCategory.getCategorySetNameUs());
        saveOrUpdateCategory.setSetValue(erpCategory.getSetValue());
        saveOrUpdateCategory.setSetValueDescZhs(erpCategory.getSetValueDescZhs());
        saveOrUpdateCategory.setSetValueDescUs(erpCategory.getSetValueDescUs());
        return saveOrUpdateCategory;
    }

    /**
     * 判断传入的物料有没有物料三级分类
     * 有返回true，没有则返回false
     *
     * @param categoryList
     * @return
     */
    public boolean ifHasCategory(List<Category> categoryList) {
        boolean hasCategory = false;
        if (!categoryList.isEmpty()) {
            for (Category category : categoryList) {
                if (category.getCategorySetCode().equals("1100000101")
                        && category.getCategorySetNameUs().equals("SRM物料类别")
                        && category.getSetValue() != null
                        && category.getSetValueDescZhs() != null
                        && !category.getSetValue().equals("00")
                        && !category.getSetValueDescZhs().equals("默认")) {
                    hasCategory = true;
                    break;
                }
            }
        }
        return hasCategory;
    }

    /**
     * 判断传入的物料有没有库存分类
     * 有返回true，没有则返回false
     *
     * @param categoryList
     * @return
     */
    public boolean ifHasInventory(List<Category> categoryList) {
        boolean hasInventory = false;
        if (!categoryList.isEmpty()) {
            for (Category category : categoryList) {
                if (category.getCategorySetCode().equals("1")
                        && category.getCategorySetNameZhs().equals("库存")
                        && category.getSetValue() != null
                        && category.getSetValueDescZhs() != null) {
                    hasInventory = true;
                    break;
                }
            }
        }
        return hasInventory;
    }

    /**
     * 将物料接口里的单位维护到单位表里
     *
     * @param purchaseUnit
     */
    public void saveOrUpdatePurchaseUnit(String purchaseUnit) {
        QueryWrapper<PurchaseUnit> unitQueryWrapper = new QueryWrapper<>();
        unitQueryWrapper.eq("UNIT_CODE", purchaseUnit);
        unitQueryWrapper.eq("UNIT_NAME", purchaseUnit);
        List<PurchaseUnit> srmUnitList = iPurchaseUnitService.list(unitQueryWrapper);
        if (srmUnitList.isEmpty()) {
            PurchaseUnit saveUnit = new PurchaseUnit();
            Long unitId = IdGenrator.generate();
            saveUnit.setUnitId(unitId);
            saveUnit.setUnitCode(purchaseUnit);
            saveUnit.setUnitName(purchaseUnit);
            saveUnit.setEnabled("Y");
            saveUnit.setSourceSystem(DataSourceEnum.ERP_SYS.getValue());
            iPurchaseUnitService.save(saveUnit);
        }
    }

    /**
     * 处理传入的品类全路径是多级分类形式的情况（超过3级）
     *
     * @param categoryNames
     * @return
     */
    public String mutiStage(String categoryNames[]) {
        String returnCategoryFullName = categoryNames[0] + "-" + categoryNames[1] + "-" + categoryNames[2];
        return returnCategoryFullName;
    }

    /**
     * 保存或更新汇率数据GidailyRates
     *
     * @param gidailyRatesList
     * @param instId
     * @param requestTime
     * @return
     */
    @Override
    public SoapResponse saveOrUpdateGidailyRates(List<GidailyRate> gidailyRatesList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (CollectionUtils.isEmpty(gidailyRatesList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                String conversionType = "Corporate";  //2020年11月2日11:20:40 目前只查询这个类型的汇率信息
                int totalTaxNum = gidailyRatesList.size();
                gidailyRatesList = gidailyRatesList.stream().filter(x -> conversionType.equals(x.getConversionType())).collect(Collectors.toList());
                int ignoreTaxNum = totalTaxNum - gidailyRatesList.size();
                saveGidailyRatesInfo(gidailyRatesList , nowDate);
                returnStatus = "S";
                resultMsg = "接收汇率数据成功；";
                if (ignoreTaxNum > 0){
                    resultMsg += "已忽略汇率类型不为Corporate的汇率数据："+ignoreTaxNum+"条";
                }
            } catch (BaseException e){
                returnStatus = "E";
                resultMsg = e.getMessage();
                log.error("新增或修改erp汇率接口表时报错：", e);
            }catch (Exception e) {
                returnStatus = "E";
                resultMsg = "新增或修改erp汇率接口表时报错";
                log.error("新增或修改erp汇率接口表时报错：", e);
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    @Transactional
    public void saveGidailyRatesInfo(List<GidailyRate> gidailyRatesList , Date nowDate) throws Exception{
        //1.查询全量历史汇率数据
        GidailyRate queryErpGidailyRate = new GidailyRate();
        List<GidailyRate> dbErpGidailyRateList = iGidailyRateService.list(new QueryWrapper<>(queryErpGidailyRate));
        Set<String> currencySet = new HashSet<>();
        //2.对汇率接口数据进行处理
        for (GidailyRate inputGidailyRate : gidailyRatesList) {
            if(Objects.isNull(inputGidailyRate)){
                continue;
            }
            //源币种或目标币种为空当做脏数据处理，不写入汇率表中
            boolean isDirtyData = StringUtils.isBlank(inputGidailyRate.getFromCurrency()) ||
                    StringUtils.isBlank(inputGidailyRate.getToCurrency());
            if(isDirtyData){
                continue;
            }

            //汇率信息去重
            currencySet.add(new StringBuilder().append(inputGidailyRate.getFromCurrency())
                    .append(";")
                    .append(inputGidailyRate.getToCurrency()).toString());

            boolean inputDataNotNull = !isDirtyData &&
                    StringUtils.isNotBlank(inputGidailyRate.getConversionDate());
            inputGidailyRate.setItfStatus(InterfaceStatusEnum.NEW.getName());

            if (inputDataNotNull && CollectionUtils.isNotEmpty(dbErpGidailyRateList)) {
                for (GidailyRate dbGidailyRate : dbErpGidailyRateList) {
                    if(Objects.isNull(dbGidailyRate)){
                        continue;
                    }
                    //数据库存在了该记录，标志为更改
                    boolean dbExistGidailyRate = inputGidailyRate.getFromCurrency().compareTo(dbGidailyRate.getFromCurrency()) == 0
                            && inputGidailyRate.getToCurrency().compareTo(dbGidailyRate.getToCurrency()) == 0
                            && inputGidailyRate.getConversionDate().compareTo(dbGidailyRate.getConversionDate()) == 0;
                    if (dbExistGidailyRate){
                        inputGidailyRate.setItfStatus(InterfaceStatusEnum.UPDATE.getName());
                        inputGidailyRate.setId(dbGidailyRate.getId());
                        break;
                    }
                }
            }
            if (InterfaceStatusEnum.NEW.getName().equals(inputGidailyRate.getItfStatus())) {
                inputGidailyRate.setCreationDate(nowDate);
            }
            inputGidailyRate.setLastUpdateDate(nowDate);
        }
        //3.保存汇率接口数据
        log.info("新增或修改汇率接口数据: " + JsonUtil.entityToJsonStr(gidailyRatesList));
        iGidailyRateService.saveOrUpdateBatch(gidailyRatesList);
        //4.对最新汇率记录 进行更新或新增
        Iterator<String> it =  currencySet.iterator();
        while(it.hasNext()){
            //汇率格式为  【formCurrency;toCurrency】
            String currencyStr = it.next();
            String formCurrency = currencyStr.split(";")[0];
            String toCurrency = currencyStr.split(";")[1];
            //查询最新汇率表;可能有脏数据，所以用list
            List<LatestGidailyRate> dbLatestGidailyRateList = iLatestGidailyRateService.
                    list(new QueryWrapper<>(new LatestGidailyRate()
                            .setFromCurrencyCode(formCurrency)
                            .setToCurrencyCode(toCurrency)));

            //根据汇率接口数据 获取最新汇率
            List<GidailyRate> gidailyRateList =  iGidailyRateService.list(Wrappers.lambdaQuery(GidailyRate.class)
                    .eq(GidailyRate::getToCurrency , toCurrency)
                    .eq(GidailyRate::getFromCurrency , formCurrency)
                    .orderByDesc(GidailyRate::getErpLastUpdateDate));
            GidailyRate newGidailyRate = gidailyRateList.get(0);
            LatestGidailyRate saveLatestGidailyRate = new LatestGidailyRate();
            //新增
            if(dbLatestGidailyRateList.isEmpty() || Objects.isNull(dbLatestGidailyRateList.get(0))){
                saveLatestGidailyRate.setId(IdGenrator.generate());
                saveLatestGidailyRate.setFromCurrencyCode(newGidailyRate.getFromCurrency());
                saveLatestGidailyRate.setToCurrencyCode(newGidailyRate.getToCurrency());
                saveLatestGidailyRate.setConversionRate(newGidailyRate.getConversionRate());
                //获取币种名称
                PurchaseCurrency fromPurchaseCurrency = iPurchaseCurrencyService
                        .getOne(new QueryWrapper<>(new PurchaseCurrency().
                                setCurrencyCode(newGidailyRate.getFromCurrency())));
                PurchaseCurrency toPurchaseCurrency = iPurchaseCurrencyService
                        .getOne(new QueryWrapper<>(new PurchaseCurrency()
                                .setCurrencyCode(newGidailyRate.getToCurrency())));
                if(Objects.isNull(fromPurchaseCurrency)){
                    throw new BaseException("源币种"+newGidailyRate.getFromCurrency()+"在SRM系统中不存在，请联系管理员");
                }
                if(Objects.isNull(toPurchaseCurrency)){
                    throw new BaseException("目标币种"+newGidailyRate.getToCurrency()+"在SRM系统中不存在，请联系管理员");
                }
                saveLatestGidailyRate.setFromCurrency(fromPurchaseCurrency.getCurrencyName());
                saveLatestGidailyRate.setToCurrency(toPurchaseCurrency.getCurrencyName());
                saveLatestGidailyRate.setConversionType(newGidailyRate.getConversionType());
            }else{
                //更新
                saveLatestGidailyRate = dbLatestGidailyRateList.get(0);
                saveLatestGidailyRate.setLastUpdateDate(new Date());
                saveLatestGidailyRate.setConversionRate(newGidailyRate.getConversionRate());
            }
            iLatestGidailyRateService.saveOrUpdate(saveLatestGidailyRate);
        }
    }




    /**
     * 保存或更新币种数据Currency
     *
     * @param currencysList
     * @param instId
     * @param requestTime
     * @return
     */
    @Override
    @Transactional
    public SoapResponse saveOrUpdateCurrencys(List<ErpCurrency> currencysList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (CollectionUtils.isEmpty(currencysList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                for (ErpCurrency erpCurrency : currencysList) {
                    if (null != erpCurrency) {
                        /**根据币种代码 查询记录，如果有记录则修改，反之则新增*/
                        Long id = IdGenrator.generate();
                        String currencyCode = erpCurrency.getCurrencyCode();
                        String status = InterfaceStatusEnum.NEW.getName();
                        if (StringUtils.isNotBlank(currencyCode)) {
                            ErpCurrency queryErpCurrency = new ErpCurrency();
                            queryErpCurrency.setCurrencyCode(currencyCode);
                            List<ErpCurrency> queryCurrencyList = iErpCurrencyService.list(new QueryWrapper<>(queryErpCurrency));
                            if (CollectionUtils.isNotEmpty(queryCurrencyList) && null != queryCurrencyList.get(0)) {
                                id = queryCurrencyList.get(0).getId();
                                status = InterfaceStatusEnum.UPDATE.getName();
                            }
                        }
                        erpCurrency.setId(id);
                        erpCurrency.setItfStatus(status);
                        if (status.equals("NEW")) {
                            erpCurrency.setCreationDate(nowDate);
                        }
                        erpCurrency.setLastUpdateDate(nowDate);
                    }
                }

                log.info("新增或修改币种接口数据: " + JsonUtil.entityToJsonStr(currencysList));
                boolean isSaveOrUpdate = iErpCurrencyService.saveOrUpdateBatch(currencysList);
                if (isSaveOrUpdate) {
                    returnStatus = "S";
                    resultMsg = "成功插入币种接口表.";
                } else {
                    returnStatus = "E";
                    resultMsg = "新增或修改币种接口表时报错";
                    log.error("新增或修改币种接口表时报错");
                }
            } catch (Exception e) {
                returnStatus = "E";
                resultMsg = "新增或修改erp币种接口表时报错";
                log.error("新增或修改erp币种接口表时报错：", e);
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    /**
     * 保存或更新税率数据PurchaseTax
     *
     * @param erpPurchaseTaxsList
     * @param instId
     * @param requestTime
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse saveOrUpdatePurchaseTaxs(List<ErpPurchaseTax> erpPurchaseTaxsList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        int numForNonTaxRateIdOrCode = 0;
        int numForRepeat = 0;
        if (CollectionUtils.isEmpty(erpPurchaseTaxsList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                String err = "";
                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                //过滤掉TAX_RATE_ID和TAX_RATE_CODE为空的数据
                List<ErpPurchaseTax> collect = erpPurchaseTaxsList.stream().filter(
                        x -> (StringUtils.isNotEmpty(x.getTaxRateId())
                                && StringUtils.isNotEmpty(x.getTaxRateCode())
                                && (StringUtils.isEmpty(x.getEffectiveTo()) || x.getEffectiveTo().compareTo(today) > 0))).collect(Collectors.toList());
                //TAX_RATE_ID和TAX_RATE_CODE为空的数据的税率数据数
                numForNonTaxRateIdOrCode = erpPurchaseTaxsList.size() - collect.size();
                //对传入的税率数据根据TAX_RATE_ID和TAX_RATE_CODE去重
                List<ErpPurchaseTax> erpPurchaseTaxList = collect.stream().distinct().collect(Collectors.toList());
                numForRepeat = collect.size() - erpPurchaseTaxList.size();

                /** 复制IErpPurchaseTaxWsService接口传入的erpPurchaseTaxList **/
                List<ErpPurchaseTax> copyPurchaseTaxList = erpPurchaseTaxList;

                //查询全量历史税率数据
                List<ErpPurchaseTax> erpPurchaseTaxs = iErpPurchaseTaxService.list().stream().distinct().collect(Collectors.toList());
                Map<String, ErpPurchaseTax> map = erpPurchaseTaxs.stream().collect(Collectors.toMap(k -> k.getTaxRateId() + k.getTaxRateCode(), part -> part));

                List<ErpPurchaseTax> saveErpPurchaseTaxList = new ArrayList<>();
                List<ErpPurchaseTax> updateErpPurchaseTaxList = new ArrayList<>();

                for (ErpPurchaseTax erpPurchaseTax : erpPurchaseTaxList) {
                    if (Objects.nonNull(erpPurchaseTax)) {
                        /** 根据TAX_RATE_ID和TAX_RATE_CODE进行唯一校验 */
                        Long id = null;
                        String taxRateId = erpPurchaseTax.getTaxRateId();
                        String taxRateCode = erpPurchaseTax.getTaxRateCode();
                        String taxRateIdCode = taxRateId + taxRateCode;
                        String status = InterfaceStatusEnum.NEW.getName();
                        //数据库存在这个税率数据
                        boolean dbHasErpPurchaseTax = (map.containsKey(taxRateIdCode)) && (Objects.nonNull(map.get(taxRateIdCode)));
                        //数据库存在这个税率 更新操作
                        if (dbHasErpPurchaseTax) {
                            id = map.get(taxRateIdCode).getTaxId();
                            status = InterfaceStatusEnum.UPDATE.getName();
                            erpPurchaseTax.setTaxId(id).setItfStatus(status).setSourceSystem(DataSourceEnum.ERP_SYS.getValue());
                            updateErpPurchaseTaxList.add(erpPurchaseTax);
                        }
                        //新增操作
                        else {
                            id = IdGenrator.generate();
                            erpPurchaseTax.setTaxId(id).setItfStatus(status).setSourceSystem(DataSourceEnum.ERP_SYS.getValue());
                            saveErpPurchaseTaxList.add(erpPurchaseTax);
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(saveErpPurchaseTaxList)) {
                    try {
                        iErpPurchaseTaxService.saveBatch(saveErpPurchaseTaxList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        err = "新增erp税率数据出错";
                    }
                }
                if (CollectionUtils.isNotEmpty(updateErpPurchaseTaxList)) {
                    try {
                        iErpPurchaseTaxService.updateBatchById(updateErpPurchaseTaxList);
                    } catch (Exception e) {
                        e.printStackTrace();
                        err = "修改erp税率数据出错";
                    }
                }

                //将税率数据保存到srm的税率表
                err = saveOrUpdateSrmTax(err, copyPurchaseTaxList);

                if (StringUtils.isEmpty(err)) {
                    returnStatus = "S";
                    resultMsg = "税率数据接收成功。成功插入" + saveErpPurchaseTaxList.size() + "条，更新：" + updateErpPurchaseTaxList.size() + "条。";
                    if (numForNonTaxRateIdOrCode > 0) {
                        resultMsg += "已忽略税率id或税率编码为空或已失效的税率数据：" + numForNonTaxRateIdOrCode + "条";
                    }
                    if (numForRepeat > 0) {
                        resultMsg += "已忽略税率id重复的数据：" + numForRepeat + "条";
                    }
                } else {
                    returnStatus = "E";
                    resultMsg = err;
                    log.error(err);
                }
            } catch (Exception e) {
                e.printStackTrace();
                returnStatus = "E";
                resultMsg = "接收erp税率数据出错";
                log.error("接收Erp税率数据出错", e);
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    /**
     * 保存或更新到srm到数据配置表
     * @param err
     * @param copyPurchaseTaxList
     */
    public String saveOrUpdateSrmTax(String err, List<ErpPurchaseTax> copyPurchaseTaxList) {
        //查询srm税率全量数据 初始化srm更新和保存的List
        List<PurchaseTax> srmQueryPurchaseTaxList = iPurchaseTaxService.list();
        List<PurchaseTax> saveSrmPurchaseTaxList = new ArrayList<>();
        List<PurchaseTax> updateSrmPurchaseTaxList = new ArrayList<>();

        /** 开始 循环遍历税率列表 **/
        for (ErpPurchaseTax erpPurchaseTax : copyPurchaseTaxList) {
            PurchaseTax saveSrmPurchaseTax = new PurchaseTax();
            PurchaseTax updateSrmPurchaseTax = new PurchaseTax();

            Long srmTaxId = IdGenrator.generate();
            Long taxRateId = Long.valueOf(erpPurchaseTax.getTaxRateId()); //获取erp税率Id
            String taxKey = erpPurchaseTax.getTaxRateCode(); //获取erp税率编码
            String srmTaxStatus = InterfaceStatusEnum.NEW.getName();

            Assert.notNull(taxRateId, "税率Id为空！");
            //非首次拉数据
            if (!srmQueryPurchaseTaxList.isEmpty()) {
                //根据erpTaxRateId和税码 判断 是新增还是更新
                for (PurchaseTax srmTax : srmQueryPurchaseTaxList) {
                    if (taxRateId.equals(srmTax.getErpTaxRateId()) && taxKey.equals(srmTax.getTaxKey())) {
                        //匹配到就更新 更新操作
                        BeanUtils.copyProperties(srmTax, updateSrmPurchaseTax);
                        updateSrmPurchaseTax.setLanguage("zh_CN"); //设置税率语言
                        updateSrmPurchaseTax.setEnabled("Y"); //设置启用状态
                        srmTaxStatus = InterfaceStatusEnum.UPDATE.getName();
                        updateSrmPurchaseTax.setSourceSystem(InterfaceResourceEnum.ERP.getName()); //设置来源系统
                        updateSrmPurchaseTaxList.add(updateSrmPurchaseTax);
                        break;
                    }
                }
                //新增操作
                if (srmTaxStatus.equals("NEW")) {
                    saveSrmPurchaseTax.setTaxId(srmTaxId); //主键Id
                    saveSrmPurchaseTax.setErpTaxRateId(taxRateId); //erp税率Id
                    saveSrmPurchaseTax.setTaxKey(erpPurchaseTax.getTaxRateCode()); //税码
                    saveSrmPurchaseTax.setTaxCode(erpPurchaseTax.getPercentageRate()); //税率值
                    saveSrmPurchaseTax.setLanguage("zh_CN"); //语言
                    saveSrmPurchaseTax.setEnabled("Y"); //启用状态
                    saveSrmPurchaseTax.setSourceSystem(InterfaceResourceEnum.ERP.getName()); //来源系统
                    saveSrmPurchaseTaxList.add(saveSrmPurchaseTax);
                }
            }
            //首次拉数据
            else {
                saveSrmPurchaseTax.setTaxId(srmTaxId);
                saveSrmPurchaseTax.setErpTaxRateId(taxRateId);
                saveSrmPurchaseTax.setTaxKey(erpPurchaseTax.getTaxRateCode());
                saveSrmPurchaseTax.setTaxCode(erpPurchaseTax.getPercentageRate());
                saveSrmPurchaseTax.setLanguage("zh_CN");
                saveSrmPurchaseTax.setEnabled("Y");
                saveSrmPurchaseTax.setSourceSystem(InterfaceResourceEnum.ERP.getName());
                saveSrmPurchaseTaxList.add(saveSrmPurchaseTax);
            }
        }
        /** 结束 循环遍历税率列表 **/

        /** 开始 批量更新或者保存税率到Srm的purchase_Tax表 **/
        if (!saveSrmPurchaseTaxList.isEmpty()) {
            log.info("新增Srm税率数据：" + saveSrmPurchaseTaxList.size() + "条数据");
            try {
                iPurchaseTaxService.saveBatch(saveSrmPurchaseTaxList);
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                err = "新增Srm税率数据出错";
            }
        }
        if (!updateSrmPurchaseTaxList.isEmpty()) {
            log.info("修改Srm税率数据：" + updateSrmPurchaseTaxList.size() + "条数据");
            try {
                iPurchaseTaxService.updateBatchById(updateSrmPurchaseTaxList);
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                err = "修改Srm税率数据出错";
            }
        }
        return err;
    }

    /**
     * 保存或更新erp税率数据到基础全量表
     *
     * @param nowDate
     * @param duplicateRemovedTaxList
     * @param err
     * @modifiedBy xiexh12@meicloud.com
     */
    public String saveOrUpdateErpTax(Date nowDate, List<ErpPurchaseTax> duplicateRemovedTaxList, String err) {


        //查询全量历史税率数据 初始化erp税率更新和保存的list
        List<ErpPurchaseTax> queryErpPurchaseTaxList = iErpPurchaseTaxService.list();

        List<ErpPurchaseTax> saveErpPurchaseTaxList = new ArrayList<>();
        List<ErpPurchaseTax> updateErpPurchaseTaxList = new ArrayList<>();

        /** 开始 循环遍历税率列表 **/
        for (ErpPurchaseTax erpPurchaseTax : duplicateRemovedTaxList) {
            /** 根据erp系统的税率Id进行唯一性校验 */
            Long id = IdGenrator.generate();
            String taxRateId = erpPurchaseTax.getTaxRateId();
            String erpTaxStatus = InterfaceStatusEnum.NEW.getName();
            Assert.notNull(taxRateId, "传入的erp税率Id为空！");

            //初始化新增erp税率实体和更新erp税率实体
            ErpPurchaseTax saveErpPurchaseTax = new ErpPurchaseTax();
            ErpPurchaseTax updateErpPurchaseTax = new ErpPurchaseTax();

            //非首次拉取erp税率数据
            if (CollectionUtils.isNotEmpty(queryErpPurchaseTaxList)) {
                for (ErpPurchaseTax queryErpPurchaseTax : queryErpPurchaseTaxList) {
                    //如果可以在erp税率数据表查到这条税率数据
                    if (taxRateId.equals(queryErpPurchaseTax.getTaxRateId())) {
                        BeanUtils.copyProperties(queryErpPurchaseTax, updateErpPurchaseTax); //复制对象属性
                        erpTaxStatus = InterfaceStatusEnum.UPDATE.getName();
                        updateErpPurchaseTax.setItfStatus(erpTaxStatus); //设置数据新增更新状态
                        updateErpPurchaseTax.setSourceSystem(DataSourceEnum.ERP_SYS.getValue()); //设置数据来源系统
                        updateErpPurchaseTax.setLastUpdateDate(nowDate);
                        updateErpPurchaseTaxList.add(updateErpPurchaseTax);
                        break;
                    }
                }
                //整个循环完成后状态依然为NEW，则为新增操作
                if (erpTaxStatus.equals("NEW")) {
                    BeanUtils.copyProperties(erpPurchaseTax, saveErpPurchaseTax); //复制对象属性
                    saveErpPurchaseTax.setTaxId(id); //设置主键Id
                    saveErpPurchaseTax.setItfStatus(erpTaxStatus); //设置数据新增更新状态
                    saveErpPurchaseTax.setSourceSystem(DataSourceEnum.ERP_SYS.getValue()); //设置数据来源系统
                    saveErpPurchaseTax.setCreationDate(nowDate);
                    saveErpPurchaseTax.setLastUpdateDate(nowDate);
                    saveErpPurchaseTaxList.add(erpPurchaseTax);
                }
            }
            //否则，即为首次拉取数据，只做新增操作
            else {
                BeanUtils.copyProperties(erpPurchaseTax, saveErpPurchaseTax); //复制对象属性
                erpPurchaseTax.setTaxId(id);
                erpTaxStatus = InterfaceStatusEnum.NEW.getName();
                erpPurchaseTax.setItfStatus(erpTaxStatus);
                erpPurchaseTax.setSourceSystem(DataSourceEnum.ERP_SYS.getValue());
                erpPurchaseTax.setCreationDate(nowDate);
                erpPurchaseTax.setLastUpdateDate(nowDate);
                saveErpPurchaseTaxList.add(erpPurchaseTax);
            }
        }
        /** 结束 循环遍历税率列表 **/

        /** 开始 保存或更新erp税率数据到基础全量表 **/
        if (CollectionUtils.isNotEmpty(saveErpPurchaseTaxList)) {
            log.info("新增Erp税率数据: " + saveErpPurchaseTaxList.size() + "条数据");
            try {
                iErpPurchaseTaxService.saveBatch(saveErpPurchaseTaxList);
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                err = "新增Erp税率数据出错";
            }
        }
        if (CollectionUtils.isNotEmpty(updateErpPurchaseTaxList)) {
            log.info("修改Erp税率数据: " + updateErpPurchaseTaxList.size() + "条数据");
            try {
                iErpPurchaseTaxService.updateBatchById(updateErpPurchaseTaxList);
            } catch (Exception e) {
                log.error("操作失败",e);
                e.printStackTrace();
                err = "修改Erp税率数据出错";
            }
        }
        /** 结束 保存或更新erp税率数据到基础全量表 **/
        return err;
    }

    /**
     * 根据erp税率id对传入的税率列表进行去重
     * @param inPurchaseTaxList
     * @modifiedBy xiexh12@meicloud.com
     */
    public List<ErpPurchaseTax> duplicateRemove(List<ErpPurchaseTax> inPurchaseTaxList) {
        Assert.notEmpty(inPurchaseTaxList, "初始进行去重时传入的税率列表为空！");
        Map<String, ErpPurchaseTax> map = new HashMap<>();
        //开始 根据erpPurchaseTaxId进行去重
        inPurchaseTaxList.forEach(inTax -> {
            String taxIdCode = inTax.getTaxRateId() + "==" + inTax.getTaxRateCode();
            //map中查不到这两个值的组合key
            if (!map.containsKey(taxIdCode)) {
                map.put(taxIdCode, inTax);
            }
            //map中查到这两个值的组合key，根据失效日期值判断
            else {
                //如果失效日期为空，则取这条税率数据，更新操作
                //TODO 如果传入的多条税码相同的税率都存在失效日期，选取失效日期最大的那一条（即距离当前日期最远的那一条）
                if (StringUtils.isNotBlank(inTax.getEffectiveTo())) {
                    map.replace(taxIdCode, inTax);
                }
            }
        });
        Collection<ErpPurchaseTax> taxCollection = map.values();
        List<ErpPurchaseTax> outErpPurchaseTaxList = new ArrayList<>(taxCollection);
        //结束 根据erpPurchaseTaxId进行去重
        return outErpPurchaseTaxList;
    }

    /**
     * 保存或更新银行分行数据
     * @param erpBranchBanksList
     * @param instId
     * @param requestTime
     * @return
     */
    @Override
    @Transactional
    public SoapResponse saveOrUpdateBranchBanks(List<ErpBranchBank> erpBranchBanksList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if (CollectionUtils.isEmpty(erpBranchBanksList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                String err = "";
                //查询全量历史银行分行数据
                List<ErpBranchBank> queryErpBranchBankList = iErpBranchBankService.list();
                List<ErpBranchBank> saveBranchBankList = new ArrayList<>();
                List<ErpBranchBank> updateErpBranchBankList = new ArrayList<>();
                for (ErpBranchBank erpBranchBank : erpBranchBanksList) {
                    if (null != erpBranchBank) {
                        /**根据银行编码和分行编号，查询记录，作为唯一性校验（新增还是保存）的标准*/
                        Long id = IdGenrator.generate();
                        String bankNum = erpBranchBank.getBankNum();
                        String branchBankNum = erpBranchBank.getBranchBankNum();
                        String erpBranchBankStatus = InterfaceStatusEnum.NEW.getName();
                        if (StringUtils.isNotBlank(branchBankNum)) {
                            if (CollectionUtils.isNotEmpty(queryErpBranchBankList)) {
                                for (ErpBranchBank queryErpBranchBank : queryErpBranchBankList) {
                                    if (null != queryErpBranchBank
                                            && branchBankNum.equals(queryErpBranchBank.getBranchBankNum())
                                            && bankNum.equals(queryErpBranchBank.getBankNum())
                                            && null != queryErpBranchBank.getBranchBankId()) {
                                        id = queryErpBranchBank.getBranchBankId();
                                        erpBranchBank.setBranchBankId(id);
                                        erpBranchBankStatus = InterfaceStatusEnum.UPDATE.getName();
                                        erpBranchBank.setItfStatus(erpBranchBankStatus);
                                        erpBranchBank.setSourceSystem(InterfaceResourceEnum.ERP.getName());
                                        erpBranchBank.setLastUpdateDate(nowDate);
                                        updateErpBranchBankList.add(erpBranchBank);
                                        break;
                                    }
                                }
                                if (erpBranchBankStatus.equals("NEW")) {
                                    erpBranchBank.setBranchBankId(id);
                                    erpBranchBank.setItfStatus(erpBranchBankStatus);
                                    erpBranchBank.setSourceSystem(InterfaceResourceEnum.ERP.getName());
                                    erpBranchBank.setCreationDate(nowDate);
                                    erpBranchBank.setLastUpdateDate(nowDate);
                                    saveBranchBankList.add(erpBranchBank);
                                }
                            } else {
                                erpBranchBank.setBranchBankId(id);
                                erpBranchBankStatus = InterfaceStatusEnum.NEW.getName();
                                erpBranchBank.setItfStatus(erpBranchBankStatus);
                                erpBranchBank.setCreationDate(nowDate);
                                erpBranchBank.setLastUpdateDate(nowDate);
                                saveBranchBankList.add(erpBranchBank);
                            }
                        } else {
                            log.error("银行分行编号为空！");
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(saveBranchBankList)) {
                    log.info("新增Erp银行分行数据: " + saveBranchBankList.size() + "条数据");
                    try {
                        iErpBranchBankService.saveBatch(saveBranchBankList);
                    } catch (Exception e) {
                        log.error("操作失败",e);
                        err = "新增Erp银行分行数据出错";
                    }
                }
                if (CollectionUtils.isNotEmpty(updateErpBranchBankList)) {
                    log.info("修改Erp银行分行数据: " + updateErpBranchBankList.size() + "条数据");
                    try {
                        iErpBranchBankService.updateBatchById(updateErpBranchBankList);
                    } catch (Exception e) {
                        log.error("操作saveOrUpdateEmployees失败",e);
                        err = "修改Erp银行分行数据出错";
                    }
                }

                if (err.isEmpty() || err.equals("")) {
                    returnStatus = "S";
                    resultMsg = "接收Erp银行分行数据成功";
                } else {
                    returnStatus = "E";
                    resultMsg = err;
                    log.error(err);
                }
            } catch (Exception e) {
                returnStatus = "E";
                resultMsg = "接收erp银行分行数据出错";
                log.error("接收Erp银行分行数据出错", e);
            }
        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }
}

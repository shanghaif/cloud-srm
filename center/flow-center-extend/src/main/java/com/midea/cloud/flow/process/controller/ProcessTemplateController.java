package com.midea.cloud.flow.process.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.flow.common.method.WorkFLowCommonMethod;
import com.midea.cloud.flow.workflow.service.ITemplateHeaderService;
import com.midea.cloud.flow.workflow.service.ITemplateLinesService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.flow.process.dto.ProcessTemplateDTO;
import com.midea.cloud.srm.model.flow.process.dto.TemplateHeaderDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
import com.midea.cloud.srm.model.flow.process.entity.TemplateLines;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <pre>
 *  流程模板Contrller
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 11:43
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/flow/processTemplent")
public class ProcessTemplateController extends BaseController {
    private static Logger LOGGER = LoggerFactory.getLogger(ProcessTemplateController.class);

    @Autowired
    private ITemplateHeaderService iTemplateHeaderService; //流程表头Service接口

    @Autowired
    private ITemplateLinesService iTemplateLinesService; //流程行Service接口

    @Autowired
    private RbacClient rbacClient;

    /**
     * Description 根据条件分页获取流程模板(流程头)信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.22
     * @throws
     **/
    @PostMapping("/listPage")
    public PageInfo<TemplateHeader> getTemplateHeaderPage(@RequestBody TemplateHeader templateHeader){
        PageUtil.startPage(templateHeader.getPageNum(), templateHeader.getPageSize());
        return new PageInfo<TemplateHeader>(iTemplateHeaderService.queryTemplateHeaderPageByParam(templateHeader));
    }

    /**
     * Description 获取流程模板页面-分流服务
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     **/
    @GetMapping("/getShuntService")
    public Map<String, Object> getShuntServiceMap(){
       return WorkFLowCommonMethod.getShuntServiceMap();
    }

    /**
     * Description 获取流程模板页面-事件名称
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     **/
    @GetMapping("/getEventTypeOption")
    public Map<String, Object> getEventTypeOptionMap(){
        return WorkFLowCommonMethod.getEventTypeOptionMap();
    }


    /**
     * Description 保存流程模板信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    @PostMapping("/saveProcessTemplate")
    public BaseResult<String> saveProcessTemplate(@RequestBody ProcessTemplateDTO processTemplateDTO){
        try {
            return iTemplateHeaderService.saveProcessTemplate(processTemplateDTO);
        }catch (Exception e){
            return BaseResult.build(ResultCode.OPERATION_FAILED.getCode(),ResultCode.OPERATION_FAILED.getMessage());
        }
    }

    /**
     * Description 根据流程头ID获取流程头、行相关信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    @GetMapping("/queryProcessTemplateById")
    public TemplateHeaderDTO queryProcessTemplateById(Long templateHeadId){
        Assert.notNull(templateHeadId, FlowCommonConst.ID_IS_NOT_NULL);
        TemplateHeaderDTO templateHeader = new TemplateHeaderDTO();
        templateHeader.setTemplateHeadId(templateHeadId);
        return iTemplateHeaderService.queryProcessTemplateByParam(templateHeader);
    }

    /**
     * Description 修改流程模板信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    @PostMapping("/updateProcessTemplate")
    public BaseResult<String> updateProcessTemplate(@RequestBody ProcessTemplateDTO processTemplateDTO){
        BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
        try{
            return iTemplateHeaderService.updateProcessTemplate(processTemplateDTO);
        }catch (Exception e){
            return BaseResult.build(ResultCode.OPERATION_FAILED.getCode(),ResultCode.OPERATION_FAILED.getMessage());
        }
    }

    /**
     * Description 获取所有启动流程的菜单集合
     * @Param 
     * @return 
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.24
     * @throws 
     **/ 
    @GetMapping("/queryEnablePermission")
    public List<Map<String, Object>> queryEnablePermission() {
        List<Map<String, Object>> enablePermissionList = new ArrayList<Map<String, Object>>();
        Permission permission = new Permission();
        permission.setEnableWorkFlow(Enable.Y.toString());
        List<Permission> permissionList = rbacClient.queryEnablePermission(permission);
        if(CollectionUtils.isNotEmpty(permissionList)){
            for(Permission permissionEntity : permissionList){
                if(null != permissionEntity){
                    Map<String, Object> enablePermissionMap = new HashMap<>();
                    enablePermissionMap.put("functionName",permissionEntity.getFunctionName());
                    enablePermissionMap.put("functionId", permissionEntity.getFunctionId());
                    enablePermissionMap.put("functionCode", permissionEntity.getFunctionCode());
                    enablePermissionList.add(enablePermissionMap);
                }
            }
        }
        return enablePermissionList;
    }

    /**
     * Description 删除流程模板-事件信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    @PostMapping("/deleteTemplateLines")
    public String deleteTemplateLines(Long templateLinesId){
        Assert.notNull(templateLinesId, FlowCommonConst.ID_IS_NOT_NULL);
        String result = ResultCode.OPERATION_FAILED.getMessage();
        if(null != templateLinesId){
            try{
                TemplateLines templateLines = iTemplateLinesService.getById(templateLinesId);
                if(null != templateLines){
                    templateLines.setDeleteFlag(FlowCommonConst.DELETE_FLAG_YES);
                    LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                    String loginName = "";
                    String nickName = "";
                    if(null != loginAppUser) {
                        loginName = loginAppUser.getUsername();
                        nickName = loginAppUser.getNickname();
                    }
                    templateLines.setLastUpdatedBy(loginName);
                    templateLines.setLastUpdatedFullName(nickName);
                    templateLines.setLastUpdateDate(new Date());
                    boolean isUpdate = iTemplateLinesService.updateById(templateLines);
                    if(isUpdate){
                        result = ResultCode.SUCCESS.getMessage();
                    }
                }

            }catch (Exception e){
                LOGGER.error("删除流程模板-事件信息时报错：",e);
            }
        }
        return result;
    }

    /**
     * Description 修改单个流程行
     * @Param
     * @return 返回修改结果
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.22
     **/
    @PostMapping("/updateTemplateLines")
    public String updateTemplateLines(@RequestBody TemplateLines templateLines){
        String result = ResultCode.OPERATION_FAILED.getMessage();

        //不为空判断
        if(null == templateLines){
            result = FlowCommonConst.ID_IS_NOT_NULL;
            return result;
        }
        if( null == templateLines.getTemplateLinesId() || null == templateLines.getTemplateHeadId()){
            result = FlowCommonConst.ID_IS_NOT_NULL;
        }

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String loginName = "";
        String nickName = "";
        if(null != loginAppUser){
            loginName = loginAppUser.getUsername();
            nickName = loginAppUser.getNickname();
        }
        templateLines.setLastUpdateDate(new Date());
        templateLines.setLastUpdatedBy(loginName);
        templateLines.setLastUpdatedFullName(nickName);
        boolean isUpdate = iTemplateLinesService.updateById(templateLines);
        if(isUpdate){
            result = ResultCode.SUCCESS.getMessage();
        }
        return result;
    }

    /**
     * Description 是否有效设置
     * @Param templateHeaderId 流程头表ID,enableFlag是否有效
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.22
     **/
    @PostMapping("/updateEnableFlag")
    public BaseResult<String> updateEnableFlag(Long templateHeadId, String enableFlag){
        BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
        try{
            result = iTemplateHeaderService.updateEnableFlag(templateHeadId, enableFlag);

        }catch (BaseException e){
            return BaseResult.build(ResultCode.OPERATION_FAILED.getCode(),ResultCode.OPERATION_FAILED.getMessage());
        }
        return result;
    }

    /**
     * Description 判断流程模板是否已配置
     * @return
     * @Date 2020.04.22
     **/
    @GetMapping("/getCount")
    public int getCount(){
        return iTemplateHeaderService.count();
    }
}

package com.midea.cloud.flow.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IPUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.ResultListUtil;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.flow.common.constant.WorkFlowConst;
import com.midea.cloud.flow.common.method.WorkFLowCommonMethod;
import com.midea.cloud.flow.workflow.mapper.TemplateHeaderMapper;
import com.midea.cloud.flow.workflow.service.ITemplateHeaderService;
import com.midea.cloud.flow.workflow.service.ITemplateLinesService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.flow.process.dto.ProcessTemplateDTO;
import com.midea.cloud.srm.model.flow.process.dto.TemplateHeaderDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
import com.midea.cloud.srm.model.flow.process.entity.TemplateLines;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
*  <pre>
 *  工作流模板头表 服务实现类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 14:05:14
 *  修改内容:
 * </pre>
*/
@Service
public class TemplateHeaderServiceImpl extends ServiceImpl<TemplateHeaderMapper, TemplateHeader>
        implements ITemplateHeaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateHeaderServiceImpl.class);

    private static final String PROCESS_TEMPLATE_IS_NULL = "保存流程模板信息为空.";

    @Autowired
    private ITemplateLinesService iTemplateLinesService;

    /**rbac模块内部接口调用类**/
    @Autowired
    private RbacClient rbacClient;

    @Override
    public List<TemplateHeader> queryTemplateHeaderPageByParam(TemplateHeader templateHeader){
        QueryWrapper<TemplateHeader> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DELETE_FLAG", FlowCommonConst.DELETE_FLAG_NO);
        if(null != templateHeader) {
            Long templateId = templateHeader.getTemplateId();
            String templateCode = templateHeader.getTemplateCode();
            String modelId = templateHeader.getModelId();
            if(null != templateId) {
                queryWrapper.eq("TEMPLATE_ID", templateId);
            }
            if(StringUtils.isNotBlank(templateCode)) {
                queryWrapper.like("TEMPLATE_CODE", templateCode);
            }
            if(StringUtils.isNotBlank(modelId)) {
                queryWrapper.like("MODEL_ID", modelId);
            }
        }

        List<TemplateHeader> templateHeaderList = super.list(queryWrapper);
        Permission permission = new Permission();
        permission.setEnableWorkFlow(Enable.Y.toString());
        List<Permission> permissionList = rbacClient.queryEnablePermission(permission);
        String dataId = "templateId";
        String sourceId = "functionId";
        Map<String, String> requireFieldMap = new HashMap<>();
        requireFieldMap.put("templateName","functionName");
        HashMap<String, String> searchKeyMap = new HashMap<String, String>();
	    searchKeyMap.put("templateId", "functionId");
	    try {
            templateHeaderList = ResultListUtil.copyRequireFields(templateHeaderList, dataId, permissionList, sourceId, requireFieldMap);
        }catch (Exception e){
	        LOGGER.error("根据条件分页获取流程头-转化功能名称中文时报错: ",e);
	        throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return templateHeaderList;
    }

    public String getFdTemplateIdByParam(TemplateHeader templateHeader){
        String fdTemplateId = "";
        if(null != templateHeader){
            QueryWrapper<TemplateHeader> templateHeaderWrapper = new QueryWrapper<>(templateHeader);
            List<TemplateHeader> list = getBaseMapper().selectList(templateHeaderWrapper);
            if(!CollectionUtils.isEmpty(list)){
                TemplateHeader queryTemplateHeader = list.get(0);
                if(null != queryTemplateHeader){
                    fdTemplateId = queryTemplateHeader.getModelId();
                }
            }
        }
        return fdTemplateId;
    }

    @Transactional
    @Override
    public BaseResult<String> saveProcessTemplate(ProcessTemplateDTO processTemplate) {
        BaseResult<String> result = BaseResult.build(ResultCode.OPERATION_FAILED.getCode(), ResultCode.OPERATION_FAILED.getMessage());
        Long templateHeaderId = null; //流程头表ID
        if(null == processTemplate){
            LOGGER.error("流程模板保存流程头时报错:"+this.PROCESS_TEMPLATE_IS_NULL);
            Assert.isNull(ResultCode.MISSING_SERVLET_REQUEST_PART);
        }

        //单元测试用
/*        String localKey = "zh_CN";
        String loginName = "admin";
        String nickName = "SRM管理员";*/

        //获取用户信息
        String loginName = "";
        String nickName = "";
        Long userId = null;
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(null != loginAppUser){
            loginName = loginAppUser.getUsername();
            nickName = loginAppUser.getNickname();
            userId = loginAppUser.getUserId();
        }
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);
        Assert.notNull(userId, WorkFlowConst.USER_ID_NOT_NULL);

        String remoteIp = IPUtil.getRemoteIpAddr(HttpServletHolder.getRequest());
        templateHeaderId = IdGenrator.generate();
        Date nowDate = new Date();
        TemplateHeader templateHeader = processTemplate.getTemplateHeader();
        List<TemplateLines> templateLinesList = processTemplate.getTemplateLinesList();

        //新增时如果为空，则设置为有效
        if(StringUtils.isBlank(templateHeader.getEnableFlag())){
            templateHeader.setEnableFlag(Enable.Y.toString());
        }
        templateHeader.setEnableFlag(Enable.Y.toString());
        templateHeader.setCreationDate(nowDate);
        templateHeader.setCreatedBy(loginName);
        templateHeader.setCreatedFullName(nickName);
        templateHeader.setCreatedByIp(remoteIp);
        templateHeader.setCreatedId(userId);
        templateHeader.setLastUpdateDate(nowDate);
        templateHeader.setLastUpdatedBy(loginName);
        templateHeader.setLastUpdatedFullName(nickName);
//        templateHeader.setLanguage(localKey); //单元测试用
        try {
            String isRepeat = this.checkProcessTemplateRepeat(processTemplate); //判断模板Id和IflowId、事件处理过程是否重复
            if(StringUtils.isNotBlank(isRepeat)){
                result.setMessage(isRepeat);
                return result;
            }
            templateHeader.setTemplateHeadId(templateHeaderId); //SetTemplateHeaderId必须放到检测数据重复之前
            int insertCount = getBaseMapper().insert(templateHeader); //保存流程表头信息
            if(0 == insertCount){
                return result;
            }

            //保存流程行集合信息
            if(!CollectionUtils.isEmpty(templateLinesList)){
                for(TemplateLines templateLines : templateLinesList){
                    if(null != templateLines){
                        templateLines.setTemplateLinesId(IdGenrator.generate());
                        templateLines.setTemplateHeadId(templateHeaderId);
                        templateLines.setCreatedId(userId);
                        templateLines.setCreatedByIp(remoteIp);
                        templateLines.setCreatedBy(loginName);
                        templateLines.setCreatedFullName(nickName);
                        templateLines.setCreationDate(nowDate);
                        templateLines.setLastUpdateDate(nowDate);
                        templateLines.setLastUpdatedBy(loginName);
                        templateLines.setLastUpdatedFullName(nickName);
                    }
                }
                boolean isSaveLines = iTemplateLinesService.saveBatch(templateLinesList);
                if(!isSaveLines){
                    LOGGER.error("保存事件失败");
                    result.setMessage("保存事件失败");
                    return  result;
                }
            }
        }catch (Exception e){
            LOGGER.error("流程模板保存流程头时报错：",e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }

        result.setData(String.valueOf(templateHeaderId));
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    @Override
    public BaseResult<String> updateProcessTemplate(ProcessTemplateDTO processTemplate) throws BaseException {
        BaseResult<String> result = BaseResult.build(ResultCode.OPERATION_FAILED.getCode(), ResultCode.OPERATION_FAILED.getMessage());
        if(null == processTemplate){
            LOGGER.error("修改流程头时报错: ",this.PROCESS_TEMPLATE_IS_NULL);
            Assert.isNull(ResultCode.MISSING_SERVLET_REQUEST_PART);
        }
        //设置data里面为流程头ID
        result.setData(null != processTemplate.getTemplateHeader() ? String.valueOf(processTemplate.getTemplateHeader().getTemplateHeadId()) : "");

        //单元测试用
/*        String localKey = "zh_CN";
        String loginName = "admin";
        String nickName = "SRM管理员";*/

        String loginName = "";
        String nickName = "";
        Long userId = null;
        //获取用户信息
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(null != loginAppUser){
            loginName = loginAppUser.getUsername();
            nickName = loginAppUser.getNickname();
            userId = loginAppUser.getUserId();
        }
        Assert.notNull(loginName, WorkFlowConst.LOGIN_NAME_NOT_NULL);
        Assert.notNull(userId, WorkFlowConst.USER_ID_NOT_NULL);

        Date nowDate = new Date();
        TemplateHeader templateHeader = processTemplate.getTemplateHeader();
        if(null == templateHeader && null == templateHeader.getTemplateHeadId()){
            Assert.isNull(ResultCode.MISSING_SERVLET_REQUEST_PARAMETER);
        }
        if(null == templateHeader.getTemplateHeadId()){
            Assert.isNull(FlowCommonConst.ID_IS_NOT_NULL);
        }

        //修改流程头信息
        Long tempHeaderId = templateHeader.getTemplateHeadId();
        List<TemplateLines> templateLinesList = processTemplate.getTemplateLinesList();
        templateHeader.setLastUpdateDate(nowDate);
        templateHeader.setLastUpdatedBy(loginName);
        templateHeader.setLastUpdatedFullName(nickName);
//        templateHeader.setLanguage(localKey);   //单元测试用
        try{
            String checkRepeat = this.checkProcessTemplateRepeat(processTemplate);
            if(StringUtils.isNotBlank(checkRepeat)){
                result.setMessage(checkRepeat);
                return result;
            }
            int updateCount = getBaseMapper().updateById(templateHeader);
            if(0 == updateCount){
                return result;
            }

        }catch (Exception e){
            LOGGER.error("修改流程头时报错: ",e);
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }

        //保存或修改流程行信息
        String remoteIp = IPUtil.getRemoteIpAddr(HttpServletHolder.getRequest());
        List<TemplateLines> saveTemplateLines = new ArrayList<>(); //新增流程行集合
        List<TemplateLines> updateTemplateLines = new ArrayList<>(); //修改流程行集合
        if(!CollectionUtils.isEmpty(templateLinesList)){
            for(TemplateLines templateLines : templateLinesList){
                if(null != templateLines){
                    templateLines.setLastUpdateDate(nowDate);
                    templateLines.setLastUpdatedBy(loginName);
                    templateLines.setLastUpdatedFullName(nickName);
                    if(null == templateLines.getTemplateHeadId()){
                        templateLines.setTemplateHeadId(tempHeaderId);
                    }

                    if(null == templateLines.getTemplateLinesId()){  //新增操作
                        templateLines.setTemplateLinesId(IdGenrator.generate());
                        templateLines.setCreatedId(userId);
                        templateLines.setCreatedByIp(remoteIp);
                        templateLines.setCreatedBy(loginName);
                        templateLines.setCreatedFullName(nickName);
                        templateLines.setCreationDate(nowDate);
                        templateLines.setLastUpdateDate(nowDate);
                        templateLines.setLastUpdatedBy(loginName);
                        templateLines.setLastUpdatedFullName(nickName);
                        saveTemplateLines.add(templateLines);
                    }else{ //修改操作
                        updateTemplateLines.add(templateLines);
                    }
                }
            }

            //保存流程行信息
            if(!CollectionUtils.isEmpty(saveTemplateLines)){
                try {
                    iTemplateLinesService.saveBatch(saveTemplateLines);
                }catch (Exception e){
                    LOGGER.error("修改流程模板，新增流程行信息时报错.",e);
                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                }
            }

            //修改流程行信息
            if(!CollectionUtils.isEmpty(updateTemplateLines)){
                try{
                    iTemplateLinesService.updateBatchById(updateTemplateLines);
                }catch (Exception e){
                    LOGGER.error("修改流程模板，修改流程行信息时报错.",e);
                    throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
                }
            }

        }

        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    /**
     * Description 保存/修改时判断模板Id和IflowId、事件处理过程是否重复，如果重复则返回重复提示信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.11
     * @throws
     **/
    private String checkProcessTemplateRepeat(ProcessTemplateDTO processTemplateDTO){
        String repeatStr = "";
        if(null != processTemplateDTO && null != processTemplateDTO.getTemplateHeader()){
            TemplateHeader templateHeader = processTemplateDTO.getTemplateHeader();
            Long headerId = templateHeader.getTemplateHeadId();

            //根据modelId和functionId查询流程模板记录，并判断是否重复
            String flowId = templateHeader.getModelId();
            String templateCode = templateHeader.getTemplateCode();
            if(StringUtils.isNotBlank(templateCode)){
                TemplateHeader queryHeader = new TemplateHeader();
                queryHeader.setEnableFlag(Enable.Y.toString());
                queryHeader.setTemplateCode(templateCode);
                List<TemplateHeader> headerList = getBaseMapper().selectList(new QueryWrapper(queryHeader));

                /**判断流程模板名称记录是否重复**/
                if(null == headerId && !CollectionUtils.isEmpty(headerList) && headerList.size() >= 1) { //新增
                    repeatStr = "已经启用的流程模板编码不能重复,请重新输入流程模板编码";
                    return repeatStr;
                }else if(null != headerId && !CollectionUtils.isEmpty(headerList) && headerList.size() > 1) { //修改
                    repeatStr = "已经启用的流程模板编码不能重复,请重新输入流程模板编码";
                    return repeatStr;
                }

                queryHeader = new TemplateHeader();
                queryHeader.setEnableFlag(Enable.Y.toString());
                queryHeader.setModelId(flowId);
                headerList = getBaseMapper().selectList(new QueryWrapper(queryHeader));
                /**判断Iflow_FDID记录是否重复**/
                if(null == headerId && !CollectionUtils.isEmpty(headerList) && headerList.size() >= 1) { //新增
                    repeatStr = "已经启用的Iflow_FDID不能重复,请重新输入Iflow_FDID";
                    return repeatStr;
                }else if(null != headerId && !CollectionUtils.isEmpty(headerList) && headerList.size() > 1) { //修改
                    repeatStr = "已经启用的Iflow_FDID不能重复,请重新输入Iflow_FDID";
                    return repeatStr;
                }
            }

            //判断时间处理过程是否重复，如果重复，则返回重复提示信息
//            StringBuilder linesRepeatTip = new StringBuilder();
            List<TemplateLines> linesList = processTemplateDTO.getTemplateLinesList();
            if(!CollectionUtils.isEmpty(linesList)){
                for(TemplateLines lines : linesList){
                    Long linesId = lines.getTemplateLinesId();
                    //如果已经有提示重复了，直接跳出循环
                    if(StringUtils.isNotBlank(repeatStr)){
                        break;
                    }
                    if(null != lines && StringUtils.isNotBlank(lines.getBussinessFunction())){
                        String eventFunction = lines.getBussinessFunction();
                        for(TemplateLines repeatLines : linesList) {
                            boolean linesIdIsQuals = true;  //初始化行表Id相等为true
                            if(null != linesId && !String.valueOf(linesId).equals(String.valueOf(repeatLines.getTemplateLinesId()))){
                                linesIdIsQuals = false;
                            }
                            if(!linesIdIsQuals && eventFunction.equals(repeatLines.getBussinessFunction())){
                                //多提示拼接的国际化现在不支持显示
/*                                if(!linesRepeatTip.toString().contains(WorkFLowCommonMethod.getEventTypeByKey(eventFunction)+"不能重复,请重新输入.")) {
                                    linesRepeatTip.append(WorkFLowCommonMethod.getEventTypeByKey(eventFunction) + "不能重复,请重新输入.");
                                }*/
                                repeatStr = WorkFLowCommonMethod.getEventTypeByKey(eventFunction) + "不能重复,请重新输入.";
                                break;
                            }
                        }
                    }

                }
//                repeatStr = linesRepeatTip.toString();
            }
        }
        return repeatStr;
    }

    @Override
    public TemplateHeaderDTO queryProcessTemplateByParam(TemplateHeaderDTO templateHeader) {
        TemplateHeaderDTO queryTemplateHeader = new TemplateHeaderDTO();
        try {
            templateHeader.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO); //查询不删除的记录
            queryTemplateHeader = getBaseMapper().queryProcessTemplateByParam(templateHeader);

            //根据templateId获取templateName
            if(null != queryTemplateHeader && null != queryTemplateHeader.getTemplateId()) {
                Permission permission = new Permission();
                permission.setFunctionId(queryTemplateHeader.getTemplateId());
                List<Permission> permissionList = rbacClient.queryEnablePermission(permission);
                if(!CollectionUtils.isEmpty(permissionList) && null != permissionList.get(0)){
                    queryTemplateHeader.setTemplateName(permissionList.get(0).getFunctionName());
                }
            }

        }catch (Exception e){
            LOGGER.error("根据条件获取流程头、行相关信息时报错：",e);
            throw new BaseException("根据条件获取流程头、行相关信息时报错："+e);
        }
        return queryTemplateHeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<String> updateEnableFlag(Long templateHeadId, String enableFlag) throws BaseException{
        BaseResult<String> result = BaseResult.build(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
        if(null == templateHeadId && StringUtils.isBlank(enableFlag)){
            Assert.isNull(ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        }

        try {
            TemplateHeader templateHeader = getBaseMapper().selectById(templateHeadId);
            if (null != templateHeader) {
                templateHeader.setEnableFlag(enableFlag);
                ProcessTemplateDTO processTemplateDTO = new ProcessTemplateDTO();
                processTemplateDTO.setTemplateHeader(templateHeader);
                String checkRepeat = this.checkProcessTemplateRepeat(processTemplateDTO);
                if(StringUtils.isNotBlank(checkRepeat)){
                    result = BaseResult.build(ResultCode.OPERATION_FAILED.getCode(), checkRepeat);
                }
                templateHeader.setLastUpdateDate(new Date());
                LoginAppUser user = AppUserUtil.getLoginAppUser();
                if (null != user) {
                    templateHeader.setLastUpdatedBy(user.getUsername());
                    templateHeader.setLastUpdatedFullName(user.getNickname());
                }
                getBaseMapper().updateById(templateHeader);
            }
        }catch (Exception e){
            throw new BaseException("是否有效设置操作ID为{}时报错:{}",enableFlag,e);
        }
        return result;
    }

    @Override
    public TemplateHeaderDTO queryProcessTemplateNotTemplateNameByParam(TemplateHeaderDTO templateHeader) {
        TemplateHeaderDTO queryTemplateHeader = new TemplateHeaderDTO();
        try {
            templateHeader.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO); //查询不删除的记录
            queryTemplateHeader = getBaseMapper().queryProcessTemplateByParam(templateHeader);
        }catch (Exception e){
            LOGGER.error("根据条件获取流程头、行相关信息时(不需要TemplateName)报错：",e);
            throw new BaseException("根据条件获取流程头、行相关信息时(不需要TemplateName)报错："+e);
        }
        return queryTemplateHeader;
    }

}

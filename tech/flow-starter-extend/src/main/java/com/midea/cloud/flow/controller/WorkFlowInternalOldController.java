package com.midea.cloud.flow.controller;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.feign.supplierauth.SupplierAuthClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.flow.cbpm.CbpmProcessNodesListDTO;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 工作流共用内部调用Controller 类，保留只是为了不报错，里面的逻辑没有实际用途---lizl7
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/17 16:00
 *  修改内容:
 * </pre>
 */
@RestController
@Slf4j
public class WorkFlowInternalOldController extends BaseController {

    /**
     * Description 初始化流程(并保存流程实例化表)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.17
     **/
    @PostMapping("/flow-anon/internal/initWorkFlow")
    public Map<String, Object> initProcess(@RequestBody CbpmRquestParamDTO cbpmRquestParam)  {
        Map<String, Object> resultMap = new HashMap<>();
        return resultMap;
    }

    /**
     * Description 判断是否启动工作流
     * @Param menuId 菜单ID，functionId-功能ID，templateCode-流程模板Code
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.22
     * @throws BaseException
     **/
    @GetMapping("/flow-anon/internal/getFlowEnable")
    public boolean getFlowEnable(Long menuId, Long functionId, String templateCode) throws BaseException{
        return false;
    }

}

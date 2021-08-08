package com.midea.cloud.srm.feign.flow;

import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <pre>
 *  工作流模块Feign，保留只是为了不报错，里面的逻辑没有实际用途---lizl7
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/03/28
 *  修改内容:
 * </pre>
 */
// 转到base模块，以starter方式
//@FeignClient("flow-center")
@FeignClient(value = "cloud-biz-base", contextId  = "WorkFlowFeign")
public interface WorkFlowFeign {
    /**
     * Description 初始化流程-内部调用(并保存流程实例化表)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.17
     * @throws
     **/
    @PostMapping("/flow-anon/internal/initWorkFlow")
    Map<String, Object> initProcess(@RequestBody CbpmRquestParamDTO cbpmRquestParam);

    /**
     * Description 判断是否启动工作流
     * @Param menuId 菜单ID，functionId-功能ID，templateCode-流程模板Code
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.22
     * @throws FeignException
     **/
    @GetMapping("/flow-anon/internal/getFlowEnable")
    boolean getFlowEnable(@RequestParam("menuId") Long menuId,@RequestParam("functionId")  Long functionId,
                                 @RequestParam("templateCode")  String templateCode);


    /**
     *获取流程数量
     */
    @GetMapping("/flow/processTemplent/getCount")
    int getCount();
}

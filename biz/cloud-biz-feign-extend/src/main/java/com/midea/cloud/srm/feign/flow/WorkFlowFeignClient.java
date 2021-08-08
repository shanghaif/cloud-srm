package com.midea.cloud.srm.feign.flow;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * <pre>
 *  工作流回调方法接口Feign
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
public interface WorkFlowFeignClient {
    /**工作流各个模块Client需要暴露的外部接口url*/
    @RequestMapping("/feignServerCbpm/cbpmBaseFeignHandler")
    Object cbpmBaseFeignHandler(@RequestBody Map<String, Object> eventParamMap) throws Exception;

//    @RequestMapping("/feignServerMip/getFormData")
//    public Object getFormData(@RequestBody Map<String, Object> paramMap);
//
//    @RequestMapping("/feignServerMip/getMobileFormData")
//    public Object getMobileFormData(@RequestBody Map<String, Object> paramMap);

}

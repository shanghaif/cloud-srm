package com.midea.cloud.srm.bargaining.flow.controller;

import com.midea.cloud.srm.feign.flow.CbpmBaseFeign;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <pre>
 *  招投标模块工作流对接FeignController
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/18
 *  修改内容:
 * </pre>
 */
@RestController
public class CbpmBidFeignController extends CbpmBaseFeign {

    /**Cbpm事件回调Handler共用类*/
    @RequestMapping("/feignServerCbpm/cbpmBaseFeignHandler")
    public Map<String, Object> cbpmBaseFeignHandler(@RequestBody Map<String, Object> eventParamMap) throws Exception{
        return super.cbpmBaseFeignHandler(eventParamMap);
    }

}

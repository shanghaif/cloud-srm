package com.midea.cloud.srm.inq.flow.controller;

import com.midea.cloud.srm.feign.flow.CbpmBaseFeign;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-20 9:42
 *  修改内容:
 * </pre>
 */
@RestController
public class CbpmInqFeignController extends CbpmBaseFeign {

    /**Cbpm事件回调Handler共用类*/
    @RequestMapping("/feignServerCbpm/cbpmBaseFeignHandler")
    public Map<String, Object> cbpmBaseFeignHandler(@RequestBody Map<String, Object> eventParamMap) throws Exception{
        return super.cbpmBaseFeignHandler(eventParamMap);
    }
}
package com.midea.cloud.srm.sup.flow.controller;

import com.midea.cloud.srm.feign.flow.CbpmBaseFeign;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CbpmSupplierFeignController extends CbpmBaseFeign {

    /**Cbpm事件回调Handler共用类*/
    @RequestMapping("/feignServerCbpm/cbpmBaseFeignHandler")
    public Map<String, Object> cbpmBaseFeignHandler(@RequestBody Map<String, Object> eventParamMap) throws Exception{
        return super.cbpmBaseFeignHandler(eventParamMap);
    }

}
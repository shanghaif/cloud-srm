package com.midea.cloud.srm.feign.api;

import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@FeignClient(value = "api-center")
public interface ApiClient {

	@RequestMapping("/interface/send")
    Object send(@RequestParam("interfaceCode") String interfaceCode,@RequestParam("params") Map params,HttpServletResponse httpServletResponse);

    /**
     * 创建对接外部系统接口日志
     * @param interfaceLog
     */
    @PostMapping("/interfacelog/add")
    void createInterfaceLog(@RequestBody InterfaceLogDTO interfaceLog);

    /**
     * 创建对接外部系统接口日志(anon调用)
     * @param interfaceLog
     */
    @PostMapping("/api-anon/internal/interfacelog/addForAnon")
    void createInterfaceLogForAnon(@RequestBody InterfaceLogDTO interfaceLog);

    @GetMapping("/api-anon/external/interface/DeliverPlanJob")
    void DeliverPlanJob();

    @GetMapping("/api-anon/external/interface/DeliverPlanAffirm")
    void DeliverPlanAffirm(@RequestParam("deliverPlanNum")String deliverPlanNum);

    /**
     * 添加日志或再次发送
     * @param interfaceLog
     */
    @PostMapping("/api-anon/interfacelog/createInterfaceLogAnon")
    void createInterfaceLogAnon(@RequestBody InterfaceLogDTO interfaceLog);
}

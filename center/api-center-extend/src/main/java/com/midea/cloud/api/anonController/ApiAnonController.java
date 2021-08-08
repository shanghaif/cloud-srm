package com.midea.cloud.api.anonController;

import com.midea.cloud.api.interfacelog.service.IInterfaceLogService;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/18
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/api-anon/interfacelog")
public class ApiAnonController {

    @Autowired
    private IInterfaceLogService iInterfaceLogService;

    /**
     * 添加日志或再次发送
     * @param interfaceLog
     */
    @PostMapping("/createInterfaceLogAnon")
    public void createInterfaceLogAnon(@RequestBody InterfaceLogDTO interfaceLog) {
        iInterfaceLogService.createInterfaceLog(interfaceLog);
    }
}

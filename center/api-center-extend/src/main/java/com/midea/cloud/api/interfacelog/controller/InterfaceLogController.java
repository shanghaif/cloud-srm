package com.midea.cloud.api.interfacelog.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.api.interfacelog.service.IInterfaceLogLineService;
import com.midea.cloud.api.interfacelog.service.IInterfaceLogService;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.api.interfacelog.entity.InterfaceLog;
import com.midea.cloud.srm.model.api.interfacelog.entity.InterfaceLogLine;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  接口日志表 前端控制器
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 10:58:43
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/interfacelog")
public class InterfaceLogController extends BaseController {

    @Autowired
    private IInterfaceLogService iInterfaceLogService;
    @Autowired
    private IInterfaceLogLineService iInterfaceLogLineService;

    private RestTemplate restTemplate = new RestTemplate();
    
    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public InterfaceLogDTO get(@RequestParam("logId") Long logId) {
        Assert.notNull(logId, "logId不能为空");
        InterfaceLogDTO dto = new InterfaceLogDTO();
        InterfaceLog log = iInterfaceLogService.getById(logId);
        dto.setServiceName(log.getServiceName());
        dto.setServiceType(log.getServiceType());
        dto.setType(log.getType());
        dto.setBillId(log.getBillId());
        dto.setBillType(log.getBillType());
        dto.setDealTime(log.getDealTime());
        dto.setFinishDate(log.getFinishDate());
        dto.setLogId(dto.getLogId());
        dto.setStatus(log.getStatus());
        dto.setTargetSys(log.getTargetSys());
        InterfaceLogLine line = this.iInterfaceLogLineService.getById(log.getLineLogId());
        dto.setServiceInfo(line.getServiceInfo());
        dto.setReturnInfo(line.getReturnInfo());
        dto.setErrorInfo(line.getErrorInfo());
        return dto;
    }

    /**
    * 添加日志或再次发送
    * @param interfaceLog
    */
    @PostMapping("/add")
    public void add(@RequestBody InterfaceLogDTO interfaceLog) {
    	iInterfaceLogService.createInterfaceLog(interfaceLog);
    }

    @GetMapping("/sendAgain")
    public void sendAgain(Long logId) {
    	InterfaceLog log = iInterfaceLogService.getById(logId);
    	InterfaceLogLine line = this.iInterfaceLogLineService.getById(log.getLineLogId());
    	InterfaceLogDTO dto = new InterfaceLogDTO();
		dto.setLogId(log.getLogId());
		dto.setServiceInfo(line.getServiceInfo());
		dto.setUrl(log.getUrl());
    	try {
    		BaseResult result = this.httpSend(dto);
        	if (result.getCode().equals(ResultCode.SUCCESS.getCode())) {
        		dto.setFinishDate(new Date());
        		dto.setStatus("SUCCESS");
        		dto.setReturnInfo(JSON.toJSONString(result));
        	} else {
        		dto.setStatus("FAIL");
        		dto.setReturnInfo(JSON.toJSONString(result));
        		dto.setErrorInfo(result.getMessage());
        	}
		} catch (Exception e) {
			dto.setStatus("FAIL");
    		dto.setErrorInfo(e.getMessage());
		}
    	this.iInterfaceLogService.createInterfaceLog(dto);
    }
    
    @PostMapping("/send")
    public BaseResult send() {
    	String url="http://localhost:9005/cloud-srm/api-base/base-anon/external/externalOrder/getAccountNum";
        InterfaceLogDTO dto = new InterfaceLogDTO();
        dto.setServiceName("外部订单数接口");
        dto.setServiceType("HTTP");
        dto.setType("SEND");
        dto.setUrl(url);
//        dto.setServiceInfo(serviceInfo);
//        dto = this.iInterfaceLogService.createInterfaceLog(dto);
        BaseResult result = null;
        try {
        	result = httpSend(dto);
        	if (result.getCode().equals(ResultCode.SUCCESS.getCode())) {
        		dto.setFinishDate(new Date());
        		dto.setStatus("SUCCESS");
        		dto.setReturnInfo(JSON.toJSONString(result));
        	} else {
        		dto.setFinishDate(new Date());
        		dto.setStatus("FAIL");
        		dto.setReturnInfo(JSON.toJSONString(result));
        		dto.setErrorInfo(result.getMessage());
        	}
		} catch (Exception e) {
			dto.setFinishDate(new Date());
    		dto.setStatus("FAIL");
    		dto.setErrorInfo(e.getMessage());
    		result =  new BaseResult();
    		result.setCode(ResultCode.RPC_ERROR.getCode());
    		result.setMessage(e.getMessage());
		}
        this.iInterfaceLogService.createInterfaceLog(dto);
        return  result;
    }
    
    private BaseResult httpSend(InterfaceLogDTO dto) throws Exception{
    	 MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
         HttpHeaders header = new HttpHeaders();
         // 需求需要传参为form-data格式
         header.setContentType(MediaType.MULTIPART_FORM_DATA);
         header.set("appId", "paas");
         header.set("timestamp", "123456987");
         header.set("sign", "99d04e92b87719d06a6d2cb997cca0f3");
         HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, header);
         BaseResult result = restTemplate.postForObject(dto.getUrl(), httpEntity, BaseResult.class);
         return result;
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iInterfaceLogService.removeById(id);
    }
    
    /**
     * 接口调用返回后修改日志记录
     * @param interfaceLog
     */
     @PostMapping("/update")
     public void update(@RequestBody InterfaceLogDTO interfaceLog) {
    	 iInterfaceLogService.updateInterfaceLog(interfaceLog);
     }
     
     

    /**
    * 修改
    * @param interfaceLog
    */
    @PostMapping("/modify")
    public void modify(@RequestBody InterfaceLog interfaceLog) {
        iInterfaceLogService.updateById(interfaceLog);
    }

    /**
    * 分页查询
    * @param interfaceLog
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<InterfaceLog> listPage(@RequestBody InterfaceLogDTO interfaceLog) {
        PageUtil.startPage(interfaceLog.getPageNum(), interfaceLog.getPageSize());
        String billId = interfaceLog.getBillId();
        interfaceLog.setBillId(null);
        QueryWrapper<InterfaceLog> wrapper = new QueryWrapper<InterfaceLog>(interfaceLog);
        
        
        if (StringUtils.isNoneBlank(billId)) {
        	wrapper.like("BILL_ID", billId);
        }
        if (null != interfaceLog.getCreationDateBegin()) {
        	wrapper.ge("CREATION_DATE", interfaceLog.getCreationDateBegin());
        }
        if (null != interfaceLog.getCreationDateEnd()) {
        	wrapper.le("CREATION_DATE", interfaceLog.getCreationDateEnd());
        }
        if (StringUtils.isNoneBlank(interfaceLog.getServiceInfo())) {
        	wrapper.inSql("LOG_ID", "SELECT LOG_ID FROM scc_api_interface_log_line where service_info like '%"+interfaceLog.getServiceInfo()+"%' ");
        }
        wrapper.orderByDesc("CREATION_DATE");
//        throw new BaseException("测试");
        return new PageInfo<InterfaceLog>(iInterfaceLogService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<InterfaceLog> listAll() { 
        return iInterfaceLogService.list();
    }
 
}

package com.midea.cloud.srm.model.api.interfacelog.dto;

import java.util.Date;

import com.midea.cloud.srm.model.api.interfacelog.entity.InterfaceLog;

import lombok.Data;

@Data
public class InterfaceLogDTO extends InterfaceLog {
    /**
     * 创建时间开始
     */
    private Date creationDateBegin;
    /**
     * 创建时间结束
     */
    private Date creationDateEnd;
	
}

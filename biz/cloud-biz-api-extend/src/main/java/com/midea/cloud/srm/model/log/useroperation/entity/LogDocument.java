package com.midea.cloud.srm.model.log.useroperation.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author tanjl11
 * @date 2020/12/18 9:51
 */
@Data
public class LogDocument {
    private Long operationLogId;
    private String nickname;
    private String username;
    private String userType;
    private String methodName;
    private String operationTime;
    private String requestIp;
    private String requestUrl;
    private String requestParam;
    private String responseResult;
    private String resultStatus;
    private String errorInfo;
    private String model;
    private String requestStartTime;
    private String requestEndTime;
    private Long responseDate;
    private String logInfo;
    private Integer pageNum; // 页码
    private Integer pageSize;
    private String operationTimeEnd;
    private String operationTimeStart;
    private long interval;
    private String sql;
    private String creationDate;
    //调用链
    private String traceId;
    private String spanId;
    private String parentSpanId;
    

}
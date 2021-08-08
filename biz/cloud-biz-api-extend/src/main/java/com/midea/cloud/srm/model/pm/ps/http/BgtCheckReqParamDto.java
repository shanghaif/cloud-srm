package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *    预算冻结/释放DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 13:30
 *  修改内容:
 * </pre>
 */
@Data
public class BgtCheckReqParamDto implements Serializable {

    private String priKey;//记录唯一关键字   采购申请头id

    private String sourceSystem;//来源系统编码

    private String groupCode;//集团编码

    private String templateCode;//模板编码  Budget2020

    private String documentNum;//单据号  //采购单号

    private String budgetUseStatus;//预算使用状态  //p

    private String documentCreateBy;//申请人工号

    private String documentDescription;//摘要

    private String transferTime;//提单日期  //当前日期

    private List<Line> lineList;//请求行

    private String ignore;//预算告警忽略标识

}

package com.midea.cloud.srm.model.base.ou.dto.query;

import lombok.Data;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 20:21
 *  修改内容:
 * </pre>
 */
@Data
public class BaseOuGroupQueryDTO {
    private String ouCode;
    private String ouName;
    private String ouGroupName;
    private String ouGroupCode;
    private String groupStatus;
    private String ouId;
    private int pageSize;
    private int pageNum;


}

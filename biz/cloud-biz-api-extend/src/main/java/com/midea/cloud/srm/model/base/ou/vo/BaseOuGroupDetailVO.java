package com.midea.cloud.srm.model.base.ou.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

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
 *  修改日期: 2020/9/4 20:00
 *  修改内容:
 * </pre>
 */
@Data
public class BaseOuGroupDetailVO {

    private Long ouGroupId;

    /**
     * OU组名称
     */
    private String ouGroupName;

    /**
     * OU组编码
     */
    private String ouGroupCode;
    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;
    /**
     * 状态
     */
    private String groupStatus;
    /**
     * ou组详情
     */
    private List<BaseOuDetailVO> details;
}

package com.midea.cloud.srm.model.flow.query.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 * 功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-22 11:37
 * 修改内容:
 * </pre>
 */
@Data
public class WorkbenchMyTaskDTO extends BaseDTO {

    private Long formId;// 单据id
    private String moduleName;// 功能名称
    private String formNo;// 单据号
    private String title;// 标题
    private String createName;// 创建人
    private Date creationDate;// 创建时间
    private String residenceTime;// 停留时间
    private Long userId;// 创建人或受理人id
    private String userName;// 账号
    private String formStatus;// 单据状态
    private String queryType;// 查询类型：待办-process 已办-worked 我启动-start
    private String ifMove; //是否是移动端
    private Long vendorId; //供应商ID

}

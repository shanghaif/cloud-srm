package com.midea.cloud.srm.model.flow.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 工作台我的任务vo
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-22 19:45
 * 修改内容:
 * </pre>
 */

@Data
public class WorkbenchMyTaskVo implements Serializable {

    private Long formId;// 单据id
    private String moduleName;// 功能名称
    private String formNo;// 单据号
    private String title;// 标题
    private String createName;// 创建人
    private Date creationDate;// 创建时间
    private String residenceTime;// 停留时间
    private String searchUrl;// 查询链接
}

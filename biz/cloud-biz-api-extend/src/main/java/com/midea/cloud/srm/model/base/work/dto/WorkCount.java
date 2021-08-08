package com.midea.cloud.srm.model.base.work.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * <pre>
 * 供应商我的任务数量统计
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-24 16:31
 * 修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class WorkCount {
    private static final long serialVersionUID = 1L;
    private String title;
    private String url;
    private Integer count;
    private String listName;// 单据列表名称
    private Integer sort;// 排序
    private Map condition;//查询条件值
}

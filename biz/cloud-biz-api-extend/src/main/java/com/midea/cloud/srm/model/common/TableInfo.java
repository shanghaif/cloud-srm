package com.midea.cloud.srm.model.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  数据库表信息
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/28 20:17
 *  修改内容:
 * </pre>
 */
@Data
public class TableInfo implements Serializable {
    //表名
    private String tableName;
    //字段
    List<TableField> tableFields;
}

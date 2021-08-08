package com.midea.cloud.srm.model.common;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *   数据库表字段
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/28 20:15
 *  修改内容:
 * </pre>
 */
@Data
public class TableField implements Serializable {
    //所属表明
    private String tableName;
    //字段名称
    private String columnName;
    //字段类型
    private String dataType;
}

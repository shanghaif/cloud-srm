package com.midea.cloud.srm.model.pm.ps.http;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/28 14:03
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class Line implements Serializable {

    private String lineId;//行唯一主键

    private String documentLineNum;//行编号

    private String documentLineAmount;//冻结金额

    private String templateCode;//模板编码

    private String segment29;//HR部门编码

    private String segment30;//业务小类编码

    private String ignore;//预算告警忽略标识

}

package com.midea.cloud.srm.model.flow.cbpm;

import lombok.Data;

/**
 * <pre>
 * 流程节点处理人-子参数
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/27 23:38
 *  修改内容:
 * </pre>
 */
@Data
public class ProcessHandlersDTO {
    /**处理人类型(USER-表示用户，DEPARTMENT表示部门)*/
    private String type;
    /**处理人账号(type为USER-填写用户名，值为DEPARTMENT时填写部门ID)*/
    private String id;
}

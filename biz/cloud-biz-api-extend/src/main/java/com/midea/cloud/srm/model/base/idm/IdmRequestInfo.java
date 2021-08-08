package com.midea.cloud.srm.model.base.idm;

import lombok.Data;

/**
 * <pre>
 * Idm接口RequestInfo(包括用户表集合)
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/24 18:51
 *  修改内容:
 * </pre>
 */
@Data
public class IdmRequestInfo {
    /**隆基IDm接口用户集合实体类*/
    private IdmEmployees Employees;
}

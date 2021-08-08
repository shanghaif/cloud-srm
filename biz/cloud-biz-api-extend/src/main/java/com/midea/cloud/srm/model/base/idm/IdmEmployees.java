package com.midea.cloud.srm.model.base.idm;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  隆基IDm接口用户集合实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/24 17:33
 *  修改内容:
 * </pre>
 */
@Data
public class IdmEmployees {
    /**
     * 应用编码 xapms
     */
    private String SysCode;
    /**
     * 隆基IDm接口用户实体类
     */
//    List<IdmEmployee> Employee;
    List<Map<String, Object>> Employee;
}
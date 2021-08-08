package com.midea.cloud.srm.model.cm.contract.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/30
 *  修改内容:
 * </pre>
 */
@Data
public class BulkMaintenanceFrameworkParamDto implements Serializable {
    private static final long serialVersionUID = 8705004178105118918L;
    /**
     * 合同id列表
     */

    private List<Long> contractIds;

    /**
     * 合同头信息ID
     */
    private Long contractHeadId;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同编号
     */
    private String contractCode;
}

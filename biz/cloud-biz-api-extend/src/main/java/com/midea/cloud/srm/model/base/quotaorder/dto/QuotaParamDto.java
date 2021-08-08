package com.midea.cloud.srm.model.base.quotaorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
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
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuotaParamDto implements Serializable {
    /**
     * 组织ID集合
     */
    private List<Long> orgIds;
    /**
     * 物料ID集合
     */
    private List<Long> itemIds;

    /**
     * 组织id
     */
    private Long orgId;
    /**
     * 物料id
     */
    private Long itemId;
    /**
     * 供应商id集合
     */
    private List<Long> vendorIds;

    /**
     * 需求日期
     */
    private Date requirementDate;
}

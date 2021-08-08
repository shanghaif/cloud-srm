package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/7 16:31
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class RequirermentLineQueryDTO extends RequirementLine {
    private LocalDate startDate;

    private LocalDate endDate;

    /**
     * 物料小类编码或物料小类名称
     */
    private String categoryKey;

    /**
     * 采购类型
     */
    private String purchaseType;

    /**
     * 采购类型（）
     */
    private List<String> purchaseTypeList;

    private List<Long> requirementLineIds;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 业务实体id
     */
    private Long ceeaOrgId;

    /**
     * 供应商组织品类关系
     */
    private List<OrgCategory> orgCategoryList;

}

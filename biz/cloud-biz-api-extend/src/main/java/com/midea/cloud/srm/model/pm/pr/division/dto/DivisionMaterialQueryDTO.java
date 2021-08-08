package com.midea.cloud.srm.model.pm.pr.division.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称  物料分工规则查询DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/23 14:56
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class DivisionMaterialQueryDTO extends BaseDTO{

    /**
     * 主键,物料分工规则ID
     */
    private Long divisionMaterialId;

    /**
     * 业务实体ID
     */
    private Long orgId;

    /**
     * 业务实体编码
     */
    private String orgCode;

    /**
     * 业务实体名称
     */
    private String orgName;

    /**
     * 库存组织ID
     */
    private Long organizationId;

    /**
     * 库存组织编码
     */
    private String organizationCode;

    /**
     * 库存组织名称
     */
    private String organizationName;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 供应商管理采购员ID
     */
    private Long supUserId;

    /**
     * 供应商管理采购员名称
     */
    private String supUserNickname;

    /**
     * 策略负责采购员ID
     */
    private Long strategyUserId;

    /**
     * 策略负责采购员名称
     */
    private String strategyUserNickname;

    /**
     * 采购履行采购员ID
     */
    private Long performUserId;

    /**
     * 采购履行采购员名称
     */
    private String performUserNickname;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    private LocalDate endDate;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 业务主体id集
     */
    private List<Long> orgIds;

    /**
     * 库存组织id集
     */
    private List<Long> organizationIds;

    /**
     * 品类id集
     */
    private List<Long> materialIds;

    /**
     * 是否生效
     */
    private String enable;

}

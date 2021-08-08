package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
 *  修改日期: 2020/8/20 14:50
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WarehousingReturnDetailRequestDTO extends WarehousingReturnDetail implements Serializable {
    private static final long serialVersionUID = -4628017798219110628L;

    /**
     * 事务处理日期从
     */
    private Date startTime;

    /**
     * 事务处理日期至
     */
    private Date endTime;

    /**
     * 业务实体多选
     */
    private List<Long> orgIdList;

    /**
     * 库存组织多选
     */
    private List<Long> organizationIdList;

    /**
     * 物料小类模糊查询
     */
    private String categoryKey;

    /**
     * 物料模糊查询
     */
    private String materialKey;

    /**
     * 供应商模糊查询
     */
    private String supplierKey;

    /**
     * 成本类型(longi)
     */
    private String ceeaCostType;

    /**
     * 成本类型编码(对应ERP供应商地点ID)
     */
    private String ceeaCostTypeCode;

    /**
     * 采购员名称
     */
    private String ceeaEmpUsername;

}

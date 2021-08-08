package com.midea.cloud.srm.model.perf.itemexceptionhandle.dto;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuaItemEHeaderQueryDTO extends BaseEntity {
    /**
     * 业务实体
     */
    private String orgName;
    /**
     * 头行表异常单号ID
     */
    private Long itemExceptionHeadId;
    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 检验单类型
     */
    private String checkListType;
    /**
     * 检验标准
     */
    private String checkStandard;
    /**
     * 供应商
     */
    private String vendorName;
    /**
     * 材料责任人
     */
    private String itemAgent;
    /**
     * 环保责任人
     */
    private String epAgent;
    /**
     * 返工数量
     */
    private Long reworkTotal;
    /**
     * 返工结论
     */
    private String reworkConclusion;

}

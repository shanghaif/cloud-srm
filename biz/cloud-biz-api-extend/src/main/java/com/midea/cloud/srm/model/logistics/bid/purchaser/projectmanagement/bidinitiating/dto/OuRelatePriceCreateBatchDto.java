package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 11:56
 *  修改内容:
 * </pre>
 */
@Data
public class OuRelatePriceCreateBatchDto {


    private Long orgId;
    private String orgName;
    private Long ouGroupId;
    private String ouGroupName;
    @NotNull(message = "需求头id不能为空")
    private Long requireHeaderId;
    @NotNull(message = "物料id不能为空")
    private Long targetId;
    @Valid
    private List<OuRelatePriceCreateDto> relateList;

}

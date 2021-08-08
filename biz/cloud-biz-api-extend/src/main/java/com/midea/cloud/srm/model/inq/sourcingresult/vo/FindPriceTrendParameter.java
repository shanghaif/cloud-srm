package com.midea.cloud.srm.model.inq.sourcingresult.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 入参 - 价格趋势获取
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindPriceTrendParameter implements Serializable {

    private Long limit;              // 数目

    private Long orgId;             // 组织ID
    private Long itemId;            // 物料ID
    private OrgItemCompose orgItemCompose;    // 组织-物料组合
    //物料描述
    private String itemDesc;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OrgItemCompose implements Serializable {
        private Long orgId;
        private Long itemId;
    }
}

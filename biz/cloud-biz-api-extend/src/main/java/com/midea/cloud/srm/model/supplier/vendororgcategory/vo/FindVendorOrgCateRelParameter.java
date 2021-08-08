package com.midea.cloud.srm.model.supplier.vendororgcategory.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 入参 - 查询供应商组织品类关系
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindVendorOrgCateRelParameter implements Serializable {

    private Long[] vendorIds;
    private Long[] orgIds;
    private Long[] categoryIds;
    private List<OrgCateCompose> orgCateComposes;
    /**
     * 是否为招标模块，招标无需把注册中的添加进来
     */
    private boolean isBargain;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OrgCateCompose implements Serializable {
        private Long orgId;
        private Long categoryId;
    }
}

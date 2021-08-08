package com.midea.cloud.srm.model.supplier.vendororgcategory.vo;

import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * VO for {@link CompanyInfo} and {@link List<OrgCategory>}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorOrgCateRelsVO implements Serializable {

    private CompanyInfo         companyInfo;
    private List<OrgCategory>   orgCategories;


    /**
     * 对象分组合并并转换 - {@link List<VendorOrgCateRelVO>}.
     *
     * @param relVOS 供应商组织品类关系集
     * @return A new {@link List<VendorOrgCateRelsVO>}.
     */
    public static List<VendorOrgCateRelsVO> mergeAndCollect(List<VendorOrgCateRelVO> relVOS) {
        List<VendorOrgCateRelsVO> collects = new ArrayList<>();
        relVOS.stream()
                .collect(Collectors.groupingBy(VendorOrgCateRelVO::getCompanyId))
                .forEach((companyId, group) -> {
                    VendorOrgCateRelsVO relsVO = VendorOrgCateRelsVO.builder()
                            .companyInfo(group.get(0).toCompanyInfo())
                            .orgCategories(group.stream()
                                    .map(VendorOrgCateRelVO::toOrgCategory)
                                    .collect(Collectors.toList())
                            )
                            .build();
                    collects.add(relsVO);
                });
        return collects;
    }
}

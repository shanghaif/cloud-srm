package com.midea.cloud.srm.feign.base;

import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeRelateVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * tanjl11
 */
@FeignClient(name = "cloud-biz-base", contextId = "cloud-biz-base-MaterialItemAttribute")
public interface IMaterialItemAttributeClient {
    /**
     * 根据物料id获取关键物料主属性
     *
     * @param materialId
     * @return
     */
    @GetMapping("/base/material-item-attribute/getKeyFeatureMaterialAttribute")
    List<MaterialItemAttributeRelateVO> getKeyFeatureMaterialAttribute(@RequestParam("materialId") Long materialId);

    @PostMapping("/base/material-item-attribute/getKeyFeatureMaterialAttributes")
    Map<Long, List<MaterialItemAttributeRelateVO>> getKeyFeatureMaterialAttributes(@RequestBody List<Long> masterialIds);
}
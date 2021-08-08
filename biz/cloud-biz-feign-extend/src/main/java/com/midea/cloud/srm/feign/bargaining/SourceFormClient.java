package com.midea.cloud.srm.feign.bargaining;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormParameter;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.base.material.MaterialItemAttributeRelate;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeRelateVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * FeignClient - 寻源单API
 *
 * @author zixuan.yan@meicloud.com
 */
@FeignClient(value = "cloud-biz-bargaining", contextId = "cloud-biz-bargaining-sourceForm")
public interface SourceFormClient {

    @PostMapping("/bidInitiating/sourceForm/generate")
    GenerateSourceFormResult generateForm(@RequestBody GenerateSourceFormParameter parameter);

    /**
     * 合同物料价格变更转寻缘
     * @param sourceForm
     * @return
     */
    @PostMapping("/bidInitiating/sourceForm/generateByCm")
    GenerateSourceFormResult generateByCm(@RequestBody SourceForm sourceForm);

    /**
     * 根据bidingId获取封装好的关联信息
     */
    @PostMapping("/bidInitiating/sourceForm/getBidingOrgId2factor")
    List<MaterialItemAttributeRelateVO> getBidingOrgId2factor(@RequestParam("bidingId") Long bidingId);
}

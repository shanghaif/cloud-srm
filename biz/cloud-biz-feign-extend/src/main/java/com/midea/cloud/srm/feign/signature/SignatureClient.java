package com.midea.cloud.srm.feign.signature;

import com.midea.cloud.srm.model.signature.SigningParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/17
 *  修改内容:
 * </pre>
 */
//@FeignClient("signature-center")
@FeignClient(value = "cloud-biz-base", contextId  = "signatureFeign")
public interface SignatureClient {
    /**
     * 新增智慧签合同演示接口
     * @return
     */
    @PostMapping(value = "/feignPermit/msign/addSigning")
    Map<String,Object> addSigning(@RequestBody SigningParam signingParam) throws Exception;
}

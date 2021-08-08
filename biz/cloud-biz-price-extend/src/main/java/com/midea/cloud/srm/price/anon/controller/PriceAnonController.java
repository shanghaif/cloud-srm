package com.midea.cloud.srm.price.anon.controller;

import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务内部调用接口(无需登录就可以对微服务模块间暴露)
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-10 16:24
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/price-anon/internal")
public class PriceAnonController extends BaseController {

}

package com.midea.cloud.srm.ps.http.fssc.controller;

import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.http.PaymentApplyDto;
import com.midea.cloud.srm.ps.http.fssc.service.IFSSCReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 21:29
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/fsscReq")
public class FSSCReqController {

    @Autowired
    IFSSCReqService ifsscReqService;

    /**
     * 提交网上开票(对接费控)
     * @param paymentApplyDto
     * @return
     */
    @PostMapping("/submitOnlineInvoice")
    public FSSCResult submitOnlineInvoice(@RequestBody PaymentApplyDto paymentApplyDto) {
        return ifsscReqService.submitOnlineInvoice(paymentApplyDto);
    }

    /**
     * 作废(对接费控)
     * @param fsscStatus
     * @return
     */
    @PostMapping("/abandon")
    public FSSCResult fsscAbandon(@RequestBody FsscStatus fsscStatus) {
        return ifsscReqService.abandon(fsscStatus);
    }

    @PostMapping("/testAbandon")
    public FSSCResult testAbandon(@RequestBody FsscStatus fsscStatus) {
        return ifsscReqService.abandon(fsscStatus);
    }
}

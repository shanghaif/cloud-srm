package com.midea.cloud.srm.base.datatransfer.controller;

import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.base.datatransfer.service.DataTransferApprovalPriceTransfer;
import com.midea.cloud.srm.base.datatransfer.service.DataTransferOuGroupTransfer;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanjl11
 * @date 2020/11/02 14:41
 */
@RestController
@RequestMapping("/sourcing")
public class SourcingTransferController {

    @PostMapping("/transferByNo")
    public void transferByNo(@RequestBody TransferParam param) {
        DataTransferApprovalPriceTransfer approvalPriceTransfer = SpringContextHolder.getBean(DataTransferApprovalPriceTransfer.class);
        approvalPriceTransfer.transferByNo(param.getSourcingNo(), param.getOuName());
    }

    @GetMapping("/transferBidAll")
    public void transferBidALL() {
        DataTransferApprovalPriceTransfer approvalPriceTransfer = SpringContextHolder.getBean(DataTransferApprovalPriceTransfer.class);
        approvalPriceTransfer.transferBidToApprovalHeader();
    }

    @GetMapping("/transferBrgAll")
    private void transferBrgAll() {
        DataTransferApprovalPriceTransfer approvalPriceTransfer = SpringContextHolder.getBean(DataTransferApprovalPriceTransfer.class);
        approvalPriceTransfer.transferBrgToApprovalHeader();
    }

    @GetMapping("/transferBidOu")
    public void transferBidOu() {
        DataTransferOuGroupTransfer ouGroupTransfer = SpringContextHolder.getBean(DataTransferOuGroupTransfer.class);
        ouGroupTransfer.transferBidOuGroup();
    }

    @GetMapping("/transferBrgOu")
    public void transferBrgOu() {
        DataTransferOuGroupTransfer ouGroupTransfer = SpringContextHolder.getBean(DataTransferOuGroupTransfer.class);
        ouGroupTransfer.transferBrgOuGroup();
    }
}

package com.midea.cloud.srm.bid.projectlist.controller;

import com.midea.cloud.common.controller.BaseCheckController;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IOuRelatePriceService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.dto.OuRelatePriceCreateBatchDto;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.OuRelatePrice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
 *  修改日期: 2020/9/7 10:14
 *  修改内容:
 * </pre>
 */

@RequestMapping("/bid/ou-price-relate")
@RestController
@AllArgsConstructor
@Slf4j
public class OuRelatePriceController extends BaseCheckController {
    @Autowired
    private IOuRelatePriceService ouRelatePriceService;

    /**
     * 根据ou组的报价行
     */
    @GetMapping("/calculateRelatePrice")
    public List<OuRelatePrice> listPrice(@RequestParam("requirementLineId") Long baseOuRequireLineId,@RequestParam("currentPrice")BigDecimal currentPrice) {
        return ouRelatePriceService.listOuRelatePriceByOrderLine(baseOuRequireLineId,currentPrice);
    }

    @PostMapping("/createOuRelatePrice")
    public Boolean createOuRelatePrice(@RequestBody OuRelatePriceCreateBatchDto batchDto, BindingResult result) {
        checkParamBeforeHandle(log, result, batchDto);
        return ouRelatePriceService.createOuRelatePrice(batchDto);
    }

    @PostMapping("/updateRelatePrice")
    public Boolean updateRelatePrice(@RequestBody Map<String, Object> map) {
        Long relateId = Long.valueOf(map.get("id").toString());
        BigDecimal price = new BigDecimal(map.get("price").toString());
        return ouRelatePriceService.updatePrice(relateId, price);
    }

    @PostMapping("/updateRelatePrices")
    public Boolean updateRelatePrices(@RequestBody List<Map<String, Object>> listMap) {
        for (Map<String, Object> map : listMap) {
            updateRelatePrice(map);
        }
        return true;
    }

    @GetMapping("/getOuRelate")
    public List<OuRelatePrice> getOuRelate(@RequestParam("headerId") Long headerId, @RequestParam("baseOuId") Long baseOuId) {
        return ouRelatePriceService.listOuRelatePrices(headerId, baseOuId);
    }

    @GetMapping("/deleteOuRelateById")
    public Boolean delete(@RequestParam("relateId") Long relateId) {
        return ouRelatePriceService.deleteById(relateId);
    }
}

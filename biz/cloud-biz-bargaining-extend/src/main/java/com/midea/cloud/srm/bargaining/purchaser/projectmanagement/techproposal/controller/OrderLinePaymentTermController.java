package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.mapper.OrderlinePaymentTermMapper;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderlinePaymentTerm;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tanjl11
 * @Date 2020/09/18
 */
@RestController
@RequestMapping("orderline-paymentTerm")
public class OrderLinePaymentTermController {

    @Autowired
    private OrderlinePaymentTermMapper mapper;

    @PostMapping("/add")
    public OrderlinePaymentTerm orderlinePaymentTerm(@RequestBody OrderlinePaymentTerm term) {
        term.setPaymentTermId(IdGenrator.generate());
        mapper.insert(term);
        return term;
    }

    @PostMapping("/addBatch")
    public void addBatch(@RequestBody List<OrderlinePaymentTerm> term) {
        term.forEach(e -> {
            e.setPaymentTermId(IdGenrator.generate());
            mapper.insert(e);
        });
    }

    @PostMapping("/update")
    public OrderlinePaymentTerm update(@RequestBody OrderlinePaymentTerm term) {
        mapper.updateById(term);
        return term;
    }

    @PostMapping("/updateBatch")
    public void updateBatch(@RequestBody List<OrderlinePaymentTerm> terms) {
        terms.forEach(e -> mapper.updateById(e));
    }

    @PostMapping("/listPage")
    public PageInfo listPage(@RequestBody Map<String, Object> paramMap) {
        PageUtil.startPage(
                toInt(paramMap.get("pageNum"))
                , toInt(paramMap.get("pageSize")));
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<OrderlinePaymentTerm> orderlinePaymentTerms = mapper.selectList(
                Wrappers.lambdaQuery(OrderlinePaymentTerm.class)
                        .eq(Objects.nonNull(paramMap.get("orderLineId")),OrderlinePaymentTerm::getOrderLineId,paramMap.get("orderLineId"))
                .eq(OrderlinePaymentTerm::getCreatedId, loginAppUser.getUserId()));
        return new PageInfo(orderlinePaymentTerms);
    }

    @PostMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<Long> ids) {

        mapper.deleteBatchIds(ids);
    }

    @GetMapping("/delete")
    public void delete(@RequestParam Long id) {

        mapper.deleteById(id);
    }


    public Integer toInt(Object o) {
        return Integer.valueOf(o.toString());
    }
}

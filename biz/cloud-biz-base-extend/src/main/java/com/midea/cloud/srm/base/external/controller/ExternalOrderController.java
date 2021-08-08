package com.midea.cloud.srm.base.external.controller;

import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.external.service.IExternalOrderService;
import com.midea.cloud.srm.model.base.external.entity.ExternalOrder;
import com.midea.cloud.srm.model.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *  外部订单表 前端控制器
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 16:40:11
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/base-anon/external/externalOrder")
@Slf4j
public class ExternalOrderController extends BaseController {

    @Autowired
    private IExternalOrderService iExternalOrderService;

    /**
     * 新增
     *
     * @param sysExternalOrder
     */
    @RequestMapping("/sysExternalOrder")
    public BaseResult synExternalOrder(@RequestBody ExternalOrder sysExternalOrder) {
        log.info("收到新的门户订单：" + sysExternalOrder.toString());
        Assert.notNull(sysExternalOrder.getRentId(), "租户id不能为空");
        Assert.notNull(sysExternalOrder.getCompanyName(), "企业名称不能为空");
        Assert.notNull(sysExternalOrder.getCreditCode(), "社会信用代码不能为空");
        Assert.notNull(sysExternalOrder.getCompanyFlag(), "企业标识不能为空");
        Assert.notNull(sysExternalOrder.getUserAccount(), "管理员账号不能为空");
        Assert.notNull(sysExternalOrder.getUserName(), "管理员姓名不能为空");
        Assert.notNull(sysExternalOrder.getMailAddress(), "管理员邮箱不能为空");
        Assert.notNull(sysExternalOrder.getMobileNumber(), "管理员手机号不能为空");
        Assert.notNull(sysExternalOrder.getOrderNumber(), "订单编号不能为空");
        Assert.notNull(sysExternalOrder.getOrderDetails(), "订单套餐不能为空");
        Assert.notNull(sysExternalOrder.getStartDate(), "订单套餐生效时间不能为空");
        Assert.notNull(sysExternalOrder.getEndDate(), "订单套餐失效时间不能为空");
        Assert.notNull(sysExternalOrder.getUrlAddress(), "应用实例地址不能为空");
        Long id = IdGenrator.generate();
        sysExternalOrder.setSysExtenerallOrderId(id);
        iExternalOrderService.addOrUpdate(sysExternalOrder);
        log.info("门户订单执行成功：" + sysExternalOrder.toString());
        return BaseResult.build(ResultCode.SUCCESS, "创建订单成功：" + sysExternalOrder.getUserAccount());
    }

    /**
     * 获取租户下账号
     */
    @RequestMapping("/getAccountNum")
    public Integer getAccountNum() {
        return iExternalOrderService.getAccountNum();
    }


    /**
     * 查询所有
     * @return
     */
    @PostMapping("/listAll")
    public List<ExternalOrder> listAll() {
        return iExternalOrderService.list();
    }

}

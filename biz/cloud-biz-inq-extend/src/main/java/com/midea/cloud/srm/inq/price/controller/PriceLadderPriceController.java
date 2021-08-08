package com.midea.cloud.srm.inq.price.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.price.service.IPriceLadderPriceService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.price.entity.PriceLadderPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  价格目录-阶梯价 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/13 15:54
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/price/priceLadderPrice")
public class PriceLadderPriceController extends BaseController {

    @Autowired
    private IPriceLadderPriceService iPriceLadderPriceService;

    /**
     * 分页查询
     * @param priceLadderPrice
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<PriceLadderPrice> listPage(@RequestBody PriceLadderPrice priceLadderPrice) {
        Assert.notNull(priceLadderPrice,"数据格式错误");
        Assert.notNull(priceLadderPrice.getPriceLibraryId(),"价格目录ID不能为空");

        PageUtil.startPage(priceLadderPrice.getPageNum(), priceLadderPrice.getPageSize());
        QueryWrapper<PriceLadderPrice> wrapper = new QueryWrapper<PriceLadderPrice>(priceLadderPrice);
        return new PageInfo<PriceLadderPrice>(iPriceLadderPriceService.list(wrapper));
    }
}

package com.midea.cloud.srm.base.purchase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.purchase.service.ILatestGidailyRateService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCurrencyService;
import com.midea.cloud.srm.model.base.purchase.dto.PurchaseRateCheck;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  汇率表（最新的汇率，持续更新） 前端控制器
 * </pre>
 *
 * @author xiexh12@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 16:29:44
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/purchase/latest-gidaily-rate")
public class LatestGidailyRateController extends BaseController {

    @Autowired
    private ILatestGidailyRateService iLatestGidailyRateService;
    @Autowired
    private IPurchaseCurrencyService iPurchaseCurrencyService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public LatestGidailyRate get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iLatestGidailyRateService.getById(id);
    }

    /**
     * 新增
     *
     * @param latestGidailyRate
     */
    @PostMapping("/add")
    public void add(@RequestBody LatestGidailyRate latestGidailyRate) {
        Long id = IdGenrator.generate();
        latestGidailyRate.setId(id);
        iLatestGidailyRateService.save(latestGidailyRate);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iLatestGidailyRateService.removeById(id);
    }

    /**
     * 修改
     *
     * @param latestGidailyRate
     */
    @PostMapping("/modify")
    public void modify(@RequestBody LatestGidailyRate latestGidailyRate) {
        iLatestGidailyRateService.updateById(latestGidailyRate);
    }

    /**
     * 分页查询
     *
     * @param latestGidailyRate
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<LatestGidailyRate> listPage(@RequestBody LatestGidailyRate latestGidailyRate) {
        PageUtil.startPage(latestGidailyRate.getPageNum(), latestGidailyRate.getPageSize());
        QueryWrapper<LatestGidailyRate> wrapper = new QueryWrapper<LatestGidailyRate>(latestGidailyRate);
        return new PageInfo<LatestGidailyRate>(iLatestGidailyRateService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<LatestGidailyRate> listAll() {
        return iLatestGidailyRateService.list();
    }

    @GetMapping("/getRateByFromTypeAndToType")
    public BigDecimal getRateByFromTypeAndToType(@RequestParam("from") String fromCode, @RequestParam(value = "to", required = false) String toCode) {
        //默认转为人民币
        if (StringUtils.isEmpty(toCode)) {
            toCode = "CNY";
        }
        if (fromCode.equals(toCode)) {
            return BigDecimal.ONE;
        }
        LatestGidailyRate one = iLatestGidailyRateService.getOne(Wrappers.lambdaQuery(LatestGidailyRate.class)
                .eq(LatestGidailyRate::getFromCurrencyCode, fromCode)
                .eq(LatestGidailyRate::getToCurrencyCode, toCode).orderByDesc(LatestGidailyRate::getLastUpdateDate).last("limit 1"));
        return one == null ? null : one.getConversionRate();
    }

    @PostMapping("/checkRateFromCodeToCode")
    public List<String> checkRateFromCodeToCode(@RequestBody PurchaseRateCheck check) {
        String fromCode = check.getFromCode();
        Set<String> collect = check.getToCodes().stream().filter(e -> !Objects.equals(e, fromCode)).collect(Collectors.toSet());
        if(CollectionUtils.isEmpty(collect)){
            return Collections.emptyList();
        }
        List<LatestGidailyRate> list = iLatestGidailyRateService.list(Wrappers.lambdaQuery(LatestGidailyRate.class)
                .eq(LatestGidailyRate::getFromCurrencyCode, fromCode)
                .in(LatestGidailyRate::getToCurrencyCode, collect)
        );
        List<String> result = new LinkedList<>();
        for (String s : collect) {
            boolean find = false;
            for (LatestGidailyRate latestGidailyRate : list) {
                if (Objects.equals(latestGidailyRate.getToCurrencyCode(), s)) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                result.add(s);
            }
        }
        return result;
    }


    /**
     * 查询本位币为人民币对应其他外币的汇率
     */
    @PostMapping("/getRMBRate")
    public List<LatestGidailyRate> getRMBRate(@RequestParam("toCurrencyCode") String toCurrencyCode) {
        return iLatestGidailyRateService.getRMBRate(toCurrencyCode);
    }

    /**
     * 根据币种名字获取汇率信息
     * @param toCurrencyName
     * @param fromCurrencyName
     * @return
     */
    @GetMapping("/loadRateByName")
    public LatestGidailyRate loadRateByName(@RequestParam(value = "toCurrencyName" , required = true) String toCurrencyName ,
                                            @RequestParam(value = "fromCurrencyName" , required = true)String fromCurrencyName){
        //1,获取系统存在的币种
        List<PurchaseCurrency> allCurrencyForImport  = iPurchaseCurrencyService.listAllCurrencyForImport();
        Map<String ,String> currencyNameMap = allCurrencyForImport.stream().collect(Collectors.toMap(
                c -> c.getCurrencyName() , c -> c.getCurrencyCode() , (o, o2) -> o
        ));

        String toCurrencyCode = currencyNameMap.get(toCurrencyName);
        String fromCurrencyCode = currencyNameMap.get(fromCurrencyName);
        if(StringUtils.isEmpty(toCurrencyCode)){
            throw new BaseException(String.format("系统中不存在币种[%s]，请检查!" , toCurrencyName));
        }
        if(StringUtils.isEmpty(fromCurrencyCode)){
            throw new BaseException(String.format("系统中不存在币种[%s]，请检查!" , fromCurrencyName));
        }

        //2.根据币种查找汇率
        LatestGidailyRate newRate = iLatestGidailyRateService.getOne(Wrappers.lambdaQuery(LatestGidailyRate.class)
                .eq(LatestGidailyRate::getFromCurrencyCode, fromCurrencyCode)
                .eq(LatestGidailyRate::getToCurrencyCode, toCurrencyCode).orderByDesc(LatestGidailyRate::getLastUpdateDate)
                .last("limit 1"));

        BigDecimal minMagnify = new BigDecimal(0.8);    //浮动值左区间
        BigDecimal maxMagnify = new BigDecimal(1.2);    //浮动值右区间
        newRate.setMinConversionRate(newRate.getConversionRate().multiply(minMagnify).setScale(4 , BigDecimal.ROUND_HALF_UP));
        newRate.setMaxConversionRate(newRate.getConversionRate().multiply(maxMagnify).setScale(4 , BigDecimal.ROUND_HALF_UP));
        return newRate;
    }
    
    
    /**
     * 获取汇率
     * @param gidailyRate
     */
    @PostMapping("/getCurrency")
    public  Map<String,List<LatestGidailyRate>> getCurrency(@RequestBody LatestGidailyRate gidailyRate) {
        return iLatestGidailyRateService.getCurrency(gidailyRate);
    }

    /**
     * 根据目标币种和汇率类型获取汇率
     * @param toCurrencyCode 目标币种编码
     * @param conversionType 汇率类型
     * @return
     */
    @GetMapping("/getLatestGidailyRate")
    public Map<String,LatestGidailyRate> getLatestGidailyRate(
            @RequestParam("toCurrencyCode") String toCurrencyCode,
            @RequestParam("conversionType")String conversionType){
        return iLatestGidailyRateService.getLatestGidailyRate(toCurrencyCode, conversionType);
    }
}

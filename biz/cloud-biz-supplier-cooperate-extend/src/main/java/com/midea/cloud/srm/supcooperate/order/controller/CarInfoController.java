package com.midea.cloud.srm.supcooperate.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.order.CarInfoStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.CarInfo;
import com.midea.cloud.srm.supcooperate.order.service.ICarInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *   车辆信息表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/19 19:06
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order/carInfo")
public class CarInfoController extends BaseController {
    @Autowired
    private ICarInfoService iCarInfoService;

    /**
     * 新增车辆
     * @param carInfo
     */
    @PostMapping("/save")
    public void save(@RequestBody CarInfo carInfo) {
        Assert.notNull(carInfo.getLicensePlate(), "车牌号码不能为空");
        Assert.notNull(carInfo.getCarType(), "车牌类型不能为空");
        Assert.notNull(carInfo.getEffectiveDate(), "生效日期不能为空");
        Assert.notNull(carInfo.getExpirationDate(), "失效日期不能为空");

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("LICENSE_PLATE",carInfo.getLicensePlate());
        queryWrapper.eq("VENDOR_ID",carInfo.getVendorId());
        CarInfo carInfo1 = iCarInfoService.getOne(queryWrapper);
        Assert.isNull(carInfo1,"已存在车牌号码为：'"+carInfo.getLicensePlate()+"'的车辆");

        carInfo.setCarInfoId(IdGenrator.generate());
        carInfo.setVendorId(loginAppUser.getCompanyId());
        carInfo.setVendorName(loginAppUser.getCompanyName());
        carInfo.setVendorCode(loginAppUser.getCompanyCode());
        carInfo.setStatus(CarInfoStatus.CREATE.name());

        iCarInfoService.saveOrUpdate(carInfo);
    }

    /**
     * 分页查询
     * @param carInfo 车辆信息
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<CarInfo> listPage(@RequestBody CarInfo carInfo) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            carInfo.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(carInfo.getPageNum(), carInfo.getPageSize());
        List<CarInfo> list = iCarInfoService.findList(carInfo);
        for(CarInfo item:list){
            if(!StringUtils.equals(CarInfoStatus.CREATE.name(),item.getStatus())){
                if(item.getExpirationDate().getTime()>System.currentTimeMillis()
                        &&item.getEffectiveDate().getTime()<=System.currentTimeMillis()){
                    item.setStatus(CarInfoStatus.EFFECTIVE.name());
                }else{
                    item.setStatus(CarInfoStatus.INVALID.name());
                }
            }
        }
        return new PageInfo(list);
    }

    /**
     * 批量提交
     * @param ids ids
     */
    @PostMapping("/submitBatch")
    public void submitBatch(@RequestBody List<Long> ids) {
        iCarInfoService.submitBatch(ids);
    }


    /**
     * 修改车辆
     * @param carInfo
     */
    @PostMapping("/update")
    public void update(@RequestBody CarInfo carInfo) {
        Assert.notNull(carInfo.getCarInfoId(),"车辆ID不能为空");

        Assert.notNull(carInfo.getLicensePlate(), "车牌号码不能为空");
        Assert.notNull(carInfo.getCarType(), "车牌类型不能为空");
        Assert.notNull(carInfo.getEffectiveDate(), "生效日期不能为空");
        Assert.notNull(carInfo.getExpirationDate(), "失效日期不能为空");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("LICENSE_PLATE",carInfo.getLicensePlate());
        queryWrapper.ne("CAR_INFO_ID",carInfo.getCarInfoId());
        CarInfo carInfo1 = iCarInfoService.getOne(queryWrapper);
        Assert.isNull(carInfo1,"不能将车牌修改为：'"+carInfo.getLicensePlate()+"',因为该车牌已经存在");

        iCarInfoService.saveOrUpdate(carInfo);
    }


    /**
     * 车辆失效
     * @param ids
     */
    @PostMapping("/invalidBatch")
    public void invalidBatch(@RequestBody List<Long> ids) {
        List<CarInfo> list = new ArrayList<>();
        for(Long id:ids){
            CarInfo carInfo = new CarInfo();
            carInfo.setCarInfoId(id);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.roll(Calendar.DAY_OF_YEAR, -1);

            carInfo.setExpirationDate(calendar.getTime());

            list.add(carInfo);
        }
        iCarInfoService.saveOrUpdateBatch(list);
    }
}

package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.order.CarInfoStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.CarInfo;
import com.midea.cloud.srm.supcooperate.order.mapper.CarInfoMapper;
import com.midea.cloud.srm.supcooperate.order.service.ICarInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  车辆信息表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:13
 *  修改内容:
 * </pre>
 */
@Service
public class CarInfoServiceImpl extends ServiceImpl<CarInfoMapper, CarInfo> implements ICarInfoService {
    @Autowired
    private CarInfoMapper carInfoMapper;

    @Override
    @Transactional
    public void submitBatch(List<Long> ids) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        List list = new ArrayList();

        ids.forEach(item -> {
            CarInfo checkCarInfo = this.getBaseMapper().selectById(item);
            Assert.notNull(checkCarInfo, "找不到车辆信息");
            Assert.isTrue(StringUtils.equals(CarInfoStatus.CREATE.name(), checkCarInfo.getStatus()), "选择采购商编辑状态车辆");

            CarInfo carInfo = new CarInfo();
            carInfo.setCarInfoId(item);
            carInfo.setStatus(CarInfoStatus.SUBMIT.name());
            carInfo.setSubmittedId(loginAppUser.getUserId());
            carInfo.setSubmittedBy(loginAppUser.getUsername());
            carInfo.setSubmittedTime(new Date());

            list.add(carInfo);
        });
        this.updateBatchById(list);
    }

    @Override
    public List<CarInfo> findList(CarInfo carInfo) {
        return carInfoMapper.findList(carInfo);
    }
}

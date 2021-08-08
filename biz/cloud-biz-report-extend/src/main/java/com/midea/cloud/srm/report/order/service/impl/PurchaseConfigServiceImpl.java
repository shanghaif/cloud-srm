package com.midea.cloud.srm.report.order.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.report.purchase.entity.PurchaseConfig;
import com.midea.cloud.srm.report.order.mapper.PurchaseConfigMapper;
import com.midea.cloud.srm.report.order.service.IPurchaseConfigService;
@Service
public class PurchaseConfigServiceImpl extends ServiceImpl<PurchaseConfigMapper, PurchaseConfig> implements IPurchaseConfigService {

}

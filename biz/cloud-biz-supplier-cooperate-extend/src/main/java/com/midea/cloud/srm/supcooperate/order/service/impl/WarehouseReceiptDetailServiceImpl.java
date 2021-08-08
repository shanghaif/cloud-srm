package com.midea.cloud.srm.supcooperate.order.service.impl;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;

import com.midea.cloud.srm.model.suppliercooperate.order.vo.WarehouseReceiptDetailVO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import com.midea.cloud.srm.supcooperate.order.mapper.WarehouseReceiptDetailMapper;
import com.midea.cloud.srm.supcooperate.order.service.IWarehouseReceiptDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/**
* <pre>
 *  入库单行表 服务实现类
 * </pre>
*
* @author chenwj92@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 19, 2021 1:40:47 PM
 *  修改内容:
 * </pre>
*/
@Service
public class WarehouseReceiptDetailServiceImpl extends ServiceImpl<WarehouseReceiptDetailMapper, WarehouseReceiptDetail> implements IWarehouseReceiptDetailService {

    @Autowired
    private WarehouseReceiptDetailMapper warehouseReceiptDetailMapper;

    @Transactional
    public void batchUpdate(List<WarehouseReceiptDetail> warehouseReceiptDetailList) {
        this.saveOrUpdateBatch(warehouseReceiptDetailList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<WarehouseReceiptDetail> warehouseReceiptDetailList) throws IOException {
        for(WarehouseReceiptDetail warehouseReceiptDetail : warehouseReceiptDetailList){
            if(warehouseReceiptDetail.getWarehouseReceiptDetailId() == null){
                Long id = IdGenrator.generate();
                warehouseReceiptDetail.setWarehouseReceiptDetailId(id);
            }
        }
        if(!CollectionUtils.isEmpty(warehouseReceiptDetailList)) {
            batchUpdate(warehouseReceiptDetailList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public PageInfo<WarehouseReceiptDetail> listPage(WarehouseReceiptDetail warehouseReceiptDetail) {
        PageUtil.startPage(warehouseReceiptDetail.getPageNum(), warehouseReceiptDetail.getPageSize());
        List<WarehouseReceiptDetail> warehouseReceiptDetails = getWarehouseReceiptDetails(warehouseReceiptDetail);
        return new PageInfo<>(warehouseReceiptDetails);
    }

    /**
     * 查询入库单明细
     * @param warehouseReceiptId 入库单头id
     * @return
     */
    @Override
    public List<WarehouseReceiptDetail> list(Long warehouseReceiptId) {
        return warehouseReceiptDetailMapper.list(warehouseReceiptId);
    }

    @Override
    public List<WarehouseReceiptDetailVO> list(List<Long> ids) {
        return warehouseReceiptDetailMapper.listInWarehouseReceipt(ids);
    }

    @Override
    public List<WarehouseReceiptDetail> listByWarehouseReceiptId(List<Long> warehouseReceiptIdList) {
        if (CollectionUtils.isEmpty(warehouseReceiptIdList)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<WarehouseReceiptDetail> warehouseReceiptDetailWrapper = Wrappers.lambdaQuery(WarehouseReceiptDetail.class)
                .in(WarehouseReceiptDetail::getWarehouseReceiptId, warehouseReceiptIdList);
        return this.list(warehouseReceiptDetailWrapper);
    }

    @Override
    public int deleteByWarehouseReceiptId(Long warehouseReceiptId) {
        return this.getBaseMapper().delete(Wrappers.lambdaQuery(WarehouseReceiptDetail.class)
                .eq(WarehouseReceiptDetail::getWarehouseReceiptId, warehouseReceiptId));
    }

    @Override
    public int deleteByWarehouseReceiptId(List<Long> warehouseReceiptIdList) {
        if (CollectionUtils.isEmpty(warehouseReceiptIdList)) {
            return 0;
        }

        return this.getBaseMapper().delete(Wrappers.lambdaQuery(WarehouseReceiptDetail.class)
                .in(WarehouseReceiptDetail::getWarehouseReceiptId, warehouseReceiptIdList));
    }

    @Override
    public List<WarehouseReceiptDetail> listByDeliveryNoteDetailId(Collection<Long> deliveryNoteDetailIdList) {
        if (CollectionUtils.isEmpty(deliveryNoteDetailIdList)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<WarehouseReceiptDetail> warehouseReceiptDetailWrapper = Wrappers.lambdaQuery(WarehouseReceiptDetail.class)
                .in(WarehouseReceiptDetail::getDeliveryNoteDetailId, deliveryNoteDetailIdList);
        return this.list(warehouseReceiptDetailWrapper);
    }

    public List<WarehouseReceiptDetail> getWarehouseReceiptDetails(WarehouseReceiptDetail warehouseReceiptDetail) {
        QueryWrapper<WarehouseReceiptDetail> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",warehouseReceiptDetail.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",warehouseReceiptDetail.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",warehouseReceiptDetail.getStartDate()).
//                        le("CREATION_DATE",warehouseReceiptDetail.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}

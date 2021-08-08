package com.midea.cloud.srm.po.logisticsOrder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderFile;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.po.logisticsOrder.mapper.OrderFileMapper;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderFileService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
*  <pre>
 *  物流采购订单附件 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:52:41
 *  修改内容:
 * </pre>
*/
@Service
public class OrderFileServiceImpl extends ServiceImpl<OrderFileMapper, OrderFile> implements IOrderFileService {

    @Override
    public void addOrderFileBatch(OrderHead orderHead, List<OrderFile> orderList) {
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (int i = 0; i < orderList.size(); i++) {
                OrderFile orderAttach = orderList.get(i);
                if (orderAttach == null) continue;
                saveOrderAttach(orderHead, orderAttach,i+1);
            }
        }
    }

    private void saveOrderAttach(OrderHead orderHead, OrderFile orderAttach,int rowNum) {
        orderAttach.setOrderFileId(IdGenrator.generate())
                .setOrderHeadId(orderHead.getOrderHeadId())
                .setRowNum(rowNum);
        this.save(orderAttach);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderAttachBatch(OrderHead orderHead, List<OrderFile> orderFileList) {
        if (CollectionUtils.isNotEmpty(orderFileList)) {
            List<OrderFile> oldList = this.list(new QueryWrapper<>(new OrderFile().setOrderHeadId(orderHead.getOrderHeadId())));
            List<Long> oldAttachIds = oldList.stream().map(OrderFile::getOrderFileId).collect(Collectors.toList());
            List<Long> newAttachIds = new ArrayList<>();

            List<OrderFile> orderFileListAdd = new LinkedList<>();
            List<OrderFile> orderFileListUpdate = new LinkedList<>();
            for (int i = 0; i < orderFileList.size(); i++) {
                OrderFile orderAttach = orderFileList.get(i);
                if (orderAttach == null) continue;
                Long attachId = orderAttach.getOrderFileId();

                if (attachId == null) {
                    //新增
                    orderAttach.setOrderHeadId(orderHead.getOrderHeadId()).setRowNum(i + 1).setOrderFileId(IdGenrator.generate());
                    orderFileListAdd.add(orderAttach);
                }else {
                    //修改
                    newAttachIds.add(attachId);
                    orderFileListUpdate.add(orderAttach);
                }
            }
            //批量增加
            if(CollectionUtils.isNotEmpty(orderFileListAdd)){
                this.saveBatch(orderFileListAdd);
            }
            //批量修改
            if(CollectionUtils.isNotEmpty(orderFileListUpdate)){
                this.updateBatchById(orderFileListUpdate);
            }

            //删除
            List<Long> removeIds = new LinkedList<>();
            if (CollectionUtils.isNotEmpty(oldAttachIds)) {
                for (Long oldAttachId : oldAttachIds) {
                    if (!newAttachIds.contains(oldAttachId)) {
                        removeIds.add(oldAttachId);
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(removeIds)){
                this.removeByIds(removeIds);
            }
        }
    }
}

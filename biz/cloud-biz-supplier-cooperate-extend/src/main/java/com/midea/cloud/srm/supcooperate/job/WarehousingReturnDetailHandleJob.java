package com.midea.cloud.srm.supcooperate.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.quartz.bind.Job;
import com.midea.cloud.quartz.handler.ExecuteableJob;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetailErp;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailErpService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/10/26 16:05
 *  修改内容:
 * </pre>
 */
@Job("WarehousingReturnDetailHandleJob")
@Slf4j
public class WarehousingReturnDetailHandleJob implements ExecuteableJob {

    @Autowired
    private IWarehousingReturnDetailErpService warehousingReturnDetailErpService;

    @Autowired
    private IWarehousingReturnDetailService warehousingReturnDetailService;


    /**
     * 分页查询获取所有 if_handle为N ,if_handle_now为空的数据
     * 处理之后，将真正处理的数据置if_handle为Y，if_handle_now 修改为Y，记下
     * 全部执行完之后，将 if_handle_now的字段全部置为null
     * @param params
     * @return
     */
    @Override
    public BaseResult executeJob(Map<String, String> params) {
        Long startTime = System.currentTimeMillis();
        /**
         * 获取最近三天的ifHandle为"N"的数据，进行处理
         */
        List<Long> warehousingReturnDetailErpIds = new LinkedList<>();
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now().plusDays(-20),LocalTime.MAX);
        QueryWrapper<WarehousingReturnDetailErp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IF_HANDLE","N");
        queryWrapper.ge("CREATION_DATE",localDateTime);
        queryWrapper.isNull("IF_HANDLE_NOW");
        int count = warehousingReturnDetailErpService.count(queryWrapper);
        List<WarehousingReturnDetailErp> warehousingReturnDetailErpList = null;
        Map<String,Integer> map = new HashMap<String,Integer>(){{
            put("updatesErp",0);
            put("updates",0);
        }};
        if(count < 1000){
            warehousingReturnDetailErpList = warehousingReturnDetailErpService.list(queryWrapper);
            map = handleWarehousingReturn(warehousingReturnDetailErpList);
            List<Long> ids = warehousingReturnDetailErpList.stream().map(item -> item.getWarehousingReturnDetailErpId()).collect(Collectors.toList());
            warehousingReturnDetailErpIds.addAll(ids);
        }else{
            int time = (int)Math.ceil(count/1000.0);
            for(int i=0;i<time;i++){
                queryWrapper.clear();
                queryWrapper.eq("IF_HANDLE","N")
                        .ge("CREATION_DATE",localDateTime)
                        .isNull("IF_HANDLE_NOW")
                        .last("limit " + i + ", 1000");
                warehousingReturnDetailErpList = warehousingReturnDetailErpService.list(queryWrapper);
                Map<String,Integer> map1 = handleWarehousingReturn(warehousingReturnDetailErpList);
                map.put("updatesErp",map1.get("updatesErp") + map.get("updatesErp"));
                map.put("updates",map1.get("updates") + map.get("updates"));
                List<Long> ids = warehousingReturnDetailErpList.stream().map(item -> item.getWarehousingReturnDetailErpId()).collect(Collectors.toList());
                warehousingReturnDetailErpIds.addAll(ids);
            }
        }

        UpdateWrapper<WarehousingReturnDetailErp> updateWrapper = new UpdateWrapper();
        updateWrapper.set("IF_HANDLE_NOW",null);
        updateWrapper.in("WAREHOUSING_RETURN_DETAIL_ERP_ID",warehousingReturnDetailErpIds);
        warehousingReturnDetailErpService.update(updateWrapper);

        Long totalTime = System.currentTimeMillis() - startTime;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("查询出来的数据有" + count + "条,"
                +" 更新了入库退货明细" + map.get("updates") + "条,"
                +"更新了入库退货明细(erp)" + map.get("updatesErp") + "条,"
                +"重新置空了IF_HANDLE_NOW字段" + warehousingReturnDetailErpIds.size() + "条。"
                +"总共执行" + totalTime + "毫秒");
        return BaseResult.build(ResultCode.SUCCESS,stringBuilder.toString());
    }

    public Map<String,Integer> handleWarehousingReturn(List<WarehousingReturnDetailErp> warehousingReturnDetailErpList){
        log.info("=======================================批次处理开始=========================================");
        log.info("该批次数量为：" + warehousingReturnDetailErpList.size());
        Map<String,Integer> map = new HashMap<>();
        List<WarehousingReturnDetailErp> updatesIfHandleNow = new LinkedList<>();
        List<WarehousingReturnDetailErp> updatesErp = new LinkedList<>();
        List<WarehousingReturnDetail> updates = new LinkedList<>();
        for(WarehousingReturnDetailErp warehousingReturnDetailErp : warehousingReturnDetailErpList){
            if("RETURN TO VENDOR".equals(warehousingReturnDetailErp.getTxnType()) ||
                    "RETRUN_TO_VENDOR".equals(warehousingReturnDetailErp.getTxnType())
            ){
                /**
                 * 获取parentTxnId
                 */
                updatesIfHandleNow.add(new WarehousingReturnDetailErp()
                        .setWarehousingReturnDetailErpId(warehousingReturnDetailErp.getWarehousingReturnDetailErpId())
                        .setIfHandleNow("Y")
                );
                WarehousingReturnDetailErp erpEntity = new WarehousingReturnDetailErp();
                BeanUtils.copyProperties(warehousingReturnDetailErp,erpEntity);
                while (erpEntity != null && !"RECEIVE".equals(erpEntity.getTxnType()) && !"RECEIVE_STANDARD".equals(erpEntity.getTxnType())){
                    if(Objects.isNull(erpEntity.getParentTxnId())){
                        erpEntity = null;
                    }else{
                        WarehousingReturnDetailErp param = new WarehousingReturnDetailErp()
                                .setTxnId(erpEntity.getParentTxnId());
                        List<WarehousingReturnDetailErp> list = warehousingReturnDetailErpService.list(new QueryWrapper<>(param));
                        if(CollectionUtils.isNotEmpty(list)){
                            erpEntity = list.get(0);
                        }else{
                            erpEntity = null;
                        }
                    }
                }
                if(erpEntity == null){
                    /*没有检测到parentTxnId*/
                    continue;
                }else{
                    WarehousingReturnDetail warehousingReturnDetail = warehousingReturnDetailService.getById(warehousingReturnDetailErp.getWarehousingReturnDetailId());
                    if(warehousingReturnDetail != null){
                        updates.add(new WarehousingReturnDetail()
                                .setWarehousingReturnDetailId(warehousingReturnDetail.getWarehousingReturnDetailId())
                                .setEnable("Y")
                                .setParentTxnId(erpEntity.getTxnId())
                        );
                        updatesErp.add(new WarehousingReturnDetailErp()
                                .setWarehousingReturnDetailErpId(warehousingReturnDetailErp.getWarehousingReturnDetailErpId())
                                .setIfHandle("Y")
                        );
                    }
                }

            }
        }
        updateBatch(updatesErp,updates,updatesIfHandleNow);
        map.put("updatesErp",updatesErp.size());
        map.put("updates",updates.size());
        log.info("=================================批次处理结束======================================");
        return map;
    }


    @Transactional
    public void updateBatch(List<WarehousingReturnDetailErp> updatesErp, List<WarehousingReturnDetail> updates,List<WarehousingReturnDetailErp> updatesIfHandleNow){
        warehousingReturnDetailService.updateBatchById(updates);
        warehousingReturnDetailErpService.updateBatchById(updatesErp);
        warehousingReturnDetailErpService.updateBatchById(updatesIfHandleNow);
    }

    public static void main(String[] args) {
        int count = 89020;
        int time = (int)Math.ceil(count/1000.0);
        System.out.println(time);
    }

}

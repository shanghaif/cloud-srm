package com.midea.cloud.srm.po.order.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.order.NoteStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.redis.RedisLockUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.DeliveryNoticeImportVO;
import com.midea.cloud.srm.po.order.service.IDeliveryNoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *   送货通知单表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:06
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/po/deliveryNotice")
public class DeliveryNoticeController extends BaseController {

    @Autowired
    private com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoticeService scIDeliveryNoticeService;
    @Autowired
    private IDeliveryNoticeService iDeliveryNoticeService;
    @Resource
    private com.midea.cloud.srm.supcooperate.order.controller.DeliveryNoticeController deliveryNoticeController;
    @Resource
    private RedisLockUtil redisLockUtil;
    /**
     * 分页查询
     * @param requestDTO
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryNoticeDTO> listPage(@RequestBody DeliveryNoticeRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        List<DeliveryNoticeDTO> deliveryNoticeServiceList = scIDeliveryNoticeService.findList(requestDTO);
        if(!CollectionUtils.isEmpty(deliveryNoticeServiceList)){
            deliveryNoticeServiceList.forEach(deliveryNoticeDTO -> {
                // 送货通知引用数量
                BigDecimal deliveryNoticeQuantity = deliveryNoticeDTO.getDeliveryNoticeQuantity();
                // 订单数量
                BigDecimal orderNum = deliveryNoticeDTO.getOrderNum();
                BigDecimal surplusDeliveryQuantity = orderNum.subtract(deliveryNoticeQuantity);
                surplusDeliveryQuantity = surplusDeliveryQuantity.compareTo(BigDecimal.ZERO) >= 0 ? surplusDeliveryQuantity : BigDecimal.ZERO;
                BigDecimal noticeSum = deliveryNoticeDTO.getNoticeSum();
                surplusDeliveryQuantity = null != noticeSum ? surplusDeliveryQuantity.add(noticeSum):surplusDeliveryQuantity;
                // 本次可通知送货数量
                deliveryNoticeDTO.setSurplusDeliveryQuantity(surplusDeliveryQuantity);
            });
        }
        return new PageInfo(deliveryNoticeServiceList);
    }

    /**
     *
     * 修改送货通知单(暂不使用)
     * @param deliveryNotice
     */
    @PostMapping("/update")
    public void update(@RequestBody DeliveryNotice deliveryNotice) {
        Assert.notNull(deliveryNotice,"数据格式错误");
        Assert.notNull(deliveryNotice.getDeliveryNoticeId(),"送货通知单ID不能为空");

        DeliveryNotice checkNotice = scIDeliveryNoticeService.getById(deliveryNotice.getDeliveryNoticeId());
        Assert.notNull(checkNotice,"送货通知单不存在");
        Assert.isTrue(StringUtils.equals(NoteStatus.EDIT.name(),checkNotice.getDeliveryNoticeStatus()),"只能修改拟态送货通知单");

        DeliveryNotice updateNotice = new DeliveryNotice();
        updateNotice.setDeliveryNoticeId(deliveryNotice.getDeliveryNoticeId());
        updateNotice.setComments(deliveryNotice.getComments());
        updateNotice.setDeliveryTime(deliveryNotice.getDeliveryTime());
        scIDeliveryNoticeService.updateById(updateNotice);
    }

    /**
     * 采购商批量发布
     *
     * @param deliveryNotices 送货通知单deliveryNotices
     */
    @PostMapping("/releasedBatch")
    public void releasedBatch(@RequestBody List<DeliveryNotice> deliveryNotices) {
        iDeliveryNoticeService.releasedBatch(deliveryNotices);
    }

    /**
     * excel导入送货通知
     */
    @PostMapping("/excelImportLine")
    public void excelImport(@RequestParam("file") MultipartFile file) throws IOException {
        Assert.notNull(file, "文件上传失败");
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Assert.isTrue((org.apache.commons.lang3.StringUtils.equals("xls", suffix.toLowerCase()) || org.apache.commons.lang3.StringUtils.equals("xlsx", suffix.toLowerCase())),
                "请上传excel文件");
        List<Object> voList = EasyExcelUtil.readExcelWithModel(file.getInputStream(), DeliveryNoticeImportVO.class);
        iDeliveryNoticeService.importExcelInfo(voList);
    }

    /**
     * 新增送货通知单
     * @param deliveryNoticeList
     */
    @PostMapping("/add")
    public void add(@RequestBody List<DeliveryNotice> deliveryNoticeList){
        String key = RedisKey.DELIVERY_NOTICE_ADD +  JSON.toJSONString(!CollectionUtils.isEmpty(deliveryNoticeList)?deliveryNoticeList:"");
        try {
            // 获取锁
            redisLockUtil.lock(key,60);
            // 执行业务
            iDeliveryNoticeService.add(deliveryNoticeList);
        } finally {
            // 释放锁
            redisLockUtil.unlock(key);
        }
    }

    /**
     * 修改送货通知单
     * @param deliveryNoticeList
     */
    @PostMapping("/batchUpdate")
    public void batchUpdate(@RequestBody List<DeliveryNotice> deliveryNoticeList){
        String key = RedisKey.DELIVERY_NOTICE_UPDATE +  JSON.toJSONString(!CollectionUtils.isEmpty(deliveryNoticeList)?deliveryNoticeList:"");
        try {
            // 获取锁
            redisLockUtil.lock(key,60);
            // 执行业务
            iDeliveryNoticeService.batchUpdate(deliveryNoticeList);
        } finally {
            // 释放锁
            redisLockUtil.unlock(key);
        }
    }


    /**
     * 批量删除送货通知
     * @param ids
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> ids){
        String key = RedisKey.DELIVERY_NOTICE_DELETE +  JSON.toJSONString(!CollectionUtils.isEmpty(ids)?ids:"");
        try {
            // 获取锁
            redisLockUtil.lock(key,60);
            // 执行业务
            iDeliveryNoticeService.batchDelete(ids);
        } finally {
            // 释放锁
            redisLockUtil.unlock(key);
        }
    }
}

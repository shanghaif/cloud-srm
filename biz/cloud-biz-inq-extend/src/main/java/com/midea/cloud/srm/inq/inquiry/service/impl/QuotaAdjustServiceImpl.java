package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.inq.AdjustStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.inq.inquiry.mapper.QuotaAdjustMapper;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaAdjustService;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaSourceService;
import com.midea.cloud.srm.inq.price.service.IApprovalBiddingItemService;
import com.midea.cloud.srm.inq.price.service.IApprovalHeaderService;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaAdjustDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaSourceDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaAdjust;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaSource;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  配额调整 服务实现类
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-08 14:01:49
 *  修改内容:
 * </pre>
 */
@Service
public class QuotaAdjustServiceImpl extends ServiceImpl<QuotaAdjustMapper, QuotaAdjust> implements IQuotaAdjustService {
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private IQuotaSourceService iQuotaSourceService;
    @Autowired
    private FileCenterClient fileCenterClient;
    @Autowired
    private QuotaAdjustMapper quotaAdjustMapper;
    @Autowired
    private IApprovalHeaderService iApprovalHeaderService;
    @Autowired
    private IApprovalBiddingItemService iApprovalBiddingItemService;

    @Override
    public List<QuotaAdjust> quotaAdjustList(QuotaAdjustDTO quotaAdjustDTO) {
        QueryWrapper<QuotaAdjustDTO> wrapper = new QueryWrapper<>();
        //审批状态条件查询
        wrapper.eq(StringUtils.isNotEmpty(quotaAdjustDTO.getStatus()), "STATUS", quotaAdjustDTO.getStatus());
        //创建人
        wrapper.like(StringUtils.isNotEmpty(quotaAdjustDTO.getCreatedBy()), "CREATED_BY", quotaAdjustDTO.getCreatedBy());
        //业务实体
        wrapper.eq(quotaAdjustDTO.getOrgId()!=null,"b.ORG_ID",quotaAdjustDTO.getOrgId());
        //物料小类
        wrapper.eq(quotaAdjustDTO.getCategoryId()!=null,"b.CATEGORY_ID",quotaAdjustDTO.getCategoryId());
        //供应商
        wrapper.eq(StringUtils.isNotEmpty(quotaAdjustDTO.getVendorName()),"b.VENDOR_NAME",quotaAdjustDTO.getVendorName());
        //物料编码
        wrapper.eq(StringUtils.isNotEmpty(quotaAdjustDTO.getItemCode()),"b.ITEM_CODE",quotaAdjustDTO.getItemCode());
        //过滤
        wrapper.groupBy("a.QUOTA_ADJUST_ID");
        //按照创建日期排序
        wrapper.orderByDesc("CREATION_DATE");
        return quotaAdjustMapper.quotaAdjustList(wrapper);
    }

    /**
     * 添加暂存
     *
     * @param quotaAdjustDTO
     */
    @Transactional
    @Override
    public void quotaAdjustAdd(QuotaAdjustDTO quotaAdjustDTO) {
        QuotaAdjust quotaAdjust = quotaAdjustDTO.getQuotaAdjust();
        Assert.notNull(quotaAdjust, "id不能为空");
        List<QuotaSourceDTO> quotaSourceDTOList = quotaAdjustDTO.getQuotaSourceDTOList();
        List<Fileupload> quotaAdjustFile = quotaAdjustDTO.getQuotaAdjustFile();
        //当配额调整id不存在时，为添加
        if (quotaAdjust.getQuotaAdjustId() == null) {
            //添加配额调整
            Long id = IdGenrator.generate();
            //添加id
            quotaAdjust.setQuotaAdjustId(id);
            //添加编号
            quotaAdjust.setQuotaAdjustCode(baseClient.seqGen(SequenceCodeConstant.SEQ_INQ_QUOTA_ADJUST_CODE));
            //默认审批状态为新建
            quotaAdjust.setStatus(AdjustStatus.get("DRAFT"));
            this.save(quotaAdjust);
        } else {
            this.updateById(quotaAdjust);
        }
        //添加寻源单号对应的列表
        if (CollectionUtils.isNotEmpty(quotaSourceDTOList)) {
            for (QuotaSourceDTO QuotaSourceDTO : quotaSourceDTOList) {
                if (QuotaSourceDTO.getQuotaSourceId()==null){
                    QuotaSourceDTO.setQuotaSourceId(IdGenrator.generate());
                    QuotaSourceDTO.setQuotaAdjustId(quotaAdjust.getQuotaAdjustId());
                    iQuotaSourceService.save(QuotaSourceDTO);
                }else {
                    iQuotaSourceService.updateById(QuotaSourceDTO);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(quotaAdjustFile)) {
            //添加附件
            fileCenterClient.bindingFileupload(quotaAdjustFile, quotaAdjust.getQuotaAdjustId());
        }
    }

    /**
     * 获取配额调整详情
     *
     * @param id
     * @return
     */
    @Override
    public QuotaAdjustDTO getQuotaAdjust(Long id) {
        QuotaAdjustDTO quotaAdjustDTO = new QuotaAdjustDTO();
        //获取表头信息
        quotaAdjustDTO.setQuotaAdjust(this.getById(id));
        //获取寻源单号对应的价格审批单
        List<QuotaSourceDTO> quotaSourceDTOList = iQuotaSourceService.quotaSourceList(new QuotaSource().setQuotaAdjustId(id));
        quotaAdjustDTO.setQuotaSourceDTOList(quotaSourceDTOList);
        //获取附件列表  模块名为"quotaAdjustFile"
        Fileupload quotaAdjustFile = new Fileupload().setBusinessId(id).setFileFunction("quotaAdjustFile");
        quotaAdjustFile.setPageSize(1000);
        PageInfo<Fileupload> quotaAdjustFileList = fileCenterClient.listPage(quotaAdjustFile, "");
        quotaAdjustDTO.setQuotaAdjustFile(quotaAdjustFileList.getList());
        //返回数据
        return quotaAdjustDTO;
    }

    /**
     *删除配额调整相关的数据
     * @param id
     */
    @Transactional
    @Override
    public void deleteQuotaAdjust(Long id) {
        //删除配额调整数据
        this.removeById(id);
        //删除价格相关审批数据
        iQuotaSourceService.remove(new QueryWrapper<>(new QuotaSource().setQuotaAdjustId(id)));
        //删除附件
        fileCenterClient.deleteByParam(new Fileupload().setBusinessId(id));
    }
    /**
     * 审批通过
     *
     * @param id
     */
    @Transactional
    @Override
    public void getApprove(Long id) {
        //修改状态
        UpdateWrapper<QuotaAdjust> wrapper = new UpdateWrapper<>();
        wrapper.set("STATUS", AdjustStatus.get("APPROVED"));
        wrapper.eq("QUOTA_ADJUST_ID",id);
        this.update(wrapper);
        //获取对应的价格审批列表的id
        QueryWrapper<QuotaSource> QuotaSourceWrapper = new QueryWrapper<>();
        QuotaSourceWrapper.eq("QUOTA_ADJUST_ID",id);
        List<QuotaSource> list = iQuotaSourceService.list(QuotaSourceWrapper);
        if (CollectionUtils.isNotEmpty(list)){
            List<Long> longs = new ArrayList<>();
            //添加需要的中标行
            for (QuotaSource quotaSource:list) {
                longs.add(quotaSource.getApprovalBiddingItemId());
            }
            QueryWrapper<ApprovalBiddingItem> approvalBiddingItemwrapper = new QueryWrapper<>();
            approvalBiddingItemwrapper.in("APPROVAL_BIDDING_ITEM_ID",longs);
            List<ApprovalBiddingItem> approvalBiddingItemList = iApprovalBiddingItemService.list(approvalBiddingItemwrapper);
            for (int i=0;i<approvalBiddingItemList.size();i++){
                if (list.get(i).getTargetProportion()!=null){
                    ApprovalBiddingItem approvalBiddingItem = approvalBiddingItemList.get(i);
                    approvalBiddingItem.setQuotaProportion(list.get(i).getTargetProportion());
                    approvalBiddingItemList.set(i,approvalBiddingItem);
                }
            }
            //回写配额到价格库
            iApprovalHeaderService.ceeaQuotaToPriceLibrary(approvalBiddingItemList);
        }


    }
}

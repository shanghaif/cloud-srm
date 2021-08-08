package com.midea.cloud.srm.sup.invite.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;

import com.midea.cloud.common.annotation.CacheData;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;

import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.invite.entity.InviteVendor;
import com.midea.cloud.srm.model.supplier.invite.enums.InviteVendorStatusEnum;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.invite.mapper.InviteVendorMapper;
import com.midea.cloud.srm.sup.invite.service.InviteVendorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *  邀请供应商 服务实现类
 * </pre>
 *
 * @author dengbin1@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 30, 2021 9:52:55 AM
 *  修改内容:
 * </pre>
 */
@Service
public class InviteVendorServiceImpl extends ServiceImpl<InviteVendorMapper, InviteVendor> implements InviteVendorService {

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private ICompanyInfoService iCompanyInfoService;
    @Autowired
    private InviteVendorService inviteVendorService;
    
    @Transactional
    public void batchUpdate(List<InviteVendor> inviteVendorList) {
        this.saveOrUpdateBatch(inviteVendorList);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<InviteVendor> inviteVendorList) throws IOException {
        for (InviteVendor inviteVendor : inviteVendorList) {
            if (inviteVendor.getInviteVendorId() == null) {
                Long id = IdGenrator.generate();
                inviteVendor.setInviteVendorId(id);
            }
        }
        if (!CollectionUtils.isEmpty(inviteVendorList)) {
            batchUpdate(inviteVendorList);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public PageInfo<InviteVendor> listPage(InviteVendor inviteVendor) {
        PageUtil.startPage(inviteVendor.getPageNum(), inviteVendor.getPageSize());
        List<InviteVendor> inviteVendors = getInviteVendors(inviteVendor);
        return new PageInfo<>(inviteVendors);
    }

    /*** 新增数据 ***/
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addNewData(InviteVendor inviteVendor) {
        Long id = IdGenrator.generate();
        inviteVendor.setInviteVendorId(id);
        inviteVendor.setInviteStatus(InviteVendorStatusEnum.DRAFT.getValue());
        inviteVendor.setInviteVendorNo(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_INVITE_VENDOR_NO));
        this.save(inviteVendor);
    }

    /*** 发布邀请 ***/
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void publishInviteVendor(InviteVendor inviteVendor) {
        // 修改状态，保存数据
        Long id = inviteVendor.getInviteVendorId();
        inviteVendor.setInviteStatus(InviteVendorStatusEnum.INVITING.getValue());
        if (id == null) {
            id = IdGenrator.generate();
            inviteVendor.setInviteVendorId(id);
            inviteVendor.setInviteVendorNo(baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_INVITE_VENDOR_NO));
            inviteVendor.setPublishDate(new Date());
            this.save(inviteVendor);
        } else {
            InviteVendor temp = this.getById(id);
            temp.setInviteStatus(inviteVendor.getInviteStatus());
            temp.setPublishDate(new Date());
            this.updateById(temp);
        }
        // 发送邮件
        inviteVendorService.sendEmail(inviteVendor, inviteVendor.getInviteVendorNo());
    }

    /*** 更新邀请供应商注册状态 ***/
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateInviteStatus() {
        QueryWrapper<InviteVendor> wrapper = new QueryWrapper<>();
        wrapper.eq("INVITE_STATUS", InviteVendorStatusEnum.INVITING.getValue());
        List<InviteVendor> inviteVendors = this.list(wrapper);
        if (CollectionUtils.isNotEmpty(inviteVendors)) {
            // 根据名称去公司表中查询是否已经存在，存在则修改状态为已注册
            List<String> vendorName = inviteVendors.stream().map(InviteVendor::getVendorName).collect(Collectors.toList());
            QueryWrapper<CompanyInfo> companyWrapper = new QueryWrapper<>();
            companyWrapper.in("COMPANY_NAME", vendorName);
            List<CompanyInfo> companyInfos = iCompanyInfoService.list(companyWrapper);
            if (CollectionUtils.isEmpty(companyInfos)) {
                return;
            }
            List<InviteVendor> updateList = new ArrayList<>();
            for (CompanyInfo companyInfo : companyInfos) {
                for (InviteVendor inviteVendor : inviteVendors) {
                    if (inviteVendor.getVendorName().equals(companyInfo.getCompanyName())) {
                        inviteVendor.setInviteStatus(InviteVendorStatusEnum.REGISTERED.getValue());
                        inviteVendor.setVendorId(companyInfo.getCompanyId());
                        updateList.add(inviteVendor);
                    }
                }
            }
            this.batchUpdate(updateList);
        }
    }

    @Value("${cloudNsrm.url}")
    private String cloudUrl;
    @Value("${cloudNsrm.settlementProcessPng}")
    private String settlementProcessPng;
    @Value("${cloudNsrm.companyName}")
    private String buyersCompanyName;

    // 发送邮件，邮件链接设置缓存时效3天，第二个参数作为key
    @CacheData(keyName = RedisKey.EMAIL_URL_CACHE, paramIndex = {1}, cacheTime = 3600*24*3,interfaceName = "供应商邀请注册接口")
    public String sendEmail(InviteVendor inviteVendor, String inviteVendorNo) {
        String title = "供应商注册邀请";
        String currentUrl = cloudUrl + "login?regType=invite&code=" + inviteVendorNo;
        // 替换base64转码图片中的+号
        String curSettlementProcessPng = settlementProcessPng.replace("+", "%2B");
        String content = String.format("<p> 尊敬的%s先生/小姐，你好！</p>" +
                        "\n" +
                        "\n" +
                        "<p>  我们是%s，为了加强我司与%s的采购业务交流，互相促进采购业务的发展，现在诚挚地邀请贵公司注册入驻我司采购系统。点击链接可直接注册： <a href='%s'>%s</a>。</p>" +
                        "\n<img height=\"200\" width=\"1100\" src='%s'/>",
                inviteVendor.getContactPerson(), buyersCompanyName, inviteVendor.getVendorName(), currentUrl, currentUrl, curSettlementProcessPng);
        new Thread(() -> {
            int tryNoticeNum = 0;
            while (tryNoticeNum < 3) {
                try {
                    baseClient.sendEmail(inviteVendor.getContactEmail(), title, content);
                    tryNoticeNum = 3;
                } catch (Exception ex) {
                    log.error("邮件发送密码失败，请检查邮箱配置", ex);
                    try {
                        Thread.sleep(3000); // 3秒重试
                        tryNoticeNum++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
        return currentUrl;
    }

    public List<InviteVendor> getInviteVendors(InviteVendor inviteVendor) {
        QueryWrapper<InviteVendor> wrapper = new QueryWrapper<>();
        // 供应商名称
        if (StringUtils.isNotBlank(inviteVendor.getVendorName())) {
            wrapper.like("VENDOR_NAME", inviteVendor.getVendorName()); // 模糊匹配
        }
        // 联系人
        if (StringUtils.isNotBlank(inviteVendor.getContactPerson())) {
            wrapper.like("CONTACT_PERSON", inviteVendor.getContactPerson()); // 模糊匹配
        }
        // 邮箱
        if (StringUtils.isNotBlank(inviteVendor.getContactEmail())) {
            wrapper.like("CONTACT_EMAIL", inviteVendor.getContactEmail()); // 模糊匹配
        }
        // 状态
        if (StringUtils.isNotBlank(inviteVendor.getInviteStatus())) {
            wrapper.eq("INVITE_STATUS", inviteVendor.getInviteStatus()); // 精准匹配
        }
        if (inviteVendor.getPublishStartDate() != null) {
            wrapper.ge("PUBLISH_DATE", inviteVendor.getPublishStartDate());
        }
        if (inviteVendor.getPublishEndDate() != null) {
            wrapper.le("PUBLISH_DATE", inviteVendor.getPublishEndDate());
        }

        return this.list(wrapper);
    }
}

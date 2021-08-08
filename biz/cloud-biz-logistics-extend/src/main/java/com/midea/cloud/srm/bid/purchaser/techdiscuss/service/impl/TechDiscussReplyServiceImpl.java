package com.midea.cloud.srm.bid.purchaser.techdiscuss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bid.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.bid.purchaser.techdiscuss.mapper.TechDiscussReplyMapper;
import com.midea.cloud.srm.bid.purchaser.techdiscuss.service.ITechDiscussProjInfoService;
import com.midea.cloud.srm.bid.purchaser.techdiscuss.service.ITechDiscussReplyService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussReply;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.vo.TechDiscussReplyVO;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidVendorFileVO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ContactInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  技术交流项目供应商回复表 服务实现类
 * </pre>
*
* @author fengdc3@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
*/
@Service
public class TechDiscussReplyServiceImpl extends ServiceImpl<TechDiscussReplyMapper, TechDiscussReply> implements ITechDiscussReplyService {
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private IBidVendorFileService iBidVendorFileService;
    @Autowired
    private ITechDiscussProjInfoService iTechDiscussProjInfoService;
    @Autowired
    private BaseClient baseClient;

    @Override
    public TechDiscussReplyVO saveTechDiscussReplyInfo(TechDiscussReplyVO techDiscussReplyVO) {
        //获取供应商ID
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        Long vendorId = user.getCompanyId();
        Long replyId = techDiscussReplyVO.getReplyId();
        if(null == replyId){
            TechDiscussReply techDiscussReply = new TechDiscussReply();
            Long id = IdGenrator.generate();
            techDiscussReply.setReplyId(id);
            techDiscussReply.setReplyCode(baseClient.seqGen(SequenceCodeConstant.SEQ_TECH_DICUSS_REPLY_CODE));
            techDiscussReply.setStatus(techDiscussReplyVO.getStatus());
            techDiscussReply.setProjId(techDiscussReplyVO.getProjId());
            //设置供应商编码和名称
            CompanyInfo companyInfo = supplierClient.getCompanyInfo(vendorId);
            techDiscussReply.setSupplierCode(companyInfo.getCompanyCode());
            techDiscussReply.setSupplierName(companyInfo.getCompanyName());
            //设置联系人信息
            ContactInfo info = new ContactInfo();
            info.setCompanyId(vendorId);
            ContactInfo contactInfo = supplierClient.getContactInfoByParam(info);
            techDiscussReply.setEmail(contactInfo.getEmail());
            techDiscussReply.setTelephone(contactInfo.getPhoneNumber());
            techDiscussReply.setContactName(contactInfo.getContactName());
            this.save(techDiscussReply);
            techDiscussReplyVO.setReplyId(id);
        }else {
            TechDiscussReply techDiscussReply = new TechDiscussReply();
            BeanCopyUtil.copyProperties(techDiscussReply,techDiscussReplyVO);
            this.updateById(techDiscussReply);
        }
        //2 保存附件信息
        List<BidVendorFileVO> vendorFileVOS = techDiscussReplyVO.getFiles();
        Long businessId = techDiscussReplyVO.getReplyId();
        String fileType = VendorFileType.TECH_DISCUSS_REPLY.getValue();
        vendorFileVOS.forEach(vendorFileVO -> {vendorFileVO.setFileType(fileType);vendorFileVO.setBusinessId(businessId);});
        boolean vendorFileflag = iBidVendorFileService.saveBatchVendorFilesByBusinessIdAndFileType(vendorFileVOS);

        return techDiscussReplyVO;
    }

    @Override
    public List<TechDiscussReplyVO> listTechDiscussReplyInfo(TechDiscussReplyVO techDiscussReplyVO) {
        return this.baseMapper.listTechDiscussReplyInfo(techDiscussReplyVO);
    }
}

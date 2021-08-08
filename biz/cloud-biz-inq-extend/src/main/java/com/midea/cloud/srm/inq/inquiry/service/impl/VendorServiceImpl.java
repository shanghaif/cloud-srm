package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.InquiryFileType;
import com.midea.cloud.common.enums.QuoteStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.enums.inq.QuoteStatusEnum;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteQueryDto;
import com.midea.cloud.srm.inq.inquiry.mapper.VendorMapper;
import com.midea.cloud.srm.inq.inquiry.service.*;
import com.midea.cloud.srm.inq.quote.service.IQuoteItemService;
import com.midea.cloud.srm.inq.quote.service.IQuoteSelectionService;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteItemDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Item;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuoteAuth;
import com.midea.cloud.srm.model.inq.inquiry.entity.Vendor;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItem;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteLadderPrice;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteSelection;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
*  <pre>
 *  询价-邀请供应商表 服务实现类
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
@Service
public class VendorServiceImpl extends ServiceImpl<VendorMapper, Vendor> implements IVendorService {

    @Autowired
    private IVendorService iVendorService;

    @Autowired
    private IQuoteAuthService iQuoteAuthService;

    @Autowired
    private ILadderPriceService iLadderPriceService;

    @Autowired
    private IItemService iItemService;

    @Resource
    VendorMapper vendorMapper;

    @Autowired
    private IQuoteSelectionService iQuoteSelectionService;

    @Autowired
    private IQuoteItemService iQuoteItemService;

    @Autowired
    private IFileService iFileService;

    @Override
    public List<Vendor> getByHeadId(Long inquiryId) {
        QueryWrapper warapper = new QueryWrapper();
        warapper.eq("INQUIRY_ID", inquiryId);
        return iVendorService.list(warapper);
    }

    @Override
    public List<QuoteHeaderDto> getByVendorId(Long vendorId) {
        QuoteHeaderDto quoteHeaderDto = new QuoteHeaderDto();
        QueryWrapper warapper = new QueryWrapper();
        warapper.eq("VENDOR_ID", vendorId);
        List<QuoteHeaderDto> dto = vendorMapper.getByVendorId(vendorId,null,null,null,null,null);
        return dto;
    }

    @Override
    public List<QuoteHeaderDto> getByVendorId(QuoteQueryDto quoteQueryDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<QuoteHeaderDto> dto = vendorMapper.getByVendorId(loginAppUser.getCompanyId(),quoteQueryDto.getInquiryNo(),quoteQueryDto.getItemCode(),quoteQueryDto.getStatus(),quoteQueryDto.getInquiryStatus(),quoteQueryDto.getOrganizationId());
        if (CollectionUtils.isEmpty(dto)) {
            return dto;
        }

        List<Long> quoteIds = new ArrayList<>();
        /*过滤出有报价信息并且是已报价状态*/
        dto.stream().filter(f -> f.getQuoteId() != null && QuoteStatusEnum.SUBMIT.getKey().equals(f.getQuoteStatus()))
                .forEach(head -> quoteIds.add(head.getQuoteId()));
        if(CollectionUtils.isNotEmpty(quoteIds)) {
            /*如果有报价信息则查询报价行信息*/
            List<QuoteItem> quoteItemByQuteIds = iQuoteItemService.getQuoteItemByQuteIds(quoteIds);
            dto.forEach(head -> {
                /*找到对应报价行信息*/
                if (head.getQuoteId() != null && QuoteStatusEnum.SUBMIT.getKey().equals(head.getQuoteStatus())
                        && CollectionUtils.isNotEmpty(quoteItemByQuteIds)) {
                    List<QuoteItem> quoteItems = quoteItemByQuteIds.stream().filter(f ->
                            f.getQuoteId().equals(head.getQuoteId())).collect(Collectors.toList());
                    /*查询评选信息*/
                    List<Long> quoteItemIds = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(quoteItems)) {
                        quoteItems.forEach(quoteItem -> quoteItemIds.add(quoteItem.getQuoteItemId()));
                    }
                    List<QuoteSelection> quoteItemSelections = iQuoteSelectionService.getQuoteSelectionByQuoteItemId(quoteItemIds);
                    if (CollectionUtils.isNotEmpty(quoteItemSelections)) {
                        /*找到有选定的报价*/
                        boolean match = quoteItemSelections.stream().anyMatch(f -> YesOrNo.YES.getValue().equals(f.getIsSelected()));
                        if (match) {
                            head.setInquiryResult(QuoteStatus.SELECTED.getKey());
                        }
                    }
                }
            });
        }
        return dto;
    }

    @Override
    public QuoteHeaderDto getInquiryInfo(Long vendorId,Long inquiryId) {
        QuoteHeaderDto quoteHeaderDto = new QuoteHeaderDto();
        QueryWrapper warapper = new QueryWrapper();
        warapper.eq("VENDOR_ID", vendorId);
        QuoteHeaderDto dto = vendorMapper.getByInquiryId(vendorId,inquiryId);
        if(null !=dto){
            BeanUtils.copyProperties(dto,quoteHeaderDto);
            List<QuoteItemDto>  items = new ArrayList();
            //获取报价权限
            QueryWrapper query = new QueryWrapper();
            query.eq("INQUIRY_ID",inquiryId);
            query.eq("VENDOR_ID",vendorId);
            query.eq("QUOTE_FORBID","N");
            List<QuoteAuth> authList = iQuoteAuthService.list(query);
            authList.forEach(auth ->{
                QuoteItemDto itemDto = new QuoteItemDto();
                Item item = iItemService.getById(auth.getInquiryItemId());
                BeanUtils.copyProperties(item,itemDto);
                QueryWrapper ladder = new QueryWrapper();
                ladder.eq("INQUIRY_ITEM_ID",auth.getInquiryItemId());
                List<QuoteLadderPrice> prices = iLadderPriceService.list(ladder);
                itemDto.setLadderPrices(prices);
                items.add(itemDto);
            });
            quoteHeaderDto.setItems(items);
        }
        quoteHeaderDto.setOuterFiles(iFileService.getByHeadId(inquiryId, InquiryFileType.OUTER.toString()));
        return quoteHeaderDto;
    }

    @Override
    public List<Vendor> getByInquiryVendorIds(List<Long> inquiryVendorIds) {
        QueryWrapper<Vendor> warapper = new QueryWrapper<>();
        warapper.in("INQUIRY_VENDOR_ID", inquiryVendorIds);
        return list(warapper);
    }

    @Override
    public Integer getWaitQuote() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        /*查询该供应商待报价的数据*/
        List<QuoteHeaderDto> headerDtos =
                vendorMapper.getByVendorId(loginAppUser.getCompanyId(), null, null, QuoteStatusEnum.SAVE.getKey(), null, null);
        if (CollectionUtils.isEmpty(headerDtos)) {
            return 0;
        }
        return headerDtos.size();
    }
}

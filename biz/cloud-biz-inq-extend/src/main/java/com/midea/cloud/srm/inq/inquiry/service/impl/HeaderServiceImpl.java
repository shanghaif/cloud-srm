package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.InquiryFileType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.DateChangeUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.flow.WorkFlowFeign;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.common.enums.inq.InquiryPublishStatusEnum;
import com.midea.cloud.common.enums.inq.SelectionTypeEnum;
import com.midea.cloud.srm.inq.inquiry.mapper.HeaderMapper;
import com.midea.cloud.srm.inq.inquiry.service.*;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.flow.process.dto.CbpmRquestParamDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.InquiryDeadlineRequestDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.InquiryHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.*;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.param.FollowNameParam;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  询价-询价信息头表 服务实现类
 * </pre>
 *
 * @author zhongbh
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
 */
@Service
public class HeaderServiceImpl extends ServiceImpl<HeaderMapper, Header> implements IHeaderService {
    @Autowired
    private IFileService iFileService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private IHeaderService iHeaderService;
    @Autowired
    private IItemService iItemService;
    @Autowired
    private IVendorService iVendorService;
    @Autowired
    private IScoreRuleService iScoreRuleService;
    @Autowired
    private IScoreRuleItemService iScoreRuleItemService;
    @Autowired
    private ILadderPriceService iLadderPriceService;
    @Autowired
    private IQuoteAuthService iQuoteAuthService;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private RbacClient rbacClient;
    @Autowired
    private WorkFlowFeign workFlowFeign;
    @Autowired
    private PmClient pmClient;

    @Override
    public InquiryHeaderDto getHeadById(Long headId) {
        InquiryHeaderDto dto = new InquiryHeaderDto();
        dto.setHeader(iHeaderService.getById(headId));
        dto.setInnerFiles(iFileService.getByHeadId(headId, InquiryFileType.INNER.toString()));
        dto.setOuterFiles(iFileService.getByHeadId(headId, InquiryFileType.OUTER.toString()));
        dto.setItems(iItemService.getByHeadId(headId));
        dto.setVendors(iVendorService.getByHeadId(headId));
        ScoreRule scoreRule = iScoreRuleService.getByHeadId(headId);
        dto.setScoreRule(scoreRule);
        if (null != scoreRule && null != scoreRule.getScoreRuleId()) {
            dto.setRuleItems(iScoreRuleItemService.getByRuleId(scoreRule.getScoreRuleId()));
        }
        List<QuoteAuth> quoteAuth = iQuoteAuthService.getByHeadId(headId);
        dto.setQuoteAuth(quoteAuth);
        return dto;
    }

    @Override
    public Long saveAndUpdate(InquiryHeaderDto header, String actionType) {
        Header head = header.getHeader();
        Long id = head.getInquiryId();
        if (null == id) {
            id = IdGenrator.generate();
            head.setInquiryId(id);
            head.setInquiryNo(baseClient.seqGen(SequenceCodeConstant.SEQ_INQ_INQUIRYNO_CODE));
        }
        saveHeader(header, actionType);
        saveInquiryFiles(header.getInnerFiles(),id,InquiryFileType.INNER.toString());
        saveInquiryFiles(header.getOuterFiles(),id, InquiryFileType.OUTER.toString());
        saveItems(header, id);
        saveVendors(header.getVendors(), id);
        saveScoreRule(header.getScoreRule(), header.getRuleItems(), id);
        return id;
    }

    @Override
    @Transactional
    public List<Item> saveItemsByExcel(Long inquiryId,List<Item> list) {
        Header header = this.getById(inquiryId);
        Assert.notNull(header,"找不到询价单");

        List<MaterialItem> materialItems = baseClient.listAllMaterialItem();
        List<DictItemDTO> currenyDictItems = baseClient.listAllByDictCode("BID_TENDER_CURRENCY");
        List<DictItemDTO> itemTypeDictItems = baseClient.listAllByDictCode("DMAND_LINE_TYPE");

        list.forEach(item->{
            PurchaseTax purchaseTax = baseClient.getByTaxKeyAndLanguage(item.getTaxKey(), LocaleHandler.getLocaleKey());
            Assert.notNull(purchaseTax,  "税率编码'"+item.getTaxKey()+"'不存在");

            item.setTaxRate(purchaseTax.getTaxCode().toString());

            MaterialItem materialItem = getMaterialItem(materialItems,item);
            Assert.notNull(materialItem,"不存在物料编号为：'"+item.getItemCode()+"'" +
                    ",物料名称为：'"+item.getItemDesc()+"'" +
                    ",物料分类为：'"+item.getCategoryName()+"'" +
                    "的物料");

            String itemType = getDictItemCode(itemTypeDictItems,item.getItemType());
            Assert.notNull(itemType,"不存在名称为：'"+item.getItemType()+"'的行类型");

            String curreny = getDictItemCode(currenyDictItems,item.getCurrency());
            Assert.notNull(curreny,"不存在名称为：'"+item.getCurrency()+"'的币种");

            item.setInquiryItemId(IdGenrator.generate());
            item.setInquiryId(inquiryId);
            item.setOrganizationId(header.getOrganizationId());

            item.setCurrency(curreny);
            item.setItemType(itemType);

            item.setItemId(materialItem.getMaterialId());
            item.setCategoryId(materialItem.getCategoryId());
            item.setCategoryName(materialItem.getCategoryName());
            item.setUnit(materialItem.getUnit());
        });
        iItemService.saveBatch(list);
        return list;
    }

    @Override
    @Transactional
    public List<Vendor> saveVendorsByExcel(Long inquiryId, List<Vendor> list) {
        Header header = this.getById(inquiryId);
        Assert.notNull(header,"找不到询价单");

        List<CompanyInfo> companyInfos = supplierClient.listAllCompanyInfo();

        list.forEach(item->{
            CompanyInfo companyInfo = getCompanyInfo(companyInfos,item.getVendorCode(),item.getVendorName());
            if(StringUtils.isBlank(item.getVendorName())){
                Assert.notNull(companyInfo,"不存在编码为：'"+item.getVendorCode()+"'的已批准供应商");
            }else{
                Assert.notNull(companyInfo,"不存在编码为：'"+item.getVendorCode()+"',名称为:'"+item.getVendorName()+"'的已批准供应商");
            }

            item.setVendorId(companyInfo.getCompanyId());
            item.setVendorName(companyInfo.getCompanyName());

            item.setInquiryVendorId(IdGenrator.generate());
            item.setInquiryId(inquiryId);
        });
        iVendorService.saveBatch(list);
        return list;
    }

    @Override
    public void changDeadline(InquiryDeadlineRequestDTO request) {

        //截止时间
        Date deadline = DateChangeUtil.formatDate(request.getDeadline(), null);
        Date now = new Date();

        Header header = getById(request.getInquiryId());
        header.setDeadline(deadline);
        if (deadline.before(now)) {
            header.setStatus(InquiryPublishStatusEnum.CLOSE_QUOTATION.getKey());
        }else{
            header.setStatus(InquiryPublishStatusEnum.PUBLISHED.getKey());
        }
        updateById(header);
    }

    @Override
    public Map<String, Object> initWorkFlow(Long inquiryId, Long menuId) {
        Header header = getById(inquiryId);
        Permission permission = rbacClient.getMenu(menuId);
        boolean flowEnable = workFlowFeign.getFlowEnable(menuId, permission.getFunctionId(), CbpmFormTemplateIdEnum.INQUIRY_FLOW.getKey());
        if (flowEnable) {
            return workFlowFeign.initProcess(
                    buildCbpmRquestParamDTO(CbpmFormTemplateIdEnum.INQUIRY_FLOW.getKey(), header));

        }
        return null;
    }

    @Override
    @Transactional
    public String requirementGenInquiry(List<RequirementLine> requirementLineList) {
        String code = null;
        if (CollectionUtils.isNotEmpty(requirementLineList)) {
            //需要初始化的字段写这里
            Header head = new Header();
            Long headId = IdGenrator.generate();
            head.setInquiryId(headId);
            code = baseClient.seqGen(SequenceCodeConstant.SEQ_INQ_INQUIRYNO_CODE);
            head.setInquiryNo(code);
            head.setStatus(InquiryPublishStatusEnum.DRAFT.getKey());
            head.setAuditStatus(ApproveStatusType.DRAFT.getValue());
            head.setQuoteCnt(BigDecimal.ZERO);
            head.setInviteCnt(BigDecimal.ZERO);
            head.setOrganizationId(requirementLineList.get(0).getOrgId());
            head.setOrganizationName(requirementLineList.get(0).getPurchaseOrganization());
            iHeaderService.saveOrUpdate(head);

            for(RequirementLine requirementLine : requirementLineList){
                Item newItem = new Item();
                newItem.setInquiryItemId(IdGenrator.generate());
                newItem.setOrganizationId(requirementLine.getOrgId());
//                newItem.setItemId(requirementLine.getItemId());
//                newItem.setItemCode(requirementLine.getItemCode());
//                newItem.setItemDesc(requirementLine.getItemDesc());
                newItem.setCategoryId(requirementLine.getCategoryId());
                newItem.setCategoryName(requirementLine.getCategoryName());
                newItem.setDemandQuantity(requirementLine.getRequirementQuantity());
                newItem.setUnit(requirementLine.getUnitCode());
                newItem.setOrderQuantity(requirementLine.getOrderQuantity());
                newItem.setNotaxPrice(requirementLine.getNotaxPrice());
                newItem.setTaxKey(requirementLine.getTaxKey());
                newItem.setTaxRate(requirementLine.getTaxRate() != null ? requirementLine.getTaxRate().toString() : null);
                newItem.setInquiryId(headId);
                iItemService.save(newItem);
            }

        /*List<LadderPrice> ladderPrices = item.getLadderPrices();
        for (LadderPrice ladder : ladderPrices) {
            ladder.setInquiryId(headId);
            ladder.setInquiryItemId(newItem.getInquiryItemId());
            ladder.setInquiryLadderPriceId(IdGenrator.generate());
        }
        iLadderPriceService.saveBatch(ladderPrices);*/
        }

        return code;
    }

    @Override
    public Long commit(InquiryHeaderDto header, String actionType) {
        Header head = header.getHeader();
        Long id = head.getInquiryId();
        if (null == id) {
            throw new BaseException(ResultCode.NEED_PERMISSION);
        }
        if ("PASS".equals(actionType)) {
            head.setStatus(InquiryPublishStatusEnum.UNPUBLISH.getKey());
            head.setAuditStatus(ApproveStatusType.APPROVED.getValue());
        } else if ("REJECTED".equals(actionType)) {
            head.setStatus(InquiryPublishStatusEnum.DRAFT.getKey());
            head.setAuditStatus(ApproveStatusType.REJECTED.getValue());
        }else if ("PUBLISH".equals(actionType)) {
            head.setPublishDate(new Date());
            head.setStatus(InquiryPublishStatusEnum.PUBLISHED.getKey());
            head.setAuditStatus(ApproveStatusType.APPROVED.getValue());
        }else if ("CANCEL".equals(actionType)) {
            head.setStatus(InquiryPublishStatusEnum.CANCEL.getKey());
            head.setAuditStatus(ApproveStatusType.APPROVED.getValue());
        }
        iHeaderService.updateById(head);
        return id;
    }

    @Override
    @Transactional
    public Long publish(Header head, String actionType) {
        Long id = head.getInquiryId();
        if (null == id) {
            throw new BaseException(ResultCode.NEED_PERMISSION);
        }
        if ("PUBLISH".equals(actionType)) {
            head.setPublishDate(new Date());
            head.setStatus(InquiryPublishStatusEnum.PUBLISHED.getKey());
        }else if ("CANCEL".equals(actionType)) {
//            head.setPublishDate(new Date());
            head.setStatus(InquiryPublishStatusEnum.CANCEL.getKey());
        }

        iHeaderService.updateById(head);

        Header uHeader = this.getById(id);

        head.setInquiryNo(uHeader.getInquiryNo());
        head.setInquiryTitle(uHeader.getInquiryTitle());

        //发布时校验询价单是否来自采购需求,如果是的话就往采购需求行回写后续单据名称
//        pmClient.updateIfExistRequirementLine(new FollowNameParam().setHeader(head));

        return id;
    }

    /**
     * 构建初始化流程参数
     */
    private CbpmRquestParamDTO buildCbpmRquestParamDTO(String templateCode, Header header) {

        CbpmRquestParamDTO cbpmRquestParam = new CbpmRquestParamDTO();
        cbpmRquestParam.setTemplateCode(templateCode);
        cbpmRquestParam.setBusinessId(String.valueOf(header.getInquiryId()));
        cbpmRquestParam.setSubject(header.getInquiryNo() + "询价单流程");
        cbpmRquestParam.setFdId(header.getCbpmInstaceId());
        return cbpmRquestParam;
    }

    private void saveHeader(InquiryHeaderDto headDto, String actionType) {
        //需要初始化的字段写这里
        Header head = headDto.getHeader();
        head.setStatus(InquiryPublishStatusEnum.DRAFT.getKey());
        head.setAuditStatus(ApproveStatusType.DRAFT.getValue());
        if ("SUBMIT".equals(actionType)) {
            head.setStatus(InquiryPublishStatusEnum.UNPUBLISH.getKey());
            head.setAuditStatus(ApproveStatusType.SUBMITTED.getValue());
        }
        head.setInviteCnt(new BigDecimal(headDto.getVendors().size()));
        head.setQuoteCnt(new BigDecimal("0"));
        head.setDeadline(DateChangeUtil.getDateMaxTime(head.getDeadline()));
        iHeaderService.saveOrUpdate(head);
    }

    private void saveInquiryFiles(List<File> files, Long headId,String type) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("INQUIRY_ID", headId);
        wrapper.eq("TYPE", type);
        iFileService.remove(wrapper);
        files.forEach(file -> {
            file.setInquiryFileId(IdGenrator.generate());
            file.setInquiryId(headId);
        });
        iFileService.saveBatch(files);
    }

    private void saveItems(InquiryHeaderDto header, Long headId) {
        List<ItemDto> items = header.getItems();
        /*保存报价权限*/
        if (CollectionUtils.isNotEmpty(header.getQuoteAuth())) {
            saveQuoteAuth(header, headId);
        }
        for (ItemDto item:items){
            if(null !=item.getInquiryItemId()){
                Item item1 = new Item();
                BeanUtils.copyProperties(item,item1);
                iItemService.updateById(item1);
//                auth.eq("INQUIRY_ITEM_ID",item.getInquiryItemId());
//                for(QuoteAuth quote:quoteAuth){
//                    quote.setInquiryQuoteAuthId(IdGenrator.generate());
//                    quote.setInquiryItemId(item.getInquiryItemId());
//                    quote.setInquiryId(headId);
//                }

                QueryWrapper ladder = new QueryWrapper();
                List<LadderPrice> ladderPrices = item.getLadderPrices();
                ladder.eq("INQUIRY_ID", headId);
                ladder.eq("INQUIRY_ITEM_ID",item.getInquiryItemId());
                iLadderPriceService.remove(ladder);
                for(LadderPrice price:ladderPrices){
                    price.setInquiryLadderPriceId(IdGenrator.generate());
                    price.setInquiryItemId(item.getInquiryItemId());
                    price.setInquiryId(headId);
                }
                iLadderPriceService.saveBatch(ladderPrices);
            }else{
                Item newItem = new Item();
                BeanUtils.copyProperties(item,newItem);
                newItem.setInquiryItemId(IdGenrator.generate());
                newItem.setInquiryId(headId);
                iItemService.save(newItem);
                List<LadderPrice> ladderPrices = item.getLadderPrices();
                for(LadderPrice ladder:ladderPrices){
                    ladder.setInquiryId(headId);
                    ladder.setInquiryItemId(newItem.getInquiryItemId());
                    ladder.setInquiryLadderPriceId(IdGenrator.generate());
                }
                iLadderPriceService.saveBatch(ladderPrices);
            }
        }
    }

    /**
     * 保存报价权限
     */
    private void saveQuoteAuth(InquiryHeaderDto header, Long headId) {

        List<QuoteAuth> quoteAuth = header.getQuoteAuth();
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("INQUIRY_ID", headId);
//        iItemService.remove(wrapper);
//        iLadderPriceService.remove(wrapper);
        if (CollectionUtils.isNotEmpty(quoteAuth) && SelectionTypeEnum.QUOTE_BY_SUPPLIER.toString().equals(header.getHeader().getQuoteRule())) {
            /*报价方式为组合的方式，供应商组合报价物料必须一致*/
            QuoteAuth auth = quoteAuth.get(0);
            /*以第一个供应商为准，过滤出第一个供应商的权限信息*/
            List<QuoteAuth> quoteAuths = quoteAuth.stream().filter(f ->
                    f.getVendorId().equals(auth.getVendorId())).collect(Collectors.toList());
            /*按照该供应商的每条权限对比其他供应商对应的物料权限*/
            quoteAuths.forEach(quote -> quoteAuth.stream()
                    .filter(f -> f.getInquiryItemId().equals(quote.getInquiryItemId()))
                    .forEach(each -> {
                        if (!quote.getQuoteForbid().equals(each.getQuoteForbid())) {
                            throw new BaseException("供应商组合报价物料必须一致");
                        }
                    }));
        }
        //已经校验过的供应商，减少校验次数
        HashSet<Long> hasCheckVendors = new HashSet<>();
        if (CollectionUtils.isNotEmpty(quoteAuth)) {
            quoteAuth.forEach(quote -> {
                /*没有被校验的供应商才进行校验*/
                if (!hasCheckVendors.contains(quote.getVendorId())) {
                    boolean allForbid = true;
                    /*根据供应商id过滤*/
                    List<QuoteAuth> quoteAuths = quoteAuth.stream().filter(f ->
                            f.getVendorId().equals(quote.getVendorId())).collect(Collectors.toList());
                    for (QuoteAuth auth : quoteAuths) {
                        if (YesOrNo.NO.getValue().equals(auth.getQuoteForbid())) {
                            allForbid = false;
                        }
                    }
                    if (allForbid) {
                        throw new BaseException("供应商报价的物料不能为空");
                    }
                    hasCheckVendors.add(quote.getVendorId());
                }
                quote.setInquiryQuoteAuthId(IdGenrator.generate());
            });
        }

        QueryWrapper auth = new QueryWrapper();
        auth.eq("INQUIRY_ID", headId);
        iQuoteAuthService.remove(auth);
        iQuoteAuthService.saveBatch(quoteAuth);
    }

    private void saveVendors(List<Vendor> vendors, Long headId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("INQUIRY_ID", headId);
        iVendorService.remove(wrapper);
        if (vendors.size() > 0) {
            vendors.forEach(vendor -> {
                vendor.setInquiryVendorId(IdGenrator.generate());
                vendor.setInquiryId(headId);
            });
            iVendorService.saveBatch(vendors);
        }
    }

    private void saveQuoteAuth(List<QuoteAuth> quoteAuths, Long headId) {
        if (quoteAuths.size() > 0) {
            quoteAuths.forEach(vendor -> {
                vendor.setInquiryQuoteAuthId(IdGenrator.generate());
                vendor.setInquiryId(headId);
            });
            iQuoteAuthService.saveBatch(quoteAuths);
        }
    }

    private void saveScoreRule(ScoreRule rule, List<ScoreRuleItem> ruleItems, Long headId) {
        if(null !=rule.getScoreRuleId()){
            iScoreRuleService.removeById(rule.getScoreRuleId());
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("SCORE_RULE_ID", rule.getScoreRuleId());
            iScoreRuleItemService.remove(wrapper);
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("INQUIRY_ID", headId);
        iScoreRuleService.remove(wrapper);
        rule.setInquiryId(headId);
        iScoreRuleService.save(rule);
        if (ruleItems.size() > 0) {
            ruleItems.forEach(item -> {
                item.setScoreRuleId(rule.getScoreRuleId());
                item.setScoreRuleItemId(IdGenrator.generate());
            });
            iScoreRuleItemService.saveBatch(ruleItems);
        }
    }

    /**
     * 通过字典类型名称查询字典类型编号
     * @param dictItems
     * @param dictItemName
     * @return
     */
    private String getDictItemCode(List<DictItemDTO> dictItems, String dictItemName) {
        for(DictItemDTO dictItemDTO:dictItems){
            if(StringUtils.equals(dictItemName,dictItemDTO.getDictItemName())){
                return dictItemDTO.getDictItemCode();
            }
        }
        return null;
    }

    /**
     * 通过物料属性获取物料Id
     * @param materialItems
     * @param item
     * @return
     */
    private MaterialItem getMaterialItem(List<MaterialItem> materialItems, Item item){
        for(MaterialItem materialItem:materialItems){
            if(StringUtils.equals(item.getItemCode(),materialItem.getMaterialCode())
                    &&StringUtils.equals(item.getItemDesc(),materialItem.getMaterialName())
                    &&StringUtils.equals(item.getCategoryName(),materialItem.getCategoryName())){
                return materialItem;
            }
        }
        return null;
    }

    /**
     * 通过供应商编码获取供应商ID
     * @param companyInfos
     * @param companyCode
     * @return
     */
    private CompanyInfo getCompanyInfo(List<CompanyInfo> companyInfos,String companyCode,String companyName){
        for(CompanyInfo companyInfo:companyInfos){
            if(StringUtils.isBlank(companyName)){
                if(StringUtils.equals("APPROVED",companyInfo.getStatus())&&StringUtils.equals(companyCode,companyInfo.getCompanyCode())){
                    return companyInfo;
                }
            }else{
                if(StringUtils.equals("APPROVED",companyInfo.getStatus())&&StringUtils.equals(companyCode,companyInfo.getCompanyCode())
                        &&StringUtils.equals(companyName,companyInfo.getCompanyName())){
                    return companyInfo;
                }
            }
        }
        return null;
    }
}

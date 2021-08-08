package com.midea.cloud.srm.inq.price.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.constants.BaseConst;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.TitleColorSheetWriteHandler;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.inq.price.mapper.PriceLibraryMapper;
import com.midea.cloud.srm.inq.price.mapper.PriceLibraryPaymentTermMapper;
import com.midea.cloud.srm.inq.price.service.IPriceLadderPriceService;
import com.midea.cloud.srm.inq.price.service.IPriceLibraryPaymentTermService;
import com.midea.cloud.srm.inq.price.service.IPriceLibraryService;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.enums.CeeaMaterialStatus;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.cm.contract.dto.ContractDTO;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.domain.PriceLibraryAddParam;
import com.midea.cloud.srm.model.inq.price.dto.*;
import com.midea.cloud.srm.model.inq.price.entity.PriceLadderPrice;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibraryPaymentTerm;
import com.midea.cloud.srm.model.inq.price.vo.PriceLibraryVO;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.BidFrequency;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
*  <pre>
 *  报价-价格目录表 服务实现类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-25 11:41:48
 *  修改内容:
 * </pre>
*/
@Slf4j
@Service
public class PriceLibraryServiceImpl extends ServiceImpl<PriceLibraryMapper, PriceLibrary> implements IPriceLibraryService {

    private final IPriceLadderPriceService iPriceLadderPriceService;

    @Resource
    private BaseClient baseClient;

    @Resource
    private PriceLibraryMapper priceLibraryMapper;

    @Autowired
    private IPriceLibraryPaymentTermService priceLibraryPaymentTermService;

    @Autowired
    private PriceLibraryPaymentTermMapper priceLibraryPaymentTermMapper;

    @Autowired
    private ContractClient contractClient;

    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    public PriceLibraryServiceImpl(IPriceLadderPriceService iPriceLadderPriceService) {
        this.iPriceLadderPriceService = iPriceLadderPriceService;
    }

    @Autowired
    private FileCenterClient fileCenterClient;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<PriceLibrary> getQuotePrice(Long organizationId, Long vendorId, Long itemId) {

        Date now = new Date();
        QueryWrapper<PriceLibrary> wrapper = new QueryWrapper<>();
        wrapper.eq("ORGANIZATION_ID", organizationId)
                .eq("VENDOR_ID", vendorId)
                .eq("ITEM_ID", itemId)
                .le("EFFECTIVE_DATE", now).gt("EXPIRATION_DATE", now);
        return list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generatePriceLibrary(List<PriceLibraryAddParam> params) {
        //价格目录列表
        List<PriceLibrary> libraryEntities = new ArrayList<>();
        //阶梯价列表
        List<PriceLadderPrice> ladderEntities = new ArrayList<>();
        params.forEach(priceLibraryAddParam -> {
            PriceLibrary entity = new PriceLibrary();
            BeanUtils.copyProperties(priceLibraryAddParam, entity);
            entity.setPriceLibraryId(IdGenrator.generate());
            libraryEntities.add(entity);
            /*阶梯价*/
            if (YesOrNo.YES.getValue().equals(priceLibraryAddParam.getIsLadder())) {
                priceLibraryAddParam.getLadderPrices().forEach(priceLibraryLadderPrice -> {
                    PriceLadderPrice priceLadderPrice = new PriceLadderPrice();
                    BeanUtils.copyProperties(priceLibraryLadderPrice, priceLadderPrice);
                    priceLadderPrice.setPriceLadderPriceId(IdGenrator.generate());
                    priceLadderPrice.setPriceLibraryId(entity.getPriceLibraryId());
                    ladderEntities.add(priceLadderPrice);
                });
            }
        });

        saveBatch(libraryEntities);
        if (CollectionUtils.isNotEmpty(ladderEntities)) {
            iPriceLadderPriceService.saveBatch(ladderEntities);
        }
    }

    /**
     *
     * @param ceeaBuildPriceLibraryParam
     * 2020-11-6价格库插入添加两种规则
     * 原价格：2020-11-05 到 2020-11-30 待插入数据 2020-11-10 到 2020-11-30 （原价格包含待插入价格）
     * 原价格：2020-11-05 到 2020-11-30 待插入数据 2020-11-05 到 2020-11-10 （原价格包含待插入价格）
     *
     */
//    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ceeaGeneratePriceLibraryOld(List<PriceLibraryDTO> ceeaBuildPriceLibraryParam) {
        /**
         * 2020-11-17 将价格类型默认为 STANDARD
         */
        ceeaBuildPriceLibraryParam.forEach(item -> {
            item.setPriceType("STANDARD");
        });
        String approvalNo = ceeaBuildPriceLibraryParam.stream().map(PriceLibraryDTO::getApprovalNo).findAny().orElseThrow(() -> new BaseException("找不到审批单号"));
        /*判断插入的价格的格式是否正确*/
        /*checkIfFormatCorrect(ceeaBuildPriceLibraryParam);*/
        /*判断插入的价格是否交叉，如果交叉报错*/
        /*checkIfOverlapping(ceeaBuildPriceLibraryParam);*/
        Date date = new Date();
        List<PriceLibrary> adds = new ArrayList<>();

        Boolean isLock = redisUtil.tryLock("ceeaGeneratePriceLibrary" + approvalNo, 20, TimeUnit.MINUTES);
        if(!isLock){
            throw new BaseException("当前单据已被占用，请稍后再试^_^");
        }
        try{
            List<DictItem> priceUpdateCategary = baseClient.listDictItemByDictCode("PRICE_UPDATE_CATEGARY");
            Set<String> updateCategorySet = priceUpdateCategary.stream().map(DictItem::getDictItemCode).collect(Collectors.toSet());
            ceeaBuildPriceLibraryParam.forEach(item -> {
                /*判断 物料编号 + 物料描述 + 供应商编号 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间 是否相等*/
                String temp = item.getItemDesc();
                if(!updateCategorySet.contains(item.getCategoryCode())){
                    item.setItemDesc(null);
                }
                List<PriceLibrary> priceLibraryList = priceLibraryMapper.ceeaFindListByParams(item);
                item.setItemDesc(temp);
                if(CollectionUtils.isNotEmpty(priceLibraryList)){
                    PriceLibrary priceLibrary = priceLibraryList.get(0);
                    Long priceLibraryId = priceLibrary.getPriceLibraryId();
                    BeanUtils.copyProperties(item,priceLibrary);
                    priceLibrary.setLastUpdateDate(date)
                            .setLastUpdatedBy(priceLibrary.getLastUpdatedBy())
                            .setLastUpdatedId(priceLibrary.getLastUpdatedId())
                            .setPriceLibraryId(priceLibraryId);
                    priceLibraryMapper.updateById(priceLibrary);
                    /*更新价格库的付款条款（覆盖数据,不用操作付款条款）*/
                }else{
                    /*判断 物料编号 + 物料描述 + 供应商编号 + 业务实体id + 库存组织id + 到货地点 + 价格类型 是否相等 */
                    String temp1 = item.getItemDesc();
                    if(!updateCategorySet.contains(item.getCategoryCode())){
                        item.setItemDesc(null);
                    }
                    List<PriceLibrary> priceLibraryList2 = priceLibraryMapper.ceeaFindListByParams2(item);
                    item.setItemDesc(temp1);
                    /*原数据 (原行(多条) 与 待写入的行 的重叠只有一条)  判断插入的价格是否与原数据有多条交叉*/
                    checkIfOverlapping(priceLibraryList2,item);
                    if(CollectionUtils.isNotEmpty(priceLibraryList2)){
                        boolean ifCoincidence = false;
                        for(PriceLibrary oldItem:priceLibraryList2){
                            /*前交叉：原数据6.1-6.30 待写入数据6-15-7-30  (右交叉)*/
                            if(isBefore(DateUtil.dateToLocalDate(oldItem.getEffectiveDate()),DateUtil.dateToLocalDate(item.getEffectiveDate())) &&
                                    isBeforeAndEquals(DateUtil.dateToLocalDate(item.getEffectiveDate()),DateUtil.dateToLocalDate(oldItem.getExpirationDate())) &&
                                    isBefore(DateUtil.dateToLocalDate(oldItem.getExpirationDate()),DateUtil.dateToLocalDate(item.getExpirationDate()))
                            ){
                                priceLibraryMapper.insert(item.setPriceLibraryId(IdGenrator.generate()));
                                priceLibraryMapper.updateById(new PriceLibrary()
                                        .setPriceLibraryId(oldItem.getPriceLibraryId())
                                        .setExpirationDate(sub(item.getEffectiveDate()))
                                );
                                /*更新价格库付款条款 - */
                                item.getPriceLibraryPaymentTermList().forEach(paymentTermItem -> {
                                    paymentTermItem.setPriceLibraryId(item.getPriceLibraryId())
                                            .setPriceLibraryPaymentTermId(IdGenrator.generate());
                                });
                                priceLibraryPaymentTermService.saveBatch(item.getPriceLibraryPaymentTermList());
                                ifCoincidence = true;
                                break;
                            }
                            /*中间交叉：原数据6.1-6.30 待写入数据6月15-6月20  (包含) */
                            if(isBeforeAndEquals(DateUtil.dateToLocalDate(oldItem.getEffectiveDate()),DateUtil.dateToLocalDate(item.getEffectiveDate())) &&
                                    isBeforeAndEquals(DateUtil.dateToLocalDate(item.getExpirationDate()),DateUtil.dateToLocalDate(oldItem.getExpirationDate()))
                            ){
                                /**
                                 * 添加规则：
                                 * 原价格：2020-11-05 到 2020-11-30 待插入数据 2020-11-10 到 2020-11-30 （原价格包含待插入价格）
                                 */
                                if(DateUtil.dateToLocalDate(oldItem.getEffectiveDate()).isBefore(DateUtil.dateToLocalDate(item.getEffectiveDate())) &&
                                        DateUtil.dateToLocalDate(oldItem.getExpirationDate()).isEqual(DateUtil.dateToLocalDate(item.getExpirationDate()))
                                ){
                                    /*更新旧价格库数据,添加新价格库数据*/
                                    Long id = IdGenrator.generate();
                                    PriceLibrary p1 = new PriceLibrary();
                                    BeanUtils.copyProperties(oldItem,p1);
                                    p1.setPriceLibraryId(oldItem.getPriceLibraryId())
                                            .setExpirationDate(sub(item.getEffectiveDate()));
                                    priceLibraryMapper.insert(item.setPriceLibraryId(id));
                                    priceLibraryMapper.updateById(p1);
                                    /*添加新价格库付款条款*/
                                    QueryWrapper<PriceLibraryPaymentTerm> priceLibraryPaymentTermQueryWrapper = new QueryWrapper<>();
                                    priceLibraryPaymentTermQueryWrapper.eq("PRICE_LIBRARY_ID",oldItem.getPriceLibraryId());
                                    List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = item.getPriceLibraryPaymentTermList();
                                    priceLibraryPaymentTermList.forEach(paymentTerm -> {
                                        paymentTerm.setPriceLibraryPaymentTermId(IdGenrator.generate())
                                                .setPriceLibraryId(id);
                                    });
                                    priceLibraryPaymentTermService.saveBatch(priceLibraryPaymentTermList);
                                    ifCoincidence = true;
                                    break;
                                }

                                /**
                                 * 添加规则：
                                 * 原价格：2020-11-05 到 2020-11-30 待插入数据 2020-11-05 到 2020-11-10 （原价格包含待插入价格）
                                 */
                                if(DateUtil.dateToLocalDate(oldItem.getEffectiveDate()).equals(DateUtil.dateToLocalDate(item.getEffectiveDate())) &&
                                        DateUtil.dateToLocalDate(item.getExpirationDate()).isBefore(DateUtil.dateToLocalDate(oldItem.getExpirationDate()))
                                ){
                                    /*更新旧价格库数据，添加新价格库数据*/
                                    Long id = IdGenrator.generate();
                                    PriceLibrary p1 = new PriceLibrary();
                                    BeanUtils.copyProperties(oldItem,p1);
                                    p1.setPriceLibraryId(oldItem.getPriceLibraryId())
                                            .setEffectiveDate(add(item.getExpirationDate()));
                                    priceLibraryMapper.insert(item.setPriceLibraryId(id));
                                    priceLibraryMapper.updateById(p1);

                                    /*添加新价格库付款条款*/
                                    QueryWrapper<PriceLibraryPaymentTerm> priceLibraryPaymentTermQueryWrapper = new QueryWrapper<>();
                                    priceLibraryPaymentTermQueryWrapper.eq("PRICE_LIBRARY_ID",oldItem.getPriceLibraryId());
                                    List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = item.getPriceLibraryPaymentTermList();
                                    priceLibraryPaymentTermList.forEach(paymentTerm -> {
                                        paymentTerm.setPriceLibraryPaymentTermId(IdGenrator.generate())
                                                .setPriceLibraryId(id);
                                    });
                                    priceLibraryPaymentTermService.saveBatch(priceLibraryPaymentTermList);
                                    ifCoincidence = true;
                                    break;
                                }


                                /*这种情况不允许插入*/
                                if(DateUtil.dateToLocalDate(oldItem.getEffectiveDate()).isEqual(DateUtil.dateToLocalDate(item.getEffectiveDate())) ||
                                        DateUtil.dateToLocalDate(oldItem.getExpirationDate()).isEqual(DateUtil.dateToLocalDate(item.getExpirationDate()))
                                ){
                                    log.info("原价格有效期与插入价格有效期包含：原价格：:["
                                            + new SimpleDateFormat("yyyyMMdd").format(oldItem.getEffectiveDate())
                                            + "-" + new SimpleDateFormat("yyyyMMdd").format(oldItem.getExpirationDate())
                                            + "] 插入价格： [" + new SimpleDateFormat("yyyyMMdd").format(item.getEffectiveDate()) + "-" + new SimpleDateFormat("yyyyMMdd").format(item.getExpirationDate()) + "]");
                                    throw new BaseException(LocaleHandler.getLocaleMsg("原价格有效期包含插入价格有效期：原价格：["
                                            + new SimpleDateFormat("yyyyMMdd").format(oldItem.getEffectiveDate())
                                            + "-" + new SimpleDateFormat("yyyyMMdd").format(oldItem.getExpirationDate())
                                            + "] 插入价格：[" + new SimpleDateFormat("yyyyMMdd").format(item.getEffectiveDate()) + "-" + new SimpleDateFormat("yyyyMMdd").format(item.getExpirationDate()) + "]"));
                                }


                                priceLibraryMapper.insert(item.setPriceLibraryId(IdGenrator.generate()));
                                PriceLibrary p1 = new PriceLibrary();
                                PriceLibrary p2 = new PriceLibrary();
                                BeanUtils.copyProperties(oldItem,p1);
                                BeanUtils.copyProperties(oldItem,p2);
                                p1.setPriceLibraryId(IdGenrator.generate())
                                        .setExpirationDate(sub(item.getEffectiveDate()));
                                p2.setPriceLibraryId(IdGenrator.generate())
                                        .setEffectiveDate(add(item.getExpirationDate()));
                                priceLibraryMapper.deleteById(oldItem);
                                priceLibraryMapper.insert(p1);
                                priceLibraryMapper.insert(p2);

                                /*更新价格库付款条款 - item*/
                                item.getPriceLibraryPaymentTermList().forEach(paymentTermItem -> {
                                    paymentTermItem.setPriceLibraryId(item.getPriceLibraryId())
                                            .setPriceLibraryPaymentTermId(IdGenrator.generate());
                                });
                                priceLibraryPaymentTermService.saveBatch(item.getPriceLibraryPaymentTermList());
                                /*更新价格库付款条款 p1 p2*/
                                QueryWrapper<PriceLibraryPaymentTerm> wrapper = new QueryWrapper();
                                wrapper.eq("PRICE_LIBRARY_ID",oldItem.getPriceLibraryId());
                                List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = priceLibraryPaymentTermService.list(wrapper);
                                List<PriceLibraryPaymentTerm> priceLibraryPaymentTermP1s = new ArrayList<>();
                                List<PriceLibraryPaymentTerm> priceLibraryPaymentTermP2s = new ArrayList<>();
                                priceLibraryPaymentTermList.forEach(paymentTermItem -> {
                                    PriceLibraryPaymentTerm pt1 = new PriceLibraryPaymentTerm();
                                    PriceLibraryPaymentTerm pt2 = new PriceLibraryPaymentTerm();
                                    BeanUtils.copyProperties(paymentTermItem,pt1);
                                    BeanUtils.copyProperties(paymentTermItem,pt2);
                                    pt1.setPriceLibraryId(p1.getPriceLibraryId())
                                            .setPriceLibraryPaymentTermId(IdGenrator.generate());
                                    pt2.setPriceLibraryId(p2.getPriceLibraryId())
                                            .setPriceLibraryPaymentTermId(IdGenrator.generate());
                                    priceLibraryPaymentTermP1s.add(pt1);
                                    priceLibraryPaymentTermP2s.add(pt2);
                                });
                                priceLibraryPaymentTermService.saveBatch(priceLibraryPaymentTermP1s);
                                priceLibraryPaymentTermService.saveBatch(priceLibraryPaymentTermP2s);

                                /*删除旧的付款条款*/
                                priceLibraryPaymentTermService.remove(wrapper);
                                ifCoincidence = true;
                                break;
                            }
                            /* 原数据 6.3-6.4 ,待插入数据 6.2-6.5 (被包含) (旧价格 被 新价格 包含)*/
                            if(isBeforeAndEquals(DateUtil.dateToLocalDate(item.getEffectiveDate()),DateUtil.dateToLocalDate(oldItem.getEffectiveDate())) &&
                                    isBeforeAndEquals(DateUtil.dateToLocalDate(oldItem.getExpirationDate()),DateUtil.dateToLocalDate(item.getExpirationDate()))
                            ){

                            /*if(DateUtil.dateToLocalDate(oldItem.getEffectiveDate()).isEqual(DateUtil.dateToLocalDate(item.getEffectiveDate())) &&
                                    DateUtil.dateToLocalDate(oldItem.getExpirationDate()).isBefore(DateUtil.dateToLocalDate(item.getExpirationDate()))
                            ){
                                *//*删除旧的数据（价格库数据 付款条款）*//*
                                priceLibraryMapper.deleteById(oldItem.getPriceLibraryId());
                                QueryWrapper<PriceLibraryPaymentTerm> wrapper = new QueryWrapper();
                                wrapper.eq("PRICE_LIBRARY_ID",oldItem.getPriceLibraryId());
                                priceLibraryPaymentTermService.remove(wrapper);
                                *//*插入新数据（价格库数据 付款条款）*//*
                                priceLibraryMapper.insert(item.setPriceLibraryId(IdGenrator.generate()));
                                priceLibraryPaymentTermService.saveBatch(item.getPriceLibraryPaymentTermList());
                                ifCoincidence = true;
                                break;
                            }*/

                                log.info("原价格有效期被插入价格有效期包含：原价格:["
                                        + new SimpleDateFormat("yyyyMMdd").format(oldItem.getEffectiveDate())
                                        + "-" + new SimpleDateFormat("yyyyMMdd").format(oldItem.getExpirationDate())
                                        + "] 插入价格：[" + new SimpleDateFormat("yyyyMMdd").format(item.getEffectiveDate()) + "-" + new SimpleDateFormat("yyyyMMdd").format(item.getExpirationDate()) + "]");
                                throw new BaseException(LocaleHandler.getLocaleMsg("原价格有效期被插入价格有效期包含：原价格:["
                                        + new SimpleDateFormat("yyyyMMdd").format(oldItem.getEffectiveDate())
                                        + "-" + new SimpleDateFormat("yyyyMMdd").format(oldItem.getExpirationDate())
                                        + "] 插入价格：[" + new SimpleDateFormat("yyyyMMdd").format(item.getEffectiveDate()) + "-" + new SimpleDateFormat("yyyyMMdd").format(item.getExpirationDate()) + "]"));
                            }

                            /*后交叉：原数据6.15-6.30  待写入数据 6.1-6.16 (左交叉)*/
                            if(isBefore(DateUtil.dateToLocalDate(item.getEffectiveDate()),DateUtil.dateToLocalDate(oldItem.getEffectiveDate())) &&
                                    isBeforeAndEquals(DateUtil.dateToLocalDate(oldItem.getEffectiveDate()),DateUtil.dateToLocalDate(item.getExpirationDate())) &&
                                    isBefore(DateUtil.dateToLocalDate(item.getExpirationDate()),DateUtil.dateToLocalDate(oldItem.getExpirationDate()))
                            ){
                                log.info("原价格有效期与插入价格有效期左交叉：原价格:["
                                        + new SimpleDateFormat("yyyyMMdd").format(oldItem.getEffectiveDate())
                                        + "-" + new SimpleDateFormat("yyyyMMdd").format(oldItem.getExpirationDate())
                                        + "] 插入价格：[" + new SimpleDateFormat("yyyyMMdd").format(item.getEffectiveDate()) + "-" + new SimpleDateFormat("yyyyMMdd").format(item.getExpirationDate()) + "]");
                                throw new BaseException(LocaleHandler.getLocaleMsg("原价格有效期被插入价格有效期包含：原价格:["
                                        + new SimpleDateFormat("yyyyMMdd").format(oldItem.getEffectiveDate())
                                        + "-" + new SimpleDateFormat("yyyyMMdd").format(oldItem.getExpirationDate())
                                        + "] 插入价格：[" + new SimpleDateFormat("yyyyMMdd").format(item.getEffectiveDate()) + "-" + new SimpleDateFormat("yyyyMMdd").format(item.getExpirationDate()) + "]"));
                            }
                        }
                        /*无交叉情况*/
                        if(!ifCoincidence){
                            log.info("非左交叉，非右交叉，非包含，非被包含: item = " + JsonUtil.entityToJsonStr(item));
                            /*添加价格库*/
                            priceLibraryMapper.insert(item.setPriceLibraryId(IdGenrator.generate()));
                            /*添加付款条款*/
                            List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = item.getPriceLibraryPaymentTermList();
                            for(PriceLibraryPaymentTerm priceLibraryPaymentTerm:priceLibraryPaymentTermList){
                                priceLibraryPaymentTerm.setPriceLibraryId(item.getPriceLibraryId());
                            }
                            if(CollectionUtils.isNotEmpty(priceLibraryPaymentTermList)){
                                priceLibraryPaymentTermService.saveBatch(priceLibraryPaymentTermList);
                            }
                        }
                    }else{
                        log.info("物料编号 + 物料描述 + 供应商编号 + 业务实体id + 库存组织id + 到货地点 + 价格类型 某个其中某个条件与其他不等，直接插入");
                        priceLibraryMapper.insert(item.setPriceLibraryId(IdGenrator.generate()));
                        /*添加付款条款*/
                        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = item.getPriceLibraryPaymentTermList();
                        for(PriceLibraryPaymentTerm priceLibraryPaymentTerm:priceLibraryPaymentTermList){
                            priceLibraryPaymentTerm.setPriceLibraryId(item.getPriceLibraryId());
                        }
                        if(CollectionUtils.isNotEmpty(priceLibraryPaymentTermList)){
                            priceLibraryPaymentTermService.saveBatch(priceLibraryPaymentTermList);
                        }

                    }
                }

            });
        }finally {
            redisUtil.unLock("ceeaGeneratePriceLibrary" + approvalNo);
        }

    }

    public static enum CROSSTYPE {
        LEFTCROSS(0),   //左交叉
        RIGHTCROSS(1),  //右交叉
        CONTAINS(2),   //包含
        INCLUDED(3),   //被包含
        NOCROSS(4);  //不交叉

        private final Integer value;

        CROSSTYPE(Integer value) {
            this.value = value;
        }
    }

    /**
     *
     * @param ceeaBuildPriceLibraryParam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ceeaGeneratePriceLibrary(List<PriceLibraryDTO> ceeaBuildPriceLibraryParam){
        //获取外围系统数据 PurchaseCurrency CategoryCode
        List<String> currencyCodeList = ceeaBuildPriceLibraryParam
                .stream()
                .filter(item -> StringUtils.isNotBlank(item.getCurrencyCode()))
                .map(item -> item.getCurrencyCode())
                .collect(Collectors.toList());
        List<Long> categoryIdList = ceeaBuildPriceLibraryParam
                .stream()
                .filter(item -> Objects.nonNull(item.getCategoryId()))
                .map(item -> item.getCategoryId())
                .collect(Collectors.toList());
        List<PurchaseCurrency> purchaseCurrencyList = baseClient.listPurchaseCurrencyAnon(currencyCodeList);
        List<PurchaseCategory> purchaseCategoryList = baseClient.listPurchaseCategoryAnon(categoryIdList);

        Map<String,PurchaseCurrency> purchaseCurrencyMap = purchaseCurrencyList.stream().collect(Collectors.toMap(item -> item.getCurrencyCode(),item -> item));
        Map<Long,PurchaseCategory> purchaseCategoryMap = purchaseCategoryList.stream().collect(Collectors.toMap(item -> item.getCategoryId(),item -> item));

        ceeaBuildPriceLibraryParam.forEach(item -> {
            PurchaseCurrency purchaseCurrency = purchaseCurrencyMap.get(item.getCurrencyCode());
            if(Objects.nonNull(purchaseCurrency) && Objects.nonNull(purchaseCurrency.getCurrencyId())){
                item.setCurrencyId(purchaseCurrency.getCurrencyId());
            }
            if(Objects.nonNull(purchaseCurrency) && StringUtils.isNotBlank(purchaseCurrency.getCurrencyName())){
                item.setCurrencyName(purchaseCurrency.getCurrencyName());
            }
            PurchaseCategory purchaseCategory = purchaseCategoryMap.get(item.getCategoryId());
            if(Objects.nonNull(purchaseCategory) && StringUtils.isNotBlank(purchaseCategory.getCategoryCode())){
                item.setCategoryCode(purchaseCategory.getCategoryCode());
            }
        });


        //将价格类型默认为 STANDARD
        ceeaBuildPriceLibraryParam.forEach(item -> {
            item.setPriceType("STANDARD");
        });

        //获取价格审批单号作为锁的key值
        String approvalNo = ceeaBuildPriceLibraryParam.stream().map(PriceLibraryDTO::getApprovalNo).findAny().orElseThrow(() -> new BaseException("找不到审批单号"));
        Boolean isLock = redisUtil.tryLock("ceeaGeneratePriceLibrary" + approvalNo, 20, TimeUnit.MINUTES);
        if(!isLock){
            throw new BaseException("当前单据已被占用，请稍后再试^_^");
        }
        try {
            //根据物料小类是否为【更新小类】分组
            List<DictItem> priceUpdateCategory = baseClient.listDictItemByDictCode("PRICE_UPDATE_CATEGARY");
            Set<String> updateCategorySet = priceUpdateCategory.stream().map(DictItem::getDictItemCode).collect(Collectors.toSet());
            List<PriceLibraryDTO> updateCategoryList = new LinkedList<>();
            List<PriceLibraryDTO> notUpdateCategoryList = new LinkedList<>();

            //如果为更新小类，则按物料描述查询，如果不为更新小类，则不按物料描述查询
            for(int i=0;i<ceeaBuildPriceLibraryParam.size();i++){
                PriceLibraryDTO p = ceeaBuildPriceLibraryParam.get(i);
                if(updateCategorySet.contains(p.getCategoryCode())){
                    updateCategoryList.add(p);
                }else{
                    notUpdateCategoryList.add(p);
                }
            }

            //处理更新小类的价格库 按照 物料编码 + 物料描述 + 供应商编码 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间是否相等
            for(int i=0;i<updateCategoryList.size();i++){
                PriceLibraryDTO item = updateCategoryList.get(i);
                List<PriceLibrary> priceLibraryList = priceLibraryMapper.ceeaFindListByParams2(
                        new PriceLibrary().setItemCode(item.getItemCode())
                            .setItemDesc(item.getItemDesc())
                            .setVendorCode(item.getVendorCode())
                            .setCeeaOrgId(item.getCeeaOrgId())
                            .setCeeaOrganizationId(item.getCeeaOrganizationId())
                            .setCeeaArrivalPlace(item.getCeeaArrivalPlace())
                            .setPriceType(item.getPriceType())
                );
                //直接插入价格库和付款条款
                if(CollectionUtils.isEmpty(priceLibraryList)){
                    savePriceLibraryAndPaymentItems(item);
                    continue;
                }

                //将价格库转为Map,以物料编码 + 物料描述 + 供应商编码 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间 为key值
                Map<String,PriceLibrary> map1 = getUpdateCategoryPriceLibraryMap1(priceLibraryList);
                String startTime = String.valueOf(DateUtil.dateToLocalDate(item.getEffectiveDate()));
                String endTime = String.valueOf(DateUtil.dateToLocalDate(item.getExpirationDate()));
                String key = new StringBuffer().append(item.getItemCode())
                                .append(item.getItemDesc())
                                .append(item.getVendorCode())
                                .append(item.getCeeaOrgId())
                                .append(item.getCeeaOrganizationId())
                                .append(item.getCeeaArrivalPlace())
                                .append(item.getPriceType())
                                .append(startTime)
                                .append(endTime).toString();

                //全等
                if(Objects.nonNull(map1.get(key))){
                    //覆盖旧的数据 ,使用新的付款条款
                    PriceLibrary priceLibrary = map1.get(key);
                    item.setPriceLibraryId(priceLibrary.getPriceLibraryId());
                    updatePriceLibraryAndPaymentItems(item);
                    continue;
                }

                //判断是哪种交叉方式
                List<PriceLibraryDTO> addPriceLibrary = new LinkedList<>();
                List<PriceLibraryDTO> updatePriceLibrary = new LinkedList<>();
                List<PriceLibraryDTO> removePriceLibrary = new LinkedList<>();

                for(PriceLibrary p : priceLibraryList){
                    Integer crossType = whichCrossType(item,p);
                    switch (crossType){
                        case 0 :
                            Map<String,List<PriceLibraryDTO>> map = leftCross(item,p);
                            addPriceLibrary.addAll(map.get("ADD"));
                            updatePriceLibrary.addAll(map.get("UPDATE"));
                            removePriceLibrary.addAll(map.get("REMOVE"));
                            break;
                        case 1 :
                            Map<String,List<PriceLibraryDTO>> map2 = rightCross(item,p);
                            addPriceLibrary.addAll(map2.get("ADD"));
                            updatePriceLibrary.addAll(map2.get("UPDATE"));
                            removePriceLibrary.addAll(map2.get("REMOVE"));
                            break;
                        case 2 :
                            Map<String,List<PriceLibraryDTO>> map3 = contains(item,p);
                            addPriceLibrary.addAll(map3.get("ADD"));
                            updatePriceLibrary.addAll(map3.get("UPDATE"));
                            removePriceLibrary.addAll(map3.get("REMOVE"));
                            break;
                        case 3 :
                            Map<String,List<PriceLibraryDTO>> map4 = included(item,p);
                            addPriceLibrary.addAll(map4.get("ADD"));
                            updatePriceLibrary.addAll(map4.get("UPDATE"));
                            removePriceLibrary.addAll(map4.get("REMOVE"));
                            break;
                        case 4 :
                            Map<String,List<PriceLibraryDTO>> map5 = noCross(item,p);
                            addPriceLibrary.addAll(map5.get("ADD"));
                            updatePriceLibrary.addAll(map5.get("UPDATE"));
                            removePriceLibrary.addAll(map5.get("REMOVE"));
                            break;
                    }

                }

                //通过map去重
                Map<String,PriceLibraryDTO> addMap = getUpdateCategoryPriceLibraryMap2(addPriceLibrary);
                Map<String,PriceLibraryDTO> updateMap = getUpdateCategoryPriceLibraryMap2(updatePriceLibrary);
                Map<String,PriceLibraryDTO> removeMap = getUpdateCategoryPriceLibraryMap2(removePriceLibrary);

                if(!addMap.isEmpty()){
                    List<PriceLibraryDTO> priceLibraryDTOList = new LinkedList<PriceLibraryDTO>(addMap.values());
                    saveBatchPriceLibraryAndPaymentItems(priceLibraryDTOList);
                }
                if(!updateMap.isEmpty()){
                    List<PriceLibraryDTO> priceLibraryDTOList = new LinkedList<PriceLibraryDTO>(updateMap.values());
                    updateBatchPriceLibraryAndPaymentItems(priceLibraryDTOList);
                }
                if(!removeMap.isEmpty()){
                    List<PriceLibraryDTO> priceLibraryDTOList = new LinkedList<PriceLibraryDTO>(removeMap.values());
                    removeBatchPriceLibraryAndPaymentItems(priceLibraryDTOList);
                }


                //输出日志
                log.info("价格审批单号：" + approvalNo);
                log.info("中标行(更新小类)：" + JsonUtil.entityToJsonStr(item));
                log.info("需要删除的：" + JsonUtil.arrayToJsonStr(new ArrayList<PriceLibraryDTO>(removeMap.values())));
                log.info("需要更新的：" + JsonUtil.arrayToJsonStr(new ArrayList<PriceLibraryDTO>(updateMap.values())));
                log.info("需要插入的：" + JsonUtil.arrayToJsonStr(new ArrayList<PriceLibraryDTO>(addMap.values())));


            }

            //处理非更新小类的价格库 按照 物料编码 + 供应商编码 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间
            for(int i=0;i<notUpdateCategoryList.size();i++){
                PriceLibraryDTO item = notUpdateCategoryList.get(i);
                List<PriceLibrary> priceLibraryList = priceLibraryMapper.ceeaFindListByParams2(
                        new PriceLibrary().setItemCode(item.getItemCode())
                            .setVendorCode(item.getVendorCode())
                            .setCeeaOrgId(item.getCeeaOrgId())
                            .setCeeaOrganizationId(item.getCeeaOrganizationId())
                            .setCeeaArrivalPlace(item.getCeeaArrivalPlace())
                            .setPriceType(item.getPriceType())
                );
                //直接插入价格库和付款条款
                if(CollectionUtils.isEmpty(priceLibraryList)){
                    savePriceLibraryAndPaymentItems(item);
                    continue;
                }
                //将价格库转为Map,以物料编码 + 供应商编码 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间 为key值
                Map<String,PriceLibrary> map1 = getNotUpdateCategoryPriceLibraryMap1(priceLibraryList);
                String startTime = String.valueOf(DateUtil.dateToLocalDate(item.getEffectiveDate()));
                String endTime = String.valueOf(DateUtil.dateToLocalDate(item.getExpirationDate()));
                String key = new StringBuffer().append(item.getItemCode())
                        .append(item.getVendorCode())
                        .append(item.getCeeaOrgId())
                        .append(item.getCeeaOrganizationId())
                        .append(item.getCeeaArrivalPlace())
                        .append(item.getPriceType())
                        .append(startTime)
                        .append(endTime).toString();

                //全等
                if(Objects.nonNull(map1.get(key))){
                    //覆盖旧的数据 ,使用新的付款条款
                    PriceLibrary priceLibrary = map1.get(key);
                    item.setPriceLibraryId(priceLibrary.getPriceLibraryId());
                    updatePriceLibraryAndPaymentItems(item);
                    continue;
                }

                List<PriceLibraryDTO> addPriceLibrary = new LinkedList<>();
                List<PriceLibraryDTO> updatePriceLibrary = new LinkedList<>();
                List<PriceLibraryDTO> removePriceLibrary = new LinkedList<>();

                //判断是哪种交叉方式
                for(PriceLibrary p : priceLibraryList){
                    Integer crossType = whichCrossType(item,p);
                    switch (crossType){
                        case 0 :
                            Map<String,List<PriceLibraryDTO>> map = leftCross(item,p);
                            addPriceLibrary.addAll(map.get("ADD"));
                            updatePriceLibrary.addAll(map.get("UPDATE"));
                            removePriceLibrary.addAll(map.get("REMOVE"));
                            break;
                        case 1 :
                            Map<String,List<PriceLibraryDTO>> map2 = rightCross(item,p);
                            addPriceLibrary.addAll(map2.get("ADD"));
                            updatePriceLibrary.addAll(map2.get("UPDATE"));
                            removePriceLibrary.addAll(map2.get("REMOVE"));
                            break;
                        case 2 :
                            Map<String,List<PriceLibraryDTO>> map3 = contains(item,p);
                            addPriceLibrary.addAll(map3.get("ADD"));
                            updatePriceLibrary.addAll(map3.get("UPDATE"));
                            removePriceLibrary.addAll(map3.get("REMOVE"));
                            break;
                        case 3 :
                            Map<String,List<PriceLibraryDTO>> map4 = included(item,p);
                            addPriceLibrary.addAll(map4.get("ADD"));
                            updatePriceLibrary.addAll(map4.get("UPDATE"));
                            removePriceLibrary.addAll(map4.get("REMOVE"));
                            break;
                        case 4 :
                            Map<String,List<PriceLibraryDTO>> map5 = noCross(item,p);
                            addPriceLibrary.addAll(map5.get("ADD"));
                            updatePriceLibrary.addAll(map5.get("UPDATE"));
                            removePriceLibrary.addAll(map5.get("REMOVE"));
                            break;
                    }
                }
                //通过map去重
                Map<String,PriceLibraryDTO> addMap = getNotUpdateCategoryPriceLibraryMap2(addPriceLibrary);
                Map<String,PriceLibraryDTO> updateMap = getNotUpdateCategoryPriceLibraryMap2(updatePriceLibrary);
                Map<String,PriceLibraryDTO> removeMap = getNotUpdateCategoryPriceLibraryMap2(removePriceLibrary);

                if(!addMap.isEmpty()){
                    List<PriceLibraryDTO> priceLibraryDTOList = new LinkedList<PriceLibraryDTO>(addMap.values());
                    saveBatchPriceLibraryAndPaymentItems(priceLibraryDTOList);
                }
                if(!updateMap.isEmpty()){
                    List<PriceLibraryDTO> priceLibraryDTOList = new LinkedList<PriceLibraryDTO>(updateMap.values());
                    updateBatchPriceLibraryAndPaymentItems(priceLibraryDTOList);
                }
                if(!removeMap.isEmpty()){
                    List<PriceLibraryDTO> priceLibraryDTOList = new LinkedList<PriceLibraryDTO>(removeMap.values());
                    removeBatchPriceLibraryAndPaymentItems(priceLibraryDTOList);
                }
                //输出日志
                log.info("价格审批单号：" + approvalNo);
                log.info("中标行(非更新小类)：" + JsonUtil.entityToJsonStr(item));
                log.info("需要删除的：" + JsonUtil.arrayToJsonStr(new ArrayList<PriceLibraryDTO>(removeMap.values())));
                log.info("需要更新的：" + JsonUtil.arrayToJsonStr(new ArrayList<PriceLibraryDTO>(updateMap.values())));
                log.info("需要插入的：" + JsonUtil.arrayToJsonStr(new ArrayList<PriceLibraryDTO>(addMap.values())));
            }

        }finally {
            redisUtil.unLock("ceeaGeneratePriceLibrary" + approvalNo);
        }

    }


    /**
     * 左交叉，拆分价格
     * @param item
     * @param p
     */
    private Map<String,List<PriceLibraryDTO>> leftCross(PriceLibraryDTO item,PriceLibrary p){
        List<PriceLibraryDTO> addList = new LinkedList<PriceLibraryDTO>(){{
            add(item);
        }};

        List<PriceLibraryDTO> updateList = new LinkedList<PriceLibraryDTO>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("PRICE_LIBRARY_ID",p.getPriceLibraryId());
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = priceLibraryPaymentTermService.list(wrapper);
        p.setExpirationDate(sub(item.getEffectiveDate()));
        PriceLibraryDTO priceLibraryDTO = new PriceLibraryDTO();
        BeanUtils.copyProperties(p,priceLibraryDTO);
        priceLibraryDTO.setPriceLibraryPaymentTermList(priceLibraryPaymentTermList);
        updateList.add(priceLibraryDTO);

        Map<String,List<PriceLibraryDTO>> result = new HashMap<>();
        result.put("ADD",addList);
        result.put("UPDATE",updateList);
        result.put("REMOVE",Collections.EMPTY_LIST);
        return result;
    }

    /**
     * 右交叉。拆分价格
     * @param item
     * @param p
     */
    private Map<String,List<PriceLibraryDTO>> rightCross(PriceLibraryDTO item,PriceLibrary p){
        List<PriceLibraryDTO> addList = new LinkedList<PriceLibraryDTO>(){{
            add(item);
        }};

        List<PriceLibraryDTO> updateList = new LinkedList<PriceLibraryDTO>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("PRICE_LIBRARY_ID",p.getPriceLibraryId());
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = priceLibraryPaymentTermService.list(wrapper);
        p.setEffectiveDate(add(item.getExpirationDate()));
        PriceLibraryDTO priceLibraryDTO = new PriceLibraryDTO();
        BeanUtils.copyProperties(p,priceLibraryDTO);
        priceLibraryDTO.setPriceLibraryPaymentTermList(priceLibraryPaymentTermList);
        updateList.add(priceLibraryDTO);

        Map<String,List<PriceLibraryDTO>> result = new HashMap<>();
        result.put("ADD",addList);
        result.put("UPDATE",updateList);
        result.put("REMOVE",Collections.EMPTY_LIST);
        return result;

    }

    /**
     * 包含
     * @param item
     * @param p
     */
    private Map<String,List<PriceLibraryDTO>> contains(PriceLibraryDTO item,PriceLibrary p){
        List<PriceLibraryDTO> addList = new LinkedList<PriceLibraryDTO>(){{
            add(item);
        }};

        List<PriceLibraryDTO> removeList = new LinkedList<PriceLibraryDTO>();
        PriceLibraryDTO pRemove = new PriceLibraryDTO();
        BeanUtils.copyProperties(p,pRemove);
        removeList.add(pRemove);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("PRICE_LIBRARY_ID",p.getPriceLibraryId());
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = priceLibraryPaymentTermService.list(wrapper);
        PriceLibraryDTO p1 = new PriceLibraryDTO();
        BeanUtils.copyProperties(p,p1);
        p1.setExpirationDate(sub(item.getEffectiveDate()));
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList1 = deepCopy(priceLibraryPaymentTermList);
        p1.setPriceLibraryPaymentTermList(priceLibraryPaymentTermList1);

        PriceLibraryDTO p2 = new PriceLibraryDTO();
        BeanUtils.copyProperties(p,p2);
        p2.setEffectiveDate(add(item.getExpirationDate()));
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList2 = deepCopy(priceLibraryPaymentTermList);
        p2.setPriceLibraryPaymentTermList(priceLibraryPaymentTermList2);

        LocalDate p1EffectiveDate = DateUtil.dateToLocalDate(p1.getEffectiveDate());
        LocalDate p1ExpirationDate = DateUtil.dateToLocalDate(p1.getExpirationDate());
        if(isBeforeAndEquals(p1EffectiveDate,p1ExpirationDate)){
            addList.add(p1);
        }

        LocalDate p2EffectiveDate = DateUtil.dateToLocalDate(p2.getEffectiveDate());
        LocalDate p2ExpirationDate = DateUtil.dateToLocalDate(p2.getExpirationDate());
        if(isBeforeAndEquals(p2EffectiveDate,p2ExpirationDate)){
            addList.add(p2);
        }

        Map<String,List<PriceLibraryDTO>> result = new HashMap<>();
        result.put("ADD",addList);
        result.put("UPDATE",Collections.EMPTY_LIST);
        result.put("REMOVE",removeList);
        return result;

    }

    private List<PriceLibraryPaymentTerm> deepCopy(List<PriceLibraryPaymentTerm> paymentTermList){
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = new LinkedList<>();
        for(int i=0;i<paymentTermList.size();i++){
            PriceLibraryPaymentTerm p = new PriceLibraryPaymentTerm();
            BeanUtils.copyProperties(paymentTermList.get(i),p);
            priceLibraryPaymentTermList.add(p);
        }
        return priceLibraryPaymentTermList;
    }

    /**
     * 被包含
     * @param item
     * @param p
     */
    private Map<String,List<PriceLibraryDTO>> included(PriceLibraryDTO item,PriceLibrary p){
        List<PriceLibraryDTO> addList = new LinkedList<PriceLibraryDTO>(){{
            add(item);
        }};

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("PRICE_LIBRARY_ID",p.getPriceLibraryId());
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = priceLibraryPaymentTermService.list(wrapper);

        PriceLibraryDTO rmPriceLibraryDTO = new PriceLibraryDTO();
        BeanUtils.copyProperties(p,rmPriceLibraryDTO);
        rmPriceLibraryDTO.setPriceLibraryPaymentTermList(priceLibraryPaymentTermList);
        List<PriceLibraryDTO> removeList = new LinkedList<>();
        removeList.add(rmPriceLibraryDTO);

        Map<String,List<PriceLibraryDTO>> result = new HashMap<>();
        result.put("ADD",addList);
        result.put("REMOVE",removeList);
        result.put("UPDATE",Collections.EMPTY_LIST);
        return result;
    }

    /**
     * 不交叉
     * @param item
     * @param p
     */
    private Map<String,List<PriceLibraryDTO>> noCross(PriceLibraryDTO item,PriceLibrary p){
        List<PriceLibraryDTO> addList = new LinkedList<PriceLibraryDTO>(){{
            add(item);
        }};

        Map<String,List<PriceLibraryDTO>> result = new HashMap<>();
        result.put("ADD",addList);
        result.put("REMOVE",Collections.EMPTY_LIST);
        result.put("UPDATE",Collections.EMPTY_LIST);
        return result;
    }


    /**
     * 判断是哪种交叉方式
     * @param item
     * @param priceLibrary
     * @return
     */
    private Integer whichCrossType(PriceLibraryDTO item, PriceLibrary priceLibrary) {
        LocalDate effectiveDate1 = DateUtil.dateToLocalDate(priceLibrary.getEffectiveDate());
        LocalDate expirationDate1 = DateUtil.dateToLocalDate(priceLibrary.getExpirationDate());

        LocalDate effectiveDate2 = DateUtil.dateToLocalDate(item.getEffectiveDate());
        LocalDate expirationDate2 = DateUtil.dateToLocalDate(item.getExpirationDate());

        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
                isBeforeAndEquals(effectiveDate2,expirationDate1) &&
                isBeforeAndEquals(expirationDate1,expirationDate2)
        ){
            //左相交
            return CROSSTYPE.LEFTCROSS.value;
        }else if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
                isBeforeAndEquals(effectiveDate1,expirationDate2) &&
                isBeforeAndEquals(expirationDate2,expirationDate1)
        ){
            //右相交
            return CROSSTYPE.RIGHTCROSS.value;
        }else if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
                isBeforeAndEquals(expirationDate2,expirationDate1)
        ){
            //包含
            return CROSSTYPE.CONTAINS.value;
        }else if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
                isBeforeAndEquals(expirationDate1,expirationDate2)
        ){
            //被包含
            return CROSSTYPE.INCLUDED.value;
        }else {
            //不交叉
            return CROSSTYPE.NOCROSS.value;
        }

    }

    /**
     * 根据物料编码 + 物料描述 + 供应商编码 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间 为key值
     * @param priceLibraryList
     * @return
     */
    private Map<String,PriceLibrary> getUpdateCategoryPriceLibraryMap1(List<PriceLibrary> priceLibraryList){
        Map<String,PriceLibrary> map = new HashMap<>();
        for(int i=0;i<priceLibraryList.size();i++){
            PriceLibrary priceLibrary = priceLibraryList.get(i);
            String startTime = String.valueOf(DateUtil.dateToLocalDate(priceLibrary.getEffectiveDate()));
            String endTime = String.valueOf(DateUtil.dateToLocalDate(priceLibrary.getExpirationDate()));
            StringBuffer key = new StringBuffer();
            key.append(priceLibrary.getItemCode())
                    .append(priceLibrary.getItemDesc())
                    .append(priceLibrary.getVendorCode())
                    .append(priceLibrary.getCeeaOrgId())
                    .append(priceLibrary.getCeeaOrganizationId())
                    .append(priceLibrary.getCeeaArrivalPlace())
                    .append(priceLibrary.getPriceType())
                    .append(startTime)
                    .append(endTime);
            map.put(key.toString(),priceLibrary);
        }
        return map;
    }

    /**
     * (含付款条款)
     * 根据物料编码 + 物料描述 + 供应商编码 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间 为key值
     * @param priceLibraryList
     * @return
     */
    private Map<String,PriceLibraryDTO> getUpdateCategoryPriceLibraryMap2(List<PriceLibraryDTO> priceLibraryList){
        Map<String,PriceLibraryDTO> map = new HashMap<>();
        for(int i=0;i<priceLibraryList.size();i++){
            PriceLibraryDTO priceLibrary = priceLibraryList.get(i);
            String startTime = String.valueOf(DateUtil.dateToLocalDate(priceLibrary.getEffectiveDate()));
            String endTime = String.valueOf(DateUtil.dateToLocalDate(priceLibrary.getExpirationDate()));
            StringBuffer key = new StringBuffer();
            key.append(priceLibrary.getItemCode())
                    .append(priceLibrary.getItemDesc())
                    .append(priceLibrary.getVendorCode())
                    .append(priceLibrary.getCeeaOrgId())
                    .append(priceLibrary.getCeeaOrganizationId())
                    .append(priceLibrary.getCeeaArrivalPlace())
                    .append(priceLibrary.getPriceType())
                    .append(startTime)
                    .append(endTime);
            map.put(key.toString(),priceLibrary);
        }
        return map;
    }

    /**
     * 根据物料编码 + 供应商编码 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间 为key值
     * @param priceLibraryList
     * @return
     */
    private Map<String,PriceLibrary> getNotUpdateCategoryPriceLibraryMap1(List<PriceLibrary> priceLibraryList){
        Map<String,PriceLibrary> map = new HashMap<>();
        for(int i=0;i<priceLibraryList.size();i++){
            PriceLibrary priceLibrary = priceLibraryList.get(i);
            String startTime = String.valueOf(DateUtil.dateToLocalDate(priceLibrary.getEffectiveDate()));
            String endTime = String.valueOf(DateUtil.dateToLocalDate(priceLibrary.getExpirationDate()));
            StringBuffer key = new StringBuffer();
            key.append(priceLibrary.getItemCode())
                    .append(priceLibrary.getVendorCode())
                    .append(priceLibrary.getCeeaOrgId())
                    .append(priceLibrary.getCeeaOrganizationId())
                    .append(priceLibrary.getCeeaArrivalPlace())
                    .append(priceLibrary.getPriceType())
                    .append(startTime)
                    .append(endTime);
            map.put(key.toString(),priceLibrary);
        }
        return map;
    }

    /**
     * (含付款条款)
     * 根据物料编码 + 物料描述 + 供应商编码 + 业务实体id + 库存组织id + 到货地点 + 价格类型 + 价格开始时间 + 价格结束时间 为key值
     * @param priceLibraryList
     * @return
     */
    private Map<String,PriceLibraryDTO> getNotUpdateCategoryPriceLibraryMap2(List<PriceLibraryDTO> priceLibraryList){
        Map<String,PriceLibraryDTO> map = new HashMap<>();
        for(int i=0;i<priceLibraryList.size();i++){
            PriceLibraryDTO priceLibrary = priceLibraryList.get(i);
            String startTime = String.valueOf(DateUtil.dateToLocalDate(priceLibrary.getEffectiveDate()));
            String endTime = String.valueOf(DateUtil.dateToLocalDate(priceLibrary.getExpirationDate()));
            StringBuffer key = new StringBuffer();
            key.append(priceLibrary.getItemCode())
                    .append(priceLibrary.getVendorCode())
                    .append(priceLibrary.getCeeaOrgId())
                    .append(priceLibrary.getCeeaOrganizationId())
                    .append(priceLibrary.getCeeaArrivalPlace())
                    .append(priceLibrary.getPriceType())
                    .append(startTime)
                    .append(endTime);
            map.put(key.toString(),priceLibrary);
        }
        return map;
    }


    /**
     * 插入价格库和付款条款
     * @param priceLibraryDTO
     */
    public void savePriceLibraryAndPaymentItems(PriceLibraryDTO priceLibraryDTO){
        LocalDate startDate = DateUtil.dateToLocalDate(priceLibraryDTO.getEffectiveDate());
        LocalDate endDate = DateUtil.dateToLocalDate(priceLibraryDTO.getExpirationDate());
        if(!isBeforeAndEquals(startDate,endDate)){
            return;
        }

        Long priceLibraryId = IdGenrator.generate();
        priceLibraryMapper.insert(priceLibraryDTO.setPriceLibraryId(priceLibraryId));
        if(CollectionUtils.isNotEmpty(priceLibraryDTO.getPriceLibraryPaymentTermList())){
            priceLibraryDTO.getPriceLibraryPaymentTermList().forEach(item -> {
                Long priceLibraryPaymentTermId = IdGenrator.generate();
                item.setPriceLibraryId(priceLibraryId)
                        .setPriceLibraryPaymentTermId(priceLibraryPaymentTermId);
            });
            priceLibraryPaymentTermService.saveBatch(priceLibraryDTO.getPriceLibraryPaymentTermList());
        }
    }

    /**
     * 批量插入价格库和付款条款
     * @param priceLibraryDTOList
     */
    public void saveBatchPriceLibraryAndPaymentItems(List<PriceLibraryDTO> priceLibraryDTOList){
        //过滤掉 生效时间大于失效时间的价格库数据
        priceLibraryDTOList = priceLibraryDTOList
                .stream()
                .filter(item -> isBeforeAndEquals(DateUtil.dateToLocalDate(item.getEffectiveDate()),DateUtil.dateToLocalDate(item.getExpirationDate())))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(priceLibraryDTOList)){
            return;
        }


        List<PriceLibrary> priceLibraryList = new LinkedList<>();
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = new LinkedList<>();
        for(PriceLibraryDTO priceLibraryDTO : priceLibraryDTOList){
            Long priceLibraryId = IdGenrator.generate();
            priceLibraryDTO.setPriceLibraryId(priceLibraryId);
            if(CollectionUtils.isNotEmpty(priceLibraryDTO.getPriceLibraryPaymentTermList())){
                priceLibraryDTO.getPriceLibraryPaymentTermList().forEach(item -> {
                    Long priceLibraryPaymentTermId = IdGenrator.generate();
                    item.setPriceLibraryId(priceLibraryId)
                            .setPriceLibraryPaymentTermId(priceLibraryPaymentTermId);
                });
            }

            PriceLibrary p = new PriceLibrary();
            BeanUtils.copyProperties(priceLibraryDTO,p);
            priceLibraryList.add(p);
            priceLibraryPaymentTermList.addAll(priceLibraryDTO.getPriceLibraryPaymentTermList());
        }
        this.saveBatch(priceLibraryList);
        priceLibraryPaymentTermService.saveBatch(priceLibraryPaymentTermList);

    }

    /**
     * 更新价格库和付款条款
     * @param priceLibraryDTO
     */
    public void updatePriceLibraryAndPaymentItems(PriceLibraryDTO priceLibraryDTO){
        //过滤掉 生效时间大于失效时间的价格库数据
        LocalDate startDate = DateUtil.dateToLocalDate(priceLibraryDTO.getEffectiveDate());
        LocalDate endDate = DateUtil.dateToLocalDate(priceLibraryDTO.getExpirationDate());
        if(!isBeforeAndEquals(startDate,endDate)){
            return;
        }

        priceLibraryMapper.updateById(priceLibraryDTO);
        Long priceLibraryId = priceLibraryDTO.getPriceLibraryId();
        QueryWrapper<PriceLibraryPaymentTerm> wrapper = new QueryWrapper();
        wrapper.eq("PRICE_LIBRARY_ID",priceLibraryId);
        priceLibraryPaymentTermService.remove(wrapper);
        if(CollectionUtils.isNotEmpty(priceLibraryDTO.getPriceLibraryPaymentTermList())){
            priceLibraryDTO.getPriceLibraryPaymentTermList().forEach(item -> {
                Long priceLibraryPaymentTermId = IdGenrator.generate();
                item.setPriceLibraryId(priceLibraryId)
                        .setPriceLibraryPaymentTermId(priceLibraryPaymentTermId);
            });
            priceLibraryPaymentTermService.saveBatch(priceLibraryDTO.getPriceLibraryPaymentTermList());
        }
    }

    public void updateBatchPriceLibraryAndPaymentItems(List<PriceLibraryDTO> priceLibraryDTOList){
        //过滤掉 生效时间大于失效时间的价格库数据
        priceLibraryDTOList = priceLibraryDTOList
                .stream()
                .filter(item -> isBeforeAndEquals(DateUtil.dateToLocalDate(item.getEffectiveDate()),DateUtil.dateToLocalDate(item.getExpirationDate())))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(priceLibraryDTOList)){
            return;
        }

        List<Long> priceLibraryIds = new LinkedList<>();
        List<PriceLibrary> priceLibraryList = new LinkedList<>();
        List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = new LinkedList<>();
        for(PriceLibraryDTO priceLibraryDTO : priceLibraryDTOList){
            Long priceLibraryId = priceLibraryDTO.getPriceLibraryId();
            priceLibraryIds.add(priceLibraryId);

            PriceLibrary p = new PriceLibrary();
            BeanUtils.copyProperties(priceLibraryDTO,p);
            priceLibraryList.add(p);

            if(CollectionUtils.isNotEmpty(priceLibraryDTO.getPriceLibraryPaymentTermList())){
                priceLibraryDTO.getPriceLibraryPaymentTermList().forEach(item -> {
                    Long priceLibraryPaymentTermId = IdGenrator.generate();
                    item.setPriceLibraryId(priceLibraryId)
                            .setPriceLibraryPaymentTermId(priceLibraryPaymentTermId);
                });
                priceLibraryPaymentTermList.addAll(priceLibraryDTO.getPriceLibraryPaymentTermList());
            }
        }
        this.updateBatchById(priceLibraryList);
        QueryWrapper<PriceLibraryPaymentTerm> wrapper = new QueryWrapper();
        wrapper.in("PRICE_LIBRARY_ID",priceLibraryIds);
        priceLibraryPaymentTermService.remove(wrapper);
        priceLibraryPaymentTermService.saveBatch(priceLibraryPaymentTermList);

    }


    /**
     * 删除价格库和付款条款
     * @param priceLibraryDTO
     */
    public void removePriceLibraryAndPaymentItems(PriceLibraryDTO priceLibraryDTO){
        Long priceLibraryId = priceLibraryDTO.getPriceLibraryId();
        priceLibraryMapper.deleteById(priceLibraryId);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("PRICE_LIBRARY_ID",priceLibraryId);
        priceLibraryPaymentTermService.remove(wrapper);
    }

    /**
     * 批量删除价格库和付款条款
     * @param priceLibraryDTOList
     */
    public void removeBatchPriceLibraryAndPaymentItems(List<PriceLibraryDTO> priceLibraryDTOList){
        List<Long> priceLibraryIds = new LinkedList<>();
        for(PriceLibraryDTO priceLibraryDTO : priceLibraryDTOList){
            priceLibraryIds.add(priceLibraryDTO.getPriceLibraryId());
        }
        this.removeByIds(priceLibraryIds);
        QueryWrapper<PriceLibraryPaymentTerm> wrapper = new QueryWrapper<>();
        wrapper.in("PRICE_LIBRARY_ID",priceLibraryIds);
        priceLibraryPaymentTermService.remove(wrapper);

    }


    /**
     * 判断数据格式是否正确
     * @param priceLibraryList
     */
    public void checkIfFormatCorrect(List<PriceLibraryDTO> priceLibraryList){
        priceLibraryList.forEach(item -> {
            LocalDate effective = DateUtil.dateToLocalDate(item.getEffectiveDate());
            LocalDate expiration = DateUtil.dateToLocalDate(item.getExpirationDate());
            if(effective.isAfter(expiration)){
                throw new BaseException(LocaleHandler.getLocaleMsg("生效日期不可大于失效日期"));
            }

        });
    }

    /**
     * 判断list集合中的价格和传入的价格是否交叉
     * @param priceLibraryList
     * @param priceLibrary
     */
    public void checkIfOverlapping(List<PriceLibrary> priceLibraryList,PriceLibrary priceLibrary){
        int index = 0;
        for(PriceLibrary pl:priceLibraryList){
            if(ifDateOverlapping(pl,priceLibrary)){
                index ++;
            }
        }
        if(index >= 2){
            throw new BaseException(LocaleHandler.getLocaleMsg("新插入的价格和数据库多条价格有交叉"));
        }
    }

    /**
     * 判断list集合中的数据是否有相交情况
     * @param priceLibraryList
     */
    public void checkIfOverlapping(List<PriceLibraryDTO> priceLibraryList){
        /*按照 物料编号 + 物料描述 + 供应商编号 + 业务实体id + 库存组织id + 到货地点 + 价格类型 */
        List<List<PriceLibrary>> lists = new ArrayList<>();
        priceLibraryList.forEach(insertItem -> {
            boolean add = true;
            for(int i=0;i<lists.size();i++){
                List<PriceLibrary> list = lists.get(i);
                if(list.size() > 0){
                    PriceLibrary priceLibrary = list.get(0);
                    if(ifCommonGroup(priceLibrary,insertItem)){
                        list.add(insertItem);
                        add = false;
                        break;
                    }
                }
            }
            if(add){
                List<PriceLibrary> list = new ArrayList<>();
                list.add(insertItem);
                lists.add(list);
            }
        });
        log.info("lists.size:" + lists.size());
        log.info("lists:" + lists);

        lists.forEach(list -> {
            for(int i=0;i<list.size();i++){
                PriceLibrary priceLibrary1 = list.get(i);
                for(int j=i+1;j<list.size();j++){
                    PriceLibrary priceLibrary2 = list.get(j);
                    if(ifDateOverlapping(priceLibrary1,priceLibrary2)){
                        throw new BaseException(LocaleHandler.getLocaleMsg("插入的价格有效时间有相交情况，请检查"));
                    }
                }
            }
        });
    }

    /**
     * 判断两个价格是否交叉
     * @param priceLibrary1
     * @param priceLibrary2
     * @return
     */
    public boolean ifDateOverlapping(PriceLibrary priceLibrary1,PriceLibrary priceLibrary2){
        LocalDate effectiveDate1 = DateUtil.dateToLocalDate(priceLibrary1.getEffectiveDate());
        LocalDate expirationDate1 = DateUtil.dateToLocalDate(priceLibrary1.getExpirationDate());

        LocalDate effectiveDate2 = DateUtil.dateToLocalDate(priceLibrary2.getEffectiveDate());
        LocalDate expirationDate2 = DateUtil.dateToLocalDate(priceLibrary2.getExpirationDate());
        /*左相交*/
        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
                isBeforeAndEquals(effectiveDate2,expirationDate1) &&
                isBeforeAndEquals(expirationDate1,expirationDate2)
        ){
            log.info("priceLibrary1:" + priceLibrary1);
            log.info("priceLibrary2:" + priceLibrary2);
            log.info("价格不规范-左相交,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
            return true;
        }
        /*右相交*/
        if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
                isBeforeAndEquals(effectiveDate1,expirationDate2) &&
                isBeforeAndEquals(expirationDate2,expirationDate1)
        ){
            log.info("priceLibrary1:" + priceLibrary1);
            log.info("priceLibrary2:" + priceLibrary2);
            log.info("价格不规范-右相交,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
            return true;
        }
        /*包含*/
        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
                isBeforeAndEquals(expirationDate2,expirationDate1)
        ){
            log.info("priceLibrary1:" + priceLibrary1);
            log.info("priceLibrary2:" + priceLibrary2);
            log.info("价格不规范-包含,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
            return true;
        }
        /*被包含*/
        if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
                isBeforeAndEquals(expirationDate1,expirationDate2)
        ){
            log.info("priceLibrary1:" + priceLibrary1);
            log.info("priceLibrary2:" + priceLibrary2);
            log.info("价格不规范-被包含,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
            return true;
        }
        return false;
    }

    /**
     * @param localDate1
     * @param localDate2
     * @return
     */
    public boolean isBeforeAndEquals(LocalDate localDate1,LocalDate localDate2){
        return localDate1.isBefore(localDate2) || localDate1.isEqual(localDate2);
    }

    public boolean isBefore(LocalDate localDate1,LocalDate localDate2){
        return localDate1.isBefore(localDate2);
    }

    /**
     * 判断两个价格是否为同一组
     * @param priceLibrary1
     * @param priceLibrary2
     * @return
     */
    public boolean ifCommonGroup(PriceLibrary priceLibrary1,PriceLibrary priceLibrary2){
        if(!priceLibrary1.getItemCode().equals(priceLibrary2.getItemCode())){
            return false;
        }
        if(!priceLibrary1.getItemDesc().equals(priceLibrary2.getItemDesc())){
            return false;
        }
        if(!priceLibrary1.getVendorCode().equals(priceLibrary2.getVendorCode())){
            return false;
        }
        if(Long.compare(priceLibrary1.getCeeaOrgId(),priceLibrary2.getCeeaOrgId()) != 0){
            return false;
        }
        if(Long.compare(priceLibrary1.getCeeaOrganizationId(),priceLibrary2.getCeeaOrganizationId()) != 0){
            return false;
        }
        if(Objects.nonNull(priceLibrary1.getCeeaArrivalPlace()) &&
                !priceLibrary1.getCeeaArrivalPlace().equals(priceLibrary2.getCeeaArrivalPlace())){
            return false;
        }
        return !Objects.nonNull(priceLibrary1.getPriceType()) ||
                priceLibrary1.getPriceType().equals(priceLibrary2.getPriceType());
    }

    @Override
    public String ifHasPrice(PriceLibrary priceLibrary) {
        Integer count = priceLibraryMapper.findEffectivePriceCount(priceLibrary);
        if(count != 0){
            return "Y";
        }else{
            return "N";
        }
    }

    @Override
    public List<PriceLibrary> listEffectivePrice(PriceLibrary priceLibrary) {
        return priceLibraryMapper.findEffectivePrice(priceLibrary);
    }

    @Override
    public void ceeaUpdateBatch(List<PriceLibrary> priceLibraryList) {
        this.updateBatchById(priceLibraryList);
    }

    public Date sub(Date date){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_MONTH, -1);
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    public Date add(Date date){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_MONTH, 1);
        Date dt1 = rightNow.getTime();
        return dt1;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long priceLibraryId) {
        removeById(priceLibraryId);
        iPriceLadderPriceService.deleteByPriceLibraryId(priceLibraryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(List<PriceLibraryAddParam> params) {

        List<PriceLibrary> priceLibraryUpdates = new ArrayList<>();
        List<PriceLadderPrice> ladderPriceUpdates = new ArrayList<>();
        params.forEach(priceLibraryAddParam -> {
            PriceLibrary library = new PriceLibrary();
            BeanUtils.copyProperties(priceLibraryAddParam, library);

            if (CollectionUtils.isNotEmpty(priceLibraryAddParam.getLadderPrices())) {
                priceLibraryAddParam.getLadderPrices().forEach(ladder -> {
                    PriceLadderPrice ladderPrice = new PriceLadderPrice();
                    BeanUtils.copyProperties(ladder, ladderPrice);
                    ladderPriceUpdates.add(ladderPrice);
                });
            }
            priceLibraryUpdates.add(library);
        });

        updateBatchById(priceLibraryUpdates);
        if (CollectionUtils.isNotEmpty(ladderPriceUpdates)) {
            iPriceLadderPriceService.updateBatchById(ladderPriceUpdates);
        }
    }

    @Override
    public PriceLibrary getPriceLibraryByParam(NetPriceQueryDTO netPriceQueryDTO) {
        QueryWrapper<PriceLibrary> queryWrapper = new QueryWrapper<PriceLibrary>(new PriceLibrary().setItemId(netPriceQueryDTO.getMaterialId()).
                setOrganizationId(netPriceQueryDTO.getOrganizationId()).setVendorId(netPriceQueryDTO.getVendorId()));
        if (netPriceQueryDTO.getRequirementDate() != null) {
            queryWrapper.le("EFFECTIVE_DATE", netPriceQueryDTO.getRequirementDate());
            queryWrapper.ge("EXPIRATION_DATE", netPriceQueryDTO.getRequirementDate());
        }
        queryWrapper.orderByAsc("NOTAX_PRICE");
        List<PriceLibrary> priceLibraryList = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(priceLibraryList)) {
            return priceLibraryList.get(0);
        }
        return null;
    }

    /**
     * @Description 查询一条价格
     * @Param [priceLibrary]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.25 16:50
     */
    @Override
    public PriceLibrary getOnePriceLibrary(PriceLibrary priceLibrary) {
    	List<PriceLibrary> list = this.list(new QueryWrapper<>(priceLibrary));
    	if (CollectionUtils.isNotEmpty(list)) {
    		for (PriceLibrary pl : list) {
    			if (pl.getEffectiveDate() != null && pl.getExpirationDate() != null) {
    				long ms = System.currentTimeMillis();
					if (ms >= pl.getEffectiveDate().getTime() && ms <= pl.getExpirationDate().getTime()) { // 有效期内的
						return pl;
					}
				}
			}
		}
        return null;
    }

    @Override
    public List<PriceLibrary> listPriceLibraryByParam(List<NetPriceQueryDTO> netPriceQueryDTOList) {
        List<PriceLibrary> priceLibraryList = new ArrayList<>();
        netPriceQueryDTOList.forEach(dto -> {
            PriceLibrary priceLibrary = this.getPriceLibraryByParam(dto);
            if(priceLibrary != null) {
                priceLibraryList.add(priceLibrary);
            }
        });
        return priceLibraryList;
    }

    @Override
    public List<PriceLibrary> listPriceLibrary(NetPriceQueryDTO netPriceQueryDTO) {
        QueryWrapper<PriceLibrary> queryWrapper = new QueryWrapper<PriceLibrary>(new PriceLibrary().setItemId(netPriceQueryDTO.getMaterialId()).
                setCeeaOrgId(netPriceQueryDTO.getOrganizationId()).setVendorId(netPriceQueryDTO.getVendorId()));
        if (netPriceQueryDTO.getRequirementDate() != null) {
            queryWrapper.lt("EFFECTIVE_DATE", netPriceQueryDTO.getRequirementDate());
            queryWrapper.gt("EXPIRATION_DATE", netPriceQueryDTO.getRequirementDate());
        }
        return this.list(queryWrapper);
    }

    @Override
    public List<PriceLibrary> queryByContract(PriceLibraryParam priceLibraryParam) {
        // 业务实体ID
        Long organizationId = priceLibraryParam.getOrganizationId();
        String sourceNo = priceLibraryParam.getSourceNo();
        Long vendorId = priceLibraryParam.getVendorId();
        List<Long> longs = new ArrayList<>();
        if (null != organizationId) {
            List<Organization> organizations = baseClient.queryIvnByOuId(vendorId);
            if(CollectionUtils.isNotEmpty(organizations)){
                organizations.forEach(organization -> longs.add(organization.getOrganizationId()));
            }else {
                return null;
            }
        }
        QueryWrapper<PriceLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtil.notEmpty(sourceNo),"SOURCE_NO",sourceNo);
        queryWrapper.eq(StringUtil.notEmpty(vendorId),"VENDOR_ID",vendorId);
        queryWrapper.in(CollectionUtils.isNotEmpty(longs),"ORGANIZATION_ID",longs);
        return this.list(queryWrapper);
    }

    @Override
    public List<BidFrequency> getThreeYearsBidFrequency(Long vendorId) throws ParseException {
        Assert.notNull(vendorId,"vendorId不能为空");
        int year = LocalDate.now().getYear();
        ArrayList<BidFrequency> frequencies = new ArrayList<>();
        for(int i =0;i < 3 ;i++){
            Map<String, Object> param = getBetweenDate(year);
            param.put("vendorId",vendorId);
            Integer sum = this.baseMapper.getThreeYearsBidFrequency(param);
            BidFrequency bidFrequency = new BidFrequency();
            bidFrequency.setYear(year);
            bidFrequency.setFrequency(sum);
            frequencies.add(bidFrequency);
            year --;
        }
        return frequencies;
    }

    @Transactional
    @Override
    public void putOnShelves(PriceLibraryPutOnShelvesDTO priceLibraryPutOnShelvesDTO) {
        Boolean flag = false;
        List<PriceLibrary> priceLibraryList = priceLibraryPutOnShelvesDTO.getPriceLibraryList();
        Long contractHeadId = priceLibraryPutOnShelvesDTO.getContractHeadId();
        ContractDTO contractDTO = contractClient.getContractDetail(contractHeadId);
        //查询合同明细
        List<ContractMaterial> contractMaterialList = contractDTO.getContractMaterials();
        ContractHead contractHead =  contractDTO.getContractHead();
        String vendorCode = contractHead.getVendorCode();
        String contractCode = contractHead.getContractCode();
        for(PriceLibrary priceLibrary :priceLibraryList){
            String itemCode = priceLibrary.getItemCode();
            String ceeaOrgCode = priceLibrary.getCeeaOrgCode();
            String ceeaOrganizationCode = priceLibrary.getCeeaOrganizationCode();
            String itemDesc = priceLibrary.getItemDesc();
            //校验--按物料编码+物料描述+供应商+业务实体+库存组织，查所选的合同里明细行里有没有记录
            if(vendorCode.equals(priceLibrary.getVendorCode())){
                if(CollectionUtils.isNotEmpty(contractMaterialList)){
                    for(ContractMaterial contractMaterial:contractMaterialList){
                        if(itemDesc.equals(contractMaterial.getMaterialName())&&itemCode.equals(contractMaterial.getMaterialCode())&&ceeaOrgCode.equals(contractMaterial.getBuCode())&&ceeaOrganizationCode.equals(contractMaterial.getInvCode())){
                            //更新状态为已上架
                            flag = true;
                            priceLibrary.setCeeaIfUse("Y");
                            int row = this.getBaseMapper().updateById(priceLibrary);
                            if(row > 0){
                                updateMaterialItemInfo(priceLibrary,contractCode);
                            }
                        }
                    }
                }
            }
        }
        // 全部校验失败
        if(!flag){
            throw new BaseException("R015", "上架物料和您维护的合同中签订的物料不一致，请检查");
        }
    }

    @Transactional
    @Override
    public void pullOffShelves(List<PriceLibrary> priceLibraryList) {
        for(int i = 0; i < priceLibraryList.size(); i++){
            PriceLibrary priceLibrary = priceLibraryList.get(i);
            if(!StringUtil.isEmpty(priceLibrary.getCeeaIfUse())&&priceLibrary.getCeeaIfUse().equals("Y")){
                priceLibrary.setCeeaIfUse("N");
                int row = this.getBaseMapper().updateById(priceLibrary);
                if(row > 0){
                    //【采购物料维护】中是否目录化更新为否
                    MaterialItem materialItem = baseClient.findMaterialItemByMaterialCode(priceLibrary.getItemCode());
                    materialItem.setCeeaIfCatalogMaterial("N");
                    baseClient.updateMaterialItemById(materialItem);
                }
            }
        }
    }

    @Override
    public void importModelDownload(HttpServletResponse httpServletResponse) throws Exception{
        String fileName = "价格目录导入模板";
        ArrayList<PriceLibraryModelDto> priceLibraryModelDtos = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(httpServletResponse, fileName);
        List<Integer> rows = Arrays.asList(0);
        List<Integer> columns = Arrays.asList(0,1,2,4,5,7,8,9,10,13,14,15,16,17,18,19);
        //新增第一行导入规则描述
        PriceLibraryModelDto priceLibraryModelDto = new PriceLibraryModelDto();
        priceLibraryModelDto.setItemCode("必填");
        priceLibraryModelDto.setItemDesc("必填");
        priceLibraryModelDto.setVendorCode("必填填写供应商在ERP系统的编号");
        priceLibraryModelDto.setVendorName("非必填");
        priceLibraryModelDto.setCeeaOrgCode("必填填写业务实体编号");
        priceLibraryModelDto.setCeeaOrganizationCode("必填填写库存组织编号");
        priceLibraryModelDto.setCeeaArrivalPlace("只有外协厂的定价才填写此栏位填写城市名称，例如西安、北京、大连等");
        priceLibraryModelDto.setTaxPrice("必填填写含税单价");
        priceLibraryModelDto.setTaxKey("必填填写税码，比如XXX");
        priceLibraryModelDto.setTaxRate("填写不带百分号的税率，比如13、16、17等，未来导入系统按%号格式");
        priceLibraryModelDto.setCurrencyCode("必填填货币代码，比如CNY,USD,HKD等");
        priceLibraryModelDto.setCeeaAllocationType("非必填下拉选择，两个选项：\n" + "1）按金额比例\n" + "2）按物料比例");
        priceLibraryModelDto.setEffectiveDate("必填按日期格式“2020-09-04”填写注意：有效期不能重叠！！！");
        priceLibraryModelDto.setExpirationDate("必填按日期格式“2020-09-04”填写注意：有效期不能重叠！！！");
        priceLibraryModelDto.setPaymentTerms("必填仅供参考，待付款条件重新收集整理后填写");
        priceLibraryModelDto.setPaymentType("必填下拉选择");
        priceLibraryModelDto.setPaymentDays("必填下拉选择");
        priceLibraryModelDto.setCeeaLt("必填填写供货周期天数");
        priceLibraryModelDto.setFrameworkAgreement("必填填写下订单时所需的框架协议编号");
        priceLibraryModelDtos.add(priceLibraryModelDto);
        TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rows,columns, IndexedColors.RED.index);
        EasyExcelUtil.writeExcelWithModel(outputStream,priceLibraryModelDtos,PriceLibraryModelDto.class,fileName,titleColorSheetWriteHandler);
    }

    @Override
    public void importExcel(MultipartFile file) throws Exception {
        try {
            //校验文件格式
            String originalFilename = file.getOriginalFilename();
            if (!EasyExcelUtil.isExcel(originalFilename)) {
                throw new BaseException("请导入正确的Excel文件");
            }
            InputStream inputStream = file.getInputStream();
            List<PriceLibrary> updatePriceLibrarys = new ArrayList<>();
            List<PriceLibrary> addPriceLibrarys = new ArrayList<>();
            List<Object> objectList = EasyExcelUtil.readExcelWithModel(inputStream, PriceLibraryModelDto.class);
            objectList.forEach(object ->{
                try {
                    if(object != null){
                        PriceLibraryModelDto priceLibraryModelDto = (PriceLibraryModelDto) object;
                        checkRequireParam(priceLibraryModelDto);
                        PriceLibrary priceLibrary = checkImportRowIfExist(priceLibraryModelDto);
                        if(null != priceLibrary){
                            priceLibrary.setVendorName(priceLibraryModelDto.getVendorName());
                            priceLibrary.setTaxPrice(new BigDecimal(priceLibraryModelDto.getTaxPrice()));
                            priceLibrary.setTaxKey(priceLibraryModelDto.getTaxKey());
                            priceLibrary.setTaxRate(priceLibraryModelDto.getTaxRate());
                            priceLibrary.setCurrencyCode(priceLibraryModelDto.getCurrencyCode());
                            priceLibrary.setCeeaAllocationType(priceLibraryModelDto.getCeeaAllocationType());
                            priceLibrary.setCeeaQuotaProportion(new BigDecimal(priceLibraryModelDto.getCeeaQuotaProportion()));
                            priceLibrary.setCeeaLt(priceLibraryModelDto.getCeeaLt());
                            updatePriceLibrarys.add(priceLibrary);
                        }else{
                            addPriceLibrarys.add(buildAddPriceLibrary(priceLibraryModelDto));
                        }
                    }
                } catch (Exception e) {
                    log.info("import PriceCatalog error:{}",e);
                    throw new BaseException("导入失败");
                }
            });
            saveBatch(addPriceLibrarys);
            updateBatchById(updatePriceLibrarys);
        } catch (BaseException be) {
            throw be;
        }catch (Exception e){
            log.info("import PriceCatalog error:{}",e);
            throw e;
        }
    }

    /**
     * 获取所有的业务实体，库存组织
     * 根据业务实体名称 获取业务实体id,业务实体名称，业务实体编码。
     * 根据库存组织编码 获取库存组织id,库存组织名称，库存组织编码。
     * 处理价格有效期，价格失效期
     * 获取所有的价格库数据，判断是否重叠，如果重叠则报错
     * 筛选出 插入与更新的数据。
     *
     * @param file
     * @throws IOException
     */
    @Override
    @Transactional
    public Map<String,Object> importInitDataExcel(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (!EasyExcelUtil.isExcel(filename)) {
            throw new BaseException("请导入正确的Excel文件");
        }
        log.info("读取文件开始");
        InputStream inputStream = file.getInputStream();
        // 获取excel数据
        List<PriceLibraryImportDTO> priceLibraryImportDTOList = EasyExcelUtil.readExcelWithModel(PriceLibraryImportDTO.class,inputStream);
        if(CollectionUtils.isEmpty(priceLibraryImportDTOList)){
            throw new BaseException("导入文件内容不能为空");
        }
        log.info("读取文件完成");

        List<PriceLibrary> priceLIbraryAll = priceLibraryMapper.listAllPriceForImport();

        AtomicBoolean errorFlag = new AtomicBoolean(false);
        List<PriceLibrary> priceLibraryUpdates = new ArrayList<>();
        List<PriceLibrary> priceLibraryAdds = new ArrayList<>();

        checkAndSetPriceLibraryData(priceLibraryImportDTOList,
                errorFlag,priceLibraryUpdates,priceLibraryAdds,priceLIbraryAll);
        log.info("校验完成");
        if(errorFlag.get()){
            /*报错，返回错误文件*/
            log.info("文件发生错误");
            String type = filename.substring(filename.lastIndexOf(".") + 1);
            Fileupload fileupload = new Fileupload()
                    .setFileModular("价格库")
                    .setFileFunction("价格库导入")
                    .setSourceType("价格库导入")
                    .setUploadType(FileUploadType.FASTDFS.name())
                    .setFileType(type);
            fileupload.setFileSourceName(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "价格库导入报错." + type);
            Fileupload wrongFile = EasyExcelUtil.uploadErrorFile(fileCenterClient,fileupload,priceLibraryImportDTOList,PriceLibraryImportDTO.class,file);
            return ImportStatus.importError(wrongFile.getFileuploadId(),wrongFile.getFileSourceName());

        }else{
            log.info("------------------------------------价格库导入更新开始-------------------------------------------");
            if(CollectionUtils.isNotEmpty(priceLibraryUpdates)){
                this.updateBatchById(priceLibraryUpdates);
            }
            log.info("------------------------------------价格库导入更新结束-------------------------------------------");
            log.info("------------------------------------价格库导入新增开始-------------------------------------------");
            if(CollectionUtils.isNotEmpty(priceLibraryAdds)){
                this.saveBatch(priceLibraryAdds);
            }
            log.info("------------------------------------价格库导入新增结束-------------------------------------------");

        }
        return ImportStatus.importSuccess();

    }

    /**
     * 物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格类型+价格有效期自+价格有效期至
     * @return
     */
    public Map<String, PriceLibrary> getPriceLibraryMap1(List<PriceLibrary> priceLIbraryAll) {
        Map<String, PriceLibrary> priceLibraryMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(priceLIbraryAll)){
            priceLibraryMap = priceLIbraryAll.stream().
                    filter(priceLibrary -> StringUtil.notEmpty(priceLibrary.getPriceType())
                            && "STANDARD".equals(priceLibrary.getPriceType().trim())
                            && null != priceLibrary.getEffectiveDate()
                            && null != priceLibrary.getExpirationDate()).
                    collect(Collectors.toMap(k -> k.getItemCode() + k.getItemDesc() + k.getVendorCode() +
                                    k.getCeeaOrgCode() + k.getCeeaOrganizationCode() + k.getCeeaArrivalPlace() +
                                    DateUtil.format(k.getEffectiveDate(),DateUtil.DATE_FORMAT_14) + DateUtil.format(k.getExpirationDate(),DateUtil.DATE_FORMAT_14),
                            v -> v, (k1, k2) -> k1));
        }
        return priceLibraryMap;
    }

    /**
     * 物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格类型
     * @return
     */
    public Map<String, List<PriceLibrary>> getPriceLibraryMap2(List<PriceLibrary> priceLIbraryAll) {
        Map<String, List<PriceLibrary>> priceLibraryMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(priceLIbraryAll)){
            priceLibraryMap = priceLIbraryAll.stream().
                    filter(priceLibrary -> StringUtil.notEmpty(priceLibrary.getPriceType())
                            && "STANDARD".equals(priceLibrary.getPriceType().trim())
                            && null != priceLibrary.getEffectiveDate()
                            && null != priceLibrary.getExpirationDate()).
                    collect(Collectors.groupingBy(k -> k.getItemCode() + k.getItemDesc() + k.getVendorCode() +
                                    k.getCeeaOrgCode() + k.getCeeaOrganizationCode() + k.getCeeaArrivalPlace()));
        }
        return priceLibraryMap;
    }

    /**
     * 分批次插入数据库
     * @param priceLibraryList
     */
    private void saveBatchPriceLibrary(List<PriceLibrary> priceLibraryList){

    }


    /**
     * 查询最新的一条价格库数据
     * @param priceLibrary
     * @return
     */
    @Override
    public PriceLibrary getLatest(PriceLibrary priceLibrary) {
        System.out.println(JsonUtil.entityToJsonStr(priceLibrary));
        return priceLibraryMapper.getLatest(priceLibrary);
    }

    /**
     *  当集合数量 = 0 时，直接插入，排序，按时间从远到近
     *  当集合数量 <5且>0 时
     *  遍历集合，判断是否相等，相等则替换，否则直接插入，排序（按时间从远到近）
     *  当集合数量 > 5 时
     *  获取集合第一个值，远，相等，近
     *  （比第一个集合近，判断和其他四个值是否相等，相等则替换，不相等则替换掉第一个值）
     * @param priceLibraryRequestDTO
     * @return
     */
    @Override
    public List<PriceLibrary> getLatestFive(PriceLibraryRequestDTO priceLibraryRequestDTO) {
        List<PriceLibrary> priceLibraryList = priceLibraryMapper.getLatestFive(priceLibraryRequestDTO);
        List<PriceLibrary> result = new ArrayList<>();
        //创建日期 含税单价
        Map<LocalDate,PriceLibrary> map = new HashMap<>();

        for(PriceLibrary p : priceLibraryList){
            LocalDate localDate = DateUtil.dateToLocalDate(p.getCreationDate());
            if(map.get(localDate) == null){
                map.put(localDate,p);
            }else{
                PriceLibrary priceLibrary = map.get(localDate);
                if(p.getTaxPrice().compareTo(priceLibrary.getTaxPrice()) == -1){
                    map.put(localDate,p);
                }
            }
        }

        for(Map.Entry<LocalDate, PriceLibrary> entry:map.entrySet()){
            result.add(entry.getValue());
        }

        result.stream().sorted((p1,p2) -> {
            return p2.getCreationDate().compareTo(p1.getCreationDate());
        });
        if(result.size() <=5){
            return result;
        }else{
            return result.subList(0,5);
        }

    }

    /**
     * 物料维护-采购方 根据物料编码 + 上架物料 + 价格有效期 查询
     * @param priceLibrary
     * @return
     */
    @Override
    public PageInfo<PriceLibrary> listForMaterialSecByBuyer(PriceLibrary priceLibrary) {
        PageUtil.startPage(priceLibrary.getPageNum(),priceLibrary.getPageSize());
        return new PageInfo<PriceLibrary>(priceLibraryMapper.listForMaterialSecByBuyer(priceLibrary));
    }

    /**
     * 物料维护-供应商端 根据供应商 + 上架物料 + 价格有效期 查询
     * @param priceLibrary
     * @return
     */
    @Override
    public PageInfo<PriceLibrary> listForMaterialSecByVendor(PriceLibrary priceLibrary) {
        PageUtil.startPage(priceLibrary.getPageNum(),priceLibrary.getPageSize());
        return new PageInfo<PriceLibrary>(priceLibraryMapper.listForMaterialSecByVendor(priceLibrary));
    }

    private List<PriceLibrary> checkAndSetPriceLibraryData(List<PriceLibraryImportDTO> priceLibraryImportDTOList,AtomicBoolean errorFlag,
                                                           List<PriceLibrary> priceLibraryUpdates,List<PriceLibrary> priceLibraryAdds
            ,List<PriceLibrary> priceLIbraryAll){
        // 获取数据库所有价格目录数据(key->物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格类型+价格有效期自+价格有效期至)
        Map<String, PriceLibrary> priceLibraryMap1 = getPriceLibraryMap1(priceLIbraryAll);
        // 物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格类型
        Map<String, List<PriceLibrary>> priceLibraryMap2 = getPriceLibraryMap2(priceLIbraryAll);

        Map<String, List<PriceLibrary>> priceLibraryMap3 = new HashMap<>();

        // 所有组织
        Map<String, List<Organization>> orgNameMap = new HashMap<>();
        List<Organization> organizationList = baseClient.listAllOrganizationForImport();
        if(CollectionUtils.isNotEmpty(organizationList)){
            orgNameMap = organizationList.stream().collect(Collectors.groupingBy(Organization::getOrganizationName));
        }

        // 所有物料
        Map<String, List<MaterialItem>> materialItemMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(priceLibraryImportDTOList)) {
            List<String> itemCodeList = new ArrayList<>();
            for(PriceLibraryImportDTO priceLibraryImportDTO : priceLibraryImportDTOList) {
                String itemCode = priceLibraryImportDTO.getItemCode();
                if(StringUtil.notEmpty(itemCode)){
                    itemCode = itemCode.trim();
                    itemCodeList.add(itemCode);
                }
            }
            if (CollectionUtils.isNotEmpty(itemCodeList)) {
                itemCodeList = itemCodeList.stream().distinct().collect(Collectors.toList());
                List<MaterialItem> materialItemList = baseClient.listMaterialByCodeBatch(itemCodeList);
                if(CollectionUtils.isNotEmpty(materialItemList)){
                    materialItemMap = materialItemList.stream().collect(Collectors.groupingBy(MaterialItem::getMaterialCode));
                }
            }
        }
        // 供应商数据
        Map<String, List<CompanyInfo>> companyInfoMap = new HashMap<>();
        List<CompanyInfo> companyInfoList = supplierClient.listAllForImport();
        if(CollectionUtils.isNotEmpty(companyInfoList)){
            companyInfoMap = companyInfoList.stream().collect(Collectors.groupingBy(CompanyInfo::getCompanyCode));
        }
        // 所有币种
        Map<String, List<PurchaseCurrency>> purchaseCurrencyMap = new HashMap<>();
        List<PurchaseCurrency> purchaseCurrencyList = baseClient.listAllPurchaseCurrency();
        if(CollectionUtils.isNotEmpty(purchaseCurrencyList)){
            purchaseCurrencyMap = purchaseCurrencyList.stream().collect(Collectors.groupingBy(PurchaseCurrency::getCurrencyCode));
        }
        // 判断条件是 物料编码 + 物料描述 + 供应商编号 + 业务实体 + 库存组织 + 到货地点(excel表数据默认是空) + 价格类型(excel表数据默认STANDARD) + 价格有效期自 + 价格有效期至

        List<PriceLibrary> result = new ArrayList<>();
        HashSet<String> hashSet = new HashSet<>();
        log.info("校验1开始");
        int k = 0;
        for(PriceLibraryImportDTO priceLibraryImportDTO:priceLibraryImportDTOList){
            StringBuffer onlyCode = new StringBuffer();
            boolean errorLine = true;
            k++;
            log.info("第"+k+"次循环开始");

            StringBuffer errorMsg = new StringBuffer();
            PriceLibrary priceLibrary = new PriceLibrary();
            // 寻源单号
            String sourceNo = priceLibraryImportDTO.getSourceNo();
            if(StringUtil.notEmpty(sourceNo)){
                sourceNo = sourceNo.trim();
                priceLibrary.setSourceNo(sourceNo);
            }

            // 报价行ID
            String quotationLineId = priceLibraryImportDTO.getQuotationLineId();
            if(StringUtil.notEmpty(quotationLineId)){
                quotationLineId = quotationLineId.trim();
                priceLibrary.setQuotationLineId(quotationLineId);
            }

            // 物料编码
            String itemCode = priceLibraryImportDTO.getItemCode();
            if(StringUtil.notEmpty(itemCode)){
                itemCode = itemCode.trim();
                onlyCode.append(itemCode);
                if (CollectionUtils.isNotEmpty(materialItemMap.get(itemCode))){
                    List<MaterialItem> materialItems = materialItemMap.get(itemCode);
                    MaterialItem materialItem = materialItems.get(0);
                    priceLibrary.setItemId(materialItem.getMaterialId());
                    priceLibrary.setItemCode(materialItem.getMaterialCode());
                    priceLibrary.setItemDesc(materialItem.getMaterialName());
                    priceLibrary.setCategoryId(materialItem.getCategoryId());
                    priceLibrary.setCategoryCode(materialItem.getCategoryCode());
                    priceLibrary.setCategoryName(materialItem.getCategoryName());
                    priceLibrary.setUnit(materialItem.getUnitName());
                    priceLibrary.setUnitCode(materialItem.getUnit());
                }else {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("物料编码数据库不存在; ");
                }
            }else {
                errorFlag.set(true);
                errorLine = false;
                errorMsg.append("物料编码不能为空; ");
            }

            // 物料名称
            String itemDesc = priceLibraryImportDTO.getItemDesc();
            if(StringUtil.notEmpty(itemDesc)){
                itemDesc = itemDesc.trim();
                onlyCode.append(itemDesc);
                priceLibrary.setItemDesc(itemDesc);
            }else {
                onlyCode.append(priceLibrary.getItemDesc());
            }

            // 供应商编号
            String vendorCode = priceLibraryImportDTO.getVendorCode();
            if(StringUtil.notEmpty(vendorCode)){
                vendorCode = vendorCode.trim();
                List<CompanyInfo> companyInfos = companyInfoMap.get(vendorCode);
                if(CollectionUtils.isNotEmpty(companyInfos)){
                    CompanyInfo companyInfo = companyInfos.get(0);
                    priceLibrary.setVendorId(companyInfo.getCompanyId());
                    priceLibrary.setVendorCode(companyInfo.getCompanyCode());
                    priceLibrary.setVendorName(companyInfo.getCompanyName());
                    onlyCode.append(companyInfo.getCompanyCode());
                }else {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("供应商编号数据库不存在; ");
                }
            }else {
                errorFlag.set(true);
                errorLine = false;
                errorMsg.append("供应商编号不能为空; ");
            }

            // 业务实体
            String ceeaOrgName = priceLibraryImportDTO.getCeeaOrgName();
            if(StringUtil.notEmpty(ceeaOrgName)){
                ceeaOrgName = ceeaOrgName.trim();
                List<Organization> organizations = orgNameMap.get(ceeaOrgName);
                if(CollectionUtils.isNotEmpty(organizations)){
                    Organization organization = organizations.get(0);
                    priceLibrary.setCeeaOrgId(organization.getOrganizationId());
                    priceLibrary.setCeeaOrgCode(organization.getOrganizationCode());
                    priceLibrary.setCeeaOrgName(organization.getOrganizationName());
                    onlyCode.append(organization.getOrganizationCode());
                }else {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("业务实体数据库不存在; ");
                }
            }else {
                errorFlag.set(true);
                errorLine = false;
                errorMsg.append("业务实体不能为空不能为空; ");
            }

            // 库存组织
            String ceeaOrganizationCode = priceLibraryImportDTO.getCeeaOrganizationCode();
            if(StringUtil.notEmpty(ceeaOrganizationCode)){
                ceeaOrganizationCode = ceeaOrganizationCode.trim();
                List<Organization> organizations = orgNameMap.get(ceeaOrganizationCode);
                if(CollectionUtils.isNotEmpty(organizations)){
                    Organization organization = organizations.get(0);
                    priceLibrary.setCeeaOrganizationId(organization.getOrganizationId());
                    priceLibrary.setCeeaOrganizationCode(organization.getOrganizationCode());
                    priceLibrary.setCeeaOrganizationName(organization.getOrganizationName());
                    onlyCode.append(organization.getOrganizationCode());
                }else {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("库存组织数据库不存在; ");
                }
            }else {
                errorFlag.set(true);
                errorLine = false;
                errorMsg.append("库存组织不能为空; ");
            }

            // 到货地点
            String ceeaArrivalPlace = priceLibraryImportDTO.getCeeaArrivalPlace();
            if(StringUtil.notEmpty(ceeaArrivalPlace)){
                ceeaArrivalPlace = ceeaArrivalPlace.trim();
                priceLibrary.setCeeaArrivalPlace(ceeaArrivalPlace);
                onlyCode.append(ceeaArrivalPlace);
            }else {
                onlyCode.append(ceeaArrivalPlace);
            }

            // 价格类型, 默认为 STANDARD
            priceLibrary.setPriceType("STANDARD");

            // 含税价
            String taxPrice = priceLibraryImportDTO.getTaxPrice();
            if(StringUtil.notEmpty(taxPrice)){
                taxPrice = taxPrice.trim();
                if(StringUtil.isDigit(taxPrice)){
                    priceLibrary.setTaxPrice(new BigDecimal(taxPrice));
                }else {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("含税价格式非法; ");
                }
            }

            // 税码
            String taxKey = priceLibraryImportDTO.getTaxKey();
            if(StringUtil.notEmpty(taxKey)){
                taxKey = taxKey.trim();
                priceLibrary.setTaxKey(taxKey);
            }

            // 税率
            String taxRate = priceLibraryImportDTO.getTaxRate();
            if(StringUtil.notEmpty(taxRate)){
                if (StringUtil.isDigit(taxRate)) {
                    taxRate = taxRate.trim();
                    priceLibrary.setTaxRate(taxRate);
                }else {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("税率格式非法; ");
                }
            }

            // 计算不含税价
            if(StringUtil.notEmpty(priceLibrary.getTaxRate()) && StringUtil.notEmpty(priceLibrary.getTaxPrice())){
                BigDecimal ceeaTaxRate = new BigDecimal(priceLibrary.getTaxRate()).divide(new BigDecimal(100),4,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
                priceLibrary.setNotaxPrice(priceLibrary.getTaxPrice().divide(ceeaTaxRate,2,BigDecimal.ROUND_HALF_UP));
            }

            // 补充默认字段
            priceLibrary.setCeeaIfUse("N");
            priceLibrary.setMinOrderQuantity(new BigDecimal(0));

            // 币种
            String currencyCode = priceLibraryImportDTO.getCurrencyCode();
            if(StringUtil.notEmpty(currencyCode)){
                currencyCode = currencyCode.trim();
                List<PurchaseCurrency> purchaseCurrencies = purchaseCurrencyMap.get(currencyCode);
                if(CollectionUtils.isNotEmpty(purchaseCurrencies)){
                    PurchaseCurrency purchaseCurrency = purchaseCurrencies.get(0);
                    priceLibrary.setCurrencyId(purchaseCurrency.getCurrencyId());
                    priceLibrary.setCurrencyCode(purchaseCurrency.getCurrencyCode());
                    priceLibrary.setCurrencyName(purchaseCurrency.getCurrencyName());
                }else {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("币种数据库不存在; ");
                }
            }

            // 价格有效日期
            String effectiveDateStr = priceLibraryImportDTO.getEffectiveDateStr();
            if(StringUtil.notEmpty(effectiveDateStr)){
                effectiveDateStr = effectiveDateStr.trim();
                try {
                    Date date = DateUtil.parseDate(effectiveDateStr);
                    priceLibrary.setEffectiveDate(date);
                    onlyCode.append(DateUtil.parseDateToStr(date,DateUtil.DATE_FORMAT_14));
                } catch (Exception e) {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("价格有效期自格式非法; ");
                }
            }else {
                errorFlag.set(true);
                errorLine = false;
                errorMsg.append("价格有效期自不能为空; ");
            }

            // 失效日期
            String expirationDateStr = priceLibraryImportDTO.getExpirationDateStr();
            if(StringUtil.notEmpty(expirationDateStr)){
                expirationDateStr = expirationDateStr.trim();
                try {
                    Date date = DateUtil.parseDate(expirationDateStr);
                    priceLibrary.setExpirationDate(date);
                    onlyCode.append(DateUtil.parseDateToStr(date,DateUtil.DATE_FORMAT_14));
                } catch (Exception e) {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("价格有效期至格式非法; ");
                }
            }else {
                errorFlag.set(true);
                errorLine = false;
                errorMsg.append("价格有效期至不能为空; ");
            }

            // L/T
            String ceeaLt = priceLibraryImportDTO.getCeeaLt();
            if(StringUtil.notEmpty(ceeaLt)){
                ceeaLt = ceeaLt.trim();
                priceLibrary.setCeeaLt(ceeaLt);
            }

            // 框架协议编号
            String contractCode = priceLibraryImportDTO.getContractCode();
            if(StringUtil.notEmpty(contractCode)){
                contractCode = contractCode.trim();
                priceLibrary.setContractCode(contractCode);
            }

            // 是否已上架
            String ceeaIfUse = priceLibraryImportDTO.getCeeaIfUse();
            if(StringUtil.notEmpty(ceeaIfUse)){
                ceeaIfUse = ceeaIfUse.trim();
                if(YesOrNo.YES.getValue().equals(ceeaIfUse) || YesOrNo.NO.getValue().equals(ceeaIfUse)){
                    priceLibrary.setCeeaIfUse(ceeaIfUse);
                }else {
                    errorFlag.set(true);
                    errorLine = false;
                    errorMsg.append("是否已上架只能填\"Y\"或\"N\"; ");
                }
            }


            if(errorLine){
                // 校验是否有重复行
                if(hashSet.add(onlyCode.toString())){
                    // 检查数据库是否有
                    PriceLibrary library = priceLibraryMap1.get(onlyCode.toString());
                    /**
                     * 物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格类型+价格有效期自+价格有效期至）匹配现有价格库的行项目，如果有相同的记录，则更新
                     */
                    if(null != library){
                        // 更新
                        library.setSourceNo(priceLibrary.getSourceNo());
                        library.setQuotationLineId(priceLibrary.getQuotationLineId());
                        library.setItemId(priceLibrary.getItemId());
                        library.setItemCode(priceLibrary.getItemCode());
                        library.setItemDesc(priceLibrary.getItemDesc());
                        library.setCategoryId(priceLibrary.getCategoryId());
                        library.setCategoryCode(priceLibrary.getCategoryCode());
                        library.setCategoryName(priceLibrary.getCategoryName());
                        library.setUnit(priceLibrary.getUnit());
                        library.setUnitCode(priceLibrary.getUnitCode());
                        library.setVendorId(priceLibrary.getVendorId());
                        library.setVendorCode(priceLibrary.getVendorCode());
                        library.setVendorName(priceLibrary.getVendorName());
                        library.setCeeaOrgId(priceLibrary.getCeeaOrgId());
                        library.setCeeaOrgCode(priceLibrary.getCeeaOrgCode());
                        library.setCeeaOrgName(priceLibrary.getCeeaOrgName());
                        library.setCeeaOrganizationId(priceLibrary.getCeeaOrganizationId());
                        library.setCeeaOrganizationCode(priceLibrary.getCeeaOrganizationCode());
                        library.setCeeaOrganizationName(priceLibrary.getCeeaOrganizationName());
                        library.setTaxPrice(priceLibrary.getTaxPrice());
                        library.setTaxKey(priceLibrary.getTaxKey());
                        library.setTaxRate(priceLibrary.getTaxRate());
                        library.setNotaxPrice(priceLibrary.getNotaxPrice());
                        library.setCurrencyId(priceLibrary.getCurrencyId());
                        library.setCurrencyCode(priceLibrary.getCurrencyCode());
                        library.setCurrencyName(priceLibrary.getCurrencyName());
                        library.setCeeaLt(priceLibrary.getCeeaLt());
                        library.setContractCode(priceLibrary.getContractCode());
                        library.setCeeaIfUse(priceLibrary.getCeeaIfUse());
                        library.setCeeaArrivalPlace(priceLibrary.getCeeaArrivalPlace());
                        priceLibraryUpdates.add(library);
                    }else {
                        /**
                         * 如果没有，则按按（物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格类型）去匹配现有记录，
                         * 判断待更新的数据和现有价格库数据是否存在价格有效期的重叠，如果重叠，则该行报错
                         */

                        AtomicBoolean addFlag = new AtomicBoolean(true);
                        String str = priceLibrary.getItemCode() + priceLibrary.getItemDesc() + priceLibrary.getVendorCode() + priceLibrary.getCeeaOrgCode() + priceLibrary.getCeeaOrganizationCode() + priceLibrary.getCeeaArrivalPlace();
                        List<PriceLibrary> priceLibraries = priceLibraryMap2.get(str);
                        if(CollectionUtils.isNotEmpty(priceLibraries)){
                            for(PriceLibrary priceLibrary1: priceLibraries){
                                boolean flag = ifIntersect1(priceLibrary, priceLibrary1);
                                if(flag){
                                    errorFlag.set(true);
                                    addFlag.set(false);
                                    errorMsg.append("与数据库比较存在价格有效期的重叠; ");
                                    break;
                                }
                            }
                        }

                        // 校验导入行是否存在时间重叠
                        List<PriceLibrary> priceLibraryList = priceLibraryMap3.get(str);
                        if(CollectionUtils.isNotEmpty(priceLibraryList)){
                            for(PriceLibrary priceLibrary1: priceLibraryList){
                                boolean flag = ifIntersect1(priceLibrary, priceLibrary1);
                                if(flag){
                                    errorFlag.set(true);
                                    addFlag.set(false);
                                    errorMsg.append("与导入数据比较存在价格有效期的重叠; ");
                                    break;
                                }
                            }
                            priceLibraryList.add(priceLibrary);
                            priceLibraryMap3.put(str,priceLibraryList);
                        }else {
                            ArrayList<PriceLibrary> arrayList = new ArrayList<>();
                            arrayList.add(priceLibrary);
                            priceLibraryMap3.put(str,arrayList);
                        }

                        if(addFlag.get()){
                            priceLibrary.setPriceLibraryId(IdGenrator.generate());
                            priceLibrary.setCeeaPriceLibraryNo(baseClient.seqGen(SequenceCodeConstant.SEQ_INQ_PRICE_LIBRARY_NO));
                            priceLibraryAdds.add(priceLibrary);
                        }
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("物料编号+物料描述+供应商编号+业务实体+库存组织+到货地点+价格有效期自+价格有效期至:存在重复行; ");
                }
            }

            if(errorMsg.length() > 0){
                priceLibraryImportDTO.setError(errorMsg.toString());
            }else {
                priceLibraryImportDTO.setError(null);
            }

        }
        return result;
    }

//    /**
//     * 判断插入的价格库数据有没有相互交叉
//     * @param priceLibraryImportDTOList
//     * @return
//     */
//    private boolean checkIntersect(List<PriceLibraryImportDTO> priceLibraryImportDTOList){
//        boolean result = false;
//        for(int i=0;i<priceLibraryImportDTOList.size();i++){
//            for(int j=i+1;j<priceLibraryImportDTOList.size();j++){
//                if(ifIntersect(priceLibraryImportDTOList.get(i),priceLibraryImportDTOList.get(j))){
//                    String error1 = priceLibraryImportDTOList.get(i).getError();
//                    StringBuilder stringBuilder = new StringBuilder(error1 == null ? "" : error1);
//                    stringBuilder.append("[序号{"+priceLibraryImportDTOList.get(i).getLocation()+"}与序号{"+priceLibraryImportDTOList.get(j).getLocation()+"}插入的价格相交]");
//                    priceLibraryImportDTOList.get(i).setError(stringBuilder.toString());
//
//                    String error2 = priceLibraryImportDTOList.get(j).getError();
//                    StringBuilder stringBuilder2 = new StringBuilder(error2 == null ? "" : error2);
//                    stringBuilder.append("[序号{"+priceLibraryImportDTOList.get(j).getLocation()+"}与序号{"+priceLibraryImportDTOList.get(i).getLocation()+"}插入的价格相交]");
//                    priceLibraryImportDTOList.get(j).setError(stringBuilder2.toString());
//                    result = true;
//                    break;
//                }
//            }
//        }
//        return result;
//    }

//    private boolean ifCommonGroup(PriceLibraryImportDTO priceLibraryImportDTO1,PriceLibraryImportDTO priceLibraryImportDTO2){
//        /*业务实体*/
//        if(!Objects.equals(priceLibraryImportDTO1.getCeeaOrgName(),priceLibraryImportDTO2.getCeeaOrgName())){
//            return false;
//        }
//        /*库存组织*/
//        if(!Objects.equals(priceLibraryImportDTO1.getCeeaOrganizationCode(),priceLibraryImportDTO2.getCeeaOrganizationCode())){
//            return false;
//        }
//        /*物料编码*/
//        if(!Objects.equals(priceLibraryImportDTO1.getItemCode(),priceLibraryImportDTO2.getItemCode())){
//            return false;
//        }
//
//        /*物料名称*/
//        if(!Objects.equals(priceLibraryImportDTO1.getItemDesc(),priceLibraryImportDTO2.getItemDesc())){
//            return false;
//        }
//
//        /*供应商编码*/
//        if(!Objects.equals(priceLibraryImportDTO1.getVendorCode(),priceLibraryImportDTO2.getVendorCode())){
//            return false;
//        }
//        /*到货地点(不判断，默认为空)*/
//        /*价格类型(不判断，默认为空)*/
//        return true;
//    }

    public static void main(String[] args) {
        /*List<PriceLibrary> priceLibraryList = new ArrayList<>();
        PriceLibrary priceLibrary1 = new PriceLibrary()
                .setPriceLibraryId(1L)
                .setPriceNumber("P123");
        PriceLibrary priceLibrary2 = new PriceLibrary()
                .setPriceLibraryId(2L)
                .setPriceNumber("P234");
        priceLibraryList.add(priceLibrary1);
        priceLibraryList.add(priceLibrary2);
        Boolean a = new Boolean(true);
        testList(priceLibraryList,a);
        for(PriceLibrary priceLibrary:priceLibraryList){
            System.out.println(priceLibrary.getPriceLibraryId());
            System.out.println(priceLibrary.getPriceNumber());
            System.out.println("------------");
        }
        System.out.println(a);*/

        /*分批次插入数据库*/
//        List<PriceLibrary> priceLibraryList = new ArrayList<>();
//        PriceLibrary priceLibrary1 = new PriceLibrary()
//                .setPriceLibraryId(1L)
//                .setPriceNumber("P123");
//        PriceLibrary priceLibrary2 = new PriceLibrary()
//                .setPriceLibraryId(2L)
//                .setPriceNumber("P234");
//        PriceLibrary priceLibrary3 = new PriceLibrary()
//                .setPriceLibraryId(3L)
//                .setPriceNumber("P234");
//        PriceLibrary priceLibrary4 = new PriceLibrary()
//                .setPriceLibraryId(4L)
//                .setPriceNumber("P234");
//        PriceLibrary priceLibrary5 = new PriceLibrary()
//                .setPriceLibraryId(5L)
//                .setPriceNumber("P234");
//        PriceLibrary priceLibrary6 = new PriceLibrary()
//                .setPriceLibraryId(6L)
//                .setPriceNumber("P234");
//        PriceLibrary priceLibrary7 = new PriceLibrary()
//                .setPriceLibraryId(7L)
//                .setPriceNumber("P234");
//        PriceLibrary priceLibrary8 = new PriceLibrary()
//                .setPriceLibraryId(8L)
//                .setPriceNumber("P234");
//        PriceLibrary priceLibrary9 = new PriceLibrary()
//                .setPriceLibraryId(9L)
//                .setPriceNumber("P234");
//        PriceLibrary priceLibrary10 = new PriceLibrary()
//                .setPriceLibraryId(10L)
//                .setPriceNumber("P234");
//
//        priceLibraryList.add(priceLibrary1);
//        priceLibraryList.add(priceLibrary2);
//        priceLibraryList.add(priceLibrary3);
//        priceLibraryList.add(priceLibrary4);
//        priceLibraryList.add(priceLibrary5);
//        priceLibraryList.add(priceLibrary6);
//        priceLibraryList.add(priceLibrary7);
//        priceLibraryList.add(priceLibrary8);
//        priceLibraryList.add(priceLibrary9);
//        priceLibraryList.add(priceLibrary10);
//
//        List<PriceLibrary> subList = new ArrayList<>();
//        int size = 8;
//        if(priceLibraryList.size() <= size){
//        }else{
//            int times = (int) Math.ceil(priceLibraryList.size() / new Double(size));
//            log.info("总插入批次times:" + times);
//            for(int i=0;i<times;i++){
//
//                System.out.println("开始坐标：" + (i * size));
//                System.out.println("截止坐标：" +  Math.min((i + 1) * size, priceLibraryList.size()));
//                subList = priceLibraryList.subList(i * size, Math.min((i + 1) * size, priceLibraryList.size()));
//                for(PriceLibrary priceLibrary:subList){
//                    System.out.println(priceLibrary.getPriceLibraryId());
//                }
//                System.out.println("----------------");
//
//            }
//        }

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());




    }
    public static boolean testList(List<PriceLibrary> priceLibraryList,Boolean flag){
        for(PriceLibrary priceLibrary:priceLibraryList){
            priceLibrary.setPriceNumber("P345");
        }
        flag = new Boolean(false);
        return false;
    }

//    private boolean ifIntersect(PriceLibraryImportDTO priceLibraryImportDTO1,PriceLibraryImportDTO priceLibraryImportDTO2){
//        LocalDate effectiveDate1 = DateUtil.dateToLocalDate(priceLibraryImportDTO1.getEffectiveDate());
//        LocalDate expirationDate1 = DateUtil.dateToLocalDate(priceLibraryImportDTO1.getExpirationDate());
//
//        LocalDate effectiveDate2 = DateUtil.dateToLocalDate(priceLibraryImportDTO2.getEffectiveDate());
//        LocalDate expirationDate2 = DateUtil.dateToLocalDate(priceLibraryImportDTO2.getExpirationDate());
//        /*左相交*/
//        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
//                isBeforeAndEquals(effectiveDate2,expirationDate1) &&
//                isBeforeAndEquals(expirationDate1,expirationDate2)
//        ){
//            log.info("priceLibrary:" + priceLibraryImportDTO1);
//            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO2);
//            log.info("价格不规范-左相交,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
//            return true;
//        }
//        /*右相交*/
//        if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
//                isBeforeAndEquals(effectiveDate1,expirationDate2) &&
//                isBeforeAndEquals(expirationDate2,expirationDate1)
//        ){
//            log.info("priceLibrary:" + priceLibraryImportDTO1);
//            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO2);
//            log.info("价格不规范-右相交,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
//            return true;
//        }
//        /*包含*/
//        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
//                isBeforeAndEquals(expirationDate2,expirationDate1)
//        ){
//            log.info("priceLibrary:" + priceLibraryImportDTO1);
//            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO2);
//            log.info("价格不规范-包含,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
//            return true;
//        }
//        /*被包含*/
//        if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
//                isBeforeAndEquals(expirationDate1,expirationDate2)
//        ){
//            log.info("priceLibrary:" + priceLibraryImportDTO1);
//            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO2);
//            log.info("价格不规范-被包含,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
//            return true;
//        }
//        return false;
//    }

    private boolean ifIntersect1(PriceLibrary priceLibraryImportDTO1,PriceLibrary priceLibraryImportDTO2){
        LocalDate effectiveDate1 = DateUtil.dateToLocalDate(priceLibraryImportDTO1.getEffectiveDate());
        LocalDate expirationDate1 = DateUtil.dateToLocalDate(priceLibraryImportDTO1.getExpirationDate());

        LocalDate effectiveDate2 = DateUtil.dateToLocalDate(priceLibraryImportDTO2.getEffectiveDate());
        LocalDate expirationDate2 = DateUtil.dateToLocalDate(priceLibraryImportDTO2.getExpirationDate());
        /*左相交*/
        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
                isBeforeAndEquals(effectiveDate2,expirationDate1) &&
                isBeforeAndEquals(expirationDate1,expirationDate2)
        ){
            log.info("priceLibrary:" + priceLibraryImportDTO1);
            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO2);
            log.info("价格不规范-左相交,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
            return true;
        }
        /*右相交*/
        if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
                isBeforeAndEquals(effectiveDate1,expirationDate2) &&
                isBeforeAndEquals(expirationDate2,expirationDate1)
        ){
            log.info("priceLibrary:" + priceLibraryImportDTO1);
            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO2);
            log.info("价格不规范-右相交,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
            return true;
        }
        /*包含*/
        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
                isBeforeAndEquals(expirationDate2,expirationDate1)
        ){
            log.info("priceLibrary:" + priceLibraryImportDTO1);
            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO2);
            log.info("价格不规范-包含,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
            return true;
        }
        /*被包含*/
        if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
                isBeforeAndEquals(expirationDate1,expirationDate2)
        ){
            log.info("priceLibrary:" + priceLibraryImportDTO1);
            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO2);
            log.info("价格不规范-被包含,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
            return true;
        }
        return false;
    }


//    private boolean ifIntersect(PriceLibrary priceLibrary,PriceLibraryImportDTO priceLibraryImportDTO){
//        LocalDate effectiveDate1 = DateUtil.dateToLocalDate(priceLibrary.getEffectiveDate());
//        LocalDate expirationDate1 = DateUtil.dateToLocalDate(priceLibrary.getExpirationDate());
//
//        LocalDate effectiveDate2 = DateUtil.dateToLocalDate(priceLibraryImportDTO.getEffectiveDate());
//        LocalDate expirationDate2 = DateUtil.dateToLocalDate(priceLibraryImportDTO.getExpirationDate());
//        /*左相交*/
//        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
//                isBeforeAndEquals(effectiveDate2,expirationDate1) &&
//                isBeforeAndEquals(expirationDate1,expirationDate2)
//        ){
//            log.info("priceLibrary:" + priceLibrary);
//            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO);
//            log.info("价格不规范-左相交,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
//            return true;
//        }
//        /*右相交*/
//        if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
//                isBeforeAndEquals(effectiveDate1,expirationDate2) &&
//                isBeforeAndEquals(expirationDate2,expirationDate1)
//        ){
//            log.info("priceLibrary:" + priceLibrary);
//            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO);
//            log.info("价格不规范-右相交,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
//            return true;
//        }
//        /*包含*/
//        if(isBeforeAndEquals(effectiveDate1,effectiveDate2) &&
//                isBeforeAndEquals(expirationDate2,expirationDate1)
//        ){
//            log.info("priceLibrary:" + priceLibrary);
//            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO);
//            log.info("价格不规范-包含,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
//            return true;
//        }
//        /*被包含*/
//        if(isBeforeAndEquals(effectiveDate2,effectiveDate1) &&
//                isBeforeAndEquals(expirationDate1,expirationDate2)
//        ){
//            log.info("priceLibrary:" + priceLibrary);
//            log.info("priceLibraryImportDTO:" + priceLibraryImportDTO);
//            log.info("价格不规范-被包含,[" + effectiveDate1 + "," +expirationDate1 + "] [" + effectiveDate2 + "," + expirationDate2 + "]");
//            return true;
//        }
//        return false;
//    }


    @Override
    public List<PriceLibrary> listAllEffective(PriceLibrary priceLibrary) {
        return priceLibraryMapper.listAllEffective(priceLibrary);
    }


    public PriceLibrary buildAddPriceLibrary(PriceLibraryModelDto priceLibraryModelDto) throws Exception{
        Long id = IdGenrator.generate();
        PriceLibrary addPriceLibrary = new PriceLibrary();
        addPriceLibrary.setPriceLibraryId(id);
        //查询物料id和品类名称
        MaterialItem param = new MaterialItem();
        param.setMaterialCode(priceLibraryModelDto.getItemCode());
        List<MaterialItem> materialItemList = baseClient.listMaterialByParam(param);
        if(CollectionUtils.isEmpty(materialItemList)){
            throw new BaseException("导入物料编码不存在");
        }else{
            MaterialItem materialItem = materialItemList.get(0);
            addPriceLibrary.setCategoryId(materialItem.getCategoryId());
            addPriceLibrary.setCategoryName(materialItem.getCategoryName());
            addPriceLibrary.setItemId(materialItem.getMaterialId());
        }
        addPriceLibrary.setItemCode(priceLibraryModelDto.getItemCode());
        addPriceLibrary.setItemDesc(priceLibraryModelDto.getItemDesc());
        addPriceLibrary.setVendorCode(priceLibraryModelDto.getVendorCode());
        addPriceLibrary.setVendorName(priceLibraryModelDto.getVendorName());
        //查询业务实体信息
        Organization orgInfo = baseClient.getOrganizationByParam(new Organization().setOrganizationCode(priceLibraryModelDto.getCeeaOrgCode()).setOrganizationTypeCode(BaseConst.ORG_OU));
        addPriceLibrary.setCeeaOrgId(orgInfo.getOrganizationId());
        addPriceLibrary.setCeeaOrgName(orgInfo.getOrganizationName());
        addPriceLibrary.setCeeaOrgCode(priceLibraryModelDto.getCeeaOrgCode());
        //查询库存组织信息
        Organization organizationInfo = baseClient.getOrganizationByParam(new Organization().setOrganizationCode(priceLibraryModelDto.getCeeaOrganizationCode()).setOrganizationTypeCode(BaseConst.ORG_INV));
        addPriceLibrary.setCeeaOrganizationId(organizationInfo.getOrganizationId());
        addPriceLibrary.setCeeaOrganizationName(organizationInfo.getOrganizationName());
        addPriceLibrary.setCeeaOrganizationCode(priceLibraryModelDto.getCeeaOrganizationCode());
        addPriceLibrary.setCeeaArrivalPlace(priceLibraryModelDto.getCeeaArrivalPlace());
        addPriceLibrary.setTaxPrice(new BigDecimal(priceLibraryModelDto.getTaxPrice()));
        addPriceLibrary.setTaxKey(priceLibraryModelDto.getTaxKey());
        addPriceLibrary.setTaxRate(priceLibraryModelDto.getTaxRate());
        addPriceLibrary.setCurrencyCode(priceLibraryModelDto.getCurrencyCode());
        addPriceLibrary.setCeeaAllocationType(priceLibraryModelDto.getCeeaAllocationType());
        addPriceLibrary.setCeeaQuotaProportion(new BigDecimal(priceLibraryModelDto.getCeeaQuotaProportion()));
        addPriceLibrary.setExpirationDate(DateUtil.parseDate(priceLibraryModelDto.getExpirationDate()));
        addPriceLibrary.setEffectiveDate(DateUtil.parseDate(priceLibraryModelDto.getEffectiveDate()));
        addPriceLibrary.setCeeaLt(priceLibraryModelDto.getCeeaLt());
        return addPriceLibrary;
    }

    private PriceLibrary checkImportRowIfExist(PriceLibraryModelDto priceLibraryModelDto) throws Exception{
        QueryWrapper<PriceLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PriceLibrary::getItemCode,priceLibraryModelDto.getItemCode())
                .eq(PriceLibrary::getItemDesc,priceLibraryModelDto.getItemDesc())
                .eq(PriceLibrary::getVendorCode,priceLibraryModelDto.getVendorCode())
                .eq(PriceLibrary::getCeeaOrgCode,priceLibraryModelDto.getCeeaOrgCode())
                .eq(PriceLibrary::getCeeaOrganizationCode,priceLibraryModelDto.getCeeaOrganizationCode())
                .eq(StringUtils.isNotBlank(priceLibraryModelDto.getCeeaArrivalPlace()),PriceLibrary::getCeeaArrivalPlace,priceLibraryModelDto.getCeeaArrivalPlace())
                .eq(PriceLibrary::getEffectiveDate,DateUtil.parseDate(priceLibraryModelDto.getEffectiveDate()))
                .eq(PriceLibrary::getExpirationDate,DateUtil.parseDate(priceLibraryModelDto.getExpirationDate()));
       return this.getBaseMapper().selectOne(queryWrapper);
    }

    private void checkRequireParam(PriceLibraryModelDto priceLibraryModelDto){
        if(StringUtils.isBlank(priceLibraryModelDto.getItemCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("物料编码必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getItemDesc())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("物料描述必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getVendorCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("供应商ERP编号必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getVendorName())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("供应商名称必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getCeeaOrgCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("业务实体编号必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getCeeaOrganizationCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("库存组织编号必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getTaxPrice())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("含税价必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getTaxRate())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("含率必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getTaxKey())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("税码必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getCurrencyCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("币种必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getEffectiveDate())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("价格有效期自必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getExpirationDate())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("价格有效期至必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getCeeaLt())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("L/T必填，请检查导入数据行合法性再进行导入!"));
        }
        //TODO 校验待新增字段
     /*   if(StringUtils.isBlank(priceLibraryModelDto.getPaymentTerms())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("付款条件必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getPaymentType())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("付款方式必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getPaymentDays())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("账期必填，请检查导入数据行合法性再进行导入!"));
        }
        if(StringUtils.isBlank(priceLibraryModelDto.getFrameworkAgreement())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("框架协议必填，请检查导入数据行合法性再进行导入!"));
        }*/
    }


    /**
     * 更新上架物料维护信息
     * @param priceLibrary
     * @param contractCode
     */
    public void updateMaterialItemInfo(PriceLibrary priceLibrary,String contractCode){
        //根据物料编码获取物料维护
        MaterialItem materialItem = baseClient.findMaterialItemByMaterialCode(priceLibrary.getItemCode());
        materialItem.setCeeaSupplierCode(priceLibrary.getVendorCode());
        materialItem.setCeeaSupplierName(priceLibrary.getVendorName());
        materialItem.setCeeaIfCatalogMaterial("Y");
        materialItem.setCeeaContractNo(contractCode);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        materialItem.setCeeaNickname(loginAppUser.getNickname());
        materialItem.setCeeaUserId(loginAppUser.getUserId());
        materialItem.setCeeaEmpNo(loginAppUser.getUsername());
        materialItem.setCeeaOnShelfTime(new Date());
        materialItem.setCeeaMaterialStatus(CeeaMaterialStatus.NOT_NOTIFIED.getCode());
        baseClient.updateMaterialItemById(materialItem);
    }

    public Map<String, Object> getBetweenDate(int year) throws ParseException {
        String dateModel1 = "$date-01-01 00:00:00";
        String dateModel2 = "$date-12-31 23:59:59";
        Map<String, Object> betweenDate = new HashMap<>();
        betweenDate.put("startDate", DateUtil.parseDate(dateModel1.replace("$date",String.valueOf(year))));
        betweenDate.put("endDate",DateUtil.parseDate(dateModel2.replace("$date",String.valueOf(year))));
        return betweenDate;
    }

    @Override
    public PageInfo<PriceLibraryVO> PriceLibraryListPage(PriceLibrary priceLibrary) {
        PageUtil.startPage(priceLibrary.getPageNum(), priceLibrary.getPageSize());
        QueryWrapper<PriceLibrary> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getVendorName()), "VENDOR_NAME", priceLibrary.getVendorName());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getItemCode()), "ITEM_CODE", priceLibrary.getItemCode());
        wrapper.eq(priceLibrary.getCeeaOrgId() != null,"CEEA_ORG_ID",priceLibrary.getCeeaOrgId());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getCeeaOrgName()), "CEEA_ORG_NAME", priceLibrary.getCeeaOrgName());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getApprovalNo()), "APPROVAL_NO", priceLibrary.getApprovalNo());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getCeeaOrganizationName()), "CEEA_ORGANIZATION_NAME", priceLibrary.getOrganizationName());
        wrapper.eq(null != priceLibrary.getCeeaOrganizationId(), "CEEA_ORGANIZATION_ID", priceLibrary.getCeeaOrganizationId());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getCategoryName()), "CATEGORY_NAME", priceLibrary.getCategoryName());
        wrapper.eq(StringUtils.isNotBlank(priceLibrary.getCeeaIfUse()),"CEEA_IF_USE",priceLibrary.getCeeaIfUse());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getContractCode()), "CONTRACT_CODE",priceLibrary.getContractCode());
        wrapper.orderByDesc("CREATION_DATE");
        //获取所有符合条件的
        PageInfo<PriceLibraryVO> priceLibraryVOPageInfo = priceLibraryListFilterAuth(wrapper);
        if (CollectionUtils.isNotEmpty(priceLibraryVOPageInfo.getList())){
            //查询关联表的数据
//            for(PriceLibraryVO item:priceLibraryVOPageInfo.getList()){
//                /*查询改价格库的付款条款*/
//                QueryWrapper<PriceLibraryPaymentTerm> priceLibraryPaymentTermQueryWrapper = new QueryWrapper<>();
//                priceLibraryPaymentTermQueryWrapper.eq("PRICE_LIBRARY_ID",item.getPriceLibraryId());
//                List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = priceLibraryPaymentTermService.list(priceLibraryPaymentTermQueryWrapper);
//                item.setPriceLibraryPaymentTermList(priceLibraryPaymentTermList);
//            }
            // 代码优化关联
            List<Long> priceLibraryIdList = priceLibraryVOPageInfo.getList().stream().map(PriceLibraryVO::getPriceLibraryId).collect(Collectors.toList());
            List<PriceLibraryPaymentTerm> priceLibraryPaymentTermList = priceLibraryPaymentTermService.listByPriceLibraryIdCollection(priceLibraryIdList);
            Map<Long, List<PriceLibraryPaymentTerm>> priceLibraryPaymentTermMap = priceLibraryPaymentTermList.stream().collect(Collectors.groupingBy(PriceLibraryPaymentTerm::getPriceLibraryId));

            for(PriceLibraryVO item: priceLibraryVOPageInfo.getList()){
                item.setPriceLibraryPaymentTermList(priceLibraryPaymentTermMap.get(item.getPriceLibraryId()));
            }
        }
        return priceLibraryVOPageInfo;
    }

    /**
     * 根据权限过滤价格库数据
     * @param wrapper
     * @return
     */
    @AuthData(module = MenuEnum.PRICE_CATALOG)
    private PageInfo<PriceLibraryVO>  priceLibraryListFilterAuth(QueryWrapper<PriceLibrary> wrapper ){
        return new PageInfo<>(priceLibraryMapper.listPageCopy(wrapper));
    }

}

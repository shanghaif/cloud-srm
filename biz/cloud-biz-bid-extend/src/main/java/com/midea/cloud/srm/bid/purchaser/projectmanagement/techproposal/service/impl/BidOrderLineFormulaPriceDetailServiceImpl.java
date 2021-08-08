package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.BidOrderLineFormulaPriceDetailMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IBidOrderLineFormulaPriceDetailService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.feign.bargaining.BidClient;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.PricingFormulaCalculateClient;
import com.midea.cloud.srm.model.base.formula.dto.calculate.FormulaCalculateParam;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaHeader;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaLine;
import com.midea.cloud.srm.model.base.formula.enums.SourcingType;
import com.midea.cloud.srm.model.base.formula.vo.*;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.BidOrderLineFormulaPriceDetail;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implement of {@link IBidOrderLineFormulaPriceDetailService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class BidOrderLineFormulaPriceDetailServiceImpl extends ServiceImpl<BidOrderLineFormulaPriceDetailMapper, BidOrderLineFormulaPriceDetail> implements IBidOrderLineFormulaPriceDetailService {

    private final EntityManager<BidOrderLineFormulaPriceDetail>
            formulaPriceDetailDao = EntityManager.use(BidOrderLineFormulaPriceDetailMapper.class);
    private final EntityManager<BidRequirementLine>
            requirementLineDao = EntityManager.use(BidRequirementLineMapper.class);
    private final EntityManager<OrderLine>
            orderLineDao = EntityManager.use(OrderLineMapper.class);


    @Resource
    private IOrderHeadService orderHeadService;
    @Resource
    private BaseClient baseClient;
    @Resource
    private PricingFormulaCalculateClient pricingFormulaCalculateClient;
    @Resource
    private BidVendorMapper bidVendorMapper;
    @Resource
    private IOrderLineService orderLineService;

    @Override
    public List<BidOrderLineFormulaPriceDetail> findDetailsByLineId(Long lineId) {
        Assert.notNull(lineId, "供应商投标行ID不能为空");
        return formulaPriceDetailDao.findAll(
                Wrappers.lambdaQuery(BidOrderLineFormulaPriceDetail.class)
                        .eq(BidOrderLineFormulaPriceDetail::getLineId, lineId)
        );
    }

    @Override
    public List<EssentialFactorValue> findEssentialFactorValuesByDetailId(Long detailId) {
        Assert.notNull(detailId, "供应商投标行[公式报价]明细ID不能为空");
        return Optional.ofNullable(formulaPriceDetailDao.findById(detailId))
                .map(detail -> JSON.<Map<String, String>>parseObject(detail.getEssentialFactorValues(), Map.class))
                .map(propertyValueMap -> {
                    List<EssentialFactorValue> essentialFactorValues = new ArrayList<>();
                    propertyValueMap.forEach((property, value) -> essentialFactorValues.add(
                            EssentialFactorValue.builder()
                                    .essentialFactorId(Long.parseLong(property))
                                    .essentialFactorValue(value)
                                    .build()
                    ));
                    return essentialFactorValues;
                })
                .orElseThrow(() -> new BaseException("供应商投标行[公式报价]明细的要素值获取失败 | 行公式报价明细ID [" + detailId + "]"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveDetails(List<BidOrderLineFormulaPriceDetail> details) {
        if (details == null || details.isEmpty())
            return;

        // 存储 供应商投标行[公式报价]明细集
        for (BidOrderLineFormulaPriceDetail detail : details) {
            detail.setId(IdGenrator.generate());
        }
        saveBatch(details);

        // 待事务提交后，更新报价行的总价
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                Set<Long> ids = details.stream().filter(e -> Objects.nonNull(e.getLineId())).map(BidOrderLineFormulaPriceDetail::getLineId).collect(Collectors.toSet());
                //todo 公式报价不应该一行行算
                List<OrderLine> updateList = new LinkedList<>();
                for (Long id : ids) {
                    // 计算 报价行的含税总价
                    PricingFormulaCalculateResult result = calculateOrderLineTaxTotalPrice(id, null);
                    OrderLine orderLine = new OrderLine()
                            .setOrderLineId(id)
                            .setPrice(result.getValue())
                            .setFormulaResult(JSON.toJSONString(result.getFormulaParam()));
                  /*  // 更新 报价行的总价
                    orderHeadService.updateOrderLinePrice(id, lineTotalPrice);*/
                    updateList.add(orderLine);
                }
                orderLineService.updateBatchById(updateList);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDetails(FormulaCalculateParam param) {
        List<BidOrderLineFormulaPriceDetail> details = param.getBidDetails();
        List<BidOrderLineFormulaPriceDetail> updateList = new LinkedList<>();
        List<BidOrderLineFormulaPriceDetail> addList = new LinkedList<>();
        for (BidOrderLineFormulaPriceDetail detail : details) {
            if (Objects.isNull(detail.getId())) {
                detail.setId(IdGenrator.generate());
                addList.add(detail);
            } else {
                updateList.add(detail);
            }
        }
        List<BaseMaterialPriceVO> prices = param.getPrices();
        Map<Long, BaseMaterialPriceVO> collect = prices.stream().collect(Collectors.toMap(BaseMaterialPriceVO::getEssentialFactorId, Function.identity()));
        if (CollectionUtils.isNotEmpty(addList)) {
            saveBatch(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            updateBatchById(updateList);
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                Set<Long> ids = details.stream().filter(e -> Objects.nonNull(e.getLineId())).map(BidOrderLineFormulaPriceDetail::getLineId).collect(Collectors.toSet());
                List<OrderLine> updateList = new LinkedList<>();
                //todo 公式报价不应该一行行算
                for (Long id : ids) {
                    // 计算 报价行的含税总价
                    PricingFormulaCalculateResult result = calculateOrderLineTaxTotalPrice(id, collect);

                    OrderLine o = new OrderLine()
                            .setOrderLineId(id)
                            .setPrice(result.getValue())
                            .setFormulaResult(JSON.toJSONString(result.getFormulaParam()));
                    updateList.add(o);
                    /*BigDecimal lineTotalPrice = result;
                    // 更新 报价行的总价
                    orderHeadService.updateOrderLinePrice(id, lineTotalPrice);*/
                }
                orderLineService.updateBatchById(updateList);
                // 获取 其中报价行
                Long next = ids.iterator().next();
                OrderLine orderLine = orderLineDao.findById(next);
                Assert.notNull(orderLine, "获取报价行失败。 | orderLineId: [" + next + "]");

                // 获取 寻源需求行
                BidRequirementLine requirementLine = requirementLineDao.findById(orderLine.getRequirementLineId());

                if (Objects.equals(param.getSourcingType(), com.midea.cloud.srm.model.inq.price.enums.SourcingType.TENDER.getItemValue()) && !Objects.equals(requirementLine.getIsSeaFoodFormula(), "Y")) {
                    Map<Long, List<BaseMaterialPriceVO>> collect1 = prices.stream().collect(Collectors.groupingBy(BaseMaterialPriceVO::getBaseMaterialId));
                    List<BaseMaterialPriceTable> tables = new LinkedList<>();
                    for (Map.Entry<Long, List<BaseMaterialPriceVO>> entry : collect1.entrySet()) {
                        BaseMaterialPriceTable table = new BaseMaterialPriceTable();
                        table.setChange("Y");
                        table.setPrices(entry.getValue());
                        table.setBaseMaterialId(entry.getKey());
                        table.setBaseMaterialName(entry.getValue().get(0).getBaseMaterialName());
                        tables.add(table);
                    }
                    bidVendorMapper.update(null,
                            Wrappers.lambdaUpdate(BidVendor.class)
                                    .set(BidVendor::getChooseBaseMaterialPrice, JSON.toJSONString(tables))
                                    .eq(BidVendor::getBidingId, param.getBidingId())
                                    .eq(BidVendor::getVendorId, param.getVendorId())
                    );
                }

            }
        });

    }

    @Override
    public PricingFormulaCalculateResult calculateOrderLineTaxTotalPrice(Long lineId, Map<Long, BaseMaterialPriceVO> collect) {

        // 获取 报价行
        OrderLine orderLine = orderLineDao.findById(lineId);
        Assert.notNull(orderLine, "获取报价行失败。 | orderLineId: [" + lineId + "]");

        // 获取 寻源需求行
        BidRequirementLine requirementLine = requirementLineDao.findById(orderLine.getRequirementLineId());
        Assert.notNull(requirementLine, "获取寻源需求行失败。 | requirementLineId: [" + orderLine.getRequirementLineId() + "]");
        //todo 海鲜价逻辑计算逻辑补充
        String priceJSON = requirementLine.getPriceJson();
        String isSeaFoodFormula = requirementLine.getIsSeaFoodFormula();
        // 获取 寻源需求行关联物料
        MaterialItem materialItem = baseClient.listMaterialByParam(MaterialItem.builder()
                .materialId(requirementLine.getTargetId())
                .build()
        ).stream().findAny().orElseThrow(() -> new BaseException("获取寻源需求行关联物料失败。 | materialId: [" + requirementLine.getTargetId() + "]"));

        // 获取 寻源需求行关联公式
        PricingFormulaDetailVO pricingFormulaDetail = baseClient.getPricingFormulaById(requirementLine.getFormulaId());
        PricingFormulaHeader pricingFormulaHeader = pricingFormulaDetail.toPricingFormulaHeader();
        List<PricingFormulaLine> pricingFormulaLines = pricingFormulaDetail.toPricingFormulaLines();

        // 创建 公式计算参数
        PricingFormulaCalculateParameter calculateParameter = findDetailsByLineId(lineId).stream()
                .findAny()  // 业务要求，供应商只会填报一行公式值
                .map(detail -> PricingFormulaCalculateParameter.from(
                        SourcingType.BIDDING, materialItem,
                        String.valueOf(detail.getId()),
                        pricingFormulaHeader, pricingFormulaLines, collect, isSeaFoodFormula, priceJSON, null))
                .orElseThrow(() -> new BaseException("公式计算参数创建失败。"));
        // 计算价格公式值
        return pricingFormulaCalculateClient.calculate(calculateParameter);
    }
}

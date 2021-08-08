package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineTemplatePriceService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.mapper.BidOrderLineTemplatePriceDetailLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.mapper.BidOrderLineTemplatePriceDetailMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.mapper.OrderLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.IBidOrderLineTemplatePriceDetailService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.BidOrderLineTemplatePriceDetail;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.BidOrderLineTemplatePriceDetailLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo.BidOrderLineTemplatePriceDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implement of {@link IBidOrderLineTemplatePriceDetailService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class BidOrderLineTemplatePriceDetailServiceImpl implements IBidOrderLineTemplatePriceDetailService {

    private final EntityManager<OrderLine>                              orderLineDao
            = EntityManager.use(OrderLineMapper.class);
    private final EntityManager<BidOrderLineTemplatePriceDetail>        templatePriceDetailDao
            = EntityManager.use(BidOrderLineTemplatePriceDetailMapper.class);
    private final EntityManager<BidOrderLineTemplatePriceDetailLine>    templatePriceDetailLineDao
            = EntityManager.use(BidOrderLineTemplatePriceDetailLineMapper.class);

    @Resource
    private IOrderHeadService                       orderHeadService;
    @Resource
    private IBidRequirementLineTemplatePriceService demandLineTemplatePriceService;


    @Override
    public List<BidOrderLineTemplatePriceDetailVO> findDetailsByLineId(Long lineId) {

        // 获取 投标行
        OrderLine orderLine = Optional.ofNullable(lineId)
                .map(orderLineDao::findById)
                .orElseThrow(() -> new BaseException("获取供应商投标行失败。 | lineId: [" + lineId + "]"));

        // 获取 供应商模型报价明细
        List<BidOrderLineTemplatePriceDetailVO> detailVOs = templatePriceDetailDao.findAll(
                Wrappers.lambdaQuery(BidOrderLineTemplatePriceDetail.class)
                        .eq(BidOrderLineTemplatePriceDetail::getLineId, lineId))
                .stream()
                .map(header -> BidOrderLineTemplatePriceDetailVO.builder()
                        .templatePriceDetail(header)
                        .templatePriceDetailLines(
                                templatePriceDetailLineDao.findAll(
                                        Wrappers.lambdaQuery(BidOrderLineTemplatePriceDetailLine.class)
                                                .eq(BidOrderLineTemplatePriceDetailLine::getHeaderId, header.getId()))
                        )
                        .build())
                .collect(Collectors.toList());

        // 若明细为空，则从需求行定义的报价模型中带出默认值
        if (detailVOs.isEmpty())
            return demandLineTemplatePriceService.findDetailsByLineId(orderLine.getRequirementLineId()).stream()
                    .map(vo -> BidOrderLineTemplatePriceDetailVO.builder()
                            .templatePriceDetail(BidOrderLineTemplatePriceDetail.builder()
                                    .lineId(orderLine.getOrderLineId())
                                    .headerId(orderLine.getOrderHeadId())
                                    .description(vo.getTemplatePrice().getDescription())
                                    .build())
                            .templatePriceDetailLines(
                                    vo.getTemplatePriceLines().stream()
                                            .map(line -> BidOrderLineTemplatePriceDetailLine.builder()
                                                    .costType(line.getCostType())
                                                    .costDescription(line.getCostDescription())
                                                    .quantity(line.getQuantity())
                                                    .unit(line.getUnit())
                                                    .taxUnitPrice(line.getTaxUnitPrice())
                                                    .taxKey(line.getTaxKey())
                                                    .taxRate(line.getTaxRate())
                                                    .taxTotalPrice(line.getTaxTotalPrice())
                                                    .remark(line.getRemark())
                                                    .build())
                                            .collect(Collectors.toList())
                            )
                            .build())
                    .collect(Collectors.toList());

        return detailVOs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveDetails(List<BidOrderLineTemplatePriceDetailVO> details) {
        if (details == null || details.isEmpty())
            return;
        details.forEach(detail -> {

            // 存储 供应商投标行[模型报价]明细集
            templatePriceDetailDao.useInterceptor()
                    .beforeCreate(parameter -> parameter.getPrepareCreateEntity().setId(IdGenrator.generate()))
                    .save(detail.getTemplatePriceDetail());
            templatePriceDetailLineDao.useInterceptor()
                    .beforeCreate(parameter -> {
                        BidOrderLineTemplatePriceDetailLine prepareCreateEntity = parameter.getPrepareCreateEntity();
                        prepareCreateEntity.setId(IdGenrator.generate());
                        prepareCreateEntity.setHeaderId(detail.getTemplatePriceDetail().getId());
                    })
                    .beforeUpdate(parameter -> {
                        BidOrderLineTemplatePriceDetailLine prepareUpdateEntity = parameter.getPrepareUpdateEntity();
                        prepareUpdateEntity.setTaxTotalPrice(
                                prepareUpdateEntity.getTaxUnitPrice().multiply(prepareUpdateEntity.getQuantity())
                        );
                        prepareUpdateEntity.setHeaderId(detail.getTemplatePriceDetail().getId());
                        BeanUtils.copyProperties(parameter.getPrepareUpdateEntity(), parameter.getExistEntity());
                    })
                    .save(detail.getTemplatePriceDetailLines());
        });


        // 获取 报价行ID
        Long orderLineId = details.stream().findAny()
                .map(detail -> detail.getTemplatePriceDetail().getLineId())
                .orElseThrow(() -> new BaseException("缺失供应商报价行ID。"));
        //获取 temprice id
        Long tempriceId = details.stream().findAny()
                .map(detail -> detail.getTemplatePriceDetail().getId())
                .orElseThrow(() -> new BaseException("缺失temprice_l ID。"));
        // 计算 报价行的总价
        BigDecimal lineTotalPrice = calculateOrderLineTaxTotalPrice(orderLineId);
        //查询id设置税率
        QueryWrapper<BidOrderLineTemplatePriceDetailLine> wrapper =new QueryWrapper();
        BidOrderLineTemplatePriceDetailLine parameter =templatePriceDetailLineDao.findOne(wrapper.eq("HEADER_ID",tempriceId));
        // 更新 报价行的总价和税率
        orderHeadService.updateOrderLinePrice(orderLineId, lineTotalPrice,parameter.getTaxKey(),parameter.getTaxRate());
    }

    @Override
    public BigDecimal calculateOrderLineTaxTotalPrice(Long lineId) {
        return findDetailsByLineId(lineId).stream()
                .flatMap(vo -> vo.getTemplatePriceDetailLines().stream()
                        .map(BidOrderLineTemplatePriceDetailLine::getTaxTotalPrice))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

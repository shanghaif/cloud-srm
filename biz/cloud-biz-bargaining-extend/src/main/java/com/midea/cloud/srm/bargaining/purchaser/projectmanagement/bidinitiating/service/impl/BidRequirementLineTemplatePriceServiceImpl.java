package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineTemplatePriceLineMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineTemplatePriceMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineTemplatePriceService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLineTemplatePrice;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLineTemplatePriceLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.BidRequirementLineTemplatePriceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implement of {@link IBidRequirementLineTemplatePriceService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class BidRequirementLineTemplatePriceServiceImpl implements IBidRequirementLineTemplatePriceService {

    private final EntityManager<BidRequirementLineTemplatePrice>
            templatePriceDao = EntityManager.use(BidRequirementLineTemplatePriceMapper.class);
    private final EntityManager<BidRequirementLineTemplatePriceLine>
            templatePriceLineDao = EntityManager.use(BidRequirementLineTemplatePriceLineMapper.class);


    @Override
    public List<BidRequirementLineTemplatePriceVO> findDetailsByLineId(Long lineId) {
        Assert.notNull(lineId, "寻源需求行ID不能为空");
        return templatePriceDao.findAll(
                Wrappers.lambdaQuery(BidRequirementLineTemplatePrice.class)
                        .eq(BidRequirementLineTemplatePrice::getLineId, lineId))
                .stream()
                .map(header -> BidRequirementLineTemplatePriceVO.builder()
                        .templatePrice(header)
                        .templatePriceLines(templatePriceLineDao.findAll(
                                Wrappers.lambdaQuery(BidRequirementLineTemplatePriceLine.class)
                                        .eq(BidRequirementLineTemplatePriceLine::getHeaderId, header.getId())))
                        .build()).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveDetails(List<BidRequirementLineTemplatePriceVO> details) {
        if (details == null || details.isEmpty())
            return;
        details.forEach(detail -> {
            templatePriceDao.useInterceptor()
                    .beforeCreate(parameter -> parameter.getPrepareCreateEntity().setId(IdGenrator.generate()))
                    .save(detail.getTemplatePrice());
            templatePriceLineDao.useInterceptor()
                    .beforeCreate(parameter -> {
                        BidRequirementLineTemplatePriceLine prepareCreateEntity = parameter.getPrepareCreateEntity();
                        prepareCreateEntity.setId(IdGenrator.generate());
                        prepareCreateEntity.setHeaderId(detail.getTemplatePrice().getId());
                    })
                    .beforeUpdate(parameter -> {
                        parameter.getPrepareUpdateEntity().setHeaderId(detail.getTemplatePrice().getId());
                        BeanUtils.copyProperties(parameter.getPrepareUpdateEntity(), parameter.getExistEntity());
                    })
                    .save(detail.getTemplatePriceLines());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDetails(Long[] detailIds) {
        if (detailIds == null || detailIds.length == 0)
            return;
        templatePriceLineDao.delete(
                Wrappers.lambdaQuery(BidRequirementLineTemplatePriceLine.class)
                        .in(BidRequirementLineTemplatePriceLine::getHeaderId, (Object[]) detailIds)
        );
        templatePriceDao.delete(detailIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDetailLines(Long[] detailLineIds) {
        if (detailLineIds == null || detailLineIds.length == 0)
            return;
        templatePriceLineDao.delete(detailLineIds);
    }
}

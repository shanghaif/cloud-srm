package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BidException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.mapper.OuRelatePriceMapper;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IOuRelatePriceService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.dto.OuRelatePriceCreateBatchDto;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.dto.OuRelatePriceCreateDto;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.OuRelatePrice;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 15:32
 *  修改内容:
 * </pre>
 */
@Service
public class OuRelatePriceServiceImpl extends ServiceImpl<OuRelatePriceMapper, OuRelatePrice> implements IOuRelatePriceService {
    @Autowired
    private IOrderLineService orderLineService;
    @Autowired
    private IBidRequirementLineService service;

    /**
     * 如果价格关系不为空，则把该关系存起来
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createOuRelatePrice(OuRelatePriceCreateBatchDto dto) {
        List<OuRelatePrice> relatePrices = new ArrayList<>();
        List<OuRelatePriceCreateDto> relateList = dto.getRelateList();
        if (dto.getOrgId() == null && dto.getOuGroupId() == null) {
            throw new BidException("基准组织或ou组id不能为空");
        }
        if (dto.getOrgName() == null && dto.getOuGroupName() == null) {
            throw new BidException("基准组织名或ou组名不能为空");
        }
        for (OuRelatePriceCreateDto createDto : relateList) {
            if (Objects.nonNull(createDto.getPriceDiff())) {
                if (createDto.getOrgId() == null && createDto.getOuId() == null) {
                    throw new BidException("组织或ou组id不能为空");
                }
                if (createDto.getOrgName() == null && createDto.getOuName() == null) {
                    throw new BidException("组织或ou组名不能为空");
                }
                OuRelatePrice relatePrice = BeanCopyUtil.copyProperties(createDto, OuRelatePrice::new);
                relatePrice.setBaseOuName(dto.getOuGroupName());
                if (dto.getOuGroupId() != null) {
                    relatePrice.setBaseOuId(dto.getOuGroupId());
                } else {
                    relatePrice.setOrgId(dto.getOrgId());
                }
                relatePrice.setRequireHeaderId(dto.getRequireHeaderId());
                relatePrice.setBaseOrgId(dto.getOrgId());
                relatePrice.setBaseOrgName(dto.getOrgName());
                relatePrice.setRelateId(IdGenrator.generate());
                relatePrices.add(relatePrice);
            }
        }
        saveBatch(relatePrices);
        return true;
    }

    /**
     * 根据需求头获取价格关系,可以是业务实体id或者是基准ouid
     *
     * @param headerId
     * @param baseOuId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OuRelatePrice> listOuRelatePrices(Long headerId, Long baseOuId) {
        return list(Wrappers.lambdaQuery(OuRelatePrice.class)
                .eq(OuRelatePrice::getRequireHeaderId, headerId)
                .nested(q -> q.eq(OuRelatePrice::getBaseOuId, baseOuId).or().eq(OuRelatePrice::getBaseOrgId, baseOuId)));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePrice(Long id, BigDecimal price) {
        OuRelatePrice p = new OuRelatePrice();
        p.setRelateId(id);
        p.setPriceDiff(price);
        return updateById(p);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(Long relateId) {
        return removeById(relateId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByRequirementLineId(Long requirementLineId) {
        return remove(Wrappers.lambdaQuery(OuRelatePrice.class)
                .eq(OuRelatePrice::getRequirementLineId, requirementLineId));
    }

    /**
     * 根据基准ouid获取其他的价格关系
     *
     * @param baseOuRequireLineId
     * @return
     */
    @Override
    public List<OuRelatePrice> listOuRelatePriceByOrderLine(Long baseOuRequireLineId, BigDecimal currentPrice) {
        BidRequirementLine byId = service.getOne(Wrappers.lambdaQuery(BidRequirementLine.class)
                .eq(BidRequirementLine::getRequirementLineId, baseOuRequireLineId));
        if (Objects.isNull(byId)) {
            throw new BidException("报价关系表中没有该需求行");
        }
        if (YesOrNo.NO.getValue().equals(byId.getBaseOu())) {
            throw new BidException("该行所属ou组不是基准ou组");
        }
        List<OuRelatePrice> relatePrices = listOuRelatePrices(byId.getRequirementId(), byId.getOuId() == null ? byId.getOrgId() : byId.getOuId());
        for (OuRelatePrice relatePrice : relatePrices) {
            relatePrice.setPriceDiff(currentPrice.add(relatePrice.getPriceDiff()));
        }
        return relatePrices;
    }

}

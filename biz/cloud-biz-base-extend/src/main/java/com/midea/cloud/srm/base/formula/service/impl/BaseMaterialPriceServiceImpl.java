package com.midea.cloud.srm.base.formula.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.base.formula.mapper.BaseMaterialMapper;
import com.midea.cloud.srm.base.formula.mapper.BaseMaterialPriceMapper;
import com.midea.cloud.srm.base.formula.service.IBaseMaterialPriceService;
import com.midea.cloud.srm.model.base.formula.dto.create.BaseMaterialPriceCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.BaseMaterialPriceQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.BaseMaterialPriceUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterial;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterialPrice;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialPriceVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 *  基本材料价格表 服务实现类
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:27:13
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
@AllArgsConstructor
public class BaseMaterialPriceServiceImpl extends ServiceImpl<BaseMaterialPriceMapper, BaseMaterialPrice> implements IBaseMaterialPriceService {
    private final BaseMaterialMapper baseMaterialMapper;
    private final DefaultPriceCalculateTemplate calculateTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseMaterialPriceVO createBaseMaterialPrice(BaseMaterialPriceCreateDto dto) {
        //判断数据库中是否有重复的数据,根据编码、有效期、价格类型、数据来源判断
        String baseMaterialCode = dto.getBaseMaterialCode();
        String baseMaterialPriceType = dto.getBaseMaterialPriceType();
        Integer count = count(Wrappers.lambdaQuery(BaseMaterialPrice.class)
                .select(BaseMaterialPrice::getBaseMaterialId)
                .eq(BaseMaterialPrice::getBaseMaterialCode, baseMaterialCode)
                .eq(BaseMaterialPrice::getActiveDateFrom, dto.getActiveDateFrom())
                .eq(BaseMaterialPrice::getActiveDateTo, dto.getActiveDateTo())
                .eq(BaseMaterialPrice::getPriceFrom,dto.getPriceFrom())
                .eq(BaseMaterialPrice::getBaseMaterialPriceStatus,StuffStatus.ACTIVE.getStatus())
                .eq(BaseMaterialPrice::getBaseMaterialPriceType, baseMaterialPriceType)
        );
        if (count == 1) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.BASE_MATERIAL_NOT_EXISTS));
            throw new BaseException(ResultCode.BASE_MATERIAL_PRICE_EXISTS);
        }
        //已经确保这里的数据是通过校验的
        BaseMaterialPrice price = BeanCopyUtil.copyProperties(dto, BaseMaterialPrice::new);
        price.setBaseMaterialId(IdGenrator.generate());
        //判断基材id和基材名是否存在,不存在则查出来
        if (StringUtil.isEmpty(dto.getBaseMaterialName()) || Objects.isNull(dto.getBaseMaterialId())||Objects.isNull(dto.getBaseMaterialUnit())) {
            BaseMaterial baseMaterial = baseMaterialMapper
                    .selectOne(Wrappers.lambdaQuery(BaseMaterial.class)
                            .select(BaseMaterial::getBaseMaterialName, BaseMaterial::getBaseMaterialId,BaseMaterial::getBaseMaterialUnit)
                            .eq(BaseMaterial::getBaseMaterialCode, baseMaterialCode));
            if (Objects.isNull(baseMaterial)) {
                log.error(LogUtil.getCurrentLogInfo(ResultCode.BASE_MATERIAL_NOT_EXISTS));
                throw new BaseException(ResultCode.BASE_MATERIAL_NOT_EXISTS);
            }
            price.setBaseMaterialId(baseMaterial.getBaseMaterialId());
            price.setBaseMaterialCode(baseMaterialCode);
            price.setBaseMaterialUnit(baseMaterial.getBaseMaterialUnit());
        }
        //TODO 币种、单位、税率要从基础配置里面找
        //设置为草稿（新建状态）
        price.setBaseMaterialPriceStatus(StuffStatus.DRAFT.getStatus());
        //处理价格拆分
        calculateTemplate.handle(dto);
        price.setBaseMaterialPriceId(IdGenrator.generate());
        save(price);
        BaseMaterialPriceVO vo=BeanCopyUtil.copyProperties(price, BaseMaterialPriceVO::new);
        return vo;
    }


    @Override
    public Boolean deleteBaseMaterialPriceById(Long id) {
        BaseMaterialPrice res = getOne(Wrappers.lambdaQuery(BaseMaterialPrice.class)
                .select(BaseMaterialPrice::getBaseMaterialPriceStatus)
                .eq(BaseMaterialPrice::getBaseMaterialPriceId, id));
        if (Objects.nonNull(res) && StuffStatus.DRAFT.getStatus().equals(res.getBaseMaterialPriceStatus())) {
            removeById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateStatus(StuffStatus status, Long id) {
        return update(Wrappers.lambdaUpdate(BaseMaterialPrice.class)
                .set(BaseMaterialPrice::getBaseMaterialPriceStatus, status.getStatus())
                .eq(BaseMaterialPrice::getBaseMaterialPriceId, id));
    }

    @Override
    public BaseMaterialPriceVO updateById(BaseMaterialPriceUpdateDto dto) {
        BaseMaterialPrice update = BeanCopyUtil.copyProperties(dto,BaseMaterialPrice::new);
        BaseMaterialPrice byId = getById(dto.getBaseMaterialPriceId());
        updateById(update);
        BaseMaterialPriceVO vo = BeanCopyUtil.copyProperties(byId, BaseMaterialPriceVO::new);
        return vo;
    }


    @Override
    public PageInfo<BaseMaterialPrice> listBaseMaterialPriceByPage(BaseMaterialPriceQueryDto dto) {
        PageUtil.startPage(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<BaseMaterialPrice> queryWrapper = Wrappers.lambdaQuery(BaseMaterialPrice.class);
        //构造基本价格查询参数，主要为基材编码，基材名称，基价状态，基价类型，基材id,有效期
        String baseMaterialCode = dto.getBaseMaterialCode();
        String baseMaterialName = dto.getBaseMaterialName();
        String baseMaterialPriceStatus = dto.getBaseMaterialPriceStatus();
        String baseMaterialPriceType = dto.getBaseMaterialPriceType();
        Long baseMaterialPriceId = dto.getBaseMaterialPriceId();
        Date activeDateFrom = dto.getActiveDateFrom();
        Date activeDateTo = dto.getActiveDateTo();
        queryWrapper.eq(Strings.isNotBlank(baseMaterialCode), BaseMaterialPrice::getBaseMaterialCode, baseMaterialCode)
                .eq(Objects.nonNull(baseMaterialPriceId), BaseMaterialPrice::getBaseMaterialPriceId, baseMaterialPriceId)
                .eq(Strings.isNotBlank(baseMaterialName), BaseMaterialPrice::getBaseMaterialName, baseMaterialName)
                .eq(Strings.isNotBlank(baseMaterialPriceStatus), BaseMaterialPrice::getBaseMaterialPriceStatus, baseMaterialPriceStatus)
                .eq(Strings.isNotBlank(baseMaterialPriceType), BaseMaterialPrice::getBaseMaterialPriceType, baseMaterialPriceType)
                .ge(Objects.nonNull(activeDateFrom), BaseMaterialPrice::getActiveDateFrom, activeDateFrom)
                .le(Objects.nonNull(activeDateTo), BaseMaterialPrice::getActiveDateTo, activeDateTo)
                .ne(BaseMaterialPrice::getBaseMaterialPriceStatus,StuffStatus.ABANDON.getStatus())
                .like(Strings.isNotBlank(dto.getDataSource()), BaseMaterialPrice::getPriceFrom,dto.getDataSource());
        return new PageInfo<>(list(queryWrapper));
    }
}

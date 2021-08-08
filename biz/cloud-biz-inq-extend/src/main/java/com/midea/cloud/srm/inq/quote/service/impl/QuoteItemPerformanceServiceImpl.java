package com.midea.cloud.srm.inq.quote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.enums.inq.ScoreRuleEnum;
import com.midea.cloud.srm.inq.inquiry.service.IHeaderService;
import com.midea.cloud.srm.inq.quote.mapper.QuoteItemPerformanceMapper;
import com.midea.cloud.srm.inq.quote.service.IQuoteItemPerformanceService;
import com.midea.cloud.srm.inq.quote.service.impl.convertor.QuoteItemPerformanceConvertor;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceResponseDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceSaveDTO;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceUpdateDTO;
import com.midea.cloud.srm.model.inq.quote.entity.QuoteItemPerformance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  报价-供应商物料绩效评分表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-27 10:09:27
 *  修改内容:
 * </pre>
*/
@Service
public class QuoteItemPerformanceServiceImpl extends ServiceImpl<QuoteItemPerformanceMapper, QuoteItemPerformance> implements IQuoteItemPerformanceService {

    @Autowired
    private IHeaderService iHeaderService;

    @Override
    public QuoteItemPerformance getDimensionScore(Long inquiryId, Long organizationId, Long vendorId, Long itemId) {

        QueryWrapper<QuoteItemPerformance> wrapper = new QueryWrapper<>();
        wrapper.eq("INQUIRY_ID", inquiryId)
                .eq("ORGANIZATION_ID", organizationId)
                .eq("VENDOR_ID", vendorId)
                .eq("ITEM_ID", itemId);
        return getOne(wrapper);
    }

    @Override
    public List<QuoteItemPerformanceResponseDTO> getQuoteItemPerformance(Long inquiryId) {

        Header header = iHeaderService.getById(inquiryId);
        Assert.notNull(header, "请求参数有误");

        if (!ScoreRuleEnum.COMPREHENSIVE_SCORING_METHOD.toString().equals(header.getInquiryRule())) {
            throw new BaseException("该询价单的评分方式不是综合评分法");
        }

        return QuoteItemPerformanceConvertor.convert(this.baseMapper.getQuoteItemPerformances(inquiryId));
    }

    @Override
    public void saveQuoteItemPerformance(QuoteItemPerformanceSaveDTO request) {

        Header header = iHeaderService.getById(request.getInquiryId());
        Assert.notNull(header, "请求参数有误");
        /*新增绩效评分校验*/
        checkBeforeAdd(request.getQuoteItemPerformances());

        List<QuoteItemPerformance> saveList = new ArrayList<>();
        request.getQuoteItemPerformances().forEach(value -> {
            QuoteItemPerformance entity = new QuoteItemPerformance();
            if(value.getItemPerformanceId() == null) {
                /*新增评分*/
                BeanUtils.copyProperties(value, entity);
                entity.setItemPerformanceId(IdGenrator.generate());
                entity.setInquiryId(header.getInquiryId());
                entity.setInquiryNo(header.getInquiryNo());
            }else {
                /*更新评分*/
                entity.setItemPerformanceId(value.getItemPerformanceId());
                entity.setScore(value.getScore());
            }
            saveList.add(entity);
        });
        saveOrUpdateBatch(saveList);
    }

    @Override
    public void updateSocre(QuoteItemPerformanceUpdateDTO request) {

        Header header = iHeaderService.getById(request.getInquiryId());
        Assert.notNull(header, "请求参数有误");
        /*更新前检查*/
        checkBeforeUpate(request);

        List<QuoteItemPerformance> updateList = new ArrayList<>();
        request.getQuoteItemPerformanceScores().forEach(value -> {
            QuoteItemPerformance entity = new QuoteItemPerformance();
            BeanUtils.copyProperties(value, entity);
            updateList.add(entity);
        });
        updateBatchById(updateList);
    }

    @Override
    public List<QuoteItemPerformance> getDimensionByInquiryNo(String inquiryNo) {

        QueryWrapper<QuoteItemPerformance> wrapper = new QueryWrapper<>();
        wrapper.eq("INQUIRY_NO", inquiryNo);
        return list(wrapper);
    }

    /**
     * 更新前数据检查
     */
    private void checkBeforeUpate(QuoteItemPerformanceUpdateDTO request) {
        List<Long> itemPerformanceIds = new ArrayList<>();
        request.getQuoteItemPerformanceScores().forEach(value -> itemPerformanceIds.add(value.getItemPerformanceId()));
        List<QuoteItemPerformance> inDbs = listByIds(itemPerformanceIds);
        if (CollectionUtils.isEmpty(inDbs)) {
            throw new BaseException("更新的评分列表不存在");
        }

        request.getQuoteItemPerformanceScores().forEach(value -> {
            QuoteItemPerformance quoteItemPerformance =
                    inDbs.stream().filter(f -> f.getItemPerformanceId().equals(value.getItemPerformanceId())).findFirst().get();
            if (value.getScore().doubleValue() > quoteItemPerformance.getFullScore().doubleValue()) {
                throw new BaseException("评分不能大于满分值");
            }
        });
    }

    /**
     * 新增绩效评分校验
     */
    private void checkBeforeAdd(List<QuoteItemPerformanceDTO> quoteItemPerformances) {
        quoteItemPerformances.forEach(value -> {
            if (value.getScore() == null) {
                throw new BaseException("评分不能为空");
            }
            if (value.getScore().doubleValue() > value.getFullScore().doubleValue()) {
                throw new BaseException("评分不能大于满分值");
            }
        });
    }
}

package com.midea.cloud.srm.bid.purchaser.techdiscuss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.bid.techdiscuss.ProjStatus;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.techdiscuss.mapper.TechDiscussProjInfoMapper;
import com.midea.cloud.srm.bid.purchaser.techdiscuss.service.ITechDiscussProjInfoService;
import com.midea.cloud.srm.bid.purchaser.techdiscuss.service.ITechDiscussSupplierService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussProjInfo;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity.TechDiscussSupplier;
import com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.vo.TechDiscussProjInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <pre>
 *  技术交流项目信息表 服务实现类
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
 */
@Service
public class TechDiscussProjInfoServiceImpl extends ServiceImpl<TechDiscussProjInfoMapper, TechDiscussProjInfo> implements ITechDiscussProjInfoService {

    @Autowired
    private BaseClient baseClient;
    @Autowired
    private ITechDiscussSupplierService iTechDiscussSupplierService;

    @Override
    @Transactional
    public void saveOrUpdateProjInfo(TechDiscussProjInfoVO techDiscussProjInfoVO) {
        //验参
        checkSaveOrUpdateParam(techDiscussProjInfoVO);

        TechDiscussProjInfo projInfo = new TechDiscussProjInfo();
        BeanCopyUtil.copyProperties(projInfo, techDiscussProjInfoVO);

        //操作技术交流项目信息表
        if (projInfo.getProjId() == null) {
            Long id = IdGenrator.generate();
            projInfo.setProjId(id);

            projInfo.setProjCode(baseClient.seqGen(SequenceCodeConstant.SEQ_BID_TECH_DISCUSS_PROJECT_CODE)).
                    setStatus(ProjStatus.DRAFT.getValue());
        }
        this.saveOrUpdate(projInfo);

        //操作技术交流供应商表
        List<TechDiscussSupplier> supplierList = techDiscussProjInfoVO.getTechDiscussSupplierList();
        iTechDiscussSupplierService.removeByProjId(projInfo.getProjId());
        if (CollectionUtils.isNotEmpty(supplierList)) {
            for (TechDiscussSupplier supplier : supplierList) {
                Long supplierId = IdGenrator.generate();
                supplier.setSupplierId(supplierId).setProjId(projInfo.getProjId());
            }
            iTechDiscussSupplierService.saveBatch(supplierList);
        }
    }

    @Override
    public PageInfo<TechDiscussProjInfo> listPage(TechDiscussProjInfo techDiscussProjInfo) {
        PageUtil.startPage(techDiscussProjInfo.getPageNum(), techDiscussProjInfo.getPageSize());
        QueryWrapper<TechDiscussProjInfo> wrapper = new QueryWrapper<TechDiscussProjInfo>();
        wrapper.like(StringUtils.isNoneBlank(techDiscussProjInfo.getProjName()),
                "PROJ_NAME", techDiscussProjInfo.getProjName());
        wrapper.like(StringUtils.isNoneBlank(techDiscussProjInfo.getProjCode()),
                "PROJ_CODE", techDiscussProjInfo.getProjCode());
        wrapper.like(StringUtils.isNoneBlank(techDiscussProjInfo.getPublishBy()),
                "PUBLISH_BY", techDiscussProjInfo.getPublishBy());
        wrapper.eq(StringUtils.isNoneBlank(techDiscussProjInfo.getStatus()),
                "STATUS", techDiscussProjInfo.getStatus());
        wrapper.eq(StringUtils.isNoneBlank(techDiscussProjInfo.getProjType()),
                "PROJ_TYPE", techDiscussProjInfo.getProjType());
        return new PageInfo<TechDiscussProjInfo>(this.list(wrapper));
    }

    @Override
    public void publish(Long projId) {
        TechDiscussProjInfo techDiscussProjInfo = new TechDiscussProjInfo();
        techDiscussProjInfo.setProjId(projId).setStatus(ProjStatus.PUBLISHED.getValue());
        this.updateById(techDiscussProjInfo);
    }

    private void checkSaveOrUpdateParam(TechDiscussProjInfoVO techDiscussProjInfoVO) {
        Assert.hasText(techDiscussProjInfoVO.getProjName(), "项目名称不能为空");
        Assert.hasText(techDiscussProjInfoVO.getProjType(), "项目类型不能为空");
        Assert.notNull(techDiscussProjInfoVO.getStopTime(), "截止时间不能为空");
        Assert.hasText(techDiscussProjInfoVO.getResume(), "需求简述不能为空");
        Assert.hasText(techDiscussProjInfoVO.getPublishRange(), "发布范围不能为空");
        Assert.hasText(techDiscussProjInfoVO.getShowPriceEnabled(), "是否需提供参考价格选项不能为空");
    }
}

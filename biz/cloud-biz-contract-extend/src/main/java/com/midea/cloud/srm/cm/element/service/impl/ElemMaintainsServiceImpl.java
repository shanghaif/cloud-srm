package com.midea.cloud.srm.cm.element.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.element.mapper.ElemMaintainMapper;
import com.midea.cloud.srm.cm.element.service.IElemMaintainsService;
import com.midea.cloud.srm.cm.element.service.IElemRangeService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.cm.element.entity.ElemMaintain;
import com.midea.cloud.srm.model.cm.element.entity.ElemRange;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
*  <pre>
 *  合同要素维护表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-12 16:29:02
 *  修改内容:
 * </pre>
*/
@Service
public class ElemMaintainsServiceImpl extends ServiceImpl<ElemMaintainMapper, ElemMaintain> implements IElemMaintainsService {
    @Resource
    private IElemRangeService iElemRangeService;
    @Resource
    private BaseClient baseClient;

    @Override
    @Transactional
    public Long add(ElemMaintain elemMaintain) {
        long elemMaintainId = IdGenrator.generate();
        elemMaintain.setElemMaintainId(elemMaintainId);
        elemMaintain.setElemCode(baseClient.seqGen(SequenceCodeConstant.SEQ_CONTRACT_ELEMENT));
        // 保存头表
        this.save(elemMaintain);

        // 保存行表
        List<ElemRange> elemRanges = elemMaintain.getElemRanges();
        if(CollectionUtils.isNotEmpty(elemRanges)){
            elemRanges.forEach(elemRange -> {
                elemRange.setElemMaintainId(elemMaintainId);
                elemRange.setElemRangeId(IdGenrator.generate());
            });
            iElemRangeService.saveBatch(elemRanges);
        }
        return elemMaintainId;
    }

    @Override
    @Transactional
    public Long modify(ElemMaintain elemMaintain) {
        Assert.notNull(elemMaintain.getElemMaintainId(), "elemMaintainId不能为空");
        Long elemMaintainId = elemMaintain.getElemMaintainId();
        // 更新头表
        this.updateById(elemMaintain);
        // 更新行表
        iElemRangeService.remove(new QueryWrapper<>(new ElemRange().setElemMaintainId(elemMaintainId)));
        List<ElemRange> elemRanges = elemMaintain.getElemRanges();
        if(CollectionUtils.isNotEmpty(elemRanges)){
            elemRanges.forEach(elemRange -> {
                elemRange.setElemMaintainId(elemMaintainId);
                elemRange.setElemRangeId(IdGenrator.generate());
            });
        }
        iElemRangeService.saveBatch(elemRanges);
        return elemMaintainId;
    }

    @Override
    public ElemMaintain get(Long elemMaintainId) {
        Assert.notNull(elemMaintainId, "elemMaintainId不能为空");
        ElemMaintain elemMaintain = this.getById(elemMaintainId);
        if(null != elemMaintain){
            List<ElemRange> elemRangeList = iElemRangeService.list(new QueryWrapper<>(new ElemRange().setElemMaintainId(elemMaintainId)));
            elemMaintain.setElemRanges(elemRangeList);
        }
        return elemMaintain;
    }

    @Override
    public PageInfo<ElemMaintain> listPage(ElemMaintain elemMaintain) {
        PageUtil.startPage(elemMaintain.getPageNum(),elemMaintain.getPageSize());
        QueryWrapper<ElemMaintain> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtil.notEmpty(elemMaintain.getElemName()),"ELEM_NAME",elemMaintain.getElemName());
        queryWrapper.like(StringUtil.notEmpty(elemMaintain.getElemCode()),"ELEM_CODE",elemMaintain.getElemCode());
        queryWrapper.orderByDesc("ELEM_MAINTAIN_ID");
        List<ElemMaintain> elemMaintains = this.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(elemMaintains)){
            elemMaintains.forEach(elemMain->{
                Long elemMaintainId = elemMain.getElemMaintainId();
                List<ElemRange> elemRangeList = iElemRangeService.list(new QueryWrapper<>(new ElemRange().setElemMaintainId(elemMaintainId)));
                elemMain.setElemRanges(elemRangeList);
            });
        }
        return new PageInfo<>(elemMaintains);
    }

    /**
     * 查询有效的
     * @return
     */
    @Override
    public List<ElemMaintain> listAll() {
        QueryWrapper<ElemMaintain> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("END_DATE").or().ge("END_DATE", LocalDate.now());
        return this.list(queryWrapper);
    }
}

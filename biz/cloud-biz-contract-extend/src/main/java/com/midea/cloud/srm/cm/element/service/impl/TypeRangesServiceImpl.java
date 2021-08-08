package com.midea.cloud.srm.cm.element.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.element.mapper.TypeRangeMapper;
import com.midea.cloud.srm.cm.element.service.IElemRangeService;
import com.midea.cloud.srm.cm.element.service.ITypeRangesService;
import com.midea.cloud.srm.model.cm.element.entity.ElemMaintain;
import com.midea.cloud.srm.model.cm.element.entity.ElemRange;
import com.midea.cloud.srm.model.cm.element.entity.TypeRange;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

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
 *  修改日期: 2020-08-12 16:29:03
 *  修改内容:
 * </pre>
*/
@Service
public class TypeRangesServiceImpl extends ServiceImpl<TypeRangeMapper, TypeRange> implements ITypeRangesService {
    @Resource
    private IElemRangeService iElemRangeService;

    @Override
    public PageInfo<TypeRange> listPage(TypeRange typeRange) {
        PageUtil.startPage(typeRange.getPageNum(),typeRange.getPageSize());
        QueryWrapper<TypeRange> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtil.notEmpty(typeRange.getContractType()),"CONTRACT_TYPE",typeRange.getContractType());
        queryWrapper.like(StringUtil.notEmpty(typeRange.getElemName()),"ELEM_NAME",typeRange.getElemName());
        queryWrapper.like(StringUtil.notEmpty(typeRange.getElemCode()),"ELEM_CODE",typeRange.getElemCode());
        queryWrapper.ge(StringUtil.notEmpty(typeRange.getStartDate()),"START_DATE",typeRange.getStartDate());
        queryWrapper.le(StringUtil.notEmpty(typeRange.getStartDateEnd()),"START_DATE",typeRange.getStartDateEnd());
        queryWrapper.le(StringUtil.notEmpty(typeRange.getEndDate()),"END_DATE",typeRange.getEndDate());
        return new PageInfo<>(this.list(queryWrapper));
    }

    /**
     * 查询指定合同类型所有有效的要素
     * @param contractType
     * @return
     */
    @Override
    public List<ElemMaintain> queryByValid(String contractType) {
        List<ElemMaintain> elemMaintains = this.getBaseMapper().queryByValid(contractType);
        if(CollectionUtils.isNotEmpty(elemMaintains)){
            // 把有效的值域找出来
            for(ElemMaintain elemMaintain : elemMaintains){
                QueryWrapper<ElemRange> queryWrapper = new QueryWrapper<>();
                queryWrapper.and(wrapper -> wrapper.isNull("END_DATE").or().ge("END_DATE", LocalDate.now()));
                queryWrapper.eq("ELEM_MAINTAIN_ID",elemMaintain.getElemMaintainId());
                List<ElemRange> list = iElemRangeService.list(queryWrapper);
                elemMaintain.setElemRanges(list);
            }
        }
        return elemMaintains;
    }
}

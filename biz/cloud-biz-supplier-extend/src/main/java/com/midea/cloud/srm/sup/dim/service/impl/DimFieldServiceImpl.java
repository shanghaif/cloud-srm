package com.midea.cloud.srm.sup.dim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.supplier.dim.dto.DimFieldDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.Dim;
import com.midea.cloud.srm.model.supplier.dim.entity.DimField;
import com.midea.cloud.srm.sup.dim.mapper.DimFieldMapper;
import com.midea.cloud.srm.sup.dim.mapper.DimMapper;
import com.midea.cloud.srm.sup.dim.service.IDimFieldService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
*  <pre>
 *  维度字段表 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 15:28:51
 *  修改内容:
 * </pre>
*/
@Service
public class DimFieldServiceImpl extends ServiceImpl<DimFieldMapper, DimField> implements IDimFieldService {

    @Autowired
    private DimFieldMapper dimFieldMapper;

    @Autowired
    private DimMapper dimMapper;

    @Override
    public PageInfo<DimFieldDTO> listPageByParam(DimFieldDTO requestDto) {
        PageUtil.startPage(requestDto.getPageNum(), requestDto.getPageSize());
        return new PageInfo<DimFieldDTO>(dimFieldMapper.listPageByParam(requestDto));
    }

    @Override
    public void saveOrUpdateField(DimField dimField) {
        Assert.notNull(dimField, LocaleHandler.getLocaleMsg("不能传空值"));
        Assert.notNull(dimField.getDimId(), LocaleHandler.getLocaleMsg("维度Id不能为空"));
        Assert.notNull(dimField.getFieldName(), LocaleHandler.getLocaleMsg("属性名称不能为空"));
        Assert.notNull(dimField.getFieldCode(), LocaleHandler.getLocaleMsg("属性编码不能为空"));
        Assert.notNull(dimField.getFieldTypeName(),LocaleHandler.getLocaleMsg("属性类型名称不能为空"));
        Assert.notNull(dimField.getFieldTypeCode(), LocaleHandler.getLocaleMsg("属性类型编码不能为空"));

        QueryWrapper queryWrapper = new QueryWrapper<DimField>();
        queryWrapper.eq("FIELD_CODE",dimField.getFieldCode());
        queryWrapper.ne(dimField.getFieldId()!=null,"FIELD_ID",dimField.getFieldId());

        try{
            DimField checkDim = this.getOne(queryWrapper);
            Assert.isTrue(checkDim==null,"属性编码重复");
        }catch (Exception e){
            Assert.isTrue(false,"属性编码重复");
        }


        if(dimField.getFieldId() != null){
            dimField.setLastUpdateDate(new Date());
        }else{
            Long id = IdGenrator.generate();
            dimField.setFieldId(id);
            dimField.setCreationDate(new Date());
        }

        this.saveOrUpdate(dimField);
    }

    @Override
    public DimFieldDTO get(Long fieldId) {
        DimFieldDTO dimFieldDTO = new DimFieldDTO();
        DimField dimField = dimFieldMapper.selectById(fieldId);
        if (dimField != null) {
            BeanUtils.copyProperties(dimField, dimFieldDTO);
            Dim dim = dimMapper.selectById(dimField.getDimId());
            if (dim != null) {
                dimFieldDTO.setDimCode(dim.getDimCode());
                dimFieldDTO.setDimName(dim.getDimName());
            }
        }
        return dimFieldDTO;
    }
}

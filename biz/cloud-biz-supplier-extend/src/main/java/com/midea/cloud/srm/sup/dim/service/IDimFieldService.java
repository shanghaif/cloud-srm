package com.midea.cloud.srm.sup.dim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.dim.dto.DimFieldDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimField;

/**
*  <pre>
 *  维度字段表 服务类
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
public interface IDimFieldService extends IService<DimField> {

    PageInfo<DimFieldDTO> listPageByParam(DimFieldDTO requestDto);

    void saveOrUpdateField(DimField dimField);

    /**
     * 根据属性ID获取属性DTO对象
     * @param fieldId
     * @return
     */
    DimFieldDTO get(Long fieldId);
}

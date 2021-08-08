package com.midea.cloud.srm.base.formula.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.formula.dto.MaterialAttrFormulaDTO;
import com.midea.cloud.srm.model.base.material.MaterialItemAttributeRelate;

import java.util.List;

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
 *  修改日期: 2020/8/28 14:56
 *  修改内容:
 * </pre>
 */
public interface IMaterialAttributeRelateService extends IService<MaterialItemAttributeRelate> {

    void convertDTO(List<MaterialAttrFormulaDTO> materialAttrFormulaDTOList);

}

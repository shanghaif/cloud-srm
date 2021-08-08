package com.midea.cloud.srm.base.material.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.base.material.dto.MaterialItemAttributeRelateCreateDto;
import com.midea.cloud.srm.model.base.material.dto.MaterialItemAttributeRelateUpdateDto;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeRelateVO;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeVO;
import com.midea.cloud.srm.model.base.quicksearch.param.JsonParam;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  物料主数据属性表 服务类
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:28:22
 *  修改内容:
 * </pre>
 */
public interface IMaterialItemAttributeService extends IService<MaterialItemAttribute> {

    /**
     * 新增属性
     *
     * @param name
     * @return
     */
    MaterialItemAttributeVO createMaterialItemAttribute(String name);

    Map<String, Object> listPageByParam(JsonParam param);

    /**
     * 根据id删除物料主属性
     *
     * @param id
     * @return
     */
    Boolean deleteMaterialItemAttributeById(Long id);

    /**
     * 根据物料id查询关联的主属性信息
     *
     * @param materalId
     * @return
     */
    List<MaterialItemAttributeRelateVO> getMaterialAttributeRelateByMaterialId(Long materalId, boolean filterKeyFuture);

    /**
     * 根据物料ids查询
     * @param materialIds
     * @return
     */
    Map<Long, List<MaterialItemAttributeRelateVO>> getMaterialAttributeRelateByMaterialIds(List<Long> materialIds);

    /**
     * 单个新增关联信息
     *
     * @param dto
     * @return
     */
    MaterialItemAttributeRelateVO createMaterialItemAttributeRelate(MaterialItemAttributeRelateCreateDto dto);

    /**
     * 修改单个关联信息
     *
     * @param dto
     * @return
     */
    MaterialItemAttributeRelateVO updateMaterialItemAttributeRelate(MaterialItemAttributeRelateUpdateDto dto);

    /**
     * 批量插入物料-主属性关联信息
     *
     * @param createDtoList
     * @return
     */
    List<MaterialItemAttributeRelateVO> batchCreateMaterialItemAttributeRelate(List<MaterialItemAttributeRelateCreateDto> createDtoList);

    /**
     * 批量更新物料-主属性关联信息
     *
     * @param updateDtos
     * @return
     */
    List<MaterialItemAttributeRelateVO> batchUpdateMaterialItemAttributeRelate(List<MaterialItemAttributeRelateUpdateDto> updateDtos);

}

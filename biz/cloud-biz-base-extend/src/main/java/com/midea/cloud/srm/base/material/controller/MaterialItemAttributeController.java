package com.midea.cloud.srm.base.material.controller;


import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.controller.BaseCheckController;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.LogUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.formula.service.IMaterialAttributeRelateService;
import com.midea.cloud.srm.base.material.service.IMaterialItemAttributeService;
import com.midea.cloud.srm.feign.bid.SourceFormClient;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.base.material.dto.MaterialItemAttributeRelateCreateDto;
import com.midea.cloud.srm.model.base.material.dto.MaterialItemAttributeRelateUpdateDto;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeRelateVO;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeVO;
import com.midea.cloud.srm.model.base.quicksearch.param.JsonParam;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.inq.price.enums.SourcingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  物料主数据属性表
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
@RestController
@RequestMapping("/base/material-item-attribute")
@Slf4j
public class MaterialItemAttributeController extends BaseCheckController {
    @Autowired
    private IMaterialItemAttributeService attributeService;
    @Autowired
    private IMaterialAttributeRelateService relateService;
    @Autowired
    private SourceFormClient bidFeign;
    @Autowired
    private com.midea.cloud.srm.feign.bargaining.SourceFormClient brgFeign;

    /**
     * 物料主数据属性
     */
    @PostMapping("/listMaterialItemAttribute")
    public PageInfo<MaterialItemAttribute> getMaterialItemAttributeList(@RequestBody BaseEntity entity) {
        PageUtil.startPage(entity.getPageNum(), entity.getPageSize());
        List<MaterialItemAttribute> list = attributeService.list();
        return new PageInfo<>(list);
    }

    /**
     * 分页查询物料属性
     */
    @PostMapping("/listPageMaterialAttribute")
    public Map<String, Object> listPageMaterialAttribute(@RequestBody JsonParam param) {
        if (param.getPageNum() == null) {
            param.setPageNum(1);
        }
        if (param.getPageSize() == null) {
            param.setPageSize(10);
        }
        return attributeService.listPageByParam(param);
    }

    /**
     * 物料主数据属性新建
     */
    @PostMapping("/createMaterialItemAttribute")
    public MaterialItemAttributeVO createMaterialAttribute(@NotEmpty @RequestBody Map<String, String> map) {
        return attributeService.createMaterialItemAttribute(map.get("attributeName"));
    }

    @PostMapping("/updateMaterialItemAttribute")
    public MaterialItemAttributeVO updateMaterialAttribute(@RequestBody MaterialItemAttributeVO vo) {
        if (Objects.isNull(vo.getMaterialAttributeId())) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.MATERIAL_ATTRIBUTE_EMPTY));
            throw new BaseException(ResultCode.MATERIAL_ATTRIBUTE_EMPTY);
        }
        MaterialItemAttribute attribute = new MaterialItemAttribute();
        BeanUtils.copyProperties(vo, attribute);
        attributeService.updateById(attribute);
        return vo;
    }

    /**
     * 物料主数据属性删除
     *
     * @param materialAttributeId
     * @return
     */
    @GetMapping("/deleteMaterialItemAttributeById")
    public Boolean deleteById(@NotNull @RequestParam("id") Long id) {
        return attributeService.deleteMaterialItemAttributeById(id);
    }

/**
 *  ======物料-属性关联接口=========
 */


    /**
     * 根据物料id查询物料-属性关联表数据
     *
     * @param materialId
     * @return
     */
    @GetMapping("/getMaterialAttributeRelate")
    public List<MaterialItemAttributeRelateVO> getMaterialAttributeRelate(@NotNull @RequestParam("materialId") Long materialId,
                                                                          @RequestParam(value = "sourcingType", required = false) String sourcingType, @RequestParam(value = "bidingId", required = false) Long bidingId
    ) {
        List<MaterialItemAttributeRelateVO> materialAttributeRelateByMaterialId = attributeService.getMaterialAttributeRelateByMaterialId(materialId, false);
        if (materialAttributeRelateByMaterialId.size() == 0) {
	        MaterialItemAttributeRelateVO relateVO=new MaterialItemAttributeRelateVO();
	        relateVO.setAttributeValue("");
	        relateVO.setKeyFeature("Y");
	        relateVO.setMaterialAttributeId(-1L);
	        relateVO.setMaterialItemId(materialId);
	        relateVO.setAttributeName("业务实体");
	        materialAttributeRelateByMaterialId.add(relateVO);
	        MaterialItemAttributeRelateVO materialVO=new MaterialItemAttributeRelateVO();
	        materialVO.setAttributeValue("");
	        materialVO.setKeyFeature("Y");
	        materialVO.setMaterialItemId(materialId);
	        materialVO.setMaterialAttributeId(-2L);
	        materialVO.setAttributeName("物料描述");
	        materialAttributeRelateByMaterialId.add(materialVO);
        }
        return materialAttributeRelateByMaterialId;
    }

    /**
     * 根据物料id查询关键属性
     *
     * @param materialId
     * @return
     */
    @GetMapping("/getKeyFeatureMaterialAttribute")
    public List<MaterialItemAttributeRelateVO> getKeyFeatureMaterialAttribute(@RequestParam("materialId") Long materialId) {
        if (Objects.isNull(materialId)) {
            return Collections.EMPTY_LIST;
        }
        return attributeService.getMaterialAttributeRelateByMaterialId(materialId, true);
    }

    /**
     * 根据id分组
     *
     * @param masterialIds
     * @return
     */
    @PostMapping("/getKeyFeatureMaterialAttributes")
    public Map<Long, List<MaterialItemAttributeRelateVO>> getKeyFeatureMaterialAttributes(@RequestBody List<Long> masterialIds) {
        return attributeService.getMaterialAttributeRelateByMaterialIds(masterialIds);
    }

    /**
     * 单个新增
     *
     * @param dto
     * @param result
     * @return
     */
    @PostMapping("/createMaterialAttributeRelate")
    public MaterialItemAttributeRelateVO createMaterialAttributeRelate(@Valid @RequestBody MaterialItemAttributeRelateCreateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return attributeService.createMaterialItemAttributeRelate(dto);
    }

    /**
     * 批量新增物料-属性关联表数据
     *
     * @param createDtoList
     * @param result
     * @return
     */
    @PostMapping("/batchCreateMaterialAttributeRelate")
    public List<MaterialItemAttributeRelateVO> batchCreate(@Valid @RequestBody List<MaterialItemAttributeRelateCreateDto> createDtoList, BindingResult result) {
        checkParamBeforeHandle(log, result, createDtoList);
        return attributeService.batchCreateMaterialItemAttributeRelate(createDtoList);
    }

    @PostMapping("/updateMaterialAttributeRelate")
    public MaterialItemAttributeRelateVO updateMaterialAttributeRelate(@Valid @RequestBody MaterialItemAttributeRelateUpdateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return attributeService.updateMaterialItemAttributeRelate(dto);
    }

    /**
     * 批量修改物料-属性关联表
     */
    @PostMapping("/batchUpdateMaterialAttributeRelate")
    public List<MaterialItemAttributeRelateVO> batchUpdate(@Valid @RequestBody List<MaterialItemAttributeRelateUpdateDto> dtos, BindingResult result) {
        checkParamBeforeHandle(log, result, dtos);
        return attributeService.batchUpdateMaterialItemAttributeRelate(dtos);
    }

    /**
     * 删除物料-属性
     */
    @PostMapping("/batchDeleteMaterialAttributeRelateByIds")
    public Boolean batchDeleteMaterialAttributeRelateByIds(@RequestBody List<Long> ids) {
        return relateService.removeByIds(ids);
    }

    /**
     * 2020-12-28隆基回迁产品 添加物料属性清单详情接口查询
     * @param materialAttributeId
     * @return
     */
    @GetMapping("/getById")
    public MaterialItemAttribute getById(@RequestParam Long materialAttributeId){
        if(Objects.isNull(materialAttributeId)){
            throw new BaseException("缺失必要请求参数【materialAttributeId】");
        }
        return attributeService.getById(materialAttributeId);
    }

}

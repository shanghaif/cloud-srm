package com.midea.cloud.srm.base.formula.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.base.formula.controller.PricingFormulaController;
import com.midea.cloud.srm.base.formula.param.BasePriceParam;
import com.midea.cloud.srm.model.base.formula.dto.calculate.BaseMaterialPriceDTO;
import com.midea.cloud.srm.model.base.formula.dto.create.MaterialFormulaRelateCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.create.PricingFormulaCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.MaterialFormulaRelateQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.query.PricingFormulaQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.PricingFormulaUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaHeader;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.*;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  计价公式头表 服务类
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
public interface IPricingFormulaHeaderService extends IService<PricingFormulaHeader> {
    /**
     * 创建公式
     *
     * @param dto
     * @return
     */
    PricingFormulaDetailVO createPricingFormulate(PricingFormulaCreateDto dto);


    /**
     * 删除公式
     */
    Boolean deleteByHeaderId(Long headerId);

    /**
     * 查询公式id
     *
     * @param headerId
     * @return
     */
    PricingFormulaDetailVO getPriceFormulaDetailById(Long headerId);

    /**
     * 修改公式
     *
     * @param dto
     * @return
     */
    PricingFormulaDetailVO updateFormulateDetailById(PricingFormulaUpdateDto dto);

    /**
     * 更改公式
     *
     * @param status
     * @param id
     * @return
     */
    Boolean updateStatus(StuffStatus status, Long id);

    /**
     * 根据公式id返回要素vo
     *
     * @param formulaId
     * @return
     */
    List<EssentialFactorVO> getEssentialFactorByFormulaId(Long formulaId);

    /**
     * 物料id和公式值
     *
     * @param materialId
     * @param formulaValue
     * @return
     */
    List<PricingFormulaHeaderVO> getPricingFormulaHeaderByMaterialIdAndValue(Long materialId, String formulaValue);

    /**
     * @param materialId
     * @return
     */
    List<PricingFormulaHeaderVO> getPricingFormulaHeaderByMaterialId(Long materialId);

    /**
     * 关联公式和物料id
     */
    Boolean combineMaterialAndFormula(MaterialFormulaRelateCreateDto createDto);

    /**
     * 解除关联
     *
     * @param dto
     * @return
     */
    Boolean divideMaterialAndFormula(MaterialFormulaRelateCreateDto dto);


    PageInfo<PricingFormulaHeader> listPricingFormulaHeaderByPage(PricingFormulaQueryDto dto);

    PageInfo<MaterialFormulaRelateVO> listMaterialFormulaRelateByPage(MaterialFormulaRelateQueryDto dto);

    void importLineModelDownload(List<MaterialItemAttribute> materialAttributeList, HttpServletResponse response) throws Exception;

    Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    List<BaseMaterialPriceTable> getBasePriceByFormulaInfo(BasePriceParam priceParam);

    List<BaseMaterialPriceTable> getPriceTablesByFormulaId(Long formulaId);

    List<BaseMaterialPriceDTO> getBaseMaterialPriceByFormulaId(Long formulaId);

    List<PricingFormulaHeader> listAllFormulaHeader();
}

package com.midea.cloud.srm.base.formula.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.base.formula.mapper.MaterialFormulaRelateMapper;
import com.midea.cloud.srm.base.formula.mapper.PricingFormulaHeaderMapper;
import com.midea.cloud.srm.base.formula.param.BasePriceParam;
import com.midea.cloud.srm.base.formula.service.*;
import com.midea.cloud.srm.base.material.mapper.MaterialItemAttributeMapper;
import com.midea.cloud.srm.base.material.mapper.MaterialItemMapper;
import com.midea.cloud.srm.feign.bid.BidClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.formula.dto.MaterialAttrFormulaDTO;
import com.midea.cloud.srm.model.base.formula.dto.calculate.BaseMaterialPriceDTO;
import com.midea.cloud.srm.model.base.formula.dto.create.MaterialFormulaRelateCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.create.PricingFormulaCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.create.PricingFormulaLineCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.MaterialFormulaRelateQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.query.PricingFormulaQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.PricingFormulaLineUpdateDto;
import com.midea.cloud.srm.model.base.formula.dto.update.PricingFormulaUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.*;
import com.midea.cloud.srm.model.base.formula.enums.EssentialFactorFromType;
import com.midea.cloud.srm.model.base.formula.enums.PricingFormulaLineType;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.*;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialItemAttribute;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.enums.SourcingType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  计价公式头表 服务实现类
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
@Service
@Slf4j
@AllArgsConstructor
public class PricingFormulaHeaderServiceImpl extends ServiceImpl<PricingFormulaHeaderMapper, PricingFormulaHeader> implements IPricingFormulaHeaderService {
    @Resource
    private final FileCenterClient fileCenterClient;

    @Resource
    private final MaterialItemAttributeMapper materialItemAttributeMapper;

    @Autowired
    private final IMaterialAttributeRelateService iMaterialAttributeRelateService;
    @Autowired
    private final BidClient bidClient;
    @Autowired
    private final com.midea.cloud.srm.feign.bargaining.BidClient brgClient;
    @Autowired
    private final IBaseMaterialPriceService priceService;

    private static final List<String> fixedTitle;

    private static final List<String> fixedLineTitle;

    static {
        fixedTitle = new ArrayList<>();
        fixedTitle.addAll(Arrays.asList("*物资编码", "物资描述", "*价格公式", "价格公式明细"));
        fixedLineTitle = new ArrayList<>();
        fixedLineTitle.addAll(Arrays.asList("*计价公式", "*项目"));
    }

    private final IPricingFormulaLineService pricingFormulaLineService;
    private final IEssentialFactorService essentialFactorService;
    private final MaterialFormulaRelateMapper materialFormulaRelateMapper;
    private final MaterialItemMapper materialItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PricingFormulaDetailVO createPricingFormulate(PricingFormulaCreateDto dto) {
        //1.首先创建头行
        /*PricingFormulaHeader p = new PricingFormulaHeader();*/
        PricingFormulaHeader header = BeanCopyUtil.copyProperties(dto, PricingFormulaHeader::new);
        long headerId = IdGenrator.generate();
        header.setPricingFormulaHeaderId(headerId);
        header.setPricingFormulaStatus(StuffStatus.DRAFT.getStatus());
        //2.再插入列行
        List<PricingFormulaLineCreateDto> lineDto = dto.getLineDto();
        List<PricingFormulaLine> lines = lineDto.stream().map(e -> {
            PricingFormulaLine line = new PricingFormulaLine();
            checkEssentialFactorBeforeInsert(e.getPricingFormulaLineType(), e.getEssentialFactorId(), e.getPricingFormulaLineValue());
            line.setPricingFormulaLineId(IdGenrator.generate());
            BeanUtils.copyProperties(e, line);
            line.setPricingFormulaHeaderId(headerId);
            return line;
        }).collect(Collectors.toList());
        save(header);
        pricingFormulaLineService.saveBatch(lines);
        PricingFormulaDetailVO vo = BeanCopyUtil.copyProperties(header, PricingFormulaDetailVO::new);
        return getPricingFormulaDetailVO(vo, lines);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByHeaderId(Long headerId) {
        PricingFormulaHeader source = getById(headerId);
        if (Objects.isNull(source) || !StuffStatus.DRAFT.getStatus().equals(source.getPricingFormulaStatus())) {
            return false;
        }
        removeById(headerId);
        //删除关联的行
        pricingFormulaLineService
                .remove(Wrappers.lambdaQuery(PricingFormulaLine.class)
                        .eq(PricingFormulaLine::getPricingFormulaHeaderId, headerId));
        return true;
    }

    /**
     * 根据id查询公式
     *
     * @param headerId
     * @return
     */
    @Override
    public PricingFormulaDetailVO getPriceFormulaDetailById(Long headerId) {
        PricingFormulaHeader source = getById(headerId);
        PricingFormulaDetailVO detailVO = BeanCopyUtil.copyProperties(source, PricingFormulaDetailVO::new);
        List<PricingFormulaLine> list = getPricingFormulaLines(headerId);
        return getPricingFormulaDetailVO(detailVO, list);
    }

    private List<PricingFormulaLine> getPricingFormulaLines(Long headerId) {
        return pricingFormulaLineService
                .list(Wrappers.lambdaQuery(PricingFormulaLine.class)
                        .eq(PricingFormulaLine::getPricingFormulaHeaderId, headerId));
    }

    /**
     * 批量更新公式行
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PricingFormulaDetailVO updateFormulateDetailById(PricingFormulaUpdateDto dto) {
        PricingFormulaHeader header = BeanCopyUtil.copyProperties(dto, PricingFormulaHeader::new);
        List<PricingFormulaLineUpdateDto> lineDto = dto.getLineDto();
        if (CollectionUtils.isEmpty(lineDto)) {
            lineDto = Collections.EMPTY_LIST;
        }
        //查出数据库已有的id
        List<Long> previousIds = pricingFormulaLineService
                .list(Wrappers.lambdaQuery(PricingFormulaLine.class).select(PricingFormulaLine::getPricingFormulaLineId)
                        .eq(PricingFormulaLine::getPricingFormulaHeaderId, dto.getPricingFormulaHeaderId()))
                .stream().map(PricingFormulaLine::getPricingFormulaLineId).collect(Collectors.toList());
//把数据库里面没有现有id的去掉
        for (int i = previousIds.size() - 1; i >= 0; i--) {
            Long previousId = previousIds.get(i);
            Boolean contain = false;
            for (int j = 0; j < lineDto.size(); j++) {
                PricingFormulaLineUpdateDto e = lineDto.get(j);
                Long currentId = e.getPricingFormulaLineId();
                if (Objects.nonNull(currentId) && previousId.equals(currentId)) {
                    contain = true;
                    break;
                }
            }
            //把无需删除的去掉
            if (contain) {
                previousIds.remove(i);
            }
        }
        if (CollectionUtils.isNotEmpty(previousIds)) {
            pricingFormulaLineService.removeByIds(previousIds);
        }
        //新增
        List<PricingFormulaLine> addList = new ArrayList<>();
        for (int i = lineDto.size() - 1; i >= 0; i--) {
            PricingFormulaLineUpdateDto e = lineDto.get(i);
            if (Objects.isNull(e.getPricingFormulaLineId())) {
                checkEssentialFactorBeforeInsert(e.getPricingFormulaLineType(), e.getEssentialFactorId(), e.getPricingFormulaLineValue());
                PricingFormulaLine line = BeanCopyUtil.copyProperties(e, PricingFormulaLine::new);
                line.setPricingFormulaHeaderId(dto.getPricingFormulaHeaderId());
                line.setPricingFormulaLineId(IdGenrator.generate());
                addList.add(line);
                lineDto.remove(i);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            pricingFormulaLineService.saveBatch(addList);
        }
        //剩下都是要修改的
        if (CollectionUtils.isNotEmpty(lineDto)) {
            List<PricingFormulaLine> lines = lineDto.stream().map(e -> BeanCopyUtil.copyProperties(e, PricingFormulaLine::new)).collect(Collectors.toList());
            pricingFormulaLineService.updateBatchById(lines);
        }
        updateById(header);
        return null;
//        getPriceFormulaDetailById(dto.getPricingFormulaHeaderId());
    }


    @Override
    public Boolean updateStatus(StuffStatus status, Long id) {
        return update(Wrappers.lambdaUpdate(PricingFormulaHeader.class)
                .set(PricingFormulaHeader::getPricingFormulaStatus, status.getStatus())
                .eq(PricingFormulaHeader::getPricingFormulaHeaderId, id)
        );
    }

    @Override
    public List<EssentialFactorVO> getEssentialFactorByFormulaId(Long formulaId) {
        if (Objects.isNull(formulaId)) {
            throw new BaseException("公式id不能为空,请查看该物料是否维护了公式");
        }
        List<PricingFormulaLine> pricingFormulaLines = getPricingFormulaLines(formulaId);
    	 //把属于要素的行信息查出来
        List<Long> factorIds = pricingFormulaLines.stream().filter(e -> PricingFormulaLineType.FIELD.getCode().equals(e.getPricingFormulaLineType()))
                .map(PricingFormulaLine::getEssentialFactorId).collect(Collectors.toList());
        if (null != factorIds && factorIds.size() >0) { 
            List<EssentialFactor> essentialFactors = essentialFactorService.listByIds(factorIds);
            //把要素取值来源为报价且生效得要素查出来
            return essentialFactors.stream().filter(e -> StuffStatus.ACTIVE.getStatus().equals(e.getEssentialFactorStatus())
                    && EssentialFactorFromType.SUPPLIER_QUOTED_PRICE.getCode().equals(e.getEssentialFactorFrom()))
                    .map(e -> BeanCopyUtil.copyProperties(e, EssentialFactorVO::new)).collect(Collectors.toList());
        } else {
        	throw new BaseException("公式设置有误，不存在配置字段，请联系管理员");
        }
       
    }

    @Override
    public List<PricingFormulaHeaderVO> getPricingFormulaHeaderByMaterialIdAndValue(Long materialId, String formulaValue) {
        List<PricingFormulaHeaderVO> list = materialFormulaRelateMapper.selectList(Wrappers.lambdaQuery(MaterialFormulaRelate.class)
                .eq(MaterialFormulaRelate::getMaterialId, materialId)
                .eq(MaterialFormulaRelate::getPricingFormulaValue, formulaValue)
        ).stream().map(e -> BeanCopyUtil.copyProperties(e, PricingFormulaHeaderVO::new)).collect(Collectors.toList());
        return list;
    }

    /**
     * 根据物料id获取公式信息
     * 报价行需要根据物料id选公式
     *
     * @param materialId
     * @return
     */
    @Override
    public List<PricingFormulaHeaderVO> getPricingFormulaHeaderByMaterialId(Long materialId) {
        //
        List<PricingFormulaHeaderVO> list = materialFormulaRelateMapper.selectList(Wrappers.lambdaQuery(MaterialFormulaRelate.class)
                .eq(MaterialFormulaRelate::getMaterialId, materialId)
        ).stream().map(e -> BeanCopyUtil.copyProperties(e, PricingFormulaHeaderVO::new)).collect(Collectors.toList());
        return list;
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public Boolean combineMaterialAndFormula(MaterialFormulaRelateCreateDto dto) {
        Long materialId = dto.getMaterialId();
        Long formulaId = dto.getFormulaId();
        PricingFormulaHeader header = checkBeforeCombineOrDivide(materialId, formulaId);
        //判断是否已经关联了，目前只支持一条公式关联
        Integer count = materialFormulaRelateMapper.selectCount(Wrappers.lambdaQuery(MaterialFormulaRelate.class)
                .eq(MaterialFormulaRelate::getMaterialId, materialId));
///                .eq(MaterialFormulaRelate::getPricingFormulaHeaderId, formulaId));
        if (count >= 1) {
            return false;
        }
        MaterialFormulaRelate relate = BeanCopyUtil.copyProperties(header, MaterialFormulaRelate::new);
        BeanUtils.copyProperties(dto, relate);
        relate.setMaterialId(materialId);
        relate.setRelateId(IdGenrator.generate());
        materialFormulaRelateMapper.insert(relate);
        return true;
    }


    @Override
    public Boolean divideMaterialAndFormula(MaterialFormulaRelateCreateDto dto) {
        Long materialId = dto.getMaterialId();
        Long formulaId = dto.getFormulaId();
        checkBeforeCombineOrDivide(materialId, formulaId);
        materialFormulaRelateMapper.delete(Wrappers.lambdaQuery(MaterialFormulaRelate.class)
                .eq(MaterialFormulaRelate::getMaterialId, materialId));
        //目前支持一个物料对应一条公式
///                .eq(MaterialFormulaRelate::getPricingFormulaHeaderId,formulaId));
        return true;
    }

    @Override
    public PageInfo<PricingFormulaHeader> listPricingFormulaHeaderByPage(PricingFormulaQueryDto dto) {
        PageUtil.startPage(dto.getPageNum(), dto.getPageSize());
        String desc = dto.getPricingFormulaDesc();
        String name = dto.getPricingFormulaName();
        String status = dto.getPricingFormulaStatus();
        List<PricingFormulaHeader> list = list(Wrappers.lambdaQuery(PricingFormulaHeader.class).eq(
                Objects.nonNull(status), PricingFormulaHeader::getPricingFormulaStatus, status)
                .likeRight(Objects.nonNull(name), PricingFormulaHeader::getPricingFormulaName, name)
                .likeRight(Objects.nonNull(desc), PricingFormulaHeader::getPricingFormulaDesc, desc));
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<MaterialFormulaRelateVO> listMaterialFormulaRelateByPage(MaterialFormulaRelateQueryDto dto) {
        PageUtil.startPage(dto.getPageNum(), dto.getPageSize());
        List<MaterialFormulaRelateVO> relates = materialFormulaRelateMapper.getMaterialFormulaRelateMapper(dto);
        return new PageInfo<>(relates);
    }

    /**
     * 下载计价公式头表导入模板
     *
     * @param materialAttributeList
     * @param response
     * @throws Exception
     */
    @Override
    public void importLineModelDownload(List<MaterialItemAttribute> materialAttributeList, HttpServletResponse response) throws Exception {
        // 构建表格
        Workbook workbook = crateWorkbookModel(materialAttributeList);
        // 获取输出流
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "物料计划导入模板");
        // 导出
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查上传文件
        EasyExcelUtil.checkParam(file, fileupload);
        // 获取文件输入流
        InputStream inputStream = file.getInputStream();
        // 导入文件是否有错误信息标志
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 导入的数据
        Map<Integer, List<MaterialAttrFormulaDTO>> materialAttrFormulaDTOMap = new HashMap<>();
        // 每行报错信息
        List<String> errorList = new ArrayList<>();
        // 返回信息
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message", "success");
        // 获取导入数据
        Workbook workbook = this.getImportData(inputStream, materialAttrFormulaDTOMap, errorList, errorFlag);
        if (errorFlag.get()) {
            // 有报错,上传报错文件
            this.uploadErrorFile(file, fileupload, errorList, result, workbook);
        } else {
            if (!materialAttrFormulaDTOMap.isEmpty()) {
                for (Integer id : materialAttrFormulaDTOMap.keySet()) {
                    List<MaterialAttrFormulaDTO> materialAttrFormulaDTOList = materialAttrFormulaDTOMap.get(id);
                    if (CollectionUtils.isNotEmpty(materialAttrFormulaDTOList)) {
                        iMaterialAttributeRelateService.convertDTO(materialAttrFormulaDTOList);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<BaseMaterialPriceTable> getBasePriceByFormulaInfo(BasePriceParam priceParam) {
        if (Objects.equals(priceParam.getSourcingType(), SourcingType.RFQ.getItemValue())) {
            com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidVendor bidVendorInfo = brgClient.getBidVendorInfo(priceParam.getVendorId(), priceParam.getSourcingId());
            if (Objects.nonNull(bidVendorInfo)) {
                if (StringUtils.isNotBlank(bidVendorInfo.getChooseBaseMaterialPrice())) {
                    return JSON.parseArray(bidVendorInfo.getChooseBaseMaterialPrice(), BaseMaterialPriceTable.class);
                }
                if (Objects.equals(bidVendorInfo.getIsSeaFoodPrice(), YesOrNo.YES.getValue())) {
                    String priceJSON = bidVendorInfo.getPriceJSON();
                    List<BaseMaterialPriceDTO> priceDTOS = JSON.parseArray(priceJSON, BaseMaterialPriceDTO.class);
                    return priceDTOS.stream().map(e -> {
                        BaseMaterialPriceTable priceTable = new BaseMaterialPriceTable();
                        priceTable.setBaseMaterialId(e.getBaseMaterialId());
                        priceTable.setBaseMaterialName(e.getBaseMaterialName());
                        priceTable.setChange("Y");
                        BaseMaterialPriceVO baseMaterialPriceVO = new BaseMaterialPriceVO();
                        baseMaterialPriceVO.setPriceFrom("采购员维护");
                        baseMaterialPriceVO.setBaseMaterialPrice(e.getBaseMaterialPrice());
                        baseMaterialPriceVO.setEssentialFactorId(e.getEssentialFactorId());
                        priceTable.setPrices(Collections.singletonList(baseMaterialPriceVO));
                        return priceTable;
                    }).collect(Collectors.toList());

                }
            }
        }
        if (Objects.equals(priceParam.getSourcingType(), SourcingType.TENDER.getItemValue())) {
            BidVendor bidVendor = bidClient.getBidVendorInfo(priceParam.getVendorId(), priceParam.getSourcingId());
            if (Objects.equals(bidVendor.getIsSeaFoodPrice(), YesOrNo.YES.getValue())&&StringUtils.isNotBlank(bidVendor.getPriceJSON())) {
                String priceJSON = bidVendor.getPriceJSON();
                List<BaseMaterialPriceDTO> priceDTOS = JSON.parseArray(priceJSON, BaseMaterialPriceDTO.class);
                return priceDTOS.stream().map(e -> {
                    BaseMaterialPriceTable priceTable = new BaseMaterialPriceTable();
                    priceTable.setBaseMaterialId(e.getBaseMaterialId());
                    priceTable.setBaseMaterialName(e.getBaseMaterialName());
                    priceTable.setChange("Y");
                    BaseMaterialPriceVO baseMaterialPriceVO = new BaseMaterialPriceVO();
                    baseMaterialPriceVO.setPriceFrom("采购员维护");
                    baseMaterialPriceVO.setEssentialFactorId(e.getEssentialFactorId());
                    baseMaterialPriceVO.setBaseMaterialPrice(e.getBaseMaterialPrice());
                    priceTable.setPrices(Collections.singletonList(baseMaterialPriceVO));
                    return priceTable;
                }).collect(Collectors.toList());

            }
            if (Objects.nonNull(bidVendor) && StringUtils.isNotBlank(bidVendor.getChooseBaseMaterialPrice())) {
                return JSON.parseArray(bidVendor.getChooseBaseMaterialPrice(), BaseMaterialPriceTable.class);
            }
        }
        List<BaseMaterialPriceTable> result = getPriceTablesByFormulaId(priceParam.getFormulaId());
        return result;
    }


    @Override
    public List<BaseMaterialPriceTable> getPriceTablesByFormulaId(Long formulaId) {
        List<PricingFormulaLine> pricingFormulaLines = getPricingFormulaLines(formulaId);
        //获取要素id集合
        List<Long> factorIds = pricingFormulaLines.stream().filter(e -> PricingFormulaLineType.FIELD.getCode().equals(e.getPricingFormulaLineType()))
                .map(PricingFormulaLine::getEssentialFactorId).collect(Collectors.toList());
        //展示可选报表结果
        List<BaseMaterialPriceTable> result = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(factorIds)) {
            //获取要素集合
            List<EssentialFactor> essentialFactors = essentialFactorService.listByIds(factorIds);
            if (CollectionUtils.isNotEmpty(essentialFactors)) {
                //同一个公式下，对应的物料要素id只有一个
                Map<Long, EssentialFactor> factorMap = essentialFactors.stream().filter(e -> StuffStatus.ACTIVE.getStatus().equals(e.getEssentialFactorStatus())
                        && EssentialFactorFromType.BASE_MATERIAL_PRICE.getCode().equals(e.getEssentialFactorFrom()))
                        .collect(Collectors.toMap(EssentialFactor::getBaseMaterialId, Function.identity(), (x, y) -> x));
                if (CollectionUtils.isNotEmpty(factorMap)) {
                    //根据基材id查找基价集合
                    LocalDate today = LocalDate.now();
                    Map<Long, List<BaseMaterialPrice>> collect = priceService.list(Wrappers.lambdaQuery(BaseMaterialPrice.class)
                            .in(BaseMaterialPrice::getBaseMaterialId, factorMap.keySet())
                            .eq(BaseMaterialPrice::getBaseMaterialPriceStatus, StuffStatus.ACTIVE.getStatus())
                            .le(BaseMaterialPrice::getActiveDateFrom, today)
                            .ge(BaseMaterialPrice::getActiveDateTo, today)
                    ).stream().collect(Collectors.groupingBy(BaseMaterialPrice::getBaseMaterialId));

                    for (Map.Entry<Long, List<BaseMaterialPrice>> entry : collect.entrySet()) {
                        List<BaseMaterialPrice> value = entry.getValue();
                        BaseMaterialPriceTable table = new BaseMaterialPriceTable();
                        Long baseMaterialId = value.get(0).getBaseMaterialId();
                        String baseMaterialName = value.get(0).getBaseMaterialName();
                        List<BaseMaterialPriceVO> prices = value.stream().map(e ->
                        {
                            BaseMaterialPriceVO vo = BeanCopyUtil.copyProperties(e, BaseMaterialPriceVO::new);
                            EssentialFactor factor = factorMap.get(entry.getKey());
                            vo.setEssentialFactorId(factor.getEssentialFactorId());
                            return vo;
                        }).collect(Collectors.toList());
                        table.setPrices(prices);
                        table.setBaseMaterialName(baseMaterialName);
                        table.setBaseMaterialId(baseMaterialId);

                        result.add(table);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 根据公式id获取基价信息
     *
     * @param formulaId
     * @return
     */
    @Override
    public List<BaseMaterialPriceDTO> getBaseMaterialPriceByFormulaId(Long formulaId) {
        List<PricingFormulaLine> pricingFormulaLines = pricingFormulaLineService.list(Wrappers.lambdaQuery(PricingFormulaLine.class)
                .select(PricingFormulaLine::getEssentialFactorId, PricingFormulaLine::getPricingFormulaLineType)
                .eq(PricingFormulaLine::getPricingFormulaHeaderId, formulaId)
        );
        //获取
        List<Long> factorIds = pricingFormulaLines.stream().filter(e -> PricingFormulaLineType.FIELD.getCode().equals(e.getPricingFormulaLineType()))
                .map(PricingFormulaLine::getEssentialFactorId).collect(Collectors.toList());
        
        List<BaseMaterialPriceDTO> result = null;
        if (null != factorIds && factorIds.size() > 0) {
        	result = essentialFactorService.listByIds(factorIds)
                    .stream()
                    .filter(factor -> Objects.equals(factor.getEssentialFactorStatus(), StuffStatus.ACTIVE.getStatus())
                            && Objects.equals(factor.getEssentialFactorFrom(), EssentialFactorFromType.BASE_MATERIAL_PRICE.getCode())
                    )
                    .map(e -> {
                        BaseMaterialPriceDTO dto = new BaseMaterialPriceDTO();
                        dto.setBaseMaterialId(e.getBaseMaterialId());
                        dto.setBaseMaterialName(e.getBaseMaterialName());
                        dto.setEssentialFactorId(e.getEssentialFactorId());
                        dto.setFormulaId(formulaId);
                        return dto;
                    }).collect(Collectors.toList());
        } else {
        	throw new BaseException("请先配置公式定义->非常量要素！");
        }
        
        return result;
    }

    /**
     * 获取导入的数据, 并对数据进行校验
     *
     * @param inputStream
     * @param errorList
     * @throws IOException
     * @throws ParseException
     */
    private Workbook getImportData(InputStream inputStream, Map<Integer, List<MaterialAttrFormulaDTO>> materialAttrFormulaDTOMap, List<String> errorList, AtomicBoolean errorFlag) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        // 获取标题行数据
        List<String> head = new ArrayList<>();
        HashMap<Integer, String> headMap = new HashMap<>();
        QueryWrapper<MaterialItemAttribute> wrapper = new QueryWrapper<>();
        wrapper.select("MATERIAL_ATTRIBUTE_ID,ATTRIBUTE_CODE,ATTRIBUTE_NAME");
        List<MaterialItemAttribute> MaterialItemAttributeList = materialItemAttributeMapper.selectList(wrapper);
        Map<String, MaterialItemAttribute> MaterialItemAttributeMap = MaterialItemAttributeList.stream().collect(Collectors.toMap(MaterialItemAttribute::getAttributeName, Function.identity()));
        Map<Integer, MaterialItemAttribute> MaterialItemAttributeMaps = new HashMap<>();


        //获取首行
        Row headRow0 = sheet.getRow(0);
        // 遍历每个单元格的值
        for (int i = 4; i < totalCells; i++) {
            Cell cell = headRow0.getCell(i);
            headMap.put(i, ExcelUtil.getCellValue(cell));
        }
        StringBuffer errorMsg1 = new StringBuffer();
        // 获取第二行
        Row headRow = sheet.getRow(1);
        // 遍历每个单元格的值
        for (int i = 0; i < totalCells; i++) {
            Cell cell = headRow.getCell(i);
            head.add(ExcelUtil.getCellValue(cell));
            MaterialItemAttribute materialItemAttribute = MaterialItemAttributeMap.get(ExcelUtil.getCellValue(cell));
            if (i > 3) {
                if (materialItemAttribute == null) {
                    errorFlag.set(true);
                    errorMsg1.append("找不到对应的属性名称(" + ExcelUtil.getCellValue(cell) + ")！请先维护对应的属性名称。");
                }
            }
            MaterialItemAttributeMaps.put(i, materialItemAttribute);
        }
        errorList.add(errorMsg1.toString());
        // 遍历内容行获取数据,从2行开始,也就是行的下标为1
        for (int r = 2; r <= totalRows; r++) {
            Row row = sheet.getRow(r);
            if (null == row) {
                // 过滤空行,空行一下内容全部上移一行
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows--;
                continue;
            }

            // 过滤空行, 当前行每个单元格的值都为空时, 当前行一下行全部往上移一行
            int count = 0;
            for (int i = 0; i < totalCells; i++) {
                // 获取当前单元格
                Cell cell = row.getCell(i);
                // 调用方法获取数值
                String cellValue = ExcelUtil.getCellValue(cell);
                if (null == cellValue || "".equals(cellValue)) {
                    count++;
                }
            }
            if (count == totalCells) {
                if (r + 1 > totalRows) {
                    break;
                }
                sheet.shiftRows(r + 1, totalRows, -1);
                r--;
                totalRows--;
                continue;

            }
// <------------------------------------一下正式开始获取表格内容-------------------------------------------->
            // 收集每行错误信息
            StringBuffer errorMsg = new StringBuffer();
            // 开始遍历行的单元格值
            ArrayList<MaterialAttrFormulaDTO> materialAttrFormualDTOList = new ArrayList<>();
            StringBuffer uniKey = new StringBuffer();


            // 获取物资编码
            Cell cell0 = row.getCell(0);
            String materialCode = ExcelUtil.getCellValue(cell0);
            if (StringUtils.isNotEmpty(materialCode)) {
                materialCode = materialCode.trim();
                uniKey.append(materialCode);
            } else {
                errorFlag.set(true);
                errorMsg.append("物资编码不能为空; ");
            }

            // 检查物资描述
            Cell cell1 = row.getCell(1);
            String materialDescr = ExcelUtil.getCellValue(cell1);
            if (StringUtils.isNotEmpty(materialDescr)) {
                materialDescr = materialDescr.trim();
                uniKey.append(materialDescr);
            }

            // 价格公式
            Cell cell2 = row.getCell(2);
            String pricingFormulaName = ExcelUtil.getCellValue(cell2);
            if (StringUtils.isNotEmpty(pricingFormulaName)) {
                pricingFormulaName = pricingFormulaName.trim();
                uniKey.append(pricingFormulaName);
            } else {
                errorFlag.set(true);
                errorMsg.append("交货地点不能为空; ");
            }

            // 价格公式明细
            Cell cell3 = row.getCell(3);
            String pricingFormulaValue = ExcelUtil.getCellValue(cell3);
            if (StringUtils.isNotEmpty(pricingFormulaValue)) {
                pricingFormulaValue = pricingFormulaValue.trim();
                uniKey.append(pricingFormulaValue);
            }


            // 遍历获取需求数量
            for (int c = 4; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = ExcelUtil.getCellValue(cell);
                if (StringUtils.isNotEmpty(cellValue)) {
                    cellValue = cellValue.trim();
                    uniKey.append(cellValue);
                    MaterialAttrFormulaDTO materialAttrFormualDTO = new MaterialAttrFormulaDTO();
                    materialAttrFormualDTO.setMaterialCode(materialCode);
                    materialAttrFormualDTO.setMaterialDescr(materialDescr);
                    materialAttrFormualDTO.setPricingFormulaName(pricingFormulaName);
                    materialAttrFormualDTO.setPricingFormulaValue(pricingFormulaValue);
                    materialAttrFormualDTO.setAttributeName(MaterialItemAttributeMaps.get(c) == null ? null : MaterialItemAttributeMaps.get(c).getAttributeName());
                    materialAttrFormualDTO.setAttributeCode(MaterialItemAttributeMaps.get(c) == null ? null : MaterialItemAttributeMaps.get(c).getAttributeCode());
                    materialAttrFormualDTO.setKeyFeature(headMap.get(c));
                    materialAttrFormualDTO.setAttributeValue(cellValue);
                    materialAttrFormualDTOList.add(materialAttrFormualDTO);
                } else {
                    errorFlag.set(true);
                    errorMsg.append("属性值不能为空！");
                }
            }
            materialAttrFormulaDTOMap.put(r, materialAttrFormualDTOList);
            errorList.add(errorMsg.toString());
        }
        return workbook;
    }

    private void uploadErrorFile(MultipartFile file, Fileupload fileupload, List<String> errorList, Map<String, Object> result, Workbook workbook) {
        // 获取指定工作表
        Sheet sheet = workbook.getSheetAt(0);

        // 获取最后一行的行号(0开始)
        int totalRows = sheet.getLastRowNum();
        // 获取每行的列数, 获取内容行的格数
        int totalCells = sheet.getRow(0).getLastCellNum();
        sheet.setColumnWidth(totalCells, sheet.getColumnWidth(totalCells) * 17 / 5);

        // 设置"错误信息"标题
        this.setErrorTitle(workbook, sheet, totalCells);
        for (int i = 1; i <= totalRows; i++) {
            Cell cell = sheet.getRow(i).createCell(totalCells);
            cell.setCellValue(errorList.get(i - 1));
        }
        Fileupload fileupload1 = ExcelUtil.uploadErrorFile(fileCenterClient, fileupload, workbook, file);
        result.put("status", YesOrNo.NO.getValue());
        result.put("message", "error");
        result.put("fileuploadId", fileupload1.getFileuploadId());
        result.put("fileName", fileupload1.getFileSourceName());
    }

    private void setErrorTitle(Workbook workbook, Sheet sheet, int totalCells) {
        Row row0 = sheet.getRow(0);
        // 创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        Cell cell1 = row0.createCell(totalCells);
        cell1.setCellValue("错误信息");
        cell1.setCellStyle(cellStyle);
    }

    /**
     * 构建导入模板
     */
    public Workbook crateWorkbookModel(List<MaterialItemAttribute> materialAttributeList) throws ParseException {
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表:自定义导入
        XSSFSheet sheet = workbook.createSheet("sheet");
        // 创建单元格样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        // 创建标题行
        //第一行
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell0 = row0.createCell(0);
        cell0.setCellValue("是否关键属性标识：Y是，N否");
        //第二行
        XSSFRow row = sheet.createRow(1);

        // 当前单元格下标
        int cellIndex = 0;

        // 设置固定标题列
        for (int i = 0; i < fixedTitle.size(); i++) {
            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellValue(fixedTitle.get(i));
            cell1.setCellStyle(cellStyle);
            cellIndex++;
        }

        // 设置时间动态列

        List<String> dayBetween = materialAttributeList.stream().map(MaterialItemAttribute::getAttributeName).collect(Collectors.toList());
        for (int i = 0; i < dayBetween.size(); i++) {
            Cell cell1 = row.createCell(cellIndex);
            cell1.setCellValue(dayBetween.get(i));
            cell1.setCellStyle(cellStyle);
            cellIndex++;
        }
        return workbook;
    }

    /**
     * 主要是把行信息转化为vo对象
     *
     * @param detailVO
     * @param list
     * @return
     */
    private PricingFormulaDetailVO getPricingFormulaDetailVO(PricingFormulaDetailVO detailVO, List<PricingFormulaLine> list) {
        List<PricingFormulaLineVO> pricingFormulaLineVOS = list.stream().map(e -> BeanCopyUtil.copyProperties(e, PricingFormulaLineVO::new)).collect(Collectors.toList());
        detailVO.setLineDto(pricingFormulaLineVOS);
        return detailVO;
    }

    /**
     * 如果来源为字段，校验要素
     *
     * @param pricingFormulaLineType
     * @param essentialFactorId
     * @param pricingFormulaLineValue
     */
    private void checkEssentialFactorBeforeInsert(String pricingFormulaLineType, Long essentialFactorId, String pricingFormulaLineValue) {
        if (PricingFormulaLineType.FIELD.getCode().equals(pricingFormulaLineType)) {
            //要素id为空
            if (Objects.isNull(essentialFactorId)) {
                String message = String.format("字段:%s的要素id为空", pricingFormulaLineValue);
                log.error(LogUtil.getCurrentLogInfo(message));
                throw new BaseException(ResultCode.FORMULA_LINE_FACTOR_EMPTY.getCode(), message);
            }
            //要素id状态为生效才可以用
            EssentialFactor factor = essentialFactorService.getOne(Wrappers.lambdaQuery(EssentialFactor.class)
                    .select(EssentialFactor::getEssentialFactorStatus).eq(EssentialFactor::getEssentialFactorId, essentialFactorId));
            String currentStatus = factor.getEssentialFactorStatus();
            if (!StuffStatus.ACTIVE.getStatus().equals(currentStatus)) {
                String message = String.format("当前要素%s的状态为%s,%s", factor.getEssentialFactorName(), StuffStatus.getValueByStatus(currentStatus), ResultCode.STATUS_ERROR.getMessage());
                log.error(message);
                throw new BaseException(message);
            }
        }
    }

    private PricingFormulaHeader checkBeforeCombineOrDivide(Long materialId, Long formulaId) {
        Integer materialCount = materialItemMapper.selectCount(Wrappers.lambdaQuery(MaterialItem.class)
                .eq(MaterialItem::getMaterialId, materialId));
        if (materialCount == 0) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.MATERIAL_ITEM_NOT_EXISTS.getMessage()));
            throw new BaseException(ResultCode.MATERIAL_ITEM_NOT_EXISTS);
        }
        PricingFormulaHeader header = getOne(Wrappers.lambdaQuery(PricingFormulaHeader.class)
                .select(PricingFormulaHeader::getPricingFormulaHeaderId, PricingFormulaHeader::getPricingFormulaName
                        , PricingFormulaHeader::getPricingFormulaValue, PricingFormulaHeader::getPricingFormulaDesc, PricingFormulaHeader::getPricingFormulaStatus)
                .eq(PricingFormulaHeader::getPricingFormulaHeaderId, formulaId)
                .eq(PricingFormulaHeader::getPricingFormulaStatus, StuffStatus.ACTIVE.getStatus()));
        if (Objects.isNull(header)) {
            log.error(LogUtil.getCurrentLogInfo(ResultCode.FORMULA_STATUS_ERROR_OR_NOT_EXISTS));
            throw new BaseException(ResultCode.FORMULA_STATUS_ERROR_OR_NOT_EXISTS);
        }
        return header;
    }

    @Override
    public List<PricingFormulaHeader> listAllFormulaHeader() {
        return list(Wrappers.lambdaQuery(PricingFormulaHeader.class).eq(
                PricingFormulaHeader::getPricingFormulaStatus, StuffStatus.ACTIVE.getStatus()));
    }
}

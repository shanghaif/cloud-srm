package com.midea.cloud.srm.inq.price.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.inq.price.mapper.PriceLibraryMapper;
import com.midea.cloud.srm.inq.price.service.IPriceLibraryPaymentTermService;
import com.midea.cloud.srm.inq.price.service.IPriceLibraryService;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaParamDto;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.price.domain.PriceLibraryAddParam;
import com.midea.cloud.srm.model.inq.price.domain.PriceLibraryLadderPriceAddParam;
import com.midea.cloud.srm.model.inq.price.dto.*;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.price.vo.PriceLibraryExcelVO;
import com.midea.cloud.srm.model.inq.price.vo.PriceLibraryVO;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.BidFrequency;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  报价-价格目录表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/13 15:11
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/price/priceLibrary")
public class PriceLibraryController extends BaseController {

    @Autowired
    private IPriceLibraryService iPriceLibraryService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private IPriceLibraryPaymentTermService iPriceLibraryPaymentTermService;
    @Resource
    private PriceLibraryMapper priceLibraryMapper;

    /**
     * 根据供应商_物料 检查是否存在有效价格
     *
     * @param priceLibraryParamDto
     * @return
     */
    @PostMapping("/queryValidByVendorItem")
    public boolean queryValidByVendorItem(@RequestBody PriceLibraryParamDto priceLibraryParamDto) {
        boolean flag = false;
        List<PriceLibrary> priceLibraries = iPriceLibraryService.list(Wrappers.lambdaQuery(PriceLibrary.class).
                eq(PriceLibrary::getVendorId, priceLibraryParamDto.getVendorId()).
                eq(PriceLibrary::getItemId, priceLibraryParamDto.getItemId()).
                eq(PriceLibrary::getCeeaOrgId, priceLibraryParamDto.getOrgId()).
                ge(PriceLibrary::getExpirationDate, LocalDate.now()).
                le(PriceLibrary::getEffectiveDate, LocalDate.now()));
        if (CollectionUtils.isNotEmpty(priceLibraries)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据供应商_物料_日期 检查是否存在有效价格
     * @param priceLibraryParamDto
     * @return
     */
    @PostMapping("/queryValidByVendorItemDate")
    public boolean queryValidByVendorItemDate(@RequestBody PriceLibraryParamDto priceLibraryParamDto){
        boolean flag = false;
        List<PriceLibrary> priceLibraries = iPriceLibraryService.list(Wrappers.lambdaQuery(PriceLibrary.class).
                eq(PriceLibrary::getVendorId, priceLibraryParamDto.getVendorId()).
                eq(PriceLibrary::getItemId, priceLibraryParamDto.getItemId()).
                eq(PriceLibrary::getCeeaOrgId, priceLibraryParamDto.getOrgId()).
                ge(PriceLibrary::getExpirationDate, priceLibraryParamDto.getRequirementDate()).
                le(PriceLibrary::getEffectiveDate, priceLibraryParamDto.getRequirementDate()));
        if (CollectionUtils.isNotEmpty(priceLibraries)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据批量供应商_物料_组织 检查是否存在有效价格
     *
     * @param quotaParamDto
     * @return
     */
    @PostMapping("/queryValidByBatchVendorItem")
    public boolean queryValidByBatchVendorItem(@RequestBody QuotaParamDto quotaParamDto) {
        boolean flag = false;
        if (CollectionUtils.isNotEmpty(quotaParamDto.getVendorIds()) &&
                null != quotaParamDto.getOrgId() && null != quotaParamDto.getItemId()) {
            List<PriceLibrary> priceLibraries = iPriceLibraryService.list(Wrappers.lambdaQuery(PriceLibrary.class).
                    in(PriceLibrary::getVendorId, quotaParamDto.getVendorIds()).
                    eq(PriceLibrary::getItemId, quotaParamDto.getItemId()).
                    eq(PriceLibrary::getCeeaOrgId, quotaParamDto.getOrgId()).
                    ge(PriceLibrary::getExpirationDate, null != quotaParamDto.getRequirementDate()?quotaParamDto.getRequirementDate():LocalDate.now()).
                    le(PriceLibrary::getEffectiveDate, null != quotaParamDto.getRequirementDate()?quotaParamDto.getRequirementDate():LocalDate.now()));
            if (CollectionUtils.isNotEmpty(priceLibraries) && priceLibraries.size() >= quotaParamDto.getVendorIds().size()) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 分页查询
     *
     * @param priceLibrary
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<PriceLibraryVO> listPage(@RequestBody PriceLibrary priceLibrary) {
        Assert.notNull(priceLibrary, "查询条件不能为空。");
        return iPriceLibraryService.PriceLibraryListPage(priceLibrary);
    }

    /**
     * 合同查找价格目录
     *
     * @param priceLibraryParam
     * @return
     */
    @PostMapping("/queryByContract")
    public List<PriceLibrary> queryByContract(@RequestBody PriceLibraryParam priceLibraryParam) {
        return iPriceLibraryService.queryByContract(priceLibraryParam);
    }

    @PostMapping("/excel")
    @AuthData(module = MenuEnum.PRICE_CATALOG)
    public void exportExcel(@RequestBody PriceLibrary priceLibrary, HttpServletResponse response) throws Exception {
        QueryWrapper<PriceLibrary> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getVendorName()), "VENDOR_NAME", priceLibrary.getVendorName());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getItemCode()), "ITEM_CODE", priceLibrary.getItemCode());
        wrapper.eq(priceLibrary.getCeeaOrgId() != null,"CEEA_ORG_ID",priceLibrary.getCeeaOrgId());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getCeeaOrgName()), "CEEA_ORG_NAME", priceLibrary.getCeeaOrgName());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getApprovalNo()), "APPROVAL_NO", priceLibrary.getApprovalNo());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getCeeaOrganizationName()), "CEEA_ORGANIZATION_NAME", priceLibrary.getOrganizationName());
        wrapper.like(StringUtils.isNotBlank(priceLibrary.getCategoryName()), "CATEGORY_NAME", priceLibrary.getCategoryName());
        wrapper.eq(StringUtils.isNotBlank(priceLibrary.getCeeaIfUse()),"CEEA_IF_USE",priceLibrary.getCeeaIfUse());
        wrapper.orderByDesc("CREATION_DATE");
        List<PriceLibrary> priceLibraryList = iPriceLibraryService.list(wrapper);

        if(CollectionUtils.isNotEmpty(priceLibraryList)){
            List<PriceLibraryExportDto> priceLibraryExportDtos = new ArrayList<>();
            priceLibraryList.forEach(priceLibrary1 -> {
                PriceLibraryExportDto priceLibraryExportDto = new PriceLibraryExportDto();
                BeanCopyUtil.copyProperties(priceLibraryExportDto,priceLibrary1);
                priceLibraryExportDtos.add(priceLibraryExportDto);
            });
            OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "价格目录");
            EasyExcel.write(outputStream).head(PriceLibraryExportDto.class).sheet(0,"价格目录").
                    doWrite(priceLibraryExportDtos);
        }

    }

    /**
     * 查询结果转excel
     */
    private List<PriceLibraryExcelVO> convertToPriceLibraryExcel(List<PriceLibrary> libraries) {

        List<PriceLibraryExcelVO> excel = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(libraries)) {
            // 寻源方式
            List<DictItemDTO> sourceDict = baseClient.listAllByDictCode("SOURCE_FIND_MODE");
            libraries.forEach(priceLibrary -> {
                PriceLibraryExcelVO excelVO = new PriceLibraryExcelVO();
                BeanUtils.copyProperties(priceLibrary, excelVO);
                DictItemDTO item = sourceDict.stream().filter(f -> f.getDictItemCode().equals(priceLibrary.getSourceType())).findFirst().orElse(null);
                excelVO.setSourceType(item == null ? priceLibrary.getSourceType() : item.getDictItemName());
                excelVO.setEffectiveDate(DateUtil.parseDateToStr(priceLibrary.getEffectiveDate(), DateUtil.YYYY_MM_DD));
                excelVO.setExpirationDate(DateUtil.parseDateToStr(priceLibrary.getExpirationDate(), DateUtil.YYYY_MM_DD));
                excelVO.setCreationDate(DateUtil.parseDateToStr(priceLibrary.getCreationDate(), DateUtil.YYYY_MM_DD));
                excelVO.setLastUpdateDate(DateUtil.parseDateToStr(priceLibrary.getLastUpdateDate(), DateUtil.YYYY_MM_DD));
                excel.add(excelVO);
            });
        }

        return excel;
    }

    /**
     * 批量增加价格目录
     */
    @PutMapping
    public void saveBatch(@RequestBody List<PriceLibraryAddRequestDTO> request) {
        Assert.notNull(request, "请求参数有误");
        iPriceLibraryService.generatePriceLibrary(convertPriceLibraryAddParam(request));
    }

    /**
     * 删除价格目录
     */
    @DeleteMapping
    public void delete(Long priceLibraryId) {
        Assert.notNull(priceLibraryId, "priceLibraryId不能为空");
        iPriceLibraryService.delete(priceLibraryId);
    }

    /**
     * 批量更新价格目录
     */
    @PostMapping
    public void updateBatch(@RequestBody List<PriceLibraryUpdateRequestDTO> request) {
        Assert.notNull(request, "请求参数有误");
        iPriceLibraryService.updateBatch(convertPriceLibraryUpdateParam(request));
    }

    /**
     * 批量更新价格库
     */
    @PostMapping("/updateBatch")
    private void ceeaUpdateBatch(@RequestBody List<PriceLibrary> priceLibraryList) {
        Assert.notNull(priceLibraryList, "请求参数有误");
        iPriceLibraryService.ceeaUpdateBatch(priceLibraryList);
    }

    /**
     * 请求参数转化
     */
    private List<PriceLibraryAddParam> convertPriceLibraryUpdateParam(List<PriceLibraryUpdateRequestDTO> request) {
        List<PriceLibraryAddParam> priceLibraryAddParams = new ArrayList<>();
        request.forEach(priceLibraryUpdateRequestDTO -> {
            PriceLibraryAddParam priceLibraryAddParam = new PriceLibraryAddParam();
            BeanUtils.copyProperties(priceLibraryUpdateRequestDTO, priceLibraryAddParam);

            if (CollectionUtils.isNotEmpty(priceLibraryUpdateRequestDTO.getLadderPrices())) {
                List<PriceLibraryLadderPriceAddParam> ladderPrices = new ArrayList<>();
                priceLibraryUpdateRequestDTO.getLadderPrices().forEach(priceLibraryLadderPriceUpdateDTO -> {
                    PriceLibraryLadderPriceAddParam ladderPriceAddParam = new PriceLibraryLadderPriceAddParam();
                    BeanUtils.copyProperties(priceLibraryLadderPriceUpdateDTO, ladderPriceAddParam);
                    ladderPrices.add(ladderPriceAddParam);
                });
                priceLibraryAddParam.setLadderPrices(ladderPrices);
            }
            priceLibraryAddParams.add(priceLibraryAddParam);
        });
        return priceLibraryAddParams;
    }

    /**
     * 请求参数转化
     */
    private List<PriceLibraryAddParam> convertPriceLibraryAddParam(List<PriceLibraryAddRequestDTO> request) {

        List<PriceLibraryAddParam> priceLibraryAddParams = new ArrayList<>();
        request.forEach(priceLibraryAddRequestDTO -> {
            PriceLibraryAddParam priceLibraryAddParam = new PriceLibraryAddParam();
            BeanUtils.copyProperties(priceLibraryAddRequestDTO, priceLibraryAddParam);

            if (CollectionUtils.isNotEmpty(priceLibraryAddRequestDTO.getLadderPrices())) {
                List<PriceLibraryLadderPriceAddParam> ladderPrices = new ArrayList<>();
                priceLibraryAddRequestDTO.getLadderPrices().forEach(priceLibraryLadderPriceDTO -> {
                    PriceLibraryLadderPriceAddParam ladderPriceAddParam = new PriceLibraryLadderPriceAddParam();
                    BeanUtils.copyProperties(priceLibraryLadderPriceDTO, ladderPriceAddParam);
                    ladderPrices.add(ladderPriceAddParam);
                });
                priceLibraryAddParam.setLadderPrices(ladderPrices);
            }
            priceLibraryAddParams.add(priceLibraryAddParam);
        });
        return priceLibraryAddParams;
    }

    /**
     * 根据条件 查询价格目录
     */
    @PostMapping("/getPriceLibraryByParam")
    PriceLibrary getPriceLibraryByParam(@RequestBody NetPriceQueryDTO netPriceQueryDTO) {
        return iPriceLibraryService.getPriceLibraryByParam(netPriceQueryDTO);
    }

    /**
     * @return
     * @Description 查询一条价格
     * @Param [priceLibrary]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.25 16:53
     **/
    @PostMapping("/getOnePriceLibrary")
    public PriceLibrary getOnePriceLibrary(@RequestBody PriceLibrary priceLibrary) {
        Assert.notNull(priceLibrary, "priceLibrary不能为空");
        return iPriceLibraryService.getOnePriceLibrary(priceLibrary);
    }

    /**
     * 根据条件 批量查询价格目录列表
     */
    @PostMapping("listPriceLibraryByParam")
    List<PriceLibrary> listPriceLibraryByParam(@RequestBody List<NetPriceQueryDTO> netPriceQueryDTOList) {
        return iPriceLibraryService.listPriceLibraryByParam(netPriceQueryDTOList);
    }

    /**
     * 根据条件 查询价格目录列表
     */
    @PostMapping("/listPriceLibrary")
    List<PriceLibrary> listPriceLibrary(@RequestBody NetPriceQueryDTO netPriceQueryDTO) {
        return iPriceLibraryService.listPriceLibrary(netPriceQueryDTO);
    }

    /**
     * 查询某个业务实体，某个库存组织下的物料是否存在有效价格
     *
     * @param priceLibrary
     * @return
     */
    @PostMapping("/ifHasPrice")
    String ifHasPrice(@RequestBody PriceLibrary priceLibrary) {
        return iPriceLibraryService.ifHasPrice(priceLibrary);
    }

    /**
     * 查询某个业务实体，某个库存组织，某个物料的有效价格
     *
     * @return
     */
    @PostMapping("/listEffectivePrice")
    List<PriceLibrary> listEffectivePrice(@RequestBody PriceLibrary priceLibrary) {
        return iPriceLibraryService.listEffectivePrice(priceLibrary);
    }

    /**
     * 获取近三年供应商中标次数
     *
     * @param vendorId
     * @return
     */
    @PostMapping("/getThreeYearsBidFrequency")
    List<BidFrequency> getThreeYearsBidFrequency(@RequestParam("vendorId") Long vendorId) throws ParseException {
        return iPriceLibraryService.getThreeYearsBidFrequency(vendorId);
    }


    @PostMapping("/putOnShelves")
    public void putOnShelves(@RequestBody PriceLibraryPutOnShelvesDTO priceLibraryPutOnShelvesDTO) {
        iPriceLibraryService.putOnShelves(priceLibraryPutOnShelvesDTO);
    }

    @PostMapping("/pullOffShelves")
    public void pullOffShelves(@RequestBody List<PriceLibrary> priceLibraryList) {
        iPriceLibraryService.pullOffShelves(priceLibraryList);
    }

    /**
     * @Description 导入模板下载
     * @Param: [response]
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/29 14:57
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws Exception {
        iPriceLibraryService.importModelDownload(response);
    }

    /**
     * @Description 导入价格目录excel数据
     * @Param: [file]
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/29 16:02
     */
    @RequestMapping("/importExcel")
    public void importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        iPriceLibraryService.importExcel(file);
    }

    /**
     * 查询所有有效价格
     *
     * @return
     */
    @PostMapping("/listAllEffective")
    public List<PriceLibrary> listAllEffective(@RequestBody PriceLibrary priceLibrary) {
        return iPriceLibraryService.listAllEffective(priceLibrary);
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownloadNew")
    public void importModelDownloadNew (HttpServletResponse response) throws IOException {
        String fileName = "价格库导入模板";
        ArrayList<PriceLibraryImportDTO> priceLibraryImportDTOS = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,priceLibraryImportDTOS,PriceLibraryImportDTO.class);
    }

    /**
     * 价格库基础数据导入
     *
     * @param file
     * @throws Exception
     */
    @RequestMapping("/importInitDataExcel")
    public Map<String, Object> importInitDataExcel(@RequestParam("file") MultipartFile file) throws Exception {
        return iPriceLibraryService.importInitDataExcel(file);
    }

    /**
     * 查询最新的一条价格库数据
     *
     * @param priceLibrary
     * @return
     */
    @PostMapping("/getLatest")
    public PriceLibrary getLatest(@RequestBody PriceLibrary priceLibrary) {
        System.out.println(JsonUtil.entityToJsonStr(priceLibrary));
        return iPriceLibraryService.getLatest(priceLibrary);
    }

    /**
     * 根据业务实体id(多选)，物料id(多选)
     */
    @PostMapping("/getLatestFive")
    public List<PriceLibrary> getLatestFive(@RequestBody PriceLibraryRequestDTO priceLibraryRequestDTOList) {
        return iPriceLibraryService.getLatestFive(priceLibraryRequestDTOList);
    }

    @PostMapping("/batchSetContractCode")
    public void batchSetContractCode(@RequestBody Map<String, Object> param) {
        Object contractCode = param.get("contractCode");
        if (Objects.isNull(contractCode)) {
            throw new BaseException("合同号不能为空");
        }
        Object contractName = param.get("contractName");
        if(Objects.isNull(contractName)){
            throw new BaseException("合同名称不能为空");
        }
        List<Long> priceLibraryIds = (List<Long>) param.get("priceLibraryIds");
        if (CollectionUtils.isEmpty(priceLibraryIds)) {
            return;
        }
        iPriceLibraryService.update(Wrappers.lambdaUpdate(PriceLibrary.class)
                .set(PriceLibrary::getContractName, contractName.toString())
                .set(PriceLibrary::getContractCode, contractCode.toString())
                .in(PriceLibrary::getPriceLibraryId, priceLibraryIds)
        );
    }

    /**
     * 与当前行物料编码相同，"是否已上架"为是，且在价格有效期内的物料，
     * 并按物料编码+物料描述+供应商去重
     * @return
     */
    @PostMapping("/listForMaterialSecByBuyer")
    public PageInfo<PriceLibrary> listForMaterialSecByBuyer(@RequestBody PriceLibrary priceLibrary){
        return iPriceLibraryService.listForMaterialSecByBuyer(priceLibrary);
    }

    /**
     * 物料维护-供应商端 根据供应商 + 上架物料 + 价格有效期 查询
     * 并按物料编码+物料描述+供应商去重
     * @return
     */
    @PostMapping("/listForMaterialSecByVendor")
    public PageInfo<PriceLibrary> listForMaterialSecByVendor(@RequestBody PriceLibrary priceLibrary){
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(loginAppUser.getCompanyId() == null){
            throw new BaseException("请使用供应商账号登录");
        }
        priceLibrary.setVendorId(loginAppUser.getCompanyId());
        return iPriceLibraryService.listForMaterialSecByVendor(priceLibrary);
    }

    /**
     * 根据物料编码,物料描述,供应商编码,已上架,有效,查询是否存在
     * @param priceLibraryCheckDto
     * @return
     */
    @PostMapping("/queryPriceLibraryByItemCodeNameVendorCode")
    public List<String> queryPriceLibraryByItemCodeNameVendorCode(@RequestBody PriceLibraryCheckDto priceLibraryCheckDto){
        List<String> result = new ArrayList<>();
        List<String> itemCodes = priceLibraryCheckDto.getItemCodes();
        List<String> itemNames = priceLibraryCheckDto.getItemNames();
        List<String> vendorCodes = priceLibraryCheckDto.getVendorCodes();
        if(CollectionUtils.isNotEmpty(itemCodes) &&
        CollectionUtils.isNotEmpty(itemNames) &&
        CollectionUtils.isNotEmpty(vendorCodes)){
            QueryWrapper<PriceLibrary> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("ITEM_CODE",itemCodes);
            queryWrapper.in("ITEM_DESC",itemNames);
            queryWrapper.in("VENDOR_CODE",vendorCodes);
            queryWrapper.le("EFFECTIVE_DATE",new Date());
            queryWrapper.ge("EXPIRATION_DATE",new Date());
            queryWrapper.eq("CEEA_IF_USE", YesOrNo.YES.getValue());
            List<PriceLibrary> priceLibraries = iPriceLibraryService.list(queryWrapper);
            if(CollectionUtils.isNotEmpty(priceLibraries)){
                List<PriceLibrary> libraries = ListUtil.listDeduplication(priceLibraries, o -> o.getItemCode() + o.getItemDesc() + o.getVendorCode());
                result = libraries.stream().map(priceLibrary -> priceLibrary.getItemCode() + priceLibrary.getItemDesc() + priceLibrary.getVendorCode()).collect(Collectors.toList());
            }
        }
        return result;
    }

    /**
     * 根据聚业务实体,库存组织查询
     * @param
     * @return
     */
    @GetMapping("/queryItemIdByOuAndInv")
    public List<Long> queryItemIdByOuAndInv(@RequestParam("ouId") Long ouId,@RequestParam("invId") Long invId){
        return priceLibraryMapper.queryItemIdByOuAndInv(ouId,invId);
    }

}

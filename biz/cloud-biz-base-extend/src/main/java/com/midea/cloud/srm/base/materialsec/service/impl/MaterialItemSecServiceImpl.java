package com.midea.cloud.srm.base.materialsec.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.base.materialsec.mapper.MaterialItemSecMapper;
import com.midea.cloud.srm.base.materialsec.service.IItemSceImageService;
import com.midea.cloud.srm.base.materialsec.service.IMaterialItemSecService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCategoryService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialItemSec;
import com.midea.cloud.srm.model.base.material.dto.MaterialItemSecImportDto;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryCheckDto;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
*  <pre>
 *  物料附表维护 服务实现类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-27 14:39:23
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class MaterialItemSecServiceImpl extends ServiceImpl<MaterialItemSecMapper, MaterialItemSec> implements IMaterialItemSecService {
    @Resource
    private IMaterialItemService iMaterialItemService;
    @Resource
    private SupplierClient supplierClient;
    @Resource
    private InqClient inqClient;
    @Resource
    private IPurchaseCategoryService iPurchaseCategoryService;
    @Resource
    private FileCenterClient fileCenterClient;
    @Resource
    private IItemSceImageService itemSceImageService;

    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "物料维护导入模板";
        ArrayList<MaterialItemSecImportDto> materialItemSecImportDtos = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,materialItemSecImportDtos,MaterialItemSecImportDto.class);
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file,fileupload);
        // 读取数据
        List<MaterialItemSecImportDto> materialItemSecImportDtos = readData(file);
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 新增集合
        List<MaterialItemSec> materialItemSecAdds = new ArrayList<>();
        // 更新集合
        List<MaterialItemSec> materialItemSecUpdates = new ArrayList<>();
        // 检查数据
        checkData(materialItemSecImportDtos,errorFlag,materialItemSecAdds,materialItemSecUpdates);
        if(errorFlag.get()){
            // 有报错
            fileupload.setFileSourceName("物料维护导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    materialItemSecImportDtos, MaterialItemSecImportDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
        }else {
            if(CollectionUtils.isNotEmpty(materialItemSecAdds)){
                this.saveBatch(materialItemSecAdds);
            }
            if(CollectionUtils.isNotEmpty(materialItemSecUpdates)){
                this.updateBatchById(materialItemSecUpdates);
            }
        }

        return ImportStatus.importSuccess();
    }

    void checkData(List<MaterialItemSecImportDto> materialItemSecImportDtos,AtomicBoolean errorFlag,
                   List<MaterialItemSec> materialItemSecAdds,List<MaterialItemSec> materialItemSecUpdates){
        /**
         * 1. 物料编码+物料描述+供应商编码为唯一组件
         * 2. 物料对应的大类是否为“备品备件”或“综合类物资”，如果是可以导入；如果不是，需报错提示：只有备品备件和综合类物资需要维护物料详细信息；
         * 3. “物料编码+物料描述+供应商编码+是否上架为是+有效期”在【价格目录】中查找是否有对应记录，如果有，在附表中新增一行；如果没有，报错提示：您导入的物料未上架；
         */
        if(CollectionUtils.isNotEmpty(materialItemSecImportDtos)){
            List<String> itemCodes = new ArrayList<>();
            List<String> vendorCodes = new ArrayList<>();
            List<String> itemNames = new ArrayList<>();
            for(MaterialItemSecImportDto materialItemSecImportDto : materialItemSecImportDtos){
                String ceeaSupplierCode = materialItemSecImportDto.getCeeaSupplierCode();
                String materialCode = materialItemSecImportDto.getMaterialCode();
                String materialName = materialItemSecImportDto.getMaterialName();
                if(StringUtil.notEmpty(materialName)){
                    itemNames.add(materialName.trim());
                }
                if(StringUtil.notEmpty(ceeaSupplierCode)){
                    vendorCodes.add(ceeaSupplierCode.trim());
                }
                if(StringUtil.notEmpty(materialCode)){
                    itemCodes.add(materialCode.trim());
                }
            }

            // 物料数据
            Map<String, MaterialItem> materialItemMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(itemCodes)) {
                itemCodes = itemCodes.stream().distinct().collect(Collectors.toList());
                List<MaterialItem> materialItemList = iMaterialItemService.listMaterialByCodeBatch(itemCodes);
                if(CollectionUtils.isNotEmpty(materialItemList)){
                    materialItemMap = materialItemList.stream().collect(Collectors.toMap(MaterialItem::getMaterialCode,v->v,(k1,k2)->k1));
                }
            }

            // 供应商数据
            Map<String, CompanyInfo> companyInfoMap = supplierClient.getComponyByCodeList(vendorCodes);

            /**
             * 价格目录数据
             * 物料编码+物料描述+供应商编码+是否上架为是+有效期
             */
            PriceLibraryCheckDto priceLibraryCheckDto = new PriceLibraryCheckDto();
            priceLibraryCheckDto.setItemCodes(itemCodes);
            priceLibraryCheckDto.setItemNames(itemNames);
            priceLibraryCheckDto.setVendorCodes(vendorCodes);
            List<String> priceLibraryCheckList = inqClient.queryPriceLibraryByItemCodeNameVendorCode(priceLibraryCheckDto);

            // 查找采购分类大类
            List<Long> categoryIds = new ArrayList<>();
            QueryWrapper<PurchaseCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("LEVEL",1);
            queryWrapper.in("CATEGORY_NAME","备品备件","综合类物资");
            List<PurchaseCategory> purchaseCategories = iPurchaseCategoryService.list(queryWrapper);
            if(CollectionUtils.isNotEmpty(purchaseCategories)){
                categoryIds = purchaseCategories.stream().map(PurchaseCategory::getCategoryId).collect(Collectors.toList());
            }

            // 查找物料附表的数据
            Map<String,MaterialItemSec> materialItemSecMap= new HashMap<>();
            if (CollectionUtils.isNotEmpty(itemCodes) &&
            CollectionUtils.isNotEmpty(itemNames) && CollectionUtils.isNotEmpty(vendorCodes)) {
                QueryWrapper<MaterialItemSec> secQueryWrapper = new QueryWrapper<>();
                secQueryWrapper.in("MATERIAL_CODE",itemCodes);
                secQueryWrapper.in("MATERIAL_NAME",itemNames);
                secQueryWrapper.in("CEEA_SUPPLIER_CODE",vendorCodes);
                List<MaterialItemSec> materialItemSecs = this.list(secQueryWrapper);
                if(CollectionUtils.isNotEmpty(materialItemSecs)){
                    materialItemSecMap = materialItemSecs.stream().collect
                            (Collectors.toMap(k->k.getMaterialCode()+k.getMaterialName()+k.getCeeaSupplierCode(),v->v,(k1,k2)->k1));
                }
            }


            LoginAppUser user = AppUserUtil.getLoginAppUser();


            HashSet<String> hashSet = new HashSet<>();
            int i = 1;
            for(MaterialItemSecImportDto materialItemSecImportDto : materialItemSecImportDtos){
                log.info("第"+i+"次循环");
                i ++;
                MaterialItemSec materialItemSec = new MaterialItemSec();
                StringBuffer errorMag = new StringBuffer();
                StringBuffer onlyKey = new StringBuffer();
                boolean lineErrorFlag = true;
                // 复制参数
                BeanCopyUtil.copyProperties(materialItemSec,materialItemSecImportDto);
                // 物料编码
                String materialCode = materialItemSecImportDto.getMaterialCode();
                if(StringUtil.notEmpty(materialCode)){
                    materialCode = materialCode.trim();
                    onlyKey.append(materialCode);
                    MaterialItem materialItem = materialItemMap.get(materialCode);
                    if(null != materialItem){
                        /**
                         * 物料对应的大类是否为“备品备件”或“综合类物资”，如果是可以导入；如果不是，需报错提示：只有备品备件和综合类物资需要维护物料详细信息；
                         */
                        String struct = materialItem.getStruct();
                        if(StringUtil.notEmpty(struct)){
                            String[] ids = struct.split("-");
                            if(3 == ids.length && categoryIds.contains(Long.parseLong(ids[0]))){
                                materialItemSec.setMaterialId(materialItem.getMaterialId());
                                materialItemSec.setMaterialName(materialItem.getMaterialName());
                                materialItemSec.setMaterialCode(materialItem.getMaterialCode());
                                materialItemSec.setCategoryId(materialItem.getCategoryId());
                                materialItemSec.setCategoryName(materialItem.getCategoryName());
                                materialItemSec.setCategoryFullName(materialItem.getCategoryFullName());
                                materialItemSec.setStruct(materialItem.getStruct());
                                materialItemSec.setUnit(materialItem.getUnit());
                                materialItemSec.setUnitName(materialItem.getUnitName());
                            }else {
                                errorFlag.set(true);
                                lineErrorFlag = false;
                                errorMag.append("只有备品备件和综合类物资才能维护物料详细信息; ");
                            }
                        }else {
                            errorFlag.set(true);
                            lineErrorFlag = false;
                            errorMag.append("只有备品备件和综合类物资才能维护物料详细信息; ");
                        }
                    }else {
                        errorFlag.set(true);
                        lineErrorFlag = false;
                        errorMag.append("物料编码数据库不存在; ");
                    }
                }else {
                    errorFlag.set(true);
                    lineErrorFlag = false;
                    errorMag.append("物料编码不能为空; ");
                }

                // 物料描述
                String materialName = materialItemSecImportDto.getMaterialName();
                if(StringUtil.notEmpty(materialName)){
                    materialName = StringUtil.replaceFillet(materialName.trim());
                    onlyKey.append(materialName);
                    materialItemSec.setMaterialName(materialName);
                }else {
                    errorFlag.set(true);
                    lineErrorFlag = false;
                    errorMag.append("物料描述不能为空; ");
                }

                // 供应商编码
                String ceeaSupplierCode = materialItemSecImportDto.getCeeaSupplierCode();
                if(StringUtil.notEmpty(ceeaSupplierCode)){
                    ceeaSupplierCode = ceeaSupplierCode.trim();
                    onlyKey.append(ceeaSupplierCode);
                    if(UserType.VENDOR.name().equals(user.getUserType())){
                        String companyCode = user.getCompanyCode();
                        if(!ceeaSupplierCode.equals(companyCode)){
                            errorFlag.set(true);
                            lineErrorFlag = false;
                            errorMag.append("供应商只能导入自己物料; ");
                        }
                    }
                    CompanyInfo companyInfo = companyInfoMap.get(ceeaSupplierCode);
                    if(null != companyInfo){
                        materialItemSec.setCeeaSupplierId(companyInfo.getCompanyId());
                        materialItemSec.setCeeaSupplierCode(companyInfo.getCompanyCode());
                        materialItemSec.setCeeaSupplierName(companyInfo.getCompanyName());
                    }else {
                        errorFlag.set(true);
                        lineErrorFlag = false;
                        errorMag.append("供应商编码数据库不存在; ");
                    }
                }else {
                    errorFlag.set(true);
                    lineErrorFlag = false;
                    errorMag.append("供应商编码不能为空; ");
                }

                // 规格/型号
                String specification = materialItemSecImportDto.getSpecification();
                if(StringUtil.notEmpty(specification)){
                    specification = specification.trim();
                    materialItemSec.setSpecification(specification);
                }
//                else {
//                    errorFlag.set(true);
//                    lineErrorFlag = false;
//                    errorMag.append("规格/型号不能为空; ");
//                }

                // 送货周期
                String ceeaDeliveryCycle = materialItemSecImportDto.getCeeaDeliveryCycle();
                if(StringUtil.notEmpty(ceeaDeliveryCycle)){
                    ceeaDeliveryCycle = ceeaDeliveryCycle.trim();
                    materialItemSec.setCeeaDeliveryCycle(ceeaDeliveryCycle);
                }
//                else {
//                    errorFlag.set(true);
//                    lineErrorFlag = false;
//                    errorMag.append("送货周期不能为空; ");
//                }

                // 最小起送量
                String ceeaOrderQuantityMinimum = materialItemSecImportDto.getCeeaOrderQuantityMinimum();
                if(StringUtil.notEmpty(ceeaOrderQuantityMinimum)){
                    ceeaOrderQuantityMinimum = ceeaOrderQuantityMinimum.trim();
                    if(StringUtil.isDigit(ceeaOrderQuantityMinimum)){
                        materialItemSec.setCeeaOrderQuantityMinimum(new BigDecimal(ceeaOrderQuantityMinimum));
                    }else {
                        errorFlag.set(true);
                        lineErrorFlag = false;
                        errorMag.append("最小起送量格式非法; ");
                    }
                }

                if(!hashSet.add(onlyKey.toString())){
                    errorFlag.set(true);
                    lineErrorFlag = false;
                    errorMag.append("物料编码+物料描述+供应商编码存在重复行; ");
                }

                // 检查是否有重复
                if(lineErrorFlag){
                    String key = materialItemSec.getMaterialCode() + materialItemSec.getMaterialName() + materialItemSec.getCeeaSupplierCode();
                    if(priceLibraryCheckList.contains(key)){
                        MaterialItemSec itemSec = materialItemSecMap.get(key);
                        if(null != itemSec){
                            // 更新
                            materialItemSec.setMaterialSecondaryId(itemSec.getMaterialSecondaryId());
                            materialItemSecUpdates.add(materialItemSec);
                        }else {
                            // 新增
                            materialItemSec.setMaterialSecondaryId(IdGenrator.generate());
                            materialItemSecAdds.add(materialItemSec);
                        }
                    }else {
                        errorFlag.set(true);
                        errorMag.append("您导入的物料未上架; ");
                    }
                }

                if(errorMag.length() > 0){
                    materialItemSecImportDto.setErrorMsg(errorMag.toString());
                }else {
                    materialItemSecImportDto.setErrorMsg(null);
                }

            }

        }
    }

    private List<MaterialItemSecImportDto> readData(MultipartFile file) {
        List<MaterialItemSecImportDto> materialItemSecImportDtos = new ArrayList<>();
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<MaterialItemSecImportDto> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(MaterialItemSecImportDto.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            materialItemSecImportDtos = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return materialItemSecImportDtos;
    }
}

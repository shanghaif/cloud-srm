package com.midea.cloud.srm.cm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.contract.ModelKey;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.cm.contract.mapper.ContractHeadMapper;
import com.midea.cloud.srm.cm.contract.mapper.ContractMaterialMapper;
import com.midea.cloud.srm.cm.contract.service.IContractMaterialService;
import com.midea.cloud.srm.cm.model.service.IModelLineService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
import com.midea.cloud.srm.model.cm.contract.dto.ContractItemDto;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDto2;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialModel;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.cm.model.entity.ModelLine;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
*  <pre>
 *  合同物料 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:16:46
 *  修改内容:
 * </pre>
*/
@Service
public class ContractMaterialServiceImpl extends ServiceImpl<ContractMaterialMapper, ContractMaterial> implements IContractMaterialService {

    @Autowired
    BaseClient baseClient;

    @Autowired
    FileCenterClient fileCenterClient;

    @Autowired
    IModelLineService iModelLineService;
    @Resource
    private ContractHeadMapper contractHeadMapper;

    @Override
    public PageInfo<ContractMaterialDTO> listPageMaterialDTOByParm(ContractMaterialDTO contractMaterialDTO) {
        PageUtil.startPage(contractMaterialDTO.getPageNum(), contractMaterialDTO.getPageSize());
        List<ContractMaterialDTO> contractMaterialDTOS = this.baseMapper.listPageMaterialDTOByParm(contractMaterialDTO);
        return new PageInfo<>(contractMaterialDTOS);
    }

    @Override
    public void updateContractMaterial(ContractMaterialDTO contractMaterialDTO) {
        UpdateWrapper<ContractMaterial> updateWrapper = new UpdateWrapper<>(new ContractMaterial()
                .setContractHeadId(contractMaterialDTO.getContractHeadId())
                .setLineNumber(contractMaterialDTO.getLineNumber())
                .setMaterialId(contractMaterialDTO.getMaterialId()));
        ContractMaterial contractMaterial = new ContractMaterial();
        BeanUtils.copyProperties(contractMaterialDTO, contractMaterial);
        this.update(contractMaterial, updateWrapper);
    }

    @Override
    public void updateContractMaterials(List<ContractMaterialDTO> contractMaterialDTOS) {
        if (!CollectionUtils.isEmpty(contractMaterialDTOS)) {
            contractMaterialDTOS.forEach(contractMaterialDTO -> {
                UpdateWrapper<ContractMaterial> updateWrapper = new UpdateWrapper<>(new ContractMaterial()
                        .setContractHeadId(contractMaterialDTO.getContractHeadId())
                        .setLineNumber(contractMaterialDTO.getLineNumber())
                        .setMaterialId(contractMaterialDTO.getMaterialId()));
                ContractMaterial contractMaterial = new ContractMaterial();
                BeanUtils.copyProperties(contractMaterialDTO, contractMaterial);
                this.update(contractMaterial, updateWrapper);
            });
        }
    }

    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "合同物料导入模板";
        List<ContractMaterialModel> contractMaterialModels = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream, fileName, contractMaterialModels,ContractMaterialModel.class);
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws IOException {
        //检验参数
        EasyExcelUtil.checkParam(file, fileupload);
        Long contractHeadId = fileupload.getBusinessId();
        Assert.notNull(contractHeadId, LocaleHandler.getLocaleMsg("contractHeadId不能为空"));

        HashMap<String,Object> result = new HashMap<>();
        InputStream inputStream = file.getInputStream();
        List<ContractMaterialModel> contractMaterialModels = new ArrayList<>();
        List<ContractMaterial> contractMaterials = new ArrayList<>();

        //检查导入数据
        List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, ContractMaterialModel.class);
        if (!CollectionUtils.isEmpty(objects)) {
            objects.forEach((object -> {
                if (object != null) {
                    ContractMaterialModel contractMaterialModel = (ContractMaterialModel) object;
                    ContractMaterial contractMaterial = new ContractMaterial();
                    //校验参数,并且设置报错信息
                    checkParmAndSetErrorMsg(contractMaterialModel, contractMaterial);
                    contractMaterialModels.add(contractMaterialModel);
                    contractMaterials.add(contractMaterial);
                }
            }));
        }

        //检查是否有错误信息
        boolean flag = false;
        if (!CollectionUtils.isEmpty(contractMaterialModels)) {
            for (ContractMaterialModel contractMaterialModel : contractMaterialModels) {
                String errorMsg = contractMaterialModel.getErrorMsg();
                if (StringUtils.isNotBlank(errorMsg)) {
                    flag = true;
                    break;
                }
            }
        }

        if (flag) {
            //有错误信息则上传错误信息报告,供用户下载
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    contractMaterialModels, ContractMaterialModel.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            result.put("status", YesOrNo.NO.getValue());
            result.put("message","error");
            result.put("fileuploadId",errorFileupload.getFileuploadId());
            result.put("fileName",errorFileupload.getFileSourceName());
            return result;
        }else {
            //无错误信息直接保存数据
            if (!CollectionUtils.isEmpty(contractMaterials)) {
                contractMaterials.forEach(contractMaterial -> {
                    contractMaterial.setContractHeadId(contractHeadId)
                            .setContractMaterialId(IdGenrator.generate());
                    this.save(contractMaterial);
                });
            }
            result.put("status", YesOrNo.YES.getValue());
            result.put("message","success");
            return result;
        }
    }

    @Override
    public void batchDeleteSecond(List<Long> contractMaterialIds) {
        if (!CollectionUtils.isEmpty(contractMaterialIds)) {
            this.removeByIds(contractMaterialIds);
            //删除合同行表中value的payPlan数据
            removeContractMaterialsInModelValue(contractMaterialIds);
        }
    }

    private void removeContractMaterialsInModelValue(List<Long> contractMaterialIds) {
        Long contractMaterialId = contractMaterialIds.get(0);
        ContractMaterial contractMaterial = this.getById(contractMaterialId);
        if (contractMaterial != null) {
            Long contractHeadId = contractMaterial.getContractHeadId();
            ModelLine modelLine = iModelLineService.getOne(new QueryWrapper<>(new ModelLine()
                    .setContractHeadId(contractHeadId)
                    .setModelKey(ModelKey.materialList.name())));
            String materialListJsonString = modelLine.getModelValue();
            List<ContractMaterial> contractMaterials = JsonUtil.parseJsonStrToList(materialListJsonString, ContractMaterial.class);
            Iterator<ContractMaterial> iterator = contractMaterials.iterator();
            while (iterator.hasNext()) {
                ContractMaterial next = iterator.next();
                for (Long materialId : contractMaterialIds) {
                    if (materialId.compareTo(next.getContractMaterialId()) == 0) {
                        iterator.remove();
                        break;
                    }
                }
            }
            materialListJsonString = JsonUtil.arrayToJsonStr(contractMaterials);
            modelLine.setModelValue(materialListJsonString);
            iModelLineService.updateById(modelLine);
        }
    }

    private void checkParmAndSetErrorMsg(ContractMaterialModel contractMaterialModel, ContractMaterial contractMaterial) {
        StringBuffer errorMsg = new StringBuffer();
        contractMaterialModel.setErrorMsg(null);

        //检查合同行号
        String lineNumber = contractMaterialModel.getLineNumber();
        if (StringUtils.isNumeric(lineNumber)) {
            contractMaterial.setLineNumber(Long.valueOf(lineNumber));
        } else {
            errorMsg.append("合同行号必须为数字; ");
        }

        //检查物料
        String materialCode = contractMaterialModel.getMaterialCode();
        List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialCode(materialCode));
        if (!CollectionUtils.isEmpty(materialItems)) {
            MaterialItem materialItem = materialItems.get(0);
            contractMaterial.setMaterialId(materialItem.getMaterialId())
                    .setMaterialName(materialItem.getMaterialName())
                    .setMaterialCode(materialItem.getMaterialCode());
        } else {
            errorMsg.append("系统无法找到该物料; ");
        }

        //检查采购分类
        String categoryCode = contractMaterialModel.getCategoryCode();
        PurchaseCategory purchaseCategory = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryCode(categoryCode));
        if (purchaseCategory != null) {
            contractMaterial.setCategoryId(purchaseCategory.getCategoryId())
                    .setCategoryCode(categoryCode)
                    .setCategoryName(purchaseCategory.getCategoryName());
        } else {
            errorMsg.append("系统无法找到该采购分类; ");
        }

        //检查金额
        String amount = contractMaterialModel.getAmount();
        if (StringUtils.isNumeric(amount)) {
            contractMaterial.setAmount(new BigDecimal(amount));
        } else {
            errorMsg.append("金额格式不对; ");
        }

        //检查合同数量
        String contractQuantity = contractMaterialModel.getContractQuantity();
        if (StringUtils.isNumeric(contractQuantity)) {
            contractMaterial.setContractQuantity(new BigDecimal(contractQuantity));
        } else {
            errorMsg.append("合同数量格式不对; ");
        }

        //检查订单数量
        String orderQuantity = contractMaterialModel.getOrderQuantity();
        if (StringUtils.isNumeric(orderQuantity)) {
//            contractMaterial.setOrderQuantity(new BigDecimal(orderQuantity));
        } else {
            errorMsg.append("订单数量格式不对; ");
        }

        //检查未税单价
        String untaxedPrice = contractMaterialModel.getUntaxedPrice();
        if (StringUtils.isNumeric(untaxedPrice)) {
            contractMaterial.setUntaxedPrice(new BigDecimal(untaxedPrice));
        } else {
            errorMsg.append("未税单价格式不对; ");
        }

        //检查人工单价
        String peoplePrice = contractMaterialModel.getPeoplePrice();
        if (StringUtils.isNumeric(peoplePrice)) {
            contractMaterial.setPeoplePrice(new BigDecimal(peoplePrice));
        } else {
            errorMsg.append("人工单价格式不对; ");
        }

        //检查材料单价
        String materialPrice = contractMaterialModel.getMaterialPrice();
        if (StringUtils.isNumeric(materialPrice)) {
            contractMaterial.setMaterialPrice(new BigDecimal(materialPrice));
        } else {
            errorMsg.append("材料单价格式不对; ");
        }

        //检查税率编码
        String taxKey = contractMaterialModel.getTaxKey();
        List<PurchaseTax> purchaseTaxes = baseClient.listPagePurchaseTax(new PurchaseTax().setTaxKey(taxKey)).getList();
        if (!CollectionUtils.isEmpty(purchaseTaxes)) {
            PurchaseTax purchaseTax = purchaseTaxes.get(0);
            contractMaterial.setTaxKey(taxKey).setTaxRate(purchaseTax.getTaxCode());
        } else {
            errorMsg.append("系统无法找到该税率; ");
        }

        //检查单位编码
        String unitCode = contractMaterialModel.getUnitCode();
        List<PurchaseUnit> purchaseUnits = baseClient.listPurchaseUnitByParam(new PurchaseUnit().setUnitCode(unitCode));
        if (!CollectionUtils.isEmpty(purchaseUnits)) {
            PurchaseUnit purchaseUnit = purchaseUnits.get(0);
            contractMaterial.setUnitId(purchaseUnit.getUnitId())
                    .setUnitCode(purchaseUnit.getUnitCode())
                    .setUnitName(purchaseUnit.getUnitName());
        } else {
            errorMsg.append("系统无法找到该单位");
        }

        contractMaterialModel.setErrorMsg(errorMsg.toString());
    }

    @Override
    public List<ContractMaterialDto2> queryContractPrice(ContractItemDto contractItemDto) {
        return contractHeadMapper.queryContractItem(contractItemDto);
    }

    @Override
    public List<ContractVo> listAllEffectiveCM(ContractItemDto contractItemDto) {
        return contractHeadMapper.listAllEffectiveCM(contractItemDto);
    }

    @Override
    public List<ContractVo> listEffectiveContractByParam(ContractMaterial contractMaterial) {
        return contractHeadMapper.listEffectiveContractByParam(contractMaterial);
    }
}

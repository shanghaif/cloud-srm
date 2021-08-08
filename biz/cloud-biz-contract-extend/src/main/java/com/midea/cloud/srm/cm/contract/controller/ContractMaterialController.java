package com.midea.cloud.srm.cm.contract.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.cm.contract.mapper.ContractHeadMapper;
import com.midea.cloud.srm.cm.contract.mapper.PayPlanMapper;
import com.midea.cloud.srm.cm.contract.service.IContractMaterialService;
import com.midea.cloud.srm.cm.contract.service.IContractPartnerService;
import com.midea.cloud.srm.cm.contract.service.IPayPlanService;
import com.midea.cloud.srm.model.cm.contract.dto.ContractItemDto;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDto2;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  合同物料 前端控制器
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 10:16:46
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/contract/contractMaterial")
public class ContractMaterialController extends BaseController {

    @Autowired
    private IContractMaterialService iContractMaterialService;
    @Resource
    private ContractHeadMapper contractHeadMapper;
    @Autowired
    private IContractPartnerService iContractPartnerService;
    @Resource
    private PayPlanMapper payPlanMapper;


    /**
     * 批量删除
     *
     * @param contractMaterialIds
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> contractMaterialIds) {
        if (!CollectionUtils.isEmpty(contractMaterialIds)) {
            iContractMaterialService.removeByIds(contractMaterialIds);
        }
    }

    /**
     * 批量删除 2.0
     *
     * @param contractMaterialIds
     */
    @PostMapping("/batchDeleteSecond")
    public void batchDeleteSecond(@RequestBody List<Long> contractMaterialIds) {
        iContractMaterialService.batchDeleteSecond(contractMaterialIds);
    }

    /**
     * 分页查询合同物料(采购订单需要)
     *
     * @param contractMaterialDTO
     * @return
     */
    @PostMapping("/listPageMaterialDTOByParm")
    public PageInfo<ContractMaterialDTO> listPageMaterialDTOByParm(@RequestBody ContractMaterialDTO contractMaterialDTO) {
        return iContractMaterialService.listPageMaterialDTOByParm(contractMaterialDTO);
    }

    /**
     * 更新合同物料(内部调用)
     *
     * @param contractMaterialDTO
     */
    @PostMapping("/updateContractMaterial")
    public void updateContractMaterial(@RequestBody ContractMaterialDTO contractMaterialDTO) {
        iContractMaterialService.updateContractMaterial(contractMaterialDTO);
    }

    /**
     * 批量更新合同物料(内部调用)
     *
     * @param contractMaterialDTOS
     */
    @PostMapping("/updateContractMaterials")
    public void updateContractMaterials(@RequestBody List<ContractMaterialDTO> contractMaterialDTOS) {
        iContractMaterialService.updateContractMaterials(contractMaterialDTOS);
    }

    /**
     * 导入文件模板下载
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iContractMaterialService.importModelDownload(response);
    }

    /**
     * 导入文件
     *
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iContractMaterialService.importExcel(file, fileupload);
    }

    /**
     * 查询合同物料
     *
     * @param contractItemDto
     * @return
     */
    @PostMapping("/queryContractItem")
    public List<ContractMaterialDto2> queryContractItem(@RequestBody ContractItemDto contractItemDto) {
//        QueryWrapper<ContractMaterial> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq(StringUtil.notEmpty(contractItemDto.getOuId()),"BU_ID",contractItemDto.getOuId());
//        queryWrapper.eq(StringUtil.notEmpty(contractItemDto.getInvId()),"INV_ID",contractItemDto.getInvId());
//        queryWrapper.eq(StringUtil.notEmpty(contractItemDto.getItemCode()),"MATERIAL_CODE",contractItemDto.getItemCode());
//        queryWrapper.eq(StringUtil.notEmpty(contractItemDto.getItemName()),"MATERIAL_NAME",contractItemDto.getItemName());
//        List<ContractMaterial> contractMaterials = iContractMaterialService.list(queryWrapper);
        return iContractMaterialService.queryContractPrice(contractItemDto);
    }

    /**
     * 查询所有的物料
     */
    @PostMapping("/listAllEffectiveCM")
    public List<ContractVo> listAllEffectiveCM(@RequestBody ContractItemDto contractItemDto) {
        return iContractMaterialService.listAllEffectiveCM(contractItemDto);
    }

    /**
     * 查询合同合作伙伴
     */
    @PostMapping("/listAllEffectiveCP")
    public List<ContractPartner> listAllEffectiveCP(@RequestBody List<Long> contractHeadIds) {
        if (Objects.isNull(contractHeadIds) || contractHeadIds.isEmpty()) {
            return new ArrayList<>();
        }
        return iContractPartnerService.list(Wrappers.lambdaQuery(ContractPartner.class)
                .in(ContractPartner::getContractHeadId, contractHeadIds));
    }


    /**
     * 根据条件查询有效合同
     */
    @PostMapping("/listEffectiveContractByParam")
    public List<ContractVo> listEffectiveContractByParam(@RequestBody ContractMaterial contractMaterial) {
        return iContractMaterialService.listEffectiveContractByParam(contractMaterial);
    }
}

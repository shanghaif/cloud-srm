package com.midea.cloud.srm.cm.contract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.contract.dto.ContractItemDto;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDTO;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDto2;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  合同物料 服务类
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
public interface IContractMaterialService extends IService<ContractMaterial> {

    /**
     *分页查询合同物料(采购订单需要)
     * @param contractMaterialDTO
     * @return
     */
    PageInfo<ContractMaterialDTO> listPageMaterialDTOByParm(ContractMaterialDTO contractMaterialDTO);

    /**
     * 更新合同物料
     * @param contractMaterialDTO
     */
    void updateContractMaterial(ContractMaterialDTO contractMaterialDTO);

    /**
     * 批量更新合同物料
     * @param contractMaterialDTOS
     */
    void updateContractMaterials(List<ContractMaterialDTO> contractMaterialDTOS);

    /**
     * 导入文件模板下载
     * @param response
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    /**
     * 导入文件
     * @param file
     * @param fileupload
     * @return
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws IOException;

    /**
     * 批量删除 2.0
     * @param contractMaterialIds
     */
    void batchDeleteSecond(List<Long> contractMaterialIds);

    /**
     * 查询合同物料
     * @param contractItemDto
     * @return
     */
    List<ContractMaterialDto2> queryContractPrice(@RequestBody ContractItemDto contractItemDto);

    /**
     * 查询所有的有效物料
     * @return
     */
    List<ContractVo> listAllEffectiveCM(ContractItemDto contractItemDto);

    /**
     * 根据条件查询有效合同
     * @param contractMaterial
     * @return
     */
    List<ContractVo> listEffectiveContractByParam(ContractMaterial contractMaterial);
}

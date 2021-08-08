package com.midea.cloud.srm.supauth.materialtrial.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplierauth.materialtrial.dto.MaterialTrialDTO;
import com.midea.cloud.srm.model.supplierauth.materialtrial.dto.RequestTrialDTO;
import com.midea.cloud.srm.model.supplierauth.materialtrial.entity.MaterialTrial;

import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  物料试用表 服务类
 * </pre>
 *
 * @author zhuwl7@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 10:59:47
 *  修改内容:
 * </pre>
 */
public interface IMaterialTrialService extends IService<MaterialTrial> {

    PageInfo<MaterialTrial> listPageByParam(RequestTrialDTO requestTrialDTO);

    Long addMaterialTrial(MaterialTrial materialTrial);

    Long modifyMaterial(MaterialTrial materialTrial);

    void bathDeleteByList(List<Long> materialTrialIds);

    void commonCheck(MaterialTrial materialTrial, String orderStatus);

    FormResultDTO updateMaterial(MaterialTrial materialTrial, String orderStatus);

    Long saveOrUpdateMaterial(MaterialTrialDTO materialTrialDTO, String orderStatus);

    void submittedSave(MaterialTrialDTO materialTrialDTO);

    WorkCount countConfirmed();

    Map<String,Object> updateMaterialWithFlow(MaterialTrialDTO materialTrialDTO);

    void updateCataLogAfterFlow(MaterialTrial materialTrial);
}

package com.midea.cloud.srm.supauth.orgcategory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCateServiceStatusDTO;
import com.midea.cloud.srm.model.supplierauth.orgcategory.dto.OrgCatFormDTO;
import com.midea.cloud.srm.model.supplierauth.orgcategory.entity.OrgCatForm;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  组织品类控制单据 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-24 15:04:22
 *  修改内容:
 * </pre>
*/
public interface IOrgCatFormService extends IService<OrgCatForm> {

    /**
     * 暂存
     * @param orgCatFormDTO
     * @param approveStatusType
     * @return
     */
    FormResultDTO saveTemporary(OrgCatFormDTO orgCatFormDTO, String approveStatusType);

    /**
     * 提交
     * @param orgCatFormDTO
     * @param approveStatusType
     * @return
     */
    FormResultDTO submitted(OrgCatFormDTO orgCatFormDTO, String approveStatusType);

    /**
     * 分页条件查询
     * @param orgCatForm
     * @return
     */
    PageInfo<OrgCatForm> listPageByParm(OrgCatForm orgCatForm);

    /**
     * 根据控制类型和供应商ID查询组织与品类
     * @param supplierControlType
     * @param companyId
     * @return
     */
    PageInfo listOrgCateServiceStatusPageByParm(String supplierControlType, Long companyId);

    /**
     * 根据orgCatFormId获取OrgCatFormDTO
     * @param orgCatFormId
     * @return
     */
    OrgCatFormDTO getOrgCatFormDTO(Long orgCatFormId);

    /**
     * 根据单据ID删除合作终止单据
     * @param orgCatFormId
     */
    void deleteOrgCatFormById(Long orgCatFormId);

    Map<String,Object> submitWithFlow(OrgCatFormDTO orgCatFormDTO,String value);

    void updateVendorMainDataAfterFLow(List<OrgCateJournal> orgCateJournals,OrgCatForm orgCatForm);
}

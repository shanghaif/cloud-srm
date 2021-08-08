package com.midea.cloud.srm.sup.vendorimport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportCategoryDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.OrgDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImport;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
*  <pre>
 *  供应商引入表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-13 18:08:07
 *  修改内容:
 * </pre>
*/
public interface IVendorImportService extends IService<VendorImport> {

    /**
     * 分页条件查询
     * @param vendorImport
     * @return
     */
    PageInfo<VendorImport> listPageByParam(VendorImport vendorImport);

    /**
     * 根据importId（头表Id）查询引入详情信息
     * @param importId
     * @return
     */
    VendorImportSaveDTO getVendorImportDetail(Long importId);

    /**
     * 条件查询合作品类
     * @param orgCategoryQueryDTO
     * @return
     */
    List<OrgCategory> listOrgCategoryByParam(OrgCategoryQueryDTO orgCategoryQueryDTO);

    /**
     * 暂存
     * @param vendorImportSaveDTO
     * @return
     */
    Long saveTemporary(VendorImportSaveDTO vendorImportSaveDTO, String vendorImportStatus);

    /**
     * 审批
     * @param vendorImportSaveDTO
     * @param vendorImportStatus
     * @return
     */
    Long submit(VendorImportSaveDTO vendorImportSaveDTO, String vendorImportStatus);

    /**
     * 审批
     * @param importId
     */
    void approve(Long importId);

    /**
     * 驳回
     * @param importId
     */
    void reject(Long importId,String opinion);

    /**
     * 撤回
     * @param importId
     */
    void withdraw(Long importId,String opinion);

    /**
     * 删除
     * @param importId
     */
    void delete(Long importId);

    /**
     * 废弃订单
     * @param importId
     */
    @GetMapping("/abandon")
     void abandon(Long importId);
    /**
     * 根据vendorId去OrgCategory表里查询合作的Org（业务实体）
     * @param vendorId
     * @return
     */
    List<OrgDTO> getOrgByVendorId(Long vendorId);

    /**
     * 根据vendorId和orgId查询合作的品类列表
     * @param vendorId
     * @param orgId
     * @return
     */
    List<VendorImportCategoryDTO> getCategoryListByParams(Long vendorId, Long orgId);

    /**
     * 刷新组织品类数据(批量)
     */
    void refresh();

    /**
     * 刷新组织品类数据
     */
    void refreshByImportNum(List<VendorImport> vendorImportList);

}

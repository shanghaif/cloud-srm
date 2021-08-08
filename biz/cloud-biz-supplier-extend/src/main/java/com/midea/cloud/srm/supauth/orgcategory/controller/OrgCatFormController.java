package com.midea.cloud.srm.supauth.orgcategory.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCateServiceStatusDTO;
import com.midea.cloud.srm.model.supplierauth.orgcategory.dto.OrgCatFormDTO;
import com.midea.cloud.srm.model.supplierauth.orgcategory.entity.OrgCatForm;
import com.midea.cloud.srm.supauth.orgcategory.service.IOrgCatFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  组织品类控制单据 前端控制器
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
@RestController
@RequestMapping("/orgcategory/orgCatForm")
public class OrgCatFormController extends BaseController {

    @Autowired
    private IOrgCatFormService iOrgCatFormService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrgCatForm get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrgCatFormService.getById(id);
    }

    /**
    * 新增
    * @param orgCatForm
    */
    @PostMapping("/add")
    public void add(@RequestBody OrgCatForm orgCatForm) {
        Long id = IdGenrator.generate();
        orgCatForm.setOrgCatFormId(id);
        iOrgCatFormService.save(orgCatForm);
    }
    
    /**
    * 删除
    * @param orgCatFormId
    */
    @GetMapping("/delete")
    public void delete(Long orgCatFormId) {
        Assert.notNull(orgCatFormId, "orgCatFormId不能为空");
        iOrgCatFormService.deleteOrgCatFormById(orgCatFormId);
    }

    /**
    * 修改
    * @param orgCatForm
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrgCatForm orgCatForm) {
        iOrgCatFormService.updateById(orgCatForm);
    }

    /**
    * 分页查询
    * @param orgCatForm
    * @return
    */
    @PostMapping("/listPageByParm")
    public PageInfo<OrgCatForm> listPageByParm(@RequestBody OrgCatForm orgCatForm) {
        return iOrgCatFormService.listPageByParm(orgCatForm);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrgCatForm> listAll() {
        return iOrgCatFormService.list();
    }

    /**
     * 暂存
     * @param
     * @return
     */
    @PostMapping("/saveTemporary")
    public FormResultDTO saveTemporary(@RequestBody OrgCatFormDTO orgCatFormDTO) {
        return iOrgCatFormService.saveTemporary(orgCatFormDTO, ApproveStatusType.DRAFT.getValue());
    }

    /**
     * 提交
     * @param orgCatFormDTO
     * @return
     */
    @PostMapping("/submitted")
    public FormResultDTO submitted(@RequestBody OrgCatFormDTO orgCatFormDTO) {
        return iOrgCatFormService.submitted(orgCatFormDTO, ApproveStatusType.SUBMITTED.getValue());
    }

    /**
     * 根据控制类型和供应商ID分页查询组织与品类
     * @param supplierControlType
     * @param companyId
     * @return
     */
    @GetMapping("/listOrgCateServiceStatusPageByParm")
    public PageInfo listOrgCateServiceStatusPageByParm(String supplierControlType, Long companyId) {
        return iOrgCatFormService.listOrgCateServiceStatusPageByParm(supplierControlType, companyId);
    }

    /**
     * 根据orgCatFormId获取OrgCatFormDTO
     * @param orgCatFormId
     * @return
     */
    @GetMapping("/getOrgCatFormDTO")
    public OrgCatFormDTO getOrgCatFormDTO(Long orgCatFormId) {
        return iOrgCatFormService.getOrgCatFormDTO(orgCatFormId);
    }

    @PostMapping("/submitWithFlow")
    public Map<String,Object> submitWithFlow(@RequestBody OrgCatFormDTO orgCatFormDTO) {
        return iOrgCatFormService.submitWithFlow(orgCatFormDTO, ApproveStatusType.SUBMITTED.getValue());
    }
}

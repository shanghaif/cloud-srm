package com.midea.cloud.srm.supauth.materialtrial.controller;

import com.midea.cloud.common.enums.OrgCateBillType;
import com.midea.cloud.common.enums.SampleStatusType;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplierauth.materialtrial.dto.MaterialTrialDTO;
import com.midea.cloud.srm.model.supplierauth.materialtrial.dto.RequestTrialDTO;
import com.midea.cloud.srm.model.supplierauth.materialtrial.entity.MaterialTrial;
import com.midea.cloud.srm.model.supplierauth.quasample.dto.QuaSampleDTO;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.supauth.entry.service.IFileRecordService;
import com.midea.cloud.srm.supauth.materialtrial.service.IMaterialTrialService;
import com.midea.cloud.srm.supauth.review.service.IOrgCateJournalService;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
 *  <pre>
 *  物料试用表 前端控制器
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
@RestController
@RequestMapping("/materialTrial")
public class MaterialTrialController extends BaseController {

    @Autowired
    private IMaterialTrialService iMaterialTrialService;

    @Autowired
    private IOrgCateJournalService iOrgCateJournalService;
    
    @Autowired
    private IFileRecordService iFileRecordService;
    
    /**
     * 获取
     * @param materialTrialId
     */
    @GetMapping("/get")
    public MaterialTrialDTO get(Long materialTrialId) {
        Assert.notNull(materialTrialId, "id不能为空");
        MaterialTrialDTO materialTrialDTO = new MaterialTrialDTO();
        MaterialTrial materialTrial = iMaterialTrialService.getById(materialTrialId);
    	Assert.notNull(materialTrial, "物料试用单据不存在");
    	materialTrialDTO.setMaterialTrial(materialTrial);
    	materialTrialDTO.setOrgCateJournals(iOrgCateJournalService.getOrgCateList(materialTrialId, OrgCateBillType.MATERIAL_FORM.getValue()));
    	materialTrialDTO.setFileRecords(iFileRecordService.getFileRecord(materialTrialId, OrgCateBillType.MATERIAL_FORM.getValue()));
        return materialTrialDTO;
    }

    /**
     * 新增
     * @param materialTrial
     */
    @PostMapping("/add")
    public Long add(@RequestBody MaterialTrial materialTrial) {
        return   iMaterialTrialService.addMaterialTrial(materialTrial);
    }

    /**
     * 删除
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iMaterialTrialService.removeById(id);
    }

    /**
     *批量删除
     * @param materialTrialIds
     */
    @PostMapping("/bathDeleteByList")
    public void bathDeleteByList(@RequestBody List<Long> materialTrialIds) {
        Assert.notNull(materialTrialIds, "id不能为空");
        iMaterialTrialService.bathDeleteByList(materialTrialIds);
    }

    /**
     * 修改
     * @param materialTrial
     */
    @PostMapping("/modify")
    public Long modify(@RequestBody MaterialTrial materialTrial) {
        return iMaterialTrialService.modifyMaterial(materialTrial);
    }

    /**
     * 分页查询
     * @param materialTrial
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<MaterialTrial> listPage(@RequestBody MaterialTrial materialTrial) {
        PageUtil.startPage(materialTrial.getPageNum(), materialTrial.getPageSize());
        QueryWrapper<MaterialTrial> wrapper = new QueryWrapper<MaterialTrial>(materialTrial);
        return new PageInfo<MaterialTrial>(iMaterialTrialService.list(wrapper));
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/listAll")
    public List<MaterialTrial> listAll() {
        return iMaterialTrialService.list();
    }

    /**
     * 分页查询
     * @param requestTrialDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<MaterialTrial> listPageByParam(@RequestBody RequestTrialDTO requestTrialDTO) {
        PageUtil.startPage(requestTrialDTO.getPageNum(), requestTrialDTO.getPageSize());
        return iMaterialTrialService.listPageByParam(requestTrialDTO);
    }

    /**
     *暂存
     * @param materialTrialDTO
     */
    @PostMapping("/saveTemporary")
    public Long saveTemporary(@RequestBody MaterialTrialDTO materialTrialDTO) {
        iMaterialTrialService.commonCheck(materialTrialDTO.getMaterialTrial(), SampleStatusType.DRAFT.getValue());
        return   iMaterialTrialService.saveOrUpdateMaterial(materialTrialDTO,SampleStatusType.DRAFT.getValue());
    }

    /**
     *发布
     * @param materialTrialDTO
     */
    @PostMapping("/publish")
    public Long publish(@RequestBody MaterialTrialDTO materialTrialDTO) {
        iMaterialTrialService.commonCheck(materialTrialDTO.getMaterialTrial(), SampleStatusType.PUBLISHED.getValue());
        return iMaterialTrialService.saveOrUpdateMaterial(materialTrialDTO,SampleStatusType.PUBLISHED.getValue());
    }


    /**
     *拒绝
     * @param materialTrial
     */
    @PostMapping("/refused")
    public void refused(@RequestBody MaterialTrial materialTrial) {
        iMaterialTrialService.commonCheck(materialTrial, SampleStatusType.REFUSED.getValue());
        iMaterialTrialService.updateMaterial(materialTrial, SampleStatusType.REFUSED.getValue());
    }

    /**
     *确认
     * @param materialTrial
     */
    @PostMapping("/confirmed")
    public void confirmed(@RequestBody MaterialTrial materialTrial) {
        iMaterialTrialService.commonCheck(materialTrial, SampleStatusType.CONFIRMED.getValue());
        iMaterialTrialService.updateMaterial(materialTrial, SampleStatusType.CONFIRMED.getValue());
    }

    /**
     *提交
     * @param materialTrial
     */
    @PostMapping("/submitted")
    public FormResultDTO submitted(@RequestBody MaterialTrial materialTrial) {
        iMaterialTrialService.commonCheck(materialTrial, SampleStatusType.SUBMITTED.getValue());
        return   iMaterialTrialService.updateMaterial(materialTrial,SampleStatusType.SUBMITTED.getValue());
    }

    /**
     *提交保存
     * @param materialTrialDTO
     */
    @PostMapping("/submittedSave")
    public void submittedSave(@RequestBody MaterialTrialDTO materialTrialDTO) {
        iMaterialTrialService.commonCheck(materialTrialDTO.getMaterialTrial(), SampleStatusType.SUBMITTED.getValue());
        iMaterialTrialService.submittedSave(materialTrialDTO);
    }

    /**
     *已审批
     * @param materialTrial
     */
    @PostMapping("/approved")
    public void approved(@RequestBody MaterialTrial materialTrial) {
        iMaterialTrialService.commonCheck(materialTrial, SampleStatusType.APPROVED.getValue());
        iMaterialTrialService.updateMaterial(materialTrial,SampleStatusType.APPROVED.getValue());
    }

    /**
     *已驳回
     * @param materialTrial
     */
    @PostMapping("/rejected")
    public void rejected(@RequestBody MaterialTrial materialTrial) {
        iMaterialTrialService.commonCheck(materialTrial, SampleStatusType.APPROVED.getValue());
        iMaterialTrialService.updateMaterial(materialTrial,SampleStatusType.APPROVED.getValue());
    }

    /**
     *物料试用的待确认单
     */
    @GetMapping("/countConfirmed")
    public WorkCount countConfirmed() {
        return iMaterialTrialService.countConfirmed();
    }

    @PostMapping("/submitWithFlow")
    public Map<String,Object> submitWithFlow(@RequestBody MaterialTrialDTO materialTrialDTO){
        return   iMaterialTrialService.updateMaterialWithFlow(materialTrialDTO);
    }

    /**
     * 删除组织与品类试用信息
     * add by chensl26 2021-02-22
     * @param orgCateJournalId
     */
    @GetMapping("/deleteOrgCateJournal")
    public void deleteOrgCateJournal(@RequestParam Long orgCateJournalId) {
        iOrgCateJournalService.removeById(orgCateJournalId);
    }
}

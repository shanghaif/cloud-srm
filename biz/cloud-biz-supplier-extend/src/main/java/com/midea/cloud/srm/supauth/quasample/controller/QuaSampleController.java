package com.midea.cloud.srm.supauth.quasample.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.OrgCateBillType;
import com.midea.cloud.common.enums.SampleStatusType;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.quasample.dto.QuaSampleDTO;
import com.midea.cloud.srm.model.supplierauth.quasample.dto.RequestSampleDTO;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.supauth.entry.service.IFileRecordService;
import com.midea.cloud.srm.supauth.quasample.service.IQuaSampleService;
import com.midea.cloud.srm.supauth.review.service.IOrgCateJournalService;

/**
 *  <pre>
 *  样品确认表 前端控制器
 * </pre>
 *
 * @author zhuwl7
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 19:04:43
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/qua/quaSample")
public class QuaSampleController extends BaseController {

    @Autowired
    private IQuaSampleService iQuaSampleService;
    @Autowired
    private IOrgCateJournalService iOrgCateJournalService;
    @Autowired
    private IFileRecordService iFileRecordService;

    /**
     * 获取
     * @param sampleId
     */
    @GetMapping("/get")
    public QuaSampleDTO get(Long sampleId) {
    	Assert.notNull(sampleId, "sampleId不能为空");
    	QuaSampleDTO quaSampleDTO = new QuaSampleDTO();
    	QuaSample quaSample = iQuaSampleService.getById(sampleId);
    	Assert.notNull(quaSample, "样品确认单据不存在");
        quaSampleDTO.setQuaSample(quaSample);
        quaSampleDTO.setOrgCateJournals(iOrgCateJournalService.getOrgCateList(sampleId, OrgCateBillType.SAMPLE_FORM.getValue()));
        quaSampleDTO.setFileRecords(iFileRecordService.getFileRecord(sampleId, OrgCateBillType.SAMPLE_FORM.getValue()));
        return quaSampleDTO;
    }

    /**
     * 获取合格的样品 add by chensl26  2021-2-23
     * @param sampleId
     * @return
     */
    @GetMapping("/getQualifiedSample")
    public QuaSampleDTO getQualifiedSample(@RequestParam Long sampleId) {
        return iQuaSampleService.getQualifiedSample(sampleId);
    }

    /**
     * 新增
     * @param quaSample
     */
    @PostMapping("/add")
    public Long add(@RequestBody QuaSample quaSample) {
        return iQuaSampleService.addQuaSample(quaSample);
    }

    /**
     * 删除
     * @param sampleId
     */
    @GetMapping("/delete")
    public void delete(Long sampleId) {
        Assert.notNull(sampleId, "id不能为空");
        iQuaSampleService.removeById(sampleId);
    }

    /**
     * 修改
     * @param quaSample
     */
    @PostMapping("/modify")
    public Long modify(@RequestBody QuaSample quaSample) {
        return iQuaSampleService.modifyQuaSample(quaSample);
    }

    /**
     * 分页查询
     * @param quaSample
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<QuaSample> listPage(@RequestBody QuaSample quaSample) {
        PageUtil.startPage(quaSample.getPageNum(), quaSample.getPageSize());
        QueryWrapper<QuaSample> wrapper = new QueryWrapper<QuaSample>(quaSample);
        return new PageInfo<QuaSample>(iQuaSampleService.list(wrapper));
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/listAll")
    public List<QuaSample> listAll() {
        return iQuaSampleService.list();
    }


    /**
     * 分页查询
     * @param requestSampleDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<QuaSample> listPageByParam(@RequestBody RequestSampleDTO requestSampleDTO) {
        PageUtil.startPage(requestSampleDTO.getPageNum(), requestSampleDTO.getPageSize());
        return iQuaSampleService.listPageByParam(requestSampleDTO);
    }



    /**
     *批量删除
     * @param sampleIds
     */
    @PostMapping("/bathDeleteByList")
    public void bathDeleteByList(@RequestBody List<Long> sampleIds) {
        Assert.notEmpty(sampleIds, "sampleIds不能为空");
        iQuaSampleService.bathDeleteByList(sampleIds);
    }

    /**
     *暂存
     * @param quaSampleDTO
     */
    @PostMapping("/saveTemporary")
    public Long saveTemporary(@RequestBody QuaSampleDTO quaSampleDTO) {
        iQuaSampleService.commonCheck(quaSampleDTO.getQuaSample(), SampleStatusType.DRAFT.getValue());
        return   iQuaSampleService.saveOrUpdateSample(quaSampleDTO,SampleStatusType.DRAFT.getValue());
    }

    /**
     *发布
     * @param quaSampleDTO
     */
    @PostMapping("/publish")
    public Long publish(@RequestBody QuaSampleDTO quaSampleDTO) {
        iQuaSampleService.commonCheck(quaSampleDTO.getQuaSample(), SampleStatusType.PUBLISHED.getValue());
        return iQuaSampleService.saveOrUpdateSample(quaSampleDTO,SampleStatusType.PUBLISHED.getValue());
    }

    /**
     *确认
     * @param quaSample
     */
    @PostMapping("/confirmed")
    public void confirmed(@RequestBody QuaSample quaSample) {
        iQuaSampleService.commonCheck(quaSample, SampleStatusType.CONFIRMED.getValue());
        iQuaSampleService.updateQuaSample(quaSample, SampleStatusType.CONFIRMED.getValue());
    }

    /**
     *拒绝
     * @param quaSample
     */
    @PostMapping("/refused")
    public void refused(@RequestBody QuaSample quaSample) {
        iQuaSampleService.commonCheck(quaSample, SampleStatusType.REFUSED.getValue());
        iQuaSampleService.updateQuaSample(quaSample, SampleStatusType.REFUSED.getValue());
    }

    /**
     *提交保存
     * @param quaSampleDTO
     */
    @PostMapping("/submittedSave")
    public void submittedSave(@RequestBody QuaSampleDTO quaSampleDTO) {
        iQuaSampleService.commonCheck(quaSampleDTO.getQuaSample(), SampleStatusType.SUBMITTED.getValue());
        iQuaSampleService.submittedSave(quaSampleDTO);
    }


    /**
     *提交
     * @param quaSample
     */
    @PostMapping("/submitted")
    public void submitted(@RequestBody QuaSample quaSample) {
        iQuaSampleService.commonCheck(quaSample, SampleStatusType.SUBMITTED.getValue());
        iQuaSampleService.updateQuaSample(quaSample,SampleStatusType.SUBMITTED.getValue());
    }

    /**
     *已审批
     * @param quaSample
     */
    @PostMapping("/approved")
    public void approved(@RequestBody QuaSample quaSample) {
        iQuaSampleService.commonCheck(quaSample, SampleStatusType.APPROVED.getValue());
        iQuaSampleService.updateQuaSample(quaSample,SampleStatusType.APPROVED.getValue());
    }

    /**
     *已驳回
     * @param quaSample
     */
    @PostMapping("/rejected")
    public void rejected(@RequestBody QuaSample quaSample) {
        iQuaSampleService.commonCheck(quaSample, SampleStatusType.APPROVED.getValue());
        iQuaSampleService.updateQuaSample(quaSample,SampleStatusType.APPROVED.getValue());
    }

    /**
     *统计样品确认的待确认单
     */
    @GetMapping("/countConfirmed")
    public WorkCount countConfirmed() {
        return iQuaSampleService.countConfirmed();
    }


    @PostMapping("/submitWithFlow")
    public Map<String,Object> submitWithFlow(@RequestBody QuaSampleDTO quaSampleDTO){
        return iQuaSampleService.updateQuaSampleWithFlow(quaSampleDTO,SampleStatusType.SUBMITTED.getValue());
    }
}

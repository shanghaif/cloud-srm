package com.midea.cloud.srm.perf.scoring.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoring.ScoreManScoringV1;
import com.midea.cloud.srm.model.perf.scoring.dto.ScoreManScoringV1SubmitConfirmDTO;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsService;
import com.midea.cloud.srm.perf.scoring.service.IScoreManScoringV1Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  评分人绩效评分表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-21 17:26:15
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/perf/score-man-scoring-v1")
public class ScoreManScoringV1Controller extends BaseController {

    @Autowired
    private IScoreManScoringV1Service iScoreManScoringV1Service;

    @Resource
    private IPerfScoreItemsService iPerfScoreItemsService;


    /**
     * Description 分页查询评分人绩效评分信息
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     * @throws
     **/
    @PostMapping("/listScoreManScoringPage")
    public PageInfo<ScoreManScoringV1> listScoreManScoringPage(@RequestBody ScoreManScoringV1 scoreManScoringV1) {
        PageUtil.startPage(scoreManScoringV1.getPageNum(), scoreManScoringV1.getPageSize());
        return new PageInfo<ScoreManScoringV1>(iScoreManScoringV1Service.listScoreManScoringPage(scoreManScoringV1));
    }

    /**
     * 评分人绩效评分提交前获取哪些数据没有评分
     * @param scoreManScoringV1List
     * @return
     */
    @PostMapping("/confirmBeforeScoreManScoringSubmit")
    public BaseResult<ScoreManScoringV1SubmitConfirmDTO> confirmBeforeScoreManScoringSubmit(@RequestBody List<ScoreManScoringV1> scoreManScoringV1List) {
        Assert.isTrue(CollectionUtils.isNotEmpty(scoreManScoringV1List), "请选择进行提交的数据。");
        return iScoreManScoringV1Service.confirmBeforeScoreManScoringSubmit(scoreManScoringV1List);
    }

    /**
     * 评分人绩效评分获取项目列表
     * @param scoreManScoringV1List
     * @return
     */
    @PostMapping("/getScoreItemsAfterSubmit")
    public List<PerfScoreItems> getScoreItemsAfterSubmit(@RequestBody List<ScoreManScoringV1> scoreManScoringV1List) {
        Assert.isTrue(CollectionUtils.isNotEmpty(scoreManScoringV1List), "请选择进行提交的数据。");
        return iScoreManScoringV1Service.getScoreItemsAfterSubmit(scoreManScoringV1List);
    }

    /**
     * Description 保存评分人绩效评分集合（评分人绩效评分提交）
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.13
     **/
    @PostMapping("/saveScoreManScoring")
    @PreAuthorize("hasAuthority('perf:scoreManScoring:edit')")
    public String saveScoreManScoring(@RequestBody List<ScoreManScoringV1> scoreManScoringV1List){
        // 先更新评分人绩效评分 scc_perf_score_man_scoring_v1 表
        String result = iScoreManScoringV1Service.saveScoreManScoring(scoreManScoringV1List);
        if(ResultCode.SUCCESS.getMessage().equals(result)){
            // 更新项目信息（主要是更新评分回应）
            iPerfScoreItemsService.updateScoreItemsAndScorePeople(scoreManScoringV1List);
        }
        return result;
    }

    /**
     * 放弃评分
     * @param scoreManScoringV1List
     */
    @PostMapping("/abstention")
    public void abstention(@RequestBody List<ScoreManScoringV1> scoreManScoringV1List) {
        Assert.isTrue(CollectionUtils.isNotEmpty(scoreManScoringV1List), "请选择要放弃评分的数据记录。");
        iScoreManScoringV1Service.abstention(scoreManScoringV1List);
    }

    /**
     * 上传附件
     */
    @PostMapping("/uploadFile")
    public void updateFile(@RequestBody ScoreManScoringV1 scoreManScoringV1) {
        Assert.notNull(scoreManScoringV1.getScoreManScoringId(), "评分人绩效评分id不能为空。");
        iScoreManScoringV1Service.uploadFile(scoreManScoringV1);
    }

    /**
     * 附件删除
     */
    @PostMapping("/deleteFile")
    public void deleteFile(@RequestBody ScoreManScoringV1 scoreManScoringV1) {
        Assert.notNull(scoreManScoringV1.getScoreManScoringId(), "评分人绩效评分id不能为空。");
        Assert.notNull(scoreManScoringV1.getScoreManScoringFileId(), "附件id不能为空。");
        iScoreManScoringV1Service.deleteFile(scoreManScoringV1);
    }

    /**
     * @Description 获取导出数据总条数
     * @modified xiexh12@meicloud.com
     * @date 2021-01-28 19:24
     */
    @PostMapping("/getExportDataSize")
    public BaseResult getExportDataSize(@RequestBody ExportExcelParam<ScoreManScoringV1> scoreManScoringV1ExportParam) {
        ScoreManScoringV1 scoreManScoringV1 = scoreManScoringV1ExportParam.getQueryParam();
        BaseResult baseResult = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        List<ScoreManScoringV1> scoreManScoringV1List = iScoreManScoringV1Service.listScoreManScoringPage(scoreManScoringV1);
        int size = scoreManScoringV1List.size();
        baseResult.setData(size);
        return baseResult;
    }

    /**
     * @Description 评分人绩效评分excel自定义导出
     * @modified xiexh12@meicloud.com
     * 2021-01-28 19:44
     */
    @PostMapping("/exportScoreManScoringV1Excel")
    public BaseResult exportScoreManScoringV1Excel(@RequestBody ExportExcelParam<ScoreManScoringV1> scoreManScoringV1ExportParam, HttpServletResponse response) throws IOException {
        try {
            iScoreManScoringV1Service.exportScoreManScoringV1Excel(scoreManScoringV1ExportParam, response);
            return BaseResult.buildSuccess();
        } catch (Exception e) {
            return BaseResult.build(ResultCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    /**
     * 评分人绩效评分-导入文件模板下载
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importScoreManScoringV1Download")
    public void importScoreManScoringV1Download(HttpServletResponse response) throws Exception {
        iScoreManScoringV1Service.importScoreManScoringV1Download(response);
    }

    /**
     * 自定义导入文件
     * @param file
     */
    @RequestMapping("/importScoreManScoringV1Excel")
    public Map<String, Object> importScoreManScoringV1Excel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iScoreManScoringV1Service.importScoreManScoringV1Excel(file, fileupload);
    }

    /**
     * 查询通过excel导入的数据
     * @return
     */
    @PostMapping("/listExcelImportData")
    public List<ScoreManScoringV1> listExcelImportData() {
        return iScoreManScoringV1Service.listExcelImportData();
    }

    /**
     * 刷新通过excel导入的数据
     * @return
     */
    @PostMapping("/freshExcelImportData")
    public void freshExcelImportData() {
        iScoreManScoringV1Service.freshExcelImportData();
    }

}

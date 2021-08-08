package com.midea.cloud.srm.perf.scoring.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoring.PerfOverallScore;
import com.midea.cloud.srm.model.perf.scoring.ScoreManScoringV1;
import com.midea.cloud.srm.model.supplier.info.dto.PerfOverallScoreDto;
import com.midea.cloud.srm.perf.level.service.IPerfLevelService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsService;
import com.midea.cloud.srm.perf.scoring.service.IPerfOverallScoreService;
import com.midea.cloud.srm.perf.scoring.utils.OverallScoreExportUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  综合绩效得分主表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-16 11:50:39
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/scoring/perfOverallScore")
public class PerfOverallScoreController extends BaseController {

    /**综合绩效得分主Service*/
    @Resource
    private IPerfOverallScoreService iPerfOverallScoreService;

    /**绩效等级表Service*/
    @Resource
    private IPerfLevelService iPerfLevelService;
    /**评分绩效项目Service*/
    @Resource
    private IPerfScoreItemsService iPerfScoreItemsService;

    /**
     * Description 分页查询综合绩效得分信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.16
     **/
    @PostMapping("/listPerfOverallScorePage")
    public PageInfo<PerfOverallScore> listPage(@RequestBody PerfOverallScore perfOverallScore) {
        PageUtil.startPage(perfOverallScore.getPageNum(), perfOverallScore.getPageSize());
        return new PageInfo<>(iPerfOverallScoreService.findOverallScoreList(perfOverallScore));
    }

    /**
     * 查询指定供应商的绩效评分信息
     * @param vendorId
     * @return
     */
    @PostMapping("/getPerfOverallScoreVendorId")
    public List<PerfOverallScoreDto> getPerfOverallScoreVendorId(@RequestParam("vendorId") Long vendorId){
        return iPerfOverallScoreService.getPerfOverallScoreVendorId(vendorId);
    }

    /**
     * Description 获取有效的去重等级名称集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     * @throws BaseException
     **/
    @GetMapping("/findDistinctLevelNameList")
    public List<Map<String, Object>> findDistinctLevelNameList() throws BaseException{
        return iPerfLevelService.findDistinctLevelNameList();
    }

    /**
     * Description 获取已经计算的之后的评分绩效项目集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     * @throws BaseException
     **/
    @GetMapping("/findCalculatedScoreItemsList")
    public List<PerfScoreItems> findCalculatedScoreItemsList() throws BaseException{
        return iPerfScoreItemsService.findCalculatedScoreItemsList();
    }

    /**
     * Description 根据绩效指标绩效得分主表ID获取绩效指标绩效得分主表和子表信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     * @throws BaseException
     **/
    @GetMapping("/findOverallScorelById")
    public PerfOverallScore findOverallScorelById(Long overallScoreId) throws BaseException{
        PerfOverallScore overallScore = new PerfOverallScore();
        PerfOverallScore queryOverallScore = new PerfOverallScore();
        queryOverallScore.setOverallScoreId(overallScoreId);
        List<PerfOverallScore> overallScoreList = iPerfOverallScoreService.findOverallScoreAndSonList(queryOverallScore);
        if(CollectionUtils.isNotEmpty(overallScoreList) && null != overallScoreList.get(0)){
            overallScore = overallScoreList.get(0);
        }
        return overallScore;
    }

    /**
     * 供应商提交反馈
     * @param perfOverallScoreList
     */
    @PostMapping("/vendorSubmitFeedback")
    public void vendorSubmitFeedback(@RequestBody List<PerfOverallScore> perfOverallScoreList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(perfOverallScoreList), "请选择要进行提交反馈的记录。");
        iPerfOverallScoreService.vendorSubmitFeedback(perfOverallScoreList);
    }

    /**
     * Description 导出绩效综合查询列表标题
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.23
     **/
    @PostMapping("/exportOverallScoreExcelTitle")
    public Map<String,String> exportOverallScoreExcelTitle(){
        return OverallScoreExportUtils.getOverallScoreTitles();
    }

    /**
     * 导出文件
     * @param  overallScore 导出Excel类
     * @param response
     * @throws Exception
     */
    @PostMapping("/exportOverallScoreExcel")
    public void exportOverallScoreExcel(@RequestBody ExportExcelParam<PerfOverallScore> overallScore, HttpServletResponse response) throws Exception {
        iPerfOverallScoreService.exportPerfOverallScore(overallScore, response);
    }

    /**
     * 计算评分分数（测试用）
     * @return
     */
    @GetMapping("/listPerfOverallScoreAll")
    public boolean listAll(Long scoreItemsId ) {
        return iPerfOverallScoreService.generateScoreInfo(scoreItemsId);
    }

    /**
     * 多人评分计算平均分（测试用）
     * @param scoreManScoringV1
     */
    @PostMapping("/calculateAverageScore")
    public void calculateAverageScore(@RequestBody ScoreManScoringV1 scoreManScoringV1) {
        iPerfOverallScoreService.calculateAverageScore(scoreManScoringV1.getScoreItemsId());
    }

    /**
     * 供应商确认
     */
    @PostMapping("/vendorConfirm")
    public void vendorConfirm(@RequestBody List<PerfOverallScore> perfOverallScores) {
        Assert.isTrue(CollectionUtils.isNotEmpty(perfOverallScores), "请选择要进行确认的数据。");
        iPerfOverallScoreService.vendorConfirm(perfOverallScores);
    }

    /**
     * 上传附件
     */
    @PostMapping("/uploadFile")
    public void updateFile(@RequestBody PerfOverallScore perfOverallScore) {
        Assert.notNull(perfOverallScore.getOverallScoreId(), "综合绩效id不能为空。");
        iPerfOverallScoreService.uploadFile(perfOverallScore);
    }

    /**
     * 附件删除
     */
    @PostMapping("/deleteFile")
    public void deleteFile(@RequestBody PerfOverallScore perfOverallScore) {
        Assert.notNull(perfOverallScore.getOverallScoreId(), "综合绩效id不能为空。");
        Assert.notNull(perfOverallScore.getPerfOverallScoreFileId(), "附件id不能为空。");
        iPerfOverallScoreService.deleteFile(perfOverallScore);
    }

}

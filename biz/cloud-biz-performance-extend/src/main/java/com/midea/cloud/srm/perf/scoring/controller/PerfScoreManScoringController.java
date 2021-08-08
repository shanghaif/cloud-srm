package com.midea.cloud.srm.perf.scoring.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoring.dto.PerfScoreManScoringSaveDTO;
import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsService;
import com.midea.cloud.srm.perf.scoring.service.IPerfScoreManScoringService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *  <pre>
 *  评分人绩效评分表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-11 14:29:16
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/scoreManScoring")
public class PerfScoreManScoringController extends BaseController {

    @Resource
    private IPerfScoreManScoringService iPerfScoreManScoringService;

    /**绩效评分项目Service*/
    @Resource
    private IPerfScoreItemsService iPerfScoreItemsService;

    /**
     * Description 分页查询评分人绩效评分信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     * @throws
     **/
    @PostMapping("/listScoreManScoringPage")
    public PageInfo<PerfScoreManScoring> listScoreManScoringPage(@RequestBody PerfScoreManScoring scoreManScoring) {
        PageUtil.startPage(scoreManScoring.getPageNum(), scoreManScoring.getPageSize());
        return new PageInfo<PerfScoreManScoring>(iPerfScoreManScoringService.listScoreManScoringPage(scoreManScoring));
    }

    /**
     * Description 保存评分人绩效评分集合
     * @Param scoreManScoringsList 绩效模型指标集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.13
     **/
    @PostMapping("/saveScoreManScoring")
    @PreAuthorize("hasAuthority('perf:scoreManScoring:edit')")
    public String saveScoreManScoring(@RequestBody List<PerfScoreManScoring> scoreManScoringsList){
        // 先更新评分人绩效评分 scc_perf_score_man_scoring 表
        String result = iPerfScoreManScoringService.saveScoreManScoring(scoreManScoringsList);
        if(ResultCode.SUCCESS.getMessage().equals(result)){
            // 更新项目信息（主要是更新评分回应）
            iPerfScoreItemsService.updateScoreItemsScorePeople(scoreManScoringsList);
        }
        return result;
    }

    /**
     * 绩效评分项目 任务分配
     * @param saveDTO
     */
    @PostMapping("/allocate")
    public void allocate(@RequestBody PerfScoreManScoringSaveDTO saveDTO) {
        // 至少选择一项进行分配的任务
        List<PerfScoreManScoring> perfScoreManScorings = saveDTO.getPerfScoreManScorings();
        Assert.isTrue(CollectionUtils.isNotEmpty(perfScoreManScorings), "请至少选择一项进行分配的任务。");
        // 绩效模板不能为空
        Long templateHeadId = saveDTO.getTemplateHeadId();
        Assert.notNull(templateHeadId, "绩效模板不能为空，请选择后重试。");
        Long scoreItemsId = saveDTO.getScoreItemsId();
        // 评分人不能为空
        PerfScoreItemsMan perfScoreItemsMan = saveDTO.getPerfScoreItemsMan();
        Assert.notNull(perfScoreItemsMan, "评分人不能为空。");

        iPerfScoreManScoringService.allocate(perfScoreManScorings, perfScoreItemsMan, scoreItemsId, templateHeadId);
    }

    /**
     * 上传附件
     */
    @PostMapping("/uploadFile")
    public void updateFile(@RequestBody PerfScoreManScoring perfScoreManScoring) {
        Assert.notNull(perfScoreManScoring.getScoreManScoringId(), "评分人绩效评分id不能为空。");
        iPerfScoreManScoringService.uploadFile(perfScoreManScoring);
    }

    /**
     * 附件删除
     */
    @PostMapping("/deleteFile")
    public void deleteFile(@RequestBody PerfScoreManScoring perfScoreManScoring) {
        Assert.notNull(perfScoreManScoring.getScoreManScoringId(), "评分人绩效评分id不能为空。");
        Assert.notNull(perfScoreManScoring.getScoreManScoringFileId(), "附件id不能为空。");
        iPerfScoreManScoringService.deleteFile(perfScoreManScoring);
    }

}

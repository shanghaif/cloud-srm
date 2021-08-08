package com.midea.cloud.srm.perf.scoreproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.perf.template.PerfTemplateStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.scoreproject.dto.PerfScoreItemSupIndDTO;
import com.midea.cloud.srm.model.perf.scoreproject.dto.PerfScoreItemsDTO;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemManSupInd;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItems;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateHeader;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemManSupIndService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsManService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsService;
import com.midea.cloud.srm.perf.scoreproject.service.IPerfScoreItemsSupService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateHeaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  绩效评分项目主信息表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-05 17:33:40
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/scoreproject/scoreItems")
@Slf4j
public class PerfScoreItemsController extends BaseController {

    /**绩效评分项目Service*/
    @Autowired
    private IPerfScoreItemsService iPerfScoreItemsService;

    /**绩效模板Service*/
    @Autowired
    private IPerfTemplateHeaderService iPerfTemplateHeaderService;

    /**绩效评分项目供应商*/
    @Autowired
    private IPerfScoreItemsSupService iPerfScoreItemsSupService;

    /**绩效评分项目评分人*/
    @Autowired
    private IPerfScoreItemsManService iPerfScoreItemsManService;

    /**绩效评分项目评分人-供应商指标Service*/
    @Resource
    private IPerfScoreItemManSupIndService iPerfScoreItemManSupIndService;

    /**绩效评分项目评分人-供应商Service*/
//    @Resource
//    private IPerfScoreItemsManSupService iPerfScoreItemsManSupService;

    /**绩效评分项目评分人-绩效指标Service*/
//    @Resource
//    private IPerfScoreItemsManIndicatorService iPerfScoreItemsManIndicatorService;

    /**
     * 分页查询
     * @param perfScoreItems
     * @return
     */
    @PostMapping("/listPerfScoreItemsPage")
//    @AuthData(module = MenuEnum.PERFORMANCE_SCORE_ITEMS)
    public PageInfo<PerfScoreItems> listPage(@RequestBody PerfScoreItems perfScoreItems) {
        PageUtil.startPage(perfScoreItems.getPageNum(), perfScoreItems.getPageSize());
        QueryWrapper<PerfScoreItems> wrapper = new QueryWrapper<PerfScoreItems>();
        if(null != perfScoreItems){
            String projectName = perfScoreItems.getProjectName();
            String evaluationPeriod = perfScoreItems.getEvaluationPeriod();
            String projectStatus = perfScoreItems.getProjectStatus();
            String approveStatus = perfScoreItems.getApproveStatus();
            String templateName = perfScoreItems.getTemplateName();
            if(StringUtils.isNotEmpty(projectName)){
                wrapper.like("PROJECT_NAME", projectName);
            }
            if(StringUtils.isNotEmpty(evaluationPeriod)){
                wrapper.eq("EVALUATION_PERIOD", evaluationPeriod);
            }
            if(StringUtils.isNotEmpty(projectStatus)){
                wrapper.eq("PROJECT_STATUS", projectStatus);
            }
            if(StringUtils.isNotEmpty(approveStatus)){
                wrapper.eq("APPROVE_STATUS", approveStatus);
            }
            if(StringUtils.isNotEmpty(templateName)){
                wrapper.like("TEMPLATE_NAME", templateName);
            }
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<PerfScoreItems>(iPerfScoreItemsService.list(wrapper));
    }

    /**
     * Description 获取有效的绩效模型集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.06
     * @throws
     **/
    @GetMapping("/getValidTemplateHeader")
    public List<PerfTemplateHeader> getValidTemplateHeader(){
        PerfTemplateHeader queryTemplateHeader = new PerfTemplateHeader();
        queryTemplateHeader.setDeleteFlag(Enable.N.toString());
        queryTemplateHeader.setTemplateStatus(PerfTemplateStatusEnum.VALID.getValue());
        List<PerfTemplateHeader> perfTemplateHeaderList = iPerfTemplateHeaderService.list(new QueryWrapper<>(queryTemplateHeader));
        return perfTemplateHeaderList;
    }

    /**
     * Description 根据绩效评分项目ID获取绩效评分项目供应商
     * @Param scoreItemsId 绩效评分项目ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     * @throws BaseException
     **/
    @GetMapping("/getPerfScoreItemsSupList")
    public List<PerfScoreItemsSup> getPerfScoreItemsSupList(Long scoreItemsId){
        Assert.notNull(scoreItemsId, "id不能为空");
        List<PerfScoreItemsSup> perfScoreItemsSupList = new ArrayList<>();
        PerfScoreItemsSup perfScoreItemsSup = new PerfScoreItemsSup();
        perfScoreItemsSup.setScoreItemsId(scoreItemsId);
        try{
            perfScoreItemsSupList = iPerfScoreItemsSupService.list(new QueryWrapper<PerfScoreItemsSup>(perfScoreItemsSup));
        }catch (Exception e){
            log.error("根据绩效评分项目ID:"+scoreItemsId+"获取绩效评分项目供应商时报错："+e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return perfScoreItemsSupList;
    }

    /**
     * Description 根据绩效模型头ID获取绩效指标信息集合
     * @Param templateHeadId 绩效模型头ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     **/
    @GetMapping("/getPerfTemplateLineByHeaderId")
    public List<PerfTemplateLine> getPerfTemplateLineByHeaderId(Long templateHeadId){
        return iPerfScoreItemsService.getPerfTemplateLineByHeaderId(templateHeadId);
    }

    /**
     * Description 根据绩效评分供应商获取评分绩效评分人-供应商指标表集合
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.02
     * @throws
     **/
    @PostMapping("/getScoreItemsSupIndicatorList")
    public List<PerfScoreItemSupIndDTO> getScoreItemsSupIndicatorList(@RequestBody List<PerfScoreItemsSup> scoreItemsSupList){
        return iPerfScoreItemsService.getScoreItemsSupIndicatorList(scoreItemsSupList);
    }

    /**
     * Description 根据条件获取绩效评分项目和子表集合
     * @Param scoreItemsId 绩效评分项目ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.08
     **/
    @GetMapping("/findPerfScoreItemsById")
    public PerfScoreItemsDTO findPerfScoreItemsById(Long scoreItemsId) {
        Assert.notNull(scoreItemsId, "id不能为空");
        return iPerfScoreItemsService.findScoreItemsAndSonList(scoreItemsId);
    }

    /**
     * Description 新增绩效评分项目和子表信息
     * @Param perfScoreItemsDTO 改绩效评分项目和子表信息DTO
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     **/
    @PostMapping("/savePerfScoreItems")
    @PreAuthorize("hasAuthority('perf:scoreproject:edit')")
    public String savePerfScoreItems(@RequestBody PerfScoreItemsDTO perfScoreItemsDTO) {
        return iPerfScoreItemsService.saveOrUpdateScoreItemsAndSon(perfScoreItemsDTO);
    }

    /**
     * Description 修改绩效评分项目和子表信息
     * @Param perfScoreItemsDTO 改绩效评分项目和子表信息DTO
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     **/
    @PostMapping("/updatePerfScoreItems")
    @PreAuthorize("hasAuthority('perf:scoreproject:edit')")
    public String updatePerfScoreItems(@RequestBody PerfScoreItemsDTO perfScoreItemsDTO) {
        return iPerfScoreItemsService.saveOrUpdateScoreItemsAndSon(perfScoreItemsDTO);
    }

    /**
     * @Description 保存或更新绩效评分项目和子表信息（目前在用的）
     * @Author xiexh12@meicloud.com
     * @Date 2021.01.21
     **/
    @PostMapping("/saveOrUpdatePerfScoreItems")
    @PreAuthorize("hasAuthority('perf:scoreproject:edit')")
    public Long saveOrUpdatePerfScoreItems(@RequestBody PerfScoreItemsDTO perfScoreItemsDTO) {
        return iPerfScoreItemsService.saveOrUpdatePerfScoreItems(perfScoreItemsDTO);
    }

    /**
     * Description 根据绩效评分项目ID获取绩效评分项目供应商信息
     * @Param scoreItemsId 绩效评分项目ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     * @throws BaseException
     **/
    @GetMapping("/findPerfScoreItemsSupByItemsId")
    public List<PerfScoreItemsSup> findPerfScoreItemsSupByItemsId(Long scoreItemsId){
        Assert.notNull(scoreItemsId, "id不能为空");
        List<PerfScoreItemsSup> scoreItemsSupList = new ArrayList<>();
        try{
            PerfScoreItemsSup scoreItemsSup = new PerfScoreItemsSup();
            scoreItemsSup.setScoreItemsId(scoreItemsId);
            scoreItemsSupList = iPerfScoreItemsSupService.list(new QueryWrapper<>(scoreItemsSup));
        }catch (Exception e){
            log.error("根据绩效评分项目ID:"+scoreItemsId+"获取绩效评分项目供应商信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return scoreItemsSupList;
    }

    /**
     * Description 根据ID删除绩效评分项目供应商和项目评分人-供应商信息
     * @Param scoreItemsSupId 绩效评分项目供应商ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     * @throws
     **/
    @PostMapping("/delPerfScoreItemsSupById")
    @PreAuthorize("hasAuthority('perf:scoreproject:edit')")
    public String deletePerfScoreItemsSupById(@RequestBody PerfScoreItemsSup scoreItemsSup){
        Assert.notNull(scoreItemsSup, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        return iPerfScoreItemsSupService.deletePerfScoreItemsSupById(scoreItemsSup.getScoreItemsSupId());
    }

    /**
     * Description 根据ID删除绩效评分项目评分人和子表信息
     * @Param scoreItemsSupId 绩效评分项目供应商ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     * @throws
     **/
    @PostMapping("/delPerfScoreItemsManById")
    @PreAuthorize("hasAuthority('perf:scoreproject:edit')")
    public String deletePerfScoreItemsManById(@RequestBody PerfScoreItemsMan scoreItemsMan){
        Assert.notNull(scoreItemsMan, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        return iPerfScoreItemsManService.deletePerfScoreItemsManById(scoreItemsMan.getScoreItemsManId());
    }

    /**
     * Description 批量保存绩效评分项目供应商表-供应商指标信息(供应商指标配置)
     * @Param scoreItemsManSupList 绩效评分项目供应商表-供应商指标集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     **/
    @PostMapping("/updateScoreItemManSupInd")
    @PreAuthorize("hasAuthority('perf:scoreproject:edit')")
    public String updateScoreItemManSupInd(@RequestBody List<PerfScoreItemManSupInd> scoreItemManSupIndList){
        return iPerfScoreItemManSupIndService.saveOrUpdateScoreItemManSupInd(scoreItemManSupIndList);
    }

    /**
     * Description 批量保存绩效评分项目供应商表-供应商信息(供应商权限)
     * @Param scoreItemsManSupList 绩效评分项目供应商表-供应商集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     **/
/*    @PostMapping("/updateScoreItemsManSup")
    @PreAuthorize("hasAuthority('perf:scoreproject:edit')")
    public String updateScoreItemsManSup(@RequestBody List<PerfScoreItemsManSup> scoreItemsManSupList){
        return iPerfScoreItemsManSupService.saveOrUpdateScoreItemsManSup(scoreItemsManSupList);
    }*/

    /**
     * Description 批量保存绩效评分项目供应商表-绩效指标信息(指标权限)
     * @Param scoreItemsManSupList 绩效评分项目供应商表-绩效指标集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.09
     **/
/*    @PostMapping("/updateScoreItemsManIndicator")
    @PreAuthorize("hasAuthority('perf:scoreproject:edit')")
    public String updateScoreItemsManIndicator(@RequestBody List<PerfScoreItemsManIndicator> scoreItemsManIndicatorList){
        return iPerfScoreItemsManIndicatorService.saveOrUpdateScoreItemsManIndicator(scoreItemsManIndicatorList);
    }*/

    /**
     * Description 通知评分人(项目状态修改为通知评分人, 并生成评分人绩效评分表)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     **/
    @PostMapping("/notifyScorers")
    @PreAuthorize("hasAuthority('perf:scoreProject:notifyScorers')")
    public String notifyScorers(@RequestBody PerfScoreItems scoreItems) throws Exception{
        return iPerfScoreItemsService.notifyScorers(scoreItems);
    }

    /**
     * Description 废弃绩效评项目(项目状态修改为废弃)
     * @Param PerfScoreItems.scoreItemsId 绩效评分项目ID
     * @Param PerfScoreItems.projectStatus 绩效评分项目状态(废弃)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     **/
    @PostMapping("/abandonScoreItems")
    @PreAuthorize("hasAuthority('perf:scoreProject:abandon')")
    public String abandonScoreItems(@RequestBody PerfScoreItems scoreItems){
        return iPerfScoreItemsService.updateScoreItems(scoreItems);
    }

    /**
     * Description 废弃绩效评项目(项目状态修改为废弃) 已通知评分的才能调用
     * @Param PerfScoreItems.scoreItemsId 绩效评分项目ID
     * @Param PerfScoreItems.projectStatus 绩效评分项目状态(废弃)
     * @return
     * @Author huangxq44@meicloud.com
     * @Date 2020.06.10
     **/
    @GetMapping("/abandonAlreadyScoreItems")
    public BaseResult abandonAlreadyScoreItems(Long scoreItemId){
        iPerfScoreItemsService.abandonAlready(scoreItemId);
        return BaseResult.buildSuccess();
    }

    /**
     * 项目计算评分前查询哪些评分人没有评分
     * @param scoreItems
     * @return
     */
    @PostMapping("/confirmBeforeCalculate")
    public BaseResult<String> confirmBeforeCalculate(@RequestBody PerfScoreItems scoreItems){
        Assert.notNull(scoreItems.getScoreItemsId(), "绩效项目id不能为空。");
        return iPerfScoreItemsService.confirmBeforeCalculate(scoreItems);
    }

    /**
     * Description 计算绩效评项目(修改项目状态为已计算得分,并计算评分结果)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     **/
    @PostMapping("/calculateScoreItems")
    @PreAuthorize("hasAuthority('perf:scoreProject:calculate')")
    public String calculateScoreItems(@RequestBody PerfScoreItems scoreItems){
        return iPerfScoreItemsService.updateScoreItems(scoreItems);
    }

    /**
     * Description 提交绩效评项目流程(修改项目状态为‘结果未发布’,审批状态改为“已批准”)
     * @Param PerfScoreItems.scoreItemsId 绩效评分项目ID
     * @Param PerfScoreItems.projectStatus 绩效评分项目状态(结果未发布)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     **/
    @PostMapping("/submitProcessScoreItems")
    @PreAuthorize("hasAuthority('perf:scoreProject:publish')")
    public String submitProcessScoreItems(@RequestBody PerfScoreItems scoreItems){
        return iPerfScoreItemsService.updateScoreItems(scoreItems);
    }

    /**
     * Description 发布绩效评项目(项目状态修改为结果已发布)
     * @Param PerfScoreItems.scoreItemsId 绩效评分项目ID
     * @Param PerfScoreItems.projectStatus 绩效评分项目状态(结果已发布)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.10
     **/
    @PostMapping("/publishScoreItems")
    @PreAuthorize("hasAuthority('perf:scoreProject:process')")
    public String publishScoreItems(@RequestBody PerfScoreItems scoreItems){
        return iPerfScoreItemsService.updateScoreItems(scoreItems);
    }

    /**
     * Description 根据ID删除绩效项目和子表信息
     * @Param socreItemsId 绩效项目表ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.22
     * @throws BaseException
     **/
    @PostMapping("/delScoreItemsAndSon")
    @PreAuthorize("hasAuthority('perf:scoreProject:del')")
    public String delScoreItemsAndSon(@RequestBody PerfScoreItems scoreItems) throws BaseException{
        Assert.notNull(scoreItems, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        return iPerfScoreItemsService.delScoreItemsAndSon(scoreItems.getScoreItemsId());
    }

    /**
     * 绩效评分项目复制
     * @param perfScoreItems
     */
    @PostMapping("/copyScoreItems")
    public void copyScoreItems(@RequestBody PerfScoreItems perfScoreItems) {
        Assert.notNull(perfScoreItems.getScoreItemsId(), "绩效项目id不能为空。");
        iPerfScoreItemsService.copyScoreItems(perfScoreItems.getScoreItemsId());
    }

    @PostMapping("/checkIsSaveOrUpdateScoreItems")
    public String checkIsSaveOrUpdateScoreItems(@RequestBody PerfScoreItemsDTO scoreItems) throws BaseException{
        return iPerfScoreItemsService.checkIsSaveOrUpdateScoreItems(scoreItems.getScoreItemsId(),scoreItems);
    }

    @PostMapping("/submitProcessScoreItemsWithFlow")
    @PreAuthorize("hasAuthority('perf:scoreProject:publish')")
    public Map<String,Object> submitProcessScoreItemsWithFlow(@RequestBody PerfScoreItems scoreItems){
        return iPerfScoreItemsService.updateScoreItemsWithFlow(scoreItems);
    }



}

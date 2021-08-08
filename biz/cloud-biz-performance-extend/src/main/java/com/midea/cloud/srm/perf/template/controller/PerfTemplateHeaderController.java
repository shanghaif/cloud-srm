package com.midea.cloud.srm.perf.template.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.inditors.dto.IndicatorsHeaderDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateHeaderQueryDTO;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateLineDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateCategory;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateDimWeight;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateHeader;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsHeaderService;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsLineService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateCategoryService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateDimWeightService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateHeaderService;
import com.midea.cloud.srm.perf.template.service.IPerfTemplateLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  绩效模型头表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 16:41:07
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/template")
@Slf4j
public class PerfTemplateHeaderController extends BaseController {

    /**绩效模型头表Service接口*/
    @Autowired
    private IPerfTemplateHeaderService iPerfTemplateHeaderService;

    /**绩效模板品类行Service接口*/
    @Autowired
    private IPerfTemplateCategoryService iPerfTemplateCategoryService;

    /**绩效模型头行Service接口*/
    @Autowired
    private IPerfTemplateLineService iPerfTemplateLineService;

    /*指标库头表Service接口*/
    @Autowired
    private IIndicatorsHeaderService iIndicatorsHeaderService;

    /*指标库头行Service接口*/
    @Autowired
    private IIndicatorsLineService iIndicatorsLineService;

    /**绩效指标维度Service*/
    @Autowired
    private IPerfTemplateDimWeightService iPerfTemplateDimWeightService;

    /**
     * Description 分页查询绩效模型头表信息
     * @Param pefTemplateHeader 绩效模型头表实体类
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws
     **/
    @PostMapping("/listPefTemplateHeaderPage")
    public PageInfo<PerfTemplateHeader> listPefTemplateHeaderPage(@RequestBody PerfTemplateHeaderQueryDTO queryDTO) {
        PageUtil.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        return new PageInfo<PerfTemplateHeader>(iPerfTemplateHeaderService.listPefTemplateHeaderPage(queryDTO));
    }

    /**
     * Description 绩效模型启动/禁用
     * @Param pefTempateHeadId 绩效模型头表Id（实际使用）
     * @Param templateStatus 模版状态（实际使用）
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @PostMapping("/enablePefTemplateHeader")
    @PreAuthorize("hasAuthority('perf:template:enable')")
    public String enablePefTemplateHeader(@RequestBody PerfTemplateHeader pefTemplateHeader) {
        Assert.notNull(pefTemplateHeader, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        return iPerfTemplateHeaderService.enablePefTemplateHeader(pefTemplateHeader.getTemplateHeadId(), pefTemplateHeader.getTemplateStatus());
    }

    /**
     * Description
     * @Param pefTempateHeadId 根据绩效模型头表Id删除记录（实际使用）
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @PostMapping("/delPefTemplateHeader")
    @PreAuthorize("hasAuthority('perf:template:del')")
    public void delPefTemplateHeader(@RequestBody PerfTemplateHeader pefTemplateHeader) {
        Assert.notNull(pefTemplateHeader, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        iPerfTemplateHeaderService.delPefTemplateHeader(pefTemplateHeader.getTemplateHeadId());
    }

    /**
     * Description 根据ID获取有效的绩效模型头、采购分类表、绩效指标维度和绩效指标行表信息
     * @Param perfTemplateHeadId 绩效模型头表ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws
     **/
    @GetMapping("/findPerTemplateByTemplateHeadId")
    public PerfTemplateDTO findPerTemplateByTemplateHeadId(Long perfTemplateHeadId) {
        return iPerfTemplateHeaderService.findPerTemplateByTemplateHeadId(perfTemplateHeadId);
    }

    /**
     * Description 获取指标类型-指标维度(启动、不删除和不重复的指标维度和指标类型)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.30
     **/
    @GetMapping("/findIndicatorsHeaderDimensionList")
    public List<Map<String, Object>> findIndicatorsHeaderDimensionList(){
        return iIndicatorsHeaderService.findIndicatorsHeaderDimensionList();
    }

    /**
     * Description 根据指标类型和指标维度获取有效的指标头信息
     * @Param indicatorsType 指标类型
     * @Param indicatorsDimension 指标维度
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.30
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @GetMapping("/findIndicatorsHeaderByDimension")
    public List<IndicatorsHeaderDTO> findIndicatorsLineByDimension(String indicatorType, String indicatorDimension){
        try{
            return iIndicatorsHeaderService.findIndicatorsHeaderByDimension(indicatorType, indicatorDimension,
                    Enable.N.toString(), Enable.Y.toString());
        }catch (Exception e){
            log.error("根据指标库类型和指标维度获取有效的指标头信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
    }

    /**
     * Description 根据模板头id查询维度和指标信息
     * @Param indicatorsType 指标类型
     * @Param indicatorsDimension 指标维度
     * @return
     * @Author xiexh12@meicloud.com
     * @Date 2021.01.21
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @GetMapping("/listTemplateLinesByTemplateHeaderId")
    public List<PerfTemplateLine> listTemplateLinesByTemplateHeaderId(Long templateHeaderId){
        Assert.notNull(templateHeaderId, "绩效模板id不能为空。");
        return iPerfTemplateHeaderService.listTemplateLinesByTemplateHeaderId(templateHeaderId);
    }

    /**
     * Description 根据绩效模型指标ID或指标库行ID获取绩效指标详情信息
     * @Param indicatorHeaderId 指标库行ID
     * @Param perfTemplateLineId 绩效模型指标ID
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.01
     * @throws BaseException
     **/
    @GetMapping("/findPerfTemplateLineAndIndsLine")
    public PerfTemplateLineDTO findPerfTemplateLineAndIndsLine(Long indicatorHeaderId, Long perfTemplateLineId){
        return iPerfTemplateLineService.findPerfTemplateLineAndIndsLine(indicatorHeaderId, perfTemplateLineId);
    }

    /**
     * Description
     * @Param pefTempateHeadId 根据绩效模型行表Id删除记录
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.28
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @PostMapping("/delPefTemplateLine")
    @PreAuthorize("hasAuthority('perf:template:del')")
    public String delPefTemplateLine(@RequestBody PerfTemplateLine pefTemplateLine) {
        Assert.notNull(pefTemplateLine, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        String result = "";
        boolean isDelte = iPerfTemplateLineService.removeById(pefTemplateLine.getTemplateLineId());
        if(isDelte){
            result = ResultCode.SUCCESS.getMessage();
        }else{
            result = ResultCode.OPERATION_FAILED.getMessage();
        }
        return result;
    }

    /**
     * Description 保存绩效模型信息
     * @Param perfTemplateDTO 保存绩效模型DTO
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.01
     * @throws BaseException
     **/
    @PostMapping("/savePerfTemplate")
    @PreAuthorize("hasAuthority('perf:template:edit')")
    public String savePerfTemplate(@RequestBody PerfTemplateDTO perfTemplateDTO) {
        PerfTemplateHeader perfTemplateHeader = perfTemplateDTO.getPerfTemplateHeader();
        iPerfTemplateHeaderService.checkTemplateHeader(perfTemplateHeader);
        List<PerfTemplateCategory> perfTemplateCategoryList = perfTemplateDTO.getPerfTemplateCategoryList();
        iPerfTemplateCategoryService.checkTemplateCategories(perfTemplateCategoryList);
        return iPerfTemplateHeaderService.saveOrUpdatePerfTemplate(perfTemplateDTO);

    }

    /**
     * Description 修改绩效模型信息
     * @Param perfTemplateDTO 修改绩效模型DTO
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.01
     * @throws BaseException
     **/
    @PostMapping("/updatePerfTemplate")
    @PreAuthorize("hasAuthority('perf:template:edit')")
    public String updatePerfTemplate(@RequestBody PerfTemplateDTO perfTemplateDTO) {
        return iPerfTemplateHeaderService.saveOrUpdatePerfTemplate(perfTemplateDTO);
    }

    /**
     * Description
     * @Param dimWeightId 根据绩效模型维度Id删除记录(包括绩效维度、绩效指标和绩效指标行信息)
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.02
     * @throws com.midea.cloud.common.exception.BaseException
     **/
    @PostMapping("/delPefTemplateDimWeight")
    @PreAuthorize("hasAuthority('perf:template:del')")
    public String delPefTemplateDimWeight(@RequestBody PerfTemplateDimWeight perfTemplateDimWeight) {
        Assert.notNull(perfTemplateDimWeight, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        return iPerfTemplateDimWeightService.delPefTemplateDimWeight(perfTemplateDimWeight.getDimWeightId());
    }

    /**
     * 根据绩效模型查询供应商
     * 根据绩效模型行上的品类去组织品类表查询供应商
     * @param perfTemplateHeader
     * @return
     */
    @PostMapping("/listCompanysByPerfTemplateHeader")
    public List<CompanyInfo> listCompanysByPerfTemplateHeader(@RequestBody PerfTemplateHeader perfTemplateHeader) {
        Assert.notNull(perfTemplateHeader.getTemplateHeadId(), "所选绩效模型为空，请选择后重试。");
        return iPerfTemplateHeaderService.listCompanysByPerfTemplateHeader(perfTemplateHeader);
    }

    /**
     * 绩效模板复制
     * @param perfTemplateHeader
     * @return
     */
    @PostMapping("/copyPerfTemplateHeader")
    public void copyPerfTemplateHeader(@RequestBody PerfTemplateHeader perfTemplateHeader) {
        Assert.notNull(perfTemplateHeader.getTemplateHeadId(), "绩效模板头id不能为空，请选择后重试。");
        iPerfTemplateHeaderService.copyPerfTemplateHeader(perfTemplateHeader.getTemplateHeadId());
    }

}

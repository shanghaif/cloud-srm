package com.midea.cloud.srm.perf.level.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.level.entity.PerfLevel;
import com.midea.cloud.srm.perf.common.PerfLevelConst;
import com.midea.cloud.srm.perf.level.service.IPerfLevelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  绩效等级表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-03 09:26:13
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/perfLevel")
@Slf4j
public class PerfLevelController extends BaseController {

    @Autowired
    private IPerfLevelService iPerfLevelService;

    /**
     * Description 分页查询绩效等级信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     **/
    @PostMapping("/listPerfLevelPage")
    public PageInfo<PerfLevel> listPerLevelPage(@RequestBody PerfLevel perfLevel) {
        PageUtil.startPage(perfLevel.getPageNum(), perfLevel.getPageSize());

        QueryWrapper<PerfLevel> wrapper = new QueryWrapper<PerfLevel>();
        if(null != perfLevel) {
            String levelName = perfLevel.getLevelName();
            BigDecimal scoreStart = perfLevel.getScoreStart();
            String status = perfLevel.getStatus();
            if (StringUtils.isNotBlank(levelName)) {
                wrapper.like("LEVEL_NAME", levelName);
            }
            if (StringUtils.isNotBlank(status)) {
                wrapper.eq("STATUS", status);
            }
            if (null != scoreStart) {
                wrapper.le("SCORE_START", scoreStart);
                wrapper.ge("SCORE_END", scoreStart);
            }
        }
        List<PerfLevel> perfLevelList = new ArrayList<>();
        try{
            wrapper.orderByDesc("ORGANIZATION_ID");
            wrapper.orderByDesc("CREATION_DATE");
            perfLevelList = iPerfLevelService.list(wrapper);
        }catch (Exception e){
            log.error("分页查询绩效等级信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return new PageInfo<PerfLevel>(perfLevelList);
    }

    /**
     * Description 根据ID获取绩效等级信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     **/
    @GetMapping("/getPerfLevel")
    public PerfLevel getPerfLevel(Long levelId) {
        Assert.notNull(levelId, "id不能为空");
        PerfLevel perfLevel = new PerfLevel();
        try{
            perfLevel = iPerfLevelService.getById(levelId);
        }catch (Exception e){
            log.error("根据ID获取绩效等级信息时报错：",e);
            throw new BaseException(ResultCode.UNKNOWN_ERROR.getMessage());
        }
        return perfLevel;
    }

    /**
     * Description 新增绩效等级信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws BaseException
     **/
    @PostMapping("/savePerfLevel")
    @PreAuthorize("hasAuthority('perf:level:edit')")
    public String savePerfLevel(@RequestBody PerfLevel perfLevel) {
        return iPerfLevelService.saveOrUpdatePerfLevel(perfLevel);
    }

    /**
     * Description 修改绩效等级信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws BaseException
     **/
    @PostMapping("/updatePerfLevel")
    @PreAuthorize("hasAuthority('perf:level:edit')")
    public String updatePerfLevel(@RequestBody PerfLevel perfLevel) {
        return iPerfLevelService.saveOrUpdatePerfLevel(perfLevel);
    }

    /**
     * Description 根据ID删除绩效等级信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws BaseException
     **/
    @PostMapping("/deletePerfLevel")
    @PreAuthorize("hasAuthority('perf:level:delete')")
    public String deletePerfLevel(@RequestBody PerfLevel perfLevel) {
        Assert.notNull(perfLevel, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        return iPerfLevelService.deletePerfLevel(perfLevel.getLevelId());
    }

    /**
     * Description 启动/禁用绩效等级信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws BaseException
     **/
    @PostMapping("/enablePerfLevel")
    @PreAuthorize("hasAuthority('perf:level:enable')")
    public String enablePerfLevel(@RequestBody PerfLevel perfLevel) {
        Assert.notNull(perfLevel, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        Long perfLevelId = perfLevel.getLevelId();
        String status = perfLevel.getStatus();
        Assert.notNull(perfLevelId, "id不能为空");
        Assert.notNull(status, PerfLevelConst.STATUS_NOT_NULL);
        PerfLevel oldPerfLevel = iPerfLevelService.getById(perfLevelId);
        // 判断有效的绩效名称、采购组织不能重复
        iPerfLevelService.checkRepeatLevelNameAndOrgId(oldPerfLevel, true);
        // 当状态为启用时，校验得分区间不能重叠
        perfLevel = iPerfLevelService.getById(perfLevelId);
        perfLevel.setStatus(status);
        iPerfLevelService.checkScoreOverlap(perfLevel);
        try {
            PerfLevel updatePerLevel = new PerfLevel();
            updatePerLevel.setLevelId(perfLevelId);
            updatePerLevel.setStatus(status);
            iPerfLevelService.updateById(updatePerLevel);
        }catch (Exception e){
            log.error("启动/禁用绩效等级信息时报错：",e);
            return ResultCode.OPERATION_FAILED.getMessage();
        }
        return ResultCode.SUCCESS.getMessage();
    }

    /**
     * Description 导出‘绩效等级数据导入模板’
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws IOException
     **/
    @RequestMapping("/exportPerfLevelModel")
    public void exportPerfLevelModel(HttpServletResponse response) throws IOException {
        iPerfLevelService.exportPerfLevelModel(response);
    }

    /**
     * Description 通过导入Excel数据保存绩效等级信息
     * @Param file Excel文件
     * @Param fileupload
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.03
     * @throws Exception
     **/
    @RequestMapping("/importExcelInsertLevel")
    public String importExcelInsertLevel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iPerfLevelService.importExcelInsertLevel(file,fileupload);
    }


}

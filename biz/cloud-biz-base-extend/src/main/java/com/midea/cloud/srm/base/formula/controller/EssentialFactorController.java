package com.midea.cloud.srm.base.formula.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.controller.BaseCheckController;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorService;
import com.midea.cloud.srm.model.base.formula.dto.create.EssentialFactorCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.EssentialFactorQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.EssentialFactorUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.EssentialFactor;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *  公式要素表 前端控制器
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:27:13
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bid/essential-factor")
@Slf4j
public class EssentialFactorController extends BaseCheckController {

    private final IEssentialFactorService iEssentialFactorService;


    public EssentialFactorController(IEssentialFactorService iEssentialFactorService) {
        this.iEssentialFactorService = iEssentialFactorService;
    }

    /**
     * 新增要素
     *
     * @param dto
     * @param result
     * @return
     */
    @PostMapping("/createEssentialFactor")
    public EssentialFactorVO createEssentialFactor(@Valid @RequestBody EssentialFactorCreateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return iEssentialFactorService.createEssentialFactor(dto);
    }

    @GetMapping("/deleteEssentialFactorById")
    public Boolean deleteEssentialFactoryById(@RequestParam("id") Long id) {
        return iEssentialFactorService.deleteEssentialFactorById(id);
    }

    @PostMapping("/saveEssentialFactorTemporary")
    public EssentialFactorVO saveTemporary(@Valid @RequestBody EssentialFactorUpdateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        dto.setEssentialFactorStatus(StuffStatus.DRAFT.getStatus());
        return iEssentialFactorService.updateEssentialFactor(dto);
    }
    @PostMapping("/activeEssentialFactor")
    public EssentialFactorVO activeEssentialFactor(@Valid @RequestBody EssentialFactorUpdateDto dto, BindingResult result){
        checkParamBeforeHandle(log,result,dto);
        dto.setEssentialFactorStatus(StuffStatus.ACTIVE.getStatus());
        return iEssentialFactorService.updateEssentialFactor(dto);
    }
    @PostMapping("/queryEssentialFactorByPage")
    public PageInfo<EssentialFactorVO> queryByPage(@RequestBody EssentialFactorQueryDto dto) {
        return iEssentialFactorService.queryEssentialFactorByPage(dto);
    }

    /**
     * 查找单条要素信息
     * @param essentialFactorId
     * @return
     */
    @GetMapping("/queryEssentialFactorById")
    public EssentialFactorVO  queryEssentialFactorById(Long essentialFactorId){
        return iEssentialFactorService.queryEssentialFactorById(essentialFactorId);
    }


    @GetMapping("/inActiveEssentialFactor")
    public Boolean inActive(@RequestParam Long id){
        return iEssentialFactorService.updateStatus(StuffStatus.INVAILD,id);
    }

    /**
     * 通过要素名称批量获取公式要素信息
     * @param essentialFactorNameList
     * @return
     */
    @PostMapping("/listByEssentialFactorName")
    public List<EssentialFactor> listByEssentialFactorName(@RequestBody List<String> essentialFactorNameList){
        return iEssentialFactorService.listByEssentialFactorName(essentialFactorNameList);

    }


}

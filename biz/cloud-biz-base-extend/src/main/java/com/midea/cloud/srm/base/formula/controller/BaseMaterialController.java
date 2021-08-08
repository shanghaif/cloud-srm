package com.midea.cloud.srm.base.formula.controller;

import com.github.pagehelper.PageInfo;

import com.midea.cloud.common.controller.BaseCheckController;
import com.midea.cloud.srm.base.formula.service.IBaseMaterialService;
import com.midea.cloud.srm.model.base.formula.dto.create.BaseMaterialCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.BaseMaterialQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.BaseMaterialUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterial;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <pre>
 *  基本材料表 前端控制器
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
@RequestMapping("/bid/base-material")
@Slf4j
public class BaseMaterialController extends BaseCheckController {

    private final IBaseMaterialService iBaseMaterialService;

    public BaseMaterialController(IBaseMaterialService iBaseMaterialService) {
        this.iBaseMaterialService = iBaseMaterialService;
    }

    /**
     * 新增基本材料
     *
     * @param dto
     * @param result
     * @return
     */
    @PostMapping("/createBaseMaterial")
    public BaseMaterialVO add(@Valid @RequestBody BaseMaterialCreateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        return iBaseMaterialService.createBaseMaterial(dto);
    }

    @PostMapping("/queryBaseMaterialByPage")
    public PageInfo<BaseMaterial> queryBaseMaterialByPage(@RequestBody BaseMaterialQueryDto dto) {
        return iBaseMaterialService.queryBaseMaterialByPage(dto);
    }

    @GetMapping("/deleteBaseMaterialById")
    public Boolean deleteById(@RequestParam("id") Long id) {
        return iBaseMaterialService.deleteBaseMaterialById(id);
    }

    /**
     * 暂存和生效都属于修改操作
     *
     * @param dto
     * @param result
     * @return
     */
    @PostMapping("/saveBaseMaterialTemporary")
    public BaseMaterialVO saveTemporary(@Valid @RequestBody BaseMaterialUpdateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        dto.setBaseMaterialStatus(StuffStatus.DRAFT.getStatus());
        return iBaseMaterialService.updateBaseMaterial(dto);
    }

    @PostMapping("/activeBaseMaterial")
    public BaseMaterialVO active(@Valid @RequestBody BaseMaterialUpdateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        dto.setBaseMaterialStatus(StuffStatus.ACTIVE.getStatus());
        return iBaseMaterialService.updateBaseMaterial(dto);
    }

    @GetMapping("/inActiveBaseMateria")
    public Boolean inActive(@RequestParam Long id) {
        return iBaseMaterialService.updateStatus(StuffStatus.INVAILD, id);
    }


}

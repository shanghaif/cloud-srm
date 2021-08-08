package com.midea.cloud.srm.base.formula.controller;


import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.controller.BaseCheckController;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.srm.base.formula.service.IBaseMaterialPriceService;
import com.midea.cloud.srm.base.formula.service.impl.DefaultPriceCalculateTemplate;
import com.midea.cloud.srm.model.base.formula.dto.create.BaseMaterialCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.create.BaseMaterialPriceCreateDto;
import com.midea.cloud.srm.model.base.formula.dto.query.BaseMaterialPriceQueryDto;
import com.midea.cloud.srm.model.base.formula.dto.update.BaseMaterialPriceUpdateDto;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterialPrice;
import com.midea.cloud.srm.model.base.formula.enums.StuffStatus;
import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialPriceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <pre>
 *  基本材料价格表 前端控制器
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
@RequestMapping("/bid/base-material-price")
@Slf4j
public class BaseMaterialPriceController extends BaseCheckController {

    private final IBaseMaterialPriceService iBaseMaterialPriceService;
    private final DefaultPriceCalculateTemplate template;

    public BaseMaterialPriceController(IBaseMaterialPriceService iBaseMaterialPriceService,
                                       DefaultPriceCalculateTemplate template) {
        this.iBaseMaterialPriceService = iBaseMaterialPriceService;
        this.template = template;
    }

    /**
     * 新增基价
     */
    @PostMapping("/createBaseMaterialPrice")
    public BaseMaterialPriceVO add(@Valid @RequestBody BaseMaterialPriceCreateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        BaseMaterialPriceVO baseMaterialPrice = iBaseMaterialPriceService.createBaseMaterialPrice(dto);
        return baseMaterialPrice;
    }

    @PostMapping("/queryBaseMaterialPriceByPage")
    public PageInfo<BaseMaterialPrice> queryBaseMaterialPriceByPage(@RequestBody BaseMaterialPriceQueryDto dto) {
        return iBaseMaterialPriceService.listBaseMaterialPriceByPage(dto);
    }

    @GetMapping("/deleteBaseMaterialPriceById")
    public Boolean deleteById(@RequestParam Long id) {
        return iBaseMaterialPriceService.deleteBaseMaterialPriceById(id);
    }

    @PostMapping("/saveBaseMaterialPriceTemporary")
    public BaseMaterialPriceVO updateById(@Valid @RequestBody BaseMaterialPriceUpdateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        dto.setBaseMaterialPriceStatus(StuffStatus.DRAFT.getStatus());
        return iBaseMaterialPriceService.updateById(dto);
    }


    @PostMapping("/activeBaseMaterialPrice")
    public BaseMaterialPriceVO active(@Valid @RequestBody BaseMaterialPriceUpdateDto dto, BindingResult result) {
        checkParamBeforeHandle(log, result, dto);
        dto.setBaseMaterialPriceStatus(StuffStatus.ACTIVE.getStatus());
        BaseMaterialPriceCreateDto createDto = BeanCopyUtil.copyProperties(dto, BaseMaterialPriceCreateDto::new);
        template.handle(createDto);
        return iBaseMaterialPriceService.updateById(dto);
    }

    @GetMapping("/inActiveBaseMaterialPrice")
    public Boolean inActive(@RequestParam Long id) {
        return iBaseMaterialPriceService.updateStatus(StuffStatus.INVAILD, id);
    }

    /**
     * 废弃
     * @param id
     * @return
     */
    @GetMapping("/dropBaseMaterialPrice")
    public Boolean dropBaseMaterialPrice(@RequestParam Long id) {
        return iBaseMaterialPriceService.updateStatus(StuffStatus.ABANDON, id);
    }

}

package com.midea.cloud.srm.logistics.template.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateHeadService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.template.dto.LogisticsTemplateDTO;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateHead;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateLine;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  物流采购申请模板头表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 16:25:07
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/logistics-template-head")
public class LogisticsTemplateHeadController extends BaseController {

    @Autowired
    private ILogisticsTemplateHeadService iLogisticsTemplateHeadService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public LogisticsTemplateHead get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iLogisticsTemplateHeadService.getById(id);
    }

    /**
    * 新增
    * @param logisticsTemplateHead
    */
    @PostMapping("/add")
    public void add(@RequestBody LogisticsTemplateHead logisticsTemplateHead) {
        Long id = IdGenrator.generate();
        logisticsTemplateHead.setTemplateHeadId(id);
        iLogisticsTemplateHeadService.save(logisticsTemplateHead);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iLogisticsTemplateHeadService.removeById(id);
    }

    /**
    * 修改
    * @param logisticsTemplateHead
    */
    @PostMapping("/modify")
    public void modify(@RequestBody LogisticsTemplateHead logisticsTemplateHead) {
        iLogisticsTemplateHeadService.updateById(logisticsTemplateHead);
    }

    /**
    * 分页条件查询
    * @param logisticsTemplateHead
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<LogisticsTemplateHead> listPageByParam(@RequestBody LogisticsTemplateHead logisticsTemplateHead) {
        PageUtil.startPage(logisticsTemplateHead.getPageNum(), logisticsTemplateHead.getPageSize());
        return new PageInfo<LogisticsTemplateHead>(iLogisticsTemplateHeadService.listByParam(logisticsTemplateHead));
    }

    /**
     * 根据头id获取模板头行
     * @param headId
     * @return
     */
    @GetMapping("/listTemplateLinesByHeadId")
    public LogisticsTemplateDTO listTemplateLinesByHeadId(@RequestParam("headId") Long headId){
        Optional.ofNullable(headId).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("物流模板头id为空.")));
        return iLogisticsTemplateHeadService.listTemplateDTOByHeadId(headId);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<LogisticsTemplateHead> listAll() {
        return iLogisticsTemplateHeadService.list();
    }

    /**
     * 暂存
     */
    @PostMapping("/saveTemporary")
    public Long saveTemporary(@RequestBody LogisticsTemplateDTO logisticsTemplateDTO) {
        return iLogisticsTemplateHeadService.saveOrUpdateTemplateDTO(logisticsTemplateDTO, LogisticsStatus.DRAFT.getValue());
    }

    /**
     * 删除数据
     * @param headId
     */
    @GetMapping("/deleteByHeadId")
    public void deleteByHeadId(@RequestParam("headId") Long headId) {
        Optional.ofNullable(headId).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("模板头id为空, 找不到对应的模板.")));
        Assert.isTrue(Objects.equals(LogisticsStatus.DRAFT.getValue(), iLogisticsTemplateHeadService.getById(headId).getStatus()), "只有拟定状态才可以删除.");
        iLogisticsTemplateHeadService.deleteByHeadId(headId);
    }

    /**
     * 批量删除采购申请模板id
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> headIds){
        iLogisticsTemplateHeadService.batchDelete(headIds);
    }

    /**
     * 根据头id生效
     * @param headId
     */
    @GetMapping("/effectiveByHeadId")
    public void effectiveByHeadId(@RequestParam("headId") Long headId) {
        Optional.ofNullable(headId).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("模板头id为空, 找不到对应的模板.")));
        //Assert.isTrue(Objects.equals(LogisticsStatus.DRAFT.getValue(), iLogisticsTemplateHeadService.getById(headId).getStatus()), "只有拟定状态才可以生效.");
        iLogisticsTemplateHeadService.updateTemplateHeadStatus(headId, LogisticsStatus.EFFECTIVE.getValue());
    }

    /**
     * 根据头id失效
     * @param headId
     */
    @GetMapping("/inEffectiveByHeadId")
    public void inEffectiveByHeadId(@RequestParam("headId") Long headId) {
        Optional.ofNullable(headId).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("模板头id为空, 找不到对应的模板.")));
        Assert.isTrue(Objects.equals(LogisticsStatus.EFFECTIVE.getValue(), iLogisticsTemplateHeadService.getById(headId).getStatus()), "只有生效状态才可以失效.");
        iLogisticsTemplateHeadService.updateTemplateHeadStatus(headId, LogisticsStatus.INEFFECTIVE.getValue());
    }

}

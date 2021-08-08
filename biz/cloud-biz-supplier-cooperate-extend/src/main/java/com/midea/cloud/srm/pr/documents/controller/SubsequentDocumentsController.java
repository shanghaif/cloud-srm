package com.midea.cloud.srm.pr.documents.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.documents.entity.SubsequentDocuments;
import com.midea.cloud.srm.model.pm.pr.documents.param.RemoveParam;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.pr.documents.service.ISubsequentDocumentsService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  后续单据表 前端控制器
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-05 15:34:30
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/documents/subsequentDocuments")
public class SubsequentDocumentsController extends BaseController {

    @Autowired
    private ISubsequentDocumentsService iSubsequentDocumentsService;
    @Autowired
    private IRequirementLineService requirementLineService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public SubsequentDocuments get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSubsequentDocumentsService.getById(id);
    }

    /**
     * 新增
     *
     * @param subsequentDocuments
     */
    @PostMapping("/add")
    public void add(@RequestBody SubsequentDocuments subsequentDocuments) {
        Long id = IdGenrator.generate();
        subsequentDocuments.setSubsequentDocumentsId(id);
        iSubsequentDocumentsService.save(subsequentDocuments);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iSubsequentDocumentsService.removeById(id);
    }

    /**
     * 修改
     *
     * @param subsequentDocuments
     */
    @PostMapping("/modify")
    public void modify(@RequestBody SubsequentDocuments subsequentDocuments) {
        iSubsequentDocumentsService.updateById(subsequentDocuments);
    }

    /**
     * 分页查询
     *
     * @param subsequentDocuments
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<SubsequentDocuments> listPage(@RequestBody SubsequentDocuments subsequentDocuments) {
        PageUtil.startPage(subsequentDocuments.getPageNum(), subsequentDocuments.getPageSize());
        QueryWrapper<SubsequentDocuments> wrapper = new QueryWrapper<SubsequentDocuments>(subsequentDocuments);
        return new PageInfo<SubsequentDocuments>(iSubsequentDocumentsService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/subsequentDocumentsList")
    public List<SubsequentDocuments> subsequentDocumentsList(@RequestBody SubsequentDocuments subsequentDocuments) {
        Assert.notNull(subsequentDocuments, "后续单据不存在。");
        Assert.isTrue(subsequentDocuments.getRequirementLineId() != null, "找不到对应的后续单据。");
        QueryWrapper<SubsequentDocuments> wrapper = new QueryWrapper<>();
        wrapper.eq("REQUIREMENT_LINE_ID", subsequentDocuments.getRequirementLineId());
        return iSubsequentDocumentsService.list(wrapper);
    }

    /**
     * 删除与单据行相关的id
     *
     * @param followFormId
     */
    @GetMapping("/deleteByFollowFormId")
    public Boolean deleteByRequirementLineId(Long followFormId) {
        Assert.notNull(followFormId, "单据id不能为空");
        QueryWrapper<SubsequentDocuments> wrapper = new QueryWrapper<>();
        wrapper.eq("FOLLOW_FORM_ID", followFormId);
        return iSubsequentDocumentsService.remove(wrapper);
    }

    @PostMapping("/deleletByRowNumAndFolloFormId")
    public Boolean deleletByRowNumAndFolloFormId(@RequestBody RemoveParam param) {

        if (Objects.isNull(param.getFormNo()) || CollectionUtils.isEmpty(param.getParams())) {
            return false;
        }
        List<Long> lineId = new LinkedList<>();
        param.getParams().forEach((k, v) -> {
            requirementLineService.list(Wrappers.lambdaQuery(RequirementLine.class)
                    .select(RequirementLine::getRequirementLineId)
                    .in(RequirementLine::getRowNum, v)
                    .eq(RequirementLine::getRequirementHeadNum, k)
            ).forEach(e -> lineId.add(e.getRequirementLineId()));
        });
        iSubsequentDocumentsService.remove(Wrappers.lambdaQuery(SubsequentDocuments.class)
                .in(SubsequentDocuments::getRequirementLineId, lineId)
                .eq(SubsequentDocuments::getFollowFormId, param.getFormNo())
        );
        return true;
    }

    /**
     * 新增
     *
     * @param subsequentDocuments
     */
    @PostMapping("/addSubsequentDocuments")
    public void addSubsequentDocuments(@RequestBody SubsequentDocuments subsequentDocuments) {
        Assert.notNull(subsequentDocuments, "新增对象不能为空。");
        Assert.isTrue(StringUtils.isNotEmpty(subsequentDocuments.getSubsequentDocumentsNumber()), "后续单据编码不能为空。");
        Assert.isTrue(StringUtils.isNotEmpty(subsequentDocuments.getIsubsequentDocumentssType()), "后续单据类型不能为空。");
        Assert.notNull(subsequentDocuments, "新增对象不能为空。");
        Assert.notNull(subsequentDocuments, "新增对象不能为空。");
        Long id = IdGenrator.generate();
        subsequentDocuments.setSubsequentDocumentsId(id);

        iSubsequentDocumentsService.save(subsequentDocuments);
    }

}

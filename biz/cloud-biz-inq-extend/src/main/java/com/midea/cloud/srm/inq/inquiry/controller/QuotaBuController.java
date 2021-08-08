package com.midea.cloud.srm.inq.inquiry.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaBuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaBuService;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaBu;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  配额事业部中间表 前端控制器
 * </pre>
 *
 * @author yourname@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/inquiry/quotaBu")
public class QuotaBuController extends BaseController {

    @Autowired
    private IQuotaBuService iQuotaBuService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public QuotaBu get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuotaBuService.getById(id);
    }

    /**
     * 新增
     *
     * @param quotaBu
     */
    @PostMapping("/quotaBuAdd")
    public void quotaBuAdd(@RequestBody QuotaBu quotaBu) {
        Long id = IdGenrator.generate();
        quotaBu.setId(id);
        iQuotaBuService.save(quotaBu);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuotaBuService.removeById(id);
    }

    /**
     * 修改
     *
     * @param quotaBu
     */
    @PostMapping("/modify")
    public void modify(@RequestBody QuotaBu quotaBu) {
        iQuotaBuService.updateById(quotaBu);
    }

    /**
     * 分页查询
     *
     * @param quotaBu
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<QuotaBu> listPage(@RequestBody QuotaBuDTO quotaBu) {
        PageUtil.startPage(quotaBu.getPageNum(), quotaBu.getPageSize());
        return new PageInfo<QuotaBu>(iQuotaBuService.quotaBuList(quotaBu));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<QuotaBu> listAll() {
        return iQuotaBuService.list();
    }


    @PostMapping("/isSameQuotaInBuIds")
    public Boolean isSameQuotaInBus(@RequestBody Collection<String> buIds) {
        if(CollectionUtils.isEmpty(buIds)){
            return true;
        }
        int count =0;
        for (String buId : buIds) {
            if(!StringUtils.isEmpty(buId)){
                count++;
            }
        }
        if(count<=1){
            return true;
        }
        return iQuotaBuService.list(Wrappers.lambdaQuery(QuotaBu.class)
                .select(QuotaBu::getQuotaId)
                .in(QuotaBu::getBuCode, buIds)).stream().map(QuotaBu::getQuotaId)
                .distinct().count() == 1;
    }
}

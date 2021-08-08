package com.midea.cloud.srm.inq.inquiry.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IAgreementRatioService;
import com.midea.cloud.srm.model.inq.inquiry.entity.AgreementRatio;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  配额-协议比例 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/agreementRatio")
public class AgreementRatioController extends BaseController {

    @Autowired
    private IAgreementRatioService iAgreementRatioService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public AgreementRatio get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iAgreementRatioService.getById(id);
    }

    /**
    * 新增或保存
    * @param agreementRatio
    */
    @PostMapping("/AgreementRatioAdd")
    public void AgreementRatioAdd(@RequestBody List<AgreementRatio> agreementRatio) {
        Assert.isTrue(CollectionUtils.isNotEmpty(agreementRatio),"保存或修改的对象不能为空。");
        iAgreementRatioService.agreementRatioAdd(agreementRatio);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iAgreementRatioService.removeById(id);
    }

    /**
    * 修改
    * @param agreementRatio
    */
    @PostMapping("/modify")
    public void modify(@RequestBody AgreementRatio agreementRatio) {
        iAgreementRatioService.updateById(agreementRatio);
    }

    /**
    * 分页查询
    * @param agreementRatio
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<AgreementRatio> listPage(@RequestBody AgreementRatio agreementRatio) {
        PageUtil.startPage(agreementRatio.getPageNum(), agreementRatio.getPageSize());
        QueryWrapper<AgreementRatio> wrapper = new QueryWrapper<AgreementRatio>(agreementRatio);
        return new PageInfo<AgreementRatio>(iAgreementRatioService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<AgreementRatio> listAll() { 
        return iAgreementRatioService.list();
    }
 
}

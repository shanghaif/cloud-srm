package com.midea.cloud.srm.inq.inquiry.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.midea.cloud.common.enums.inq.QuotaStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.inquiry.service.impl.QuotaCalculateService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaDTO;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateParam;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateParameter;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IQuotaService;
import com.midea.cloud.srm.model.inq.inquiry.entity.Quota;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  配额表 前端控制器
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
@RequestMapping("/inquiry/quota")
public class QuotaController extends BaseController {

    @Autowired
    private IQuotaService iQuotaService;
    @Autowired
    private QuotaCalculateService quotaCalculateService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/getQuota")
    public QuotaDTO getQuota(Long id) {
        Assert.notNull(id, "id不能为空");
        return iQuotaService.getQuota(id);
    }

    /**
    * 新增
    * @param quotaDTO
    */
    @PostMapping("/updateAndAdd")
    public void updateAndAdd(@RequestBody QuotaDTO quotaDTO) {
        Assert.notNull(quotaDTO, "传输对象不能为空");
        Assert.notNull(quotaDTO.getQuota(), "单据内容不能为空");
        iQuotaService.updateAndAdd(quotaDTO);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iQuotaService.removeById(id);
    }

    /**
    * 修改
    * @param quota
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Quota quota) {
        iQuotaService.updateById(quota);
    }

    /**
    * 分页查询
    * @param quotaDTO
    * @return
    */
    @PostMapping("/quotaListPage")
    public PageInfo<Quota> quotaListPage(@RequestBody QuotaDTO quotaDTO) {
        PageUtil.startPage(quotaDTO.getPageNum(), quotaDTO.getPageSize());
        return new PageInfo<Quota>(iQuotaService.quotaList(quotaDTO));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Quota> listAll() { 
        return iQuotaService.list();
    }
    /**
     * 配额配置生效
     */
    @GetMapping("/getQuotaCIF")
    public void getQuotaCIF(Long id) {
        Assert.notNull(id, "id不能为空");
        UpdateWrapper<Quota> wrapper = new UpdateWrapper<>();
        wrapper.set("QUOTA_STATUS", QuotaStatus.get("EFFECT"));
        wrapper.eq("QUOTA_ID",id);
        iQuotaService.update(wrapper);
    }

    /**
     * 智能决标
     * @param quotaCalculateParameterList
     * @return
     */
    @PostMapping("/getCalculate")
    public Map<Integer,QuotaCalculateResult> getCalculate(@RequestBody List<QuotaCalculateParameter> quotaCalculateParameterList){
        Assert.isTrue(CollectionUtils.isNotEmpty(quotaCalculateParameterList),"quotaCalculateParameter不能为空");
        return iQuotaService.getCalculate(quotaCalculateParameterList);
    }

    /**
     * 计算配额
     * @param parameterList
     * @return
     */
    @PostMapping("/getNewCalculate")
    public List<QuotaCalculateParam> calculate(@RequestBody List<QuotaCalculateParam> parameterList){
        return quotaCalculateService.calculate(parameterList);
    }

}

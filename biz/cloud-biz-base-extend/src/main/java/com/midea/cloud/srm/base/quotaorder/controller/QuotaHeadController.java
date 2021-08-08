package com.midea.cloud.srm.base.quotaorder.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.base.quotaorder.service.IQuotaHeadService;
import com.midea.cloud.srm.base.quotaorder.service.IQuotaLineService;
import com.midea.cloud.srm.model.base.quotaorder.QuotaHead;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaHeadDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaHeadParamDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaLineDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaParamDto;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  配额比例头表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:00:12
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quotaorder")
public class QuotaHeadController extends BaseController {

    @Autowired
    private IQuotaHeadService iQuotaHeadService;
    @Resource
    private IQuotaLineService iQuotaLineService;

    /**
     * 分页查询
     * @param quotaHeadDto
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<QuotaHeadDto> listPage(@RequestBody QuotaHeadDto quotaHeadDto) {
        return iQuotaHeadService.listPage(quotaHeadDto);
    }

    /**
    * 获取配额详情
    * @param quotaHeadId
    */
    @GetMapping("/get")
    public QuotaHead get(Long quotaHeadId) {
        Assert.notNull(quotaHeadId, "id不能为空");
        return iQuotaHeadService.get(quotaHeadId);
    }

    /**
    * 新增
    * @param quotaHead
    */
    @PostMapping("/add")
    public Long add(@RequestBody QuotaHead quotaHead) {
        return iQuotaHeadService.addOrUpdate(quotaHead);
    }
    
    /**
    * 删除
    * @param quotaHeadId
    */
    @GetMapping("/delete")
    public void delete(Long quotaHeadId) {
        Assert.notNull(quotaHeadId, "quotaHeadId不能为空");
        iQuotaHeadService.removeById(quotaHeadId);
    }

    /**
    * 修改
    * @param quotaHead
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody QuotaHead quotaHead) {
        return iQuotaHeadService.addOrUpdate(quotaHead);
    }

    /**
     * 根据物料ID和组织ID查找有效的配额
     * @return orgId+itemId - QuotaHead
     */
    @PostMapping("/queryQuotaHeadByOrgIdItemId")
    public Map<String,QuotaHead> queryQuotaHeadByOrgIdItemId(@RequestBody QuotaParamDto quotaParamDto){
        return iQuotaHeadService.queryQuotaHeadByOrgIdItemId(quotaParamDto);
    }

    /**
     * 检查检查配额数据中的供应商必须在价格库中具备有效价格。
     * @return 错误信息
     */
    @PostMapping("/checkVendorIfValidPrice")
    public String checkVendorIfValidPrice(@RequestBody QuotaHeadParamDto quotaHeadParamDto) throws ParseException {
        return iQuotaHeadService.checkVendorIfValidPrice(quotaHeadParamDto.getQuotaHeadId(), quotaHeadParamDto.getRequirementDate());
    }

    /**
     * 根据组织ID+物料ID+有效时间: 查找有效价格的配额
     * @return 错误信息
     */
    @PostMapping("/getQuotaHeadByOrgIdItemIdDate")
    public QuotaHead getQuotaHeadByOrgIdItemIdDate(@RequestBody QuotaParamDto quotaParamDto){
        return iQuotaHeadService.getQuotaHeadByOrgIdItemIdDate(quotaParamDto);
    }

    /**
     * 根据物料ID和组织ID查找有效的配额, 并且配额的供应商有存在有效价格
     * @return orgId+itemId - QuotaHead
     */
    @PostMapping("/queryQuotaHeadByOrgIdItemIdAndPriceValid")
    public Map<String,QuotaHead> queryQuotaHeadByOrgIdItemIdAndPriceValid(@RequestBody QuotaParamDto quotaParamDto){
        return iQuotaHeadService.queryQuotaHeadByOrgIdItemIdAndPriceValid(quotaParamDto);
    }

    /**
     * 创建采购订单回写数据
     * @param quotaLineDtos
     */
    @PostMapping("/updateQuotaLineByQuotaLineDtos")
    public void updateQuotaLineByQuotaLineDtos(@RequestBody List<QuotaLineDto> quotaLineDtos){
        iQuotaHeadService.updateQuotaLineByQuotaLineDtos(quotaLineDtos);
    }

    /**
     * 采购订单删除回写数据
     * @param quotaLineDtos
     */
    @PostMapping("/rollbackQuotaLineByQuotaLineDtos")
    public void rollbackQuotaLineByQuotaLineDtos(@RequestBody List<QuotaLineDto> quotaLineDtos){
        iQuotaHeadService.rollbackQuotaLineByQuotaLineDtos(quotaLineDtos);
    }
}

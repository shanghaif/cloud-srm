package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.IErpBranchBankService;
import com.midea.cloud.srm.model.base.organization.entity.ErpBranchBank;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  银行分行信息（隆基银行分行数据同步） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-14 12:29:12
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/erp-branch-bank")
public class ErpBranchBankController extends BaseController {

    @Autowired
    private IErpBranchBankService iErpBranchBankService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ErpBranchBank get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iErpBranchBankService.getById(id);
    }

    /**
    * 新增
    * @param erpBranchBank
    */
    @PostMapping("/add")
    public void add(@RequestBody ErpBranchBank erpBranchBank) {
        Long id = IdGenrator.generate();
        erpBranchBank.setBranchBankId(id);
        iErpBranchBankService.save(erpBranchBank);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iErpBranchBankService.removeById(id);
    }

    /**
    * 修改
    * @param erpBranchBank
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ErpBranchBank erpBranchBank) {
        iErpBranchBankService.updateById(erpBranchBank);
    }

    /**
    * 分页查询
    * @param erpBranchBank
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ErpBranchBank> listPage(@RequestBody ErpBranchBank erpBranchBank) {
        PageUtil.startPage(erpBranchBank.getPageNum(), erpBranchBank.getPageSize());
        QueryWrapper<ErpBranchBank> wrapper = new QueryWrapper<ErpBranchBank>(erpBranchBank);
        return new PageInfo<ErpBranchBank>(iErpBranchBankService.list(wrapper));
    }

    /**
    * 公司信息导入校验
    * @return
    */
    @PostMapping("/listAll")
    public List<ErpBranchBank> listAll(@RequestBody List<ErpBranchBank> erpBranchBanks) {
        return iErpBranchBankService.listAll(erpBranchBanks);
    }

    /**
     * 根据开户行查询银行分行编码
     * @param openingBanks
     * @return
     */
    @PostMapping("/getUnionCodeByOpeningBanks")
    public Map<String, String> getUnionCodeByOpeningBanks(@RequestBody List<String> openingBanks) {
        return iErpBranchBankService.getUnionCodeByOpeningBanks(openingBanks);
    }
}

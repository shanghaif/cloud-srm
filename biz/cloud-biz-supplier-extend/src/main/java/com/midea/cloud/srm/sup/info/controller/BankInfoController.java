package com.midea.cloud.srm.sup.info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.sup.info.service.IBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  联系人信息 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-11 09:57:11
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/info/bankInfo")
public class BankInfoController extends BaseController {

    @Autowired
    private IBankInfoService iBankInfoService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BankInfo get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBankInfoService.getById(id);
    }

    /**
    * 新增
    * @param bankInfo
    */
    @PostMapping("/addBankInfo")
    public void addBankInfo(@RequestBody BankInfo bankInfo) {
        Long id = IdGenrator.generate();
        bankInfo.setBankInfoId(id);
        iBankInfoService.save(bankInfo);
    }
    
    /**
    * 删除
    * @param bankInfoId
    */
    @GetMapping("/delete")
    public void delete(Long bankInfoId) {
        Assert.notNull(bankInfoId, "id不能为空");
        iBankInfoService.removeById(bankInfoId);
    }

    /**
    * 修改
    * @param bankInfo
    */
    @PostMapping("/modify")
    public void modify(BankInfo bankInfo) {
        iBankInfoService.updateById(bankInfo);
    }

    /**
    * 分页查询
    * @param bankInfo
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BankInfo> listPage(BankInfo bankInfo) {
        PageUtil.startPage(bankInfo.getPageNum(), bankInfo.getPageSize());
        QueryWrapper<BankInfo> wrapper = new QueryWrapper<BankInfo>(bankInfo);
        return new PageInfo<BankInfo>(iBankInfoService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BankInfo> listAll() { 
        return iBankInfoService.list();
    }


    /**
     * 根据公司ID获取银行信息
     * @param companyId
     * @return
     */
    @GetMapping("/getByCompanyId")
    public List<BankInfo> getByBankInfoCompanyId(Long companyId) {
        return iBankInfoService.getByCompanyId(companyId);
    }

    /**
     * 根据条件获取
     * @param bankInfo
     * @return
     */
    @PostMapping("/getBankInfoByParm")
    public BankInfo getBankInfoByParm(@RequestBody BankInfo bankInfo) {
        return iBankInfoService.getBankInfoByParm(bankInfo);
    }

    /**
     * 根据条件获取
     * @param bankInfo
     * @return
     */
    @PostMapping("/getBankInfosByParam")
    public List<BankInfo> getBankInfosByParam(@RequestBody BankInfo bankInfo) {
        return iBankInfoService.getBankInfosByParam(bankInfo);
    }

    /**
     * 分页条件查询
     * @param bankInfo
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<BankInfo> listPageByParam(@RequestBody BankInfo bankInfo) {
        return iBankInfoService.listPageByParam(bankInfo);
    }
}

package com.midea.cloud.srm.inq.inquiry.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.enums.inq.QuoteStatusEnum;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteQueryDto;
import com.midea.cloud.srm.inq.inquiry.service.IVendorService;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuoteHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  询价-邀请供应商表 前端控制器
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/vendor")
public class VendorController extends BaseController {

    @Autowired
    private IVendorService iVendorService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Vendor get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iVendorService.getById(id);
    }

    /**
    * 新增
    * @param vendor
    */
    @PostMapping("/add")
    public void add(@RequestBody Vendor vendor) {
        Long id = IdGenrator.generate();
        vendor.setInquiryVendorId(id);
        iVendorService.save(vendor);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iVendorService.removeById(id);
    }

    /**
    * 修改
    * @param vendor
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Vendor vendor) {
        iVendorService.updateById(vendor);
    }

    /**
    * 分页查询
    * @param vendor
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Vendor> listPage(@RequestBody Vendor vendor) {
        PageUtil.startPage(vendor.getPageNum(), vendor.getPageSize());
        QueryWrapper<Vendor> wrapper = new QueryWrapper<Vendor>(vendor);
        return new PageInfo<Vendor>(iVendorService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Vendor> listAll() { 
        return iVendorService.list();
    }


    /**
     * 分页查询
     * @param vendor
     * @return
     */
    @PostMapping("/listInquiryByVendorId")
    public PageInfo<QuoteHeaderDto> listInquiryByVendorId(@RequestBody QuoteQueryDto quoteQueryDto) {
        PageUtil.startPage(quoteQueryDto.getPageNum(), quoteQueryDto.getPageSize());
        QueryWrapper<QuoteQueryDto> wrapper = new QueryWrapper<QuoteQueryDto>(quoteQueryDto);
        return new PageInfo<QuoteHeaderDto>(iVendorService.getByVendorId(quoteQueryDto));
    }

    @PostMapping("/getInquiryInfo")
    public QuoteHeaderDto getInquiryInfo(@RequestBody Vendor vendor) {
        Assert.notNull(vendor.getVendorId(), "供应商id不能为空");
        Assert.notNull(vendor.getInquiryId(), "询价单id不能为空");
        return iVendorService.getInquiryInfo(vendor.getVendorId(),vendor.getInquiryId());
    }

    /**
     * 工作台统计待报价数量
     */
    @GetMapping("/waitQuote")
    public WorkCount waitQuoteCount() {
        WorkCount workCount = new WorkCount();
        Integer waitQuote = iVendorService.getWaitQuote();
        workCount.setCount(waitQuote);
        workCount.setTitle("待报价");
        workCount.setUrl("/vendorSourceSynergy/inquiryOrders?from=workCount&status="+ QuoteStatusEnum.SAVE.getKey());
        return workCount;
    }

}

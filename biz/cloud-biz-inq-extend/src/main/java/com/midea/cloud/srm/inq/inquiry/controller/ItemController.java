package com.midea.cloud.srm.inq.inquiry.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.inquiry.service.IItemService;
import com.midea.cloud.srm.inq.inquiry.service.ILadderPriceService;
import com.midea.cloud.srm.inq.inquiry.service.IQuoteAuthService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemTargetPriceResponseDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemTargetPriceSaveDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.Item;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  询价-询价信息行表 前端控制器
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
@RequestMapping("/inquiry/item")
public class ItemController extends BaseController {

    @Autowired
    private IItemService iItemService;
    @Autowired
    private ILadderPriceService iLadderPriceService;
    @Autowired
    private IQuoteAuthService iQuoteAuthService;
    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Item get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iItemService.getById(id);
    }

    /**
    * 新增
    * @param item
    */
    @PostMapping("/add")
    public void add(@RequestBody Item item) {
        Long id = IdGenrator.generate();
        item.setInquiryItemId(id);
        iItemService.save(item);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iItemService.removeById(id);
        QueryWrapper query = new QueryWrapper();
        query.eq("INQUIRY_ITEM_ID",id);
        iLadderPriceService.remove(query);
        iQuoteAuthService.remove(query);
    }

    /**
    * 修改
    * @param item
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Item item) {
        iItemService.updateById(item);
    }

    /**
    * 分页查询
    * @param item
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Item> listPage(@RequestBody Item item) {
        PageUtil.startPage(item.getPageNum(), item.getPageSize());
        QueryWrapper<Item> wrapper = new QueryWrapper<Item>(item);
        return new PageInfo<Item>(iItemService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Item> listAll() { 
        return iItemService.list();
    }

    /**
     * 新增
     *
     * @param items
     */
    @PostMapping("/saveItem")
    public void saveItem(@RequestBody List<Item> items) {
        items.forEach(item -> {
            Long id = IdGenrator.generate();
            item.setInquiryItemId(id);
        });
        iItemService.saveBatch(items);
    }

    /**
     * 查询目标价
     */
    @GetMapping("/targetPrice")
    public List<ItemTargetPriceResponseDTO> queryTargetPrice(Long inquiryId) {

        Assert.notNull(inquiryId, "询价单id不能为空");
        return iItemService.queryTargetPrice(inquiryId);
    }

    /**
     * 设定目标价
     */
    @PostMapping("/targetPrice")
    public void updateTargetPrice(@RequestBody ItemTargetPriceSaveDTO request) {
        Assert.notNull(request.getInquiryId(), "询价单id不能为空");
        if (CollectionUtils.isEmpty(request.getItemTargetPrices())) {
            throw new BaseException("请求列表不能为空");
        }
        iItemService.updateTargetPrice(request);
    }

    /**
     * 目标价解密
     */
    @RequestMapping("/targetPrice/decrypt")
    public void decryptTargetPrice(Long inquiryId) {

        Assert.notNull(inquiryId, "询价单id不能为空");
        iItemService.decryptTargetPrice(inquiryId);
    }
 
}

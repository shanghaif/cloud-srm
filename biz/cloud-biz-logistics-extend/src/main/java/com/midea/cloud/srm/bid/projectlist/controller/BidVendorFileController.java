package com.midea.cloud.srm.bid.projectlist.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.entity.BidVendorFile;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidVendorFileVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  投标报名附件表(供应商端) 前端控制器
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-06 15:06:23
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/supplierCooperate/vendorFile")
public class BidVendorFileController extends BaseController {

    @Autowired
    private IBidVendorFileService iBidVendorFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BidVendorFile get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBidVendorFileService.getById(id);
    }

    /**
    * 新增
    * @param bidVendorFile
    */
    @PostMapping("/add")
    public void add(@RequestBody BidVendorFile bidVendorFile) {
        Long id = IdGenrator.generate();
        bidVendorFile.setVendorFileId(id);
        iBidVendorFileService.save(bidVendorFile);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBidVendorFileService.removeById(id);
    }

    /**
    * 修改
    * @param bidVendorFile
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BidVendorFile bidVendorFile) {
        iBidVendorFileService.updateById(bidVendorFile);
    }

    /**
    * 分页查询
    * @param bidVendorFile
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BidVendorFile> listPage(@RequestBody BidVendorFile bidVendorFile) {
        PageUtil.startPage(bidVendorFile.getPageNum(), bidVendorFile.getPageSize());
        QueryWrapper<BidVendorFile> wrapper = new QueryWrapper<BidVendorFile>(bidVendorFile);
        return new PageInfo<BidVendorFile>(iBidVendorFileService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BidVendorFile> listAll() { 
        return iBidVendorFileService.list();
    }


    /**
     * 根据bidingId,供应商ID，文件类型，查找文件上传信息
     * @param vendorFileVO
     * @return
     */
    @PostMapping("/listVendorFile")
    public List<BidVendorFileVO> listVendorFile(@RequestBody BidVendorFileVO vendorFileVO) {
        return  iBidVendorFileService.getVendorFileList(vendorFileVO) ;
    }
}

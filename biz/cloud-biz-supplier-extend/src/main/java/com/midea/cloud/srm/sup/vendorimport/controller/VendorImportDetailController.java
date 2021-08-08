package com.midea.cloud.srm.sup.vendorimport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImportDetail;
import com.midea.cloud.srm.sup.vendorimport.service.IVendorImportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  供应商引入明细表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-13 18:08:37
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/vendorImport/vendor-import-detail")
public class VendorImportDetailController extends BaseController {

    @Autowired
    private IVendorImportDetailService iVendorImportDetailService;

    @Autowired
    private FileCenterClient fileCenterClient;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public VendorImportDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iVendorImportDetailService.getById(id);
    }

    /**
    * 新增
    * @param vendorImportDetail
    */
    @PostMapping("/add")
    public void add(@RequestBody VendorImportDetail vendorImportDetail) {
        Long id = IdGenrator.generate();
        vendorImportDetail.setImportDetailId(id);
        iVendorImportDetailService.save(vendorImportDetail);
    }
    
    /**
    * 删除组织品类行
    * @param importDetailId
    */
    @GetMapping("/delete")
    public void delete(@RequestParam("importDetailId") Long importDetailId) {
        if (importDetailId != null){
            VendorImportDetail vendorImportDetail = iVendorImportDetailService.getById(importDetailId);
            iVendorImportDetailService.remove(new QueryWrapper<>(
                new VendorImportDetail()
                    .setOrgCode(vendorImportDetail.getOrgCode())
                    .setImportId(vendorImportDetail.getImportId())
            ));
        }
    }

    /**
    * 修改
    * @param vendorImportDetail
    */
    @PostMapping("/modify")
    public void modify(@RequestBody VendorImportDetail vendorImportDetail) {
        iVendorImportDetailService.updateById(vendorImportDetail);
    }

    /**
    * 分页查询
    * @param vendorImportDetail
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<VendorImportDetail> listPage(@RequestBody VendorImportDetail vendorImportDetail) {
        PageUtil.startPage(vendorImportDetail.getPageNum(), vendorImportDetail.getPageSize());
        QueryWrapper<VendorImportDetail> wrapper = new QueryWrapper<VendorImportDetail>(vendorImportDetail);
        return new PageInfo<VendorImportDetail>(iVendorImportDetailService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<VendorImportDetail> listAll() { 
        return iVendorImportDetailService.list();
    }


    @PostMapping("/deleteFile")
    public void deleteFile(@RequestBody Fileupload fileupload) {
        Long fileuploadId = fileupload.getFileuploadId();
        if(fileuploadId != null){
            fileCenterClient.delete(fileuploadId);
        }
    }
 
}

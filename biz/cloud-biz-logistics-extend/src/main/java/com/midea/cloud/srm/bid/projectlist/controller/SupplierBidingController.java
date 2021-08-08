package com.midea.cloud.srm.bid.projectlist.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.projectlist.service.ISupplierBidingService;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.SupplierBidingVO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
*  <pre>
 *  招标项目列表--供应商端 前端控制器
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-31 11:44:36
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/supplierCooperate/supplierBiding")
public class SupplierBidingController extends BaseController {

    @Autowired
    private ISupplierBidingService iSupplierBidingService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Biding get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSupplierBidingService.getById(id);
    }

    /**
    * 分页查询
    * @param supplierBidingVO
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<SupplierBidingVO> listPage(@RequestBody SupplierBidingVO supplierBidingVO) {
//        Assert.notNull(supplierBidingVO.getVendorId(), "供应商编码不能为空");
        //获取供应商ID
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType()) && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            Assert.isTrue(false, "用户类型不存在");
        }
        if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
            if(Objects.isNull(loginAppUser.getCompanyId())){
                return new PageInfo<>(Collections.emptyList());
            }
            supplierBidingVO.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(supplierBidingVO.getPageNum(), supplierBidingVO.getPageSize());
        List<SupplierBidingVO> supplierBidingVOs = iSupplierBidingService.getSupplierBiding(supplierBidingVO);
        return new PageInfo<SupplierBidingVO>(supplierBidingVOs);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<SupplierBidingVO> listAll(@RequestBody SupplierBidingVO supplierBidingVO) {
//        Assert.notNull(supplierBidingVO.getVendorId(), "供应商编码不能为空");
        //获取供应商ID
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        supplierBidingVO.setVendorId(user.getCompanyId());
        return iSupplierBidingService.getSupplierBiding(supplierBidingVO);
    }

    @GetMapping("/countCreate")
    public WorkCount countCreate() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        SupplierBidingVO supplierBidingVO = new SupplierBidingVO();
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            supplierBidingVO.setVendorId(loginAppUser.getCompanyId());
        }
        return iSupplierBidingService.countCreate(supplierBidingVO);
    }
}

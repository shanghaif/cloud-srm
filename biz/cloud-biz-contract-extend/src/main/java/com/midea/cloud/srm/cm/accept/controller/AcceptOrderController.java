package com.midea.cloud.srm.cm.accept.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.cm.accept.service.IAcceptOrderService;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptDTO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * 验收合同
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020/8/7/007 13:07
 * 修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/accept/acceptOrder")
public class AcceptOrderController extends BaseController {

    @Autowired
    private IAcceptOrderService iAcceptOrderService;

    /**
     * 获取AcceptDTO（编辑时获取验收单和验收详情的接口）
     * @param acceptOrderId
     */
    @GetMapping("/getAcceptDTO")
    public AcceptDTO getAcceptDTO(@RequestParam Long acceptOrderId) {
        Assert.notNull(acceptOrderId,"单号为空，获取失败。");
        return iAcceptOrderService.getAcceptDTO(acceptOrderId);
    }

    /**
     * 删除AcceptDTO
     * @param
     */
    @GetMapping("/deleteAcceptDTO")
    public void deleteAcceptDTO(@RequestParam Long acceptOrderId) {
        iAcceptOrderService.deleteAcceptDTO(acceptOrderId);
    }

    /**
     * 废弃订单
     * @param acceptOrderId
     */
    @GetMapping("/abandon")
    public void abandon(Long acceptOrderId) {
        Assert.notNull(acceptOrderId,"废弃订单id不能为空");
        iAcceptOrderService.abandon(acceptOrderId);
    }



    /**
     * 采购商暂存(编辑保存时接口)
     * @param acceptDTO
     */
    @PostMapping("/buyerSaveTemporary")
    public Long buyerSaveTemporary(@RequestBody AcceptDTO acceptDTO) {
        Assert.notNull(acceptDTO.getAcceptOrder(),"头信息不能为空");
        if (acceptDTO.getAcceptOrder().getCeeaAssetQualification().equals(1)){
            Assert.isTrue(CollectionUtils.isNotEmpty(acceptDTO.getAssetFile()),"不动产大于500万必须上传附件");
        }
        return iAcceptOrderService.buyerSaveTemporary(acceptDTO);
    }

    /**
     * 采购商提交（编辑后的提交或新增）
     * 拟定转审批中
     * @param acceptOrderId
     */
    @GetMapping("/buyerSubmit")
    public void buyerSubmit(Long acceptOrderId) {
        Assert.notNull(acceptOrderId,"请选择需要提交的验收单申请");
        AcceptOrder byId = iAcceptOrderService.getById(acceptOrderId);
        Assert.notNull(byId,"找不到需要提交的验收单申请");
        iAcceptOrderService.buyerSubmit(acceptOrderId);
    }


    /**
     * 采购商提交（编辑后的提交或新增）
     * 拟定转审批中
     * @param acceptDTO
     */
    @PostMapping("/buyerSubmit")
    public void buyerSubmit(@RequestBody AcceptDTO acceptDTO) {
        Assert.notNull(acceptDTO.getAcceptOrder(),"头信息不能为空");
        if (acceptDTO.getAcceptOrder().getCeeaAssetQualification().equals(1)){
            Assert.isTrue(CollectionUtils.isNotEmpty(acceptDTO.getAssetFile()),"不动产大于500万必须上传附件");
        }
        iAcceptOrderService.buyerSubmit(acceptDTO);
    }

    /**
     * 采购商撤回
     * @param acceptOrderId
     */
    @GetMapping("/buyerWithdraw")
    public void buyerWithdraw(@RequestParam Long acceptOrderId) {
        iAcceptOrderService.buyerWithdraw(acceptOrderId);
    }

    /**
     * 供应商通过
     * @param acceptOrderDTO
     */
    @PostMapping("/vendorPass")
    public void vendorPass(@RequestBody AcceptOrderDTO acceptOrderDTO) {
        iAcceptOrderService.vendorPass(acceptOrderDTO);
    }

    /**
     * 供应商通过
     * @param id
     */
    @GetMapping("/getVendorPass")
    public void getVendorPass(Long  id) {
        Assert.notNull(id,"请选择需要审核的验收行。");
        AcceptOrder byId = iAcceptOrderService.getById(id);
        Assert.notNull(byId,"找不到需要审核的验收行。");
        AcceptOrderDTO acceptOrderDTO = new AcceptOrderDTO();
        BeanUtils.copyProperties(byId,acceptOrderDTO);
        iAcceptOrderService.vendorPass(acceptOrderDTO);
    }


    /**
     * 采购商驳回
     * @param acceptOrderDTO
     */
    @PostMapping("/buyerReject")
    public void buyerReject(@RequestBody AcceptOrderDTO acceptOrderDTO) {
        iAcceptOrderService.vendorReject(acceptOrderDTO);
    }

    /**
     * 分页查询
     * @param acceptOrderDTO
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<AcceptOrder> listPageByParm(@RequestBody AcceptOrderDTO acceptOrderDTO) {
        PageUtil.startPage(acceptOrderDTO.getPageNum(), acceptOrderDTO.getPageSize());
        return new PageInfo<AcceptOrder>(iAcceptOrderService.listByParm(acceptOrderDTO));
       // return iAcceptOrderService.listPageByParm(acceptOrderDTO);
    }

    /**
     * 根据合同查询采购组织下的用户
     * @param contractHeadId
     * @return
     */
    @PostMapping("/listOrgUserByContract")
    public PageInfo<User> listOrgUserByContract(@RequestParam Long contractHeadId) {
        return iAcceptOrderService.listOrgUserByContract(contractHeadId);
    }

    /**
     * 供应商提交
     * @param id
     */
    @GetMapping("/vendorSubmit")
    public void vendorSubmit(Long id) {
        Assert.notNull(id,"验收单id不能为空");
        AcceptOrder acceptOrder = new AcceptOrder();
        acceptOrder.setAcceptOrderId(id);
        acceptOrder.setAcceptStatus("SUBMIT");
        iAcceptOrderService.updateById(acceptOrder);
    }
    /**
     * 采购商确认审核验收协同申请
     * @param id
     */
    @GetMapping("/vendorPass")
    public void vendorPass(Long id) {
        Assert.notNull(id,"验收单id不能为空");
        AcceptOrder acceptOrder = new AcceptOrder();
        acceptOrder.setAcceptOrderId(id);
        acceptOrder.setAcceptStatus("DRAFT");
        iAcceptOrderService.updateById(acceptOrder);
    }
    /**
     * 供应商驳回
     * @param acceptOrder
     */
    @PostMapping("/vendorReject")
    public void vendorReject(@RequestBody AcceptOrder acceptOrder) {
        Assert.notNull(acceptOrder,"验收单id不能为空");
        acceptOrder.setAcceptStatus("REJECTED");
        iAcceptOrderService.updateById(acceptOrder);
    }

}

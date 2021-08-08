package com.midea.cloud.srm.cm.accept.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptDTO;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 *  <pre>
 *  合同验收单 服务类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-03 11:40:16
 *  修改内容:
 * </pre>
 */
public interface IAcceptOrderService extends IService<AcceptOrder> {

    /**
     * 采购商暂存
     * @param acceptDTO
     */
    Long buyerSaveTemporary(AcceptDTO acceptDTO);

    /**
     *分页查询
     * @param acceptOrderDTO
     */
    PageInfo<AcceptOrder>
    listPageByParm(AcceptOrderDTO acceptOrderDTO);

    /**
     * 获取AcceptDTO
     * @param acceptOrderId
     * @return
     */
    AcceptDTO getAcceptDTO(Long acceptOrderId);

    /**
     * 采购商提交
     * @param acceptDTO
     */
    void buyerSubmit(AcceptDTO acceptDTO);
    /**
     * 采购商提交
     * @param acceptOrderId
     */
    void buyerSubmit(Long acceptOrderId);

    /**
     * 采购商撤回
     * @param acceptOrderId
     */
    void buyerWithdraw(Long acceptOrderId);

    /**
     * 删除AcceptDTO
     * @param acceptOrderId
     */
    void deleteAcceptDTO(Long acceptOrderId);


    /**
     * 废弃订单
     * @param acceptOrderId
     */
    void abandon(Long acceptOrderId);

    /**
     * 供应商通过
     * @param acceptOrderDTO
     */
    void vendorPass(AcceptOrderDTO acceptOrderDTO);

    /**
     * 供应商驳回
     * @param acceptOrderDTO
     */
    void vendorReject(AcceptOrderDTO acceptOrderDTO);

    /**
     * 根据合同查询采购组织下的用户
     * @param contractHeadId
     * @return
     */
    PageInfo<User> listOrgUserByContract(Long contractHeadId);
    List<AcceptOrder> listByParm(AcceptOrderDTO acceptOrderDTO);
}

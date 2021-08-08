package com.midea.cloud.srm.cm.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.template.dto.PayTypeDTO;
import com.midea.cloud.srm.model.cm.template.entity.PayType;

import java.util.List;

/**
*  <pre>
 *  合同付款类型 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-13 16:49:04
 *  修改内容:
 * </pre>
*/
public interface IPayTypeService extends IService<PayType> {

    List<PayType> listAll();

    /**
     * 分页条件查询
     * @param payType
     * @return
     */
    PageInfo<PayTypeDTO> listPageByParm(PayType payType);

    /**
     * 新增合同付款类型
     * @param payTypeDTO
     */
    void savePayTypeDTO(PayTypeDTO payTypeDTO);


    /**
     * 编辑合同付款类型
     * @param payTypeDTO
     */
    void updatePayTypeDTO(PayTypeDTO payTypeDTO);

    /**
     * 生效
     * @param payTypeId
     */
    void effective(Long payTypeId);

    /**
     * 失效
     * @param payTypeId
     */
    void invalid(Long payTypeId);

    /**
     * 付款条件维护新增
     * @param payType
     * @return
     */
    Long paymentTermsAdd(PayType payType);

    /**
     * 付款条件维护更新
     * @param payType
     * @return
     */
    Long paymentTermsUpdate(PayType payType);

    /**
     * 付款条件分页查询
     * @param payType
     * @return
     */
    PageInfo<PayType> paymentTermsPage(PayType payType);

    /**
     * 获取有效的付款条件
     * @return
     */
    List<PayType> getActivationPaymentTerms();
}

package com.midea.cloud.srm.supauth.review.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplierauth.review.dto.EffectFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.EffectForm;

import java.util.List;

/**
*  <pre>
 *  供方生效单据 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 21:09:09
 *  修改内容:
 * </pre>
*/
public interface IEffectFormService extends IService<EffectForm> {

    /**
     * 新增供方生效单
     * @param effectFormDTO
     * @param approveStatusType
     * @return
     */
    Long add(EffectFormDTO effectFormDTO, String approveStatusType);

    /**
     * 分页条件查询供方生效单
     * @param effectForm
     * @return
     */
    PageInfo<EffectForm> listPageByParm(EffectForm effectForm);

    /**
     * 根据effectFormId获取EffectFormDTO
     * @param effectFormId
     * @return
     */
    EffectFormDTO getEffectFormDTOById(Long effectFormId);

    /**
     * 提交
     * @param effectFormDTO
     * @param approveStatusType
     * @return
     */
    FormResultDTO submitted(EffectFormDTO effectFormDTO, String approveStatusType);

    /**
     * 暂存
     * @param effectFormDTO
     * @param approveStatusType
     * @return
     */
    FormResultDTO saveTemporary(EffectFormDTO effectFormDTO, String approveStatusType);

    /**
     * 批量删除
     * @param effectFormIds
     */
    void bachDeleteByList(List<Long> effectFormIds);
}

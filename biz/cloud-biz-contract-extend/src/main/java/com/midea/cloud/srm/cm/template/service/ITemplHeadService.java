package com.midea.cloud.srm.cm.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.cm.template.dto.ContractTemplDTO;
import com.midea.cloud.srm.model.cm.template.dto.TemplHeadQueryDTO;
import com.midea.cloud.srm.model.cm.template.entity.TemplHead;

import java.util.List;

/**
*  <pre>
 *  合同模板头表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-19 08:58:08
 *  修改内容:
 * </pre>
*/
public interface ITemplHeadService extends IService<TemplHead> {

    /**
     * 保存合同模板(无需校验)
     * @param contractTemplDTO
     */
    void saveContractTemplDTO(ContractTemplDTO contractTemplDTO);

    /**
     * 修改(无需校验)
     * @param contractTemplDTO
     */
    void updateContractTemplDTO(ContractTemplDTO contractTemplDTO);

    /**
     * 获取合同模板
     * @param templHeadId
     * @return
     */
    ContractTemplDTO getContractTemplDTO(Long templHeadId);

    /**
     * 分页查询合同模板
     * @param templHeadQueryDTO
     * @return
     */
    PageInfo<TemplHead> listPageByParm(TemplHeadQueryDTO templHeadQueryDTO);

    /**
     * 生效
     * @param templHeadId
     */
    void effective(Long templHeadId);

    /**
     * 根据模板头ID删除
     * @param templHeadId
     */
    void deleteContractTemplDTO(Long templHeadId);

    /**
     * 失效
     * @param templHead
     */
    void invalid(TemplHead templHead);

    /**
     * 查询全部生效的合同模板
     */
    List<TemplHead> listEffectiveTempl();

    /**
     * 复制
     * @param templHeadId
     */
    void copy(Long templHeadId);
}

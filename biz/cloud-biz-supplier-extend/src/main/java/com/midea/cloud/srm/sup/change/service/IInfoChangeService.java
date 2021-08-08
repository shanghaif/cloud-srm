package com.midea.cloud.srm.sup.change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeRequestDTO;
import com.midea.cloud.srm.model.supplier.change.dto.InfoChangeDTO;
import com.midea.cloud.srm.model.supplier.change.entity.CompanyInfoChange;
import com.midea.cloud.srm.model.supplier.change.entity.InfoChange;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  公司信息变更表 服务类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-30 19:57:36
 *  修改内容:
 * </pre>
 */
public interface IInfoChangeService extends IService<InfoChange> {

    InfoChange addInfoChange(InfoChange infoChange);

    InfoChange updateInfoChange(InfoChange infoChange);

    List<InfoChangeDTO> listPageByParam(ChangeRequestDTO changeRequestDTO);

    PageInfo<InfoChangeDTO> listPageByParamPage(ChangeRequestDTO changeRequestDTO);

    FormResultDTO saveOrUpdateChange(ChangeInfoDTO changeInfo, String orderStatus);

    void commonCheck(ChangeInfoDTO changeInfo, String orderStatus);

    ChangeInfoDTO getInfoByChangeId(Long changeId);

    void deleteChangeInfo(Long changeId);

    /**
     * 废弃订单
     * @param changeId
     */
    void abandon(Long changeId);
    void updateChange(ChangeInfoDTO changeInfo, String orderStatus);

    Map<String,Object> saveChangeWithFlow(ChangeInfoDTO dto);

    void updateChangeWithFlow(ChangeInfoDTO changeInfo, String orderStatus);

    /**
     * 查询供应商列表
     * 变更状态处于拟定状态或者已提交状态的不作为查询对象
     * @return
     */
    List<CompanyInfo> getVendors();

    void removeChangeById(Long id);

    /**
     * 根据供应商id判断是否能够进行信息变更
     */
    InfoChangeDTO ifAddInfoChange(Long companyId);

    /**
     * 采购商驳回供方已提交
     * @param changeId
     */
    void buyerReject(Long changeId);
}

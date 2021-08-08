package com.midea.cloud.srm.inq.inquiry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.inquiry.dto.InquiryDeadlineRequestDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.InquiryHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
import com.midea.cloud.srm.model.inq.inquiry.entity.Item;
import com.midea.cloud.srm.model.inq.inquiry.entity.Vendor;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  询价-询价信息头表 服务类
 * </pre>
 *
 * @author zhongbh
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
 */
public interface IHeaderService extends IService<Header> {
    InquiryHeaderDto getHeadById(Long headId);

    Long saveAndUpdate(InquiryHeaderDto dto, String actionType);

    Long commit(InquiryHeaderDto dto, String actionType);
    Long publish(Header dto, String actionType);

    List<Item> saveItemsByExcel(Long inquiryId,List<Item> list);

    List<Vendor> saveVendorsByExcel(Long inquiryId, List<Vendor> list);

    /**
     * 提前截止报价
     */
    void changDeadline(InquiryDeadlineRequestDTO request);

    /**
     * 初始化工作流
     */
    Map<String, Object> initWorkFlow(Long inquiryId, Long menuId);

    /**
     * 采购需求生成询比价单
     * @param requirementLine
     */
    String requirementGenInquiry(List<RequirementLine> requirementLine);
}

package com.midea.cloud.srm.inq.inquiry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemDto;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemTargetPriceSaveDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.Item;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemTargetPriceResponseDTO;

import java.util.List;

/**
*  <pre>
 *  询价-询价信息行表 服务类
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
public interface IItemService extends IService<Item> {

    List<ItemDto> getByHeadId(Long inquiryId);

    void saveItems(List<ItemDto> itemDto);

    /**
     * 查询目标价
     */
    List<ItemTargetPriceResponseDTO> queryTargetPrice(Long inquiryId);

    /**
     * 添加目标价
     */
    void updateTargetPrice(ItemTargetPriceSaveDTO request);

    /**
     * 目标价解密
     */
    void decryptTargetPrice(Long inquiryId);

    /**
     * 批量获取询价物料信息
     */
    List<Item> getItemsByIds(List<Long> itemIds);
}

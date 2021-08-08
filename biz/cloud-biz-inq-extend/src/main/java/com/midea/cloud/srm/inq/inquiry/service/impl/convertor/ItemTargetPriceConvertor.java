package com.midea.cloud.srm.inq.inquiry.service.impl.convertor;

import com.midea.cloud.srm.model.inq.inquiry.dto.ItemTargetPriceResponseDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.Item;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List; /**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-7 17:30
 *  修改内容:
 * </pre>
 */
public class ItemTargetPriceConvertor {
    public static List<ItemTargetPriceResponseDTO> convert(List<Item> list) {

        List<ItemTargetPriceResponseDTO> response = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return response;
        }

        list.forEach(item -> {
            ItemTargetPriceResponseDTO dto = new ItemTargetPriceResponseDTO();
            BeanUtils.copyProperties(item, dto);
            response.add(dto);
        });
        return response;
    }
}
package com.midea.cloud.srm.inq.inquiry.service.impl.convertor;

import com.midea.cloud.srm.model.inq.inquiry.domain.VendorNResult;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNQueryResponseDTO;
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
 *  修改日期: 2020-4-7 15:25
 *  修改内容:
 * </pre>
 */
public class VendorNQueryConvertor {

    public static List<VendorNQueryResponseDTO> convert(List<VendorNResult> vendorNResults) {

        List<VendorNQueryResponseDTO> response = new ArrayList<>();
        if (CollectionUtils.isEmpty(vendorNResults)) {
            return response;
        }

        vendorNResults.forEach(value -> {
            VendorNQueryResponseDTO dto = new VendorNQueryResponseDTO();
            BeanUtils.copyProperties(value, dto);
            response.add(dto);
        });
        return response;
    }
}
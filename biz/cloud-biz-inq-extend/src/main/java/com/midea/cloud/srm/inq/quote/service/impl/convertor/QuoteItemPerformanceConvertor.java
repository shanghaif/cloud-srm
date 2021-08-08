package com.midea.cloud.srm.inq.quote.service.impl.convertor;

import com.midea.cloud.srm.model.inq.quote.domain.QuoteItemPerformanceQueryResult;
import com.midea.cloud.srm.model.inq.quote.dto.QuoteItemPerformanceResponseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List; /**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-3 16:30
 *  修改内容:
 * </pre>
 */
public class QuoteItemPerformanceConvertor {

    /**
     *
     */
    public static List<QuoteItemPerformanceResponseDTO> convert(List<QuoteItemPerformanceQueryResult> result) {

        List<QuoteItemPerformanceResponseDTO> response = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result)) {
            result.forEach(value -> {
                QuoteItemPerformanceResponseDTO dto = new QuoteItemPerformanceResponseDTO();
                BeanUtils.copyProperties(value, dto);
                response.add(dto);
            });
        }
        return response;
    }
}
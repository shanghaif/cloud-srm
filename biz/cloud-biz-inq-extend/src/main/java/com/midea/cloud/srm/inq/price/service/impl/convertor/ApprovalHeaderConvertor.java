package com.midea.cloud.srm.inq.price.service.impl.convertor;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.PriceApprovalStatus;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.inq.price.domain.ApprovalHeaderResult;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalHeaderQueryResponseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
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
 *  修改日期: 2020-4-9 16:38
 *  修改内容:
 * </pre>
 */
public class ApprovalHeaderConvertor {

    public static PageInfo<ApprovalHeaderQueryResponseDTO> convert(List<ApprovalHeaderResult> results) {

        List<ApprovalHeaderQueryResponseDTO> response = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(results)) {
            results.forEach(approvalHeaderResult -> {
                ApprovalHeaderQueryResponseDTO dto = new ApprovalHeaderQueryResponseDTO();
                BeanUtils.copyProperties(approvalHeaderResult, dto);
                if (PriceApprovalStatus.RESULT_PASSED.getValue().equals(dto.getStatus()) ||
                        PriceApprovalStatus.RESULT_REJECTED.getValue().equals(dto.getStatus())) {
                    dto.setApprovalDate(approvalHeaderResult.getLastUpdateDate());
                }
                response.add(dto);
            });
        }

        return PageUtil.buildPageInfoAfertConvert(results, response);
    }
}
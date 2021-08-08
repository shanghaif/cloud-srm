package com.midea.cloud.srm.model.supplierauth.entry.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  供方准入配置保存维护的品类之后返回的是否成功的DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/18 17:10
 *  修改内容:
 * </pre>
 */
@Data
public class EntryCategoryConfigSaveResultDTO extends BaseDTO {

    private boolean categoryListSaveStatus;
}

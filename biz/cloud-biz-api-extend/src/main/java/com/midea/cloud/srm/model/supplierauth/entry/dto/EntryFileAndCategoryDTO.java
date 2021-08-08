package com.midea.cloud.srm.model.supplierauth.entry.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryFileConfig;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @ClassName EntryFileAndCategoryDTO
 * @Author User
 * @date 2021.03.01 13:37
 */
@Data
public class EntryFileAndCategoryDTO extends BaseDTO {
    private EntryCategoryConfig entryCategoryConfig;
    private List<EntryFileConfig> entryFileConfigList;
}

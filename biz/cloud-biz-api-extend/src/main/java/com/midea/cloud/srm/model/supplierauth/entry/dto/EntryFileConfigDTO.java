package com.midea.cloud.srm.model.supplierauth.entry.dto;

import java.util.List;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryFileConfig;

import lombok.Data;

@Data
public class EntryFileConfigDTO extends BaseDTO {
	private Long entryConfigId;
	private List<EntryFileConfig> list;
}

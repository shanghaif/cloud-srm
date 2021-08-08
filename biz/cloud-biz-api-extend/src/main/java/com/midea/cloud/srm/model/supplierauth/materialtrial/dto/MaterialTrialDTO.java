package com.midea.cloud.srm.model.supplierauth.materialtrial.dto;

import java.util.List;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.model.supplierauth.materialtrial.entity.MaterialTrial;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;

import lombok.Data;
@Data
public class MaterialTrialDTO extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2191490318771630155L;

	/**
	 * 物料试用头信息
	 */
	private MaterialTrial materialTrial;
	
	/**
	 * 组织品类信息
	 */
	private List<OrgCateJournal> orgCateJournals;
	
	
	/**
	 * 附件信息
	 */
	List<FileRecord> fileRecords;
	
	/**
     * 操作类型:暂存,提交
     */
    private String opType;

    /**
     * 功能菜单ID
     */
    private Long menuId;

    private String processType;//提交审批Y，废弃N
}

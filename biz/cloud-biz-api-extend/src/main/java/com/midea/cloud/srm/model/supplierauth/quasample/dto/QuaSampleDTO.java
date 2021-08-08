package com.midea.cloud.srm.model.supplierauth.quasample.dto;

import java.util.List;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.FileRecord;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;

import lombok.Data;
@Data
public class QuaSampleDTO extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1522787455397453576L;

	/**
	 * 样品确认头信息
	 */
	private QuaSample quaSample;
	
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

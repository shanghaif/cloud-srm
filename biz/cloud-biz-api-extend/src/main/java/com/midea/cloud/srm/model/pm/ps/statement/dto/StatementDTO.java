package com.midea.cloud.srm.model.pm.ps.statement.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementReceipt;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementReturn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * <pre>
 * 对账单新增 / 编辑表单
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 12, 202010:36:53 AM
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StatementDTO extends BaseDTO {

	private static final long serialVersionUID = 154902477365946675L;

	public interface InsertGroup {
	}

	public interface UpdateGroup {
	}

	public interface SubmitGroup {
	}

	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "对账单头表不能为空")
	@Valid
	private StatementHead statementHead;// 对账单-头表
	@Valid
	private List<Fileupload> fileuploadList;// 对账单-附件列表
	@NotEmpty(groups = { SubmitGroup.class }, message = "入库明细不能为空")
	private List<StatementReceipt> receiptList;// 对账单-入库明细
	private List<StatementReturn> returnList;// 对账单-退货明细

}

package com.midea.cloud.srm.model.cm.template.dto;

import com.midea.cloud.srm.model.cm.template.entity.TemplLine;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.cm.template.entity.TemplHead;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  合同模板DTO
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-19 11:10
 *  修改内容:
 * </pre>
 */
@Data
public class ContractTemplDTO extends BaseDTO{

    private TemplHead templHead;

    private List<TemplLine> templLines;

    private List<Fileupload> fileuploads;
}

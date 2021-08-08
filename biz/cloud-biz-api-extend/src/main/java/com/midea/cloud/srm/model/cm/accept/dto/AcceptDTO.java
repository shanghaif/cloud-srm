package com.midea.cloud.srm.model.cm.accept.dto;

import com.midea.cloud.srm.model.cm.accept.entity.AcceptDetail;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import com.midea.cloud.srm.model.cm.accept.entity.ToolEqp;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *   合同验收单DTO
 * </pre>
 *
 * @author zhi1772778785@163.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-3 14:34
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class AcceptDTO extends BaseDTO {

    private AcceptOrder acceptOrder;
    //验收明细
    private List<AcceptDetailDTO> acceptDetails;
    //工具设备文件
    private List<ToolEqp> toolEqp;
    //技术文件
    private List<Fileupload> techFile;
    //附件
    private List<Fileupload> assetFile;
    //验收明细
    private List<AcceptDetailDTO> acceptDetailDTOList;

    private String processType;//提交审批Y，废弃N

}

package com.midea.cloud.srm.model.pm.ps.advance.dto;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyHead;
import com.midea.cloud.srm.model.pm.ps.advance.entity.AdvanceApplyLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称   预付款申请保存DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20 13:59
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class AdvanceApplySaveDTO {

    private AdvanceApplyHead advanceApplyHead;//预付款头

    private List<AdvanceApplyLine> advanceApplyLines;//预付款行

    private List<Fileupload> fileuploads;//文件上传行
}

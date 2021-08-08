package com.midea.cloud.srm.model.supplier.vendorimport.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImport;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImportDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/13 19:41
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class VendorImportSaveDTO extends BaseDTO {

    private VendorImport vendorImport;

    private List<VendorImportDetail> vendorImportDetails;

    private List<Fileupload> fileuploads;
    private String processType;//提交审批Y，废弃N
}

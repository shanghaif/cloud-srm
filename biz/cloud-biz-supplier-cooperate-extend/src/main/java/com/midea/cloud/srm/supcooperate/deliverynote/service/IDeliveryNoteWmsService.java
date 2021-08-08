package com.midea.cloud.srm.supcooperate.deliverynote.service;

import com.midea.cloud.srm.model.base.categorydv.entity.CategoryDv;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteWms;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  送货单WMS清单 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 19:11:49
 *  修改内容:
 * </pre>
*/
public interface IDeliveryNoteWmsService extends IService<DeliveryNoteWms> {
     List<DeliveryNoteWms> importExcel(MultipartFile file,Long id) throws Exception;
     Map<String, Object> downloadTemplate() throws Exception;
     List<DeliveryNoteWms> deliveryNoteWmsList(DeliveryNoteWms deliveryNoteWms);
     void excelEportRequirementLine(Long deliveryNoteId, HttpServletResponse response) throws IOException;
}

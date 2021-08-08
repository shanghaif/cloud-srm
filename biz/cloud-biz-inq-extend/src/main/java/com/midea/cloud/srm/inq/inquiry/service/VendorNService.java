package com.midea.cloud.srm.inq.inquiry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNSaveDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNQueryResponseDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.VendorNUpdateDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.VendorN;

import java.util.List;

/**
*  <pre>
 *  供应商N值 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-07 14:32:42
 *  修改内容:
 * </pre>
*/
public interface VendorNService extends IService<VendorN> {

    /**
     * 查询供应商N值
     */
    List<VendorNQueryResponseDTO> queryVendorN(Long inquiryId);

    /**
     * 保存供应商N值
     */
    void saveVendorN(List<VendorNSaveDTO> request);

    /**
     * 更新供应商N值
     */
    void updateVendorN(List<VendorNUpdateDTO> request);

    /**
     * 根据询价单id查询供应商N值
     */
    List<VendorN> queryByInquiryId(Long inquiryId);
}

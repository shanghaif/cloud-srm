package com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.entity.BidVendorFile;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.BidVendorFileVO;

import java.util.List;

/**
*  <pre>
 *  投标报名附件表(供应商端) 服务类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-06 15:06:23
 *  修改内容:
 * </pre>
*/
public interface IBidVendorFileService extends IService<BidVendorFile> {

    /**
     * 获取文件上传记录--供应商端
     *
     * @param vendorFileVO
     * @return
     */
    List<BidVendorFileVO> getVendorFileList(BidVendorFileVO vendorFileVO);

    /**
     *  vendorFileVOs中必须包含businessId和fileType
     * @param vendorFileVOs
     * @return
     */
    boolean saveBatchVendorFilesByBusinessIdAndFileType(List<BidVendorFileVO> vendorFileVOs);

}

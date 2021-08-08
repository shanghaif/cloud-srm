package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidFile;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;

import java.util.List;

/**
*  <pre>
 *  招标附件表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 14:08:23
 *  修改内容:
 * </pre>
*/
public interface IBidFileService extends IService<BidFile> {

    void saveBatchBidFile(List<BidFile> fileList, Long bidingId);

    void updateBatchBidFile(List<BidFile> fileList, Biding biding);
}

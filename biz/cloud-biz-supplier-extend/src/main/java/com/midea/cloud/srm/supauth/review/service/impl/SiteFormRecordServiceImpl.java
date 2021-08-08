package com.midea.cloud.srm.supauth.review.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteFormRecord;
import com.midea.cloud.srm.supauth.review.mapper.SiteFormRecordMapper;
import com.midea.cloud.srm.supauth.review.service.ISiteFormRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
*  <pre>
 *  现场评审记录 服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-15 17:14:30
 *  修改内容:
 * </pre>
*/
@Service
public class SiteFormRecordServiceImpl extends ServiceImpl<SiteFormRecordMapper, SiteFormRecord> implements ISiteFormRecordService {

    @Autowired
    FileCenterClient fileCenterClient;

    @Override
    @Transactional
    public void deleteBySiteFormRecordId(Long siteFormRecordId) {
        SiteFormRecord siteFormRecord = this.getById(siteFormRecordId);
        if (siteFormRecord != null) {
            this.removeById(siteFormRecord.getSiteFormRecordId());
            if (null != siteFormRecord.getFileuploadId()) {
            	fileCenterClient.delete(siteFormRecord.getFileuploadId());
            }
        }
    }
}

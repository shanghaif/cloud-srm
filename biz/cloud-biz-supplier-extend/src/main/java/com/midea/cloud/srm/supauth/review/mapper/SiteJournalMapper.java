package com.midea.cloud.srm.supauth.review.mapper;

import com.midea.cloud.srm.model.supplierauth.review.entity.SiteJournal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地点信息日志表 Mapper 接口
 * </p>
 *
 * @author xiexh12@meicloud.com
 * @since 2020-09-23
 */
public interface SiteJournalMapper extends BaseMapper<SiteJournal> {

    @Select("select count(*),group_concat(SITE_INFO_ID) as ids from cloud_biz_supplier.scc_sup_site_info group by COMPANY_ID,ADDRESS_NAME,VENDOR_SITE_CODE,BELONG_OPR_ID having count(*)>1")
    List<Map<String,Object>> getDuplicateSiteInfo();

}

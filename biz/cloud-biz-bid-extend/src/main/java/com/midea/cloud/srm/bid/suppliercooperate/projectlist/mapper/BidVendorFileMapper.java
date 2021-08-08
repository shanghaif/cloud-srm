package com.midea.cloud.srm.bid.suppliercooperate.projectlist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.bid.suppliercooperate.entity.BidVendorFile;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidVendorFileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 投标报名附件表(供应商端) Mapper 接口
 * </p>
 *
 * @author zhuomb1@midea.com
 * @since 2020-04-06
 */
public interface BidVendorFileMapper extends BaseMapper<BidVendorFile> {

    /**
     * 报名界面的附件内容查询
     * @param vendorFileVO
     * @return
     */
    List<BidVendorFileVO> getVendorFileListForSignUp(@Param("vendorFileVO") BidVendorFileVO vendorFileVO);

    /**
     * 查找附件
     * @param vendorFileVO
     * @return
     */
    List<BidVendorFileVO> getVendorFileList(@Param("vendorFileVO") BidVendorFileVO vendorFileVO);

}

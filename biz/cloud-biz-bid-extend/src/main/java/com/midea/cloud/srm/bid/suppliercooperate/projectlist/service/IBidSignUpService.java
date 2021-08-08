package com.midea.cloud.srm.bid.suppliercooperate.projectlist.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.entity.SignUp;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderHeadVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidSignUpVO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
*  <pre>
 *  供应商报名记录表 服务类
 * </pre>
*
* @author zhuomb1@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020-09-11 13:54:22
 *  修改内容:
 * </pre>
*/
public interface IBidSignUpService extends IService<SignUp> {
    /**
     * 保存报名记录
     * 保存附件上传记录
     * @param bidSignUpVO
     * @return
     */
    Long saveSignUpInfo(BidSignUpVO bidSignUpVO);

    /**
     * 通过bidingId和vendorId判断是否已经报名
     * 如果报名是驳回状态，再次报名的时候，新增一个报名记录
     * @param bidingId
     * @param vendorId
     * @return
     */
    SignUp alreadySignUpByBidingAndVendorId(long bidingId,long vendorId);

    /**
     * 获取报名信息，如果未报名，返回初始化信息
     * @param signUpVO
     * @return
     */
    BidSignUpVO getBidSignUpVO(BidSignUpVO signUpVO);

    boolean judgeSignUpCondition(BidSignUpVO signUpVO);

    /**
     * 报价行导出
     * @param bidOrderHeadVO
     * @param response
     * @throws Exception
     */
    void importModelDownload(BidOrderHeadVO bidOrderHeadVO, HttpServletResponse response) throws Exception;

    /**
     * 报价行导入
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload,Long bidingId) throws Exception;
}

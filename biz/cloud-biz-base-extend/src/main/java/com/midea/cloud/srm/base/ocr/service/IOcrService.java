package com.midea.cloud.srm.base.ocr.service;

import com.midea.cloud.srm.model.base.ocr.vo.CompanyResultVo;
import com.midea.cloud.srm.model.base.ocr.vo.ResultVo;

/**
*  <pre>
 *  OCR
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-15 12:42:29
 *  修改内容:
 * </pre>
*/
public interface IOcrService  {

    /**
     * 发送信息到ocr进行检查
     * @param param
     * @return
     */
    ResultVo checkLicenseFromOcr(byte[] param);

    void test();

    ResultVo recognizeImage(Long fileuploadId) ;

    void test2();

    CompanyResultVo recognizeLcImage(Long fileuploadId);
}

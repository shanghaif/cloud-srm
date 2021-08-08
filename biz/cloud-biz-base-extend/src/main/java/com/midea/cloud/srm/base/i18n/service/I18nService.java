package com.midea.cloud.srm.base.i18n.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *  <pre>
 *  国际化 服务类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-03-15 15:54:01
 *  修改内容:
 * </pre>
 */
public interface I18nService {

    void downloadI18nExcel() throws IOException;

    void convertI18nProp(MultipartFile i18nExcel) throws Exception;
}

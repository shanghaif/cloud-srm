package com.midea.cloud.srm.base.i18n.controller;

import com.midea.cloud.srm.base.i18n.service.I18nService;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <pre>
 *  国际化 前端控制器
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-03-15 15:54:01
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/base-anon/i18n")
public class I18nController extends BaseController {

    @Autowired
    I18nService i18nService;

    /**
     * 导出所有模块的国际化翻译文件excel
     *
     * @throws IOException
     */
    @RequestMapping("/downloadI18nExcel")
//    @PreAuthorize("hasAuthority('base:i18n:download-i18n-excel')")
    public void downloadI18nExcel() throws IOException {
        i18nService.downloadI18nExcel();
    }

    /**
     * 导出压缩所有模块的国际化翻译文件prop
     *
     * @throws IOException
     */
    @RequestMapping("/convertI18nProp")
//    @PreAuthorize("hasAuthority('base:i18n:convert-i18n')")
    public void convertI18nProp(@RequestParam("file") MultipartFile i18nExcel) throws Exception {
        i18nService.convertI18nProp(i18nExcel);
    }

}

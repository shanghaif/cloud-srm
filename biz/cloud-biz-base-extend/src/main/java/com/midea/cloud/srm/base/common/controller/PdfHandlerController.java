package com.midea.cloud.srm.base.common.controller;

import com.itextpdf.text.Image;
import com.midea.cloud.common.utils.PdfUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/pdf")
public class PdfHandlerController {

    @RequestMapping("/pdfAddWatermark")
    public void pdfAddWatermark(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        Assert.notNull(file, LocaleHandler.getLocaleMsg("文件不能为空！"));
        InputStream inputStream = file.getInputStream();
        ServletOutputStream outputStream = PdfUtil.getServletOutputStream(response, "abc");
        Image image = PdfUtil.getImage(this.getClass().getResourceAsStream("/image/Longi.png"));
        PdfUtil.pdfAddAll(inputStream,outputStream,image);
    }
}

package com.midea.cloud.srm.base.ocr.controller;

import com.midea.cloud.srm.base.ocr.service.IOcrService;
import com.midea.cloud.srm.model.base.ocr.vo.CompanyResultVo;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *  OCR 前端控制器
 * </pre>
 *
 * @author huanghb14@example.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-12 22:53:25
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/ocr")
public class OcrController extends BaseController {
    @Autowired
    private IOcrService iOcrService;

    /**
     * 识别图片
     * @param fileuploadId
     * @return
     */
     @GetMapping("/recognizeLcImage")
    public CompanyResultVo recognizeLcImage(Long fileuploadId){
         return iOcrService.recognizeLcImage(fileuploadId);
    }


    @GetMapping("/test")
    public void test(){
        iOcrService.test();
    }
    @GetMapping("/test2")
    public void test2(){
        iOcrService.test2();
    }
}

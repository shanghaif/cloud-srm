package com.midea.cloud.component.i18n;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <pre>
 * 多语言内部消息 前端控制器
 * </pre>`
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/03/15 17:12
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/job-anon/internal/language")
public class LanguageInternalController {

    /**
     * 获取多语言配置信息
     * @return
     */
    @RequestMapping("/getLanguages")
    public List<LinkedHashMap> getLanguages() {
        List<LinkedHashMap> properties = new ArrayList<>();
        properties.add(I18nProp.zh_props.getProps());
        properties.add(I18nProp.en_props.getProps());
        properties.add(I18nProp.ja_props.getProps());
        return properties;
    }

    /**
     * 获取多语言base64转码文件序列
     * @return
     */
    @RequestMapping("/getLanguageFiles")
    public List<String> getLanguageFiles() {
        List<String> languageFiles = new ArrayList<>();
        languageFiles.add(I18nProp.zh_base64File);
        languageFiles.add(I18nProp.en_base64File);
        languageFiles.add(I18nProp.ja_base64File);
        return languageFiles;
    }


}

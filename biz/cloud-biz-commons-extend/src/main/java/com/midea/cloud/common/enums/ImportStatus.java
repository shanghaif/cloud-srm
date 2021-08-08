package com.midea.cloud.common.enums;

import java.util.HashMap;
import java.util.Map;

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
 *  修改日期: 2020/9/19
 *  修改内容:
 * </pre>
 */
public class ImportStatus {
    /**
     * 导入失败返回结果
     * @param fileuploadId
     * @param fileSourceName
     * @return
     */
    public static Map<String,Object> importError(Long fileuploadId, String fileSourceName){
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.NO.getValue());
        result.put("message","error");
        result.put("fileuploadId",fileuploadId);
        result.put("fileName",fileSourceName);
        return result;
    }

    /**
     * 导入成功返回结果
     * @return
     */
    public static Map<String,Object> importSuccess(){
        Map<String, Object> result = new HashMap<>();
        result.put("status", YesOrNo.YES.getValue());
        result.put("message","success");
        return result;
    }
}

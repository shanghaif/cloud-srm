package com.midea.cloud.srm.cm.model.utils;

import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.model.cm.model.entity.ModelElement;

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
 *  修改日期: 2020/6/26
 *  修改内容:
 * </pre>
 */
public class BeanUtils {
    /**
     * 获取变量名和变量符号转为Map
     * @param modelElement
     * @return
     */
    public Map<String,String> beanToMap(ModelElement modelElement){
        HashMap<String, String> result = new HashMap<>();
        if(null != modelElement){
            String variableName = modelElement.getVariableName();
            String variableSign = modelElement.getVariableSign();
            if(StringUtil.notEmpty(variableName) && StringUtil.notEmpty(variableSign)){
                result.put(variableName,variableSign);
            }
        }
        return result;
    }

    /**
     * 组装id
     * @param id
     * @return
     */
    public static HashMap<String, Object> getStringObjectHashMap(Long id) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id",id);
        return result;
    }
}

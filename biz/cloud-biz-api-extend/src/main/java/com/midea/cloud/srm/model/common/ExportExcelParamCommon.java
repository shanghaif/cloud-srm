package com.midea.cloud.srm.model.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;

import lombok.Data;

/**
 * <pre>
 * excel导出传参模板
 * </pre>
 *
 * @author ex_wangpr@partner.midea.com
 * @version 2.00.00
 * <pre>
 * 修改记录:      修改人:      修改日期:       修改内容:
 * </pre>
 */
@Data
public class ExportExcelParamCommon<T> extends BaseDTO  {
	/**
     * 查询参数
     */
    private T queryParam;
    /**
     * 导出标题
     */
    private ArrayList<String> titleList;
    /**
     * 导出文件名字
     */
    private String fileName;
    
    /**
     * 导出标题
     */
    private ArrayList<String> languageList;
    
    /**
     * 地址
     */
    private String url;
    
    private T dictCodes;
    
    /**
     * 菜单名称
     */
    private String permissionName;

    /**
     * Description 获取多语言表头信息
     * @Param ExportExcelParam<T> T表示具体的实体类
     * @Param entityTitles 实体类的Excel表头集合
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.23
     **/
    public List<String> getMultilingualHeader(ExportExcelParamCommon exportExcelParam, LinkedHashMap<String, String> entityTitles) {
        List<String> titleList = exportExcelParam.getTitleList();
        List<String> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(titleList)) {
            titleList.forEach((title) -> {
                result.add(entityTitles.get(title));
            });
        }
        return result;
    }

}

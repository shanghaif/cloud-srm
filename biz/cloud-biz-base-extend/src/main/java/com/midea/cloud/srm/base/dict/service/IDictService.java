package com.midea.cloud.srm.base.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.dict.entity.Dict;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.bid.dto.BillingCombination;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  字典表 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-19 10:33:17
 *  修改内容:
 * </pre>
*/
public interface IDictService extends IService<Dict> {

    void saveOrUpdateDict(Dict dict);

    PageInfo<Dict> queryPageByConditions(Dict dict, Integer pageNum, Integer pageSize);

    /**
     * 通过计费方式查找计费单位
     * @param chargeMethod
     * @return
     */
    List<BillingCombination> queryBillingCombination(String chargeMethod);

    /**
     * 通过计费方式查找计费单位
     * @param chargeMethod
     * @return 计费方式编码-(计费单位-计费单位名称)
     */
    Map<String, Map<String,String>> queryBillingCombinationMap(List<String> chargeMethod);

    /**
     * 导出左边字典
     * @param excelParam
     * @param response
     * @throws IOException
     */
    void exportExcel(Dict excelParam, HttpServletResponse response)throws IOException;
    /**
     * 自定义导出左边字典
     */
    void exportExcel(List<Long> ids, HttpServletResponse response)throws IOException;
    /**
     * 字典左边导入模板
     */
    void leftImportExcelTemplate(HttpServletResponse response) throws IOException;

    /**
     * 字典左边导入
     */
    Map<String,Object> leftImportExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 批量删除
     */
    void removeDictAndItems(List<Long>ids) throws Exception;
}

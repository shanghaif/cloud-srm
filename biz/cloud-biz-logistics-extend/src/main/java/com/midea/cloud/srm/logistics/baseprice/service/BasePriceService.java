package com.midea.cloud.srm.logistics.baseprice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.baseprice.entity.BasePrice;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
* <pre>
 *  物流招标基础价格 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 26, 2021 4:32:59 PM
 *  修改内容:
 * </pre>
*/
public interface BasePriceService extends IService<BasePrice>{
    /*
     批量增加
     */
    void batchAdd(List<BasePrice> basePriceList) throws IOException;

    /*
     批量更新
     */
    void batchUpdate(List<BasePrice> basePriceList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;

    /*
    导出excel模板文件
    */
    public void exportExcelTemplate(HttpServletResponse response) throws IOException;

    /*
    导入excel 内容
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /*
     导出excel内容数据
     param ： 传入参数
     */
    void exportExcel(BasePrice excelParam, HttpServletResponse response)throws IOException;
   /*
   分页查询
    */
    PageInfo<BasePrice> listPage(BasePrice basePrice);

    /**
     * 批量生效
     * @param basePriceIds
     */
    void effect(List<Long> basePriceIds);

    /**
     * 批量失效
     * @param basePriceIds
     */
    void invalid(List<Long> basePriceIds);


}

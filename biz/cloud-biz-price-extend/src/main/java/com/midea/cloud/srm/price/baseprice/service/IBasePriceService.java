package com.midea.cloud.srm.price.baseprice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.price.baseprice.entity.BasePrice;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
*  <pre>
 *  基价表 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:16:13
 *  修改内容:
 * </pre>
*/
public interface IBasePriceService extends IService<BasePrice> {

    /**
     * 分页查询
     * @param basePrice
     * @return
     */
    PageInfo<BasePrice> listPage(BasePrice basePrice);

    /**
     * 新增
     * @param basePrice
     * @return
     */
    Long add(BasePrice basePrice);

    /**
     * 生效
     * @param basePriceId
     */
    void takeEffect(Long basePriceId);

    /**
     * 失效
     * @param basePriceId
     */
    void failure(Long basePriceId);

    /**
     * 根据成本要素编码和属性值查询
     */
    BasePrice queryBy(BasePrice basePrice);

    /**
     * 基价导入模板下载
     * @param basePrice
     * @param response
     */
    void importModelDownload(BasePrice basePrice, HttpServletResponse response) throws IOException;

    /**
     * 基价导入
     * @param file
     * @param fileupload
     * @return
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload);

}

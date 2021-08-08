package com.midea.cloud.srm.supcooperate.material.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.material.dto.CeeaMaterialItemDTO;
import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialItem;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  物料计划维护表 服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 23:38:18
 *  修改内容:
 * </pre>
*/
public interface IMaterialItemService extends IService<CeeaMaterialItem> {
    PageInfo<CeeaMaterialItem> getMaterialItemList(CeeaMaterialItemDTO materialItemDTO);
    CeeaMaterialItemDTO getMaterialItemDetail(Long id);

    /**
     * 下载物料计划导入模板
     * @param monthlySchDate
     * @param response
     * @throws IOException
     */
    void importModelDownload(String monthlySchDate,HttpServletResponse response) throws IOException, ParseException;

    /**
     * 下载物料计划详情导入模板
     * @param materialItemId
     * @param response
     * @throws IOException
     */
    void importModelDetailDownload(String materialItemId,HttpServletResponse response) throws IOException, ParseException;

    /**
     * 导入物料计划
     * @param file
     * @param fileupload
     * @return
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload,HttpServletResponse response) throws IOException, ParseException;


    /**
     * 导入物料计划详情
     * @param file
     * @param fileupload
     * @return
     */
    Map<String,Object> importDetailExcel(String materialItemId,MultipartFile file, Fileupload fileupload,HttpServletResponse response) throws IOException, ParseException;

    /**
     * 物料计划导出
     * @param materialItemDTO
     * @param response
     */
    void export(CeeaMaterialItemDTO materialItemDTO, HttpServletResponse response) throws ParseException, IOException;
    /**
     * 物料计划详情导出
     * @param materialItemId
     * @param response
     */
    void exportDetail(String materialItemId, HttpServletResponse response) throws ParseException, IOException;
}

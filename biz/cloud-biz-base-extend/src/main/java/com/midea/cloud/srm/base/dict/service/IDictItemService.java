package com.midea.cloud.srm.base.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemQueryDTO;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *  <pre>
 *  字典条目 服务类
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-19 10:33:18
 *  修改内容:
 * </pre>
 */
public interface IDictItemService extends IService<DictItem> {


    void saveOrUpdateDictItem(DictItem dictItem);

    PageInfo<DictItem> queryPageByConditions(DictItem dictItem, Integer pageNum, Integer pageSize);

    DictItemDTO queryById(Long id);

    List<DictItemDTO> listAllByDictCode(String dictCode);

    List<DictItemDTO> listByDictCode(List<String> dictCodes);

    List<DictItemDTO> listAllByParam(DictItemDTO requestDto);

    List<Map<String, Object>> listAllByListParam(List<DictItemDTO> requestDtos);

    List<DictItemDTO> getDictItemsByDictCodeAndDictItemNames(DictItemQueryDTO dictItemQueryDTO);

    List<DictItem> listDictItemsByParam(DictItemDTO dictItemDTO);
    
    List<DictItemDTO> queryProductType();

    /**
     * 字典右边导入模板
     */
    void rightImportExcelTemplate(HttpServletResponse response ) throws IOException;

    /**
     * 字典右边导出
     */
    void exportExcel(List<Long> ids, HttpServletResponse response) throws IOException;

    /**
     * 字典右边导入
     */
    Map<String,Object> rightImportExcel(MultipartFile file, Fileupload fileupload) throws Exception;
}

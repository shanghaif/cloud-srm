package com.midea.cloud.srm.base.dict.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.dict.mapper.DictItemMapper;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.base.dict.service.IDictLanguageService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.*;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.dict.entity.DictLanguage;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  <pre>
 *  字典条目 服务实现类
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
@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements IDictItemService {
    @Resource
    private DictItemMapper dictItemMapper;
    @Resource
    private FileCenterClient fileCenterClient;
    @Resource
    private IDictLanguageService dictLanguageService;


    @Override
    @Transactional
    public void saveOrUpdateDictItem(DictItem dictItem) {
        if(dictItem == null || StringUtils.isBlank(dictItem.getDictItemCode())
                || StringUtils.isBlank(dictItem.getItemLanguage())
                || StringUtils.isBlank(dictItem.getDictItemName())
                || dictItem.getActiveDate() == null ){
            throw new BaseException("字典条目的编码、语言、名称、生效日期不能为空");
        }
        if(StringUtil.checkString(dictItem.getDictItemCode())){
            throw new BaseException("字典条目编码包含特殊字符");
        }

        //相同的“字典编码+字典语言”，条目名称和编码不能重复
        Boolean existsBaseDict = false;
        if(dictItem.getDictItemId() != null){
            existsBaseDict = this.existsNameBaseDictItem(dictItem.getDictItemName(), dictItem.getDictItemCode()
                    , dictItem.getDictItemId(), dictItem.getDictId());
        }else{
            existsBaseDict = this.existsNameBaseDictItem(dictItem.getDictItemName(), dictItem.getDictItemCode()
                    ,null, dictItem.getDictId());
        }

        if(existsBaseDict){
            throw new BaseException("当前配置的字典条目编码或名称重复:"+ dictItem.getDictItemName());
        }

        if(dictItem.getDictItemId()!= null){
            dictItem.setLastUpdateDate(new Date());
            dictItemMapper.updateById(dictItem);
        }else{

            dictItem.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            dictItem.setDictItemId(id);
            dictItemMapper.insert(dictItem);
        }


    }


    private boolean existsNameBaseDictItem(String dictItemName,String dictItemCode,Long id,Long dictId){
        return  dictItemMapper.findByNameOrCode(dictItemName,dictItemCode,id,dictId)>0;
    };


    @Override
    public PageInfo<DictItem> queryPageByConditions(DictItem dictItem, Integer pageNum, Integer pageSize) {
        PageUtil.startPage(pageNum, pageSize);
        List<DictItem> dictItems = dictItemMapper.queryPageByConditions(dictItem);
        PageInfo<DictItem> pageInfo = new PageInfo<>(dictItems);
        return pageInfo;
    }

    @Override
    public DictItemDTO queryById(Long id) {
        return dictItemMapper.queryById(id);
    }

    @Override
    public List<DictItemDTO> listAllByDictCode(String dictCode) {
        return dictItemMapper.listAllByDictCode(dictCode,LocaleHandler.getLocaleKey());
    }

    @Override
    public List<DictItemDTO> listByDictCode(List<String> dictCodes) {
        return dictItemMapper.listByDictCode(dictCodes,LocaleHandler.getLocaleKey());
    }

    @Override
    public List<DictItemDTO> listAllByParam(DictItemDTO requestDto) {
        // 获取当前用户（游客）的国家标识名称
        String localeKey = LocaleHandler.getLocaleKey();
        requestDto.setLanguage(localeKey);
        return dictItemMapper.listAllByParam(requestDto);
    }

    @Override
    public List<Map<String, Object>> listAllByListParam(List<DictItemDTO> requestDtos) {
        List<Map<String, Object>>  mapList= new ArrayList<>();
        for(DictItemDTO request:requestDtos){
            request.setLanguage(LocaleHandler.getLocaleKey());
            List<DictItemDTO> dictItemDTOS = dictItemMapper.listAllByParam(request);
            Map<String, Object> map = new HashMap<>();
            if(!CollectionUtils.isEmpty(dictItemDTOS)) {
                map.put(dictItemDTOS.get(0).getDictCode(), dictItemDTOS);
            }else{
                map.put(request.getDictCode(), dictItemDTOS);
            }
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<DictItemDTO> getDictItemsByDictCodeAndDictItemNames(DictItemQueryDTO dto) {
        String dictCode = dto.getDictCode();
        List<String> dictItemNames = dto.getDictItemNames();
        List<DictItemDTO> dictItemDTOList = dictItemMapper.getDictItemsByDictCodeAndDictItemNames(dictCode, dictItemNames);
        return dictItemDTOList;
    }

    @Override
    public List<DictItem> listDictItemsByParam(DictItemDTO dictItemDTO) {
        DictItem dictItem = new DictItem();
        BeanUtils.copyProperties(dictItemDTO, dictItem);
        List<DictItem> dictItems = this.list(new QueryWrapper<>(dictItem));
        return dictItems;
    }
    
    @Override
    public List<DictItemDTO> queryProductType() {
    	List<DictItemDTO> list = dictItemMapper.queryProductType();
    	//标准产品 默认第二级
    	if (list.size() > 1) {
    		list.get(1).setIsDefault(true);
    	}
        return list;
    }

    /**
     * 右边导入模板
     * @param response
     * @throws IOException
     */
    @Override
    public void rightImportExcelTemplate(HttpServletResponse response) throws IOException {
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(ExportExcelDictItemDTORight.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new ExportExcelDictItemDTORight()));
    }

    @Override
    public void exportExcel(List<Long> ids, HttpServletResponse response) throws IOException {
        // 查询数据
        List<ExcelDictItemDTO> excelDictItemDTOS = dictItemMapper.getExportData(ids);
        List<ExportExcelDictItemDTORight> exportExcelDictItemDTORights =new ArrayList<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(excelDictItemDTOS)){
            excelDictItemDTOS.forEach(dictItem -> {
                ExportExcelDictItemDTORight exportExcelDictItemDTORight = new ExportExcelDictItemDTORight();
                BeanCopyUtil.copyProperties(exportExcelDictItemDTORight,dictItem);
                exportExcelDictItemDTORights.add(exportExcelDictItemDTORight);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExportExcelDictItemDTORight.class).sheet(0).sheetName("sheetName").doWrite(exportExcelDictItemDTORights);
    }

    @Override
    @Transactional
    public Map<String, Object> rightImportExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExportExcelDictItemDTORight> exportExcelDictItemDTORights = EasyExcelUtil.readExcelWithModel(file, ExportExcelDictItemDTORight.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<DictItem> dictItems = chackImportParam(exportExcelDictItemDTORights, errorFlag);

        if(errorFlag.get()){
            // 保存
            this.saveBatch(dictItems);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, exportExcelDictItemDTORights, ExportExcelDictItemDTORight.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 导入校验
     * @param exportExcelDictItemDTORights
     * @param errorFlag
     * @return
     */
    public List<DictItem> chackImportParam(List<ExportExcelDictItemDTORight> exportExcelDictItemDTORights, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<DictItem> dictItems = new ArrayList<>();
        Set<String> set =new HashSet<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(exportExcelDictItemDTORights)){
            exportExcelDictItemDTORights.forEach(exportExcelDictItemDTORight -> {
                DictItem dictItem = new DictItem();
                StringBuffer errorMsg = new StringBuffer();
                // 检查 必填项
                String dictCode = exportExcelDictItemDTORight.getDictCode();
                if(ObjectUtils.isEmpty(dictCode)){
                    errorMsg.append("字典编码不能为空 ");
                    errorFlag.set(false);
                }
                String languageName = exportExcelDictItemDTORight.getLanguageName();
                if(ObjectUtils.isEmpty(languageName)){
                    errorMsg.append("字典语言名称不能为空 ");
                    errorFlag.set(false);
                }
                String dictName = exportExcelDictItemDTORight.getDictName();
                if(ObjectUtils.isEmpty(dictName)){
                    errorMsg.append("字典名称不能为空 ");
                    errorFlag.set(false);
                }
                Date activeDate = exportExcelDictItemDTORight.getActiveDate();
                if(ObjectUtils.isEmpty(activeDate)){
                    errorMsg.append("生效日期不能为空 ");
                    errorFlag.set(false);
                }
                String dictItemCode = exportExcelDictItemDTORight.getDictItemCode();
                if(ObjectUtils.isEmpty(dictItemCode)){
                    errorMsg.append("条目编码不能为空 ");
                    errorFlag.set(false);
                }
                String itemLanguageName = exportExcelDictItemDTORight.getItemLanguageName();
                if(ObjectUtils.isEmpty(itemLanguageName)){
                    errorMsg.append("条目语言名称不能为空 ");
                    errorFlag.set(false);
                }
                String dictItemName = exportExcelDictItemDTORight.getDictItemName();
                if(ObjectUtils.isEmpty(dictItemName)){
                    errorMsg.append("条目名称不能为空 ");
                    errorFlag.set(false);
                }
                //判断是否已重复
                if(errorMsg.length() == 0){
                    //数组重复
                    if(!(set.add(dictCode+languageName+dictName+dictItemCode+itemLanguageName+dictItemName))) {
                        errorMsg.append("该数据存在重复值在Excel中");
                        errorFlag.set(false);
                        exportExcelDictItemDTORight.setErrorMsg(errorMsg.toString());
                        return;
                    }
                    DictLanguage dictLanguage =dictLanguageService.getOne(Wrappers.lambdaQuery(DictLanguage.class)
                                    .eq(DictLanguage::getLanguageName,languageName)
                    );
                    if(dictLanguage == null){
                        errorMsg.append("不存在这样的字典语言");
                        errorFlag.set(false);
                        exportExcelDictItemDTORight.setErrorMsg(errorMsg.toString());
                        return;
                    }
                    //判断数据库是否有记录
                    List<ExcelDictItemDTO> excelDictItemDTOS =dictItemMapper.getRecordsByDictProperties(dictCode,dictLanguage.getLanguage(),dictName);
                    if(excelDictItemDTOS == null || excelDictItemDTOS.size() == 0) {
                        errorMsg.append("数据库不存在这样的头字典");
                        errorFlag.set(false);
                        exportExcelDictItemDTORight.setErrorMsg(errorMsg.toString());
                        return;
                    }

                    DictLanguage dictItemLanguage =dictLanguageService.getOne(Wrappers.lambdaQuery(DictLanguage.class)
                            .eq(DictLanguage::getLanguageName,itemLanguageName)
                    );
                    if(dictItemLanguage == null){
                        errorMsg.append("不存在这样的条目语言");
                        errorFlag.set(false);
                        exportExcelDictItemDTORight.setErrorMsg(errorMsg.toString());
                        return;
                    }
                    exportExcelDictItemDTORight.setItemLanguage(dictItemLanguage.getLanguage());
                    //数据库重复
                    if(checkIsRepeat(excelDictItemDTOS,exportExcelDictItemDTORight)){
                        String err = "该记录在数据库已经存在";
                        errorMsg.append(err);
                        errorFlag.set(false);
                        exportExcelDictItemDTORight.setErrorMsg(errorMsg.toString());
                    }
                    else {
                        //不存在就新增
                        Long id = IdGenrator.generate();
                        BeanCopyUtil.copyProperties(dictItem, exportExcelDictItemDTORight);
                        dictItem.setDictId(excelDictItemDTOS.get(0).getDictId());
                        LocalDate localDate =exportExcelDictItemDTORight.getActiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        dictItem.setActiveDate(localDate);
                        dictItem.setDictItemId(id);
                        dictItems.add(dictItem);
                    }

                }else {
                    exportExcelDictItemDTORight.setErrorMsg(errorMsg.toString());
                }
            });
        }
        return dictItems;
    }
    /**
     * 导入判断是否存在数据库
     * @param
     * @return
     */
    public boolean checkIsRepeat(List<ExcelDictItemDTO> excelDictItemDTOS,ExportExcelDictItemDTORight exportExcelDictItemDTORight) {
        Set<String>set =new HashSet<>();
        boolean flag =false;
        String temp =
                exportExcelDictItemDTORight.getDictItemCode()+exportExcelDictItemDTORight.getDictItemName()+exportExcelDictItemDTORight.getItemLanguage();
        set.add(temp);
        for(ExcelDictItemDTO excelDictItemDTO : excelDictItemDTOS) {
            temp =excelDictItemDTO.getDictItemCode()+excelDictItemDTO.getDictItemName()+excelDictItemDTO.getItemLanguage();
            if(!set.add(temp)) {
                flag =true;
                break;
            }
        }
        return flag;
    }

}

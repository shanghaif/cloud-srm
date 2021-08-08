package com.midea.cloud.srm.base.dict.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.dict.mapper.DictItemMapper;
import com.midea.cloud.srm.base.dict.mapper.DictMapper;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.base.dict.service.IDictLanguageService;
import com.midea.cloud.srm.base.dict.service.IDictService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.ExportExcelDictDTOLeft;
import com.midea.cloud.srm.model.base.dict.dto.ImportExcelDictDTOLeft;
import com.midea.cloud.srm.model.base.dict.entity.Dict;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.dict.entity.DictLanguage;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.bid.dto.BillingCombination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
*  <pre>
 *  字典表 服务实现类
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
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Autowired
    private DictMapper dictMapper;
    @Resource
    private FileCenterClient fileCenterClient;
    @Resource
    private IDictItemService iDictItemService;
    @Resource
    private DictItemMapper dictItemMapper;
    @Resource
    private IDictLanguageService dictLanguageService;

    // 计费组合字典编码
    public static final String BILLING_COMBINATION = "BILLING_COMBINATION";

    @Override
    public Map<String, Map<String,String>> queryBillingCombinationMap(List<String> chargeMethodList) {
        Map<String, Map<String, String>> map = new HashMap<>();
        if(CollectionUtils.isNotEmpty(chargeMethodList)){
            String localeKey = LocaleHandler.getLocaleKey();
            // 获取字典
            Dict dict = this.getOne(Wrappers.lambdaQuery(Dict.class).
                    eq(Dict::getDictCode, BILLING_COMBINATION).
                    eq(Dict::getLanguage, localeKey).
                    last("LIMIT 1"));
            if(null != dict){
                List<DictItem> itemList = iDictItemService.list(Wrappers.lambdaQuery(DictItem.class).
                        eq(DictItem::getItemLanguage, localeKey).
                        in(DictItem::getDictItemName,chargeMethodList).
                        and(wrapper -> wrapper.isNull(DictItem::getInactiveDate).or().ge(DictItem::getInactiveDate, LocalDate.now())));
                if(CollectionUtils.isNotEmpty(itemList)){
                    List<String> codeList = itemList.stream().map(DictItem::getDictItemCode).collect(Collectors.toList());
                    codeList.forEach(code -> {
                        List<BillingCombination> billingCombinations = queryBillingCombination(code);
                        if(CollectionUtils.isNotEmpty(billingCombinations)){
                            Map<String, String> map1 = billingCombinations.stream().collect(Collectors.toMap(BillingCombination::getChargeUnit,BillingCombination::getChargeUnitName, (k1, k2) -> k1));
                            map.put(code,map1);
                        }
                    });
                }
            }
        }
        return map;
    }

    /**
     * 字典左边导出
     * @param excelParam
     * @param response
     * @throws IOException
     */
    @Override
    public void exportExcel(Dict excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<Dict> dicts = this.list(Wrappers.lambdaQuery(Dict.class));
        List<ExportExcelDictDTOLeft> exportExcelDictDTOLefts = new ArrayList<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dicts)){
            dicts.forEach(dict -> {
                ExportExcelDictDTOLeft exportExcelDictDTOLeft = new ExportExcelDictDTOLeft();
                BeanCopyUtil.copyProperties(exportExcelDictDTOLeft,dict);
                exportExcelDictDTOLefts.add(exportExcelDictDTOLeft);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExportExcelDictDTOLeft.class).sheet(0).sheetName("sheetName").doWrite(exportExcelDictDTOLefts);
    }
    //字典左边导出
    @Override
    public void exportExcel(List<Long> ids, HttpServletResponse response) throws IOException {
        List<ExportExcelDictDTOLeft> exportExcelDictDTOLefts = new ArrayList<>();
        for(Long id :ids) {
            Dict dict =this.getById(id);
            ExportExcelDictDTOLeft exportExcelDictDTOLeft = new ExportExcelDictDTOLeft();
            BeanCopyUtil.copyProperties(exportExcelDictDTOLeft,dict);
            exportExcelDictDTOLefts.add(exportExcelDictDTOLeft);
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExportExcelDictDTOLeft.class).sheet(0).sheetName("sheetName").doWrite(exportExcelDictDTOLefts);
    }

    /**
     * 字典左边导入模板
     * @param response
     * @throws IOException
     */
    @Override
    public void leftImportExcelTemplate(HttpServletResponse response) throws IOException {
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(ExportExcelDictDTOLeft.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new ExportExcelDictDTOLeft()));
    }

    /**
     * 导入
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Map<String, Object> leftImportExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExportExcelDictDTOLeft> exportExcelDictDTOLefts = EasyExcelUtil.readExcelWithModel(file, ExportExcelDictDTOLeft.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<Dict> dicts = chackImportParam(exportExcelDictDTOLefts, errorFlag);
        if(errorFlag.get()){
            // 保存
            this.saveBatch(dicts);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, exportExcelDictDTOLefts, ExportExcelDictDTOLeft.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }

    @Override
    public void removeDictAndItems(List<Long> ids) throws Exception{
        this.removeByIds(ids);
        iDictItemService.remove(Wrappers.lambdaQuery(DictItem.class).in(DictItem::getDictId,ids));
    }

    public List<Dict> chackImportParam(List<ExportExcelDictDTOLeft> exportExcelDictDTOLefts, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<Dict> dicts = new ArrayList<>();
        Set<String> set =new HashSet<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(exportExcelDictDTOLefts)){
            exportExcelDictDTOLefts.forEach(exportExcelDictDTOLeft -> {
                Dict dict = new Dict();
                StringBuffer errorMsg = new StringBuffer();
                // 检查 必填项
                String dictCode = exportExcelDictDTOLeft.getDictCode();
                if(ObjectUtils.isEmpty(dictCode)){
                    errorMsg.append("字典编码不能为空 ");
                    errorFlag.set(false);
                }
                String languageName = exportExcelDictDTOLeft.getLanguageName();
                if(ObjectUtils.isEmpty(languageName)){
                    errorMsg.append("字典语言不能为空 ");
                    errorFlag.set(false);
                }
                String dictName = exportExcelDictDTOLeft.getDictName();
                if(ObjectUtils.isEmpty(dictName)){
                    errorMsg.append("字典名称不能为空 ");
                    errorFlag.set(false);
                }
                Date activeDate = exportExcelDictDTOLeft.getActiveDate();
                if(ObjectUtils.isEmpty(activeDate)){
                    errorMsg.append("生效日期不能为空 ");
                    errorFlag.set(false);
                }
                //判断是否已重复
                if(errorMsg.length() == 0){
                    BeanCopyUtil.copyProperties(dict, exportExcelDictDTOLeft);
                    //数组重复
                    if(!(set.add(dictCode+languageName+dictName))) {
                        errorMsg.append("该数据存在重复值在Excel中");
                        errorFlag.set(false);
                        exportExcelDictDTOLeft.setErrorMsg(errorMsg.toString());
                        return;
                    }
                    //数据库重复
                    DictLanguage dictLanguage =
                            dictLanguageService.getOne(Wrappers.lambdaQuery(DictLanguage.class)
                                    .eq(DictLanguage::getLanguageName,exportExcelDictDTOLeft.getLanguageName()));
                    if(dictLanguage == null){
                        errorMsg.append("不存在这样的字典语言");
                        errorFlag.set(false);
                        exportExcelDictDTOLeft.setErrorMsg(errorMsg.toString());
                        return;
                    }
                    dict.setLanguage(dictLanguage.getLanguage());
                    if(!checkIsRepeat(dict)){
                        String err = "已经存在同样的字典编码、字典语言在数据库中";
                        errorMsg.append(err);
                        errorFlag.set(false);
                        exportExcelDictDTOLeft.setErrorMsg(errorMsg.toString());
                    }
                    else {
                        //不存在就新增
                        Long id = IdGenrator.generate();
                        dict.setDictId(id);
                        dicts.add(dict);
                    }

                }else {
                    exportExcelDictDTOLeft.setErrorMsg(errorMsg.toString());
                }
            });
        }
        return dicts;
    }

    /**
     * 导入判断是否存在数据库
     * @param
     * @return
     */
    public boolean checkIsRepeat(Dict dict) {
        Dict isNull =this.getOne(Wrappers.lambdaQuery(Dict.class)
                .eq(Dict::getDictCode,dict.getDictCode())
                .eq(Dict::getLanguage,dict.getLanguage())
        );//这里还要加一个生效日期
        if( isNull != null) {
            return false;
        }
        return true;
    }

    @Override
    public List<BillingCombination> queryBillingCombination(String chargeMethod) {
        List<BillingCombination> billingCombinations = new ArrayList<>();
        String localeKey = LocaleHandler.getLocaleKey();
        // 获取字典
        Dict dict = this.getOne(Wrappers.lambdaQuery(Dict.class).
                eq(Dict::getDictCode, BILLING_COMBINATION).
                eq(Dict::getLanguage, localeKey).
                last("LIMIT 1"));
        Assert.notNull(dict,"请维护[计费组合]字典!");
        // 查询字典条目
        List<DictItem> dictItems = dictItemMapper.queryDictItemBy(localeKey,dict.getDictId(),chargeMethod);
        if(CollectionUtils.isNotEmpty(dictItems)){
            /**
             * 计费组合名称  dictItemName
             * 计费方式编码  itemDescription
             * 计费单位编码  dictItemMark
             */
            List<String> dictItemMarks = dictItems.stream().map(DictItem::getDictItemMark).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(dictItemMarks)) {
                List<DictItem> itemList = iDictItemService.list(Wrappers.lambdaQuery(DictItem.class).
                        eq(DictItem::getItemLanguage, localeKey).
                        in(DictItem::getDictItemCode, dictItemMarks));
                if(CollectionUtils.isNotEmpty(itemList)){
                    billingCombinations = itemList.stream().map(dictItem -> {
                        return new BillingCombination().
                                setChargeUnit(dictItem.getDictItemCode()).
                                setChargeUnitName(dictItem.getDictItemName());
                    }).collect(Collectors.toList());
                }
            }
        }
        return billingCombinations;
    }

    @Override
    @Transactional
    public void  saveOrUpdateDict(Dict dict) {
        if(dict == null || StringUtils.isBlank(dict.getDictCode())
         || StringUtils.isBlank(dict.getLanguage())
                || StringUtils.isBlank(dict.getDictName())
                  || dict.getActiveDate() == null ){
            throw new BaseException(LocaleHandler.getLocaleMsg("字典的编码、语言、名称、生效日期不能为空"));
        }
        if(StringUtil.checkString(dict.getDictCode())){
            throw new BaseException(LocaleHandler.getLocaleMsg("字典编码包含特殊字符"));
        }

        //判断是否已重复配置以及是否重复命名
        Boolean existsBaseDict = false;
            existsBaseDict = this.existsLanguageAndCodeBaseDict(dict.getDictCode(), dict.getLanguage()
                    , dict.getDictId());

        if(existsBaseDict){

            throw new BaseException(LocaleHandler.getLocaleMsg("当前字典已配置",dict.getDictCode(),dict.getLanguage()));
        }


        if(dict.getDictId()!= null){
            dict.setLastUpdateDate(new Date());
            dictMapper.updateById(dict);
        }else{
            dict.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            dict.setDictId(id);
            if(dict.getActiveDate() == null) {
                dict.setActiveDate(new Date());
            }
            dictMapper.insert(dict);
        }
    }

    private boolean existsLanguageAndCodeBaseDict(String dictCode,String language,Long id){
        return  dictMapper.findByLanguageAndCode(dictCode,language,id) >0;
    };
    private boolean existsNameBaseDict(String dictName,Long id){
        return  dictMapper.findByNameAndId(dictName,id)>0;
    };


   public PageInfo<Dict> queryPageByConditions(Dict dict, Integer pageNum, Integer pageSize){
        PageUtil.startPage(pageNum,pageSize);
//       List<Dict> dictPages = dictMapper.queryPageByConditions(dict);
       QueryWrapper<Dict> wrapper =new QueryWrapper<>();
       wrapper.eq(dict.getLanguage()!=null,"LANGUAGE",dict.getLanguage());
       if(dict.getDictCode() !=null) {
           final String[] dictCodeArray =dict.getDictCode().split("；|;");
           if (dictCodeArray.length != 0) {
               wrapper.and( i -> {
                   for (String str : dictCodeArray) {
                       i.or().like("DICT_CODE",str);
                   }
               });

           }
       }
       if(dict.getDictName() !=null) {
           final String[] dictNameArray =dict.getDictName().split("；|;");
           wrapper.and( i ->{
               if (dictNameArray.length != 0) {
                   for (String str : dictNameArray) {
                       i.or().like("DICT_NAME",str);
                   }
               }
           });
       }
       wrapper.like(dict.getDescription()!=null,"DESCRIPTION",dict.getDescription());
       wrapper.orderByDesc("CREATION_DATE");
       List<Dict> dictPages = this.list(wrapper);
       PageInfo<Dict> pageInfo = new PageInfo<>(dictPages);
       return pageInfo;
    }

}


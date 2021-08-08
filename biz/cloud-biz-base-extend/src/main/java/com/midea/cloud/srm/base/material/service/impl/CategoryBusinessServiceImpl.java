package com.midea.cloud.srm.base.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.TitleColorSheetWriteHandler;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.base.material.mapper.CategoryBusinessMapper;
import com.midea.cloud.srm.base.material.service.ICategoryBusinessService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCategoryService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.material.dto.CategoryBusinessModelDto;
import com.midea.cloud.srm.model.base.material.entity.CategoryBusiness;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
*  <pre>
 *  物料小类业务小类维护表 服务实现类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-26 20:04:01
 *  修改内容:
 * </pre>
*/
@Service
public class CategoryBusinessServiceImpl extends ServiceImpl<CategoryBusinessMapper, CategoryBusiness> implements ICategoryBusinessService {

    @Autowired
    FileCenterClient fileCenterClient;
    @Autowired
    private IPurchaseCategoryService iPurchaseCategoryService;
    @Autowired
    private IDictItemService iDictItemService;
    /**
     * 条件查询
     * @param categoryBusiness
     * @return
     */
    @Override
    public PageInfo<CategoryBusiness> listByParam(CategoryBusiness categoryBusiness) {
        PageUtil.startPage(categoryBusiness.getPageNum(),categoryBusiness.getPageSize());
        List<CategoryBusiness> categoryBusinesses = new ArrayList<>();
        try {
            categoryBusinesses = getBaseMapper().selectList(Wrappers.lambdaQuery(categoryBusiness));
        }catch (Exception e){
            log.error("操作失败",e);
            throw new BaseException("分页获取物料小类信息时报错: ");
        }
        return new PageInfo<CategoryBusiness>(categoryBusinesses);
    }

    @Override
    public void importModelDownload(HttpServletResponse response) throws Exception {
        String fileName = "物料小类业务小类维护导入模板";
        List<CategoryBusinessModelDto> CategoryBusinessModels = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream, fileName, CategoryBusinessModels, CategoryBusinessModelDto.class);
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 校验传参
        EasyExcelUtil.checkParam(file, fileupload);
        // 返回结果map
        HashMap<String, Object> result = new HashMap<>();
        // 获取输入流
        InputStream inputStream = file.getInputStream();
        // 数据库bean
        ArrayList<CategoryBusiness> categoryBusinesses = new ArrayList<>();
        // 导入bean
        ArrayList<CategoryBusinessModelDto> categoryBusinessModels = new ArrayList<>();
        // 检查导入数据
        List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, CategoryBusinessModelDto.class);
        if (CollectionUtils.isNotEmpty(objects)) {
            objects.forEach((object -> {
                if (null != object) {
                    CategoryBusinessModelDto categoryBusinessModelDto = (CategoryBusinessModelDto) object;
                    CategoryBusiness categoryBusiness = new CategoryBusiness();
                    StringBuffer errorMsg = new StringBuffer();
                    //校验数据
                    checkParam(categoryBusinessModelDto, categoryBusiness, errorMsg);
                    if(StringUtils.isNotEmpty(categoryBusiness.getCategoryName())
                            && StringUtils.isNotEmpty(categoryBusiness.getBusinessLittleType())){
                        categoryBusinesses.add(categoryBusiness);
                    }
                    categoryBusinessModels.add(categoryBusinessModelDto);
                }
            }));
        }
        // 判断导入是否有错误信息
        Boolean flag = true;
        if (CollectionUtils.isNotEmpty(categoryBusinessModels)) {
            for (CategoryBusinessModelDto categoryBusinessModelDto : categoryBusinessModels) {
                String errorMsg = categoryBusinessModelDto.getErrorMsg();
                if (StringUtils.isNotEmpty(errorMsg)) {
                    flag = false;
                    break;
                }
            }
        }

        if (flag) {
            // 无错误信息保存数据
            if (CollectionUtils.isNotEmpty(categoryBusinesses)) {
                for (CategoryBusiness categoryBusiness : categoryBusinesses) {
                    QueryWrapper<CategoryBusiness> wrapper = new QueryWrapper<CategoryBusiness>();
                    wrapper.eq("BUSINESS_LITTLE_TYPE", categoryBusiness.getBusinessLittleType());
                    wrapper.eq("CATEGORY_NAME", categoryBusiness.getCategoryName());
                    Integer count = this.count(wrapper);
                    // 不存在库里的才插入
                    if (count == 0) {
                        this.save(categoryBusiness);
                    }
                }
            }
            result.put("status", YesOrNo.YES.getValue());
            result.put("message", "success");
            return result;
        } else {
            Fileupload errorFileUpload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    categoryBusinessModels, CategoryBusinessModelDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            result.put("status", YesOrNo.NO.getValue());
            result.put("message", "error");
            result.put("fileuploadId", errorFileUpload.getFileuploadId());
            result.put("fileName", errorFileUpload.getFileSourceName());
            return result;
        }
    }

    /**
     * Description 校验数据并且封装入库entity
     * @Param  [categoryBusinessModelDto, categoryBusiness, errorMsg]
     * @Author fansb3@meicloud.com
     * @Date 2020/9/30
     **/
    private void checkParam(CategoryBusinessModelDto categoryBusinessModelDto, CategoryBusiness categoryBusiness, StringBuffer errorMsg) {
        // 清空错误信息
        categoryBusinessModelDto.setErrorMsg(null);
        // 物料小类名称
        if (StringUtils.isNotEmpty(categoryBusinessModelDto.getCategoryName())) {
            String categoryName = categoryBusinessModelDto.getCategoryName();
            QueryWrapper<PurchaseCategory> wrapper = new QueryWrapper<PurchaseCategory>();
            wrapper.eq("level", 3);// 新增时快速下拉查询只查询等级为3的
            wrapper.eq("CATEGORY_NAME", categoryName);
            List<PurchaseCategory> list = iPurchaseCategoryService.list(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                PurchaseCategory purchaseCategory = list.get(0);
                categoryBusiness.setCategoryName(categoryName);
                categoryBusiness.setCategoryId(purchaseCategory.getCategoryId());
                categoryBusiness.setCategoryCode(purchaseCategory.getCategoryCode());
                categoryBusiness.setCategoryName(purchaseCategory.getCategoryName());
            } else {
                errorMsg.append("物料小类名称不存在,");
            }
        } else {
            errorMsg.append("物料小类名称不能为空,");
        }
        // 业务小类
        if (StringUtils.isNotEmpty(categoryBusinessModelDto.getBusinessLittleType())) {
            String BusinessLittleType = categoryBusinessModelDto.getBusinessLittleType();
            DictItemDTO dictItemDTO = new DictItemDTO();
            dictItemDTO.setDictItemCode(BusinessLittleType);
            dictItemDTO.setDictCode("BUSSINESS_LITTLE_TYPE"); // 业务小类字典编码
            List<DictItemDTO> dictItemDTOS = iDictItemService.listAllByParam(dictItemDTO);
            if (CollectionUtils.isNotEmpty(dictItemDTOS) && dictItemDTOS.size() > 0) {
                categoryBusiness.setBusinessLittleType(BusinessLittleType);
            } else {
                errorMsg.append("业务小类编码不存在,");
            }
        } else {
            errorMsg.append("业务小类编码不能为空,");
        }
        // Date转LocalDate
        try {
            String date = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_10);
            LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DateUtil.DATE_FORMAT_10));
            categoryBusiness.setStartDate(startDate);
        } catch (Exception e) {
            log.error("操作失败",e);
            throw new BaseException("导入Excel时时间转换异常: " );
        }
        // 生成表主键
        Long categoryBusinessId = IdGenrator.generate();
        categoryBusiness.setCategoryBusinessId(categoryBusinessId);
        // 保存错误信息
        categoryBusinessModelDto.setErrorMsg(errorMsg.toString());
    }
}

package com.midea.cloud.srm.base.materialsec.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.base.materialsec.service.IItemSceImageService;
import com.midea.cloud.srm.base.materialsec.service.IMaterialItemSecService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialItemSec;
import com.midea.cloud.srm.model.base.material.dto.MaterialItemSecDto;
import com.midea.cloud.srm.model.base.material.entity.ItemSceImage;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemSecVo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
*  <pre>
 *  物料附表维护 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-27 14:39:23
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base/material-item-sec")
public class MaterialItemSecController extends BaseController {

    @Autowired
    private IMaterialItemSecService iMaterialItemSecService;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private IMaterialItemService materialItemService;

    @Resource
    private IItemSceImageService itemSceImageService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public MaterialItemSec get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iMaterialItemSecService.getById(id);
    }

    /**
     * 新增
     * @param materialItemSecDto
     * @return
     */
    @Transactional
    @PostMapping("/add")
    public Long add(@RequestBody MaterialItemSecDto materialItemSecDto) {
        MaterialItemSec materialItemSec = materialItemSecDto.getMaterialItemSec();
        Long id = IdGenrator.generate();
        materialItemSec.setMaterialSecondaryId(id);
        /*根据物料id获取categoryFullName*/
        Assert.notNull(materialItemSecDto.getMaterialItemSec().getMaterialId(),LocaleHandler.getLocaleMsg("缺少必要参数：【materialId】"));
        MaterialItem materialItem = materialItemService.getById(materialItemSecDto.getMaterialItemSec().getMaterialId());
        if(materialItem == null){
            throw new BaseException(LocaleHandler.getLocaleMsg("请检查物料是否存在"));
        }
        if(StringUtils.isBlank(materialItem.getCategoryFullName())){
            throw new BaseException(LocaleHandler.getLocaleMsg("请检查物料的【categoryFullName】是否维护"));
        }
        materialItemSec.setCategoryFullName(materialItem.getCategoryFullName());
        iMaterialItemSecService.save(materialItemSec);
        List<Fileupload> fileuploadList = materialItemSecDto.getFileuploadList();
        if(!CollectionUtils.isEmpty(fileuploadList)){
            fileCenterClient.binding(fileuploadList.stream().map(item -> item.getFileuploadId()).collect(Collectors.toList()), id);
        }
        return id;
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iMaterialItemSecService.removeById(id);
    }

    /**
     * 修改
     * @param materialItemSecDto
     */
    @Transactional
    @PostMapping("/modify")
    public void modify(@RequestBody MaterialItemSecDto materialItemSecDto) {
        MaterialItemSec materialItemSec = materialItemSecDto.getMaterialItemSec();
        iMaterialItemSecService.updateById(materialItemSec);
        /*插入附件*/
        List<Fileupload> fileuploadList = materialItemSecDto.getFileuploadList();
        if(!CollectionUtils.isEmpty(fileuploadList)){
            fileCenterClient.binding(fileuploadList.stream().map(item -> item.getFileuploadId()).collect(Collectors.toList()), materialItemSec.getMaterialSecondaryId());
        }
        // 图片
        List<ItemSceImage> itemSceImages = materialItemSecDto.getItemSceImages();
        itemSceImageService.remove(Wrappers.lambdaQuery(ItemSceImage.class).eq(
                ItemSceImage::getMaterialSecondaryId,materialItemSec.getMaterialSecondaryId()
        ));
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(itemSceImages)){
            itemSceImages.forEach(itemSceImage -> {
                itemSceImage.setMaterialSecondaryId(materialItemSec.getMaterialSecondaryId());
                itemSceImage.setItemSceImageId(IdGenrator.generate());
            });
            itemSceImageService.saveBatch(itemSceImages);
        }
    }

    /**
    * 分页查询
    * @param materialItemSec
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<MaterialItemSec> listPage(@RequestBody MaterialItemSec materialItemSec) {
        PageUtil.startPage(materialItemSec.getPageNum(), materialItemSec.getPageSize());
        QueryWrapper<MaterialItemSec> wrapper = new QueryWrapper<MaterialItemSec>(materialItemSec);
        return new PageInfo<MaterialItemSec>(iMaterialItemSecService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<MaterialItemSec> listAll() {
        return iMaterialItemSecService.list();
    }

    /**
     * 通过条件查询物料
     * @return
     */
    @PostMapping("/listByParam")
    public List<MaterialItemSec> listByParam(@RequestBody MaterialItemSec materialItemSec){
        return iMaterialItemSecService.list(new QueryWrapper<>(materialItemSec));
    }


    /**
     * 根据价格库id查询物料附表
     * @param priceLibraryId
     * @return
     */
    @GetMapping("/getByPriceLibraryId")
    public MaterialItemSecVo getByPriceLibraryId(@RequestParam("priceLibraryId") Long priceLibraryId){
        if(priceLibraryId == null){
            throw new BaseException(LocaleHandler.getLocaleMsg("priceLibraryId必传"));
        }
        List<MaterialItemSec> materialItemSecList = iMaterialItemSecService.list(new QueryWrapper<>(new MaterialItemSec().setPriceLibraryId(priceLibraryId)));
        if(CollectionUtils.isEmpty(materialItemSecList)){
            return new MaterialItemSecVo();
        }else{
            MaterialItemSec materialItemSec = materialItemSecList.get(0);
            /*获取附件*/
            Fileupload param = new Fileupload();
            param.setBusinessId(materialItemSec.getMaterialSecondaryId());
            param.setPageNum(1);
            param.setPageSize(1000);
            List<Fileupload> fileuploadList = fileCenterClient.listPage(param, null).getList();
            return new MaterialItemSecVo().setMaterialItem(materialItemSec).setMatFiles(fileuploadList);
        }
    }

    /**
     * 根据物料编码，物料名称，供应商查询唯一物料附表
     */
    @PostMapping("/getByParam")
    public MaterialItemSecVo getByParam(@RequestBody MaterialItemSec materialItemSec){
        if(StringUtils.isBlank(materialItemSec.getMaterialCode())){
            throw new BaseException(LocaleHandler.getLocaleMsg("物料编码不可为空"));
        }
        if(StringUtils.isBlank(materialItemSec.getMaterialName())){
            throw new BaseException(LocaleHandler.getLocaleMsg("物料名称不可为空"));
        }
        if(Objects.isNull(materialItemSec.getCeeaSupplierId())){
            throw new BaseException(LocaleHandler.getLocaleMsg("供应商id不可为空"));
        }
        List<MaterialItemSec> materialItemSecList = iMaterialItemSecService.list(new QueryWrapper<>(materialItemSec));
        if(CollectionUtils.isEmpty(materialItemSecList)){
            return null;
        }else{
            MaterialItemSec m = materialItemSecList.get(0);
            MaterialItemSecVo materialItemSecVo = new MaterialItemSecVo();
            materialItemSecVo.setMaterialItem(m);
            /*获取附件*/
            Fileupload param = new Fileupload();
            param.setBusinessId(m.getMaterialSecondaryId());
            param.setPageNum(1);
            param.setPageSize(1000);
            // 查询附件
            List<Fileupload> fileuploadList = fileCenterClient.listPage(param, null).getList();
            materialItemSecVo.setMatFiles(fileuploadList);
            // 查询图片
            List<ItemSceImage> itemSceImages = itemSceImageService.list(Wrappers.lambdaQuery(ItemSceImage.class).
                    eq(ItemSceImage::getMaterialSecondaryId, m.getMaterialSecondaryId()));
            materialItemSecVo.setItemSceImages(itemSceImages);
            return materialItemSecVo;
        }
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iMaterialItemSecService.importModelDownload(response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iMaterialItemSecService.importExcel(file, fileupload);
    }

}

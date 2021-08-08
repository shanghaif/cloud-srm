package com.midea.cloud.srm.feign.file;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <pre>
 *  文件中心模块 内部调用Feign接口
 * </pre>
 *
 * @author weiliang zhu
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-16 17:19
 *  修改内容:
 * </pre>
 */
@FeignClient("file-center")
public interface FileCenterClient {


    // 文件上传下载功能 [upload] - >>>>>

    /**
     * 通过文件ID删除文件
     *
     * @param id
     */
    @PostMapping("/file/fileupload/delete")
    void delete(@RequestParam("id") Long id);

    /**
     * 通过查询条件删除
     *
     * @param fileupload
     */
    @PostMapping("/file/fileupload/deleteByParam")
    void deleteByParam(@RequestBody Fileupload fileupload);

    /**
     * 多个文件ID与业务单据ID关联
     *
     * @param fileuploadIds
     * @param businessId
     * @return
     */
    @PostMapping("/file/fileupload/binding")
    Boolean binding(@RequestParam("fileuploadIds") List<Long> fileuploadIds, @RequestParam("businessId") Long businessId);


    /**
     * 多个上传文件与业务ID关联
     * @param fileuploads
     * @param businessId
     * @return
     */
    @PostMapping("/file/fileupload/bindingFileupload")
    public Boolean bindingFileupload(@RequestBody List<Fileupload> fileuploads,
                                     @RequestParam("businessId") Long businessId);

    /**
     * 通过查询参数下载文件
     *
     * @param fileupload
     * @return
     */
    @RequestMapping(value = "/file/fileupload/download", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response downloadFileByParam(Fileupload fileupload);

    /**
     * 通过查询参数下载文件(暴露给外部系统)
     *
     * @param fileupload
     * @return
     */
    @RequestMapping(value = "/files-anon/download", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response downloadFileByParamForAnon(Fileupload fileupload);

    /**
     * 单文件上传
     * 根据fileSource选择上传方式，目前仅实现了上传到本地
     * 如有需要可上传到Fastdfs或者第三方，如阿里云、七牛等
     *
     * @param file
     * @param sourceType
     * @param uploadType
     * @param fileModular
     * @param fileFunction
     * @param fileType
     * @throws Exception
     */
    @RequestMapping(value = "/file/fileupload/feignClientUpload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Fileupload feignClientUpload(@RequestPart(value = "file") MultipartFile file,@RequestParam("sourceType") String sourceType
            ,@RequestParam("uploadType") String uploadType,@RequestParam("fileModular") String fileModular
            ,@RequestParam("fileFunction") String fileFunction,@RequestParam("fileType") String fileType);

    /**
     * 分页查询
     *
     * @param fileupload
     * @return
     */
    @PostMapping(value = "/file/fileupload/listPage", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public PageInfo<Fileupload> listPage(Fileupload fileupload, @RequestParam("convertBase64") String convertBase64);

    /**
     * 分页查询
     *
     * @param fileupload
     * @return
     */
    @PostMapping(value = "/file/fileupload/listPage", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public PageInfo<Fileupload> listByPage(Fileupload fileupload, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("convertBase64") String convertBase64);

    @PostMapping(value = "/file/fileupload/upload")
    public Fileupload upload(@RequestParam("file") MultipartFile file, Fileupload fileupload);

    // 文件上传下载功能 [upload] - <<<<<

    /**
     * feignClient单文件上传
     * 根据fileSource选择上传方式，目前仅实现了上传到本地
     * 如有需要可上传到Fastdfs或者第三方，如阿里云、七牛等
     *
     * @param file
     * @throws Exception
     */
    @RequestMapping(value = "/files-anon/file/fileupload/feignAnonClientUpload",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Fileupload feignAnonClientUpload(@RequestPart(value = "file") MultipartFile file,@RequestParam("sourceType") String sourceType
            ,@RequestParam("uploadType") String uploadType,@RequestParam("fileModular") String fileModular
            ,@RequestParam("fileFunction") String fileFunction,@RequestParam("fileType") String fileType);

    /**
     * 储存供应商范围ID
     */
    @PostMapping("/file/fileupload/saveVendorScopeById")
    void saveVendorScopeById(@RequestBody Fileupload file);

    /**
     * 通过供应商范围ID查找文件
     * @param vendorScopeId
     * @return
     */
    @PostMapping(value = "/file/fileupload/getByBusinessId")
    Fileupload getByBusinessId(@RequestParam("vendorScopeId")Long vendorScopeId);
    

    /**
     * 分页查询，内部调用
     *
     * @param fileupload
     * @return
     */
    @PostMapping(value = "/files-anon/internal/listPage", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public PageInfo<Fileupload> listPageInternal(@RequestBody Fileupload fileupload, @RequestParam("convertBase64") String convertBase64);
}

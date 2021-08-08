package com.midea.cloud.file.upload.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.ExceptionUtil;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.file.config.FileServiceFactory;
import com.midea.cloud.file.upload.service.FileService;
import com.midea.cloud.file.upload.service.IFileuploadService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  文件详情记录 前端控制器
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-04 13:39:58
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/file/fileupload")
@Slf4j
public class FileuploadController extends BaseController {

	@Autowired
	private IFileuploadService iFileuploadService;

	@Autowired
	private FileServiceFactory fileServiceFactory;

	/**
	 * 获取
	 *
	 * @param id
	 */
	@GetMapping("/get")
	public Fileupload get(Long id) {
		Assert.notNull(id, "id不能为空");
		return iFileuploadService.getById(id);
	}

	/**
	 * feignClient单文件上传 根据fileSource选择上传方式，目前仅实现了上传到本地 如有需要可上传到Fastdfs或者第三方，如阿里云、七牛等
	 *
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping(value = "/feignClientUpload", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Fileupload feignClientUpload(@RequestPart(value = "file") MultipartFile file,
			@RequestParam("sourceType") String sourceType, @RequestParam("uploadType") String uploadType,
			@RequestParam("fileModular") String fileModular, @RequestParam("fileFunction") String fileFunction,
			@RequestParam("fileType") String fileType) {
		FileService fileService = null;
		Fileupload fileupload = new Fileupload();
		fileupload.setSourceType(sourceType);
		fileupload.setUploadType(uploadType);
		fileupload.setFileModular(fileModular);
		fileupload.setFileFunction(fileFunction);
		fileupload.setFileType(fileType);
		try {
			if (fileupload.getUploadType() == null) {
				fileupload.setUploadType(FileUploadType.FASTDFS.name());
			}
			fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(fileupload.getUploadType()));
		} catch (Exception e) {
			throw new BaseException("文件上传方式有误");
		}
		try {
			return fileService.upload(file, fileupload);
		} catch (Exception ex) {
			log.error("文件上传失败", ex);
			throw new BaseException("文件上传失败"); // 支持国际化输出
		}
	}

	/**
	 * 单文件上传 根据fileSource选择上传方式，目前仅实现了上传到本地 如有需要可上传到Fastdfs或者第三方，如阿里云、七牛等
	 *
	 * @param file
	 * @param fileupload
	 * @throws Exception
	 */
	@PostMapping("/upload")
	public Fileupload upload(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
		FileService fileService = null;
		try {
			if (fileupload.getUploadType() == null) {
				fileupload.setUploadType(FileUploadType.FASTDFS.name());
			}
			fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(fileupload.getUploadType()));
		} catch (Exception e) {
			throw new BaseException("文件上传方式有误");
		}
		try {
			return fileService.upload(file, fileupload);
		} catch (Exception ex) {
			log.error("文件上传失败", ex);
//			throw new BaseException("文件上传失败"); // 支持国际化输出
			throw new BaseException(ExceptionUtil.getErrorMsg(ex));
		}
	}

	/**
	 * 多个文件上传 根据fileSource选择上传方式，目前仅实现了上传到本地 如有需要可上传到Fastdfs或者第三方，如阿里云、七牛等
	 *
	 * @param files
	 * @param fileupload
	 * @throws Exception
	 */
	@PostMapping("/uploads")
	public List<Fileupload> uploads(@RequestParam("files") MultipartFile[] files, Fileupload fileupload)
			throws Exception {
		FileService fileService = null;
		List<Fileupload> fileuploads = new ArrayList<>();
		try {
			if (fileupload.getUploadType() == null) {
				fileupload.setUploadType(FileUploadType.FASTDFS.name());
			}
			fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(fileupload.getUploadType()));
		} catch (Exception e) {
			throw new BaseException("文件上传方式有误");
		}
		for (MultipartFile file : files) {
			try {
				Fileupload upload = fileService.upload(file, fileupload);
				fileuploads.add(upload);
			} catch (Exception ex) {
				log.error("文件上传失败", ex);
				throw new BaseException("文件上传失败"); // 支持国际化输出
			}
		}
		return fileuploads;
	}

	/**
	 * 删除
	 *
	 * @param id
	 */
	@PostMapping("/delete")
	public void delete(Long id) {
		Assert.notNull(id, "id不能为空");
		Fileupload fileupload = iFileuploadService.getById(id);
		if (fileupload == null) {
			throw new BaseException("文件不存在");
		}
		FileService fileService = null;
		try {
			fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(fileupload.getUploadType()));
		} catch (Exception e) {
			throw new BaseException("文件上传方式有误");
		}
		try {
			fileService.delete(fileupload); // 删除数据库记录及物理文件
		} catch (Exception ex) {
			log.error("文件删除失败", ex);
			throw new BaseException("文件删除失败"); // 支持国际化输出
		}
	}

	/**
	 * 通过查询条件删除
	 *
	 * @param fileupload
	 */
	@PostMapping("/deleteByParam")
	public void deleteByParam(@RequestBody Fileupload fileupload) {
		Assert.notNull(fileupload.getBusinessId(), "业务id不能为空");
		iFileuploadService.remove(new QueryWrapper<Fileupload>(fileupload));
	}

	/**
	 * 分页查询
	 *
	 * @param fileupload
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<Fileupload> listPage(Fileupload fileupload, String convertBase64) {
		PageUtil.startPage(fileupload.getPageNum(), fileupload.getPageSize());
		QueryWrapper<Fileupload> wrapper = new QueryWrapper<Fileupload>(fileupload);
		PageInfo<Fileupload> fileuploadPageInfo = new PageInfo<Fileupload>(iFileuploadService.list(wrapper));
		if( fileuploadPageInfo==null || fileuploadPageInfo.getList()==null || fileuploadPageInfo.getList().size()==0 ) {
			log.error("no file "+JsonUtil.entityToJsonStr(fileuploadPageInfo));
		}
		if (Enable.Y.name().equals(convertBase64)) {
			convertBase64(fileuploadPageInfo.getList());
		}
		return fileuploadPageInfo;
	}
	
	/**
	 * 将文件转换为base64编码
	 *
	 * @param fileuploads
	 */
	private void convertBase64(List<Fileupload> fileuploads) {
		fileuploads.forEach(fileupload -> {
			try {
				FileService fileService = null;
				fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(fileupload.getUploadType()));
				// 获取文件流
				InputStream is = fileService.download(fileupload);
				// 将文件中的内容读入到数组中
				byte[] bytes = new byte[is.available()];
				is.read(bytes);
				String strBase64 = new BASE64Encoder().encode(bytes); // 将字节流数组转换为字符串
				fileupload.setBase64(strBase64);
			} catch (Exception ex) {
				log.error("文件转换base64失败", ex);
			}
		});
	}

	/**
	 * 多个文件ID与业务ID关联
	 *
	 * @param fileuploadIds
	 * @param businessId
	 * @throws Exception
	 */
	@PostMapping("/binding")
	public Boolean binding(@RequestParam List<Long> fileuploadIds, @RequestParam Long businessId) {
		List<Fileupload> updateFiles = new ArrayList<Fileupload>();
		fileuploadIds.forEach(fileuploadId -> {
			Fileupload fileupload = iFileuploadService.getById(fileuploadId);
			if (fileupload != null) {
				fileupload.setBusinessId(businessId);
				updateFiles.add(fileupload);
			}
		});
		if (CollectionUtils.isNotEmpty(updateFiles)) {
			iFileuploadService.updateBatchById(updateFiles);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 多个上传文件与业务ID关联
	 * 
	 * @param fileuploads
	 * @param businessId
	 * @return
	 */
	@PostMapping("/bindingFileupload")
	public Boolean bindingFileupload(@RequestBody List<Fileupload> fileuploads,
			@RequestParam("businessId") Long businessId) {
		List<Fileupload> updateFiles = new ArrayList<Fileupload>();
		fileuploads.forEach(fileupload -> {
			Fileupload fileuploadServiceById = iFileuploadService.getById(fileupload.getFileuploadId());
			if (fileuploadServiceById != null) {
				fileupload.setBusinessId(businessId);
				updateFiles.add(fileupload);
			}
		});
		if (CollectionUtils.isNotEmpty(updateFiles)) {
			iFileuploadService.updateBatchById(updateFiles);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 文件下载
	 *
	 * @param request
	 * @param response
	 * @param fileupload
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response, Fileupload fileupload)
			throws Exception {
		QueryWrapper<Fileupload> queryWrapper = new QueryWrapper<Fileupload>(fileupload);
		PageUtil.startPage(1, 1);
		fileupload = iFileuploadService.getOne(queryWrapper);
		if (fileupload != null) {
			FileService fileService = null;
			try {
				fileService = fileServiceFactory.getFileService(FileUploadType.valueOf(fileupload.getUploadType()));
			} catch (Exception e) {
				throw new BaseException("文件上传方式有误");
			}
			if (fileupload != null) {
				InputStream is = fileService.download(fileupload);
				wirte(request, response, is, fileupload.getFileSourceName());
			}
		}
	}

	/**
	 * 输出文件流到前端
	 *
	 * @param request
	 * @param response
	 * @param is
	 * @param fileName
	 * @throws IOException
	 */
	public void wirte(HttpServletRequest request, HttpServletResponse response, InputStream is, String fileName)
			throws IOException {
		fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20").replaceAll("%28", "\\(")
				.replaceAll("%29", "\\)").replaceAll("%25", "\\%");// 替换空格 不然会变为加号
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String mimeType = request.getServletContext().getMimeType(fileName);
			response.setHeader("Content-type", mimeType);
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 解决axios下载后取不到文件名的问题
			response.setHeader("FileName", fileName); // 解决axios下载后取不到文件名的问题
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception ex) {
			log.error("文件下载失败", ex);
		} finally {
			if (bis != null) {
				try {
					is.close();
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (bos != null)
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	
	/**
	 * 分页查询
	 *
	 * @param fileupload
	 * @return
	 */
	@PostMapping("/listExportPage")
	public PageInfo<Fileupload> listExportPage(@RequestBody Fileupload fileupload, String convertBase64) {
		PageUtil.startPage(fileupload.getPageNum(), fileupload.getPageSize());
		String functionName = fileupload.getFileFunction();
		String fileName = fileupload.getFileSourceName();
		Date creationBeginBegin = null;
		Date creationBeginEnd = null;
		if (!StringUtil.isEmpty(fileupload.getFileFunction())) {
			functionName = fileupload.getFileFunction();
			fileupload.setFileFunction(null);
		}
		if (!StringUtil.isEmpty(fileupload.getFileSourceName())) {
			fileName = fileupload.getFileSourceName();
			fileupload.setFileSourceName(null);
		}
		
		if (null != fileupload.getCreationDateBegin()) {
			creationBeginBegin = fileupload.getCreationDateBegin();
			fileupload.setCreationDateBegin(null);
		}
		
		if (null != fileupload.getCreationDateEnd()) {
			creationBeginEnd = fileupload.getCreationDateEnd();
			fileupload.setCreationDateEnd(null);
		}
		
		QueryWrapper<Fileupload> wrapper = new QueryWrapper<Fileupload>(fileupload);
		
		if (!StringUtil.isEmpty(functionName)) {
			wrapper.like("FILE_FUNCTION", functionName);
		}
		
		if (!StringUtil.isEmpty(fileName)) {
			wrapper.like("FILE_SOURCE_NAME", fileName);
		}
		
		if (null != creationBeginBegin) {
			wrapper.ge("CREATION_DATE", creationBeginBegin);
		}
		
		if (null != creationBeginEnd) {
			wrapper.le("CREATION_DATE", creationBeginEnd);
		}
		
		wrapper.eq("FILE_TYPE", "EXPORT_FILE");
		LoginAppUser user = AppUserUtil.getLoginAppUser();
		wrapper.eq("CREATED_ID",user.getUserId());
		wrapper.orderByDesc("CREATION_DATE");
		PageInfo<Fileupload> fileuploadPageInfo = new PageInfo<Fileupload>(iFileuploadService.list(wrapper));
		if (Enable.Y.name().equals(convertBase64)) {
			convertBase64(fileuploadPageInfo.getList());
		}
		return fileuploadPageInfo;
	}

	/**
	 * 储存供应商范围ID
	 */
	@PostMapping("/saveVendorScopeById")
	public void saveVendorScopeById(@RequestBody Fileupload file){
		iFileuploadService.updateById(file);
	}

	/**
	 * 根据bussinessId查找对应的文件
	 */
	@PostMapping("/getByBusinessId")
	public Fileupload getByBusinessId(@RequestParam("vendorScopeId")Long vendorScopeId){
		Assert.notNull(vendorScopeId, "业务id不能为空");
		QueryWrapper<Fileupload> wrapper = new QueryWrapper<>();
		wrapper.eq("BUSINESS_ID",vendorScopeId);
		return iFileuploadService.getOne(wrapper);
	}

}

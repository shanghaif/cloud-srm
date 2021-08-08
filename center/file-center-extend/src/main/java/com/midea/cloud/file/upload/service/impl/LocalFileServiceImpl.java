package com.midea.cloud.file.upload.service.impl;

import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.file.utils.FileUtil;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.time.LocalDate;

/**
 * 本地存储文件<br>
 * 该实现文件服务只能部署一台<br>
 * 如多台机器间能共享到一个目录，即可部署多台
 * 
 * @author artifact
 *
 */
@Service("localFileServiceImpl")
public class LocalFileServiceImpl extends AbstractFileService {

	/**
	 * 上传文件存储在本地的根路径
	 */
	@Value("${file.local.path}")
	private String localFilePath;

	@Override
	protected FileUploadType fileUploadType() {
		return FileUploadType.LOCAL;
	}

	@Override
	protected void uploadFile(MultipartFile file, Fileupload fileupload) throws Exception {
		String suffix = "/" + LocalDate.now().toString().replace("-", "/") + "/";
		String path = localFilePath + suffix;
		String fileStorageeName = FileUtil.getPureName(fileupload);
		fileupload.setFilePath(path);
		fileupload.setFilePureName(fileStorageeName);
		fileupload.setFileFullname(path + fileupload.getFileSourceName()); // 文件全路径名
		FileUtil.saveFile(file, path + fileStorageeName);
	}

	@Override
	protected boolean deleteFile(Fileupload fileupload) {
		return FileUtil.deleteFile(fileupload.getFilePath() + fileupload.getFilePureName());
	}

	@Override
	public FileInputStream download(Fileupload fileupload) throws Exception {
		return new FileInputStream(fileupload.getFilePath() + fileupload.getFilePureName());
	}

}

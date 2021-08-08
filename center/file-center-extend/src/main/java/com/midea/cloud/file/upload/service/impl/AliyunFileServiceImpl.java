package com.midea.cloud.file.upload.service.impl;

import com.aliyun.oss.OSSClient;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <pre>
 *  阿里云存储文件
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 15:26
 *  修改内容:
 * </pre>
 */
@Service("aliyunFileServiceImpl")
public class AliyunFileServiceImpl extends AbstractFileService {

	@Override
	protected FileUploadType fileUploadType() {
		return FileUploadType.ALIYUN;
	}

	@Autowired
	private OSSClient ossClient;

	@Value("${file.aliyun.bucketName}")
	private String bucketName;
	@Value("${file.aliyun.domain}")
	private String domain;

	@Override
	protected void uploadFile(MultipartFile file, Fileupload fileupload) throws Exception {
		ossClient.putObject(bucketName, fileupload.getFileSourceName(), file.getInputStream());
		fileupload.setFilePath(domain + "/" + fileupload.getFileSourceName());
	}

	@Override
	protected boolean deleteFile(Fileupload fileupload) {
		ossClient.deleteObject(bucketName, fileupload.getFileSourceName());
		return true;
	}

	@Override
	public InputStream download(Fileupload fileupload) throws Exception {
		return null;
	}
}

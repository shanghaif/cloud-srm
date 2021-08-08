package com.midea.cloud.file.config;

import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.file.upload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * FileService工厂
 * 将各个实现类放入map
 * 
 * @author artifact
 *
 */
@Configuration
public class FileServiceFactory {

	private Map<FileUploadType, FileService> serviceMap = new HashMap<>();

	@Autowired
	private FileService localFileServiceImpl;
	@Autowired
	private FileService aliyunFileServiceImpl;
	@Autowired
	private FileService fastdfsFileServiceImpl;
	@Autowired
	private FileService mideaOssFileServiceImpl;
	@PostConstruct
	public void init() {
		serviceMap.put(FileUploadType.LOCAL, localFileServiceImpl);
		serviceMap.put(FileUploadType.ALIYUN, aliyunFileServiceImpl);
		serviceMap.put(FileUploadType.FASTDFS, fastdfsFileServiceImpl);
		serviceMap.put(FileUploadType.MIDEAOSS, mideaOssFileServiceImpl);
	}

	public FileService getFileService(FileUploadType fileUploadType) {
		if (fileUploadType == null) {
			return localFileServiceImpl;
		}
		return serviceMap.get(fileUploadType);
	}

}

package com.midea.cloud.file.upload.service;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {

	Fileupload upload(MultipartFile file, Fileupload fileupload) throws Exception;

	InputStream download(Fileupload fileupload) throws Exception;

	void delete(Fileupload fileupload);
	
	public Fileupload saveFile(Fileupload fileupload) throws Exception;
	
	public Fileupload updateFile(MultipartFile file, Fileupload orginFileupload) throws Exception;

}

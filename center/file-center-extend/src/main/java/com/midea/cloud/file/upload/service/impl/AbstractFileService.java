package com.midea.cloud.file.upload.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.file.upload.mapper.FileuploadMapper;
import com.midea.cloud.file.upload.service.FileService;
import com.midea.cloud.file.utils.FileUtil;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public abstract class AbstractFileService implements FileService {

	@Autowired
	private FileuploadMapper fileuploadMapper;

	/**
	 * 封装文件上传
	 *
	 * @param file
	 * @param orginFileupload
	 * @return
	 * @throws Exception
	 */
	@Override
	public Fileupload upload(MultipartFile file, Fileupload orginFileupload) throws Exception {
		Fileupload fileupload = FileUtil.getFileInfo(file, orginFileupload);

		uploadFile(file, fileupload);

		fileupload.setUploadType(fileUploadType().name());// 设置文件来源
		Long id = IdGenrator.generate();
		fileupload.setFileuploadId(id);
		System.out.println(JSONObject.toJSONString(fileupload));
		fileuploadMapper.insert(fileupload); // 将文件信息保存到数据库
		log.info("上传文件：{}", fileupload);
		return fileupload;
	}

	/**
	 * 获取文件上传介质类型
	 * 
	 * @return
	 */
	protected abstract FileUploadType fileUploadType();

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param fileupload
	 */
	protected abstract void uploadFile(MultipartFile file, Fileupload fileupload) throws Exception;

	@Override
	public void delete(Fileupload fileupload) {
		deleteFile(fileupload); // 删除物理文件
		fileuploadMapper.deleteById(fileupload.getFileuploadId()); // 删除数据库记录
		log.info("删除文件：{}", fileupload);
	}

	/**
	 * 删除文件资源
	 *
	 * @param fileupload
	 * @return
	 */
	protected abstract boolean deleteFile(Fileupload fileupload);
	
	/**
	 * 保存
	 *
	 * @param file
	 * @param orginFileupload
	 * @return
	 * @throws Exception
	 */
	@Override
	public Fileupload saveFile(Fileupload fileupload) throws Exception {
		fileupload.setUploadType(fileUploadType().name());// 设置文件来源
		Long id = IdGenrator.generate();
		fileupload.setFileuploadId(id);
		System.out.println(JSONObject.toJSONString(fileupload));
		fileuploadMapper.insert(fileupload); // 将文件信息保存到数据库
		log.info("上传文件：{}", fileupload);
		return fileupload;
	}
	
	/**
	 * 封装文件上传
	 *
	 * @param file
	 * @param orginFileupload
	 * @return
	 * @throws Exception
	 */
	@Override
	public Fileupload updateFile(MultipartFile file, Fileupload orginFileupload) throws Exception {
		Fileupload fileupload = FileUtil.getFileInfo(file, orginFileupload);

		uploadFile(file, fileupload);

		fileupload.setUploadType(fileUploadType().name());// 设置文件来源
		fileuploadMapper.updateById(fileupload);
		log.info("上传文件：{}", fileupload);
		return fileupload;
	}
}

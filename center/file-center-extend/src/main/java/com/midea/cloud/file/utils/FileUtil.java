package com.midea.cloud.file.utils;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;

/**
 *  <pre>
 *  文件上传工具类 服务实现类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-04 15:41:58
 *  修改内容:
 * </pre>
 */
public class FileUtil {

	public static Fileupload getFileInfo(MultipartFile file, Fileupload orginFileupload) throws Exception {
		String md5 = fileMd5(file.getInputStream());
		Fileupload fileupload = new Fileupload();
		BeanUtils.copyProperties(orginFileupload, fileupload);
		fileupload.setFingerprint(md5); // 将文件的md5设置为文件的指纹
		fileupload.setFileExtendType(getExtendType(file.getOriginalFilename())); // 文件扩展名
		fileupload.setFileSourceName(file.getOriginalFilename()); // 原始文件名
		fileupload.setFileSize(new BigDecimal(file.getSize())); // 文件大小
//		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		return fileupload;
	}

	/**
	 * 获取文件扩展类型
	 * @param fileName
	 * @return
	 */
	public static String getExtendType(String fileName) {
		if (fileName == null || !fileName.contains(".")) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 通过指纹获取文件名字
	 * @param fileupload
	 * @return
	 */
	public static String getPureName(Fileupload fileupload) {
		if (fileupload == null) {
			return null;
		}
		if (fileupload.getFileExtendType() != null) {
			return System.currentTimeMillis() + "-" + fileupload.getFingerprint() + "." + fileupload.getFileExtendType();
		} else {
			return System.currentTimeMillis() + "-" + fileupload.getFingerprint();
		}
	}

	/**
	 * 文件的md5
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String fileMd5(InputStream inputStream) {
		try {
			return DigestUtils.md5Hex(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存文件
	 * @param file
	 * @param path
	 * @return
	 */
	public static String saveFile(MultipartFile file, String path) {
		try {
			File targetFile = new File(path);
			if (targetFile.exists()) {
				return path;
			}
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			Files.copy(file.getInputStream(), targetFile.toPath());
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除文件
	 * @param pathname
	 * @return
	 */
	public static boolean deleteFile(String pathname) {
		File file = new File(pathname);
		if (file.exists()) {
			boolean flag = file.delete();
			if (flag) {
				File[] files = file.getParentFile().listFiles();
				if (files == null || files.length == 0) {
					file.getParentFile().delete();
				}
			}
			return flag;
		}
		return false;
	}
}

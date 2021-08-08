package com.midea.cloud.file.upload.service.impl;

import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.file.config.FastdfsAutoConfiguration;
import com.midea.cloud.file.utils.FileUtil;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <pre>
 *  Fastdfs存储文件
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
@Service("fastdfsFileServiceImpl")
@Slf4j
public class FastdfsFileServiceImpl extends AbstractFileService {

    @Autowired
    private FastdfsAutoConfiguration fastdfsAutoconfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected FileUploadType fileUploadType() {
        return FileUploadType.FASTDFS;
    }

    @Override
    protected void uploadFile(MultipartFile file, Fileupload fileupload) throws Exception {
        String[] ss = fastdfsAutoconfiguration.storageClient().upload_appender_file(file.getBytes(), fileupload.getFileExtendType(), new NameValuePair[]{});
        Assert.isTrue(ss.length == 2, "文件上传失败");
        String fileStorageeName = FileUtil.getPureName(fileupload);
        fileupload.setFilePath(ss[0] + "|" + ss[1]);
        fileupload.setFilePureName(fileStorageeName);
        fileupload.setFileFullname(ss[0] + "/" + ss[1]); // 文件全路径名
    }

    @Override
    protected boolean deleteFile(Fileupload fileupload) {
        try {
            String[] groupAndName = fileupload.getFilePath().split("\\|");
            fastdfsAutoconfiguration.storageClient().delete_file(groupAndName[0], groupAndName[1]);
        } catch (Exception ex) {
            log.error("文件删除失败", ex);
            throw new BaseException("文件删除失败");
        }
        return true;
    }

    @Override
    public InputStream download(Fileupload fileupload) throws Exception {
        InputStream inputStream = restTemplate.execute(fastdfsAutoconfiguration.getFastdfsProperties().getHttpDownloadUrl() + fileupload.getFileFullname(), HttpMethod.GET, null, clientHttpResponse -> {
            return new ByteArrayInputStream(StreamUtils.copyToByteArray(clientHttpResponse.getBody()));
        });
        return inputStream;
//        String[] groupAndName = fileupload.getFilePath().split("\\|");
//        return new ByteArrayInputStream(fastdfsAutoconfiguration.storageClient().download_file(groupAndName[0], groupAndName[1]));
    }

}

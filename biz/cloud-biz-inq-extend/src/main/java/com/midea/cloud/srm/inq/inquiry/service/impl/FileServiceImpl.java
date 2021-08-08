package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.InquiryFileType;
import com.midea.cloud.srm.inq.inquiry.mapper.FileMapper;
import com.midea.cloud.srm.inq.inquiry.service.IFileService;
import com.midea.cloud.srm.model.inq.inquiry.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  询价-附件表 服务实现类
 * </pre>
 *
 * @author zhongbh
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:54
 *  修改内容:
 * </pre>
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {
    @Autowired
    IFileService iFileService;

    @Override
    public List<File> getByHeadId(Long inquiryId, String type) {
        List<File> files = new ArrayList<>();
        if (StringUtils.hasText(type)) {
            QueryWrapper warapper = new QueryWrapper();
            warapper.eq("INQUIRY_ID", inquiryId);
            if (InquiryFileType.INNER.name().equals(type)) {
                warapper.eq("TYPE", type);
                files = iFileService.list(warapper);
            } else if (InquiryFileType.OUTER.name().equals(type)) {
                warapper.eq("TYPE", type);
                files = iFileService.list(warapper);
            }
        }
        return files;
    }
}
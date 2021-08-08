package com.midea.cloud.srm.base.noticetest.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.noticetest.mapper.NoticeTestMapper;
import com.midea.cloud.srm.base.noticetest.service.NoticeTestService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.noticetest.dto.NoticeTestDto;
import com.midea.cloud.srm.model.base.noticetest.entity.NoticeTest;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
*  <pre>
 *  功能代码生成测试 服务实现类
 * </pre>
*
* @author linsb@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-1-17 20:36:12
 *  修改内容:
 * </pre>
*/
@Service
public class NoticeTestserviceImpl extends ServiceImpl<NoticeTestMapper, NoticeTest> implements NoticeTestService {
    @Resource
    private FileCenterClient fileCenterClient;

    @Override
    @Transactional
    public void batchAdd(List<NoticeTest> noticeTestList) {
        //设置主键id
        for(NoticeTest noticeTest :noticeTestList){
            Long id = IdGenrator.generate();
            noticeTest.setNoticeId(id);
        }
        this.saveBatch(noticeTestList);
    }

    @Override
    @Transactional
    public void batchUpdate(List<NoticeTest> noticeTestList) {
        this.saveOrUpdateBatch(noticeTestList);
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(NoticeTestDto.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new NoticeTestDto()));
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<NoticeTestDto> noticeTestDtos = EasyExcelUtil.readExcelWithModel(file, NoticeTestDto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<NoticeTest> noticeTests = chackImportParam(noticeTestDtos, errorFlag);

        if(errorFlag.get()){
            // 保存
            batchAdd(noticeTests);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, noticeTests, NoticeTest.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 检查导入参数
     * @param noticeTestDtos
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<NoticeTest> chackImportParam(List<NoticeTestDto> noticeTestDtos,AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<NoticeTest> noticeTests = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(noticeTestDtos)){
            noticeTestDtos.forEach(noticeTestDto -> {
                NoticeTest noticeTest = new NoticeTest();
                StringBuffer errorMsg = new StringBuffer();

                // 检查示例: noticeId 非空
//                Long noticeId = noticeTestDto.getNoticeId();
//                if(ObjectUtils.isEmpty(noticeId)){
//                    errorMsg.append("noticeId不能为空;");
//                    errorFlag.set(false);
//                }else {
//                    noticeTest.setNoticeId(noticeId);
//                }
//
//                // .......
//                if(errorMsg.length() > 0){
//                    noticeTestDto.setErrorMsg(errorMsg.toString());
//                }else {
//                    noticeTests.add(noticeTest);
//                }
                noticeTests.add(noticeTest);
            });
        }
        return noticeTests;
    }

    @Override
    public void exportExcel(NoticeTest excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<NoticeTest> noticeTests = getNoticeTests(excelParam);
        List<NoticeTestDto> noticeTestDtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(noticeTests)){
            noticeTests.forEach(noticeTest -> {
                NoticeTestDto noticeTestDto = new NoticeTestDto();
                BeanCopyUtil.copyProperties(noticeTestDto,noticeTests);
                noticeTestDtos.add(noticeTestDto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(NoticeTestDto.class).sheet(0).sheetName("sheetName").doWrite(noticeTestDtos);

    }

    @Override
    public PageInfo<NoticeTest> listPage(NoticeTest noticeTest) {
        PageUtil.startPage(noticeTest.getPageNum(), noticeTest.getPageSize());
        List<NoticeTest> noticeTests = getNoticeTests(noticeTest);
        return new PageInfo<>(noticeTests);
    }

    public List<NoticeTest> getNoticeTests(NoticeTest noticeTest) {
        QueryWrapper<NoticeTest> wrapper = new QueryWrapper<>();
        wrapper.eq("NOTICE_ID",noticeTest.getNoticeId()); // 精准匹配
        wrapper.like("TITLE",noticeTest.getTitle());      // 模糊匹配
        wrapper.and(queryWrapper ->
                queryWrapper.ge("CREATION_DATE",noticeTest.getStartDate()).
                        le("CREATION_DATE",noticeTest.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}

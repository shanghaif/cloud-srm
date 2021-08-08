package com.midea.cloud.srm.base.questionairesurvey.service.impl;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.midea.cloud.common.enums.base.FileFlagEnum;
import com.midea.cloud.common.enums.base.QuestionTypeEnum;
import com.midea.cloud.common.enums.base.ResultFlagEnum;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveyResultMapper;
import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveyScopeEmployeeMapper;
import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveyScopeVendorMapper;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyQuestionService;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyResultService;

import com.midea.cloud.srm.base.questionairesurvey.service.SurveySelectionService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.noticetest.dto.NoticeTestDto;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.ExcelFeedBackInfoDto;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.ExcelSurveyResultDto;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyQuestionDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyResultDto;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.*;


import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.dto.UserPermissionDTO;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
* <pre>
 *  问卷调查 服务实现类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 17, 2021 5:30:44 PM
 *  修改内容:
 * </pre>
*/
@Service
public class SurveyResultServiceImpl extends ServiceImpl<SurveyResultMapper, SurveyResult> implements SurveyResultService {

    @Autowired
    SurveyQuestionService surveyQuestionService;

    @Autowired
    SurveySelectionService surveySelectionService;

    @Autowired
    SurveyScopeVendorMapper surveyScopeVendorMapper;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private SurveyScopeEmployeeMapper surveyScopeEmployeeMapper;

    @Autowired
    private RbacClient rbacClient;


    @Override
    public List<SurveyQuestionDTO> queryFeedbackChartResult(Long id) {
        //获取问题
        List<SurveyQuestion> surveyQuestionList = surveyQuestionService.list(new QueryWrapper<>(new SurveyQuestion().setSurveyId(id)));
        //筛选问题Id
        List<Long> question = surveyQuestionList.stream().map(SurveyQuestion::getQuestionId).collect(Collectors.toList());
        //获取选项
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.in(question.size() != 0,"QUESTION_ID", question);
        List<SurveySelection> surveySelectionList = surveySelectionService.list(wrapper);
        //问题对应的选项list
        Map<Long,List<SurveySelection>> selectionMap = surveySelectionList
                .stream().collect(Collectors.groupingBy(SurveySelection::getQuestionId));
        //获取员工列表
//        List<SurveyResult> surveyResultList = xxx.list(new QueryWrapper<>(new SurveyResult().set));
        //获取结果
        List<SurveyResult> surveyResultList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(question)) {
            surveyResultList = this.list(new QueryWrapper<SurveyResult>().in("QUESTION_ID",question));
        }
        //每个选项ID对应的list
        Map<Long,List<SurveyResult>> surveyResultGroup = surveyResultList.stream().collect(Collectors.groupingBy(SurveyResult::getSelectionId));
        Map<Long,List<SurveyResult>> questionList = surveyResultList.stream().collect(Collectors.groupingBy(SurveyResult::getQuestionId));
        List<SurveyQuestionDTO> surveyQuestionDTOList = new ArrayList<>();
        for (int i = 0; i < surveyQuestionList.size(); ++i) {
            SurveyQuestionDTO surveyQuestionDTO = new SurveyQuestionDTO();
            //设置返回问题数据
            int totalCount = 0;
            if(CollectionUtils.isNotEmpty(questionList.get(surveyQuestionList.get(i).getQuestionId()))) {
                //每个问题对应的resultList
                List<SurveyResult> resultList = questionList.get(surveyQuestionList.get(i).getQuestionId());
                //每个供应商对应的resultList
                Map<Long, List<SurveyResult>> questionCountList = resultList.stream().collect(Collectors.groupingBy(SurveyResult::getVendorScopeId));
                totalCount = questionCountList.size();
            }
            surveyQuestionDTO.setSurveyQuestion(surveyQuestionList.get(i).setTotalCount(totalCount));
            //设置返回选项被填写次数
            List<SurveySelection> dataSelectionList = selectionMap.get(surveyQuestionList.get(i).getQuestionId());//返回该问题对应的选项ID
            //如果为问答题，则没有选项反馈次数
            if(!QuestionTypeEnum.Q.getValue().equals(surveyQuestionList.get(i).getQuestionType())){
                dataSelectionList.forEach( selection -> {
                    if(surveyResultGroup.get(selection.getSelectionId()) !=null ) {
                        selection.setFeedBackCount(surveyResultGroup.get(selection.getSelectionId()).size());
                    }
                });
            }
            //设置返回选项
            surveyQuestionDTO.setSurveySelectionList(dataSelectionList);
            surveyQuestionDTOList.add(surveyQuestionDTO);
        }
        return surveyQuestionDTOList;
    }

    @Override
    public void saveQuestionnaireInfo(List<SurveyResultDto> surveyResultDtoList) {
        //修改供应商范围表反馈标识
        Long vendorScopeId = surveyResultDtoList.get(0).getVendorScopeId();
        SurveyScopeVendor surveyScopeVendor = surveyScopeVendorMapper.selectById(vendorScopeId);
        surveyScopeVendor.setResultFlag(ResultFlagEnum.Y.getValue());
        surveyScopeVendorMapper.updateById(surveyScopeVendor);

        //储存供应商范围ID到文件表中
        if(StringUtil.notEmpty(surveyResultDtoList.get(0).getFileUploadId())){
            Fileupload fileupload = new Fileupload();
            fileupload.setFileuploadId(surveyResultDtoList.get(0).getFileUploadId());
            fileupload.setBusinessId(vendorScopeId);
            fileCenterClient.saveVendorScopeById(fileupload);
        }

        for(SurveyResultDto surveyResultDto:surveyResultDtoList){
            SurveyResult surveyResult = new SurveyResult();
            //获取创建人ID、创建人 等
            BeanCopyUtil.copyProperties(surveyResult,surveyScopeVendor);
            BeanCopyUtil.copyProperties(surveyResult,surveyResultDto);
            this.save(surveyResult);

        }
    }

    @Override
    public void resultExport(Long surveyId, HttpServletResponse response) throws IOException {
        List<SurveyQuestionDTO> surveyQuestionDTOList = queryFeedbackChartResult(surveyId);
        //反馈明细list
        List<ExcelFeedBackInfoDto> excelFeedBackInfoDtos = new ArrayList<>();
        //汇总结果list
        List<ExcelSurveyResultDto> excelSurveyResultDtos = new ArrayList<>();
        //设置问题序号
        int num=1;
        for(int i =0;i<surveyQuestionDTOList.size();i++){
            ExcelSurveyResultDto excelSurveyResultDto = new ExcelSurveyResultDto();
            ExcelFeedBackInfoDto excelFeedBackInfoDto = new ExcelFeedBackInfoDto();
            excelSurveyResultDto.setNum(i+1);
            excelFeedBackInfoDto.setTitleNum(i+1);

            SurveyQuestionDTO surveyQuestionDTO = surveyQuestionDTOList.get(i);
            SurveyQuestion surveyQuestion = surveyQuestionDTO.getSurveyQuestion();

            //修改题目类型和是否员工调查
            Map<String,String> map = new HashMap<>();
            map.put(QuestionTypeEnum.S.getValue(),QuestionTypeEnum.S.getFlag());
            map.put(QuestionTypeEnum.M.getValue(),QuestionTypeEnum.M.getFlag());
            map.put(QuestionTypeEnum.Q.getValue(),QuestionTypeEnum.Q.getFlag());
            map.put(FileFlagEnum.Y.getValue(),FileFlagEnum.Y.getFlag());
            map.put(FileFlagEnum.N.getValue(),FileFlagEnum.N.getFlag());
            surveyQuestion.setQuestionType(map.get(surveyQuestion.getQuestionType()));
            surveyQuestion.setEmployeeFlag(map.get(surveyQuestion.getEmployeeFlag()));

            BeanCopyUtil.copyProperties(excelSurveyResultDto,surveyQuestion);
            BeanCopyUtil.copyProperties(excelFeedBackInfoDto,surveyQuestion);

            //设置各选项的选择次数
            List<SurveySelection> surveySelectionList = surveyQuestionDTO.getSurveySelectionList();
            setSelectionCount(surveySelectionList,excelSurveyResultDto);

            //获取供应商信息
            //获取结果
            List<SurveyResult> surveyResultList = this.list(new QueryWrapper<SurveyResult>().eq("QUESTION_ID",surveyQuestion.getQuestionId()));
            //每个vendor对应的resultList
            Map<Long, List<SurveyResult>> vendorScopeMap = surveyResultList.stream().collect(Collectors.groupingBy(SurveyResult::getVendorScopeId));
            for(Map.Entry<Long, List<SurveyResult>> entry : vendorScopeMap.entrySet()){
                ExcelFeedBackInfoDto excelFeedBackInfoDto1 = new ExcelFeedBackInfoDto();
                BeanCopyUtil.copyProperties(excelFeedBackInfoDto1,excelFeedBackInfoDto);
                excelFeedBackInfoDto1.setNum(num++);
                Long vendorScopeId = entry.getKey();
                //供应商对应的问题结果集合
                List<SurveyResult> vendorResults = entry.getValue();
                //设置供应商的信息
                SurveyScopeVendor vendor = surveyScopeVendorMapper.selectById(vendorScopeId);
                excelFeedBackInfoDto1.setVendorCode(vendor.getVendorCode());
                excelFeedBackInfoDto1.setVendorName(vendor.getVendorName());
                excelFeedBackInfoDto1.setLastUpdateTime(vendor.getLastUpdateDate());

                //判断是否员工调查
                if(FileFlagEnum.Y.getFlag().equals(surveyQuestion.getEmployeeFlag())){
                    //每个employee对应的结果集合
                    Map<Long, List<SurveyResult>> employeeMap = vendorResults.stream().collect(Collectors.groupingBy(SurveyResult::getEmployeeScopeId));
                    for(Map.Entry<Long,List<SurveyResult>> m: employeeMap.entrySet()){
                        ExcelFeedBackInfoDto excelFeedBackInfoDto2 = new ExcelFeedBackInfoDto();
                        BeanCopyUtil.copyProperties(excelFeedBackInfoDto2,excelFeedBackInfoDto1);
                        //员工调查时的序号
                        excelFeedBackInfoDto2.setNum(num++);
                        Long employeeScopeId = m.getKey();
                        List<SurveyResult> employeeResults = m.getValue();
                        //查找员工信息
                        SurveyScopeEmployee employee = surveyScopeEmployeeMapper.selectById(employeeScopeId);
                        excelFeedBackInfoDto2.setEmployeeName(employee.getEmployeeName());
                        excelFeedBackInfoDto2.setEmployeeJob(employee.getEmployeeJob());
                        //获取员工账号和所在部门
                        User user = rbacClient.getUserByIdAnon(Long.valueOf(employee.getEmployeeCode()));
                        if(user!=null){
                            excelFeedBackInfoDto2.setDepartment(user.getDepartment());
                            excelFeedBackInfoDto2.setUsreName(user.getUsername());
                        }
                        //设置员工反馈内容
                        setResult(employeeResults,surveyQuestion,excelFeedBackInfoDto2);
                        excelFeedBackInfoDtos.add(excelFeedBackInfoDto2);
                    }
                }else{
                    //非员工调查，设置供应商反馈内容
                    setResult(vendorResults,surveyQuestion,excelFeedBackInfoDto1);
                    excelFeedBackInfoDtos.add(excelFeedBackInfoDto1);
                }
            }

            excelSurveyResultDtos.add(excelSurveyResultDto);
        }

        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"问卷反馈结果");
        //EasyExcel.write(outputStream).head(ExcelSurveyResultDto.class).sheet(0).sheetName("汇总结果").doWrite(excelSurveyResultDtos);
        //EasyExcel.write(outputStream).head(ExcelFeedBackInfoDto.class).sheet(1).sheetName("反馈明细").doWrite(excelFeedBackInfoDtos);
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet(0, "汇总结果").head(ExcelSurveyResultDto.class).build();
        WriteSheet writeSheet2 = EasyExcel.writerSheet(1, "反馈明细").head(ExcelFeedBackInfoDto.class).build();
        excelWriter.write(excelSurveyResultDtos, writeSheet1);
        excelWriter.write(excelFeedBackInfoDtos, writeSheet2);
        excelWriter.finish();
        response.flushBuffer();
    }

    private void setResult(List<SurveyResult> results, SurveyQuestion surveyQuestion,ExcelFeedBackInfoDto excelFeedBackInfoDto1) {
        //问题为问答题类型则
        if(QuestionTypeEnum.Q.getFlag().equals(surveyQuestion.getQuestionType())){
            //问答题类型的vendorResults大小为1
            excelFeedBackInfoDto1.setResult(results.get(0).getResultValue());
        }else{
            //设置供应商反馈内容
            StringBuffer stringBuffer = new StringBuffer();
            QueryWrapper<SurveySelection> wrapper = new QueryWrapper();
            List<Long> selections = results.stream().map(SurveyResult::getSelectionId).collect(Collectors.toList());
            wrapper.in(results.size() != 0,"SELECTION_ID", selections);
            List<SurveySelection> selectionList = surveySelectionService.list(wrapper);
            selectionList.forEach(selection->{
                stringBuffer.append(selection.getSelectionCode());
                stringBuffer.append("-");
                stringBuffer.append(selection.getSelectionValue());
                //用","进行分割
                stringBuffer.append(",");
            });
            //将最后的","去掉
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
            excelFeedBackInfoDto1.setResult(stringBuffer.toString());
        }
    }

    private void setSelectionCount(List<SurveySelection> surveySelectionList, ExcelSurveyResultDto excelSurveyResultDto) {
        surveySelectionList.forEach(selection->{
            String selectionCode = selection.getSelectionCode();
            int feedBackCount = selection.getFeedBackCount();
            switch (selectionCode){
                case "A":
                    excelSurveyResultDto.setCountA(feedBackCount);
                    break;
                case "B":
                    excelSurveyResultDto.setCountB(feedBackCount);
                    break;
                case "C":
                    excelSurveyResultDto.setCountC(feedBackCount);
                    break;
                case "D":
                    excelSurveyResultDto.setCountD(feedBackCount);
                    break;
                case "E":
                    excelSurveyResultDto.setCountE(feedBackCount);
                    break;
                case "F":
                    excelSurveyResultDto.setCountF(feedBackCount);
                    break;
                case "G":
                    excelSurveyResultDto.setCountG(feedBackCount);
                    break;
                case "H":
                    excelSurveyResultDto.setCountH(feedBackCount);
                    break;
                case "I":
                    excelSurveyResultDto.setCountI(feedBackCount);
                    break;
                default:
                    break;
            }
        });
    }
}

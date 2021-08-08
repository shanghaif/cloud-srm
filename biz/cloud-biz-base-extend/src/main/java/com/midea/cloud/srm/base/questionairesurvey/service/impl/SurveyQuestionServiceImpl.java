package com.midea.cloud.srm.base.questionairesurvey.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.base.FileFlagEnum;
import com.midea.cloud.common.enums.base.QuestionTypeEnum;
import com.midea.cloud.common.enums.base.ResultFlagEnum;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.base.questionairesurvey.mapper.*;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyQuestionService;

import com.midea.cloud.srm.model.base.businesstype.dto.BussinessTypeImportDto;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.material.dto.CategoryBusinessModelDto;
import com.midea.cloud.srm.model.base.noticetest.dto.NoticeTestDto;
import com.midea.cloud.srm.model.base.noticetest.entity.NoticeTest;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.*;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.midea.cloud.srm.feign.file.FileCenterClient;
import org.apache.commons.collections4.CollectionUtils;

import com.midea.cloud.common.enums.ImportStatus;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import com.alibaba.excel.EasyExcel;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.stream.Collectors;

/**
* <pre>
 *  问卷调查 服务实现类
 * </pre>
*
* @author yancj@1.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 14, 2021 5:07:37 PM
 *  修改内容:
 * </pre>
*/
@Service
public class SurveyQuestionServiceImpl extends ServiceImpl<SurveyQuestionMapper, SurveyQuestion> implements SurveyQuestionService {

    @Resource
    private FileCenterClient fileCenterClient;

    @Autowired
    private SurveyResultMapper surveyResultMapper;

    @Autowired
    private SurveySelectionMapper surveySelectionMapper;

    @Autowired
    private SurveyScopeEmployeeMapper surveyScopeEmployeeMapper;

    @Autowired
    private SurveyScopeJobMapper surveyScopeJobMapper;

    @Autowired
    private IDictItemService iDictItemService;

    @Transactional
    public void batchUpdate(List<SurveyQuestion> SurveyQuestionList) {
        this.saveOrUpdateBatch(SurveyQuestionList);
    }


    @Override
    @Transactional
    public void batchSaveOrUpdate(List<SurveyQuestion> SurveyQuestionList) throws IOException {
        for(SurveyQuestion SurveyQuestion : SurveyQuestionList){
            if(SurveyQuestion.getQuestionId() == null){
                Long id = IdGenrator.generate();
                SurveyQuestion.setQuestionId(id);
            }
        }
        if(!CollectionUtils.isEmpty(SurveyQuestionList)) {
            batchUpdate(SurveyQuestionList);
        }
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
        EasyExcel.write(outputStream).head(ExcelSurveyScopeVendorDto.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new ExcelSurveyScopeVendorDto()));
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExcelSurveyScopeVendorDto> SurveyQuestionDtos = EasyExcelUtil.readExcelWithModel(file, ExcelSurveyScopeVendorDto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<SurveyQuestion> SurveyQuestions = chackImportParam(SurveyQuestionDtos, errorFlag);

        if(errorFlag.get()){
            // 保存或者更新
            batchSaveOrUpdate(SurveyQuestions);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, SurveyQuestionDtos, ExcelSurveyScopeVendorDto.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 检查导入参数
     * @param excelSurveyScopeVendorDto
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<SurveyQuestion> chackImportParam(List<ExcelSurveyScopeVendorDto> excelSurveyScopeVendorDto, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<SurveyQuestion> SurveyQuestions = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excelSurveyScopeVendorDto)){
            excelSurveyScopeVendorDto.forEach(SurveyQuestionDto -> {
                SurveyQuestion SurveyQuestion = new SurveyQuestion();
                StringBuffer errorMsg = new StringBuffer();
                BeanCopyUtil.copyProperties(SurveyQuestion,SurveyQuestionDto);
                // 检查示例: noticeId 非空
//                Long noticeId = SurveyQuestionDto.getNoticeId();
//                if(ObjectUtils.isEmpty(noticeId)){
//                    errorMsg.append("noticeId不能为空;");
//                    errorFlag.set(false);
//                }else {
//                    SurveyQuestion.setNoticeId(noticeId);
//                }
//
//                // .......
//                if(errorMsg.length() > 0){
//                    SurveyQuestionDto.setErrorMsg(errorMsg.toString());
//                }else {
//                    SurveyQuestions.add(SurveyQuestion);
//                }
                SurveyQuestions.add(SurveyQuestion);
            });
        }
        return SurveyQuestions;
    }

    @Override
    public void exportExcel(SurveyQuestion excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<SurveyQuestion> SurveyQuestions = getSurveyQuestions(excelParam);
        List<ExcelSurveyScopeVendorDto> excelSurveyScopeVendorDto = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(SurveyQuestions)){
            SurveyQuestions.forEach(SurveyQuestion -> {
                ExcelSurveyScopeVendorDto SurveyQuestionDto = new ExcelSurveyScopeVendorDto();
                BeanCopyUtil.copyProperties(SurveyQuestionDto,SurveyQuestion);
                excelSurveyScopeVendorDto.add(SurveyQuestionDto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExcelSurveyScopeVendorDto.class).sheet(0).sheetName("sheetName").doWrite(excelSurveyScopeVendorDto);

    }

    @Override
    public List<Long> getSurveyQuestionIds(Long surveyHeaderId) {
        QueryWrapper wrapper = new QueryWrapper<SurveyQuestion>();
        wrapper.select("QUESTION_ID").eq(surveyHeaderId != null,"SURVEY_ID" ,surveyHeaderId);
        return this.list(wrapper);
    }

    @Override
    public PageInfo<SurveyQuestion> listPage(SurveyQuestion SurveyQuestion) {
        PageUtil.startPage(SurveyQuestion.getPageNum(), SurveyQuestion.getPageSize());
        List<SurveyQuestion> SurveyQuestions = getSurveyQuestions(SurveyQuestion);
        return new PageInfo<>(SurveyQuestions);
    }

    public List<SurveyQuestion> getSurveyQuestions(SurveyQuestion SurveyQuestion) {
        QueryWrapper<SurveyQuestion> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",SurveyQuestion.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",SurveyQuestion.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",SurveyQuestion.getStartDate()).
//                        le("CREATION_DATE",SurveyQuestion.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }

    @Override
    public void exportSurveyQuestionExcelTemplate(HttpServletResponse response)throws IOException {
        //获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(ExcelSurveyQuestionDto.class).sheet(0).sheetName("问卷题目").doWrite(Arrays.asList(new ExcelSurveyQuestionDto()));
    }

    @Override
    public Map<String, Object> importSurveyQuestionExcel(MultipartFile file, Fileupload fileupload) {
        //检查参数
        EasyExcelUtil.checkParam(file,fileupload);
        //读取数据
        List<ExcelSurveyQuestionDto> excelSurveyQuestionDtos = EasyExcelUtil.readExcelWithModel(file, ExcelSurveyQuestionDto.class);
        // 返回结果map
        HashMap<String, Object> result = new HashMap<>();
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        //检验数据
        List<SurveyQuestionDTO> surveyQuestionDTOs = chackParam(excelSurveyQuestionDtos, errorFlag);
        if(errorFlag.get()){
            result.put("data",surveyQuestionDTOs);
            result.put("status", YesOrNo.YES.getValue());
            result.put("message", "success");
            return result;
        }else{
            Fileupload errorFileUpload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    excelSurveyQuestionDtos, ExcelSurveyQuestionDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            result.put("status", YesOrNo.NO.getValue());
            result.put("message", "error");
            result.put("fileuploadId", errorFileUpload.getFileuploadId());
            result.put("fileName", errorFileUpload.getFileSourceName());
            return result;
        }

    }


    private List<SurveyQuestionDTO> chackParam(List<ExcelSurveyQuestionDto> excelSurveyQuestionDtos, AtomicBoolean errorFlag) {
        List<SurveyQuestionDTO> surveyQuestionDTOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(excelSurveyQuestionDtos)){
            excelSurveyQuestionDtos.forEach(excelSurveyQuestionDto->{
                StringBuffer errorMsg = new StringBuffer();
                SurveyQuestionDTO surveyQuestionDTO = new SurveyQuestionDTO();
                SurveyQuestion surveyQuestion = new SurveyQuestion();

                //题目名称
                String questionName = excelSurveyQuestionDto.getQuestionName();
                if(StringUtil.isEmpty(questionName)){
                    errorMsg.append("题目名称不能为空;");
                    errorFlag.set(false);
                }else{
                    surveyQuestion.setQuestionName(questionName.trim());
                }

                //题目类型
                String questionType = excelSurveyQuestionDto.getQuestionType();
                if(StringUtils.isNotEmpty(questionType)){
                    //检查输入的题目类型
                    Map<String,String> map = new HashMap<>();
                    map.put(QuestionTypeEnum.S.getFlag(),QuestionTypeEnum.S.getValue());
                    map.put(QuestionTypeEnum.M.getFlag(),QuestionTypeEnum.M.getValue());
                    map.put(QuestionTypeEnum.Q.getFlag(),QuestionTypeEnum.Q.getValue());
                    if(!map.containsKey(questionType)){
                        errorMsg.append("题目类型不存在;");
                        errorFlag.set(false);
                        return;
                    }else{
                        surveyQuestion.setQuestionType(map.get(questionType));
                    }
                }else{
                    errorMsg.append("题目类型不能为空;");
                    errorFlag.set(false);
                    return;
                }

                //员工调查
                String employeeFlag = excelSurveyQuestionDto.getEmployeeFlag();
                String jobs = excelSurveyQuestionDto.getJob();
                List<String> jobList = new ArrayList<>();
                if(StringUtil.notEmpty(employeeFlag)){
                    if(FileFlagEnum.Y.getFlag().equals(employeeFlag)){
                        surveyQuestion.setEmployeeFlag(FileFlagEnum.Y.getValue());
                        //校验员工调查范围
                        if(StringUtil.notEmpty(jobs)){
                            //用“;”来分割填入的员工范围
                            String[] employeeJob = jobs.split(";");
                            //获取字典编码对应的条目
                            DictItemDTO dictItemDTO = new DictItemDTO();
                            dictItemDTO.setDictCode("ROLE_CODE"); // 岗位字典编码
                            List<DictItemDTO> dictItemDTOS = iDictItemService.listAllByParam(dictItemDTO);
                            Map<String, String> map = new HashMap<>();
                            if(CollectionUtils.isNotEmpty(dictItemDTOS) && dictItemDTOS.size()>0) {
                                dictItemDTOS.forEach(ditItem -> {
                                    map.put(ditItem.getDictItemCode(), ditItem.getDictItemName());
                                });
                            }
                            for(String jobName:employeeJob){
                                //检查是否有对应的岗位
                                if(map!=null&&map.size()>0){
                                    if(map.containsValue(jobName)){
                                        jobList.add(jobName);
                                    }else{
                                        errorMsg.append("员工调研范围填写不正确;");
                                        errorFlag.set(false);
                                    }
                                }
                            }
                        }
                    }else{
                        surveyQuestion.setEmployeeFlag(FileFlagEnum.N.getValue());
                        if(StringUtil.notEmpty(jobs)){
                            errorMsg.append("员工调查范围应为空;");
                            errorFlag.set(false);
                        }
                    }
                }else{
                    if(StringUtil.notEmpty(jobs)){
                        errorMsg.append("员工调查范围应为空;");
                        errorFlag.set(false);
                    }
                }


                //检验选项
                Map<String,String> map = new HashMap<>();
                map.put("A",excelSurveyQuestionDto.getSelectionA());
                map.put("B",excelSurveyQuestionDto.getSelectionB());
                map.put("C",excelSurveyQuestionDto.getSelectionC());
                map.put("D",excelSurveyQuestionDto.getSelectionD());
                map.put("E",excelSurveyQuestionDto.getSelectionE());
                map.put("F",excelSurveyQuestionDto.getSelectionF());
                map.put("G",excelSurveyQuestionDto.getSelectionG());
                map.put("H",excelSurveyQuestionDto.getSelectionH());
                map.put("I",excelSurveyQuestionDto.getSelectionI());
                List<SurveySelection> surveySelectionList = new ArrayList<>();
                //当为问答题时,文件各选项应为空
                if(QuestionTypeEnum.Q.getValue().equals(surveyQuestion.getQuestionType())){
                    Collection<String> values = map.values();
                    Set<String> set = new HashSet<>(values);
                    if(set.size()>1){
                        errorMsg.append("问答题选项应设置为空;");
                        errorFlag.set(false);
                    }
                    //设置一个selectionCode="A"的选项添加到该问答题对应的selectionList
                    surveySelectionList.add(new SurveySelection().setSelectionCode("A"));
                }else{
                    boolean flag=true;//作为该选项前一个选项是否为空的标识
                    for(Map.Entry<String, String> m : map.entrySet()){
                        if(flag&&StringUtil.notEmpty(m.getValue())){
                            SurveySelection surveySelection = new SurveySelection();
                            surveySelection.setSelectionCode(m.getKey());
                            surveySelection.setSelectionValue(m.getValue());
                            surveySelectionList.add(surveySelection);
                        }else if(flag&&StringUtil.isEmpty(m.getValue())){
                            flag=false;
                        }else if(flag==false&&StringUtil.notEmpty(m.getValue())){
                            errorMsg.append("问答题选项要连续填写;");
                            errorFlag.set(false);
                        }
                    }
                }

                //校验最多可选项
                Long maxSelection = excelSurveyQuestionDto.getMaxSelection();
                if(QuestionTypeEnum.S.getValue().equals(surveyQuestion.getQuestionType())){
                    //为单选题则
                    if(StringUtil.notEmpty(maxSelection)&&maxSelection>1){
                        errorMsg.append("单选题最多可选不大于1;");
                        errorFlag.set(false);
                    }
                }else if(QuestionTypeEnum.M.getValue().equals(surveyQuestion.getQuestionType())){
                    //为多选题则
                    if(StringUtil.notEmpty(maxSelection)&&maxSelection>surveySelectionList.size()){
                        errorMsg.append("最多可选项不能大于选项可数;");
                        errorFlag.set(false);
                    }
                }else{
                    //为问答题
                    if(StringUtil.notEmpty(maxSelection)&&maxSelection>0){
                        errorMsg.append("问答题最多可选项不能大于0;");
                        errorFlag.set(false);
                    }
                }

                surveyQuestion.setMaxSelection(maxSelection);
                surveyQuestion.setSelectionCount((long) surveySelectionList.size());
                surveyQuestionDTO.setSurveyQuestion(surveyQuestion);
                surveyQuestionDTO.setSurveySelectionList(surveySelectionList);
                surveyQuestionDTO.setJobList(jobList);
                //保存错误信息
                if(errorMsg.length()>0){
                    excelSurveyQuestionDto.setErrorMsg(errorMsg.toString());
                }
                surveyQuestionDTOList.add(surveyQuestionDTO);
            });
        }
        return surveyQuestionDTOList;
    }


    /////////// supplier ///////////    /////////// supplier ///////////
    @Override
    public List<SurveyQuestionSupplierDTO> questionSurveyInfo(Long id,Long vendorScopeId) {

        List<SurveyQuestionSupplierDTO> surveyQuestionSupplierDTOList = new ArrayList<>();

        //查询问题相关信息
        QueryWrapper<SurveyQuestion> questionWrapper = new QueryWrapper();
        questionWrapper.eq("SURVEY_ID",id);
        List<SurveyQuestion> surveyQuestionList = this.list(questionWrapper);
        for(SurveyQuestion question:surveyQuestionList){
            //设置问题
            SurveyQuestionSupplierDTO surveyQuestionSupplierDTO = new SurveyQuestionSupplierDTO();
            surveyQuestionSupplierDTO.setSurveyQuestion(question);
            //是否员工调查
            if(ResultFlagEnum.Y.getValue().equals(question.getEmployeeFlag())){
                getJobEmployeeDtoList(question.getQuestionId(),surveyQuestionSupplierDTO,vendorScopeId);
            }else{
                //问题对应的结果
                List<SurveyResultDto> surveyResultDtoList = new ArrayList<>();
                QueryWrapper<SurveyResult> resultQueryWrapper = new QueryWrapper<>();
                resultQueryWrapper.eq("QUESTION_ID",question.getQuestionId());
                resultQueryWrapper.eq("VENDOR_SCOPE_ID",vendorScopeId);
                List<SurveyResult> surveyResults = surveyResultMapper.selectList(resultQueryWrapper);
                for(SurveyResult surveyResult :surveyResults){
                    SurveyResultDto surveyResultDto = new SurveyResultDto();
                    BeanCopyUtil.copyProperties(surveyResultDto,surveyResult);
                    surveyResultDtoList.add(surveyResultDto);
                }
                surveyQuestionSupplierDTO.setSurveyResultDtoList(surveyResultDtoList);
            }

            //问题对应的选项
            QueryWrapper<SurveySelection> selectionWrapper = new QueryWrapper();
            selectionWrapper.eq("QUESTION_ID",question.getQuestionId());
            List<SurveySelection> surveySelectionList = surveySelectionMapper.selectList(selectionWrapper);
            surveyQuestionSupplierDTO.setSurveySelectionList(surveySelectionList);

            surveyQuestionSupplierDTOList.add(surveyQuestionSupplierDTO);
        }
        return surveyQuestionSupplierDTOList;
    }

    private void getJobEmployeeDtoList(Long questionId,SurveyQuestionSupplierDTO surveyQuestionSupplierDTO,Long vendorScopeId) {
        List<JobEmployeeDto> jobEmployeeDtoList = new ArrayList<>();
        //查询岗位
        QueryWrapper<SurveyScopeJob> scopeJobWrapper = new QueryWrapper();
        scopeJobWrapper.eq("QUESTION_ID",questionId);
        List<SurveyScopeJob> surveyScopeJobList = surveyScopeJobMapper.selectList(scopeJobWrapper);
        Map<String, List<SurveyScopeJob>> jobMap = surveyScopeJobList.stream().collect(Collectors.groupingBy(SurveyScopeJob::getJob));
        for(Map.Entry<String,List<SurveyScopeJob>> m:jobMap.entrySet()){
            //员工
            List<EmployeeResultDto> employeeResultDtoList = new ArrayList<>();
            JobEmployeeDto jobEmployeeDto = new JobEmployeeDto();
            jobEmployeeDto.setJob(m.getKey());
            List<SurveyScopeJob> surveyScopeJobs = m.getValue();
            //岗位对应的员工
            List<Long> scopeIds = surveyScopeJobs.stream().map(scopeJob -> scopeJob.getScopeId()).collect(Collectors.toList());
            QueryWrapper<SurveyScopeEmployee> scopeEmployeeWrapper = new QueryWrapper();
            scopeEmployeeWrapper.in(CollectionUtils.isNotEmpty(scopeIds),"SCOPE_ID",scopeIds);
            List<SurveyScopeEmployee> employeeList = surveyScopeEmployeeMapper.selectList(scopeEmployeeWrapper);
            if(CollectionUtils.isNotEmpty(employeeList)){
                for(SurveyScopeEmployee employee:employeeList){
                    EmployeeResultDto employeeResultDto = new EmployeeResultDto();
                    BeanCopyUtil.copyProperties(employeeResultDto,employee);

                    //员工对应的结果
                    Long scopeId = employee.getScopeId();
                    QueryWrapper<SurveyResult> resultQueryWrapper = new QueryWrapper<>();
                    resultQueryWrapper.eq("QUESTION_ID",questionId);
                    resultQueryWrapper.eq("EMPLOYEE_SCOPE_ID",scopeId);
                    resultQueryWrapper.eq("VENDOR_SCOPE_ID",vendorScopeId);
                    List<SurveyResult> surveyResults = surveyResultMapper.selectList(resultQueryWrapper);

                    List<SurveyResultDto> surveyResultDtoList = new ArrayList<>();
                    for(SurveyResult surveyResult :surveyResults){
                        SurveyResultDto surveyResultDto = new SurveyResultDto();
                        BeanCopyUtil.copyProperties(surveyResultDto,surveyResult);
                        surveyResultDtoList.add(surveyResultDto);
                    }

                    employeeResultDto.setSurveyResultDtoList(surveyResultDtoList);
                    employeeResultDtoList.add(employeeResultDto);
                }
            }
            jobEmployeeDto.setEmployeeResultDtoList(employeeResultDtoList);
            jobEmployeeDtoList.add(jobEmployeeDto);
        }
        surveyQuestionSupplierDTO.setJobEmployeeDtoList(jobEmployeeDtoList);
    }
}

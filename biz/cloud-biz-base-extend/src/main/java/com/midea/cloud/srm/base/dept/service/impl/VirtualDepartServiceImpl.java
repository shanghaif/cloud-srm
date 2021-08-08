package com.midea.cloud.srm.base.dept.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.base.dept.mapper.DeptMapper;
import com.midea.cloud.srm.base.dept.mapper.VirtualDepartMapper;
import com.midea.cloud.srm.base.dept.service.ICompaniesService;
import com.midea.cloud.srm.base.dept.service.IDeptService;
import com.midea.cloud.srm.base.dept.service.IVirtualDepartService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dept.dto.VirtualDepartImportModel;
import com.midea.cloud.srm.model.base.dept.entity.Companies;
import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.base.dept.entity.VirtualDepart;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
*  <pre>
 *  虚拟组织表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-22 10:53:05
 *  修改内容:
 * </pre>
*/
@Service
public class VirtualDepartServiceImpl extends ServiceImpl<VirtualDepartMapper, VirtualDepart> implements IVirtualDepartService {

    @Resource
    private IDeptService iDeptService;
    @Resource
    private ICompaniesService iCompaniesService;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private FileCenterClient fileCenterClient;

    @Override
    public PageInfo<DeptDto> pageDept(DeptDto deptDto) {
        Assert.notNull(deptDto.getCompany(),"组织缺少公司id");
        Integer pageNum = deptDto.getPageNum();
        Integer pageSize = deptDto.getPageSize();
        deptDto.setPageNum(null);
        deptDto.setPageSize(null);
        PageInfo<DeptDto> deptDtoPageInfo = null;
        ArrayList<DeptDto> deptDtos = getDeptDtos(deptDto);
        deptDtoPageInfo = PageUtil.pagingByFullData(pageNum, pageSize, deptDtos);
        return deptDtoPageInfo;
    }

    private ArrayList<DeptDto> getDeptDtos(DeptDto deptDto) {
        Assert.notNull(deptDto.getCompany(),"缺少必要参数: company");
        Optional.ofNullable(deptDto.getCompany()).ifPresent(s -> deptDto.setCompany(s.trim()));
        Optional.ofNullable(deptDto.getDescr()).ifPresent(s -> deptDto.setDescr(s.trim()));
        // 查询虚拟部门
        deptDto.setOrgId(null); // 去掉组织关联
        List<DeptDto> deptDtos1 = this.baseMapper.queryVirtualDepartByOrgId(deptDto);
        // 查询部门表
        List<DeptDto> deptDtos2 = iDeptService.queryDeptByCompany(deptDto);
        ArrayList<DeptDto> deptDtos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(deptDtos1)){
            deptDtos.addAll(deptDtos1);
        }
        if(CollectionUtils.isNotEmpty(deptDtos2)){
            deptDtos.addAll(deptDtos2);
        }
        if (CollectionUtils.isNotEmpty(deptDtos)) {
            deptDtos.sort((o1, o2) -> o2.getLastUpdateDate().compareTo(o1.getLastUpdateDate()));
        }
        return deptDtos;
    }

    @Override
    public List<DeptDto> getAll(DeptDto deptDto) {
        ArrayList<DeptDto> deptDtos = getDeptDtos(deptDto);
        return deptDtos;
    }

    @Override
    public void add(VirtualDepart virtualDepart) {
        Assert.notNull(virtualDepart.getCompany(),"公司id不能为空");
        Assert.notNull(virtualDepart.getCompanyDescr(),"公司名称不能为空");
        Assert.notNull(virtualDepart.getDeptid(),"部门编码不能为空");
        Assert.notNull(virtualDepart.getDescr(),"部门名字不能为空");
        Assert.notNull(virtualDepart.getStartDate(),"生效时间不能为空");
        Assert.notNull(virtualDepart.getEffStatus(),"状态不能为空");
        Assert.notNull(virtualDepart.getOrgId(),"组织id不能为空");
        List<VirtualDepart> list = this.list(new QueryWrapper<>(new VirtualDepart().setDeptid(virtualDepart.getDeptid())));
        if(CollectionUtils.isNotEmpty(list)){
            throw new BaseException("虚拟部门编码已存在");
        }
        List<Dept> deptList = iDeptService.list(new QueryWrapper<>(new Dept().setDeptid(virtualDepart.getDeptid())));
        if(CollectionUtils.isNotEmpty(deptList)){
            throw new BaseException("HR部门编码已存在");
        }
        // 去掉跟组织关联, 部门只跟公司关联
        virtualDepart.setOrgId(null);
        virtualDepart.setOrgCode(null);
        virtualDepart.setOrgName(null);
        virtualDepart.setVirtualDepartId(IdGenrator.generate());
        // 设置层级
        setLgiDeptLevel(virtualDepart);
        this.save(virtualDepart);
    }

    private void setLgiDeptLevel(VirtualDepart virtualDepart) {
        // 父部门ID
        String partDeptidChn = virtualDepart.getPartDeptidChn();
        if (StringUtil.notEmpty(partDeptidChn)) {
            List<Dept> depts = iDeptService.list(new QueryWrapper<>(new Dept().setDeptid(partDeptidChn)));
            if(CollectionUtils.isNotEmpty(depts)){
                String lgiDeptLevel = depts.get(0).getLgiDeptLevel();
                if(StringUtil.notEmpty(lgiDeptLevel)){
                    virtualDepart.setLgiDeptLevel(getLgiDeptLevel(lgiDeptLevel));
                }
            }else {
                List<VirtualDepart> virtualDeparts = this.list(new QueryWrapper<>(new VirtualDepart().setDeptid(partDeptidChn)));
                if(CollectionUtils.isNotEmpty(virtualDeparts)){
                    String lgiDeptLevel = virtualDeparts.get(0).getLgiDeptLevel();
                    if(StringUtil.notEmpty(lgiDeptLevel)){
                        virtualDepart.setLgiDeptLevel(getLgiDeptLevel(lgiDeptLevel));
                    }
                }
            }
        }else {
            virtualDepart.setPartDeptidChn("-1");
            virtualDepart.setLgiDeptLevel("L1");
        }
    }

    public String getLgiDeptLevel(String str) {
        String substring = str.substring(str.length() - 1);
        return  "L"+ String.valueOf(Integer.parseInt(substring) + 1);
    }

    @Override
    public void modify(VirtualDepart virtualDepart) {
        Assert.notNull(virtualDepart.getVirtualDepartId(),"缺少必要参数: virtualDepartId");
        // 去掉跟组织关联, 部门只跟公司关联
        virtualDepart.setOrgId(null);
        virtualDepart.setOrgCode(null);
        virtualDepart.setOrgName(null);
        // 设置层级
        setLgiDeptLevel(virtualDepart);
        this.updateById(virtualDepart);
    }

    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "公司虚拟部门导入模板";
        ArrayList<VirtualDepartImportModel> virtualDepartImportModels = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,virtualDepartImportModels,VirtualDepartImportModel.class);
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file,fileupload);
        // 读取数据
        List<VirtualDepartImportModel> virtualDepartImportModels = readData(file);
        //
        List<VirtualDepart> virtualDeparts = new ArrayList<>();
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        checkData(virtualDepartImportModels, virtualDeparts, errorFlag);
        if (errorFlag.get()){
            // 有报错
            fileupload.setFileSourceName("公司虚拟部门导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    virtualDepartImportModels, VirtualDepartImportModel.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
        }else {
            if(CollectionUtils.isNotEmpty(virtualDeparts)){
                virtualDeparts.forEach(virtualDepart -> {
                    virtualDepart.setVirtualDepartId(IdGenrator.generate());
                });
                this.saveBatch(virtualDeparts);
            }
        }
        return ImportStatus.importSuccess();
    }

    private void checkData(List<VirtualDepartImportModel> virtualDepartImportModels, List<VirtualDepart> virtualDeparts, AtomicBoolean errorFlag) {
        if(CollectionUtils.isNotEmpty(virtualDepartImportModels)){

            // 公司名称
            List<String> companyNameList = new ArrayList<>();
            // 上级部门编码
            List<String> deptIdList = new ArrayList<>();
            // 用来储存虚拟部门
            Map<String, VirtualDepart> virtualDepartMap = new HashMap<>();
            List<VirtualDepart> virtualDepartList = this.list();
            if(CollectionUtils.isNotEmpty(virtualDepartList)){
                virtualDepartMap = virtualDepartList.stream().collect(Collectors.toMap(VirtualDepart::getDeptid,v->v,(k1,k2)->k1));
            }

            for(VirtualDepartImportModel virtualDepartImportModel : virtualDepartImportModels){
                // 公司名称
                String companyDescr = virtualDepartImportModel.getCompanyDescr();
                if(StringUtil.notEmpty(companyDescr)){
                    companyDescr =companyDescr.trim();
                    companyNameList.add(companyDescr);
                }
                // 父部门编码
                String partDeptidChn = virtualDepartImportModel.getPartDeptidChn();
                if(StringUtil.notEmpty(partDeptidChn)){
                    partDeptidChn = partDeptidChn.trim();
                    deptIdList.add(partDeptidChn);
                }
            }

            // 公司信息
            Map<String, List<Companies>> companieMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(companyNameList)) {
                companyNameList = companyNameList.stream().distinct().collect(Collectors.toList());
                List<Companies> companiesList = iCompaniesService.list(new QueryWrapper<Companies>().in("COMPANY_FULL_NAME", companyNameList));
                if(CollectionUtils.isNotEmpty(companiesList)){
                    companieMap = companiesList.stream().collect(Collectors.groupingBy(Companies::getCompanyFullName));
                }
            }

            // 父部门信息
            Map<String, Dept> deptMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(deptIdList)){
                deptIdList = deptIdList.stream().distinct().collect(Collectors.toList());
                List<Dept> depts = iDeptService.list(new QueryWrapper<Dept>().in("DEPTID", deptIdList));
                if(CollectionUtils.isNotEmpty(depts)){
                    deptMap = depts.stream().collect(Collectors.toMap(Dept::getDeptid,v->v,(k1,k2)->k1));
                }
            }

            // 所有编码
            List<String> deptIds = new ArrayList<>();
            List<String> strings = deptMapper.queryDeptidAll();
            List<String> strings1 = this.baseMapper.queryBeptidAll();
            if(CollectionUtils.isNotEmpty(strings)){
                deptIds.addAll(strings);
            }
            if(CollectionUtils.isNotEmpty(strings1)){
                deptIds.addAll(strings1);
            }

            HashSet<String> hashSet = new HashSet<>();
            ArrayList<String> onlyCode = new ArrayList<>();
            for(VirtualDepartImportModel virtualDepartImportModel : virtualDepartImportModels){

                StringBuffer errorMsg = new StringBuffer();
                StringBuffer onlyKey = new StringBuffer();
                VirtualDepart virtualDepart = new VirtualDepart();
                // 公司名称
                String companyDescr = virtualDepartImportModel.getCompanyDescr();
                if(StringUtil.notEmpty(companyDescr)){
                    companyDescr = companyDescr.trim();
                    onlyKey.append(companyDescr);
                    if(CollectionUtils.isNotEmpty(companieMap.get(companyDescr))){
                        Companies companies = companieMap.get(companyDescr).get(0);
                        virtualDepart.setCompanyDescr(companies.getCompanyFullName());
                        virtualDepart.setCompany(companies.getCompanyId().toString());
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("未找到公司信息; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("公司名称不能为空; ");
                }

                // 部门编码
                String deptid = virtualDepartImportModel.getDeptid();
                if(StringUtil.notEmpty(deptid)){
                    deptid = deptid.trim();
                    onlyKey.append(deptid);
                    if(deptIds.contains(deptid)){
                        errorFlag.set(true);
                        errorMsg.append("部门编码已存在; ");
                    }else if (onlyCode.contains(deptid)){
                        errorMsg.append("部门编码重复; ");
                    }else {
                        onlyCode.add(deptid);
                        if(hashSet.add(onlyKey.toString())){
                            virtualDepart.setDeptid(deptid);
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("公司+部门行存在重复; ");
                        }
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("部门编码不能为空; ");
                }

                // 部门名称
                String descr = virtualDepartImportModel.getDescr();
                if(StringUtil.notEmpty(descr)){
                    descr = descr.trim();
                    virtualDepart.setDescr(descr);
                }else {
                    errorFlag.set(true);
                    errorMsg.append("部门名称不能为空; ");
                }

                // 父级部门编码
                String partDeptidChn = virtualDepartImportModel.getPartDeptidChn();
                if(StringUtil.notEmpty(partDeptidChn)){
                    partDeptidChn = partDeptidChn.trim();
                    Dept dept = deptMap.get(partDeptidChn);
                    if(null == dept){
                        VirtualDepart depart = virtualDepartMap.get(partDeptidChn);
                        if(null != depart){
                            dept = new Dept();
                            BeanCopyUtil.copyProperties(dept,depart);
                        }
                    }
                    if(null != dept){
                        if(dept.getCompanyDescr().equals(virtualDepart.getCompanyDescr())){
                            virtualDepart.setPartDeptidChn(dept.getDeptid());
                            virtualDepart.setPartDescrChn(dept.getDescr());
                        }else {
                            errorFlag.set(true);
                            errorMsg.append("上级部门不是该公司下的部门; ");
                        }
                    }else {
                        errorFlag.set(true);
                        errorMsg.append("未找到对应上级部门信息; ");
                    }
                }else {
                    errorFlag.set(true);
                    errorMsg.append("上级部门编码不能为空; ");
                }

                // 生效时间
                virtualDepart.setStartDate(LocalDate.now());
                virtualDeparts.add(virtualDepart);

                if(errorMsg.length() > 0){
                    virtualDepartImportModel.setErrorMsg(errorMsg.toString());
                }else {
                    virtualDepartMap.put(virtualDepart.getDeptid(),virtualDepart);
                    virtualDepartImportModel.setErrorMsg(null);
                }

            }
        }
    }

    private List<VirtualDepartImportModel> readData(MultipartFile file) {
        List<VirtualDepartImportModel> virtualDepartImportModels = new ArrayList<>();
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<VirtualDepartImportModel> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(VirtualDepartImportModel.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            virtualDepartImportModels = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return virtualDepartImportModels;
    }
}

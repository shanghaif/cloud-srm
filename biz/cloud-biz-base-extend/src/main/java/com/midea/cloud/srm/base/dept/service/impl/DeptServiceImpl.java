package com.midea.cloud.srm.base.dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.base.dept.mapper.DeptMapper;
import com.midea.cloud.srm.base.dept.service.ICompaniesService;
import com.midea.cloud.srm.base.dept.service.IDeptService;
import com.midea.cloud.srm.base.dept.service.IVirtualDepartService;
import com.midea.cloud.srm.model.base.dept.dto.CompaniesDto;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dept.entity.Companies;
import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.base.dept.entity.VirtualDepart;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
*  <pre>
 *  部门表(隆基部门同步) 服务实现类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-01 14:07:03
 *  修改内容:
 * </pre>
*/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Resource
    private ICompaniesService iCompaniesService;

    @Resource
    private IVirtualDepartService iVirtualDepartService;

    /**
     *
     * @param srmCompanyId SRM公司ID
     * @param orgId        业务实体ID
     * @return
     */
    @Override
    public List<Dept> deptTree(String srmCompanyId,Long orgId) {
        Assert.notNull(srmCompanyId,"缺少必要参数: company");
        // 获取根节点
        List<Dept> deptRoots = getDeptRoots(srmCompanyId);
        if(CollectionUtils.isNotEmpty(deptRoots)){
            // 获取部门基础父级关系数据
            Map<String, List<Dept>> deptMap = getDeptMap(srmCompanyId);
            if (null != deptMap) {
                deptRoots.forEach(root -> {
                    // 部门ID
                    String deptid = root.getDeptid();
                    List<Dept> depts = deptMap.get(deptid);
                    root.setDepts(depts);
                    if(CollectionUtils.isNotEmpty(root.getDepts())){
                        setTree(root, deptMap);
                    }
                });
            }
        }
        return deptRoots;
    }

    public List<Dept> getDeptRoots(String srmCompanyId) {
        List<Dept> deptRoots = new ArrayList<>();
        List<Dept> deptRootsTemp = this.list(new QueryWrapper<Dept>().eq("SRM_COMPANY_ID", srmCompanyId).
                and(deptQueryWrapper -> deptQueryWrapper.isNull("PART_DEPTID_CHN").or().eq("PART_DEPTID_CHN","")));
        if(CollectionUtils.isEmpty(deptRootsTemp)){
            int count = this.count(new QueryWrapper<>(new Dept().setSrmCompanyId(new BigDecimal(srmCompanyId))));
            if(count > 0){
                List<Dept> deptList = this.list(new QueryWrapper<>(new Dept().setSrmCompanyId(new BigDecimal(srmCompanyId))).orderByAsc("LGI_DEPT_LEVEL"));
                String lgiDeptLevel = deptList.get(0).getLgiDeptLevel();
                if(StringUtil.notEmpty(lgiDeptLevel)){
                    List<Dept> collect = deptList.stream().filter(dept -> lgiDeptLevel.equals(dept.getLgiDeptLevel())).collect(Collectors.toList());
                    deptRoots.addAll(collect);
                }
            }
        }else {
            deptRoots.addAll(deptRootsTemp);
        }
        List<VirtualDepart> virtualDeparts = iVirtualDepartService.list(new QueryWrapper<>(new VirtualDepart().setCompany(srmCompanyId).setLgiDeptLevel("L1")));
        if(CollectionUtils.isNotEmpty(virtualDeparts)){
            virtualDeparts.forEach(virtualDepart -> {
                Dept dept = new Dept();
                BeanCopyUtil.copyProperties(dept,virtualDepart);
                deptRoots.add(dept);
            });
        }
        return deptRoots;
    }

    private Map<String, List<Dept>> getDeptMap(String srmCompanyId) {
        Map<String, List<Dept>> deptMap = null;
        // 部门信息
        ArrayList<Dept> depts = new ArrayList<>();
        List<Dept> deptList = this.list(new QueryWrapper<>(new Dept().setSrmCompanyId(new BigDecimal(srmCompanyId))).orderByAsc("LGI_DEPT_LEVEL"));
        List<VirtualDepart> virtualDeparts = iVirtualDepartService.list(new QueryWrapper<>(new VirtualDepart().setCompany(srmCompanyId)));
        if(CollectionUtils.isNotEmpty(deptList)){
            depts.addAll(deptList);
        }
        if(CollectionUtils.isNotEmpty(virtualDeparts)){
            virtualDeparts.forEach(virtualDepart -> {
                Dept dept = new Dept();
                BeanCopyUtil.copyProperties(dept,virtualDepart);
                depts.add(dept);
            });
        }
        // 根据父进行分组
        deptMap = depts.stream().filter(dept -> StringUtil.notEmpty(dept.getPartDeptidChn())).collect(Collectors.groupingBy(Dept::getPartDeptidChn));
        return deptMap;
    }

    private void setTree(Dept root, Map<String, List<Dept>> deptMap) {
        List<Dept> deptList1 = root.getDepts();
        deptList1.forEach(dept -> {
            String deptid1 = dept.getDeptid();
            List<Dept> depts1 = deptMap.get(deptid1);
            dept.setDepts(depts1);
            if(CollectionUtils.isNotEmpty(depts1)){
                setTree(dept,deptMap);
            }
        });
    }

    @Override
    public List<DeptDto> queryDeptByCompany(DeptDto deptDto) {
        return this.baseMapper.queryDeptByCompany(deptDto);
    }

    @Override
    public List<CompaniesDto> listAllByCompany() {
        List<CompaniesDto> companiesDtos = new ArrayList<>();
        List<Companies> companiesList = iCompaniesService.list();
        companiesList.forEach(companies -> {
            CompaniesDto companiesDto = new CompaniesDto();
            companiesDto.setCompany(String.valueOf(companies.getCompanyId()));
            companiesDto.setCompanyDescr(companies.getCompanyFullName());
            companiesDtos.add(companiesDto);
        });
        return companiesDtos;
    }
}

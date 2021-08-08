package com.midea.cloud.srm.pr.division.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.pm.pr.division.dto.DivisionMaterialModelDTO;
import com.midea.cloud.srm.model.pm.pr.division.dto.DivisionMaterialQueryDTO;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionMaterial;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.pr.division.mapper.DivisionMaterialMapper;
import com.midea.cloud.srm.pr.division.service.IDivisionCategoryService;
import com.midea.cloud.srm.pr.division.service.IDivisionMaterialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  <pre>
 *  物料分工规则表  服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-22 08:45:14
 *  修改内容:
 * </pre>
 */
@Service
public class DivisionMaterialServiceImpl extends ServiceImpl<DivisionMaterialMapper, DivisionMaterial> implements IDivisionMaterialService {

    @Autowired
    BaseClient baseClient;

    @Autowired
    IDivisionCategoryService iDivisionCategoryService;

    @Autowired
    RbacClient rbacClient;

    @Override
    @Transactional
    public void saveOrUpdateDivisionMaterial(List<DivisionMaterial> divisionMaterials) {
        if (!CollectionUtils.isEmpty(divisionMaterials)) {
            for (DivisionMaterial divisionMaterial : divisionMaterials) {
                if (divisionMaterial == null) continue;
                checkParam(divisionMaterial);
                if (divisionMaterial.getDivisionMaterialId() == null) {
                    saveDivisionMaterial(divisionMaterial);
                } else {
                    if (divisionMaterial.getStartDate() == null) {
                        divisionMaterial.setStartDate(LocalDate.now());
                    }
                    this.updateById(divisionMaterial);
                }
            }
        }
    }

    private void saveDivisionMaterial(DivisionMaterial divisionMaterial) {
        divisionMaterial.setDivisionMaterialId(IdGenrator.generate());
        if (divisionMaterial.getStartDate() == null) {
            divisionMaterial.setStartDate(LocalDate.now());
        }
        try {
            this.save(divisionMaterial);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            String message = e.getMessage();
            if (StringUtils.isNotBlank(message) && message.indexOf("ceea_pr_division_material_u1") != -1) {
                throw new BaseException(LocaleHandler.getLocaleMsg("已存在相同业务主体、相同库存组织、相同物料的物料分工规则"));
            }
        }
    }

    @Override
    public PageInfo<DivisionMaterial> listPageByParam(DivisionMaterialQueryDTO divisionMaterialQueryDTO) {
        PageUtil.startPage(divisionMaterialQueryDTO.getPageNum(), divisionMaterialQueryDTO.getPageSize());
        QueryWrapper<DivisionMaterial> queryWrapper = new QueryWrapper<>(new DivisionMaterial());
        queryWrapper.in(!CollectionUtils.isEmpty(divisionMaterialQueryDTO.getOrgIds()), "ORG_ID", divisionMaterialQueryDTO.getOrgIds());
        queryWrapper.in(!CollectionUtils.isEmpty(divisionMaterialQueryDTO.getOrganizationIds()), "ORGANIZATION_ID", divisionMaterialQueryDTO.getOrganizationIds());
        queryWrapper.in(!CollectionUtils.isEmpty(divisionMaterialQueryDTO.getMaterialIds()), "MATERIAL_ID", divisionMaterialQueryDTO.getMaterialIds());
        queryWrapper.like(StringUtils.isNotBlank(divisionMaterialQueryDTO.getSupUserNickname()), "SUP_USER_NICKNAME", divisionMaterialQueryDTO.getSupUserNickname());
        queryWrapper.like(StringUtils.isNotBlank(divisionMaterialQueryDTO.getStrategyUserNickname()), "STRATEGY_USER_NICKNAME", divisionMaterialQueryDTO.getStrategyUserNickname());
        queryWrapper.like(StringUtils.isNotBlank(divisionMaterialQueryDTO.getPerformUserNickname()), "PERFORM_USER_NICKNAME", divisionMaterialQueryDTO.getPerformUserNickname());
        if (StringUtils.isNotBlank(divisionMaterialQueryDTO.getEnable()) && Enable.Y.name().equals(divisionMaterialQueryDTO.getEnable())) {
            queryWrapper.le("START_DATE", LocalDate.now());
            queryWrapper.gt("END_DATE", LocalDate.now()).or().isNull("END_DATE");
        }
        else if (StringUtils.isNotBlank(divisionMaterialQueryDTO.getEnable()) && Enable.N.name().equals(divisionMaterialQueryDTO.getEnable())) {
            queryWrapper.le("END_DATE", LocalDate.now());
        }
        return new PageInfo<>(this.list(queryWrapper));
    }

    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "品类分工规则导入模版";
        List<DivisionMaterialModelDTO> divisionMaterialModelDTOS = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream, fileName, divisionMaterialModelDTOS,DivisionMaterialModelDTO.class);
    }

    @Override
    @Transactional
    public void importExcel(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        if (!EasyExcelUtil.isExcel(originalFilename)) {
            throw new RuntimeException("请导入正确的Excel文件");
        }

        InputStream inputStream = file.getInputStream();
        List<DivisionMaterialModelDTO> divisionMaterialModelDTOS = new ArrayList<>();

        //检查导入数据
        List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, DivisionMaterialModelDTO.class);
        if (!CollectionUtils.isEmpty(objects)) {
            for (int i = 0; i < objects.size(); i++) {
                Object object = objects.get(i);
                if (object == null) continue;
                DivisionMaterialModelDTO divisionMaterialModelDTO = (DivisionMaterialModelDTO) object;
                divisionMaterialModelDTO.setRow(i + 2);
                DivisionMaterial divisionMaterial = new DivisionMaterial();
                //检查参数
                checkParamBeforeImportExcel(divisionMaterialModelDTO, divisionMaterial);
                //保存数据
                saveDivisionMaterial(divisionMaterial);
            }

        }else {
            throw new BaseException(LocaleHandler.getLocaleMsg("导入excel没有数据"));
        }
    }

    private void checkParamBeforeImportExcel(DivisionMaterialModelDTO divisionMaterialModelDTO, DivisionMaterial divisionMaterial) {
        //校验日期格式
        String startDate = divisionMaterialModelDTO.getStartDate();
        String endDate = divisionMaterialModelDTO.getEndDate();
        int row = divisionMaterialModelDTO.getRow();
        LocalDate parseStartDate = null;
        LocalDate parseEndDate = null;
        if (StringUtils.isNotBlank(startDate)) {
            try {
                Date date = DateUtil.parseDate(startDate);
                parseStartDate = DateUtil.dateToLocalDate(date);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BaseException(LocaleHandler.getLocaleMsg("第行生效日期格式错误,请检查!", "" + row));
            }
        }
        if (StringUtils.isNotBlank(endDate)) {
            try {
                Date date = DateUtil.parseDate(endDate);
                parseEndDate = DateUtil.dateToLocalDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BaseException(LocaleHandler.getLocaleMsg("第行失效日期格式错误,请检查!", "" + row));
            }
        }

        //校验导入必填项
        if (StringUtils.isBlank(divisionMaterialModelDTO.getOrgName())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("第行业务主体为空", "" + row));
        }
        if (StringUtils.isBlank(divisionMaterialModelDTO.getOrganizationName())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("第行库存组织为空", "" + row));
        }
        if (StringUtils.isBlank(divisionMaterialModelDTO.getMaterialCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("第行物料编码为空", "" + row));
        }
        if (StringUtils.isBlank(divisionMaterialModelDTO.getMaterialName())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("第行物料名称为空", "" + row));
        }
        if (StringUtils.isBlank(divisionMaterialModelDTO.getSupUserNickname())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("第行供应商管理为空", "" + row));
        }
        if (StringUtils.isBlank(divisionMaterialModelDTO.getStrategyUserNickname())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("第行策略负责为空", "" + row));
        }
        if (StringUtils.isBlank(divisionMaterialModelDTO.getPerformUserNickname())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("第行采购履行为空", "" + row));
        }
        //校验业务实体是否存在
        Organization org = baseClient.getOrganizationByParam(new Organization().setOrganizationName(divisionMaterialModelDTO.getOrgName()));
        Assert.notNull(org, LocaleHandler.getLocaleMsg("第行的业务实体不存在", "" + row));

        //校验库存组织是否存在
        Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(divisionMaterialModelDTO.getOrganizationName()));
        Assert.notNull(organization, LocaleHandler.getLocaleMsg("第行的库存组织不存在", "" + row));

        //检验物料小类是否存在
        List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialName(divisionMaterialModelDTO.getMaterialName()));
        Assert.notEmpty(materialItems, LocaleHandler.getLocaleMsg("第行的物料不存在", "" + row));

        //检验供应商管理采购员是否存在
        List<User> supUsers = rbacClient.listByUser(new User().setNickname(divisionMaterialModelDTO.getSupUserNickname()));
        Assert.notEmpty(supUsers, LocaleHandler.getLocaleMsg("第行的供应商管理采购员不存在", "" + row));

        //校验策略负责采购员是否存在
        List<User> strategyUsers = rbacClient.listByUser(new User().setNickname(divisionMaterialModelDTO.getStrategyUserNickname()));
        Assert.notEmpty(strategyUsers, LocaleHandler.getLocaleMsg("第行的策略负责采购员不存在", "" + row));

        //采购履行采购员是否存在
        List<User> performUsers = rbacClient.listByUser(new User().setNickname(divisionMaterialModelDTO.getPerformUserNickname()));
        Assert.notEmpty(performUsers, LocaleHandler.getLocaleMsg("第行的采购履行采购员不存在", "" + row));

        BeanUtils.copyProperties(divisionMaterialModelDTO, divisionMaterial, startDate, endDate);
        divisionMaterial.setStartDate(parseStartDate).setEndDate(parseEndDate);

        //补全业务主体信息
        divisionMaterial.setOrgId(org.getOrganizationId())
                .setOrgCode(org.getOrganizationCode())
                .setOrgName(org.getOrganizationName());

        //补全库存组织信息
        divisionMaterial.setOrganizationId(organization.getOrganizationId())
                .setOrganizationCode(organization.getOrganizationCode())
                .setOrganizationName(organization.getOrganizationName());

        //补全物料小类信息
        divisionMaterial.setMaterialId(materialItems.get(0).getMaterialId())
                .setMaterialCode(materialItems.get(0).getMaterialCode())
                .setMaterialName(materialItems.get(0).getMaterialName());

        //补全供应商管理采购员信息
        divisionMaterial.setSupUserId(supUsers.get(0).getUserId())
                .setSupUserNickname(supUsers.get(0).getNickname())
                .setSupUserName(supUsers.get(0).getUsername());

        //补全策略负责采购员信息
        divisionMaterial.setStrategyUserId(strategyUsers.get(0).getUserId())
                .setStrategyUserNickname(strategyUsers.get(0).getNickname())
                .setStrategyUserName(strategyUsers.get(0).getUsername());

        //补全采购履行采购员信息
        divisionMaterial.setPerformUserId(performUsers.get(0).getUserId())
                .setPerformUserNickname(performUsers.get(0).getNickname())
                .setPerformUserName(performUsers.get(0).getUsername());

        //基础校验
        checkParam(divisionMaterial);
    }

    private void checkParam(DivisionMaterial divisionMaterial) {
        //校验在品类分工规则里是否存在该物料对应的品类
        Long materialId = divisionMaterial.getMaterialId();
        List<MaterialItem> materialItems = baseClient.listMaterialByParam(new MaterialItem().setMaterialId(materialId));
        if (!CollectionUtils.isEmpty(materialItems)) {
            MaterialItem materialItem = materialItems.get(0);
            if (materialItem != null) {
                Long categoryId = materialItem.getCategoryId();
                DivisionCategory one = iDivisionCategoryService.getOne(new QueryWrapper<>(new DivisionCategory()
                        .setCategoryId(categoryId)
                        .setOrgId(divisionMaterial.getOrgId())
                        .setOrganizationId(divisionMaterial.getOrganizationId())));
                if (one != null) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("业务实体：,库存组织：,物料：对应物料小类下已存在对应品类分工规则,请检查!",
                            divisionMaterial.getOrgName(), divisionMaterial.getOrganizationName(), divisionMaterial.getMaterialName()));
                }
            }
        }
    }
}

package com.midea.cloud.srm.pr.division.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.pr.requirement.DUTY;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.TitleColorSheetWriteHandler;
import com.midea.cloud.common.listener.AnalysisEventListenerBatch;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.division.dto.DivisionCategoryModelDTO;
import com.midea.cloud.srm.model.pm.pr.division.dto.DivisionCategoryQueryDTO;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.pr.division.mapper.DivisionCategoryMapper;
import com.midea.cloud.srm.pr.division.service.IDivisionCategoryService;
import com.midea.cloud.srm.pr.division.service.IDivisionMaterialService;
import com.midea.cloud.srm.pr.division.utils.ExportUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  品类分工规则表 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-22 08:41:41
 *  修改内容:
 * </pre>
 */
@Service
public class DivisionCategoryServiceImpl extends ServiceImpl<DivisionCategoryMapper, DivisionCategory> implements IDivisionCategoryService {

    @Autowired
    BaseClient baseClient;

    @Autowired
    IDivisionMaterialService iDivisionMaterialService;

    @Autowired
    RbacClient rbacClient;

    @Autowired
    @Qualifier("requestExecutors")
    private ThreadPoolExecutor requestExecutors;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Override
    @Transactional
    public void saveOrUpdateDivisionCategory(List<DivisionCategory> divisionCategories) {
        if (!CollectionUtils.isEmpty(divisionCategories)) {
            for (DivisionCategory divisionCategory : divisionCategories) {
                if (divisionCategory == null) continue;
                if (divisionCategory.getDivisionCategoryId() == null) {
                    checkBeforeSave(divisionCategory);
                    divisionCategory.setDivisionCategoryId(IdGenrator.generate());
                    if (divisionCategory.getStartDate() == null) {
                        divisionCategory.setStartDate(LocalDate.now());
                    }
                    this.save(divisionCategory);
                } else {
                    if (divisionCategory.getStartDate() == null) {
                        divisionCategory.setStartDate(LocalDate.now());
                    }
                    this.updateById(divisionCategory);
                }
            }
        }
    }

    private void checkBeforeSave(DivisionCategory divisionCategory) {
        if (YesOrNo.YES.getValue().equals(divisionCategory.getIfMainPerson())) {
            DivisionCategory divisionCategoryQuery = new DivisionCategory()
                    .setOrgId(divisionCategory.getOrgId())
                    .setOrganizationId(divisionCategory.getOrganizationId())
                    .setCategoryId(divisionCategory.getCategoryId())
                    .setDuty(divisionCategory.getDuty())
                    .setIfMainPerson(divisionCategory.getIfMainPerson());
            QueryWrapper<DivisionCategory> queryWrapper = new QueryWrapper<>(divisionCategoryQuery);
            List<DivisionCategory> divisionCategoryList = this.list(queryWrapper);
            Assert.isTrue(CollectionUtils.isEmpty(divisionCategoryList), LocaleHandler.getLocaleMsg("已存在相同的品类分工规则,请检查!"));
        }
        if (YesOrNo.NO.getValue().equals(divisionCategory.getIfMainPerson())) {
            DivisionCategory divisionCategoryQuery = new DivisionCategory()
                    .setOrgId(divisionCategory.getOrgId())
                    .setOrganizationId(divisionCategory.getOrganizationId())
                    .setCategoryId(divisionCategory.getCategoryId())
                    .setDuty(divisionCategory.getDuty())
                    .setPersonInChargeUserId(divisionCategory.getPersonInChargeUserId())
                    .setIfMainPerson(divisionCategory.getIfMainPerson());
            QueryWrapper<DivisionCategory> queryWrapper = new QueryWrapper<>(divisionCategoryQuery);
            List<DivisionCategory> divisionCategoryList = this.list(queryWrapper);
            Assert.isTrue(CollectionUtils.isEmpty(divisionCategoryList), LocaleHandler.getLocaleMsg("已存在相同的品类分工规则,请检查!"));
        }
    }

    private void saveDivisionCategory(DivisionCategory divisionCategory) {
        divisionCategory.setDivisionCategoryId(IdGenrator.generate());
        if (divisionCategory.getStartDate() == null) {
            divisionCategory.setStartDate(LocalDate.now());
        }
        this.save(divisionCategory);

//        需求修改,不需要
//        try {
//        } catch (DuplicateKeyException e) {
//            e.printStackTrace();
//            String message = e.getMessage();
//            if (StringUtils.isNotBlank(message) && message.indexOf("ceea_pr_division_category_u1") != -1) {
//                throw new BaseException(LocaleHandler.getLocaleMsg("已存在相同业务主体、相同库存组织、相同物料小类的品类分工规则"));
//            }
//        }
    }

    @Override
    public PageInfo<DivisionCategory> listPageByParam(DivisionCategoryQueryDTO divisionCategoryQueryDTO) {
        PageUtil.startPage(divisionCategoryQueryDTO.getPageNum(), divisionCategoryQueryDTO.getPageSize());
        DivisionCategory divisionCategory = new DivisionCategory();
        if (StringUtils.isNotBlank(divisionCategoryQueryDTO.getDuty())) {
            divisionCategory.setDuty(divisionCategoryQueryDTO.getDuty());
        }
        QueryWrapper<DivisionCategory> queryWrapper = new QueryWrapper<>(divisionCategory);
        queryWrapper.in(!CollectionUtils.isEmpty(divisionCategoryQueryDTO.getOrgIds()), "ORG_ID", divisionCategoryQueryDTO.getOrgIds());
        queryWrapper.in(!CollectionUtils.isEmpty(divisionCategoryQueryDTO.getOrganizationIds()), "ORGANIZATION_ID", divisionCategoryQueryDTO.getOrganizationIds());
//        queryWrapper.in(!CollectionUtils.isEmpty(divisionCategoryQueryDTO.getCategoryIds()), "CATEGORY_ID", divisionCategoryQueryDTO.getCategoryIds());
        queryWrapper.eq(StringUtils.isNotBlank(divisionCategoryQueryDTO.getIfMainPerson()), "IF_MAIN_PERSON", divisionCategoryQueryDTO.getIfMainPerson())
                .like(StringUtils.isNotBlank(divisionCategoryQueryDTO.getPersonInChargeNickname()), "PERSON_IN_CHARGE_NICKNAME", divisionCategoryQueryDTO.getPersonInChargeNickname());
        queryWrapper.like(StringUtils.isNotBlank(divisionCategoryQueryDTO.getSupUserNickname()), "SUP_USER_NICKNAME", divisionCategoryQueryDTO.getSupUserNickname());
        queryWrapper.like(StringUtils.isNotBlank(divisionCategoryQueryDTO.getStrategyUserNickname()), "STRATEGY_USER_NICKNAME", divisionCategoryQueryDTO.getStrategyUserNickname());
        queryWrapper.like(StringUtils.isNotBlank(divisionCategoryQueryDTO.getPerformUserNickname()), "PERFORM_USER_NICKNAME", divisionCategoryQueryDTO.getPerformUserNickname());
        queryWrapper.like(StringUtils.isNotBlank(divisionCategoryQueryDTO.getPersonInCharge()), "PERSON_IN_CHARGE", divisionCategoryQueryDTO.getPersonInCharge());
        if (StringUtils.isNotBlank(divisionCategoryQueryDTO.getEnable()) && Enable.Y.name().equals(divisionCategoryQueryDTO.getEnable())) {
            queryWrapper.le("START_DATE", LocalDate.now());
            queryWrapper.gt("END_DATE", LocalDate.now()).or().isNull("END_DATE");
        } else if (StringUtils.isNotBlank(divisionCategoryQueryDTO.getEnable()) && Enable.N.name().equals(divisionCategoryQueryDTO.getEnable())) {
            queryWrapper.le("END_DATE", LocalDate.now());
        }
        queryWrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<>(this.list(queryWrapper));
    }


    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "品类分工规则导入模版";
        List<DivisionCategoryModelDTO> divisionCategoryModelDTOS = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        // 指定标颜色的行
        List<Integer> rows = Arrays.asList(0);
        // 指定标颜色的列
        List<Integer> columns = Arrays.asList(0, 1, 2, 3, 4, 5, 7, 8);
        TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rows, columns, IndexedColors.RED.index);
        EasyExcelUtil.writeExcelWithModel(outputStream, divisionCategoryModelDTOS, DivisionCategoryModelDTO.class, fileName, titleColorSheetWriteHandler);
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file) throws Exception {
        Map<String, Object> uploadResult = new HashMap<>();
        String filename = file.getOriginalFilename();
        if (!EasyExcelUtil.isExcel(filename)) {
            throw new BaseException("请导入正确的Excel文件");
        }
        List<DictItemDTO> duty = baseClient.listAllByDictCode("DUTY");
        Map<String, DictItemDTO> dutyMap = duty.stream().collect(Collectors.toMap(DictItemDTO::getDictItemName, Function.identity()));
        InputStream inputStream = file.getInputStream();
        List<DivisionCategoryModelDTO> faiList = new LinkedList<>();
        Function<Collection<DivisionCategoryModelDTO>, Collection<DivisionCategory>> operator = (e -> {
            List<DivisionCategory> categoryDvs = new LinkedList<>();
            //获取信息参数
            Set<String> categoryCodes = new HashSet<>();
            Set<String> invNames = new HashSet<>();
            Set<String> orgNames = new HashSet<>();
            Set<String> UserNos = new HashSet<>();
            //设置信息
            e.forEach(q -> {
                categoryCodes.add(q.getCategoryCode());
                invNames.add(q.getOrganizationName());
                orgNames.add(q.getOrgName());
                UserNos.add(q.getErpNum());
            });
            CountDownLatch countDownLatch = new CountDownLatch(4);
            Map<String, Organization> invInfoMap = null;
            Map<String, Organization> organizationInfoMap = null;
            Map<String, PurchaseCategory> purchaseCategoryInfoMap = null;
            Map<String, User> userInfoMap = null;
            try {
                //获取小类信息
                purchaseCategoryInfoMap = CompletableFuture.supplyAsync(() -> {
                    Map<String, PurchaseCategory> result = baseClient.getCategoryByCodes(categoryCodes);
                    countDownLatch.countDown();
                    return result;
                }, requestExecutors).get();
                invInfoMap = CompletableFuture.supplyAsync(() -> {
                    Map<String, Organization> result = baseClient.getOrganizationsByNames(invNames);
                    countDownLatch.countDown();
                    return result;
                }, requestExecutors).get();

                organizationInfoMap = CompletableFuture.supplyAsync(() -> {
                    Map<String, Organization> result = baseClient.getOrganizationsByNames(orgNames);
                    countDownLatch.countDown();
                    return result;
                }, requestExecutors).get();
                userInfoMap = CompletableFuture.supplyAsync(() -> {
                    Map<String, User> result = rbacClient.getUserByNos(UserNos);
                    countDownLatch.countDown();
                    return result;
                }, requestExecutors).get();
                countDownLatch.await();
            } catch (Throwable tx) {
                log.error("请求库存、组织、小类、负责人信息的countDownLatch被终止");
                throw new BaseException(tx.getMessage());
            }
            Set<Long> orgIds = organizationInfoMap.values().stream().map(Organization::getOrganizationId).collect(Collectors.toSet());
            Set<Long> invIds = invInfoMap.values().stream().map(Organization::getOrganizationId).collect(Collectors.toSet());
            Set<Long> cateogoryIds = purchaseCategoryInfoMap.values().stream().map(PurchaseCategory::getCategoryId).collect(Collectors.toSet());
            LambdaQueryWrapper<DivisionCategory> query = Wrappers.lambdaQuery(DivisionCategory.class)
                    .in(!CollectionUtils.isEmpty(orgIds), DivisionCategory::getOrgId, orgIds)
                    .in(!CollectionUtils.isEmpty(invIds), DivisionCategory::getOrganizationId, invIds)
                    .in(!CollectionUtils.isEmpty(cateogoryIds), DivisionCategory::getCategoryId, cateogoryIds);
            List<DivisionCategory> list = list(query);
            Map<Long, Map<Long, Map<Long, List<DivisionCategory>>>> orgAndInvAndCategory = list.stream().collect(Collectors.groupingBy(DivisionCategory::getOrgId, Collectors.groupingBy(DivisionCategory::getOrganizationId, Collectors.groupingBy(DivisionCategory::getCategoryId))));

            for (DivisionCategoryModelDTO current : e) {
                boolean error = false;
                {
                    StringBuilder errMsg = new StringBuilder();
                    String s = ObjectUtil.validateForImport(current, ",");
                    if (!Objects.equals(s, YesOrNo.YES.getValue())) {
                        errMsg.append(s).append(",");
                        error = true;
                    }
                    //校验日期格式
                    String startDate = current.getStartDate();
                    String endDate = current.getEndDate();
                    LocalDate parseStartDate = null;
                    LocalDate parseEndDate = null;
                    //校验职责并赋值
                    String trim = current.getDuty().trim();
                    DictItemDTO dictItemDTO = dutyMap.get(trim);
                    if (Objects.isNull(dictItemDTO)) {
                        errMsg.append(String.format("暂不支持的职责%s，请从字典配置", trim));
                        error = true;
                    }

                    try {
                        Date begin = DateUtil.parseDate(startDate);
                        parseStartDate = DateUtil.dateToLocalDate(begin);
                        if (StringUtils.isNotBlank(endDate)) {
                            Date end = DateUtil.parseDate(endDate);
                            parseEndDate = DateUtil.dateToLocalDate(end);
                        }
                    } catch (Exception pe) {
                        errMsg.append("生效/失效日期格式有误,");
                        error = true;
                    }

                    //校验四个信息
                    String categoryCode = current.getCategoryCode();
                    PurchaseCategory category = purchaseCategoryInfoMap.get(categoryCode);
                    if (Objects.isNull(category)) {
                        errMsg.append(String.format("无法找到编码为%s的物料小类信息,", categoryCode));
                        error = true;
                    }
                    String invName = current.getOrganizationName();
                    Organization inv = invInfoMap.get(invName);
                    if (Objects.isNull(inv)) {
                        errMsg.append(String.format("无法找到名称为%s的库存组织信息,", invName));
                        error = true;
                    }
                    String orgName = current.getOrgName();
                    Organization org = organizationInfoMap.get(orgName);
                    if (Objects.isNull(org)) {
                        errMsg.append(String.format("无法找到名称为%s的库存组织信息,", invName));
                        error = true;
                    }
                    String erpNum = current.getErpNum();
                    User user = userInfoMap.get(erpNum);
                    if (Objects.isNull(user)) {
                        errMsg.append(String.format("无法找到工号为%s的用户,", erpNum));
                        error = true;
                    }
                    boolean isMain = Objects.equals(current.getIfMainPerson(), YesOrNo.YES.getValue());
                    if (!error) {
                        //校验是否可以插入
                        List<Long> removeIds = new LinkedList<>();
                        Map<Long, Map<Long, List<DivisionCategory>>> invMap = orgAndInvAndCategory.get(org.getOrganizationId());
                        if (Objects.nonNull(invMap) && Objects.nonNull(invMap.get(inv.getOrganizationId()))) {
                            Map<Long, List<DivisionCategory>> categoryMap = invMap.get(inv.getOrganizationId());
                            if (Objects.nonNull(categoryMap) && Objects.nonNull(categoryMap.get(category.getCategoryId()))) {
                                List<DivisionCategory> division = categoryMap.get(category.getCategoryId());
                                for (DivisionCategory divisionCategory : division) {
                                    if (Objects.equals(divisionCategory.getDuty(), dictItemDTO.getDictItemCode())) {
                                        if (Objects.equals(divisionCategory.getIfMainPerson(), "Y") && isMain) {
                                            //把原来的删掉

                                            removeIds.add(divisionCategory.getDivisionCategoryId());
                                            /*errMsg.append("相同业务实体，相同库存组织、相同小类，相同职责，仅有一个主负责人,");
                                            error = true;
                                            break;*/
                                        } else {
                                            if (Objects.equals(divisionCategory.getIfMainPerson(), current.getIfMainPerson()) && Objects.equals(divisionCategory.getPersonInChargeNickname(), current.getPersonInChargeNickname())) {
                                                errMsg.append("已存在相同的品类分工规则,");
                                                error = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (!error) {
                            removeByIds(removeIds);
                            //执行赋值操作
                            DivisionCategory divisionCategory = BeanCopyUtil.copyProperties(current, DivisionCategory::new);
                            divisionCategory
                                    .setOrganizationCode(inv.getOrganizationCode())
                                    .setOrganizationId(inv.getOrganizationId())
                                    .setOrgCode(org.getOrganizationCode())
                                    .setOrgId(org.getOrganizationId())
                                    .setCategoryCode(category.getCategoryCode())
                                    .setCategoryId(category.getCategoryId())
                                    .setPersonInChargeUserId(user.getUserId())
                                    .setPersonInChargeNickname(user.getNickname())
                                    .setPersonInChargeUsername(user.getUsername())
                                    .setStartDate(parseStartDate).setEndDate(parseEndDate)
                                    .setDivisionCategoryId(IdGenrator.generate());
                            divisionCategory.setDuty(dictItemDTO.getDictItemCode());
                            categoryDvs.add(divisionCategory);
                        }
                    }
                    if (error) {
                        errMsg = errMsg.deleteCharAt(errMsg.length() - 1);
                        current.setErrorMsg(errMsg.toString());
                        faiList.add(current);
                    }
                }
            }

            return categoryDvs;
        });
        try {
            AnalysisEventListenerBatch<DivisionCategoryModelDTO, DivisionCategory> batch = new AnalysisEventListenerBatch<>(
                    1,
                    operator,
                    this
            );
            EasyExcelUtil.readExcelWithModel(inputStream, DivisionCategoryModelDTO.class, batch, 0);
            if (!CollectionUtils.isEmpty(faiList)) {
                String type = filename.substring(filename.lastIndexOf(".") + 1);
                Fileupload fileupload = new Fileupload()
                        .setFileModular("采购需求")
                        .setFileFunction("品类分工规则")
                        .setSourceType("品类分工规则导入")
                        .setUploadType(FileUploadType.FASTDFS.name())
                        .setFileType(type);
                fileupload.setFileSourceName("品类分工规则导入报错." + type);
                Fileupload wrongFile = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                        faiList, DivisionCategoryModelDTO.class, file);
                uploadResult.put("status", YesOrNo.NO.getValue());
                uploadResult.put("message", "error");
                uploadResult.put("fileuploadId", wrongFile.getFileuploadId());
                uploadResult.put("fileName", fileupload.getFileSourceName());
            } else {
                uploadResult.put("status", YesOrNo.YES.getValue());
                uploadResult.put("message", "success");
            }
        } catch (Throwable ex) {
            log.error(ex.getMessage(), ex);
        }
        return uploadResult;
    }


    @Override
    public List<List<Object>> queryExportData(ExportExcelParam<DivisionCategory> param) {
        DivisionCategory queryParam = param.getQueryParam();
        // 检查是否要分页导出
        boolean flag = StringUtil.notEmpty(queryParam.getPageSize()) && StringUtil.notEmpty(queryParam.getPageNum());
        if (flag) {
            // 设置分页
            PageUtil.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }
        // 查询数据
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<DivisionCategoryModelDTO> categoryDvs = list(Wrappers.lambdaQuery(queryParam)
                .in(!CollectionUtils.isEmpty(queryParam.getOrgIds()),DivisionCategory::getOrgId,queryParam.getOrgIds())
                .in(!CollectionUtils.isEmpty(queryParam.getOrganizationIds()),DivisionCategory::getOrganizationId,queryParam.getOrganizationIds())
                .orderByDesc(DivisionCategory::getLastUpdateDate))
                .stream().map(e -> {
                    DivisionCategoryModelDTO divisionCategoryModelDTO = BeanCopyUtil.copyProperties(e, DivisionCategoryModelDTO::new);
                    divisionCategoryModelDTO.setStartDate(dateTimeFormatter.format(e.getStartDate()));
                    if (Objects.nonNull(e.getEndDate())) {
                        divisionCategoryModelDTO.setEndDate(dateTimeFormatter.format(e.getEndDate()));
                    }
                    return divisionCategoryModelDTO;
                })
                .collect(Collectors.toList());
        List<List<Object>> dataList = new ArrayList<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(categoryDvs)) {
            List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(categoryDvs);
            ArrayList<String> titleList = param.getTitleList();
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(titleList)) {
                for (Map<String, Object> map : mapList) {
                    ArrayList<Object> objects = new ArrayList<>();
                    for (String key : titleList) {
                        //这里不改动枚举了，怕影响其他类
                        if (Objects.equals("duty", key)) {
                            String s = map.get(key).toString();
                            String message = "";
                            if (Objects.equals(s, DUTY.Carry_Out.name())) {
                                message = "采购履行";
                            }
                            if (Objects.equals(DUTY.Purchase_Strategy.name(), s)) {
                                message = "采购策略";
                            }
                            if (Objects.equals(DUTY.Purchase_Manager.name(), s)) {
                                message = "采购经理";
                            }
                            objects.add(message);
                        } else {
                            objects.add(map.get(key));
                        }
                    }
                    dataList.add(objects);
                }
            }
        }
        return dataList;
    }


    @Override
    public List<String> getMultilingualHeader(ExportExcelParam<DivisionCategory> param) {
        LinkedHashMap<String, String> categoryDvTitles = ExportUtils.getCategoryDvTitles();
        return param.getMultilingualHeader(param, categoryDvTitles);
    }

    @Override
    public void exportStart(ExportExcelParam<DivisionCategory> param, HttpServletResponse response) throws IOException {
        // 获取导出的数据
        List<List<Object>> dataList = queryExportData(param);
        // 标题
        List<String> head = getMultilingualHeader(param);
        // 文件名
        String fileName = param.getFileName();
        // 开始导出
        EasyExcelUtil.exportStart(response, dataList, head, fileName);
    }


    @Override
    public boolean deleteDucplite() {

        List<DivisionCategory> list = this.list(Wrappers.lambdaQuery(DivisionCategory.class).eq(DivisionCategory::getIfMainPerson, "Y"));

//        按库存组织、小类、职责、是否主责为Y，如果有重复行，把维护的早的删掉，保留最后维护的一条,只处理是否主责为Y的数据
        Map<String, List<DivisionCategory>> map = list.stream()
                .filter(x -> Objects.equals(x.getIfMainPerson(), "Y"))
                .collect(Collectors.groupingBy(x -> getKey(x)));
        ArrayList<DivisionCategory> objects = new ArrayList<>();
        for (Map.Entry<String, List<DivisionCategory>> m : map.entrySet()) {
            List<DivisionCategory> value = m.getValue();
            List<DivisionCategory> collect = value.stream()
                    .sorted(Comparator.comparing(DivisionCategory::getCreationDate)).collect(Collectors.toList());
            for (int i = 0; i < collect.size() - 1; i++) {
                objects.add(collect.get(i));
            }
        }
        this.removeByIds(objects.stream().map(DivisionCategory::getDivisionCategoryId).collect(Collectors.toList()));

        return true;

    }

    public String getKey(DivisionCategory dc) {
        StringBuilder sb = new StringBuilder();
        return sb.append(dc.getOrganizationCode()).append(dc.getCategoryCode()).append(dc.getDuty()).toString();
    }

}

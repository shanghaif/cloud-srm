package com.midea.cloud.srm.sup.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.common.enums.base.BigCategoryTypeEnum;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemVo;
import com.midea.cloud.srm.model.base.material.vo.MaterialMaxCategoryVO;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.supplier.info.dto.SiteInfoQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.sup.info.mapper.SiteInfoMapper;
import com.midea.cloud.srm.sup.info.service.ISiteInfoService;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static com.midea.cloud.common.utils.Functions.distinctByKey;

/**
 * <pre>
 *  地点信息（供应商） 服务实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-20 11:19:01
 *  修改内容:
 * </pre>
 */
@Service
public class SiteInfoServiceImpl extends ServiceImpl<SiteInfoMapper, SiteInfo> implements ISiteInfoService {

    @Autowired
    private BaseClient baseClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateSite(SiteInfo siteInfo, Long companyId) {
        siteInfo.setCompanyId(companyId);
        if (siteInfo.getSiteInfoId() != null) {
            siteInfo.setLastUpdateDate(new Date());
        } else {
            siteInfo.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            siteInfo.setSiteInfoId(id);
        }
        this.saveOrUpdate(siteInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateSite(SiteInfo siteInfo) {
        if (siteInfo.getSiteInfoId() != null) {
            siteInfo.setLastUpdateDate(new Date());
        } else {
            siteInfo.setCreationDate(new Date())
                    .setLastUpdateDate(new Date());
            Long id = IdGenrator.generate();
            siteInfo.setSiteInfoId(id);
        }
        this.saveOrUpdate(siteInfo);
    }

    @Override
    public List<SiteInfo> listSiteInfoByParam(SiteInfoQueryDTO siteInfoQueryDTO) {
        SiteInfo siteInfo = new SiteInfo();
        BeanUtils.copyProperties(siteInfoQueryDTO, siteInfo);
        QueryWrapper<SiteInfo> queryWrapper = new QueryWrapper<>(siteInfo);
        /**
         * 取消供应商地点校验
         */
//        queryWrapper.isNotNull("VENDOR_SITE_ID")
//                .ne("VENDOR_SITE_ID", "");
        return this.list(queryWrapper);
    }

    /**
     * 1、物料大类为生产材料/备品备件（编码为10/30）时，供应商地点仅能选择材料或寄售；
     * 2、物料大类为设备（编码为20）且物料编码开头是（61或78）的行，供应商地点仅能选择固定资产；其他选材料或寄售(20201019添加)
     * 3、物料大类为服务类/物流（编码为40/60）时，供应商地点仅能选择费用；
     * 4、物料大类编码是70且物料编码开头是（61或78）的物料不可与其他物料同在一个订单，供应商地点仅能选择固定资产，其他仅能选择费用
     *
     * 可选的供应商地点为以上的并集
     * @param siteInfoQueryDTO
     * @return
     */
    @Override
    public List<SiteInfo> listSiteInfoForOrder(SiteInfoQueryDTO siteInfoQueryDTO) {

        SiteInfo siteInfo = new SiteInfo();
        BeanUtils.copyProperties(siteInfoQueryDTO, siteInfo);
        /*获取所有指定供应商，指定业务实体下的所有地点*/
        List<SiteInfo> siteInfoList = this.list(new QueryWrapper<>(siteInfo));
        return siteInfoList;
        //2020-12-15 隆基回迁产品 去除供应商地点的过滤
        //过滤所有vendor_site_id为空的数据
//        siteInfoList = siteInfoList.stream().filter(item -> StringUtils.isNotBlank(item.getVendorSiteId())).collect(Collectors.toList());
//        if(CollectionUtils.isEmpty(siteInfoQueryDTO.getMaterialIds())){
//            return Collections.EMPTY_LIST;
//        }else{
//            /*查询所有物料*/
//            List<MaterialItem> materialItemList = baseClient.listMaterialItemsByIds(siteInfoQueryDTO.getMaterialIds());
//            if(CollectionUtils.isEmpty(materialItemList)){
//                return siteInfoList;
//            }
//            /*获取所有物料编码*/
//            List<String> materialCodes = materialItemList.stream().map(item -> item.getMaterialCode().trim()).collect(Collectors.toList());
//            /*查询所有物料大类编码*/
//            List<MaterialMaxCategoryVO> materialItemVoList = baseClient.queryCategoryMaxCodeByMaterialIds(materialItemList.stream().map(item -> item.getMaterialId()).collect(Collectors.toList()));
//            List<String> maxCategoryCodeList = materialItemVoList.stream().map(item -> item.getCategoryCode().trim()).collect(Collectors.toList());
//            List<SiteInfo> repeatSiteInfos = new ArrayList<>();
//            List<SiteInfo> result = null;
//
//            /*物料大类为生产材料/备品备件（编码为10/30）时，供应商地点仅能选择材料或寄售；*/
//            long count1 = maxCategoryCodeList.stream().filter(item -> "10".equals(item) || "30".equals(item)).count();
//            if(count1 > 0){
//                for(SiteInfo item:siteInfoList){
//                    if("材料".equals(item.getVendorSiteCode().trim()) || "寄售".equals(item.getVendorSiteCode().trim())){
//                        repeatSiteInfos.add(item);
//                    }
//                }
//            }
//
//            /*物料大类为设备（编码为20）且物料编码开头是（61或78）的行，供应商地点仅能选择固定资产；其他选材料或寄售*/
//            long count2 = maxCategoryCodeList.stream().filter(item -> "20".equals(item)).count();
//            if(count2 > 0){
//                long count6 = materialItemList.stream().filter(item -> StringUtils.startsWith(item.getMaterialCode(),"61") || StringUtils.startsWith(item.getMaterialCode(),"78")).count();
//                if(count6 == materialItemList.size()){
//                    for(SiteInfo item:siteInfoList){
//                        if("固定资产".equals(item.getVendorSiteCode().trim())){
//                            repeatSiteInfos.add(item);
//                        }
//                    }
//                }else{
//                    for(SiteInfo item:siteInfoList){
//                        if("材料".equals(item.getVendorSiteCode().trim()) || "寄售".equals(item.getVendorSiteCode().trim())){
//                            repeatSiteInfos.add(item);
//                        }
//                    }
//                }
//
//            }
//
//            /*物料大类为服务类/物流（编码为40/60）时，供应商地点仅能选择费用；*/
//            long count3 = maxCategoryCodeList.stream().filter(item -> "40".equals(item) || "60".equals(item)).count();
//            if(count3 > 0){
//                for(SiteInfo item:siteInfoList){
//                    if("费用".equals(item.getVendorSiteCode().trim())){
//                        repeatSiteInfos.add(item);
//                    }
//                }
//            }
//
//            /*物料大类编码是70且物料编码开头是（61或78）的物料不可与其他物料同在一个订单，供应商地点仅能选择固定资产，其他仅能选择费用*/
//            long count4 = maxCategoryCodeList.stream().filter(item -> "70".equals(item)).count();
//            if(count4 > 0){
//                long count5= materialItemList.stream().filter(item -> StringUtils.startsWith(item.getMaterialCode(),"61") || StringUtils.startsWith(item.getMaterialCode(),"78")).count();
//                if(count5 == materialItemList.size()){
//                    for(SiteInfo item:siteInfoList){
//                        if("固定资产".equals(item.getVendorSiteCode().trim())){
//                            repeatSiteInfos.add(item);
//                        }
//                    }
//                }else{
//                    for(SiteInfo item:siteInfoList){
//                        if("费用".equals(item.getVendorSiteCode().trim())){
//                            repeatSiteInfos.add(item);
//                        }
//                        if("材料".equals(item.getVendorSiteCode().trim())){
//                            repeatSiteInfos.add(item);
//                        }
//                    }
//                }
//            }
//
//            /*List去重*/
//            result = repeatSiteInfos.stream().filter(distinctByKey(SiteInfo::getSiteInfoId)).collect(Collectors.toList());
//            return result;
//        }
    }

    public static void main(String[] args) {
        SiteInfo siteInfo1 = new SiteInfo()
                .setSiteInfoId(123L)
                .setSiteComment("2132333");
        SiteInfo siteInfo2 = new SiteInfo()
                .setSiteInfoId(123L)
                .setSiteComment("21233");
        SiteInfo siteInfo3 = new SiteInfo()
                .setSiteInfoId(123L)
                .setSiteComment("2113");
        SiteInfo siteInfo4 = new SiteInfo()
                .setSiteInfoId(123L)
                .setSiteComment("2113");
        SiteInfo siteInfo5 = new SiteInfo()
                .setSiteInfoId(123L)
                .setSiteComment("21333");
        List<SiteInfo> list = new ArrayList();
        list.add(siteInfo1);
        list.add(siteInfo2);
        list.add(siteInfo3);
        list.add(siteInfo4);
        list.add(siteInfo5);
        System.out.println(JsonUtil.arrayToJsonStr(list));
        System.out.println("---");
        List result = list.stream().filter(distinctByKey(SiteInfo::getSiteInfoId)).collect(Collectors.toList());
        /*System.out.println(JsonUtil.arrayToJsonStr(result));*/


        SiteInfo siteInfo11 = new SiteInfo()
                .setSiteInfoId(123L)
                .setVendorSiteId("123")
                .setSiteComment("2132333");
        List<SiteInfo> list1 = new ArrayList();
        list1.add(siteInfo11);
        list1 = list1.stream().filter(item -> StringUtils.isNotBlank(item.getVendorSiteId())).collect(Collectors.toList());
        System.out.println(list1.size());
        System.out.println(JsonUtil.arrayToJsonStr(list1));

    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<SiteInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID", companyId);
        this.remove(wrapper);
    }

    @Override
    public List<SiteInfo> getByCompanyId(Long companyId) {
        QueryWrapper<SiteInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID", companyId);
        List<SiteInfo> siteInfos = companyId != null ? this.list(wrapper) : null;
        return siteInfos;
    }

    @Override
    public SiteInfo getSiteInfoByParm(SiteInfo siteInfo) {
        LambdaQueryWrapper<SiteInfo> queryWrapper = Wrappers.lambdaQuery(SiteInfo.class)
                .eq(siteInfo.getCompanyId() != null, SiteInfo::getCompanyId, siteInfo.getCompanyId())
                .eq(siteInfo.getVendorSiteCode() != null, SiteInfo::getVendorSiteCode, siteInfo.getVendorSiteCode())
                .eq(siteInfo.getBelongOprId() != null, SiteInfo::getBelongOprId, siteInfo.getBelongOprId())
                .eq(siteInfo.getAddressName() != null, SiteInfo::getAddressName, siteInfo.getAddressName())
                .eq(siteInfo.getAddressDetail() != null, SiteInfo::getAddressDetail, siteInfo.getAddressDetail())
                .last("limit 1");
        return getOne(queryWrapper);
    }

    @Override
    public List<SiteInfo> getSiteInfosByParam(SiteInfo siteInfo) {
        QueryWrapper<SiteInfo> queryWrapper = new QueryWrapper<>(siteInfo);
        List<SiteInfo> siteInfos = this.list(queryWrapper);
        return CollectionUtils.isNotEmpty(siteInfos)?siteInfos:null;
    }

    /**
     * 调整地点数据（导数据用，忽略）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adjustSiteErpOrgId() {
        List<SiteInfo> siteInfos = this.list();
        if (CollectionUtils.isNotEmpty(siteInfos)){
            List<SiteInfo> updateSiteInfos = new ArrayList<>();
            for (SiteInfo siteInfo : siteInfos) {
                if (StringUtil.isEmpty(siteInfo.getBelongOprId())
                        || siteInfo.getBelongOprId().length() >= 6
                        || siteInfo.getBelongOprId() == null
                        || siteInfo.getBelongOprId().equals("null")){
                    if (StringUtil.isNotEmpty(siteInfo.getOrgName())){
                        String organizationName = siteInfo.getOrgName();
                        Organization organization = baseClient.getOrganization(new Organization().setOrganizationName(organizationName));
                        if (null != organization){
                            String belongErpOrgId = organization.getErpOrgId();
                            siteInfo.setBelongOprId(belongErpOrgId);
                            updateSiteInfos.add(siteInfo);
                        }
                    }
                }
            }
            this.updateBatchById(updateSiteInfos);
        }
    }


}

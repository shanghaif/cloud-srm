package com.midea.cloud.srm.supcooperate.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.OrgStatus;
import com.midea.cloud.common.enums.order.OrderForecastStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.model.suppliercooperate.orderforecast.entry.OrderForecast;
import com.midea.cloud.srm.supcooperate.plan.mapper.OrderForecastMapper;
import com.midea.cloud.srm.supcooperate.plan.service.IOrderForecastService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  三月滚动预测表
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/14 20:00
 *  修改内容:
 * </pre>
 */
@Service
public class OrderForecastServiceImpl extends ServiceImpl<OrderForecastMapper, OrderForecast> implements IOrderForecastService {
    @Autowired
    OrderForecastMapper orderForecastMapper;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;
    @Override
    public List<ErrorCell> saveBatchByExcel(List<OrderForecast> list) {
        List<ErrorCell> errorCells = new ArrayList<ErrorCell>();

        List<MaterialItem> materialItems = baseClient.listAllMaterialItem();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List<OrganizationUser> organizations = loginAppUser.getOrganizationUsers();

        for(OrderForecast eord:list){
            CompanyInfo companyInfo1 = new CompanyInfo();
            companyInfo1.setCompanyName(eord.getVendorName());
            companyInfo1.setCompanyCode(eord.getVendorCode());
            companyInfo1.setStatus("APPROVED");
            CompanyInfo companyInfo = supplierClient.getCompanyInfoByParam(companyInfo1);
            if(companyInfo==null){
                eord.getErrorCell("vendorName").setComment("不存在名称为：'"+eord.getVendorName()+"'的已批准供应商");
                errorCells.add(eord.getErrorCell("vendorName"));
            }else{
                eord.setVendorId(companyInfo.getCompanyId());
                eord.setVendorCode(companyInfo.getCompanyCode());
                eord.setVendorName(companyInfo.getCompanyName());
            }

            OrganizationUser organization = getOrganization(organizations,eord.getOrganizationName());
            if(organization==null){
                eord.getErrorCell("organizationName").setComment("不存在名称为：'"+eord.getOrganizationName()+"'的有效组织");
                errorCells.add(eord.getErrorCell("organizationName"));
            }else{
                eord.setOrganizationId(organization.getOrganizationId());
            }

            MaterialItem materialItem = getMaterialItem(materialItems,eord);
            if(materialItem==null){
                eord.getErrorCell("lineErrorContents").setComment("不存在编号为：'"+eord.getMaterialCode()+"'," +
                        "名称为：'"+eord.getMaterialName()+"'" +
                        "的物料");
                errorCells.add(eord.getErrorCell("lineErrorContents"));
            }else{
                eord.setMaterialId(materialItem.getMaterialId());
            }
            if(organization!=null&&companyInfo!=null){
                //检验这个供应商有没有这个合作组织
                OrgInfo orgInfo = supplierClient.getOrgInfoByOrgIdAndCompanyId(organization.getOrganizationId(),companyInfo.getCompanyId());
                if(!checkOrgEffective(orgInfo)){
                    eord.getErrorCell("organizationName").setComment("不存在名称为：'"+eord.getOrganizationName()+"'的有效组织");
                    errorCells.add(eord.getErrorCell("organizationName"));
                }
            }

            eord.setOrderForecastId(IdGenrator.generate());

            eord.setStatus(OrderForecastStatus.CREATE.name());

            List ofs = this.checkExist(eord);
            if(!CollectionUtils.isEmpty(ofs)){
                eord.getErrorCell("lineErrorContents").setComment("已存在供应商：'"+eord.getVendorName()+"'," +
                        "采购组织:'"+eord.getOrganizationName()+"'," +
                        "计划月:'"+eord.getPlanMonth()+"'," +
                        "版本号:'"+eord.getPlanVersion()+"'," +
                        "物料编码:'"+eord.getMaterialCode() +
                        "'的数据");
                errorCells.add(eord.getErrorCell("lineErrorContents"));
            }
        }
        if(errorCells.size()>0){
            return errorCells;
        }
        for(OrderForecast eord:list){
            try{
                this.save(eord);
            }catch(Exception e){
                e.printStackTrace();
                eord.getErrorCell("lineErrorContents").setLineErrorContents("该行订单上传失败，请重新处理后再导入");
                errorCells.add(eord.getErrorCell("lineErrorContents"));
            }
        }
        return errorCells;
    }

    /**
     * 检验合作组织的有效性
     * @return
     */
    private static boolean checkOrgEffective(OrgInfo orgInfo)  {
        if(orgInfo == null||orgInfo.getStartDate()==null||!StringUtils.equals(orgInfo.getServiceStatus(), OrgStatus.EFFECTIVE.name())){
            return false;
        }
        LocalDate now = LocalDate.now();
        if(orgInfo.getStartDate().isAfter(now)){
            return false;
        }
        if(orgInfo.getEndDate()!=null&&(now.isAfter(orgInfo.getEndDate())||now.isEqual(orgInfo.getEndDate()))){
            return false;
        }
        return true;
    }
    /**
     * 检查数据是否重复
     * @param orderForecast
     * @return
     */
    private List<OrderForecast> checkExist(OrderForecast orderForecast) {
        OrderForecast query = new OrderForecast();
        query.setVendorId(orderForecast.getVendorId());
        query.setOrganizationId(orderForecast.getOrganizationId());
        query.setPlanMonth(orderForecast.getPlanMonth());
        query.setPlanVersion(orderForecast.getPlanVersion());
        query.setMaterialId(orderForecast.getMaterialId());
        QueryWrapper<OrderForecast> wrapper = new QueryWrapper<OrderForecast>(query);
        return this.list(wrapper);
    }


    @Override
    public void publishBatch(List<Long> ids) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        List list = new ArrayList();

        ids.forEach(item -> {
            OrderForecast checkOrderForecast = orderForecastMapper.selectById(item);
            Assert.notNull(checkOrderForecast, "找不到计划");
            Assert.isTrue(StringUtils.equals(OrderForecastStatus.CREATE.name(),
                    checkOrderForecast.getStatus()), "选择采购商拟定计划");

            OrderForecast orderForecast = new OrderForecast();
            orderForecast.setOrderForecastId(item);
            orderForecast.setStatus(OrderForecastStatus.PUBLISHED.name());
            orderForecast.setPublisherId(loginAppUser.getUserId());
            orderForecast.setPublishBy(loginAppUser.getUsername());
            orderForecast.setPublishTime(new Date());

            list.add(orderForecast);
        });
        this.updateBatchById(list);
    }

    @Override
    public void comfirmBatch(List<Long> ids) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        List list = new ArrayList();

        ids.forEach(item -> {
            OrderForecast checkOrderForecast = orderForecastMapper.selectById(item);
            Assert.notNull(checkOrderForecast, "找不到计划");
            Assert.isTrue(StringUtils.equals(OrderForecastStatus.PUBLISHED.name(),
                    checkOrderForecast.getStatus()), "选择采购商已发布计划");

            OrderForecast orderForecast = new OrderForecast();
            orderForecast.setOrderForecastId(item);
            orderForecast.setStatus(OrderForecastStatus.COMFIRM.name());
            orderForecast.setComfirmId(loginAppUser.getUserId());
            orderForecast.setComfirmBy(loginAppUser.getUsername());
            orderForecast.setComfirmTime(new Date());

            list.add(orderForecast);
        });
        this.updateBatchById(list);
    }

    /**
     * 通过物料属性获取物料Id
     * @param materialItems
     * @param orderForecast
     * @return
     */
    private MaterialItem getMaterialItem(List<MaterialItem> materialItems, OrderForecast orderForecast){
        for(MaterialItem materialItem:materialItems){
            if(StringUtils.equals(orderForecast.getMaterialCode(),materialItem.getMaterialCode())
                    &&StringUtils.equals(orderForecast.getMaterialName(),materialItem.getMaterialName())){
                return materialItem;
            }
        }
        return null;
    }

    /**
     * 通过组织名称获取组织ID
     * @param organizations
     * @param organizationName
     * @return
     */
    private OrganizationUser getOrganization(List<OrganizationUser> organizations, String organizationName) {
        for(OrganizationUser organizationUser:organizations){
            if(StringUtils.equals(organizationName,organizationUser.getOrganizationName())){
                return organizationUser;
            }
        }
        return null;
    }
}

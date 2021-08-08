package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.organization.service.IErpMaterialItemService;
import com.midea.cloud.srm.base.soap.erp.service.IMaterialItemWsService;
import com.midea.cloud.srm.model.base.organization.entity.Category;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.*;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20 17:37
 *  修改内容:
 * </pre>
 */

@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
    endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IMaterialItemWsService")
@Component("iMaterialItemWsService")
public class MaterialItemWsServiceImpl implements IMaterialItemWsService {

    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**物料接口表Service*/
    @Resource
    private IErpMaterialItemService iErpMaterialItemService;

    @Override
    public SoapResponse execute(MaterialItemRequest request) {
        Long startTime = System.currentTimeMillis();
        SoapResponse response = new SoapResponse();
        /**获取instId和requestTime*/
        EsbInfoRequest esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        /**获取物料List,并保存数据*/
        MaterialItemRequest.RequestInfo requestInfo = request.getRequestInfo();
        MaterialItemRequest.RequestInfo.MaterialItems materialItemsClass = null;
        List<MaterialItemEntity> materialItemEntityList = null;
        List<CategorySetsEntity> categorySetsEntityList = null;
        if(null != requestInfo){
            materialItemsClass = requestInfo.getMaterialItems();
            if(null != materialItemsClass){
                materialItemEntityList = materialItemsClass.getMaterialItem();
            }
        }

        log.info("erp获取物料接口数据：" + (null != request? request.toString() : "空"));
        List<ErpMaterialItem> erpMaterialItemsList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(materialItemEntityList)){
            materialItemEntityList = materialItemEntityList.stream().filter(x -> StringUtils.isNotEmpty(x.getItemId())).collect(Collectors.toList());
            for(MaterialItemEntity materialItemEntity : materialItemEntityList){
                if(null != materialItemEntity){
                    ErpMaterialItem erpMaterialItem = new ErpMaterialItem();
                    BeanUtils.copyProperties(materialItemEntity, erpMaterialItem);
                    String itemid = materialItemEntity.getItemId();
                    String shelfLifeDays = materialItemEntity.getShelfLifeDays();

                    erpMaterialItem.setItemId(Long.valueOf(itemid));
                    if (StringUtils.isNotEmpty(shelfLifeDays)){
                        erpMaterialItem.setShelfLifeDays(Long.valueOf(shelfLifeDays));
                    }
                    /**获取物料类别集List,并保存数据*/
                    categorySetsEntityList = materialItemEntity.getCategorySets();
                    log.info("erp获取物料类别集数据：" + (categorySetsEntityList.isEmpty() ? "空" : categorySetsEntityList.toString()));
                    List<Category> categoryList = new ArrayList<>();

                    if(CollectionUtils.isNotEmpty(categorySetsEntityList)) {
                        for(CategorySetsEntity categorySetsEntity : categorySetsEntityList){
                            if(null != categorySetsEntity){
                                Category category = new Category();
                                BeanUtils.copyProperties(categorySetsEntity, category);
                                //将物料类别加到物料类别集里面
                                categoryList.add(category);
                            }
                        }
                    }

                    erpMaterialItem.setCategoryList(categoryList);
                    erpMaterialItemsList.add(erpMaterialItem);
                }
            }
            response = iErpService.saveOrUpdateMaterialItems(erpMaterialItemsList, instId, requestTime);
        }
        log.info("erp物料接口插入数据用时："+(System.currentTimeMillis()-startTime)/1000+"秒");
        return response;
    }
}

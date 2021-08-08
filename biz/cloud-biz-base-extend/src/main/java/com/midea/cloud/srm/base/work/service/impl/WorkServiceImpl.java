//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.midea.cloud.srm.base.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.dynamicds.bind.CheckModuleHolder;
import com.midea.cloud.dynamicds.enums.Module;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.base.work.mapper.WorkCountMapper;
import com.midea.cloud.srm.base.work.mapper.WorkMapper;
import com.midea.cloud.srm.base.work.service.ICeeaBaseWorkSortService;
import com.midea.cloud.srm.base.work.service.IWorkService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.bid.BidClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.feign.supplierauth.SupplierAuthClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemQueryDTO;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.base.work.dto.WorkCountQueryDTO;
import com.midea.cloud.srm.model.base.work.dto.WorkRequestDTO;
import com.midea.cloud.srm.model.base.work.entity.CeeaBaseWorkSort;
import com.midea.cloud.srm.model.base.work.entry.Work;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.ManagementAttachRequestDTO;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements IWorkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkServiceImpl.class);
    @Autowired
    private SupcooperateClient supcooperateClient;
    @Autowired
    private RbacClient rbacClient;
    @Autowired
    private InqClient inqClient;
    @Autowired
    private BidClient bidClient;
    @Autowired
    private SupplierAuthClient supplierAuthClient;
    @Autowired
    private ICeeaBaseWorkSortService ceeaBaseWorkSortService;
    @Autowired
    private WorkCountMapper workCountMapper;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private BaseClient baseClient;

    public WorkServiceImpl() {
    }

    public List findList(WorkRequestDTO workRequestDTO) {
        return ((WorkMapper)this.getBaseMapper()).findList(workRequestDTO);
    }

    public List<WorkCount> workCount() {
        try {
            LoginAppUser loginAppUser = this.rbacClient.getUser();
            Long vendorId = AppUserUtil.getLoginAppUser().getCompanyId();
            String vendorCode = AppUserUtil.getLoginAppUser().getCompanyCode();
            Assert.notNull(loginAppUser, "账号有误");
            if (loginAppUser.getMenus() == null) {
                return new ArrayList();
            } else {
                ArrayList list = new ArrayList();

                // 取出我的任务排序列表
                QueryWrapper<CeeaBaseWorkSort> workSortQueryWrapper = new QueryWrapper<>();
                workSortQueryWrapper.eq("USER_ID", loginAppUser.getUserId());
                List<CeeaBaseWorkSort> baseWorkSortList = ceeaBaseWorkSortService.list(workSortQueryWrapper);
                Map<String, Integer> sortMap = baseWorkSortList.stream()
                        .collect(Collectors.toMap(v -> v.getListName(), v -> v.getSort(), (v1, v2) -> v2));

                // 查询具体业务单据数据
                WorkCountQueryDTO countQueryDTO = new WorkCountQueryDTO();
                countQueryDTO.setVendorCode(vendorCode);
                countQueryDTO.setVendorId(vendorId);
                // 绩效模块
                CheckModuleHolder.checkout(Module.PERF);
                {
                    // 绩效改善
                    countQueryDTO.setStatus("IMPROVING");
                    try {
                        Map<String, String> codition = new HashMap<>();
                        codition.put("status", "IMPROVING");
                        Integer count = workCountMapper.countVendorImprovement(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("供应商改善单")
                                .setUrl("/performanceManagement/vendorImprovement?from=workCount")
                                .setCount(count)
                                .setListName("vendorImprovement")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "vendorImprovement"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询供应商改善单报错：" , e);
                    }
                    // 绩效考核
                    countQueryDTO.setStatus("IN_FEEDBACK");
                    try {
                        Map<String, String> codition = new HashMap<>();
                        codition.put("status", "IN_FEEDBACK");
                        Integer count = workCountMapper.countPerformanceAssessment(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("绩效考核单")
                                .setUrl("/performanceManagement/performanceAssessment?from=workCount")
                                .setCount(count)
                                .setListName("performanceAssessment")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "performanceAssessment"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询绩效考核单报错：" , e);
                    }
                }
                // 招投标模块
                CheckModuleHolder.checkout(Module.BID);
                {
                    // 招投标-项目管理
                    try {
                        Map<String, String> codition = new HashMap<>();
                        codition.put("bidingStatus", "ACCEPT_BID");
                        Integer count = workCountMapper.coutVendorBidding(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("招投标项目管理列表")
                                .setUrl("/vendorBiddingManagement/vendorBiddingList?from=workCount")
                                .setCount(count)
                                .setListName("vendorBidding")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "vendorBidding"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询招投标项目管理列表报错：" ,e);
                    }
                }
                // 询比价模块
                CheckModuleHolder.checkout(Module.BARGAIN);
                {
                    // 询价单
                    try {
                        Map<String, String> codition = new HashMap<>();
                        codition.put("bidingStatus", "ACCEPT_BID");
                        codition.put("orderStatus", "DRAFT");
                        Integer count = workCountMapper.coutInquiryOrders(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("询价单")
                                .setUrl("/queryComparePriceSynergy/vendorBiddingList_new?from=workCount")
                                .setCount(count)
                                .setListName("inquiryOrders")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "inquiryOrders"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询询价单报错：" , e);
                    }
                }
                //SUP模块
                CheckModuleHolder.checkout(Module.SUP);
                //证件到期提醒
                {
                    String day =null;
                    int isDeadLine=0;
                    //获取字典编码里面的到期编码id的条目名称的天数
                    DictItemQueryDTO dto =new DictItemQueryDTO();
                    dto.setDictCode("CERTIFICATE EXPIRY REMINDER");
                    Map<String,String> map =new HashMap<>();
                    map =baseClient.getDictItemsByDictCodeAndDictItemNames(dto).stream()
                            .collect(Collectors.toMap(DictItemDTO::getDictItemCode,DictItemDTO::getDictItemName));
                    day =map.get("01");//dictItemName 到期天数前
                    //营业证件到期
                    {
                        //获取供应商的营业到期时间
                        LocalDate bussinessEndDate =null;
                        if(vendorId != null) {
                            bussinessEndDate =supplierClient.getCompanyInfo(vendorId).getBusinessEndDate();
                        }
                        //比对字典到期时间
                        if(day != null && bussinessEndDate != null) {
                            Long days =Long.parseLong(day);
                            if(!LocalDate.now().plusDays(days).isBefore(bussinessEndDate) && !LocalDate.now().isAfter(bussinessEndDate)) {
                                isDeadLine++;
                            }

                        }
                    }
                    //认证附件到期
                    {
                        ManagementAttach managementAttach =new ManagementAttach();
                        if(vendorId != null) {
                            managementAttach.setCompanyId(vendorId);
                            //获取认证附件信息
                            List<ManagementAttach> managementAttaches=supplierClient.listAllManagementAttachPageByDTO(managementAttach);
                            LocalDate bussinessEndDate =null;
                            //比对字典到期时间
                            for(ManagementAttach managementAttachFile : managementAttaches) {
                                bussinessEndDate =managementAttachFile.getEndDate();
                                if(day != null && bussinessEndDate != null) {
                                    Long days =Long.parseLong(day);
                                    if(!LocalDate.now().plusDays(days).isBefore(bussinessEndDate) && !LocalDate.now().isAfter(bussinessEndDate)) {
                                        isDeadLine++;
                                    }
                                }
                            }
                        }
                    }
                    //设置属性
                    if (isDeadLine != 0) {
                        try {
                            WorkCount workCount = new WorkCount();
                            Integer count =new Integer(isDeadLine);
                            workCount.setTitle("营业/认证附件到期提醒")
                                    .setUrl("/vendorManagement/vendorInfoChange?from=workCount")
                                    .setCount(count)
                                    .setListName("vendorInfoDeadLine")
                                    .setSort(this.getListSort(sortMap, "vendorInfoDeadLine"));
                            list.add(workCount);
                        } catch (Exception e) {
                            LOGGER.error("报错：" , e);
                        }
                    }

                }
                // 供应商协同模块
                CheckModuleHolder.checkout(Module.SUPCOOPERATE);
                {
                    // 采购订单
                    try {
                        countQueryDTO.setStatus("APPROVED");
                        Map<String, String> codition = new HashMap<>();
                        codition.put("orderStatus", "APPROVED");// 已审批
                        codition.put("ceeaIfSupplierConfirm", "Y");// 是否供方已确认为是
                        Integer count = workCountMapper.coutPurchaseOrder(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("采购订单")
                                .setUrl("/vendorOrderSynergy/vendorPurchaseOrder?from=workCount")
                                .setCount(count)
                                .setListName("purchaseOrder")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "purchaseOrder"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询采购订单报错：", e);
                    }
                    // 送货单
                    try {
                        countQueryDTO.setStatus("CREATE");
                        Map<String, String> codition = new HashMap<>();
                        codition.put("deliveryNoteStatus", "CREATE");// 新建
                        Integer count = workCountMapper.coutDeliveryOrder(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("送货单")
                                .setUrl("/vendorOrderSynergy/vendorDeliveryOrder?from=workCount")
                                .setCount(count)
                                .setListName("deliveryOrder")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "deliveryOrder"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询送货单报错：" ,e);
                    }
                    // 到货计划列表
                    try {
                        countQueryDTO.setStatus("APPROVAL");
                        Map<String, String> codition = new HashMap<>();
                        codition.put("deliverPlanStatus", "APPROVAL");// 已发布
                        Integer count = workCountMapper.coutDeliverPlan(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("到货计划列表")
                                .setUrl("/vendorOrderSynergy/vendorDeliverPlan?from=workCount")
                                .setCount(count)
                                .setListName("deliverPlan")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "deliverPlan"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询到货计划列表报错：" ,e);
                    }
                    // 开票通知
                    try {
                        countQueryDTO.setStatus("SUBMITTED");
                        Map<String, String> codition = new HashMap<>();
                        codition.put("invoiceNoticeStatus", "SUBMITTED");// 已提交
                        Integer count = workCountMapper.coutInvoice(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("开票通知")
                                .setUrl("/purSettlement/purInvoice?from=workCount")
                                .setCount(count)
                                .setListName("invoice")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "invoice"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询开票通知报错：" , e);
                    }
                    // 供应商网上开票
                    try {
                        countQueryDTO.setStatus("REJECTED");
                        Map<String, String> codition = new HashMap<>();
                        codition.put("invoiceStatus", "REJECTED");// 已驳回
                        Integer count = workCountMapper.coutOnlineInvoice(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("供应商网上开票")
                                .setUrl("/purSettlement/supOnlineInvoice?from=workCount")
                                .setCount(count)
                                .setListName("OnlineInvoice")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "OnlineInvoice"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询供应商网上开票报错：" , e);
                    }
                }
                // 合同模块
                CheckModuleHolder.checkout(Module.CONTRACT);
                {
                    // 供应商验收协同
                    try {
                        countQueryDTO.setStatus("REJECT");
                        Map<String, String> codition = new HashMap<>();
                        codition.put("acceptStatus", "REJECT");// 已驳回
                        Integer count = workCountMapper.coutInspectionBill(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("供应商验收协同单")
                                .setUrl("/contractManagement/supInspectionBillList?from=workCount")
                                .setCount(count)
                                .setListName("inspectionBill")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "inspectionBill"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询供应商验收协同单报错：" , e);
                    }
                    // 合同管理
                    try {
                        countQueryDTO.setStatus("SUPPLIER_CONFIRMING");
                        Map<String, String> codition = new HashMap<>();
                        codition.put("contractStatus", "SUPPLIER_CONFIRMING");// 待供应商确认
                        Integer count = workCountMapper.coutContract(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("合同管理单")
                                .setUrl("/contractManagement/contractMaintainList?from=workCount")
                                .setCount(count)
                                .setListName("contract")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "contract"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询合同管理单报错：" ,e);
                    }
                }
                // 基础模块
                CheckModuleHolder.checkout(Module.BASE);
                {
                    // 物料维护
                    try {
                        countQueryDTO.setStatus("NOTIFIED");
                        Map<String, String> codition = new HashMap<>();
                        codition.put("ceeaMaterialStatus", "NOTIFIED");// 已通知
                        Integer count = workCountMapper.coutMaterialMaintenance(countQueryDTO);
                        WorkCount workCount = new WorkCount();
                        workCount.setTitle("物料维护")
                                .setUrl("/baseSetting/materialMaintenance?from=workCount")
                                .setCount(count)
                                .setListName("materialMaintenance")
                                .setCondition(codition)
                                .setSort(this.getListSort(sortMap, "materialMaintenance"));
                        list.add(workCount);
                    } catch (Exception e) {
                        LOGGER.error("查询物料维护报错：" , e);
                    }
                }

            /*try {
                list.add(this.supcooperateClient.orderCountSubmit()
                        .setListName("order")// 设置列表名称
                        .setSort(this.getListSort(sortMap, "order")));// 设置排序
            } catch (Exception var19) {
                LOGGER.error("查询待确认订单时报错：" + var19.toString());
            }

            try {
                list.add(this.supcooperateClient.deliveryNoteCountCreate()
                        .setListName("delivery")
                        .setSort(this.getListSort(sortMap, "delivery")));
            } catch (Exception var18) {
                LOGGER.error("查询创建送货单时报错：" + var18.toString());
            }

            try {
                list.add(this.supplierAuthClient.quaSampleCountConfirmed()
                        .setListName("quaSample")
                        .setSort(this.getListSort(sortMap, "quaSample")));
            } catch (Exception var17) {
                LOGGER.error("查询样品确认的待确认单时报错：" + var17.toString());
            }

            try {
                list.add(this.supplierAuthClient.materialTrialCountConfirmed()
                        .setListName("supplier")
                        .setSort(this.getListSort(sortMap, "supplier")));
            } catch (Exception var16) {
                LOGGER.error("查询物料试用的待确认单时报错：" + var16.toString());
            }

            try {
                list.add(this.bidClient.supplierBidingCountCreate()
                        .setListName("bid")
                        .setSort(this.getListSort(sortMap, "bid")));
            } catch (Exception var15) {
                LOGGER.error("查询待投标统计时报错：" + var15.toString());
            }

            try {
                list.add(this.inqClient.waitQuoteCount()
                        .setListName("inq")
                        .setSort(this.getListSort(sortMap, "inq")));
            } catch (Exception var14) {
                LOGGER.error("查询待报价数量时报错：" + var14.toString());
            }*/

                List<WorkCount> resultList = new ArrayList();
                Iterator var4 = list.iterator();

                while(var4.hasNext()) {
                    WorkCount workCount = (WorkCount)var4.next();
                    Iterator var6 = loginAppUser.getMenus().iterator();

                    while(var6.hasNext()) {
                        Permission permission = (Permission)var6.next();
                        boolean have = false;
                        Iterator var9 = permission.getChildPermissions().iterator();

                        while(var9.hasNext()) {
                            Permission sonPermission = (Permission)var9.next();
                            if (workCount.getUrl().contains(sonPermission.getFunctionAddress() + "?")) {
                                String[] arr = workCount.getUrl().split("/");
                                StringBuffer sb = new StringBuffer();

                                for(int i = 2; i < arr.length; ++i) {
                                    sb.append(arr[i]).append("/");
                                }

                                sb.deleteCharAt(sb.length() - 1);
                                workCount.setUrl(sb.toString());
                                resultList.add(workCount);
                                have = true;
                                break;
                            }
                        }

                        if (have) {
                            break;
                        }
                    }
                }

                return resultList.stream().sorted(Comparator.comparing(WorkCount::getSort)).collect(Collectors.toList());
            }
        } finally {
            CheckModuleHolder.release();
        }

    }

    /*** 获取列表排序 ***/
    private Integer getListSort(Map<String, Integer> sortMap, String listName) {
        if (sortMap == null) {
            return 0;
        }
        return sortMap.get(listName)== null ? 0:sortMap.get(listName);
    }
}

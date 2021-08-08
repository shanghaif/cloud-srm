package com.midea.cloud.srm.perf.itemexceptionhandle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.dto.QuaItemEHeaderQueryDTO;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.entity.QuaEPNgDesc;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.entity.QuaItemEHeader;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.entity.QuaItemNgDesc;
import com.midea.cloud.srm.model.perf.itemexceptionhandle.entity.QuaProblemComments;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dProblem;
import com.midea.cloud.srm.model.perf.report8d.entity.Qua8dReport;
import com.midea.cloud.srm.perf.itemexceptionhandle.mapper.QuaItemEHeaderMapper;
import com.midea.cloud.srm.perf.itemexceptionhandle.service.QuaEPNgDescService;
import com.midea.cloud.srm.perf.itemexceptionhandle.service.QuaItemEHeaderService;
import com.midea.cloud.srm.perf.itemexceptionhandle.service.QuaItemNgDescService;
import com.midea.cloud.srm.perf.itemexceptionhandle.service.QuaProblemCommentsService;
import com.midea.cloud.srm.perf.report8d.service.Qua8dProblemService;
import com.midea.cloud.srm.perf.report8d.service.Qua8dReportService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class QuaItemEHeaderServiceImpl extends ServiceImpl<QuaItemEHeaderMapper, QuaItemEHeader> implements QuaItemEHeaderService {
    @Autowired
    private QuaItemEHeaderService quaItemEHeaderService;
    @Autowired
    private QuaItemNgDescService quaItemNgDescService;
    @Autowired
    private QuaEPNgDescService quaEPNgDescService;
    @Autowired
    private QuaProblemCommentsService quaProblemCommentsService;
    @Autowired
    private Qua8dReportService qua8dReportService;
    @Autowired
    private Qua8dProblemService qua8dProblemService;
    /**
     * 查询全部来料异常处理单
     * @return
     */
    @Override
    public List<QuaItemEHeaderQueryDTO> listAll() {
        List<QuaItemEHeaderQueryDTO> itemEHeaderDTOList = new ArrayList<QuaItemEHeaderQueryDTO>();
        List<QuaItemEHeader> quaItemEHeaderList = quaItemEHeaderService.list();
        QuaItemEHeaderQueryDTO quaItemEHeaderQueryDTO = null;
        if (null != quaItemEHeaderList && quaItemEHeaderList.size() > 0) {
            for (QuaItemEHeader h : quaItemEHeaderList) {
                quaItemEHeaderQueryDTO = new QuaItemEHeaderQueryDTO();
                BeanUtils.copyProperties(h, quaItemEHeaderQueryDTO);
                itemEHeaderDTOList.add(quaItemEHeaderQueryDTO);
            }
        }
        return itemEHeaderDTOList;
    }

    /**
     * 分页查询全部来料异常处理单
     * @param
     */
    @Override
    public PageInfo<QuaItemEHeaderQueryDTO> listPage(QuaItemEHeader quaItemEHeader) {
        PageUtil.startPage(quaItemEHeader.getPageNum(),quaItemEHeader.getPageSize());
        List<QuaItemEHeader> quaItemEHeaderList = getQuaItemEHeaders(quaItemEHeader);

        List<QuaItemEHeaderQueryDTO> itemEHeaderDTOList = new ArrayList<QuaItemEHeaderQueryDTO>();
        QuaItemEHeaderQueryDTO quaItemEHeaderQueryDTO = null;
        if (null != quaItemEHeaderList && quaItemEHeaderList.size() > 0) {
            for (QuaItemEHeader h : quaItemEHeaderList) {
                quaItemEHeaderQueryDTO = new QuaItemEHeaderQueryDTO();
                BeanUtils.copyProperties(h, quaItemEHeaderQueryDTO);
                itemEHeaderDTOList.add(quaItemEHeaderQueryDTO);
            }
        }
        return new PageInfo<>(itemEHeaderDTOList);
    }

    public List<QuaItemEHeader> getQuaItemEHeaders(QuaItemEHeader quaItemEHeader) {
        String vendorName=quaItemEHeader.getVendorName();
        Long itemExceptionHeadId = quaItemEHeader.getItemExceptionHeadId();
        String report8D = quaItemEHeader.getReport8D();
        Date creationDate = quaItemEHeader.getCreationDate();

        if (!StringUtil.isEmpty(quaItemEHeader.getVendorName())) {
            vendorName = quaItemEHeader.getVendorName();
            quaItemEHeader.setVendorName(null);
        }

        if (null != quaItemEHeader.getItemExceptionHeadId()) {
            itemExceptionHeadId = quaItemEHeader.getItemExceptionHeadId();
            quaItemEHeader.setItemExceptionHeadId(null);
        }

        if (!StringUtil.isEmpty(quaItemEHeader.getReport8D())) {
            report8D = quaItemEHeader.getReport8D();
            quaItemEHeader.setReport8D(null);
        }

        if (null != quaItemEHeader.getCreationDate()) {
            creationDate = quaItemEHeader.getCreationDate();
            quaItemEHeader.setCreationDate(null);
        }

        QueryWrapper<QuaItemEHeader> wrapper = new QueryWrapper<>(quaItemEHeader);

        if (!StringUtil.isEmpty(vendorName)){
            wrapper.eq("VENDOR_NAME", vendorName);
        }

        if (itemExceptionHeadId != null){
            wrapper.eq("ITEM_EXCEPTION_HEAD_ID", itemExceptionHeadId);
        }

        if (!StringUtil.isEmpty(report8D)){
            wrapper.eq("REPORT_8D", report8D);
        }

        if (null != creationDate) {
            wrapper.ge("CREATION_DATE", creationDate);
        }

        return this.list(wrapper);
    }


    /**
     * 点击单号获取信息
     * @param
     */
    @Override
    public QuaItemEHeader get(Long itemExceptionHeadId) {
        Assert.notNull(itemExceptionHeadId, "参数:itemExceptionHeadId,不能为空");
        QuaItemEHeader quaItemEHeader = this.getById(itemExceptionHeadId);
        Assert.notNull(quaItemEHeader,"找不到配额详情,itemExceptionHeadId="+itemExceptionHeadId);
        List<QuaItemNgDesc> quaItemNgDescList = quaItemNgDescService.list(new QueryWrapper<>(new QuaItemNgDesc().setItemExceptionHeadId(itemExceptionHeadId)));
        List<QuaEPNgDesc> quaEPNgDescList = quaEPNgDescService.list(new QueryWrapper<>(new QuaEPNgDesc().setItemExceptionHeadId(itemExceptionHeadId)));
        List<QuaProblemComments> quaProblemCommentsList = quaProblemCommentsService.list(new QueryWrapper<>(new QuaProblemComments().setItemExceptionHeadId(itemExceptionHeadId)));
        quaItemEHeader.setQuaItemNgDescList(quaItemNgDescList);
        quaItemEHeader.setQuaEPNgDescList(quaEPNgDescList);
        quaItemEHeader.setQuaProblemCommentsList(quaProblemCommentsList);
        return quaItemEHeader;
    }

    /**
     * 增加或更新来料异常处理单
     * @param
     */
    @Override
    @Transactional
    public Long addOrUpdate(QuaItemEHeader quaItemEHeader) {
        Long itemExceptionHeadId = quaItemEHeader.getItemExceptionHeadId();
        if(StringUtil.isEmpty(itemExceptionHeadId)){
            quaItemEHeader.setItemExceptionHeadId(IdGenrator.generate());
            this.save(quaItemEHeader);
        }else {
            this.updateById(quaItemEHeader);
        }
        // 保存材料不合格描述行
        quaItemNgDescService.remove(new QueryWrapper<>(new QuaItemNgDesc().setItemExceptionHeadId(itemExceptionHeadId)));
        List<QuaItemNgDesc> quaItemNgDescList = quaItemEHeader.getQuaItemNgDescList();
        if(CollectionUtils.isNotEmpty(quaItemNgDescList)){
            quaItemNgDescList.forEach(quaItemNgDesc -> {
                quaItemNgDesc.setItemExceptionHeadId(quaItemEHeader.getItemExceptionHeadId());
                quaItemNgDesc.setItemNgDescLineId(IdGenrator.generate());
            });
            quaItemNgDescService.saveBatch(quaItemNgDescList);
        }

        // 保存环保不合格描述行
        quaEPNgDescService.remove(new QueryWrapper<>(new QuaEPNgDesc().setItemExceptionHeadId(itemExceptionHeadId)));
        List<QuaEPNgDesc> quaEPNgDescList = quaItemEHeader.getQuaEPNgDescList();
        if(CollectionUtils.isNotEmpty(quaEPNgDescList)){
            quaEPNgDescList.forEach(quaEPNgDesc -> {
                quaEPNgDesc.setItemExceptionHeadId(quaItemEHeader.getItemExceptionHeadId());
                quaEPNgDesc.setEpNgDescLineId(IdGenrator.generate());
            });
            quaEPNgDescService.saveBatch(quaEPNgDescList);
        }

        // 保存问题备注行
        quaProblemCommentsService.remove(new QueryWrapper<>(new QuaProblemComments().setItemExceptionHeadId(itemExceptionHeadId)));
        List<QuaProblemComments> quaProblemCommentsList = quaItemEHeader.getQuaProblemCommentsList();
        if(CollectionUtils.isNotEmpty(quaProblemCommentsList)){
            quaProblemCommentsList.forEach(quaProblemComments -> {
                quaProblemComments.setItemExceptionHeadId(quaItemEHeader.getItemExceptionHeadId());
                quaProblemComments.setProblemCommentsLineId(IdGenrator.generate());
            });
            quaProblemCommentsService.saveBatch(quaProblemCommentsList);
        }

        return quaItemEHeader.getItemExceptionHeadId();
    }

    @Override
    @Transactional
    public Long add8D(Qua8dReport qua8dReport) {
        Long reportId = qua8dReport.getReportId();
        qua8dReport.setReportId(IdGenrator.generate());
        qua8dReportService.save(qua8dReport);
        // 保存问题描述行
        qua8dProblemService.remove(new QueryWrapper<>(new Qua8dProblem().setReportId(reportId)));
        List<Qua8dProblem> qua8dProblemList = qua8dReport.getQua8DProblemList();
        if(CollectionUtils.isNotEmpty(qua8dProblemList)){
            qua8dProblemList.forEach(qua8dProblem -> {
                qua8dProblem.setReportId(qua8dReport.getReportId());
                qua8dProblem.setProblemId(IdGenrator.generate());
            });
            qua8dProblemService.saveBatch(qua8dProblemList);
        }
        return qua8dReport.getReportId();
    }

}

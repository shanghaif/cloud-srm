package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.neworder.DeliveryAppointStatus;
import com.midea.cloud.common.enums.neworder.DeliveryNoticeStatus;
import com.midea.cloud.common.enums.order.DeliveryApponintStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointVO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppointVisitor;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.supcooperate.order.mapper.AppointDeliveryNoteMapper;
import com.midea.cloud.srm.supcooperate.order.mapper.DeliveryAppointMapper;
import com.midea.cloud.srm.supcooperate.order.mapper.DeliveryAppointVisitorMapper;
import com.midea.cloud.srm.supcooperate.order.service.IAppointDeliveryNoteService;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryAppointService;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryAppointVisitorService;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 *  送货预约表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 13:58
 *  修改内容:
 * </pre>
 */
@Service
public class DeliveryAppointServiceImpl extends ServiceImpl<DeliveryAppointMapper, DeliveryAppoint> implements IDeliveryAppointService {
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private DeliveryAppointMapper deliveryAppointMapper;
    @Autowired
    private IDeliveryAppointVisitorService iDeliveryAppointVisitorService;
    @Autowired
    private IAppointDeliveryNoteService iAppointDeliveryNoteService;
    @Autowired
    private AppointDeliveryNoteMapper appointDeliveryNoteMapper;
    @Autowired
    private DeliveryAppointVisitorMapper deliveryAppointVisitorMapper;
    @Resource
    private IDeliveryNoteService deliveryNoteService;

    @Transactional
    @Override
    public void saveOrUpdate(String opt,DeliveryAppoint deliveryAppoint, List<DeliveryAppointVisitor> deliveryAppointVisitors, List<AppointDeliveryNote> appointDeliveryNotes) {
        if(StringUtils.equals("add",opt)){
            Long id = IdGenrator.generate();

            deliveryAppoint.setDeliveryAppointId(id);
            deliveryAppoint.setDeliveryAppointNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_DELIVERY_APPOINT_NUM));
            deliveryAppoint.setDeliveryAppointStatus(DeliveryApponintStatus.CREATE.name());
        }else{
            DeliveryAppoint checkDeliveryAppoint = deliveryAppointMapper.selectById(deliveryAppoint.getDeliveryAppointId());
            Assert.notNull(checkDeliveryAppoint, "送货预约单不存在");

            if(!StringUtils.equals(DeliveryApponintStatus.CREATE.name(), checkDeliveryAppoint.getDeliveryAppointStatus())
                    &&!StringUtils.equals(DeliveryApponintStatus.REFUSE.name(), checkDeliveryAppoint.getDeliveryAppointStatus())){
                Assert.isTrue(false, "不是待提交或已驳回预约单不能"+(StringUtils.equals(DeliveryApponintStatus.SUBMIT.name(),opt)?"提交":"修改"));
            }
            if(StringUtils.equals(DeliveryApponintStatus.SUBMIT.name(),opt)){
                deliveryAppoint.setDeliveryAppointStatus(DeliveryApponintStatus.SUBMIT.name());
//                deliveryAppoint.se
            }else{//拒绝后重新发起，状态由拒绝变换为创建
                deliveryAppoint.setDeliveryAppointStatus(DeliveryApponintStatus.CREATE.name());
            }

            QueryWrapper<DeliveryAppointVisitor> deliveryAppointVisitorQueryWrapper = new QueryWrapper<>();
            deliveryAppointVisitorQueryWrapper.eq("DELIVERY_APPOINT_ID", deliveryAppoint.getDeliveryAppointId());
            deliveryAppointVisitorMapper.delete(deliveryAppointVisitorQueryWrapper);

            QueryWrapper<AppointDeliveryNote> appointDeliveryNoteQueryWrapper = new QueryWrapper<>();
            appointDeliveryNoteQueryWrapper.eq("DELIVERY_APPOINT_ID", deliveryAppoint.getDeliveryAppointId());
            appointDeliveryNoteMapper.delete(appointDeliveryNoteQueryWrapper);
        }
        this.saveOrUpdate(deliveryAppoint);

        deliveryAppointVisitors.forEach(item -> {
            item.setDeliveryAppointVisitorId(IdGenrator.generate());
            item.setDeliveryAppointId(deliveryAppoint.getDeliveryAppointId());
        });

        iDeliveryAppointVisitorService.saveBatch(deliveryAppointVisitors);

        appointDeliveryNotes.forEach(item->{
            item.setAppointDeliveryNoteId(IdGenrator.generate());
            item.setDeliveryAppointId(deliveryAppoint.getDeliveryAppointId());
        });
        iAppointDeliveryNoteService.saveBatch(appointDeliveryNotes);
    }

    @Override
    public List<DeliveryAppoint> listPage(DeliveryAppointRequestDTO deliveryAppointRequestDTO) {
        return deliveryAppointMapper.findList(deliveryAppointRequestDTO);
    }

    @Override
    public DeliveryAppointVO getDeliveryAppointById(Long deliveryAppointId) {

        //查询送货预约头
        DeliveryAppoint deliveryAppoint = this.getById(deliveryAppointId);
        //查询到访人员
        List<DeliveryAppointVisitor> visitorList = iDeliveryAppointVisitorService.list(new QueryWrapper<DeliveryAppointVisitor>(new DeliveryAppointVisitor().setDeliveryAppointId(deliveryAppointId)));
        //查询送货单
        List<AppointDeliveryNote> appointDeliveryNoteList = iAppointDeliveryNoteService.getByDeliveryAppointId(deliveryAppointId);

        return new DeliveryAppointVO().setDeliveryAppoint(deliveryAppoint)
                .setAppointDeliveryNotes(appointDeliveryNoteList)
                .setVisitors(visitorList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitBatch(List<Long> ids) {
        //校验
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        for(Long id : ids){
            DeliveryAppointDetailDTO detail = detail(id);
            DeliveryAppointSaveRequestDTO request = transfer(detail);
            checkIfSubmit(request);
        }
        //校验状态
        List<DeliveryAppoint> deliveryAppointList = this.listByIds(ids);
        for(DeliveryAppoint deliveryAppoint : deliveryAppointList){
            if(!DeliveryAppointStatus.DRAFT.getValue().equals(deliveryAppoint.getDeliveryAppointStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("[deliveryAppointNumber=%s]状态不为拟定状态,不可提交",deliveryAppoint.getDeliveryAppointNumber())));
            }
        }
        //批量修改数据
        for(DeliveryAppoint deliveryAppoint : deliveryAppointList){
            deliveryAppoint.setDeliveryAppointStatus(DeliveryAppointStatus.WAITING_CONFIRM.getValue());
        }
        this.updateBatchById(deliveryAppointList);
    }






    /**
     * 暂存
     * @param requestDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long temporarySave(DeliveryAppointSaveRequestDTO requestDTO) {
        //校验
        checkIfTemporarySave(requestDTO);
        DeliveryAppoint deliveryAppoint = requestDTO.getDeliveryAppoint();
        if(Objects.isNull(deliveryAppoint.getDeliveryAppointId())){
            //新增
            return add(requestDTO);
        }else{
            //修改
            requestDTO.getDeliveryAppoint().setDeliveryAppointStatus(DeliveryAppointStatus.DRAFT.getValue());
            return update(requestDTO);
        }

    }

    /**
     * 校验是否可暂存
     * @param requestDTO
     */
    private void checkIfTemporarySave(DeliveryAppointSaveRequestDTO requestDTO){
        DeliveryAppoint deliveryAppoint = requestDTO.getDeliveryAppoint();
        List<AppointDeliveryNote> appointDeliveryNotes = requestDTO.getAppointDeliveryNotes();
        List<DeliveryAppointVisitor> deliveryAppointVisitors = requestDTO.getDeliveryAppointVisitors();
        if(StringUtils.isNotBlank(deliveryAppoint.getDeliveryAppointStatus())){
            if(!DeliveryNoticeStatus.DRAFT.getValue().equals(deliveryAppoint.getDeliveryAppointStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg("只有拟定状态的送货预约单才能暂存"));
            }
        }
    }

    /**
     * 提交
     * @param requestDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submit(DeliveryAppointSaveRequestDTO requestDTO) {
        //校验
        checkIfSubmit(requestDTO);

        DeliveryAppoint deliveryAppoint = requestDTO.getDeliveryAppoint();
        if(Objects.isNull(deliveryAppoint.getDeliveryAppointId())){
            //新增
            deliveryAppoint.setDeliveryAppointStatus(DeliveryAppointStatus.WAITING_CONFIRM.getValue());
            return add(requestDTO);
        }else{
            //修改
            deliveryAppoint.setDeliveryAppointStatus(DeliveryAppointStatus.WAITING_CONFIRM.getValue());
            return update(requestDTO);
        }
    }

    /**
     * 校验是否可提交
     * @param requestDTO
     */
    private void checkIfSubmit(DeliveryAppointSaveRequestDTO requestDTO){
        DeliveryAppoint deliveryAppoint = requestDTO.getDeliveryAppoint();
        List<DeliveryAppointVisitor> deliveryAppointVisitors = requestDTO.getDeliveryAppointVisitors();
        List<AppointDeliveryNote> appointDeliveryNotes = requestDTO.getAppointDeliveryNotes();

        //头信息校验
        if(StringUtils.isBlank(deliveryAppoint.getVendorCode())){
            throw new BaseException(LocaleHandler.getLocaleMsg("供应商编码不可为空"));
        }
        if(StringUtils.isBlank(deliveryAppoint.getOrgCode())){
            throw new BaseException(LocaleHandler.getLocaleMsg("业务实体编码不可为空"));
        }
        if(StringUtils.isBlank(deliveryAppoint.getReceiveAddress())){
            throw new BaseException(LocaleHandler.getLocaleMsg("收货地址不可为空"));
        }
        if(StringUtils.isBlank(deliveryAppoint.getReceiveOrderAddress())){
            throw new BaseException(LocaleHandler.getLocaleMsg("收单地址不可为空"));
        }
        if(StringUtils.isBlank(deliveryAppoint.getRespondents())){
            throw new BaseException(LocaleHandler.getLocaleMsg("受访人员不可为空"));
        }
        if(StringUtils.isBlank(deliveryAppoint.getLicensePlate())){
            throw new BaseException(LocaleHandler.getLocaleMsg("车牌号码不可为空"));
        }
        if(Objects.isNull(deliveryAppoint.getEntryTime())){
            throw new BaseException(LocaleHandler.getLocaleMsg("送货日期不可为空"));
        }
        if(StringUtils.isBlank(deliveryAppoint.getDeliveryLocation())){
            throw new BaseException(LocaleHandler.getLocaleMsg("送货地点不可为空"));
        }

        //送货单行信息校验
        if(CollectionUtils.isEmpty(appointDeliveryNotes)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请至少添加一行送货单行"));
        }
        //来访人员信息校验
        if(CollectionUtils.isEmpty(deliveryAppointVisitors)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请至少添加一行来访人员信息行"));
        }

    }

    /**
     * 添加送货预约
     * @param requestDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public Long add(DeliveryAppointSaveRequestDTO requestDTO) {
        DeliveryAppoint deliveryAppoint = requestDTO.getDeliveryAppoint();
        List<DeliveryAppointVisitor> deliveryAppointVisitors = requestDTO.getDeliveryAppointVisitors();
        List<AppointDeliveryNote> appointDeliveryNotes = requestDTO.getAppointDeliveryNotes();

        Long id = IdGenrator.generate();
        String deliveryAppointNumber = baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_DELIVERY_APPOINT_NUM);

        //送货预约头保存
        deliveryAppoint.setDeliveryAppointId(id)
                .setDeliveryAppointNumber(deliveryAppointNumber);
        if(StringUtils.isBlank(deliveryAppoint.getDeliveryAppointStatus())){
            deliveryAppoint.setDeliveryAppointStatus(DeliveryAppointStatus.DRAFT.getValue());
        }
        this.save(deliveryAppoint);

        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(appointDeliveryNotes)) {
            //送货预约-送货单保存
            for(AppointDeliveryNote appointDeliveryNote : appointDeliveryNotes){
                appointDeliveryNote.setAppointDeliveryNoteId(IdGenrator.generate());
                appointDeliveryNote.setDeliveryAppointId(id);
            }
            iAppointDeliveryNoteService.saveBatch(appointDeliveryNotes);
            // 回写送货单标识 deliveryNoteService
            writebackDeliveryNoteService(appointDeliveryNotes,YesOrNo.YES.getValue());
        }

        //来访人员信息保存
        for(DeliveryAppointVisitor deliveryAppointVisitor : deliveryAppointVisitors){
            deliveryAppointVisitor.setDeliveryAppointVisitorId(IdGenrator.generate());
            deliveryAppointVisitor.setDeliveryAppointId(id);
        }
        iDeliveryAppointVisitorService.saveBatch(deliveryAppointVisitors);

        return id;
    }

    @Transactional
    public void writebackDeliveryNoteService(List<AppointDeliveryNote> appointDeliveryNotes,String flag) {
        if (!CollectionUtils.isEmpty(appointDeliveryNotes)) {
            List<Long> ids = appointDeliveryNotes.stream().map(AppointDeliveryNote::getDeliveryNoteId).collect(Collectors.toList());
            deliveryNoteService.update(Wrappers.lambdaUpdate(DeliveryNote.class).
                    set(DeliveryNote::getIfCreateDeliveryAppointment, flag).
                    in(DeliveryNote::getDeliveryNoteId,ids));
        }
    }

    /**
     * 更新送货预约
     * @param requestDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public Long update(DeliveryAppointSaveRequestDTO requestDTO) {
        DeliveryAppoint deliveryAppoint = requestDTO.getDeliveryAppoint();
        List<DeliveryAppointVisitor> deliveryAppointVisitors = requestDTO.getDeliveryAppointVisitors();
        List<AppointDeliveryNote> appointDeliveryNotes = requestDTO.getAppointDeliveryNotes();

        //送货预约头更新
        this.updateById(deliveryAppoint);

        //送货预约-送货单更新
        QueryWrapper<AppointDeliveryNote> appointDeliveryNoteWrapper = new QueryWrapper<>();
        appointDeliveryNoteWrapper.eq("DELIVERY_APPOINT_ID",deliveryAppoint.getDeliveryAppointId());
        List<AppointDeliveryNote> deliveryNotes = iAppointDeliveryNoteService.list(appointDeliveryNoteWrapper);
        writebackDeliveryNoteService(deliveryNotes,YesOrNo.NO.getValue());
        // // 回写送货单标识
        iAppointDeliveryNoteService.remove(appointDeliveryNoteWrapper);
        for(AppointDeliveryNote appointDeliveryNote : appointDeliveryNotes){
            appointDeliveryNote.setAppointDeliveryNoteId(IdGenrator.generate());
            appointDeliveryNote.setDeliveryAppointId(deliveryAppoint.getDeliveryAppointId());
        }
        // 回写送货单标识
        writebackDeliveryNoteService(appointDeliveryNotes,YesOrNo.NO.getValue());
        iAppointDeliveryNoteService.saveBatch(appointDeliveryNotes);

        //来访人员信息保存
        QueryWrapper<DeliveryAppointVisitor> deliveryAppointVisitorWrapper = new QueryWrapper<>();
        deliveryAppointVisitorWrapper.eq("DELIVERY_APPOINT_ID",deliveryAppoint.getDeliveryAppointId());
        iDeliveryAppointVisitorService.remove(deliveryAppointVisitorWrapper);
        for(DeliveryAppointVisitor deliveryAppointVisitor : deliveryAppointVisitors){
            deliveryAppointVisitor.setDeliveryAppointVisitorId(IdGenrator.generate());
            deliveryAppointVisitor.setDeliveryAppointId(deliveryAppoint.getDeliveryAppointId());
        }
        iDeliveryAppointVisitorService.saveBatch(deliveryAppointVisitors);

        return deliveryAppoint.getDeliveryAppointId();
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        //校验
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        List<DeliveryAppoint> deliveryAppointList = this.listByIds(ids);
        for(DeliveryAppoint deliveryAppoint : deliveryAppointList){
            if(!DeliveryAppointStatus.DRAFT.getValue().equals(deliveryAppoint.getDeliveryAppointStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg("拟定状态的单据才可删除"));
            }
        }
        //删除单据
        this.removeByIds(ids);

        QueryWrapper<AppointDeliveryNote> appointDeliveryNoteWrapper = new QueryWrapper<>();
        appointDeliveryNoteWrapper.in("DELIVERY_APPOINT_ID",ids);
        List<AppointDeliveryNote> deliveryNotes = iAppointDeliveryNoteService.list(appointDeliveryNoteWrapper);
        // 回写送货单标识
        writebackDeliveryNoteService(deliveryNotes,YesOrNo.NO.getValue());
        iAppointDeliveryNoteService.remove(appointDeliveryNoteWrapper);

        QueryWrapper<DeliveryAppointVisitor> deliveryAppointVisitorWrapper = new QueryWrapper<>();
        deliveryAppointVisitorWrapper.in("DELIVERY_APPOINT_ID",ids);
        iDeliveryAppointVisitorService.remove(deliveryAppointVisitorWrapper);

    }



    private DeliveryAppointSaveRequestDTO transfer(DeliveryAppointDetailDTO detail){
        DeliveryAppoint deliveryAppoint = new DeliveryAppoint();
        BeanUtils.copyProperties(detail,deliveryAppoint);
        List<AppointDeliveryNote> appointDeliveryNotes = detail.getAppointDeliveryNotes();
        List<DeliveryAppointVisitor> visitors = detail.getVisitors();
        return new DeliveryAppointSaveRequestDTO()
                .setDeliveryAppoint(deliveryAppoint)
                .setAppointDeliveryNotes(appointDeliveryNotes)
                .setDeliveryAppointVisitors(visitors);

    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    private DeliveryAppointDetailDTO detail(Long id){
        //查询送货预约头
        DeliveryAppoint deliveryAppoint = this.getById(id);
        //查询送货单信息
        List<AppointDeliveryNote> appointDeliveryNoteList = iAppointDeliveryNoteService.list(new QueryWrapper<>(new AppointDeliveryNote().setDeliveryAppointId(id)));
        //查询来访人员信息
        List<DeliveryAppointVisitor> deliveryAppointVisitorList = iDeliveryAppointVisitorService.list(new QueryWrapper<>(new DeliveryAppointVisitor().setDeliveryAppointId(id)));

        DeliveryAppointDetailDTO deliveryAppointDetailDTO = new DeliveryAppointDetailDTO();
        BeanUtils.copyProperties(deliveryAppoint,deliveryAppointDetailDTO);
        deliveryAppointDetailDTO.setAppointDeliveryNotes(appointDeliveryNoteList);
        deliveryAppointDetailDTO.setVisitors(deliveryAppointVisitorList);
        return deliveryAppointDetailDTO;

    }

    /**
     * 批量确认送货通知
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmBatch(List<Long> ids) {
        //校验
        if(CollectionUtils.isEmpty(ids)){
           throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        List<DeliveryAppoint> deliveryAppointList = this.listByIds(ids);
        for(DeliveryAppoint deliveryAppoint : deliveryAppointList){
            if(!DeliveryAppointStatus.WAITING_CONFIRM.getValue().equals(deliveryAppoint.getDeliveryAppointStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("[deliveryAppointNumber=%s]不为待确认状态,请检查",deliveryAppoint.getDeliveryAppointNumber())));
            }
        }
        //批量更新
        for(DeliveryAppoint deliveryAppoint : deliveryAppointList){
            deliveryAppoint.setDeliveryAppointStatus(DeliveryAppointStatus.ACCEPT.getValue());
        }
        this.updateBatchById(deliveryAppointList);
    }

    /**
     * 批量拒绝送货通知
     * @param requestDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseBatch(DeliveryAppointRequestDTO requestDTO) {
        List<Long> ids = requestDTO.getIds();
        String refusedReason = requestDTO.getRefusedReason();
        //校验
        if(CollectionUtils.isEmpty(ids)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请传递必传参数"));
        }
        List<DeliveryAppoint> deliveryAppointList = this.listByIds(ids);
        for(DeliveryAppoint deliveryAppoint : deliveryAppointList){
            if(!DeliveryAppointStatus.WAITING_CONFIRM.getValue().equals(deliveryAppoint.getDeliveryAppointStatus())){
                throw new BaseException(LocaleHandler.getLocaleMsg(String.format("[deliveryAppointNumber=%s]不为待确认状态,请检查",deliveryAppoint.getDeliveryAppointNumber())));
            }
        }
        //批量更新
        for(DeliveryAppoint deliveryAppoint : deliveryAppointList){
            deliveryAppoint.setDeliveryAppointStatus(DeliveryAppointStatus.REJECT.getValue());
            deliveryAppoint.setRefusedReason(refusedReason);
        }
        this.updateBatchById(deliveryAppointList);
        //todo 回写数量
    }
}

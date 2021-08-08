package com.midea.cloud.srm.cm.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.contract.ContractStatus;
import com.midea.cloud.common.enums.contract.ModelStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.contract.service.IContractHeadService;
import com.midea.cloud.srm.cm.model.mapper.ModelHeadMapper;
import com.midea.cloud.srm.cm.model.service.IModelHeadService;
import com.midea.cloud.srm.cm.model.utils.BeanUtils;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.model.entity.ModelHead;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  合同模板头表 服务实现类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 09:24:06
 *  修改内容:
 * </pre>
*/
@Service
public class ModelHeadServiceImpl extends ServiceImpl<ModelHeadMapper, ModelHead> implements IModelHeadService {
    @Resource
    private BaseClient baseClient;

    @Resource
    private IContractHeadService iContractHeadService;

    @Override
    public Map<String,Object> add(ModelHead modelHead) {
        // 创建id
        Long id = IdGenrator.generate();
        modelHead.setModelHeadId(id);
        String modelName = modelHead.getModelName();
        Assert.notNull(modelName,"c不能为空");
        Assert.isTrue(!(modelName.length() > 100),"模板名称长度不能超过100");
        // 合同模板编码
        modelHead.setModelCode(baseClient.seqGen(SequenceCodeConstant.SEQ_SCC_CM_COMPANY_MODEL));
        // 默认开始时间
        if(StringUtil.isEmpty(modelHead.getStartDate())){
            modelHead.setStartDate(LocalDate.now());
        }
        // 设置合同状态拟定
        modelHead.setStatus(ModelStatus.DRAFT.getKey());
        // 是否可修改全部
        if(StringUtil.isEmpty(modelHead.getEnable())){
            modelHead.setEnable("Y");
        }
        // 保存数据
        this.save(modelHead);
        HashMap<String, Object> result = BeanUtils.getStringObjectHashMap(id);
        return result;
    }

    @Override
    public Map<String, Object> update(ModelHead modelHead) {
        // 判断状态非拟定
        Assert.isTrue(!ModelStatus.DRAFT.getKey().equals(modelHead.getStatus()),"状态不能为拟定");
        // 只能修改生效与失效日期及状态
        Assert.notNull(modelHead.getModelHeadId(),"参数modelHeadId不能为空");
        // 设置更新参数
        UpdateWrapper<ModelHead> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(StringUtil.notEmpty(modelHead.getStartDate()),"START_DATE",modelHead.getStartDate());
        updateWrapper.set(StringUtil.notEmpty(modelHead.getEndDate()),"END_DATE",modelHead.getEndDate());
        updateWrapper.set(StringUtil.notEmpty(modelHead.getStatus()),"STATUS",modelHead.getStatus());
        updateWrapper.set(StringUtil.notEmpty(modelHead.getEnable()),"enable",modelHead.getEnable());
        updateWrapper.eq("MODEL_HEAD_ID",modelHead.getModelHeadId());
        this.update(updateWrapper);
        return BeanUtils.getStringObjectHashMap(modelHead.getModelHeadId());
    }

    @Override
    public PageInfo<ModelHead> listPage(ModelHead modelHead) {
        // 设置分页参数
        PageUtil.startPage(modelHead.getPageNum(),modelHead.getPageSize());
        // 设置查询条件
        QueryWrapper<ModelHead> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtil.notEmpty(modelHead.getModelName()),"MODEL_NAME",modelHead.getModelName());
        queryWrapper.like(StringUtil.notEmpty(modelHead.getModelCode()),"MODEL_CODE",modelHead.getModelCode());
        queryWrapper.eq(StringUtil.notEmpty(modelHead.getModelType()),"MODEL_TYPE",modelHead.getModelType());
        queryWrapper.eq(StringUtil.notEmpty(modelHead.getStatus()),"STATUS",modelHead.getStatus());
        queryWrapper.ge(StringUtil.notEmpty(modelHead.getStartDate()),"START_DATE",modelHead.getStartDate());
        queryWrapper.le(StringUtil.notEmpty(modelHead.getEndDate()),"END_DATE",modelHead.getEndDate());
        queryWrapper.orderByDesc("MODEL_HEAD_ID");
        List<ModelHead> list = this.list(queryWrapper);
        return new PageInfo<>(list);
    }

    @Override
    public List<ModelHead> modelList() {
        QueryWrapper<ModelHead> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("START_DATE");
        queryWrapper.and(modelHeadQueryWrapper -> {
            modelHeadQueryWrapper.isNull("END_DATE").or().ge("END_DATE",LocalDate.now());
        });
        queryWrapper.eq("STATUS",ModelStatus.VALID.getKey());
        return this.list(queryWrapper);
    }

    @Override
    public List<ModelHead> modelListByType(String modelType) {
        Assert.notNull(modelType,"缺少参数: modelType");
        QueryWrapper<ModelHead> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(modelHeadQueryWrapper -> {
            modelHeadQueryWrapper.isNull("END_DATE").or().ge("END_DATE",LocalDate.now());
        });
        queryWrapper.eq("STATUS",ModelStatus.VALID.getKey());
        queryWrapper.eq("MODEL_TYPE",modelType);
        return this.list(queryWrapper);
    }

    @Override
    public void takeEffect(Long modelHeadId) {
        ModelHead modelHead = this.getById(modelHeadId);
        Assert.notNull(modelHead,"找不到该合同:"+modelHeadId);
        modelHead.setStatus(ModelStatus.VALID.getKey());
        this.updateById(modelHead);
    }

    @Override
    public void failure(Long modelHeadId) {
        /**
         * 校验：当前模板是否被引用到合同中，且合同状态为“新增、审批中”；如果是，则不可进行操作
         */
        this.checkContractStatus(modelHeadId);
        ModelHead modelHead = this.getById(modelHeadId);
        Assert.notNull(modelHead,"找不到该合同:"+modelHeadId);
        modelHead.setStatus(ModelStatus.INVALID.getKey());
        modelHead.setEndDate(LocalDate.now());
        this.updateById(modelHead);
    }

    private void checkContractStatus(Long modelHeadId) {
        List<ContractHead> contractHeads = iContractHeadService.list(new QueryWrapper<>(new ContractHead().setModelHeadId(modelHeadId)));
        if(!CollectionUtils.isEmpty(contractHeads)){
            contractHeads.forEach(contractHead -> {
                // 判断是否合同同状态存在“新增、审批中”
                if(ContractStatus.DRAFT.name().equals(contractHead.getContractStatus()) ||
                        ContractStatus.UNDER_REVIEW.name().equals(contractHead.getContractStatus())){
                    throw new BaseException("存在合同状态为新增或审批的合同引用了该单据, 模板不能更改状态");
                }
            });
        }
    }

    @Override
    public void freeze(Long modelHeadId) {
        //当前模板是否被引用到合同中，且合同状态为“新增、审批中”；如果是，则不可进行操作
//        this.checkContractStatus(modelHeadId);
        ModelHead modelHead = this.getById(modelHeadId);
        Assert.notNull(modelHead,"找不到该合同:"+modelHeadId);
        modelHead.setStatus("FREEZE");
        this.updateById(modelHead);
    }
}

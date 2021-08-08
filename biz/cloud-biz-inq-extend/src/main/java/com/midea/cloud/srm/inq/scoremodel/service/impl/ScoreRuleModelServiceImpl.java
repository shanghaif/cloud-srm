package com.midea.cloud.srm.inq.scoremodel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.inq.ScoreModelStatus;
import com.midea.cloud.common.enums.inq.ScoreModelType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.inq.scoremodel.mapper.ScoreRuleModelMapper;
import com.midea.cloud.srm.inq.scoremodel.service.IScoreRuleModelItemService;
import com.midea.cloud.srm.inq.scoremodel.service.IScoreRuleModelService;
import com.midea.cloud.srm.model.inq.scoremodel.dto.ScoreRuleModelDto;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModel;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModelItem;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 *  询价评分规则模板表 服务实现类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 19:21:23
 *  修改内容:
 * </pre>
 */
@Service
public class ScoreRuleModelServiceImpl extends ServiceImpl<ScoreRuleModelMapper, ScoreRuleModel> implements IScoreRuleModelService {
    @Autowired
    private BaseClient baseClient;

    @Autowired
    private IScoreRuleModelService iScoreRuleModelService;

    @Autowired
    private IScoreRuleModelItemService iScoreRuleModelItemService;

    @Override
    public ScoreRuleModelDto getHeadById(Long modelId) {
        ScoreRuleModelDto dto = new ScoreRuleModelDto();
        dto.setModel(iScoreRuleModelService.getById(modelId));
        dto.setModelItems(iScoreRuleModelItemService.getByHeadId(modelId));
        return dto;
    }

    @Override
    @Transactional
    public void saveAndUpdate(ScoreRuleModelDto header) {
        ScoreRuleModel model = header.getModel();
        if (null != model) {
            /**
             * 为保证 TITLE,TYPE,STATUS=A 确定唯一进行校验
             */
            if (ScoreModelStatus.A.name().equals(model.getStatus())) {
                if (StringUtil.notEmpty(model.getScoreRuleModelId())) {
                    // 更新操作
                    // 查找状态
                    String status = this.getById(model.getScoreRuleModelId()).getStatus();
                    if (ScoreModelStatus.D.name().equals(status)) {
                        this.checkScoreModel(model);
                    }
                }else {
                    // 新增操作
                    // 检验模板是否重复
                    this.checkScoreModel(model);
                }
            }
            Long id = model.getScoreRuleModelId();
            if (null == id) {
                id = IdGenrator.generate();
                model.setScoreRuleModelId(id);
                model.setScoreRuleModelNo(baseClient.seqGen(SequenceCodeConstant.SEQ_INQ_SCORERULE_CODE));
            }
            saveHeader(model);
            saveItems(header.getModelItems(), id);
        }

    }

    public void checkScoreModel(ScoreRuleModel model) {
        // 检验模板是否重复
        QueryWrapper<ScoreRuleModel> query = new QueryWrapper();
        query.eq("TITLE", model.getTitle());
        query.eq("STATUS", ScoreModelStatus.A.name());
        query.eq("TYPE", model.getType());
        List<ScoreRuleModel> list = this.list(query);
        if (CollectionUtils.isNotEmpty(list)) {
            // 存在, 检查原状态
            throw new BaseException("评分模板不能重复");
        }
    }

    @Override
    public ScoreRuleModelDto getModelByCode(String modelCode) {
        ScoreRuleModelDto dto = new ScoreRuleModelDto();
        QueryWrapper<ScoreRuleModel> query = new QueryWrapper();
        query.eq("TITLE",modelCode);
        query.eq("STATUS", ScoreModelStatus.A.name());
        query.eq("TYPE", ScoreModelType.INQUIRY.name());
        ScoreRuleModel one = iScoreRuleModelService.getOne(query);
        if(one==null){
            return dto;
        }
        dto.setModel(one);
        dto.setModelItems(iScoreRuleModelItemService.getByHeadId(one.getScoreRuleModelId()));
        return dto;
    }

    @Transactional
    public void saveHeader(ScoreRuleModel head) {
        //需要初始化的字段写这里
        iScoreRuleModelService.saveOrUpdate(head);
    }


    @Transactional
    public void saveItems(List<ScoreRuleModelItem> items, Long headId) {
        if (StringUtil.notEmpty(headId)) {
            QueryWrapper<ScoreRuleModelItem> wrapper = new QueryWrapper();
            wrapper.eq("SCORE_RULE_MODEL_ID", headId);
            iScoreRuleModelItemService.remove(wrapper);
        }
        if (CollectionUtils.isNotEmpty(items)) {
            double sum = 0 ;
            // 校验权重和必须100
            for(ScoreRuleModelItem item : items){
                item.setScoreRuleModelItemId(IdGenrator.generate());
                item.setScoreRuleModelId(headId);
                BigDecimal scoreWeight = item.getScoreWeight();
                if(StringUtil.notEmpty(scoreWeight)){
                    sum += scoreWeight.doubleValue();
                }else {
                    throw new BaseException("权重不能为空");
                }
            }
            if(sum != 100){
                throw new BaseException("权重合计必须等于100%");
            }
            iScoreRuleModelItemService.saveBatch(items);
        }
    }
}

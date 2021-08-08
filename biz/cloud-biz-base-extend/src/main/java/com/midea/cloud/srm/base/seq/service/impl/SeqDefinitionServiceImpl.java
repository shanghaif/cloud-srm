package com.midea.cloud.srm.base.seq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.srm.base.seq.mapper.SeqDefinitionMapper;
import com.midea.cloud.srm.base.seq.service.ISeqDefinitionService;
import com.midea.cloud.srm.model.base.seq.entity.SeqDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  序列号服务实现类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-11 17:24
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class SeqDefinitionServiceImpl extends ServiceImpl<SeqDefinitionMapper, SeqDefinition> implements ISeqDefinitionService {

    @Resource
    SeqDefinitionMapper seqDefinitionMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String genSequencesNumBase(String sequenceCode, Serializable... granularsChain) {
        log.info("--------------------生成单据号编码开始---------------------");
        log.info("单据号编码为:"+sequenceCode);
        SeqDefinition seqDefinition = new SeqDefinition();
        seqDefinition.setSequenceCode(sequenceCode);
        if (granularsChain != null) {
            // 粒度赋值
            MethodAccess ma = MethodAccess.get(SeqDefinition.class);
            for (int i = 0; i < granularsChain.length && i < 20; i++) {
                ma.invoke(seqDefinition, "setGranular" + (i + 1), granularsChain[i] == null ? null : granularsChain[i].toString());
            }
        }
        String sequencesNumBase = this.genSequencesNumBase(seqDefinition);
        log.info("--------------------生成单据号编码结束---------------------");
        log.info("生成的单据号为:"+sequencesNumBase);
        return sequencesNumBase;
    }

    @Override
    public Map<String, Object> createSequencesNum(SeqDefinition seqDefinition, Map<String, Object> extParam) {
        //TODO 未完成
        return null;
    }

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String genSequencesNumBase(SeqDefinition seqDefinition) {
        String result = "";

        // 获得没有粒度控制的定义
        SeqDefinition existDefinitionWithoutGranular = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("endDate", new Date());
        params.put("sequenceCode", seqDefinition.getSequenceCode());
        List<SeqDefinition> existDefinitionWithoutGranularList = this.seqDefinitionMapper.getDefinition(params);
        if (existDefinitionWithoutGranularList != null && existDefinitionWithoutGranularList.size() > 0) {
            existDefinitionWithoutGranular = existDefinitionWithoutGranularList.get(0);
        }

        // 粒度赋值
        // 精确查询
        params.put("enableFlag", "yes");
        MethodAccess ma = MethodAccess.get(SeqDefinition.class);
        for (int i = 0; i < 20; i++) {
            Object granularValue = ma.invoke(seqDefinition, "getGranular" + (i + 1));
            params.put("granular" + (i + 1), granularValue == null ? null : granularValue.toString());
        }
        // 获得有粒度控制的定义使用select for update 锁
        // 20200709-使用分布式锁代替for update
        /**
         *   * @param key        锁名
         *      * @param expireTime 锁过期时间
         *      * @param unit       锁过期时间单位
         *      * @param awaitTime  锁等待超时时间
         *      * @param awaitUnit  锁
         *      * @param retryCount 最大获取次数
         *      * @param sleepTime  每次获取失败后的睡眠时间
         *      * @param sleepUnit  睡眠单位
         */
        try {
            // 获取锁
            Boolean lock = redisUtil.tryLockInTime(seqDefinition.getSequenceCode(),
                    5, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, 10,
                    500, TimeUnit.MILLISECONDS
            );
            if (lock) {
                List<SeqDefinition> existDefinition = this.seqDefinitionMapper.selectForUpdate(params);
                // 重置CURRENT_VALUE
//                if (CollectionUtils.isNotEmpty(existDefinition) && existDefinition.get(0).getScopeDefinition().contains("df_date")
//                        && !dateFormatter.format(existDefinition.get(0).getLastUpdateDate()).equals(dateFormatter.format(new Date()))) {
//                    SeqDefinition definition = existDefinition.get(0);
//                    UpdateWrapper seqUpdateWrapper = new UpdateWrapper<SeqDefinition>(
//                            new SeqDefinition().setSequenceId(definition.getSequenceId()).setLastUpdateDate(definition.getLastUpdateDate()));
//                    definition.setLastUpdateDate(new Date());
//                    definition.setCurrentValue(0L);
//                    this.update(definition, seqUpdateWrapper);
//                    existDefinition = this.seqDefinitionMapper.selectForUpdate(params);
//                }
                if (CollectionUtils.isNotEmpty(existDefinition) && existDefinition.get(0).getScopeDefinition() != null) {
                    if (existDefinition.get(0).getScopeDefinition().contains("df_date")
                            && !dateFormatter.format(existDefinition.get(0).getLastUpdateDate()).equals(dateFormatter.format(new Date()))) {
                        SeqDefinition definition = existDefinition.get(0);
                        UpdateWrapper seqUpdateWrapper = new UpdateWrapper<SeqDefinition>(
                                new SeqDefinition().setSequenceId(definition.getSequenceId()).setLastUpdateDate(definition.getLastUpdateDate()));
                        definition.setLastUpdateDate(new Date());
                        definition.setCurrentValue(0L);
                        this.update(definition, seqUpdateWrapper);
                        existDefinition = this.seqDefinitionMapper.selectForUpdate(params);
                    }
                }
                if (existDefinition == null || existDefinition.size() == 0) {
                    // 不存在,创建
                    SeqDefinition definitionToCreate = new SeqDefinition();
                    // 设置序列名称
                    try {
                        BeanUtils.copyProperties(seqDefinition, definitionToCreate, "serialVersionUID");
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BaseException("赋值异常!");
                    }
                    // 设置默认值
                    {
                        if (existDefinitionWithoutGranular != null) {
                            if (definitionToCreate.getSequenceName() == null) {
                                definitionToCreate.setSequenceName(existDefinitionWithoutGranular.getSequenceName());
                            }
                            if (definitionToCreate.getSequenceCode() == null) {
                                definitionToCreate.setSequenceCode(existDefinitionWithoutGranular.getSequenceCode());
                            }
                            if (definitionToCreate.getLength() == null) {
                                definitionToCreate.setLength(existDefinitionWithoutGranular.getLength());
                            }
                            if (definitionToCreate.getPrefix() == null) {
                                definitionToCreate.setPrefix(existDefinitionWithoutGranular.getPrefix());
                            }

                            if (definitionToCreate.getCurrentValue() == null) {
                                definitionToCreate.setCurrentValue(existDefinitionWithoutGranular.getInitialValue());
                            }
                            if (definitionToCreate.getScopeDefinition() == null) {
                                definitionToCreate.setScopeDefinition(existDefinitionWithoutGranular.getScopeDefinition());
                            }
                            if (definitionToCreate.getSequenceNumReset() == null) {
                                definitionToCreate.setSequenceNumReset(existDefinitionWithoutGranular.getSequenceNumReset());
                            }
                            if (definitionToCreate.getOffset() == null) {
                                definitionToCreate.setOffset(existDefinitionWithoutGranular.getOffset());
                            }
                        }
                        if (definitionToCreate.getInitialValue() == null) {
                            definitionToCreate.setInitialValue(0L);
                            definitionToCreate.setCurrentValue(definitionToCreate.getInitialValue());
                        }
                    }
                    definitionToCreate.setCreationDate(new Date());
                    definitionToCreate.setSequenceId(null);
                    definitionToCreate.setVersion(0L);
                    extCreatSeqDefinition(definitionToCreate, params);
                    // 读已提交
                    existDefinition = this.seqDefinitionMapper.selectForUpdate(params);
                } else if (existDefinition.size() > 1) {
                    // 存在多个.抛出异常
                    throw new BaseException("未能获得唯一定义!");
                }
                SeqDefinition definition = existDefinition.get(0);
                String scopeDefinition = definition.getScopeDefinition();
                if (StringUtils.isBlank(scopeDefinition)) {
                    scopeDefinition = "[@df_prefix][@df_date_yyyyMMdd][@df_seq]";
                }
                for (String scopeCode : scopeDefinition.split("]")) {
                    if (StringUtils.isNotBlank(scopeCode)) {
                        scopeCode = scopeCode + "]";
                        String s = this.rebulidScope(scopeCode, definition);
                        if (s != null) {
                            result = result + s;
                        }
                    }
                }

                // 存在,更新
                definition.setLastUpdateDate(new Date());
                this.updateById(definition);
                return result;
            } else {
                throw new BaseException("生成序列号失败，已重试10次或超时5分钟尚未获取到");
            }
        } finally {
            redisUtil.unLock(seqDefinition.getSequenceCode()); // 释放锁
        }
    }

    /**
     * 构建区域,可考虑改为接口实现注入
     *
     * @param scopeCode     区域构建编码
     * @param seqDefinition 序列号定义
     * @return
     */
    private String rebulidScope(String scopeCode, SeqDefinition seqDefinition) {
        Date nowDate = new Date();
        if (scopeCode != null) {
            scopeCode = scopeCode.replaceAll("[\\[\\]\\@]", "");
            if (scopeCode.startsWith("df_prefix")) {
                return seqDefinition.getPrefix();
            }
            if (scopeCode.startsWith("df_date")) {
                // 截取日期格式
                SimpleDateFormat sdf = new SimpleDateFormat(scopeCode.substring(8));
                return sdf.format(seqDefinition.getLastUpdateDate());
            }
            if (scopeCode.startsWith("df_seq")) {
                String sequenceNumReset = seqDefinition.getSequenceNumReset();
                // NONE,DAY,WEEK,MONTH,QUARTERLY,YEAR
                boolean requireReset = false;
                // 无量纲偏移,eg:为周时 3600*24*4周一重置, 3600*24*3 周日重置
                int offset = seqDefinition.getOffset() == null ? 0 : seqDefinition.getOffset().intValue();
                if (sequenceNumReset == null) {
                    requireReset = false;
                } else if ("NONE".equals(sequenceNumReset)) {
                    requireReset = false;
                } else if (sequenceNumReset.lastIndexOf("_s") >= 0) {
                    // 按秒重置
                    Long resetSecond = Long.valueOf(sequenceNumReset.toLowerCase().replace("_s", ""));
                    Long lastUpdateSecond = seqDefinition.getLastUpdateDate().getTime() / 1000;
                    Long nowSecond = nowDate.getTime() / 1000;
                    if (((nowSecond + offset) / resetSecond) != ((lastUpdateSecond + offset) / resetSecond)) {
                        requireReset = true;
                    } else {
                        requireReset = false;
                    }
                } else if (sequenceNumReset.lastIndexOf("_m") >= 0) {
                    // 按月重置
                    Long resetMonth = Long.valueOf(sequenceNumReset.toLowerCase().replace("_m", ""));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(seqDefinition.getLastUpdateDate());
                    int lastUpdateMonth = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
                    calendar.setTime(nowDate);
                    int nowMonth = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
                    if (((nowMonth + offset) / resetMonth) != ((lastUpdateMonth + offset) / resetMonth)) {
                        requireReset = true;
                    } else {
                        requireReset = false;
                    }
                } else if (sequenceNumReset.lastIndexOf("_w") >= 0) {
                    // 按周重置
                    Long resetWeek = Long.valueOf(sequenceNumReset.toLowerCase().replace("_w", "")) * 3600 * 24 * 7;
                    Long lastUpdateSecond = (seqDefinition.getLastUpdateDate().getTime()) / 1000;
                    Long nowSecond = (nowDate.getTime()) / 1000;
                    if (((nowSecond + offset) / resetWeek) != ((lastUpdateSecond + offset) / resetWeek)) {
                        requireReset = true;
                    } else {
                        requireReset = false;
                    }
                } else {
                    requireReset = false;
                }
                // 重置标记
                if (requireReset) {
                    // 如果符合重置条件,重置
                    seqDefinition.setCurrentValue(seqDefinition.getInitialValue());
                }
                seqDefinition.setCurrentValue(seqDefinition.getCurrentValue() + 1);
                if (seqDefinition.getLength() == null) {
                    seqDefinition.setLength(5L);
                }
                String result = StringUtils.leftPad(seqDefinition.getCurrentValue() + "", seqDefinition.getLength().intValue(), "0");
                return result;
            } else {
                // 指定格式不存在
                return "";
            }
        } else {
            return "";
        }
    }

    @Override
    public synchronized int extCreatSeqDefinition(SeqDefinition seqDefinition, Map<String, Object> params) {
        List<SeqDefinition> extItem = this.seqDefinitionMapper.getDefinition(params);
        if (extItem != null && extItem.size() > 0) {
            return extItem.size();
        } else {
            seqDefinition.setSequenceId(IdGenrator.generate());
            this.save(seqDefinition);
            if (seqDefinition.getSequenceId() != null) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public PageInfo<SeqDefinition> pageList(SeqDefinition seqDefinition) {
        Assert.notNull(seqDefinition.getPageNum(), "分页参数不能为空");
        Assert.notNull(seqDefinition.getPageSize(), "分页参数不能为空");
        // 设置分页
        PageUtil.startPage(seqDefinition.getPageNum(), seqDefinition.getPageSize());
        QueryWrapper<SeqDefinition> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtil.notEmpty(seqDefinition.getSequenceName()), "SEQUENCE_NAME", seqDefinition.getSequenceName());
        queryWrapper.like(StringUtil.notEmpty(seqDefinition.getSequenceCode()), "SEQUENCE_CODE", seqDefinition.getSequenceCode());
        queryWrapper.like(StringUtil.notEmpty(seqDefinition.getPrefix()), "PREFIX", seqDefinition.getPrefix());
        queryWrapper.orderByDesc("SEQUENCE_ID");
        List<SeqDefinition> seqDefinitions = this.list(queryWrapper);
        return new PageInfo<>(seqDefinitions);
    }

    /**
     * 单号模板列表, 校验用
     */
    public static final List<String> scopeDefinitionList;

    static {
        scopeDefinitionList = new ArrayList<>();
        scopeDefinitionList.add("[@df_prefix][@df_seq]");
        scopeDefinitionList.add("[@df_prefix][@df_date_yyyyMMdd][@df_seq]");
        scopeDefinitionList.add("[@df_prefix][@df_date_yyMMdd][@df_seq]");
        scopeDefinitionList.add("[@df_prefix][@df_date_MMdd][@df_seq]");
    }

    @Override
    public Map<String, Object> add(SeqDefinition seqDefinition) {
        // 参数校验
        this.checkParam(seqDefinition);
        // 设置参数
        if (null == seqDefinition.getStartDate()) {
            seqDefinition.setStartDate(new Date());
        }
        seqDefinition.setInitialValue(0L);
        seqDefinition.setCurrentValue(0L);
        seqDefinition.setSequenceNumReset("NONE");
        seqDefinition.setOffset(0L);
        // 查询最新的id
        Long id = IdGenrator.generate();
        seqDefinition.setSequenceId(id);
        // 保存
        this.save(seqDefinition);
        HashMap<String, Object> resut = new HashMap<>();
        resut.put("sequenceId", id);
        return resut;
    }

    // 参数校验
    public void checkParam(SeqDefinition seqDefinition) {
        StringBuffer errorMag = new StringBuffer();
        if (StringUtil.isEmpty(seqDefinition.getSequenceName())) {
            errorMag.append("单据名称不能为空; ");
        }
        if (StringUtil.isEmpty(seqDefinition.getSequenceCode())) {
            errorMag.append("单据编号不能为空; ");
        }
        if (StringUtil.isEmpty(seqDefinition.getScopeDefinition())) {
            errorMag.append("单号模板格式不能为空; ");
        } else if (!scopeDefinitionList.contains(seqDefinition.getScopeDefinition())) {
            errorMag.append("单号模板格式错误; ");
        }
        if (StringUtil.isEmpty(seqDefinition.getLength())) {
            errorMag.append("流水号长度不能为空; ");
        }
        if (StringUtil.isEmpty(seqDefinition.getLength())) {
            errorMag.append("流水号长度不能为空; ");
        }
        Long length = seqDefinition.getLength();
        if (StringUtil.notEmpty(length) && length < 1) {
            errorMag.append("流水号长度不能小于1; ");
        }
        String sequenceCode = seqDefinition.getSequenceCode();
        if (null != sequenceCode) {
            QueryWrapper<SeqDefinition> queryWrapper = new QueryWrapper<>(new SeqDefinition().setSequenceCode(sequenceCode));
            List<SeqDefinition> list = this.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                errorMag.append("单据编号不能重复; ");
            }
        }
        if (errorMag.length() > 1) {
            throw new RuntimeException(errorMag.toString());
        }
    }

    @Override
    public void delete(Long sequenceId) {
        Assert.notNull(sequenceId, "参数不能为空:sequenceId");
        this.removeById(sequenceId);
    }

    @Override
    public Map<String, Object> update(SeqDefinition seqDefinition) {
        Long sequenceId = seqDefinition.getSequenceId();
        Assert.notNull(sequenceId, "参数不能为空:sequenceId");
        UpdateWrapper<SeqDefinition> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(StringUtil.notEmpty(seqDefinition.getScopeDefinition()), "SCOPE_DEFINITION", seqDefinition.getScopeDefinition());
        updateWrapper.set(StringUtil.notEmpty(seqDefinition.getPrefix()), "PREFIX", seqDefinition.getPrefix());
        updateWrapper.set(StringUtil.notEmpty(seqDefinition.getLength()), "LENGTH", seqDefinition.getLength());
        updateWrapper.set(StringUtil.notEmpty(seqDefinition.getStartDate()), "START_DATE", seqDefinition.getStartDate());
        updateWrapper.set(StringUtil.notEmpty(seqDefinition.getEndDate()), "END_DATE", seqDefinition.getEndDate());
        updateWrapper.eq("SEQUENCE_ID", sequenceId);
        this.update(updateWrapper);
        HashMap<String, Object> resut = new HashMap<>();
        resut.put("sequenceId", sequenceId);
        return resut;
    }
}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.midea.cloud.srm.base.seq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.seq.entity.SeqDefinition;
import java.io.Serializable;
import java.util.Map;

public interface ISeqDefinitionService extends IService<SeqDefinition> {
    String genSequencesNumBase(SeqDefinition seqDefinition);

    String genSequencesNumBase(String sequenceCode, Serializable... granularsChain);

    int extCreatSeqDefinition(SeqDefinition seqDefinition, Map<String, Object> params);

    Map<String, Object> createSequencesNum(SeqDefinition seqDefinition, Map<String, Object> extParam);

    PageInfo<SeqDefinition> pageList(SeqDefinition seqDefinition);

    Map<String, Object> add(SeqDefinition seqDefinition);

    void delete(Long sequenceId);

    Map<String, Object> update(SeqDefinition seqDefinition);
}

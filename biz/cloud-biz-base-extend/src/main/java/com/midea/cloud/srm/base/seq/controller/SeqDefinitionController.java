//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.midea.cloud.srm.base.seq.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.base.seq.service.ISeqDefinitionService;
import com.midea.cloud.srm.model.base.seq.entity.SeqDefinition;
import com.midea.cloud.srm.model.common.BaseController;
import java.io.Serializable;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/seq"})
public class SeqDefinitionController extends BaseController {
    @Autowired
    private ISeqDefinitionService iSeqDefinitionService;

    public SeqDefinitionController() {
    }

    @GetMapping({"/get"})
    public String get(String sequenceCode) {
        Assert.notNull(sequenceCode, "序列号不能为空");
        return this.iSeqDefinitionService.genSequencesNumBase(sequenceCode, new Serializable[0]);
    }

    @PostMapping({"/pageList"})
    public PageInfo<SeqDefinition> pageList(@RequestBody SeqDefinition seqDefinition) {
        return this.iSeqDefinitionService.pageList(seqDefinition);
    }

    @PostMapping({"/add"})
    public Map<String, Object> add(@RequestBody SeqDefinition seqDefinition) {
        return this.iSeqDefinitionService.add(seqDefinition);
    }

    @GetMapping({"/delete"})
    public void delete(Long sequenceId) {
        this.iSeqDefinitionService.removeById(sequenceId);
    }

    @GetMapping({"/queryById"})
    public SeqDefinition queryById(Long sequenceId) {
        Assert.notNull(sequenceId, "参数不能为空:sequenceId");
        return (SeqDefinition)this.iSeqDefinitionService.getById(sequenceId);
    }

    @PostMapping({"/update"})
    public Map<String, Object> update(@RequestBody SeqDefinition seqDefinition) {
        return this.iSeqDefinitionService.update(seqDefinition);
    }
}

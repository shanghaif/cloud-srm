//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.midea.cloud.srm.base.sceneattachment.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.sceneattachment.service.ISceneAttachmentService;
import com.midea.cloud.srm.model.base.sceneattachment.entity.SceneAttachment;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/sceneAttachment"})
public class SceneAttachmentController extends BaseController {
    @Autowired
    private ISceneAttachmentService iSceneAttachmentService;

    public SceneAttachmentController() {
    }

    @GetMapping({"/get"})
    public SceneAttachment get(Long id) {
        Assert.notNull(id, "id不能为空");
        return (SceneAttachment)this.iSceneAttachmentService.getById(id);
    }

    @PostMapping({"/add"})
    public void add(@RequestBody SceneAttachment sceneAttachment) {
        Long id = IdGenrator.generate();
        sceneAttachment.setSceneAttachmentId(id);
        this.iSceneAttachmentService.save(sceneAttachment);
    }

    @GetMapping({"/delete"})
    public void delete(Long sceneAttachmentId) {
        Assert.notNull(sceneAttachmentId, "sceneAttachmentId不能为空");
        this.iSceneAttachmentService.deleteSceneAttachment(sceneAttachmentId);
    }

    @PostMapping({"/modify"})
    public void modify(@RequestBody SceneAttachment sceneAttachment) {
        this.iSceneAttachmentService.updateById(sceneAttachment);
    }

    @PostMapping({"/listPageByParm"})
    public PageInfo<SceneAttachment> listPageByParm(@RequestBody SceneAttachment sceneAttachment) {
        return this.iSceneAttachmentService.listPageByParm(sceneAttachment);
    }

    @PostMapping({"/listAll"})
    public List<SceneAttachment> listAll() {
        return this.iSceneAttachmentService.list();
    }

    @PostMapping({"/listSceneCodeAndSceneName"})
    public List<Map<String, String>> listSceneCodeAndSceneName() {
        return this.iSceneAttachmentService.listSceneCodeAndSceneName();
    }

    @PostMapping({"/batchSaveOrUpdate"})
    public void batchSaveOrUpdate(@RequestBody List<SceneAttachment> sceneAttachments) {
        this.iSceneAttachmentService.batchSaveOrUpdateSceneAttachment(sceneAttachments);
    }

    @PostMapping({"/deleteFileUpLoad"})
    public void deleteFileUpLoad(@RequestParam("sceneAttachmentId") Long sceneAttachmentId) {
        this.iSceneAttachmentService.deleteFileUpLoad(sceneAttachmentId);
    }
}

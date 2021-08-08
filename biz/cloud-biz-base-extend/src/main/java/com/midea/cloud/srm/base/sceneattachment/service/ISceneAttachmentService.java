//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.midea.cloud.srm.base.sceneattachment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.sceneattachment.entity.SceneAttachment;
import java.util.List;
import java.util.Map;

public interface ISceneAttachmentService extends IService<SceneAttachment> {
    List<Map<String, String>> listSceneCodeAndSceneName();

    void batchSaveOrUpdateSceneAttachment(List<SceneAttachment> sceneAttachments);

    PageInfo<SceneAttachment> listPageByParm(SceneAttachment sceneAttachment);

    void deleteFileUpLoad(Long sceneAttachmentId);

    void deleteSceneAttachment(Long sceneAttachmentId);
}

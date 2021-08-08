//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.midea.cloud.srm.base.sceneattachment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.PermissionType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.sceneattachment.mapper.SceneAttachmentMapper;
import com.midea.cloud.srm.base.sceneattachment.service.ISceneAttachmentService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.sceneattachment.entity.SceneAttachment;
import com.midea.cloud.srm.model.rbac.function.entity.Function;
import com.midea.cloud.srm.model.rbac.permission.entity.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SceneAttachmentServiceImpl extends ServiceImpl<SceneAttachmentMapper, SceneAttachment> implements ISceneAttachmentService {
    @Autowired
    RbacClient rbacClient;
    @Autowired
    FileCenterClient fileCenterClient;

    public SceneAttachmentServiceImpl() {
    }

    public List<Map<String, String>> listSceneCodeAndSceneName() {
        List<Function> functions = this.rbacClient.listFunctionByParm((new Permission()).setPermissionType(PermissionType.MENU.name()).setEnableAttachManage(YesOrNo.YES.getValue()));
        List<Map<String, String>> sceneCodeAndSceneName = new ArrayList();
        if (!CollectionUtils.isEmpty(functions)) {
            Iterator var3 = functions.iterator();

            while(var3.hasNext()) {
                Function function = (Function)var3.next();
                if (function != null) {
                    Map<String, String> map = new HashMap();
                    map.put("senceCode", function.getFunctionCode());
                    map.put("senceName", function.getFunctionName());
                    sceneCodeAndSceneName.add(map);
                }
            }
        }

        return sceneCodeAndSceneName;
    }

    public void batchSaveOrUpdateSceneAttachment(List<SceneAttachment> sceneAttachments) {
        if (!CollectionUtils.isEmpty(sceneAttachments)) {
            Iterator var2 = sceneAttachments.iterator();

            while(var2.hasNext()) {
                SceneAttachment sceneAttachment = (SceneAttachment)var2.next();
                if (sceneAttachment != null) {
                    this.checkBeforeSaveOrUpdate(sceneAttachment);
                    if (sceneAttachment.getSceneAttachmentId() == null) {
                        long id = IdGenrator.generate();
                        sceneAttachment.setSceneAttachmentId(id).setLastUpdateDate(new Date());
                    }
                }
            }

            this.saveOrUpdateBatch(sceneAttachments);
        }

    }

    public PageInfo<SceneAttachment> listPageByParm(SceneAttachment sceneAttachment) {
        PageUtil.startPage(sceneAttachment.getPageNum(), sceneAttachment.getPageSize());
        SceneAttachment attachmentQuery = new SceneAttachment();
        if (StringUtils.isNotBlank(sceneAttachment.getSenceCode())) {
            attachmentQuery.setSenceCode(sceneAttachment.getSenceCode());
        }

        if (StringUtils.isNotBlank(sceneAttachment.getSenceName())) {
            attachmentQuery.setSenceName(sceneAttachment.getSenceName());
        }

        if (StringUtils.isNotBlank(sceneAttachment.getEnabled())) {
            attachmentQuery.setEnabled(sceneAttachment.getEnabled());
        }
        if (StringUtils.isNotBlank(sceneAttachment.getCeeaSmallModule())) {
            attachmentQuery.setCeeaSmallModule(sceneAttachment.getCeeaSmallModule());
        }

        QueryWrapper<SceneAttachment> wrapper = new QueryWrapper(attachmentQuery);
        ((QueryWrapper)wrapper.like(StringUtils.isNotBlank(sceneAttachment.getAttachmentName()), "ATTACHMENT_NAME", sceneAttachment.getAttachmentName())).orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo(this.list(wrapper));
    }

    @Transactional
    public void deleteFileUpLoad(Long sceneAttachmentId) {
        SceneAttachment sceneAttachment = (SceneAttachment)this.getById(sceneAttachmentId);
        UpdateWrapper<SceneAttachment> updateWrapper = new UpdateWrapper((new SceneAttachment()).setSceneAttachmentId(sceneAttachment.getSceneAttachmentId()));
        ((UpdateWrapper)updateWrapper.set("FILEUPLOAD_ID", (Object)null)).set("FILE_SOURCE_NAME", (Object)null);
        this.update(updateWrapper);
        this.fileCenterClient.delete(sceneAttachment.getFileuploadId());
    }

    @Transactional
    public void deleteSceneAttachment(Long sceneAttachmentId) {
        SceneAttachment sceneAttachment = (SceneAttachment)this.getById(sceneAttachmentId);
        this.removeById(sceneAttachmentId);
        Long fileuploadId = sceneAttachment.getFileuploadId();
        if(fileuploadId!=null){
            this.fileCenterClient.delete(fileuploadId);
        }

    }

    private void checkBeforeSaveOrUpdate(SceneAttachment sceneAttachment) {
        if (!StringUtils.isBlank(sceneAttachment.getSenceCode()) && !StringUtils.isBlank(sceneAttachment.getSenceName())) {
            if (StringUtils.isBlank(sceneAttachment.getAttachmentName())) {
                throw new BaseException("附件名称为空");
            }
        } else {
            throw new BaseException("流程场景为空");
        }
    }
}

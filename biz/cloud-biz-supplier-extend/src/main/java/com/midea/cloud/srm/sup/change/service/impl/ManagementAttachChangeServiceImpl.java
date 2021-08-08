package com.midea.cloud.srm.sup.change.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.OpConstant;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.change.entity.FileuploadChange;
import com.midea.cloud.srm.model.supplier.change.entity.ManagementAttachChange;
import com.midea.cloud.srm.sup.change.mapper.ManagementAttachChangeMapper;
import com.midea.cloud.srm.sup.change.service.ManagementAttachChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
/**
* <pre>
 *  后端开发 服务实现类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 12, 2021 5:16:45 PM
 *  修改内容:
 * </pre>
*/
@Service
public class ManagementAttachChangeServiceImpl extends ServiceImpl<ManagementAttachChangeMapper, ManagementAttachChange> implements ManagementAttachChangeService {

    @Autowired
    FileCenterClient fileCenterClient;

    @Transactional
    public void batchUpdate(List<ManagementAttachChange> managementAttachChangeList) {
        this.saveOrUpdateBatch(managementAttachChangeList);
    }


    @Override
    public void saveOrUpdateAttachs(List<ManagementAttachChange> attachFiles, Long companyId, Long changeId) {
        if(!CollectionUtils.isEmpty(attachFiles)){
            for(ManagementAttachChange managementAttachChange:attachFiles){
                managementAttachChange.setChangeId(changeId);
                managementAttachChange.setBusinessId(companyId);
                if(managementAttachChange.getManagementAttachChangeId() != null){
                    this.updateById(managementAttachChange);
                }else {
                    Long id = IdGenrator.generate();
                    managementAttachChange.setManagementAttachChangeId(id);
                    this.save(managementAttachChange);
                }
            }
        }
    }

    @Override
    public List<ManagementAttachChange> getByChangeId(Long changeId) {
        ManagementAttachChange managementAttachChange = new ManagementAttachChange();
        managementAttachChange.setChangeId(changeId);
        return this.list(new QueryWrapper<>(managementAttachChange));
    }

    @Override
    public void deleteByChangeId(Long changeId) {
        ManagementAttachChange managementAttachChange = new ManagementAttachChange();
        managementAttachChange.setChangeId(changeId);
        managementAttachChange.setOpType(OpConstant.add);
        List<ManagementAttachChange> addFiles = this.list(new QueryWrapper<>(managementAttachChange));
        ManagementAttachChange deletByChangeId = new ManagementAttachChange();
        deletByChangeId.setChangeId(changeId);
        this.remove(new QueryWrapper<>(deletByChangeId));
        for(ManagementAttachChange fileChange:addFiles){
            if(fileChange.getFileuploadId() != null){
                fileCenterClient.delete(fileChange.getFileuploadId());
            }
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    @Override
    public PageInfo<ManagementAttachChange> listPage(ManagementAttachChange managementAttachChange) {
        PageUtil.startPage(managementAttachChange.getPageNum(), managementAttachChange.getPageSize());
        List<ManagementAttachChange> managementAttachChanges = getManagementAttachChanges(managementAttachChange);
        return new PageInfo<>(managementAttachChanges);
    }

    public List<ManagementAttachChange> getManagementAttachChanges(ManagementAttachChange managementAttachChange) {
        QueryWrapper<ManagementAttachChange> wrapper = new QueryWrapper<>();
        return this.list(wrapper);
    }
}

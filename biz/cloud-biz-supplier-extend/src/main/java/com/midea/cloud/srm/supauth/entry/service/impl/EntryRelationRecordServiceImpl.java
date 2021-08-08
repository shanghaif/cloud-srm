package com.midea.cloud.srm.supauth.entry.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryRelationRecord;
import com.midea.cloud.srm.supauth.entry.mapper.EntryRelationRecordMapper;
import com.midea.cloud.srm.supauth.entry.service.IEntryRelationRecordService;

/**
* <pre>
 *  准入组织品类关系记录表 服务实现类
 * </pre>
*
* @author scc_sup_entry_relation_record
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 26, 2021 1:47:56 PM
 *  修改内容:
 * </pre>
*/
@Service
public class EntryRelationRecordServiceImpl extends ServiceImpl<EntryRelationRecordMapper, EntryRelationRecord> implements IEntryRelationRecordService {

    @Override
    @Transactional
    public void batchAdd(List<EntryRelationRecord> entryRelationRecordList) {
        //设置主键id
        for(EntryRelationRecord entryRelationRecord :entryRelationRecordList){
            Long id = IdGenrator.generate();
            entryRelationRecord.setRelationId(id);
        }
        this.saveBatch(entryRelationRecordList);
    }

    @Override
    @Transactional
    public void batchUpdate(List<EntryRelationRecord> entryRelationRecordList) {
        this.saveOrUpdateBatch(entryRelationRecordList);
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }


    @Override
    public PageInfo<EntryRelationRecord> listPage(EntryRelationRecord entryRelationRecord) {
        PageUtil.startPage(entryRelationRecord.getPageNum(), entryRelationRecord.getPageSize());
        List<EntryRelationRecord> entryRelationRecords = getEntryRelationRecords(entryRelationRecord);
        return new PageInfo<>(entryRelationRecords);
    }

    public List<EntryRelationRecord> getEntryRelationRecords(EntryRelationRecord entryRelationRecord) {
        QueryWrapper<EntryRelationRecord> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",entryRelationRecord.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",entryRelationRecord.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",entryRelationRecord.getStartDate()).
//                        le("CREATION_DATE",entryRelationRecord.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}

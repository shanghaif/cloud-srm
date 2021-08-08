package com.midea.cloud.srm.supauth.entry.service;

import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryRelationRecord;


/**
* <pre>
 *  准入组织品类关系记录表 服务类
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
public interface IEntryRelationRecordService extends IService<EntryRelationRecord>{
    /*
     批量增加
     */
    void batchAdd(List<EntryRelationRecord> entryRelationRecordList) throws IOException;

    /*
     批量更新
     */
    void batchUpdate(List<EntryRelationRecord> entryRelationRecordList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
   /*
   分页查询
    */
    PageInfo<EntryRelationRecord> listPage(EntryRelationRecord entryRelationRecord);

}

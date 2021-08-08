package com.midea.cloud.srm.sup.reminderrecord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import com.midea.cloud.srm.model.supplier.reminderrecord.entity.ReminderRecord;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IReminderRecordService extends IService<ReminderRecord> {

    void saveReminderRecord(ManagementAttach managementAttach);

    List<ReminderRecord> getRecordsById(ManagementAttach managementAttach);
}

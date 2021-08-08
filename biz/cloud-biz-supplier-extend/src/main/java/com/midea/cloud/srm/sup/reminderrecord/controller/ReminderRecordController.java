package com.midea.cloud.srm.sup.reminderrecord.controller;

import com.midea.cloud.common.enums.sup.ExpiredCertificateType;
import com.midea.cloud.srm.model.supplier.info.entity.ManagementAttach;
import com.midea.cloud.srm.model.supplier.reminderrecord.entity.ReminderRecord;
import com.midea.cloud.srm.sup.reminderrecord.service.IReminderRecordService;
import com.netflix.discovery.converters.Auto;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expireReminder")
public class ReminderRecordController {

    @Autowired
    IReminderRecordService reminderRecordService;

    @PostMapping("getReminderRecord")
    public List<ReminderRecord> getReminderRecord(@RequestBody ManagementAttach managementAttach) {

        return reminderRecordService.getRecordsById(managementAttach);
    }
}

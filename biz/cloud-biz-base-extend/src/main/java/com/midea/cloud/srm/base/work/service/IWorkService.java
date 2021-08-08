//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.midea.cloud.srm.base.work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.base.work.dto.WorkRequestDTO;
import com.midea.cloud.srm.model.base.work.entry.Work;

import java.util.List;

public interface IWorkService extends IService<Work> {
    List findList(WorkRequestDTO workRequestDTO);

    List<WorkCount> workCount();
}

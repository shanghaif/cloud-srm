package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaBu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Accessors(chain = true)
@Data
public class QuotaBuDTO extends QuotaBu {
    /**
     *事业部id集合
     */
    private List<String> buIdList;
}

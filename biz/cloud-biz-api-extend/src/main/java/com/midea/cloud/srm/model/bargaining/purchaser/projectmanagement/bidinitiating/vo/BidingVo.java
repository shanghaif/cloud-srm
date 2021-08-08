package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  招标基础信息表 返回前端封装类
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/29 下午 01:12
 *  修改内容:
 * </pre>
 */
@Data
public class BidingVo extends Biding implements Serializable {

    /**
     * 创建人中文名
     */
    private String createdByName;

    /**
     * 创建人所属部门
     */
    private String createdByDept;

}

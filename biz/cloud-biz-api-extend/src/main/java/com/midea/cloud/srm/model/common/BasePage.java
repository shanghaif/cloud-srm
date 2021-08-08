package com.midea.cloud.srm.model.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  分页基类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 15:30
 *  修改内容:
 * </pre>
 */
@Data
public class BasePage implements Serializable {

    private static final long serialVersionUID = 1880696311647662038L;

    @TableField(exist = false)
    private Integer pageNum; // 页码
    @TableField(exist = false)
    private Integer pageSize; // 页数
    @TableField(exist = false)
    private String searchUrl; // 记录跳转url，如果有必要
    @TableField(exist = false)
    private Boolean isNeedTotal; // 是否需要统计总数
    @TableField(exist = false)
    private String createdUserName; // 创建人昵称
    @TableField(exist = false)
    private String lastUpdatedUserName; // 更新人昵称

    public void buildPage(Integer pageNum, Integer pageSize, String searchUrl, Boolean isNeedTotal) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.searchUrl = searchUrl;
        this.isNeedTotal = isNeedTotal;
    }

    public void buildPage(Integer pageNum, Integer pageSize, String searchUrl) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.searchUrl = searchUrl;
        this.isNeedTotal = true;
    }

    public void buildPage(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.searchUrl = "";
        this.isNeedTotal = true;
    }

}

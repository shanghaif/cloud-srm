package com.midea.cloud.srm.model.inq.inquiry.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaSource;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class QuotaSourceDTO extends QuotaSource {

        /**
         * 业务实体名称
         */
        @TableField("ORG_NAME")
        private String orgName;


        /**
         * 物料名称
         */
        @TableField("ITEM_NAME")
        private String itemName;


        /**
         * 物料小类名称
         */
        @TableField("CATEGORY_NAME")
        private String categoryName;

        /**
         * 需求数量
         */
        @TableField("NEED_NUM")
        private BigDecimal needNum;

        /**
         * 单位
         */
        @TableField("UNIT")
        private String unit;

        /**
         * 单价（含税）
         */
        @TableField("TAX_PRICE")
        private BigDecimal taxPrice;

        /**
         * 币种
         */
        @TableField("CURRENCY")
        private String currency;


        /**
         * 配额比例(实际比例)
         */
        @TableField("QUOTA_PROPORTION")
        private BigDecimal quotaProportion;


        /**
         * 价格开始生效日期
         */
        @TableField("START_TIME")
        private LocalDate startTime;

        /**
         * 价格失效日期
         */
        @TableField("END_TIME")
        private LocalDate endTime;

}


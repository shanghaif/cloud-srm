package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 出参 - 寻源单生成结果
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenerateSourceFormResult implements Serializable {

    private SourceForm sourceForm;
}

package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo;

import lombok.*;

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

    private SourceForm      sourceForm;
}

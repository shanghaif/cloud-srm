package com.midea.cloud.srm.model.pm.pr.requirement.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <pre>
 *  维护可下单数量入参
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 16:17
 *  修改内容:
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class FollowNameParam implements Serializable {

    // 寻源单信息
    private SourceForm  sourceForm;


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SourceForm implements Serializable {

        private String  formNum;
        private String  formTitle;
    }

}
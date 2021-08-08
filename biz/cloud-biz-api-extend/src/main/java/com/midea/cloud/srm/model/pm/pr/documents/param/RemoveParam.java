package com.midea.cloud.srm.model.pm.pr.documents.param;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * @author tanjl11
 * @date 2020/11/27 17:31
 */
@Data
public class RemoveParam {
    private String formNo;
    private Map<String,List<String>> params;
}

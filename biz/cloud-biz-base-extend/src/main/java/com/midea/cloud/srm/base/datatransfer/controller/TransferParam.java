package com.midea.cloud.srm.base.datatransfer.controller;

import lombok.Data;

import java.util.List;

/**
 * @author tanjl11
 * @date 2020/11/17 20:56
 */
@Data
public class TransferParam {
    private String sourcingNo;
    private List<String> ouName;
}

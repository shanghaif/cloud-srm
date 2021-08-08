package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PaymentDayEnum {
    IMMEDIATE_PAYMENT("IMMEDIATE_PAYMENT", 0),
    NET7("NET7", 7),
    NET75("NET75", 75),
    NET14("NET14", 14),
    NET30("NET30", 30),
    NET45("NET45", 45),
    NET90("NET90", 90),
    NET120("NET120", 120),
    NET180("NET180", 180),
    NET360("NET360", 360),
    NET15("NET15", 15),
    NET60("NET60", 60),
    NET135("NET135", 135),
    NET150("NET150", 150),
    LONGI_ASSET_PAYMENT_TERMS("LONGI_ASSET_PAYMENT_TERMS", 0);

    private String code;
    private Integer day;

    public static Integer getDayFromCode(String code) {
        for (PaymentDayEnum value : values()) {
            if (Objects.equals(code, value.getCode())) {
                return value.getDay();
            }
        }
        return 0;
    }
}

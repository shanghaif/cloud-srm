package com.midea.cloud.common.constants;

/**
 * <pre>
 *  快速查询 常量类
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 17:04
 *  修改内容:
 * </pre>
 */
public final class QuicksearchConfigFields {
    private QuicksearchConfigFields() {
    }

    public static final String DATA_FORMAT = "{\"limit\" : 20,\"events\" : {},\"xid\" : \"%s\",\"directDelete\" : false,\"idColumn\" : \"%s\",\"confirmDelete\" : true," +
            "\"confirmRefresh\" : true,\"defCols\" : {}," +
            "\"autoLoad\" : true}";
    public static final String DATA_FIELD_FORMAT = "\n" +
            "\n" +
            "{\"define\":\"%s\",\"name\":\"%s\"," +
            "\"relation\":\"%s\",\"label\":\"%s\",\"type\":\"%s\"}";
    public static final class Fields {
        private Fields() {
        }

        public static final String LABEL = "label";
        public static final String WIDTH = "width";
        public static final String NAME = "name";
        public static final String CONFIG = "config";
        public static final String QUERYCONDITION = "queryCondition";
        public static final String WHERE = "where";
        public static final String ALLMATCH = "allMatch";
        public static final String EQUALMATCH = "equalMatch";
        public static final String LEFTMATCH = "leftMatch";
        public static final String JA = "ja";
        public static final String QUICK_KEY = "_quickKey";

    }

}

package com.midea.cloud.srm.perf.scoring.constants;

/**
 * <pre>
 *  折算方式
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/2/5 9:41
 *  修改内容:
 * </pre>
 */
public enum QuoteMode {

    DIRECT_QUOTE("直接取值","DIRECT_QUOTE"),
    TEXT_CONVERSION("按文本折算","TEXT_CONVERSION"),
    INTERVAL_CONVERSION("按区间折算","INTERVAL_CONVERSION");


    private String name;
    private String value;

    private QuoteMode(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**根据value获取枚举值**/
    private static QuoteMode get(String value){
        for(QuoteMode quoteMode: QuoteMode.values()){
            if(quoteMode.value.equals(value)){
                return quoteMode;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

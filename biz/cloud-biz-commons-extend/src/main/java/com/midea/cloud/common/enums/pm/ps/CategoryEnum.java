package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *  物料类别枚举，代码中需要用到的可以写在此，避免魔法值出现
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/8 10:39
 *  修改内容:
 * </pre>
 */
public enum  CategoryEnum {

    //物料大类，服务类
    BIG_CATEGORY_SERVER("服务类" , "40");

    private String categoryName;

    private String categoryCode;

    private CategoryEnum(String categoryName ,String categoryCode ){
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}

package com.midea.cloud.gernator.util;

import com.midea.cloud.gernator.autocode.entity.TableDefDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoCodeUtil {
    //功能模块转换标识
    public static String getSwitchModule(String moduleName){
        String mName = "base";//默认是base模块
        if(moduleName != null && !"".equals(moduleName)) {
            moduleName= moduleName.toUpperCase();
            if(moduleName.contains("CLOUD_BIZ_BASE")){
                mName = "base";
            }else if(moduleName.contains("CLOUD_BIZ_SUPPLIER")){
                mName = "sup";
            }else if(moduleName.contains("CLOUD_BIZ_SUPPLIER_AUTH")){
                mName = "supauth";
            }else if(moduleName.contains("CLOUD_BIZ_SUPPLIER_COOPERATE")){
                mName = "supcooperate";
            }else if(moduleName.contains("NSRM_CLOUD_LOG")){
                mName = "log";
            }else if(moduleName.contains("NSRM_CLOUD_FILE")){
                mName = "file";
            }else if(moduleName.contains("NSRM_CLOUD_FLOW")){
                mName = "flow";
            }else if(moduleName.contains("NSRM_CLOUD_BIZ_BIDDING")){
                mName = "bid";
            }else if(moduleName.contains("NSRM_CLOUD_BIZ_INQUIRY")){
                mName = "inq";
            }else if(moduleName.contains("NSRM_CLOUD_BIZ_CONTRACT")){
                mName = "cm";
            }else if(moduleName.contains("NSRM_CLOUD_BIZ_PERFORMANCE")){
                mName = "perf";
            }else if(moduleName.contains("NSRM_CLOUD_RBAC")){
                mName = "rbc";
            }
        }
        return mName;
    }

    //功能模块转换标识
    public static String getAplicationNameByModule(String moduleName){
        String aplicationName = "base";//默认是base模块
        if(moduleName != null && !"".equals(moduleName)) {
            moduleName= moduleName.toUpperCase();
            if(moduleName.contains("CLOUD_BIZ_BASE")){
                aplicationName = "api-base";
            }else if(moduleName.contains("CLOUD_BIZ_SUPPLIER")){
                aplicationName = "api-sup";
            }else if(moduleName.contains("CLOUD_BIZ_SUPPLIER_AUTH")){
                aplicationName = "api-sup-auth";
            }else if(moduleName.contains("CLOUD_BIZ_SUPPLIER_COOPERATE")){
                aplicationName = "api-sup-ce";
            }else if(moduleName.contains("SRM_CLOUD_LOG")){
                aplicationName = "api-log";
            }else if(moduleName.contains("SRM_CLOUD_FILE")){
                aplicationName = "api-file";
            }else if(moduleName.contains("SRM_CLOUD_FLOW")){
                aplicationName = "file-center";
            }else if(moduleName.contains("SRM_CLOUD_BIZ_BIDDING")){
                aplicationName = "api-bid";
            }else if(moduleName.contains("SRM_CLOUD_BIZ_INQUIRY")){
                aplicationName = "api-inq";
            }else if(moduleName.contains("SRM_CLOUD_BIZ_CONTRACT")){
                aplicationName = "api-cm";
            }else if(moduleName.contains("SRM_CLOUD_BIZ_PERFORMANCE")){
                aplicationName = "api-pef";
            }else if(moduleName.contains("SRM_CLOUD_RBAC")){
                aplicationName = "api-rbac";
            }
        }
        return aplicationName;
    }
    //分解头分区
    public static Map<String,List<TableDefDTO>> getHeadPartMap(List<TableDefDTO> selectHeadFileList){
        Map<String,List<TableDefDTO>>  partMap = new HashMap<>();//分区组装
        for(TableDefDTO tableDefDTO : selectHeadFileList){
            String partName = tableDefDTO.getPartName();
            if(partName == null) continue;
            if(partMap.get(partName) != null){
                partMap.get(partName).add(tableDefDTO);
            }else {
                List<TableDefDTO> list = new ArrayList<>();
                list.add(tableDefDTO);
                partMap.put(partName,list);
            }
        }
        return  partMap;
    }

    //分解头分区
    public static String getPk(List<TableDefDTO> selectHeadFileList){
        String pk = "";
        Map<String,List<TableDefDTO>>  partMap = new HashMap<>();//分区组装
        for(TableDefDTO tableDefDTO : selectHeadFileList){
            String isPk = tableDefDTO.getIsPk();
            if("1" .equals(isPk)) {
                pk = tableDefDTO.getJavaCode();

            }
        }
        return  pk;
    }
    public static String getTableName(String tableNme){
        //平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名
        tableNme = tableNme.toLowerCase();//全部转小写
        String tableNameArry[] = tableNme.split("_");
        String tName = "";
        for(int i=2;i<tableNameArry.length;i++){
            if(i == tableNameArry.length-1 ){
                tName = tName + tableNameArry[i];
            }else {
                tName = tName + tableNameArry[i]+"_";
            }
        }
        return tName;
    }
    public static void   setJavaType (List<TableDefDTO> selectHeadFileList){
        for(TableDefDTO tableDefDTO :selectHeadFileList){
            String javaType = "";//字段在java中类型
            String type = tableDefDTO.getFiledType();//字段类型
            if ("VARCHAR".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "String";
            } else if ("VARCHAR2".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "String";
            } else if ("CHAR".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "String";
            } else if ("INTEGER".equalsIgnoreCase(type.toUpperCase()) || "BIGINT".equals(type.toUpperCase())) {
                javaType = "Long";
            } else if ("INT".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "Long";
            } else if ("DECIMAL".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "Long";
            } else if ("NUMBER".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "Long";;
            } else if ("TINYINT".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "Boolean";
            } else if ("BLOB".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "Blob";
                //数据库只需要定义Data类型，不要定义其他类型
            } else if (("DATE".equals(type.toUpperCase())) || "DATETIME".equalsIgnoreCase(type.toUpperCase()) ) {
                //创建时间和修改时间需要用Timestamp
                javaType = "Date";//实体类型为Date


            } else if ("TEXT".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "String";
            } else if ("LONGTEXT".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "String";
            } else if ("Double".equalsIgnoreCase(type.toUpperCase())) {
                javaType = "Double";
            }
            tableDefDTO.setJavaType(javaType);
        }




    }
    public static void main(String args[]) {
        System.out.println(getTableName("ceea_inquiry_quota_adjust"));
    }
}
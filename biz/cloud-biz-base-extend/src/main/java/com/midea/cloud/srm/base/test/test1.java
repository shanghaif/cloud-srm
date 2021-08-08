package com.midea.cloud.srm.base.test;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;


/**
 * 获取数据库参数和参数注释，方便前端调试
 */
public class test1 {
    //数据库表名
    private static String tableNames = "ceea_logistics_region";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://10.0.10.47:3306/cloud_biz_contract?useSSL=false";
    private static String user = "appdba";
    private static String password = "srm82Dev!s";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stamt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);//载入驱动器(即Java-connector-Mysql-bin.jar文件)
            conn = DriverManager.getConnection(url, user, password);
            stamt = conn.createStatement();
            System.out.println("连接开始");

            String sql = "" +
                    "SELECT SCHEMA_NAME AS `database`,b.table_name AS 'tableName'  FROM INFORMATION_SCHEMA.SCHEMATA a\n" +
                    "LEFT JOIN  information_schema.TABLES b ON b.TABLE_SCHEMA=a.SCHEMA_NAME\n" +
                    "WHERE TABLE_SCHEMA=a.SCHEMA_NAME and TABLE_TYPE = 'base table';";
            rs = stamt.executeQuery(sql);
            String tablaku = "";
            while (rs.next()) {
                if (tableNames.equals(rs.getString("tableName"))) {
                    tablaku = rs.getString("Database");
                    sql = "SELECT\n" +
                            "column_name,column_comment\n" +
                            "FROM information_schema.columns\n" +
                            "WHERE\n" +
                            "table_schema= '" + tablaku + "' AND table_name = '" + tableNames + "'";
                    rs = stamt.executeQuery(sql);
                    System.out.printf("%-40s", "参数名称");
                    System.out.printf("参数意思\n");
                    while (rs.next()) {
                        System.out.printf("%-40s", changeToJavaFiled(rs.getString("column_name")));
                        System.out.printf(rs.getString("column_comment") + "\n");
                    }
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {//程序结束关闭连接
            try {
                if (rs != null) rs.close();
                if (stamt != null) stamt.close();
                if (conn != null) conn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String changeToJavaFiled(String field) {
        String[] fields = field.toLowerCase().split("_");
        StringBuilder sbuilder = new StringBuilder(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            char[] cs = fields[i].toCharArray();
            cs[0] -= 32;
            sbuilder.append(String.valueOf(cs));
        }
        return sbuilder.toString();
    }
}
class TestEntity {
    private String tablebase;
    private String tableName;

    public TestEntity(String tablebase, String tableName) {
        this.tablebase = tablebase;
        this.tableName = tableName;
    }

    public String getTablebase() {
        return tablebase;
    }

    public void setTablebase(String tablebase) {
        this.tablebase = tablebase;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
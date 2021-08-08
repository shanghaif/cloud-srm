package com.midea.cloud.gernator.autocode.entity;
import java.io.Serializable;
import java.sql.Timestamp;

public class TableNameDTO implements Serializable {

	public String databaseName;//数据库名称
    public String tableName;//表名
	public String tableComment;//表注释
	public Timestamp creationDate;//创建时间
	public String keyName;//查询关键字

	public String getDatabaseName() { return databaseName; }

	public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}

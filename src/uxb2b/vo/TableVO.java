package uxb2b.vo;

import java.util.List;
import java.util.Map;

/**
 *  轉換SQL CREATE TABLE為HTML 的VO
 */
public class TableVO {
	private String tableName;
	private String tableChineseName;
	private Map<String,List<ColumnVO>> map;//key:tableName ,value:columnList
	private List<ColumnVO> columnList;
	private String primaryKey;
	private String other;
	private String foreign;
	private String error;
    
    
    public TableVO() {
        
    }
    
    public String getTableName() {
        return tableName == null ? "" : tableName.toUpperCase();
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getTableChineseName() {
        return tableChineseName;
    }
    public void setTableChineseName(String tableChineseName) {
        this.tableChineseName = tableChineseName;
    }

    public List<ColumnVO> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnVO> columnList) {
        this.columnList = columnList;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Map<String, List<ColumnVO>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<ColumnVO>> map) {
        this.map = map;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

	public String getForeign() {
		return foreign;
	}

	public void setForeign(String foreign) {
		this.foreign = foreign;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


}

package uxb2b.vo;


/**
 *  轉換SQL column 的VO
 */
public class ColumnVO {
    private String columnName;
    private String columnType;
    private boolean isNotNull;//true = not null;false = null
    private String note;
    private String columnChineseName;
    private String detail;//欄位說明
    
    public ColumnVO() {
        
    }
    

    public String getColumnName() {
        return columnName == null ? "" : columnName.toUpperCase();
    }


    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }


    public String getColumnType() {
        return columnType == null ? "" : columnType.toUpperCase();
    }
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public boolean isNotNull() {
        return isNotNull;
    }


    public void setNotNull(boolean isNotNull) {
        this.isNotNull = isNotNull;
    }


    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getColumnChineseName() {
        return columnChineseName;
    }
    public void setColumnChineseName(String columnChineseName) {
        this.columnChineseName = columnChineseName;
    }


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}
    
    
}

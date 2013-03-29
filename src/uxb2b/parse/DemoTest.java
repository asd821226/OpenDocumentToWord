package uxb2b.parse;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import uxb2b.util.FileUtils;
import uxb2b.util.ParseSQLUtils;
import uxb2b.vo.ColumnVO;
import uxb2b.vo.TableVO;

public class DemoTest {

    private Map<String, TableVO> voMap;
    private Integer sortType;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        DemoTest test = new DemoTest();
        test.parse();
    }

    private void parse() throws Exception {
        ParseSQLUtils utils = new ParseSQLUtils();

        // String sqlFiles = project.getSqlFiles();
        // SVNRepository repository = SVNUtil.createSVNRepoitory(projectId);
        // String[] files = sqlFiles.replaceAll("\r", "").split("\n");

        // TODO:TEST:read db script file
        String aInputFileName = "src/uxb2b/sql/Script_FN_LB04_script_phase2.sql";
        
        byte[] readData = FileUtils.read(aInputFileName);
        
        utils.parse(readData, "big5");
        voMap = utils.getVoMap();

        // 依字母排序
        Map<String, TableVO> sortMap = new TreeMap<String, TableVO>();
        sortMap.putAll(voMap);
        voMap = sortMap;
        
        System.out.println(voMap);
        
        String tabString = "    ";
        for (Map.Entry<String, TableVO> entry : voMap.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : "
                    + entry.getValue());
            System.out.println("Table_Name:" + entry.getValue().getTableName());
            System.out.println("Table_Chinese_Name:" + entry.getValue().getTableChineseName());
            System.out.println("Table_PrimaryKey:" + entry.getValue().getPrimaryKey());
            System.out.println("Table_ForeignKey:" + entry.getValue().getForeign());
            System.out.println("Table_Other_Info:" + entry.getValue().getOther());
            System.out.println("Table_Error_Info:" + entry.getValue().getError());
            for(ColumnVO subEntry : entry.getValue().getColumnList()) {
                System.out.println(tabString + "Column_Name:" + subEntry.getColumnName());
                System.out.println(tabString + "Column_Chinese_Name:" + subEntry.getColumnChineseName());
                System.out.println(tabString + "Column_Type:" + subEntry.getColumnType());
                System.out.println(tabString + "Column_Detail:" + subEntry.getDetail());
                System.out.println(tabString + "Column_Note:" + subEntry.getNote());
                System.out.println(tabString + "Column_isNotNull:" + subEntry.isNotNull());
                System.out.println("");
            }
            System.out.println("");
        }
        
    }

    public Map<String, TableVO> getVoMap() {
        return voMap;
    }

    public void setVoMap(Map<String, TableVO> voMap) {
        this.voMap = voMap;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}

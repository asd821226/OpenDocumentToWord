package uxb2b.parse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import uxb2b.util.FileUtils;
import uxb2b.util.ParseSQLUtils;
import uxb2b.vo.ColumnVO;
import uxb2b.vo.TableVO;

/**
 * 讀取sql_script,
 * 並寫入至txt_file.
 * 
 * @author tony
 *
 */
public class SqlParseDemo {

    private Map<String, TableVO> voMap;
    private Integer sortType;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SqlParseDemo test = new SqlParseDemo();
        test.parse();
    }

    private void parse() throws Exception {
        ParseSQLUtils utils = new ParseSQLUtils();

        // String sqlFiles = project.getSqlFiles();
        // SVNRepository repository = SVNUtil.createSVNRepoitory(projectId);
        // String[] files = sqlFiles.replaceAll("\r", "").split("\n");

        // read db script file
        String aInputFileName = "src/uxb2b/sql/Script_FN_LB04_script_phase2.sql";

        byte[] readData = FileUtils.read(aInputFileName);

        utils.parse(readData, "big5");
        voMap = utils.getVoMap();

        // 依字母排序
        Map<String, TableVO> sortMap = new TreeMap<String, TableVO>();
        sortMap.putAll(voMap);
        voMap = sortMap;

        // System.out.println(voMap);
        
        // console out:
        String tabString = "    ";
        for (Map.Entry<String, TableVO> entry : voMap.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : "
                    + entry.getValue());

            System.out.println("Table_Name:" + entry.getValue().getTableName());
            System.out.println("Table_Chinese_Name:"
                    + entry.getValue().getTableChineseName());
            System.out.println("Table_PrimaryKey:"
                    + entry.getValue().getPrimaryKey());
            System.out.println("Table_ForeignKey:"
                    + entry.getValue().getForeign());
            System.out.println("Table_Other_Info:"
                    + entry.getValue().getOther());
            System.out.println("Table_Error_Info:"
                    + entry.getValue().getError());

            for (ColumnVO subEntry : entry.getValue().getColumnList()) {
                System.out.println(tabString + "Column_Name:"
                        + subEntry.getColumnName());
                System.out.println(tabString + "Column_Chinese_Name:"
                        + subEntry.getColumnChineseName());
                System.out.println(tabString + "Column_Type:"
                        + subEntry.getColumnType());
                System.out.println(tabString + "Column_Detail:"
                        + subEntry.getDetail());
                System.out.println(tabString + "Column_Note:"
                        + subEntry.getNote());
                System.out.println(tabString + "Column_isNotNull:"
                        + subEntry.isNotNull());
                System.out.println("");
            }

            System.out.println("");
        }

        // write to txt
        try {

            File file = new File("build/sql/sql_data.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            // write to file:start
            for (Map.Entry<String, TableVO> entry : voMap.entrySet()) {
                
//                bw.write("Key : " + entry.getKey() + " Value : "
//                        + entry.getValue());
//                bw.newLine();
                
                bw.write("Key : " + entry.getKey());
                bw.newLine();
                
                bw.write("Table_Name:" + entry.getValue().getTableName());
                bw.newLine();
                
                bw.write("Table_Chinese_Name:"
                        + entry.getValue().getTableChineseName());
                bw.newLine();
                
                bw.write("Table_PrimaryKey:"
                        + entry.getValue().getPrimaryKey());
                bw.newLine();
                
                bw.write("Table_ForeignKey:"
                        + entry.getValue().getForeign());
                bw.newLine();
                
                bw.write("Table_Other_Info:"
                        + entry.getValue().getOther());
                bw.newLine();
                
                bw.write("Table_Error_Info:"
                        + entry.getValue().getError());
                bw.newLine();

                for (ColumnVO subEntry : entry.getValue().getColumnList()) {
                    
                    bw.write(tabString + "Column_Name:"
                            + subEntry.getColumnName());
                    bw.newLine();
                    
                    bw.write(tabString + "Column_Chinese_Name:"
                            + subEntry.getColumnChineseName());
                    bw.newLine();
                    
                    bw.write(tabString + "Column_Type:"
                            + subEntry.getColumnType());
                    bw.newLine();
                    
                    bw.write(tabString + "Column_Detail:"
                            + subEntry.getDetail());
                    bw.newLine();
                    
                    bw.write(tabString + "Column_Note:"
                            + subEntry.getNote());
                    bw.newLine();
                    
                    bw.write(tabString + "Column_isNotNull:"
                            + subEntry.isNotNull());
                    bw.newLine();
                    
                    bw.newLine();
                }
                
            }
            // write to file:end
            
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
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

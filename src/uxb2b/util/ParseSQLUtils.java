package uxb2b.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import uxb2b.vo.ColumnVO;
import uxb2b.vo.TableVO;

public class ParseSQLUtils {
    
    private Map<String, TableVO> voMap = new LinkedHashMap<String, TableVO>();
    //private List<TableVO> voList = new ArrayList<TableVO>();
    private int i;//行號

    public void parse(byte[] data, String encoding) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data), encoding));
        
        List<ColumnVO> columnList = null;
        TableVO vo = null;
        Map map = null;
        String tableChineseName = "";
        String line = null;
        while ((line = br.readLine()) != null) {
            i++;
            line = line.trim().replace("\t", " ");
            if(line.contains("--")){//註解使用--
                line = line.replace("--", "/* ") + " */";
            }
            if (line.indexOf("/*") > 0) {//註解剛好空1格變成多1格空白
                StringBuffer buf = new StringBuffer(line);
                int noteIndex = line.indexOf("/*");
                if (noteIndex > 1) {
                    line = buf.insert(noteIndex - 1, "  ").toString();
                }
            }
            if (line.toUpperCase().indexOf("DECIMAL") > 0 && !line.toUpperCase().contains("ALTER TABLE")) {//DECIMAL多1格空白,例:DECIMAL(13, 2),
                StringBuffer buf = new StringBuffer(line);
                int noteIndex = line.indexOf(",");
                if (noteIndex > 1) {
                    line = buf.insert(noteIndex + 1, "  ").toString();
                }
            }
            String[] strings = line.trim().split(" ");
            //去空值         
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<strings.length; i++) {
                if("".equals(strings[i])) {
                    continue;
                }
                sb.append(strings[i]);
                if(i != strings.length - 1) {
                    sb.append(";");
                }
            }
            
            strings = sb.toString().split(";");
            if(strings[0].equalsIgnoreCase("USE")){
                 continue;
            }
            
            if (line.toUpperCase().contains("ALTER TABLE")) {
                //only for LB04
                int idx = line.indexOf("/*");
                String memo = "";
                if (idx >= 0) {
                    memo = line.substring(idx).replace("/*", "").replace("*/", "");
                }
                Pattern p = Pattern.compile("alter\\s+table\\s+(\\S+)\\s+ADD\\s+(.*);", Pattern.CASE_INSENSITIVE); 
                Matcher m = p.matcher(line);
                if (m.find()) {
                    String tableName = m.group(1);
                    String col = m.group(2);
                    String[] s  = col.split(" ");
                    ColumnVO colVO = new ColumnVO();
                    colVO.setColumnName(s[0]);
                    colVO.setColumnType(s[1]);
                    colVO.setColumnChineseName(memo);
                    TableVO tableVO = voMap.get(tableName);
                    List<ColumnVO> cols = tableVO.getColumnList();
                    cols.add(colVO);
                }
            } else if (line.toUpperCase().indexOf("TABLE") > 0) {// CREATE TABLE
                vo = new TableVO();
                vo.setTableName(strings[2].trim().replace("(", ""));
                vo.setTableChineseName(tableChineseName);
                columnList = new ArrayList<ColumnVO>();
                map = new HashMap();
            } else if (line.indexOf(";") > 0) {// );
                vo.setColumnList(columnList);
                voMap.put(vo.getTableName(), vo);
                vo.setMap(map);
                tableChineseName = "";
            } else if (strings.length == 1) {// 空白列
            } else if (line.toUpperCase().contains("CREATE INDEX")) {
            } else if (line.startsWith("/*")) {
                tableChineseName = line.replace("/*", "").replace("*/", "")
                        .trim();
            } else if (strings[0].equalsIgnoreCase("PRIMARY") || (strings[0].equalsIgnoreCase("constraint") && strings[2].equalsIgnoreCase("PRIMARY"))) {// PRIMARY KEY
                line = line.trim().split("\\(")[1];
                vo.setPrimaryKey(line.replace(")", "").trim());
            } else if (strings[0].equalsIgnoreCase("constraint") && strings[2].equalsIgnoreCase("UNIQUE")) {
                // do nothing
            } else if (strings[0].equalsIgnoreCase("FOREIGN")) {
                vo.setForeign(line.trim().split("\\(")[1].replace(")", "")
                        .trim());
            } else {
                ColumnVO columnVO = new ColumnVO();
                if(!line.trim().contains(",")){
                      vo.setError("第"+i+"行檢查逗點");
                }
                columnVO.setColumnName(strings[0].trim());

                int i = 0;
                if (strings.length <= 2) {
                    columnVO.setColumnType(strings[1].trim().replace(",", ""));
                } else {
                    if (strings[1].toUpperCase().contains("DECIMAL")) {
                    	if(strings[2].contains("/*")){
                    		strings[2] = strings[2].replace("/*", "");//例:DECIMAL(4),
	                    	columnVO.setColumnType(strings[1].replace(",", "").trim()
	                                + strings[2].replace(",", "").trim());
                    	}else{
	                    	columnVO.setColumnType(strings[1].trim()
	                                + strings[2].replace(",", "").trim());
                    	}
                    } else {
                        columnVO.setColumnType(strings[1].trim().replace(",",
                                ""));
                    }
                    if (strings[2].toUpperCase().contains("NOT")) {// not null
                        columnVO.setNotNull(true);
                        i = 4;
                    } else if(strings[1].toUpperCase().contains("DECIMAL") && strings[3].toUpperCase().contains("NOT")) {//DECIMAL(13,2) not null
                    	columnVO.setNotNull(true);
                        i = 5;
                    } else {
                        columnVO.setNotNull(false);// null
                        i = 3;
                    }
                    String note = "";
                    for (int j = i; j < strings.length; j++) {
                        if (strings[j].indexOf("/*") < 0) {
                            note = note + strings[j].trim();
                        } else {
                            break;
                        }
                        if (note.endsWith(",")) {
                            note = note.trim().substring(0, note.length() - 1);
                        }
                        columnVO.setNote(note);
                    }
                }
                if (line.indexOf("/*") > 0) {
                    //欄位中文名字
                    String chineseName = line.substring(
                            line.indexOf("/*"), line.length());
                    chineseName = chineseName.replace("/*", "")
                            .replace("*/", "").trim();
                    if (chineseName.indexOf("||") > 0) {
                        columnVO.setDetail(chineseName.split("\\|\\|")[1]
                                .replace("\\", "<br>"));
                        chineseName = chineseName.split("\\|\\|")[0];
                    }
                    if (chineseName.indexOf("\\") > 0) {
                        columnVO.setDetail(chineseName.trim().replace("\\",
                                "<br>"));
                        chineseName = "";
                    }
                    columnVO.setColumnChineseName(chineseName);
                    
                }
                if (columnList != null) {
                    columnList.add(columnVO);
                    map.put(vo.getTableName(), columnList);
                }
            }
        }
    }


    public Map<String, TableVO> getVoMap() {
        return voMap;
    }
    
    public static void main(String[] args) throws Exception {
        ParseSQLUtils utils = new ParseSQLUtils();
        
        String fileName = "D:/svn/LB04/版本號4643/lb04_script.sql";
        fileName = "D:/Script_FN_LB04_script_phase2.sql";
        byte[] data0 = FileUtils.readFileToByteArray(new File(fileName));
        utils.parse(data0, "big5");
        
//        fileName = "D:\\MyDocuments\\Data\\projects\\LB04_doc_it\\SA\\SQL_SCRIPT\\20130104上版sql\\Script_FN_LB04_script.sql";
//        //fileName = "D:/MyDocuments/Data/projects/TCB05/doc_it/SA/SQL_SCRIPT/script.sql";
//        byte[] data = FileUtils.readFileToByteArray(new File(fileName));
//        utils.parse(data, "big5");
    }

}

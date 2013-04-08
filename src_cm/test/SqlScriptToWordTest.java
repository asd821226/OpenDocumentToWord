package test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import test.util.ParseSQLUtils;


import com.uxb2b.word.main.SqlScriptToWord;
import com.uxb2b.word.main.utils.FileUtils;
import com.uxb2b.word.main.vo.TableVO;

public class SqlScriptToWordTest {

    /**
     * test code
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String sqlFile = "src/uxb2b/sql/Script_FN_LB04_script_phase2.sql";
        // String sqlFile = "src/uxb2b/sql/tcb_script.sql";
        Map<String, TableVO> voMap = SqlScriptToWordTest.parseScriptToVo(sqlFile);
        SqlScriptToWord.parseVo(voMap);

    }

    public static Map<String, TableVO> parseScriptToVo(String sqlFile) throws Exception {
        
        ParseSQLUtils utils = new ParseSQLUtils();
        Map<String, TableVO> voMap = new HashMap<String, TableVO>();
        
        byte[] readData = FileUtils.read(sqlFile);

        utils.parse(readData, "big5");
        voMap = utils.getVoMap();

        // 依字母排序
        Map<String, TableVO> sortMap = new TreeMap<String, TableVO>();
        sortMap.putAll(voMap);
        voMap = sortMap;
        
        return voMap;
    }

}

package uxb2b.working;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

import uxb2b.util.FileUtils;
import uxb2b.util.ParseSQLUtils;
import uxb2b.vo.TableVO;
import uxb2b.zip.WordZip;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SqlScriptToWordDemo {

    public static Map<String, TableVO> voMap;
    private Integer sortType;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // TODO:TEST:read db script file
        String sqlFile = "src/uxb2b/sql/Script_FN_LB04_script_phase2.sql";
        SqlScriptToWordDemo scriptContent = new SqlScriptToWordDemo();
        scriptContent.parse(sqlFile);

        // into freemarker
        // Freemarker configuration object
        String templateFile = "src/uxb2b/working/ftl/helloworld.ftl";

        Configuration cfg = new Configuration();
        try {
            // Load template from source folder
            Template template = cfg.getTemplate(templateFile);

//            Map<String, TableVO> dataTest = new HashMap<String, TableVO>();
//            dataTest = voMap;

            // TEST DATA
            
            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(voMap, out);
            out.flush();

            // File output
            // Writer file = new FileWriter(new File(
            // "word/word/document.xml"));
            // template.process(voMap, file);
            // file.flush();
            // file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        // file to zip
        // try {
        //
        // // zip output file:副檔名可直接命名為docx
        // FileOutputStream f = new FileOutputStream("build/word/xxx.docx");
        //
        // //
        // // CheckedOutputStream ch = new CheckedOutputStream(f, new CRC32());
        // // Adler32
        // CheckedOutputStream ch = new CheckedOutputStream(f, new Adler32());
        // ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
        // ch));
        //
        // // zip input folder
        // File file = new File("word");
        //
        // WordZip.fileZip(out, file);
        //
        // out.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

    }

    private void parse(String sqlFile) throws Exception {
        ParseSQLUtils utils = new ParseSQLUtils();

        byte[] readData = FileUtils.read(sqlFile);

        utils.parse(readData, "big5");
        voMap = utils.getVoMap();

        // 依字母排序
        Map<String, TableVO> sortMap = new TreeMap<String, TableVO>();
        sortMap.putAll(voMap);
        voMap = sortMap;

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

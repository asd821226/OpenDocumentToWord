package com.uxb2b.word.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

import uxb2b.vo.TableVO;

import com.uxb2b.word.main.utils.ZipUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SqlScriptToWord {
    
    private final static String SQL_TABLE_WORD_TEMPLATE_PATH = "src/com/uxb2b/word/main/word_template/sql_table/";
    private final static String SQL_TABLE_FTL_PATH = "src/com/uxb2b/word/main/ftl/sql_table/document.ftl";
    private final static String WORD_ZIP_PATH = "build/word/qqq.docx";

    /**
     * 傳入由ParseSQLUtils所parse出來的sql_script vo
     * 
     * @param Map<String, TableVO> voMap
     */
    public static void parseVo(Map<String, TableVO> voMap) {
        
        // Freemarker configuration object
        Configuration cfg = new Configuration();
        
        // Build the data-model
        Map<String, Object> ftlVo = new HashMap<String, Object>();
        
        try {
            // Load template from source folder
            Template template = cfg.getTemplate(SQL_TABLE_FTL_PATH);
            ftlVo.put("tableData", voMap);

            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(ftlVo, out);
            out.flush();

            // File output
            Writer file = new FileWriter(new File(SQL_TABLE_WORD_TEMPLATE_PATH + "/word/document.xml"));
            template.process(ftlVo, file);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        // file to zip
        try {
            
            // zip output file:副檔名可直接命名為docx
            FileOutputStream f = new FileOutputStream(WORD_ZIP_PATH);

            // CheckedOutputStream ch = new CheckedOutputStream(f, new CRC32());
            // Adler32
            CheckedOutputStream ch = new CheckedOutputStream(f, new Adler32());
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    ch));

            // zip input folder
            File file = new File(SQL_TABLE_WORD_TEMPLATE_PATH);
            ZipUtils.fileZip(out, file, "sql_table");
            out.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
